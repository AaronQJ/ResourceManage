package com.bussniess.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.struts2.ServletActionContext;

public class ExcelCellType<T> {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 根据HSSFCell类型设置数据
	 * @param cell
	 * @return
	 */
	public String getCellFormatValue(HSSFCell cell) {
		String cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			// 如果当前Cell的Type为NUMERIC
			case HSSFCell.CELL_TYPE_NUMERIC: {
				// 判断当前的cell是否为Date
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// 如果是Date类型则，转化为Data格式

					//方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
					//cellvalue = cell.getDateCellValue().toLocaleString();

					//方法2：这样子的data格式是不带带时分秒的：2011-10-12
					Date date = cell.getDateCellValue();

					cellvalue = sdf.format(date);
				} else
					//如果是数字的话，需要进一步判断是否是整数 还是小数
					if(cell.getNumericCellValue() - (int)cell.getNumericCellValue() == 0) {	//说明是整数
						cellvalue = String.valueOf((int)cell.getNumericCellValue());
					} else {	//说明不是整数
						cellvalue = String.valueOf(cell.getNumericCellValue());
					}
				break;
			}
			// 如果当前Cell的Type为STRIN
			case HSSFCell.CELL_TYPE_STRING:
				// 取得当前的Cell字符串
				cellvalue = cell.getStringCellValue();
				break;
				// 默认的Cell值
			default:
				cellvalue = " ";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;

	}

	public String getXCellFormatValue(Cell cell) {
		String cellValue = null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:							//文本
			cellValue = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:						//数字、日期
			if(DateUtil.isCellDateFormatted(cell)) {
				cellValue = sdf.format(cell.getDateCellValue());			//日期
			}else {
				cellValue = String.valueOf(cell.getNumericCellValue());		//数字
			}
			break;
		case Cell.CELL_TYPE_BOOLEAN:						//布尔型
			cellValue = String.valueOf(cell.getBooleanCellValue());  
			break;  
		case Cell.CELL_TYPE_BLANK: 						//空白  
			cellValue = cell.getStringCellValue();  
			break;  
		case Cell.CELL_TYPE_ERROR: 						//错误  
			cellValue = " ";  
			break;  
		case Cell.CELL_TYPE_FORMULA: 						//公式  
			cellValue = " ";  
			break;  
		default:  
			cellValue = " ";  
		}
		return cellValue;	
	}



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



	//判断日期格式
	public boolean isValidDate(String str) {
		boolean convertSuccess=true;
		// 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			// 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			// e.printStackTrace();
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			convertSuccess=false;
		} 
		return convertSuccess;
	}
	String fileFileName = null;
	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	//生成excel文件，通过shet将第i行数据的colNums列值 按rowStart递增地拷贝到sheet里，保存到bookWorkbook中
	public String creatExcel(int rowNum, int colNum, HSSFWorkbook bookWorkbook, HSSFSheet shet, HSSFSheet sheet, int rowStart) throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HSSFRow row, row2;
		HSSFCell cell;
		row  = shet.getRow(rowNum);					//获取传过来的行值
		row2 = sheet.createRow(rowStart);			//新建excel的行
		System.out.println(rowNum+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		for(int i = 0; i < colNum; i++)
		{		
			cell = row2.createCell((short)i);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(getCellFormatValue(row.getCell((short) i)));
		}

		FileOutputStream outputStream;
	
		try {
			outputStream = new FileOutputStream(request.getSession().getServletContext().getRealPath("/")+"/Generatefile//DataError.xls");//同上
			bookWorkbook.write(outputStream);
			outputStream.flush();
			outputStream.close();
			fileFileName="DataError.xls";
		} catch (FileNotFoundException e) {
			System.err.println("获取不到位置");

		} catch (IOException e) {

		}


		return null;
	}



}
