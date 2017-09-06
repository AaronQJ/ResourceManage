 //批量导出
   	$(function(){
   		
   		
		$("#export").bind("click",function(){
			
			$.ajax({
		         type:"post", 
		         url:"usersAction_check_ProExport_power",
		         success:function(data){
		         	if(data == "noLimit"){
		         		alert("没有权限！请您联系超级管理员");
		         		right.src="noPower.jsp";
		         	    //window.location.href="noPower.jsp";
		         	}else{
		         		var checkboxs=$("input[name='same']");//所有复选框
		    		    // alert(checkboxs.length);
		    		    // alert(checkboxs.eq(4).attr("id"));
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
		    		    
		    		     json1={"offIds":ids};

		    		     var jsonstr={data:JSON.stringify(json1)};
		    			  
		    		     //发送请求
		    		     $.ajax({
		    		     
		    		    	 type:"post",
		    		    	 url:"offpro/offAction_exportpartExcelByConditions.action", //批量导出的action
		    		    	 data:jsonstr,
		    		    	 success:function(data){
		    		    		 var json1=JSON.parse(data);
		    			    		if(json1!=""){ 		
		    			    				location.href=FAD+'/Generatefile/'+json1;//单引号内些你生成的文件相对路径，我这是测试
		    			    				$("#checkbox").attr("checked",false);
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
   	
	//导出当前页
	$(function(){
		$("#exportNow").bind("click",function(){
			
			$.ajax({
                type:"post", 
                url:"usersAction_check_offExport_power",
                success:function(data){
                   if(data == "noLimit"){
                       alert("没有权限！请您联系超级管理员");
                       right.src="noPower.jsp";
                       //window.location.href="noPower.jsp";
                   }else{
                	   var checkboxs=$("input[name='same']");//所有复选框
           		    
          		     var json1={};
          		     var ids=[];
          		     $.each(checkboxs,function(i,val){
          		         ids.push(checkboxs.eq(i).attr("id"));
          		     });
          		    
          		     json1={"offIds":ids};

          		     var jsonstr={data:JSON.stringify(json1)};
          			   
          		     //发送请求
          		     $.ajax({
          		    	 type:"post",
          		    	 url:"offpro/offAction_exportExcelByConditions.action", //导出全部页的action
          		    	 data:jsonstr,
          		    	 success:function(data){
          		    		 var json1=JSON.parse(data);
          			    		if(json1!=""){ 		
          			    				location.href=FAD+'/Generatefile/'+json1;//单引号内些你生成的文件相对路径，我这是测试
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
                url:"usersAction_check_offExport_power",
                success:function(data){
                   if(data == "noLimit"){
                       alert("没有权限！请您联系超级管理员");
                       right.src="noPower.jsp";
                       //window.location.href="noPower.jsp";
                   }else{
                	   var json1={//查询条件
       		    		    "offSeqNum":$("#searchoffSeqNum").val(),
       		    		    "offName":$("#searchoffName").val(),
       		    		    "offNum":$("#searchoffNum").val(),
       		    		    "offBarCode":$("#searchoffBarCode").val(),
       		    		    "offUser":$("#searchoffUser").val(),
       		    		    //筛选条件
       		    		  /*  "offName1":offNameValue,						//名称
       						"offUseState1":offUseStateValue,				//使用状况
       						"offState1":offStateValue,						//状态
       						"offUseDepart1":offUseDepartValue,				//使用/管理部门
       						"offManager1":offManagerValue				//管理人	
       						*/
       						};
       		     
       		     //发送请求
       		     $.ajax({
       		    	 type:"post",
       		    	 url:"offpro/offAction_exportAllExcelByConditions.action", //批量导出的action
       		    	 data:json1,
       		    	 success:function(data){
       		    		 var json1=JSON.parse(data);
       			    		if(json1!=""){ 		
       			    				location.href=FAD+'/Generatefile/'+json1;//单引号内些你生成的文件相对路径，我这是测试
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