package com.bussniess.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import net.sourceforge.jbarcodebean.JBarcodeBean;
import net.sourceforge.jbarcodebean.model.Code39;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.bussniess.dao.impl.ProSumDaoImpl;
import com.bussniess.dao.util.Conditions;
import com.bussniess.dao.util.Conditions.Operator;
import com.bussniess.domain.ProSum;

public class BarcodeInfo<T>{
	
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
	HibernateTemplate hibernateTemplate = (HibernateTemplate) applicationContext.getBean("hibernateTemplate");
	ProSumDaoImpl proSumDao = (ProSumDaoImpl)applicationContext.getBean("proSumDao");	//getId
	
	
	public static String offTableName = "offPro";		//在pro_sum表中tableName字段的值
	public static String speTableName = "spePro";		//同上
	
	/**
	 * 自动获取条形码序号，返回一个10位的字符串，"年份（4）"+"设备类型（2）"+"id号（4）" 
	 * @param date	获取设备的时间/到货验收的时间
	 * @param k		表示资产类型，0：办公资产。1：专用资产
	 * @param s		表示设备类型
	 * @param i		表示每年该资产的流水号
	 * @return
	 */
	public String getCodeNum(String date,int k, int s, int i) {
		//将系统当前时间显示形式格式化，即将只显示年份并且后面补6个0 【该操作限定在办公资产表，所以设备类型统一规定为1X】
		//		SimpleDateFormat form = new SimpleDateFormat("yyyy000000");		
		//		int num = Integer.parseInt(form.format(date));					//将格式化后的年份（字符串）转化为int型		
		
		String barcode = date.substring(0, 4); 
		//如果是办公资产 条码第5位是0 表示办公资产， 如果是专用资产，条码第5位是1
		barcode = barcode + k;
		s *= 10000;			//s0000
		s += i;				//s+i; 构成条码的最后5位
		barcode += s;			//转化后成为yyyy0(s+i) 即为条形码序列

		return barcode;
	}
	
	

	/**
	 * 根据字符串 内容的不同 返回不同的整型值 用作条码第6位 [该方法只针对办公资产]
	 * @param name
	 * @return
	 */
	public int getOffType(String name) {
		if(name.contains("电脑") || name.contains("计算机")){
			return 1;
		}else if(name.contains("打印机")){
			return 2;
		}else if(name.contains("办公桌椅")){
			return 3;
		}else {
			return 4;
		}
	}
	
	/**
	 * 根据字符串 内容的不同 返回不同的整型值 用作条码第6位 [该方法是针对专用资产]
	 * @param name
	 * @return
	 */
	public int getSpeType(String name) {
		if(name.contains("交换机")){
			return 1;
		}else if(name.contains("路由器")){
			return 2;
		}else if(name.contains("服务器")){
			return 3;
		}else if(name.contains("定制") || name.contains("专用设备")){		//定制类设备
			return 4;	
		}else {			//其他传输设备
			return 5;
		}
	}
	
	
	
	/**
	 * 得到Id号,即获取每年设备的流水号
	 * @return
	 */
	public int getId(String str, Date date) {
		SimpleDateFormat form = new SimpleDateFormat("yyyy");		//将系统当前时间显示形式格式化，即将只显示年份并且后面补6个0 	
		//		int yea = Integer.valueOf(form.format(dt));
		int year = Integer.valueOf(form.format(date));
		int sum = 0;			//记录设备数量
		ProSum proSum = new ProSum();
		List<ProSum> list = new ArrayList<ProSum>();
		Conditions conditions = new Conditions();

		conditions.addCondition("tableName", str, Operator.EQUAL);		//在pro_sum表中查找与给出的字符串str相等的数据，即就是哪一种设备
		conditions.addCondition("year", year, Operator.EQUAL);			//在pro_sum表中查找与year2相等的数据

		list = proSumDao.findByConditions(conditions);					//list.size()要么==0 ，要么==1
		if(list.size() ==0){		//若==0
			sum++;					//设备数量加1
			proSum.setTableName(str); 		//在表中添加待添加设备的资产类型
			proSum.setSum(sum);				//在表中添加待添加的设备的数
			proSum.setYear(year);			//在表中添加待添加设备的获取年份
			hibernateTemplate.save(proSum);		//保存到数据库	
			return sum;
		}else {				//若！= 0
			sum = list.get(0).getSum()+1;		//记录当前设备的数量并加1
			list.get(0).setSum(sum);				//将原记录中的设备数量改成当前设备数量
			proSumDao.addOrUpdateAll(list);			//更新数据表
			return sum;
		}
	}

	
	/**
	 * 修改图片,返回修改后的图片缓冲区（只输出一行文本）
	 */
	public BufferedImage modifyImage(BufferedImage img, Object content, int _x, int _y) {
		Graphics2D g = null;
		Font font = new Font("楷体", Font.PLAIN, 16);// 添加字体的属性设置
		int fontsize = 0;
		int x = 0;
		int y = 0;
		try {
			int w = img.getWidth();
			int h = img.getHeight();
			g = img.createGraphics();
			g.setBackground(Color.WHITE);
			g.setColor(Color.black);//设置字体颜色
			if (font != null)
				g.setFont(font);
			// 验证输出位置的纵坐标和横坐标
			if (_x >= h || _y >= w) {
				x = h - fontsize + 2;
				y = w;
			} else {
				x = _x;
				y = _y;
			}
			if (content != null) {
				g.drawString(content.toString(), x, y);
			}
			g.dispose();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return img;
	}
	
	/**
	 * 生成条码图片
	 * @return 
	 * @throws IOException 
	 */
	public void createBarcode(String barcode, String type) throws IOException{
		//	String barcode = "2019000002";
		String s = "资产管理系统";
		JBarcodeBean jBarcodeBean = new JBarcodeBean();
		// 条形码类型
		jBarcodeBean.setCodeType(new Code39());		
		// 在条形码下面显示文字	
		jBarcodeBean.setLabelPosition(JBarcodeBean.LABEL_BOTTOM);
		//	jBarcodeBean.setShowText(true); 默认是true

		OutputStream out;
		BufferedImage image = new BufferedImage(200, 150,BufferedImage.TYPE_INT_RGB);
		HttpServletRequest request =  ServletActionContext.getRequest();
		//此行没必要存在，因为条形码已经在数据库中存在，该行是为了导入/添加时自动生成条形码
		//	String str1 = getCodeNum(text.getOffBarCode(), waterNum);				
		jBarcodeBean.setCode(barcode);
		image = jBarcodeBean.draw(image);
		image = modifyImage(image, s, 55, 35);
		image = modifyImage(image, type, 100-9*type.length(), 115);
	
		//this.getClass().getClassLoader().getResource("").getPath()
		out = new FileOutputStream(request.getSession().getServletContext().getRealPath("/")
					+"/barcodeImages/"+barcode+".jpg"); 		//将生成的条码图片保存到服务器目录下项目目录下的barcodeImages文件下
		ImageIO.write(image, "JPEG", out);

		/*String path = request.getSession().getServletContext().getRealPath("/")
					+"\\barcodeImages\\"+offBarCode+".jpg";
		*/		

	}

}
