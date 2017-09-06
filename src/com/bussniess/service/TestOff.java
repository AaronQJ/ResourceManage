package com.bussniess.service;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestOff {
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
	OffProService offService = (OffProService) applicationContext.getBean("offService");
	
	@Test
	public void testFind(){
		System.out.println(offService.getOffDao().findAll());
	}

}
