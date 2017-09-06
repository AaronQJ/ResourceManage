package com.bussniess.domain;

public class SelfAvailability {
	private Integer selfAvaId;
	private Device device;
	private String bitValue;
	public Integer getSelfAvaId() {
		return selfAvaId;
	}
	public void setSelfAvaId(Integer selfAvaId) {
		this.selfAvaId = selfAvaId;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public String getBitValue() {
		return bitValue;
	}
	public void setBitValue(String bitValue) {
		this.bitValue = bitValue;
	}
	@Override
	public String toString() {
		return "SelfAvailability [selfAvaId=" + selfAvaId + ", device=" + device + ", bitValue=" + bitValue + "]";
	}
	
}
