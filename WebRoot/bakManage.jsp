<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page language="java" import="java.util.*" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge" />
    <title>备品备件</title>
 
    <meta name="viewport" content="width=device-width,user-scalable=no" />
    <link href="${pageContext.request.contextPath }/Styles/superManager.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath }/Styles/addBox.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath }/Styles/bakManage.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath }/Styles/changeBox.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath }/Styles/chose.css" rel="stylesheet" type="text/css"/>
   
</head>
<body>
        <div class="bread">
            当前位置：资产管理 > 备品备件
        </div>
        <!--bread over-->
         <div class="rightHeader">

            <div class="searchRulesBox">
                <form name="form1">
                    <label for="searchbakSeqNum">序列号</label>
                   <input  type="text" autocomplete="off" class="searchRules" id="searchbakSeqNum" />

                    <label for="searchbakDevType">设备类别</label>
                    <input  type="text" autocomplete="off" placeholder="双击可选" class="searchRules" id="searchbakDevType" list="bakDevTypelist" />
                    <datalist id="bakDevTypelist">
                        <%
 						 List bakDevTypelist =(List)application.getAttribute("bakDevTypeList1");
 							int i ;
 							for( i = 0; i <bakDevTypelist.size(); i++)
 							{%>
 						<option value="<%=bakDevTypelist.get(i)%>">
 			 			<%}%>
                    </datalist>

                    <label for="searchbakUpdTime" >更新时间</label>
                    <input  type="text" autocomplete="off"  readonly  id="searchbakUpdTime" class="datepicker searchRules"   />

                    <input type="button" value="查询" id="searchButton" />
                </form>
            </div>
            
            <div class="operate">
                <span class="first"><a id="add"><img src="images/add.png" />添加</a></span><span class="line">|</span>
                <span><a id="change"><img src="images/change.png" />批量修改</a></span><span class="line">|</span>
                <span><a id="delete"><img src="images/delete.png" />删除</a></span><span class="line">|</span>
                <span><a id="export"><img src="images/output.png" />导出</a></span><span class="line">|</span>
                <span><a id="import"><img src="images/input.png" />导入</a></span>

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


            <table  cellspacing="0" >
                <thead>
                <tr>
                    <th>序号</th>
                    <th><input type="checkbox" value="1" id="checkbox"/></th>
                    <th>序列号</th>
                    <th>型号</th>
                    <th>设备厂商</th>
                    <th>设备类别<img src="images/downdown.png"></th>
                    <th>实物形态</th>
                    <th>状态<img src="images/downdown.png"></th>
                    <th>所属库房<img src="images/downdown.png"></th>
                    <th>所在货架<img src="images/downdown.png"></th>
                    <th>实物管理部门<img src="images/downdown.png"></th>
                    <th>使用部门<img src="images/downdown.png"></th>
                    <th>使用人</th>
                    <th>操作</th>
                </tr>
                </thead>

                <tbody id="massageTable">
                </tbody>
            </table>
        </div>
        <!--tableBox end-->

        <div class="rightFooter">
            <div class="leadCount">共<span id="count">0</span>条记录，每页<span id="perPageCount">20</span>条，共<span id="pages">0</span>页</div>
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
    <!--right over-->
    <!--点击th出现-->
        <!--设备类别-->

        <div id="bakDevType1" class="choseDiv">
            <ul>
            </ul>
        </div>
        <!--状态-->
        <div id="bakState1" class="choseDiv">
            <ul>
                <li>入库</li>
                <li>出库</li>
                <li>待报废</li>
                <li>报废</li>
            </ul>
        </div>
        <!--所属库房-->
        <div id="bakDevRoom1" class="choseDiv">
            <ul>
            </ul>
        </div>
        <!--所在货架-->

        <div id="bakDevFrame1" class="choseDiv">
            <ul>
            </ul>
        </div>
        <!--实物管理部门-->

        <div id="bakManaDepart1" class="choseDiv">
            <ul>
            </ul>
        </div>

        <!--使用部门-->
        <div id="bakUseDepart1" class="choseDiv">
            <ul>
            </ul>
        </div>
        <!--使用人-->
        <div id="bakUser1" class="choseDiv">
            <ul>
            </ul>
        </div>
        <!--结束--> 

        <!--点击操作显示-->
        <div id="showOperate">
            <ul>
                <li>删除</li>
                <li id="changeInfo">修改</li>
                <li id="detail">详细信息</li>
                <li id="deadline">生命周期查询</li>
            </ul>
        </div>
        <!--弹出框的设计-->
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
	   <div style="padding-bottom:0px;" class="massageBox"> 
		<form id="changeForm">
			<input type="hidden" id="bakId2" name="bakId"/>
			<span>*</span>
			<label for="bakSeqNum2">序列号:</label>
 			<input type="text" autocomplete="off" name="bakSeqNum" id="bakSeqNum2" class="notNullInput" />
			<span>*</span>
			<label for="bakVersion2">型号:</label>
 			<input type="text" autocomplete="off" name="bakVersion" id="bakVersion2" class="notNullInput" />
			<span>*</span>
			<label for="bakFactory2">设备厂商:</label>
 			<input type="text" autocomplete="off" name="bakFactory" id="bakFactory2" class="notNullInput" /><br />
			<span>*</span>
			<label for="bakDevType2">设备类别:</label>
 			<input type="text" autocomplete="off" name="bakDevType" id="bakDevType2" class="notNullInput" />
			<span>*</span>
			<label for="bakForm2">实物形态:</label>
 			<input type="text" autocomplete="off" name="bakForm" id="bakForm2" class="notNullInput" />
			<span>*</span>
			<label for="bakState2">状态:</label>
			<select id="bakState2" name="bakState">  
  				<option value ="">请选择</option>  
 				<option value ="入库">入库</option>  
 				<option value="出库">出库</option>  
				<option value ="待报废">待报废</option> 
				<option value ="报废">报废</option> 
			</select><br />
			<span>*</span>
			<label for="bakManaDepart2">实物管理部门:</label>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="bakManaDepart" id="bakManaDepart2" list="bakManaDepartList2" class="notNullInput" />
 			<datalist id="bakManaDepartList2">
 				<%
 			 List bakManaDepartList2 =(List)application.getAttribute("bakManaDepartList1");
 				
 				for(i=0; i <bakManaDepartList2.size(); i++)
 			{%>
 				<option value="<%=bakManaDepartList2.get(i)%>">
 				
 			 <%}%>
  			
			</datalist>
			<span>*</span>
			<label for="bakUseDepart2">使用部门:</label>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="bakUseDepart" id="bakUseDepart2" list="bakUseDepartList2" class="notNullInput" />
 			<datalist id="bakUseDepartList2">
 			<%
 			 List bakUseDepartList2 =(List)application.getAttribute("bakUseDepartList1");
 				
 				for(i=0; i <bakUseDepartList2.size(); i++)
 			{%>
 				<option value="<%=bakUseDepartList2.get(i)%>">
 				
 			 <%}%>
			</datalist>
 			
			<span>*</span>
			<label for="bakUser2">使用人:</label>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="bakUser" id="bakUser2" list="bakUserList2" class="notNullInput" />
 			<datalist id="bakUserList2">
 			<%
 			 List bakUserList2 =(List)application.getAttribute("bakUserList1");
 				
 				for(i=0; i <bakUserList2.size(); i++)
 			{%>
 				<option value="<%=bakUserList2.get(i)%>">
 				
 			 <%}%>
			</datalist><br />
			<span>*</span>
			<label for="bakDevRoom2">所属库房:</label>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="bakDevRoom" id="bakDevRoom2" list="bakDevRoomList2" class="notNullInput" />
 			<datalist id="bakDevRoomList2">
 			<%
 			 List bakDevRoomList2 =(List)application.getAttribute("bakDevRoomList1");
 				
 				for(i=0; i <bakDevRoomList2.size(); i++)
 			{%>
 				<option value="<%=bakDevRoomList2.get(i)%>">
 				
 			 <%}%>
  			
			</datalist>
			<span>*</span>
			<label for="bakDevFrame2">所在货架:</label>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="bakDevFrame" id="bakDevFrame2" list="bakDevFrameList2" class="notNullInput" />
 			<datalist id="bakDevFrameList2">
 			<%
 			 List bakDevFrameList2 =(List)application.getAttribute("bakDevFrameList1");
 				
 				for(i=0; i <bakDevFrameList2.size(); i++)
 			{%>
 				<option value="<%=bakDevFrameList2.get(i)%>">
 				
 			 <%}%>
  			
			</datalist>
			<span>*</span>
			<label for="bakArrAcceptTime2">到货验收时间:</label>
 			<input type="text" autocomplete="off" readonly name="bakArrAcceptTime" id="bakArrAcceptTime2" class="datepicker notNullInput" /><br />
			<span>*</span>
			<label for="bakRecorder2">记录负责人:</label>
 			<input type="text" autocomplete="off" name="bakRecorder" id="bakRecorder2" class="notNullInput" />
			<span>*</span>
			<label for="bakMaintainDeadLine2">维保截止时间:</label>
 			<input type="text" autocomplete="off" readonly name="bakMaintainDeadLine" id="bakMaintainDeadLine2" class="datepicker notNullInput" />
			&nbsp;&nbsp;
			<label for="bakBuyCont2"> 采购合同:</label>
 			<input type="text" autocomplete="off" name="bakBuyCont" id="bakBuyCont2" class="" /><br />
			&nbsp;
			<label for="bakMaintainCont2"> 维保合同: </label>
 			<input type="text" autocomplete="off" name="bakMaintainCont" id="bakMaintainCont2" class="" />
			
			&nbsp;
			<label for="bakMaintainFactor2"> 维保厂商: </label>
 			<input type="text" autocomplete="off" name="bakMaintainFactor" id="bakMaintainFactor2" class="" />
			&nbsp;&nbsp;&nbsp;
			<label for="bakRemark2"> 备注:</label>
 			<input type="text" autocomplete="off" name="bakRemark" id="bakRemark2" class="" /><br />
		
			<div class="buttonCon">
			    <span>带*号的为必填项，若不修改，请勿删除</span>
			    <input type="button" value="确定修改" id="changeOk" />
			</div>
			
		</form>
	
	
	</div>
  	<!-- massageBox结束 -->
  
  </div>
  <!-- 修改结束 -->
         
        <!--生成条码-->
        <div id="getCodeBar" class="addBox dialog">
            <p><span>生成条码</span>
                <a  class="closeBox close"><img src="images/close.png" /></a>
            </p>
            <div class="codeCon">

            </div>
            <div class="printCodeBar">
                <button type="button">打印条码</button>
            </div>

        </div>
        
        <!--显示详细信息-->
        
         <div  id="showDetail" class="addBox dialog">
            <p><span>详细信息</span>
                <a  class="closeBox close"><img src="images/close.png" /></a>
            </p>
            <div class="detailCon">
				<label for="detailbakSeqNum">序列号:</label>
	 			<input type="text" readonly id="detailbakSeqNum"  />
				
				<label for="detailbakVersion">型号:</label>
	 			<input type="text" readonly id="detailbakVersion" />
				
				<label for="detailbakFactory">设备厂商:</label>
	 			<input type="text" readonly id="detailbakFactory"  /><br />
			
				<label for="detailbakDevType">设备类别:</label>
	 			<input type="text" readonly id="detailbakDevType" />
				
				<label for="detailbakForm">实物形态:</label>
	 			<input type="text" readonly id="detailbakForm"  />
				
				<label for="detailbakState">状态:</label>
	 			<input type="text" readonly id="detailbakState" />
				<br />
				
				<label for="detailbakManaDepart">实物管理部门:</label>
	 			<input type="text" readonly id="detailbakManaDepart" />
	 			
				<label for="detailbakUseDepart">使用部门:</label>
	 			<input type="text" readonly id="detailbakUseDepart"  />
	 			
				<label for="detailbakUser">使用人:</label>
	 			<input type="text" readonly id="detailbakUser"  /><br/>
	 			
				<label for="detailbakDevRoom">所属库房:</label>
	 			<input type="text" readonly id="detailbakDevRoom"  />
	 			
				<label for="detailbakDevFrame">所在货架:</label>
	 			<input type="text" readonly id="detailbakDevFrame" />
	 			
				<label for="detailbakArrAcceptTime">到货验收时间:</label>
	 			<input type="text" readonly id="detailbakArrAcceptTime"/><br />
			
				<label for="detailbakRecorder">记录负责人:</label>
	 			<input type="text" readonly id="detailbakRecorder" />
			
				<label for="detailbakMaintainDeadLine">维保截止时间:</label>
	 			<input type="text" readonly id="detailbakMaintainDeadLine" />
			
				<label for="detailbakBuyCont"> 采购合同:</label>
	 			<input type="text" readonly id="detailbakBuyCont"/><br />
				
				<label for="detailbakMaintainCont"> 维保合同: </label>
	 			<input type="text"  readonly id="detailbakMaintainCont"  />
				
				<label for="detailbakMaintainFactor"> 维保厂商: </label>
	 			<input type="text" readonly id="detailbakMaintainFactor"  />
				
				<label for="detailbakRemark"> 备注:</label>
	 			<input type="text" readonly id="detailbakRemark" /><br />
			
            </div>
        </div>

        <!--显示生命周期-->
        <div  id="showDeadline" class="addBox dialog">
            <p><span>查看生命周期</span>
                <a  class="closeBox close"><img src="images/close.png" /></a>
            </p>
            <div class="deadlineCon">
                <label>序列号：</label><span></span>
                <br>
                <label>型&nbsp;号：</label><span></span>
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



<!--王佳-->
		
		<!--弹出添加页面-->
  	<div class="addBox" id="addBox" >
  	<p><span>请填写资产详细信息</span>
  	<a id="closeBox" class="closeBox"><img src="${pageContext.request.contextPath}/images/close.png" /></a></p>
	<div id="massageBox" class="massageBox"> 
		<form id="addForm" method="post"><!-- action="${pageContext.request.contextPath }/probak/bakAction_add.action" -->
			
			<!--不可为空-->
			<span>*</span>
			<label for="bakSeqNum">序列号:</label>
 			<input type="text" autocomplete="off" name="bakSeqNum" id="bakSeqNum" class="notNullInput" />
			<span>*</span>
			<label for="bakVersion">型号:</label>
 			<input type="text" autocomplete="off" name="bakVersion" id="bakVersion" class="notNullInput" />
			<span>*</span>
			<label for="bakFactory">设备厂商:</label>
 			<input type="text" autocomplete="off" name="bakFactory" id="bakFactory" class="notNullInput" /><br />
			<span>*</span>
			<label for="bakDevType">设备类别:</label>
 			<input type="text" autocomplete="off" name="bakDevType" id="bakDevType" class="notNullInput" />
			<span>*</span>
			<label for="bakForm">实物形态:</label>
 			<input type="text" autocomplete="off" name="bakForm" id="bakForm" class="notNullInput" />
			<span>*</span>
			<label for="bakState">状态:</label>
 			<!--<input type="text" name="bakState" id="bakState" class="notNullInput" />-->
			<select id="bakState" name="bakState">  
  				<option value ="">请选择</option>  
 				<option value ="入库">入库</option>  
 				<option value="出库">出库</option>  
				<option value ="待报废">待报废</option> 
				<option value ="报废">报废</option> 
			</select><br />
			<span>*</span>
			<label for="bakManaDepart">实物管理部门:</label>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="bakManaDepart" id="bakManaDepart" list="bakManaDepartList" class="notNullInput" />
 			<datalist id="bakManaDepartList">
 				<%
 			 List bakManaDepartList =(List)application.getAttribute("bakManaDepartList1");
 				
 				for(i=0; i <bakManaDepartList.size(); i++)
 			{%>
 				<option value="<%=bakManaDepartList.get(i)%>">
 				
 			 <%}%>
  			
			</datalist>
			<span>*</span>
			<label for="bakUseDepart">使用部门:</label>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="bakUseDepart" id="bakUseDepart" list="bakUseDepartList" class="notNullInput" />
 			<datalist id="bakUseDepartList">
 			<%
 			 List bakUseDepartList =(List)application.getAttribute("bakUseDepartList1");
 				
 				for(i=0; i <bakUseDepartList.size(); i++)
 			{%>
 				<option value="<%=bakUseDepartList.get(i)%>">
 				
 			 <%}%>
			</datalist>
 			
			<span>*</span>
			<label for="bakUser">使用人:</label>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="bakUser" id="bakUser" list="bakUserList" class="notNullInput" />
 			<datalist id="bakUserList">
 			<%
 			 List bakUserList =(List)application.getAttribute("bakUserList1");
 				
 				for(i=0; i <bakUserList.size(); i++)
 			{%>
 				<option value="<%=bakUserList.get(i)%>">
 				
 			 <%}%>
			</datalist><br />
			<span>*</span>
			<label for="bakDevRoom">所属库房:</label>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="bakDevRoom" id="bakDevRoom" list="bakDevRoomList" class="notNullInput" />
 			<datalist id="bakDevRoomList">
 			<%
 			 List bakDevRoomList =(List)application.getAttribute("bakDevRoomList1");
 				
 				for(i=0; i <bakDevRoomList.size(); i++)
 			{%>
 				<option value="<%=bakDevRoomList.get(i)%>">
 				
 			 <%}%>
  			
			</datalist>
			<span>*</span>
			<label for="bakDevFrame">所在货架:</label>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="bakDevFrame" id="bakDevFrame" list="bakDevFrameList" class="notNullInput" />
 			<datalist id="bakDevFrameList">
 			<%
 			 List bakDevFrameList =(List)application.getAttribute("bakDevFrameList1");
 				
 				for(i=0; i <bakDevFrameList.size(); i++)
 			{%>
 				<option value="<%=bakDevFrameList.get(i)%>">
 				
 			 <%}%>
  			
			</datalist>
			<span>*</span>
			<label for="bakArrAcceptTime">到货验收时间:</label>
 			<input type="text" autocomplete="off" readonly name="bakArrAcceptTime" id="bakArrAcceptTime" class="datepicker notNullInput" /><br />
			<span>*</span>
			<label for="bakRecorder">记录负责人:</label>
 			<input type="text" autocomplete="off" name="bakRecorder" id="bakRecorder" class="notNullInput" />
			<span>*</span>
			<label for="bakMaintainDeadLine">维保截止时间:</label>
 			<input type="text" autocomplete="off" readonly name="bakMaintainDeadLine" id="bakMaintainDeadLine" class="datepicker notNullInput" />
			&nbsp;&nbsp;
			<label for="bakBuyCont"> 采购合同:</label>
 			<input type="text" autocomplete="off" name="bakBuyCont" id="bakBuyCont" class="" /><br />
			&nbsp;
			<label for="bakMaintainCont"> 维保合同: </label>
 			<input type="text" autocomplete="off" name="bakMaintainCont" id="bakMaintainCont" class="" />
			
			
			<!--可为空-->
			&nbsp;
			<label for="bakMaintainFactor"> 维保厂商: </label>
 			<input type="text" autocomplete="off" name="bakMaintainFactor" id="bakMaintainFactor" class="" />
			&nbsp;&nbsp;&nbsp;
			<label for="bakRemark"> 备注:</label>
 			<input type="text" autocomplete="off" name="bakRemark" id="bakRemark" class="" /><br />
		
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
    		<label for="changeBakState">状 态<span></span></label><span class="colon">:</span>
			<select id="changeBakState">  
  				<option value ="">请选择</option>  
 				<option value ="1">入库</option>  
 				<option value="2">出库</option>  
				<option value ="3">待报废</option> 
				<option value ="4">报废</option> 
			</select><br /><br />
    		<label for="changeBakUser">使 用 人<span></span></label><span class="colon">:</span>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="changeBakUser" id="changeBakUser" list="changeBakUserList" class="notNullInput" />
 			<datalist id="changeBakUserList">
 				<%
 			 List changeBakUserList =(List)application.getAttribute("bakUserList1");
 				
 				for(i=0; i <changeBakUserList.size(); i++)
 					{%>
 				<option value="<%=changeBakUserList.get(i)%>">
 				
 				 <%}%>
			</datalist><br /><br />
			
  			<label for="changeBakDevRoom">所 属 库 房<span></span></label><span class="colon">:</span>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="changeBakDevRoom" id="changeBakDevRoom" list="changeBakDevRoomList" class="notNullInput" />
 			<datalist id="changeBakDevRoomList">
 				<%
 			 List changeBakDevRoomList =(List)application.getAttribute("bakDevRoomList1");
 				
 				for(i=0; i <changeBakDevRoomList.size(); i++)
 					{%>
 				<option value="<%=changeBakDevRoomList.get(i)%>">
 				
 				 <%}%>
			</datalist><br /><br />
  			<label for="changeBakDevFrame">所 在 货 架<span></span></label><span class="colon">:</span>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="changeBakDevFrame" id="changeBakDevFrame" list="changeBakDevFrameList" class="notNullInput" />
 			<datalist id="changeBakDevFrameList">
 				<%
 			 List changeBakDevFrameList =(List)application.getAttribute("bakDevFrameList1");
 				
 				for(i=0; i <changeBakDevFrameList.size(); i++)
 			{%>
 				<option value="<%=changeBakDevFrameList.get(i)%>">
 				
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
      		<input id="fileAddress" class="fileAddress" type="file" name="file" /><br />
      		<p><input type="submit" value="确认导入" id="sureToImport" /></p>
      	</form>
  		<p class="isDownload" id="isDownload"><a href="${pageContext.request.contextPath }/Module/BPBJ.xls">下载模板文件？</a> </p>
  </div>
    <!-- importBox结束 -->
      <!-- exportBox开始   -->
  <div class="exportBox addBox" id="exportBox">
        <p><span>导出提示</span>
         <a  class="closeBox close"><img src="images/close.png" /></a>
        </p>
        <span class="span1">本次共导出&nbsp;</span><span id="tips" class="span1"></span><span class="span1">&nbsp;条资产信息</span><br/>
        <a href="#" id="okExport">确定导出</a>
  </div>
   <!-- exportBox结束  -->
    
    <!--遮罩层-->
<div id="maskIframe" >
</div>
<!-- 遮罩层结束 -->

   <script>
	var FAD='${pageContext.request.contextPath }';
	var bakDevTypeValue="";
	var bakStateValue="";
	var bakDevRoomValue="";
	var bakDevFrameValue="";
	var bakManaDepartValue="";
	var bakUseDepartValue="";
	var bakUserValue="";
	</script>
   <script  src="${pageContext.request.contextPath }/Scripts/jquery.js" type="text/javascript"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/Scripts/bakSearch.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath }/Scripts/baktrans.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath }/Scripts/bakExport.js"></script>
   <script src="${pageContext.request.contextPath }/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
   <script src="${pageContext.request.contextPath }/Scripts/choseth.js" type="text/javascript"></script>
   <script src="${pageContext.request.contextPath }/Scripts/superManager.js" type="text/javascript"></script>
   <script src="${pageContext.request.contextPath }/Scripts/addBoxLocal.js" type="text/javascript"></script>
   <script src="${pageContext.request.contextPath }/Scripts/shade.js" type="text/javascript"></script>
   <script src="${pageContext.request.contextPath }/Scripts/dialogPos.js" type="text/javascript"></script>
  
</body>
</html>