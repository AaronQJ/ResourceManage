<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge" />
    <title>报表管理 > 专用资产</title>
    <meta name="viewport" content="width=device-width,user-scalable=no" />
    <link href="${pageContext.request.contextPath }/Styles/superManager.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath }/Styles/form.css" rel="stylesheet" type="text/css"/>
   
	<script type="text/javascript" src="${pageContext.request.contextPath }/Scripts/jquery.js"></script>
	<script src="${pageContext.request.contextPath }/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath }/Scripts/highcharts.js"></script> 
	<script src="${pageContext.request.contextPath }/Scripts/exporting.js"></script>
	<script src="${pageContext.request.contextPath }/Scripts/speTable.js"></script>
	
	<style>
	.clearfloat{ zoom:1; }
	.clearfloat:after{ display:block; clear:both; content:""; visibility:hidden; height:0; }
	.tableBox1{overflow:scroll; width:100%; height:auto;  }
	.firstSecond{ width:900px; height:auto; margin-left:110px; margin-top:50px;}
	#equType{ width:400px; height:400px; float:left; }
	#equState{ width:300px; height:400px; float:left; margin-left:110px; }
	#equSite{ min-width:200px; width:auto;height:400px; }
	.equSiteBox{ width:800px; height:auto;overflow-x:auto; margin: 50px 0 0 110px; text-align:center; }
	#tableRoom{ width:800px;  font-size:14px; line-height:23px; margin:50px 0 80px 120px; }
	#tableRoom tr th{ border-bottom:1px solid #F0F0F0;  border-left:1px solid #C4D9F6;  height:23px; width:auto; min-width:30px; max-width:250px; background-color:#F4F4F4;  }
    #tableRoom tr td{  border-bottom:1px solid #F0F0F0; border-right:1px solid #F0F0F0; border-left:1px solid #C4D9F6; height:23px; width:auto; min-width:30px; white-space:nowrap; overflow:hidden; text-overflow: ellipsis; }
	
	.listBox{ width:100%; height:22px; background-color:#CFDDEE; text-align:left; line-height:26px; position:fixed; z-index:10 }
	#listUl{ margin-left:20px; }
	#listUl li{ float:left; margin-right:5px ; font-size:13px; width:80px; text-align:center; border-radius: 5px 5px 0 0; cursor:pointer; }
	#listUl li:hover{ background-color:#fff; }
	.clicked{background-color:#fff;   }
	
	/*预报废列表*/
	#scrapingListBox{ display:none; padding-left:5px; text-align:left; font-size:14px; margin-right:10px; margin-top:20px   }
	
	
	/*报废table*/
	
	/*.tableBox{ width:99.5%;  border:1px solid #F0F0F0;  overflow-y:auto; position:absolute; top:125px; bottom:30px;  z-index:0;}

	.tableBox table{ min-width:100%;  font-size:14px; line-height:23px;  }
	
	.tableBox table tr th{ border-bottom:1px solid #F0F0F0;  border-left:1px solid #C4D9F6;  height:23px; width:auto; min-width:30px; max-width:250px; background-color:#F4F4F4;  }
	.tableBox table tr th img{margin-left:2px;vertical-align:middle; cursor:pointer;  }
	.tableBox table tr td{  border-bottom:1px solid #F0F0F0; border-right:1px solid #F0F0F0; border-left:1px solid #C4D9F6; height:23px; width:auto; min-width:30px; max-width:130px; white-space:nowrap;overflow:hidden;text-overflow: ellipsis;}
	.tableBox table tr td button{ height:23px;  font-size:13px; width:100px; margin:auto auto; background-color:#fff; border:none;}
	.tableBox table tr td button img{ vertical-align: middle;}
	*/
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
		if(document.getElementsByClassName("chartsDiv"))
			var chartsDiv=document.getElementsByClassName("chartsDiv");
		else return false;
		//else alert("获取失败")
		if(document.getElementById("scrapingListBox"))
			var scrapingListBox=document.getElementById("scrapingListBox");
		else return false;
			numCount.onclick=function()
			{
				//alert("1");
				$(numCount).addClass("clicked");
				$(scrapingList).removeClass("clicked");

				chartsDiv[0].style.display="block";
				chartsDiv[1].style.display="block";
				chartsDiv[2].style.display="block";
				scrapingListBox.style.display="none";
			};
			scrapingList.onclick=function()
			{
				//alert("1");
				$(numCount).removeClass("clicked");
				$(scrapingList).addClass("clicked");
				//$(this).addClass("clicked");
				chartsDiv[0].style.display="none";
				chartsDiv[1].style.display="none";
				chartsDiv[2].style.display="none";
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
			
			$.ajax({
				type:"POST",
				url:"spepro/speAction_searchOutChart",			
				data:{				
					speArrAcceptTime:$("#timeInput").val(),
					iDisplayLength:$("#perPageCount").html(),	//每页显示条数
					nowPage:"1"					//当前页数位置			        
				},
				success:function(data)
				{
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
			         for(var i=0; i < json.data.length; i++)
    					{
          					var temp= "<tr>"
                   				+ "<td >"+(i+1)+"</td>"
                   				+ "<td>"+json.data[i].speSeqNum+"</td>" 
                   				+ "<td>"+json.data[i].speDevName+"</td>" 
                   				+ "<td>"+json.data[i].speSeqNum+"</td>" 
                  				+ "<td>"+json.data[i].speDevRoom+"</td>" 
                   				+ "<td>"+json.data[i].speDevRoomFrame+"</td>" 
                   				+ "<td>"+json.data[i].speUser+"</td>" 
                   				+ "<td>"+json.data[i].speState+"</td>"                    				
               	   				+ "</tr>";
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
       			// alert(iDisplayLength)
       			// alert($("#timeInput").val())
       			// alert($("#nowPage").val())

 		$.ajax({
			      type:"POST",
			      url:"spepro/speAction_searchOutChart",
			      data:{
			      		speArrAcceptTime:$("#timeInput").val(),
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
       					for(var i=0; i < json.data.length; i++)
    					{
          					var temp= "<tr>"
                   				+ "<td >"+(i+1)+"</td>"
                   				+ "<td>"+json.data[i].speSeqNum+"</td>" 
                   				+ "<td>"+json.data[i].speDevName+"</td>" 
                   				+ "<td>"+json.data[i].speSeqNum+"</td>" 
                  				+ "<td>"+json.data[i].speDevRoom+"</td>" 
                   				+ "<td>"+json.data[i].speDevRoomFrame+"</td>" 
                   				+ "<td>"+json.data[i].speUser+"</td>" 
                   				+ "<td>"+json.data[i].speState+"</td>"                    				
               	   				+ "</tr>";
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
			      url:"spepro/speAction_searchOutChart",
			     // dataType:"json",
			      data:{
			      		speArrAcceptTime:$("#timeInput").val(),
						iDisplayLength:$("#perPageCount").html(),	//每页显示条数
						nowPage:nowPageTemp				//当前页数位置	
			     		},
			      success:function(data)
			      {
			    	 var json =JSON.parse(data)
			         $("#massageTable tr").remove();
			         for(var i=0; i < json.data.length; i++)
    					{
          					var temp= "<tr>"
                   				+ "<td >"+(i+1)+"</td>"
                   				+ "<td>"+json.data[i].speSeqNum+"</td>" 
                   				+ "<td>"+json.data[i].speDevName+"</td>" 
                   				+ "<td>"+json.data[i].speSeqNum+"</td>" 
                  				+ "<td>"+json.data[i].speDevRoom+"</td>" 
                   				+ "<td>"+json.data[i].speDevRoomFrame+"</td>" 
                   				+ "<td>"+json.data[i].speUser+"</td>" 
                   				+ "<td>"+json.data[i].speState+"</td>"                    				
               	   				+ "</tr>";
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
			      url:"spepro/speAction_searchOutChart",
			      data:{
			      		speArrAcceptTime:$("#timeInput").val(),
						iDisplayLength:$("#perPageCount").html(),	//每页显示条数
						nowPage:$("#nowPage").val()			//当前页数位置	
			     		},
			      success:function(data)
			      {
			    	 var json =JSON.parse(data)
			         $("#massageTable tr").remove();
			        for(var i=0; i < json.data.length; i++)
    					{
          					var temp= "<tr>"
                   				+ "<td >"+(i+1)+"</td>"
                   				+ "<td>"+json.data[i].speSeqNum+"</td>" 
                   				+ "<td>"+json.data[i].speDevName+"</td>" 
                   				+ "<td>"+json.data[i].speSeqNum+"</td>" 
                  				+ "<td>"+json.data[i].speDevRoom+"</td>" 
                   				+ "<td>"+json.data[i].speDevRoomFrame+"</td>" 
                   				+ "<td>"+json.data[i].speUser+"</td>" 
                   				+ "<td>"+json.data[i].speState+"</td>"                    				
               	   				+ "</tr>";
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
			}
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
            当前位置：报表管理 > 专用资产
        </div>
        
		<div class="tableBox1">
			<div class="listBox" style=""><!-- 菜单栏 -->
				<ul id="listUl">
					<li class="clicked" id="numCount">数量统计</li>
					<li id="scrapingList">预报废列表</li>
				</ul>
			
			</div>
			<div class="clearfloat firstSecond chartsDiv" >
	 			<div id="equType"></div>
	 			<div id="equState" > </div>
	 		</div><br/>
			<div class="equSiteBox chartsDiv">
		
				<div id="equSite"> </div>
			</div>
			<div id="roomListTable" class="chartsDiv">
				<table id="tableRoom" cellspacing="0">
					<thead>
						<tr>
							<th>编号</td>
							<th>机房名称</td>
						</tr>
					</thead>
					<tbody id="roomTbody">
					
					</tbody>
				
				</table>
			
			</div>
			
			<!-- 预报废列表 -->
			<div id="scrapingListBox" class="clearfloat" >
				<input  class="datepicker searchRules"  autocomplete="off"  readonly style="width:150px; margin-left:2px;"  placeholder="请输入报废时间点" type="text"  id="timeInput"/>
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
                    			<th class="secondCol">标签</th>
                    			<th class="secondCol">设备名称</th>
                    			<th class="secondCol">序列号</th>
                    			<th class="thirdCol">所属机房</th>
                    			<th class="firstCol">所在机柜</th>
                    			<th class="forthCol">使用人</th>
                    			<th class="forthCol">状态</th>
                    			
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