package com.ericsson.li.HelloJdbcTemplate;

public class VehicleCity {
	public VehicleCity(int id, String name, String pswd) {
		this.id = id;
		this.name = name;
		this.passwd = pswd;
		};
		public VehicleCity(){};
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", pswd=" + passwd + "]";
	}
	private int id;  
    private String name;  
    private String passwd;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String pswd) {
		this.passwd = pswd;
	}  
}
