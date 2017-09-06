package com.bussniess.domain;

import java.util.Date;

public class ElecText {

	/*
	 * 
	create table elecText(
	textId varchar(100) primary key,
	textName varchar(100),
	textDate date,
	textComment text
	);

	 * 
	 */

	private String textId;
	private String textName;
	private Date textDate;
	private String textComment;

	public String getTextComment() {
		return textComment;
	}

	public Date getTextDate() {
		return textDate;
	}

	public String getTextId() {
		return textId;
	}

	public String getTextName() {
		return textName;
	}

	public void setTextComment(String textComment) {
		this.textComment = textComment;
	}

	public void setTextDate(Date textDate) {
		this.textDate = textDate;
	}

	public void setTextId(String textId) {
		this.textId = textId;
	}

	public void setTextName(String textName) {
		this.textName = textName;
	}

}
