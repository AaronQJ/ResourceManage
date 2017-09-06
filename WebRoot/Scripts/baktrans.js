 //添加
        $(function(){
			     $("#sive").bind("click",function(){
			     
				    var notNullInputs=$("#addForm>.notNullInput");
				    var temp=0, state=0;
				   // alert(notNullInputs.length);
				    $.each(notNullInputs,function(i,val){//对每个不能为空的input做判断
				      if(notNullInputs.eq(i).val()=="")
				      {
				    	  notNullInputs.eq(i).addClass("empty_input");
				    	  temp=1;
				      }
				      else
				      {
				    	  notNullInputs.eq(i).removeClass("empty_input");
				    	  
				      }
			        });
			       
			        if($("#bakState").val()==""){
			           state=1;
			        }
			        
			        if(temp==1){
			           alert("带 * 的为必填项，请填写完整！");
			           $(".empty_input:first").focus();//光标定位到第一个空的input
			           return false;
			        }
			        if(state==1){
			           alert("请选择资产状态 ！");
			           return false;
			        }
			        
			     
			        $.post("probak/bakAction_checkSeqNum.action",{bakSeqNum:$("#bakSeqNum").val()},function(data){
					       	if(data=="true"){
					       		$("#bakSeqNum").focus();
					       		alert("序列号不唯一！");
					        	}
					        else{
							        $.post("probak/bakAction_add.action",
							               $("#addForm").serialize(),
							               function(data){
								        	 if(data){
								        		
							                      alert("添加成功!");
							                      $("#addBox").css("display","none");
							                      deleteGlobalShade();
							                      window.location.reload();
							                    }
							                   
							                  else{
							                      alert("添加失败！");
							   
							                      $("#addBox").css("display","none");
							                      deleteGlobalShade();
							                      window.location.reload();
							                    }
							                });
			        
					        	}
					        });
    		});
    		
    	});
      //批量修改弹出框	
		$(function(){
			 $("#change").bind("click",function(){
				 $.ajax({
			         type:"post", 
			         url:"usersAction_check_ProUpd_power",
			         success:function(data){
			         	if(data == "noLimit"){
			         		alert("没有权限！请您联系超级管理员");
			         		right.src="noPower.jsp";
			         	    //window.location.href="noPower.jsp";
			         	}else{
			         		 var checkboxs=$("input[name='same']");//所有复选框
					    	  var ids=[];
							  $.each(checkboxs,function(i,val){
							       if(checkboxs.eq(i).is(":checked")){//判断是否被选中
							          ids.push(checkboxs.eq(i).attr("id"));
							       }
							  });
							  if(ids.length==0){//判断是否选择
							       confirm("请选择要修改的资产！");
							       return false;
							   }
							  else{
							    	 globalShade();
							    	 $("#changeBox").css("display","block");
							    	 $("#changeCloseBox").bind("click",function(){
							    		 if(window.confirm('您的数据尚未保存,您确定要退出吗？')){
							    			$("#changeBox").css("display","none"); 
							    				deleteGlobalShade();
							    				$("#checkbox").attr("checked",false);
							    				window.location.reload();
							    		 }
							    	 });
							    }
							  
			         	}
				
				}
				});
				 
		    	 
		      });
		     
		});
        
    	//批量修改
    	$(function(){
		   $("#sureToChange").bind("click",function(){
		     var checkboxs=$("input[name='same']");//所有复选框
		     var json1={};
		     var ids=[];
		     $.each(checkboxs,function(i,val){
		       if(checkboxs.eq(i).is(":checked")){//判断是否被选中
		          ids.push(checkboxs.eq(i).attr("id"));
		       }
		     });
		     
		     json1={"bakIds":ids,"bakState":$("#changeBakState option:selected").text(),
		         "bakUser":$("#changeBakUser").val(),
		         "bakDevRoom":$("#changeBakDevRoom").val(),
		         "bakDevFrame":$("#changeBakDevFrame").val()};

		     var jsonstr={data:JSON.stringify(json1)};
		     //发送请求
		     $.ajax({
		    	 type:"post",
		    	 url:"probak/bakAction_updateMore.action",
		    	
		    	 data:jsonstr,
		    	 success:function(data){
		    		 var json1=JSON.parse(data);
		    		 if(json1=="true"){
		    			 alert("修改成功！");
		    			 deleteGlobalShade();
		    			 $("#changeBox").css('display','none');
		    			 $("#checkbox").attr("checked",false);
		    			 window.location.reload();
			    			
			    	 }
			    	else{
			    		alert("修改失败！");
			    		deleteGlobalShade();
			    		$("#changeBox").css('display','none');
			    		$("#checkbox").attr("checked",false);
			    		window.location.reload();
			    	}
		    	 },
		    	 error:function(){
		    		 window.parent.document.getElementById('right').src="false.jsp";

		    	 }
		     });
		    
		     
		   });
		});
      
    	//批量删除/单条删除
		$(function(){
			   $("#okBtn").bind("click",function(){
			     var checkboxs=$("input[name='same']");//所有复选框
			     var json1={};
			     var ids=[];
			     $.each(checkboxs,function(i,val){
			       if(checkboxs.eq(i).is(":checked")){//判断是否被选中
			          ids.push(checkboxs.eq(i).attr("id"));
			       }
			     });
			    
			     if(ids.length==0){//判断是否选择
			       confirm("请选择要删除的资产！");
			       return false;
			     }
			     json1={"bakIds":ids};

			     var jsonstr={data:JSON.stringify(json1)};
			     //发送请求
			     $.ajax({
			    	 type:"post",
			    	 url:"probak/bakAction_deleteMore.action",
			    	 data:jsonstr,
			    	 success:function(data){
			    		 if(data){
				    			alert("共删除 "+ids.length+" 条资产信息！");
				    			deleteGlobalShade();
			    				$("#deleteOne").css('display','none');
			    				$("#checkbox").attr("checked",false);
			    				window.location.reload();
				    			
				    		}
				    	 else{
				    			alert("删除失败！");
				    			deleteGlobalShade();
			    				$("#deleteOne").css('display','none');
			    				$("#checkbox").attr("checked",false);
			    				window.location.reload();
				    		}
			    	 },
			    	 error:function(){
			    		 window.parent.document.getElementById('right').src="false.jsp";

			    	 }
			     });
			    
			   });
			});
		
	//日期格式转化
		var formatDate = function (date) {  
		    var y = date.getFullYear();  
		    var m = date.getMonth() + 1;  
		    m = m < 10 ? '0' + m : m;  
		    var d = date.getDate();  
		    d = d < 10 ? ('0' + d) : d;  
		    return y + '-' + m + '-' + d;  
		};  
	//详细信息	
	$(function(){
		$("#detail").bind("click",function(){
			 var checkboxs=$("input[name='same']");//所有复选框
		     var ids=[];
		     $.each(checkboxs,function(i,val){
		       if(checkboxs.eq(i).is(":checked")){//判断是否被选中
		          ids.push(checkboxs.eq(i).attr("id"));
		       }
		     });
		     $.ajax({
		    	 type:"post",
		         url:"probak/bakAction_findOne",
		    	 data:{
		    	     "bakId":ids[0]	 
		    	 },
		    	 success:function(data){
		    		
		    		 var json1=JSON.parse(data);
		    		 $("#detailbakSeqNum").val(json1.bakSeqNum);
		    		 $("#detailbakVersion").val(json1.bakVersion);
		    		 $("#detailbakState").val(json1.bakState);
		    		 $("#detailbakForm").val(json1.bakForm);
		    		 
		    		 $("#detailbakDevType").val(json1.bakDevType);
		    		 $("#detailbakUser").val(json1.bakUser);
		    		 $("#detailbakUseDepart").val(json1.bakUseDepart);
		    		 $("#detailbakFactory").val(json1.bakFactory);
		    		 
		    		 $("#detailbakBuyCont").val(json1.bakBuyCont);
		    		 $("#detailbakMaintainCont").val(json1.bakMaintainCont);
		    		 $("#detailbakMaintainFactor").val(json1.bakMaintainFactor);
		    		 var date=new Date(json1.bakArrAcceptTime);
		    		 $("#detailbakArrAcceptTime").val(formatDate(date));
		    		 
		    		 var reg=/\d+/;
			    	 if(reg.test(json1.bakMaintainDeadLine)){
			    		 var date1=new Date(json1.bakMaintainDeadLine);
			    		 $("#detailbakMaintainDeadLine").val(formatDate(date1));
			    	 }
			    	 else{
			    		 $("#detailbakMaintainDeadLine").val("");
			    	 }
		    		
		    		 $("#detailbakDevRoom").val(json1.bakDevRoom);
		    		 $("#detailbakDevFrame").val(json1.bakDevFrame);
		    		 
		    		 
		    		 $("#detailbakManaDepart").val(json1.bakManaDepart);
		    		 $("#detailbakRecorder").val(json1.bakRecorder);
		    		 $("#detailbakRemark").val(json1.bakRemark);
		    		 
		    		 
		    	 },
		    	 error:function(){
		    		 window.parent.document.getElementById('right').src="false.jsp";

		    	 }
		    	 
		     });
		});
	});
	
	
	//修改单条
	$(function(){
		$("#changeInfo").bind("click",function(){
			 var checkboxs=$("input[name='same']");//所有复选框
		     var ids=[];
		     $.each(checkboxs,function(i,val){
		       if(checkboxs.eq(i).is(":checked")){//判断是否被选中
		          ids.push(checkboxs.eq(i).attr("id"));
		       }
		     });
		     $.ajax({
		    	 type:"post",
		         url:"probak/bakAction_findOne",
		    	 data:{
		    	     "bakId":ids[0]	 
		    	 },
		    	 success:function(data){
		    		
		    		 var json1=JSON.parse(data);
		    		 $("#bakId2").val(json1.bakId);
		    		 $("#bakSeqNum2").val(json1.bakSeqNum);
	                 $("#bakVersion2").val(json1.bakVersion);
		    		 
		    		 $("#bakFactory2").val(json1.bakFactory);
		    		 $("#bakDevType2").val(json1.bakDevType);
		    		 $("#bakForm2").val(json1.bakForm);
		    		
		    		 $("#bakState2").val(json1.bakState);
		    		
		    		 
		    		 $("#bakManaDepart2").val(json1.bakManaDepart);
		    		 $("#bakUseDepart2").val(json1.bakUseDepart);
		    		 $("#bakUser2").val(json1.bakUser);
		    		 $("#bakDevRoom2").val(json1.bakDevRoom);
		    		 $("#bakDevFrame2").val(json1.bakDevFrame);
		    		 var date=new Date(json1.bakArrAcceptTime);
		    		 $("#bakArrAcceptTime2").val(formatDate(date));
		    		 $("#bakRecorder2").val(json1.bakRecorder);
		    		 $("#bakBuyCont2").val(json1.bakBuyCont);
		    		 
		    		 $("#bakMaintainCont2").val(json1.bakMaintainCont);
		    		 $("#bakMaintainFactor2").val(json1.bakMaintainFactor);
		    		 
		    		
		    		 var reg=/\d+/;
			    	 if(reg.test(json1.bakMaintainDeadLine)){
			    		 var date1=new Date(json1.bakMaintainDeadLine);
				    	 $("#bakMaintainDeadLine2").val(formatDate(date1));
			    	 }
			    	 else{
			    		 $("#bakMaintainDeadLine2").val("");
			    	 }
		    		
		    		 $("#bakRemark2").val(json1.bakRemark);
		    		
		    	 },
		    	 error:function(){
		    		 window.parent.document.getElementById('right').src="false.jsp";

		    	 }
		    	 
		     });
		     
		});
		$("#changeOk").bind("click",function(){
			 var temp=0,state=0;
			 var notNulls=$("#changeForm>.notNullInput");
		     $.each(notNulls,function(i,val){//对每个不能为空的input做判断
			      if(notNulls.eq(i).val()=="")
			      {
			    	  notNulls.eq(i).addClass("empty_input");	  
			    	  temp=1;
			      }
			      else
			      {
			    	  notNulls.eq(i).removeClass("empty_input");
			    	  
			      }
		        });
		     
		       if($("#bakState2").val()==""){
		           state=1;
		        }
		        
		        if(temp==1){
		           alert("带 * 的为必填项，请填写完整！");
		           $(".empty_input:first").focus();//光标定位到第一个空的input
		           return false;
		        }
		        if(state==1){
		           alert("请选择资产状态 ！");
		           return false;
		        }
		        
			    
			$.ajax({
				 type:"post",
				 url:"probak/bakAction_updateOne",
				 data: $("#changeForm").serialize(),
				 success:function(data){
					var json1=JSON.parse(data);
					if(json1=="true"){
						alert("修改成功！");
						deleteGlobalShade();
						window.location.reload();
					}
					else{
						alert("修改失败！");
						deleteGlobalShade();
						window.location.reload();
					}
				 },
				 error:function(){
					 window.parent.document.getElementById('right').src="false.jsp";

				 }
				 
			  });
	    });
	 
		
	});

	//生命周期
	$(function(){
		$("#deadline").bind("click",function(){
			
			 var checkboxs=$("input[name='same']");//所有复选框
		     var ids=[];
		     $.each(checkboxs,function(i,val){
		       if(checkboxs.eq(i).is(":checked")){//判断是否被选中
		          ids.push(checkboxs.eq(i).attr("id"));
		       }
		     });
		     $.ajax({
		    	 type:"post",
		         url:"ProBakLifeTime.action",
		    	 data:{
		    	     "bakId":ids[0]	 
		    	 },
		    	 success:function(data){
		    		
			    		var spans=$(".deadlineCon>span");
			    		spans.eq(0).text(data[0].bakSeqNum);
			    		spans.eq(1).text(data[0].bakVersion);
			    	
			    		var tds;
			    		if(!data[1]){
			    			tds="<tr>"+"<td>"+data[0].bakState+"</td>"
			    			+"<td>"+data[0].bakUpdTime+"</td>"
			    			+"<td>"+data[0].loginUserName+"</td>"+"</tr>";
	    			$(tds).appendTo($("#deadlineTbody"));	         
			    		}else{
			    			for(var i=1;i<data.length;i++){
			    				tds="<tr>"+"<td>"+data[i].fieldUpdValue+"</td>"
			    				+"<td>"+data[i].operTime+"</td>"
			    				+"<td>"+data[i].userName+"</td>"+"</tr>";
			    				$(tds).appendTo($("#deadlineTbody"));		         
			    			}
			    		}
			    	
			    	    
		    		 
		    	 },
		    	 error:function(){
		    		 window.parent.document.getElementById('right').src="false.jsp";

		    		 
		    	 }
		    	 
		     });
			
		});	

	});
	
	//导入
	$(function (){
		$("#sureToImport").bind("click",function(){
			var sourceStr=$("#fileAddress").val();
			if(sourceStr==""){
				alert("请选择文件后再进行导入！");
				$("#importBox").css("display","none");
				deleteGlobalShade();
				window.location.reload();
			}else {
				var FileListType="xls,XLS,xlsx,XLSX,xlt,XLT,xlsm,XLSM";
				var destStr = sourceStr.substring(sourceStr.lastIndexOf(".")+1,sourceStr.length);
				if(FileListType.indexOf(destStr) == -1)
				{
				alert("只允许上传excl表格文件");
				$("#importBox").css("display","none");
				deleteGlobalShade();
				window.location.reload();
			//	return false;
			}else{		
			var formData = new FormData($( "#importForm" )[0]);  
			$.ajax({
				url: "probak/bakAction_importBak.action" ,  
				type: "POST",  
				data: formData,
		//		dataType: "json",
				async: false,  
				cache: false,  
				contentType: false,  
				processData: false,  
				success: function (data) {
					var json1=JSON.parse(data);
					//json1[0]数据表是否正确
					//json1[1]是否重复
					//json1[2]应导入数据
					//json1[3]成功导入数据
				//	alert(json1[0]+"...."+json1[1]+",,,,,"+json1[2]);			//分别是重复数，应导入条数，成功条数
					if(json1[0] == 0){		//导入数据表正确
						if((json1[1] == 0) && (json1[2]==json1[3])){					//没有重复，且应导入和成功条数一样
						alert("本次应导入"+json1[2]+"条数据，已成功导入"+json1[3]+"条数据！");
						$("#importBox").css("display","none");
						deleteGlobalShade();
						window.location.reload();
					}else {
						if(confirm("本次应导入"+json1[2]+"条数据，已成功导入"+json1[3]+"条数据！"+"您是否要导出未导入成功数据？")){
							$("#importBox").css("display","none");
							deleteGlobalShade();
							window.open(FAD+'/Generatefile/DataError.xls');
						}
					}
					}else {
						alert("请检查导入数据表是否正确！");
						$("#importBox").css("display","none");
						deleteGlobalShade();
						window.location.reload();
					}
				},  
				error: function (data) {  
					alert("导入失败!!!");
					$("#importBox").css("display","none");
					deleteGlobalShade();
					window.location.reload();
				}  
			}).done(function(res) {
			}).fail(function(res) {});  
			}
			}
			});

		});
		
	
	
    	