package com.bussniess.service;

import java.io.Serializable;
import java.util.Date;
import com.bussniess.dao.IElecTextDao;
import com.bussniess.dao.util.Conditions;
import com.bussniess.domain.ElecText;
import com.bussniess.util.DataTablesPage;


public class ElecTextService {

	private IElecTextDao textDao;

	public void add(ElecText text) {
		textDao.addOrUpdate(text);
	}

	public void addTestAopTransaction() {

		ElecText text = new ElecText();
		text.setTextName("ddddddd");
		text.setTextDate(new Date());
		text.setTextComment("Hibernate测试ddddddd");

		ElecText text2 = new ElecText();
		text2.setTextName("d2222222");
		text2.setTextDate(new Date());
		text2.setTextComment("Hibernate测试d22222222");

		textDao.addOrUpdate(text);
		// int i = 1 / 0;
		textDao.addOrUpdate(text2);

	}

	public ElecText findById(Serializable id) {
		
		return textDao.findById(id);
	}

	public IElecTextDao getTextDao() {
		return textDao;
	}

	public void setTextDao(IElecTextDao textDao) {
		this.textDao = textDao;
	}

	public void page( DataTablesPage page,Conditions conditions) {
		textDao.page(page, conditions);
		
	}

		
	
}
