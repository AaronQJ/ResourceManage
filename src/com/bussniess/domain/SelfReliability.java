package com.bussniess.domain;

public class SelfReliability {
	private Integer selfRelId;
	private Device device;
	private String bitValue;
	@Override
	public String toString() {
		return "SelfReliability [selfRelId=" + selfRelId + ", device=" + device + ", bitValue=" + bitValue + "]";
	}
	public Integer getSelfRelId() {
		return selfRelId;
	}
	public void setSelfRelId(Integer selfRelId) {
		this.selfRelId = selfRelId;
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

}
