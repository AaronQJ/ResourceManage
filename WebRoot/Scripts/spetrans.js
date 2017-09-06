			function isValidIP(ip)   
			{   
			    var reg =/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;  
			    return reg.test(ip);   
			}  
			//添加
			$(function(){
			     $("#sive").bind("click",function(){
			     
				    var notNullInputs=$("#addForm>.notNullInput");
				    var temp=0, state=0;
				    var masterIP=$("#speMasterIP").val();
				    var slaveIP=$("#speSlaveIP").val();
				    var master=0,slave=0;
				    if(masterIP!=""){
				      if(!isValidIP(masterIP)){
				        master=1;
				      }
				    }
				    if(slaveIP!=""){
				      if(!isValidIP(slaveIP)){
				        slave=1;
				      }  
				    }
				    
				    
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
				    
			        
			        if($("#speState").val()==""){
			          state=1;
			        }
			        
			        //验证
			        if(temp==1){
			           alert("带  * 的为必填项，请填写完整！");		
			           $(".empty_input:first").focus();//光标定位到第一个空的input
			           return false;
			        }
			        if(state==1){
			           alert("请选择资产状态！");
			           return false;
			        }
			        if(master==1){
			           alert("主IP地址错误！");
			           $("#speMasterIP").focus();
			           return false;
			        }
			        if(slave==1){
			           alert("普通IP地址错误！");
			           $("#speSlaveIP").focus();
			           return false;
			        }
			       
			      // $("#sive").val("提交中……");
				   $.post("spepro/speAction_checkSeqNum.action",{speSeqNum:$("#speSeqNum").val()},function(data){
					       	if(data=="true"){
					       		alert("序列号不唯一！");
					        	}
					        	else{
					        		
					        		 $.post("spepro/speAction_add.action",
								                $("#addForm").serialize(),
								                function(data){
								                  if(data){
								                	//  $("#sive").val("提交");
								                      alert("添加成功!");
								                      $("#addBox").css("display","none");
								                      deleteGlobalShade();
								                      window.location.reload();
								                    }
								                   
								                  else{
								                      alert("添加失败！");
								                     // $("#sive").val("提交");
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
				         		$("#addBox").css("display","none");
				         		alert("没有权限！请您联系超级管理员");
				         		deleteGlobalShade();}else{
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
				     
				     
				     json1={"speIds":ids,"speState":$("#changeSpeState option:selected").text(),
				    		 "speUser":$("#changeSpeUser").val(),"speDevRoom":$("#changeSpeDevRoom").val(),
				    		 "speDevRoomFrame":$("#changeSpeDevRoomFrame").val()};
	
				     var jsonstr={data:JSON.stringify(json1)};
				     //发送请求
			
				    $.ajax({
				    	type:"post",
				    	url:"spepro/speAction_updateMore.action",
				    	data:jsonstr,
				    	success:function(data){
				    		var json1=JSON.parse(data)
				    		if(json1== "true"){
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
				

		
		//批量删除,单条删除
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
			     json1={"speIds":ids};

			     var jsonstr={data:JSON.stringify(json1)};
			     //发送请求
			     $.ajax({
			    	 type:"post",
			    	 url:"spepro/speAction_deleteMore",
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
		         url:"spepro/speAction_findOne",
		    	 data:{
		    	     "speId":ids[0]	 
		    	 },
		    	 success:function(data){
		    		
		    		 var json1=JSON.parse(data);
		    		 $("#detailspeBarCode").val(json1.speBarCode);
		    		 $("#detailspeState").val(json1.speState);
		    		 $("#detailspeVersion").val(json1.speVersion);
		    		 $("#detailspeUser").val(json1.speUser);
		    		 $("#detailspeDevFactor").val(json1.speDevFactor);
		    		 $("#detailspeDevType").val(json1.speDevType);
		    		 $("#detailspeUseDepart").val(json1.speUseDepart);
		    		 $("#detailspeBuyCont").val(json1.speBuyCont);
		    		 $("#detailspeMaintainCont").val(json1.speMaintainCont);
		    		 $("#detailspeMaintainFactor").val(json1.speMaintainFactor);
		    		 $("#detailspeSeqNum").val(json1.speSeqNum);
		    		 $("#detailspeDevName").val(json1.speDevName);
		    		 $("#detailspeOwnSystem").val(json1.speOwnSystem);
		    		 $("#detailspeProOwn").val(json1.speProOwn);
		    		 $("#detailspeDevRoom").val(json1.speDevRoom);
		    		 
		    		 $("#detailspeDevRoomFrame").val(json1.speDevRoomFrame);
		    		 $("#detailspeOwnNet").val(json1.speOwnNet);
		    		 $("#detailspeMasterIP").val(json1.speMasterIP);
		    		 $("#detailspeSlaveIP").val(json1.speSlaveIP);
		    		 
		    		 $("#detailspeProject").val(json1.speProject);
		    		 $("#detailspeAssertsNum").val(json1.speAssertsNum);
		    		 $("#detailspeManaDepart").val(json1.speManaDepart);
		    		 $("#detailspeAssetsManaDepart").val(json1.speAssetsManaDepart);
		    		 
		    		 $("#detailspeAssState").val(json1.speAssState);
		    		 $("#detailspeFixedTime").val(json1.speFixedTime);
		    		 $("#detailspeScrapTime").val(json1.speScrapTime);
		    		 
		    		 
		    		 var date=new Date(json1.speArrAcceptTime);
		    		 $("#detailspeArrAcceptTime").val(formatDate(date));
		    		 
		    		 var dateDeadLine=new Date(json1.speMaintainDeadLine);
		    		 $("#detailspeMaintainDeadLine").val(formatDate(dateDeadLine));
		    		 
		    		 /*不知道为啥要这样写，所以我注释了重写后无验证
		    		  * var reg=/\d+/;
		    		 if(reg.test(json1.speMaintainDeadLine)){//维保截止时间
		    			 var date1=new Date(json1.speMaintainDeadLine);
		    			 $("#detailspeMaintainDeadLine").text(formatDate(date1));
		    		 }
		    		 else{
		    			 $("#detailspeMaintainDeadLine").val("");
		    		 }*/
		    		 
		    		 
		    		 $("#detailspeRemark").val(json1.speRemark);
		    		 
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
		         url:"spepro/speAction_findOne",
		    	 data:{
		    	     "speId":ids[0]	 
		    	 },
		    	 success:function(data){
		    		 var json1=JSON.parse(data);
		    		 $("#speId2").val(json1.speId);
		    		 $("#speDevName2").val(json1.speDevName);
		    		 $("#speDevRoom2").val(json1.speDevRoom);
		    		 $("#speSeqNum2").val(json1.speSeqNum);
		    		 $("#speDevRoomFrame2").val(json1.speDevRoomFrame);
		    		 
		    		 $("#speState2").val(json1.speState);
		    		
		    		 
		    		 $("#speUser2").val(json1.speUser);
		    		 $("#speOwnSystem2").val(json1.speOwnSystem);
		    		 $("#speProOwn2").val(json1.speProOwn);
		    		 $("#speAssetsManaDepart2").val(json1.speAssetsManaDepart);
		    		 
		    		 $("#speDevFactor2").val(json1.speDevFactor);
		    		 $("#speVersion2").val(json1.speVersion);
		    		 $("#speDevType2").val(json1.speDevType);
		    		 
		    		 $("#speManaDepart2").val(json1.speManaDepart);
		    		 $("#speUseDepart2").val(json1.speUseDepart);
		    		 
		    		 $("#speOwnNet2").val(json1.speOwnNet);
		    		 $("#speProject2").val(json1.speProject);
		    		 
		    		 $("#speMasterIP2").val(json1.speMasterIP);
		    		 $("#speSlaveIP2").val(json1.speSlaveIP); 
		    		 $("#speBuyCont2").val(json1.speBuyCont);
		    		 
		    		 $("#speMaintainCont2").val(json1.speMaintainCont);
		    		 $("#speMaintainFactor2").val(json1.speMaintainFactor);
		    		 
		    		 
		    		 $("#speAssState2").val(json1.speAssState);
		    		 $("#speFixedTime2").val(json1.speFixedTime);
		    		 $("#speScrapTime2").val(json1.speScrapTime);
		    		 
		    		 var reg=/\d+/;
		    		 if(reg.test(json1.speMaintainDeadLine)){//到货验收时间不为空
		    			 var date1=new Date(json1.speMaintainDeadLine);
			    		 $("#speMaintainDeadLine2").val(formatDate(date1));
		    		 }
		    		 else{
		    			 $("#speMaintainDeadLine2").val("");//到货验收时间为空
		    		 }
		    		 
		    		 var date=new Date(json1.speArrAcceptTime);
		    		 $("#speArrAcceptTime2").val(formatDate(date));
		    		 $("#speAssertsNum2").val(json1.speAssertsNum);
		    		 $("#speRemark2").val(json1.speRemark);
		    		 
		    	 },
		    	 error:function(){
		    		 window.parent.document.getElementById('right').src="false.jsp";

		    	 }
		    	 
		     });
			
		});
		$("#changeOk").bind("click",function(){
		     var masterIP=$("#speMasterIP2").val();
		     var slaveIP=$("#speSlaveIP2").val();
		     var master=0,slave=0,temp=0,state=0;
		     
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
			    
		        
		        if($("#speState2").val()==""){
		          state=1;
		        }
		        
		        //验证
		        if(temp==1){
		           alert("带  * 的为必填项，请填写完整！");		
		           $(".empty_input:first").focus();//光标定位到第一个空的input
		           return false;
		        }
		        if(state==1){
		           alert("请选择资产状态！");
		           return false;
		        }
		     
		     if(masterIP!=""&&!(isValidIP(masterIP))){//IP地址验证
		        alert("主IP地址输入错误!");
		        $("#speMasterIP2").focus();
		        return false;
		     }
		     if(slaveIP!=""&&(!isValidIP(slaveIP))){
		        alert("普通IP地址错误!");
		        $("#speSlaveIP2").focus();
		        return false;
		     }
		     
		     $.ajax({
				 type:"post",
				 url:"spepro/speAction_updateOne",
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
		         url:"SpeProLifeTime",
		    	 data:{
		    	     "speId":ids[0]	 
		    	 },
		    	 success:function(data){
			    		var spans=$(".deadlineCon>span");
			    		spans.eq(0).text(data[0].speDevName);
			    		spans.eq(1).text(data[0].speBarCode);
			    		spans.eq(2).text(data[0].speSeqNum);
			    		var tds;
			    		if(!data[1]){
			    			tds="<tr>"+"<td>"+data[0].speState+"</td>"
			    			+"<td>"+data[0].speUpdTime+"</td>"
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
		//	alert($("#fileAddress")[0].files[0]);
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
					url: "spepro/speAction_importSpe.action" ,  
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
						alert("导入失败!!!"+data); 
						$("#importBox").css("display","none");
						deleteGlobalShade();
						window.location.reload();
					}  
				});  
			}
			}
			});

		});
		
	
	//生成条码
	$(function(){
		$("#getBarcode").bind("click",function(){
			 var checkboxs=$("input[name='same']");//所有复选框
		     var ids=[];
		     $.each(checkboxs,function(i,val){
		       if(checkboxs.eq(i).is(":checked")){//判断是否被选中
		          ids.push(checkboxs.eq(i).attr("id"));
		       }
		     });
		   
		     $.ajax({
		    	 type:"post",
		         url:"spepro/speAction_findBarcode",
		       //  async:true,
		    	 data:{
		    	     "speId":ids[0]	 
		    	 },
		    	 success:function(data){
		    		 var json1=JSON.parse(data);
		    		 
		    		 var path=FAD+'/barcodeImages/'+json1+'.jpg';
		    		 $("#printImg").attr("src",path);
		    		 
		    		
		    	 },
		    	 error:function(){
		    		 window.parent.document.getElementById('right').src="false.jsp";

		    	 }
		    	 
		     });
		     
		});
	});
	