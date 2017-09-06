//批量导出
   	$(function(){
		$("#export").bind("click",function(){
			$.ajax({
		         type:"post", 
		         url:"usersAction_check_ProExport_power",
		         success:function(data){
		         	if(data == "noLimit"){
		         		alert("您无此权限！请您联系超级管理员");
		         		right.src="noPower.jsp";
		         	}else{
		         		var checkboxs=$("input[name='same']");//所有复选框
		    		 
		    		     var json1={};
		    		     var ids=[];
		    		     $.each(checkboxs,function(i,val){
		    		       if(checkboxs.eq(i).is(":checked")){//判断是否被选中
		    		          ids.push(checkboxs.eq(i).attr("id"));
		    		       }
		    		     });
		    		    
		    		     if(ids.length==0){//判断是否选择
		    		       confirm("请选择要导出的资产！");
		    		       return false;
		    		     }
		    		     json1={"speIds":ids};

		    		     var jsonstr={data:JSON.stringify(json1)}; 
		    		     //发送请求
		    		     $.ajax({
		    		     
		    		    	 type:"post",
		    		    	 url:"spepro/speAction_exportpartExcelByConditions.action", 
		    		    	 data:jsonstr,
		    		    	 success:function(data){
		    		    		 var json1=JSON.parse(data);
		    		    		 if(json1!=""){		
		    			    			$("input[name='same']").attr("checked",false);
		    			    			location.href=FAD+'/Generatefile/'+json1;
		    			    			
		    			    		}
		    			    		else{
		    			    			alert("导出失败！");
		    			    			$("#checkbox").attr("checked",false);
		    			    			window.location.reload();
		    			    		}
		    		    	 },
		    		    	 error:function(){
		    		    		 window.parent.document.getElementById('right').src="false.jsp";

		    		    	 }
		    		     });
		         	}
			
			}
			});
			
			
		});
	});
  
	
	//专用资产导出当前页
	$(function(){
		$("#exportNow").bind("click",function(){
			$.ajax({
		         type:"post", 
		         url:"usersAction_check_speExport_power",
		         success:function(data){
		         	if(data == "noLimit"){
		         		alert("您无此权限！请您联系超级管理员");
		         		right.src="noPower.jsp";
		         	}else{
		         		var checkboxs=$("input[name='same']");//所有复选框
		    		     var json1={};
		    		     var ids=[];
		    		     $.each(checkboxs,function(i,val){
		    		         ids.push(checkboxs.eq(i).attr("id"));
		    		     });
		    
		    		     json1={"speIds":ids};
		    		     var jsonstr={data:JSON.stringify(json1)};
		    			   
		    		     //发送请求
		    		     $.ajax({
		    		    	 type:"post",
		    		    	 url:"spepro/speAction_exportExcelByConditions.action", 
		    		    	 data:jsonstr,
		    		    	 success:function(data){
		    		    		 var json1=JSON.parse(data);
		    			    		if(json1!=""){ 		
		    			    			location.href=FAD+'/Generatefile/'+json1;
		    			    		}
		    			    		else{
		    			    			alert("导出失败！");
		    			    			window.location.reload();
		    			    		}
		    		    	 },
		    		    	 error:function(){
		    		    		 window.parent.document.getElementById('right').src="false.jsp";

		    		    	 }
		    		     });
		         	}
			
			}
			});
		});
	});

	//导出全部页
	$(function(){
		
		$("#exportAll").bind("click",function(){
				
				$.ajax({
	                type:"post", 
	                url:"usersAction_check_speExport_power",
	                success:function(data){
	                   if(data == "noLimit"){
	                       alert("您无此权限！请您联系超级管理员");
	                       right.src="noPower.jsp";
	                   }else{
	                	   var json1={//查询条件
	       		    		    "speDevName":$("#searchspeDevName").val(), //设备名称
	       		    		    "speSeqNum":$("#searchspeSeqNum").val(),   //序列号
	       		    		    "speBarCode":$("#searchspeBarCode").val(), //条码
	       		    		    "speUser":$("#searchspeUser").val(),      //使用人
	       		    		
	       		               };
	       		     $.ajax({
	       		    	 type:"post",
	       		    	 url:"spepro/speAction_exportAllExcelByConditions.action", 
	       		    	 data:json1,
	       		    	 success:function(data){
	       		    		
	       		    		 var json1=JSON.parse(data);
	       			    		if(json1!=""){ 		
	       			    				location.href=FAD+'/Generatefile/'+json1;
	       			    		}
	       			    		else{
	       			    			alert("导出失败！");
	       			    			window.location.reload();
	       			    		}
	       		    	 },
	       		    	 error:function(){
	       		    		 window.parent.document.getElementById('right').src="false.jsp";
	       		    	 }
	       		     });
	                   }
	           }
	           });
				
			});
		
	
	});
	
	
   
   
   		