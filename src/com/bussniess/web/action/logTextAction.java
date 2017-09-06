package com.bussniess.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.struts2.ServletActionContext;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bussniess.dao.impl.ElecTextDaoImpl;
import com.bussniess.dao.impl.logTextDaoImpl;
import com.bussniess.dao.util.Conditions;
import com.bussniess.dao.util.Conditions.Operator;
import com.bussniess.domain.ElecText;
import com.bussniess.service.ElecTextService;
import com.bussniess.service.logTextService;
import com.bussniess.util.DataTablesPage;
import com.bussniess.domain.logText;
import com.google.gson.Gson;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class logTextAction extends ActionSupport  {//对日志表操作的事件
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	//private static Logger logger = Logger.getLogger(logTextAction.class);
	
	
	
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
	logTextDaoImpl logDao = (logTextDaoImpl) applicationContext.getBean("logTextDao");
	 List<logText> log =  new ArrayList<>();//实例化一个logText数据对象
	public String logDelete() throws ParseException, IOException{//根据时间删除日志的方法
		HttpServletRequest request =  ServletActionContext.getRequest();
		
		String operTime = request.getParameter("date");
		Conditions conditions = new Conditions();
		Gson gson = new Gson();
		if(!operTime.equals("")){
		conditions.addCondition("operTime", operTime, Operator.LESS_EQUAL);
		log =  logDao.findByConditions(conditions);
		
		logDao.deleteAll(log);
		}
		String deleSucc;
		if(log.equals(null)){
			deleSucc= "false";
		}
		else{
			deleSucc=  "true";
		}
		
		String succes =  gson.toJson(deleSucc);
		ServletActionContext.getResponse().getWriter().print(succes);
		
		return null;
		
	}
	@Test
	public String logSelect() throws IOException, ParseException{//实现日志条件查询的方法
		HttpServletRequest request =  ServletActionContext.getRequest();
		Conditions conditions = new Conditions();
		int countPerPage = Integer.valueOf(request.getParameter("countPerPage"));//每页显示的条数
		int pagenum = Integer.valueOf(request.getParameter("page"));//当前第几页
		String startTime = request.getParameter("startTime");//起始时间
		String endTime = request.getParameter("endTime");//结束时间
		String objName = request.getParameter("objName");//对象名称
		String userName = request.getParameter("userName");//操作人
		String logType = request.getParameter("logType");//日志的类型
	
		if(!(startTime.equals(""))){
		conditions.addCondition("operTime",startTime, Operator.GREATER_EQUAL);
		}
		if(!(endTime.equals("") )){
		conditions.addCondition("operTime",endTime, Operator.LESS_EQUAL);
		} 
		
		if(!(objName.equals("") )){
		conditions.addCondition("objName", objName, Operator.LIKE);
		}
		
		if(!(logType.equals("") )){
		conditions.addCondition("logType",logType, Operator.LIKE);
		}
		if(!(userName.equals("") )){
		conditions.addCondition("userName", userName, Operator.LIKE);
		}
		
		DataTablesPage<logText>   pageData = logDao. findAll(conditions, countPerPage,pagenum) ;
		
		
		
		
		Gson gson = new Gson();//通过json格式传递到前台
        String result = gson.toJson(pageData);
        HttpServletResponse  response = ServletActionContext.getResponse();
        response.setContentType("text/json; charset=utf-8");//设置数据格式，防止乱码
        response.setHeader("Cache-Control", "no-cache"); //取消浏览器缓存
        PrintWriter out = response.getWriter();
        out.print(result);
        out.flush();
        out.close(); 
		
		System.out.println(countPerPage);
		System.out.println(pagenum);
		
		return null;
	}
	public String logSelectAll() throws IOException, ParseException{//进页面时，将全部信息找到
		HttpServletRequest request =  ServletActionContext.getRequest();
		
		int countPerPage = Integer.valueOf(request.getParameter("countPerPage"));//每页多少条
		int pagenum = Integer.valueOf(request.getParameter("page"));//当前第几页
		
		Conditions conditions = new Conditions();
		DataTablesPage<logText>   pageData = logDao. findAll(conditions, countPerPage,pagenum) ;

		Gson gson = new Gson();//以json格式传递到前台
        String result = gson.toJson(pageData);
        HttpServletResponse  response = ServletActionContext.getResponse();
        response.setContentType("text/json; charset=utf-8");//设置数据格式，防止乱码
        response.setHeader("Cache-Control", "no-cache"); //取消浏览器缓存
        PrintWriter out = response.getWriter();
        out.print(result);
        out.flush();
        out.close(); 
		
		System.out.println(countPerPage);
		System.out.println(pagenum);
		
		
		return null;
	}
	

	

	
	
}
