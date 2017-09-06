package com.bussniess.service;



import com.bussniess.dao.IUsersDao;
import com.bussniess.domain.Users;

public class UsersService {

	private IUsersDao userDao;

	public void setUserDao(IUsersDao userDao) {
		this.userDao = userDao;
	}	
	
	public void add(Users user) {
		userDao.addOrUpdate(user);
	}

	public void addUsersAopTransaction() {

		Users user = new Users();
		user.setUserName("龙");
		user.setPwd("123");
		user.setTel("11111");


		userDao.addOrUpdate(user);
		// int i = 1 / 0;
		}
}
