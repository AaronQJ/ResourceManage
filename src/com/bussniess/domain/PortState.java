package com.bussniess.domain;

import java.util.Date;

public class PortState {
	private Integer portStateId;
	private Port port;
	private String ifInUcastPkts;
	private String ifOutUcastPkts;
	private String ifOperStatus;
	private String ifAdminStatus;
	private String ifInOctets;
	private String ifInNUcastPkts;
	private String ifInDiscards;
	private String ifInErrors;
	private String ifInUnknownProtos;
	private String ifOutOctets;
	private String ifOutNUcastPkts;
	private String ifOutDiscards;
	private String ifOutErrors;
	private Date ifLastChange;
	public Integer getPortStateId() {
		return portStateId;
	}
	public void setPortStateId(Integer portStateId) {
		this.portStateId = portStateId;
	}
	public Port getPort() {
		return port;
	}
	public void setPort(Port port) {
		this.port = port;
	}
	public String getIfInUcastPkts() {
		return ifInUcastPkts;
	}
	public void setIfInUcastPkts(String ifInUcastPkts) {
		this.ifInUcastPkts = ifInUcastPkts;
	}
	public String getIfOutUcastPkts() {
		return ifOutUcastPkts;
	}
	public void setIfOutUcastPkts(String ifOutUcastPkts) {
		this.ifOutUcastPkts = ifOutUcastPkts;
	}
	public String getIfOperStatus() {
		return ifOperStatus;
	}
	public void setIfOperStatus(String ifOperStatus) {
		this.ifOperStatus = ifOperStatus;
	}
	public String getIfAdminStatus() {
		return ifAdminStatus;
	}
	public void setIfAdminStatus(String ifAdminStatus) {
		this.ifAdminStatus = ifAdminStatus;
	}
	public String getIfInOctets() {
		return ifInOctets;
	}
	public void setIfInOctets(String ifInOctets) {
		this.ifInOctets = ifInOctets;
	}
	public String getIfInNUcastPkts() {
		return ifInNUcastPkts;
	}
	public void setIfInNUcastPkts(String ifInNUcastPkts) {
		this.ifInNUcastPkts = ifInNUcastPkts;
	}
	public String getIfInDiscards() {
		return ifInDiscards;
	}
	public void setIfInDiscards(String ifInDiscards) {
		this.ifInDiscards = ifInDiscards;
	}
	public String getIfInErrors() {
		return ifInErrors;
	}
	public void setIfInErrors(String ifInErrors) {
		this.ifInErrors = ifInErrors;
	}
	public String getIfInUnknownProtos() {
		return ifInUnknownProtos;
	}
	public void setIfInUnknownProtos(String ifInUnknownProtos) {
		this.ifInUnknownProtos = ifInUnknownProtos;
	}
	public String getIfOutOctets() {
		return ifOutOctets;
	}
	public void setIfOutOctets(String ifOutOctets) {
		this.ifOutOctets = ifOutOctets;
	}
	public String getIfOutNUcastPkts() {
		return ifOutNUcastPkts;
	}
	public void setIfOutNUcastPkts(String ifOutNUcastPkts) {
		this.ifOutNUcastPkts = ifOutNUcastPkts;
	}
	public String getIfOutDiscards() {
		return ifOutDiscards;
	}
	public void setIfOutDiscards(String ifOutDiscards) {
		this.ifOutDiscards = ifOutDiscards;
	}
	public String getIfOutErrors() {
		return ifOutErrors;
	}
	public void setIfOutErrors(String ifOutErrors) {
		this.ifOutErrors = ifOutErrors;
	}
	public Date getIfLastChange() {
		return ifLastChange;
	}
	public void setIfLastChange(Date ifLastChange) {
		this.ifLastChange = ifLastChange;
	}
	@Override
	public String toString() {
		return "PortState [portStateId=" + portStateId + ", port=" + port + ", ifInUcastPkts=" + ifInUcastPkts
				+ ", ifOutUcastPkts=" + ifOutUcastPkts + ", ifOperStatus=" + ifOperStatus + ", ifAdminStatus="
				+ ifAdminStatus + ", ifInOctets=" + ifInOctets + ", ifInNUcastPkts=" + ifInNUcastPkts
				+ ", ifInDiscards=" + ifInDiscards + ", ifInErrors=" + ifInErrors + ", ifInUnknownProtos="
				+ ifInUnknownProtos + ", ifOutOctets=" + ifOutOctets + ", ifOutNUcastPkts=" + ifOutNUcastPkts
				+ ", ifOutDiscards=" + ifOutDiscards + ", ifOutErrors=" + ifOutErrors + ", ifLastChange=" + ifLastChange
				+ "]";
	}
	

}
