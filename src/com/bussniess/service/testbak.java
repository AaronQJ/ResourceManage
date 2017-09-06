package com.bussniess.service;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class testbak {
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
	ProBakService bakService = (ProBakService) applicationContext.getBean("bakService");
	
	@Test
	public void testFind(){
		System.out.println(bakService.getBakDao().findAll());
	}

	

}
