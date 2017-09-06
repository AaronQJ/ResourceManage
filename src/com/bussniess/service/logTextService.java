package com.bussniess.service;

import com.bussniess.dao.impl.logTextDaoImpl;

public class logTextService {
	private logTextDaoImpl logTextDao;
	public logTextDaoImpl getLogTextDao() {
		return logTextDao;
	}
	public void setLogTextDao(logTextDaoImpl logDao) {
		this.logTextDao = logDao;
	}
	
}
