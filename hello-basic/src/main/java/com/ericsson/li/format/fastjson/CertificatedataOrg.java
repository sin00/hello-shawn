package com.ericsson.li.format.fastjson;

public class CertificatedataOrg {
	private String fingerprint;
	private String subjectRDN;
	private String issuerRDN;
	private String serialNumber;
	private long expireDate;
	private long revocationDate;
	private String status;
	
	private int intStatus;
	private String jsonString;
	private String vin;
	private String ecuType;
	
	public String getFingerprint() {
		return fingerprint;
	}


	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}


	public String getSubjectRDN() {
		return subjectRDN;
	}


	public void setSubjectRDN(String subjectRDN) {
		this.subjectRDN = subjectRDN;
	}


	public String getIssuerRDN() {
		return issuerRDN;
	}


	public void setIssuerRDN(String issuerRDN) {
		this.issuerRDN = issuerRDN;
	}


	public String getSerialNumber() {
		return serialNumber;
	}


	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}


	public long getExpireDate() {
		return expireDate;
	}


	public void setExpireDate(long expireDate) {
		this.expireDate = expireDate;
	}


	public long getRevocationDate() {
		return revocationDate;
	}


	public void setRevocationDate(long revocationDate) {
		this.revocationDate = revocationDate;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getIntStatus() {
		return intStatus;
	}


	public void setIntStatus(int intStatus) {
		this.intStatus = intStatus;
	}
	

	public String getVin() {
		return vin;
	}


	public void setVin(String vin) {
		this.vin = vin;
	}


	public String getEcuType() {
		return ecuType;
	}


	public void setEcuType(String ecuType) {
		this.ecuType = ecuType;
	}


	public String getJsonString() {
		return jsonString;
	}


	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
	
/*	public String toLine(String yyyyMMddHHmmss) {
		StringBuilder sb = new StringBuilder();
		sb.append(yyyyMMddHHmmss).append("|");
		sb.append(fingerprint);
		sb.append(", subjectRDN=");
		sb.append(subjectRDN);
		sb.append(", issuerRDN=");
		sb.append(issuerRDN);
		sb.append(", serialNumber=");
		sb.append(serialNumber);
		sb.append(", expireDate=");
		sb.append(expireDate);
		sb.append(", revocationDate=");
		sb.append(revocationDate);
		sb.append(", status=");
		sb.append(status);
		return sb.toString();
	}*/



	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("CertificatedataOrg [fingerprint=");
		sb.append(fingerprint);
		sb.append(", subjectRDN=");
		sb.append(subjectRDN);
		sb.append(", issuerRDN=");
		sb.append(issuerRDN);
		sb.append(", serialNumber=");
		sb.append(serialNumber);
		sb.append(", expireDate=");
		sb.append(expireDate);
		sb.append(", revocationDate=");
		sb.append(revocationDate);
		sb.append(", status=");
		sb.append(status);
		sb.append(", vin=");
		sb.append(vin);		
		sb.append(", ecuType=");
		sb.append(ecuType);		
		return sb.toString();
	}
/*
 * {
"fingerprint":
"507D2F3C293E42595F22267A05697060F5C3AFC1467248E5969BDB7C973B0B1B",
"subjectRDN": "CN=11111111111115, OU=201230, O=2013, L=3CN6214,
ST=Zhejiang, C=CN, DC=873, DC=Connected Car, DC=XZY",
"issuerRDN": "CN=Global Vehicle Issuing Production CA,
OU=Zhejiang Geely Holding Group Co. Ltd, O=Geely Automobile Holdings
Ltd, L=Hangzhou, ST=Zhejiang, C=CN, DC=Geely, DC=Connected Car",
"serialNumber": "31F07D0FB2189B3C253C304869B28FFA5820E7EF",
"expireDate": -1,
"revocationDate": 1476230836,
"status": "REVOKED"
}
 * 
 * 
 * */

/*	@Override
	public String toLine() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("\"fingerprint\":").append(fingerprint);
		sb.append("\",\"subjectRDN\":").append(subjectRDN);
		sb.append("\",\"subjectRDN\":").append(subjectRDN);
		sb.append(", subjectRDN=");
		sb.append(subjectRDN);
		sb.append(", issuerRDN=");
		sb.append(issuerRDN);
		sb.append(", serialNumber=");
		sb.append(serialNumber);
		sb.append(", expireDate=");
		sb.append(expireDate);
		sb.append(", revocationDate=");
		sb.append(revocationDate);
		sb.append(", status=");
		sb.append(status);
		return sb.toString();
	}*/		
}
