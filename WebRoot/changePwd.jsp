<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge" />
    <title>修改密码</title>
    <meta name="viewport" content="width=device-width,user-scalable=no" />
    <link href="Styles/changePwd.css" rel="stylesheet" type="text/css"/>
    <script  src="Scripts/jquery.js" type="text/javascript"></script>
    <script  src="Scripts/changePwd.js" type="text/javascript"></script>
    <script  src="Scripts/user.js" type="text/javascript"></script>
	
</head>
<body>
  <div class="changePwdHeader">
            修改密码
  </div>
  <div id="resetpasswd" class="changeContent">
     <label>用户名：</label>
     <span id="userName">${user.userName}</span><br>
     <form name="changePwd" id="changePwd" action="usersAction_updatePwd" >
     	<%-- <label for="userName">用户名：</label><input  name="${user.userName}" type="hidden" name="userName" />00 --%>
     	<input id="getUserName" type="hidden" name="userName" value="${user.userName}"/>
        <label for="oldPwd">旧密码：</label><input name="oldPwd" type="password" autocomplete="off"  id="oldPwd" name="oldPwd"/><br>
        <label for="newPwd">输入新密码：</label><input name="pwd"type="password" autocomplete="off"  id="newPwd" name="newPwd"/><br>
        <label for="newPwdOk">确认新密码：</label ><input name="pwdCheck" type="password" autocomplete="off"  id="newPwdOk"/><br>
        <input id="changePwdOk" name="changePwdOk" type="button" value="确定">
        
     </form>
  </div>
  

</body>
</html>



