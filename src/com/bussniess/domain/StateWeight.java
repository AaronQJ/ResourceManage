package com.bussniess.domain;

public class StateWeight {

	private Integer weightId;
	private Device device;
	private float vulWeight;
	private float danWeight;
	private float avaWeight;
	private float relWeight;
	public Integer getWeightId() {
		return weightId;
	}
	public void setWeightId(Integer weightId) {
		this.weightId = weightId;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public float getVulWeight() {
		return vulWeight;
	}
	public void setVulWeight(float vulWeight) {
		this.vulWeight = vulWeight;
	}
	public float getDanWeight() {
		return danWeight;
	}
	public void setDanWeight(float danWeight) {
		this.danWeight = danWeight;
	}
	public float getAvaWeight() {
		return avaWeight;
	}
	public void setAvaWeight(float avaWeight) {
		this.avaWeight = avaWeight;
	}
	public float getRelWeight() {
		return relWeight;
	}
	public void setRelWeight(float relWeight) {
		this.relWeight = relWeight;
	}
	@Override
	public String toString() {
		return "StateWeight [weightId=" + weightId + ", device=" + device + ", vulWeight=" + vulWeight + ", danWeight="
				+ danWeight + ", avaWeight=" + avaWeight + ", relWeight=" + relWeight + "]";
	}
	
}
