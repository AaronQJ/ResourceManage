package com.bussniess.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;







import com.bussniess.dao.impl.LinInfDaoImpl;
import com.bussniess.dao.util.Conditions;
import com.bussniess.dao.util.Conditions.Operator;
import com.bussniess.domain.LinInf;

public class InfoDaoTest {
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
	LinInfDaoImpl infoDao = (LinInfDaoImpl) applicationContext.getBean("infoDao");
	
	
	@Test
	public void findByConditions(){
		Conditions conditions = new Conditions();
		conditions.addCondition("lineNum", "100M", Operator.LIKE);
		conditions.addCondition("lineState", "再用", Operator.EQUAL);
		conditions.addCondition("lineSystem", "p2p监控", Operator.LIKE);
		List<LinInf> line = infoDao.findByConditions(conditions);
		
		System.out.println(line);
		
	
		
	}
	
	
	@Test
	public void findAll(){
		List<LinInf> hah = infoDao.findAll();
		System.out.println(hah);
	}
	
	@Test
	public void testnull() {
		String s="";
		String s1 = null;
		
		if(!s.endsWith("")){
			System.out.println(s+"不为空");
		}else{
			System.out.println(s+"为空？");
		}
		
		if(s.length() != 0){
			System.out.println(s+"不为空");
		}else{
			System.out.println(s+"为空？");
		}
		
		if(s == null) {
			System.out.println(s+"为null");
		}
		
		if(s1 == null) {
			System.out.println(s1+"为null");
		}
	
		if(s1.equals("")) {
			System.out.println(s1+"为空");
		}
	}
	
}
