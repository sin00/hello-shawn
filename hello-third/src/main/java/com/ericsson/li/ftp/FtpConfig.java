package com.ericsson.li.ftp;

public class FtpConfig {
	private String hostname;
	private int port;
	private String username;
	private String password;
	private boolean binaryFileType;
	private String remoteDir = "";
	private boolean deleteFlag = true;
	
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isBinaryFileType() {
		return binaryFileType;
	}
	public void setBinaryFileType(boolean binaryFileType) {
		this.binaryFileType = binaryFileType;
	}
	public String getRemoteDir() {
		return remoteDir;
	}
	public void setRemoteDir(String remoteDir) {
		this.remoteDir = remoteDir;
	}
	public boolean isDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}	
}
