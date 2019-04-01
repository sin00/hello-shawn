package com.ericsson.li.ftp;


public class UploadFtpMgr {
	private static class SingletonHolder {
		private final static UploadFtpMgr INSTANCE = new UploadFtpMgr();
	}
	private UploadFtpMgr() {}
	public final static UploadFtpMgr getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	/**
	 * @param ftpType default is ftp, 1 sftp
	 * @return
	 */
	public UploadFtp getUploadFtp(int ftpType) {
		if (ftpType == 1) {
			return new USFtp();
		}
		return new UFtp();
	}
	
	public UploadFtp getFtp() {
		return new UFtp();
	}
	
	public UploadFtp getSFtp() {
		return new USFtp();
	}
}
