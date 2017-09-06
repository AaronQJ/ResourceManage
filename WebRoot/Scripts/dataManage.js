
$(function(){
	function stopPropagation(e){
		
	  if(e.stopPropagation){
		  e.stopPropagation();
	  }else{
		  e.cancelBubble=true;
	  }
	}
	//点击旁边的位置隐藏列表
	$(document).bind('click',function(event){
		var e=event||window.event;
		var target=e.target||srcElement;
		var name=target.tagName;
		if(name!="BUTTON"&&name!="LI"&&name!="UL"){
			$("#ulImport").css("display","none"); 
			$("#ulExport").css("display","none");
		}
	});
	
	$("#importAct").bind('click',function(e){
		stopPropagation(e);
		if($("#ulImport").css("display")=="block"){
			$("#ulImport").css("display","none");
		}
		else{
			$("#ulImport").css("display","block");
			$("#ulExport").css("display","none");
		}
		
	});
	$("#exportAct").bind('click',function(e){
		stopPropagation(e);
		if($("#ulExport").css("display")=="block"){
			$("#ulExport").css("display","none");
		}
		else{
			$("#ulImport").css("display","none"); 
			$("#ulExport").css("display","block");
		}
		
	});
	
	
});

//导出
//专用资产
$(function(){
//	var FAD='${pageContext.request.contextPath}';
	$("#speExport").bind("click",function(){
		$.ajax({
			type:"post",
		    url:"spepro/speAction_exportExcel.action",
		    data:{
		    	
		    },
		    success:function(data){
		    	 var json1=JSON.parse(data);
		    	
		    		if(json1!=""){ 		
		    			location.href=FAD+'/Generatefile/'+json1;//单引号内些你生成的文件相对路径，我这是测试
		    			
		    		}
		    		else{
		    			alert("导出失败,请重试!");
		    		}
		    },
		    error:function(){
		    	alert("导出失败,请重试!");
		    }
		});
	});
	
});

//线路管理
$(function(){
	$("#linExport").bind("click",function(){
		$.ajax({
			type:"post",
		    url:"lininfo/infoAction_exportExcel.action",
		    data:{
		    	
		    },
		    success:function(data){
		    	 var json1=JSON.parse(data);
		    		if(json1!=""){ 		
		    			location.href=FAD+'/Generatefile/'+json1;//单引号内些你生成的文件相对路径，我这是测试
		    		}
		    		else{
		    			alert("导出失败,请重试!");
		    		}
		    },
		    error:function(){
		    	alert("导出失败,请重试!");
		    }
		});
	});
	
});

//备品备件
$(function(){
	$("#bakExport").bind("click",function(){
		$.ajax({
			type:"post",
		    url:"probak/bakAction_exportExcel.action",
		    data:{
		    	
		    },
		    success:function(data){
		    	 var json1=JSON.parse(data);
		    		if(json1!=""){ 		
		    			location.href=FAD+'/Generatefile/'+json1;//单引号内些你生成的文件相对路径，我这是测试
		    		}
		    		else{
		    			alert("导出失败,请重试!");
		    		}
		    },
		    error:function(){
		    	alert("导出失败,请重试!");
		    }
		});
	});
	
});

//办公资产
$(function(){
	$("#offExport").bind("click",function(){
		$.ajax({
			type:"post",
		    url:"offpro/offAction_exportExcel.action",
		    data:{
		    	
		    },
		    success:function(data){
		    	 var json1=JSON.parse(data);
		    		if(json1!=""){ 		
		    			location.href=FAD+'/Generatefile/'+json1;//单引号内些你生成的文件相对路径，我这是测试
		    		}
		    		else{
		    			alert("导出失败,请重试!");
		    		}
		    },
		    error:function(){
		    	alert("导出失败,请重试!");
		    }
		});
	});
	
});

//系统文件
$(function(){
	$("#all").bind("click",function(){
		$.ajax({
			type:"post",
		    url:"mysql/mysqlAction_exportSql.action",
		    data:{
		    	
		    },
		    success:function(data){
		    	 var json1=JSON.parse(data);
		    		if(json1!=""){ 		
		    			location.href=FAD+'/Generatefile/'+json1;//单引号内些你生成的文件相对路径，我这是测试
		    		}
		    		else{
		    			alert("导出失败,请重试!");
		    		}
		    },
		    error:function(){
		    	alert("导出失败,请重试!");
		    }
		});
	});
	
});

//弹出导入框
$(function()
		{	
			if(document.getElementById("speImport"))
				var speImport=document.getElementById("speImport");
			if(document.getElementById("linImport"))
				var linImport=document.getElementById("linImport");
			if(document.getElementById("bakImport"))
				var bakImport=document.getElementById("bakImport");
			if(document.getElementById("offImport"))
				var offImport=document.getElementById("offImport");
			
			//专用资产
			speImport.onclick=function()
			{
				if(!confirm("该操作会清空数据库原始内容,请谨慎！")){
					return false;
				}
				setPosition($("#importBox"));
				globalShade();
				$("#importBox").css("display","block");
			
					$("#sureToImport").bind("click",function(){
						/*alert($("#fileAddress")[0].files[0]);
						alert($("#fileAddress").val());*/
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
						/*alert(formData);*/
						$.ajax({
							url: "spepro/speAction_excelToMysql" ,  
							type: "POST",  
							data: formData,
					//		dataType: "json",
							async: false,  
							cache: false,  
							contentType: false,  
							processData: false,  
							success: function (data) {
								/*alert(data);*/
								if(data == -1){
									alert("全部导入成功！");
									$("#importBox").css("display","none");
									deleteGlobalShade();
									window.location.reload();
								}else
								{
									alert("导入失败！不是专用资产表");
									$("#importBox").css("display","none");
									deleteGlobalShade();
									window.location.reload();
									}
							},  
							error: function(data) {  
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

				
				
				
				
				
				
				
				
				
			}
			//线路管理
			linImport.onclick=function()
			{
				if(!confirm("该操作存在风险,请谨慎！")){
					return false;
				}
				setPosition($("#importBox"));
				globalShade();
				$("#importBox").css("display","block");
				
				$("#sureToImport").bind("click",function(){
					/*alert($("#fileAddress")[0].files[0]);
					alert($("#fileAddress").val());*/
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
					/*alert(formData);*/
					$.ajax({
						url: "lininfo/infoAction_excelToMysql" ,  
						type: "POST",  
						data: formData,
				//		dataType: "json",
						async: false,  
						cache: false,  
						contentType: false,  
						processData: false,  
						success: function (data) {
							/*alert(typeof(data)+"888888888");*/
							if(data == -1){
								alert("全部导入成功！");
								$("#importBox").css("display","none");
								deleteGlobalShade();
								window.location.reload();
							}else
								{
								alert("导入失败！不是线路信息表");
								$("#importBox").css("display","none");
								deleteGlobalShade();
								window.location.reload();
								}
						},  
						error: function(data) {  
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

				
			}
			//备品备件
			bakImport.onclick=function()
			{
				if(!confirm("该操作存在风险,请谨慎！")){
					return false;
				}
				setPosition($("#importBox"));
				globalShade();
				$("#importBox").css("display","block");
				$("#sureToImport").bind("click",function(){
					/*alert($("#fileAddress")[0].files[0]);
					alert($("#fileAddress").val());*/
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
					/*alert(formData);*/
					$.ajax({
						url: "probak/bakAction_excelToMysql" ,  
						type: "POST",  
						data: formData,
				//		dataType: "json",
						async: false,  
						cache: false,  
						contentType: false,  
						processData: false,  
						success: function (data) {
							/*alert(typeof(data));*/
							if(data == -1){
								alert("全部导入成功！");
								$("#importBox").css("display","none");
								deleteGlobalShade();
								window.location.reload();
							}else
								{
								alert("导入失败！不是备品备件表");
								$("#importBox").css("display","none");
								deleteGlobalShade();
								window.location.reload();
								}
						},  
						error: function(data) {  
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
	
			}
			//办公资产
			offImport.onclick=function()
			{
				if(!confirm("该操作存在风险,请谨慎！")){
					return false;
				}
				setPosition($("#importBox"));
				globalShade();
				$("#importBox").css("display","block");
				
				$("#sureToImport").bind("click",function(){
					/*alert($("#fileAddress")[0].files[0]);
					alert($("#fileAddress").val());*/
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
					/*alert(formData);*/
					$.ajax({
						url: "offpro/offAction_excelToMysql" ,  
						type: "POST",  
						data: formData,
				//		dataType: "json",
						async: false,  
						cache: false,  
						contentType: false,  
						processData: false,  
						success: function (data) {
							/*alert(data);*/
							if(data == -1){
								alert("全部导入成功！");
								$("#importBox").css("display","none");
								deleteGlobalShade();
								window.location.reload();
							}else
								{
								alert("导入失败！不是办公资产表");
								$("#importBox").css("display","none");
								deleteGlobalShade();
								window.location.reload();
								}
								
						},  
						error: function(data) {  
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
				
			}
			
			
			$("#importCloseBox").bind("click",function(){
				$("#importBox").css("display","none");
				deleteGlobalShade();
				window.location.reload();
			});
			
		});





