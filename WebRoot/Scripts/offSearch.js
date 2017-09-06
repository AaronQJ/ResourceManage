
		$(function()
		{
		//alert(111);
		$.ajax({
			 type:"POST",
			 url:"offpro/offAction_search",
			 async: false,
			 data:{
			 		offSeqNum:$("#searchoffSeqNum").val(),				//商品序列号
					offName:$("#searchoffName").val(),					//名称
					offNum:$("#searchoffNum").val(),					//资产编号
					offBarCode:$("#searchoffBarCode").val(),			//条码
					offUser:$("#searchoffUser").val(),					//使用人
					iDisplayLength:"20",
					nowPage:"1"
				  },
			cache:false,
			 success:function(data)
			 {	//alert("成功");
    			var json =JSON.parse(data);
    			var iDisplayLength=json.pagaData.iDisplayLength; 				//每页条数
    			var iTotalRecords=json.pagaData.iTotalRecords;					//总数据条数
    			var pageCount=json.pagaData.totalPage;							//页数
    			//alert(555)
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
    			for(var i=0; i <json.pagaData.data.length; i++)
    			{
          			var temp="<tr>"
                    +"<td >"+(i+1)+"</td>"
                    +"<td><input type='checkbox' name='same' id='"+json.pagaData.data[i].offId+"' /></td>"
                    +"<td>"+json.pagaData.data[i].offNum+"</td>"
                    +"<td>"+json.pagaData.data[i].offName+"</td>"
                    +"<td>"+json.pagaData.data[i].offDevVersion+"</td>"
                    +"<td>"+json.pagaData.data[i].offSeqNum+"</td>"
                    +"<td>"+json.pagaData.data[i].offUser+"</td>"
                    +"<td>"+json.pagaData.data[i].offUseState+"</td>"
                    +"<td>"+json.pagaData.data[i].offState+"</td>"
                    +"<td>"+json.pagaData.data[i].offUseDepart+"</td>"
                    +"<td>"+json.pagaData.data[i].offManager+"</td>"
                    +"<td><button>更多操作<img src='"+FAD+"/images/downdown.png'/></button></td>"
                 	+"</tr>";
        
					$(temp).appendTo($("#massageTable"));
    			}
    			/*重绘筛选列表*/
				$("#offName1 ul li").remove();					//名称
				for(var i=0; i < json.offNameList.length; i++)
				{
					var temp= "<li>"+json.offNameList[i]+"</li>";
					$(temp).appendTo($("#offName1 ul"));
				}
				$("#offUseState1 ul li").remove();				//使用状况
				for(var i=0; i < json.offUseStateList.length; i++)
				{
					var temp= "<li>"+json.offUseStateList[i]+"</li>";
					$(temp).appendTo($("#offUseState1 ul"));
				}
				/*$("#offState1 ul li").remove();
				for(var i=0; i < json.offStateList.length; i++)
				{
					var temp= "<li>"+json.offStateList[i]+"</li>";
					$(temp).appendTo($("#offState1 ul"));	//状态
				}*/
				$("#offUseDepart1 ul li").remove();			//使用/管理部门
				for(var i=0; i < json.offUseDepartList.length; i++)
				{
					var temp= "<li>"+json.offUseDepartList[i]+"</li>";
					$(temp).appendTo($("#offUseDepart1 ul"));
				}
				$("#offUser1 ul li").remove();			//管理人
				
				for(var i=0; i < json.offManagerList.length; i++)
				{
					var temp= "<li>"+json.offManagerList[i]+"</li>";
					$(temp).appendTo($("#offUser1 ul"));
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
			var nowPage=$("#nowPage").val();
 		$.ajax({
			      type:"POST",
			      url:"offpro/offAction_search",
			      async: false,
			      data:{
			      		offSeqNum:$("#searchoffSeqNum").val(),				//商品序列号
						offName:$("#searchoffName").val(),					//名称
						offNum:$("#searchoffNum").val(),					//资产编号
						offBarCode:$("#searchoffBarCode").val(),			//条码
						offUser:$("#searchoffUser").val(),					//使用人
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
       					for(var i=0; i < json.pagaData.data.length; i++)
    					{
          					var temp="<tr>"
                    		+"<td >"+(i+1)+"</td>"
                    		+"<td><input type='checkbox' name='same' id='"+json.pagaData.data[i].offId+"' /></td>"
                    		+"<td>"+json.pagaData.data[i].offNum+"</td>"
                    		+"<td>"+json.pagaData.data[i].offName+"</td>"
                    		+"<td>"+json.pagaData.data[i].offDevVersion+"</td>"
                    		+"<td>"+json.pagaData.data[i].offSeqNum+"</td>"
                    		+"<td>"+json.pagaData.data[i].offUser+"</td>"
                    		+"<td>"+json.pagaData.data[i].offUseState+"</td>"
                    		+"<td>"+json.pagaData.data[i].offState+"</td>"
                    		+"<td>"+json.pagaData.data[i].offUseDepart+"</td>"
                    		+"<td>"+json.pagaData.data[i].offManager+"</td>"
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
      };

    });
    
    function ajaxDisplay( nowPageTemp )
    {
    
    	$.ajax({
			 type:"POST",
			 url:"offpro/offAction_search",
			 async: false,
			 data:{
			 		offSeqNum:$("#searchoffSeqNum").val(),				//商品序列号
					offName:$("#searchoffName").val(),					//名称
					offNum:$("#searchoffNum").val(),					//资产编号
					offBarCode:$("#searchoffBarCode").val(),			//条码
					offUser:$("#searchoffUser").val(),					//使用人
					iDisplayLength:$("#perPageCount").html(),			//每页显示条数
					nowPage:nowPageTemp									//当前页数位置
				  },
			 cache:false,
			 success:function(data)
			 {	
    			var json =JSON.parse(data);
				$("#massageTable tr").remove();
    			for(var i=0; i <json.pagaData.data.length; i++)
    			{
          			var temp="<tr>"
                    +"<td >"+(i+1)+"</td>"
                    +"<td><input type='checkbox' name='same' id='"+json.pagaData.data[i].offId+"' /></td>"
                    +"<td>"+json.pagaData.data[i].offNum+"</td>"
                    +"<td>"+json.pagaData.data[i].offName+"</td>"
                    +"<td>"+json.pagaData.data[i].offDevVersion+"</td>"
                    +"<td>"+json.pagaData.data[i].offSeqNum+"</td>"
                    +"<td>"+json.pagaData.data[i].offUser+"</td>"
                    +"<td>"+json.pagaData.data[i].offUseState+"</td>"
                    +"<td>"+json.pagaData.data[i].offState+"</td>"
                    +"<td>"+json.pagaData.data[i].offUseDepart+"</td>"
                    +"<td>"+json.pagaData.data[i].offManager+"</td>"
                    +"<td><button>更多操作<img src='"+FAD+"/images/downdown.png'/></button></td>"
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
			      url:"offpro/offAction_search",
			      async: false,
			      data:{
			      		offSeqNum:$("#searchoffSeqNum").val(),				//商品序列号
						offName:$("#searchoffName").val(),					//名称
						offNum:$("#searchoffNum").val(),					//资产编号
						offBarCode:$("#searchoffBarCode").val(),			//条码
						offUser:$("#searchoffUser").val(),					//使用人
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
                    		+"<td><input type='checkbox' name='same' id='"+json.pagaData.data[i].offId+"' /></td>"
                    		+"<td>"+json.pagaData.data[i].offNum+"</td>"
                    		+"<td>"+json.pagaData.data[i].offName+"</td>"
                    		+"<td>"+json.pagaData.data[i].offDevVersion+"</td>"
                    		+"<td>"+json.pagaData.data[i].offSeqNum+"</td>"
                    		+"<td>"+json.pagaData.data[i].offUser+"</td>"
                    		+"<td>"+json.pagaData.data[i].offUseState+"</td>"
                    		+"<td>"+json.pagaData.data[i].offState+"</td>"
                    		+"<td>"+json.pagaData.data[i].offUseDepart+"</td>"
                    		+"<td>"+json.pagaData.data[i].offManager+"</td>"
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
			};
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
 
    //按条码查询
    $(document).ready(function () { 	
		$("#searchoffBarCode").keydown(function (e) {	
		$("#selectCount").hide();					//条码唯一，查询后只有一条数据不需要分页
		var curKey = e.which ;
		if (curKey == 13) {
			$("#searchoffSeqNum").val("");
			$("#searchoffName").val("");
			$("#searchoffNum").val("");
			
			$("#searchoffUser").val("");
     		$.ajax({
     			type:"POST",
				url:"offpro/offAction_findByBarcode",
				async: false,
				data:{
			 		offBarCode:$("#searchoffBarCode").val(),			//条码
					iDisplayLength:$("#perPageCount").html(),
					nowPage:"1"
				  },
				cache:false,
				success:function(data){
					var json =JSON.parse(data);
	    			var pageCount=json.totalPage;							//页数
	    			$("#count").html("1");			//设置数据总数
	    			$("#perPageCount").html("1");	//设置每页数据数
	    			$("#pages").html( "1");			//设置总共页数
	    			//$("#nowPage").attr("value",1);	//设置 当前页数位置
	    			document.getElementById("nowPage").value="1";		//设置 当前页数位置
				
    				document.getElementById("left").disabled=true;
    				document.getElementById("leftUp").disabled=true;
    				document.getElementById("right").disabled=false;
    				document.getElementById("rightUp").disabled=false;
					$("#massageTable tr").remove();
				
					if(($("#searchoffBarCode").val()).length!=0)
					{
          			var temp="<tr>"
                    	+"<td >1</td>"
                    	+"<td><input type='checkbox' name='same' id='"+json.data[0].offId+"' /></td>"
                    	+"<td>"+json.data[0].offNum+"</td>"
                    	+"<td>"+json.data[0].offName+"</td>"
                    	+"<td>"+json.data[0].offDevVersion+"</td>"
                   		+"<td>"+json.data[0].offSeqNum+"</td>"
                    	+"<td>"+json.data[0].offUser+"</td>"
                    	+"<td>"+json.data[0].offUseState+"</td>"
                    	+"<td>"+json.data[0].offState+"</td>"
                    	+"<td>"+json.data[0].offUseDepart+"</td>"
                    	+"<td>"+json.data[0].offManager+"</td>"
                   		+"<td><button>更多操作<img src='"+FAD+"/images/downdown.png'/></button></td>"
                 		+"</tr>";
						$(temp).appendTo($("#massageTable"));
					}
					else{
						$("#count").html("0");		//设置数据总数
	    				$("#perPageCount").html("0");//设置每页数据数
	    				$("#pages").html( "0");			//设置总共页数
					}
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
		 	 offNameValue="";
			 offUseStateValue="";
			 offStateValue="";
			 offUseDepartValue="";
			 offManagerValue="";
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
			 url:"offpro/offAction_search",
			 async: false,
			// dataType:"json",
			 data:{
					offSeqNum:$("#searchoffSeqNum").val(),				//商品序列号
					offName:$("#searchoffName").val(),					//名称
					offNum:$("#searchoffNum").val(),					//资产编号
					offBarCode:$("#searchoffBarCode").val(),			//条码
					offUser:$("#searchoffUser").val(),					//使用人
					iDisplayLength:iDisplayLength,						//每页条数
			        nowPage:"1"							//当前页数
				  },
			 cache:false,
			 success:function(data)
			 {
			 //alert("success");
			// alert(data)
    			var json =JSON.parse(data);
    			//alert(json.pagaData.iDisplayLength);
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
                    		+"<td><input type='checkbox' name='same' id='"+json.pagaData.data[i].offId+"' /></td>"
                    		+"<td>"+json.pagaData.data[i].offNum+"</td>"
                    		+"<td>"+json.pagaData.data[i].offName+"</td>"
                    		+"<td>"+json.pagaData.data[i].offDevVersion+"</td>"
                    		+"<td>"+json.pagaData.data[i].offSeqNum+"</td>"
                    		+"<td>"+json.pagaData.data[i].offUser+"</td>"
                    		+"<td>"+json.pagaData.data[i].offUseState+"</td>"
                    		+"<td>"+json.pagaData.data[i].offState+"</td>"
                    		+"<td>"+json.pagaData.data[i].offUseDepart+"</td>"
                    		+"<td>"+json.pagaData.data[i].offManager+"</td>"
                    		+"<td><button>更多操作<img src='"+FAD+"/images/downdown.png'/></button></td>"
                 			+"</tr>";
        
							$(temp).appendTo($("#massageTable"));
    			}
    			/*重绘筛选列表*/
				$("#offName1 ul li").remove();					//名称
				for(var i=0; i < json.offNameList.length; i++)
				{
					var temp= "<li>"+json.offNameList[i]+"</li>";
					$(temp).appendTo($("#offName1 ul"));
				}
				$("#offUseState1 ul li").remove();				//使用状况
				for(var i=0; i < json.offUseStateList.length; i++)
				{
					var temp= "<li>"+json.offUseStateList[i]+"</li>";
					$(temp).appendTo($("#offUseState1 ul"));
				}
				/*$("#offState1 ul li").remove();
				for(var i=0; i < json.offStateList.length; i++)
				{
					var temp= "<li>"+json.offStateList[i]+"</li>";
					$(temp).appendTo($("#offState1 ul"));	//状态
				}*/
				$("#offUseDepart1 ul li").remove();			//使用/管理部门
				for(var i=0; i < json.offUseDepartList.length; i++)
				{
					var temp= "<li>"+json.offUseDepartList[i]+"</li>";
					$(temp).appendTo($("#offUseDepart1 ul"));
				}
				$("#offUser1 ul li").remove();			//管理人
				
				for(var i=0; i < json.offUserList.length; i++)
				{
					var temp= "<li>"+json.offUserList[i]+"</li>";
					$(temp).appendTo($("#offUser1 ul"));
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
   function printHTML(){  
        var bdhtml=window.document.body.innerHTML;		//获取当前页的html代码    
        var sprnstr="<!--begin-->";						//设置打印开始区域    
        var eprnstr="<!--end-->";						//设置打印结束区域    
        var prnhtml=bdhtml.substring(bdhtml.indexOf(sprnstr)+18); 		//从开始代码向后取html    
        printhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));		//从结束代码向前取html    
    
        printWindow = window.open();
        printWindow.document.write(printhtml);
        printWindow.print();
		printWindow.close(); 
       }    
   //各筛选条件的公用ajax传输函数
	function selectDisplay( )
	{
		//alert("--名称:"+offNameValue+" --使用状况:"+offUseStateValue+" --状态:"+offStateValue+" --使用/管理部门:"+offUseDepartValue+" --管理人:"+offManagerValue);
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
			 			url:"offpro/offAction_send",
			 			data:{
								offName:offNameValue,						//名称
								offUseState:offUseStateValue,				//使用状况
								offState:offStateValue,						//状态
								offUseDepart:offUseDepartValue,				//使用/管理部门
								offManager:offManagerValue,					//管理人	
								nowPage:"1",								//当前页数
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
			 				$("#massageTable tr").remove();
      						for(var i=0; i < json.data.length; i++)
   						{
         						var temp="<tr>"
                   			+"<td >"+(i+1)+"</td>"
                   			+"<td><input type='checkbox' name='same' id='"+json.data[i].offId+"' /></td>"
                   			+"<td>"+json.data[i].offNum+"</td>"
                   			+"<td>"+json.data[i].offName+"</td>"
                   			+"<td>"+json.data[i].offDevVersion+"</td>"
                   			+"<td>"+json.data[i].offSeqNum+"</td>"
                   			+"<td>"+json.data[i].offUser+"</td>"
                   			+"<td>"+json.data[i].offUseState+"</td>"
                   			+"<td>"+json.data[i].offState+"</td>"
                   			+"<td>"+json.data[i].offUseDepart+"</td>"
                   			+"<td>"+json.data[i].offManager+"</td>"
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
 
  $(function(){
 			var searchLis=$(".choseDiv ul li");
 			var offNames=$("#offName1 ul li");
 			var offUseStates=$("#offUseState1 ul li");
 			var offStates=$("#offState1 ul li");
 			var offUseDeparts=$("#offUseDepart1 ul li");
 			var offUsers=$("#offUser1 ul li");				//管理人（但是div的id写的有问题写成了使用人，就将错就错了）
 			
 			offNames.each(function()						//名称
 			{
 				$(this).removeClass("searchSelect");
 				$(this).addClass("offNameSend");
 				$(this).bind("click",function()
 				{
 					
 					$(this).parent().children("li").removeClass("searchSelect");
 					$(this).addClass("searchSelect");
 					searchLis.each(function()
 					{
 						if($(this).hasClass("searchSelect"))
 						{
 							if($(this).hasClass("offNameSend"))
 							{	
 								offNameValue=$(this).text().replace(/\ +/g,"");
 							}
 							if($(this).hasClass("offUseStateSend"))
 							{
 								offUseStateValue=$(this).text().replace(/\ +/g,"");
 							}
 							if($(this).hasClass("offStateSend"))
 							{
 								offStateValue=$(this).text().replace(/\ +/g,"");
 							}
 							if($(this).hasClass("offUseDepartSend"))
 							{
 								offUseDepartValue=$(this).text().replace(/\ +/g,"");
 							}
 							if($(this).hasClass("offUserSend"))
 							{
 								offManagerValue=$(this).text().replace(/\ +/g,"");
 							}
 						}
 					});
 					selectDisplay();
 				});
 			});
 			offUseStates.each(function()							//使用状况
 			{
 				$(this).removeClass("searchSelect");
 				$(this).addClass("offUseStateSend");
 				$(this).bind("click",function()
 				{	
 					
 					$(this).parent().children("li").removeClass("searchSelect");
 					$(this).addClass("searchSelect");
 					searchLis.each(function()
 					{
 						if($(this).hasClass("searchSelect"))
 						{
 							if($(this).hasClass("offNameSend"))
 							{	
 								offNameValue=$(this).text().replace(/\ +/g,"");
 							}
 							if($(this).hasClass("offUseStateSend"))
 							{
 								offUseStateValue=$(this).text().replace(/\ +/g,"");
 							}
 							if($(this).hasClass("offStateSend"))
 							{
 								offStateValue=$(this).text().replace(/\ +/g,"");
 								
 							}
 							if($(this).hasClass("offUseDepartSend"))
 							{
 								offUseDepartValue=$(this).text().replace(/\ +/g,"");
 							}
 							if($(this).hasClass("offUserSend"))
 							{
 								offManagerValue=$(this).text().replace(/\ +/g,"");
 							}
 						}
 					});
 					selectDisplay()
 				});
 				
 			});
 			offStates.each(function()							//状态
 			{
 				$(this).removeClass("searchSelect");
 				$(this).addClass("offStateSend");
 				$(this).bind("click",function()
 				{
 					
 					$(this).parent().children("li").removeClass("searchSelect");
 					$(this).addClass("searchSelect");
 					searchLis.each(function()
 					{
 						if($(this).hasClass("searchSelect"))
 						{
 							if($(this).hasClass("offNameSend"))
 							{	
 								offNameValue=$(this).text().replace(/\ +/g,"");
 							}
 							if($(this).hasClass("offUseStateSend"))
 							{
 								offUseStateValue=$(this).text().replace(/\ +/g,"");
 							}
 							if($(this).hasClass("offStateSend"))
 							{
 								offStateValue=$(this).text().replace(/\ +/g,"");
 								
 							}
 							if($(this).hasClass("offUseDepartSend"))
 							{
 								offUseDepartValue=$(this).text().replace(/\ +/g,"");
 							}
 							if($(this).hasClass("offUserSend"))
 							{
 								offManagerValue=$(this).text().replace(/\ +/g,"");
 							}
 						}
 					});
 					selectDisplay()
 				});
 				
 			});
 			offUseDeparts.each(function()								//使用/管理部门
 			{
 				$(this).removeClass("searchSelect");
 				$(this).addClass("offUseDepartSend");
 				$(this).bind("click",function()
 				{
 					
 					$(this).parent().children("li").removeClass("searchSelect");
 					$(this).addClass("searchSelect");
 					searchLis.each(function()
 					{
 						if($(this).hasClass("searchSelect"))
 						{
 							if($(this).hasClass("offNameSend"))
 							{	
 								offNameValue=$(this).text().replace(/\ +/g,"");
 							}
 							if($(this).hasClass("offUseStateSend"))
 							{
 								offUseStateValue=$(this).text().replace(/\ +/g,"");
 							}
 							if($(this).hasClass("offStateSend"))
 							{
 								offStateValue=$(this).text().replace(/\ +/g,"");
 								
 							}
 							if($(this).hasClass("offUseDepartSend"))
 							{
 								offUseDepartValue=$(this).text().replace(/\ +/g,"");
 							}
 							if($(this).hasClass("offUserSend"))
 							{
 								offManagerValue=$(this).text().replace(/\ +/g,"");
 							}
 						}
 					});
 					selectDisplay();
 				});
 				
 			});
 			offUsers.each(function()										//管理人
 			{
 				$(this).removeClass("searchSelect");
 				$(this).addClass("offUserSend");
 				$(this).bind("click",function()
 				{
 					
 					$(this).parent().children("li").removeClass("searchSelect");
 					$(this).addClass("searchSelect");
 					searchLis.each(function()
 					{
 						if($(this).hasClass("searchSelect"))
 						{
 							if($(this).hasClass("offNameSend"))
 							{	
 								offNameValue=$(this).text().replace(/\ +/g,"");
 							}
 							if($(this).hasClass("offUseStateSend"))
 							{
 								offUseStateValue=$(this).text().replace(/\ +/g,"");
 							}
 							if($(this).hasClass("offStateSend"))
 							{
 								offStateValue=$(this).text().replace(/\ +/g,"");
 								
 							}
 							if($(this).hasClass("offUseDepartSend"))
 							{
 								offUseDepartValue=$(this).text().replace(/\ +/g,"");
 							}
 							if($(this).hasClass("offUserSend"))
 							{
 								offManagerValue=$(this).text().replace(/\ +/g,"");
 							}
 						}
 						 						
 					});
 					selectDisplay();
 				});
 				
 			});
 	
 		});
 	