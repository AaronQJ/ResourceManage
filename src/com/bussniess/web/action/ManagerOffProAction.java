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

import com.bussniess.dao.impl.OffProDaoImpl;
import com.bussniess.dao.util.Conditions;
import com.bussniess.dao.util.Conditions.Operator;
import com.bussniess.domain.OffPro;
import com.bussniess.domain.Users;
import com.bussniess.service.OffProService;
import com.bussniess.util.BarcodeInfo;
import com.bussniess.util.DataTablesPage;
import com.bussniess.util.ExcelCellType;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class ManagerOffProAction  extends ActionSupport implements ModelDriven<OffPro>  {

	private static final long serialVersionUID = 1L;

	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
	HibernateTemplate   hibernateTemplate = (HibernateTemplate) applicationContext.getBean("hibernateTemplate");
	OffProDaoImpl      offDao = (OffProDaoImpl) applicationContext.getBean("offDao");
	private static Logger log = Logger.getLogger(ManagerOffProAction.class);
	private OffPro     offPro = new OffPro();
	private OffProService  offService; 
	ExcelCellType<OffPro> ect = new ExcelCellType<OffPro>();
	Users user = (Users)ServletActionContext.getRequest().getSession().getAttribute("user");		//获取登录人
	BarcodeInfo<OffPro> barInfo = new BarcodeInfo<OffPro>();

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private  File   file;    	
	private  String fileFileName;  			//提交过来的file的名字	
	private  String fileContentType;			//提交过来的file的MIME类型

	private  POIFSFileSystem fsimport;
	private  HSSFWorkbook    wbimport;
	private  HSSFSheet       sheetimport;
	private  HSSFRow         rowimport;

	@Override
	public OffPro getModel() {
		return offPro;
	}

	public OffPro getOffPro() {
		return offPro;
	}

	public void setOffPro(OffPro offPro) {
		this.offPro = offPro;
	}

	public OffProService getOffService() {
		return offService;
	}

	public void setOffService(OffProService offService) {
		this.offService = offService;
	}



	public String add() throws IOException{
		offPro.setLoginUserName(user.getUserName());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		//生成条码号
		String offBarcode = barInfo.getCodeNum(sdf.format(offPro.getOffObtDate()),
				0,barInfo.getOffType(offPro.getOffDevVersion()), barInfo.getId(BarcodeInfo.offTableName, offPro.getOffObtDate()));		

		offPro.setOffObtDate(offPro.getOffObtDate());
		offPro.setOffUpdTime(sdf.format(new Date()));
		offPro.setOffBarCode(offBarcode);

		MDC.put("userName", user.getUserName());
		MDC.put("objName", offPro.getOffName());
		MDC.put("objId", offPro.getOffNum());
		MDC.put("logType", "devRecord");
		MDC.put("objType", "办公资产");
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

		barInfo.createBarcode(offPro.getOffBarCode(), offPro.getOffDevVersion());		//生成条码图片

		offService.add(offPro);
		HttpServletResponse response  = ServletActionContext.getResponse();
		PrintWriter out =  response.getWriter();
		out.print("true");

		out.flush();
		out.close();	
		return null;
	}

	public String  send() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		Conditions conditions      = new Conditions();
		String  offUseState	  =  request.getParameter("offUseState");
		String  offUseDepart  =  request.getParameter("offUseDepart");
		String  offManager       =  request.getParameter("offManager");
		String  offState       =  request.getParameter("offState");
		String  offName       =  request.getParameter("offName");
		
		conditions.addCondition("offUseState", offUseState, Operator.EQUAL);
		conditions.addCondition("offUseDepart", offUseDepart, Operator.EQUAL);
		conditions.addCondition("offManager", offManager, Operator.EQUAL);
		conditions.addCondition("offState", offState, Operator.EQUAL);
		conditions.addCondition("offName", offName, Operator.EQUAL);
		


		String iDisplayLength = request.getParameter("iDisplayLength");
		int iDisplayLength1   = Integer.valueOf(iDisplayLength);
		String nowPage  = request.getParameter("nowPage");
		int   nowPage1  = Integer.valueOf(nowPage);

		DataTablesPage<OffPro>  pagaData = offService. findAll(conditions, iDisplayLength1+10000,nowPage1) ;

		Gson   gson = new Gson();
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
		Conditions         conditions  = new Conditions();
		DataTablesPage<OffPro>   page  = new DataTablesPage<OffPro>() ;
		HttpServletRequest    request  = ServletActionContext.getRequest();

		String  offName     =  request.getParameter("offName");
		String  offSeqNum   =  request.getParameter("offSeqNum");
		String  offNum      =  request.getParameter("offNum");
		String  offUser     =  request.getParameter("offUser");
		String  offBarCode  =  request.getParameter("offBarCode");



		conditions.addCondition("offName", offName, Operator.LIKE);
		conditions.addCondition("offSeqNum", offSeqNum, Operator.LIKE);
		conditions.addCondition("offNum", offNum, Operator.LIKE);
		conditions.addCondition("offUser", offUser, Operator.LIKE);
		conditions.addCondition("offBarCode", offBarCode, Operator.EQUAL);

		List<String>   offUseStateList  =  new ArrayList<String>();
		List<String>  offUseDepartList  =  new ArrayList<String>();
		List<String>       offUserList  =  new ArrayList<String>();
		List<String>       offNameList  =  new ArrayList<String>();
		List<String>    offManagerList  =  new ArrayList<String>();

		List<OffPro>        offProList  = offService.findByConditions(conditions);

		for(OffPro off: offProList){
			offUseStateList.add(off.getOffUseState());
		}
		Set<String>     offUseStateSet = new HashSet<String>(offUseStateList);
		offUseStateList.clear();
		offUseStateList.addAll(offUseStateSet);

		for(OffPro off: offProList){
			offUseDepartList.add(off.getOffUseDepart());
		}
		Set<String> offUseDepartSet = new HashSet<String>(offUseDepartList);
		offUseDepartList.clear();
		offUseDepartList.addAll(offUseDepartSet);

		for(OffPro off: offProList){
			offUserList.add(off.getOffUser());
		}
		Set<String> offUserSet = new HashSet<String>(offUserList);
		offUserList.clear();
		offUserList.addAll(offUserSet);


		for(OffPro off: offProList){
			offNameList.add(off.getOffName());
		}
		Set<String> offNameSet = new HashSet<String>(offNameList);
		offNameList.clear();
		offNameList.addAll(offNameSet);

		for(OffPro off: offProList){
			offManagerList.add(off.getOffManager());
		}
		Set<String> offManagerSet = new HashSet<String>(offManagerList);
		offManagerList.clear();
		offManagerList.addAll(offManagerSet);

//		HttpSession  session = ServletActionContext.getRequest().getSession();
		Map<String,Object> mapList = new HashMap<String,Object>();

		mapList.put("offUseDepartList", offUseDepartList );
		mapList.put("offUseStateList",  offUseStateList);
		mapList.put("offNameList",      offNameList);
		mapList.put("offManagerList",   offManagerList);


		String iDisplayLength  = request.getParameter("iDisplayLength");
		int   iDisplayLength1  = Integer.valueOf(iDisplayLength);
		System.out.println(iDisplayLength1+"---------");
		String  nowPage = request.getParameter("nowPage");
		int    nowPage1 = Integer.valueOf(nowPage);
		System.out.println("---------------"+nowPage1);


		System.out.println(page.getITotalRecords()+"--------------"+iDisplayLength1);
		DataTablesPage<OffPro>  pagaData = offService. findAll(conditions, iDisplayLength1,nowPage1) ;

		Gson   gson = new Gson();
		
		mapList.put("pagaData",pagaData);
		String  offStr = gson.toJson(mapList);
		System.out.println(offStr+"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		String data = gson.toJson(pagaData);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out =response.getWriter();
		out.print(offStr);
		System.out.println("77777777"+offStr);
		out.flush();
		out.close();
		return null;
	}


	public  String checkSeqNum()  throws IOException{
		Conditions conditions  =  new Conditions();
		String      offSeqNum  = (String) ServletActionContext.getRequest().getAttribute("offSeqNum");

		System.out.println(offSeqNum);
		conditions.addCondition("offSeqNum", offSeqNum, Operator.EQUAL);

		boolean isRepeat  = offService.checkSeqNum(conditions);
		Gson        gson  = new Gson();
		String seqRepeat  =  gson.toJson(isRepeat);
		System.out.println(seqRepeat);
		ServletActionContext.getResponse().getWriter().print(seqRepeat);

		return null;
	}


	//按条码查询
	public String findByBarcode() throws IOException{
		Conditions 		 conditions = new Conditions();
		DataTablesPage<OffPro> page = new DataTablesPage<OffPro>();
		HttpServletRequest  request = ServletActionContext.getRequest();
		String  		 offBarCode = request.getParameter("offBarCode");

		conditions.addCondition("offBarCode", offBarCode, Operator.EQUAL);

		String iDisplayLength  = request.getParameter("iDisplayLength");
		int   iDisplayLength1  = Integer.valueOf(iDisplayLength);
		System.out.println(iDisplayLength1+"---------");
		String  nowPage  = request.getParameter("nowPage");
		int    nowPage1  = Integer.valueOf(nowPage);
		System.out.println("---------------"+nowPage1);

		System.out.println(page.getITotalRecords()+"--------------"+iDisplayLength1);
		DataTablesPage<OffPro>  pagaData = offService. findAll(conditions, iDisplayLength1,nowPage1) ;

		Gson    gson  =  new Gson();
		String  data  =  gson.toJson(pagaData);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out =  response.getWriter();
		out.print(data);
		out.flush();
		out.close();
		return null;
	}





	public String importOff() throws IOException {
		HttpServletRequest  request  =  ServletActionContext.getRequest();
		String                 path  =  request.getRealPath("/upload");		//获取项目下的upload/路径
		InputStream              is  =  new FileInputStream(file);			//从前端input中的name="file"获取文件输入流对象
		OutputStream             os  =  new FileOutputStream(new File(path, fileFileName));
		byte[]               buffer  =  new byte[8192];
		int                     len  =  0;
		while((len=is.read(buffer)) != -1){
			os.write(buffer, 0, len);
		}
		os.close();
		is.close();

		File            ff  =  new File(path, fileFileName);
		POIFSFileSystem fs;
		HSSFWorkbook    wb  =  null;
		HSSFSheet    sheet;
		HSSFRow        row;


		String 		title = "导入后的错误数据";
		String[] rowName={"设备号","条码","资产编号","名称","设备型号",
				"价格","取得日期","使用/管理部门","存放地点","负责人","备注",
				"使用状况","状态","商品序列号","使用年限","使用标志"};
		HSSFWorkbook bookWorkbook = new HSSFWorkbook();// 创建excel文件
		HSSFSheet sheet1 = bookWorkbook.createSheet("0");
		HSSFCell cellTitle,cell;
		HSSFRow rowTitle,row1;

		HSSFCellStyle styletitle = ect.setTableHeaderFormat(bookWorkbook);
		int rowinttitle=0;
		rowTitle = sheet1.createRow((short) rowinttitle);

		cellTitle = rowTitle.createCell((short) 0);
		cellTitle.setEncoding(HSSFCell.ENCODING_UTF_16);// 设置值的编码格式，确保不会出现乱码
		cellTitle.setCellValue(title);
		cellTitle.setCellStyle(styletitle);
		int rowStart = 2;				//每次调用生成新的excel时的参数，表示在新的excel待添加数据的行
		sheet1.addMergedRegion(new Region(rowinttitle, (short) 0, rowinttitle,(short) (rowName.length-1)));		//共14列，合并第一行
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

		// 设置每列的宽度，共14列
		for(int k=0;k<rowName.length;k++){
			sheet1.setColumnWidth((short) k, (short) 4200);
		}


		try {
			//	is = new FileInputStream("d:/333.xls");
			InputStream is2 = new FileInputStream(ff);
			try {
				fs = new POIFSFileSystem(is2);
				wb = new HSSFWorkbook(fs);
			} catch (IOException e) {
				e.printStackTrace();
			}
			sheet          =  wb.getSheetAt(0);			
			int   rowNums  =  sheet.getLastRowNum(); 		//得到总行数
			System.out.println("得到要导入excel的行数>>>>>>>>>>>>>>>"+rowNums);
			String isDupli =  null;							//判别是否重复的标志
			int repeatNum  =  0;							//重复的条数
			int   succNum  =  0;							//成功导入的条数
			int 	 flag  =  0;							//导入的数据表是否正确
			row = sheet.getRow(0);
			int colNums = row.getPhysicalNumberOfCells();
			System.out.println("得到要导入excel的列数：：：：：：："+colNums);
			System.out.println(row.getCell((short) 0).getStringCellValue()+">>>>>>>");
			System.out.println(row.getCell((short) 0).getStringCellValue().contains("办公")+">>>>>>>>>>>>");
			//检查导入数据表是否正确 根据列数 和表头名判断
			if((colNums == rowName.length) && (row.getCell((short) 0).getStringCellValue().contains("办公"))){
				// 正文内容应该从第三行开始,第一行为表头的标题，第二行为数据的字段名
				for (int i = 2; i <= rowNums; i++) {
					row    = sheet.getRow(i);
					//先判断商品序列号不能为空 且 获得日期不为空 且时间格式正确
					if(!ect.getCellFormatValue(row.getCell((short) 13)).equals("") &&
							!(ect.getCellFormatValue(row.getCell((short) 6)).equals("")) &&
							ect.isValidDate(ect.getCellFormatValue(row.getCell((short) 6)))){

						isDupli = offService.isOffDupli(ect.getCellFormatValue(row.getCell((short) 13)));		//没有重复 返回null, 有重复返回重复的id

						//判断待增加的数据中和数据表中是否重复（主要看offSeqNum是否相同），若重复就不进行增加
						if(isDupli == null){		//没有重复的null
							//生成条形码            参数的含义：1.excel的第6列为获得日期，2.设备类别， 3.每年设备的流水号
							String barcode = String.valueOf(barInfo.getCodeNum(ect.getCellFormatValue(row.getCell((short)6)), 0,
									barInfo.getOffType(row.getCell((short) 4).getStringCellValue()),
									barInfo.getId(BarcodeInfo.offTableName, StrToDate(ect.getCellFormatValue(row.getCell((short)6))))));  
							System.out.println("Barcode："+barcode+"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
							//保存到数据库中       		
							offPro.setOffBarCode(barcode);													//条形码
							offPro.setOffNum(ect.getCellFormatValue(row.getCell((short) 2)));				//设备序列号
							offPro.setOffName(ect.getCellFormatValue(row.getCell((short) 3)));				//设备名称
							offPro.setOffDevVersion(ect.getCellFormatValue(row.getCell((short) 4)));		//设备类型
							offPro.setOffPrice(Float.valueOf(ect.getCellFormatValue(row.getCell((short) 5))));		//设备价格
							offPro.setOffObtDate(StrToDate(ect.getCellFormatValue(row.getCell((short) 6))));				//获得日期
							offPro.setOffUseDepart(ect.getCellFormatValue(row.getCell((short) 7)));			//使用部门
							offPro.setOffUser(ect.getCellFormatValue(row.getCell((short) 8)));				//使用人
							offPro.setOffManager(ect.getCellFormatValue(row.getCell((short) 9)));			//所属部门
							offPro.setOffRemark(ect.getCellFormatValue(row.getCell((short) 10)));			//备注
							offPro.setOffUseState(ect.getCellFormatValue(row.getCell((short) 11)));			//使用人状态
							offPro.setOffState(ect.getCellFormatValue(row.getCell((short) 12)));			//使用状态
							offPro.setOffSeqNum(ect.getCellFormatValue(row.getCell((short) 13)));			//产品序列号
							offPro.setLoginUserName(user.getUserName());			//更新人是当前登录人
							offPro.setOffUpdTime(sdf.format(new Date()));									//更新时间

							offPro.setOffLifeTime(Integer.valueOf(ect.getCellFormatValue(row.getCell((short)14))));		//使用年限（新增）
							offPro.setOffFlag(ect.getCellFormatValue(row.getCell((short)15)));				//使用标志（新增）
							
							barInfo.createBarcode(barcode, ect.getCellFormatValue(row.getCell((short) 4)));		//根据条码值和设备类型生成条码图片
							hibernateTemplate.save(offPro);
							succNum++;					//记录成功导入的条数
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
			e.printStackTrace();
			//	System.out.println("找不到文件！");
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
		}*/


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


	public String  updateMore() throws Exception{
		HttpServletRequest  request = ServletActionContext.getRequest();
		Gson 				   gson = new Gson();
		request.setCharacterEncoding("utf-8");
		String 				jsonArr = request.getParameter("data");
		JSONObject       jsonObject = JSONObject.fromObject(jsonArr);//json-lib中是jo=JSONObject.fromObject(t);
		List<String>         offIds = (List<String>) jsonObject.getJSONArray("offIds");
		String   		   offState = (String) jsonObject.get("offState");
		String              offUser = (String) jsonObject.get("offUser");
		String           offManager = (String) jsonObject.get("offManager");
		String         offUseDepart = (String) jsonObject.get("offUseDepart");

		for (int i = 0; i < offIds.size(); i++) {  
			//获取第i个数组元素  
			String offId  = offIds.get(i);  
			//		   System.out.println(speid);
			OffPro offPro = offService.findById(offId);
			//	       System.out.println(spePro);
			if(!offState.equals("请选择")){
				if(!offPro.getOffState().equals(offState)){
					MDC.put("fieldUpdValue", offState);
					MDC.put("fieldOriValue", offPro.getOffState());
					MDC.put("userName", user.getUserName());
					MDC.put("objName", offPro.getOffName());
					MDC.put("objId", offPro.getOffNum());
					MDC.put("logType", "devRecord");
					MDC.put("objType", "办公资产");
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
			if(!offUser.equals("")){
				if(!offPro.getOffUser().equals(offUser)){
					MDC.put("fieldUpdValue", offUser);
					MDC.put("fieldOriValue", offPro.getOffUser());
					MDC.put("userName", user.getUserName());
					MDC.put("objName", offPro.getOffName());
					MDC.put("objId", offPro.getOffNum());
					MDC.put("logType", "devRecord");
					MDC.put("objType", "办公资产");
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
			if(!offManager.equals("")){
				if(!offPro.getOffManager().equals(offManager)){
					MDC.put("fieldUpdValue", offManager);
					MDC.put("fieldOriValue", offPro.getOffManager());
					MDC.put("userName", user.getUserName());
					MDC.put("objName", offPro.getOffName());
					MDC.put("objId", offPro.getOffNum());
					MDC.put("logType", "devRecord");
					MDC.put("objType", "办公资产");
					MDC.put("operType", "修改");
					MDC.put("fieldName", "管理人");
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
			if(!offUseDepart.equals("")){
				if(!offPro.getOffUseDepart().equals(offUseDepart)){
					MDC.put("fieldUpdValue", offUseDepart);
					MDC.put("fieldOriValue", offPro.getOffUseDepart());
					MDC.put("userName", user.getUserName());
					MDC.put("objName", offPro.getOffName());
					MDC.put("objId", offPro.getOffNum());
					MDC.put("logType", "devRecord");
					MDC.put("objType", "办公资产");
					MDC.put("operType", "修改");
					MDC.put("fieldName", "使用/管理部门");
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
			offPro.setOffUser(offUser);
			offPro.setOffState(offState);
			offPro.setOffManager(offManager);
			offPro.setOffUseDepart(offUseDepart);

			offService.update(offPro);
		}  

		String success  = gson.toJson("true");
		ServletActionContext.getResponse().getWriter().print(success);
		return null;
	}


	public String deleteMore() throws Exception{
		HttpServletRequest  request = ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		String        jsonArr  =  request.getParameter("data");
		JSONObject jsonObject  =  JSONObject.fromObject(jsonArr);//json-lib中是jo=JSONObject.fromObject(t);
		List<String>   offIds  =  (List<String>) jsonObject.getJSONArray("offIds");

		System.out.println(offIds.toString());
		List<OffPro>  offList  = new ArrayList<OffPro>();
		Gson gson = new Gson();
		for (int i = 0; i < offIds.size(); i++) {  
			//获取第i个数组元素  
			String offid= offIds.get(i); 
			OffPro off1 = offService.findById(offid);
			MDC.put("objName", off1.getOffName());
			MDC.put("objId", off1.getOffNum());
			MDC.put("userName", user.getUserName());
			MDC.put("logType", "devRecord");
			MDC.put("objType", "办公资产");
			MDC.put("operType", "删除");

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
			offService.deleteById(offid);

			OffPro off = offService.findById(offid);
			offList.add(off);
		}  

		String deleSucc;
		if(offList!=null){
			deleSucc=  gson.toJson("false");
		}
		deleSucc = gson.toJson("true");
		ServletActionContext.getResponse().getWriter().print(deleSucc);

		return null;
	}


	public String deleteOne() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		String               offId = request.getParameter("offId");
		Gson                  gson = new Gson();
		offService.deleteById(offId);

		OffPro              offPro = offService.findById(offId);
		String deleSucc;
		if(offPro!=null){
			deleSucc =  gson.toJson("false");
		}
		deleSucc     = gson.toJson("true");
		ServletActionContext.getResponse().getWriter().print(deleSucc);
		return null;
	}

	public String findOne() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();

		String offId = request.getParameter("offId");
		OffPro offPro = offService.findById(offId);

		String off = gson.toJson(offPro);

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(off);
		return null;


	}
/*
	**
	 * 单个更新
	 * @return
	 * @throws IOException
	 */
		
		public String updateOne() throws IOException{
			HttpServletRequest request = ServletActionContext.getRequest();
			Gson gson = new Gson();
			String offId = request.getParameter("offId");
			OffPro off = offService.findById(offId);
			if(!offPro.getOffDevVersion().equals("")){
				if(!off.getOffDevVersion().equals(offPro.getOffDevVersion())){
					MDC.put("fieldUpdValue", offPro.getOffDevVersion());
					MDC.put("fieldOriValue", off.getOffDevVersion());
					MDC.put("fieldName", "设备型号");
					MDC.put("objName", off.getOffName());
					MDC.put("objId", off.getOffNum());
					MDC.put("userName", user.getUserName());
					MDC.put("logType", "devRecord");
					MDC.put("objType", "办公资产");
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
			if(!offPro.getOffManager().equals("")){
				if(!off.getOffManager().equals(offPro.getOffManager())){
					MDC.put("fieldUpdValue", offPro.getOffManager());
					MDC.put("fieldOriValue", off.getOffManager());
					MDC.put("fieldName", "责任人");
					MDC.put("objName", off.getOffName());
					MDC.put("objId", off.getOffNum());
					MDC.put("userName", user.getUserName());
					MDC.put("logType", "devRecord");
					MDC.put("objType", "办公资产");
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
			if(!offPro.getOffName().equals("")){
				if(!off.getOffName().equals(offPro.getOffName())){
					MDC.put("fieldUpdValue", offPro.getOffName());
					MDC.put("fieldOriValue", off.getOffName());
					MDC.put("fieldName", "名称");
					MDC.put("objName", off.getOffName());
					MDC.put("objId", off.getOffNum());
					MDC.put("userName", user.getUserName());
					MDC.put("logType", "devRecord");
					MDC.put("objType", "办公资产");
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
			if(!offPro.getOffNum().equals("")){
				if(!off.getOffNum().equals(offPro.getOffNum())){
					MDC.put("fieldUpdValue", offPro.getOffNum());
					MDC.put("fieldOriValue", off.getOffNum());
					MDC.put("fieldName", "资产编号");
					MDC.put("objName", off.getOffName());
					MDC.put("objId", off.getOffNum());
					MDC.put("userName", user.getUserName());
					MDC.put("logType", "devRecord");
					MDC.put("objType", "办公资产");
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
			if(!offPro.getOffObtDate().equals("")){
				if(!off.getOffObtDate().equals(offPro.getOffObtDate())){
					MDC.put("fieldUpdValue", offPro.getOffObtDate());
					MDC.put("fieldOriValue", off.getOffObtDate());
					MDC.put("fieldName", "取得日期");
					MDC.put("objName", off.getOffName());
					MDC.put("objId", off.getOffNum());
					MDC.put("userName", user.getUserName());
					MDC.put("logType", "devRecord");
					MDC.put("objType", "办公资产");
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
			if(!(offPro.getOffPrice() == null)){
				if(!(off.getOffPrice() == null)){
				if(!off.getOffPrice().equals(offPro.getOffPrice())){
					MDC.put("fieldUpdValue", offPro.getOffPrice());
					if(off.getOffPrice() == null){
						MDC.put("fieldOriValue", "无");
					}else{
						MDC.put("fieldOriValue", off.getOffPrice());
					}
					MDC.put("fieldName", "价格");
					MDC.put("objName", off.getOffName());
					MDC.put("objId", off.getOffNum());
					MDC.put("userName", user.getUserName());
					MDC.put("logType", "devRecord");
					MDC.put("objType", "办公资产");
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
				}else{
					MDC.put("fieldUpdValue", offPro.getOffPrice());
					if(off.getOffPrice() == null){
						MDC.put("fieldOriValue", "无");
					}else{
						MDC.put("fieldOriValue", off.getOffPrice());
					}
					MDC.put("fieldName", "价格");
					MDC.put("objName", off.getOffName());
					MDC.put("objId", off.getOffNum());
					MDC.put("userName", user.getUserName());
					MDC.put("logType", "devRecord");
					MDC.put("objType", "办公资产");
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
			if(!offPro.getOffRemark().equals("")){
				if(!off.getOffRemark().equals(offPro.getOffRemark())){
					MDC.put("fieldUpdValue", offPro.getOffRemark());
					MDC.put("fieldOriValue", off.getOffRemark());
					MDC.put("fieldName", "备注");
					MDC.put("objName", off.getOffName());
					MDC.put("objId", off.getOffNum());
					MDC.put("userName", user.getUserName());
					MDC.put("logType", "devRecord");
					MDC.put("objType", "办公资产");
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
			if(!offPro.getOffState().equals("")){
				if(!off.getOffState().equals(offPro.getOffState())){
					MDC.put("fieldUpdValue", offPro.getOffState());
					MDC.put("fieldOriValue", off.getOffState());
					MDC.put("fieldName", "状态");
					MDC.put("objName", off.getOffName());
					MDC.put("objId", off.getOffNum());
					MDC.put("userName", user.getUserName());
					MDC.put("logType", "devRecord");
					MDC.put("objType", "办公资产");
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
			if(!offPro.getOffUseDepart().equals("")){
				if(!off.getOffUseDepart().equals(offPro.getOffUseDepart())){
					MDC.put("fieldUpdValue", offPro.getOffUseDepart());
					MDC.put("fieldOriValue", off.getOffUseDepart());
					MDC.put("fieldName", "使用/管理部门");
					MDC.put("objName", off.getOffName());
					MDC.put("objId", off.getOffNum());
					MDC.put("userName", user.getUserName());
					MDC.put("logType", "devRecord");
					MDC.put("objType", "办公资产");
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
			if(!offPro.getOffUser().equals("")){
				if(!off.getOffUser().equals(offPro.getOffUser())){
					MDC.put("fieldUpdValue", offPro.getOffUser());
					if(off.getOffUser() == null){
						MDC.put("fieldOriValue", "无");
					}else{
						MDC.put("fieldOriValue", off.getOffUser());
					}
					MDC.put("fieldName", "存放地点");
					MDC.put("objName", off.getOffName());
					MDC.put("objId", off.getOffNum());
					MDC.put("userName", user.getUserName());
					MDC.put("logType", "devRecord");
					MDC.put("objType", "办公资产");
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
			if(!offPro.getOffUseState().equals("")){
				if(!off.getOffUseState().equals(offPro.getOffUseState())){
					MDC.put("fieldUpdValue", offPro.getOffUseState());
					MDC.put("fieldOriValue", off.getOffUseState());
					MDC.put("fieldName", "使用状况");
					MDC.put("objName", off.getOffName());
					MDC.put("objId", off.getOffNum());
					MDC.put("userName", user.getUserName());
					MDC.put("logType", "devRecord");
					MDC.put("objType", "办公资产");
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
			if(!(offPro.getOffLifeTime() == 0)){
				if(!(off.getOffLifeTime() == offPro.getOffLifeTime())){
					MDC.put("fieldUpdValue", offPro.getOffLifeTime());
					if(off.getOffLifeTime() == 0){
						MDC.put("fieldOriValue", 0);
					}else{
						MDC.put("fieldOriValue", off.getOffLifeTime());
					}
					
					MDC.put("fieldName", "使用年限");
					MDC.put("objName", off.getOffName());
					MDC.put("objId", off.getOffNum());
					MDC.put("userName", user.getUserName());
					MDC.put("logType", "devRecord");
					MDC.put("objType", "办公资产");
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
			System.out.println(off+"--------"+offPro);
			off.setOffDevVersion(offPro.getOffDevVersion());
			off.setOffManager(offPro.getOffManager());
			off.setOffName(offPro.getOffName());
			off.setOffNum(offPro.getOffNum());
			off.setOffObtDate(offPro.getOffObtDate());
			off.setOffPrice(offPro.getOffPrice());
			off.setOffRemark(offPro.getOffRemark());
			off.setOffState(offPro.getOffState());
			off.setOffUseDepart(offPro.getOffUseDepart());
			off.setOffUser(offPro.getOffUser());
			off.setOffUseState(offPro.getOffUseState());
			off.setOffLifeTime(offPro.getOffLifeTime());
			offService.add(off);


			String success = gson.toJson("true");
			ServletActionContext.getResponse().getWriter().print(success);
			return null;
		}

	public String  sendChart()  throws IOException{

		Gson gson = new Gson();
		List<Map<String,Integer>> typeNumList = new ArrayList<Map<String,Integer>>(); 
		Map<String,Integer>  typeNum = new HashMap<String,Integer>();
		Map<String,Integer> typeState = new HashMap<String,Integer>();

		List<OffPro> offList = offService.findAll();
		typeState.put("总数", offList.size());
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
		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(typeCount);

		return null;
	}


	//生成条码： 根据offId获取条码，从而得到条码图片
	public String findBarcode() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();

		String offId = request.getParameter("offId");
		OffPro offPro = offService.findById(offId);
		String offBarCode = offPro.getOffBarCode();
		String off = gson.toJson(offBarCode);

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(off);
		return null;

	}





	public  String exportExcel() throws Exception //用于数据维护，导出全部的表
	{

		String title="资产管理表";
		String[] rowName={"设备号","条码","资产编号","名称","设备型号",
				"价格","取得日期","使用/管理部门","存放地点","责任人","备注",
				"使用状况","状态","商品序列号","更新人","更新时间","使用年限","使用标志"};
		HSSFWorkbook bookWorkbook = new HSSFWorkbook();// 创建excel文件
		HSSFSheet sheet = bookWorkbook.createSheet("0");
		HSSFCell cellTitle,cell;
		HSSFRow rowTitle,row;
		HSSFCellStyle styletitle = ect.setTableHeaderFormat(bookWorkbook);

		int rowinttitle=0;//表的第一行，写入表头
		rowTitle = sheet.createRow((short) rowinttitle);

		cellTitle = rowTitle.createCell((short) 0);
		cellTitle.setEncoding(HSSFCell.ENCODING_UTF_16);// 设置值的编码格式，确保不会出现乱码
		cellTitle.setCellValue(title);
		cellTitle.setCellStyle(styletitle);

		sheet.addMergedRegion(new Region(rowinttitle, (short) 0, rowinttitle,
				(short) 17));//合并第一行
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
		for(int k=0;k<18;k++)//一共16列
		{
			sheet.setColumnWidth((short) k, (short) 4200);
		}




		//因为表头和列头需要英文，所以需要提前固定，不能直接从数据库直接读取写入
		//因此表中的列数已经固定，为16，现在只需循环list即可


		HSSFCellStyle styleInfo = ect.setColumnDataStyle(bookWorkbook);//设置表内容的表的格式
		int rowintInfo = 2;

		List<OffPro>list=offDao.findAll();//找到表的所有内容		

		for(int i=0;i<offDao.findAll().size();i++)
		{
			//list.get(i);
			//	String mess[]=(String [])list.get(i);
			row=sheet.createRow(rowintInfo++);
			int kk =0;
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getOffId());
			kk++;
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getOffBarCode());
			kk++;
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getOffNum());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getOffName());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getOffDevVersion());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			if(list.get(i).getOffPrice()==null)
			{
				cell.setCellValue(0.0);
			}
			else
			{
				cell.setCellValue(list.get(i).getOffPrice());
			}
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(sdf.format(list.get(i).getOffObtDate()));
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getOffUseDepart());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getOffUser());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getOffManager());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getOffRemark());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getOffUseState());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getOffState());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getOffSeqNum());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLoginUserName());

			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getOffUpdTime());
			
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getOffLifeTime());
			
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).isOffFlag());
			


		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String filename=null;//作为判断表是否导出成功
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
				outputStream = new FileOutputStream(request.getSession().getServletContext().getRealPath("/")+"/Generatefile//Office property sheet.xls");
				bookWorkbook.write(outputStream);
				outputStream.flush();
				outputStream.close();
				filename="Office property sheet.xls";
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
	public  String exportExcelByConditions() throws Exception //主要用于导出当前页，没有注释的参考前一个函数
	{



		HttpServletRequest request = ServletActionContext.getRequest();
		String str1=request.getParameter("data");//用于接收当前发过来当前页的id号

		JSONObject jsonArr = JSONObject.fromObject(str1);


		String title="资产管理表-按条件导出后选择条目导出";
		String[] rowName={"设备号","条码","资产编号","名称","设备型号",
				"价格","取得日期","使用/管理部门","存放地点","责任人","备注",
				"使用状况","状态","商品序列号","更新人","更新时间","使用年限","使用标志"};
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
				(short) 17));//合并第一行
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

		for(int k=0;k<18;k++)
		{
			sheet.setColumnWidth((short) k, (short) 4200);
		}



		//因为表头和列头需要英文，所以需要提前固定，不能直接从数据库直接读取写入
		//因此表中的列数已经固定，为16，现在只需循环list即可
		//ArrayList() arrylist=new ArrayList();

		//	arrylist=list.add(getText());

		//System.out.println("++++++" + getDao.findAll());

		HSSFCellStyle styleInfo = ect.setColumnDataStyle(bookWorkbook);
		int rowintInfo = 2;

		List<String> offIds=(List<String>)jsonArr.getJSONArray("offIds");//将从前台接收的数据转换为list类型
		/*for (int i = 0; i < speIds.size(); i++) {
					System.out.println(speIds.get(i));
				}*/


		List<OffPro> list1=new ArrayList<OffPro>();//遍历找到当前页的内容
		for(int i=0;i<offIds.size();i++)
		{
			list1.add(offDao.findById(offIds.get(i)));

		}

		for(int i=0;i<list1.size();i++)
		{

			row=sheet.createRow(rowintInfo++);
			int kk =0;
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getOffId());
			kk++;
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getOffBarCode());
			kk++;
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getOffNum());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getOffName());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getOffDevVersion());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			if(list1.get(i).getOffPrice()==null)
			{
				cell.setCellValue(0.0);
			}
			else
			{
				cell.setCellValue(list1.get(i).getOffPrice());
			}
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(sdf.format(list1.get(i).getOffObtDate()));
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getOffUseDepart());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getOffUser());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getOffManager());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getOffRemark());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getOffUseState());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getOffState());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getOffSeqNum());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getLoginUserName());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getOffUpdTime());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getOffLifeTime());
			
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).isOffFlag());


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
				outputStream = new FileOutputStream(request.getSession().getServletContext().getRealPath("/")+"/Generatefile//Office assets table export current page.xls");//为有导出时路径选择框，所以如此写路径
				bookWorkbook.write(outputStream);
				outputStream.flush();
				outputStream.close();
				filename="Office assets table export current page.xls";
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
	public String exportAllExcelByConditions() throws Exception//主要用于导出全部页，没有注释的部分参考前一个函数
	{

		HttpServletRequest request = ServletActionContext.getRequest();


		String[] str=new String[4];

		str[0]=request.getParameter("offSeqNum");
		str[1]=request.getParameter("offName");
		str[2]=request.getParameter("offNum");
		str[3]=request.getParameter("offUser");

		//str[4]=request.getParameter("offName1"); 本用于筛选，但功能未实现，所以注释掉
		//str[5]=request.getParameter("offUseState1");
		//str[6]=request.getParameter("offState1");
		//str[7]=request.getParameter("offUseDepart1");
		//str[8] = request.getParameter("offManager1");


		String title="资产管理表-按条件导出";
		String[] rowName={"设备号","条码","资产编号","名称","设备型号",
				"价格","取得日期","使用/管理部门","存放地点","责任人","备注",
				"使用状况","状态","商品序列号","更新人","更新时间","使用年限","使用标志"};
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
				(short) 17));//合并第一行
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

		for(int k=0;k<18;k++)
		{
			sheet.setColumnWidth((short) k, (short) 4200);
		}



		//因为表头和列头需要英文，所以需要提前固定，不能直接从数据库直接读取写入
		//因此表中的列数已经固定，为16，现在只需循环list即可
		//ArrayList() arrylist=new ArrayList();

		//	arrylist=list.add(getText());

		//System.out.println("++++++" + getDao.findAll());

		HSSFCellStyle styleInfo = ect.setColumnDataStyle(bookWorkbook);
		int rowintInfo = 2;
		//				String[] str =new String[9];
		//				str[0]="12aa";

		Conditions conditions=new Conditions();

		conditions.addCondition("offNum", str[0], Operator.LIKE);
		conditions.addCondition("offName", str[1], Operator.LIKE);
		conditions.addCondition("offDevVersion", str[2], Operator.LIKE);
		conditions.addCondition("offSeqNum", str[3], Operator.LIKE);


		/*conditions.addCondition("offName", str[4], Operator.EQUAL);
		conditions.addCondition("offUseState", str[5], Operator.EQUAL);
		conditions.addCondition("offState", str[6], Operator.EQUAL);
		conditions.addCondition("offUseDepart", str[7], Operator.EQUAL);
		conditions.addCondition("offManager", str[8], Operator.EQUAL);
		 */
		List<OffPro> list=offDao.findByConditions(conditions);
		System.out.println(list.size()+">>>>>>>>>>>");
		for(int i=0;i<list.size();i++)
		{
			System.out.println("4444444444444444444444");
			row=sheet.createRow(rowintInfo++);
			int kk =0;
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getOffId());
			kk++;
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getOffBarCode());
			kk++;
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getOffNum());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getOffName());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getOffDevVersion());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			if(list.get(i).getOffPrice()==null)
			{
				cell.setCellValue(0.0);
			}
			else
			{
				cell.setCellValue(list.get(i).getOffPrice());
			}
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(sdf.format(list.get(i).getOffObtDate()));
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getOffUseDepart());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getOffUser());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getOffManager());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getOffRemark());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getOffUseState());
			kk++;


			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getOffState());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getOffSeqNum());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getLoginUserName());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getOffUpdTime());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).getOffLifeTime());
			
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.get(i).isOffFlag());
		}
		System.out.println("000000000000000000000");
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
			System.out.println("222222222222222222222222222222");
			try {
				outputStream = new FileOutputStream(request.getSession().getServletContext().getRealPath("/")+"/Generatefile//Office assets table by condition.xls");
				bookWorkbook.write(outputStream);
				outputStream.flush();
				outputStream.close();
				filename="Office assets table by condition.xls";
				//	return "Export Success";
			} catch (FileNotFoundException e) {
				System.err.println("获取不到位置");

			} catch (IOException e) {

			}

			System.out.println("3333333333333333333333333333333333");

		} finally{}
		Gson gson = new Gson();
		String success = gson.toJson(filename);
		ServletActionContext.getResponse().getWriter().print(success);

		return null;					

	}
	@Test
	public  String exportpartExcelByConditions() throws Exception //主要用于批量导出，没有注释的部分参考导出当前页
	{



		HttpServletRequest request = ServletActionContext.getRequest();
		String str1=request.getParameter("data");

		JSONObject jsonArr = JSONObject.fromObject(str1);


		String title="资产管理表-按条件导出后选择条目导出";
		String[] rowName={"设备号","条码","资产编号","名称","设备型号",
				"价格","取得日期","使用/管理部门","存放地点","责任人","备注",
				"使用状况","状态","商品序列号","更新人","更新时间","使用年限","使用标志"};
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
				(short) 17));//合并第一行
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

		for(int k=0;k<18;k++)
		{
			sheet.setColumnWidth((short) k, (short) 4200);
		}



		//因为表头和列头需要英文，所以需要提前固定，不能直接从数据库直接读取写入
		//因此表中的列数已经固定，为16，现在只需循环list即可
		//ArrayList() arrylist=new ArrayList();

		//	arrylist=list.add(getText());

		//System.out.println("++++++" + getDao.findAll());

		HSSFCellStyle styleInfo = ect.setColumnDataStyle(bookWorkbook);
		int rowintInfo = 2;



		List<String> offIds=(List<String>)jsonArr.getJSONArray("offIds");
		/*for (int i = 0; i < speIds.size(); i++) {
					System.out.println(speIds.get(i));
				}*/


		List<OffPro> list1=new ArrayList<OffPro>();
		for(int i=0;i<offIds.size();i++)
		{
			list1.add(offDao.findById(offIds.get(i)));

		}

		for(int i=0;i<list1.size();i++)
		{

			row=sheet.createRow(rowintInfo++);
			int kk =0;
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getOffId());
			kk++;
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getOffBarCode());
			kk++;
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getOffNum());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getOffName());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getOffDevVersion());
			kk++;



			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			if(list1.get(i).getOffPrice()==null)
			{
				cell.setCellValue(0.0);
			}
			else
			{	
				cell.setCellValue(list1.get(i).getOffPrice());
			}
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(sdf.format(list1.get(i).getOffObtDate()));
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getOffUseDepart());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getOffUser());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getOffManager());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getOffRemark());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getOffUseState());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getOffState());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getOffSeqNum());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getLoginUserName());
			kk++;

			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getOffUpdTime());
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).getOffLifeTime());
			
			kk++;
			
			cell=row.createCell((short)kk);
			cell.setCellStyle(styleInfo);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list1.get(i).isOffFlag());


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
				outputStream = new FileOutputStream(request.getSession().getServletContext().getRealPath("/")+"/Generatefile/Office assets list in accordance with the conditions of bulk export.xls");
				bookWorkbook.write(outputStream);
				outputStream.flush();
				outputStream.close();
				filename="Office assets list in accordance with the conditions of bulk export.xls";
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

	public String readExcelContent(InputStream is) {//对表格进行读取

		String succ=null;//作为判断条件，判断读取是否成功


		List<OffPro> list=new ArrayList<OffPro>();
		list=offDao.findAll();
		//		System.out.println("3333333333333333333333333");
		//		System.out.println(list.size()+"4444444444");

		String offId=null;
		String offBarCode=null;
		String offNum=null;
		String offName=null;

		String offDevVersion=null;
		String offPrice=null;
		Date offObtDate=null;
		String offUseDepart=null;

		String offUser=null;
		String offManager=null;
		String offRemark=null;
		String offUseState=null;

		String offState=null;
		String offSeqNum=null;
		String loginUserName=null;
		String offUpdTime=null;
		String offLifeTime=null; 
		String offFlag=null;

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

		System.out.println("导入的excel有"+colNum+"列。。。。。。。。。。。。。");
		// 正文内容应该从第二行开始,第一行为表头的标题
		if(colNum==18) {
			//getDao.deleteAll(list);
			// hibernateTemplate.save(excel);

			for (int i = 2; i <= rowNum; i++) {
				rowimport = sheetimport.getRow(i);
				int j = 0;


				offId= ect.getCellFormatValue(rowimport.getCell((short) j));
				offPro.setOffId(offId);
				j++;
				//Integer.parseInt(offId);
				// getDao.addOrUpdateAll(beans);

				offBarCode=ect.getCellFormatValue(rowimport.getCell((short) j));
				offPro.setOffBarCode(offBarCode);
				j++;

				offNum=ect.getCellFormatValue(rowimport.getCell((short) j));
				offPro.setOffNum(offNum);
				j++;

				offName=ect.getCellFormatValue(rowimport.getCell((short) j));
				offPro.setOffName(offName);
				j++;

				offDevVersion=ect.getCellFormatValue(rowimport.getCell((short) j));
				offPro.setOffDevVersion(offDevVersion);
				j++;

				offPrice=ect.getCellFormatValue(rowimport.getCell((short) j));
				offPro.setOffPrice(Float.parseFloat(offPrice));
				j++;

				/*offObtDate=(rowimport.getCell((short) j).getDateCellValue());
				offPro.setOffObtDate(offObtDate);
				j++;*/
				offPro.setOffObtDate(StrToDate(ect.getCellFormatValue(rowimport.getCell((short) j))));
				j++;

				offUseDepart=ect.getCellFormatValue(rowimport.getCell((short) j));
				offPro.setOffUseDepart(offUseDepart);
				j++;

				offUser=ect.getCellFormatValue(rowimport.getCell((short) j));
				offPro.setOffUser(offUser);
				j++;

				offManager=ect.getCellFormatValue(rowimport.getCell((short) j));
				offPro.setOffManager(offManager);
				j++;

				offRemark=ect.getCellFormatValue(rowimport.getCell((short) j));
				offPro.setOffRemark(offRemark);
				j++;

				offUseState=ect.getCellFormatValue(rowimport.getCell((short) j));
				offPro.setOffUseState(offUseState);
				j++;

				offState=ect.getCellFormatValue(rowimport.getCell((short) j));
				offPro.setOffState(offState);
				j++;

				offSeqNum=ect.getCellFormatValue(rowimport.getCell((short) j));
				offPro.setOffSeqNum(offSeqNum);
				j++;
	
				loginUserName=ect.getCellFormatValue(rowimport.getCell((short) j));
				offPro.setLoginUserName(loginUserName);
				j++;

				offUpdTime=ect.getCellFormatValue(rowimport.getCell((short) j));
				offPro.setOffUpdTime(offUpdTime);
				j++;
							
				offLifeTime= ect.getCellFormatValue(rowimport.getCell((short) j));		
				offPro.setOffLifeTime(Integer.valueOf(offLifeTime));
				j++;
				
				offFlag= ect.getCellFormatValue(rowimport.getCell((short) j));
				offPro.setOffFlag(offFlag);
			

				
				

				hibernateTemplate.save(offPro);

			}
			return succ="1";//导出成功
		}
		else
			return succ;
	}

	@Test 
	public String  excelToMysql() throws IOException  {//对表格进行导入，主要调用上一个函数


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
		List<OffPro> list1=offDao.findAll();





		if(readExcelContent(is2)=="1")
		{

			HttpServletResponse response  = ServletActionContext.getResponse();
			PrintWriter out = response.getWriter();
			out.print(-1);
			out.flush();
			out.close();
			try {
				offDao.deleteAll(list1);//导入之前对数据库表进行清空
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
		//	DataTablesPage<OffPro>   page = new DataTablesPage<OffPro>() ;
		HttpServletRequest request= ServletActionContext.getRequest();
		String  offObtDate  = request.getParameter("offObtDate");
		Date date = StrToDate(offObtDate);

		conditions.addCondition("offObtDate", date, Operator.LESS_EQUAL);
		String iDisplayLength = request.getParameter("iDisplayLength");
		int iDisplayLength1 = Integer.valueOf(iDisplayLength);
		System.out.println(iDisplayLength1+"---------");
		String nowPage = request.getParameter("nowPage");
		int nowPage1 = Integer.valueOf(nowPage);
		System.out.println("---------------"+nowPage1);



		DataTablesPage<OffPro>  pagaData = offService. findAll(conditions, iDisplayLength1,nowPage1) ;

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
	/**
	 * 预报警	
	 * @return
	 * @throws Exception 
	 */
		public String preDict() throws Exception{
			
		 HttpServletRequest request = ServletActionContext.getRequest();
			
			SimpleDateFormat  text = new SimpleDateFormat("yyyy-MM-dd");
	       
		
			Conditions conditions = new Conditions();
			
			String iDisplayLength  = request.getParameter("iDisplayLength");
			int   iDisplayLength1  = Integer.valueOf(iDisplayLength);
			String  nowPage = request.getParameter("nowPage");
			int    nowPage1 = Integer.valueOf(nowPage);
			Date date;
			boolean isOld=false;
			List<OffPro> offList = offService.findAll();
			
			for (OffPro offPro : offList) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.YEAR, -offPro.getOffLifeTime());
				cal.add(Calendar.DATE, 30);
				date = cal.getTime();
				System.out.println(date+"-------"+offPro.getOffLifeTime()+"-----------"+offPro.getOffObtDate());
			isOld = date.after(offPro.getOffObtDate());
			if(isOld==true){
				
				offPro.setOffFlag("true");
			}else{
				offPro.setOffFlag("false");
			}
			
			offService.add(offPro);
			}
			
			conditions.addCondition("offFlag", "true", Operator.EQUAL);
			
			DataTablesPage<OffPro> pagaData = offService.findAll(conditions, iDisplayLength1, nowPage1);
			Gson gson = new Gson();
			String data = gson.toJson(pagaData);
			
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out =response.getWriter();
			out.print(data);
			System.out.println(data+"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			out.flush();
			out.close();
			
			return null;
		}

	
	


}
