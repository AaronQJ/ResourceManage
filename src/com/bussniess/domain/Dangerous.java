package com.bussniess.domain;

public class Dangerous {
	private Integer dangerId;
	private Device device;
	private Integer idsAlertNum;
	private String ifOutErrorRate;
	private String ifInErrorRate;
	private String ifOutPackageLoss;
	private String ifInPackageLoss;
	private String ipInErrorRate;
	private String ipOutErrorRate;
	private String ifInFlowGrowthRate;
	private String ifInFlowNum;
	private String ifOutFlowNum;
	private float concentration;
	public Integer getDangerId() {
		return dangerId;
	}
	public void setDangerId(Integer dangerId) {
		this.dangerId = dangerId;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public Integer getIdsAlertNum() {
		return idsAlertNum;
	}
	public void setIdsAlertNum(Integer idsAlertNum) {
		this.idsAlertNum = idsAlertNum;
	}
	public String getIfOutErrorRate() {
		return ifOutErrorRate;
	}
	public void setIfOutErrorRate(String ifOutErrorRate) {
		this.ifOutErrorRate = ifOutErrorRate;
	}
	public String getIfInErrorRate() {
		return ifInErrorRate;
	}
	public void setIfInErrorRate(String ifInErrorRate) {
		this.ifInErrorRate = ifInErrorRate;
	}
	public String getIfOutPackageLoss() {
		return ifOutPackageLoss;
	}
	public void setIfOutPackageLoss(String ifOutPackageLoss) {
		this.ifOutPackageLoss = ifOutPackageLoss;
	}
	public String getIfInPackageLoss() {
		return ifInPackageLoss;
	}
	public void setIfInPackageLoss(String ifInPackageLoss) {
		this.ifInPackageLoss = ifInPackageLoss;
	}
	public String getIpInErrorRate() {
		return ipInErrorRate;
	}
	public void setIpInErrorRate(String ipInErrorRate) {
		this.ipInErrorRate = ipInErrorRate;
	}
	public String getIpOutErrorRate() {
		return ipOutErrorRate;
	}
	public void setIpOutErrorRate(String ipOutErrorRate) {
		this.ipOutErrorRate = ipOutErrorRate;
	}
	public String getIfInFlowGrowthRate() {
		return ifInFlowGrowthRate;
	}
	public void setIfInFlowGrowthRate(String ifInFlowGrowthRate) {
		this.ifInFlowGrowthRate = ifInFlowGrowthRate;
	}
	public String getIfInFlowNum() {
		return ifInFlowNum;
	}
	public void setIfInFlowNum(String ifInFlowNum) {
		this.ifInFlowNum = ifInFlowNum;
	}
	public String getIfOutFlowNum() {
		return ifOutFlowNum;
	}
	public void setIfOutFlowNum(String ifOutFlowNum) {
		this.ifOutFlowNum = ifOutFlowNum;
	}
	public float getConcentration() {
		return concentration;
	}
	public void setConcentration(float concentration) {
		this.concentration = concentration;
	}
	@Override
	public String toString() {
		return "Dangerous [dangerId=" + dangerId + ", device=" + device + ", idsAlertNum=" + idsAlertNum
				+ ", ifOutErrorRate=" + ifOutErrorRate + ", ifInErrorRate=" + ifInErrorRate + ", ifOutPackageLoss="
				+ ifOutPackageLoss + ", ifInPackageLoss=" + ifInPackageLoss + ", ipInErrorRate=" + ipInErrorRate
				+ ", ipOutErrorRate=" + ipOutErrorRate + ", ifInFlowGrowthRate=" + ifInFlowGrowthRate + ", ifInFlowNum="
				+ ifInFlowNum + ", ifOutFlowNum=" + ifOutFlowNum + ", concentration=" + concentration + "]";
	}
	

}
