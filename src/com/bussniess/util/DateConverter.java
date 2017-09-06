package com.bussniess.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.opensymphony.xwork2.XWorkException;
import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;

public class DateConverter extends DefaultTypeConverter {

	private DateFormat[] dateFormats = {//
	new SimpleDateFormat("yyyy-MM-dd"),//
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") };

	@Override
	public Object convertValue(Map<String, Object> context, Object value, Class toType) {

		if (toType == Date.class) {
			Date date = null;
			String[] values = (String[]) value;
			String dateStr = values[0];
			for (DateFormat dateFormat : dateFormats) {
				try {
					date = dateFormat.parse(dateStr);
				} catch (Exception e) {
				}
			}
			if (date == null) {
				throw new XWorkException("Could not parse date : " + dateStr);
			}
			return date;
		} else if (toType == String.class) {
			return dateFormats[0].format(value);
		}
		return null;
	}
}
