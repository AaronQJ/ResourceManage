<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge" />
    <title>资源管理系统</title>
    <meta name="viewport" content="width=device-width" />
    <link rel=" shortcut icon " href=" favicon.ico" type=" image/x-icon " />
    <link href="Styles/superManager.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="Scripts/jquery.js"></script>
    <script src="Scripts/shade.js" type="text/javascript"></script>
    <script src="Scripts/superManager.js" type="text/javascript"></script>
	<script src="Scripts/webTab.js" type="text/javascript"></script>
	
</head>
<body style="min-width:1024px;">

<div class="header">
    <ul>
        <li ><img src="images/out.png"/><a href="usersAction_logout.action">退出</a></li>
        <li ><img src="images/changePWD1.png"/><a target="_blank" href="changePwd.jsp">修改密码</a></li>
        <li ><span>用户名：</span><span id="user">${user.userName}</span></li>
    </ul>
</div>
<!--header over-->

<div class="content">
    <div class="left">
 
    <div class="ulBox">
    <ul id="prosort">
        <li style="margin-top:0px;" class="list-li"><a href="#">资产管理</a>
        </li>
            <ul id="ul1" style="display:block;">
                <li><a href="#" id="spe_pro"><img src="images/resource1.png"/>专用资产</a></li>
                <li><a href="#" id="lin_inf"><img src="images/resource2.png">线路管理</a></li>
                <li><a href="#" id="pro_bak"><img src="images/resource3.png">备品备件</a></li>
                <li><a href="#" id="off_pro"><img src="images/resource4.png"/>办公资产</a></li>
            </ul>
        <li class="list-li"><a href="#">报表管理</a>
           <!-- <img src="images/down1.png" class="imgbtn">-->
       </li>
            <ul id="ul2">
                <li><a href="#" id="tableSpe"><img src="images/resource1.png"/>专用资产</a></li>
                <li><a href="#" id="tableBak"><img src="images/resource3.png">备品备件</a></li>
                <li><a href="#" id="tableOff"><img src="images/resource4.png"/>办公资产</a></li>
               
            </ul>
        <li class="list-li"><a href="#" id="peopleManage">人员管理</a>
            <!--<img src="images/down1.png" class="imgbtn">-->
        </li>
          
        <li class="list-li"><a href="#" id="logManage">日志查询</a>
        </li>
        <li class="list-li"><a href="#" id="dataManage">数据维护</a>
        </li>
    </ul>

    </div>
    </div>
    <!--left over-->
	<iframe src="speManage.jsp" class="right" id="right"></iframe> 
    <!--right over-->
</div>
<!--content over-->

<div class="footer clearfix">
    <p class="footerLeft">
        <span>国家计算机网络与信息安全管理中心陕西分中心</span>
    </p>
    <p class="footerRight">
        <span>当前用户:</span><span id="role">${user.roleName}</span>
    </p>
</div>
<!--footer over-->

<!--遮罩层-->
 <div id="mask" >

</div>
<!-- 遮罩层结束 -->

</body>
</html>



