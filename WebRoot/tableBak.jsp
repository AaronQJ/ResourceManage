<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge" />
    <title>报表管理 > 备品备件</title>
    <meta name="viewport" content="width=device-width,user-scalable=no" />
   
    <link href="${pageContext.request.contextPath }/Styles/superManager.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath }/Styles/form.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath }/Scripts/jquery.js"></script>
    <script src="${pageContext.request.contextPath }/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath }/Scripts/highcharts.js"></script> 
	<script src="${pageContext.request.contextPath }/Scripts/exporting.js"></script>
	<script src="${pageContext.request.contextPath }/Scripts/tableBak.js"></script>
	<style type="text/css">
		.clearfloat{ zoom:1; }
		.clearfloat:after{ display:block; clear:both; content:""; visibility:hidden; height:0; }
		.equTypeBox{ width:400px; height:auto;overflow-x:auto;  float:left; margin: 50px 0 0 100px; text-align:center; }
		#equType{ min-width:200px; width:400px;  height:360px; }
		#equState{ float:left; width:400px; height:400px; margin: 50px 0 0 110px; }
		.tableBox1{overflow:scroll; width:100%; height:auto;  }
		#listUl{ margin-left:20px; }
		#listUl li{ float:left; margin-right:5px ; font-size:13px; width:80px; text-align:center; border-radius: 5px 5px 0 0; cursor:pointer; }
		#listUl li:hover{ background-color:#fff; }
		.clicked{background-color:#fff;   }
		
		/*翻页 左右*/
		.leadPage a img{ width:10px; height:10px }
		.leadPage input{ width:30px; height:15px; border:1px solid #B1CEEC; text-align:center }
	
	</style>
	<script type="text/javascript">
	//日历选择时间

	$(function(){
 		 $(".datepicker").bind("click",function(){
    	 WdatePicker();
  		});
	});
	//td的悬浮效果
	$(function(){
    $("#massageTable").on("mouseover","td",function(){
                $(this).prop("title",$(this).text());
      
    	});
	});
	$(function(){
	
		if(document.getElementById("numCount"))
			var numCount=document.getElementById("numCount");
		else return false;
	//	else alert("获取失败")
		if(document.getElementById("scrapingList"))
			var scrapingList=document.getElementById("scrapingList");
		else return false;
		if(document.getElementById("chartsDiv"))
			var chartsDiv=document.getElementById("chartsDiv");
		else return false;
		if(document.getElementById("scrapingListBox"))
			var scrapingListBox=document.getElementById("scrapingListBox");
		else return false;
		numCount.onclick=function()
			{
				//alert("1");
				$(numCount).addClass("clicked");
				$(scrapingList).removeClass("clicked");

				chartsDiv.style.display="block";
				scrapingListBox.style.display="none";
				
			};
			scrapingList.onclick=function()
			{
				//alert("1");
				$(numCount).removeClass("clicked");
				$(scrapingList).addClass("clicked");
				//$(this).addClass("clicked");
				chartsDiv.style.display="none";
				scrapingListBox.style.display="block";
				
			};	
		
	
	
	
		});
	
	
	
	
	</script>
	
	<script type="text/javascript">
		$(function(){
		if(document.getElementById("sureToSearch"))
			var sureToSearch=document.getElementById("sureToSearch");
		else return false;
		
		sureToSearch.onclick=function()
		{
			var timeLine=$("#timeInput").val();
			if(timeLine=="")
				alert("请选择日期")
			else
			{
			//alert(timeLine);
			//alert($("#nowPage").val());
			//alert($("#perPageCount").html());
			$.ajax({
				type:"POST",
				url:"probak/bakAction_searchOutChart",			
				data:{				
					bakArrAcceptTime:$("#timeInput").val(),
					iDisplayLength:$("#perPageCount").html(),	//每页显示条数
					nowPage:"1"					//当前页数位置			        
				},
				success:function(data)
				{
					//alert("成功")
					var json =JSON.parse(data);
					var iDisplayLength=json.iDisplayLength; 				//每页条数
					var iTotalRecords=json.iTotalRecords;					//总数据条数
					var pageCount=json.totalPage;							//页数
					$("#count").html(iTotalRecords);		//设置数据总数
    				$("#perPageCount").html(iDisplayLength);//设置每页数据数
    				$("#pages").html( pageCount);			//设置总共页数
    				//$("#nowPage").attr("value",1);			//设置 当前页数位置
    				document.getElementById("nowPage").value="1";		//设置 当前页数位置
    				
    				if($("#nowPage").val()==1)
    					{	
    						document.getElementById("left").disabled=true;
    						document.getElementById("leftUp").disabled=true;
    						document.getElementById("right").disabled=false;
    						document.getElementById("rightUp").disabled=false;
    					}
    				if( Number( $("#pages").html() )==1 )
						{
							document.getElementById("right").disabled=true;
    						document.getElementById("rightUp").disabled=true;  
						}
    				
    				if($("#nowPage").val()==1)
    					{	
    						document.getElementById("left").disabled=true;
    						document.getElementById("leftUp").disabled=true;  
    						document.getElementById("right").disabled=false;
    						document.getElementById("rightUp").disabled=false;
    					}
    					if( Number( $("#pages").html() )==1 )
						{
							document.getElementById("right").disabled=true;
    						document.getElementById("rightUp").disabled=true;  
						}
    					
					$("#massageTable tr").remove();
			       	for(var i=0; i <json.data.length; i++)
    				{
          				var temp="<tr>"
                   	 	+"<td >"+(i+1)+"</td>"
                    	+"<td>"+json.data[i].bakSeqNum+"</td>"
                   		+"<td>"+json.data[i].bakVersion+"</td>"
                    	+"<td>"+json.data[i].bakFactory+"</td>"
                    	+"<td>"+json.data[i].bakDevType+"</td>"
                    	+"<td>"+json.data[i].bakForm+"</td>"
                    	+"<td>"+json.data[i].bakState+"</td>"
                    	+"<td>"+json.data[i].bakDevRoom+"</td>"
                    	+"<td>"+json.data[i].bakDevFrame+"</td>"
                    	+"<td>"+json.data[i].bakManaDepart+"</td>"
                    	+"<td>"+json.data[i].bakUseDepart+"</td>"
                    	+"<td>"+json.data[i].bakUser+"</td>"
                 		+"</tr>";        
						$(temp).appendTo($("#massageTable"));
    				}
				
				},
				error:function()
				{
					window.parent.document.getElementById('right').src="false.jsp";
				
				}
		});
	
		}
		}

	});
	//更改页面每页显示数据量开始		
	$(function()
	{
	var countPerPage=document.getElementById("countPerPage");
	
	countPerPage.onchange=function()
	  {
	 // alert($("#nowPage").val());
	 	 var iDisplayLength=$('#countPerPage option:selected').val();
	  	 switch(iDisplayLength)
       			{
            	 	case "1":
               			{
               				iDisplayLength="20";
               				break;
               			}
              		case "2":
               			{
               				iDisplayLength="30";
               				break;
               			}
              		case "3":
               			{
               				iDisplayLength="50";
               				break;
               			}
       			 }
       			 //alert(iDisplayLength)
       			 //alert($("#timeInput").val())
       			 //alert($("#nowPage").val())

 		$.ajax({
			      type:"POST",
			      url:"probak/bakAction_searchOutChart",
			      data:{
			      		bakArrAcceptTime:$("#timeInput").val(),
						iDisplayLength:iDisplayLength,	//每页显示条数
						nowPage:"1"					//当前页数位置	
			    	   },
			      success:function(data)
				  {		//alert("AJAX成功");
    					//alert("成功")
						var json =JSON.parse(data);
    					var iDisplayLength=json.iDisplayLength; 				//每页条数
						var iTotalRecords=json.iTotalRecords;					//总数据条数
						var pageCount=json.totalPage;							//页数
						$("#count").html(iTotalRecords);		//设置数据总数
    					$("#perPageCount").html(iDisplayLength);//设置每页数据数
    					$("#pages").html( pageCount);			//设置总共页数
    					//$("#nowPage").attr("value",1);			//设置 当前页数位置
    					document.getElementById("nowPage").value="1";		//设置 当前页数位置
						$("#massageTable tr").remove();
    					
    					if($("#nowPage").val()==1)
    					{	
    						document.getElementById("left").disabled=true;
    						document.getElementById("leftUp").disabled=true;  
    						document.getElementById("right").disabled=false;
    						document.getElementById("rightUp").disabled=false;
    					}
    					if( Number( $("#pages").html() )==1 )
						{
							document.getElementById("right").disabled=true;
    						document.getElementById("rightUp").disabled=true;  
						}
    					
    					switch(iDisplayLength)
       					{
            	 			case 20:
            	 				{
            	 					$('#countPerPage option:eq(1)').attr('selected',false);
               						$('#countPerPage option:eq(2)').attr('selected',false);
               						$('#countPerPage option:eq(0)').attr('selected',true);
               						break;
               					}
              				case 30:
								{
									$('#countPerPage option:eq(0)').attr('selected',false);
               						$('#countPerPage option:eq(2)').attr('selected',false);
               						$('#countPerPage option:eq(1)').attr('selected',true);
               						break;
               					}
              				case 50:
              					{
              						$('#countPerPage option:eq(0)').attr('selected',false);
               						$('#countPerPage option:eq(1)').attr('selected',false);
               						$('#countPerPage option:eq(2)').attr('selected',true);
               						break;
               					}             
       				 	}

       					$("#massageTable tr").remove();
       					for(var i=0; i <json.data.length; i++)
    					{
          					var temp="<tr>"
                   	 		+"<td >"+(i+1)+"</td>"
                    		+"<td>"+json.data[i].bakSeqNum+"</td>"
                   			+"<td>"+json.data[i].bakVersion+"</td>"
                    		+"<td>"+json.data[i].bakFactory+"</td>"
                    		+"<td>"+json.data[i].bakDevType+"</td>"
                    		+"<td>"+json.data[i].bakForm+"</td>"
                    		+"<td>"+json.data[i].bakState+"</td>"
                    		+"<td>"+json.data[i].bakDevRoom+"</td>"
                    		+"<td>"+json.data[i].bakDevFrame+"</td>"
                    		+"<td>"+json.data[i].bakManaDepart+"</td>"
                    		+"<td>"+json.data[i].bakUseDepart+"</td>"
                    		+"<td>"+json.data[i].bakUser+"</td>"
                 			+"</tr>";        
							$(temp).appendTo($("#massageTable"));
    					}
				
    			},
    			error:function()
    			{
    				//alert("AJAX请求失败！请重试");
    				window.parent.document.getElementById('right').src="false.jsp";
    			}
	  	  });		
      }

    });
    //更改每页显示数量结束
	//翻页公用请求开始
	function ajaxDisplay( nowPageTemp )
    {
    
    	$.ajax({
			      type:"POST",
			      url:"probak/bakAction_searchOutChart",
			     // dataType:"json",
			      data:{
			      		bakArrAcceptTime:$("#timeInput").val(),
						iDisplayLength:$("#perPageCount").html(),	//每页显示条数
						nowPage:nowPageTemp				//当前页数位置	
			     		},
			      success:function(data)
			      {
			    	 var json =JSON.parse(data)
			         $("#massageTable tr").remove();
			         for(var i=0; i <json.data.length; i++)
    					{
          					var temp="<tr>"
                   	 		+"<td >"+(i+1)+"</td>"
                    		+"<td>"+json.data[i].bakSeqNum+"</td>"
                   			+"<td>"+json.data[i].bakVersion+"</td>"
                    		+"<td>"+json.data[i].bakFactory+"</td>"
                    		+"<td>"+json.data[i].bakDevType+"</td>"
                    		+"<td>"+json.data[i].bakForm+"</td>"
                    		+"<td>"+json.data[i].bakState+"</td>"
                    		+"<td>"+json.data[i].bakDevRoom+"</td>"
                    		+"<td>"+json.data[i].bakDevFrame+"</td>"
                    		+"<td>"+json.data[i].bakManaDepart+"</td>"
                    		+"<td>"+json.data[i].bakUseDepart+"</td>"
                    		+"<td>"+json.data[i].bakUser+"</td>"
                 			+"</tr>";        
							$(temp).appendTo($("#massageTable"));
    					}
				
			      },
			      error:function()
			      {
			      		//alert("AJAX请求失败！请重试");
			      		window.parent.document.getElementById('right').src="false.jsp";
			      }
			    });
    
    }
 //翻页公用请求开结束
	
	 //点击换页开始
		$(function(){
			if(document.getElementById("left"))
				var left=document.getElementById("left"); 
			else return false;
			if(document.getElementById("leftUp"))
				var leftUp=document.getElementById("leftUp"); 
			else return false;
			if(document.getElementById("right"))
				var right=document.getElementById("right"); 
			else return false;if(document.getElementById("rightUp"))
				var rightUp=document.getElementById("rightUp"); 
			else return false;
			if(document.getElementById("nowPage"))
				var nowPage=document.getElementById("nowPage");
			else return false;
			
			nowPage.onblur=function()
			{
				if( Number( $("#nowPage").val() ) > Number( $("#pages").html() ) || Number( $("#nowPage").val() ) < 1 || $("#nowPage").val()=="")
				{
    				//alert("请输入合法页数！");
    				;
				}
				else 
				{
				  $.ajax({
			      type:"POST",
			      url:"probak/bakAction_searchOutChart",
			      data:{
			      		bakArrAcceptTime:$("#timeInput").val(),
						iDisplayLength:$("#perPageCount").html(),	//每页显示条数
						nowPage:$("#nowPage").val()			//当前页数位置	
			     		},
			      success:function(data)
			      {
			    	 var json =JSON.parse(data)
			         $("#massageTable tr").remove();
			      	for(var i=0; i <json.data.length; i++)
    				{
          				var temp="<tr>"
                   	 	+"<td >"+(i+1)+"</td>"
                    	+"<td>"+json.data[i].bakSeqNum+"</td>"
                   		+"<td>"+json.data[i].bakVersion+"</td>"
                    	+"<td>"+json.data[i].bakFactory+"</td>"
                    	+"<td>"+json.data[i].bakDevType+"</td>"
                    	+"<td>"+json.data[i].bakForm+"</td>"
                    	+"<td>"+json.data[i].bakState+"</td>"
                    	+"<td>"+json.data[i].bakDevRoom+"</td>"
                    	+"<td>"+json.data[i].bakDevFrame+"</td>"
                    	+"<td>"+json.data[i].bakManaDepart+"</td>"
                    	+"<td>"+json.data[i].bakUseDepart+"</td>"
                    	+"<td>"+json.data[i].bakUser+"</td>"
                 		+"</tr>";        
						$(temp).appendTo($("#massageTable"));
    				}
				
			      },
			      error:function()
			      {
			      		//alert("AJAX请求失败！请重试");
			      		window.parent.document.getElementById('right').src="false.jsp";
			      }
			    });
			    	//若页数大于1则左按钮和左顶按钮可以
			    	if(Number( $("#nowPage").val() ) > 1)
			    	{
			    		document.getElementById("left").disabled=false;
    					document.getElementById("leftUp").disabled=false;
    				}
    				if(Number( $("#nowPage").val() ) == 1)
    				{
    					document.getElementById("left").disabled=true;
    					document.getElementById("leftUp").disabled=true;
    					document.getElementById("right").disabled=false;
    					document.getElementById("rightUp").disabled=false;
    				}
    				if( Number( $("#nowPage").val() ) == Number( $("#pages").html() ) )
    				{
    					document.getElementById("right").disabled=true;
    					document.getElementById("rightUp").disabled=true;
    				}
    				if(Number( $("#nowPage").val() ) != Number( $("#pages").html() )  )
    				{
    					document.getElementById("right").disabled=false;
    					document.getElementById("rightUp").disabled=false;
    				}
			    }
				
			}
			
			left.onclick=function()
			{
				if(window.document.getElementById("left").disabled == true)
					alert("当前为数据第一页");
				else 
					{
						
							
						if(document.getElementById("nowPage") && Number( $("#nowPage").val() ) > 1 )
						{
							document.getElementById("nowPage").value=Number( $("#nowPage").val() ) - 1;	
							ajaxDisplay(  $("#nowPage").val() );							
						}
						else return false;	
						if(Number( $("#nowPage").val() ) == 1)
						{
							document.getElementById("left").disabled=true;
    						document.getElementById("leftUp").disabled=true;
    						
						}	
						if(Number( $("#nowPage").val() ) == Number( $("#pages").html() ))
						{
							
							document.getElementById("right").disabled=true;
    						document.getElementById("rightUp").disabled=true;
    						
						}	
						if(Number( $("#nowPage").val() ) < Number( $("#pages").html() ) )
						{
							document.getElementById("right").disabled=false;
    						document.getElementById("rightUp").disabled=false;
						}
						if(Number( $("#nowPage").val() ) > 1 )
						{
							document.getElementById("left").disabled=false;
    						document.getElementById("leftUp").disabled=false;
						}
					}
			}
			leftUp.onclick=function()
			{
				if(window.document.getElementById("leftUp").disabled == true)
					alert("当前为数据第一页");
				else 
				{
					if( document.getElementById("nowPage") )
					{
						document.getElementById("nowPage").value = 1;	
						ajaxDisplay( $("#nowPage").val()  );
						document.getElementById("left").disabled=true;
    					document.getElementById("leftUp").disabled=true;
    					document.getElementById("right").disabled=false;
    					document.getElementById("rightUp").disabled=false;	
					}
				}
			};
			right.onclick=function()
			{
				if(document.getElementById("right").disabled == true)
					alert("当前为数据最后一页");
				else 
					{
						if(document.getElementById("nowPage") && Number( $("#nowPage").val() ) < Number( $("#pages").html() ) )
						{
							document.getElementById("nowPage").value=Number( $("#nowPage").val() ) + 1;	
							ajaxDisplay( $("#nowPage").val()  );	
						}
						else return false;
						if(Number( $("#nowPage").val() ) == 1)
						{
							document.getElementById("left").disabled=true;
    						document.getElementById("leftUp").disabled=true;
						}	
						if(Number( $("#nowPage").val() ) == Number( $("#pages").html() ))
						{
							document.getElementById("right").disabled=true;
    						document.getElementById("rightUp").disabled=true;  						
						}	
						if(Number( $("#nowPage").val() ) < Number( $("#pages").html() ) )
						{
							document.getElementById("right").disabled=false;
    						document.getElementById("rightUp").disabled=false;
						}
						if(Number( $("#nowPage").val() ) > 1 )
						{
							document.getElementById("left").disabled=false;
    						document.getElementById("leftUp").disabled=false;
						}
					}
			}
			rightUp.onclick=function()
			{
				if(window.document.getElementById("rightUp").disabled == true)
					alert("当前为数据最后一页");
				else 
				{
					if( document.getElementById("nowPage") )
					{
						document.getElementById("nowPage").value = Number( $("#pages").html() );	
						ajaxDisplay( $("#nowPage").val()  );	
						document.getElementById("right").disabled=true;
    					document.getElementById("rightUp").disabled=true;
    					document.getElementById("left").disabled=false;
    					document.getElementById("leftUp").disabled=false;
					}
				}
			}
			
			
		});
	//点击换页结束
	</script>
	
</head>
<body>

	 	<div class="bread">
            当前位置：报表管理 > 备品备件
        </div>
	 	<div class="tableBox1"> 	
	 	
	 		<div style="width:100%; height:22px; background-color:#CFDDEE; text-align:left; line-height:26px; position:fixed; z-index:10"><!-- 菜单栏 -->
				<ul id="listUl">
					<li class="clicked" id="numCount">数量统计</li>
					<li id="scrapingList">预报废列表</li>
				</ul>
			
			</div>
			<div id="chartsDiv">
	 			<div class="equTypeBox">
	 				设备类型
	 				<div id="equType"></div>
	 			</div>
	 			<div id="equState"> </div>
	 		</div>
	 		<!-- 预报废列表 -->
			<div id="scrapingListBox" class="clearfloat" style="display:none; padding-left:5px; text-align:left; font-size:14px; margin-right:10px; margin-top:40px   ">
				<input  autocomplete="off"  readonly style="width:150px; margin-left:2px;" class="datepicker searchRules"  placeholder="请输入报废时间点" type="text"  id="timeInput"/>
				<input type="button" value="确认" id="sureToSearch" />
				<div style="float:right"><span>每页</span>
					<select id="countPerPage">
            			<option selected value="1">20</option>
            			<option value="2">30</option>
           			 	<option value="3">50</option>
        
      	  			</select><span>条</span>
        		</div>
				<div  class="tableBox" style=" top:70px; text-align:center; ">
					<table id="table" cellspacing="0"  >
               			<thead>
                			<tr>
                    			<th class="firstCol">序号</th>         
                    			<th class="secondCol">序列号</th>
                    			<th class="secondCol">型号</th>
                   				<th class="secondCol">设备厂商</th>
                    			<th class="thirdCol">设备类别</th>
                    			<th class="firstCol">实物形态</th>
                    			<th class="forthCol">状态</th>
                    			<th class="forthCol">所属机房</th>
                    			<th class="forthCol">所在机柜</th>
                    			<th class="forthCol">实物管理部门</th>
                    			<th class="forthCol">使用部门</th>
                    			<th class="forthCol">使用人</th>
                    			
                			</tr>
                		</thead>

                	<tbody id="massageTable">
                	
                	</tbody>
           		</table>
			
			</div>
			
			 <div class="rightFooter">
            	<div class="leadCount">共<span id="count">0</span>条记录，每页
                	<span id="perPageCount">20</span>条，共<span id="pages">0</span>页
           		</div>
           	 	<div class="leadPage">
                	<a id="leftUp"><img src="${pageContext.request.contextPath }/images/leftUp.png" /></a>
                	<a id="left"><img src="${pageContext.request.contextPath }/images/left.png" /></a>
                	<input type="text" id="nowPage" value="1" />
                	<a id="right"><img src="${pageContext.request.contextPath }/images/right.png" /></a>
                	<a id="rightUp"><img src="${pageContext.request.contextPath }/images/rightUp.png" /></a>&nbsp;&nbsp;
                	           
            	</div> 
                      
        	</div>
        <!--rightFooter-->
			
	
			
	 	</div>
	 	</div>
</body>
</html>