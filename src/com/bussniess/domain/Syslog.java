package com.bussniess.domain;

public class Syslog {
	private Integer syslogId;
	private Device device;
	private String syslogAlertNum;
	private String syslogHighAlertNum;
	private String syslogMediumAlertNum;
	private String syslogLowAlertNum;
	public Integer getSyslogId() {
		return syslogId;
	}
	public void setSyslogId(Integer syslogId) {
		this.syslogId = syslogId;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public String getSyslogAlertNum() {
		return syslogAlertNum;
	}
	public void setSyslogAlertNum(String syslogAlertNum) {
		this.syslogAlertNum = syslogAlertNum;
	}
	public String getSyslogHighAlertNum() {
		return syslogHighAlertNum;
	}
	public void setSyslogHighAlertNum(String syslogHighAlertNum) {
		this.syslogHighAlertNum = syslogHighAlertNum;
	}
	public String getSyslogMediumAlertNum() {
		return syslogMediumAlertNum;
	}
	public void setSyslogMediumAlertNum(String syslogMediumAlertNum) {
		this.syslogMediumAlertNum = syslogMediumAlertNum;
	}
	public String getSyslogLowAlertNum() {
		return syslogLowAlertNum;
	}
	public void setSyslogLowAlertNum(String syslogLowAlertNum) {
		this.syslogLowAlertNum = syslogLowAlertNum;
	}
	@Override
	public String toString() {
		return "Syslog [syslogId=" + syslogId + ", device=" + device + ", syslogAlertNum=" + syslogAlertNum
				+ ", syslogHighAlertNum=" + syslogHighAlertNum + ", syslogMediumAlertNum=" + syslogMediumAlertNum
				+ ", syslogLowAlertNum=" + syslogLowAlertNum + "]";
	}
	

}
