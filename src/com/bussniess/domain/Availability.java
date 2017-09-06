package com.bussniess.domain;

public class Availability {
	private Integer avaId;
	private Device device;
	private String bandwithUsage;
	private String ifThroughPutRate;
	private String CPUUsage;
	private String memoryUsage;
	private float concentration;
	public Integer getAvaId() {
		return avaId;
	}
	public void setAvaId(Integer avaId) {
		this.avaId = avaId;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public String getBandwithUsage() {
		return bandwithUsage;
	}
	public void setBandwithUsage(String bandwithUsage) {
		this.bandwithUsage = bandwithUsage;
	}
	public String getIfThroughPutRate() {
		return ifThroughPutRate;
	}
	public void setIfThroughPutRate(String ifThroughPutRate) {
		this.ifThroughPutRate = ifThroughPutRate;
	}
	public String getCPUUsage() {
		return CPUUsage;
	}
	public void setCPUUsage(String cPUUsage) {
		CPUUsage = cPUUsage;
	}
	public String getMemoryUsage() {
		return memoryUsage;
	}
	public void setMemoryUsage(String memoryUsage) {
		this.memoryUsage = memoryUsage;
	}
	public float getConcentration() {
		return concentration;
	}
	public void setConcentration(float concentration) {
		this.concentration = concentration;
	}
	@Override
	public String toString() {
		return "Availability [avaId=" + avaId + ", device=" + device + ", bandwithUsage=" + bandwithUsage
				+ ", ifThroughPutRate=" + ifThroughPutRate + ", CPUUsage=" + CPUUsage + ", memoryUsage=" + memoryUsage
				+ ", concentration=" + concentration + "]";
	}
	

}
