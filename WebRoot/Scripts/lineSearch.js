		var lineOperatorValue="";		//运营商
		var lineSystemValue="";			//所属系统
		var lineLocalRemoteValue="";	//本地/长途
		var lineBandWidthValue="";		//带宽
		var lineARoomValue="";			//A端所在机房
		var lineZRoomValue="";			//Z端所在机房
		//var lineCountryUserValue="";	//国家中心使用人

	$(function()
	{//alert(5);
		$.ajax({
			 type:"POST",
			 url:"lininfo/infoAction_search",
			 async: false,
			 data:{
			 		lineNum:$("#searchlineNum").val(),					//专线号
					lineOperator:$("#searchlineOperator").val(),		//运营商
					lineSystem:$("#searchlineSystem").val(),			//所属系统
					iDisplayLength:"20",
					nowPage:"1"
				  },
			cache:false,
			 success:function(data)
			 {
    			var json =JSON.parse(data);
    			//alert(json.pagaData.iDisplayLength);
    			var iDisplayLength=json.pagaData.iDisplayLength; 				//每页条数
    			var iTotalRecords=json.pagaData.iTotalRecords;					//总数据条数
    			var pageCount=json.pagaData.totalPage;//页数
    			//alert(json.totalPage);
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
    			for(var i=0; i <json.pagaData.data.length; i++)
    			{
          			var temp="<tr>"
                    +"<td >"+(i+1)+"</td>"
                    +"<td><input type='checkbox' name='same' id='"+json.pagaData.data[i].lineId+"' /></td>"
                    +"<td>"+json.pagaData.data[i].lineNum+"</td>"
                    +"<td>"+json.pagaData.data[i].lineOperator+"</td>"
                    +"<td>"+json.pagaData.data[i].lineSystem+"</td>"
                    +"<td>"+json.pagaData.data[i].lineLocalRemote+"</td>"
                    +"<td>"+json.pagaData.data[i].lineBandWidth+"</td>"
                    +"<td>"+json.pagaData.data[i].lineARoom+"</td>"
                    +"<td>"+json.pagaData.data[i].lineZRoom+"</td>"
                    //+"<td>"+json.pagaData.data[i].lineCountryUser+"</td>"
                    +"<td><button>更多操作<img src='"+FAD+"/images/downdown.png'/></button></td>"
                 	+"</tr>";
        //alert(4)
					$(temp).appendTo($("#massageTable"));
    			}
    			//alert(2);
    			/*重绘筛选列表*/
				$("#lineOperator1 ul li").remove();					//运营商
				for(var i=0; i < json.lineOperatorList.length; i++)
				{
					var temp= "<li>"+json.lineOperatorList[i]+"</li>";
					$(temp).appendTo($("#lineOperator1 ul"));
				}
				
				$("#lineSystem1 ul li").remove();				//所属系统
				for(var i=0; i < json.lineSystemList.length; i++)
				{
					var temp= "<li>"+json.lineSystemList[i]+"</li>";
					$(temp).appendTo($("#lineSystem1 ul"));
				}
				/*$("#lineLocalRemote1 ul li").remove();
				for(var i=0; i < json.lineLocalRemoteList.length; i++)
				{alert(3);
					var temp= "<li>"+json.lineLocalRemoteList[i]+"</li>";
					$(temp).appendTo($("#lineLocalRemote1 ul"));	//本地/长途
				}*/
				
				$("#lineBandwidth1 ul li").remove();			//带宽
				for(var i=0; i < json.lineBandWidthList.length; i++)
				{
					var temp= "<li>"+json.lineBandWidthList[i]+"</li>";
					$(temp).appendTo($("#lineBandwidth1 ul"));
				}
				$("#lineARoom1 ul li").remove();				//A端所在机房
				
				for(var i=0; i < json.lineARoomList.length; i++)
				{
					var temp= "<li>"+json.lineARoomList[i]+"</li>";
					$(temp).appendTo($("#lineARoom1 ul"));
				}
				$("#lineZRoom1 ul li").remove();				//Z端所在机房
				for(var i=0; i < json.lineZRoomList.length; i++)
				{
					var temp= "<li>"+json.lineZRoomList[i]+"</li>";
					$(temp).appendTo($("#lineZRoom1 ul"));
				}
				//$("#lineCountryUser1 ul li").remove();			//国家中心使用人
				//alert(json.lineCountryUserList.length)
				/*for(var i=0; i < json.lineCountryUserList.length; i++)
				{
					var temp= "<li>"+json.lineCountryUserList[i]+"</li>";
					$(temp).appendTo($("#lineCountryUser1 ul"));
				}*/
				selectFun();
				/*重绘筛选列表结束*/
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
			//alert(iDisplayLength);
			var nowPage=$("#nowPage").val();
 		$.ajax({
			      type:"POST",
			      url:"lininfo/infoAction_search",
			      async: false,
			      data:{
			      		lineNum:$("#searchlineNum").val(),					//专线号
						lineOperator:$("#searchlineOperator").val(),		//运营商
						lineSystem:$("#searchlineSystem").val(),			//所属系统
			        	iDisplayLength:iDisplayLength,	//每页条数
			        	nowPage:"1"
			    	   },
			      success:function(data)
				  {		
    					var json =JSON.parse(data)
    					var iDisplayLength=json.pagaData.iDisplayLength; 				//每页条数
    					var iTotalRecords=json.pagaData.iTotalRecords;					//总数据条数
    					var iTotalDisplayRecords=json.pagaData.iTotalDisplayRecords;		//当前页数据条数
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
       					for(var i=0; i <json.pagaData.data.length; i++)
    					{
          					var temp="<tr>"
                    		+"<td >"+(i+1)+"</td>"
                    		+"<td><input type='checkbox' name='same' id='"+json.pagaData.data[i].lineId+"' /></td>"
                    		+"<td>"+json.pagaData.data[i].lineNum+"</td>"
                    		+"<td>"+json.pagaData.data[i].lineOperator+"</td>"
                    		+"<td>"+json.pagaData.data[i].lineSystem+"</td>"
                    		+"<td>"+json.pagaData.data[i].lineLocalRemote+"</td>"
                    		+"<td>"+json.pagaData.data[i].lineBandWidth+"</td>"
                    		+"<td>"+json.pagaData.data[i].lineARoom+"</td>"
                    		+"<td>"+json.pagaData.data[i].lineZRoom+"</td>"
                    		//+"<td>"+json.pagaData.data[i].lineCountryUser+"</td>"
                    		+"<td><button>更多操作<img src='"+FAD+"/images/downdown.png'/></button></td>"
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
    
    function ajaxDisplay( nowPageTemp )
    {
    	$.ajax({
			 type:"POST",
			 url:"lininfo/infoAction_search",
			 async: false,
			 data:{
			 		lineNum:$("#searchlineNum").val(),					//专线号
					lineOperator:$("#searchlineOperator").val(),		//运营商
					lineSystem:$("#searchlineSystem").val(),			//所属系统
					iDisplayLength:$("#perPageCount").html(),			//每页显示条数
					nowPage:nowPageTemp									//当前页数
				  },
			cache:false,
			 success:function(data)
			 {
    			var json =JSON.parse(data);
    			
    			$("#massageTable tr").remove();
    			for(var i=0; i <=json.pagaData.data.length; i++)
    			{
          			var temp="<tr>"
                    +"<td >"+(i+1)+"</td>"
                    +"<td><input type='checkbox' name='same' id='"+json.pagaData.data[i].lineId+"' /></td>"
                    +"<td>"+json.pagaData.data[i].lineNum+"</td>"
                    +"<td>"+json.pagaData.data[i].lineOperator+"</td>"
                    +"<td>"+json.pagaData.data[i].lineSystem+"</td>"
                    +"<td>"+json.pagaData.data[i].lineLocalRemote+"</td>"
                    +"<td>"+json.pagaData.data[i].lineBandWidth+"</td>"
                    +"<td>"+json.pagaData.data[i].lineARoom+"</td>"
                    +"<td>"+json.pagaData.data[i].lineZRoom+"</td>"
                  //  +"<td>"+json.pagaData.data[i].lineCountryUser+"</td>"
                    +"<td><button>更多操作<img src='"+FAD+"/images/downdown.png'/></button></td>"
                 	+"</tr>";
        
					$(temp).appendTo($("#massageTable"));
    			}
    			//window.parent.document.getElementById('right').src="false.jsp";
    		},
			error:function(){
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
				{ $.ajax({
			      type:"POST",
			      url:"lininfo/infoAction_search",
			      async: false,
			      data:{
			      		lineNum:$("#searchlineNum").val(),					//专线号
						lineOperator:$("#searchlineOperator").val(),		//运营商
						lineSystem:$("#searchlineSystem").val(),			//所属系统
			         	nowPage:$("#nowPage").val(),				//当前页数位置
			         	iDisplayLength:$("#perPageCount").html(),	//每页显示条数
			     		},
			      success:function(data)
			      {
			    	 var json =JSON.parse(data)
			         $("#massageTable tr").remove();
			         for(var i=0; i <json.pagaData.data.length; i++)
    					{
          					var temp="<tr>"
                   			+"<td >"+(i+1)+"</td>"
                    		+"<td><input type='checkbox' name='same' id='"+json.pagaData.data[i].lineId+"' /></td>"
                    		+"<td>"+json.pagaData.data[i].lineNum+"</td>"
                    		+"<td>"+json.pagaData.data[i].lineOperator+"</td>"
                    		+"<td>"+json.pagaData.data[i].lineSystem+"</td>"
                    		+"<td>"+json.pagaData.data[i].lineLocalRemote+"</td>"
                    		+"<td>"+json.pagaData.data[i].lineBandWidth+"</td>"
                    		+"<td>"+json.pagaData.data[i].lineARoom+"</td>"
                    		+"<td>"+json.pagaData.data[i].lineZRoom+"</td>"
                    		//+"<td>"+json.pagaData.data[i].lineCountryUser+"</td>"
                    		+"<td><button>更多操作<img src='"+FAD+"/images/downdown.png'/></button></td>"
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
				if(window.document.getElementById("rightUp").disabled == true || Number( $("#pages").html() ) <= 1)
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
  		 lineOperatorValue="";		//运营商
		 lineSystemValue="";			//所属系统
		 lineLocalRemoteValue="";	//本地/长途
		 lineBandWidthValue="";		//带宽
		 lineARoomValue="";			//A端所在机房
		 lineZRoomValue="";			//Z端所在机房
		 //lineCountryUserValue="";	//国家中心使用人
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
			 url:"lininfo/infoAction_search",
			 async: false,
			 data:{
					lineNum:$("#searchlineNum").val(),					//专线号
					lineOperator:$("#searchlineOperator").val(),		//运营商
					lineSystem:$("#searchlineSystem").val(),			//所属系统
					iDisplayLength:iDisplayLength,						//每页条数
			        nowPage:"1"						//当前页数
				  },
			 cache:false,
			 success:function(data)
			 {
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
          			var temp="<tr>"
                    		+"<td >"+(i+1)+"</td>"
                    		+"<td><input type='checkbox' name='same' id='"+json.pagaData.data[i].lineId+"' /></td>"
                    		+"<td>"+json.pagaData.data[i].lineNum+"</td>"
                    		+"<td>"+json.pagaData.data[i].lineOperator+"</td>"
                    		+"<td>"+json.pagaData.data[i].lineSystem+"</td>"
                    		+"<td>"+json.pagaData.data[i].lineLocalRemote+"</td>"
                    		+"<td>"+json.pagaData.data[i].lineBandWidth+"</td>"
                    		+"<td>"+json.pagaData.data[i].lineARoom+"</td>"
                    		+"<td>"+json.pagaData.data[i].lineZRoom+"</td>"
                    	//+"<td>"+json.pagaData.data[i].lineCountryUser+"</td>"
                    		+"<td><button>更多操作<img src='"+FAD+"/images/downdown.png'/></button></td>"
                 			+"</tr>";
        
							$(temp).appendTo($("#massageTable"));
    			}
    			/*重绘筛选列表*/
				$("#lineOperator1 ul li").remove();					//运营商
				for(var i=0; i < json.lineOperatorList.length; i++)
				{
					var temp= "<li>"+json.lineOperatorList[i]+"</li>";
					$(temp).appendTo($("#lineOperator1 ul"));
				}
				
				$("#lineSystem1 ul li").remove();				//所属系统
				for(var i=0; i < json.lineSystemList.length; i++)
				{
					var temp= "<li>"+json.lineSystemList[i]+"</li>";
					$(temp).appendTo($("#lineSystem1 ul"));
				}
				/*$("#lineLocalRemote1 ul li").remove();
				for(var i=0; i < json.lineLocalRemoteList.length; i++)
				{alert(3);
					var temp= "<li>"+json.lineLocalRemoteList[i]+"</li>";
					$(temp).appendTo($("#lineLocalRemote1 ul"));	//本地/长途
				}*/
				
				$("#lineBandwidth1 ul li").remove();			//带宽
				for(var i=0; i < json.lineBandWidthList.length; i++)
				{
					var temp= "<li>"+json.lineBandWidthList[i]+"</li>";
					$(temp).appendTo($("#lineBandwidth1 ul"));
				}
				$("#lineARoom1 ul li").remove();				//A端所在机房
				
				for(var i=0; i < json.lineARoomList.length; i++)
				{
					var temp= "<li>"+json.lineARoomList[i]+"</li>";
					$(temp).appendTo($("#lineARoom1 ul"));
				}
				$("#lineZRoom1 ul li").remove();				//Z端所在机房
				for(var i=0; i < json.lineZRoomList.length; i++)
				{
					var temp= "<li>"+json.lineZRoomList[i]+"</li>";
					$(temp).appendTo($("#lineZRoom1 ul"));
				}
				//$("#lineCountryUser1 ul li").remove();			//国家中心使用人
				//alert(json.lineCountryUserList.length)
				/*for(var i=0; i < json.lineCountryUserList.length; i++)
				{
					var temp= "<li>"+json.lineCountryUserList[i]+"</li>";
					$(temp).appendTo($("#lineCountryUser1 ul"));
				}*/
					
				/*重绘筛选列表结束*/
				selectFun();
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
		//alert("--运营商:"+lineOperatorValue+"--带宽:"+lineBandWidthValue+"--所在系统:"+lineSystemValue+"--本地/长途:"+lineLocalRemoteValue+"--A端:"+lineARoomValue+"Z端:"+lineZRoomValue+"国家中心使用人:"+lineCountryUserValue);
		//alert(typeof(lineBandWidthValue))
		$(".choseDiv ul li").each(function(){	
			
			if($(this).hasClass("searchSelect"))
				$(this).css("backgroundColor","#D0E6F4");
			else
				$(this).css("backgroundColor","#F4F4F4");
		});
		/*若点击触发筛选则去掉分页功能*/
		$("#selectCount").hide();
		$("#exportNow").text("导出全部");
		$("#exportAll").hide();	     //筛选无分页，去掉导出当前页111
		
		$("#left").hide();
		$("#leftUp").hide();
		$("#right").hide();
		$("#rightUp").hide();
		$("#nowPage").hide();			//去掉翻页
		$.ajax(
  					{
  						type:"POST",
			 			url:"lininfo/infoAction_send",
			 			data:{
								lineOperator:lineOperatorValue,						//运营商
								lineSystem:lineSystemValue,							//所属系统
								lineLocalRemote:lineLocalRemoteValue,				//本地/长途
								//lineBandWidth:lineBandWidthValue,					//带宽
								lineBandWidth:lineBandWidthValue,					//带宽
								lineARoom:lineARoomValue,							//A端所在机房
								lineZRoom:lineZRoomValue,							//Z端所在机房
								//lineCountryUser:lineCountryUserValue,				//国家中心使用人
								nowPage:"1",										//当前页数
			        			iDisplayLength:"20",			//每页显示条数			
				 			 },
			 			cache:false,
			 			success:function(data)
			 			{
			 				//alert("请求成功！");
			 				var json =JSON.parse(data);
			 				//alert("haha")
			 				$("#count").html(json.pagaData.data.length);			//设置数据总数
	    					$("#perPageCount").html(json.pagaData.data.length);	//设置每页数据数
	    					$("#pages").html("1");						//设置总共页数
			 				$("#massageTable tr").remove();
			 				//alert(data)
			 				//alert("满足筛选条件的数据有："+json.pagaData.data.length+"条")
       						for(var i=0; i < json.pagaData.data.length; i++)
    						{
          						var temp="<tr>"
                    			+"<td >"+(i+1)+"</td>"
                    			+"<td><input type='checkbox' name='same' id='"+json.pagaData.data[i].lineId+"' /></td>"
                    			+"<td>"+json.pagaData.data[i].lineNum+"</td>"
                    			+"<td>"+json.pagaData.data[i].lineOperator+"</td>"
                    			+"<td>"+json.pagaData.data[i].lineSystem+"</td>"
                    			+"<td>"+json.pagaData.data[i].lineLocalRemote+"</td>"
                    			+"<td>"+json.pagaData.data[i].lineBandWidth+"</td>"
                    			+"<td>"+json.pagaData.data[i].lineARoom+"</td>"
                    			+"<td>"+json.pagaData.data[i].lineZRoom+"</td>"
                    			//+"<td>"+json.pagaData.data[i].lineCountryUser+"</td>"
                    			+"<td><button>更多操作<img src='"+FAD+"/images/downdown.png'/></button></td>"
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
	//各筛选条件的公用ajax传输函数结束
   //筛选开始
  // $(function(){
	 var selectFun= function(){
  			var searchLis=$(".choseDiv ul li");
  			var lineOperators=$("#lineOperator1 ul li");
  			var lineSystems=$("#lineSystem1 ul li");
  			var lineLocalRemotes=$("#lineLocalRemote1 ul li");
  			var lineBandWidths=$("#lineBandwidth1 ul li");
  			var lineARooms=$("#lineARoom1 ul li");
  			var lineZRooms=$("#lineZRoom1 ul li");
  			//var lineCountryUsers=$("#lineCountryUser1 ul li");
  							
  			lineOperators.each(function()							//运营商
  			{
  				$(this).removeClass("searchSelect");
  				$(this).addClass("lineOperatorSend");
  				$(this).bind("click",function()
  				{
  					$(this).parent().children("li").removeClass("searchSelect");
  					$(this).addClass("searchSelect");
  					searchLis.each(function()
  					{
  						if($(this).hasClass("searchSelect"))
  						{
  							if($(this).hasClass("lineOperatorSend"))				//运营商
  							{	
  								lineOperatorValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineSystemSend"))					//所属系统
  							{
  								lineSystemValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineLocalRemoteSend"))				//本地/长途
  							{
  								lineLocalRemoteValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineBandWidthSend"))				//带宽
  							{
  								lineBandWidthValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineARoomSend"))					//A端所在机房
  							{
  								lineARoomValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineZRoomSend"))					//Z端所在机房
  							{
  								lineZRoomValue=$(this).text().replace(/\ +/g,"");
  							}
  							/*if($(this).hasClass("lineCountryUserSend"))				//国家中心使用人
  							{
  								lineCountryUserValue=$(this).text().replace(/\ +/g,"");
  							}*/
  										
  						}
  					});							
  					selectDisplay();				
  				});
  				
  			});
  			lineSystems.each(function()							//所属系统
  			{
  				$(this).removeClass("searchSelect");
  				$(this).addClass("lineSystemSend");
  				$(this).bind("click",function()
  				{
  					$(this).parent().children("li").removeClass("searchSelect");
  					$(this).addClass("searchSelect");
  					searchLis.each(function()
  					{
  						if($(this).hasClass("searchSelect"))
  						{
  							if($(this).hasClass("lineOperatorSend"))				//运营商
  							{	
  								lineOperatorValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineSystemSend"))					//所属系统
  							{
  								lineSystemValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineLocalRemoteSend"))				//本地/长途
  							{
  								lineLocalRemoteValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineBandWidthSend"))				//带宽
  							{
  								lineBandWidthValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineARoomSend"))					//A端所在机房
  							{
  								lineARoomValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineZRoomSend"))					//Z端所在机房
  							{
  								lineZRoomValue=$(this).text().replace(/\ +/g,"");
  							}
  							/*if($(this).hasClass("lineCountryUserSend"))				//国家中心使用人
  							{
  								lineCountryUserValue=$(this).text().replace(/\ +/g,"");
  							}*/
  										
  						}
  					});
  					selectDisplay();
  				});
  				
  			});
  			lineLocalRemotes.each(function()							//本地/长途
  			{
  				$(this).removeClass("searchSelect");
  				$(this).addClass("lineLocalRemoteSend");
  				$(this).bind("click",function()
  				{
  					$(this).parent().children("li").removeClass("searchSelect");
  					$(this).addClass("searchSelect");
  					searchLis.each(function()
  					{
  						if($(this).hasClass("searchSelect"))
  						{
  							if($(this).hasClass("lineOperatorSend"))				//运营商
  							{	
  								lineOperatorValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineSystemSend"))					//所属系统
  							{
  								lineSystemValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineLocalRemoteSend"))				//本地/长途
  							{
  								lineLocalRemoteValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineBandWidthSend"))				//带宽
  							{
  								lineBandWidthValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineARoomSend"))					//A端所在机房
  							{
  								lineARoomValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineZRoomSend"))					//Z端所在机房
  							{
  								lineZRoomValue=$(this).text().replace(/\ +/g,"");
  							}
  							/*if($(this).hasClass("lineCountryUserSend"))				//国家中心使用人
  							{
  								lineCountryUserValue=$(this).text().replace(/\ +/g,"");
  							}*/
  										
  						}
  					});
  					selectDisplay();
  				});
  				
  			});
  			lineBandWidths.each(function()								//带宽
  			{
  				$(this).removeClass("searchSelect");
  				$(this).addClass("lineBandWidthSend");
  				$(this).bind("click",function()
  				{
  					$(this).parent().children("li").removeClass("searchSelect");
  					$(this).addClass("searchSelect");
  					searchLis.each(function()
  					{
  						if($(this).hasClass("searchSelect"))
  						{
  							if($(this).hasClass("lineOperatorSend"))				//运营商
  							{	
  								lineOperatorValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineSystemSend"))					//所属系统
  							{
  								lineSystemValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineLocalRemoteSend"))				//本地/长途
  							{
  								lineLocalRemoteValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineBandWidthSend"))				//带宽
  							{
  								lineBandWidthValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineARoomSend"))					//A端所在机房
  							{
  								lineARoomValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineZRoomSend"))					//Z端所在机房
  							{
  								lineZRoomValue=$(this).text().replace(/\ +/g,"");
  							}
  							/*if($(this).hasClass("lineCountryUserSend"))				//国家中心使用人
  							{
  								lineCountryUserValue=$(this).text().replace(/\ +/g,"");
  							}*/
  										
  						}
  					});
  					selectDisplay();
  				});
  				
  			});
  			lineARooms.each(function()										//A端所在机房
  			{
  				$(this).removeClass("searchSelect");
  				$(this).addClass("lineARoomSend");
  				$(this).bind("click",function()
  				{
  					$(this).parent().children("li").removeClass("searchSelect");
  					$(this).addClass("searchSelect");
  					searchLis.each(function()
  					{
  						if($(this).hasClass("searchSelect"))
  						{
  							if($(this).hasClass("lineOperatorSend"))				//运营商
  							{	
  								lineOperatorValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineSystemSend"))					//所属系统
  							{
  								lineSystemValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineLocalRemoteSend"))				//本地/长途
  							{
  								lineLocalRemoteValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineBandWidthSend"))				//带宽
  							{
  								lineBandWidthValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineARoomSend"))					//A端所在机房
  							{
  								lineARoomValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineZRoomSend"))					//Z端所在机房
  							{
  								lineZRoomValue=$(this).text().replace(/\ +/g,"");
  							}
  							/*if($(this).hasClass("lineCountryUserSend"))				//国家中心使用人
  							{
  								lineCountryUserValue=$(this).text().replace(/\ +/g,"");
  							}*/
  										
  						}	
  					});
  					selectDisplay();
  				});
  				
  			});
  			
  			lineZRooms.each(function()										//Z端所在机房
  			{
  				$(this).removeClass("searchSelect");
  				$(this).addClass("lineZRoomSend");
  				$(this).bind("click",function()
  				{
  					$(this).parent().children("li").removeClass("searchSelect");
  					$(this).addClass("searchSelect");
  					searchLis.each(function()
  					{
  						if($(this).hasClass("searchSelect"))
  						{
  							if($(this).hasClass("lineOperatorSend"))				//运营商
  							{	
  								lineOperatorValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineSystemSend"))					//所属系统
  							{
  								lineSystemValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineLocalRemoteSend"))				//本地/长途
  							{
  								lineLocalRemoteValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineBandWidthSend"))				//带宽
  							{
  								lineBandWidthValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineARoomSend"))					//A端所在机房
  							{
  								lineARoomValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineZRoomSend"))					//Z端所在机房
  							{
  								lineZRoomValue=$(this).text().replace(/\ +/g,"");
  							}
  							/*if($(this).hasClass("lineCountryUserSend"))				//国家中心使用人
  							{
  								lineCountryUserValue=$(this).text().replace(/\ +/g,"");
  							}*/
  										
  						}	
  					});
  					selectDisplay();
  				});
  				
  			});
  			
  			/*lineCountryUsers.each(function()										//国家中心使用人
  			{
  				$(this).removeClass("searchSelect");
  				$(this).addClass("lineCountryUserSend");
  				$(this).bind("click",function()
  				{
  					$(this).parent().children("li").removeClass("searchSelect");
  					$(this).addClass("searchSelect");
  					searchLis.each(function()
  					{
  						if($(this).hasClass("searchSelect"))
  						{
  							if($(this).hasClass("lineOperatorSend"))				//运营商
  							{	
  								lineOperatorValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineSystemSend"))					//所属系统
  							{
  								lineSystemValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineLocalRemoteSend"))				//本地/长途
  							{
  								lineLocalRemoteValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineBandWidthSend"))				//带宽
  							{
  								lineBandWidthValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineARoomSend"))					//A端所在机房
  							{
  								lineARoomValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("lineZRoomSend"))					//Z端所在机房
  							{
  								lineZRoomValue=$(this).text().replace(/\ +/g,"");
  							}
  							/*if($(this).hasClass("lineCountryUserSend"))				//国家中心使用人
  							{
  								lineCountryUserValue=$(this).text().replace(/\ +/g,"");
  							}*/
  										
  				/*		}	
  					});
  					selectDisplay();
  				});
  				
  			});*/
  			
  			
	 }	
  	
  	//	});
   
  
  
  