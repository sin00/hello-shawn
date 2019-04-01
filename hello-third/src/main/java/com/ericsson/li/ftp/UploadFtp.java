package com.ericsson.li.ftp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public abstract class UploadFtp {
	private static Logger logger = LoggerFactory.getLogger(UploadFtp.class);
	protected FtpConfig ftpConfig;
	protected String remoteRoot = "/";
	public boolean init(FtpConfig ftpConfig) {
		this.ftpConfig = ftpConfig;
		return init(ftpConfig.getRemoteDir());
	}
	protected abstract boolean init(String remoteDir);
	public abstract void destroy();
	/**
	 * 如下步骤，多次调用process，使用同一个连接，上传到同一路径，ftpConfig。getRemoteDir()
	 * 1. init
	 * 2. process(String localDir)
	 * 3. destroy
	 * 
	 */
	public void process(String localDir) {
		try {
			cdRoot();
		} catch (Exception e) {
			logger.error("process error:" + e.getMessage());
			return;
		}
		
		
		File localFile = new File(localDir);
		if (!localFile.exists()) {
			logger.error("directory[" + localDir + "] is not exist.");
			return;
		}
		File[] files = localFile.listFiles();
		for (File file : files) {
			try {				
				upload(file);
			} catch (Exception e) {
				logger.error("upload file[" + file.getAbsolutePath() + "] error:" + e.getMessage());
			}
			
		}
	}
	
	/**
	 * 如下步骤，多次调用process，使用同一个连接，上传到同一路径，ftpConfig。getRemoteDir()不同的remoteSubDir
	 * 1. init
	 * 2. process(String localDir, String remoteSubDir)
	 * 3. destroy
	 * 
	 */
	public void process(String localDir, String remoteSubDir) {
		try {
			cdRoot();
			if (remoteSubDir != null && remoteSubDir.length() > 0) {
				ftpMkAndCdDir(remoteSubDir);
			}
		} catch (Exception e) {
			logger.error("process error:" + e.getMessage());
			return;
		}
		
		
		File localFile = new File(localDir);
		if (!localFile.exists()) {
			logger.error("directory[" + localDir + "] is not exist.");
			return;
		}
		File[] files = localFile.listFiles();
		for (File file : files) {
			try {				
				upload(file);
			} catch (Exception e) {
				logger.error("upload file[" + file.getAbsolutePath() + "] error:" + e.getMessage());
			}
			
		}
	}
	
	/**
	 * 每次调用process，使用不同连接，上传到remoteDir
	 * processOnce(String localDir, String remoteDir)
	 * 
	 */
	public void processOnce(String localDir, String remoteDir) {
		if (!this.init(remoteDir)) {
			return;
		}
		try {
			File localFile = new File(localDir);
			if (!localFile.exists()) {
				logger.error("directory[" + localDir + "] is not exist.");
				return;
			}
			File[] files = localFile.listFiles();
			for (File file : files) {
				try {
					upload(file);
				} catch (Exception e) {
					logger.error("upload file[" + file.getAbsolutePath() + "] error:" + e.getMessage());
				}

			}
		} finally {
			this.destroy();
		}
	}
	
	protected abstract void cdRoot() throws Exception;
	protected abstract void ftpMkAndCdDir(String dirName) throws Exception;
	protected abstract boolean ftpCdToParentDir() throws Exception;
	protected abstract void upload(File file) throws Exception;
	protected void deleteFile(File file) {
		if (!ftpConfig.isDeleteFlag()) {
			return;
		}
		logger.debug("delete file: " + file.getAbsolutePath());
		try {
			file.delete();
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
	}	
}
