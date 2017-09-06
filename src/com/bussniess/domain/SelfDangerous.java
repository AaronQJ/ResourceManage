package com.bussniess.domain;

public class SelfDangerous {
	private Integer selfDanId;
	private Device device;
	private String bitValue;
	public Integer getSelfDanId() {
		return selfDanId;
	}
	public void setSelfDanId(Integer selfDanId) {
		this.selfDanId = selfDanId;
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
		return "Selfdangerous [selfDanId=" + selfDanId + ", device=" + device + ", bitValue=" + bitValue + "]";
	}
	

}
