package com.bussniess.service;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class TestInfo {
	
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
	LinInfService infoService = (LinInfService) applicationContext.getBean("infoService");
	
	
	@Test
	public void testFind(){
		System.out.println(infoService.getInfoDao().findAll());
	}

}
