package com.bussniess.web.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.struts2.ServletActionContext;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bussniess.dao.impl.PrivilegesDaoImpl;
import com.bussniess.dao.impl.RolesDaoImpl;
import com.bussniess.dao.impl.UsersDaoImpl;
import com.bussniess.dao.util.Conditions;
import com.bussniess.dao.util.Conditions.Operator;
import com.bussniess.domain.Roles;
import com.bussniess.domain.Users;
import com.bussniess.service.UsersService;
import com.bussniess.util.Md5Utils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;





@SuppressWarnings("serial")
public class UsersAction extends ActionSupport implements ModelDriven<Users> {
	private static  Logger log =  Logger.getLogger(UsersAction.class);
	private Users user = new Users();
	private UsersService userService;
	private String checkNumber;
	
	private String result;
	
	List<Users> rootList = new ArrayList<>();
	List<Users> adminList = new ArrayList<>();
	List<Users> comUserList = new ArrayList<>();
	List<String> departList = new ArrayList<>();
	
	


	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
//	HttpServletResponse response = ServletActionContext.getResponse();
	UsersDaoImpl userDao = (UsersDaoImpl) applicationContext.getBean("userDao");
	RolesDaoImpl roleDao = (RolesDaoImpl) applicationContext.getBean("roleDao");
	PrivilegesDaoImpl privilegeDao = (PrivilegesDaoImpl) applicationContext.getBean("privilegeDao");

	public String usersAction_login_checkCode(){
		String sessionCheckNumber = (String) ServletActionContext.getRequest().getSession().getAttribute("checkNumber");
		//System.out.println("sessionCheckNumber : " + sessionCheckNumber);
		//System.out.println("checkNumber : " + checkNumber);
		if (!sessionCheckNumber.equals(checkNumber)) {
			result = "No";
		}else{
			result = "Yes";
		}	
		
		return "success";
	}
	public String usersAction_userNameCheck(){
		Conditions conditions = new Conditions();
		conditions.addCondition("userName",user.getUserName(),Operator.EQUAL);
		conditions.addCondition("pwd",Md5Utils.md5(user.getPwd()), Operator.EQUAL);
		List<Users> userList = userDao.findByConditions(conditions);
		System.out.println("++++++++++++++++++++++++++" + userList.size());
		if(CollectionUtils.isNotEmpty(userList)) {
			user.setRoleName(userList.get(0).getRole().getRoleName());
			//System.out.println(userList.get(0).getRole().getRoleName());
			ServletActionContext.getRequest().getSession().setAttribute("user",user);
			result = "Yes";
		}else{
			result = "No";
		}		
		
		return "success";
	}
	
	public String usersAction_login(){//登录验证	
		ServletActionContext.getRequest().getSession().setAttribute("user",user);
		/*String sessionCheckNumber = (String) ServletActionContext.getRequest().getSession().getAttribute("checkNumber");
		//System.out.println("sessionCheckNumber : " + sessionCheckNumber);
		//System.out.println("checkNumber : " + checkNumber);
		if (!sessionCheckNumber.equals(checkNumber)) {
			addActionError("验证码错误");
			return "Login_Failed";
		}else{
			addActionMessage("正确");
		}
		
		// 数据有效性检查
		if (user.getUserName() == null || user.getUserName().trim().length() == 0) {
			addActionError("账号不能为空");
			return "Login_Failed";
		}
		if (user.getPwd() == null || user.getPwd().trim().length() == 0) {
			addActionError("密码不能为空");
			return "Login_Failed";
		}		
*/
		Conditions conditions = new Conditions();

		conditions.addCondition("userName",user.getUserName(),Operator.EQUAL);
		conditions.addCondition("pwd",Md5Utils.md5(user.getPwd()), Operator.EQUAL);
		List<Users> userList = userDao.findByConditions(conditions);
		
		if(CollectionUtils.isNotEmpty(userList)) {
			user.setRoleName(userList.get(0).getRole().getRoleName());
			//System.out.println(userList.get(0).getRole().getRoleName());
			ServletActionContext.getRequest().getSession().setAttribute("user",user);
			MDC.put("userName", user.getUserName());
			MDC.put("logType", "userLogin");
			MDC.put("objType", "登陆");
			System.out.println("fada++++++++++++++++++++++++++++++++++++=");
			log.error("");
			System.out.println("fada>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.");
			MDC.remove("userName");
			MDC.remove("logType");
			MDC.remove("objType");
			return "Login_Success";
		}else{
			return "Login_Failed";
		}		
	}
	public String usersAction_logout(){
		ActionContext.getContext().getSession().remove("user");
		return "Logout";
	}
	
	public String usersAction_userNameExistCheck(){
		Conditions conditions = new Conditions();
		conditions.addCondition("userName",user.getUserName(),Operator.EQUAL);
		List<Users> userList = userDao.findByConditions(conditions);
		if (userList.size() == 0){
			result = "Yes";//用户名不存在
		}else{
			result = "No";//用户名已存在
		}
		
		
		return "success";
	}
	public String usersAction_add() throws UnsupportedEncodingException{//添加新用户
		Users nowuser = (Users) ServletActionContext.getRequest().getSession().getAttribute("user");
		Conditions conditions = new Conditions();
		conditions.addCondition("userName",user.getUserName(),Operator.EQUAL);
		//System.out.println("SSSSSSSSSS" + userDao.findByConditions(conditions).size());
		if(userDao.findByConditions(conditions).size() != 0){
			System.out.println("已经存在！");
			return "Add_FailedUserName";
		}
		if(!user.getPwd().equals(user.getPwdCheck())){
			System.out.println("两次密码不一样！");
			return "Add_FailedPwd";
		}
		user.setPwd(Md5Utils.md5(user.getPwd()));
		user.getUserName();
		user.getTel();
		user.getDepart();
//		Roles role = new Roles();//新建角色对象
		Roles role = roleDao.findById(user.getRoleId());
		user.setRole(role);//通过获得用户角色ID将角色分配给新增用户
		
		userDao.addOrUpdate(user);
		MDC.put("userName", nowuser.getUserName());
		MDC.put("objName", user.getUserName());
		MDC.put("objId",user.getUserId());
		MDC.put("fieldOriValue", "无");
		MDC.put("fieldUpdValue", role.getRoleName());
		MDC.put("logType", "userManage");
		MDC.put("objType", "人员管理");
		MDC.put("operType", "增加");
		MDC.put("fieldName", "身份");
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
		return "Add_Success";
	}
	@Test
	public void usersAction_page(){
		Object[] list = userDao.findAll().toArray();
		//System.out.println(list.toString());
		//return "Page";
	}
	public String usersAction_del()throws UnsupportedEncodingException{//删除用户

		Conditions conditions = new Conditions();
		conditions.addCondition("userName",user.getUserName(),Operator.EQUAL);
		
		System.out.println("用户名 " + user.getUserName());
		Users nowuser = (Users) ServletActionContext.getRequest().getSession().getAttribute("user");
		List<Users> userList = userDao.findByConditions(conditions);
		

		MDC.put("userName", nowuser.getUserName());
		MDC.put("objName", userList.get(0).getUserName());
		MDC.put("objId",userList.get(0).getUserId());
		MDC.put("fieldOriValue", userList.get(0).getRole().getRoleName());
		MDC.put("fieldUpdValue", "无");
		MDC.put("logType", "userManage");
		MDC.put("objType", "人员管理");
		MDC.put("operType", "删除");
		MDC.put("fieldName", "身份");
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
		
		//System.out.println(userList);
		userDao.deleteAll(userList);
		
		return "Delete_Success";
	}
	
	public String usersAction_del_check() throws IOException{
		
		Conditions conditions = new Conditions();
		conditions.addCondition("userName",user.getUserName(),Operator.EQUAL);
		System.out.println("---------------------+>" + user.getUserName());
		List<Users> userList = userDao.findByConditions(conditions);
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setContentType("text/json;charset=utf-8");
		
		//System.out.println("--------------------->" + userList.get(0).getUserName());
		if("admin".equalsIgnoreCase(userList.get(0).getUserName())){
			result="NO";
		} else {
			result="YES";
		}
		//System.out.println(result);		
		return "success";
	}
	
	public String usersAction_showSelect(){
		Conditions conditions = new Conditions();
		conditions.addCondition("userName", user.getUserName(), Operator.LIKE);
		conditions.addCondition("depart", user.getDepart(), Operator.LIKE);
		conditions.addCondition("roleId", user.getRoleId(), Operator.LIKE);
		List<Users> userList = userDao.findByConditions(conditions);
		for(int i = 0 ; i < userList.size() ; i++ ){
			Users user1 = new Users();
			if(!userList.get(i).getUserName().equals("root")){
			user1.setUserName(userList.get(i).getUserName());
			user1.setRoleName(userList.get(i).getRole().getRoleName());
			user1.setRoleId(userList.get(i).getRole().getRoleId());
			user1.setTel(userList.get(i).getTel());
			user1.setDepart(userList.get(i).getDepart());
			if(!departList.contains(userList.get(i).getDepart())){
				departList.add(userList.get(i).getDepart());
			}
			if(user1.getRoleId() == 1){
				rootList.add(user1); 
			}
			if(user1.getRoleId() == 2){
				adminList.add(user1);
			}
			if(user1.getRoleId() == 3){
				comUserList.add(user1);
			}
			
		}
		
	}
		
		return "Show_Success";
}

	@Test
	public String usersAction_showDetail() throws IOException{
		
		List<Users> usersFindAll = userDao.findAll();
		
		for(int i = 0 ; i < usersFindAll.size() ; i++ ){
			Users user = new Users();
			user.setUserName(usersFindAll.get(i).getUserName());
			user.setRoleName(usersFindAll.get(i).getRole().getRoleName());
			user.setRoleId(usersFindAll.get(i).getRole().getRoleId());
			user.setTel(usersFindAll.get(i).getTel());
			user.setDepart(usersFindAll.get(i).getDepart());
			if(user.getRoleId() == 1){
				rootList.add(user); 
			}
			if(user.getRoleId() == 2){
				adminList.add(user);
			}
			if(user.getRoleId() == 3){
				comUserList.add(user);
			}
			
		}
		/*Gson gson = new Gson();
		String result = gson.toJson(userList1);
		System.out.println(result);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.flush();
		out.close();*/
		return "Show_Success";	
	}
	

	public String usersAction_update(){
		Conditions conditions = new Conditions();
		conditions.addCondition("userName",user.getUserName(),Operator.EQUAL);
		List<Users> userList = userDao.findByConditions(conditions);
		Users nowuser = (Users) ServletActionContext.getRequest().getSession().getAttribute("user");
		Users user2 = userList.get(0);
		Roles nowrole = roleDao.findById(user.getRoleId());
		
//		Roles role = new Roles();
		if (user.getRoleId() != null) {
			if("admin".equals(user.getUserName())){
				Roles role = roleDao.findById(1);
				user2.setRole(role);
				//System.out.println("admin!!!!");
			}else{
				Roles role = roleDao.findById(user.getRoleId());
				if(!nowrole.getRoleName().equals(user2.getRole().getRoleName())){
				MDC.put("userName", nowuser.getUserName());
				MDC.put("objName", user.getUserName());
				MDC.put("objId", user2.getUserId());
				MDC.put("fieldOriValue", user2.getRole().getRoleName());
				MDC.put("fieldUpdValue", nowrole.getRoleName());
				MDC.put("logType", "userManage");
				MDC.put("objType", "人员管理");
				MDC.put("operType", "修改");
				MDC.put("fieldName", "身份");
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
				user2.setRole(role);
			}
			//user2.setRoleId(role.getRoleId());
		}
		
		if (StringUtils.isNotEmpty(user.getDepart())) {
			if(!user.getDepart().equals(user2.getDepart())){
			MDC.put("userName", nowuser.getUserName());
			MDC.put("objName", user.getUserName());
			MDC.put("objId", user2.getUserId());
			MDC.put("fieldOriValue", user2.getDepart());
			MDC.put("fieldUpdValue", user.getDepart());
			MDC.put("logType", "userManage");
			MDC.put("objType", "人员管理");
			MDC.put("operType", "修改");
			MDC.put("fieldName", "部门");
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
			user2.setDepart(user.getDepart());
			
		}
		if (StringUtils.isNotEmpty(user.getTel())) {
			if(!user.getTel().equals(user2.getTel())){
			MDC.put("userName", nowuser.getUserName());
			MDC.put("objName", user.getUserName());
			MDC.put("objId", user2.getUserId());
			MDC.put("fieldOriValue", user2.getTel());
			MDC.put("fieldUpdValue", user.getTel());
			MDC.put("logType", "userManage");
			MDC.put("objType", "人员管理");
			MDC.put("operType", "修改");
			MDC.put("fieldName", "电话");
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
			user2.setTel(user.getTel());
		}
		if (StringUtils.isNotEmpty(user.getPwd())) {
			if(!user.getPwd().equals(user.getPwdCheck())){
				System.out.println("两次密码不一样！");
				return "Update_Failed";
			}
			MDC.put("userName", nowuser.getUserName());
			MDC.put("objName", user.getUserName());
			MDC.put("objId", user2.getUserId());
			MDC.put("logType", "userManage");
			MDC.put("objType", "人员管理");
			MDC.put("operType", "修改");
			MDC.put("fieldName", "密码");
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
			user2.setPwd(Md5Utils.md5(user.getPwd()));
		}
		userDao.addOrUpdate(user2);

		
		return "Update_Success";
	}
	
	public String usersAction_updatePwd(){
		Conditions conditions = new Conditions();
		conditions.addCondition("userName",user.getUserName(),Operator.EQUAL);
		/*System.out.println("用户名" + user.getUserName());
		System.out.println("输入的旧密码" + user.getOldPwd());
		System.out.println("密码：" + user.getPwd());
		System.out.println("确认密码：" + user.getPwdCheck());*/
		List<Users> userList = userDao.findByConditions(conditions);
		Users user3 = userList.get(0);
		if(user.getOldPwd().isEmpty()){
			return "Empty";
		}
		if(user3.getPwd().equals(Md5Utils.md5(user.getOldPwd()))){
			if(user.getPwd().equals(user.getPwdCheck())){
				user3.setPwd(Md5Utils.md5(user.getPwd()));
				userDao.addOrUpdate(user3);
				return "Update_Success";
			}else{
				return "Update_PwdCheckFailed";
			}
		}else{
			return "Update_OldFailed";
		}
		
	}
	
	public String usersAction_updatePwdOldCheck(){
		Conditions conditions = new Conditions();
		conditions.addCondition("userName",user.getUserName(),Operator.EQUAL);
		List<Users> userList = userDao.findByConditions(conditions);
		Users user3 = userList.get(0);
		if(user.getOldPwd().isEmpty()){
			result="No";
		}
		if(user3.getPwd().equals(Md5Utils.md5(user.getOldPwd()))){
			result="Yes";
		}else{
			result="No";
		}
		
		return "success";
		
	}
	
	//用户数据维护功能权限检查
	public String usersAction_check_dataManage_power(){
		return "success";
	}
	//用户资产添加功能权限检查
	public String usersAction_check_ProAdd_power(){
		return "success";
	}
	//用户资产删除功能权限检查
	public String usersAction_check_ProDel_power(){
		return "success";
	}
	//用户资产修改功能权限检查
	public String usersAction_check_ProUpd_power(){
		return "success";
	}
	//用户资产导入功能权限检查
	public String usersAction_check_ProImport_power(){
		return "success";
	}
	//用户资产导出功能权限检查
	public String usersAction_check_ProExport_power(){
		return "success";
	}
	//专用资产导出全部功能权限检查
	public String usersAction_check_speExport_power(){
		return "success";
	}
	//专用资产导入全部功能权限检查
	public String usersAction_check_speImport_power(){
		return "success";
	}
	
	//备品备件导出全部功能权限检查
	public String usersAction_check_bakExport_power(){
		return "success";
	}
	//备品备件导入全部功能权限检查
	public String usersAction_check_bakImport_power(){
		return "success";
	}
	//办公资产导出全部功能权限检测
	public String usersAction_check_offExport_power(){
		return "success";
	}
	
	//办公资产导入全部功能权限检查
	public String usersAction_check_offImport_power(){
		return "success";
	}
	//线路资产导出全部功能权限检查
	public String usersAction_check_lineExport_power(){
		return "success";
	}
	//线路资产导入全部功能权限检查
	public String usersAction_check_lineImport_power(){
		return "success";
	}
	//用户生成条码功能权限检查
	public String usersAction_check_BarCode_power(){
		return "success";
	}
	
	//用户日志功能权限检查
	public String usersAction_check_Log_power(){
		return "success";
	}
	
	
	
	
	//用户报表功能权限检查
	public String usersAction_check_TableBak_power(){
		return "success";
	}
	public String usersAction_check_TableOff_power(){
		return "success";
	}
	public String usersAction_check_TableSpe_power(){
		return "success";
	}
	
	
	

	public String getCheckNumber() {
		return checkNumber;
	}

	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}
	
	public List<Users> getRootList() {
		return rootList;
	}

	public void setRootList(List<Users> rootList) {
		this.rootList = rootList;
	}

	public List<Users> getComUserList() {
		return comUserList;
	}

	public void setComUserList(List<Users> comUserList) {
		this.comUserList = comUserList;
	}

	public List<Users> getAdminList() {
		return adminList;
	}

	public void setAdminList(List<Users> adminList) {
		this.adminList = adminList;
	}
	
	public String add() {
		userService.add(user);
		return "add";
	}
	
	public Users getModel() {
		return user;
	}

	@Override
	public String toString() {
		return "UsersAction [user=" + user + ", userService=" + userService
				+ ", applicationContext=" + applicationContext + ", userDao="
				+ userDao + "]";
	}

	public Users getUser() {
		return user;
	}

	public UsersService getUsersService() {
		return userService;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public void setUsersService(UsersService userService) {
		this.userService = userService;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	

	public List<String> getDepartList() {
		return departList;
	}
	public void setDepartList(List<String> departList) {
		this.departList = departList;
	}
	
	
}
