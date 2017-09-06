 $(function(){
			     $("#sive").bind("click",function(){
			     
				    var notNullInputs=$("#addForm>.notNullInput");
				    var temp=0, state=0;
				    var lentemp=0,bindwidtemp=0; 
				    
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
			        
			        var bindWidth=$("#lineBandWidth").val();//验证带宽是否为整数
			        if(bindWidth!=""){
			          if(!(parseInt(bindWidth)==bindWidth)){
			             bindwidtemp=1;
			          }
			        }
			        
			        
			        var len=$("#lineLength").val();//验证长度是否合法
			        if(len!=""){
			          if(!(/^[1-9]\d*$/.test(len))){
			            lentemp=1;
			          }
			        }
			        
			       //判断状态
			        if($("#lineLocalRemote").val()=="" || $("#lineState").val()==""){
			           state=1;
			        }
			        if(temp==1){
			           alert("带 * 的为必填项，请填写完整！");
			           $(".empty_input:first").focus();//光标定位到第一个空的input
			           return false;
			        }
			        if(state==1){
			           alert("请选则本地/长途,线路状态 ！");
			           return false;
			        }
			        if(bindwidtemp==1){
			           alert("带宽必须为整数！");
			           $("#lineBandWidth").focus();
			           return false;
			        }
			        if(lentemp==1){
			           alert("长度输入错误！");
			           $("#lineLength").focus();
			           return false;
			        }
			        
			        $.post("lininfo/infoAction_add.action",
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
    		});
    		
    	});
//批量删除
	$(function(){
		   $("#okBtn").bind("click",function(){
		     var checkboxs=$("input[name='same']");//所有复选框
		     //alert(checkboxs.length);
		    // alert(checkboxs.eq(4).attr("id"));
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
		     json1={"lineIds":ids};

		     var jsonstr={data:JSON.stringify(json1)};
		     //发送请求
		     $.ajax({
		    	 type:"post",
		    	 url:"lininfo/infoAction_deleteMore.action",
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
	         url:"lininfo/infoAction_findOne",
	    	 data:{
	    	     "lineId":ids[0]	 
	    	 },
	    	 success:function(data){
	    		
	    		 var json1=JSON.parse(data);
	    		 $("#detaillineNum").val(json1.lineNum);
	    		 $("#detaillineOperator").val(json1.lineOperator);
	    		 $("#detaillineState").val(json1.lineState);
	    		 $("#detaillineLocalRemote").val(json1.lineLocalRemote);
	    		 
	    		 $("#detaillineSystem").val(json1.lineSystem);
	    		
	    		 $("#detaillineBandWidth").val(json1.lineBandWidth);
	    		 $("#detaillineLength").val(json1.lineLength);
	    		 $("#detaillineType").val(json1.lineType);
	    		 
	    		 $("#detaillineContract").val(json1.lineContract);
	    		 $("#detaillineContOperator").val(json1.lineContOperator);
	    		 $("#detaillineContFirstParty").val(json1.lineContFirstParty);
	    		 $("#detaillineContSecondParty").val(json1.lineContSecondParty);
	    		 //$("#detaillineCountryUser").val(json1.lineCountryUser);//国家中心使用人
	    		 //$("#detaillineCountryUseDepart").val(json1.lineCountryUseDepart);//国家中心使用部门
	    		 $("#detaillineCountryManaDepart").val(json1.lineCountryManaDepart);
	    		 
	    		 
	    		 $("#detaillineSubUser").val(json1.lineSubUser);
	    		 $("#detaillineSubUseDepart").val(json1.lineSubUseDepart);
	    		 $("#detaillineSubManaDepart").val(json1.lineSubManaDepart);
	    		 $("#detaillineARoom").val(json1.lineARoom);
	    		 
	    		 $("#detaillineARoomSite").val(json1.lineARoomSite);
	    		 $("#detaillineZRoom").val(json1.lineZRoom);
	    		 $("#detaillineZRoomSite").val(json1.lineZRoomSite);
	    		 
	    		 $("#detaillineRecordor").val(json1.lineRecordor);
	    		
	    		 $("#detaillineRemark").val(json1.lineRemark);
	    		 $("#detaillineUseTime").val(json1.lineUseTime);
	    		 
	    		 $("#detaillineDownTime").val(json1.lineDownTime);
	    		
	    		 $("#detaillinePayer").val(json1.linePayer);
	    		 
	    		 
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
	         url:"lininfo/infoAction_findOne",
	    	 data:{
	    	     "lineId":ids[0]	 
	    	 },
	    	 success:function(data){
	    		 
	    		 var json1=JSON.parse(data);
	    		 
	    		 $("#lineId2").val(json1.lineId);
	    		 $("#lineNum2").val(json1.lineNum);
                 $("#lineARoom2").val(json1.lineARoom);
	    		 
	    		 $("#lineARoomSite2").val(json1.lineARoomSite);
	    		 $("#lineZRoom2").val(json1.lineZRoom);
	    		 $("#lineZRoomSite2").val(json1.lineZRoomSite);
	    		 $("#lineBandWidth2").val(json1.lineBandWidth);
	    		 $("#lineLocalRemote2").val(json1.lineLocalRemote);
	    		
	    		 
	    		 
	    		 $("#lineOperator2").val(json1.lineOperator);//运营商显示
	    		 
	    		 $("#lineState2").val(json1.lineState);
	    		
	    		
	    		 $("#lineSystem2").val(json1.lineSystem);
	    		 $("#lineType2").val(json1.lineType);
	    		 $("#lineContract2").val(json1.lineContract);
	    		 $("#lineContFirstParty2").val(json1.lineContFirstParty);
	    		 $("#lineContSecondParty2").val(json1.lineContSecondParty);
	    		 $("#lineContOperator2").val(json1.lineContOperator);
	    		 
	    		 $("#lineSubUseDepart2").val(json1.lineSubUseDepart);
	    		 
	    		 $("#lineSubUser2").val(json1.lineSubUser);
	    		 
	    		 //$("#lineCountryUser2").val(json1.lineCountryUser);//国家中心使用人
	    		 //$("#lineCountryUseDepart2").val(json1.lineCountryUseDepart);//国家中心使用部门
	    		 $("#lineSubManaDepart2").val(json1.lineSubManaDepart);
	    		
	    		 $("#lineCountryManaDepart2").val(json1.lineSubManaDepart);
	    		 
                 $("#lineLength2").val(json1.lineLength);
 
	    		 $("#lineRecordor2").val(json1.lineRecordor);
	    		
	    		 $("#lineRemark2").val(json1.lineRemark);
	    		 
	    		 
	    		 $("#lineUseTime2").val(json1.lineUseTime);
	    		 
	    		 $("#lineDownTime2").val(json1.lineDownTime);
	    		
	    		 $("#linePayer2").val(json1.linePayer);
	    		 
	    		 
	    		 
	    		 
	    		 
	    		 //alert(data);
	    	 },
	    	 error:function(){
	    		 window.parent.document.getElementById('right').src="false.jsp";

	    	 }
	    	 
	     });
	     
	});
	$("#changeOk").bind("click",function(){
	    var lentemp=0,bindwidtemp=0,temp=0,state=0; 
	    var bindWidth=$("#lineBandWidth2").val();//验证带宽是否为整数
	    
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
		    
	    
		if(bindWidth!=""){
		   if(!(parseInt(bindWidth)==bindWidth)){
		      bindwidtemp=1;
		   }
		}
		var len=$("#lineLength2").val();//验证长度是否合法
		if(len!=""){
		   if(!(/^[1-9]\d*$/.test(len))){
		      lentemp=1;
		   }
		}
		
		//判断状态
        if($("#lineLocalRemote2").val()=="" || $("#lineState2").val()==""){
           state=1;
        }
		
		if(temp==1){
	           alert("带 * 的为必填项，请填写完整！");
	           $(".empty_input:first").focus();//光标定位到第一个空的input
	           return false;
	    }
		 if(state==1){
	           alert("请选则本地/长途,线路状态 ！");
	           return false;
	        }
		if(bindwidtemp==1){
		   alert("带宽必须为整数！");
		   $("#lineBandWidth2").focus();
		   return false;
		}
		if(lentemp==1){
		   alert("长度输入错误！");
		   $("#lineLength2").focus();
		   return false;
		}
		$.ajax({
			 type:"post",
			 url:"lininfo/infoAction_updateOne",
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
	         url:"LinInfLifeTime",
	    	 data:{
	    	     "lineId":ids[0]	 
	    	 },
	    	 success:function(data){
	    		//alert(data.length);
	    		var spans=$(".deadlineCon>span");
	    		spans.eq(0).text(data[0].lineNum);
	    		spans.eq(1).text(data[0].lineOperator);
	    		spans.eq(2).text(data[0].lineSystem);
	    		var tds;
	    		if(!data[1]){
	    			tds="<tr>"+"<td>"+data[0].lineState+"</td>"
	    			+"<td>"+data[0].lineUpdTime+"</td>"
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
		
		var formData = new FormData($("#importForm" )[0]);  
	
		$.ajax({
			url: "lininfo/infoAction_importLine.action" ,  
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
				confirm("导入失败!!!"); 
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
	



