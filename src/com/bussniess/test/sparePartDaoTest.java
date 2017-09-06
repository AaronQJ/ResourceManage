package com.bussniess.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bussniess.dao.impl.ProBakDaoImpl;
import com.bussniess.dao.impl.SpeProDaoImpl;
import com.bussniess.dao.util.Conditions;
import com.bussniess.dao.util.Conditions.Operator;
import com.bussniess.domain.ElecText;
import com.bussniess.domain.SpePro;
import com.bussniess.util.DataTablesPage;
import com.google.gson.Gson;

public class sparePartDaoTest {
	

	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
	SpeProDaoImpl  speDao = (SpeProDaoImpl) applicationContext.getBean("speDao");
	
	@Test
	public void testFind(){
		DataTablesPage<SpePro>  page = new DataTablesPage<SpePro> ();
		page.setIDisplayStart(0);
		page.setIDisplayLength(10);
		Conditions  conditions = new Conditions();
		conditions.addCondition("speId", "1", Operator.EQUAL);
		speDao.page(page, conditions);
		Gson gson  = new Gson();
		String str = gson.toJson(page);
		System.out.println(str);
	}


}
