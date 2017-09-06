package com.bussniess.test;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bussniess.dao.impl.ElecTextDaoImpl;
import com.bussniess.dao.util.Conditions;
import com.bussniess.dao.util.Conditions.Operator;
import com.bussniess.domain.ElecText;
import com.bussniess.util.DataTablesPage;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



public class DaoTest {

	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
	ElecTextDaoImpl textDao = (ElecTextDaoImpl) applicationContext.getBean("textDao");

	@Test
	public void testAdd() {

		ElecText text = new ElecText();
		text.setTextName("testAdd");

		textDao.addOrUpdate(text);

	}

	@Test
	public void testAddAll() {
		List<ElecText> list = new ArrayList<ElecText>();

		ElecText text1 = new ElecText();
		text1.setTextName("testAddAll5");

		ElecText text2 = new ElecText();
		text2.setTextName("testAddAll6");

		list.add(text1);
		list.add(text2);

		textDao.addOrUpdateAll(list);

	}

	@Test
	public void testDelete() {
		List<ElecText> list = new ArrayList<ElecText>();

		list.add(textDao.findById("4028d081563b795201563b795fa60001"));
		list.add(textDao.findById("4028d081563e959001563e995d990001"));

		textDao.deleteAll(list);
	}

	@Test
	public void testDeleteAll() {

	}
/*
	@Test
	public void testDeleteById() {
		textDao.deleteById("402881014bc8a8a1014bc8a8a4340001");
	}
*/
	@Test
	public void testDeleteByIdsl() {
		textDao.deleteAllByIds("402881014bc8a8a1014bc8a8a4340001", "402881014bc8a8a1014bc8a8a43c0002");
	}

	@Test
	public void testFindAll() {
		System.out.println(textDao.findAll());

	}

	@Test
	public void testFindByConditions() {
		Conditions conditions = new Conditions();
		// textName like'%test%'
		conditions.addCondition("textName", "test", Operator.LIKE);
		conditions.addOrderBy("textName", true);
		conditions.addOrderBy("textId", false);
		// conditions.addCondition("textComment", "test", Operator.EQUAL);

		// WhereAndValues wv = conditions.createWhereAndValues();
		//
		// System.out.println(wv.getWhere());
		// Object[] values = wv.getValues();
		// for (Object object : values) {
		// System.out.println(object);
		// }
		List<ElecText> textList = textDao.findByConditions(conditions);
		
		
		
		textDao.deleteAll(textList);
		System.out.println(textList);

	}
/*
	@Test
	public void testUpdate() {
		ElecText text1 = new ElecText();
		text1.setTextId("402880eb4bc87903014bc87906ac0001");
		text1.setTextName("testUpdate");

		textDao.addOrUpdate(text1);
	}
*/
	@Test
	public void testUpdateAll() {
		List<ElecText> list = new ArrayList<ElecText>();

		ElecText text1 = new ElecText();
		text1.setTextId("402880eb4bc87903014bc87906ac0001");
		text1.setTextName("testUpdateAll1");

		ElecText text2 = new ElecText();
		text2.setTextId("402880eb4bc87903014bc87906bc0002");
		text2.setTextName("testUpdateAll2");

		list.add(text1);
		list.add(text2);

		textDao.addOrUpdateAll(list);
	}
	@Test
	public void testPage(){
		DataTablesPage<ElecText>  page = new DataTablesPage<ElecText> ();
		page.setIDisplayStart(0);
		page.setIDisplayLength(10);
		Conditions  conditions = new Conditions();
		conditions.addCondition("textName", "testAddAll1", Operator.EQUAL);
		textDao.page(page, conditions);
		Gson gson  = new Gson();
		String str = gson.toJson(page);
		System.out.println(str);
	}
	
	@Test
	public void testdeleteMany(){
		
		
		
		textDao.deleteAllByIds("4028d081563b795201563b795fa60001","4028d08156899d900156899dbb860008");
		
	}
	
	@Test
	public void testUpdateMany(){
		List<ElecText> list = textDao.findAllByIds("5e98981b568bd3b101568bd3c6260001","5e98981b568bd3b101568bd3c6550002","4028d08156899d900156899db5550005");
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setTextComment("xixixix");
			
		}
		
		textDao.addOrUpdateAll(list);
	}
	
	
	@Test
	public void testSession(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		HttpSession session     =request.getSession();
		
		session.setAttribute("username", "dandan");
		
		
		
	 String username =	(String) session.getAttribute("username");
		System.out.println(username);
	}
	
	
	@Test
	public void testJson(){
		String jsonArr  =  "{'data':['1','2']}";
		
		 Gson gson = new Gson();  
        JsonParser parser = new JsonParser();  
        JsonObject jsonObject = parser.parse(jsonArr).getAsJsonObject();  
        //将data节点下的内容转为JsonArray  
        JsonArray jsonArray = jsonObject.getAsJsonArray("data");  
        for (int i = 0; i < jsonArray.size(); i++) {  
            //获取第i个数组元素  
            JsonElement el = jsonArray.get(i);  
            System.out.println(el.getAsInt());
           
           
        }  
	}
}
