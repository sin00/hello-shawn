package com.ericsson.li.ftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class USFtp extends UploadFtp {
	private static Logger logger = LoggerFactory.getLogger(USFtp.class);
	private ChannelSftp channelSftp = null;
	
	protected boolean init(String remoteDir) {
		logger.info("USFtp init ...");
		try {
			JSch jsch = new JSch();
			Session sshSession = jsch.getSession(ftpConfig.getUsername(), ftpConfig.getHostname(), ftpConfig.getPort());
			sshSession.setPassword(ftpConfig.getPassword());
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			channelSftp = (ChannelSftp) sshSession.openChannel("sftp");
			channelSftp.connect();
			if (remoteDir.length() > 0) {
				if (remoteDir.startsWith("/")) {
					remoteRoot = remoteDir;
				} else {
					remoteRoot = channelSftp.pwd() + "/" + remoteDir;
				}
				
				ftpMkAndCdDir(remoteRoot);
			} else {
				remoteRoot = channelSftp.pwd();
			}
			
			logger.info("REMOTE ROOT:" + remoteRoot);
		} catch (Exception e) {
			logger.error("ChannelSftp init exception:" + e.getMessage());
			return false;
		}
		logger.info("USFtp init done.");
		return true;
	}
	
	public void destroy() {
		Session session = null;
		try {
			session = channelSftp.getSession();
			if (session != null) {
				channelSftp.getSession().disconnect();
			}	
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}

		try {
			channelSftp.disconnect();
			channelSftp.quit();
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}

		
		logger.info("USFtp destroy done.");
	}

	protected boolean ftpCdToParentDir() throws Exception {
		channelSftp.cd("../");
		return true;
	}
	
	protected void ftpMkAndCdDir(String dirName) throws Exception {
		//logger.info("pwd11:" + channelSftp.pwd());
		String[] subs = dirName.split("/");
		if ("/".equals(dirName.substring(0, 1))) {
			channelSftp.cd("/");
		}

		for (int i = 0; i < subs.length; i++) {
			if (subs[i].length() > 0) {
				try {
					channelSftp.cd(subs[i]);
				} catch (Exception e) {
					channelSftp.mkdir(subs[i]);
					channelSftp.cd(subs[i]);
				}
			}
		}

		logger.info("pwd:" + channelSftp.pwd());
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
			logger.info("remote: " + channelSftp.pwd() + "/" + file.getName());
			try {
				input = new FileInputStream(file);
				channelSftp.put(input, file.getName());
				flag = true;
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
		channelSftp.cd(remoteRoot);
	}
}
