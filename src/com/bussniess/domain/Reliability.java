package com.bussniess.domain;

public class Reliability {
	private Integer relId;
	private Device device;
	private String ifInBytesNum;
	private String ifInAverageRate;
	private String ifOutAverageRate;
	private float concentration;
	@Override
	public String toString() {
		return "Reliability [relId=" + relId + ", device=" + device + ", ifInBytesNum=" + ifInBytesNum
				+ ", ifInAverageRate=" + ifInAverageRate + ", ifOutAverageRate=" + ifOutAverageRate + ", concentration="
				+ concentration + "]";
	}
	public Integer getRelId() {
		return relId;
	}
	public void setRelId(Integer relId) {
		this.relId = relId;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public String getIfInBytesNum() {
		return ifInBytesNum;
	}
	public void setIfInBytesNum(String ifInBytesNum) {
		this.ifInBytesNum = ifInBytesNum;
	}
	public String getIfInAverageRate() {
		return ifInAverageRate;
	}
	public void setIfInAverageRate(String ifInAverageRate) {
		this.ifInAverageRate = ifInAverageRate;
	}
	public String getIfOutAverageRate() {
		return ifOutAverageRate;
	}
	public void setIfOutAverageRate(String ifOutAverageRate) {
		this.ifOutAverageRate = ifOutAverageRate;
	}
	public float getConcentration() {
		return concentration;
	}
	public void setConcentration(float concentration) {
		this.concentration = concentration;
	}

}
