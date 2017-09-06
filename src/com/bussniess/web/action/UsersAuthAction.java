package com.bussniess.web.action;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bussniess.dao.impl.RolesDaoImpl;
import com.bussniess.dao.impl.UsersDaoImpl;
import com.bussniess.domain.Roles;
import com.bussniess.domain.Users;
import com.bussniess.service.PrivilegesService;
import com.bussniess.service.RolesService;
import com.bussniess.service.UsersService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class UsersAuthAction extends ActionSupport{

	private UsersService userService;
	private RolesService roleService;
	private PrivilegesService privilegeService;
	/*Roles role = new Roles();
	
	ApplicationContext applicationContext1 = new ClassPathXmlApplicationContext("beans.xml");
	RolesDaoImpl roleDao = (RolesDaoImpl) applicationContext1.getBean("roleDao");
	
	public Roles getModel() {
		
		return role;
	}

	
	@Test
	public void test(){
		System.out.println(roleDao.findAll());
	}*/
	public UsersService getUserService() {
		return userService;
	}
	public void setUserService(UsersService userService) {
		this.userService = userService;
	}
	public RolesService getRoleService() {
		return roleService;
	}
	public void setRoleService(RolesService roleService) {
		this.roleService = roleService;
	}
	public PrivilegesService getPrivilegeService() {
		return privilegeService;
	}
	public void setPrivilegeService(PrivilegesService privilegeService) {
		this.privilegeService = privilegeService;
	}
}
