<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page language="java" import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
     <meta http-equiv="x-ua-compatible" content="ie=edge" />
    <title>资产管理 > 线路管理</title>
   
    <meta name="viewport" content="width=device-width" />
    <link href="${pageContext.request.contextPath }/Styles/superManager.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath }/Styles/addBox.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath }/Styles/lineManage.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath }/Styles/chose.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath }/Styles/changeBox.css" rel="stylesheet" type="text/css"/>
 
</head>
<body>
        <div class="bread">
            当前位置：资产管理 > 线路管理
        </div>
        <!--bread over-->
       <div class="rightHeader">

            <div class="searchRulesBox">
                <form name="form1">
                    <label for="searchlineNum">专线号</label>
                    <input type="text" autocomplete="off" id="searchlineNum" class="searchRules"/>
                    <!--<input  type="text" class="searchRules" id="date" /> -->
                    <label for="searchlineOperator">运营商</label>
                    <input  type="text" autocomplete="off" placeholder="双击可选"  class="searchRules" id="searchlineOperator"  list="type" />
                    <datalist id="type">
                        <%
 				 	List type =(List)application.getAttribute("lineOperatorList1");
 				    int i;
 					for(i=0; i <type.size(); i++)
 					{%>
 					<option value="<%=type.get(i)%>">
 				
 					 <%}%>
                    </datalist>

                    <label for="searchlineSystem">所属系统</label>
                    <input  type="text" autocomplete="off" id="searchlineSystem" class="searchRules"  />
                    
                    <input type="button" value="查询" id="searchButton" />
                </form>
            </div>
            
            <div class="operate">
                <span class="first"><a id="add"><img src="images/add.png" />添加</a></span><span class="line">|</span>
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
                    <th>专线号</th>
                    <th>运营商<img src="images/downdown.png"></th>
                    <th>所属系统<img src="images/downdown.png"></th>
                    <th>本地/长途<img src="images/downdown.png"></th>
                    <th>带宽<img src="images/downdown.png"></th>
                    <th>A端所在机房<img src="images/downdown.png"></th>
                    <th>Z端所在机房<img src="images/downdown.png"></th>
                   <!--   <th>国家中心使用人<img src="images/downdown.png"></th>-->
                    <th style="width:100px;">操作</th>
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

        <div id="lineOperator1" class="choseDiv">
            <ul>
            </ul>
        </div>
        <div id="lineSystem1" class="choseDiv">
            <ul>
            </ul>
        </div>
        <div id="lineLocalRemote1" class="choseDiv">
            <ul>
                <li>本地</li>
                <li>长途</li>
            </ul>
        </div>
        <div id="lineBandwidth1" class="choseDiv">
            <ul>
            </ul>
        </div>
        <div id="lineARoom1" class="choseDiv">
            <ul>
            </ul>
        </div>
        <div id="lineZRoom1" class="choseDiv">
            <ul>
            </ul>
        </div>
       <!--   <div id="lineCountryUser1" class="choseDiv">
            <ul>
            </ul>
        </div>-->
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
                <a class="closeBox close"><img src="images/close.png" /></a></p>
            <div style="padding-bottom:0px;" class="massageBox">
                <form id="changeForm">
                   <input type="hidden" id="lineId2" name="lineId"/>
                    <!--不可为空-->
                    <span>*</span>
                    <label for="lineNum2">专线号:</label>
                    <input type="text" autocomplete="off" readonly name="lineNum" id="lineNum2" class="notNullInput" />
                    <span>*</span>
                    <label for="lineARoom2">A端所在机房:</label>
                    <input type="text" autocomplete="off" name="lineARoom" id="lineARoom2" class="notNullInput" />
                    <span>*</span>
                    <label for="lineZRoom2">Z端所在机房:</label>
                    <input type="text" autocomplete="off" name="lineZRoom" id="lineZRoom2" class="notNullInput" /><br />
                    <span>*</span>
                    <label for="lineARoomSite2">A端所在机房地址:</label>
                    <input type="text" autocomplete="off" name="lineARoomSite" id="lineARoomSite2" class="notNullInput" />
                    <span>*</span>
                    <label for="lineZRoomSite2">Z端所在机房地址:</label>
                    <input type="text" autocomplete="off" name="lineZRoomSite" id="lineZRoomSite2" class="notNullInput" />
                    <span>*</span>
                    <label for="lineBandWidth2">带宽:</label>
                    <input type="text" autocomplete="off" name="lineBandWidth" placeholder="输入整数" id="lineBandWidth2" class="lineBandWidth notNullInput"/><br />
                    <span>*</span>
                    <label for="lineLocalRemote2">本地/长途:</label>
                    <!--<input type="text" name="lineLocalRemote" id="lineLocalRemote" class="notNullInput" />-->
                    <select id="lineLocalRemote2" name="lineLocalRemote">
                        <option value ="">请选择</option>
                        <option value ="本地">本地</option>
                        <option value="长途">长途</option>
                    </select>
                    <span>*</span>
                    <label for="lineOperator2">运营商:</label>
                    <input type="text" name="lineOperator" placeholder="双击可选" id="lineOperator2" class="notNullInput" list="lineOperatorList2"/>
                    <datalist id="lineOperatorList2">
                       <%
 				 	List lineOperatorList2 =(List)application.getAttribute("lineOperatorList1");
 				
 					for(i=0; i <lineOperatorList2.size(); i++)
 					{%>
 					<option value="<%=lineOperatorList2.get(i)%>">
 				
 					 <%}%>
                    </datalist>
                 
                    <span>*</span>
                    <label for="lineState2">线路状态:</label>
                    <!--<input type="text" name="lineState" id="lineState" class="notNullInput" />-->
                    <select id="lineState2" name="lineState">
                        <option value ="">请选择</option>
                        <option value ="在用">在用</option>
                        <option value="空闲">空闲</option>
                    </select><br />
                    <span> *</span>
                    <label for="lineSystem2">所属系统: </label>
                    <input type="text" autocomplete="off" name="lineSystem" id="lineSystem2" class="notNullInput" />
                    <span>*</span>
                    <label for="lineType2">线路类型:</label>
                    <input type="text" autocomplete="off" name="lineType" id="lineType2" class="notNullInput" />

                    <span>*</span>
                    <label for="lineContract2">合同名称:</label>
                    <input type="text" autocomplete="off" name="lineContract" id="lineContract2" class="notNullInput" /><br />
                    <span>*</span>
                    <label for="lineContFirstParty2">合同甲方:</label>
                    <input type="text" autocomplete="off" name="lineContFirstParty" id="lineContFirstParty2" class="notNullInput" />
                    <span>*</span>
                    <label for="lineContSecondParty2">合同乙方:</label>
                    <input type="text" autocomplete="off" name="lineContSecondParty" id="lineContSecondParty2" class="notNullInput" />
                    <span>*</span>
                    <label for="lineContOperator2">合同经办人:</label>
                    <input type="text" autocomplete="off" name="lineContOperator" id="lineContOperator2" class="notNullInput" /><br />
                    <span>*</span>
                    <label for="lineSubUseDepart2">分中心使用部门:</label>
                    <input type="text" autocomplete="off" name="lineSubUseDepart" id="lineSubUseDepart2" class="notNullInput" />
                    <span>*</span>
                    <label for="lineSubUser2">分中心使用人:</label>
                    <input type="text" autocomplete="off" name="lineSubUser" id="lineSubUser2" class="notNullInput" />
                    <span>*</span>
                    <label for="lineCountryManaDepart2">国家中心管理部门:</label>
                    <input type="text" autocomplete="off" name="lineCountryManaDepart" id="lineCountryManaDepart2" class="notNullInput" /><br />
                    <span>*</span>
                    <label for="lineSubManaDepart2">分中心管理部门:</label>
                    <input type="text" autocomplete="off" name="lineSubManaDepart" id="lineSubManaDepart2" class="notNullInput" />
                    <span>*</span>
                    <label for="lineRecordor2">记录负责人:</label>
                    <input type="text" autocomplete="off" name="lineRecordor" id="lineRecordor2" class="notNullInput" /> 
                    <!--可为空-->
                    &nbsp;
                    <label for="lineLength2"> 距离:</label>
                    <input type="text" autocomplete="off" name="lineLength" placeholder="输入数值" id="lineLength2" class="" /><br />
                    <p style=" text-align:left; margin-left:68px; " id="canNullP">
                    <label for="lineUseTime2">开通时间:</label>
                    <input type="text" autocomplete="off" name="lineUseTime" id="lineUseTime2" class="datepicker" />&nbsp;
                    <label for="lineDownTime2">停租时间:</label>
                    <input type="text" autocomplete="off" name="lineDownTime" id="lineDownTime2" class="datepicker" />
                    <label for="linePayer2">付费方:</label>
                    <input type="text" autocomplete="off" name="linePayer" id="linePayer2"  /><br />
					
					
                    <label for="lineRemark2"> 备注:</label>
                    <input type="text" autocomplete="off" name="lineRemark" id="lineRemark2" class="" /><br />
                    <span>带*号的为必填项，若不修改，请勿删除</span>
                    </p>
                    <div class="buttonCon">
                      
			          <input type="button" autocomplete="off"  value="确定修改" id="changeOk" />
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
          
                    <label for="detaillineNum">专线号:</label>
                    <input type="text" readonly id="detaillineNum" />
                   
                    <label for="detaillineARoom">A端所在机房:</label>
                    <input type="text" readonly id="detaillineARoom"  />
                    
                    <label for="detaillineZRoom">Z端所在机房:</label>
                    <input type="text" readonly id="detaillineZRoom" /><br />
                   
                    <label for="detaillineARoomSite">A端所在机房地址:</label>
                    <input type="text" readonly id="detaillineARoomSite" />
                  
                    <label for="detaillineZRoomSite">Z端所在机房地址:</label>
                    <input type="text" readonly id="detaillineZRoomSite"  />
                    <label for="detaillineBandWidth">带宽:</label>
                    <input type="text" readonly id="detaillineBandWidth"/><br />
                  
                    <label for="detaillineLocalRemote">本地/长途:</label>
                    <input type="text" readonly id="detaillineLocalRemote"/>
                    <label for="detaillineOperator">运营商:</label>
                    <input type="text" readonly id="detaillineOperator"/>
     
                    <label for="detaillineState">线路状态:</label>
                    <input type="text" readonly id="detaillineState" /><br/>
                   
                    <label for="detaillineSystem">所属系统: </label>
                    <input type="text" readonly id="detaillineSystem" />
                    
                    <label for="detaillineType">线路类型:</label>
                    <input type="text" readonly id="detaillineType" />

                    <label for="detaillineContract">合同名称:</label>
                    <input type="text" readonly id="detaillineContract"  /><br />
                    
                    <label for="detaillineContFirstParty">合同甲方:</label>
                    <input type="text" readonly id="detaillineContFirstParty" />
                    
                    <label for="detaillineContSecondParty">合同乙方:</label>
                    <input type="text" readonly id="detaillineContSecondParty" />
                   
                    <label for="detaillineContOperator">合同经办人:</label>
                    <input type="text"  id="detaillineContOperator"  /><br />
                   
                    <label for="detaillineSubUseDepart">分中心使用部门:</label>
                    <input type="text" readonly id="detaillineSubUseDepart" />
                   
                    <label for="detaillineSubUser">分中心使用人:</label>
                    <input type="text"  readonly id="detaillineSubUser" />
                  	<label for="detaillineCountryManaDepart">国家中心管理部门:</label>
                    <input type="text" readonly id="detaillineCountryManaDepart" />
                    <br />
                  
                    
                  
                    <label for="detaillineSubManaDepart">分中心管理部门:</label>
                    <input type="text" readonly id="detaillineSubManaDepart"  />
                  
                    
                  
                    <label for="detaillineRecordor">记录负责人:</label>
                    <input type="text"  readonly id="detaillineRecordor"  /> 
                   
                    <label for="detaillineLength"> 距离:</label>
                    <input type="text"  readonly id="detaillineLength" /><br />
                 
                    
                    <label for="detaillineUseTime">开通时间:</label>
                    <input type="text" autocomplete="off"  id="detaillineUseTime" class="datepicker" />
                    <label for="detaillineDownTime">停租时间:</label>
                    <input type="text" autocomplete="off"  id="detaillineDownTime" class="datepicker" />
                    <label for="detaillinePayer">付费方:</label>
                    <input type="text" autocomplete="off"  id="detaillinePayer"  /><br />
					<label for="detaillineRemark"> 备注:</label> 
					<input type="text" autocomplete="off"  id="detaillineRemark" class="" />
					
            
            </div>   

        </div>


        <!--显示生命周期-->
        <div  id="showDeadline" class="addBox dialog">
            <p><span>查看生命周期</span>
                <a  class="closeBox close"><img src="images/close.png" /></a>
            </p>
            <div class="deadlineCon">
                <label>专线号：</label><span></span><br>
                <label>运营商：</label><span></span><br>
                <label>所属系统：</label><span></span><br>
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
                <a id="closeBox" class="closeBox"><img src="${pageContext.request.contextPath }/images/close.png" /></a></p>
            <div id="massageBox" class="massageBox">
                <form  id="addForm"  method="post"><!--action="${pageContext.request.contextPath}/lininfo/infoAction_add.action"  -->
                    <!--不可为空-->
                    <span>*</span>
                    <label for="lineNum">专线号:</label>
                    <input type="text" autocomplete="off" name="lineNum" id="lineNum" class="notNullInput" />
                    <span>*</span>
                    <label for="lineARoom">A端所在机房:</label>
                    <input type="text" autocomplete="off" name="lineARoom" id="lineARoom" class="notNullInput" />
                    <span>*</span>
                    <label for="lineZRoom">Z端所在机房:</label>
                    <input type="text" autocomplete="off" name="lineZRoom" id="lineZRoom" class="notNullInput" /><br />
                    <span>*</span>
                    <label for="lineARoomSite">A端所在机房地址:</label>
                    <input type="text" autocomplete="off" name="lineARoomSite" id="lineARoomSite" class="notNullInput" />
                    <span>*</span>
                    <label for="lineZRoomSite">Z端所在机房地址:</label>
                    <input type="text" autocomplete="off" name="lineZRoomSite" id="lineZRoomSite" class="notNullInput" />
                    <span>*</span>
                    <label for="lineBandWidth">带宽:</label>
                    <input type="text" autocomplete="off" name="lineBandWidth" placeholder="输入整数" id="lineBandWidth" class="lineBandWidth notNullInput"/><br />
                    <span>*</span>
                    <label for="lineLocalRemote">本地/长途:</label>
                    <!--<input type="text" name="lineLocalRemote" id="lineLocalRemote" class="notNullInput" />-->
                    <select id="lineLocalRemote" name="lineLocalRemote">
                        <option value ="">请选择</option>
                        <option value ="本地">本地</option>
                        <option value="长途">长途</option>
                    </select>
                    <span>*</span>
                    <label for="lineOperator">运营商:</label>
                    <input type="text" name="lineOperator" placeholder="双击可选" id="lineOperator" class="notNullInput" list="lineOperatorList"/>
                    <datalist id="lineOperatorList">
                       <%
 				 	List lineOperatorList =(List)application.getAttribute("lineOperatorList1");
 				
 					for(i=0; i <lineOperatorList.size(); i++)
 					{%>
 					<option value="<%=lineOperatorList.get(i)%>">
 				
 					 <%}%>
                    </datalist>
                 
                    <span>*</span>
                    <label for="lineState">线路状态:</label>
                    <!--<input type="text" name="lineState" id="lineState" class="notNullInput" />-->
                    <select id="lineState" name="lineState">
                        <option value ="">请选择</option>
                        <option value ="在用">在用</option>
                        <option value="空闲">空闲</option>
                    </select><br />
                    <span> *</span>
                    <label for="lineSystem">所属系统: </label>
                    <input type="text" autocomplete="off" name="lineSystem" id="lineSystem" class="notNullInput" />
                    <span>*</span>
                    <label for="lineType">线路类型:</label>
                    <input type="text" autocomplete="off" name="lineType" id="lineType" class="notNullInput" />

                    <span>*</span>
                    <label for="lineContract">合同名称:</label>
                    <input type="text" autocomplete="off" name="lineContract" id="lineContract" class="notNullInput" /><br />
                    <span>*</span>
                    <label for="lineContFirstParty">合同甲方:</label>
                    <input type="text" autocomplete="off" name="lineContFirstParty" id="lineContFirstParty" class="notNullInput" />
                    <span>*</span>
                    <label for="lineContSecondParty">合同乙方:</label>
                    <input type="text" autocomplete="off" name="lineContSecondParty" id="lineContSecondParty" class="notNullInput" />
                    <span>*</span>
                    <label for="lineContOperator">合同经办人:</label>
                    <input type="text" autocomplete="off" name="lineContOperator" id="lineContOperator" class="notNullInput" /><br />
                    <span>*</span>
                    <label for="lineSubUseDepart">分中心使用部门:</label>
                    <input type="text" autocomplete="off" name="lineSubUseDepart" id="lineSubUseDepart" class="notNullInput" />
                    <span>*</span>
                    <label for="lineSubUser">分中心使用人:</label>
                    <input type="text" autocomplete="off" name="lineSubUser" id="lineSubUser" class="notNullInput" />
                    <span>*</span>
                    <label for="lineCountryManaDepart">国家中心管理部门:</label>
                    <input type="text" autocomplete="off" name="lineCountryManaDepart" id="lineCountryManaDepart" class="notNullInput" />
                    <br />
                    <span>*</span>
                    <label for="lineSubManaDepart">分中心管理部门:</label>
                    <input type="text" autocomplete="off" name="lineSubManaDepart" id="lineSubManaDepart" class="notNullInput" />
                    <span>*</span>
                    <label for="lineRecordor">记录负责人:</label>
                    <input type="text" autocomplete="off" name="lineRecordor" id="lineRecordor" class="notNullInput" /> 

                    <!--可为空-->
                    &nbsp;
                    <label for="lineLength"> 距离:</label>
                    <input type="text" autocomplete="off" name="lineLength" placeholder="输入数值" id="lineLength" class="" /><br />
                    <p style=" text-align:left; margin-left:68px; " id="canNullP">
                    <label for="lineUseTime">开通时间:</label>
                    <input type="text" autocomplete="off" name="lineUseTime" id="lineUseTime" class="datepicker" />&nbsp;
                    <label for="lineDownTime">停租时间:</label>
                    <input type="text" autocomplete="off" name="lineDownTime" id="lineDownTime" class="datepicker" />
                    <label for="linePayer">付费方:</label>
                    <input type="text" autocomplete="off" name="linePayer" id="linePayer"  /><br />
					
					
                    <label for="lineRemark"> 备注:</label>
                    <input type="text" autocomplete="off" name="lineRemark" id="lineRemark" class="" />
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
        <!-- importBox -->
  <div class="importBox" id="importBox">
      	<p class="title">您选择导入数据：</p>
      	<a id="importCloseBox" class="importCloseBox"><img src="images/close.png" /></a>
      	<form class="importForm" id="importForm" method="post" enctype="multipart/form-data" >
      		<input id="fileAddress" class="fileAddress" type="file" name="file" /><br />
      		<p><input type="submit" value="确认导入" id="sureToImport" /></p>
      	</form>
  		<p class="isDownload" id="isDownload"><a href="${pageContext.request.contextPath }/Module/ZX.xls">下载模板文件？</a> </p>
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
		var lineOperatorValue="";		//运营商
		var lineSystemValue="";			//所属系统
		var lineLocalRemoteValue="";	//本地/长途
		var lineBandWidthValue="";		//带宽
		var lineARoomValue="";			//A端所在机房
		var lineZRoomValue="";			//Z端所在机房
	//	var lineCountryUserValue="";	//国家中心使用人
	</script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/Scripts/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/Scripts/lineSearch.js"></script>
    
    <script type="text/javascript" src="${pageContext.request.contextPath }/Scripts/lintrans.js"></script>
     <script type="text/javascript" src="${pageContext.request.contextPath }/Scripts/lineExport.js"></script>
     <script src="${pageContext.request.contextPath }/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
     <script src="${pageContext.request.contextPath }/Scripts/choseth.js" type="text/javascript"></script>
     <script src="${pageContext.request.contextPath }/Scripts/superManager.js" type="text/javascript"></script>
     <script src="${pageContext.request.contextPath }/Scripts/addBoxLocal.js" type="text/javascript"></script>
     <script src="${pageContext.request.contextPath }/Scripts/shade.js" type="text/javascript"></script>
     <script src="${pageContext.request.contextPath }/Scripts/dialogPos.js" type="text/javascript"></script>
    
</body>
</html>