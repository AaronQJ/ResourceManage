package com.bussniess.web.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.bussniess.dao.util.Conditions;
import com.bussniess.dao.util.Conditions.Operator;
import com.bussniess.domain.ElecText;
import com.bussniess.service.ElecTextService;
import com.bussniess.util.DataTablesPage;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;



public class ElecTextAction extends ActionSupport implements ModelDriven<ElecText> {


	private static final long serialVersionUID = 1L;
	private ElecText text = new ElecText();
	private ElecTextService textService;
	

	
	

	public String add() {
		textService.add(text);
		return "add";
	}

	public ElecText getModel() {
		return text;
	}

	public ElecText getText() {
		return text;
	}

	public ElecTextService getTextService() {
		return textService;
	}

	public void setText(ElecText text) {
		this.text = text;
	}

	public void setTextService(ElecTextService textService) {
		this.textService = textService;
	}

	
	public String  send() throws IOException{
		
		
		Conditions conditions  = new Conditions();
		conditions.addCondition("textName", "test", Operator.LIKE);
		DataTablesPage<ElecText>   page = new DataTablesPage<ElecText>() ;

		page.setIDisplayStart(0);
		page.setIDisplayLength(10);
		
		textService.page(page,conditions);
		Gson gson = new Gson();
		String data = gson.toJson(page);
		HttpServletResponse response = ServletActionContext.getResponse();
//		response.setContentType("application/html");
		PrintWriter out =response.getWriter();
		out.print(data);
		
		out.flush();
		out.close();
		
		
		return null;
	}
	
}
