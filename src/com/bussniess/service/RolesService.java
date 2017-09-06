package com.bussniess.service;

import java.util.List;

import com.bussniess.dao.IRolesDao;
import com.bussniess.domain.Roles;


public class RolesService {
	private IRolesDao roleDao;

	public IRolesDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(IRolesDao roleDao) {
		this.roleDao = roleDao;
	}
	
	public List<Roles> list(){
		return roleDao.findAll();
	}
	
	public Roles findRoleById(Integer roleId){
		return roleDao.findById(roleId);
	}
	
	public void updateRoles(Integer roleId){
		Roles role = roleDao.findById(roleId);
		roleDao.addOrUpdate(role);
	}
	
	
	public void add(Roles role) {
		roleDao.addOrUpdate(role);
	}
	
}
