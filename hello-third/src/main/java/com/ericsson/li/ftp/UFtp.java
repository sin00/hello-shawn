package com.ericsson.li.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;

public class UFtp extends UploadFtp {
	private static Logger logger = LoggerFactory.getLogger(UFtp.class);
	private FTPClient ftpClient;
	
	protected boolean init(String remoteDir) {
		logger.info("MFtp init ...");
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(ftpConfig.getHostname(), ftpConfig.getPort());
			int reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				logger.error("FTP server is not positiveCompletion.");
				return false;
			}

			if (!ftpClient.login(ftpConfig.getUsername(), ftpConfig.getPassword())) {
				logger.error("FTP login error.");
				return false;
			}

			int fileType = FTPClient.ASCII_FILE_TYPE;
			if (ftpConfig.isBinaryFileType()) {
				fileType = FTPClient.BINARY_FILE_TYPE;
			}

			if (!ftpClient.setFileType(fileType)) {
				logger.error("FTP  setFileType-BINARY  error.");
				return false;
			}
			
			if (remoteDir.length() > 0) {
				if (remoteDir.startsWith("/")) {
					remoteRoot = remoteDir;
				} else {
					remoteRoot = ftpClient.printWorkingDirectory() + remoteDir;
				}
				ftpMkAndCdDir(remoteRoot);
			} else {
				remoteRoot = ftpClient.printWorkingDirectory();
			}
			
			logger.info("REMOTE ROOT:" + remoteRoot);
			
		} catch (Exception e) {
			logger.error("FTPClient init exception:" + e.getMessage());
			return false;
		}
		logger.info("MFtp init done.");
		return true;
	}
	
	public void destroy() {
		try {
			ftpClient.logout();
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}

		try {
			ftpClient.disconnect();
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}

		logger.info("MFtp destroy done.");
	}

	protected boolean ftpCdToParentDir() throws Exception {
		return ftpClient.changeToParentDirectory();
	}
	protected void ftpMkAndCdDir(String dirName) throws Exception {
		String[] subs = dirName.split("/");
		if ("/".equals(dirName.substring(0, 1))) {
			ftpClient.changeWorkingDirectory("/");
		}
		for (int i = 0; i < subs.length; i++) {
			if (subs[i].length() > 0) {
				if (!ftpClient.changeWorkingDirectory(subs[i])) {
					if (ftpClient.makeDirectory(subs[i])) {
						ftpClient.changeWorkingDirectory(subs[i]);
					}
				}
			}
		}

		logger.info("pwd:" + ftpClient.printWorkingDirectory());

	}

	protected void upload(File file) throws Exception {
		if (file.isDirectory()) {
			ftpMkAndCdDir(file.getName());
			File[] files = file.listFiles();
			//MFileUtil.sortByLastModified(files);
			for (int i = 0; i < files.length; i++) {
				upload(files[i]);
			}
			ftpCdToParentDir();
			deleteFile(file);
		} else {
			FileInputStream input = null;
			boolean flag = false;
			logger.info("upload begin...");
			logger.info("local: " + file.getAbsolutePath());
			logger.info("remote: " + ftpClient.printWorkingDirectory() + "/" + file.getName());
			try {
				input = new FileInputStream(file);
				if (ftpClient.storeFile(file.getName(), input)) {
					flag = true;
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			} finally {
				try {
					input.close();
				} catch (Exception e) {
					logger.warn(e.getMessage());
				}
				if (flag) {
					deleteFile(file);
					logger.info("upload success.");
				} else {
					logger.info("upload fail.");
				}
			}
		}
	}
	
	protected void cdRoot() throws Exception {
		ftpMkAndCdDir(remoteRoot);
	}
}


