<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge" />
    <title>数据维护</title>
    <meta name="viewport" content="width=device-width" />
	<link href="${pageContext.request.contextPath }/Styles/superManager.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath }/Styles/addBox.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath }/Styles/offManage.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath }/Styles/changeBox.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath }/Styles/chose.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
    .dataCon{ width:99%; position:absolute; top:30px; bottom:10px; border:1px solid #F0F0F0;
              border-radius:3px; -moz-border-radius:3px; -webkit-border-radius:3px; }
    .ulCon{ width:420px; height:300px; margin-top:30px; margin-left:60px; letter-spacing:2px; }
    .ulCon button{width:190px; height:40px; font-size:16px; font-weight:bold; display:block; border:1px solid #F0F0F0; background-color:#CFDDEE; outline:0; border-radius:5px; -moz-border-radius:5px; -webkit-border-radius:5px;cursor:pointer;}
    .ulCon button:hover{ color:#8F9CA8;}
    .ulCon div{float:left; width:190px; height:300px;}
     #ulImport li,#ulExport li{ cursor:pointer;}
     #ulImport li:hover,#ulExport li:hover{ color:red;}
    #ulImport,#ulExport{font-size:16px; line-height:35px; width:186px; margin-top:5px; display:none; background-color:#E5EFF7;
                       border:1px solid #F0F0F0; border-radius:3px; -moz-border-radius:3px; -webkit-border-radius:3px;}
    
    #ulImport{ height:175px;}
    #ulExport{ height:175px;}
    </style>
     
</head>
<body>


<div class="bread">
   当前位置：数据维护
</div>
<!--bread over-->
<div class="dataCon">
  <div class="ulCon">
     <div>
	  <button id="importAct">导入操作</button>
	  <ul id="ulImport">
		  <li id="speImport">专用资产</li>
		  <li id="linImport">线路管理</li>
		  <li id="bakImport">备品备件</li>
		  <li id="offImport">办公资产</li>
		 
	  </ul>
	  </div>
	  
	  <div style="margin-left:30px;">
	  <button id="exportAct">导出操作</button>
	  <ul id="ulExport">
		  <li id="speExport">专用资产</li>
		  <li id="linExport">线路管理</li>
		  <li id="bakExport">备品备件</li>
		  <li id="offExport">办公资产</li>
		  <li id="all">系统文件</li> 
	  </ul>
	  </div>
  </div>
</div>
    
   <!-- importBox -->
  <div style="z-index:2;position:absolute;" class="importBox" id="importBox">
      	<p class="title">您选择导入数据：</p>
      	<a id="importCloseBox" class="importCloseBox"><img src="images/close.png" /></a>
      	<form class="importForm" id="importForm" method="post" enctype="multipart/form-data">
      		<input id="fileAddress" class="fileAddress" type="file" name="file"/><br />
      		<p><input type="submit" value="确认导入" id="sureToImport" /></p>
      	</form>
  </div>
    <!-- importBox结束 -->
    
<!--遮罩层-->
<div id="maskIframe" >
</div>
<!-- 遮罩层结束 -->

<script src="${pageContext.request.contextPath }/Scripts/jquery.js" type="text/javascript"></script>
	  <script>
	 	var FAD='${pageContext.request.contextPath}';
	 </script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/Scripts/dataManage.js"></script>    
	<script src="${pageContext.request.contextPath }/Scripts/shade.js" type="text/javascript"></script>
     <script src="${pageContext.request.contextPath }/Scripts/dialogPos.js" type="text/javascript"></script>
   
</body>
</html>