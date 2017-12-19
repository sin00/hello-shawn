package com.ericsson.li.format.fastjson;

public class CepVehicle {

	@Override
	public String toString() {
		return "CepVehicle [vin=" + vin + ", series_code_vs=" + series_code_vs + ", model_code=" + model_code + ", id="
				+ id + "]";
	}
	private String	vin;
	private String	series_code_vs;
	private String	model_code;
	private int id;
/*	private String	mat_code;
	private String	color_code;
	private String	factory_code;
	private String	engine_no;
	private String	dealer_id;
	private String	model_code_as;*/
	public String getVin() {
		return vin;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getSeries_code_vs() {
		return series_code_vs;
	}
	public void setSeries_code_vs(String series_code_vs) {
		this.series_code_vs = series_code_vs;
	}
	public String getModel_code() {
		return model_code;
	}
	public void setModel_code(String model_code) {
		this.model_code = model_code;
	}
	
	/*public String getMat_code() {
		return mat_code;
	}
	public void setMat_code(String mat_code) {
		this.mat_code = mat_code;
	}
	public String getColor_code() {
		return color_code;
	}
	public void setColor_code(String color_code) {
		this.color_code = color_code;
	}
	public String getFactory_code() {
		return factory_code;
	}
	public void setFactory_code(String factory_code) {
		this.factory_code = factory_code;
	}
	public String getEngine_no() {
		return engine_no;
	}
	public void setEngine_no(String engine_no) {
		this.engine_no = engine_no;
	}
	public String getDealer_id() {
		return dealer_id;
	}
	public void setDealer_id(String dealer_id) {
		this.dealer_id = dealer_id;
	}
	public String getModel_code_as() {
		return model_code_as;
	}
	public void setModel_code_as(String model_code_as) {
		this.model_code_as = model_code_as;
	}*/
	
}
