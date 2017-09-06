package com.bussniess.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.bussniess.dao.util.Conditions;
import com.bussniess.dao.util.Conditions.Operator;
import com.bussniess.domain.ElecText;
import com.bussniess.domain.ProBak;
import com.bussniess.domain.SpePro;
import com.bussniess.service.ElecTextService;
import com.bussniess.service.OffProService;
import com.bussniess.service.ProBakService;
import com.bussniess.service.SpeProService;
import com.bussniess.util.DataTablesPage;
import com.google.gson.Gson;



public class SpringTest {

	public static void main(String[] args) {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
		ElecText text = (ElecText) applicationContext.getBean("elecText");
		System.out.println(text.getTextName());
	}

	@Test
	public void testAopTransaction() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
		ElecTextService textService = (ElecTextService) applicationContext.getBean("textService");

		textService.addTestAopTransaction();
	}

	@Test
	public void testDao() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
		ElecTextService textService = (ElecTextService) applicationContext.getBean("textService");

		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd ");
		String st = sd.format(new Date());
		
		Date date = sd.parse(st);
		ElecText text = new ElecText();
		 text.setTextName("name333");
		 text.setTextDate(date);
		 text.setTextComment("Hibernate测试333");

		textService.add(text);
//		ElecText text1 = textService.findById("4028d081563b705b01563b7067750001");
//		System.out.println(text.getTextName());

	}

	@Test
	public void testSpringHibernate() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
		HibernateTemplate hibernateTemplate = (HibernateTemplate) applicationContext.getBean("hibernateTemplate");

		ElecText text = new ElecText();
		text.setTextName("name3455666");
		text.setTextDate(new Date());
		text.setTextComment("Hibernate测试2222");

		hibernateTemplate.save(text);

	}

	@Test
	public void testPage() throws IOException{
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
		ElecTextService textService = (ElecTextService) applicationContext.getBean("textService");
		SpeProService  speService = (SpeProService) applicationContext.getBean("speService");
		Conditions conditions  = new Conditions();
		conditions.addCondition("textName", "name333", Operator.LIKE);
		DataTablesPage<ElecText>   page = new DataTablesPage<ElecText>() ;

		page.setIDisplayStart(0);
		page.setIDisplayLength(10);
		
		textService.page(page,conditions);
		Gson gson = new Gson();
		String data = gson.toJson(page);
	
		System.out.println(data);
		
		
		
		
	}
	
	
	@Test
	public void  sendChart() throws IOException{
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
		SpeProService  speService = (SpeProService) applicationContext.getBean("speService");
		Gson gson = new Gson();
		List<Map<String,Integer>> typeNumList = new ArrayList<Map<String,Integer>>(); 
		Map<String,Integer>  typeNum = new HashMap<String,Integer>();
		Map<String,Integer> devState = new HashMap<String,Integer>();
		Map<String,Integer>  devRoom= new HashMap<String,Integer>();
		
		
		List<SpePro> speList = speService.findAll();
		devState.put("总数", speList.size());
		List<String> roomList = new ArrayList<String>();
		for (SpePro spe : speList) {
			roomList.add(spe.getSpeDevRoom());
		}
		HashSet<String> roomSet  =   new  HashSet<String>(roomList); 
		roomList.clear(); 
		roomList.addAll(roomSet); 
		
		//机房
		for(int i=0;i<roomList.size();i++){
	    	Conditions conditions  = new Conditions();
	    	conditions.addCondition("speDevRoom",roomList.get(i) , Operator.EQUAL);
		
	    	int count = speService.findByCondition(conditions);
	    	devRoom.put(roomList.get(i) , count);

	    }
		
		//路由器
		Conditions conditions  = new Conditions();
		conditions.addCondition("speDevType", "路由器", Operator.EQUAL);
		
		int LYNum = speService.findByCondition(conditions);
		typeNum.put("路由器", LYNum);
		//交换机
		Conditions conditions1  = new Conditions();
		conditions1.addCondition("speDevType", "交换机", Operator.EQUAL);
		int JHNum = speService.findByCondition(conditions1);
		typeNum.put("交换机", JHNum);
		//服务器
		Conditions conditions2 = new Conditions();
		conditions2.addCondition("speDevType", "服务器", Operator.EQUAL);
		int FWNum = speService.findByCondition(conditions2);
		typeNum.put("服务器", FWNum);
		//其他
		int OTNum = speService.findAll().size()-LYNum-JHNum-FWNum;
		typeNum.put("其他", OTNum);
		
		
		//在维
		Conditions conditions3 = new Conditions();
		conditions3.addCondition("speState", "在维", Operator.EQUAL);
		int ZWNum = speService.findByCondition(conditions3);
		devState.put("在维", ZWNum);
		//不在维
		Conditions conditions4 = new Conditions();
		conditions4.addCondition("speState", "不在维", Operator.EQUAL);
		int BZWNum = speService.findByCondition(conditions4);
		devState.put("不在维", BZWNum);
		//下线
		Conditions conditions5 = new Conditions();
		conditions5.addCondition("speState", "下线", Operator.EQUAL);
		int XXNum = speService.findByCondition(conditions5);
		devState.put("下线", XXNum);
		
		typeNumList.add(typeNum);
		typeNumList.add(devState);
		typeNumList.add(devRoom);
		String typeCount = gson.toJson(typeNumList);
		
		
	
		
		
		System.out.println(typeCount);
		
	
	
	}
	
	@Test
	public void  sendChart1() throws IOException{
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
		OffProService  offService = (OffProService) applicationContext.getBean("offService");
		Gson gson = new Gson();
		List<Map<String,Integer>> typeNumList = new ArrayList<Map<String,Integer>>(); 
		Map<String,Integer>  typeNum = new HashMap<String,Integer>();
		Map<String,Integer> typeState = new HashMap<String,Integer>();
		
		
		//打印机
		Conditions conditions  = new Conditions();
		conditions.addCondition("offDevVersion", "打印机", Operator.EQUAL);
		
		int LYNum = offService.findByCondition(conditions);
		typeNum.put("打印机", LYNum);
		//电脑
		Conditions conditions1  = new Conditions();
		conditions1.addCondition("offDevVersion", "电脑", Operator.EQUAL);
		int JHNum = offService.findByCondition(conditions1);
		typeNum.put("电脑", JHNum);
	
		//其他
		int OTNum = offService.findAll().size()-LYNum-JHNum;
		typeNum.put("其他", OTNum);
		
		
		//待领用
		Conditions conditions3 = new Conditions();
		conditions3.addCondition("offState", "待领用", Operator.EQUAL);
		int DLYNum = offService.findByCondition(conditions3);
		typeState.put("待领用", DLYNum);
		//在用
		Conditions conditions4 = new Conditions();
		conditions4.addCondition("offState", "在用", Operator.EQUAL);
		int ZYNum = offService.findByCondition(conditions4);
		typeState.put("在用", ZYNum);
		//待报废
		Conditions conditions5 = new Conditions();
		conditions5.addCondition("offState", "待报废", Operator.EQUAL);
		int DBFNum = offService.findByCondition(conditions5);
		typeState.put("待报废", DBFNum);
		
		//报废
		Conditions conditions6 = new Conditions();
		conditions6.addCondition("offState", "报废", Operator.EQUAL);
		int BFNum = offService.findByCondition(conditions6);
		typeState.put("报废", BFNum);
		
		typeNumList.add(typeNum);
		typeNumList.add(typeState);
		
		String typeCount = gson.toJson(typeNumList);
		
		
		System.out.println(typeCount);
		
	
	
	}
	
	@Test
	public void  sendChart2() throws IOException{
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
		ProBakService  bakService = (ProBakService) applicationContext.getBean("bakService");
		Gson gson = new Gson();
		List<Map<String,Integer>> typeNumList = new ArrayList<Map<String,Integer>>(); 
		Map<String,Integer>  typeNum = new HashMap<String,Integer>();
		Map<String,Integer> typeState = new HashMap<String,Integer>();
		
		
		List<ProBak>  bakList = bakService.findAll();
		List<String>  typeList = new ArrayList<String>();
		for (ProBak bak : bakList) {
			typeList.add(bak.getBakDevType());
		}
		
		HashSet typeSet  =   new  HashSet(typeList); 
		typeList.clear(); 
		typeList.addAll(typeSet); 
		
		
		
		
	    System.out.println(typeList); 
		
		
		
	
		/*
		 * 设备类型
		 */
		
	    for(int i=0;i<typeList.size();i++){
	    	
	    
		
		Conditions conditions  = new Conditions();
		conditions.addCondition("bakDevType",typeList.get(i) , Operator.EQUAL);
		
		int count = bakService.findByCondition(conditions);
		typeNum.put(typeList.get(i) , count);
	
	
	    }
		
		
		
		
		
		
		/*
		 * 设备状态:
		 */
		//入库
		Conditions conditions3 = new Conditions();
		conditions3.addCondition("bakState", "入库", Operator.EQUAL);
		int DLYNum = bakService.findByCondition(conditions3);
		typeState.put("入库", DLYNum);
		//出库
		Conditions conditions4 = new Conditions();
		conditions4.addCondition("bakState", "出库", Operator.EQUAL);
		int ZYNum = bakService.findByCondition(conditions4);
		typeState.put("出库", ZYNum);
		//待报废
		Conditions conditions5 = new Conditions();
		conditions5.addCondition("bakState", "待报废", Operator.EQUAL);
		int DBFNum = bakService.findByCondition(conditions5);
		typeState.put("待报废", DBFNum);
		
		//报废
		Conditions conditions6 = new Conditions();
		conditions6.addCondition("bakState", "报废", Operator.EQUAL);
		int BFNum = bakService.findByCondition(conditions6);
		typeState.put("报废", BFNum);
		
		typeNumList.add(typeNum);
		typeNumList.add(typeState);
		
		String typeCount = gson.toJson(typeNumList);
		
		
		System.out.println(typeCount);
		
	
	
	}
	
}
