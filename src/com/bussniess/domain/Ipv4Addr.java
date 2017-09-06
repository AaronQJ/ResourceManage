package com.bussniess.domain;

public class Ipv4Addr {
	private Integer ipAddrId;
	private Port port;
	private String ipv4Addr;
	private String ipv4Mask;
	public Integer getIpAddrId() {
		return ipAddrId;
	}
	public void setIpAddrId(Integer ipAddrId) {
		this.ipAddrId = ipAddrId;
	}
	public Port getPort() {
		return port;
	}
	public void setPort(Port port) {
		this.port = port;
	}
	public String getIpv4Addr() {
		return ipv4Addr;
	}
	public void setIpv4Addr(String ipv4Addr) {
		this.ipv4Addr = ipv4Addr;
	}
	public String getIpv4Mask() {
		return ipv4Mask;
	}
	public void setIpv4Mask(String ipv4Mask) {
		this.ipv4Mask = ipv4Mask;
	}
	@Override
	public String toString() {
		return "Ipv4Addr [ipAddrId=" + ipAddrId + ", port=" + port + ", ipv4Addr=" + ipv4Addr + ", ipv4Mask=" + ipv4Mask
				+ "]";
	}
	

}
