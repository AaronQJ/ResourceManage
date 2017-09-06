package com.bussniess.service;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpe {

	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
	SpeProService speService = (SpeProService) applicationContext.getBean("speService");
	
	@Test
	public void testFind(){
		System.out.println(speService.getSpeDao().findAll());
	}
}
