<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page import="java.util.*" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge" />
    <title>日志查询</title>
    <meta name="viewport" content="width=device-width,user-scalable=no" />
	<style type="text/css">
	/*翻页 左右*/
	.leadPage a img{ width:10px; height:10px }
	.leadPage input{ width:30px; height:15px; border:1px solid #B1CEEC; text-align:center }
	</style>
	
  	<link href="Styles/superManager.css" rel="stylesheet" type="text/css"/>
	<link href="Styles/addBox.css" rel="stylesheet" type="text/css"/>
	<link href="Styles/offManage.css" rel="stylesheet" type="text/css"/>
    <link href="Styles/chose.css" rel="stylesheet" type="text/css"/>
  
</head>
<body>
      
	 	<div class="bread">
            当前位置：日志查询
        </div>
       <div class="rightHeader">
            <div class="searchRulesBox">
                <form name="form1">
                    <label >设备名称</label>
                    <input  type="text" autocomplete="off" placeholder="双击可选" class="searchRules" list="logDevName" id="objName"name="objName"/>
                    <datalist id="logDevName">
                        <%
 			 				List logdevname =(List)application.getAttribute("logdevname");		
 							for( int i=0; i < logdevname.size(); i++)
 						{%>
 								<option value="<%=logdevname.get(i)%>">
 				
 			 			<%}%>
                        
                    </datalist>

                    <label >操作人</label>
                    <input  type="text" autocomplete="off" placeholder="双击可选" class="searchRules"  list="operator" id="userName" name="userName"/>
                    <datalist id="operator">
                         <%
 			 				List logopertor =(List)application.getAttribute("logopertor");		
 							for(int i=0; i < logopertor.size(); i++)
 						{%>
 								<option value="<%=logopertor.get(i)%>">
 				
 			 			<%}%>
                    </datalist>
                    <label for="opeType">日志类别</label>
                    <select  class="searchRules" name="logType" id="logType">
                        <option value ="" >请选择</option>
                        <option value ="devRecord" >资产记录</option>
                        <option value ="userLogin">用户登录</option>
                        <option value ="userManage">人员管理</option>
                    </select>
                    
                    <label >时间</label>
                    <input  style="width:86px; margin-right:0px;" class="datepicker searchRules" readonly placeholder="起始时间" type="text"  id="updTime1"/>
                    <span>-</span>
                    <input style="width:86px;" class="datepicker searchRules" readonly placeholder="截止时间" type="text"  id="updTime2" />

                    <input  type="button" value="查询" id="searchButton"/>
                </form>
            </div>
	       
            <div class="operate">
            
                <span class="first">请选择日期</span>
                
                <input style="font-size:12px;"  readonly class="datepicker" placeholder="删除在此之前的日志" type="text"  id="updTime" name="operTime"/>
               <%--  <span><img src="images/delete.png" /><input type="button" value="删除日志" id="delete" /></span> --%>
                 <span><a id="del" ><img src="images/delete.png" />删除日志</a></span>
            </div><!--操作-->
	
        </div>
        <!--rightHeader over-->

        <div id="selectCount"><span>每页</span><select id="countPerPage" name="countPerPage">
            <option selected>20</option>
            <option>30</option>
            <option>50</option>
           
        </select><span>条</span>
        </div>

        <div class="tableBox">
        	 
            <table  cellspacing="0" >
               <thead>
                <tr>
                    <th> </th>
                    <th><input type="checkbox" value="1" id="checkbox"/></th>
                    <th>设备/人员名称</th>
		            <th>设备/人员ID</th>
                    <th>设备/人员类型</th>
                    <th>操作类型</th>
                    <th>更改项</th>
                    <th>更改前</th>
                    <th>更改后</th>
                    <th>操作人</th>
                    <th>操作时间</th>
                </tr>
                </thead>
                <tbody id="logTable" >
             
               </tbody>
               
            </table>
            
        </div>
        <!--tableBox end-->
        <div class="rightFooter">
            <div class="leadCount">共<span id="count">15</span>条记录，每页
                <span id="perPageCount">50</span>条，共<span id="pages">1</span>页
            </div>
            <div class="leadPage">
                <a id="leftUp"><img src="${pageContext.request.contextPath }/images/leftUp.png" /></a>
                <a id="left"><img src="${pageContext.request.contextPath }/images/left.png" /></a>
                <input type="text" id="page" value="1"/>
                <a id="right"><img src="${pageContext.request.contextPath }/images/right.png" /></a>
                <a id="rightUp"><img src="${pageContext.request.contextPath }/images/rightUp.png" /></a>&nbsp;&nbsp;
                <a id="exportNow">导出当前页</a>
                <a id="exportAll">导出全部页</a>

            </div>
        </div>
        <!--rightFooter-->
        
        <!--弹出框的设计-->
          <!--删除-->
		 <div  id="deleteOne" class="showDialog dialog">

  		   <div class="dialogHeader"> 
    		 	<p style="font-size:12px; color:#666; position:absolute; bottom:0; left:5px;">删除日志数据信息</p>
     			<a href="#" class="closeButton close"><img src="images/close.png" height="24px" width="24px" /></a>
  		   </div>
   		  <div class="deleteCon">
    		     <img src="images/deletePng.png" height="32px" width="32px">
     		   	你确定要把此前日志信息删除吗？
    		 </div>
   		  <div class="buttonNav">
    		     <button class="okBtn" id="okBtn" value="yes">是(Y)</button>
    		     <button class="noBtn close" value="no">否(N)</button>
   		  </div>
		 </div>

	   <!--遮罩层-->
<div id="maskIframe" >

</div>
<!-- 遮罩层结束 -->

 <script type="text/javascript">
    	FAD='${pageContext.request.contextPath }';
 </script>
 <script src="${pageContext.request.contextPath }/Scripts/jquery.js"  type="text/javascript"></script>
 <script src="${pageContext.request.contextPath }/Scripts/logSearch.js" type="text/javascript"></script>
 <script src="${pageContext.request.contextPath }/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
 <script src="${pageContext.request.contextPath }/Scripts/logManage.js" type="text/javascript"></script>
 <script src="${pageContext.request.contextPath }/Scripts/shade.js" type="text/javascript"></script>
 <script src="${pageContext.request.contextPath }/Scripts/superManager.js" type="text/javascript"></script>
    
</body>
</html>