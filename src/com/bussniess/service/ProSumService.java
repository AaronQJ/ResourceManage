package com.bussniess.service;

import java.io.Serializable;

import com.bussniess.dao.IProSumDao;
import com.bussniess.domain.ProSum;

public class ProSumService {
	private IProSumDao proSumDao;
//	DevSum devtext = new DevSum();
	
	

	public void add(ProSum protext) {
		proSumDao.addOrUpdate(protext);
	}

	public IProSumDao getProSumDao() {
		return proSumDao;
	}

	public void setProSumDao(IProSumDao proSumDao) {
		this.proSumDao = proSumDao;
	}

	public void addTestAopTransaction() {

		ProSum devtext = new ProSum();
		devtext.setId(1);
		devtext.setTableName("OffPro");;
		devtext.setSum(2);
		proSumDao.addOrUpdate(devtext);
	}

	public ProSum findById(Serializable id) {
		return proSumDao.findById(id);
	}

	
}
