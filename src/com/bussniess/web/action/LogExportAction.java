package com.bussniess.web.action;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts2.ServletActionContext;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;





import com.bussniess.dao.impl.logTextDaoImpl;
import com.bussniess.dao.util.Conditions;
import com.bussniess.dao.util.Conditions.Operator;
import com.bussniess.domain.logText;
import com.bussniess.service.logTextService;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class LogExportAction extends ActionSupport implements ModelDriven<logText> {

	private logText logtext=new logText();
	private logTextService logtextService;
	
	 private POIFSFileSystem fsimport;
	 private HSSFWorkbook wbimport;
	 private HSSFSheet sheetimport;
	 private HSSFRow rowimport;
	 
	 ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
	 HibernateTemplate hibernateTemplate = (HibernateTemplate) applicationContext.getBean("hibernateTemplate");
	 logTextDaoImpl logtextDao = (logTextDaoImpl) applicationContext.getBean("logTextDao");
	 
	 public logText getModel() {
			return logtext;
		}

		public logText getText() {
			return logtext;
		}

		public logText getTextService() {
			return logtext;
		}

		public void setText(logText logtext) {
			this.logtext = logtext;
		}
		

		/*
		 * 列头单元格样式
		  */
		public HSSFCellStyle setTableHeaderFormat(HSSFWorkbook workbook) {
			//设置字体 
			HSSFFont font= workbook.createFont(); //设置字体 
			font.setFontHeightInPoints((short)44);  //设置字体大小
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//字体加粗
			font.setFontName("Courier New"); //设置字体的名字
			HSSFCellStyle style = workbook.createCellStyle();  //设置样式
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);//设置底边框
			style.setBottomBorderColor(HSSFColor.BLACK.index);//设置底边框的颜色
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN); //设置左边框
			style.setLeftBorderColor(HSSFColor.BLACK.index);//设置做边框的颜色
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);  //设置右边框
			style.setRightBorderColor(HSSFColor.BLACK.index); //设置右边框的颜色
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);  //设置顶边框
			style.setTopBorderColor(HSSFColor.BLACK.index);//设置顶边框的颜色
			style.setFont(font);  
			style.setWrapText(false);  //设置自动换行
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 设置水平对齐的样式为居中对齐
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //设置水平对齐的样式为居中对齐
			
			return style;
		
		}
		
		/*
		 *列数据信息单元的样式
		 */
		
		public HSSFCellStyle setColumnDataStyle(HSSFWorkbook workbook) 
		{
			HSSFFont font = workbook.createFont(); //设置字体 
			font.setFontHeightInPoints((short)10);
			font.setFontName("Courier New"); 
			 HSSFCellStyle style = workbook.createCellStyle(); //设置样式
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);//设置底边框
			style.setBottomBorderColor(HSSFColor.BLACK.index);//设置底边框的颜色
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN); //设置左边框
			style.setLeftBorderColor(HSSFColor.BLACK.index);//设置做边框的颜色
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);  //设置右边框
			style.setRightBorderColor(HSSFColor.BLACK.index); //设置右边框的颜色
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);  //设置顶边框
			style.setTopBorderColor(HSSFColor.BLACK.index);//设置顶边框的颜色
			// 背景色的设定
			style.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
					// 前景色的设定
			style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
					// 填充模式
			style.setFillPattern(HSSFCellStyle.FINE_DOTS);
			style.setFont(font);  
			style.setWrapText(false);  //设置自动换行
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 设置水平对齐的样式为居中对齐
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //设置水平对齐的样式为居中对齐
			
			return style;
		}
		@Test
		public  String  exportExcel() throws Exception 
		{
			String title="日志表";
			String[] rowName={"日志号","操作人","操作时间","字段名","字段原值","字段更新值","操作类型","对象类型","日志类型","对象名称"};
			HSSFWorkbook bookWorkbook = new HSSFWorkbook();// 创建excel文件
			HSSFSheet sheet = bookWorkbook.createSheet("0");
			HSSFCell cellTitle,cell;
			HSSFRow rowTitle,row;
			HSSFCellStyle styletitle = setTableHeaderFormat(bookWorkbook);
			
			int rowinttitle=0;
			rowTitle = sheet.createRow((short) rowinttitle);
			cellTitle = rowTitle.createCell((short) 0);
			cellTitle.setEncoding(HSSFCell.ENCODING_UTF_16);// 设置值的编码格式，确保不会出现乱码
			cellTitle.setCellValue(title);
			cellTitle.setCellStyle(styletitle);
			sheet.addMergedRegion(new Region(rowinttitle, (short) 0, rowinttitle,
					(short) 9));//合并第一行
			HSSFCellStyle style = setColumnDataStyle(bookWorkbook);
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
			for(int k=0;k<10;k++)//一共10列
			{
				sheet.setColumnWidth((short) k, (short) 4200);
			}
			HSSFCellStyle styleInfo = setColumnDataStyle(bookWorkbook);
			int rowintInfo = 2;
			int count=0;
			List<logText> list=logtextDao.findAll();
			for(int i=0;i<list.size();i++)
			{
				row=sheet.createRow(rowintInfo++);
				int kk =0;
				cell=row.createCell((short)kk);
				cell.setCellStyle(styleInfo);
				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(list.get(i).getLogId());
				kk++;
				
				cell=row.createCell((short)kk);
				cell.setCellStyle(styleInfo);
				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(list.get(i).getUserName());
				kk++;
				
				cell=row.createCell((short)kk);
				cell.setCellStyle(styleInfo);
				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(list.get(i).getOperTime());
				kk++;
				
				cell=row.createCell((short)kk);
				cell.setCellStyle(styleInfo);
				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(list.get(i).getFieldName());
				kk++;
				
				cell=row.createCell((short)kk);
				cell.setCellStyle(styleInfo);
				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(list.get(i).getFieldOriValue());
				kk++;
				
				cell=row.createCell((short)kk);
				cell.setCellStyle(styleInfo);
				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(list.get(i).getFieldUpdValue());
				kk++;
				
				cell=row.createCell((short)kk);
				cell.setCellStyle(styleInfo);
				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(list.get(i).getOperType());
				kk++;
				
				cell=row.createCell((short)kk);
				cell.setCellStyle(styleInfo);
				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(list.get(i).getObjType());
				kk++;
				
				cell=row.createCell((short)kk);
				cell.setCellStyle(styleInfo);
				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(list.get(i).getLogType());
				kk++;
				
				cell=row.createCell((short)kk);
				cell.setCellStyle(styleInfo);
				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(list.get(i).getObjName());
				
			}
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
					outputStream = new FileOutputStream("D://日志信息表.xls");
					bookWorkbook.write(outputStream);
					outputStream.flush();
					outputStream.close();
					return "Export Success";
				} catch (FileNotFoundException e) {
					System.err.println("获取不到位置");
				//	e.printStackTrace();
					return "notfoundlujing";
				} catch (IOException e) {
					// TODO Auto-generated catch block
				//	e.printStackTrace();
					return "Sorry,exportfailure";
				}
			
			} finally{}
			
		
		}
		
		@Test
		public String exportExcelByConditions() throws Exception//用于导出当前 页
		{
			
			HttpServletRequest request = ServletActionContext.getRequest();
			String str1=request.getParameter("data");
			
			JSONObject jsonArr = JSONObject.fromObject(str1);
	
			
		
			
						
			String title="日志表";
			String[] rowName={"日志号","操作人","操作时间","字段名","字段原值","字段更新值","操作类型","对象类型","日志类型","对象名称"};
			HSSFWorkbook bookWorkbook = new HSSFWorkbook();// 创建excel文件
			HSSFSheet sheet = bookWorkbook.createSheet("0");
			HSSFCell cellTitle,cell;
			HSSFRow rowTitle,row;
			HSSFCellStyle styletitle = setTableHeaderFormat(bookWorkbook);
			
			int rowinttitle=0;
			rowTitle = sheet.createRow((short) rowinttitle);
			cellTitle = rowTitle.createCell((short) 0);
			cellTitle.setEncoding(HSSFCell.ENCODING_UTF_16);// 设置值的编码格式，确保不会出现乱码
			cellTitle.setCellValue(title);
			cellTitle.setCellStyle(styletitle);
			sheet.addMergedRegion(new Region(rowinttitle, (short) 0, rowinttitle,
					(short) 9));//合并第一行
			HSSFCellStyle style = setColumnDataStyle(bookWorkbook);
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
			for(int k=0;k<10;k++)//一共10列
			{
				sheet.setColumnWidth((short) k, (short) 4200);
			}
			HSSFCellStyle styleInfo = setColumnDataStyle(bookWorkbook);
			int rowintInfo = 2;
			int count=0;
			List<String> logIds=(List<String>)jsonArr.getJSONArray("logIds");
			List<logText> list=new ArrayList<logText>();
			
			for(int i=0;i<logIds.size();i++)
			{
				list.add(logtextDao.findById(Integer.parseInt(logIds.get(i))));
				System.out.println(logIds.get(i));
					
			}
				
				
					for(int i=0;i<list.size();i++)
					{
						row=sheet.createRow(rowintInfo++);
						int kk =0;
						cell=row.createCell((short)kk);
						cell.setCellStyle(styleInfo);
						cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell.setCellValue(list.get(i).getLogId());
						kk++;
						
						cell=row.createCell((short)kk);
						cell.setCellStyle(styleInfo);
						cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell.setCellValue(list.get(i).getUserName());
						kk++;
						
						cell=row.createCell((short)kk);
						cell.setCellStyle(styleInfo);
						cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell.setCellValue(list.get(i).getOperTime());
						kk++;
						
						cell=row.createCell((short)kk);
						cell.setCellStyle(styleInfo);
						cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell.setCellValue(list.get(i).getFieldName());
						kk++;
						
						cell=row.createCell((short)kk);
						cell.setCellStyle(styleInfo);
						cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell.setCellValue(list.get(i).getFieldOriValue());
						kk++;
						
						cell=row.createCell((short)kk);
						cell.setCellStyle(styleInfo);
						cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell.setCellValue(list.get(i).getFieldUpdValue());
						kk++;
						
						cell=row.createCell((short)kk);
						cell.setCellStyle(styleInfo);
						cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell.setCellValue(list.get(i).getOperType());
						kk++;
						
						cell=row.createCell((short)kk);
						cell.setCellStyle(styleInfo);
						cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell.setCellValue(list.get(i).getObjType());
						kk++;
						
						cell=row.createCell((short)kk);
						cell.setCellStyle(styleInfo);
						cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell.setCellValue(list.get(i).getLogType());
						kk++;
						
						cell=row.createCell((short)kk);
						cell.setCellStyle(styleInfo);
						cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell.setCellValue(list.get(i).getObjName());
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
						outputStream = new FileOutputStream(request.getSession().getServletContext().getRealPath("/")+"/Generatefile//Log table export the current page.xls");
						bookWorkbook.write(outputStream);
						outputStream.flush();
						outputStream.close();
						filename="Log table export the current page.xls";
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
		public String exportAllExcelByConditions() throws Exception//用于导出当前 页
		{
			
			HttpServletRequest request = ServletActionContext.getRequest();
			
			String[] str=new String[5];
			
			str[0]=request.getParameter("objName");
			str[1]=request.getParameter("userName");
			str[2]=request.getParameter("logType");
			str[3]=request.getParameter("startTime");
			str[4]=request.getParameter("endTime");
			
		
			//////////
			
			for(int i=0;i<5;i++)
			{
				System.out.println(str[i]);
				System.out.println(">>>>>>>>>>>>>>>>");
			}
	
			String title="日志表";
			String[] rowName={"日志号","操作人","操作时间","字段名","字段原值","字段更新值","操作类型","对象类型","日志类型","对象名称"};
			HSSFWorkbook bookWorkbook = new HSSFWorkbook();// 创建excel文件
			HSSFSheet sheet = bookWorkbook.createSheet("0");
			HSSFCell cellTitle,cell;
			HSSFRow rowTitle,row;
			HSSFCellStyle styletitle = setTableHeaderFormat(bookWorkbook);
			
			int rowinttitle=0;
			rowTitle = sheet.createRow((short) rowinttitle);
			cellTitle = rowTitle.createCell((short) 0);
			cellTitle.setEncoding(HSSFCell.ENCODING_UTF_16);// 设置值的编码格式，确保不会出现乱码
			cellTitle.setCellValue(title);
			cellTitle.setCellStyle(styletitle);
			sheet.addMergedRegion(new Region(rowinttitle, (short) 0, rowinttitle,
					(short) 9));//合并第一行
			HSSFCellStyle style = setColumnDataStyle(bookWorkbook);
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
			for(int k=0;k<10;k++)//一共10列
			{
				sheet.setColumnWidth((short) k, (short) 4200);
			}
			HSSFCellStyle styleInfo = setColumnDataStyle(bookWorkbook);
			int rowintInfo = 2;
			int count=0;
			Conditions conditions=new Conditions();
			conditions.addCondition("objName", str[0], Operator.LIKE);
			conditions.addCondition("userName", str[1], Operator.LIKE);
			conditions.addCondition("logType",str[2], Operator.LIKE);
			if(!(str[3] == null)){
				conditions.addCondition("operTime",str[3], Operator.GREATER_EQUAL);
				}
				if(!(str[4] == null)){
				conditions.addCondition("operTime",str[4], Operator.LESS_EQUAL);
				}
				
				List<logText> list=logtextDao.findByConditions(conditions);
				for(int i=0;i<list.size();i++)
				{
					row=sheet.createRow(rowintInfo++);
					int kk =0;
					cell=row.createCell((short)kk);
					cell.setCellStyle(styleInfo);
					cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(list.get(i).getLogId());
					kk++;
					
					cell=row.createCell((short)kk);
					cell.setCellStyle(styleInfo);
					cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(list.get(i).getUserName());
					kk++;
					
					cell=row.createCell((short)kk);
					cell.setCellStyle(styleInfo);
					cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(list.get(i).getOperTime());
					kk++;
					
					cell=row.createCell((short)kk);
					cell.setCellStyle(styleInfo);
					cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(list.get(i).getFieldName());
					kk++;
					
					cell=row.createCell((short)kk);
					cell.setCellStyle(styleInfo);
					cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(list.get(i).getFieldOriValue());
					kk++;
					
					cell=row.createCell((short)kk);
					cell.setCellStyle(styleInfo);
					cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(list.get(i).getFieldUpdValue());
					kk++;
					
					cell=row.createCell((short)kk);
					cell.setCellStyle(styleInfo);
					cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(list.get(i).getOperType());
					kk++;
					
					cell=row.createCell((short)kk);
					cell.setCellStyle(styleInfo);
					cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(list.get(i).getObjType());
					kk++;
					
					cell=row.createCell((short)kk);
					cell.setCellStyle(styleInfo);
					cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(list.get(i).getLogType());
					kk++;
					
					cell=row.createCell((short)kk);
					cell.setCellStyle(styleInfo);
					cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(list.get(i).getObjName());
					
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
						outputStream = new FileOutputStream(request.getSession().getServletContext().getRealPath("/")+"/Generatefile//Log table to export all pages.xls");
						bookWorkbook.write(outputStream);
						outputStream.flush();
						outputStream.close();
						filename="Log table to export all pages.xls";
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
}
