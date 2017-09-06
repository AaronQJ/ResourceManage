package com.bussniess.util;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class PoiUtil {

	// String[] -- 对应 一行
	// String[][] -- 对应一个二维表
	// 把数据写入到excel对象中,并导出到输出流中
	// 默认规则 : 列宽:5000, 表头行的样式是字体加粗
	public static void exportExcel(String[][] data, OutputStream out) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFSheet sheet = workbook.createSheet();

		String[] titleData = data[0];

		// 设置列宽
		for (int i = 0; i < titleData.length; i++) {
			sheet.setColumnWidth((short) i, (short) 5000);
		}

		// 写入第一行数据并设置字体加粗样式
		HSSFCellStyle titleCellStyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		titleCellStyle.setFont(font);

		int rowIndex = 0;
		int cellIndex = 0;
		HSSFRow titleRow = sheet.createRow(rowIndex);
		rowIndex++;

		for (String string : titleData) {
			HSSFCell cell = titleRow.createCell((short) cellIndex);
			cellIndex++;

			cell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);
			cell.setCellValue(string);
			cell.setCellStyle(titleCellStyle);
		}

		// 数据行
		for (int i = 1; i < data.length; i++) {
			cellIndex = 0;

			String[] rowData = data[i];
			HSSFRow row = sheet.createRow(rowIndex);
			rowIndex++;

			for (String string : rowData) {

				HSSFCell cell = row.createCell((short) cellIndex);
				cellIndex++;

				cell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);
				cell.setCellValue(string);
			}
		}

		// 把excel对象的数据写入到输出流中
		workbook.write(out);
		out.flush();

	}
}
