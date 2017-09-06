package com.bussniess.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;





import com.bussniess.dao.impl.OffProDaoImpl;
import com.bussniess.dao.util.Conditions;
import com.bussniess.dao.util.Conditions.Operator;
import com.bussniess.domain.OffPro;

public class offDaotest {
	
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
	OffProDaoImpl offDao = (OffProDaoImpl) applicationContext.getBean("offDao");
	
	@Test
	public void TestFind(){
		Conditions conditions = new Conditions();
		conditions.addCondition("offNum", "012", Operator.EQUAL);
		
		List<OffPro> line = offDao.findByConditions(conditions);
		
		System.out.println(line);
	}

	
}