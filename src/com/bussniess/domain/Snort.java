package com.bussniess.domain;

public class Snort {

	private Integer snortId;
	private Device device;
	private String snortAlertNum;
	private String snortHighAlertNum;
	private String snortMediumAlertNum;
	private String snortLowAlertNum;
	public Integer getSnortId() {
		return snortId;
	}
	public void setSnortId(Integer snortId) {
		this.snortId = snortId;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public String getSnortAlertNum() {
		return snortAlertNum;
	}
	public void setSnortAlertNum(String snortAlertNum) {
		this.snortAlertNum = snortAlertNum;
	}
	public String getSnortHighAlertNum() {
		return snortHighAlertNum;
	}
	public void setSnortHighAlertNum(String snortHighAlertNum) {
		this.snortHighAlertNum = snortHighAlertNum;
	}
	public String getSnortMediumAlertNum() {
		return snortMediumAlertNum;
	}
	public void setSnortMediumAlertNum(String snortMediumAlertNum) {
		this.snortMediumAlertNum = snortMediumAlertNum;
	}
	public String getSnortLowAlertNum() {
		return snortLowAlertNum;
	}
	public void setSnortLowAlertNum(String snortLowAlertNum) {
		this.snortLowAlertNum = snortLowAlertNum;
	}
	@Override
	public String toString() {
		return "Snort [snortId=" + snortId + ", device=" + device + ", snortAlertNum=" + snortAlertNum
				+ ", snortHighAlertNum=" + snortHighAlertNum + ", snortMediumAlertNum=" + snortMediumAlertNum
				+ ", snortLowAlertNum=" + snortLowAlertNum + "]";
	}
	
}
