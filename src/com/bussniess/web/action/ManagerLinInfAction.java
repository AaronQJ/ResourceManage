package com.bussniess.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts2.ServletActionContext;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;





import com.bussniess.dao.impl.LinInfDaoImpl;
import com.bussniess.dao.util.Conditions;
import com.bussniess.dao.util.Conditions.Operator;
import com.bussniess.domain.LinInf;
import com.bussniess.domain.Users;
import com.bussniess.service.LinInfService;
import com.bussniess.util.DataTablesPage;
import com.bussniess.util.ExcelCellType;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class ManagerLinInfAction extends ActionSupport implements ModelDriven<LinInf> {
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
	HibernateTemplate hibernateTemplate = (HibernateTemplate) applicationContext.getBean("hibernateTemplate");
	LinInfDaoImpl infoDao = (LinInfDaoImpl) applicationContext.getBean("infoDao");
	
	Users user = (Users)ServletActionContext.getRequest().getSession().getAttribute("user");

	ExcelCellType<LinInf> ect =  new ExcelCellType<LinInf>();

	private File file;
	private String fileFileName;				//文件名
	private String fileContentType;				//文件的类型

	private POIFSFileSystem fsimport;
	private HSSFWorkbook wbimport;
	private HSSFSheet sheetimport;
	private HSSFRow rowimport,rowmark;

	private static Logger log = Logger.getLogger(ManagerLinInfAction.class);
	private static final long serialVersionUID = 1L;
	private LinInf lineInfo = new LinInf();
	private LinInfService  infoService;
	






	@Override
	public LinInf getModel() {

		return lineInfo;
	}
	public LinInf getLineInfo() {
		return lineInfo;
	}
	public void setLineInfo(LinInf lineInfo) {
		this.lineInfo = lineInfo;
	}




	public LinInfService getInfoService() {
		return infoService;
	}
	public void setInfoService(LinInfService infoService) {
		this.infoService = infoService;
	}
	public String  add() throws IOException{

		lineInfo.setLoginUserName(user.getUserName());
		Date date = new Date();                                                                                                                                                                                                                                                                           
		SimpleDateFormat  sd = new SimpleDateFormat("yyyy-MM-dd");
		lineInfo.setLineUpdTime(sd.format(date));
		infoService.add(lineInfo);
		MDC.put("logType", "devRecord");
		MDC.put("objType", "线路管理");
		MDC.put("operType", "增加");
		MDC.put("userName", user.getUserName());
		MDC.put("objId", lineInfo.getLineNum());
		log.error("");
		MDC.remove("userName");
		MDC.remove("objId");
		MDC.remove("logType");
		MDC.remove("objType");
		MDC.remove("operType");
		MDC.remove("fieldName");
		MDC.remove("fieldOriValue");
		MDC.remove("fieldUpdValue");
		HttpServletResponse response  = ServletActionContext.getResponse();
		PrintWriter out = response.getWriter();
		out.print("true");


		out.flush();
		out.close();



		return null;
	}



	public String  send() throws IOException{

		Conditions conditions  = new Conditions();
		System.out.println(conditions.createWhereAndValues()+"++++++++");
		//	DataTablesPage<LinInf>   page = new DataTablesPage<LinInf>() ;
		HttpServletRequest request= ServletActionContext.getRequest();
		String lineOperator = request.getParameter("lineOperator");
		String lineSystem = request.getParameter("lineSystem");
		String lineBandWidth = request.getParameter("lineBandWidth");
//		String lineBandWidth = "66";
		int  lineBandWidth1 = 100;
	
		
		String lineARoom = request.getParameter("lineARoom");
		//String lineARoom ="16";
		String lineZRoom = request.getParameter("lineZRoom");
		String lineCountryUser = request.getParameter("lineCountryUser");
		String lineLocalRemote = request.getParameter("lineLocalRemote");
		String  lineState = request.getParameter("lineState");
		System.out.println("筛选获取到的数据----"+"运营商:"+lineOperator+"--所属系统:"+lineSystem+"--带宽:"+lineBandWidth+"--A端:"+lineARoom +"--Z端:"+lineZRoom+"");

		conditions.addCondition("lineOperator", lineOperator, Operator.EQUAL);
		conditions.addCondition("lineSystem", lineSystem, Operator.EQUAL);
		conditions.addCondition("lineARoom", lineARoom, Operator.EQUAL);
		conditions.addCondition("lineZRoom", lineZRoom, Operator.EQUAL);
		conditions.addCondition("lineCountryUser", lineCountryUser, Operator.EQUAL);
		conditions.addCondition("lineLocalRemote", lineLocalRemote, Operator.EQUAL);
		conditions.addCondition("lineState", lineState, Operator.EQUAL);
		
		if("".equals(lineBandWidth)){
		
		}else{
			lineBandWidth1 = Integer.valueOf(lineBandWidth);
			
			conditions.addCondition("lineBandWidth", lineBandWidth1, Operator.EQUAL);
		}
		

		String iDisplayLength = request.getParameter("iDisplayLength");
		String nowPage = request.getParameter("nowPage");
		int iDisplayLength1 = Integer.valueOf(iDisplayLength);
		int nowPage1 = Integer.valueOf(nowPage);

	DataTablesPage<LinInf>  pagaData = infoService. findAll(conditions, iDisplayLength1+10000,nowPage1) ;
		
	
		System.out.println("数据："+pagaData);
		
		Gson gson = new Gson();
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("pagaData", pagaData);
		String data = gson.toJson(map);
		
		System.out.println("aaaaaaaaaaaaaaaaaaaa"+data+"bbbbbbbbbbbbbbbbbb");
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out =response.getWriter();
		out.print(data);
		out.flush();
		out.close();

		return null;
	}



	public String search() throws IOException{

		Conditions conditions  = new Conditions();
		//	DataTablesPage<LinInf>   page = new DataTablesPage<LinInf>() ;
		HttpServletRequest request= ServletActionContext.getRequest();


		String lineNum	 =  request.getParameter("lineNum");
		String  lineOperator  = request.getParameter("lineOperator");
		String  lineSystem = request.getParameter("lineSystem");
		conditions.addCondition("lineNum", lineNum, Operator.LIKE);
		conditions.addCondition("lineOperator", lineOperator, Operator.LIKE);
		conditions.addCondition("lineSystem", lineSystem, Operator.LIKE);



		String iDisplayLength = request.getParameter("iDisplayLength");
		int iDisplayLength1 = Integer.valueOf(iDisplayLength);
		System.out.println(iDisplayLength1+"---------");
		String nowPage = request.getParameter("nowPage");
		int nowPage1 = Integer.valueOf(nowPage);
		//		System.out.println("---------------"+nowPage1);


		//		System.out.println(page.getITotalRecords()+"--------------"+iDisplayLength1);
		DataTablesPage<LinInf>  pagaData = infoService. findAll(conditions, iDisplayLength1,nowPage1) ;

	




		//条件过滤
		List<LinInf> lineInfList = infoService.findByConditions(conditions);
		//名称
		List<String>  lineOperatorList = new ArrayList<String>();
		for(LinInf line: lineInfList){
			lineOperatorList.add(line.getLineOperator());
		}
		Set<String>  lineOperatorSet = new HashSet<String>(lineOperatorList);
		lineOperatorList.clear();
		lineOperatorList.addAll(lineOperatorSet);

		//系统
		List<String>  lineSystemList = new ArrayList<String>();
		for(LinInf line: lineInfList){
			lineSystemList.add(line.getLineSystem());
		}
		Set<String>  lineSystemSet = new HashSet<String>(lineSystemList);
		lineSystemList.clear();
		lineSystemList.addAll(lineSystemSet);


		List<Integer>  lineBandWidthList = new ArrayList<Integer>();
		for(LinInf line: lineInfList){
			lineBandWidthList.add(line.getLineBandWidth());
		}
		Set<Integer>  lineBandWidthSet = new HashSet<Integer>(lineBandWidthList);
		lineBandWidthList.clear();
		lineBandWidthList.addAll(lineBandWidthSet);


		List<String>  lineARoomList = new ArrayList<String>();
		for(LinInf line: lineInfList){
			lineARoomList.add(line.getLineARoom());
		}
		Set<String>  lineARoomSet = new HashSet<String>(lineARoomList);
		lineARoomList.clear();
		lineARoomList.addAll(lineARoomSet);


		List<String>  lineZRoomList = new ArrayList<String>();
		for(LinInf line: lineInfList){
			lineZRoomList.add(line.getLineZRoom());
		}
		Set<String>  lineZRoomSet = new HashSet<String>(lineZRoomList);
		lineZRoomList.clear();
		lineZRoomList.addAll(lineZRoomSet);


	
		/**
		 * lineOperator
		 * lineSystem
		 * lineBandWidth
		 * lineARoom
		 * lineZRoom
		 * lineCountryUser
		 * 
		 */
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("lineOperatorList",lineOperatorList );
		map.put("lineSystemList", lineSystemList);
		map.put("lineBandWidthList", lineBandWidthList);
		map.put("lineARoomList", lineARoomList);
		map.put("lineZRoomList", lineZRoomList);
	

		map.put("pagaData",pagaData);

		Gson gson = new Gson();
		String data = gson.toJson(map);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out =response.getWriter();
		System.out.println("专线资产的data:"+data);
		out.print(data);

		out.flush();
		out.close();

		return null;

	}








	public String deleteOne() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		String lineId = request.getParameter("lineId");
		Gson gson  = new Gson();
		infoService.deleteById(lineId);

		LinInf   info=infoService.findById(lineId);
		String deleSucc;
		if(info!=null){
			deleSucc=  gson.toJson("false");
		}
		deleSucc = gson.toJson("true");
		ServletActionContext.getResponse().getWriter().print(deleSucc);

		return null;
	}


	public String deleteMore() throws Exception{

		HttpServletRequest  request = ServletActionContext.getRequest();

		request.setCharacterEncoding("utf-8");
		String jsonArr  =  request.getParameter("data");
		JSONObject jsonObject=JSONObject.fromObject(jsonArr);//json-lib中是jo=JSONObject.fromObject(t);
		List<String> lineIds=(List<String>) jsonObject.getJSONArray("lineIds");

		List<LinInf> lineList  = new ArrayList<LinInf>();
		Gson gson = new Gson();
		for (int i = 0; i < lineIds.size(); i++) {  
			//获取第i个数组元素  
			String lineid= lineIds.get(i); 
			LinInf lineInfo1 =  infoService.findById(lineid);
			System.out.println(lineid);
			MDC.put("logType", "devRecord");
			MDC.put("objType", "线路管理");
			MDC.put("operType", "删除");
			MDC.put("objId", lineInfo1.getLineNum());
			MDC.put("userName", user.getUserName());
			log.error("");
			MDC.remove("userName");
			MDC.remove("objId");
			MDC.remove("logType");
			MDC.remove("objType");
			MDC.remove("operType");
			MDC.remove("fieldName");
			MDC.remove("fieldOriValue");
			MDC.remove("fieldUpdValue");
			infoService.deleteById(lineid);

			LinInf lineInfo =  infoService.findById(lineid);
			lineList.add(lineInfo);
		}  

		String deleSucc;
		if(lineList!=null){
			deleSucc=  gson.toJson("false");
		}
		deleSucc = gson.toJson("true");
		ServletActionContext.getResponse().getWriter().print(deleSucc);


		return null;
	}


	public String findOne() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();

		String lineId = request.getParameter("lineId");


		LinInf lineInfo = infoService.findById(lineId);

		String info = gson.toJson(lineInfo);

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(info);
		return null;

	}

	public String  updateOne() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();

		String lineId = request.getParameter("lineId");

		LinInf line =  infoService.findById(lineId);
		if(!lineInfo.getLineARoom().equals("")){
			if(!line.getLineARoom().equals(lineInfo.getLineARoom()))
			{
				MDC.put("objId", line.getLineNum());
				MDC.put("userName", user.getUserName());
				MDC.put("fieldUpdValue", lineInfo.getLineARoom());
				MDC.put("fieldOriValue", line.getLineARoom());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "线路管理");
				MDC.put("operType", "修改");
				MDC.put("fieldName", "A端所在机房");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue");
			}
		}
		if(!lineInfo.getLineARoomSite().equals("")){
			if(!line.getLineARoomSite().equals(lineInfo.getLineARoomSite()))
			{
				MDC.put("objId", line.getLineNum());
				MDC.put("userName", user.getUserName());
				MDC.put("fieldUpdValue", lineInfo.getLineARoomSite());
				MDC.put("fieldOriValue", line.getLineARoomSite());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "线路管理");
				MDC.put("operType", "修改");
				MDC.put("fieldName", "A端所在机房地址");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue");
			}
		}
		if(!lineInfo.getLineBandWidth().equals("")){
			if(!line.getLineBandWidth().equals(lineInfo.getLineBandWidth()))
			{
				MDC.put("objId", line.getLineNum());
				MDC.put("userName", user.getUserName());
				MDC.put("fieldUpdValue", lineInfo.getLineBandWidth());
				MDC.put("fieldOriValue", line.getLineBandWidth());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "线路管理");
				MDC.put("operType", "修改");
				MDC.put("fieldName", "带宽");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue");
			}
		}
		if(!lineInfo.getLineContFirstParty().equals("")){
			if(!line.getLineContFirstParty().equals(lineInfo.getLineContFirstParty()))
			{
				MDC.put("objId", line.getLineNum());
				MDC.put("userName", user.getUserName());
				MDC.put("fieldUpdValue", lineInfo.getLineContFirstParty());
				MDC.put("fieldOriValue", line.getLineContFirstParty());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "线路管理");
				MDC.put("operType", "修改");
				MDC.put("fieldName", "合同甲方");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue");
			}
		}
		if(!lineInfo.getLineContract().equals("")){
			if(!line.getLineContract().equals(lineInfo.getLineContract()))
			{
				MDC.put("objId", line.getLineNum());
				MDC.put("userName", user.getUserName());
				MDC.put("fieldUpdValue", lineInfo.getLineContract());
				MDC.put("fieldOriValue", line.getLineContract());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "线路管理");
				MDC.put("operType", "修改");
				MDC.put("fieldName", "合同名称");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue");
			}
		}
		if(!lineInfo.getLineContSecondParty().equals("")){
			if(!line.getLineContSecondParty().equals(lineInfo.getLineContSecondParty()))
			{
				MDC.put("objId", line.getLineNum());
				MDC.put("userName", user.getUserName());
				MDC.put("fieldUpdValue", lineInfo.getLineContSecondParty());
				MDC.put("fieldOriValue", line.getLineContSecondParty());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "线路管理");
				MDC.put("operType", "修改");
				MDC.put("fieldName", "合同乙方");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue");
			}
		}
		if(!lineInfo.getLineCountryManaDepart().equals("")){
			if(!line.getLineCountryManaDepart().equals(lineInfo.getLineCountryManaDepart()))
			{
				MDC.put("objId", line.getLineNum());
				MDC.put("userName", user.getUserName());
				MDC.put("fieldUpdValue", lineInfo.getLineCountryManaDepart());
				MDC.put("fieldOriValue", line.getLineCountryManaDepart());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "线路管理");
				MDC.put("operType", "修改");
				MDC.put("fieldName", "国家中心管理部门");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue");
			}
		}
	
	
		if(!lineInfo.getLineLength().equals("")){
			if(!line.getLineLength().equals(lineInfo.getLineLength()))
			{
				MDC.put("objId", line.getLineNum());
				MDC.put("userName", user.getUserName());
				MDC.put("fieldUpdValue", lineInfo.getLineLength());
				MDC.put("fieldOriValue", line.getLineLength());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "线路管理");
				MDC.put("operType", "修改");
				MDC.put("fieldName", "长度");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue");
			}
		}
		if(!lineInfo.getLineLocalRemote().equals("")){
			if(!line.getLineLocalRemote().equals(lineInfo.getLineLocalRemote()))
			{
				MDC.put("objId", line.getLineNum());
				MDC.put("userName", user.getUserName());
				MDC.put("fieldUpdValue", lineInfo.getLineLocalRemote());
				MDC.put("fieldOriValue", line.getLineLocalRemote());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "线路管理");
				MDC.put("operType", "修改");
				MDC.put("fieldName", "本地/长途");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue");
			}
		}
		if(!lineInfo.getLineOperator().equals("")){
			if(!line.getLineOperator().equals(lineInfo.getLineOperator()))
			{
				MDC.put("objId", line.getLineNum());
				MDC.put("userName", user.getUserName());
				MDC.put("fieldUpdValue", lineInfo.getLineOperator());
				MDC.put("fieldOriValue", line.getLineOperator());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "线路管理");
				MDC.put("operType", "修改");
				MDC.put("fieldName", "运营商");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue");
			}
		}
		if(!lineInfo.getLineRecordor().equals("")){
			if(!line.getLineRecordor().equals(lineInfo.getLineRecordor()))
			{
				MDC.put("objId", line.getLineNum());
				MDC.put("userName", user.getUserName());
				MDC.put("fieldUpdValue", lineInfo.getLineRecordor());
				MDC.put("fieldOriValue", line.getLineRecordor());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "线路管理");
				MDC.put("operType", "修改");
				MDC.put("fieldName", "记录负责人");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue");
			}
		}
		if(!lineInfo.getLineRemark().equals("")){
			if(!line.getLineRemark().equals(lineInfo.getLineRemark()))
			{
				MDC.put("objId", line.getLineNum());
				MDC.put("userName", user.getUserName());
				MDC.put("fieldUpdValue", lineInfo.getLineRemark());
				MDC.put("fieldOriValue", line.getLineRemark());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "线路管理");
				MDC.put("operType", "修改");
				MDC.put("fieldName", "备注");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue");
			}
		}
		if(!lineInfo.getLineState().equals("")){
			if(!line.getLineState().equals(lineInfo.getLineState()))
			{
				MDC.put("objId", line.getLineNum());
				MDC.put("userName", user.getUserName());
				MDC.put("fieldUpdValue", lineInfo.getLineState());
				MDC.put("fieldOriValue", line.getLineState());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "线路管理");
				MDC.put("operType", "修改");
				MDC.put("fieldName", "线路状态");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue");
			}
		}
		if(!lineInfo.getLineSubManaDepart().equals("")){
			if(!line.getLineSubManaDepart().equals(lineInfo.getLineSubManaDepart()))
			{
				MDC.put("objId", line.getLineNum());
				MDC.put("userName", user.getUserName());
				MDC.put("fieldUpdValue", lineInfo.getLineSubManaDepart());
				MDC.put("fieldOriValue", line.getLineSubManaDepart());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "线路管理");
				MDC.put("operType", "修改");
				MDC.put("fieldName", "分中心管理部门");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue");
			}
		}
		if(!lineInfo.getLineSubUseDepart().equals("")){
			if(!line.getLineSubUseDepart().equals(lineInfo.getLineSubUseDepart()))
			{
				MDC.put("objId", line.getLineNum());
				MDC.put("userName", user.getUserName());
				MDC.put("fieldUpdValue", lineInfo.getLineSubUseDepart());
				MDC.put("fieldOriValue", line.getLineSubUseDepart());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "线路管理");
				MDC.put("operType", "修改");
				MDC.put("fieldName", "分中心使用部门");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue");
			}
		}
		if(!lineInfo.getLineSubUser().equals("")){
			if(!line.getLineSubUser().equals(lineInfo.getLineSubUser()))
			{
				MDC.put("objId", line.getLineNum());
				MDC.put("userName", user.getUserName());
				MDC.put("fieldUpdValue", lineInfo.getLineSubUser());
				MDC.put("fieldOriValue", line.getLineSubUser());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "线路管理");
				MDC.put("operType", "修改");
				MDC.put("fieldName", "分中心使用人");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue");
			}
		}
		if(!lineInfo.getLineSystem().equals("")){
			if(!line.getLineSystem().equals(lineInfo.getLineSystem()))
			{
				MDC.put("objId", line.getLineNum());
				MDC.put("userName", user.getUserName());
				MDC.put("fieldUpdValue", lineInfo.getLineSystem());
				MDC.put("fieldOriValue", line.getLineSystem());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "线路管理");
				MDC.put("operType", "修改");
				MDC.put("fieldName", "所属系统");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue");
			}
		}
		if(!lineInfo.getLineType().equals("")){
			if(!line.getLineType().equals(lineInfo.getLineType()))
			{
				MDC.put("objId", line.getLineNum());
				MDC.put("userName", user.getUserName());
				MDC.put("fieldUpdValue", lineInfo.getLineType());
				MDC.put("fieldOriValue", line.getLineType());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "线路管理");
				MDC.put("operType", "修改");
				MDC.put("fieldName", "线路类型");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue");
			}
		}
		if(!lineInfo.getLineZRoom().equals("")){
			if(!line.getLineZRoom().equals(lineInfo.getLineZRoom()))
			{
				MDC.put("objId", line.getLineNum());
				MDC.put("userName", user.getUserName());
				MDC.put("fieldUpdValue", lineInfo.getLineZRoom());
				MDC.put("fieldOriValue", line.getLineZRoom());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "线路管理");
				MDC.put("operType", "修改");
				MDC.put("fieldName", "Z端所在机房");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue");
			}
		}
		if(!lineInfo.getLineZRoomSite().equals("")){
			if(!line.getLineZRoomSite().equals(lineInfo.getLineZRoomSite()))
			{
				MDC.put("objId", line.getLineNum());
				MDC.put("userName", user.getUserName());
				MDC.put("fieldUpdValue", lineInfo.getLineZRoomSite());
				MDC.put("fieldOriValue", line.getLineZRoomSite());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "线路管理");
				MDC.put("operType", "修改");
				MDC.put("fieldName", "Z端所在机房地址");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue");
			}
		}
		if(!lineInfo.getLineContOperator().equals("")){
			if(!line.getLineContOperator().equals(lineInfo.getLineContOperator()))
			{
				MDC.put("objId", line.getLineNum());
				MDC.put("userName", user.getUserName());
				MDC.put("fieldUpdValue", lineInfo.getLineContOperator());
				MDC.put("fieldOriValue", line.getLineContOperator());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "线路管理");
				MDC.put("operType", "修改");
				MDC.put("fieldName", "合同经办人");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue");
			}
		}
		if(!lineInfo.getLineUseTime().equals("")){
		
				MDC.put("objId", line.getLineNum());
				MDC.put("userName", user.getUserName());
				MDC.put("fieldUpdValue", lineInfo.getLineUseTime());
				if( line.getLineUseTime() == null){
					MDC.put("fieldOriValue", "无");
				}else{
					MDC.put("fieldOriValue", line.getLineUseTime());
				}
				MDC.put("logType", "devRecord");
				MDC.put("objType", "线路管理");
				MDC.put("operType", "修改");
				MDC.put("fieldName", "开通时间");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue");
			
		}
		if(!lineInfo.getLineDownTime().equals("")){
			
				MDC.put("objId", line.getLineNum());
				MDC.put("userName", user.getUserName());
				MDC.put("fieldUpdValue", lineInfo.getLineDownTime());
				if( line.getLineDownTime() == null){
					MDC.put("fieldOriValue", "无");
				}else{
					MDC.put("fieldOriValue", line.getLineDownTime());
				}
				MDC.put("logType", "devRecord");
				MDC.put("objType", "线路管理");
				MDC.put("operType", "修改");
				MDC.put("fieldName", "停租时间");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue");
			
		}
		if(!lineInfo.getLinePayer().equals("")){
			
				MDC.put("objId", line.getLineNum());
				MDC.put("userName", user.getUserName());
				MDC.put("fieldUpdValue", lineInfo.getLinePayer());
				if( line.getLinePayer() == null){
					MDC.put("fieldOriValue", "无");
				}else{
					MDC.put("fieldOriValue", line.getLinePayer());
				}
				MDC.put("logType", "devRecord");
				MDC.put("objType", "线路管理");
				MDC.put("operType", "修改");
				MDC.put("fieldName", "付费方");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue");
			
		}
		line.setLineARoom(lineInfo.getLineARoom());
		line.setLineARoomSite(lineInfo.getLineARoomSite());
		line.setLineBandWidth(lineInfo.getLineBandWidth());
		line.setLineContFirstParty(lineInfo.getLineContFirstParty());
		line.setLineContract(lineInfo.getLineContract());
		line.setLineContSecondParty(lineInfo.getLineContSecondParty());
		line.setLineCountryManaDepart(lineInfo.getLineCountryManaDepart());
		line.setLineUseTime(lineInfo.getLineUseTime());
		line.setLineDownTime(lineInfo.getLineDownTime());
		line.setLineLength(lineInfo.getLineLength());
		line.setLineLocalRemote(lineInfo.getLineLocalRemote());
		line.setLineOperator(lineInfo.getLineOperator());
		line.setLineRecordor(lineInfo.getLineRecordor());
		line.setLineRemark(lineInfo.getLineRemark());
		line.setLineState(lineInfo.getLineState());
		line.setLineSubManaDepart(lineInfo.getLineSubManaDepart());
		line.setLineSubUseDepart(lineInfo.getLineSubUseDepart());
		line.setLineSubUser(lineInfo.getLineSubUser());
		line.setLineSystem(lineInfo.getLineSystem());
		line.setLineType(lineInfo.getLineType());
		line.setLineZRoom(lineInfo.getLineZRoom());
		line.setLineZRoomSite(lineInfo.getLineZRoomSite());
		line.setLineContOperator(lineInfo.getLineContOperator());
        line.setLinePayer(lineInfo.getLinePayer());
		infoService.add(line);

		String success = gson.toJson("true");

		ServletActionContext.getResponse().getWriter().print(success);

		return null;

	}



	//导入
	public String importLine() throws IOException {
		HttpServletRequest req = ServletActionContext.getRequest();
		String path = req.getRealPath("/upload");			//获取项目下的upload/路径
		InputStream is = new FileInputStream(file);			//从前端input中的name="file"获取文件输入流对象

		OutputStream os = new FileOutputStream(new File(path, fileFileName));

		byte[] buffer = new byte[8192];
		int len = 0;
		while((len=is.read(buffer)) != -1){
			os.write(buffer, 0, len);
		}
		os.close() ;
		is.close();

		File ff = new File(path, fileFileName);				//根据路径和获取的文件名来创建一个文件，
		POIFSFileSystem fs;
		HSSFWorkbook wb=null;
		HSSFSheet sheet;
		HSSFRow row;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String title="未导入成功的专线资产数据";
		String[] rowName={"序号","专线号","运营商","所属系统","线路状态","本地/长途","带宽(M)","长度(KM)","线路类型","A端所在机房",
				"A端所在机房地址","Z端所在机房","Z端所在机房地址","合同名称","合同甲方","合同乙方","合同经办人",
				"分中心使用部门","分中心使用人","分中心管理部门","国家中心管理部门",
				"记录负责人","备注","开通时间","停租时间","付费方"};
		HSSFWorkbook bookWorkbook = new HSSFWorkbook();// 创建excel文件
		HSSFSheet sheet1 = bookWorkbook.createSheet("0");
		HSSFCell cellTitle,cell;
		HSSFRow rowTitle,row1;

		int rowStart = 2;			//每次调用生成新的excel时的参数，表示在新的excel待添加数据的行

		HSSFCellStyle styletitle = ect.setTableHeaderFormat(bookWorkbook);
		int rowinttitle=0;
		rowTitle = sheet1.createRow((short) rowinttitle);

		cellTitle = rowTitle.createCell((short) 0);
		cellTitle.setEncoding(HSSFCell.ENCODING_UTF_16);// 设置值的编码格式，确保不会出现乱码
		cellTitle.setCellValue(title);
		cellTitle.setCellStyle(styletitle);

		sheet1.addMergedRegion(new Region(rowinttitle, (short) 0, rowinttitle,(short) (rowName.length-1)));//共25列，合并第一行（没有更新人和更新时间）
		HSSFCellStyle style = ect.setColumnDataStyle(bookWorkbook);
		int rowint = 1;
		int titlerow1 = rowint++;
		row1 = sheet1.createRow((short) titlerow1);
		for (int i = 0; i < rowName.length; i++) {
			cell = row1.createCell((short) i);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);// 设置值的编码格式，确保不会出现乱码
			cell.setCellValue(rowName[i]);
			cell.setCellStyle(style);
		}

		// 设置每列的宽度，共26列
		for(int k=0; k<rowName.length; k++){
			sheet1.setColumnWidth((short) k, (short) 4200);
		}

		try {
			//	is = new FileInputStream("d:/专线资产.xls");
			InputStream is2 = new FileInputStream(ff);
			try {
				fs = new POIFSFileSystem(is2);
				wb = new HSSFWorkbook(fs);
			} catch (IOException e) {
				e.printStackTrace();
			}
			sheet = wb.getSheetAt(0);
			
			int rowNums = sheet.getLastRowNum();	// 得到总行数	
			System.out.println("得到要导入excel的行数>>>>>>>>>>>>>>"+rowNums);			
			String isDupli = null;					//判断是否重复
			int  repeatNum = 0;						//重复的条数
			int    succNum = 0;						//成功导入的条数
			int		  flag = 0;						//导入的数据表是否正确
			row = sheet.getRow(0);
			int colNums = row.getPhysicalNumberOfCells();
			System.out.println("得到要导入excel的列数：：：：：：："+colNums);
			System.out.println(row.getCell((short) 0).getStringCellValue()+">>>>>>>");
			System.out.println(row.getCell((short) 0).getStringCellValue().contains("专线")+">>>>>>>>>>>>");
			//检查导入数据表是否正确 根据列数 和表头名判断
			if((colNums==rowName.length) &&(row.getCell((short) 0).getStringCellValue().contains("专线"))){	
			// 正文内容应该从第三行开始,第一行为表头的标题，第二行为数据的字段名   
			for (int i = 2; i <= rowNums; i++) {
				row = sheet.getRow(i);				
				//先判断专线号不能为空
				if((ect.getCellFormatValue(row.getCell((short) 1)).length() != 0)) {
					//判断专线号是否重复
					isDupli = infoService.isLineDupli(ect.getCellFormatValue(row.getCell((short) 1)));
					System.out.println("有重复吗？"+isDupli+">>>>>>>>>>");
					if(isDupli == null){
						System.out.println("没有重复。。。。。正在导入第"+i+"行。。。。。。。。。。。。");
						//	lineInfo.setLineId(ect.getCellFormatValue(row.getCell((short) 0)));				//ID
						lineInfo.setLineNum(ect.getCellFormatValue(row.getCell((short) 1)));				//专线号
						lineInfo.setLineOperator(ect.getCellFormatValue(row.getCell((short) 2)));			//运营商
						lineInfo.setLineSystem(ect.getCellFormatValue(row.getCell((short) 3)));				//所属系统
						lineInfo.setLineState(ect.getCellFormatValue(row.getCell((short) 4)));				//线路状态
						lineInfo.setLineLocalRemote(ect.getCellFormatValue(row.getCell((short) 5)));		//本地/长途
						lineInfo.setLineBandWidth(Integer.valueOf(ect.getCellFormatValue(row.getCell((short) 6))));			//带宽
						lineInfo.setLineLength(ect.getCellFormatValue(row.getCell((short) 7)));				//长度
						lineInfo.setLineType(ect.getCellFormatValue(row.getCell((short) 8)));				//线路类型
						lineInfo.setLineARoom(ect.getCellFormatValue(row.getCell((short) 9)));				//A端所在机房
						lineInfo.setLineARoomSite(ect.getCellFormatValue(row.getCell((short) 10)));			//A端所在机房地址
						lineInfo.setLineZRoom(ect.getCellFormatValue(row.getCell((short) 11)));				//Z端所在机房
						lineInfo.setLineZRoomSite(ect.getCellFormatValue(row.getCell((short) 12)));			//Z端所在机房地址
						lineInfo.setLineContract(ect.getCellFormatValue(row.getCell((short) 13)));			//合同名称
						lineInfo.setLineContFirstParty(ect.getCellFormatValue(row.getCell((short) 14)));	//合同甲方	
						lineInfo.setLineContSecondParty(ect.getCellFormatValue(row.getCell((short) 15)));	//合同乙方
						lineInfo.setLineContOperator(ect.getCellFormatValue(row.getCell((short) 16)));		//合同经办人
						lineInfo.setLineSubUseDepart(ect.getCellFormatValue(row.getCell((short) 17)));		//分中心使用部门
						lineInfo.setLineSubUser(ect.getCellFormatValue(row.getCell((short) 18)));			//分中心使用人
					//	lineInfo.setLineCountryUseDepart(ect.getCellFormatValue(row.getCell((short) 19)));			//国家中心使用部门（已删除）
					//	lineInfo.setLineCountryUser(ect.getCellFormatValue(row.getCell((short) 20)));		//国家中心使用人（已删除）
						lineInfo.setLineSubManaDepart(ect.getCellFormatValue(row.getCell((short) 19)));		//分中心管理部门
						lineInfo.setLineCountryManaDepart(ect.getCellFormatValue(row.getCell((short) 20)));			//国家中心管理部门
						lineInfo.setLineRecordor(ect.getCellFormatValue(row.getCell((short) 21)));			//记录负责人
						lineInfo.setLineRemark(ect.getCellFormatValue(row.getCell((short) 22)));			//备注
						lineInfo.setLoginUserName(user.getUserName());										//记录更新人
						lineInfo.setLineUpdTime(sdf.format(new Date()));									//更新时间
						
						lineInfo.setLineUseTime(ect.getCellFormatValue(row.getCell((short)23)));			//开通时间
						lineInfo.setLineDownTime(ect.getCellFormatValue(row.getCell((short)24)));			//停租时间
						lineInfo.setLinePayer(ect.getCellFormatValue(row.getCell((short)25)));				//付费方
						
						hibernateTemplate.save(lineInfo);
						succNum++;						//记录导入成功的条数
					} else {	
						System.out.println("有重复了。。。。现在是第"+i+"行。。。。。。。。。");
						repeatNum++;					//记录重复的条数
						ect.creatExcel(i, colNums, bookWorkbook, sheet, sheet1, rowStart++);
						}
				}else{
					//专线号为空
					System.out.println("专线号不能为空或重复。。。。。现在是第"+i+"行。。。。。。");
					ect.creatExcel(i, colNums, bookWorkbook, sheet, sheet1, rowStart++);
					}
			}
		}else{
			flag = 1;						//导入的数据表有错
		}

		int[] result = new int[4];
		if(repeatNum ==0){		
			HttpServletResponse response  = ServletActionContext.getResponse();
			result[0] = flag;				//导入的数据表是否正确
			result[1] = repeatNum;			//不重复的标志
			result[2] = rowNums -1;			//应该导入数据的条数
			result[3] = succNum;			//成功导入的条数
			Gson gson = new Gson();
			String data = gson.toJson(result);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(data);				//没有重复，全部导入成功
			out.flush();
			out.close();
		}else {
			HttpServletResponse response  = ServletActionContext.getResponse();
			result[0] = flag;				//导入的数据表是否正确
			result[1] = repeatNum;			//不重复的标志
			result[2] = rowNums -1;			//应该导入数据的条数
			result[3] = succNum;			//成功导入的条数
			Gson gson = new Gson();
			String data = gson.toJson(result);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(data);				//没有重复，全部导入成功
			out.flush();
			out.close();
		}
			return null;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//	System.out.println("找不到文件！");
			return null;
			//		e.printStackTrace();
		}

	}



/*	public String creatExcel(Integer rowNum,int colNum, HSSFWorkbook bookWorkbook, HSSFSheet shet, HSSFSheet sheet, int rowStart) throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HSSFRow row, row2;
		HSSFCell cell;
		row  = shet.getRow(rowNum);					//获取传过来的行值
		row2 = sheet.createRow(rowStart);			//新建excel的行
		System.out.println(rowNum+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		for(int i=0;i<27;i++)
		{		
			cell=row2.createCell((short)i);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(ect.getCellFormatValue(row.getCell((short) i)));
		}
		String fileName = null;
		try {
			FileOutputStream outputStream;
			try {
				outputStream = new FileOutputStream(request.getSession().getServletContext().getRealPath("/")+"/Generatefile//DataError.xls");//同上
				bookWorkbook.write(outputStream);
				outputStream.flush();
				outputStream.close();
				fileName = "DataError.xls";
			} catch (FileNotFoundException e) {
				System.err.println("获取不到位置");

			} catch (IOException e) {

			}

		} finally{}
		Gson gson = new Gson();
			String success = gson.toJson(fileName);
			ServletActionContext.getResponse().getWriter().print(success);
		
		return null;
	}
*/

	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	@Test
	public  String  exportExcel() throws Exception //用于数据维护导出全部表情
	{		
		//由于表头是固定的，不能从数据库导出，需要自己写
		String title="线路管理表";
		String[] rowName={"序号","专线号","运营商","所属系统","线路状态","本地/长途","带宽(M)","长度(KM)","线路类型","A端所在机房",
				"A端所在机房地址","Z端所在机房","Z端所在机房地址","合同名称","合同甲方","合同乙方","合同经办人",
				"分中心使用部门","分中心使用人","分中心管理部门","国家中心管理部门",
				"记录负责人","备注","记录更新人","更新时间","开通时间","停租时间","付费方"
		};
		HSSFWorkbook bookWorkbook = new HSSFWorkbook();// 创建excel文件
		HSSFSheet sheet = bookWorkbook.createSheet("0");
		HSSFCell cellTitle,cell;
		HSSFRow rowTitle,row;
		HSSFCellStyle styletitle = ect.setTableHeaderFormat(bookWorkbook);

		HttpServletRequest request = ServletActionContext.getRequest();

		int rowinttitle=0;
		rowTitle = sheet.createRow((short) rowinttitle);

		cellTitle = rowTitle.createCell((short) 0);
		cellTitle.setEncoding(HSSFCell.ENCODING_UTF_16);// 设置值的编码格式，确保不会出现乱码
		cellTitle.setCellValue(title);
		cellTitle.setCellStyle(styletitle);

		sheet.addMergedRegion(new Region(rowinttitle, (short) 0, rowinttitle,
				(short) 27));//共28列，合并第一行
		HSSFCellStyle style = ect.setColumnDataStyle(bookWorkbook);
		int rowint = 1;
		int titlerow1 = rowint++;
		row = sheet.createRow((short) titlerow1);
		for (int i = 0; i < rowName.length; i++) {
			cell = row.createCell((short) i);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);// 设置值的编码格式，确保不会出现乱码
			cell.setCellValue(rowName[i]);
			cell.setCellStyle(style);
		}

		// 设置每列的宽度，共28列
		for(int k=0;k<28;k++)
		{
			sheet.setColumnWidth((short) k, (short) 4200);
		}

		HSSFCellStyle styleInfo = ect.setColumnDataStyle(bookWorkbook);
		int rowintInfo = 2;//从表的第三行开始进行遍历，写入数据
		List<LinInf> list=infoDao.findAll();
		System.out.println(list.size());
		for(int i=0;i<infoDao.findAll().size();i++)
		{
			row=sheet.createRow(rowintInfo++);
			int kk =0;
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineId());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineNum());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineOperator());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineSystem());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineState());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineLocalRemote());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineBandWidth());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineLength());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineType());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineARoom());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineARoomSite());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineZRoom());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineZRoomSite());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineContract());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineContFirstParty());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineContSecondParty());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineContOperator());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineSubUseDepart());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineSubUser());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineSubManaDepart());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineContSecondParty());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineRecordor());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineRemark());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLoginUserName());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineUpdTime());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineUseTime());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineDownTime());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLinePayer());

		}


		String filename=null;
		try {
			FileOutputStream outputStream;


			/**  输出信息，导出excel
			 * 
			 *  response.setContentType("application/vnd.ms-excel;charset=UTF-8");
	response.setHeader("Content-Type", "application/octet-stream");
			 *  OutputStream out = response.getOutputStream();
	wb.write(out);
	out.close();
			 */
			try {
				outputStream = new FileOutputStream(request.getSession().getServletContext().getRealPath("/")+"/Generatefile//Line information table.xls");//如此定义路径是为了导出时弹出路径选择框
				bookWorkbook.write(outputStream);
				outputStream.flush();
				outputStream.close();
				filename="Line information table.xls";
				//	return "Export Success";
			} catch (FileNotFoundException e) {
				System.err.println("获取不到位置");

			} catch (IOException e) {

			}

		} finally{}


		Gson gson = new Gson();
		String success = gson.toJson(filename);
		ServletActionContext.getResponse().getWriter().print(success);//向后台发送数据，判断导出是否成功

		return null;

	}


	@Test


	public String exportExcelByConditions() throws Exception //用于判断导出当前页
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		String str1=request.getParameter("data");//接收前台发送的数据，数据为当前页的每行的id号

		JSONObject jsonArr = JSONObject.fromObject(str1);


		String title="线路信息表-按条件导出后选择条目导出";
		String[] rowName={"序号","专线号","运营商","所属系统","线路状态","本地/长途","带宽(M)","长度(KM)","线路类型","A端所在机房",
				"A端所在机房地址","Z端所在机房","Z端所在机房地址","合同名称","合同甲方","合同乙方","合同经办人",
				"分中心使用部门","分中心使用人","分中心管理部门","国家中心管理部门",
				"记录负责人","备注","记录更新人","更新时间","开通时间","停租时间","付费方"
		};

		HSSFWorkbook bookWorkbook = new HSSFWorkbook();// 创建excel文件
		HSSFSheet sheet = bookWorkbook.createSheet("0");
		HSSFCell cellTitle,cell;
		HSSFRow rowTitle,row;
		HSSFCellStyle styletitle = ect.setTableHeaderFormat(bookWorkbook);

		int rowinttitle=0;
		rowTitle = sheet.createRow((short) rowinttitle);

		cellTitle = rowTitle.createCell((short) 0);
		cellTitle.setEncoding(HSSFCell.ENCODING_UTF_16);// 设置值的编码格式，确保不会出现乱码
		cellTitle.setCellValue(title);
		cellTitle.setCellStyle(styletitle);

		sheet.addMergedRegion(new Region(rowinttitle, (short) 0, rowinttitle,
				(short) 27));//合并第一行，同上个函数里的功能相同
		HSSFCellStyle style = ect.setColumnDataStyle(bookWorkbook);
		int rowint = 1;
		int titlerow1 = rowint++;
		row = sheet.createRow((short) titlerow1);
		for (int i = 0; i < rowName.length; i++) {
			cell = row.createCell((short) i);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);// 设置值的编码格式，确保不会出现乱码
			cell.setCellValue(rowName[i]);
			cell.setCellStyle(style);
		}

		// 设置每列的宽度
		for(int k=0;k<28;k++)
		{
			sheet.setColumnWidth((short) k, (short) 4200);

		}

		HSSFCellStyle styleInfo = ect.setColumnDataStyle(bookWorkbook);
		int rowintInfo = 2;


		List<String> infoIds=(List<String>)jsonArr.getJSONArray("lineIds");//将id号强制转换为list类型

		List<LinInf> list=new ArrayList<LinInf>();

		for(int i=0;i<infoIds.size();i++)//遍历list，取出当前页的所有信息
		{
			list.add(infoDao.findById(infoIds.get(i)));;
		}

		for(int i=0;i<list.size();i++)//输出所选当前页信息
		{

			row=sheet.createRow(rowintInfo++);
			int kk =0;
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineId());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineNum());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineOperator());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineSystem());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineState());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineLocalRemote());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineBandWidth());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineLength());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineType());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineARoom());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineARoomSite());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineZRoom());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineZRoomSite());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineContract());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineContFirstParty());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineContSecondParty());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineContOperator());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineSubUseDepart());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineSubUser());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineSubManaDepart());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineContSecondParty());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineRecordor());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineRemark());
			kk++;

			/*cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineRemark());
			kk++;*/

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLoginUserName());
			kk++;


			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineUpdTime());	
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineUseTime());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineDownTime());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLinePayer());

			System.out.println(kk);


		}
		String filename=null;
		try {
			FileOutputStream outputStream;


			/**  输出信息，导出excel
			 * 
			 *  response.setContentType("application/vnd.ms-excel;charset=UTF-8");
	response.setHeader("Content-Type", "application/octet-stream");
			 *  OutputStream out = response.getOutputStream();
	wb.write(out);
	out.close();
			 */
			try {
				outputStream = new FileOutputStream(request.getSession().getServletContext().getRealPath("/")+"/Generatefile//Line information table to export the current page.xls");//为弹出路径选择框，所以如此写路径
				//Line information table to export the current page.xls"
				bookWorkbook.write(outputStream);
				outputStream.flush();
				outputStream.close();
				filename="Line information table to export the current page.xls";
				//	return "Export Success";
			} catch (FileNotFoundException e) {
				System.err.println("获取不到位置");

			} catch (IOException e) {

			}

		} finally{}

		//	HttpServletResponse response = ServletActionContext.getResponse();

		//	response.setContentType("textml;charset=utf-8");
		Gson gson = new Gson();
		String success = gson.toJson(filename);
		ServletActionContext.getResponse().getWriter().print(success);//给前台发送数据，判断释放导出成功

		return null;

	}

	public String exportAllExcelByConditions() throws Exception
	{

		HttpServletRequest request = ServletActionContext.getRequest();

		String title="线路管理表";
		String[] rowName={"序号","专线号","运营商","所属系统","线路状态","本地/长途","带宽(M)","长度(KM)","线路类型","A端所在机房",
				"A端所在机房地址","Z端所在机房","Z端所在机房地址","合同名称","合同甲方","合同乙方","合同经办人",
				"分中心使用部门","分中心使用人","分中心管理部门","国家中心管理部门",
				"记录负责人","备注","记录更新人","更新时间","开通时间","停租时间","付费方"
		};
		String[] str=new String[3];//用于接收前台发送的查询条件，作为条件判断语句

		str[0]=request.getParameter("lineNum");
		str[1]=request.getParameter("lineOperator");
		str[2]=request.getParameter("lineSystem");

		//		str[3]=request.getParameter("lineOperator1");
		//		str[4]=request.getParameter("lineSystem1");

		//		str[5]=request.getParameter("lineLocalRemote1");
		//		str[6]=request.getParameter("lineBandWidth1");
		//		str[7]=request.getParameter("lineARoom1");
		//		str[8]=request.getParameter("lineZRoom1");
		//		str[9]=request.getParameter("lineCountryUser1");


		HSSFWorkbook bookWorkbook = new HSSFWorkbook();// 创建excel文件
		HSSFSheet sheet = bookWorkbook.createSheet("0");
		HSSFCell cellTitle,cell;
		HSSFRow rowTitle,row;
		HSSFCellStyle styletitle = ect.setTableHeaderFormat(bookWorkbook);

		int rowinttitle=0;
		rowTitle = sheet.createRow((short) rowinttitle);

		cellTitle = rowTitle.createCell((short) 0);
		cellTitle.setEncoding(HSSFCell.ENCODING_UTF_16);// 设置值的编码格式，确保不会出现乱码
		cellTitle.setCellValue(title);
		cellTitle.setCellStyle(styletitle);

		sheet.addMergedRegion(new Region(rowinttitle, (short) 0, rowinttitle,
				(short) 27));//合并第一行
		HSSFCellStyle style = ect.setColumnDataStyle(bookWorkbook);
		int rowint = 1;
		int titlerow1 = rowint++;
		row = sheet.createRow((short) titlerow1);
		for (int i = 0; i < rowName.length; i++) {
			cell = row.createCell((short) i);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);// 设置值的编码格式，确保不会出现乱码
			cell.setCellValue(rowName[i]);
			cell.setCellStyle(style);
		}

		// 设置每列的宽度
		for(int k=0;k<28;k++)
		{
			sheet.setColumnWidth((short) k, (short) 4200);

		}

		HSSFCellStyle styleInfo = ect.setColumnDataStyle(bookWorkbook);
		int rowintInfo = 2;

		Conditions conditions=new Conditions();

		conditions.addCondition("lineNum", str[0], Operator.LIKE);//模糊查询
		conditions.addCondition("lineOperator", str[1], Operator.LIKE);
		conditions.addCondition("lineSystem", str[2], Operator.LIKE);

		//		conditions.addCondition("lineOperator", str[3], Operator.EQUAL);  用于接收筛选的条件值，因为功能未实现，所以注释掉
		//		conditions.addCondition("lineSystem", str[4], Operator.EQUAL);

		//		conditions.addCondition("lineLocalRemote", str[5], Operator.EQUAL);
		//		conditions.addCondition("lineBandWidth", str[6], Operator.EQUAL);
		//		conditions.addCondition("lineARoom", str[7], Operator.EQUAL);
		//		conditions.addCondition("lineZRoom", str[8], Operator.EQUAL);
		//		conditions.addCondition("lineCountryUser", str[9], Operator.EQUAL);

		List<LinInf> list=infoDao.findByConditions(conditions);//依据条件查找，找到查询内容
		System.out.println(list.size());
		for(int i=0;i<list.size();i++)
		{
			row=sheet.createRow(rowintInfo++);
			int kk =0;
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineId());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineNum());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineOperator());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineSystem());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineState());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineLocalRemote());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineBandWidth());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineLength());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineType());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineARoom());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineARoomSite());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineZRoom());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineZRoomSite());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineContract());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineContFirstParty());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineContSecondParty());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineContOperator());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineSubUseDepart());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineSubUser());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineSubManaDepart());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineContSecondParty());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineRecordor());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineRemark());
			kk++;



			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLoginUserName());
			kk++;


			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineUpdTime());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineUseTime());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineDownTime());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLinePayer());
		}
		String filename=null;
		try {
			FileOutputStream outputStream;


			/**  输出信息，导出excel
			 * 
			 *  response.setContentType("application/vnd.ms-excel;charset=UTF-8");
	response.setHeader("Content-Type", "application/octet-stream");
			 *  OutputStream out = response.getOutputStream();
	wb.write(out);
	out.close();
			 */
			try {
				outputStream = new FileOutputStream(request.getSession().getServletContext().getRealPath("/")+"/Generatefile//Line information table according to the conditions of the full page.xls");//同上
				bookWorkbook.write(outputStream);
				outputStream.flush();
				outputStream.close();
				filename="Line information table according to the conditions of the full page.xls";
				//	return "Export Success";
			} catch (FileNotFoundException e) {
				System.err.println("获取不到位置");

			} catch (IOException e) {

			}

		} finally{}
		Gson gson = new Gson();
		String success = gson.toJson(filename);
		ServletActionContext.getResponse().getWriter().print(success);

		return null;

	}



	@Test


	public String exportpartExcelByConditions() throws Exception //用于部分导出，其余主要对应参考前面的注释
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		String str1=request.getParameter("data");//用于接收前台发送的id号

		JSONObject jsonArr = JSONObject.fromObject(str1);


		String title="线路信息表-按条件导出后选择条目导出";
		String[] rowName={"序号","专线号","运营商","所属系统","线路状态","本地/长途","带宽(M)","长度(KM)","线路类型","A端所在机房",
				"A端所在机房地址","Z端所在机房","Z端所在机房地址","合同名称","合同甲方","合同乙方","合同经办人",
				"分中心使用部门","分中心使用人","分中心管理部门","国家中心管理部门",
				"记录负责人","备注","记录更新人","更新时间","开通时间","停租时间","付费方"
		};

		HSSFWorkbook bookWorkbook = new HSSFWorkbook();// 创建excel文件
		HSSFSheet sheet = bookWorkbook.createSheet("0");
		HSSFCell cellTitle,cell;
		HSSFRow rowTitle,row;
		HSSFCellStyle styletitle = ect.setTableHeaderFormat(bookWorkbook);

		int rowinttitle=0;
		rowTitle = sheet.createRow((short) rowinttitle);

		cellTitle = rowTitle.createCell((short) 0);
		cellTitle.setEncoding(HSSFCell.ENCODING_UTF_16);// 设置值的编码格式，确保不会出现乱码
		cellTitle.setCellValue(title);
		cellTitle.setCellStyle(styletitle);

		sheet.addMergedRegion(new Region(rowinttitle, (short) 0, rowinttitle,
				(short) 27));//合并第一行
		HSSFCellStyle style = ect.setColumnDataStyle(bookWorkbook);
		int rowint = 1;
		int titlerow1 = rowint++;
		row = sheet.createRow((short) titlerow1);
		for (int i = 0; i < rowName.length; i++) {
			cell = row.createCell((short) i);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);// 设置值的编码格式，确保不会出现乱码
			cell.setCellValue(rowName[i]);
			cell.setCellStyle(style);
		}

		// 设置每列的宽度
		for(int k=0;k<28;k++)
		{
			sheet.setColumnWidth((short) k, (short) 4200);

		}

		HSSFCellStyle styleInfo = ect.setColumnDataStyle(bookWorkbook);
		int rowintInfo = 2;	



		List<String> infoIds=(List<String>)jsonArr.getJSONArray("lineIds");

		List<LinInf> list=new ArrayList<LinInf>();

		for(int i=0;i<infoIds.size();i++)
		{
			list.add(infoDao.findById(infoIds.get(i)));;
		}

		for(int i=0;i<list.size();i++)
		{

			row=sheet.createRow(rowintInfo++);
			int kk =0;
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineId());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineNum());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineOperator());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineSystem());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineState());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineLocalRemote());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineBandWidth());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineLength());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineType());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineARoom());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineARoomSite());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineZRoom());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineZRoomSite());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineContract());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineContFirstParty());
			kk++;
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineContSecondParty());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineContOperator());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineSubUseDepart());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineSubUser());
			kk++;

			

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineSubManaDepart());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineContSecondParty());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineRecordor());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineRemark());
			kk++;

			/*cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineRemark());
			kk++;*/
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLoginUserName());
			kk++;


			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineUpdTime());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineUseTime());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLineDownTime());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLinePayer());
			
			System.out.println("+++++++++++++++++++++++++++++");
			System.out.println(kk);
			System.out.println("==================================");


		}
		String filename=null;
		try {
			FileOutputStream outputStream;


			/**  输出信息，导出excel
			 * 
			 *  response.setContentType("application/vnd.ms-excel;charset=UTF-8");
	response.setHeader("Content-Type", "application/octet-stream");
			 *  OutputStream out = response.getOutputStream();
	wb.write(out);
	out.close();
			 */
			try {
				outputStream = new FileOutputStream(request.getSession().getServletContext().getRealPath("/")+"/Generatefile//Line information table according to the conditions of the part of the export.xls");
				bookWorkbook.write(outputStream);
				outputStream.flush();
				outputStream.close();
				filename="Line information table according to the conditions of the part of the export.xls";
				//	return "Export Success";
			} catch (FileNotFoundException e) {
				System.err.println("获取不到位置");

			} catch (IOException e) {

			}

		} finally{}
		Gson gson = new Gson();
		String success = gson.toJson(filename);
		ServletActionContext.getResponse().getWriter().print(success);

		return null;

	}

	public String readExcelContent(InputStream is) {//读取导入表格的内容

		String succ=null;//作为返回值，判断读取是否成功
		List<LinInf> list=new ArrayList<LinInf>();
		list=infoDao.findAll();
		//		System.out.println("3333333333333333333333333");
		//		System.out.println(list.size()+"4444444444");

		String lineId=null;
		String lineNum=null;
		String lineOperator=null;
		String lineSystem=null;

		String lineState=null;
		String lineLocalRemote=null;
		String lineBandWidth=null;
		String lineLength=null;

		String lineType=null;
		String lineARoom=null;
		String lineARoomSite=null;
		String lineZRoom=null;

		String lineZRoomSite=null;
		String lineContract=null;
		String lineContFirstParty=null;
		String lineContSecondParty=null;

		String lineContOperator=null;
		String lineSubUseDepart=null;
		String lineSubUser=null;
		//String lineCountryUseDepart=null;

		//String lineCountryUser=null;
		String lineSubManaDepart=null;
		String lineCountryManaDepart=null;
		String lineRecordor=null;

		String lineRemark=null;
		String loginUserName=null;
		String lineUpdTime=null;
		
		String  lineUseTime=null;
		String  lineDownTime=null;
		String linePayer=null; 

	try {
			fsimport = new POIFSFileSystem(is);
			wbimport = new HSSFWorkbook(fsimport);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheetimport = wbimport.getSheetAt(0);
		// 得到总行数
		int rowNum = sheetimport.getLastRowNum();

		rowmark = sheetimport.getRow(1);//获取第二行的总列数，因为第一行已经合并，所以不能以第一行的列数作为判断条件
		int colNum = rowmark.getPhysicalNumberOfCells();

		//System.out.println("导入的excel有"+colNum+"列。。。。。。。。。。。。。");

		if(colNum==28)//判断导入的表格是否是27列，如果是，则证明是线路信息表，进行导入
		{


			for (int i = 2; i <= rowNum; i++) {
				rowimport = sheetimport.getRow(i);
				int j = 0;

				lineId= ect.getCellFormatValue(rowimport.getCell((short) j));
				lineInfo.setLineId(lineId);
				j++;
				//Integer.parseInt(offId);
				// getDao.addOrUpdateAll(beans);

				lineNum=ect.getCellFormatValue(rowimport.getCell((short) j));
				lineInfo.setLineNum(lineNum);
				j++;

				lineOperator=ect.getCellFormatValue(rowimport.getCell((short) j));
				lineInfo.setLineOperator(lineOperator);
				j++;

				lineSystem=ect.getCellFormatValue(rowimport.getCell((short) j));
				lineInfo.setLineSystem(lineSystem);
				j++;

				lineState=ect.getCellFormatValue(rowimport.getCell((short) j));
				lineInfo.setLineState(lineState);
				j++;

				lineLocalRemote=ect.getCellFormatValue(rowimport.getCell((short) j));
				lineInfo.setLineLocalRemote(lineLocalRemote);
				j++;

				lineBandWidth=ect.getCellFormatValue(rowimport.getCell((short) j));
				lineInfo.setLineBandWidth(Integer.parseInt(lineBandWidth ));
				j++;

				lineLength=ect.getCellFormatValue(rowimport.getCell((short) j));
				lineInfo.setLineLength(lineLength);
				j++;

				lineType=ect.getCellFormatValue(rowimport.getCell((short) j));
				lineInfo.setLineType(lineType);
				j++;

				lineARoom=ect.getCellFormatValue(rowimport.getCell((short) j));
				lineInfo.setLineARoom(lineARoom);
				j++;

				lineARoomSite=ect.getCellFormatValue(rowimport.getCell((short) j));
				lineInfo.setLineARoomSite(lineARoomSite);
				j++;

				lineZRoom=ect.getCellFormatValue(rowimport.getCell((short) j));
				lineInfo.setLineZRoom(lineZRoom);
				j++;

				lineZRoomSite=ect.getCellFormatValue(rowimport.getCell((short) j));
				lineInfo.setLineZRoomSite(lineZRoomSite);
				j++;

				lineContract=ect.getCellFormatValue(rowimport.getCell((short) j));
				lineInfo.setLineContract(lineContract);
				j++;

				lineContFirstParty=ect.getCellFormatValue(rowimport.getCell((short) j));
				lineInfo.setLineContFirstParty(lineContFirstParty);
				j++;

				lineContSecondParty=ect.getCellFormatValue(rowimport.getCell((short) j));
				lineInfo.setLineContSecondParty(lineContSecondParty);
				j++;

				lineContOperator=ect.getCellFormatValue(rowimport.getCell((short) j));
				lineInfo.setLineContOperator(lineContOperator);
				j++;

				lineSubUseDepart=ect.getCellFormatValue(rowimport.getCell((short) j));
				lineInfo.setLineSubUseDepart(lineSubUseDepart);
				j++;

				lineSubUser=ect.getCellFormatValue(rowimport.getCell((short) j));
				lineInfo.setLineSubUser(lineSubUser);
				j++;

//				lineCountryUseDepart=ect.getCellFormatValue(rowimport.getCell((short) j));可能需要删除的列，改动请注释
//				lineInfo.setLineCountryUseDepart(lineCountryUseDepart);
//				j++;
//
//				lineCountryUser=ect.getCellFormatValue(rowimport.getCell((short) j));可能需要删除的列，改动请注释
//				lineInfo.setLineCountryUser(lineCountryUser);
//				j++;

				lineSubManaDepart=ect.getCellFormatValue(rowimport.getCell((short) j));
				lineInfo.setLineSubManaDepart(lineSubManaDepart);
				j++;

				lineCountryManaDepart=ect.getCellFormatValue(rowimport.getCell((short) j));
				lineInfo.setLineCountryManaDepart(lineCountryManaDepart);
				j++;

				lineRecordor=ect.getCellFormatValue(rowimport.getCell((short) j));
				lineInfo.setLineRecordor(lineRecordor);
				j++;

				lineRemark=ect.getCellFormatValue(rowimport.getCell((short) j));
				lineInfo.setLineRemark(lineRemark);
				j++;

				loginUserName=ect.getCellFormatValue(rowimport.getCell((short) j));
				lineInfo.setLoginUserName(loginUserName);
				j++;

				lineUpdTime=ect.getCellFormatValue(rowimport.getCell((short) j));
				lineInfo.setLineUpdTime(lineUpdTime);
				j++;
				
				lineUseTime=ect.getCellFormatValue(rowimport.getCell((short) j));
				lineInfo.setLineUseTime(lineUseTime);
				j++;
				
				lineDownTime=ect.getCellFormatValue(rowimport.getCell((short) j));
				lineInfo.setLineDownTime(lineDownTime);
				j++;
				
				linePayer=ect.getCellFormatValue(rowimport.getCell((short) j));
				lineInfo.setLinePayer(linePayer);
				
				hibernateTemplate.save(lineInfo);	


			}
			succ="1";
			return succ;
		}
		else
			return succ;
	}


	@Test 
	public String   excelToMysql() throws IOException {//将表格内容读取到数据库

		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getRealPath("/Generatefile");
		InputStream is = new FileInputStream(file);

		OutputStream os = new FileOutputStream(new File(path, fileFileName));
		byte[] buffer = new byte[200];
		int len = 0;
		while((len=is.read(buffer)) != -1){

			os.write(buffer, 0, len);
		}
		os.close();
		is.close();

		File ff = new File(path, fileFileName);
		InputStream is2 = new FileInputStream(ff);
		List<LinInf> list1=infoDao.findAll();




		if(readExcelContent(is2)=="1")//如果导入成功，对前台返回-1作为判断
		{

			HttpServletResponse response  = ServletActionContext.getResponse();
			PrintWriter out = response.getWriter();
			out.print(-1);
			out.flush();
			out.close();
			try {
				infoDao.deleteAll(list1);//导入之前对数据库进行清空
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

		}
		else{

			HttpServletResponse response  = ServletActionContext.getResponse();
			PrintWriter out = response.getWriter();
			out.print(1);
			out.flush();
			out.close();
		}

		return null;	


	}

}
