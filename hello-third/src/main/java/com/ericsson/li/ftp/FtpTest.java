package com.ericsson.li.ftp;

public class FtpTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testFtp();
		testSFtp();
	}

	private static void testFtp() {
		String localDir = "D:\\logs";
		FtpConfig ftpConfig = new FtpConfig();
		ftpConfig.setHostname("192.168.88.128");
		ftpConfig.setPort(21);
		ftpConfig.setUsername("litx");
		ftpConfig.setPassword("xtil");
		ftpConfig.setRemoteDir("/000/ppp/");
		UploadFtp uploadFtp = new UFtp();
		if (uploadFtp.init(ftpConfig)) {
			uploadFtp.process(localDir);
			localDir = "D:\\ccc";
			uploadFtp.process(localDir);
			
			uploadFtp.process(localDir, "sss");
		}
		uploadFtp.destroy();
		
		String remoteDir = "/000/qqq/";
		uploadFtp.processOnce(localDir, remoteDir);	
	}
	
	private static void testSFtp() {
		String localDir = "D:\\logs";
		FtpConfig ftpConfig = new FtpConfig();
		ftpConfig.setHostname("192.168.88.128");
		ftpConfig.setPort(22);
		ftpConfig.setUsername("litx");
		ftpConfig.setPassword("xtil");
		ftpConfig.setRemoteDir("/home/litx/000/4444");
		UploadFtp uploadFtp = new USFtp();
		if (uploadFtp.init(ftpConfig)) {
			uploadFtp.process(localDir);
			localDir = "D:\\ccc";
			uploadFtp.process(localDir);
			
			uploadFtp.process(localDir, "sss");
		}
		uploadFtp.destroy();
		String remoteDir = "000/5555/";
		uploadFtp.processOnce(localDir, remoteDir);
	}

}
