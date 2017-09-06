
			
		$(function()
		{
		$.ajax({
			 type:"POST",
			 url:"probak/bakAction_search",
			 async: false,
			 data:{
			 		bakSeqNum:$("#searchbakSeqNum").val(),				//序列号
					bakDevType:$("#searchbakDevType").val(),			//设备类别
					bakUpdTime:$("#searchoffUpdTime").val(),			//更新时间
					iDisplayLength:"20",
					nowPage:"1"
				  },
			cache:false,
			 success:function(data)
			 {
    			var json =JSON.parse(data);
    			var iDisplayLength=json.pagaData.iDisplayLength; 				//每页条数
    			var iTotalRecords=json.pagaData.iTotalRecords;					//总数据条数
    			var pageCount=json.pagaData.totalPage;							//页数
    			$("#count").html(iTotalRecords);		//设置数据总数
    			$("#perPageCount").html(iDisplayLength);//设置每页数据数
    			$("#pages").html( pageCount);			//设置总共页数
    			//$("#nowPage").attr("value",1);			//设置 当前页数位置
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
    			for(var i=0; i < iDisplayLength; i++)
    			{
          			var temp="<tr>"
                    +"<td >"+(i+1)+"</td>"
                    +"<td><input type='checkbox' name='same' id='"+json.pagaData.data[i].bakId+"' /></td>"
                    +"<td>"+json.pagaData.data[i].bakSeqNum+"</td>"
                    +"<td>"+json.pagaData.data[i].bakVersion+"</td>"
                    +"<td>"+json.pagaData.data[i].bakFactory+"</td>"
                    +"<td>"+json.pagaData.data[i].bakDevType+"</td>"
                    +"<td>"+json.pagaData.data[i].bakForm+"</td>"
                    +"<td>"+json.pagaData.data[i].bakState+"</td>"
                    +"<td>"+json.pagaData.data[i].bakDevRoom+"</td>"
                    +"<td>"+json.pagaData.data[i].bakDevFrame+"</td>"
                    +"<td>"+json.pagaData.data[i].bakManaDepart+"</td>"
                    +"<td>"+json.pagaData.data[i].bakUseDepart+"</td>"
                    +"<td>"+json.pagaData.data[i].bakUser+"</td>"
                    +"<td><button>更多操作<img  src='"+FAD+"/images/downdown.png'/></button></td>"
                 	+"</tr>";
        
					$(temp).appendTo($("#massageTable"));
    			}
    			
    			
    			/*重绘筛选列表*/
				$("#bakDevType1 ul li").remove();					//设备类别
				//alert(json.bakStateList[0])
				//alert(json.bakUseDepartList[0]);
				for(var i=0; i < json.bakDevTypeList.length; i++)
				{
					var temp= "<li>"+json.bakDevTypeList[i]+"</li>";
					$(temp).appendTo($("#bakDevType1 ul"));
				}
				/*$("#bakState1 ul li").remove();					//状态
				for(var i=0; i < json.bakStateList.length; i++)
				{
					var temp= "<li>"+json.bakStateList[i]+"</li>";
					$(temp).appendTo($("#bakState1 ul"));
				}*/
				$("#bakDevRoom1 ul li").remove();				//所属库房
				for(var i=0; i < json.bakDevRoomList.length; i++)
				{
					var temp= "<li>"+json.bakDevRoomList[i]+"</li>";
					$(temp).appendTo($("#bakDevRoom1 ul"));
				}
				$("#bakDevFrame1 ul li").remove();
				for(var i=0; i < json.bakDevFrameList.length; i++)
				{
					var temp= "<li>"+json.bakDevFrameList[i]+"</li>";
					$(temp).appendTo($("#bakDevFrame1 ul"));	//所在货架
				}
				$("#bakManaDepart1 ul li").remove();			//实物管理部门
				for(var i=0; i < json.bakManaDepartList.length; i++)
				{
					var temp= "<li>"+json.bakManaDepartList[i]+"</li>";
					$(temp).appendTo($("#bakManaDepart1 ul"));
				}
				$("#bakUseDepart1 ul li").remove();			//使用部门
				
				for(var i=0; i < json.bakUseDepartList.length; i++)
				{
					var temp= "<li>"+json.bakUseDepartList[i]+"</li>";
					$(temp).appendTo($("#bakUseDepart1 ul"));
				}
				$("#bakUser1 ul li").remove();			//使用人
				for(var i=0; i < json.bakUserList.length; i++)
				{
					var temp= "<li>"+json.bakUserList[i]+"</li>";
					$(temp).appendTo($("#bakUser1 ul"));
				}
					
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
			      url:"probak/bakAction_search",
			      async: false,
			      data:{
			      		bakSeqNum:$("#searchbakSeqNum").val(),				//序列号
						bakDevType:$("#searchbakDevType").val(),			//设备类别
						bakUpdTime:$("#searchoffUpdTime").val(),			//更新时间
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
       					for(var i=0; i <iDisplayLength; i++)
    					{
          					var temp="<tr>"
                    		+"<td >"+(i+1)+"</td>"
                    		+"<td><input type='checkbox' name='same' id='"+json.pagaData.data[i].bakId+"' /></td>"
                    		+"<td>"+json.pagaData.data[i].bakSeqNum+"</td>"
                    		+"<td>"+json.pagaData.data[i].bakVersion+"</td>"
                    		+"<td>"+json.pagaData.data[i].bakFactory+"</td>"
                    		+"<td>"+json.pagaData.data[i].bakDevType+"</td>"
                    		+"<td>"+json.pagaData.data[i].bakForm+"</td>"
                    		+"<td>"+json.pagaData.data[i].bakState+"</td>"
                    		+"<td>"+json.pagaData.data[i].bakDevRoom+"</td>"
                    		+"<td>"+json.pagaData.data[i].bakDevFrame+"</td>"
                    		+"<td>"+json.pagaData.data[i].bakManaDepart+"</td>"
                    		+"<td>"+json.pagaData.data[i].bakUseDepart+"</td>"
                    		+"<td>"+json.pagaData.data[i].bakUser+"</td>"
                    		+"<td><button>更多操作<img  src='"+FAD+"/images/downdown.png'/></button></td>"
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
			 url:"probak/bakAction_search",
			 async: false,
			 data:{
			 		bakSeqNum:$("#searchbakSeqNum").val(),				//序列号
					bakDevType:$("#searchbakDevType").val(),			//设备类别
					bakUpdTime:$("#searchoffUpdTime").val(),			//更新时间
					iDisplayLength:$("#perPageCount").html(),			//每页显示条数
					nowPage:nowPageTemp									//当前页数
				  },
			cache:false,
			 success:function(data)
			 {
    			var json =JSON.parse(data);
    			$("#massageTable tr").remove();
    			for(var i=0; i < json.pagaData.data.length; i++)
    			{
          			var temp="<tr>"
                    +"<td >"+(i+1)+"</td>"
                    +"<td><input type='checkbox' name='same' id='"+json.pagaData.data[i].bakId+"' /></td>"
                    +"<td>"+json.pagaData.data[i].bakSeqNum+"</td>"
                    +"<td>"+json.pagaData.data[i].bakVersion+"</td>"
                    +"<td>"+json.pagaData.data[i].bakFactory+"</td>"
                    +"<td>"+json.pagaData.data[i].bakDevType+"</td>"
                    +"<td>"+json.pagaData.data[i].bakForm+"</td>"
                    +"<td>"+json.pagaData.data[i].bakState+"</td>"
                    +"<td>"+json.pagaData.data[i].bakDevRoom+"</td>"
                    +"<td>"+json.pagaData.data[i].bakDevFrame+"</td>"
                    +"<td>"+json.pagaData.data[i].bakManaDepart+"</td>"
                    +"<td>"+json.pagaData.data[i].bakUseDepart+"</td>"
                    +"<td>"+json.pagaData.data[i].bakUser+"</td>"
                    +"<td><button>更多操作<img  src='"+FAD+"/images/downdown.png'/></button></td>"
                 	+"</tr>";
        
					$(temp).appendTo($("#massageTable"));
    			}
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
			{	if( Number( $("#nowPage").val() ) > Number( $("#pages").html() ) || Number( $("#nowPage").val() ) < 1 || $("#nowPage").val()=="")
				{
    				//alert("请输入合法页数！");
    				;
				}
				else 
				{ $.ajax({
			      type:"POST",
			      url:"probak/bakAction_search",
			      async: false,
			      data:{
			      		bakSeqNum:$("#searchbakSeqNum").val(),				//序列号
						bakDevType:$("#searchbakDevType").val(),			//设备类别
						bakUpdTime:$("#searchoffUpdTime").val(),			//更新时间
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
                    		+"<td><input type='checkbox' name='same' id='"+json.pagaData.data[i].bakId+"' /></td>"
                    		+"<td>"+json.pagaData.data[i].bakSeqNum+"</td>"
                    		+"<td>"+json.pagaData.data[i].bakVersion+"</td>"
                    		+"<td>"+json.pagaData.data[i].bakFactory+"</td>"
                    		+"<td>"+json.pagaData.data[i].bakDevType+"</td>"
                    		+"<td>"+json.pagaData.data[i].bakForm+"</td>"
                    		+"<td>"+json.pagaData.data[i].bakState+"</td>"
                    		+"<td>"+json.pagaData.data[i].bakDevRoom+"</td>"
                    		+"<td>"+json.pagaData.data[i].bakDevFrame+"</td>"
                    		+"<td>"+json.pagaData.data[i].bakManaDepart+"</td>"
                    		+"<td>"+json.pagaData.data[i].bakUseDepart+"</td>"
                    		+"<td>"+json.pagaData.data[i].bakUser+"</td>"
                    		+"<td><button>更多操作<img  src='"+FAD+"/images/downdown.png'/></button></td>"
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
				if(document.getElementById("right").disabled == true|| Number( $("#pages").html() ) <= 1)
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
				if(window.document.getElementById("rightUp").disabled == true|| Number( $("#pages").html() ) <= 1)
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
  		 var countPerPage=document.getElementById("countPerPage");
  		 $("#selectCount").show();							//重新触发分页功能
  		 $("#exportNow").text("导出当前页");
  		 $("#exportAll").show();	     					//恢复分页，恢复导出当前页
  		
		 $("#left").show();
		 $("#leftUp").show();
		 $("#right").show();
		 $("#rightUp").show();
		 $("#nowPage").show();			//恢复翻页
  		 /*每次查询初始化筛选条件*/
	 	 bakDevTypeValue="";
		 bakStateValue="";
		 bakDevRoomValue="";
		 bakDevFrameValue="";
		 bakManaDepartValue="";
		 bakUseDepartValue="";
		 bakUserValue="";
		 $(".choseDiv ul li").each(function(){	
				$(this).css("backgroundColor","#F4F4F4");
		});
		 $(".choseDiv ul li").removeClass("searchSelect");
		 /*初始化筛选条件结束*/
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
			 url:"probak/bakAction_search",
			 async: false,
			// dataType:"json",
			 data:{
					bakSeqNum:$("#searchbakSeqNum").val(),				//序列号
					bakDevType:$("#searchbakDevType").val(),			//设备类别
					bakUpdTime:$("#searchbakUpdTime").val(),			//更新时间
					iDisplayLength:iDisplayLength,						//每页条数
			        nowPage:"1"							//当前页数
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
    			for(var i=0; i <=json.pagaData.data.length; i++)
    			{
          			var temp="<tr>"
                    		+"<td >"+(i+1)+"</td>"
                    		+"<td><input type='checkbox' name='same' id='"+json.pagaData.data[i].bakId+"' /></td>"
                    		+"<td>"+json.pagaData.data[i].bakSeqNum+"</td>"
                    		+"<td>"+json.pagaData.data[i].bakVersion+"</td>"
                    		+"<td>"+json.pagaData.data[i].bakFactory+"</td>"
                    		+"<td>"+json.pagaData.data[i].bakDevType+"</td>"
                    		+"<td>"+json.pagaData.data[i].bakForm+"</td>"
                    		+"<td>"+json.pagaData.data[i].bakState+"</td>"
                    		+"<td>"+json.pagaData.data[i].bakDevRoom+"</td>"
                    		+"<td>"+json.pagaData.data[i].bakDevFrame+"</td>"
                    		+"<td>"+json.pagaData.data[i].bakManaDepart+"</td>"
                    		+"<td>"+json.pagaData.data[i].bakUseDepart+"</td>"
                    		+"<td>"+json.pagaData.data[i].bakUser+"</td>"
                    		+"<td><button>更多操作<img  src='"+FAD+"/images/downdown.png'/></button></td>"
                 			+"</tr>";
        
							$(temp).appendTo($("#massageTable"));
    			}
    			/*重绘筛选列表*/
				$("#bakDevType1 ul li").remove();					//设备类别
				//alert(json.bakStateList[0])
				//alert(json.bakUseDepartList[0]);
				for(var i=0; i < json.bakDevTypeList.length; i++)
				{
					var temp= "<li>"+json.bakDevTypeList[i]+"</li>";
					$(temp).appendTo($("#bakDevType1 ul"));
				}
				/*$("#bakState1 ul li").remove();					//状态
				for(var i=0; i < json.bakStateList.length; i++)
				{
					var temp= "<li>"+json.bakStateList[i]+"</li>";
					$(temp).appendTo($("#bakState1 ul"));
				}*/
				$("#bakDevRoom1 ul li").remove();				//所属库房
				for(var i=0; i < json.bakDevRoomList.length; i++)
				{
					var temp= "<li>"+json.bakDevRoomList[i]+"</li>";
					$(temp).appendTo($("#bakDevRoom1 ul"));
				}
				$("#bakDevFrame1 ul li").remove();
				for(var i=0; i < json.bakDevFrameList.length; i++)
				{
					var temp= "<li>"+json.bakDevFrameList[i]+"</li>";
					$(temp).appendTo($("#bakDevFrame1 ul"));	//所在货架
				}
				$("#bakManaDepart1 ul li").remove();			//实物管理部门
				for(var i=0; i < json.bakManaDepartList.length; i++)
				{
					var temp= "<li>"+json.bakManaDepartList[i]+"</li>";
					$(temp).appendTo($("#bakManaDepart1 ul"));
				}
				$("#bakUseDepart1 ul li").remove();			//使用部门
				
				for(var i=0; i < json.bakUseDepartList.length; i++)
				{
					var temp= "<li>"+json.bakUseDepartList[i]+"</li>";
					$(temp).appendTo($("#bakUseDepart1 ul"));
				}
				$("#bakUser1 ul li").remove();			//使用人
				for(var i=0; i < json.bakUserList.length; i++)
				{
					var temp= "<li>"+json.bakUserList[i]+"</li>";
					$(temp).appendTo($("#bakUser1 ul"));
				}
					
				/*重绘筛选列表结束*/
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
		//alert("使用部门："+bakUseDepartValue+"    状态："+bakStateValue)
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
			 			url:"probak/bakAction_send",
			 			data:{
								bakDevType:bakDevTypeValue,							//设备类别
								bakState:bakStateValue,								//状态
								bakDevRoom:bakDevRoomValue,							//所属机房
								bakDevFrame:bakDevFrameValue,						//所属机柜
								bakManaDepart:bakManaDepartValue,					//实物管理部门
								bakUseDepart:bakUseDepartValue,						//使用部门
								bakUser:bakUserValue,								//使用人
								nowPage:"1",						//当前页数
			        			iDisplayLength:"20",			//每页显示条数			
				 			 },
			 			cache:false,
			 			success:function(data)
			 			{
			 				//alert("请求成功！");
			 				var json =JSON.parse(data);
			 				$("#count").html(json.data.length);			//设置数据总数
	    					$("#perPageCount").html(json.data.length);	//设置每页数据数
	    					$("#pages").html("1");						//设置总共页数
			 				//alert(1);
			 				$("#massageTable tr").remove();
       						for(var i=0; i < json.data.length; i++)
    						{
          						var temp="<tr>"
                    			+"<td >"+(i+1)+"</td>"
                    			+"<td><input type='checkbox' name='same' id='"+json.data[i].bakId+"' /></td>"
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
   $(function(){
  			var searchLis=$(".choseDiv ul li");
  			var bakDevTypes=$("#bakDevType1 ul li");
  			var bakStates=$("#bakState1 ul li");
  			var bakDevRooms=$("#bakDevRoom1 ul li");
  			var bakDevFrames=$("#bakDevFrame1 ul li");
  			var bakManaDeparts=$("#bakManaDepart1 ul li");
  			var bakUseDeparts=$("#bakUseDepart1 ul li");
  			var bakUsers=$("#bakUser1 ul li");
  							
  			bakDevTypes.each(function()							//设备类别
  			{
  				$(this).removeClass("searchSelect");
  				$(this).addClass("bakDevTypeSend");
  				$(this).bind("click",function()
  				{
  					$(this).parent().children("li").removeClass("searchSelect");
  					$(this).addClass("searchSelect");
  					searchLis.each(function()
  					{
  						if($(this).hasClass("searchSelect"))
  						{
  							if($(this).hasClass("bakDevTypeSend"))						//设备类别
  							{	
  								bakDevTypeValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakStateSend"))						//状态
  							{
  								bakStateValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakDevRoomSend"))						//所在机房
  							{
  								bakDevRoomValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakDevFrameSend"))						//所在机柜
  							{
  								bakDevFrameValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakManaDepartSend"))					//实物管理部门
  							{
  								bakManaDepartValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakUseDepartSend"))					//使用部门
  							{
  								bakUseDepart=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakUserSend"))						//使用人
  							{
  								bakUserValue=$(this).text().replace(/\ +/g,"");
  							}
  										
  						}
  					});							
  					selectDisplay();	
  				});
  				
  			});
  			bakStates.each(function()							//状态
  			{
  				$(this).removeClass("searchSelect");
  				$(this).addClass("bakStateSend");
  				$(this).bind("click",function()
  				{
  					
  					$(this).parent().children("li").removeClass("searchSelect");
  					$(this).addClass("searchSelect");
  					searchLis.each(function()
  					{
  						if($(this).hasClass("searchSelect"))
  						{
  							if($(this).hasClass("bakDevTypeSend"))						//设备类别
  							{	
  								bakDevTypeValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakStateSend"))						//状态
  							{
  								bakStateValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakDevRoomSend"))						//所在机房
  							{
  								bakDevRoomValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakDevFrameSend"))						//所在机柜
  							{
  								bakDevFrameValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakManaDepartSend"))					//实物管理部门
  							{
  								bakManaDepartValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakUseDepartSend"))					//使用部门
  							{
  								bakUseDepart=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakUserSend"))						//使用人
  							{
  								bakUserValue=$(this).text().replace(/\ +/g,"");
  							}
  										
  						}
  					});
  					selectDisplay();
  				});
  				
  			});
  			bakDevRooms.each(function()							//所属机房
  			{
  				$(this).removeClass("searchSelect");
  				$(this).addClass("bakDevRoomSend");
  				$(this).bind("click",function()
  				{
  					
  					$(this).parent().children("li").removeClass("searchSelect");
  					$(this).addClass("searchSelect");
  					searchLis.each(function()
  					{
  						if($(this).hasClass("searchSelect"))
  						{
  							if($(this).hasClass("bakDevTypeSend"))						//设备类别
  							{	
  								bakDevTypeValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakStateSend"))						//状态
  							{
  								bakStateValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakDevRoomSend"))						//所在机房
  							{
  								bakDevRoomValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakDevFrameSend"))						//所在机柜
  							{
  								bakDevFrameValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakManaDepartSend"))					//实物管理部门
  							{
  								bakManaDepartValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakUseDepartSend"))					//使用部门
  							{
  								bakUseDepart=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakUserSend"))						//使用人
  							{
  								bakUserValue=$(this).text().replace(/\ +/g,"");
  							}
  										
  						}
  					});
  					selectDisplay();
  				});
  				
  			});
  			bakDevFrames.each(function()											//所属机柜
  			{
  				$(this).removeClass("searchSelect");
  				$(this).addClass("bakDevFrameSend");
  				$(this).bind("click",function()
  				{
  					
  					$(this).parent().children("li").removeClass("searchSelect");
  					$(this).addClass("searchSelect");
  					searchLis.each(function()
  					{
  						if($(this).hasClass("searchSelect"))
  						{
  							if($(this).hasClass("bakDevTypeSend"))						//设备类别
  							{	
  								bakDevTypeValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakStateSend"))						//状态
  							{
  								bakStateValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakDevRoomSend"))						//所在机房
  							{
  								bakDevRoomValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakDevFrameSend"))						//所在机柜
  							{
  								bakDevFrameValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakManaDepartSend"))					//实物管理部门
  							{
  								bakManaDepartValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakUseDepartSend"))					//使用部门
  							{
  								bakUseDepart=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakUserSend"))						//使用人
  							{
  								bakUserValue=$(this).text().replace(/\ +/g,"");
  							}
  										
  						}
  					});
  					selectDisplay();
  				});
  				
  			});
  			bakManaDeparts.each(function()												//实物管理部门
  			{
  				$(this).removeClass("searchSelect");
  				$(this).addClass("bakManaDepartSend");
  				$(this).bind("click",function()
  				{
  					
  					$(this).parent().children("li").removeClass("searchSelect");
  					$(this).addClass("searchSelect");
  					searchLis.each(function()
  					{
  						if($(this).hasClass("searchSelect"))
  						{
  							if($(this).hasClass("bakDevTypeSend"))						//设备类别
  							{	
  								bakDevTypeValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakStateSend"))						//状态
  							{
  								bakStateValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakDevRoomSend"))						//所在机房
  							{
  								bakDevRoomValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakDevFrameSend"))						//所在机柜
  							{
  								bakDevFrameValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakManaDepartSend"))					//实物管理部门
  							{
  								bakManaDepartValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakUseDepartSend"))					//使用部门
  							{
  								bakUseDepart=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakUserSend"))						//使用人
  							{
  								bakUserValue=$(this).text().replace(/\ +/g,"");
  							}
  										
  						}
  					});
  					selectDisplay();
  				});
  				
  			});
  			
  			bakUseDeparts.each(function()										//使用部门
  			{
  				//alert(122)
  				$(this).removeClass("searchSelect");
  				$(this).addClass("bakUseDepartSend");
  				$(this).bind("click",function()
  				{
  					//alert(111)
  					$(this).parent().children("li").removeClass("searchSelect");
  					$(this).addClass("searchSelect");
  					searchLis.each(function()
  					{
  						if($(this).hasClass("searchSelect"))
  						{
  							if($(this).hasClass("bakDevTypeSend"))						//设备类别
  							{	
  								bakDevTypeValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakStateSend"))						//状态
  							{
  								bakStateValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakDevRoomSend"))						//所在机房
  							{
  								bakDevRoomValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakDevFrameSend"))						//所在机柜
  							{
  								bakDevFrameValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakManaDepartSend"))					//实物管理部门
  							{
  								bakManaDepartValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakUseDepartSend"))					//使用部门
  							{
  								bakUseDepartValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakUserSend"))						//使用人
  							{
  								bakUserValue=$(this).text().replace(/\ +/g,"");
  							}
  										
  						}
  					});
  					selectDisplay();
  				});
  				
  			});
  			
  			bakUsers.each(function()										//使用人
  			{//alert("使用人");
  				$(this).removeClass("searchSelect");
  				$(this).addClass("bakUserSend");
  				$(this).bind("click",function()
  				{
  					
  					$(this).parent().children("li").removeClass("searchSelect");
  					$(this).addClass("searchSelect");
  					searchLis.each(function()
  					{
  						if($(this).hasClass("searchSelect"))
  						{
  							if($(this).hasClass("bakDevTypeSend"))						//设备类别
  							{	
  								bakDevTypeValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakStateSend"))						//状态
  							{
  								bakStateValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakDevRoomSend"))						//所在机房
  							{
  								bakDevRoomValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakDevFrameSend"))						//所在机柜
  							{
  								bakDevFrameValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakManaDepartSend"))					//实物管理部门
  							{
  								bakManaDepartValue=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakUseDepartSend"))					//使用部门
  							{
  								bakUseDepart=$(this).text().replace(/\ +/g,"");
  							}
  							if($(this).hasClass("bakUserSend"))						//使用人
  							{
  								bakUserValue=$(this).text().replace(/\ +/g,"");
  							}
  										
  						}
  					});
  					selectDisplay();
  				});
  				
  			});
  		});
