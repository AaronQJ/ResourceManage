package com.bussniess.domain;



public class logText {
	private int logId;
	private String userName;
	private String operTime;
	private String objName;
	private String objType;
	private String logType;
	private String objId;
	public String getObjId() {
		return objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}
	public String getLogType() {
		return logType;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	public String getObjType() {
		return objType;
	}
	public void setObjType(String objType) {
		this.objType = objType;
	}
	
	private String fieldName;
	private String fieldOriValue;
	private String fieldUpdValue;
	private String operType;
	
	public int getLogId() {
		return logId;
	}
	public void setLogId(int logId) {
		this.logId = logId;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOperTime() {
		return operTime;
	}
	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}
	public String getObjName() {
		return objName;
	}
	public void setObjName(String objName) {
		this.objName = objName;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldOriValue() {
		return fieldOriValue;
	}
	public void setFieldOriValue(String fieldOriValue) {
		this.fieldOriValue = fieldOriValue;
	}
	public String getFieldUpdValue() {
		return fieldUpdValue;
	}
	public void setFieldUpdValue(String fieldUpdValue) {
		this.fieldUpdValue = fieldUpdValue;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	
}
