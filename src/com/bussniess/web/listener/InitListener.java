package com.bussniess.web.listener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bussniess.dao.impl.logTextDaoImpl;
import com.bussniess.domain.LinInf;
import com.bussniess.domain.OffPro;
import com.bussniess.domain.ProBak;
import com.bussniess.domain.SpePro;
import com.bussniess.domain.logText;
import com.bussniess.service.ElecTextService;
import com.bussniess.service.LinInfService;
import com.bussniess.service.OffProService;
import com.bussniess.service.ProBakService;
import com.bussniess.service.SpeProService;


/**
 * 
 * @author 高升
 * 初始化加载，完成添加模块里面可选项的获取
 *
 */
public class InitListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
		
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
		SpeProService  speService = (SpeProService) applicationContext.getBean("speService");
		ProBakService  bakService = (ProBakService) applicationContext.getBean("bakService");
		OffProService  offService = (OffProService) applicationContext.getBean("offService");
		LinInfService  infoService = (LinInfService) applicationContext.getBean("infoService");
		logTextDaoImpl logDao = (logTextDaoImpl) applicationContext.getBean("logTextDao");
		//备品备件初始化
		System.out.println(speService+"----+++++aaa");
		List<String> speDevRoomList  =  new ArrayList<String>();
		List<String> speDevRoomFrameList  =  new ArrayList<String>();
		List<String> speUserList  =  new ArrayList<String>();
		List<String> speAssetsManaDepartList  =  new ArrayList<String>();
		List<String> speManaDepartList  =  new ArrayList<String>();
		List<String> speUseDepartList  =  new ArrayList<String>();
		List<String> speOwnNetList  =  new ArrayList<String>();
		List<String> logDevName  =  new ArrayList<String>();
		List<String> logOpertor  =  new ArrayList<String>();
	
		List<logText> log =  logDao.findAll();
		System.out.println(log.toString()+"+++++++++=======");
		for(logText Log: log){
			logDevName.add(Log.getObjName());
		}
		Set<String> logDevNameSet = new HashSet<String>(logDevName);
		logDevName.clear();
		logDevName.addAll(logDevNameSet);

		for(logText Log: log){
			logOpertor.add(Log.getUserName());
		}
		Set<String> logOpertorSet = new HashSet<String>(logOpertor);
		logOpertor.clear();
		logOpertor.addAll(logOpertorSet);
		
		servletContextEvent.getServletContext().setAttribute("logdevname",logDevName );
		servletContextEvent.getServletContext().setAttribute("logopertor", logOpertor);
		
		List<SpePro> speList = speService.findAll();
		System.out.println(speList.toString()+"+++++++++=======");
		for(SpePro spe: speList){
			speDevRoomList.add(spe.getSpeDevRoom());
		}
		Set<String> speDevRoomSet = new HashSet<String>(speDevRoomList);
		speDevRoomList.clear();
		speDevRoomList.addAll(speDevRoomSet);
		
		System.out.println(speDevRoomList.toString());
		
		
		
		for(SpePro spe: speList){
			speDevRoomFrameList.add(spe.getSpeDevRoomFrame());
		}
		Set<String> speDevRoomFrameSet = new HashSet<String>(speDevRoomFrameList);
		speDevRoomFrameList.clear();
		speDevRoomFrameList.addAll(speDevRoomFrameSet);
		
		for(SpePro spe: speList){
			speUserList.add(spe.getSpeUser());
		}
		Set<String> speUserSet = new HashSet<String>(speUserList);
		speUserList.clear();
		speUserList.addAll(speUserSet);
		
		for(SpePro spe: speList){
			speAssetsManaDepartList.add(spe.getSpeAssetsManaDepart());
		}
		Set<String> speAssetsManaDepartSet = new HashSet<String>(speAssetsManaDepartList);
		speAssetsManaDepartList.clear();
		speAssetsManaDepartList.addAll(speAssetsManaDepartSet);
		
		for(SpePro spe: speList){
			speUseDepartList.add(spe.getSpeUseDepart());
		}
		Set<String> speUseDepartSet = new HashSet<String>(speUseDepartList);
		speUseDepartList.clear();
		speUseDepartList.addAll(speUseDepartSet);
	
		
		for(SpePro spe: speList){
			speDevRoomList.add(spe.getSpeDevRoom());
		}
		Set<String> speManaDepartSet = new HashSet<String>(speManaDepartList);
		speManaDepartList.clear();
		speManaDepartList.addAll(speManaDepartSet);
		
		for(SpePro spe: speList){
			speDevRoomList.add(spe.getSpeDevRoom());
		}
		Set<String> speOwnNetSet = new HashSet<String>(speOwnNetList);
		speOwnNetList.clear();
		speOwnNetList.addAll(speOwnNetSet);
		
		
		servletContextEvent.getServletContext().setAttribute("speDevRoomList1", speDevRoomList);
		servletContextEvent.getServletContext().setAttribute("speDevRoomFrameList1", speDevRoomFrameList);
		servletContextEvent.getServletContext().setAttribute("speUserList1", speUserList);
		servletContextEvent.getServletContext().setAttribute("speAssetsManaDepartList1", speAssetsManaDepartList);
		servletContextEvent.getServletContext().setAttribute("speManaDepartList1", speManaDepartList);
		servletContextEvent.getServletContext().setAttribute("speUseDepartList1", speUseDepartList);
		servletContextEvent.getServletContext().setAttribute("speOwnNetList1", speOwnNetList);
		
		
		/**
		 * bakManaDepartList
		 * bakUseDepartList
		 * bakUserList
		 * bakDevRoomList
		 * bakDevFrameList
		 * 
		 */
		
		List<ProBak> bakList = bakService.findAll();
		System.out.println(bakList+"+++++++++=======");
		
		List<String> bakManaDepartList  =  new ArrayList<String>();
		for(ProBak bak: bakList){
			bakManaDepartList.add(bak.getBakManaDepart());
		}
		Set<String> bakManaDepartSet = new HashSet<String>(bakManaDepartList);
		bakManaDepartList.clear();
		bakManaDepartList.addAll(bakManaDepartSet);
		System.out.println(bakManaDepartList.toString()+"asdfgg-----------------");
		
		List<String> bakUseDepartList  =  new ArrayList<String>();
		for(ProBak bak: bakList){
			bakUseDepartList.add(bak.getBakUseDepart());
		}
		Set<String> bakUseDepartSet = new HashSet<String>(bakUseDepartList);
		bakUseDepartList.clear();
		bakUseDepartList.addAll(bakUseDepartSet);
		System.out.println(bakUseDepartList.toString()+"asdfgg-----------------");
		
		List<String> bakUserList  =  new ArrayList<String>();
		for(ProBak bak: bakList){
			bakUserList.add(bak.getBakUser());
		}
		Set<String> bakUserSet = new HashSet<String>(bakUserList);
		bakUserList.clear();
		bakUserList.addAll(bakUserSet);
		System.out.println(bakUserList.toString()+"asdfgg-----------------");
		
		
		List<String> bakDevRoomList  =  new ArrayList<String>();
		for(ProBak bak: bakList){
			bakDevRoomList.add(bak.getBakDevRoom());
		}
		Set<String> bakDevRoomSet = new HashSet<String>(bakDevRoomList);
		bakDevRoomList.clear();
		bakDevRoomList.addAll(bakDevRoomSet);
		System.out.println(bakDevRoomList.toString()+"asdfgg-----------------");
		
		List<String> bakDevFrameList  =  new ArrayList<String>();
		for(ProBak bak: bakList){
			bakDevFrameList.add(bak.getBakDevFrame());
		}
		Set<String> bakDevFrameSet = new HashSet<String>(bakDevFrameList);
		bakDevFrameList.clear();
		bakDevFrameList.addAll(bakDevFrameSet);
		System.out.println(bakDevFrameList.toString()+"asdfgg-----------------");
		
		List<String> bakDevTypeList  =  new ArrayList<String>();
		for(ProBak bak: bakList){
			bakDevTypeList.add(bak.getBakDevType());
		}
		Set<String> bakDevTypeSet = new HashSet<String>(bakDevTypeList);
		bakDevTypeList.clear();
		bakDevTypeList.addAll(bakDevTypeSet);
		System.out.println(bakDevTypeList.toString()+"asdfgg-----------------");
		
	
		servletContextEvent.getServletContext().setAttribute("bakManaDepartList1", bakManaDepartList);
		servletContextEvent.getServletContext().setAttribute("bakUseDepartList1", bakUseDepartList);
		servletContextEvent.getServletContext().setAttribute("bakUserList1", bakUserList);
		servletContextEvent.getServletContext().setAttribute("bakDevRoomList1", bakDevRoomList);
		servletContextEvent.getServletContext().setAttribute("bakDevFrameList1", bakDevFrameList);
		servletContextEvent.getServletContext().setAttribute("bakDevTypeList1", bakDevTypeList);
		
		
		/**
		 * offManageList
		 * offUseDepartList
		 * 
		 */

		

		List<OffPro> offList = offService.findAll();
		System.out.println(offList+"+++++++++=======");
		
		List<String> offManageList  =  new ArrayList<String>();
		for(OffPro off: offList){
			offManageList.add(off.getOffManager());
		}
		Set<String> offManageSet = new HashSet<String>(offManageList);
		offManageList.clear();
		offManageList.addAll(offManageSet);
		System.out.println(offManageList.toString()+"asdfgg-----------------");
		
		List<String> offUseDepartList  =  new ArrayList<String>();
		for(OffPro off: offList){
			offUseDepartList.add(off.getOffUseDepart());
		}
		Set<String> offUseDepartSet = new HashSet<String>(offUseDepartList);
		offUseDepartList.clear();
		offUseDepartList.addAll(offUseDepartSet);
		
		List<String> offUserList  =  new ArrayList<String>();
		for(OffPro off: offList){
			offUserList.add(off.getOffUser());
		}
		Set<String> offUserSet = new HashSet<String>(offUserList);
		offUserList.clear();
		offUserList.addAll(offUserSet);
		
		System.out.println(offUseDepartList.toString()+"asdfgg-----------------");
		servletContextEvent.getServletContext().setAttribute("offManageList1", offManageList);
		servletContextEvent.getServletContext().setAttribute("offUseDepartList1", offUseDepartList);
		servletContextEvent.getServletContext().setAttribute("offUserList1", offUserList);
		//线路管理
		List<LinInf> lineList = infoService.findAll();
		List<String> lineOperatorList  =  new ArrayList<String>();
		for(LinInf line: lineList){
			lineOperatorList.add(line.getLineOperator());
		}
		Set<String> lineOperatorSet = new HashSet<String>(lineOperatorList);
		lineOperatorList.clear();
		lineOperatorList.addAll(lineOperatorSet);
		System.out.println(lineOperatorList.toString()+"asdfgg-----------------");
		servletContextEvent.getServletContext().setAttribute("lineOperatorList1", lineOperatorList);
		
		
	}


}

