  	    //添加
	    $(function(){
			     $("#sive").bind("click",function(){
				    var notNullInputs=$("#addForm>.notNullInput");
				    var temp=0, state=0,price=0,lifeTime=0;
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
			       
			        if($("#offState").val()==""){
			           state=1;
			        }
			        
			        var offPrice=$("#offPrice").val();//验证价格
			        if(offPrice!=""){
			          if(!(/(^\d{1,8}$)|(^\d{1,8}\.\d{2}$)/.test(offPrice))){
			            price=1;
			          }
			        }
			        var offLifeTime=$("#offLifeTime").val();//验证价格
			        if(offLifeTime!=""){
			          if(!(/(^\d{1,8}$)|(^\d{1,8}\$)/.test(offLifeTime))){
			        	  lifeTime=1;
			          }
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
			        if(price==1){
			        	$("#offPrice").focus();
			           alert("资产价格输入错误！");
			           return false;
			        }
			        if(lifeTime==1){
			        	$("#offLifeTime").focus();
			           alert("使用年限输入错误！");
			           return false;
			        }
			       
			       $.post("offpro/offAction_checkSeqNum",{offSeqNum:$("#offSeqNum").val()},function(data){
					       	if(data=="true"){
					       		alert("序列号不唯一！");
					        	}
					        else{
							         
						       $.post("offpro/offAction_add",
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
		     //alert(checkboxs.length);
		     var json1={};
		     var ids=[];
		     $.each(checkboxs,function(i,val){
		       if(checkboxs.eq(i).is(":checked")){//判断是否被选中
		          ids.push(checkboxs.eq(i).attr("id"));
		       }
		     });
		    
		     json1.resourceId=ids;
		     json1.offState=$("#changeOffState option:selected").text();
		     json1.offUser=$("#changeOffUser").val();
		     json1.offManager=$("#changeOffManage").val();
		     json1.offUseDepart=$("#changeOffUseDepart").val();
		     
		     json1={"offIds":ids,"offState":$("#changeOffState option:selected").text(),
		    		 "offUser":$("#changeOffUser").val(),"offManager":$("#changeOffManage").val(),
		    		 "offUseDepart":$("#changeOffUseDepart").val()};

		     var jsonstr={data:JSON.stringify(json1)};
		     //发送请求
		     $.ajax({
		    	 type:"post",
		    	 url:"offpro/offAction_updateMore",
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
   
    	//批量删除
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
			     json1={"offIds":ids};

			     var jsonstr={data:JSON.stringify(json1)};
			     //发送请求
			     $.ajax({
			    	 type:"post",
			    	 url:"offpro/offAction_deleteMore",
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
		         url:"offpro/offAction_findOne",
		    	 data:{
		    	     "offId":ids[0]	 
		    	 },
		    	 success:function(data){
		    		 var json1=JSON.parse(data);
		    		 $("#detailoffNum").val(json1.offNum);
		    		 $("#detailoffDevVersion").val(json1.offDevVersion);
		    		 $("#detailoffUseState").val(json1.offUseState);
		    		 var date=new Date(json1.offObtDate);
		    		 $("#detailoffObtDate").val(formatDate(date));
		    		 $("#detailoffSeqNum").val(json1.offSeqNum);
		    		 $("#detailoffUseDepart").val(json1.offUseDepart);
		    		 $("#detailoffUser").val(json1.offUser);
		    		 $("#detailoffManager").val(json1.offManager);
		    		 $("#detailoffState").val(json1.offState);
		    		 $("#detailoffName").val(json1.offName);
		    		 $("#detailoffPrice").val(json1.offPrice);
		    		 $("#detailoffLifeTime").val(json1.offLifeTime);//使用年限
		    		 $("#detailoffRemark").val(json1.offRemark);
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
		         url:"offpro/offAction_findOne",
		    	 data:{
		    	     "offId":ids[0]	 
		    	 },
		    	 success:function(data){
		    		
		    		 var json1=JSON.parse(data);
		    		 //alert(json1.offName);
		    		 $("#offId2").val(json1.offId);
		    		 $("#offNum2").val(json1.offNum);
	                 $("#offName2").val(json1.offName);
		    		 
		    		 $("#offDevVersion2").val(json1.offDevVersion);
		    		 var date=new Date(json1.offObtDate);
		    		 $("#offObtDate2").val(formatDate(date));
		    		 $("#offUseDepart2").val(json1.offUseDepart);
		    		 $("#offState2").val(json1.offState);
		    		 
		    		 $("#offUseState2").val(json1.offUseState);
		    		 $("#offManager2").val(json1.offManager);
		    		 $("#offUser2").val(json1.offUser);
		    		 $("#offSeqNum2").val(json1.offSeqNum);
		    		 $("#offPrice2").val(json1.offPrice);
		    		 $("#offLifeTime2").val(json1.offLifeTime);
		    		 $("#offRemark2").val(json1.offRemark);
		    		
		    	 },
		    	 error:function(){
		    		 window.parent.document.getElementById('right').src="false.jsp";

		    	 }
		    	 
		     });
		     
		});
		$("#changeOk").bind("click",function(){
		     var price=0,state=0,temp=0, lifeTime=0;
		     var offPrice=$("#offPrice2").val();//验证价格
		     var offLifeTime=$("#offLifeTime2").val();//验证使用年限
		     
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
			    //alert(temp);
		        
		        if($("#offState2").val()==""){
		          state=1;
		        }
		        
		        if(offPrice!=""){
				    if(!(/(^\d{1,8}$)|(^\d{1,8}\.\d{2}$)/.test(offPrice))){
				       price=1;
				    }
				 }
		        if(offLifeTime!=""){
				    if(!(/(^\d{1,8}$)|(^\d{1,8}\$)/.test(offLifeTime))){
				    	lifeTime=1;
				    }
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
			
		     if(price==1){
			     alert("资产价格输入错误！");
			     $("#offPrice2").focus();
			     return false;
			 }
		     if(lifeTime==1){
			     alert("使用年限输入错误！");
			     $("#offLifeTime2").focus();
			     return false;
			 }
		     $.ajax({
				 type:"post",
				 url:"offpro/offAction_updateOne",
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
		         url:"OffProLifeTime",
		    	 data:{
		    	     "offId":ids[0]	 
		    	 },
		    	 success:function(data){
		    		//alert(data.length);
			    		var spans=$(".deadlineCon>span");
			    		spans.eq(0).text(data[0].offNum);
			    		spans.eq(1).text(data[0].offName);
			    		spans.eq(2).text(data[0].offDevVersion);
			    	
			    		var tds;
			    		if(!data[1]){
			    			tds="<tr>"+"<td>"+data[0].offUseState+"</td>"
			    			+"<td>"+data[0].offUpdTime+"</td>"
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
		//	alert($("#fileAddress").val());
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
				url: "offpro/offAction_importOff" ,  
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
					}
				},  
				error: function(data) {  
					alert("导入失败!!!");
					$("#importBox").css("display","none");
					deleteGlobalShade();
					
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
		         url:"offpro/offAction_findBarcode",
		    	 data:{
		    	     "offId":ids[0]	 
		    	 },
		    	 success:function(data){
		    		 
		    		 var json1=JSON.parse(data);
		    		 
		    		 var path=FAD+'/barcodeImages/'+json1+'.jpg';
		    		 $("#printImg").attr("src",path);
	
		    	 },
		    	 error:function(){
		    		 alert("请求失败！");
		    	 }
		    	 
		     });
		     
		});
	});

	
	