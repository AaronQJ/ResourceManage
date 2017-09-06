<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge" />
    <title>资源管理系统</title>
    <meta name="viewport" content="width=device-width,user-scalable=no" />
    <link rel=" shortcut icon " href=" favicon.ico" type=" image/x-icon " />
    <link href="Styles/login.css" rel="stylesheet" type="text/css" />
    <script src="Scripts/jquery.js"></script>
    <script src="Scripts/login.js"></script>
</head>
<body>

<div class="header">

  <img src="images/logo.png" alt="logo图片"/>
  <span >资源管理系统</span>
</div>
<div class="con">
    <div class="login">
        <div class="loginTop">
            <h3>用户登录</h3>
        </div>
        <form name="form-log" id="form-logID" action="usersAction_login" method="post">
            <label id="label1"><span id="userName1"></span><span>用户名：</span></label>
            <input name="userName" type="text" autocomplete="off" id="userName" class="text-in">
            <br>
            <label id="label2"><span id="pwd1"></span><span>密&nbsp;&nbsp;&nbsp;码：<span></label>
            <input name="pwd" type="password" autocomplete="off" id="pwd" class="text-in">
            <br>
            <label id="label3"><span id="checkNumber1"></span><span>验证码：</span></label>
            <input name="checkNumber" type="text" autocomplete="off" id="checkNumber" class="text-in" maxlength="4">
            <img  src="${pageContext.request.contextPath}/checkNumber.jsp" name="checkNumber" style="vertical-align:middle;cursor:hand" title="点击可更换图片" height="22" onclick="this.src='${pageContext.request.contextPath}/checkNumber.jsp?timestamp='+new Date().getTime()"/>
            <br/>
            <span id="checkCodeAlert" style="color:red; display:none;">验证码错误</span>
             <br/><br/>
             <input type="button" name="button1" id="button1"  value="登录">
             <a class="forgetPwd" href="javascript:void(0);" onclick="forgotPwd()">忘记密码</a>
         </form>
     </div>
</div>
<div class="footer">
    <p class="tips">建议使用火狐浏览器</p>
    <p>国家计算机网络与信息安全管理中心陕西分中心</p>
</div>
 
</body>
</html>