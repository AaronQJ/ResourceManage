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

import com.bussniess.dao.impl.ProBakDaoImpl;
import com.bussniess.dao.util.Conditions;
import com.bussniess.dao.util.Conditions.Operator;
import com.bussniess.domain.ProBak;
import com.bussniess.domain.Users;
import com.bussniess.service.ProBakService;
import com.bussniess.util.DataTablesPage;
import com.bussniess.util.ExcelCellType;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class ManagerProBakAction extends ActionSupport  implements ModelDriven<ProBak> {

	private static final long serialVersionUID = 1L;
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
	HibernateTemplate hibernateTemplate = (HibernateTemplate) applicationContext.getBean("hibernateTemplate");
	ProBakDaoImpl bakDao = (ProBakDaoImpl) applicationContext.getBean("bakDao");
	Users user = (Users)ServletActionContext.getRequest().getSession().getAttribute("user");

	ExcelCellType<ProBak> ect =  new ExcelCellType<ProBak>();

	private File file;
	private String fileFileName;				//文件名
	private String fileContentType;				//文件的类型

	private POIFSFileSystem fsimport;
	private HSSFWorkbook wbimport;
	private HSSFSheet sheetimport;
	private HSSFRow rowimport;


	private ProBak proBak = new ProBak();
	private ProBakService bakService;
	private static Logger log = Logger.getLogger(ManagerProBakAction.class);

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public ProBak getModel() {
		// TODO Auto-generated method stub
		return proBak;
	}


	public ProBak getProBak() {
		return proBak;
	}


	public void setProBak(ProBak proBak) {
		this.proBak = proBak;
	}


	public ProBakService getBakService() {
		return bakService;
	}


	public void setBakService(ProBakService bakService) {
		this.bakService = bakService;
	}

	public String add() throws IOException{
		

		proBak.setLoginUserName(user.getUserName());

		proBak.setBakUpdTime(sdf.format(new Date()));

		bakService.add(proBak);

		MDC.put("userName", user.getUserName());
		MDC.put("objId", proBak.getBakSeqNum());
		MDC.put("logType", "devRecord");
		MDC.put("objType", "备品备件");
		MDC.put("operType", "增加");

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

		System.out.println(proBak);

		out.flush();
		out.close();

		return null;
	}


	public String  send() throws IOException{

		HttpServletRequest request = ServletActionContext.getRequest();
		Conditions conditions  = new Conditions();
		String    bakDevType     =request.getParameter("bakDevType");
		String     bakDevRoom    =request.getParameter("bakDevRoom");
		String      bakDevFrame   =request.getParameter("bakDevFrame");
		String     bakManaDepart    =request.getParameter("bakManaDepart");
		String      bakUser   =request.getParameter("bakUser");
		String      bakUseDepart   =request.getParameter("bakUseDepart");
		String      bakState   =request.getParameter("bakState");

		conditions.addCondition("bakDevType", bakDevType, Operator.EQUAL);
		conditions.addCondition("bakDevRoom", bakDevRoom, Operator.EQUAL);
		conditions.addCondition("bakDevFrame", bakDevFrame, Operator.EQUAL);
		conditions.addCondition("bakManaDepart", bakManaDepart, Operator.EQUAL);
		conditions.addCondition("bakUseDepart", bakUseDepart, Operator.EQUAL);
		conditions.addCondition("bakUser", bakUser, Operator.EQUAL);
		conditions.addCondition("bakState", bakState, Operator.EQUAL);


		DataTablesPage<ProBak>   page = new DataTablesPage<ProBak>() ;

		String iDisplayLength = request.getParameter("iDisplayLength");
		int iDisplayLength1 = Integer.valueOf(iDisplayLength);
		System.out.println(iDisplayLength1+"---------");
		String nowPage = request.getParameter("nowPage");
		int nowPage1 = Integer.valueOf(nowPage);
		System.out.println("---------------"+nowPage1);
		System.out.println(page.getITotalRecords()+"--------------"+iDisplayLength1);
		DataTablesPage<ProBak>  pagaData = bakService. findAll(conditions, iDisplayLength1+10000,nowPage1) ;

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


		DataTablesPage<ProBak>   page = new DataTablesPage<ProBak>() ;
		HttpServletRequest request= ServletActionContext.getRequest();

		String bakSeqNum	 =  request.getParameter("bakSeqNum");
		String  bakDevType  = request.getParameter("bakDevType");
		String  bakUpdTime = request.getParameter("bakUpdTime");
		conditions.addCondition("bakSeqNum", bakSeqNum, Operator.LIKE);
		conditions.addCondition("bakDevType", bakDevType, Operator.LIKE);
		conditions.addCondition("bakUpdTime", bakUpdTime, Operator.LIKE);



		List<String> bakDevTypeList  =  new ArrayList<String>();
		List<String> bakDevRoomList  =  new ArrayList<String>();
		List<String> bakDevFrameList  =  new ArrayList<String>();
		List<String> bakManaDepartList  =  new ArrayList<String>();
		List<String> bakUseDepartList  =  new ArrayList<String>();
		List<String> bakUserList  =  new ArrayList<String>();

		List<ProBak> bakList = bakService.findByConditions(conditions);

		for(ProBak bak: bakList){
			bakDevTypeList.add(bak.getBakDevType());
		}
		Set<String> bakDevTypeSet = new HashSet<String>(bakDevTypeList);
		bakDevTypeList.clear();
		bakDevTypeList.addAll(bakDevTypeSet);


		for(ProBak bak: bakList){
			bakDevRoomList.add(bak.getBakDevRoom());
		}
		Set<String> bakDevRoomSet = new HashSet<String>(bakDevRoomList);
		bakDevRoomList.clear();
		bakDevRoomList.addAll(bakDevRoomSet);



		for(ProBak bak: bakList){
			bakDevFrameList.add(bak.getBakDevFrame());
		}
		Set<String> bakDevFrameSet = new HashSet<String>(bakDevFrameList);
		bakDevFrameList.clear();
		bakDevFrameList.addAll(bakDevFrameSet);



		for(ProBak bak: bakList){
			bakManaDepartList.add(bak.getBakManaDepart());
		}
		Set<String> bakManaDepartSet = new HashSet<String>(bakManaDepartList);
		bakManaDepartList.clear();
		bakManaDepartList.addAll(bakManaDepartSet);



		for(ProBak bak: bakList){
			bakUseDepartList.add(bak.getBakUseDepart());
		}
		Set<String> bakUseDepartSet = new HashSet<String>(bakUseDepartList);
		bakUseDepartList.clear();
		bakUseDepartList.addAll(bakUseDepartSet);


		for(ProBak bak: bakList){
			bakUserList.add(bak.getBakUser());
		}
		Set<String> bakUserSet = new HashSet<String>(bakUserList);
		bakUserList.clear();
		bakUserList.addAll(bakUserSet);

	
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("bakDevTypeList",bakDevTypeList );
		map.put("bakDevRoomList", bakDevRoomList);
		map.put("bakDevFrameList", bakDevFrameList);
		map.put("bakManaDepartList", bakManaDepartList);
		map.put("bakUseDepartList", bakUseDepartList);
		map.put("bakUserList", bakUserList);


		String iDisplayLength = request.getParameter("iDisplayLength");
		int iDisplayLength1 = Integer.valueOf(iDisplayLength);
		System.out.println(iDisplayLength1+"---------");
		String nowPage = request.getParameter("nowPage");
		int nowPage1 = Integer.valueOf(nowPage);
		System.out.println("---------------"+nowPage1);
		System.out.println(page.getITotalRecords()+"--------------"+iDisplayLength1);
		DataTablesPage<ProBak>  pagaData = bakService. findAll(conditions, iDisplayLength1,nowPage1) ;

		Gson gson = new Gson();
		map.put("pagaData",pagaData);
		String data = gson.toJson(map);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out =response.getWriter();
		
		System.out.println("dddddddddddddddddd_"+data);
		out.print(data);

		out.flush();
		out.close();

		return null;
	}




	public  String checkSeqNum()  throws IOException{
		Conditions conditions=new Conditions();
		String bakSeqNum = (String) ServletActionContext.getRequest().getAttribute("bakSeqNum");

		System.out.println(bakSeqNum);
		conditions.addCondition("bakSeqNum", bakSeqNum, Operator.EQUAL);

		boolean isRepeat =  bakService.checkSeqNum(conditions);

		Gson gson = new Gson();
		String seqRepeat =  gson.toJson(isRepeat);
		System.out.println(seqRepeat);
		ServletActionContext.getResponse().getWriter().print(seqRepeat);

		return null;
	}

	//
	public String  updateMore() throws Exception{
		HttpServletRequest  request = ServletActionContext.getRequest();
		Gson gson  = new Gson();
		request.setCharacterEncoding("utf-8");
		String jsonArr  =  request.getParameter("data");
		JSONObject jsonObject=JSONObject.fromObject(jsonArr);//json-lib中是jo=JSONObject.fromObject(t);

		System.out.println(jsonArr); 

		List<String> bakIds=(List<String>) jsonObject.getJSONArray("bakIds");

		String  bakState = (String) jsonObject.get("bakState");
		String  bakUser = (String) jsonObject.get("bakUser");
		String  bakDevRoom = (String) jsonObject.get("bakDevRoom");
		String  bakDevFrame= (String) jsonObject.get("bakDevFrame");

		//System.out.println(speIds.toString());

		//System.out.println("speState="+speState+"--speUser="+speUser+"--speDevRoom="+speDevRoom+"--speDevRoomFrame="+speDevRoomFrame);

		for (int i = 0; i < bakIds.size(); i++) {  
			//获取第i个数组元素  
			String bakId= bakIds.get(i);  
			//	   System.out.println(speid);
			ProBak proBak = bakService.findById(bakId);
			if(!bakState.equals("请选择")){
				if(!proBak.getBakState().equals(bakState)){
					MDC.put("objId", proBak.getBakSeqNum());
					MDC.put("userName", user.getUserName());
					MDC.put("logType", "devRecord");
					MDC.put("objType", "备品备件");
					MDC.put("operType", "修改");
					MDC.put("fieldUpdValue",bakState );
					MDC.put("fieldOriValue", proBak.getBakState());
					MDC.put("fieldName", "状态");
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
			if(!bakUser.equals("")){
				if(!proBak.getBakUser().equals(bakUser)){
					MDC.put("objId", proBak.getBakSeqNum());
					MDC.put("userName", user.getUserName());
					MDC.put("logType", "devRecord");
					MDC.put("objType", "备品备件");
					MDC.put("operType", "修改");
					MDC.put("fieldUpdValue",bakUser );
					MDC.put("fieldOriValue", proBak.getBakUser());
					MDC.put("fieldName", "使用人");
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
			if(!bakDevRoom.equals("")){
				if(!proBak.getBakDevRoom().equals(bakDevRoom)){
					MDC.put("objId", proBak.getBakSeqNum());
					MDC.put("userName", user.getUserName());
					MDC.put("logType", "devRecord");
					MDC.put("objType", "备品备件");
					MDC.put("operType", "修改");
					MDC.put("fieldUpdValue",bakDevRoom );
					MDC.put("fieldOriValue", proBak.getBakDevRoom());
					MDC.put("fieldName", "所属机房");
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
			if(!bakDevFrame.equals("")){
				if(!proBak.getBakDevFrame().equals(bakDevFrame)){
					MDC.put("objId", proBak.getBakSeqNum());
					MDC.put("userName", user.getUserName());
					MDC.put("logType", "devRecord");
					MDC.put("objType", "备品备件");
					MDC.put("operType", "修改");
					MDC.put("fieldUpdValue",bakDevFrame );
					MDC.put("fieldOriValue", proBak.getBakDevFrame());
					MDC.put("fieldName", "所在机柜");
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
			//       System.out.println(spePro);
			proBak.setBakState(bakState);
			proBak.setBakUser(bakUser);
			proBak.setBakDevRoom(bakDevRoom);
			proBak.setBakDevFrame(bakDevFrame);

			bakService.update(proBak);

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

		System.out.println(jsonArr); 

		List<String> bakIds=(List<String>) jsonObject.getJSONArray("bakIds");



		System.out.println(bakIds.toString());

		//System.out.println("speState="+speState+"--speUser="+speUser+"--speDevRoom="+speDevRoom+"--speDevRoomFrame="+speDevRoomFrame);
		Gson gson = new Gson();
		List<ProBak> bakList = new ArrayList<ProBak>();
		for (int i = 0; i < bakIds.size(); i++) {  
			//获取第i个数组元素  
			String bakid= bakIds.get(i);  
			System.out.println(bakIds);
			ProBak pro1 =  bakService.findById(bakid);
			MDC.put("objId", pro1.getBakSeqNum());
			MDC.put("userName", user.getUserName());
			MDC.put("logType", "devRecord");
			MDC.put("objType", "备品备件");
			MDC.put("operType", "删除");

			log.error("");
			MDC.remove("userName");
			MDC.remove("objId");
			MDC.remove("logType");
			MDC.remove("objType");
			MDC.remove("operType");
			MDC.remove("fieldName");
			MDC.remove("fieldOriValue");
			MDC.remove("fieldUpdValue");

			bakService.deleteById(bakid);

			ProBak pro =  bakService.findById(bakid);
			bakList.add(pro);
		}  

		String deleSucc;
		if(bakList!=null){
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
		bakService.deleteById(bakId);

		ProBak   proBak =bakService.findById(bakId);
		String deleSucc;
		if(proBak!=null){
			deleSucc=  gson.toJson("false");
		}
		deleSucc = gson.toJson("true");
		ServletActionContext.getResponse().getWriter().print(deleSucc);


		return null;
	}

	public String findOne() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();

		String bakId = request.getParameter("bakId");
		ProBak proBak = bakService.findById(bakId);

		String bak = gson.toJson(proBak);

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(bak);
		return null;

	}

	//单条修改
	public String updateOne() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();

		String bakId = request.getParameter("bakId");
		ProBak bak  = bakService.findById(bakId);
		System.out.println(bak.getBakMaintainDeadLine());
		System.out.println(proBak.getBakMaintainDeadLine());
		if(!proBak.getBakArrAcceptTime().equals("")){
			if(!bak.getBakArrAcceptTime().equals(proBak.getBakArrAcceptTime())){
				MDC.put("fieldUpdValue", proBak.getBakArrAcceptTime());
				MDC.put("fieldOriValue", bak.getBakArrAcceptTime());
				MDC.put("fieldName", "到货验收时间");
				MDC.put("logType", "devRecord");
				MDC.put("objType", "备品备件");
				MDC.put("operType", "修改");
				MDC.put("objId", bak.getBakSeqNum());
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
			}
		}
		if(!proBak.getBakBuyCont().equals("")){
			if(!bak.getBakBuyCont().equals(proBak.getBakBuyCont())){
				MDC.put("fieldUpdValue", proBak.getBakBuyCont());
				MDC.put("fieldOriValue", bak.getBakBuyCont());
				MDC.put("fieldName", "采购合同");
				MDC.put("logType", "devRecord");
				MDC.put("objType", "备品备件");
				MDC.put("operType", "修改");
				MDC.put("objId", bak.getBakSeqNum());
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
			}
		}
		if(!proBak.getBakDevFrame().equals("")){
			if(!bak.getBakDevFrame().equals(proBak.getBakDevFrame())){
				MDC.put("fieldUpdValue", proBak.getBakDevFrame());
				MDC.put("fieldOriValue", bak.getBakDevFrame());
				MDC.put("fieldName", "所在机柜");
				MDC.put("logType", "devRecord");
				MDC.put("objType", "备品备件");
				MDC.put("operType", "修改");
				MDC.put("objId", bak.getBakSeqNum());
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
			}
		}
		if(!proBak.getBakDevRoom().equals("")){
			if(!bak.getBakDevRoom().equals(proBak.getBakDevRoom())){
				MDC.put("fieldUpdValue", proBak.getBakDevRoom());
				MDC.put("fieldOriValue", bak.getBakDevRoom());
				MDC.put("fieldName", "所属机房");
				MDC.put("logType", "devRecord");
				MDC.put("objType", "备品备件");
				MDC.put("operType", "修改");
				MDC.put("objId", bak.getBakSeqNum());
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
			}
		}
		if(!proBak.getBakDevType().equals("")){
			if(!bak.getBakDevType().equals(proBak.getBakDevType())){
				MDC.put("fieldUpdValue", proBak.getBakDevType());
				MDC.put("fieldOriValue", bak.getBakDevType());
				MDC.put("fieldName", "设备类别");
				MDC.put("logType", "devRecord");
				MDC.put("objType", "备品备件");
				MDC.put("operType", "修改");
				MDC.put("objId", bak.getBakSeqNum());
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
			}
		}
		if(!proBak.getBakFactory().equals("")){
			if(!bak.getBakFactory().equals(proBak.getBakFactory())){
				MDC.put("fieldUpdValue", proBak.getBakFactory());
				MDC.put("fieldOriValue", bak.getBakFactory());
				MDC.put("fieldName", "设备厂商");
				MDC.put("logType", "devRecord");
				MDC.put("objType", "备品备件");
				MDC.put("operType", "修改");
				MDC.put("objId", bak.getBakSeqNum());
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
			}
		}
		if(!proBak.getBakForm().equals("")){
			if(!bak.getBakForm().equals(proBak.getBakForm())){
				MDC.put("fieldUpdValue", proBak.getBakForm());
				MDC.put("fieldOriValue", bak.getBakForm());
				MDC.put("fieldName", "实物形态");
				MDC.put("logType", "devRecord");
				MDC.put("objType", "备品备件");
				MDC.put("operType", "修改");
				MDC.put("objId", bak.getBakSeqNum());
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
			}
		}
		if(!proBak.getBakMaintainCont().equals("")){
			if(!bak.getBakMaintainCont().equals(proBak.getBakMaintainCont())){
				MDC.put("fieldUpdValue", proBak.getBakMaintainCont());
				MDC.put("fieldOriValue", bak.getBakMaintainCont());
				MDC.put("fieldName", "维保合同");
				MDC.put("logType", "devRecord");
				MDC.put("objType", "备品备件");
				MDC.put("operType", "修改");
				MDC.put("objId", bak.getBakSeqNum());
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
			}
		}
		if(!proBak.getBakMaintainDeadLine().equals("")){
			if(!bak.getBakMaintainDeadLine().equals(proBak.getBakMaintainDeadLine())){
				MDC.put("fieldUpdValue", proBak.getBakMaintainDeadLine());
				MDC.put("fieldOriValue", bak.getBakMaintainDeadLine());
				MDC.put("fieldName", "维保截止时间");
				MDC.put("logType", "devRecord");
				MDC.put("objType", "备品备件");
				MDC.put("operType", "修改");
				MDC.put("objId", bak.getBakSeqNum());
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
			}
		}
		if(!proBak.getBakMaintainFactor().equals("")){
			if(!bak.getBakMaintainFactor().equals(proBak.getBakMaintainFactor())){
				MDC.put("fieldUpdValue", proBak.getBakMaintainFactor());
				MDC.put("fieldOriValue", bak.getBakMaintainFactor());
				MDC.put("fieldName", "维保厂商");
				MDC.put("logType", "devRecord");
				MDC.put("objType", "备品备件");
				MDC.put("operType", "修改");
				MDC.put("objId", bak.getBakSeqNum());
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
			}
		}
		if(!proBak.getBakManaDepart().equals("")){
			if(!bak.getBakManaDepart().equals(proBak.getBakManaDepart())){
				MDC.put("fieldUpdValue", proBak.getBakManaDepart());
				MDC.put("fieldOriValue", bak.getBakManaDepart());
				MDC.put("fieldName", "实物管理部门");
				MDC.put("logType", "devRecord");
				MDC.put("objType", "备品备件");
				MDC.put("operType", "修改");
				MDC.put("objId", bak.getBakSeqNum());
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
			}
		}
		if(!proBak.getBakRecorder().equals("")){
			if(!bak.getBakRecorder().equals(proBak.getBakRecorder())){
				MDC.put("fieldUpdValue", proBak.getBakRecorder());
				MDC.put("fieldOriValue", bak.getBakRecorder());
				MDC.put("fieldName", "记录负责人");
				MDC.put("logType", "devRecord");
				MDC.put("objType", "备品备件");
				MDC.put("operType", "修改");
				MDC.put("objId", bak.getBakSeqNum());
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
			}
		}
		if(!proBak.getBakRemark().equals("")){
			if(!bak.getBakRemark().equals(proBak.getBakRemark())){
				MDC.put("fieldUpdValue", proBak.getBakRemark());
				MDC.put("fieldOriValue", bak.getBakRemark());
				MDC.put("fieldName", "备注");
				MDC.put("logType", "devRecord");
				MDC.put("objType", "备品备件");
				MDC.put("operType", "修改");
				MDC.put("objId", bak.getBakSeqNum());
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
			}
		}
		if(!proBak.getBakState().equals("")){
			if(!bak.getBakState().equals(proBak.getBakState())){
				MDC.put("fieldUpdValue", proBak.getBakState());
				MDC.put("fieldOriValue", bak.getBakState());
				MDC.put("fieldName", "状态");
				MDC.put("logType", "devRecord");
				MDC.put("objType", "备品备件");
				MDC.put("operType", "修改");
				MDC.put("objId", bak.getBakSeqNum());
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
			}
		}
		if(!proBak.getBakUseDepart().equals("")){
			if(!bak.getBakUseDepart().equals(proBak.getBakUseDepart())){
				MDC.put("fieldUpdValue", proBak.getBakUseDepart());
				MDC.put("fieldOriValue", bak.getBakUseDepart());
				MDC.put("fieldName", "使用部门");
				MDC.put("logType", "devRecord");
				MDC.put("objType", "备品备件");
				MDC.put("operType", "修改");
				MDC.put("objId", bak.getBakSeqNum());
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
			}
		}
		if(!proBak.getBakUser().equals("")){
			if(!bak.getBakUser().equals(proBak.getBakUser())){
				MDC.put("fieldUpdValue", proBak.getBakUser());
				MDC.put("fieldOriValue", bak.getBakUser());
				MDC.put("fieldName", "使用人");
				MDC.put("logType", "devRecord");
				MDC.put("objType", "备品备件");
				MDC.put("operType", "修改");
				MDC.put("objId", bak.getBakSeqNum());
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
			}
		}
		if(!proBak.getBakVersion().equals("")){
			if(!bak.getBakVersion().equals(proBak.getBakVersion())){
				MDC.put("fieldUpdValue", proBak.getBakVersion());
				MDC.put("fieldOriValue", bak.getBakVersion());
				MDC.put("fieldName", "型号");
				MDC.put("logType", "devRecord");
				MDC.put("objType", "备品备件");
				MDC.put("operType", "修改");
				MDC.put("objId", bak.getBakSeqNum());
				MDC.put("userName", user.getUserName());
				log.error("");
				MDC.remove("userName");
				MDC.remove("objId");
				MDC.remove("logType");
				MDC.remove("objType");
				MDC.remove("fieldName");
				MDC.remove("fieldOriValue");
				MDC.remove("fieldUpdValue");
			}
		}
		bak.setBakArrAcceptTime(proBak.getBakArrAcceptTime());
		bak.setBakBuyCont(proBak.getBakBuyCont());
		bak.setBakDevFrame(proBak.getBakDevFrame());
		bak.setBakDevRoom(proBak.getBakDevRoom());
		bak.setBakDevType(proBak.getBakDevType());
		bak.setBakFactory(proBak.getBakFactory());
		bak.setBakForm(proBak.getBakForm());
		bak.setBakMaintainCont(proBak.getBakMaintainCont());
		bak.setBakMaintainDeadLine(proBak.getBakMaintainDeadLine());
		bak.setBakMaintainFactor(proBak.getBakMaintainFactor());
		bak.setBakManaDepart(proBak.getBakManaDepart());
		bak.setBakRecorder(proBak.getBakRecorder());
		bak.setBakRemark(proBak.getBakRemark());
		bak.setBakState(proBak.getBakState());
		bak.setBakUseDepart(proBak.getBakUseDepart());
		bak.setBakUser(proBak.getBakUser());
		bak.setBakVersion(proBak.getBakVersion());


		bakService.add(bak);

		String success = gson.toJson("true");
		ServletActionContext.getResponse().getWriter().print(success);

		return null;

	}

	public String  sendChart() throws IOException{
		Gson gson = new Gson();
		List<Map<String,String>> typeNumList = new ArrayList<Map<String,String>>(); 
		Map<String,String>  typeNum = new HashMap<String,String>();
		Map<String,String> typeState = new HashMap<String,String>();

		Map<String,String> devNameMap = new HashMap<String,String>();
		Map<String,String>  devCountMap= new HashMap<String,String>();


		List<ProBak>  bakList = bakService.findAll();

		List<String>  typeList = new ArrayList<String>();
		for (ProBak bak : bakList) {
			typeList.add(bak.getBakDevType());
		}

		HashSet<String> typeSet  =   new  HashSet<String>(typeList); 
		typeList.clear(); 
		typeList.addAll(typeSet); 

		typeState.put("类型总数", String.valueOf(typeList.size()));
		System.out.println(typeList); 


		/*
		 * 设备类型
		 */

		for(int i=0;i<typeList.size();i++){
			Conditions conditions  = new Conditions();
			conditions.addCondition("bakDevType",typeList.get(i) , Operator.EQUAL);

			int count = bakService.findByCondition(conditions);

			devNameMap.put(String.valueOf(i), String.valueOf(typeList.get(i)));
			devCountMap.put(String.valueOf(i), String.valueOf(count));
		}

		/*
		 * 设备状态:
		 */
		//入库
		Conditions conditions3 = new Conditions();
		conditions3.addCondition("bakState", "入库", Operator.EQUAL);
		int DLYNum = bakService.findByCondition(conditions3);
		typeState.put("入库", String.valueOf(DLYNum));
		//出库
		Conditions conditions4 = new Conditions();
		conditions4.addCondition("bakState", "出库", Operator.EQUAL);
		int ZYNum = bakService.findByCondition(conditions4);
		typeState.put("出库", String.valueOf(ZYNum));
		//待报废
		Conditions conditions5 = new Conditions();
		conditions5.addCondition("bakState", "待报废", Operator.EQUAL);
		int DBFNum = bakService.findByCondition(conditions5);
		typeState.put("待报废", String.valueOf(DBFNum));

		//报废
		Conditions conditions6 = new Conditions();
		conditions6.addCondition("bakState", "报废", Operator.EQUAL);
		int BFNum = bakService.findByCondition(conditions6);
		typeState.put("报废",String.valueOf( BFNum));

		typeNumList.add(typeNum);
		typeNumList.add(typeState);
		typeNumList.add(devNameMap);
		typeNumList.add(devCountMap);


		String typeCount = gson.toJson(typeNumList);


		System.out.println(typeCount);

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(typeCount);

		return null;

	}




	//导入
	public String importBak() throws IOException {
		HttpServletRequest req = ServletActionContext.getRequest();
		String path = req.getRealPath("/upload");			//获取项目下的upload/路径
		InputStream is = new FileInputStream(file);			//从前端input中的name="file"获取文件输入流对象

		OutputStream os = new FileOutputStream(new File(path, fileFileName));
		byte[] buffer = new byte[8192];
		int len = 0;
		while((len=is.read(buffer)) != -1){
			os.write(buffer, 0, len);
		}
		os.close();
		is.close();
	
		File ff = new File(path, fileFileName);				//根据路径和获取的文件名来创建一个文件，
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		POIFSFileSystem fs;
		HSSFWorkbook wb=null;
		HSSFSheet sheet;
		HSSFRow row;

		String title="导入后的错误数据";
		String[] rowName={"序号","序列号","型号","设备厂商","设备类别","实物形态","状态","实物管理部门","使用部门",
				"使用人","所属机房","所在机柜","采购合同","到货验收时间","维保合同","维保厂商","维保截止时间","记录负责人",
				"备注"};
		HSSFWorkbook bookWorkbook = new HSSFWorkbook();						// 创建excel文件
		HSSFSheet sheet1 = bookWorkbook.createSheet("0");
		HSSFCell cellTitle,cell;
		HSSFRow rowTitle,row1;

		int rowStart = 2;			//每次调用生成新的excel时的参数，表示在新的excel待添加数据的行

		HSSFCellStyle styletitle = ect.setTableHeaderFormat(bookWorkbook);
		int rowinttitle=0;
		rowTitle = sheet1.createRow((short) rowinttitle);

		cellTitle = rowTitle.createCell((short) 0);
		cellTitle.setEncoding(HSSFCell.ENCODING_UTF_16);							// 设置值的编码格式，确保不会出现乱码
		cellTitle.setCellValue(title);
		cellTitle.setCellStyle(styletitle);

		sheet1.addMergedRegion(new Region(rowinttitle, (short) 0, rowinttitle,(short) (rowName.length-1)));			//共19列，合并第一行
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

		// 设置每列的宽度，
		for(int k = 0; k < rowName.length; k++){
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
			System.out.println("得到要导入excel的行数：：：：：：："+rowNums);
			String isDupli = null;					//判断是否重复
			int  repeatNum = 0;						//重复的条数
			int    succNum = 0;						//成功导入的条数
			int       flag = 0;						//导入的数据表是否正确 0表示正确
			row = sheet.getRow(0);
			int colNums = row.getPhysicalNumberOfCells();
			System.out.println("得到要导入excel的列数：：：：：：："+colNums);
			
			
			//检查导入数据表是否正确 根据列数 和表头名判断
			if((colNums == rowName.length) &&(row.getCell((short) 0).getStringCellValue().contains("备品"))){

			// 正文内容应该从第三行开始,第一行为表头的标题，第二行为数据的字段名   
			for (int i = 2; i <= rowNums; i++) {
				row = sheet.getRow(i);
				
				//先判断序列号不能为空 且 到货验收时间不为空 且时间格式正确
				if(!ect.getCellFormatValue(row.getCell((short) 1)).equals("") &&
						!(ect.getCellFormatValue(row.getCell((short) 13)).equals("")) && 
						!(ect.getCellFormatValue(row.getCell((short) 16)).equals("")) && 
						ect.isValidDate(ect.getCellFormatValue(row.getCell((short) 13))) &&
						ect.isValidDate(ect.getCellFormatValue(row.getCell((short) 16)))) {
					//判断序列号是否重复
					isDupli = bakService.isBakDupli(ect.getCellFormatValue(row.getCell((short) 1)));

					if(isDupli == null){
						proBak.setBakSeqNum(ect.getCellFormatValue(row.getCell((short) 1)));			//序列号
						proBak.setBakVersion(ect.getCellFormatValue(row.getCell((short) 2)));			//型号
						proBak.setBakFactory(ect.getCellFormatValue(row.getCell((short) 3)));			//设备厂商
						proBak.setBakDevType(ect.getCellFormatValue(row.getCell((short) 4)));			//设备类别
						proBak.setBakForm(ect.getCellFormatValue(row.getCell((short) 5)));				//实物形态
						proBak.setBakState(ect.getCellFormatValue(row.getCell((short) 6)));				//状态
						proBak.setBakManaDepart(ect.getCellFormatValue(row.getCell((short) 7)));		//实物管理部门
						proBak.setBakUseDepart(ect.getCellFormatValue(row.getCell((short) 8)));			//使用部门
						proBak.setBakUser(ect.getCellFormatValue(row.getCell((short) 9)));				//使用人
						proBak.setBakDevRoom(ect.getCellFormatValue(row.getCell((short) 10)));			//所属机房
						proBak.setBakDevFrame(ect.getCellFormatValue(row.getCell((short) 11)));			//所在机柜
						proBak.setBakBuyCont(ect.getCellFormatValue(row.getCell((short) 12)));			//采购合同
						proBak.setBakArrAcceptTime(StrToDate(ect.getCellFormatValue(row.getCell((short) 13))));			//到货验收时间
						proBak.setBakMaintainCont(ect.getCellFormatValue(row.getCell((short) 14)));		//维保合同
						proBak.setBakMaintainFactor(ect.getCellFormatValue(row.getCell((short) 15)));		//维保厂商
						proBak.setBakMaintainDeadLine(StrToDate(ect.getCellFormatValue(row.getCell((short) 16))));		//维保截止时间
						proBak.setBakRecorder(ect.getCellFormatValue(row.getCell((short) 17)));			//记录负责人
						proBak.setBakRemark(ect.getCellFormatValue(row.getCell((short) 18)));			//备注

						proBak.setLoginUserName(user.getUserName());					//更新人
						proBak.setBakUpdTime(sdf.format(new Date()));			//更新时间

						hibernateTemplate.save(proBak);
						succNum++;					//记录成功的
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
	public  String exportExcel() throws Exception //用于数据维护，导出全部。
	{

		//HttpServletRequest request = ServletActionContext.getRequest();
		String title="备品备件信息表";
		String[] rowName={"序号","序列号","型号","设备厂商","设备类别","实物形态","状态","实物管理部门","使用部门",
				"使用人","所属机房","所在机柜","采购合同","到货验收时间","维保合同","维保厂商","维保截止时间","记录负责人",
				"备注","更新人","更新时间"};
		HSSFWorkbook bookWorkbook = new HSSFWorkbook();// 创建excel文件
		HSSFSheet sheet = bookWorkbook.createSheet("0");
		HSSFCell cellTitle,cell;
		HSSFRow rowTitle,row;
		HSSFCellStyle styletitle = ect.setTableHeaderFormat(bookWorkbook);//设置表头格式

		int rowinttitle=0;
		rowTitle = sheet.createRow((short) rowinttitle);

		cellTitle = rowTitle.createCell((short) 0);
		cellTitle.setEncoding(HSSFCell.ENCODING_UTF_16);// 设置值的编码格式，确保不会出现乱码
		cellTitle.setCellValue(title);
		cellTitle.setCellStyle(styletitle);
		sheet.addMergedRegion(new Region(rowinttitle, (short) 0, rowinttitle,
				(short) 20));//合并第一行

		HSSFCellStyle style = ect.setColumnDataStyle(bookWorkbook);//设置表头容的格式
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
		for(int k=0;k<21;k++)			//一共21列
		{
			sheet.setColumnWidth((short) k, (short) 4200);
		}
		HSSFCellStyle styleInfo = ect.setColumnDataStyle(bookWorkbook);		//设置表的内容的格式
		int rowintInfo = 2;			//表头和标题栏分别占一行，内容只能从第三行开始写
		List<ProBak> list=bakDao.findAll();			//找到全部内容
		for(int i=0;i<bakDao.findAll().size();i++)
		{
			row=sheet.createRow(rowintInfo++);
			int kk =0;
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakId());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakSeqNum());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakVersion());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakFactory());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakDevType());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakForm());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakState());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakManaDepart());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakUseDepart());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakUser());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakDevRoom());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakDevFrame());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakBuyCont());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(sdf.format(list.get(i).getBakArrAcceptTime()));
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakMaintainCont());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakMaintainFactor());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(sdf.format(list.get(i).getBakMaintainDeadLine()));
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakRecorder());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakRemark());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLoginUserName());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakUpdTime());
			kk++;
		}
		HttpServletRequest request = ServletActionContext.getRequest();

		String filename=null;//作为给前台发送的数据，判断导出是否成功
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
				outputStream = new FileOutputStream(request.getSession().getServletContext().getRealPath("/")+"/Generatefile//Spare parts.xls");
				bookWorkbook.write(outputStream);
				outputStream.flush();
				outputStream.close();
				filename="Spare parts.xls";
			} catch (FileNotFoundException e) {
				System.err.println("获取不到位置");
				//	e.printStackTrace();
				//、、	return "notfoundlujing";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//	e.printStackTrace();
				//、、	return "Sorry,exportfailure";
			}

		} finally{}
		Gson gson = new Gson();
		String success = gson.toJson(filename);
		ServletActionContext.getResponse().getWriter().print(success);//给前台发送数据
		return null;


	}
	@Test
	public  String exportExcelByConditions() throws Exception //导出当前页，未注释的部分参考前一个函数
	{
		HttpServletRequest request = ServletActionContext.getRequest();

		String str1=request.getParameter("data");//用于接收前台发送的id号

		JSONObject jsonArr = JSONObject.fromObject(str1);	


		String title="备品备件信息表";
		String[] rowName={"序号","序列号","型号","设备厂商","设备类别","实物形态","状态","实物管理部门","使用部门",
				"使用人","所属机房","所在机柜","采购合同","到货验收时间","维保合同","维保厂商","维保截止时间","记录负责人",
				"备注","更新人","更新时间"};
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
				(short) 20));//合并第一行

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
		for(int k=0;k<21;k++)//一共29列
		{
			sheet.setColumnWidth((short) k, (short) 4200);
		}
		HSSFCellStyle styleInfo = ect.setColumnDataStyle(bookWorkbook);
		int rowintInfo = 2;
		List<String> bakIds=(List<String>)jsonArr.getJSONArray("bakIds");//将前台发送的数据强制转换为list类型

		List<ProBak> list=new ArrayList<ProBak>();


		for(int i=0;i<bakIds.size();i++)
		{
			list.add(bakDao.findById(bakIds.get(i)));

		}

		for(int i=0;i<list.size();i++)
		{
			row=sheet.createRow(rowintInfo++);
			int kk =0;
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakId());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakSeqNum());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakVersion());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakFactory());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakDevType());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakForm());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakState());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakManaDepart());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakUseDepart());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakUser());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakDevRoom());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakDevFrame());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakBuyCont());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(sdf.format(list.get(i).getBakArrAcceptTime()));
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakMaintainCont());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakMaintainFactor());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(sdf.format(list.get(i).getBakMaintainDeadLine()));
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakRecorder());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakRemark());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLoginUserName());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakUpdTime());
			kk++;
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
				outputStream = new FileOutputStream(request.getSession().getServletContext().getRealPath("/")+"/Generatefile//Spare parts are the current page table.xls");
				bookWorkbook.write(outputStream);
				outputStream.flush();
				outputStream.close();
				filename="Spare parts are the current page table.xls";
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


	@Test
	public String exportAllExcelByConditions() throws Exception//用于导全部前页，未注释的部分参考前一个函数
	{

		HttpServletRequest request = ServletActionContext.getRequest();

		String[] str=new String[3];
		str[0]=request.getParameter("bakSeqNum");//用于接收前台发送的查询的数据
		str[1]=request.getParameter("bakDevType");
		str[2]=request.getParameter("bakUpdTime");

		//		str[3]=request.getParameter("bakDevType1");//用于接收前台筛选的数据，由于功能未实现，所以注释掉
		//		str[4]=request.getParameter("bakForm1");
		//		str[5]=request.getParameter("bakState1");
		//		str[6]=request.getParameter("bakDevRoom1");
		//		str[7]=request.getParameter("bakDevFrame1");
		//		str[8] = request.getParameter("bakManaDepart1");
		//		str[9]=request.getParameter("bakUseDepart1");
		//		str[10]=request.getParameter("bakUser1");





		String title="备品备件信息表";
		String[] rowName={"序号","序列号","型号","设备厂商","设备类别","实物形态","状态","实物管理部门","使用部门",
				"使用人","所属机房","所在机柜","采购合同","到货验收时间","维保合同","维保厂商","维保截止时间","记录负责人",
				"备注","更新人","更新时间"};
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
				(short) 20));//合并第一行

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
		for(int k=0;k<21;k++)//一共29列
		{
			sheet.setColumnWidth((short) k, (short) 4200);
		}
		HSSFCellStyle styleInfo = ect.setColumnDataStyle(bookWorkbook);
		int rowintInfo = 2;
		int count=0;
		Conditions conditions=new Conditions();
		conditions.addCondition("bakSeqNum", str[0], Operator.LIKE);
		conditions.addCondition("bakDevType", str[1], Operator.LIKE);
		conditions.addCondition("bakUpdTime", str[2], Operator.LIKE);

		//		conditions.addCondition("bakDevType", str[3], Operator.EQUAL);
		//		conditions.addCondition("bakForm", str[4], Operator.EQUAL);
		//		conditions.addCondition("bakState", str[5], Operator.EQUAL);
		//		conditions.addCondition("bakDevRoom", str[6], Operator.EQUAL);
		//		conditions.addCondition("bakDevFrame", str[7], Operator.EQUAL);
		//		conditions.addCondition("bakManaDepart", str[8], Operator.EQUAL);
		//		conditions.addCondition("bakUseDepart", str[9], Operator.EQUAL);
		//		conditions.addCondition("bakUser", str[10], Operator.EQUAL);

		List<ProBak> list=bakDao.findByConditions(conditions);
		for(int i=0;i<list.size();i++)
		{
			row=sheet.createRow(rowintInfo++);
			int kk =0;
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakId());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakSeqNum());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakVersion());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakFactory());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakDevType());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakForm());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakState());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakManaDepart());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakUseDepart());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakUser());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakDevRoom());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakDevFrame());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakBuyCont());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(sdf.format(list.get(i).getBakArrAcceptTime()));
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakMaintainCont());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakMaintainFactor());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(sdf.format(list.get(i).getBakMaintainDeadLine()));
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakRecorder());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakRemark());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLoginUserName());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakUpdTime());
			kk++;
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
				outputStream = new FileOutputStream(request.getSession().getServletContext().getRealPath("/")+"/Generatefile//According to the conditions of export all spare parts.xls");
				bookWorkbook.write(outputStream);
				outputStream.flush();
				outputStream.close();
				filename="According to the conditions of export all spare parts.xls";
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
	public  String exportpartExcelByConditions() throws Exception //用于批量导出，未进行注释的部分主要参考导出当前页
	{
		HttpServletRequest request = ServletActionContext.getRequest();

		String str1=request.getParameter("data");//用于接收前台的id号

		JSONObject jsonArr = JSONObject.fromObject(str1);	







		String title="备品备件信息表";
		String[] rowName={"序号","序列号","型号","设备厂商","设备类别","实物形态","状态","实物管理部门","使用部门",
				"使用人","所属机房","所在机柜","采购合同","到货验收时间","维保合同","维保厂商","维保截止时间","记录负责人",
				"备注","更新人","更新时间"};
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
				(short) 20));//合并第一行

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
		for(int k=0;k<21;k++)//一共29列
		{
			sheet.setColumnWidth((short) k, (short) 4200);
		}
		HSSFCellStyle styleInfo = ect.setColumnDataStyle(bookWorkbook);
		int rowintInfo = 2;
		List<String> bakIds=(List<String>)jsonArr.getJSONArray("bakIds");

		List<ProBak> list=new ArrayList<ProBak>();


		for(int i=0;i<bakIds.size();i++)
		{
			list.add(bakDao.findById(bakIds.get(i)));

		}

		for(int i=0;i<list.size();i++)
		{
			row=sheet.createRow(rowintInfo++);
			int kk =0;
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakId());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakSeqNum());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakVersion());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakFactory());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakDevType());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakForm());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakState());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakManaDepart());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakUseDepart());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakUser());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakDevRoom());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakDevFrame());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakBuyCont());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(sdf.format(list.get(i).getBakArrAcceptTime()));
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakMaintainCont());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakMaintainFactor());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(sdf.format(list.get(i).getBakMaintainDeadLine()));
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakRecorder());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakRemark());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLoginUserName());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getBakUpdTime());
			kk++;
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
				outputStream = new FileOutputStream(request.getSession().getServletContext().getRealPath("/")+"/Generatefile//Batch export of spare parts.xls");
				bookWorkbook.write(outputStream);
				outputStream.flush();
				outputStream.close();
				filename="Batch export of spare parts.xls";
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

	public String readExcelContent(InputStream is) {//对表格的内容进行读取

		String succ=null;//作为返回值判断读取是否成功
		List<ProBak> list=new ArrayList<ProBak>();
		list=bakDao.findAll();


		String  bakId=null;
		String  bakSeqNum=null;
		String  bakVersion=null;
		String  bakFactory=null;

		String  bakDevType=null;
		String  bakForm=null;	
		String  bakState=null;
		String  bakManaDepart=null;

		String  bakUseDepart=null;

		String  bakUser=null;
		String  bakDevRoom=null;
		String  bakDevFrame=null;
		String  bakBuyCont=null;
		Date  bakArrAcceptTime=null;

		String  bakMaintainCont=null;
		String  bakMaintainFactor=null;
		Date  bakMaintainDeadLine=null;
		String  bakRecorder=null;

		String  bakRemark=null;
		String loginUserName=null;
		String  bakUpdTime=null;

		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
		//String str = "";
		try {
			fsimport = new POIFSFileSystem(is);
			wbimport = new HSSFWorkbook(fsimport);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheetimport = wbimport.getSheetAt(0);
		// 得到总行数
		int rowNum = sheetimport.getLastRowNum();
		rowimport = sheetimport.getRow(1);
		int colNum = rowimport.getPhysicalNumberOfCells();


		if(colNum==21)
		{
			//getDao.deleteAll(list);
			// hibernateTemplate.save(excel);
			System.out.println(">>>>>>>>>>>>>>>>>>");
			for (int i = 2; i <= rowNum; i++) {
				rowimport = sheetimport.getRow(i);
				int j = 0;
				bakId= ect.getCellFormatValue(rowimport.getCell((short) j));
				proBak.setBakId(bakId);
				j++;

				bakSeqNum= ect.getCellFormatValue(rowimport.getCell((short) j));
				proBak.setBakSeqNum(bakSeqNum);
				j++;

				bakVersion= ect.getCellFormatValue(rowimport.getCell((short) j));
				proBak.setBakVersion(bakVersion);
				j++;

				bakFactory= ect.getCellFormatValue(rowimport.getCell((short) j));
				proBak.setBakFactory(bakFactory);
				j++;

				bakDevType= ect.getCellFormatValue(rowimport.getCell((short) j));
				proBak.setBakDevType(bakDevType);
				j++;

				bakForm= ect.getCellFormatValue(rowimport.getCell((short) j));
				proBak.setBakForm(bakForm);
				j++;

				bakState= ect.getCellFormatValue(rowimport.getCell((short) j));
				proBak.setBakState(bakState);
				j++;

				bakManaDepart= ect.getCellFormatValue(rowimport.getCell((short) j));
				proBak.setBakManaDepart(bakManaDepart);
				j++;

				bakUseDepart= ect.getCellFormatValue(rowimport.getCell((short) j));
				proBak.setBakUseDepart(bakUseDepart);
				j++;

				bakUser= ect.getCellFormatValue(rowimport.getCell((short) j));
				proBak.setBakUser(bakUser);
				j++;

				bakDevRoom= ect.getCellFormatValue(rowimport.getCell((short) j));
				proBak.setBakDevRoom(bakDevRoom);
				j++;

				bakDevFrame= ect.getCellFormatValue(rowimport.getCell((short) j));
				proBak.setBakDevFrame(bakDevFrame);
				j++;

				bakBuyCont= ect.getCellFormatValue(rowimport.getCell((short) j));
				proBak.setBakBuyCont(bakBuyCont);
				j++;

				/*bakArrAcceptTime= (rowimport.getCell((short) j).getDateCellValue());
				proBak.setBakArrAcceptTime(bakArrAcceptTime);
				j++;*/
				
				proBak.setBakArrAcceptTime(StrToDate(ect.getCellFormatValue(rowimport.getCell((short) j))));

				bakMaintainCont= ect.getCellFormatValue(rowimport.getCell((short) j));
				proBak.setBakMaintainCont(bakMaintainCont);
				j++;

				bakMaintainFactor= ect.getCellFormatValue(rowimport.getCell((short) j));
				proBak.setBakMaintainFactor(bakMaintainFactor);
				j++;

				/*bakMaintainDeadLine= (rowimport.getCell((short) j).getDateCellValue());
				proBak.setBakMaintainDeadLine(bakMaintainDeadLine);
				j++;*/
				proBak.setBakMaintainDeadLine(StrToDate(ect.getCellFormatValue(rowimport.getCell((short) j))));

				bakRecorder= ect.getCellFormatValue(rowimport.getCell((short) j));
				proBak.setBakRecorder(bakRecorder);
				j++;

				bakRemark= ect.getCellFormatValue(rowimport.getCell((short) j));
				proBak.setBakRemark(bakRemark);
				j++;

				loginUserName= ect.getCellFormatValue(rowimport.getCell((short) j));
				proBak.setLoginUserName(loginUserName);
				j++;

				bakUpdTime= ect.getCellFormatValue(rowimport.getCell((short) j));
				proBak.setBakUpdTime(bakUpdTime);

				hibernateTemplate.save(proBak);

			}
			succ="1";
			return succ;//读取成功

		}
		else{
			return succ; 
		}
	}

	@Test 
	public String  excelToMysql() throws IOException {//导入到数据库


		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getRealPath("/Generatefile");
		InputStream is = new FileInputStream(file);

		OutputStream os = new FileOutputStream(new File(path, fileFileName));
		byte[] buffer = new byte[200];
		int len = 0;
		while((len=is.read(buffer)) != -1){

			os.write(buffer, 0, len);
		}


		File ff = new File(path, fileFileName);
		InputStream is2 = new FileInputStream(ff);
		List<ProBak> list1=bakDao.findAll();





		if(readExcelContent(is2)=="1")//导入成功
		{

			HttpServletResponse response  = ServletActionContext.getResponse();
			PrintWriter out = response.getWriter();
			out.print(-1);
			out.flush();
			out.close();
			try {
				bakDao.deleteAll(list1);//导入之前对数据库对应的表格进行清空
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


	public Date StrToDate(String str) {

		SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar calendar = new GregorianCalendar();

		Date date = null;
		try {
			calendar.setTime(form.parse(str));
			calendar.add(Calendar.DAY_OF_YEAR, 1);

			date = calendar.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}


	public String  searchOutChart()  throws  Exception{
		Conditions conditions  = new Conditions();
		DataTablesPage<ProBak>   page = new DataTablesPage<ProBak>() ;
		HttpServletRequest request= ServletActionContext.getRequest();
		String  bakArrAcceptTime  = request.getParameter("bakArrAcceptTime");
		Date date = StrToDate(bakArrAcceptTime);

		conditions.addCondition("bakArrAcceptTime", date, Operator.LESS_EQUAL);
		String iDisplayLength = request.getParameter("iDisplayLength");
		int iDisplayLength1 = Integer.valueOf(iDisplayLength);
		System.out.println(iDisplayLength1+"---------");
		String nowPage = request.getParameter("nowPage");
		int nowPage1 = Integer.valueOf(nowPage);
		System.out.println("---------------"+nowPage1);



		DataTablesPage<ProBak>  pagaData = bakService. findAll(conditions, iDisplayLength1,nowPage1) ;

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
