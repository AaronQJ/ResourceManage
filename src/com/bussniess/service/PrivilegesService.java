package com.bussniess.service;

import java.util.List;

import com.bussniess.dao.IPrivilegesDao;
import com.bussniess.domain.Privileges;

public class PrivilegesService {
	private IPrivilegesDao privilegeDao;

	public IPrivilegesDao getPrivilegeDao() {
		return privilegeDao;
	}

	public void setPrivilegeDao(IPrivilegesDao privilegeDao) {
		this.privilegeDao = privilegeDao;
	}
	
	public List<Privileges> list(){
		return privilegeDao.findAll();
	}

	
}
