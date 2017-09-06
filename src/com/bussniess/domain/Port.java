package com.bussniess.domain;

public class Port {
	private Integer portId;
	private Device device;
	private String ifDescr;
	private String ifName;
	private String ifSpeed;
	private String ifMtu;
	private String ifType;
	private String ifPhysAddrVarbinary;
	public Integer getPortId() {
		return portId;
	}
	public void setPortId(Integer portId) {
		this.portId = portId;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public String getIfDescr() {
		return ifDescr;
	}
	public void setIfDescr(String ifDescr) {
		this.ifDescr = ifDescr;
	}
	public String getIfName() {
		return ifName;
	}
	public void setIfName(String ifName) {
		this.ifName = ifName;
	}
	public String getIfSpeed() {
		return ifSpeed;
	}
	public void setIfSpeed(String ifSpeed) {
		this.ifSpeed = ifSpeed;
	}
	public String getIfMtu() {
		return ifMtu;
	}
	public void setIfMtu(String ifMtu) {
		this.ifMtu = ifMtu;
	}
	public String getIfType() {
		return ifType;
	}
	public void setIfType(String ifType) {
		this.ifType = ifType;
	}
	public String getIfPhysAddrVarbinary() {
		return ifPhysAddrVarbinary;
	}
	public void setIfPhysAddrVarbinary(String ifPhysAddrVarbinary) {
		this.ifPhysAddrVarbinary = ifPhysAddrVarbinary;
	}
	@Override
	public String toString() {
		return "Port [portId=" + portId + ", device=" + device + ", ifDescr=" + ifDescr + ", ifName=" + ifName
				+ ", ifSpeed=" + ifSpeed + ", ifMtu=" + ifMtu + ", ifType=" + ifType + ", ifPhysAddrVarbinary="
				+ ifPhysAddrVarbinary + "]";
	}
	

}
