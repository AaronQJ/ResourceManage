package com.bussniess.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bussniess.dao.impl.ProBakDaoImpl;

public class propertyDaotest {
	
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
	ProBakDaoImpl  propertyDao = (ProBakDaoImpl) applicationContext.getBean("propertyDao");
	
	@Test
	public void testFind(){
		System.out.println(propertyDao.findAll());
	}

}
