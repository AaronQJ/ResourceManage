package com.bussniess.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.bussniess.dao.impl.SpeProDaoImpl;
import com.bussniess.dao.util.Conditions;
import com.bussniess.dao.util.Conditions.Operator;
import com.bussniess.domain.SpePro;
import com.bussniess.domain.Users;
import com.bussniess.service.SpeProService;
import com.bussniess.util.BarcodeInfo;
import com.bussniess.util.DataTablesPage;
import com.bussniess.util.ExcelCellType;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class ManagerSpeProAction extends ActionSupport implements ModelDriven<SpePro> {

	private static final long serialVersionUID = 1L;
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
	HibernateTemplate hibernateTemplate = (HibernateTemplate) applicationContext.getBean("hibernateTemplate");

	SpeProDaoImpl speDao = (SpeProDaoImpl) applicationContext.getBean("speDao");

	ExcelCellType<SpePro> ect = new ExcelCellType<SpePro>();


	private File file;
	private String fileFileName;				//文件名
	private String fileContentType;				//文件的类型

	private POIFSFileSystem fsimport;
	private HSSFWorkbook wbimport;
	private HSSFSheet sheetimport;
	private HSSFRow rowimport;
	Users user = (Users)ServletActionContext.getRequest().getSession().getAttribute("user");

	private static Logger log = Logger.getLogger(ManagerSpeProAction.class);
	private SpePro spe = new SpePro();
	private SpeProService speService;

	@Override
	public SpePro getModel() {

		return spe;
	}


	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


	public SpePro getSpe() {
		return spe;
	}

	public void setSpe(SpePro spe) {
		this.spe = spe;
	}

	public SpeProService getSpeService() {
		return speService;
	}

	public void setSpeService(SpeProService speService) {
		this.speService = speService;
	}



	//创建barInfo对象
	BarcodeInfo<SpePro> barInfo = new BarcodeInfo<SpePro>();

	public String  add() throws IOException{



		spe.setLoginUserName(user.getUserName());

		//得到条码号
		String speBarcode = barInfo.getCodeNum(sdf.format(spe.getSpeArrAcceptTime()), 1,
				barInfo.getSpeType(spe.getSpeDevType()), barInfo.getId(BarcodeInfo.speTableName, spe.getSpeArrAcceptTime()));		


		spe.setSpeUpdTime(sdf.format(new Date()));
		spe.setSpeBarCode(speBarcode);


		//生成条码图片
		barInfo.createBarcode(spe.getSpeBarCode(), spe.getSpeDevType());	

		speService.add(spe);

		MDC.put("userName", user.getUserName());
		MDC.put("objName", spe.getSpeDevName());
		MDC.put("objId", spe.getSpeSeqNum());
		MDC.put("logType", "devRecord");
		MDC.put("objType", "专用资产");
		MDC.put("operType", "增加");

		log.error("");
		MDC.remove("userName");
		MDC.remove("objName");
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


	@Test
	public void  findAll(){
		List<SpePro> speList  =  speService.findAll();
		Gson gson = new Gson();
		String speStr = gson.toJson(speList);
		System.out.println(speStr);
	}

	public String  send() throws IOException{

		Conditions conditions  = new Conditions();
		System.out.println(conditions.createWhereAndValues()+"++++++++");
		DataTablesPage<SpePro>   page = new DataTablesPage<SpePro>() ;
		HttpServletRequest request= ServletActionContext.getRequest();
		String speDevName = request.getParameter("speDevName");
		String speDevRoom = request.getParameter("speDevRoom");
		String speDevRoomFrame = request.getParameter("speDevRoomFrame");
		String speUser = request.getParameter("speUser");
		String speState = request.getParameter("speState");


		conditions.addCondition("speDevName", speDevName, Operator.EQUAL);
		conditions.addCondition("speDevRoom", speDevRoom, Operator.EQUAL);
		conditions.addCondition("speDevRoomFrame", speDevRoomFrame, Operator.EQUAL);
		conditions.addCondition("speUser", speUser, Operator.EQUAL);
		conditions.addCondition("speState", speState, Operator.EQUAL);



		//	String iDisplayLength = request.getParameter("iDisplayLength");
		String nowPage = request.getParameter("nowPage");
		//	int iDisplayLength1 = Integer.valueOf(iDisplayLength);
		int nowPage1 = Integer.valueOf(nowPage);

		DataTablesPage<SpePro>  pagaData = speService. findAll(conditions, speService.findAll().size(),nowPage1) ;
		Gson gson = new Gson();
		String data = gson.toJson(pagaData);
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


		//	DataTablesPage<SpePro>   page = new DataTablesPage<SpePro>() ;
		HttpServletRequest request= ServletActionContext.getRequest();

		String  speDevName  = request.getParameter("speDevName");
		String  speSeqNum   = request.getParameter("speSeqNum");
		String  speBarCode  = request.getParameter("speBarCode");
		String  speUser      =  request.getParameter("speUser");
		String  speState      =  request.getParameter("speState");

		conditions.addCondition("speDevName", speDevName, Operator.LIKE);
		conditions.addCondition("speSeqNum", speSeqNum, Operator.LIKE);
		conditions.addCondition("speBarCode", speBarCode, Operator.LIKE);
		conditions.addCondition("speUser", speUser, Operator.LIKE);
		conditions.addCondition("speState", speState, Operator.LIKE);


		List<String> speRoomFrameList  =  new ArrayList<String>();
		List<String>  speUserNameList  =  new ArrayList<String>();
		List<String>      speRoomList  =  new ArrayList<String>();
		List<String>   speDevNameList  =  new ArrayList<String>();
		List<String>     speStateList  =  new ArrayList<String>();
		List<SpePro>       speProList  =  speService.findByConditions(conditions);

		for(SpePro spe: speProList){
			speRoomList.add(spe.getSpeDevRoom());
		}
		Set<String>        speRoomSet  =  new HashSet<String>(speRoomList);
		speRoomList.clear();
		speRoomList.addAll(speRoomSet);
		System.out.println(speRoomList.toString()+"+++++++++=======");

		for(SpePro spe: speProList){
			speRoomFrameList.add(spe.getSpeDevRoomFrame());
		}
		Set<String>   speRoomFrameSet  =  new HashSet<String>(speRoomFrameList);
		speRoomFrameList.clear();
		speRoomFrameList.addAll(speRoomFrameSet);
		System.out.println(speRoomFrameList.toString()+"+++++++++=======");


		for(SpePro spe: speProList){
			speUserNameList.add(spe.getSpeUser());
		}
		Set<String> speUserNameSet = new HashSet<String>(speUserNameList);
		speUserNameList.clear();
		speUserNameList.addAll(speUserNameSet);
		System.out.println(speUserNameList.toString()+"+++++++++=======");

		for(SpePro spe: speProList){
			speDevNameList.add(spe.getSpeDevName());
		}
		Set<String> speDevNameSet = new HashSet<String>(speDevNameList);
		speDevNameList.clear();
		speDevNameList.addAll(speDevNameSet);
		System.out.println(speDevNameList.toString()+"+++++++++=======");

		for(SpePro spe: speProList){
			speStateList.add(spe.getSpeState());
		}
		Set<String> speStateSet = new HashSet<String>(speStateList);
		speStateList.clear();
		speStateList.addAll(speStateSet);
		System.out.println(speUserNameList.toString()+"+++++++++=======");

		Map<String,Object> map = new HashMap<String, Object>();
//		HttpSession  session = ServletActionContext.getRequest().getSession();

		map.put("speRoomFrameList",speRoomFrameList );
		map.put("speUserNameList", speUserNameList);
		map.put("speRoomList", speRoomList);
		map.put("speDevNameList", speDevNameList);
		map.put("speStateList", speStateList);


//		session.setMaxInactiveInterval(500);
		
	
		String iDisplayLength = request.getParameter("iDisplayLength");
		String nowPage = request.getParameter("nowPage");
		int iDisplayLength1 = Integer.valueOf(iDisplayLength);
		//	System.out.println(iDisplayLength1+"---------");
		int nowPage1 = Integer.valueOf(nowPage);
		System.out.println("---------------"+nowPage1);

		DataTablesPage<SpePro>  pagaData = speService.findAll(conditions, iDisplayLength1,nowPage1) ;
		Gson gson = new Gson();
		
		map.put("pagaData", pagaData);
		
		String mapStr = gson.toJson(map);
		System.out.println(mapStr+"1223455666");
		String data = gson.toJson(pagaData);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out =response.getWriter();
		out.print(mapStr);
		out.flush();
		out.close();
		return null;
	}



	public  String checkSeqNum() throws IOException{
		Conditions conditions=new Conditions();
		String speSeqNum = (String) ServletActionContext.getRequest().getAttribute("speSeqNum");

		System.out.println("根据序列号"+speSeqNum+"进行判断是否数据有重复。。。。。");
		conditions.addCondition("speSeqNum", speSeqNum, Operator.EQUAL);

		boolean isRepeat =  speService.chackSeqNum(conditions);

		Gson gson = new Gson();
		String seqRepeat =  gson.toJson(isRepeat);
		System.out.println(seqRepeat);
		ServletActionContext.getResponse().getWriter().print(seqRepeat);

		return null;
	}


	public String  updateMore() throws Exception{
		HttpServletRequest  request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		request.setCharacterEncoding("utf-8");
		String jsonArr  =  request.getParameter("data");
		JSONObject jsonObject=JSONObject.fromObject(jsonArr);//json-lib中是jo=JSONObject.fromObject(t);

		System.out.println(jsonArr); 

		List<String> speIds=(List<String>) jsonObject.getJSONArray("speIds");

		String  speState = (String) jsonObject.get("speState");

		String  speUser = (String) jsonObject.get("speUser");
		String  speDevRoom = (String) jsonObject.get("speDevRoom");
		String  speDevRoomFrame= (String) jsonObject.get("speDevRoomFrame");

		System.out.println(speIds.toString());

		System.out.println("speState="+speState+"--speUser="+speUser+"--speDevRoom="+speDevRoom+"--speDevRoomFrame="+speDevRoomFrame);

		for (int i = 0; i < speIds.size(); i++) {  
			//获取第i个数组元素  
			String speid= speIds.get(i);  
			System.out.println(speid);
			SpePro spePro = speService.findById(speid);
			System.out.println(spePro);
			if(!speState.equals("请选择") )  {
				if(!spePro.getSpeState().equals(speState)){
					MDC.put("fieldUpdValue", speState);
					MDC.put("fieldOriValue", spePro.getSpeState());
					MDC.put("userName", user.getUserName());
					MDC.put("objName", spePro.getSpeDevName());
					MDC.put("objId", spePro.getSpeSeqNum());
					MDC.put("logType", "devRecord");
					MDC.put("objType", "专用资产");
					MDC.put("operType", "修改");
					MDC.put("fieldName", "状态");
					log.error("");
					MDC.remove("userName");
					MDC.remove("objName");
					MDC.remove("objId");
					MDC.remove("logType");
					MDC.remove("objType");
					MDC.remove("operType");
					MDC.remove("fieldName");
					MDC.remove("fieldOriValue");
					MDC.remove("fieldUpdValue");
				}
			}
			if(!speUser.equals("")){
				if(!spePro.getSpeUser().equals(speUser)){
					MDC.put("fieldUpdValue", speUser);
					MDC.put("fieldOriValue", spePro.getSpeUser());
					MDC.put("userName", user.getUserName());
					MDC.put("objName", spePro.getSpeDevName());
					MDC.put("objId", spePro.getSpeSeqNum());
					MDC.put("logType", "devRecord");
					MDC.put("objType", "专用资产");
					MDC.put("operType", "修改");
					MDC.put("fieldName", "使用人");
					log.error("");
					MDC.remove("userName");
					MDC.remove("objName");
					MDC.remove("logType");
					MDC.remove("objId");
					MDC.remove("objType");
					MDC.remove("operType");
					MDC.remove("fieldName");
					MDC.remove("fieldOriValue");
					MDC.remove("fieldUpdValue");
				}
			}
			if(!speDevRoom.equals("")){
				if(!spePro.getSpeDevRoom().equals(speDevRoom)){
					MDC.put("fieldUpdValue", speDevRoom);
					MDC.put("fieldOriValue", spePro.getSpeDevRoom());
					MDC.put("userName", user.getUserName());
					MDC.put("objName", spePro.getSpeDevName());
					MDC.put("objId", spePro.getSpeSeqNum());
					MDC.put("logType", "devRecord");
					MDC.put("objType", "专用资产");
					MDC.put("operType", "修改");
					MDC.put("fieldName", "所属机房");
					log.error("");
					MDC.remove("userName");
					MDC.remove("objName");
					MDC.remove("logType");
					MDC.remove("objId");
					MDC.remove("objType");
					MDC.remove("operType");
					MDC.remove("fieldName");
					MDC.remove("fieldOriValue");
					MDC.remove("fieldUpdValue");
				}
			}
			if(!speDevRoomFrame.equals("")){
				if(!spePro.getSpeDevRoomFrame().equals(speDevRoomFrame)){
					MDC.put("fieldUpdValue", speDevRoomFrame);
					MDC.put("fieldOriValue", spePro.getSpeDevRoomFrame());
					MDC.put("userName", user.getUserName());
					MDC.put("objName", spePro.getSpeDevName());
					MDC.put("objId", spePro.getSpeSeqNum());
					MDC.put("logType", "devRecord");
					MDC.put("objType", "专用资产");
					MDC.put("operType", "修改");
					MDC.put("fieldName", "所在机柜");
					log.error("");
					MDC.remove("userName");
					MDC.remove("objName");
					MDC.remove("logType");
					MDC.remove("objId");
					MDC.remove("objType");
					MDC.remove("operType");
					MDC.remove("fieldName");
					MDC.remove("fieldOriValue");
					MDC.remove("fieldUpdValue");
				}
			}
			spePro.setSpeDevRoom(speDevRoom);
			spePro.setSpeDevRoomFrame(speDevRoomFrame);
			spePro.setSpeUser(speUser);
			spePro.setSpeState(speState);

			speService.update(spePro);

		}  

		String success = gson.toJson("true");
		ServletActionContext.getResponse().getWriter().print(success);
		return null;
	}


	public String deleteMore() throws Exception{

		HttpServletRequest  request = ServletActionContext.getRequest();

		request.setCharacterEncoding("utf-8");
		String jsonArr  =  request.getParameter("data");
		JSONObject jsonObject=JSONObject.fromObject(jsonArr);//json-lib中是jo=JSONObject.fromObject(t);
		Gson gson = new Gson();
		System.out.println(jsonArr); 

		List<String> speIds=(List<String>) jsonObject.getJSONArray("speIds");

		String  speState = (String) jsonObject.get("speState");
		String  speUser = (String) jsonObject.get("speUser");
		String  speDevRoom = (String) jsonObject.get("speDevRoom");
		String  speDevRoomFrame= (String) jsonObject.get("speDevRoomFrame");

		System.out.println(speIds.toString());

		System.out.println("speState="+speState+"--speUser="+speUser+"--speDevRoom="+speDevRoom+"--speDevRoomFrame="+speDevRoomFrame);

		List<SpePro> speList = new ArrayList<SpePro>();
		for (int i = 0; i < speIds.size(); i++) {  
			//获取第i个数组元素  
			String speid= speIds.get(i);  
			System.out.println(speid);
			SpePro spe1 =  speService.findById(speid);

			MDC.put("logType", "devRecord");
			MDC.put("objType", "专用资产");
			MDC.put("operType", "删除");
			MDC.put("objName", spe1.getSpeDevName());
			MDC.put("objId", spe1.getSpeSeqNum());
			MDC.put("userName", user.getUserName());
			log.error("");
			MDC.remove("userName");
			MDC.remove("objName");
			MDC.remove("objId");
			MDC.remove("logType");
			MDC.remove("objType");
			MDC.remove("operType");
			MDC.remove("fieldName");
			MDC.remove("fieldOriValue");
			MDC.remove("fieldUpdValue");
			speService.deleteById(speid);

			SpePro spe =  speService.findById(speid);
			speList.add(spe);

		}  
		String deleSucc;
		if(speList!=null){
			deleSucc=  gson.toJson("false");
		}
		deleSucc = gson.toJson("true");
		ServletActionContext.getResponse().getWriter().print(deleSucc);

		return null;
	}

	public String deleteOne() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		String bakId = request.getParameter("bakId");
		Gson gson  = new Gson();
		speService.deleteById(bakId);

		SpePro  spePro = speService.findById(bakId);
		String deleSucc;
		if(spePro!=null){
			deleSucc=  gson.toJson("false");
		}
		deleSucc = gson.toJson("true");
		ServletActionContext.getResponse().getWriter().print(deleSucc);


		return null;
	}

	public String  findOne() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();

		String speId = request.getParameter("speId");
		SpePro   spePro = speService.findById(speId);

		String spe= gson.toJson(spePro);

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(spe);
		return null;
	}

	public String updateOne() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		String speId = request.getParameter("speId");

		SpePro spePro =  speService.findById(speId);
		if(!spe.getSpeArrAcceptTime().equals("")){
			if(!spePro.getSpeArrAcceptTime().equals(spe.getSpeArrAcceptTime())){
				MDC.put("fieldUpdValue", spe.getSpeArrAcceptTime());
				MDC.put("fieldOriValue", spePro.getSpeArrAcceptTime());
				MDC.put("fieldName", "到货验收时间");
				MDC.put("objName", spePro.getSpeDevName());
				MDC.put("objId", spePro.getSpeSeqNum());
				MDC.put("userName", user.getUserName());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "专用资产");
				MDC.put("operType", "修改");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue"); 
			}
		}
		if(!spe.getSpeAssertsNum().equals("")){
			if(!spePro.getSpeAssertsNum().equals(spe.getSpeAssertsNum())){
				MDC.put("fieldUpdValue", spe.getSpeAssertsNum());
				MDC.put("fieldOriValue", spePro.getSpeAssertsNum());
				MDC.put("fieldName", "固定资产代号");
				MDC.put("objName", spePro.getSpeDevName());
				MDC.put("objId", spePro.getSpeSeqNum());
				MDC.put("userName", user.getUserName());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "专用资产");
				MDC.put("operType", "修改");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue"); 
			}
		}
		if(!spe.getSpeAssetsManaDepart().equals("")){
			if(!spePro.getSpeAssetsManaDepart().equals(spe.getSpeAssetsManaDepart())){
				MDC.put("fieldUpdValue", spe.getSpeAssetsManaDepart());
				MDC.put("fieldOriValue", spePro.getSpeAssetsManaDepart());
				MDC.put("fieldName", "固定资产管理部门");
				MDC.put("objName", spePro.getSpeDevName());
				MDC.put("objId", spePro.getSpeSeqNum());
				MDC.put("userName", user.getUserName());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "专用资产");
				MDC.put("operType", "修改");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue"); 
			}
		}
		if(!spe.getSpeBuyCont().equals("")){
			if(!spePro.getSpeBuyCont().equals(spe.getSpeBuyCont())){
				MDC.put("fieldUpdValue", spe.getSpeBuyCont());
				MDC.put("fieldOriValue", spePro.getSpeBuyCont());
				MDC.put("fieldName", "采购合同");
				MDC.put("objName", spePro.getSpeDevName());
				MDC.put("objId", spePro.getSpeSeqNum());
				MDC.put("userName", user.getUserName());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "专用资产");
				MDC.put("operType", "修改");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue"); 
			}
		}
		if(!spe.getSpeDevFactor().equals("")){
			if(!spePro.getSpeDevFactor().equals(spe.getSpeDevFactor())){
				MDC.put("fieldUpdValue", spe.getSpeDevFactor());
				MDC.put("fieldOriValue", spePro.getSpeDevFactor());
				MDC.put("fieldName", "设备厂商");
				MDC.put("objName", spePro.getSpeDevName());
				MDC.put("objId", spePro.getSpeSeqNum());
				MDC.put("userName", user.getUserName());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "专用资产");
				MDC.put("operType", "修改");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objName");
				MDC.remove("logType");
				MDC.remove("objId");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue"); 
			}
		}
		if(!spe.getSpeDevName().equals("")){
			if(!spePro.getSpeDevName().equals(spe.getSpeDevName())){
				MDC.put("fieldUpdValue", spe.getSpeDevName());
				MDC.put("fieldOriValue", spePro.getSpeDevName());
				MDC.put("fieldName", "设备名称");
				MDC.put("objName", spePro.getSpeDevName());
				MDC.put("objId", spePro.getSpeSeqNum());
				MDC.put("userName", user.getUserName());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "专用资产");
				MDC.put("operType", "修改");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objName");
				MDC.remove("logType");
				MDC.remove("objId");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue"); 
			}
		}
		if(!spe.getSpeDevRoom().equals("")){
			if(!spePro.getSpeDevRoom().equals(spe.getSpeDevRoom())){
				MDC.put("fieldUpdValue", spe.getSpeDevRoom());
				MDC.put("fieldOriValue", spePro.getSpeDevRoom());
				MDC.put("fieldName", "所属机房");
				MDC.put("objName", spePro.getSpeDevName());
				MDC.put("objId", spePro.getSpeSeqNum());
				MDC.put("userName", user.getUserName());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "专用资产");
				MDC.put("operType", "修改");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objName");
				MDC.remove("logType");
				MDC.remove("objId");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue"); 
			}
		}
		if(!spe.getSpeDevRoomFrame().equals("")){
			if(!spePro.getSpeDevRoomFrame().equals(spe.getSpeDevRoomFrame())){
				MDC.put("fieldUpdValue", spe.getSpeDevRoomFrame());
				MDC.put("fieldOriValue", spePro.getSpeDevRoomFrame());
				MDC.put("fieldName", "所在机柜");
				MDC.put("objName", spePro.getSpeDevName());
				MDC.put("objId", spePro.getSpeSeqNum());
				MDC.put("userName", user.getUserName());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "专用资产");
				MDC.put("operType", "修改");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue"); 
			}
		}
		if(!spe.getSpeDevType().equals("")){
			if(!spePro.getSpeDevType().equals(spe.getSpeDevType())){
				MDC.put("fieldUpdValue", spe.getSpeDevRoomFrame());
				MDC.put("fieldOriValue", spePro.getSpeDevRoomFrame());
				MDC.put("fieldName", "设备类别");
				MDC.put("objName", spePro.getSpeDevName());
				MDC.put("objId", spePro.getSpeSeqNum());
				MDC.put("userName", user.getUserName());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "专用资产");
				MDC.put("operType", "修改");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue"); 
			}
		}
		if(!spe.getSpeMaintainCont().equals("")){
			if(!spePro.getSpeMaintainCont().equals(spe.getSpeMaintainCont())){
				MDC.put("fieldUpdValue", spe.getSpeMaintainCont());
				MDC.put("fieldOriValue", spePro.getSpeMaintainCont());
				MDC.put("fieldName", "维保合同");
				MDC.put("objName", spePro.getSpeDevName());
				MDC.put("objId", spePro.getSpeSeqNum());
				MDC.put("userName", user.getUserName());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "专用资产");
				MDC.put("operType", "修改");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue"); 
			}
		}
		if(!spe.getSpeMaintainDeadLine().equals("")){
			if(!spePro.getSpeMaintainDeadLine().equals(spe.getSpeMaintainDeadLine())){
				MDC.put("fieldUpdValue", spe.getSpeMaintainDeadLine());
				MDC.put("fieldOriValue", spePro.getSpeMaintainDeadLine());
				MDC.put("fieldName", "维保截止时间");
				MDC.put("objName", spePro.getSpeDevName());
				MDC.put("objId", spePro.getSpeSeqNum());
				MDC.put("userName", user.getUserName());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "专用资产");
				MDC.put("operType", "修改");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue"); 
			}
		}
		if(!spe.getSpeAssetsManaDepart().equals("")){
			if(!spePro.getSpeManaDepart().equals(spe.getSpeAssetsManaDepart())){
				MDC.put("fieldUpdValue", spe.getSpeAssetsManaDepart());
				MDC.put("fieldOriValue", spePro.getSpeManaDepart());
				MDC.put("fieldName", "实物管理部门");
				MDC.put("objName", spePro.getSpeDevName());
				MDC.put("objId", spePro.getSpeSeqNum());
				MDC.put("userName", user.getUserName());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "专用资产");
				MDC.put("operType", "修改");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue"); 
			}
		}
		if(!spe.getSpeMasterIP().equals("")){
			if(!spePro.getSpeMasterIP().equals(spe.getSpeMasterIP())){
				MDC.put("fieldUpdValue", spe.getSpeMasterIP());
				MDC.put("fieldOriValue", spePro.getSpeMasterIP());
				MDC.put("fieldName", "主IP地址");
				MDC.put("objName", spePro.getSpeDevName());
				MDC.put("objId", spePro.getSpeSeqNum());
				MDC.put("userName", user.getUserName());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "专用资产");
				MDC.put("operType", "修改");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objName");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("objId");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue"); 
			}
		}
		if(!spe.getSpeMaintainFactor().equals("")){
			if(!spePro.getSpeMaintainFactor().equals(spe.getSpeMaintainFactor())){
				MDC.put("fieldUpdValue", spe.getSpeMaintainFactor());
				MDC.put("fieldOriValue", spePro.getSpeMaintainFactor());
				MDC.put("fieldName", "维保厂商");
				MDC.put("objName", spePro.getSpeDevName());
				MDC.put("objId", spePro.getSpeSeqNum());
				MDC.put("userName", user.getUserName());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "专用资产");
				MDC.put("operType", "修改");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objName");
				MDC.remove("logType");
				MDC.remove("objId");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue"); 
			}
		}
		if(!spe.getSpeOwnNet().equals("")){
			if(!spePro.getSpeOwnNet().equals(spe.getSpeOwnNet())){
				MDC.put("fieldUpdValue", spe.getSpeOwnNet());
				MDC.put("fieldOriValue", spePro.getSpeOwnNet());
				MDC.put("fieldName", "所属网络");
				MDC.put("objName", spePro.getSpeDevName());
				MDC.put("objId", spePro.getSpeSeqNum());
				MDC.put("userName", user.getUserName());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "专用资产");
				MDC.put("operType", "修改");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue"); 
			}
		}
		if(!spe.getSpeOwnSystem().equals("")){
			if(!spePro.getSpeOwnSystem().equals(spe.getSpeOwnSystem())){
				MDC.put("fieldUpdValue", spe.getSpeOwnSystem());
				MDC.put("fieldOriValue", spePro.getSpeOwnSystem());
				MDC.put("fieldName", "所属系统");
				MDC.put("objName", spePro.getSpeDevName());
				MDC.put("objId", spePro.getSpeSeqNum());
				MDC.put("userName", user.getUserName());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "专用资产");
				MDC.put("operType", "修改");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue"); 
			}
		}
		if(!spe.getSpeProject().equals("")){
			if(!spePro.getSpeProject().equals(spe.getSpeProject())){
				MDC.put("fieldUpdValue", spe.getSpeProject());
				MDC.put("fieldOriValue", spePro.getSpeProject());
				MDC.put("fieldName", "隶属工程");
				MDC.put("objName", spePro.getSpeDevName());
				MDC.put("objId", spePro.getSpeSeqNum());
				MDC.put("userName", user.getUserName());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "专用资产");
				MDC.put("operType", "修改");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objName");
				MDC.remove("logType");
				MDC.remove("objId");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue"); 
			}
		}
		if(!spe.getSpeProOwn().equals("")){
			if(!spePro.getSpeProOwn().equals(spe.getSpeProOwn())){
				MDC.put("fieldUpdValue", spe.getSpeProOwn());
				MDC.put("fieldOriValue", spePro.getSpeProOwn());
				MDC.put("fieldName", "资产所属");
				MDC.put("objName", spePro.getSpeDevName());
				MDC.put("objId", spePro.getSpeSeqNum());
				MDC.put("userName", user.getUserName());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "专用资产");
				MDC.put("operType", "修改");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objName");
				MDC.remove("logType");
				MDC.remove("objId");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue"); 
			}
		}
		if(!spe.getSpeRemark().equals("")){
			if(!spePro.getSpeRemark().equals(spe.getSpeRemark())){
				MDC.put("fieldUpdValue", spe.getSpeRemark());
				MDC.put("fieldOriValue", spePro.getSpeRemark());
				MDC.put("fieldName", "备注");
				MDC.put("objName", spePro.getSpeDevName());
				MDC.put("objId", spePro.getSpeSeqNum());
				MDC.put("userName", user.getUserName());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "专用资产");
				MDC.put("operType", "修改");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objName");
				MDC.remove("logType");
				MDC.remove("objId");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue"); 
			}
		}
		if(!spe.getSpeSlaveIP().equals("")){
			if(!spePro.getSpeSlaveIP().equals(spe.getSpeSlaveIP())){
				MDC.put("fieldUpdValue", spe.getSpeSlaveIP());
				MDC.put("fieldOriValue", spePro.getSpeSlaveIP());
				MDC.put("fieldName", "普通IP地址");
				MDC.put("objName", spePro.getSpeDevName());
				MDC.put("objId", spePro.getSpeSeqNum());
				MDC.put("userName", user.getUserName());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "专用资产");
				MDC.put("operType", "修改");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objName");
				MDC.remove("logType");
				MDC.remove("objId");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue"); 
			}
		}
		if(!spe.getSpeState().equals("")){
			if(!spePro.getSpeState().equals(spe.getSpeState())){
				MDC.put("fieldUpdValue", spe.getSpeState());
				MDC.put("fieldOriValue", spePro.getSpeState());
				MDC.put("fieldName", "状态");
				MDC.put("objName", spePro.getSpeDevName());
				MDC.put("objId", spePro.getSpeSeqNum());
				MDC.put("userName", user.getUserName());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "专用资产");
				MDC.put("operType", "修改");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objName");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("objId");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue"); 
			}
		}
		if(!spe.getSpeUser().equals("")){
			if(!spePro.getSpeUser().equals(spe.getSpeUser())){
				MDC.put("fieldUpdValue", spe.getSpeUser());
				MDC.put("fieldOriValue", spePro.getSpeUser());
				MDC.put("fieldName", "使用人");
				MDC.put("objName", spePro.getSpeDevName());
				MDC.put("objId", spePro.getSpeSeqNum());
				MDC.put("userName", user.getUserName());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "专用资产");
				MDC.put("operType", "修改");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objName");
				MDC.remove("logType");
				MDC.remove("objId");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue"); 
			}
		}
		if(!spe.getSpeUseDepart().equals("")){
			if(!spePro.getSpeUseDepart().equals(spe.getSpeUseDepart())){
				MDC.put("fieldUpdValue", spe.getSpeUseDepart());
				MDC.put("fieldOriValue", spePro.getSpeUseDepart());
				MDC.put("fieldName", "使用部门");
				MDC.put("objName", spePro.getSpeDevName());
				MDC.put("objId", spePro.getSpeSeqNum());
				MDC.put("userName", user.getUserName());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "专用资产");
				MDC.put("operType", "修改");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objName");
				MDC.remove("logType");
				MDC.remove("objId");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue"); 
			}
		}
		if(!spe.getSpeVersion().equals("")){
			if(!spePro.getSpeVersion().equals(spe.getSpeVersion())){
				MDC.put("fieldUpdValue", spe.getSpeVersion());
				MDC.put("fieldOriValue", spePro.getSpeVersion());
				MDC.put("fieldName", "型号");
				MDC.put("objName", spePro.getSpeDevName());
				MDC.put("objId", spePro.getSpeSeqNum());
				MDC.put("userName", user.getUserName());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "专用资产");
				MDC.put("operType", "修改");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue"); 
			}
		}
		if(!spe.getSpeAssState().equals("")){
			if(!spePro.getSpeAssState().equals(spe.getSpeAssState())){
				MDC.put("fieldUpdValue", spe.getSpeAssState());
				if(spePro.getSpeAssState() == null){
					MDC.put("fieldOriValue", "无");
				}else{
					MDC.put("fieldOriValue", spePro.getSpeAssState());
				}
				MDC.put("fieldName", "资产状态");
				MDC.put("objName", spePro.getSpeDevName());
				MDC.put("objId", spePro.getSpeSeqNum());
				MDC.put("userName", user.getUserName());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "专用资产");
				MDC.put("operType", "修改");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue"); 
			}
		}
		if(!spe.getSpeFixedTime().equals("")){
			if(!spePro.getSpeFixedTime().equals(spe.getSpeFixedTime())){
				MDC.put("fieldUpdValue", spe.getSpeFixedTime());
				if(spePro.getSpeFixedTime() == null){
					MDC.put("fieldOriValue", "无");
				}else{
					MDC.put("fieldOriValue", spePro.getSpeFixedTime());
				}
				
				MDC.put("fieldName", "加固时间");
				MDC.put("objName", spePro.getSpeDevName());
				MDC.put("objId", spePro.getSpeSeqNum());
				MDC.put("userName", user.getUserName());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "专用资产");
				MDC.put("operType", "修改");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue"); 
			}
		}
		if(!spe.getSpeScrapTime().equals("")){
			if(!spePro.getSpeScrapTime().equals(spe.getSpeScrapTime())){
				MDC.put("fieldUpdValue", spe.getSpeScrapTime());
				if(spePro.getSpeScrapTime() == null){
					MDC.put("fieldOriValue", "无");
				}else{
					MDC.put("fieldOriValue", spePro.getSpeScrapTime());
				}
				MDC.put("fieldName", "报废时间");
				MDC.put("objName", spePro.getSpeDevName());
				MDC.put("objId", spePro.getSpeSeqNum());
				MDC.put("userName", user.getUserName());
				MDC.put("logType", "devRecord");
				MDC.put("objType", "专用资产");
				MDC.put("operType", "修改");
				log.error("");
				MDC.remove("userName");
				MDC.remove("objName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("operType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue"); 
			}
		}
		spePro.setSpeArrAcceptTime(spe.getSpeArrAcceptTime());
		spePro.setSpeAssertsNum(spe.getSpeAssertsNum());
		spePro.setSpeAssetsManaDepart(spe.getSpeAssetsManaDepart());
		spePro.setSpeBuyCont(spe.getSpeBuyCont());
		spePro.setSpeDevFactor(spe.getSpeDevFactor());
		spePro.setSpeDevName(spe.getSpeDevName());
		spePro.setSpeDevRoom(spe.getSpeDevRoom());
		spePro.setSpeDevRoomFrame(spe.getSpeDevRoomFrame());
		spePro.setSpeDevType(spe.getSpeDevType());
		spePro.setSpeMaintainCont(spe.getSpeMaintainCont());
		spePro.setSpeMaintainDeadLine(spe.getSpeMaintainDeadLine());
		spePro.setSpeManaDepart(spe.getSpeAssetsManaDepart());
		spePro.setSpeMasterIP(spe.getSpeMasterIP());
		spePro.setSpeMaintainFactor(spe.getSpeMaintainFactor());
		spePro.setSpeOwnNet(spe.getSpeOwnNet());
		spePro.setSpeOwnSystem(spe.getSpeOwnSystem());
		spePro.setSpeProject(spe.getSpeProject());
		spePro.setSpeProOwn(spePro.getSpeProOwn());
		spePro.setSpeRemark(spe.getSpeRemark());
		spePro.setSpeSlaveIP(spe.getSpeSlaveIP());
		spePro.setSpeState(spe.getSpeState());
		spePro.setSpeUser(spe.getSpeUser());
		spePro.setSpeUseDepart(spe.getSpeUseDepart());
		spePro.setSpeVersion(spe.getSpeVersion());
       spePro.setSpeAssState(spe.getSpeAssState());
       spePro.setSpeFixedTime(spe.getSpeFixedTime());
        spePro.setSpeScrapTime(spe.getSpeScrapTime());

		speService.add(spePro);

		String success = gson.toJson("true");
		ServletActionContext.getResponse().getWriter().print(success);

		return null;
	}

	public String   sendChart() throws IOException{

		Gson gson = new Gson();
		List<Map<String,String>> typeNumList = new ArrayList<Map<String,String>>(); 
		Map<String,String>  typeNum = new HashMap<String,String>();
		Map<String,String> devState = new HashMap<String,String>();


		Map<String,String> roomNameMap = new HashMap<String,String>();
		Map<String,String>  roomCountMap= new HashMap<String,String>();

		//	List<String> roomName = new ArrayList<String>();
		//	List<Integer> roomCount = new ArrayList<Integer>();

		List<SpePro> speList = speService.findAll();

		List<String> roomList = new ArrayList<String>();
		for (SpePro spe : speList) {
			roomList.add(spe.getSpeDevRoom());
		}
		HashSet<String> roomSet  =   new  HashSet<String>(roomList); 
		roomList.clear(); 
		roomList.addAll(roomSet); 
		devState.put("机房总数", String.valueOf(roomList.size()));
		//机房
		for(int i=0;i<roomList.size();i++){
			Conditions conditions  = new Conditions();
			conditions.addCondition("speDevRoom",roomList.get(i) , Operator.EQUAL);

			int count = speService.findByCondition(conditions);
			roomNameMap.put(String.valueOf(i), roomList.get(i));
			roomCountMap.put(String.valueOf(i), String.valueOf(count));
		}



		//路由器
		Conditions conditions  = new Conditions();
		conditions.addCondition("speDevType", "路由器", Operator.EQUAL);

		int LYNum = speService.findByCondition(conditions);
		typeNum.put("路由器", String.valueOf(LYNum));
		//交换机
		Conditions conditions1  = new Conditions();
		conditions1.addCondition("speDevType", "交换机", Operator.EQUAL);
		int JHNum = speService.findByCondition(conditions1);
		typeNum.put("交换机", String.valueOf(JHNum));
		//服务器
		Conditions conditions2 = new Conditions();
		conditions2.addCondition("speDevType", "服务器", Operator.EQUAL);
		int FWNum = speService.findByCondition(conditions2);
		typeNum.put("服务器", String.valueOf(FWNum));
		//其他
		int OTNum = speService.findAll().size()-LYNum-JHNum-FWNum;
		typeNum.put("其他", String.valueOf(OTNum));


		//在维
		Conditions conditions3 = new Conditions();
		conditions3.addCondition("speState", "在维", Operator.EQUAL);
		int ZWNum = speService.findByCondition(conditions3);
		devState.put("在维", String.valueOf(ZWNum));
		//不在维
		Conditions conditions4 = new Conditions();
		conditions4.addCondition("speState", "不在维", Operator.EQUAL);
		int BZWNum = speService.findByCondition(conditions4);
		devState.put("不在维", String.valueOf(BZWNum));
		//下线
		Conditions conditions5 = new Conditions();
		conditions5.addCondition("speState", "下线", Operator.EQUAL);
		int XXNum = speService.findByCondition(conditions5);
		devState.put("下线", String.valueOf(XXNum));

		typeNumList.add(typeNum);
		typeNumList.add(devState);
		typeNumList.add(roomNameMap);
		typeNumList.add(roomCountMap);
		String typeCount = gson.toJson(typeNumList);

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(typeCount);

		return null;

	}

	//扫码查询
	public String findByBarcode() throws IOException{
		Conditions conditions  = new Conditions();


		//	DataTablesPage<SpePro>   page = new DataTablesPage<SpePro>() ;
		HttpServletRequest request= ServletActionContext.getRequest();

		String  speBarCode  = request.getParameter("speBarCode");

		conditions.addCondition("speBarCode", speBarCode, Operator.EQUAL);

		String iDisplayLength = request.getParameter("iDisplayLength");
		String nowPage = request.getParameter("nowPage");
		int iDisplayLength1 = Integer.valueOf(iDisplayLength);
		//	System.out.println(iDisplayLength1+"---------");
		int nowPage1 = Integer.valueOf(nowPage);
		System.out.println("---------------"+nowPage1);

		DataTablesPage<SpePro>  pagaData = speService.findAll(conditions, iDisplayLength1,nowPage1) ;
		Gson gson = new Gson();
		String data = gson.toJson(pagaData);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out =response.getWriter();
		out.print(data);
		out.flush();
		out.close();


		return null;
	}





	//导入
	public String importSpe() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		String path = req.getRealPath("/upload");
		InputStream is = new FileInputStream(file);

		OutputStream os = new FileOutputStream(new File(path, fileFileName));
		byte[] buffer = new byte[8192];
		int len = 0;
		while((len=is.read(buffer)) != -1){
			os.write(buffer, 0, len);
		}
		os.close();
		is.close();

		File ff = new File(path, fileFileName);
		POIFSFileSystem fs;
		HSSFWorkbook wb=null;
		HSSFSheet sheet;
		HSSFRow row;


		String title="导入后的错误数据";
		String[] rowName={"序号","设备名称","所属系统","状态","资产所属","固定资产管理部门",
				"型号","设备厂商","设备类别","所属机房","所在机柜","使用部门","使用人","实物管理部门",
				"序列号","所属网络","主IP地址","普通IP地址","隶属工程","采购合同","到货验收时间","固定资产代号",
				"维保合同","维保厂商","维保截止时间","条码","备注","资产状态","转固时间","报废时间"};
		HSSFWorkbook bookWorkbook = new HSSFWorkbook();// 创建excel文件
		HSSFSheet sheet1 = bookWorkbook.createSheet("0");
		HSSFCell cellTitle,cell;
		HSSFRow rowTitle,row1;

		int rowStart = 2;			//每次调用生成新的excel时的参数，表示在新的excel待添加数据的行

		HSSFCellStyle styletitle = ect.setTableHeaderFormat(bookWorkbook);
		int rowinttitle=0;
		rowTitle = sheet1.createRow((short) rowinttitle);

		cellTitle = rowTitle.createCell((short) 0);
		cellTitle.setEncoding(HSSFCell.ENCODING_UTF_16);					// 设置值的编码格式，确保不会出现乱码
		cellTitle.setCellValue(title);
		cellTitle.setCellStyle(styletitle);

		sheet1.addMergedRegion(new Region(rowinttitle, (short) 0, rowinttitle,(short) (rowName.length-1)));		//共27列，合并第一行
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
		System.out.println("要导入的excel的列数");
		// 设置每列的宽度，共27列
		for(int k=0; k<rowName.length; k++){
			sheet1.setColumnWidth((short) k, (short) 4200);
		}


		try {
			InputStream is2 =  new FileInputStream(ff);
			System.out.println("要导入的文件流是>>>>>>>>>>>>>>"+is2);
			try {
				fs = new POIFSFileSystem(is2);
				wb = new HSSFWorkbook(fs);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("版本不对!!!!!!!");
			}
			sheet = wb.getSheetAt(0);
			int rowNums = sheet.getLastRowNum();		// 得到总行数
			System.out.println("得到的excel总行数是>>>>>>>>>>"+rowNums);

			String isDupli = null;						//判断是否重复
			int  repeatNum = 0;							//重复的条数
			int    succNum = 0;							//成功导入的条数
			int       flag = 0;							//导入的数据表是否正确
			row = sheet.getRow(0);
			int colNums = row.getPhysicalNumberOfCells();
			System.out.println("得到要导入excel的列数：：：：：：："+colNums);
			System.out.println("第一行的数据是：？？"+row.getCell((short) 0).getStringCellValue());

			//检查导入数据表是否正确 根据列数 和表头名判断
			if((colNums== rowName.length) && (row.getCell((short) 0).getStringCellValue().contains("专用"))){

				// 正文内容应该从第三行开始,第一行为表头的标题，第二行为数据的字段名
				for (int i = 2; i <= rowNums; i++) {
					row = sheet.getRow(i);

					//先判断导入数据中的序列号和到货验收时间不能为空 且 时间的格式正确
					if( !(ect.getCellFormatValue(row.getCell((short) 14)).equals("")) && 
							!(ect.getCellFormatValue(row.getCell((short) 20))).equals("") && 
							!(ect.getCellFormatValue(row.getCell((short) 24))).equals("") && 
							ect.isValidDate(ect.getCellFormatValue(row.getCell((short) 20))) &&
							ect.isValidDate(ect.getCellFormatValue(row.getCell((short) 24)))){

						//判断第14列(序列号)中的值是否与数据表中的值有重复
						isDupli = speService.isSpeDupli(ect.getCellFormatValue(row.getCell((short) 14)));			

						System.out.println("有没有重复？"+isDupli+".......");
						//判断待增加的数据中和数据表中是否重复（主要看speSeqNum是否相同），若重复就不进行增加（但是可以将相同的数据的 更新人，更新时间 字段进行更新）
						if(isDupli == null){		//没有重复的
							System.out.println("正在导入第"+i+"行数据/////////////////////////////");
							//保存到数据库中      
							spe.setSpeDevName(ect.getCellFormatValue(row.getCell((short) 1)));				//设备名称				
							spe.setSpeOwnSystem(ect.getCellFormatValue(row.getCell((short) 2)));			//所属系统				
							spe.setSpeState(ect.getCellFormatValue(row.getCell((short) 3)));				//状态
							spe.setSpeProOwn(ect.getCellFormatValue(row.getCell((short) 4)));				//资产所属
							spe.setSpeAssetsManaDepart(ect.getCellFormatValue(row.getCell((short) 5)));		//固定资产管理部门
							spe.setSpeVersion(ect.getCellFormatValue(row.getCell((short) 6)));				//型号
							spe.setSpeDevFactor(ect.getCellFormatValue(row.getCell((short) 7)));			//设备厂商
							spe.setSpeDevType(ect.getCellFormatValue(row.getCell((short) 8)));				//设备类别
							spe.setSpeDevRoom(ect.getCellFormatValue(row.getCell((short) 9)));				//所属机房
							spe.setSpeDevRoomFrame(ect.getCellFormatValue(row.getCell((short) 10)));		//所属机柜
							spe.setSpeUseDepart(ect.getCellFormatValue(row.getCell((short) 11)));			//使用部门
							spe.setSpeUser(ect.getCellFormatValue(row.getCell((short) 12)));				//使用人
							spe.setSpeManaDepart(ect.getCellFormatValue(row.getCell((short) 13)));			//实物管理部门
							spe.setSpeSeqNum(ect.getCellFormatValue(row.getCell((short) 14)));				//序列号
							spe.setSpeOwnNet(ect.getCellFormatValue(row.getCell((short) 15)));				//所属网络
							spe.setSpeMasterIP(ect.getCellFormatValue(row.getCell((short) 16)));			//主IP地址
							spe.setSpeSlaveIP(ect.getCellFormatValue(row.getCell((short) 17)));				//普通IP地址
							spe.setSpeProject(ect.getCellFormatValue(row.getCell((short) 18)));				//隶属工程
							spe.setSpeBuyCont(ect.getCellFormatValue(row.getCell((short) 19)));				//采购合同
							System.out.println("准备导入到货验收时间了。。。。。。。。");
							spe.setSpeArrAcceptTime(StrToDate(ect.getCellFormatValue(row.getCell((short) 20))));			//到货验收时间
							spe.setSpeAssertsNum(ect.getCellFormatValue(row.getCell((short) 21)));			//固定资产代号
							spe.setSpeMaintainCont(ect.getCellFormatValue(row.getCell((short) 22)));		//维保合同
							spe.setSpeMaintainFactor(ect.getCellFormatValue(row.getCell((short) 23)));		//维保厂商
							System.out.println("准备导入维保截止时间了。。。。。。。。");						
							spe.setSpeMaintainDeadLine(StrToDate(ect.getCellFormatValue(row.getCell((short) 24))));			//维保截止时间				
							System.out.println("ooooooooooooookkkkkkkkkkkkkkkkkkkkkk");
							//生成条形码            参数的含义：1.excel的第6列为获得日期，2.设备类别， 3.每年设备的流水号
							String barcode = barInfo.getCodeNum(ect.getCellFormatValue(row.getCell((short)20)), 1,
									barInfo.getSpeType(row.getCell((short) 8).getStringCellValue()), 
									barInfo.getId(BarcodeInfo.speTableName, StrToDate(ect.getCellFormatValue(row.getCell((short) 20)))));
							spe.setSpeBarCode(barcode);						//条形码
							System.out.println("条码得到了。。。。。。。。。。");
							spe.setSpeRemark(ect.getCellFormatValue(row.getCell((short) 26)));				//备注					
							spe.setLoginUserName(user.getUserName());										//更新人
							spe.setSpeUpdTime(sdf.format(new Date()));										//更新时间

							barInfo.createBarcode(barcode, ect.getCellFormatValue(row.getCell((short) 8)));			//根据条码和设备类型 生成条码图片。
							System.out.println("条码生成好了。。。。。。");
							
							spe.setSpeAssState(ect.getCellFormatValue(row.getCell((short)27)));				//资产状态（新增）
							spe.setSpeFixedTime(ect.getCellFormatValue(row.getCell((short)28)));			//转固时间（新增）
							spe.setSpeScrapTime(ect.getCellFormatValue(row.getCell((short)29)));			//报废时间（新增）
							
							
							hibernateTemplate.save(spe);		//保存到数据库	
							succNum++;							//记录成功导入数据的条数

						} else {	
							repeatNum++;			//重复的条数
							System.out.println("你导入的第"+i+"有重复呢。。。。。。。。。。。。。");
							ect.creatExcel(i, colNums, bookWorkbook, sheet, sheet1, rowStart++);			//把当前重复的行写到excel中
						}
					}else {			//序列号和时间为空或者时间格式不对
						System.out.println("你导入的第"+i+"行数据不对啊");			
						ect.creatExcel(i, colNums, bookWorkbook, sheet, sheet1, rowStart++);				//把当前不对的行写入excel
					}
				}
			}else {
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
			System.out.println("找不到文件！");
			//		e.printStackTrace();
			return null;
		}
	}	

	/*//生成excel 用来保存未导入成功的数据
	public String creatExcel(Integer rowNum,HSSFWorkbook bookWorkbook, HSSFSheet shet, HSSFSheet sheet, int rowStart) throws IOException {
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
		try {
			FileOutputStream outputStream;
			try {
				outputStream = new FileOutputStream(request.getSession().getServletContext().getRealPath("/")+"/Generatefile//DataError.xls");//同上
				bookWorkbook.write(outputStream);
				outputStream.flush();
				outputStream.close();
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
	 */
	/*

	//导入
	public String importSpe() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		String path = req.getRealPath("/upload");				//获取导入文件的路径
		FileInputStream is = new FileInputStream(file);				//获取导入文件

		OutputStream os = new FileOutputStream(new File(path, fileFileName));			
		byte[] buffer = new byte[8192];
		int len = 0;
		while((len=is.read(buffer)) != -1){
			os.write(buffer, 0, len);
		}
		os.close();
		is.close();

		File ff = new File(path, fileFileName);					//得到导入文件流

		Workbook wb = null;			

		int repeatNum = 0;			//保存重复数量	
		try {
			//	is2 = new FileInputStream("d:\\444.xls");
			FileInputStream is2 =  new FileInputStream(ff);			//文件流
			System.out.println("要导入的文件流是>>>>>>>>>>>>>>"+is2);
			System.out.println("要导入的文件流是>>>>>>>>>>>>>>"+is);
			wb = WorkbookFactory.create(is);					//这种方式excel 03/07/10都可以处理
			int sheetCount = wb.getNumberOfSheets();			//获取Sheet的数量
			System.out.println("sheet有"+sheetCount+"个。。。。。");

			//遍历每个sheet
			//			for(int s = 0; s < sheetCount; s++){
			Sheet sheet = wb.getSheetAt(0);						//得到excel中第一个sheet
			Row row = sheet.getRow(0);							//得到excel中第一行
			System.out.println("第一行（表头）是："+row.getCell(0).getStringCellValue());			//得到excel中第一行一列的值即表头
			int rowNums = sheet.getPhysicalNumberOfRows();			//获取总行数
			System.out.println("得到的excel总行数是>>>>>>>>>>"+rowNums);

			String isDupli = null;
			//遍历每一行
			for(int r = 2; r < rowNums; r++){						//从第3行开始，因为每张表的前两行分别是表头和各列的列名
				row = sheet.getRow(r);
				//		int cellCount = row.getPhysicalNumberOfCells();		//获取总列数

				//判断第14列(序列号)中的值是否与数据表中的值有重复
				isDupli = speService.isSpeDupli((ect.getXCellFormatValue(row.getCell((short) 14))));			

				System.out.println("有没有重复？"+isDupli+".......");
				//判断待增加的数据中和数据表中是否重复（主要看speSeqNum是否相同），若重复就不进行增加（但是可以将相同的数据的 更新人，更新时间 字段进行更新）
				if(isDupli == null){		//没有重复的
					System.out.println("正在往数据库中写。。。");
					//生成条形码            参数的含义：1.excel的第6列为获得日期，2.设备类别， 3.每年设备的流水号
					String barcode = barInfo.getCodeNum(ect.getXCellFormatValue(row.getCell((short)20)), 1,
							barInfo.getSpeType(row.getCell((short) 8).getStringCellValue()), 
							barInfo.getId(BarcodeInfo.speTableName, StrToDate(ect.getXCellFormatValue(row.getCell((short) 20)))));
					System.out.println("/////////////////////////////");
					//保存到数据库中      
					spe.setSpeDevName(ect.getXCellFormatValue(row.getCell((short) 1)));				//设备名称				
					spe.setSpeOwnSystem(ect.getXCellFormatValue(row.getCell((short) 2)));			//所属系统				
					spe.setSpeState(ect.getXCellFormatValue(row.getCell((short) 3)));				//状态
					spe.setSpeProOwn(ect.getXCellFormatValue(row.getCell((short) 4)));				//资产所属
					spe.setSpeAssetsManaDepart(ect.getXCellFormatValue(row.getCell((short) 5)));		//固定资产管理部门
					spe.setSpeVersion(ect.getXCellFormatValue(row.getCell((short) 6)));				//型号
					spe.setSpeDevFactor(ect.getXCellFormatValue(row.getCell((short) 7)));			//设备厂商
					spe.setSpeDevType(ect.getXCellFormatValue(row.getCell((short) 8)));				//设备类别
					spe.setSpeDevRoom(ect.getXCellFormatValue(row.getCell((short) 9)));				//所属机房
					spe.setSpeDevRoomFrame(ect.getXCellFormatValue(row.getCell((short) 10)));		//所属机柜
					spe.setSpeUseDepart(ect.getXCellFormatValue(row.getCell((short) 11)));			//使用部门
					spe.setSpeUser(ect.getXCellFormatValue(row.getCell((short) 12)));				//使用人
					spe.setSpeManaDepart(ect.getXCellFormatValue(row.getCell((short) 13)));			//实物管理部门
					spe.setSpeSeqNum(ect.getXCellFormatValue(row.getCell((short) 14)));				//序列号
					spe.setSpeOwnNet(ect.getXCellFormatValue(row.getCell((short) 15)));				//所属网络
					spe.setSpeMasterIP(ect.getXCellFormatValue(row.getCell((short) 16)));			//主IP地址
					spe.setSpeSlaveIP(ect.getXCellFormatValue(row.getCell((short) 17)));				//普通IP地址
					spe.setSpeProject(ect.getXCellFormatValue(row.getCell((short) 18)));				//隶属工程
					spe.setSpeBuyCont(ect.getXCellFormatValue(row.getCell((short) 19)));				//采购合同
					System.out.println("准备导入到货验收时间了。。。。。。。。");
					//		spe.setSpeArrAcceptTime(row.getCell((short) 20).getDateCellValue());			//到货验收时间
					spe.setSpeArrAcceptTime(StrToDate(ect.getXCellFormatValue(row.getCell((short) 20))));			//到货验收时间
					spe.setSpeAssertsNum(ect.getXCellFormatValue(row.getCell((short) 21)));			//固定资产代号
					spe.setSpeMaintainCont(ect.getXCellFormatValue(row.getCell((short) 22)));		//维保合同
					spe.setSpeMaintainFactor(ect.getXCellFormatValue(row.getCell((short) 23)));		//维保厂商
					System.out.println("准备导入维保截止时间了。。。。。。。。");						
					spe.setSpeMaintainDeadLine(StrToDate(ect.getXCellFormatValue(row.getCell((short) 24))));			//维保截止时间				
					System.out.println("ooooooooooooookkkkkkkkkkkkkkkkkkkkkk");

					spe.setSpeBarCode(barcode);						//条形码
					System.out.println("?????????????????????????");
					spe.setSpeRemark(ect.getXCellFormatValue(row.getCell((short) 26)));				//备注
					System.out.println("..................");
					spe.setLoginUserName(user.getUserName());										//更新人
					System.out.println("..................");
					spe.setSpeUpdTime(sdf.format(new Date()));										//更新时间

					barInfo.createBarcode(barcode, ect.getXCellFormatValue(row.getCell((short) 8)));			//根据条码和设备类型 生成条码图片。
					hibernateTemplate.save(spe);		//保存到数据库	

				} else {	//有重复的 
					repeatNum++;
					System.out.println(isDupli+"行的数据重复了");	
				}	
			}
			if(repeatNum ==0){		
				HttpServletResponse response  = ServletActionContext.getResponse();
				PrintWriter out = response.getWriter();
				out.print(-1);		//没有重复，全部导入成功
				out.flush();
				out.close();
			}else {
				HttpServletResponse response  = ServletActionContext.getResponse();
				PrintWriter out = response.getWriter();
				out.print((rowNums-repeatNum-1));			//有重复，成功导入的个数
				out.flush();
				out.close();
			}
			return null;

			//				}					


		} catch (IOException e) {
			//	e.printStackTrace();
			System.out.println("版本不对");
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

			return null;
		}

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

	//生成条码： 根据speId获取条码，从而得到条码图片
	public String findBarcode() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();

		String speId = request.getParameter("speId");
		SpePro spePro = speService.findById(speId);
		String speBarCode = spePro.getSpeBarCode();
		String spe = gson.toJson(speBarCode);

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(spe);
		//	return "printBarcode";
		return null;
	}

	@Test
	public   String exportExcel() throws Exception //用于数据维护，导出表格的全部
	{

		//HttpServletRequest request = ServletActionContext.getRequest();
		String title="专用资产信息表";
		String[] rowName={"序号","设备名称","所属系统","状态","资产所属","固定资产管理部门",
				"型号","设备厂商","设备类别","所属机房","所在机柜","使用部门","使用人","实物管理部门",
				"序列号","所属网络","主IP地址","普通IP地址","隶属工程","采购合同","到货验收时间","固定资产代号",
				"维保合同","维保厂商","维保截止时间","条码","备注","更新人","更新时间","资产状态","转固时间","报废时间"};
		HSSFWorkbook bookWorkbook = new HSSFWorkbook();// 创建excel文件
		HSSFSheet sheet = bookWorkbook.createSheet("0");
		HSSFCell cellTitle,cell;
		HSSFRow rowTitle,row;
		HSSFCellStyle styletitle = ect.setTableHeaderFormat(bookWorkbook);//设置表头的格式

		int rowinttitle=0;
		rowTitle = sheet.createRow((short) rowinttitle);

		cellTitle = rowTitle.createCell((short) 0);
		cellTitle.setEncoding(HSSFCell.ENCODING_UTF_16);					// 设置值的编码格式，确保不会出现乱码
		cellTitle.setCellValue(title);
		cellTitle.setCellStyle(styletitle);
		sheet.addMergedRegion(new Region(rowinttitle, (short) 0, rowinttitle,(short) 31));		//合并第一行

		HSSFCellStyle style = ect.setColumnDataStyle(bookWorkbook);			//设置表头内容的格式
		int rowint = 1;
		int titlerow1 = rowint++;
		row = sheet.createRow((short) titlerow1);
		for (int i = 0; i < rowName.length; i++) {
			cell = row.createCell((short) i);	
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);						// 设置值的编码格式，确保不会出现乱码
			cell.setCellValue(rowName[i]);
			cell.setCellStyle(style);
		}
		// 设置每列的宽度
		for(int k=0;k<32;k++)												//一共29列
		{
			sheet.setColumnWidth((short) k++, (short) 4200);
		}
		HSSFCellStyle styleInfo = ect.setColumnDataStyle(bookWorkbook);		//设置单元格的格式
		int rowintInfo = 2;
		//		int count=0;
		List<SpePro> list=speDao.findAll();									//找到全部的内容
		for(int i=0;i<speDao.findAll().size();i++)
		{
			row=sheet.createRow(rowintInfo++);
			int kk =0;
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeId());						//获取ID号
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeDevName());					//设备名称
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeOwnSystem());				//所属系统
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeState());					//状态
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeProOwn());					//资产所属
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeAssetsManaDepart());		//固定资产管理部门
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeVersion());					//型号
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeDevFactor());				//设备厂商
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeDevType());					//设备类型
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeDevRoom());					//所属机房
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeDevRoomFrame());			//所属机柜
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeUseDepart());				//使用部门	
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeUser());					//使用人
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeManaDepart());				//实物管理部门
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeSeqNum());					//序列号
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeOwnNet());					//所属网络
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeMasterIP());				//主IP地址
			kk++;	

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeSlaveIP());					//普通IP地址
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeProject());					//隶属工程
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeBuyCont());					//采购合同
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(sdf.format(list.get(i).getSpeArrAcceptTime()));			//到货验收时间
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeAssertsNum());				//固定资产代号
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeMaintainCont());			//维保合同
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeMaintainFactor());			//维保厂商
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);				
			cell.setCellValue(sdf.format(list.get(i).getSpeMaintainDeadLine()));
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeBarCode());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeRemark());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLoginUserName());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeUpdTime());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeAssState());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeFixedTime());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeScrapTime());
			
			System.out.println(kk);
			
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String filename=null;//作为判断条件，判断表是否导出成功
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
				outputStream = new FileOutputStream(request.getSession().getServletContext().getRealPath("/")+"/Generatefile//Special assets.xls");
				bookWorkbook.write(outputStream);
				outputStream.flush();
				outputStream.close();
				filename="Special assets.xls";
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
	public  String exportExcelByConditions() throws Exception //导出当前页，未注释的部分参考上一个函数
	{	
		HttpServletRequest request = ServletActionContext.getRequest();
		String str1=request.getParameter("data");//接收前台发送过来的id号

		JSONObject jsonArr = JSONObject.fromObject(str1);


		//		String[] str=new String[7];
		int[] arr=new int[1];
		arr[0]=1;
		String title="专用资产信息表";
		String[] rowName={"序号","设备名称","所属系统","状态","资产所属","固定资产管理部门",
				"型号","设备厂商","设备类别","所属机房","所在机柜","使用部门","使用人","实物管理部门",
				"序列号","所属网络","主IP地址","普通IP地址","隶属工程","采购合同","到货验收时间","固定资产代号",
				"维保合同","维保厂商","维保截止时间","条码","备注","更新人","更新时间","资产状态","转固时间","报废时间"};
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
				(short) 31));//合并第一行

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
		for(int k=0;k<32;k++)//一共16列
		{
			sheet.setColumnWidth((short) k, (short) 4200);
		}
		HSSFCellStyle styleInfo = ect.setColumnDataStyle(bookWorkbook);
		int rowintInfo = 2;
		//		int count=0;

		List<String> speIds=(List<String>)jsonArr.getJSONArray("speIds");
		/*for (int i = 0; i < speIds.size(); i++) {
				System.out.println(speIds.get(i));
			}*/


		List<SpePro> list1=new ArrayList<SpePro>();//将数据转换为list格式
		for(int i=0;i<speIds.size();i++)//遍历id，找到所有的数据
		{
			list1.add(speDao.findById(speIds.get(i)));;
		}
		for(int i=0;i<list1.size();i++)
		{
			row=sheet.createRow(rowintInfo++);
			int kk =0;
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeId());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeDevName());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeOwnSystem());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeState());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeProOwn());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeAssetsManaDepart());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeVersion());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeDevFactor());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeDevType());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeDevRoom());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeDevRoomFrame());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeUseDepart());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeUser());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeManaDepart());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeSeqNum());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeOwnNet());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeMasterIP());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeSlaveIP());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeProject());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeBuyCont());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(sdf.format(list1.get(i).getSpeArrAcceptTime()));
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeAssertsNum());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeMaintainCont());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeMaintainFactor());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(sdf.format(list1.get(i).getSpeMaintainDeadLine()));
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeBarCode());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeRemark());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getLoginUserName());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeUpdTime());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeAssState());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeFixedTime());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeScrapTime());
		}
		//Boolean succ = false;
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
				outputStream = new FileOutputStream(request.getSession().getServletContext().getRealPath("/")+"/Generatefile//Special asset table export the current page.xls");
				bookWorkbook.write(outputStream);
				outputStream.flush();
				outputStream.close();
				filename="Special asset table export the current page.xls";
				//return "导出成功";
			} catch (FileNotFoundException e) {
				System.err.println("获取不到位置");
				e.printStackTrace();
				//return "获取不到位置，导出失败";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//return "导出失败";
			}
		} finally {
		}
		Gson gson = new Gson();
		String success = gson.toJson(filename);
		ServletActionContext.getResponse().getWriter().print(success);

		return null;
	}

	public String exportAllExcelByConditions() throws Exception//导出全部页，未注释的部分参考上一个函数
	{

		HttpServletRequest request = ServletActionContext.getRequest();


		String[] str=new String[4];
		str[0]=request.getParameter("speDevName");//用于接收前台查询发送过来的查询条件
		str[1]=request.getParameter("speSeqNum");
		str[2]=request.getParameter("speBarCode");
		str[3]=request.getParameter("speUser");

		//		str[4]=request.getParameter("speDevName1");//用于接收前台筛选发送过来的筛选条件，因为没有实现功能，所以注释掉

		//		str[5]=request.getParameter("speDevRoom1");
		//		str[6]=request.getParameter("speDevRoomFrame1");
		//		str[7]=request.getParameter("speUser1");
		//		str[8]=request.getParameter("speState1");



		String title="专用资产信息表";
		String[] rowName={"序号","设备名称","所属系统","状态","资产所属","固定资产管理部门",
				"型号","设备厂商","设备类别","所属机房","所在机柜","使用部门","使用人","实物管理部门",
				"序列号","所属网络","主IP地址","普通IP地址","隶属工程","采购合同","到货验收时间","固定资产代号",
				"维保合同","维保厂商","维保截止时间","条码","备注","更新人","更新时间","资产状态","转固时间","报废时间"};
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
				(short) 31));//合并第一行

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
		for(int k=0;k<32;k++)//一共16列
		{
			sheet.setColumnWidth((short) k, (short) 4200);
		}
		HSSFCellStyle styleInfo = ect.setColumnDataStyle(bookWorkbook);
		int rowintInfo = 2;
		//			int count=0;
		Conditions conditions=new Conditions();
		conditions.addCondition("speDevName", str[0], Operator.LIKE);
		conditions.addCondition("speSeqNum", str[1], Operator.LIKE);
		conditions.addCondition("speBarCode", str[2], Operator.LIKE);				
		conditions.addCondition("speUser", str[3], Operator.LIKE);

		/*conditions.addCondition("speDevName", str[4], Operator.EQUAL);
					conditions.addCondition("speDevRoom", str[5], Operator.EQUAL);
					conditions.addCondition("speDevRoomFrame", str[6], Operator.EQUAL);
					conditions.addCondition("speUser", str[7], Operator.EQUAL);
					conditions.addCondition("speState", str[8], Operator.EQUAL);*/

		List<SpePro> list=speDao.findByConditions(conditions);//按条件查找到所有的信息
		System.out.println(list.size());
		for(int i=0;i<list.size();i++)
		{
			row=sheet.createRow(rowintInfo++);
			int kk =0;
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeId());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeDevName());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeOwnSystem());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeState());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeProOwn());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeAssetsManaDepart());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeVersion());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeDevFactor());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeDevType());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeDevRoom());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeDevRoomFrame());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeUseDepart());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeUser());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeManaDepart());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeSeqNum());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeOwnNet());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeMasterIP());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeSlaveIP());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeProject());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeBuyCont());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(sdf.format(list.get(i).getSpeArrAcceptTime()));
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeAssertsNum());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeMaintainCont());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeMaintainFactor());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(sdf.format(list.get(i).getSpeMaintainDeadLine()));
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeBarCode());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeRemark());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLoginUserName());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeUpdTime());
			
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeAssState());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeFixedTime());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getSpeScrapTime());
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
				outputStream = new FileOutputStream(request.getSession().getServletContext().getRealPath("/")+"/Generatefile//Special asset tables are exported by condition.xls");
				bookWorkbook.write(outputStream);
				outputStream.flush();
				outputStream.close();
				filename="Special asset tables are exported by condition.xls";
			} catch (FileNotFoundException e) {
				System.err.println("获取不到位置");
				e.printStackTrace();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		} finally {
		}
		Gson gson = new Gson();
		String success = gson.toJson(filename);
		ServletActionContext.getResponse().getWriter().print(success);

		return null;
	}



	@Test
	public  String exportpartExcelByConditions() throws Exception //批量导出，未注释的部分参考导出当前页
	{	
		HttpServletRequest request = ServletActionContext.getRequest();
		String str1=request.getParameter("data");

		JSONObject jsonArr = JSONObject.fromObject(str1);


		//	String[] str=new String[7];
		int[] arr=new int[1];
		arr[0]=1;
		String title="专用资产信息表";
		String[] rowName={"序号","设备名称","所属系统","状态","资产所属","固定资产管理部门",
				"型号","设备厂商","设备类别","所属机房","所在机柜","使用部门","使用人","实物管理部门",
				"序列号","所属网络","主IP地址","普通IP地址","隶属工程","采购合同","到货验收时间","固定资产代号",
				"维保合同","维保厂商","维保截止时间","条码","备注","更新人","更新时间","资产状态","转固时间","报废时间"};
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
				(short) 31));//合并第一行

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
		for(int k=0;k<32;k++)//一共16列
		{
			sheet.setColumnWidth((short) k, (short) 4200);
		}
		HSSFCellStyle styleInfo = ect.setColumnDataStyle(bookWorkbook);
		int rowintInfo = 2;
		//		int count=0;

		List<String> speIds=(List<String>)jsonArr.getJSONArray("speIds");
		/*for (int i = 0; i < speIds.size(); i++) {
				System.out.println(speIds.get(i));
			}*/


		List<SpePro> list1=new ArrayList<SpePro>();
		for(int i=0;i<speIds.size();i++)
		{
			list1.add(speDao.findById(speIds.get(i)));;
		}
		for(int i=0;i<list1.size();i++)
		{
			row=sheet.createRow(rowintInfo++);
			int kk =0;
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeId());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeDevName());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeOwnSystem());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeState());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeProOwn());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeAssetsManaDepart());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeVersion());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeDevFactor());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeDevType());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeDevRoom());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeDevRoomFrame());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeUseDepart());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeUser());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeManaDepart());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeSeqNum());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeOwnNet());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeMasterIP());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeSlaveIP());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeProject());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeBuyCont());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(sdf.format(list1.get(i).getSpeArrAcceptTime()));
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeAssertsNum());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeMaintainCont());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeMaintainFactor());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(sdf.format(list1.get(i).getSpeMaintainDeadLine()));
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeBarCode());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeRemark());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getLoginUserName());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeUpdTime());
			
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeAssState());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeFixedTime());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getSpeScrapTime());
		}
		//Boolean succ = false;
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
				outputStream = new FileOutputStream(request.getSession().getServletContext().getRealPath("/")+"/Generatefile//Special asset list.xls");
				bookWorkbook.write(outputStream);
				outputStream.flush();
				outputStream.close();
				filename="Special asset list.xls";
				//return "导出成功";
			} catch (FileNotFoundException e) {
				System.err.println("获取不到位置");
				e.printStackTrace();
				//return "获取不到位置，导出失败";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//return "导出失败";
			}
		} finally {
		}
		Gson gson = new Gson();
		String success = gson.toJson(filename);
		ServletActionContext.getResponse().getWriter().print(success);

		return null;
	}

	public String readExcelContent(InputStream is) throws Exception {//对要导入的表格进行读取

		String succ=null;//作为判断是否读取成功的判断条件
		List<SpePro> list=new ArrayList<SpePro>();
		list=speDao.findAll();


		//		 SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
		//String str = "";
		try {
			fsimport = new POIFSFileSystem(is);
			wbimport = new HSSFWorkbook(fsimport);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheetimport = wbimport.getSheetAt(0);
		// 得到总行数
		int rowNum  = sheetimport.getLastRowNum();
		rowimport   = sheetimport.getRow(1);
		int colNum  = rowimport.getPhysicalNumberOfCells();

		System.out.println("导入的excel有"+colNum+"列。。。。。。。。。。。。。");
		// 正文内容应该从第二行开始,第一行为表头的标题
		if(colNum==32)
			//getDao.deleteAll(list);
			// hibernateTemplate.save(excel);
		{
			for (int i = 2; i <= rowNum; i++) {
				rowimport = sheetimport.getRow(i);
				//  System.out.println(getCellFormatValue(rowimport.getCell((short) 0))+">>>>>>>>>>>>>>>>");
				spe.setSpeId((ect.getCellFormatValue(rowimport.getCell((short) 0))));				//设备名称
				spe.setSpeDevName(ect.getCellFormatValue(rowimport.getCell((short) 1)));				//设备名称
				spe.setSpeOwnSystem(ect.getCellFormatValue(rowimport.getCell((short) 2)));			//所属系统
				spe.setSpeState(ect.getCellFormatValue(rowimport.getCell((short) 3)));				//状态
				spe.setSpeProOwn(ect.getCellFormatValue(rowimport.getCell((short) 4)));				//资产所属
				spe.setSpeAssetsManaDepart(ect.getCellFormatValue(rowimport.getCell((short) 5)));	//固定资产管理部门
				spe.setSpeVersion(ect.getCellFormatValue(rowimport.getCell((short) 6)));				//型号
				spe.setSpeDevFactor(ect.getCellFormatValue(rowimport.getCell((short) 7)));			//设备厂商
				spe.setSpeDevType(ect.getCellFormatValue(rowimport.getCell((short) 8)));				//设备类别
				spe.setSpeDevRoom(ect.getCellFormatValue(rowimport.getCell((short) 9)));				//所属机房
				spe.setSpeDevRoomFrame(ect.getCellFormatValue(rowimport.getCell((short) 10)));		//所属机柜
				spe.setSpeUseDepart(ect.getCellFormatValue(rowimport.getCell((short) 11)));			//使用部门
				spe.setSpeUser(ect.getCellFormatValue(rowimport.getCell((short) 12)));				//使用人
				spe.setSpeManaDepart(ect.getCellFormatValue(rowimport.getCell((short) 13)));			//实物管理部门
				spe.setSpeSeqNum(ect.getCellFormatValue(rowimport.getCell((short) 14)));				//序列号
				spe.setSpeOwnNet(ect.getCellFormatValue(rowimport.getCell((short) 15)));				//所属网络
				spe.setSpeMasterIP(ect.getCellFormatValue(rowimport.getCell((short) 16)));			//主IP地址
				spe.setSpeSlaveIP(ect.getCellFormatValue(rowimport.getCell((short) 17)));			//普通IP地址
				spe.setSpeProject(ect.getCellFormatValue(rowimport.getCell((short) 18)));			//隶属工程
				spe.setSpeBuyCont(ect.getCellFormatValue(rowimport.getCell((short) 19)));			//采购合同
				spe.setSpeArrAcceptTime(StrToDate(ect.getCellFormatValue(rowimport.getCell((short) 20))));		//到货验收时间
				spe.setSpeAssertsNum(ect.getCellFormatValue(rowimport.getCell((short) 21)));			//固定资产代号
				spe.setSpeMaintainCont(ect.getCellFormatValue(rowimport.getCell((short) 22)));		//维保合同
				spe.setSpeMaintainFactor(ect.getCellFormatValue(rowimport.getCell((short) 23)));		//维保厂商
				spe.setSpeMaintainDeadLine(StrToDate(ect.getCellFormatValue(rowimport.getCell((short) 24))));	//维保截止时间				
				spe.setSpeBarCode(ect.getCellFormatValue(rowimport.getCell((short) 25)));						//条形码
				spe.setSpeRemark(ect.getCellFormatValue(rowimport.getCell((short) 26)));				//备注
				spe.setLoginUserName(ect.getCellFormatValue(rowimport.getCell((short) 27)));			//使用者ID
				//text.setSpeUpdTime(sdf.format(new Date()));									
				spe.setSpeUpdTime(ect.getCellFormatValue(rowimport.getCell((short) 28)));//操作时间
				
				spe.setSpeAssState(ect.getCellFormatValue(rowimport.getCell((short)29)));				//资产状态（新增）
				spe.setSpeFixedTime(ect.getCellFormatValue(rowimport.getCell((short)30)));			//转固时间（新增）
				spe.setSpeScrapTime(ect.getCellFormatValue(rowimport.getCell((short)31)));			//报废时间（新增）
				hibernateTemplate.save(spe);


			}
			succ="1";
			return succ;//读取成功
		}
		else
		{
			System.out.println("导入失败");
			return succ;
		}

	}

	public String   excelToMysql() throws Exception {//将表格导入到数据库

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
		List<SpePro> list1=speDao.findAll();





		if(readExcelContent(is2)=="1")//导入成功，并给前台发送-1
		{

			HttpServletResponse response  = ServletActionContext.getResponse();
			PrintWriter out = response.getWriter();
			out.print(-1);
			out.flush();
			out.close();
			try {
				speDao.deleteAll(list1);//在导入之前将数据库对应的表格进行清空
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			HttpServletResponse response  = ServletActionContext.getResponse();
			PrintWriter out = response.getWriter();
			out.print(1);
			out.flush();
			out.close();
		}

		return null;	

	}
	public Date StrToDate(String str) throws Exception {

		SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar calendar = new GregorianCalendar();

		Date date = null;
		calendar.setTime(form.parse(str));
		calendar.add(Calendar.DAY_OF_YEAR, 1);

		date = calendar.getTime();

		return date;
	}


	public String searchOutChart() throws IOException, Exception{
		Conditions conditions  = new Conditions();
		//		DataTablesPage<SpePro>   page = new DataTablesPage<SpePro>() ;
		HttpServletRequest request= ServletActionContext.getRequest();

		String  speArrAcceptTime  = request.getParameter("speArrAcceptTime");

		Date date = StrToDate(speArrAcceptTime);

		System.out.println(date);
		System.out.println(speArrAcceptTime+"xxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		conditions.addCondition("speArrAcceptTime", date, Operator.LESS_EQUAL);


		String iDisplayLength = request.getParameter("iDisplayLength");
		int iDisplayLength1 = Integer.valueOf(iDisplayLength);
		System.out.println(iDisplayLength1+"---------");
		String nowPage = request.getParameter("nowPage");
		int nowPage1 = Integer.valueOf(nowPage);
		System.out.println("---------------"+nowPage1);



		DataTablesPage<SpePro>  pagaData = speService. findAll(conditions, iDisplayLength1,nowPage1) ;

		Gson gson = new Gson();
		String data = gson.toJson(pagaData);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out =response.getWriter();
		out.print(data);

		out.flush();
		out.close();

		return null;

	}



}
