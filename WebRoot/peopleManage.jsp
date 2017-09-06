<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge" />
    <title>人员管理</title>
    
    <meta name="viewport" content="width=device-width,user-scalable=no" />
    <link href="${pageContext.request.contextPath }/Styles/superManager.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath }/Styles/peopleManage.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath }/Styles/addBox.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath }/Styles/chose.css" rel="stylesheet" type="text/css"/>

</head>
<body>
		
	 	<div class="bread">
            当前位置：人员管理
        </div>
		<div class="tableContent">
			  <div class="searchBox">
                <div class="searchRulesBox" style="border:none;">
                    <form name="form1" action="usersAction_showSelect" method="post">
                   	 	<label for="userDepart">所属部门</label>
                   	 	<input id="userDepart" name="depart" class="searchRules" list="userDepartList"/>
                   	 	<datalist id="userDepartList">
                   	 	<s:if test="departList.size() != 0">
                   	 		<s:iterator value="departList" var="depart">
                   	 	   		<option value="<s:property value="#depart" />">
                   	 	   	</s:iterator>
                   	 	</s:if>
                   	 	</datalist>
                    	
  
                    	<label for="userRole">用户身份</label>		
                        <select id="userRole" class="searchRules" name="roleId">  
                            <option value ="">请选择</option>                  	
                        	<option value ="3">普通用户</option>
                        	<option value ="2">管理员</option>
                        	<option value ="1">超级管理员</option>
                    	</select>
                        
              
                    	<label >用户名</label>
						<input list="userNames" name="userName">
						<datalist id="userNames">
						</datalist>
                    <!--	<input type="text" class="searchRules"  id="code-bar"/>-->
                    	<input type="submit" value="查询" id="searchButton" />
             <!--  <form action="usersAction_showDetail"><input type="submit" value="查询所有" id="searchButton" /></form> -->
					    <a id="addUser"><img src="images/addUser.png" />添加</a>
                    </form>                    
                 </div>
           	  </div>
            <!--searchBox over-->
			
			<!--用户信息div-->
			<div class="userTableBox">
				<!-- 普通用户 --> 
				<!-- 普通用户 --> 
				<p id="normal">超级管理员</p>
				<s:if test="rootList.size() != 0">
				<table cellspacing="0">
					<thead>
						<tr>
							<th class="thwidth">用户名</th>
							<th class="thwidth">密码</th>
							<th class="thwidth">电话</th>
							<th class="thwidth">所属部门</th>
							<th class="thwidth">操作</th>
							<!-- <th>更改权限</th> -->
						</tr>
					</thead>
					<tbody>
						<s:iterator value="rootList">
							<tr>
								<td><s:property value="userName" /></td>
								<td>**********</td>
								<td><s:property value="tel" /></td>
								<td><s:property value="depart" /></td>
								<td><a href="javascript:changeInfo('<s:property value="userName" />' , '<s:property value="tel" />' , '<s:property value="depart" />' ,'<s:property value="roleId" />')">修改信息</a>  <a href="javascript:userDel('<s:property value="userName" />')">删除用户</a></td>
								<!-- <td>更改权限</td> -->
							</tr>   
						</s:iterator>
					</tbody>
				</table>
				</s:if>
				<p>管理员</p>
				<s:if test="adminList.size() != 0">
				<table cellspacing="0">
					<thead>
						<tr>
							<th>用户名</th>
							<th>密码</th>
							<th>电话</th>
							<th>所属部门</th>
							<th>操作</th>
							<!-- <th>更改权限</th> -->
						</tr>
					</thead>
					<tbody>
						<s:iterator value="adminList">
							<tr>
								<td><s:property value="userName" /></td>
								<td>**********</td>
								<td><s:property value="tel" /></td>
								<td><s:property value="depart" /></td>
								<td><a href="javascript:changeInfo('<s:property value="userName" />','<s:property value="tel" />' , '<s:property value="depart" />' , '<s:property value="roleId" />')">修改信息</a>    <a href="javascript:userDel('<s:property value="userName" />')">删除用户</a></td>
								<!-- <td>更改权限</td> -->
							</tr>   
						</s:iterator>
					</tbody>
				</table>
				</s:if>
				<p>普通用户</p>
				<s:if test="comUserList.size() != 0">
				<table cellspacing="0">
					<thead>
						<tr>
							<th>用户名</th>
							<th>密码</th>
							<th>电话</th>
							<th>所属部门</th>
							<th>操作</th>
							<!-- <th>更改权限</th> -->
						</tr>
					</thead>
					<tbody>
						<s:iterator value="comUserList">
							<tr>
								<td><s:property value="userName" /></td>
								<td>**********</td>
								<td><s:property value="tel" /></td>
								<td><s:property value="depart" /></td>
								<td><a href="javascript:changeInfo('<s:property value="userName" />' , '<s:property value="tel" />' , '<s:property value="depart" />','<s:property value="roleId" />')">修改信息</a> <a href="javascript:userDel('<s:property value="userName" />')">删除用户</a></td>
								<!-- <td>更改权限</td> -->
							</tr>   
						</s:iterator>
					</tbody>
				</table>
				</s:if>
		   </div>
		   <!--用户信息div完-->
		   <div class="tableFooter">
		   		<%-- <span>系统当前使用总人数：</span><span>11</span> --%>
		   </div>
		</div>


  <!--遮罩层-->
<div id="maskIframe" >

</div>
<!-- 遮罩层结束 -->
    
<!--添加人员-->
<div  id="addUser1" class="addUser1">
<form action="usersAction_add" method="post" id="userAdd">

	<p><span>添加用户</span>
		<a  id="closeImg" class="closeBox"><img src="images/close.png" /></a>
	</p>
	<div class="people">
		<label>用 户 名<span></span></label><span class="colon">:</span><input type="text" name="userName" autocomplete="off" class="userInfo" id="userName"/><span id="checkUserNameAlert" >已存在</span><br/>
		<label>联 系 电 话<span></span></label><span class="colon">:</span><input type="text"  name="tel" maxlength="11" autocomplete="off" class="userInfo" id="telNumber"/><br/>
		<label>用 户 权 限<span></span></label><span class="colon">:</span>
		<select id="role" class="userInfo" name="roleId">			
			<option value="3">普通用户</option>
			<option value="2">管理员</option>
			<option value="1">超级管理员</option>
		</select>
		<br/>	
		<label>密 码<span></span></label><span class="colon">:</span><input type="password" autocomplete="off" name="pwd" class="userInfo" id="pwd"/><br/>
		<label>确 认 密 码<span></span></label><span class="colon">:</span><input type="password" autocomplete="off" name="pwdCheck" class="userInfo" id="pwdCheck"/><br/>
		<label>所 属 部 门<span></span></label><span class="colon">:</span><input  id="userDepart" type="text" autocomplete="off" name="depart" class="userInfo" list="depart111"/>
			<datalist id="depart111">
				<s:if test="departList.size() != 0">
	                   <s:iterator value="departList" var="depart">
	                   	 	<option value="<s:property value="#depart" />">
	                   </s:iterator>
	            </s:if>
			</datalist><br/>
		<input class="btnOk" type="button" id="addOk" name="addOk" value="提交"  />
   </div>
   <!-- people end -->

</form>
</div>


<!-- 修改个人信息 -->
<div id="changeInfo" class="addUser1">
<form action="usersAction_update" method="post" onsubmit="return returnsumbit_sure()" id="usersAction_update_form">

	<p><span>修改信息</span>
		<a  id="closeImg" class="closeBox"><img src="images/close.png" /></a>
	</p>
	<div class="people">
		<label>当 前 用 户<span></span></label><span class="colon">:</span><input style="border:none;" id="toUserName" type="text" readonly value='<s:property value="%{userName}"/>' name="userName" class="userInfo" /><br/>
		<label>联 系 电 话<span></span></label><span class="colon">:</span><input id="toTel" type="text" name="tel" class="userInfo" value='<s:property value="%{tel}"/>' /><br/>
		
		<label>用 户 权 限<span></span></label><span class="colon">:</span>
		<select id="toRoleId" class="userInfo" name="roleId">
			<option value="3">普通用户</option>
			<option value="2">管理员</option>
			<option value="1">超级管理员</option>
		</select>
		<br/>
		<label>密 码<span></span></label> <span class="colon">:</span> <input type="password" name="pwd" class="userInfo"/><br/>
		<label>确 认 密 码<span></span></label>  <span class="colon">:</span> <input type="password" name="pwdCheck" class="userInfo"/><br/>
		<label>所 属 部 门<span></span></label> <span class="colon">:</span> <input id="toDepart" type="text"  name="depart" class="userInfo"  list="departchange" />
			<datalist id="departchange">
				<s:if test="departList.size() != 0">
                   <s:iterator value="departList" var="depart">
                   	 	<option value="<s:property value="#depart" />">
                   </s:iterator>
            	</s:if>             	 	
            </datalist>
		<br/>
		<input class="btnOk" type="submit" value="确认修改"  />

   </div>
  <!-- 修改信息结束 -->

</form>
</div>
<!--删除-->
 <div  id="deleteOne" class="showDialog dialog" style="position:absolute; top:80px; left:40px;">
     <div class="dialogHeader"> 
     	<p style="font-size:12px; color:#666; position:absolute; bottom:0; left:5px;">删除用户信息</p>
     	<a href="#" class="closeButton close"><img src="images/close.png" height="24px" width="24px" /></a>     	
     </div>
     <div class="deleteCon">
         <img src="images/deletePng.png" height="32px" width="32px">
        	确定要把此用户信息删除吗？
     </div>
     <div class="buttonNav">
         <button class="okBtn" id="okBtn" value="yes">是(Y)</button>
         <button class="noBtn close" id="noBtn" value="no">否(N)</button>
     </div>
 </div>
 <!-- 删除结束 -->


 <script src="${pageContext.request.contextPath }/Scripts/jquery.js" type="text/javascript" ></script>
 <script src="${pageContext.request.contextPath }/Scripts/user.js" type="text/javascript"></script>
 <script src="${pageContext.request.contextPath }/Scripts/superManager.js" type="text/javascript"></script>
 <script src="${pageContext.request.contextPath }/Scripts/shade.js" type="text/javascript"></script>
 <script src="${pageContext.request.contextPath }/Scripts/dialogPos.js" type="text/javascript"></script>

</body>
</html>