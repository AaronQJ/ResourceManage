package com.bussniess.web.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		HttpSession  session = sessionEvent.getSession();
		session.removeAttribute("speRoomFrameList");
		session.removeAttribute("speUserNameList");
		session.removeAttribute("speRoomList");
		session.removeAttribute("speDevNameList");
		session.removeAttribute("speStateList");
		/**
		 * 	session.setAttribute("speRoomFrameList",speRoomFrameList );
	session.setAttribute("speUserNameList", speUserNameList);
	session.setAttribute("speRoomList", speRoomList);
	session.setAttribute("speDevNameList", speDevNameList);
	session.setAttribute("speStateList", speStateList);
		 * 
		 * 
		 * 
		 * 
		 */
		
	}

}
