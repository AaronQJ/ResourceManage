package com.bussniess.web.interceptor;




import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.management.relation.Role;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bussniess.dao.impl.UsersDaoImpl;
import com.bussniess.dao.util.Conditions;
import com.bussniess.dao.util.Conditions.Operator;
import com.bussniess.domain.Privileges;
import com.bussniess.domain.Roles;
import com.bussniess.domain.Users;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;


	public class AuthenticationInterceptor implements Interceptor {
		private Set<String> whitePath = new HashSet<String>();
		private static String AJAX_NO_LIMIT = "noLimit"; 
		
	    public void init() {
	    	//whitePath.add("userAction_login");
	    }
	    

	    public String intercept(ActionInvocation invocation) throws Exception {
	    	
	    	ActionContext actionContext = invocation.getInvocationContext();
	    	
	    	HttpServletRequest response = ServletActionContext.getRequest();
	    	System.out.println("000000000000" + response.getContentType());
	    	String type = response.getHeader("x-requested-with");
	    	System.out.println("++++" + type);
	    	//PrintWriter pw = response.getWriter();
	    	String flag = "";
	    	
	    	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
	    	UsersDaoImpl userDao = (UsersDaoImpl) applicationContext.getBean("userDao");
	    	
	    	
	    	
	    	String actionName = actionContext.getName();
			//System.out.println(actionName + "----------------------------------------");
			
			Users user = (Users) actionContext.getSession().get("user");
			
			Conditions conditions = new Conditions();
			
			conditions.addCondition("userName", user.getUserName(), Operator.EQUAL);
			List<Users> userInfo = userDao.findByConditions(conditions);
			
			if(userInfo == null){
				return "nopower";
			}
			
			//System.out.println(userInfo .toString());
			Roles role = userInfo.get(0).getRole();
			//System.out.println(role);
			
			//System.out.println("UserName: " + userInfo.get(0).getUserName() + " and role: " + role);
		
			
			
			//Roles role = user.getRole();
			if(role != null){
				/*System.out.println("++++++++" + role.toString());*/
				Set<Privileges> privileges = role.getPrivileges();
				System.out.println("privileges " + privileges);
				if(privileges != null){
					for(Privileges privilege : privileges){
						//System.out.println("getPath: " + privilege.getPath());
						//System.out.println("actionName：" + actionName);
						if(privilege.getPath().equals(actionName)){
							//flag="True";
							//System.out.println("Falg = " + flag);
							//pw.write(flag);
							System.out.println("放行，已执行！");
							return invocation.invoke();
						}
						
					}
				}
			}
			
			
			System.out.println("拦截成功，未执行！");
			
			if("XMLHttpRequest".equalsIgnoreCase(type)){
				 PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
				 printWriter.print(AJAX_NO_LIMIT);
				 printWriter.flush();  
				 printWriter.close();
				 
				 return null; 
			}else{
			//flag="False";
			//System.out.println("Falg = " + flag);	
			//pw.write(flag);
				return "nopower";
			
			}
			
			/*
			
			/*if(user.getUserName().equals("admin")){
				 return invocation.invoke(); 
			}*/
			
			
	    	
	    
	    	//System.out.println("user3 = " + user3.getUserName());
	    	//return invocation.invoke();
	    	/*System.out.println("Action执行前插入 代码");      
	    	//执行目标方法 (调用下一个拦截器, 或执行Action)    
	    	final String res = invocation.invoke();    
	    	System.out.println("Action执行后插入 代码");    
	    	return res; 
	    	*/
	    	
	    	//Users user = (Users) ActionContext.getContext().getSession().get("userName");
	    	
	    			
	    	/*if(user.getUserName().equals("admin")){
	    		return invocation.invoke();
	    	}else{
	    		return "success";
	    	}
	    	/*if(user.getUserName().equals("admin")){
	    		
	    	}
	    	
	       /* Object user = ActionContext.getContext().getSession().get("user");
	        // 如果user不为null,代表用户已经登录,允许执行action中的方法
	        if (user. != null){
	            return invocation.invoke(); 
	        }
	        ActionContext.getContext().put("message", "你没有权限执行该操作");
	        return "success";
	        */
	    }

	    public void destroy() {
	    }
	
}
