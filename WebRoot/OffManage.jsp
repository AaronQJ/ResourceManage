<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge" />
    <title>资产管理 > 办公资产</title>
  
    <meta name="viewport" content="width=device-width,user-scalable=no" />
	<link href="${pageContext.request.contextPath }/Styles/superManager.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath }/Styles/addBox.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath }/Styles/offManage.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath }/Styles/changeBox.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath }/Styles/chose.css" rel="stylesheet" type="text/css"/>
    
	
</head>
<body>


<div class="bread">
    当前位置：资产管理 > 办公资产
</div>
<!--bread over-->
<div class="rightHeader">

    <div class="searchRulesBox">
        <form name="form1">
            <label for="searchoffSeqNum">商品序列号</label>
            <input  type="text" autocomplete="off" class="searchRules"  id="searchoffSeqNum"/>
            <label for="searchoffName">名称</label>
            <input  type="text" autocomplete="off" class="searchRules" id="searchoffName"/>

            <lebel for="searchoffNum">资产编号</lebel>
            <input  type="text" autocomplete="off" class="searchRules"  id="searchoffNum"/>
			<label for="searchoffBarCode">条码</label>
            <input type="text" autocomplete="off" class="searchRules"  id="searchoffBarCode"/>
            <label for="searchoffUser">存放地点</label>
            <input type="text" autocomplete="off" placeholder="双击可选" class="searchRules" id="searchoffUser" list="whouse"/>
            <datalist id="whouse">
                 <datalist id="whouse">
              <%
 				 List whouse =(List)application.getAttribute("offUserList1");
 				 int i;
 				 for(i=0; i <whouse.size(); i++)
 				{%>
 				<option value="<%=whouse.get(i)%>">
 					
 				<%}%>
            </datalist>

            </datalist>

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


<!--表格-->
<div class="tableBox">
    <table  cellspacing="0" >
        <thead>
        <tr>
            <th>序号</th>
            <th><input type="checkbox" value="1" id="checkbox"/></th>
            <th>资产编号</th>
            <th>名称<img src="images/downdown.png"></th>
            <th>设备型号</th>
            <th>商品序列号</th>
            <th>存放地点</th>
            <th>使用状况<img src="images/downdown.png"></th>
            <th>状态<img src="images/downdown.png"></th>
            <th>使用/管理部门<img src="images/downdown.png"></th>
            <th>责任人<img src="images/downdown.png"></th>
            <th>操作</th>
        </tr>
        </thead>

        <tbody id="massageTable">
        
        </tbody>
    </table>
</div>
<!--tableBox end-->

<div class="rightFooter">
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
<!--名称-->
<div id="offName1" class="choseDiv">
    <ul>
    </ul>
</div>
<!--使用状况-->
<div id="offUseState1" class="choseDiv">
    <ul>
    </ul>
</div>
<!--状态-->
<div id="offState1" class="choseDiv">
    <ul>
        <li>待领用</li>
        <li>在用</li>
        <li>待报废</li>
        <li>报废</li>
    </ul>
</div>
<!--使用/管理部门-->
<div id="offUseDepart1" class="choseDiv">
    <ul>
    </ul>
</div>
<!--责任人-->
<div id="offUser1" class="choseDiv">
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
        <li id="getBarcode">生成条码</li>
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
		    <input type="hidden" id="offId2" name="offId"/>
		   <!--不可为空-->
			<span>*</span>
			<label for="offNum2">资产编号:</label>
 			<input type="text" autocomplete="off" name="offNum" id="offNum2" class="notNullInput" />
			<span>*</span>
			<label for="offName2">名称:</label>
 			<input type="text" autocomplete="off" name="offName" id="offName2" class="notNullInput" />
			<span>*</span>
			<label for="offDevVersion2">设备型号:</label>
 			<input type="text" autocomplete="off" name="offDevVersion" id="offDevVersion2" class="notNullInput" /><br />
			<span>*</span>
			<label for="offObtDate2">取得日期:</label>
 			<input type="text" autocomplete="off" readonly name="offObtDate" id="offObtDate2" class="datepicker notNullInput" />
			<span>*</span>
			<label for="offUseDepart2">使用/管理部门:</label>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="offUseDepart" id="offUseDepart2" list="offUseDepartList2" class="notNullInput" />
 			<datalist id="offUseDepartList2">
 			<%
 			 List offUseDepartList2 =(List)application.getAttribute("offUseDepartList1");
 				
 				for(i=0; i <offUseDepartList2.size(); i++)
 			{%>
 				<option value="<%=offUseDepartList2.get(i)%>">
 				
 			 <%}%>
  			
			</datalist>
			<span>*</span>
			<label for="offState2">状态:</label>
			<select id="offState2" name="offState">  
  				<option value ="">请选择</option>  
 				<option value ="待领用">待领用</option>  
 				<option value="在用">在用</option>  
				<option value ="待报废">待报废</option> 
				<option value ="报废">报废</option> 
			</select>
			<br />
			<span>*</span>
			<label for="offUseState2">使用状况:</label>
 			<input type="text" autocomplete="off" name="offUseState" id="offUseState2" class="notNullInput" />
			<span>*</span>
			<label for="offManager2">责任人:</label>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="offManager" id="offManager2" list="offManageList2" class="notNullInput" />
 			<datalist id="offManageList2">
 			<%
 			 List offManageList2 =(List)application.getAttribute("offManageList1");
 				
 				for(i=0; i <offManageList2.size(); i++)
 			{%>
 				<option value="<%=offManageList2.get(i)%>">
 				
 			 <%}%>
			</datalist>
			<span>*</span>
			<label for="offUser2">存放地点:</label>
 			<input type="text" autocomplete="off" name="offUser" id="offUser2" class="notNullInput" /><br />
			<span>*</span>
			<label for="offSeqNum2">商品序列号:</label>
 			<input type="text" autocomplete="off" readonly name="offSeqNum" id="offSeqNum2" class="notNullInput" />
			
			<!--可为空-->
			&nbsp;
			<label for="offPrice2"> 价格:</label>
 			<input type="text" autocomplete="off" placeholder="0000或0000.00格式" name="offPrice" id="offPrice2" class="" />
			&nbsp;&nbsp;
			<label for="offLifeTime2"> 使用年限:</label>
 			<input type="text" autocomplete="off" placeholder="00格式" name="offLifeTime" id="offLifeTime2" class="" />
			<br />
			<p style=" text-align:left; margin-left:73px; " id="withBeizhu2">
			<label for="offRemark2">备注:</label>
 			<input type="text" autocomplete="off" name="offRemark" id="offRemark2" class="" />
 			</p>
		    <div class="buttonCon" style=" margin-left:43px; " id="withXiugai2">
 			    <span>带*号的为必填项，若不修改，请勿删除</span>
			    <input type="button" value="确定修改" id="changeOk" />
			</div>
		
		</form>
	
	</div>
  	<!-- massageBox结束 -->
  
  </div>
  

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
        <button type="button" onclick="printHTML()">打印条码</button>
    </div>

</div>
<!--显示详细信息-->

 <div  id="showDetail" class="addBox dialog" style="text-align:center;">
     <p><span>详细信息</span>
         <a  class="closeBox close"><img src="images/close.png" /></a>
     </p>
	<div class="detailCon" style="text-align:center; ">
			<label for="detailoffNum">资产编号:</label>
 			<input type="text" readonly id="detailoffNum" />
			
			<label for="detailoffName">名称:</label>
 			<input type="text" readonly id="detailoffName" />
			
			<label for="detailoffDevVersion">设备型号:</label>
 			<input type="text" readonly id="detailoffDevVersion" /><br />
		
			<label for="detailoffObtDate">取得日期:</label>
 			<input type="text" readonly id="detailoffObtDate"  />
		
			<label for="detailoffUseDepart">使用/管理部门:</label>
 			<input type="text" readonly id="detailoffUseDepart" />
 			
			<label for="detailoffState">状态:</label>
 			<input type="text" readonly id="detailoffState"  /><br />
			
			<label for="detailoffUseState">使用状况:</label>
 			<input type="text" readonly id="detailoffUseState" />
		
			<label for="detailoffManager">责任人:</label>
 			<input type="text" readonly id="detailoffManager" />
 			
			<label for="detailoffUser">存放地点:</label>
 			<input type="text" readonly id="detailoffUser"/><br />
			
			<label for="detailoffSeqNum">商品序列号:</label>
 			<input type="text" id="detailoffSeqNum"  />
			
			<label for="detailoffPrice"> 价格:</label>
 			<input type="text" readonly id="detailoffPrice"  />
			<label for="detailoffLifeTime"> 使用年限:</label>
 			<input type="text" autocomplete="off" placeholder="00格式" name="detailoffLifeTime" id="detailoffLifeTime" class="" />
			<br />
			<p style="text-align:left; font-weight:normal;" id="withBeizhuOff" >
			<label for="detailoffRemark">备注:</label>
 			<input type="text" readonly id="detailoffRemark" />
 			</p>
     </div>
 </div>

<!--显示生命周期-->
<div  id="showDeadline" class="addBox dialog">
    <p><span>查看生命周期</span>
        <a  class="closeBox close"><img src="images/close.png" /></a>
    </p>
    <div class="deadlineCon">
        <label>资产编号：</label><span></span><br>
        <label>设备名称：</label><span></span><br>
        <label>型&nbsp;&nbsp;号：</label><span></span>
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
  <div class="addBox" id="addBox">
  	<p><span>请填写资产详细信息</span>
  	<a id="closeBox" class="closeBox"><img src="${pageContext.request.contextPath }/images/close.png" /></a></p>
	<div id="massageBox" class="massageBox"> 
		<form id="addForm" method="post"><!-- action="${pageContext.request.contextPath }/offpro/offAction_add.action" -->
		
			<!--不可为空-->
			<span>*</span>
			<label for="offNum">资产编号:</label>
 			<input type="text" autocomplete="off" name="offNum" id="soffNum" class="notNullInput" />
			<span>*</span>
			<label for="offName">名称:</label>
 			<input type="text" autocomplete="off" name="offName" id="offName" class="notNullInput" />
			<span>*</span>
			<label for="offDevVersion">设备型号:</label>
 			<input type="text" autocomplete="off" name="offDevVersion" id="offDevVersion" class="notNullInput" /><br />
			<span>*</span>
			<label for="offObtDate">取得日期:</label>
 			<input type="text" autocomplete="off" readonly name="offObtDate" id="offObtDate" class="datepicker notNullInput" />
			<span>*</span>
			<label for="offUseDepart">使用/管理部门:</label>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="offUseDepart" id="offUseDepart" list="offUseDepartList" class="notNullInput" />
 			<datalist id="offUseDepartList">
 			<%
 			 List offUseDepartList =(List)application.getAttribute("offUseDepartList1");
 				
 				for(i=0; i <offUseDepartList.size(); i++)
 			{%>
 				<option value="<%=offUseDepartList.get(i)%>">
 				
 			 <%}%>
  			
			</datalist>
			<span>*</span>
			<label for="offState">状态:</label>
 			<!--<input type="text" name="offState" id="offState" class="notNullInput" />-->
			<select id="offState" name="offState">  
  				<option value ="">请选择</option>  
 				<option value ="待领用">待领用</option>  
 				<option value="在用">在用</option>  
				<option value ="待报废">待报废</option> 
				<option value ="报废">报废</option> 
			</select>
			<br />
			<span>*</span>
			<label for="offUseState">使用状况:</label>
 			<input type="text" autocomplete="off" name="offUseState" id="offUseState" class="notNullInput" />
			<span>*</span>
			<label for="offManager">责任人:</label>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="offManager" id="offManager" list="offManageList" class="notNullInput" />
 			<datalist id="offManageList">
 			<%
 			 List offManageList =(List)application.getAttribute("offManageList1");
 				
 				for(i=0; i <offManageList.size(); i++)
 			{%>
 				<option value="<%=offManageList.get(i)%>">
 				
 			 <%}%>
			</datalist>
			<span>*</span>
			<label for="offUser">存放地点:</label>
 			<input type="text" autocomplete="off" name="offUser" id="offUser" class="notNullInput" /><br />
			<span>*</span>
			<label for="offSeqNum">商品序列号:</label>
 			<input style="width:155px;" type="text" autocomplete="off" name="offSeqNum" id="offSeqNum" class="notNullInput" />
			
			<!--可为空-->
			&nbsp;
			<label for="offPrice"> 价格:</label>
 			<input type="text" autocomplete="off" placeholder="0000或0000.00格式" name="offPrice" id="offPrice" class="" />
			&nbsp;&nbsp;
			<label for="offLifeTime"> 使用年限:</label>
 			<input type="text" autocomplete="off" placeholder="00格式" name="offLifeTime" id="offLifeTime" class="" />
			
			<br />
			<p style=" margin-left:90px; text-align:left; " id="withBeizhu">
			<label for="offRemark">备注:</label>
 			<input type="text" autocomplete="off" name="offRemark" id="offRemark" class="" />
			</p>
			<div class="siveBoxAdd" style="position:absolute;  bottom:0px; left:420px;" id="withTijiao" >
			<input type="button" value="提 交" id="sive" />
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
    		<label style="width:100px; margin-left:80px;" for="changeOffState">状 态<span></span></label><span class="colon">:</span>
 			<!--<input type="text" name="changeOffState" id="changeOffState" class="notNullInput" />-->
			<select id="changeOffState">  
  				<option value ="">请选择</option>  
 				<option value ="1">待领用</option>  
 				<option value="2">在用</option>  
				<option value ="3">待报废</option> 
				<option value ="4">报废</option> 
			</select><br /><br />
    		<label style="width:100px; margin-left:80px;" for="changeOffUser">存 放 地 点<span></span></label><span class="colon">:</span>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="changeOffUser" id="changeOffUser" list="changeOffUserList" class="notNullInput" />
 			<datalist id="changeOffUserList">
 			<%
 			 List changeOffUserList =(List)application.getAttribute("offUserList1");
 				
 				for(i=0; i <changeOffUserList.size(); i++)
 			{%>
 				<option value="<%=changeOffUserList.get(i)%>">
 				
 			 <%}%>
  			
			</datalist><br /><br />
			
  			<label style="width:100px; margin-left:80px;" for="changeOffManage">责 任 人<span></span></label><span class="colon">:</span>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="changeOffManage" id="changeOffManage" list="changeOffManageList" class="notNullInput" />
 			<datalist id="changeOffManageList">
 			<%
 			 List changeOffManageList =(List)application.getAttribute("offManageList1");
 				
 				for(i=0; i <changeOffManageList.size(); i++)
 			{%>
 				<option value="<%=changeOffManageList.get(i)%>">
 				
 			 <%}%>
			</datalist><br /><br />
			
  			<label style="width:100px; margin-left:80px;" for="changeOffUseDepart">使用/管理部门</label><span class="colon">:</span>
 			<input type="text" autocomplete="off" placeholder="双击可选" name="changeOffUseDepart" id="changeOffUseDepart" list="changeOffUseDepartList" class="notNullInput" />
 			<datalist id="changeOffUseDepartList">
 			<%
 			 List changeOffUseDepartList =(List)application.getAttribute("offUseDepartList1");
 				
 				for(i=0; i <changeOffUseDepartList.size(); i++)
 			{%>
 				<option value="<%=changeOffUseDepartList.get(i)%>">
 				
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
  		<p class="isDownload" id="isDownload"><a href="${pageContext.request.contextPath }/Module/BGZC.xls">下载模板文件？</a> </p>
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
			var offNameValue="";
  			var offUseStateValue="";
  			var offStateValue="";
  			var offUseDepartValue="";
  			var offManagerValue="";
	</script>
	<script src="${pageContext.request.contextPath }/Scripts/jquery.js" type="text/javascript"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/Scripts/offSearch.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/Scripts/offtrans.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/Scripts/offExport.js"></script>
	
    <script src="${pageContext.request.contextPath }/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
     <script src="${pageContext.request.contextPath }/Scripts/choseth.js" type="text/javascript"></script>
     <script src="${pageContext.request.contextPath }/Scripts/superManager.js" type="text/javascript"></script>
     <script src="${pageContext.request.contextPath }/Scripts/addBoxLocal.js" type="text/javascript"></script>
     <script src="${pageContext.request.contextPath }/Scripts/shade.js" type="text/javascript"></script>
     <script src="${pageContext.request.contextPath }/Scripts/dialogPos.js" type="text/javascript"></script>
     

</body>
</html>