package com.bussniess.domain;

import java.io.Serializable;
import java.util.Date;


public class ProBak implements Serializable {

	private static final long serialVersionUID = 1L;
	private String bakId;
	private String  bakSeqNum;
	private String  bakVersion;
	private String  bakFactory;
	private String  bakDevType	;
	private String  bakForm;	
	private String  bakState;
	private String  bakManaDepart;
	private String  bakUseDepart;
	private String  bakUser	;
	private String  bakDevRoom;
	private String  bakDevFrame;
	private String  bakBuyCont;
	private Date    bakArrAcceptTime;
	private String  bakMaintainCont;
	private String  bakMaintainFactor;
	private Date   bakMaintainDeadLine;
	private String  bakRecorder;
	private String  bakRemark;
	private String loginUserName;
	private String  bakUpdTime;


	
	public String getBakId() {
		return bakId;
	}
	public void setBakId(String bakId) {
		this.bakId = bakId;
	}
	public String getBakSeqNum() {
		return bakSeqNum;
	}
	public void setBakSeqNum(String bakSeqNum) {
		this.bakSeqNum = bakSeqNum;
	}
	public String getBakVersion() {
		return bakVersion;
	}
	public void setBakVersion(String bakVersion) {
		this.bakVersion = bakVersion;
	}
	public String getBakFactory() {
		return bakFactory;
	}
	public void setBakFactory(String bakFactory) {
		this.bakFactory = bakFactory;
	}
	public String getBakDevType() {
		return bakDevType;
	}
	public void setBakDevType(String bakDevType) {
		this.bakDevType = bakDevType;
	}
	public String getBakForm() {
		return bakForm;
	}
	public void setBakForm(String bakForm) {
		this.bakForm = bakForm;
	}
	public String getBakState() {
		return bakState;
	}
	public void setBakState(String bakState) {
		this.bakState = bakState;
	}
	public String getBakManaDepart() {
		return bakManaDepart;
	}
	public void setBakManaDepart(String bakManaDepart) {
		this.bakManaDepart = bakManaDepart;
	}
	public String getBakUseDepart() {
		return bakUseDepart;
	}
	public void setBakUseDepart(String bakUseDepart) {
		this.bakUseDepart = bakUseDepart;
	}
	public String getBakUser() {
		return bakUser;
	}
	public void setBakUser(String bakUser) {
		this.bakUser = bakUser;
	}
	public String getBakDevRoom() {
		return bakDevRoom;
	}
	public void setBakDevRoom(String bakDevRoom) {
		this.bakDevRoom = bakDevRoom;
	}
	public String getBakDevFrame() {
		return bakDevFrame;
	}
	public void setBakDevFrame(String bakDevFrame) {
		this.bakDevFrame = bakDevFrame;
	}
	public String getBakBuyCont() {
		return bakBuyCont;
	}
	public void setBakBuyCont(String bakBuyCont) {
		this.bakBuyCont = bakBuyCont;
	}
	
	
	
	
	
	public String getBakMaintainCont() {
		return bakMaintainCont;
	}
	public void setBakMaintainCont(String bakMaintainCont) {
		this.bakMaintainCont = bakMaintainCont;
	}
	public String getBakMaintainFactor() {
		return bakMaintainFactor;
	}
	public void setBakMaintainFactor(String bakMaintainFactor) {
		this.bakMaintainFactor = bakMaintainFactor;
	}

	
	
	

	public String getBakRecorder() {
		return bakRecorder;
	}
	public void setBakRecorder(String bakRecorder) {
		this.bakRecorder = bakRecorder;
	}
	public String getBakRemark() {
		return bakRemark;
	}
	public void setBakRemark(String bakRemark) {
		this.bakRemark = bakRemark;
	}
	public String getLoginUserName() {
		return loginUserName;
	}
	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}
	public String getBakUpdTime() {
		return bakUpdTime;
	}
	public void setBakUpdTime(String bakUpdTime) {
		this.bakUpdTime = bakUpdTime;
	}
	public Date getBakArrAcceptTime() {
		return bakArrAcceptTime;
	}
	public void setBakArrAcceptTime(Date bakArrAcceptTime) {
		this.bakArrAcceptTime = bakArrAcceptTime;
	}
	public Date getBakMaintainDeadLine() {
		return bakMaintainDeadLine;
	}
	public void setBakMaintainDeadLine(Date bakMaintainDeadLine) {
		this.bakMaintainDeadLine = bakMaintainDeadLine;
	}
	
	

}
