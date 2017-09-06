package com.bussniess.domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


public class Device {
	
	private Integer deviceId;
	private String sysDescr;
	private String version;
	private Integer ifNumber;
	private Integer status;
	private String sysObjectId;
	private String sysName;
	private Date sysOrLastChange;
	private float deviceWeight;
	public Integer getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	public String getSysDescr() {
		return sysDescr;
	}
	public void setSysDescr(String sysDescr) {
		this.sysDescr = sysDescr;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Integer getIfNumber() {
		return ifNumber;
	}
	public void setIfNumber(Integer ifNumber) {
		this.ifNumber = ifNumber;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getSysObjectId() {
		return sysObjectId;
	}
	public void setSysObjectId(String sysObjectId) {
		this.sysObjectId = sysObjectId;
	}
	public String getSysName() {
		return sysName;
	}
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	public Date getSysOrLastChange() {
		return sysOrLastChange;
	}
	public void setSysOrLastChange(Date sysOrLastChange) {
		this.sysOrLastChange = sysOrLastChange;
	}
	public float getDeviceWeight() {
		return deviceWeight;
	}
	public void setDeviceWeight(float deviceWeight) {
		this.deviceWeight = deviceWeight;
	}
	@Override
	public String toString() {
		return "Device [deviceId=" + deviceId + ", sysDescr=" + sysDescr
				+ ", version=" + version + ", ifNumber=" + ifNumber
				+ ", status=" + status + ", sysObjectId=" + sysObjectId
				+ ", sysName=" + sysName + ", sysOrLastChange="
				+ sysOrLastChange + ", deviceWeight=" + deviceWeight + "]";
	}
	

}
