<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%@ page language="java" import="java.util.*" %>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge" />
    <title>资产管理 > 专用资产</title>
     <meta name="viewport" content="width=device-width" />
    <link href="${pageContext.request.contextPath }/Styles/superManager.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath }/Styles/addBox.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath }/Styles/changeBox.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath }/Styles/speManage.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath }/Styles/chose.css"  rel="stylesheet" type="text/css" />
</head>
<body>
 <div class="bread">
            当前位置：资产管理 > 专用资产
 </div>
        <!--bread over-->
             <div class="rightHeader">
                <div class="searchRulesBox">
                    <form name="form1">
                    <label for="searchspeDevName">设备名称</label>
                   <input  type="text" autocomplete="off" id="searchspeDevName" class="searchRules"/>
                    <label for="searchspeSeqNum">序列号</label>	
                   <input type="text" autocomplete="off" id="searchspeSeqNum" class="searchRules"/>
                    <label for="searchspeBarCode">条码</label>
                     <input type="text" autocomplete="off" class="searchRules"  id="searchspeBarCode"/>
                    <!--条码可扫描，可输入-->
                    <label for="searchspeUser">使用人</label>
                    <input type="text" autocomplete="off" placeholder="双击可选" id="searchspeUser" class="searchRules" list="whouse"/>
                    <datalist id="whouse">
                  			<%
 			 		List whouse =(List)application.getAttribute("speUserList1");
 					int i;
 					for(i=0; i <whouse.size(); i++)
 					{%>
 					<option value="<%=whouse.get(i)%>">
 				
 					 <%}%>
  			
                    </datalist>

                    <input type="button" value="查询" id="searchButton" />
                    </form>
                </div>

               
                <div class="operate">
                    <span class="first"><a id="add"><img src="${pageContext.request.contextPath }/images/add.png" />添加</a></span><span class="line">|</span>
                    <span><a id="change"><img src="${pageContext.request.contextPath }/images/change.png" />批量修改</a></span><span class="line">|</span>
                    <span><a id="delete"><img src="${pageContext.request.contextPath }/images/delete.png" />删除</a></span><span class="line">|</span>
                    <span><a id="export"><img src="${pageContext.request.contextPath }/images/output.png" />导出</a></span><span class="line">|</span>
                    <span><a id="import"><img src="${pageContext.request.contextPath }/images/input.png" />导入</a></span>
                  
                </div><!--操作-->
            </div>
           <!--rightHeader over-->

        <div id="selectCount"><span>每页</span><select id="countPerPage">
            <option selected>20</option>
            <option>30</option>
            <option>50</option>
        
        </select><span>条</span>
        </div>
        <div class="tableBox">
            <table id="table" cellspacing="0" >
                <thead>
                <tr>
                    <th>序号</th>
                    <th><input type="checkbox" value="1" id="checkbox"/></th>
                    <th>标签</th>
                    <th>设备名称<img src="images/downdown.png"></th>
                    <th>序列号</th>
                    <th>所属机房<img src="images/downdown.png"></th>
                    <th>所在机柜<img src="images/downdown.png"></th>
                    <th>使用人<img src="images/downdown.png"></th>
                    <th>状态<img src="images/downdown.png"></th>
                    <th  style="width:100px;">操作</th>
                </tr>
                </thead>
                <tbody id="massageTable">
                </tbody>
            </table>
        </div>
        <!--tableBox end-->
     
         <div class="rightFooter clearfix">
            <div class="leadCount">共<span id="count">0</span>条记录，每页
                <span id="perPageCount">20</span>条，共<span id="pages">0</span>页
            </div>
            <div class="leadPage">
                <a id="leftUp"><img src="${pageContext.request.contextPath }/images/leftUp.png" /></a>
                <a id="left"><img src="${pageContext.request.contextPath }/images/left.png" /></a>
                <input type="text" id="nowPage"  />
                <a id="right"><img src="${pageContext.request.contextPath }/images/right.png" /></a>
                <a id="rightUp"><img src="${pageContext.request.contextPath }/images/rightUp.png" /></a>&nbsp;&nbsp;
                <a id="exportNow">导出当前页</a>
                <a id="exportAll">导出全部页</a>
            </div> 
        </div>
        <!--rightFooter-->

 <!--点击th出现-->
<!--设备名称-->
 <div id="speDevName1" class="choseDiv">
     <ul>
     	
     </ul>
 </div> 
 <!--所属机房-->

 <div id="speRoom1" class="choseDiv">
     <ul>
     </ul>
 </div>
 <!--所在机柜-->

 <div id="speRoomFrame1" class="choseDiv">
     <ul>
   
     </ul>
 </div>
 <!--使用人-->

 <div id="speUserName1" class="choseDiv">
     <ul>
     </ul>
 </div>
 <!--状态-->

 <div id="speState1" class="choseDiv">
     <ul>
        <li>在维</li>
        <li>不在维</li>
        <li>下线</li>
     </ul>
 </div>
 <!--结束-->

 
 <!--点击操作显示-->
 <div id="showOperate">
     <ul>
         <li>删除</li>
         <li id="changeInfo">修改</li>
         <li id="detail">详细信息</li>
         <li id="getBarcode">生成条码</li>
         <li id="deadline">生命周期查询</li>
     </ul>
 </div>
 <!--弹出框的设计-->
<!--删除-->
<!--删除-->
 <div  id="deleteOne" class="showDialog dialog">

     <div class="dialogHeader"> 
     	<p style="font-size:12px; color:#666; position:absolute; bottom:0; left:5px;">删除数据信息</p>
     	<a href="#" class="closeButton close"><img src="images/close.png" height="24px" width="24px" /></a>
     	
     </div>
     <div class="deleteCon">
         <img src="images/deletePng.png" height="32px" width="32px">
        	确定要把选中信息删除吗？
     </div>
     <div class="buttonNav">
         <button class="okBtn" id="okBtn" value="yes">是(Y)</button>
         <button class="noBtn close" value="no">否(N)</button>
     </div>
 </div>

<!--修改-->
  <div class="addBox dialog" id="changeOne">
  	<p><span>修改资产信息</span>
  	<a  class="closeBox close"><img src="images/close.png" /></a></p>
	<div  style="padding-bottom:0px; margin-left:10px; margin-top:0px;" class="massageBox"> 
		<form id="changeForm">
		
			<input type="hidden" id="speId2" name="speId"/>
			<!--不可为空-->
			<span>*</span>
			<label for="speDevName2">设备名称:</label>
 			<input type="text" autocomplete="off" name="speDevName" id="speDevName2" class="notNullInput" />
			<span>*</span>
			<label for="speDevRoom2">所属机房:</label>
 			<input type="text"  autocomplete="off" placeholder="双击可选" name="speDevRoom"  id="speDevRoom2" list="speDevRoomList2" class="notNullInput" />
 			<datalist id="speDevRoomList2">
 			<%
 			 List speDevRoomList2 =(List)application.getAttribute("speDevRoomList1");
 				
 				for(i=0; i <speDevRoomList2.size(); i++)
 			{%>
 				<option value="<%=speDevRoomList2.get(i)%>">
 				
 			 <%}%>
  			
			</datalist>
			<span>*</span>
			<label for="speSeqNum2">序列号:</label>
 			<input type="text" readonly autocomplete="off" name="speSeqNum" id="speSeqNum2" class="notNullInput" /><br />
			<span>*</span>
			<label for="speDevRoomFrame2">所在机柜:</label>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="speDevRoomFrame"  id="speDevRoomFrame2" list="speDevRoomFrameList" class="notNullInput" />
 			<datalist id="speDevRoomFrameList2">
 			<%
 			 List speDevRoomFrameList2 =(List)application.getAttribute("speDevRoomFrameList1");
 				
 				for(i=0; i <speDevRoomFrameList2.size(); i++)
 			{%>
 				<option value="<%=speDevRoomFrameList2.get(i)%>">
 				
 			 <%}%>
  			
			</datalist>
			<span>*</span>
			<label for="speUser2">使用人:</label>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="speUser"  id="speUser2" list="speUserList2" class="notNullInput" />
 			<datalist id="speUserList2">
 			<%
 			 List speUserList2 =(List)application.getAttribute("speUserList1");
 				
 				for(i=0; i <speUserList2.size(); i++)
 			{%>
 				<option value="<%=speUserList2.get(i)%>">
 				
 			 <%}%>
  			
			</datalist>
			<span>*</span>
			<label for="speState2">状态:</label>
			<select id="speState2"  name="speState">  
  				<option value ="">请选择</option>  
 				<option value ="在维">在维</option>  
 				<option value="不在维">不在维</option>  
				<option value ="下线">下线</option> 
			</select><br/>
		
			<span>*</span>
			<label for="speOwnSystem2">所属系统:</label>
 			<input type="text" autocomplete="off" name="speOwnSystem" id="speOwnSystem2" class="notNullInput" />
			<span>*</span>
			<label for="speProOwn2">资产所属:</label>
 			<input type="text" autocomplete="off" name="speProOwn" id="speProOwn2" class="notNullInput" />
			<span>*</span>
			<label for="speAssetsManaDepart2">固定资产管理部门:</label>
 			<input type="text" autocomplete="off"  placeholder="双击可选" name="speAssetsManaDepart"  id="speAssetsManaDepart2" list="speAssetsManaDepartList2" class="notNullInput" />
 			<datalist id="speAssetsManaDepartList2" >
 			<%
 			 List speAssetsManaDepartList2 =(List)application.getAttribute("speAssetsManaDepartList1");
 				
 				for(i=0; i <speAssetsManaDepartList2.size(); i++)
 			{%>
 				<option value="<%=speAssetsManaDepartList2.get(i)%>">
 				
 			 <%}%>
			</datalist><br />
 			<span>*</span>
			<label for="speDevFactor2">设备厂商:</label>
 			<input type="text" autocomplete="off" name="speDevFactor" id="speDevFactor2" class="notNullInput" />
			<span>*</span>
			<label for="speVersion2">型号:</label>
 			<input type="text" autocomplete="off" name="speVersion" id="speVersion2" class="notNullInput" />
			<span>*</span>
			<label for="speManaDepart2">实物管理部门:</label>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="speManaDepart"  id="speManaDepart2" list="speManaDepartList2" class="notNullInput" />
 			<datalist id="speManaDepartList2">
 			<%
 			 List speManaDepartList2 =(List)application.getAttribute("speManaDepartList1");
 				
 				for(i=0; i <speManaDepartList2.size(); i++)
 			{%>
 				<option value="<%=speManaDepartList2.get(i)%>">
 				
 			 <%}%>
  			
			</datalist><br />
			<span>*</span>
			<label for="speDevType2">设备类别:</label>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="speDevType"  id="speDevType2" list="speDevTypeList2" class="notNullInput" />
 			<datalist id="speDevTypeList2">
 				<option value="交换机">
  				<option value="路由器">
  				<option value="服务器">
  				<option value="其他">
			</datalist>
			<span>*</span>
			<label for="speUseDepart2">使用部门:</label>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="speUseDepart"  id="speUseDepart2" list="speUseDepartList2" class="notNullInput" />
 			<datalist id="speUseDepartList">
 			<%
 			 List speUseDepartList =(List)application.getAttribute("speUseDepartList1");
 				
 				for(i=0; i <speUseDepartList.size(); i++)
 			{%>
 				<option value="<%=speUseDepartList.get(i)%>">
 				
 			 <%}%>
  			
			</datalist>
			<span>*</span>
			<label for="speOwnNet2">所属网络:</label>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="speOwnNet"  id="speOwnNet2" list="speOwnNetList2" class="notNullInput" />
 			<datalist id="speOwnNetList2">
 			<%
 			 List speOwnNetList2 =(List)application.getAttribute("speOwnNetList1");
 				
 				for(i=0; i <speOwnNetList2.size(); i++)
 			{%>
 				<option value="<%=speOwnNetList2.get(i)%>">
 				
 			 <%}%>
			</datalist><br />
			<span>*</span>
			<label for="speArrAcceptTime2">到货验收时间:</label>
 			<input type="text" autocomplete="off" readonly name="speArrAcceptTime" id="speArrAcceptTime2" class="datepicker notNullInput" />
			<span>*</span>
			<label for="speMaintainDeadLine2">维保截止时间:</label>
 			<input type="text" autocomplete="off" readonly name="speMaintainDeadLine" id="speMaintainDeadLine2" class="datepicker notNullInput" />
			&nbsp;&nbsp;
			<label for="speProject2">隶属工程:</label>
 			<input type="text" autocomplete="off" name="speProject" id="speProject2" class="" /><br />
			&nbsp;
			<label for="speAssertsNum2"> 固定资产代号:</label>
 			<input type="text" autocomplete="off" name="speAssertsNum" id="speAssertsNum2" class="" />
			&nbsp;&nbsp;
			<label for="speBuyCont2">采购合同:</label>
 			<input type="text" autocomplete="off" name="speBuyCont" id="speBuyCont2" class="" />
			&nbsp;
			<label for="speMaintainCont2"> 维保合同:</label>
 			<input type="text" autocomplete="off" name="speMaintainCont" id="speMaintainCont2" class="" /><br />
			&nbsp;
			<label for="speMaintainFactor2"> 维保厂商:</label>
 			<input type="text" autocomplete="off" name="speMaintainFactor" id="speMaintainFactor2" class="" />
			<!--可为空-->
			&nbsp;&nbsp;
			<label for="speMasterIP2"> 主IP地址:</label>
 			<input type="text" autocomplete="off" name="speMasterIP" id="speMasterIP2" class="" />
			&nbsp;
			<label for="speSlaveIP2"> 普通IP地址:</label>
 			<input type="text" autocomplete="off" name="speSlaveIP" id="speSlaveIP2" class="" />
			
			&nbsp;<br/>
			&nbsp;
			<label for="speAssState2"> 资产状态:</label>
 			<!-- <input type="text" autocomplete="off" name="speAssState" id="speAssState2" class="" />-->
 			<select id="speAssState2"  name="speAssState">  
  				<option value ="">请选择</option>  
 				<option value ="已转固">已转固</option>  
 				<option value="待转固">待转固</option>  
				<option value ="已报废">已报废</option> 
				<option value ="待报废">待报废</option>
			</select>
			&nbsp;&nbsp;
			<label for="speFixedTime2"> 转固时间:</label>
 			<input type="text" autocomplete="off" name="speFixedTime" id="speFixedTime2" class="datepicker" />
			&nbsp;
			<label for="speScrapTime2"> 报废时间:</label>
 			<input type="text" autocomplete="off" name="speScrapTime" id="speScrapTime2" class="datepicker" />
			
			&nbsp;
			<p style=" margin-left:66px; " id="withBeizhu">
			<label for="speRemark2"> 备注:</label>
 			<input type="text" autocomplete="off" name="speRemark" id="speRemark2" class=""/>
			</p>
			<div class="buttonCon" style=" margin-left:52px; " id="withXinghao">
			  <span>带*号的为必填项，若不修改，请勿删除</span>
			  <input type="button" value="确定修改" id="changeOk" />
			</div>
			
		</form>
	
	
	</div>
  	<!-- massageBox结束 -->
</div>
<!-- 修改结束-->

 <!--生成条码-->
 <div id="getCodeBar" class="addBox dialog">
     <p><span>生成条码</span>
         <a  class="closeBox close"><img src="images/close.png" /></a>
     </p>
     <div class="codeCon">
		<!--begin-->
      	<img id="printImg" style="width:200px;height:150px;margin:auto auto; vertical-align:middle;"  title=""/>
      <!--end-->
     </div>
     <div class="printCodeBar">
         <button type="button"  onclick="printHTML()">打印条码</button>
     </div>

 </div>
 <!--显示详细信息-->
 
 <div  id="showDetail" class="addBox dialog">
     <p><span>详细信息</span>
         <a  class="closeBox close"><img src="images/close.png" /></a>
     </p>
     <div class="detailCon">
     
            <label for="detailspeDevName">设备名称:</label>
 			<input type="text" readonly id="detailspeDevName" />
			<label for="detailspeDevRoom">所属机房:</label>
 			<input type="text" readonly id="detailspeDevRoom" />	
			<label for="detailspeSeqNum">序列号:</label>
 			<input type="text" readonly id="detailspeSeqNum"  /><br />
			<label for="detailspeDevRoomFrame">所在机柜:</label>
 			<input type="text" readonly id="detailspeDevRoomFrame"  />
			<label for="detailspeUser">使用人:</label>
 			<input type="text" readonly id="detailspeUser"  />
			<label for="detailspeState">状态:</label>
			<input type="text"  readonly id="detailspeState"  /><br/>
			<label for="detailspeOwnSystem">所属系统:</label>
 			<input type="text" readonly id="detailspeOwnSystem" />
			<label for="detailspeProOwn">资产所属:</label>
 			<input type="text" readonly id="detailspeProOwn" />
			<label for="detailspeAssetsManaDepart">固定资产管理部门:</label>
 			<input type="text" readonly id="detailspeAssetsManaDepart" /><br/>
			<label for="detailspeDevFactor">设备厂商:</label>
 			<input type="text" readonly id="detailspeDevFactor" />
			<label for="detailspeVersion">型号:</label>
 			<input type="text" readonly id="detailspeVersion"  />
			<label for="detailspeManaDepart">实物管理部门:</label>
 			<input type="text" readonly id="detailspeManaDepart" /><br/>
			<label for="detailspeDevType">设备类别:</label>
 			<input type="text"  readonly id="detailspeDevType" />	
			<label for="detailspeUseDepart">使用部门:</label>
 			<input type="text"  readonly id="detailspeUseDepart" />	
			<label for="detailspeOwnNet">所属网络:</label>
 			<input type="text" readonly id="detailspeOwnNet" />	<br/>
			<label for="detailspeArrAcceptTime">到货验收时间:</label>
 			<input type="text" readonly id="detailspeArrAcceptTime" />	
			<label for="detailspeMaintainDeadLine">维保截止时间:</label>
 			<input type="text" readonly id="detailspeMaintainDeadLine" />
			
			<label for="detailspeProject">隶属工程:</label>
 			<input type="text" readonly id="detailspeProject" /><br />
			
			<label for="detailspeAssertsNum"> 固定资产代号:</label>
 			<input type="text" readonly id="detailspeAssertsNum"  />
			
			<label for="detailspeBuyCont">采购合同:</label>
 			<input type="text" readonly id="detailspeBuyCont"  />
			
			<label for="detailspeMaintainCont"> 维保合同:</label>
 			<input type="text" readonly id="detailspeMaintainCont" /><br />
			
			<label for="detailspeMaintainFactor"> 维保厂商:</label>
 			<input type="text" readonly id="detailspeMaintainFactor" />
			
			<label for="detailspeMasterIP"> 主IP地址:</label>
 			<input type="text" readonly id="detailspeMasterIP"  />
			
			<label for="detailspeSlaveIP"> 普通IP地址:</label>
 			<input type="text"  readonly id="detailspeSlaveIP" /><br />
			<label for="detailspeAssState"> 资产状态:</label>
 			<input type="text" autocomplete="off" name="detailspeAssState" id="detailspeAssState" class="" />
			<label for="detailspeFixedTime"> 转固时间:</label>
 			<input type="text" autocomplete="off" name="detailspeFixedTime" id="detailspeFixedTime" class="datepicker" />
			<label for="detailspeScrapTime"> 报废时间:</label>
 			<input type="text" autocomplete="off" name="detailspeScrapTime" id="detailspeScrapTime" class="datepicker" />
			<br/>
			<label for="detailspeRemark"> 备注:</label>
 			<input type="text" readonly id="detailspeRemark" />
     </div>

 </div>



<!--显示生命周期-->
 <div  id="showDeadline" class="addBox dialog">
     <p><span>查看生命周期</span>
         <a  class="closeBox close"><img src="images/close.png" /></a>
     </p>
     <div class="deadlineCon">
         <label>设备名称：</label><span></span><br>
         <label>标&nbsp;&nbsp;签：</label><span></span><br>
         <label>序列号：</label><span></span><br>
         <div class="deadline-nav">
         <table class="deadlineTable" cellspacing="0">
             <thead>
             <tr>
               <th>状态变化</th>
               <th>更新时间</th>
               <th>更新人</th>
             </tr>
             </thead>
             <tbody id="deadlineTbody">
             
             </tbody>
         </table>
         </div>

     </div>

 </div>


 <!--弹出框设计完成-->

<!--王佳-operate->

<!--弹出添加页面-->
  <div class="addBox" id="addBox">
  	<p><span>请填写资产详细信息</span>
  	<a id="closeBox" class="closeBox"><img src="${pageContext.request.contextPath }/images/close.png" /></a></p>
	<div id="massageBox" class="massageBox"> 
		<form  method="post" id="addForm" >
			<!--不可为空-->
			<span>*</span>
			<label for="speDevName">设备名称:</label>
 			<input type="text" autocomplete="off" name="speDevName" id="speDevName" class="notNullInput" />
			<span>*</span>
			<label for="speDevRoom">所属机房:</label>
 			<input type="text"  autocomplete="off" placeholder="双击可选" name="speDevRoom"  id="speDevRoom" list="speDevRoomList" class="notNullInput" />
 			<datalist id="speDevRoomList">
 			<%
 			 List speDevRoomList =(List)application.getAttribute("speDevRoomList1");
 				
 				for(i=0; i <speDevRoomList.size(); i++)
 			{%>
 				<option value="<%=speDevRoomList.get(i)%>">
 				
 			 <%}%>
  			
			</datalist>
			<span>*</span>
			<label for="speSeqNum">序列号:</label>
 			<input type="text" autocomplete="off" name="speSeqNum" id="speSeqNum" class="notNullInput" /><br />
			<span>*</span>
			<label for="speDevRoomFrame">所在机柜:</label>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="speDevRoomFrame"  id="speDevRoomFrame" list="speDevRoomFrameList" class="notNullInput" />
 			<datalist id="speDevRoomFrameList">
 			<%
 			 List list2 =(List)application.getAttribute("speDevRoomFrameList1");
 				
 				for(i=0; i <list2.size(); i++)
 			{%>
 				<option value="<%=list2.get(i)%>">
 				
 			 <%}%>
  			
			</datalist>
			<span>*</span>
			<label for="speUser">使用人:</label>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="speUser"  id="speUser" list="speUserList" class="notNullInput" />
 			<datalist id="speUserList">
 			<%
 			 List list3 =(List)application.getAttribute("speUserList1");
 				
 				for(i=0; i <list3.size(); i++)
 			{%>
 				<option value="<%=list3.get(i)%>">
 				
 			 <%}%>
  			
			</datalist>
			<span>*</span>
			<label for="speState">状态:</label>
			<select id="speState"  name="speState">  
  				<option value ="">请选择</option>  
 				<option value ="在维">在维</option>  
 				<option value="不在维">不在维</option>  
				<option value ="下线">下线</option> 
			</select><br/>
		
			<span>*</span>
			<label for="speOwnSystem">所属系统:</label>
 			<input type="text" autocomplete="off" name="speOwnSystem" id="speOwnSystem" class="notNullInput" />
			<span>*</span>
			<label for="speProOwn">资产所属:</label>
 			<input type="text" autocomplete="off" name="speProOwn" id="speProOwn" class="notNullInput" />
			<span>*</span>
			<label for="speAssetsManaDepart">固定资产管理部门:</label>
 			<input type="text" autocomplete="off"  placeholder="双击可选" name="speAssetsManaDepart"  id="speAssetsManaDepart" list="speAssetsManaDepartList" class="notNullInput" />
 			<datalist id="speAssetsManaDepartList" >
 			<%
 			 List list10 =(List)application.getAttribute("speAssetsManaDepartList1");
 				
 				for(i=0; i <list10.size(); i++)
 			{%>
 				<option value="<%=list10.get(i)%>">
 				
 			 <%}%>
			</datalist><br />
 			<span>*</span>
			<label for="speDevFactor">设备厂商:</label>
 			<input type="text" autocomplete="off" name="speDevFactor" id="speDevFactor" class="notNullInput" />
			<span>*</span>
			<label for="speVersion">型号:</label>
 			<input type="text" autocomplete="off" name="speVersion" id="speVersion" class="notNullInput" />
			<span>*</span>
			<label for="speManaDepart">实物管理部门:</label>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="speManaDepart"  id="speManaDepart" list="speManaDepartList" class="notNullInput" />
 			<datalist id="speManaDepartList">
 			<%
 			 List list11 =(List)application.getAttribute("speManaDepartList1");
 				
 				for(i=0; i <list11.size(); i++)
 			{%>
 				<option value="<%=list11.get(i)%>">
 				
 			 <%}%>
  			
			</datalist><br />
			<span>*</span>
			<label for="speDevType">设备类别:</label>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="speDevType"  id="speDevType" list="speDevTypeList" class="notNullInput" />
 			<datalist id="speDevTypeList">
 				<option value="交换机">
  				<option value="路由器">
  				<option value="服务器">
  				<option value="其他">
			</datalist>
			<span>*</span>
			<label for="speUseDepart">使用部门:</label>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="speUseDepart"  id="speUseDepart" list="speUseDepartList" class="notNullInput" />
 			<datalist id="speUseDepartList">
 			<%
 			 List list12 =(List)application.getAttribute("speUseDepartList1");
 				
 				for(i=0; i <list12.size(); i++)
 			{%>
 				<option value="<%=list12.get(i)%>">
 				
 			 <%}%>
  			
			</datalist>
			<span>*</span>
			<label for="speOwnNet">所属网络:</label>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="speOwnNet"  id="speOwnNet" list="speOwnNetList" class="notNullInput" />
 			<datalist id="speOwnNetList">
 			<%
 			 List List2 =(List)application.getAttribute("speOwnNetList1");
 				
 				for(i=0; i <List2.size(); i++)
 			{%>
 				<option value="<%=List2.get(i)%>">
 				
 			 <%}%>
			</datalist><br />
			<span>*</span>
			<label for="speArrAcceptTime">到货验收时间:</label>
 			<input type="text" autocomplete="off" readonly name="speArrAcceptTime" id="speArrAcceptTime" class="datepicker notNullInput" />
			<span>*</span>
			<label for="speMaintainDeadLine">维保截止时间:</label>
 			<input type="text" autocomplete="off" readonly name="speMaintainDeadLine" id="speMaintainDeadLine" class="datepicker notNullInput" />
			&nbsp;&nbsp;
			<label for="speProject">隶属工程:</label>
 			<input type="text" autocomplete="off" name="speProject" id="speProject" class="" /><br />
			&nbsp;
			<label for="speAssertsNum"> 固定资产代号:</label>
 			<input type="text" autocomplete="off" name="speAssertsNum" id="speAssertsNum" class="" />
			&nbsp;&nbsp;
			<label for="speBuyCont">采购合同:</label>
 			<input type="text" autocomplete="off" name="speBuyCont" id="speBuyCont" class="" />
			&nbsp;
			<label for="speMaintainCont"> 维保合同:</label>
 			<input type="text" autocomplete="off" name="speMaintainCont" id="speMaintainCont" class="" /><br />
			&nbsp;
			<label for="speMaintainFactor"> 维保厂商:</label>
 			<input type="text" autocomplete="off" name="speMaintainFactor" id="speMaintainFactor" class="" />
			
		
			<!--可为空-->
			
			&nbsp;&nbsp;
			<label for="speMasterIP"> 主IP地址:</label>
 			<input type="text" autocomplete="off" name="speMasterIP" id="speMasterIP" class="" />
			&nbsp;
			<label for="speSlaveIP"> 普通IP地址:</label>
 			<input type="text" autocomplete="off" name="speSlaveIP" id="speSlaveIP" class="" />
			
			&nbsp;<br/>
			&nbsp;
			<label for="speAssState"> 资产状态:</label>
 			<!-- <input type="text" autocomplete="off" name="speAssState" id="speAssState" class="" />-->
 			<select id="speAssState"  name="speAssState">  
  				<option value ="">请选择</option>  
 				<option value ="已转固">已转固</option>  
 				<option value="待转固">待转固</option>  
				<option value ="已报废">已报废</option> 
				<option value ="待报废">待报废</option>
			</select>
			&nbsp;&nbsp;
			<label for="speFixedTime"> 转固时间:</label>
 			<input type="text" autocomplete="off" name="speFixedTime" id="speFixedTime" class="datepicker" />
			&nbsp;
			<label for="speScrapTime"> 报废时间:</label>
 			<input type="text" autocomplete="off" name="speScrapTime" id="speScrapTime" class="datepicker" />
			
			&nbsp;
			
			<p ><label for="speRemark"> 备注:</label>
 			<input type="text" autocomplete="off" name="speRemark" id="speRemark" class=""/>
			</p>
			<div class="siveBoxAdd">
			<input type="button" value="提交" id="sive" />
			<span>*为必填项,请如实填写</span>
			</div>
			
		</form>
	
	
	</div>
  	<!-- massageBox结束 -->
  
  </div>
  <!-- addBox结束 -->
  
  <!-- changeBox -->
  <div class="changeBox" id="changeBox">
  		
  			<p><span>批量修改资产信息</span>
    		 </p>
    		 <a id="changeCloseBox" class="changeCloseBox"><img src="images/close.png" /></a>
    	<div class="changeMessageBox">
    	<form id="changeForm1" method="post" >
    		<label for="changeSpeState">状 态<span></span></label><span class="colon">:</span>
 			
			<select id="changeSpeState">  
  				<option value ="">请选择</option>  
 				<option value ="1">在维</option>  
 				<option value="2">不在维</option>  
				<option value ="3">下线</option> 
			</select><br /><br />
    		<label for="changeSpeUser">使 用 人<span></span></label><span class="colon">:</span>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="changeSpeUser"  id="changeSpeUser" list="changeSpeUserList" class="notNullInput" />
 			<datalist id="changeSpeUserList">
 			<%
 			 List userList =(List)application.getAttribute("speUserList1");
 				
 				for(i=0; i <userList.size(); i++)
 			{%>
 				<option value="<%=userList.get(i)%>">
 				
 			 <%}%>
			</datalist><br /><br />
			
  			<label for="changeSpeDevRoom">所 属 机 房<span></span></label><span class="colon">:</span>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="changeSpeDevRoom" id="changeSpeDevRoom" list="changeSpeDevRoomList" class="notNullInput" />
 			<datalist id="changeSpeDevRoomList">
 			<%
 			 List roomList =(List)application.getAttribute("speDevRoomList1");
 				
 				for(i=0; i <roomList.size(); i++)
 			{%>
 				<option value="<%=roomList.get(i)%>">
 				
 			 <%}%>
			</datalist><br /><br />
  			<label for="changeSpeDevRoomFrame">所 在 机 柜<span></span></label><span class="colon">:</span>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="changeSpeDevRoomFrame" id="changeSpeDevRoomFrame" list="changeSpeDevRoomFrameList" class="notNullInput" />
 			<datalist id="changeSpeDevRoomFrameList">
 			<%
 			 List frameList =(List)application.getAttribute("speDevRoomFrameList1");
 				
 				for(i=0; i <frameList.size(); i++)
 			{%>
 				<option value="<%=frameList.get(i)%>">
 				
 			 <%}%>
			</datalist><br /><br /><br />
         	<input id="sureToChange" type="button" value="确认修改"  />
         </form>
    	
		</div>
  
  </div>
    <!-- changeBox结束 -->

    
    <!-- importBox -->
  <div class="importBox" id="importBox">
      	<p class="title">您选择导入数据：</p>
      	<a id="importCloseBox" class="importCloseBox"><img src="images/close.png" /></a>
      	<form class="importForm" id="importForm" method="post" enctype="multipart/form-data">
      		<input id="fileAddress" class="fileAddress" type="file" name="file"/><br />
      		<p><input type="submit" value="确认导入" id="sureToImport" /></p>
      	</form>
  		<p class="isDownload" id="isDownload"><a href="${pageContext.request.contextPath }/Module/ZYZC.xls">下载模板文件？</a> </p>
  </div>
    <!-- importBox结束 -->
  
<!--遮罩层-->
<div id="maskIframe" >
</div>
<!-- 遮罩层结束 -->

    <script>
	var FAD='${pageContext.request.contextPath }';
	var speDevNameValue="";
  	var speRoomValue="";
  	var speRoomFrameValue="";
  	var speUserNameValue="";
  	var speStateValue="";
	</script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/Scripts/jquery.js"></script>        
	<script type="text/javascript" src="${pageContext.request.contextPath }/Scripts/speSearch.js"></script>
	 
	 <script type="text/javascript" src="${pageContext.request.contextPath }/Scripts/spetrans.js"></script>
	 <script type="text/javascript" src="${pageContext.request.contextPath }/Scripts/speExport.js"></script>
	
     <script src="${pageContext.request.contextPath }/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
     <script src="${pageContext.request.contextPath }/Scripts/choseth.js" type="text/javascript"></script>
     <script src="${pageContext.request.contextPath }/Scripts/superManager.js" type="text/javascript"></script>
     <script src="${pageContext.request.contextPath }/Scripts/addBoxLocal.js" type="text/javascript"></script>
     <script src="${pageContext.request.contextPath }/Scripts/shade.js" type="text/javascript"></script>
     <script src="${pageContext.request.contextPath }/Scripts/dialogPos.js" type="text/javascript"></script>
 
</body>
</html>