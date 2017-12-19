package com.ericsson.li.tool.vrn;



public class PlateInfo {
	private String platePrefix;
	private String city;
	private String province;
	public String getPlatePrefix() {
		return platePrefix;
	}
	public void setPlatePrefix(String platePrefix) {
		this.platePrefix = platePrefix;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("PlateInfo [platePrefix=");
		sb.append(platePrefix);
		sb.append(", city=");
		sb.append(city);
		sb.append(", province=");
		sb.append(province);
		return sb.toString();
	}
		
}
