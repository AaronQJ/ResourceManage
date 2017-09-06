package com.bussniess.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import antlr.collections.List;

import com.bussniess.dao.impl.LinInfDaoImpl;
import com.bussniess.dao.impl.OffProDaoImpl;
import com.bussniess.dao.impl.ProBakDaoImpl;
import com.bussniess.dao.impl.SpeProDaoImpl;
import com.bussniess.dao.impl.logTextDaoImpl;
import com.bussniess.dao.util.Conditions;
import com.bussniess.dao.util.Conditions.Operator;
import com.bussniess.domain.LinInf;
import com.bussniess.domain.OffPro;
import com.bussniess.domain.ProBak;
import com.bussniess.domain.SpePro;
import com.bussniess.domain.logText;
import com.bussniess.service.LinInfService;
import com.google.gson.Gson;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class DevLifeTime extends ActionSupport{//生命周期方法
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
	logTextDaoImpl logDao = (logTextDaoImpl) applicationContext.getBean("logTextDao");
	public String  LinInfLifeTime() throws IOException{//获得线路的生命周期
		
		LinInfDaoImpl infoDao = (LinInfDaoImpl) applicationContext.getBean("infoDao");
		
		ArrayList<LinInf> lininf =  new ArrayList<LinInf>();
		ArrayList<logText> logtext =  new ArrayList<logText>();
		ArrayList<Object> list =  new ArrayList<Object>();
		HttpServletRequest request =  ServletActionContext.getRequest();
		String lineid = request.getParameter("lineId");
		Conditions conditions = new Conditions();
		conditions.addCondition("lineId", lineid, Operator.EQUAL);
		lininf = (ArrayList<LinInf>) infoDao.findByConditions(conditions);
		Conditions conditions2 = new Conditions();
		conditions2.addCondition("objId",lininf.get(0).getLineNum() , Operator.EQUAL);
		conditions2.addCondition("fieldName", "线路状态", Operator.EQUAL);
		logtext = (ArrayList<logText>) logDao.findByConditions(conditions2);
		
		list.addAll(lininf);
		list.addAll(logtext);
		
		Gson gson = new Gson();
        String result = gson.toJson(list);
        HttpServletResponse  response = ServletActionContext.getResponse();
        response.setContentType("text/json; charset=utf-8");//设置数据格式，防止乱码
        response.setHeader("Cache-Control", "no-cache"); //取消浏览器缓存
        PrintWriter out = response.getWriter();
        out.print(result); 
        out.flush();
        out.close(); 
        
      
		return null;
	}
	public String OffProLifeTime() throws IOException{//获得办公资产的生命周期
	
		OffProDaoImpl offDao = (OffProDaoImpl) applicationContext.getBean("offDao");
		ArrayList<OffPro> Offpro =  new ArrayList<OffPro>();
		ArrayList<logText> logtext =  new ArrayList<logText>();
		ArrayList<Object> list =  new ArrayList<Object>();
		HttpServletRequest request =  ServletActionContext.getRequest();
		String offid = request.getParameter("offId");
		Conditions conditions = new Conditions();
		
		conditions.addCondition("offId", offid, Operator.EQUAL);
		Offpro = (ArrayList<OffPro>) offDao.findByConditions(conditions);
		
		Conditions conditions2 = new Conditions();
		conditions2.addCondition("objId",Offpro.get(0).getOffNum(), Operator.EQUAL);
		conditions2.addCondition("fieldName", "状态", Operator.EQUAL);
		logtext = (ArrayList<logText>) logDao.findByConditions(conditions2);
		list.addAll(Offpro);
		list.addAll(logtext);
		Gson gson = new Gson();
        String result = gson.toJson(list);
        HttpServletResponse  response = ServletActionContext.getResponse();
        response.setContentType("text/json; charset=utf-8");//设置数据格式，防止乱码
        response.setHeader("Cache-Control", "no-cache"); //取消浏览器缓存
        PrintWriter out = response.getWriter();
        out.print(result);
        out.flush();
        out.close(); 
		return null;
	}
	public String ProBakLifeTime() throws IOException{//获得备品备件的生命周期
		
		ProBakDaoImpl bakDao = (ProBakDaoImpl) applicationContext.getBean("bakDao");
		ArrayList<ProBak> Probak =  new ArrayList<ProBak>();
		ArrayList<logText> logtext =  new ArrayList<logText>();
		ArrayList<Object> list =  new ArrayList<Object>();
		HttpServletRequest request =  ServletActionContext.getRequest();
		String bakId = request.getParameter("bakId");
		Conditions conditions = new Conditions();
		
		conditions.addCondition("bakId", bakId, Operator.EQUAL);
		Probak = (ArrayList<ProBak>) bakDao.findByConditions(conditions);

		Conditions conditions2 = new Conditions();
		conditions2.addCondition("objId",Probak.get(0).getBakSeqNum(), Operator.EQUAL);
		conditions2.addCondition("fieldName", "状态", Operator.EQUAL);
		logtext = (ArrayList<logText>) logDao.findByConditions(conditions2);
		list.addAll(Probak);
		list.addAll(logtext);
		Gson gson = new Gson();
        String result = gson.toJson(list);
        HttpServletResponse  response = ServletActionContext.getResponse();
        response.setContentType("text/json; charset=utf-8");//设置数据格式，防止乱码
        response.setHeader("Cache-Control", "no-cache"); //取消浏览器缓存
        PrintWriter out = response.getWriter();
        out.print(result);
        out.flush();
        out.close(); 
		return null;
	}
	public String SpeProLifeTime() throws IOException{//获得专用资产的生命周期
		
		SpeProDaoImpl speDao = (SpeProDaoImpl) applicationContext.getBean("speDao");
		ArrayList<SpePro> Spepro =  new ArrayList<SpePro>();
		ArrayList<logText> logtext =  new ArrayList<logText>();
		ArrayList<Object> list =  new ArrayList<Object>();
		HttpServletRequest request =  ServletActionContext.getRequest();
		String speid = request.getParameter("speId");
		
		Conditions conditions = new Conditions();
		conditions.addCondition("speId", speid, Operator.EQUAL);
		Spepro = (ArrayList<SpePro>) speDao.findByConditions(conditions);
		Conditions conditions2 = new Conditions();
		conditions2.addCondition("objId",Spepro.get(0).getSpeSeqNum(), Operator.EQUAL);
		conditions2.addCondition("fieldName", "状态", Operator.EQUAL);
		logtext = (ArrayList<logText>) logDao.findByConditions(conditions2);
		list.addAll(Spepro);
		list.addAll(logtext);
		Gson gson = new Gson();
        String result = gson.toJson(list);
        HttpServletResponse  response = ServletActionContext.getResponse();
        response.setContentType("text/json; charset=utf-8");//设置数据格式，防止乱码
        response.setHeader("Cache-Control", "no-cache"); //取消浏览器缓存
        PrintWriter out = response.getWriter();
        out.print(result);
        out.flush();
        out.close(); 
		return null;
	}
	
}
