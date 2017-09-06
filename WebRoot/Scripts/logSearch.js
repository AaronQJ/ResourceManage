	
    var obj;
 	$(document).ready(function(){
 		
 			$.post("logselectall",{countPerPage:$("#countPerPage").val(),page:"1"},function(data){
 			if(data == "noLimit"){
 	         		//alert("没有权限！");
 	         	    window.location.href="noPower.jsp";
 	         	}else{		
	
			$("#logTable tr").remove();
 			var iDisplayLength=data.iDisplayLength; 				//每页条数
    			var iTotalRecords=data.iTotalRecords;					//总数据条数
    			var pageCount=data.totalPage;							//页数
    			$("#count").html(iTotalRecords);		//设置数据总数
    			$("#perPageCount").html(iDisplayLength);//设置每页数据数
    			$("#pages").html( pageCount);			//设置总共页数
    			document.getElementById("page").value="1";		//设置 当前页数位置
    			document.getElementById("left").disabled=true;
    			document.getElementById("leftUp").disabled=true;
    			document.getElementById("right").disabled=false;
    			document.getElementById("rightUp").disabled=false;
    			for(var i=0; i <=iDisplayLength; i++)
    			{
          			var temp="<tr>"
          			 + "<td >"+(i+1)+"</td>"
                   + "<td><input type='checkbox' name='same' id='"+data.data[i].logId+"' /></td>"
                    +"<td>"+data.data[i].objName+"</td>"
	           +"<td>"+data.data[i].objId+"</td>"
                    +"<td>"+data.data[i].objType+"</td>"
                    +"<td>"+data.data[i].operType+"</td>"
                    +"<td>"+data.data[i].fieldName+"</td>"
                    +"<td>"+data.data[i].fieldOriValue+"</td>"
                    +"<td>"+data.data[i].fieldUpdValue+"</td>"
                    +"<td>"+data.data[i].userName+"</td>"
                    +"<td>"+data.data[i].operTime+"</td>"
                 	+"</tr>";
        
					$(temp).appendTo($("#logTable"));
					obj = 0;
    					}
				}
 				
			});
			
 			$("#searchButton").click(function(){
 				$.post("logselect",{countPerPage:$("#countPerPage").val(),page:"1",objName:$("#objName").val(),userName:$("#userName").val(),logType:$("#logType").val(),startTime:$("#updTime1").val(),endTime:$("#updTime2").val()},function(data){
					$("#logTable tr").remove();
					var iDisplayLength=data.iDisplayLength; 			//每页条数
    			var iTotalRecords=data.iTotalRecords;					//总数据条数
    			var pageCount=data.totalPage;							//页数
    			$("#count").html(iTotalRecords);		//设置数据总数
    			$("#perPageCount").html(iDisplayLength);//设置每页数据数
    			$("#pages").html( pageCount);			//设置总共页数
    			document.getElementById("page").value="1";		//设置 当前页数位置
    			document.getElementById("left").disabled=true;
    			document.getElementById("leftUp").disabled=true;
    			document.getElementById("right").disabled=false;
    			document.getElementById("rightUp").disabled=false;
    			for(var i=0; i <=iDisplayLength; i++)
    			{
          			var temp="<tr>"
          			+ "<td >"+(i+1)+"</td>"
                   	+ "<td><input type='checkbox' name='same' id='"+data.data[i].logId+"' /></td>"
                    +"<td>"+data.data[i].objName+"</td>"
                    +"<td>"+data.data[i].objId+"</td>"
                    +"<td>"+data.data[i].objType+"</td>"
                    +"<td>"+data.data[i].operType+"</td>"
                    +"<td>"+data.data[i].fieldName+"</td>"
                    +"<td>"+data.data[i].fieldOriValue+"</td>"
                    +"<td>"+data.data[i].fieldUpdValue+"</td>"
                    +"<td>"+data.data[i].userName+"</td>"
                    +"<td>"+data.data[i].operTime+"</td>"
                 	+"</tr>";
        
					$(temp).appendTo($("#logTable"));
					 obj = 1;
    			}
				   
				});
			});
 			
 		
 			$("#countPerPage").change(function(){
 					if(obj == 1){
 						$.post("logselect",{countPerPage:$("#countPerPage").val(),page:"1",objName:$("#objName").val(),userName:$("#userName").val(),logType:$("#logType").val(),startTime:$("#updTime1").val(),endTime:$("#updTime2").val()},function(data){
								$("#logTable tr").remove();
 								var iDisplayLength=data.iDisplayLength; 				//每页条数
    							var iTotalRecords=data.iTotalRecords;					//总数据条数
    							var pageCount=data.totalPage;							//页数
    							$("#count").html(iTotalRecords);		//设置数据总数
    							$("#perPageCount").html(iDisplayLength);//设置每页数据数
    							$("#pages").html( pageCount);			//设置总共页数   				
    							document.getElementById("page").value="1";		//设置 当前页数位置
    							document.getElementById("left").disabled=true;
    							document.getElementById("leftUp").disabled=true;
    							document.getElementById("right").disabled=false;
    							document.getElementById("rightUp").disabled=false;
    							for(var i=0; i <=iDisplayLength; i++)
    							{var temp="<tr>"
          			 + "<td >"+(i+1)+"</td>"
                    + "<td><input type='checkbox' name='same' id='"+data.data[i].logId+"' /></td>"
                    +"<td>"+data.data[i].objName+"</td>"
                     +"<td>"+data.data[i].objId+"</td>"
                    +"<td>"+data.data[i].objType+"</td>"
                    +"<td>"+data.data[i].operType+"</td>"
                    +"<td>"+data.data[i].fieldName+"</td>"
                    +"<td>"+data.data[i].fieldOriValue+"</td>"
                    +"<td>"+data.data[i].fieldUpdValue+"</td>"
                    +"<td>"+data.data[i].userName+"</td>"
                    +"<td>"+data.data[i].operTime+"</td>"
                 	+"</tr>";
        
									$(temp).appendTo($("#logTable"));
								}
							});
					}
					if(obj == 0){
 					
 						$.post("logselectall",{countPerPage:$("#countPerPage").val(),page:$("#page").val()},function(data){
 						$("#logTable tr").remove();
 								var iDisplayLength=data.iDisplayLength; 				//每页条数
    							var iTotalRecords=data.iTotalRecords;					//总数据条数
    							var pageCount=data.totalPage;							//页数
    							$("#count").html(iTotalRecords);		//设置数据总数
    							$("#perPageCount").html(iDisplayLength);//设置每页数据数
    							$("#pages").html( pageCount);			//设置总共页数
    							document.getElementById("page").value="1";		//设置 当前页数位置
    							document.getElementById("left").disabled=true;
    							document.getElementById("leftUp").disabled=true;
    							document.getElementById("right").disabled=false;
    							document.getElementById("rightUp").disabled=false;
    							for(var i=0; i <=iDisplayLength; i++)
    							{
          							var temp="<tr>"
          			 + "<td >"+(i+1)+"</td>"
                   + "<td><input type='checkbox' name='same' id='"+data.data[i].logId+"' /></td>"
                    +"<td>"+data.data[i].objName+"</td>"
                     +"<td>"+data.data[i].objId+"</td>"
                    +"<td>"+data.data[i].objType+"</td>"
                    +"<td>"+data.data[i].operType+"</td>"
                    +"<td>"+data.data[i].fieldName+"</td>"
                    +"<td>"+data.data[i].fieldOriValue+"</td>"
                    +"<td>"+data.data[i].fieldUpdValue+"</td>"
                    +"<td>"+data.data[i].userName+"</td>"
                    +"<td>"+data.data[i].operTime+"</td>"
                 	+"</tr>";
        
									$(temp).appendTo($("#logTable"));
									}
 					});
 					}
 					
 			});
 			});
 		function ajaxDisplay( nowPageTemp )
    	{
    		if(obj == 1){
 						$.post("logselect",{countPerPage:$("#countPerPage").val(),page:$("#page").val(),objName:$("#objName").val(),userName:$("#userName").val(),logType:$("#logType").val(),startTime:$("#updTime1").val(),endTime:$("#updTime2").val()},function(data){
								$("#logTable tr").remove();
 								var iDisplayLength=data.iDisplayLength; 				//每页条数
    							var iTotalRecords=data.iTotalRecords;					//总数据条数
    							var pageCount=data.totalPage;							//页数
    							$("#count").html(iTotalRecords);		//设置数据总数
    							$("#perPageCount").html(iDisplayLength);//设置每页数据数
    							$("#pages").html( pageCount);			//设置总共页数
    							for(var i=0; i <=iDisplayLength; i++)
    							{
          						var temp="<tr>"
          			 + "<td >"+(i+1)+"</td>"
                   	+ "<td><input type='checkbox' name='same' id='"+data.data[i].logId+"' /></td>"
                    +"<td>"+data.data[i].objName+"</td>"
                     +"<td>"+data.data[i].objId+"</td>"
                    +"<td>"+data.data[i].objType+"</td>"
                    +"<td>"+data.data[i].operType+"</td>"
                    +"<td>"+data.data[i].fieldName+"</td>"
                    +"<td>"+data.data[i].fieldOriValue+"</td>"
                    +"<td>"+data.data[i].fieldUpdValue+"</td>"
                    +"<td>"+data.data[i].userName+"</td>"
                    +"<td>"+data.data[i].operTime+"</td>"
                 	+"</tr>";
        
									$(temp).appendTo($("#logTable"));
								}
							});
					}
					if(obj == 0){
 					
 						$.post("logselectall",{countPerPage:$("#countPerPage").val(),page:$("#page").val()},function(data){
 						$("#logTable tr").remove();
 								var iDisplayLength=data.iDisplayLength; 				//每页条数
    							var iTotalRecords=data.iTotalRecords;					//总数据条数
    							var pageCount=data.totalPage;							//页数
    							$("#count").html(iTotalRecords);		//设置数据总数
    							$("#perPageCount").html(iDisplayLength);//设置每页数据数
    							$("#pages").html( pageCount);			//设置总共页数
    							for(var i=0; i <=iDisplayLength; i++)
    							{
          							var temp="<tr>"
          			+ "<td >"+(i+1)+"</td>"
                   	+ "<td><input type='checkbox' name='same' id='"+data.data[i].logId+"' /></td>"
                    +"<td>"+data.data[i].objName+"</td>"
                     +"<td>"+data.data[i].objId+"</td>"
                    +"<td>"+data.data[i].objType+"</td>"
                    +"<td>"+data.data[i].operType+"</td>"
                    +"<td>"+data.data[i].fieldName+"</td>"
                    +"<td>"+data.data[i].fieldOriValue+"</td>"
                    +"<td>"+data.data[i].fieldUpdValue+"</td>"
                    +"<td>"+data.data[i].userName+"</td>"
                    +"<td>"+data.data[i].operTime+"</td>"
                 	+"</tr>";
        
									$(temp).appendTo($("#logTable"));
									}
 					});
 					}
 				
    
    
    	}
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
			if(document.getElementById("page"))
				var nowPage=document.getElementById("page");
			else return false;
			
			nowPage.onblur=function(){
				
				if( Number( $("#page").val() ) > Number( $("#pages").html() ) || Number( $("#page").val() ) < 1 || $("#page").val()=="")
				{
    				//alert("请输入合法页数！");
    				
				}
				else{
					if(obj == 1){
 						$.post("logselect",{countPerPage:$("#countPerPage").val(),page:$("#page").val(),objName:$("#objName").val(),userName:$("#userName").val(),logType:$("#logType").val(),startTime:$("#updTime1").val(),endTime:$("#updTime2").val()},function(data){
								$("#logTable tr").remove();
 								var iDisplayLength=data.iDisplayLength; 				//每页条数
    							var iTotalRecords=data.iTotalRecords;					//总数据条数
    							var pageCount=data.totalPage;							//页数
    							$("#count").html(iTotalRecords);		//设置数据总数
    							$("#perPageCount").html(iDisplayLength);//设置每页数据数
    							$("#pages").html( pageCount);			//设置总共页数
    							for(var i=0; i <=iDisplayLength; i++)
    							{
          							var temp="<tr>"
          			 + "<td >"+(i+1)+"</td>"
                   + "<td><input type='checkbox' name='same' id='"+data.data[i].logId+"' /></td>"
                    +"<td>"+data.data[i].objName+"</td>"
                     +"<td>"+data.data[i].objId+"</td>"
                    +"<td>"+data.data[i].objType+"</td>"
                    +"<td>"+data.data[i].operType+"</td>"
                    +"<td>"+data.data[i].fieldName+"</td>"
                    +"<td>"+data.data[i].fieldOriValue+"</td>"
                    +"<td>"+data.data[i].fieldUpdValue+"</td>"
                    +"<td>"+data.data[i].userName+"</td>"
                    +"<td>"+data.data[i].operTime+"</td>"
                 	+"</tr>";
									$(temp).appendTo($("#logTable"));
								}
							});
					}
					if(obj == 0){
 					
 						$.post("logselectall",{countPerPage:$("#countPerPage").val(),page:$("#page").val()},function(data){
 						$("#logTable tr").remove();
 								var iDisplayLength=data.iDisplayLength; 				//每页条数
    							var iTotalRecords=data.iTotalRecords;					//总数据条数
    							var pageCount=data.totalPage;							//页数
    							$("#count").html(iTotalRecords);		//设置数据总数
    							$("#perPageCount").html(iDisplayLength);//设置每页数据数
    							$("#pages").html( pageCount);			//设置总共页数
    							for(var i=0; i <=iDisplayLength; i++)
    							{
          							var temp="<tr>"
          			 + "<td >"+(i+1)+"</td>"
                    + "<td><input type='checkbox' name='same' id='"+data.data[i].logId+"' /></td>"
                    +"<td>"+data.data[i].objName+"</td>"
                     +"<td>"+data.data[i].objId+"</td>"
                    +"<td>"+data.data[i].objType+"</td>"
                    +"<td>"+data.data[i].operType+"</td>"
                    +"<td>"+data.data[i].fieldName+"</td>"
                    +"<td>"+data.data[i].fieldOriValue+"</td>"
                    +"<td>"+data.data[i].fieldUpdValue+"</td>"
                    +"<td>"+data.data[i].userName+"</td>"
                    +"<td>"+data.data[i].operTime+"</td>"
                 	+"</tr>";
        
									$(temp).appendTo($("#logTable"));
									}
 					});
 					};
 					
				
			
					if(Number( $("#page").val() ) > 1)
			    	{
			    		document.getElementById("left").disabled=false;
    					document.getElementById("leftUp").disabled=false;
    				}
    				if(Number( $("#page").val() ) == 1)
    				{
    					document.getElementById("left").disabled=true;
    					document.getElementById("leftUp").disabled=true;
    					document.getElementById("right").disabled=false;
    					document.getElementById("rightUp").disabled=false;
    				}
    				if( Number( $("#page").val() ) == Number( $("#pages").html() ) )
    				{
    					document.getElementById("right").disabled=true;
    					document.getElementById("rightUp").disabled=true;
    				}
    				if(Number( $("#page").val() ) != Number( $("#pages").html() )  )
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
						
							
						if(document.getElementById("page") && Number( $("#page").val() ) > 1 )
						{
							document.getElementById("page").value=Number( $("#page").val() ) - 1;	
							ajaxDisplay(  $("#page").val() );							
						}
						else return false;	
						if(Number( $("#page").val() ) == 1)
						{
							document.getElementById("left").disabled=true;
    						document.getElementById("leftUp").disabled=true;
    						
						}	
						if(Number( $("#page").val() ) == Number( $("#pages").html() ))
						{
							
							document.getElementById("right").disabled=true;
    						document.getElementById("rightUp").disabled=true;
    						
						}	
						if(Number( $("#page").val() ) < Number( $("#pages").html() ) )
						{
							document.getElementById("right").disabled=false;
    						document.getElementById("rightUp").disabled=false;
						}
						if(Number( $("#page").val() ) > 1 )
						{
							document.getElementById("left").disabled=false;
    						document.getElementById("leftUp").disabled=false;
						}
					}
			};
			leftUp.onclick=function()
			{
				if(window.document.getElementById("leftUp").disabled == true)
					alert("当前为数据第一页");
				else 
				{
					if( document.getElementById("page") )
					{
						document.getElementById("page").value = 1;	
						ajaxDisplay( $("#page").val()  );
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
						if(document.getElementById("page") && Number( $("#page").val() ) < Number( $("#pages").html() ) )
						{
							document.getElementById("page").value=Number( $("#page").val() ) + 1;	
							ajaxDisplay( $("#page").val()  );	
						}
						else return false;
						if(Number( $("#page").val() ) == 1)
						{
							document.getElementById("left").disabled=true;
    						document.getElementById("leftUp").disabled=true;
						}	
						if(Number( $("#page").val() ) == Number( $("#pages").html() ))
						{
							document.getElementById("right").disabled=true;
    						document.getElementById("rightUp").disabled=true;  						
						}	
						if(Number( $("#page").val() ) < Number( $("#pages").html() ) )
						{
							document.getElementById("right").disabled=false;
    						document.getElementById("rightUp").disabled=false;
						}
						if(Number( $("#page").val() ) > 1 )
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
					if( document.getElementById("page") )
					{
						document.getElementById("page").value = Number( $("#pages").html() );	
						ajaxDisplay( $("#page").val()  );	
						document.getElementById("right").disabled=true;
    					document.getElementById("rightUp").disabled=true;
    					document.getElementById("left").disabled=false;
    					document.getElementById("leftUp").disabled=false;
					}
				}
			};
			
			
			});
	 			
 			//导出当前页
	$(function(){
		$("#exportNow").bind("click",function(){
			var checkboxs=$("input[name='same']");//所有复选框
		    // alert(checkboxs.length);
		    // alert(checkboxs.eq(4).attr("id"));
		     var json1={};
		     var ids=[];
		     $.each(checkboxs,function(i,val){
		         ids.push(checkboxs.eq(i).attr("id"));
		     });
		    //alert(ids);
		     json1={"logIds":ids};

		     var jsonstr={data:JSON.stringify(json1)};
			   
		     //发送请求
		     $.ajax({
		    	 type:"post",
		    	 url:"exportExcelByConditions.action", //导出全部页的action
		    	 data:jsonstr,
		    	 success:function(data){
		    		 //如果你的文件生成成功，返回的是true，那就会执行下面的if语句
			    	 var json1=JSON.parse(data);
		    		
			    		if(json1!=""){ 		
			    				location.href=FAD+'/Generatefile/'+json1;//单引号内些你生成的文件相对路径，我这是测试
			    		}
			    		else{
			    			confirm("返回失败！");
			    		}
		    	 },
		    	 error:function(){
		    		 confirm("请求失败！");
		    	 }
		     });
		});
	});

  		$(function(){//导出全部页
		$("#exportAll").bind("click",function(){
  				$.post("exportalllogexcelbyconditions",{objName:$("#objName").val(),userName:$("#userName").val(),logType:$("#logType").val(),
  				startTime:$("#updTime1").val(),endTime:$("#updTime2").val()},function(data){
  					 var json1=JSON.parse(data);
			    		if(json1!=""){ 		
			    				location.href=FAD+'/Generatefile/'+json1;//单引号内些你生成的文件相对路径，我这是测试
			    		}
			    		else{
			    			confirm("返回失败！");
			    		}
  				});
  			});
  			
 		});
 		
 