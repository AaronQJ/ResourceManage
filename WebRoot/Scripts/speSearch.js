			var speDevNameValue="";
  			var speRoomValue="";
  			var speRoomFrameValue="";
  			var speUserNameValue="";
  			var speStateValue="";	
	
  			
//初始化界面数据开始
	$(function()
	{
		$.ajax({
			 type:"POST",
			 url:"spepro/speAction_search",
			  async: false,
			// dataType:"json",
			 data:{
			 		speDevName:$("#searchspeDevName").val(),	//设备名称
					speSeqNum:$("#searchspeSeqNum").val(),		//序列号
					speBarCode:$("#searchspeBarCode").val(),	//条码
					speUser:$("#searchspeUser").val(),			//使用人
					iDisplayLength:"20",
					nowPage:"1"
				  },
			cache:false,
			 success:function(data)
			 {
    			var json =JSON.parse(data);
    			var iDisplayLength=json.pagaData.iDisplayLength; 				//每页条数
    			var iTotalRecords=json.pagaData.iTotalRecords;					//总数据条数
    			var pageCount=json.pagaData.totalPage;			//页数
    			$("#count").html(iTotalRecords);		//设置数据总数
    			$("#perPageCount").html(iDisplayLength);//设置每页数据数
    			$("#pages").html( pageCount);			//设置总共页数
    			document.getElementById("nowPage").value="1";		//设置 当前页数位置
    			document.getElementById("left").disabled=true;
    			document.getElementById("leftUp").disabled=true;
    			document.getElementById("right").disabled=false;
    			document.getElementById("rightUp").disabled=false;
 			
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
 			
    			for(var i=0; i < json.pagaData.data.length; i++)
    			{
          			var temp= "<tr>"
                   + "<td >"+(i+1)+"</td>"
                   + "<td><input type='checkbox' name='same' id='"+json.pagaData.data[i].speId+"' /></td>"
                   + "<td>"+json.pagaData.data[i].speBarCode+"</td>" 
                   + "<td>"+json.pagaData.data[i].speDevName+"</td>" 
                   + "<td>"+json.pagaData.data[i].speSeqNum+"</td>"
                   + "<td>"+json.pagaData.data[i].speDevRoom+"</td>" 
                   + "<td>"+json.pagaData.data[i].speDevRoomFrame+"</td>" 
                   + "<td>"+json.pagaData.data[i].speUser+"</td>" 
                   + "<td>"+json.pagaData.data[i].speState+"</td>" 
                   + "<td><button>更多操作<img src='"+FAD+"/images/downdown.png' /></button></td>" 
               	   + "</tr>";
				   $(temp).appendTo($("#massageTable"));
    			}
    			
    			/*重绘筛选列表*/
				$("#speDevName1 ul li").remove();					//设备名称
				for(var i=0; i < json.speDevNameList.length; i++)
				{
					var temp= "<li>"+json.speDevNameList[i]+"</li>";
					$(temp).appendTo($("#speDevName1 ul"));
				}
				$("#speRoom1 ul li").remove();					//所属机房
				for(var i=0; i < json.speRoomList.length; i++)
				{
					var temp= "<li>"+json.speRoomList[i]+"</li>";
					$(temp).appendTo($("#speRoom1 ul"));
				}
				$("#speRoomFrame1 ul li").remove();				//所在机柜
				for(var i=0; i < json.speRoomFrameList.length; i++)
				{
					var temp= "<li>"+json.speRoomFrameList[i]+"</li>";
					$(temp).appendTo($("#speRoomFrame1 ul"));
				}
				$("#speUserName1 ul li").remove();
				for(var i=0; i < json.speUserNameList.length; i++)
				{
					var temp= "<li>"+json.speUserNameList[i]+"</li>";
					$(temp).appendTo($("#speUserName1 ul"));
				}
				$("#speState1 ul li").remove();
				for(var i=0; i < json.speStateList.length; i++)
				{
					var temp= "<li>"+json.speStateList[i]+"</li>";
					$(temp).appendTo($("#speState1 ul"));
				}
					
				/*重绘筛选列表结束*/
				
				selectFun();	//绑定筛选点击事件
				
				
    		},
			error:function(){
				//alert("AJAX请求失败！请重试");
				window.parent.document.getElementById('right').src="false.jsp";
			}
		});
		
	});
	//初始化界面数据结束
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

 		$.ajax({
			      type:"POST",
			      url:"spepro/speAction_search",
			      async:false,
			      data:{
			      		speDevName:$("#searchspeDevName").val(),	//设备名称
						speSeqNum:$("#searchspeSeqNum").val(),		//序列号
						speBarCode:$("#searchspeBarCode").val(),	//条码
						speUser:$("#searchspeUser").val(),			//使用人
			        	iDisplayLength:iDisplayLength,	//每页条数
			        	nowPage:"1"
			    	   },
			      success:function(data)
				  {		//alert("AJAX成功");
    	
    					var json =JSON.parse(data)
    					var iDisplayLength=json.pagaData.iDisplayLength; 				//每页条数
    					var iTotalRecords=json.pagaData.iTotalRecords;					//总数据条数
    					var iTotalDisplayRecords=json.pagaData.iTotalDisplayRecords;		//当前页数据条数
    					//alert(iDisplayLength);
    					var pageCount=parseInt(iTotalRecords/iDisplayLength) +1;//页数
    					$("#perPageCount").html(iDisplayLength);//设置每页数据数
    					$("#pages").html( pageCount);			//设置总共页数
    					document.getElementById("nowPage").value="1";		//设置 当前页数位置
    					document.getElementById("left").disabled=true;
    					document.getElementById("leftUp").disabled=true;
    					document.getElementById("right").disabled=false;
    					document.getElementById("rightUp").disabled=false;
    					
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
       					for(var i=0; i < json.pagaData.data.length; i++)
    					{
          					var temp= "<tr>"
                   				+ "<td >"+(i+1)+"</td>"
                			    + "<td><input type='checkbox' name='same' id='"+json.pagaData.data[i].speId+"'/></td>"
                   				+ "<td>"+json.pagaData.data[i].speBarCode+"</td>" 
                   				+ "<td>"+json.pagaData.data[i].speDevName+"</td>" 
                   				+ "<td>"+json.pagaData.data[i].speSeqNum+"</td>" 
                  				+ "<td>"+json.pagaData.data[i].speDevRoom+"</td>" 
                   				+ "<td>"+json.pagaData.data[i].speDevRoomFrame+"</td>" 
                   				+ "<td>"+json.pagaData.data[i].speUser+"</td>" 
                   				+ "<td>"+json.pagaData.data[i].speState+"</td>" 
                   				+ "<td><button>更多操作<img src='"+FAD+"/images/downdown.png' /></button></td>" 
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
    
    function ajaxDisplay( nowPageTemp )
    {
    
    	$.ajax({
			      type:"POST",
			      url:"spepro/speAction_search",
			      async:false,
			     // dataType:"json",
			      data:{
			      		speDevName:$("#searchspeDevName").val(),	//设备名称
						speSeqNum:$("#searchspeSeqNum").val(),		//序列号
						speBarCode:$("#searchspeBarCode").val(),	//条码
						speUser:$("#searchspeUser").val(),			//使用人
			         	nowPage:nowPageTemp,						//当前页数位置
			         	iDisplayLength:$("#perPageCount").html(),	//每页显示条数
			     		},
			      success:function(data)
			      {
			    	 var json =JSON.parse(data)
			         $("#massageTable tr").remove();
			         for(var i=0; i < json.pagaData.data.length; i++)
    					{
          					var temp= "<tr>"
                   				+ "<td >"+(i+1)+"</td>"
                			    + "<td><input type='checkbox' name='same' id='"+json.pagaData.data[i].speId+"'/></td>"
                   				+ "<td>"+json.pagaData.data[i].speBarCode+"</td>" 
                   				+ "<td>"+json.pagaData.data[i].speDevName+"</td>" 
                   				+ "<td>"+json.pagaData.data[i].speSeqNum+"</td>" 
                  				+ "<td>"+json.pagaData.data[i].speDevRoom+"</td>" 
                   				+ "<td>"+json.pagaData.data[i].speDevRoomFrame+"</td>" 
                   				+ "<td>"+json.pagaData.data[i].speUser+"</td>" 
                   				+ "<td>"+json.pagaData.data[i].speState+"</td>" 
                   				+ "<td><button>更多操作<img src='"+FAD+"/images/downdown.png' /></button></td>" 
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
    
    
    //点击换页
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
			      url:"spepro/speAction_search",
			      async:false,
			      data:{
			      		speDevName:$("#searchspeDevName").val(),	//设备名称
						speSeqNum:$("#searchspeSeqNum").val(),		//序列号
						speBarCode:$("#searchspeBarCode").val(),	//条码
						speUser:$("#searchspeUser").val(),			//使用人
			         	nowPage:$("#nowPage").val(),				//当前页数位置
			         	iDisplayLength:$("#perPageCount").html(),	//每页显示条数
			     		},
			      success:function(data)
			      {
			    	 var json =JSON.parse(data)
			         $("#massageTable tr").remove();
			         for(var i=0; i < json.pagaData.data.length; i++)
    					{
          					var temp= "<tr>"
                   				+ "<td >"+(i+1)+"</td>"
                			    + "<td><input type='checkbox' name='same' id='"+json.pagaData.data[i].speId+"'/></td>"
                   				+ "<td>"+json.pagaData.data[i].speBarCode+"</td>" 
                   				+ "<td>"+json.pagaData.data[i].speDevName+"</td>" 
                   				+ "<td>"+json.pagaData.data[i].speSeqNum+"</td>" 
                  				+ "<td>"+json.pagaData.data[i].speDevRoom+"</td>" 
                   				+ "<td>"+json.pagaData.data[i].speDevRoomFrame+"</td>" 
                   				+ "<td>"+json.pagaData.data[i].speUser+"</td>" 
                   				+ "<td>"+json.pagaData.data[i].speState+"</td>" 
                   				+ "<td><button>更多操作<img src='"+FAD+"/images/downdown.png' /></button></td>" 
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
				if(document.getElementById("right").disabled == true || Number( $("#pages").html() ) <= 1)
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
				if(window.document.getElementById("rightUp").disabled == true || Number( $("#pages").html() ) <= 1 )
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
	
 		/* 打印 */
 	  function printHTML(){  
        var bdhtml=window.document.body.innerHTML;		//获取当前页的html代码    
        var sprnstr="<!--begin-->";			//设置打印开始区域    
        var eprnstr="<!--end-->";			//设置打印结束区域    
         var prnhtml=bdhtml.substring(bdhtml.indexOf(sprnstr)+18); 		//从开始代码向后取html    
        prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));		//从结束代码向前取html    
    
        printWindow = window.open('page.html', 'newwindow', 'height=100, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');
        printWindow.document.write(prnhtml);
        printWindow.print();
		printWindow.close(); 
    }    
 
 	  
 	 //按条码查询
 	    $(document).ready(function () {	
 			$("#searchspeBarCode").keydown(function (e) {						
 			var curKey = e.which;
 			if (curKey == 13) {
 				$("#searchspeDevName").val("");
 				$("#searchspeSeqNum").val("");
 				$("#searchspeUser").val("");
 				
 	     		$.ajax({
 	     			type:"POST",
 					url:"spepro/speAction_findByBarcode",
 					async:false,
 					// dataType:"json",
 					data:{
 				 		speBarCode:$("#searchspeBarCode").val(),			//条码
 						iDisplayLength:$("#perPageCount").html(),
 						nowPage:"1"
 					  },
 					cache:false,
 					success:function(data){
 						var json =JSON.parse(data);
 		    			
 		    			
 		    			var pageCount=json.totalPage;							//页数
 		    			
 		    			$("#count").html("1");		//设置数据总数
 		    			$("#perPageCount").html("1");//设置每页数据数
 		    			$("#pages").html( "1");			//设置总共页数
 		    			//$("#nowPage").attr("value",1);			//设置 当前页数位置
 		    			document.getElementById("nowPage").value="1";		//设置 当前页数位置
 	    				document.getElementById("left").disabled=true;
 	    				document.getElementById("leftUp").disabled=true;
 	    				document.getElementById("right").disabled=false;
 	    				document.getElementById("rightUp").disabled=false;    			
 						$("#massageTable tr").remove();
 						
 						if(($("#searchspeBarCode").val()).length!=0)
 						{
 	    				var temp= "<tr>"
 	                  	   + "<td >1</td>"
 	                 	   + "<td><input type='checkbox' name='same' id='"+json.data[0].speId+"' /></td>"
 	                	   + "<td>"+json.data[0].speBarCode+"</td>" 
 	                	   + "<td>"+json.data[0].speDevName+"</td>" 
 	                	   + "<td>"+json.data[0].speSeqNum+"</td>"
 	               	 	   + "<td>"+json.data[0].speDevRoom+"</td>" 
 	                	   + "<td>"+json.data[0].speDevRoomFrame+"</td>" 
 	                	   + "<td>"+json.data[0].speUser+"</td>" 
 	                	   + "<td>"+json.data[0].speState+"</td>" 
 	                	   + "<td><button>更多操作<img src='"+FAD+"/images/downdown.png' /></button></td>" 
 	               		   + "</tr>";
 					   	$(temp).appendTo($("#massageTable"));
 					   }
 					   else {
 					   
 					   		$("#count").html("0");		//设置数据总数
 		    				$("#perPageCount").html("0");//设置每页数据数
 		    				$("#pages").html( "0");			//设置总共页数
 					   }
 						
 					/*去掉筛选列表（因为只有一条记录）*/
/*
 		    			$("#speDevName1 ul li").remove();					//设备名称
 		    			$("#speRoom1 ul li").remove();					//所属机房
 		    			$("#speRoomFrame1 ul li").remove();				//所在机柜
 		    			$("#speUserName1 ul li").remove();
 		    			$("#speState1 ul li").remove();*/
 						
 					},
 					error:function(){
 						//alert("AJAX请求失败！请重试");
 						window.parent.document.getElementById('right').src="false.jsp";
 					}	
 					  
 	     		});
 			return false;
 			}
 			});
 		});
 	    
 	 //查询
 	   
 	   $(function()
 	   {
 	   	if(document.getElementById("searchButton"))
 	   		var	searchButton=document.getElementById("searchButton");//查询
 	   	searchButton.onclick=function()
 	   	{
 	   		 $("#selectCount").show();							//重新触发分页功能
 	   	     $("#exportNow").text("导出当前页");
 	   		 $("#exportAll").show();	     					//恢复分页，恢复导出当前页
			
			 $("#left").show();
			 $("#leftUp").show();
			 $("#right").show();
			 $("#rightUp").show();
			 $("#nowPage").show();			//恢复翻页
			 /*每次查询初始化筛选条件*/
			 speDevNameValue="";
			 speRoomValue="";
			 speRoomFrameValue="";
			 speUserNameValue="";
			 speStateValue="";
			 $(".choseDiv ul li").each(function(){	
					$(this).css("backgroundColor","#F4F4F4");
			});
			 $(".choseDiv ul li").removeClass("searchSelect");
			 /*初始化筛选条件结束*/
 	   		 var countPerPage=document.getElementById("countPerPage");

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
 	   		$.ajax({
 	 			 type:"POST",
 	 			 url:"spepro/speAction_search",
 	 			async:false,
 	 			// dataType:"json",
 	 			 data:{
 	 					speDevName:$("#searchspeDevName").val(),	//设备名称
 	 					speSeqNum:$("#searchspeSeqNum").val(),		//序列号
 	 					speBarCode:$("#searchspeBarCode").val(),	//条码
 	 					speUser:$("#searchspeUser").val(),			//使用人
 	 					iDisplayLength:iDisplayLength,				//每页条数
 	 			        nowPage:"1"					//当前页数
 	 			        
 	 				  },
 	 			 cache:false,
 	 			 success:function(data)
 	 			 {
 	 			// alert("success");
 	     			var json =JSON.parse(data);
 	     			var iDisplayLength=json.pagaData.iDisplayLength; 				//每页条数
 	     			var iTotalRecords=json.pagaData.iTotalRecords;					//总数据条数
 	     			var pageCount=json.pagaData.totalPage;//页数
 	     			
 	     			$("#count").html(iTotalRecords);		//设置数据总数
 	     			$("#perPageCount").html(iDisplayLength);//设置每页数据数
 	     			$("#pages").html( pageCount);			//设置总共页数
 	     			//$("#nowPage").attr("value",1);			//设置 当前页数位置
 	     			document.getElementById("nowPage").value="1";		//设置 当前页数位置
 	     			document.getElementById("left").disabled=true;
 	     			document.getElementById("leftUp").disabled=true;
 	     			document.getElementById("right").disabled=false;
 	     			document.getElementById("rightUp").disabled=false;
 	  				$("#massageTable tr").remove();
 	  				
 	     			for(var i=0; i < json.pagaData.data.length; i++)
 	     			{
 	           			var temp= "<tr>"
 	                    + "<td >"+(i+1)+"</td>"
 	                    + "<td><input type='checkbox' name='same' id='"+json.pagaData.data[i].speId+"' /></td>"
 	                    + "<td>"+json.pagaData.data[i].speBarCode+"</td>" 
 	                    + "<td>"+json.pagaData.data[i].speDevName+"</td>" 
 	                    + "<td>"+json.pagaData.data[i].speSeqNum+"</td>"
 	                    + "<td>"+json.pagaData.data[i].speDevRoom+"</td>" 
 	                    + "<td>"+json.pagaData.data[i].speDevRoomFrame+"</td>" 
 	                    + "<td>"+json.pagaData.data[i].speUser+"</td>" 
 	                    + "<td>"+json.pagaData.data[i].speState+"</td>" 
 	                    + "<td><button>更多操作<img src='"+FAD+"/images/downdown.png' /></button></td>" 
 	                	   + "</tr>";
 	 				   $(temp).appendTo($("#massageTable"));
 	     			}
 	     			/*重绘筛选列表*/
 	     			
 					$("#speDevName1 ul li").remove();					//设备名称
 					
 					for(var i=0; i < json.speDevNameList.length; i++)
 					{
 						var temp= "<li>"+json.speDevNameList[i]+"</li>";
 						$(temp).appendTo($("#speDevName1 ul"));
 						
 					}
 					$("#speRoom1 ul li").remove();					//所属机房
 					for(var i=0; i < json.speRoomList.length; i++)
 					{
 						var temp= "<li>"+json.speRoomList[i]+"</li>";
 						$(temp).appendTo($("#speRoom1 ul"));
 					}
 					$("#speRoomFrame1 ul li").remove();				//所在机柜
 					for(var i=0; i < json.speRoomFrameList.length; i++)
 					{
 						var temp= "<li>"+json.speRoomFrameList[i]+"</li>";
 						$(temp).appendTo($("#speRoomFrame1 ul"));
 					}
 					$("#speUserName1 ul li").remove();
 					for(var i=0; i < json.speUserNameList.length; i++)
 					{
 						var temp= "<li>"+json.speUserNameList[i]+"</li>";
 						$(temp).appendTo($("#speUserName1 ul"));
 					}
 					$("#speState1 ul li").remove();
 					for(var i=0; i < json.speStateList.length; i++)
 					{
 						var temp= "<li>"+json.speStateList[i]+"</li>";
 						$(temp).appendTo($("#speState1 ul"));
 					}
 					//alert(11111)
 					/*重绘筛选列表结束*/
 					selectFun();	//绑定筛选点击事件
 					
 					//alert(111)
 	     		},
 	 			error:function(){
 	 				//alert("AJAX请求失败！请重试");
 	 				window.parent.document.getElementById('right').src="false.jsp";
 	 			}
 	 		});
 	   	
 	   	}
 	   });
 	   
 	//各筛选条件的公用ajax传输函数
 	
 		function selectDisplay( )
 		{
 			//alert("使用人："+speUserNameValue);
 			/*若点击触发筛选则去掉分页功能*/
				$("#selectCount").hide();
				$("#exportNow").text("导出全部");
				$("#exportAll").hide();	     //筛选无分页，去掉导出当前页
				
				$("#left").hide();
				$("#leftUp").hide();
				$("#right").hide();
				$("#rightUp").hide();
				$("#nowPage").hide();			//去掉翻页
 			$(".choseDiv ul li").each(function(){	
 				
 				if($(this).hasClass("searchSelect"))
 					$(this).css("backgroundColor","#D0E6F4");
 				else
 					$(this).css("backgroundColor","#F4F4F4");
 			});
 			$.ajax(
 	  					{
 	  						type:"POST",
 				 			url:"spepro/speAction_send",
 				 			data:{
 									speDevName:speDevNameValue,			//设备名称
 									speDevRoom:speRoomValue,			//所属机房
 									speDevRoomFrame:speRoomFrameValue,	//所属机柜
 									speUser:speUserNameValue,			//使用人
 									speState:speStateValue,				//状态	
 									nowPage:$("#nowPage").val(),					//当前页数
 				        			iDisplayLength:$("#perPageCount").html(),	//每页显示条数			
 					 			 },
 				 			cache:false,
 				 			success:function(data)
 				 			{
 				 				//alert("请求成功！");
 				 				var json =JSON.parse(data);
 				 				$("#count").html(json.data.length);			//设置数据总数
 	    						$("#perPageCount").html(json.data.length);	//设置每页数据数
 	    						$("#pages").html("1");						//设置总共页数
 				 				//alert(data);
 				 				$("#massageTable tr").remove();
 	       						for(var i=0; i < json.data.length; i++)
 	    						{
 	          						var temp= "<tr>"
 	                   				+ "<td >"+(i+1)+"</td>"
 	                			    + "<td><input type='checkbox' name='same' id='"+json.data[i].speId+"' /></td>"
 	                   				+ "<td>"+json.data[i].speBarCode+"</td>" 
 	                   				+ "<td>"+json.data[i].speDevName+"</td>" 
 	                   				+ "<td>"+json.data[i].speSeqNum+"</td>" 
 	                  				+ "<td>"+json.data[i].speDevRoom+"</td>" 
 	                   				+ "<td>"+json.data[i].speDevRoomFrame+"</td>" 
 	                   				+ "<td>"+json.data[i].speUser+"</td>" 
 	                   				+ "<td>"+json.data[i].speState+"</td>" 
 	                   				+ "<td><button>更多操作<img src='"+FAD+"/images/downdown.png' /></button></td>" 
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
 	   
 		/*筛选通用函数*/
 		
 	  // $(function(){
 		   var selectFun= function(){
 	  			var searchLis=$(".choseDiv ul li");
 	  			//alert(searchLis.length)
 	  			var speDevNames=$("#speDevName1 ul li");
 	  			var speRooms=$("#speRoom1 ul li");
 	  			var speRoomFrames=$("#speRoomFrame1 ul li");
 	  			var speUserNames=$("#speUserName1 ul li");
 	  			var speStates=$("#speState1 ul li");
 	  			
 	  			var speDevNamesFun=function()
 	  			{
 	  				//alert("haha");
 	  				
  					
 	  				$(this).parent().children("li").removeClass("searchSelect");
 	  				$(this).addClass("searchSelect");
 	  				searchLis.each(function()
 	  				{
 	  					if($(this).hasClass("searchSelect"))
 	  					{
 	  						//alert(4)
 	  						if($(this).hasClass("speDevNameSend"))
 	  						{	
 	  							speDevNameValue=$(this).text().replace(/\ +/g,"");
 	  						}
 	  						if($(this).hasClass("speRoomSend"))
 	  						{
 	  						speRoomValue=$(this).text().replace(/\ +/g,"");
 	  						}
 	  						if($(this).hasClass("speRoomFrameSend"))
 	  						{
 	  							speRoomFrameValue=$(this).text().replace(/\ +/g,"");
 	  							
 	  						}
 	  						if($(this).hasClass("speUserNameSend"))
 	  						{
 	  							speUserNameValue=$(this).text().replace(/\ +/g,"");
 	  						}
 	  						if($(this).hasClass("speStateSend"))
 	  						{
 	  							speStateValue=$(this).text().replace(/\ +/g,"");
 	  						}
 	  					}
 	  				});

 	  				selectDisplay();
 	  			
 	  			}
 	  			speDevNames.each(function()							//设备名称
 	  			{
 	  				$(this).removeClass("searchSelect");
 	  				$(this).addClass("speDevNameSend");
 	  				//alert("suc");
 	  				$(this).bind("click", speDevNamesFun);
 	  				
 	  			});
 	  			speRooms.each(function()							//所属机房
 	  			{
 	  				$(this).removeClass("searchSelect");
 	  				$(this).addClass("speRoomSend");
 	  				//alert($(this).html());
 	  				$(this).bind("click",function()
 	  				{
 	  					
 	  					
 	  					$(this).parent().children("li").removeClass("searchSelect");
 	  					$(this).addClass("searchSelect");
 	  					searchLis.each(function()
 	  					{
 	  						if($(this).hasClass("searchSelect"))
 	  						{
 	  							//alert(4)
 	  							if($(this).hasClass("speDevNameSend"))
 	  							{	
 	  								speDevNameValue=$(this).text().replace(/\ +/g,"");
 	  							}
 	  							if($(this).hasClass("speRoomSend"))
 	  							{
 	  							speRoomValue=$(this).text().replace(/\ +/g,"");
 	  							}
 	  							if($(this).hasClass("speRoomFrameSend"))
 	  							{
 	  								speRoomFrameValue=$(this).text().replace(/\ +/g,"");
 	  								
 	  							}
 	  							if($(this).hasClass("speUserNameSend"))
 	  							{
 	  								speUserNameValue=$(this).text().replace(/\ +/g,"");
 	  							}
 	  							if($(this).hasClass("speStateSend"))
 	  							{
 	  								speStateValue=$(this).text().replace(/\ +/g,"");
 	  							}
 	  						}
 	  					});
 	  					//alert("speDevNameValue="+ speDevNameValue+"1111 speRoomValue="+speRoomValue +"1111speRoomFrameValue="+speRoomFrameValue +"1111speUserNameValue=" + speUserNameValue+"speStateValue="+speStateValue);
 	  					selectDisplay();
 	  				});
 	  			});
 	  			speRoomFrames.each(function()							//所属机柜
 	  			{
 	  				$(this).removeClass("searchSelect");
 	  				$(this).addClass("speRoomFrameSend");
 	  				//alert($(this).html());
 	  				$(this).bind("click",function()
 	  				{
 	  					
 	  					//alert($(this).html())
 	  					$(this).parent().children("li").removeClass("searchSelect");
 	  					$(this).addClass("searchSelect");
 	  					searchLis.each(function()
 	  					{
 	  						if($(this).hasClass("searchSelect"))
 	  						{
 	  							//alert(4)
 	  							if($(this).hasClass("speDevNameSend"))
 	  							{	
 	  								speDevNameValue=$(this).text().replace(/\ +/g,"");
 	  							}
 	  							if($(this).hasClass("speRoomSend"))
 	  							{
 	  							speRoomValue=$(this).text().replace(/\ +/g,"");
 	  							}
 	  							if($(this).hasClass("speRoomFrameSend"))
 	  							{
 	  								speRoomFrameValue=$(this).text().replace(/\ +/g,"");
 	  								
 	  							}
 	  							if($(this).hasClass("speUserNameSend"))
 	  							{
 	  								speUserNameValue=$(this).text().replace(/\ +/g,"");
 	  							}
 	  							if($(this).hasClass("speStateSend"))
 	  							{
 	  								speStateValue=$(this).text().replace(/\ +/g,"");
 	  							}
 	  						}
 	  					});
 	  					selectDisplay()
 	  				//alert("speDevNameValue="+ speDevNameValue+"1111 speRoomValue="+speRoomValue +"1111speRoomFrameValue="+speRoomFrameValue +"1111speUserNameValue=" + speUserNameValue+"speStateValue="+speStateValue);
 	  				});
 	  				
 	  			});
 	  			speUserNames.each(function()								//使用人
 	  			{
 	  				$(this).removeClass("searchSelect");
 	  				$(this).addClass("speUserNameSend");
 	  				//alert($(this).html());
 	  				$(this).bind("click",function()
 	  				{
 	  					
 	  					//alert($(this).html())
 	  					$(this).parent().children("li").removeClass("searchSelect");
 	  					$(this).addClass("searchSelect");
 	  					searchLis.each(function()
 	  					{
 	  						if($(this).hasClass("searchSelect"))
 	  						{
 	  							//alert(4)
 	  							if($(this).hasClass("speDevNameSend"))
 	  							{	
 	  								speDevNameValue=$(this).text().replace(/\ +/g,"");
 	  							}
 	  							if($(this).hasClass("speRoomSend"))
 	  							{
 	  							speRoomValue=$(this).text().replace(/\ +/g,"");
 	  							}
 	  							if($(this).hasClass("speRoomFrameSend"))
 	  							{
 	  								speRoomFrameValue=$(this).text().replace(/\ +/g,"");
 	  								
 	  							}
 	  							if($(this).hasClass("speUserNameSend"))
 	  							{
 	  								speUserNameValue=$(this).text().replace(/\ +/g,"");
 	  							}
 	  							if($(this).hasClass("speStateSend"))
 	  							{
 	  								speStateValue=$(this).text().replace(/\ +/g,"");
 	  							}
 	  						}
 	  					});
 	  					//alert("speDevNameValue="+ speDevNameValue+"1111 speRoomValue="+speRoomValue +"1111speRoomFrameValue="+speRoomFrameValue +"1111speUserNameValue=" + speUserNameValue+"speStateValue="+speStateValue);
 	  					selectDisplay();
 	  				});
 	  				
 	  			});
 	  			speStates.each(function()										//状态
 	  			{
 	  				$(this).removeClass("searchSelect");
 	  				$(this).addClass("speStateSend");
 	  				//alert($(this).html());
 	  				$(this).bind("click",function()
 	  				{
 	  					
 	  					//alert($(this).html())
 	  					$(this).parent().children("li").removeClass("searchSelect");
 	  					$(this).addClass("searchSelect");
 	  					searchLis.each(function()
 	  					{
 	  						if($(this).hasClass("searchSelect"))
 	  						{
 	  							//alert(4)
 	  							if($(this).hasClass("speDevNameSend"))
 	  							{	
 	  								speDevNameValue=$(this).text().replace(/\ +/g,"");
 	  							}
 	  							if($(this).hasClass("speRoomSend"))
 	  							{
 	  							    speRoomValue=$(this).text().replace(/\ +/g,"");
 	  							}
 	  							if($(this).hasClass("speRoomFrameSend"))
 	  							{
 	  								speRoomFrameValue=$(this).text().replace(/\ +/g,"");
 	  								
 	  							}
 	  							if($(this).hasClass("speUserNameSend"))
 	  							{
 	  								speUserNameValue=$(this).text().replace(/\ +/g,"");
 	  							}
 	  							if($(this).hasClass("speStateSend"))
 	  							{
 	  								speStateValue=$(this).text().replace(/\ +/g,"");
 	  							}
 	  						}
 	  						 						
 	  					});
 	  					selectDisplay();
 	  				});
 	  				
 	  			});
 		   }
 	  		//});