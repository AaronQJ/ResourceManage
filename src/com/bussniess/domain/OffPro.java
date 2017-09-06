
package com.bussniess.domain;

import java.io.Serializable;
import java.util.Date;


public class OffPro implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String offId;
	private String offBarCode;
	private String	offNum;
	private String	offName;
	private String	offDevVersion;
	private Float	offPrice;
	private Date		offObtDate;
	private String	offUseDepart;
	private String	offUser;
	private String	offManager;
	private String	offRemark;
	private String	offUseState;
	private String	offState;
	private String	offSeqNum;
	private String loginUserName;
	private String  offUpdTime;
	private int offLifeTime;
	private String offFlag;
	
	
	

	public String isOffFlag() {
		return offFlag;
	}
	public void setOffFlag(String offFlag) {
		this.offFlag = offFlag;
	}
	public String getOffId() {
		return offId;
	}
	public void setOffId(String offId) {
		this.offId = offId;
	}
	public String getOffBarCode() {
		return offBarCode;
	}
	public void setOffBarCode(String offBarCode) {
		this.offBarCode = offBarCode;
	}
	public String getOffNum() {
		return offNum;
	}
	public void setOffNum(String offNum) {
		this.offNum = offNum;
	}
	public String getOffName() {
		return offName;
	}
	public void setOffName(String offName) {
		this.offName = offName;
	}
	public String getOffDevVersion() {
		return offDevVersion;
	}
	public void setOffDevVersion(String offDevVersion) {
		this.offDevVersion = offDevVersion;
	}
	public Float getOffPrice() {
		return offPrice;
	}
	public void setOffPrice(Float offPrice) {
		this.offPrice = offPrice;
	}
	
	
	

	public String getOffUser() {
		return offUser;
	}
	public void setOffUser(String offUser) {
		this.offUser = offUser;
	}
	public Date getOffObtDate() {
		return offObtDate;
	}
	public void setOffObtDate(Date offObtDate) {
		this.offObtDate = offObtDate;
	}
	public String getOffUseDepart() {
		return offUseDepart;
	}
	public void setOffUseDepart(String offUseDepart) {
		this.offUseDepart = offUseDepart;
	}

	public String getOffManager() {
		return offManager;
	}
	public void setOffManager(String offManager) {
		this.offManager = offManager;
	}
	public String getOffRemark() {
		return offRemark;
	}
	public void setOffRemark(String offRemark) {
		this.offRemark = offRemark;
	}
	public String getOffUseState() {
		return offUseState;
	}
	public void setOffUseState(String offUseState) {
		this.offUseState = offUseState;
	}
	public String getOffState() {
		return offState;
	}
	public void setOffState(String offState) {
		this.offState = offState;
	}
	public String getOffSeqNum() {
		return offSeqNum;
	}
	public void setOffSeqNum(String offSeqNum) {
		this.offSeqNum = offSeqNum;
	}
	public String getLoginUserName() {
		return loginUserName;
	}
	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}
	public String getOffUpdTime() {
		return offUpdTime;
	}
	public void setOffUpdTime(String offUpdTime) {
		this.offUpdTime = offUpdTime;
	}
	public int  getOffLifeTime() {
		return offLifeTime;
	}
	public void setOffLifeTime(int offLifeTime) {
		this.offLifeTime = offLifeTime;
	}

	
	
	
	

	

}
