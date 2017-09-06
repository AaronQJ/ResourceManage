window.onload=function(){
	var spe_pro=document.getElementById("spe_pro");//资产管理>专用资产
	var off_pro=document.getElementById("off_pro");//资产管理>办公资产
	var pro_bak=document.getElementById("pro_bak");//资产管理>备品备件
	var lin_inf=document.getElementById("lin_inf");//资产管理>专线信息
	var tableSpe=document.getElementById("tableSpe");//报表管理>专用资产
	var tableBak=document.getElementById("tableBak");//报表管理>备品备件
	var tableOff=document.getElementById("tableOff");//报表管理>办公资产
	var peopleManage=document.getElementById("peopleManage");//人员管理
	var logManage=document.getElementById("logManage");//日志查询
	var dataManage=document.getElementById("dataManage");//数据维护
	
	spe_pro.onclick=function(){//资产管理>专用资产
		right.src="speManage.jsp";
		return false;
		}
	off_pro.onclick=function(){//资产管理>办公资产
		right.src="OffManage.jsp";
		return false;
		}
	pro_bak.onclick=function(){//资产管理>备品备件
		right.src="bakManage.jsp";
		return false;
		}
	lin_inf.onclick=function(){//资产管理>专线信息
		
		right.src="lineManage.jsp";
		return false;
		}
	
	tableSpe.onclick=function(){//报表管理>专用资产
		
		$.ajax({
	         type:"post", 
	         url:"usersAction_check_TableSpe_power",
	         success:function(data){
	         	if(data == "noLimit"){
	         		//alert("没有权限！");
	         		right.src="noPower.jsp";
	         	    //window.location.href="noPower.jsp";
	         	}else{
	         		right.src="tableSpe.jsp";
	        		return false;
	         	}
		
		}
		});
		
		
		}
	tableBak.onclick=function(){//报表管理>备品备件
		$.ajax({
	         type:"post", 
	         url:"usersAction_check_Tablebak_power",
	         success:function(data){
	         	if(data == "noLimit"){
	         		//alert("没有权限！");
	         		right.src="noPower.jsp";
	         	    //window.location.href="noPower.jsp";
	         	}else{
	         		right.src="tableBak.jsp";
	        		return false;
	         	}
		
		}
		});
		
	
		}
	tableOff.onclick=function(){//报表管理>办公资产
		
		$.ajax({
	         type:"post", 
	         url:"usersAction_check_TableOff_power",
	         success:function(data){
	         	if(data == "noLimit"){
	         		//alert("没有权限！");
	         		right.src="noPower.jsp";
	         	    //window.location.href="noPower.jsp";
	         	}else{
	         		right.src="tableOff.jsp";
	        		return false;
	         	}
		
		}
		});
		
		}
	peopleManage.onclick=function(){//人员管理
		
		right.src="usersAction_showSelect";
		return false;
		}
	logManage.onclick=function(){//日志查询
		$.ajax({
	         type:"post", 
	         url:"usersAction_check_Log_power",
	         success:function(data){
	         	if(data == "noLimit"){
	         		//alert("没有权限！");
	         		right.src="noPower.jsp";
	         	    //window.location.href="noPower.jsp";
	         	}else{
	         		right.src="logManage.jsp";
	        		return false; 
	         	}
		
		}
		});
	         		  		    
		}
	dataManage.onclick=function(){//数据维护
		$.ajax({
	         type:"post", 
	         url:"usersAction_check_dataManage_power",
	         success:function(data){
	         	if(data == "noLimit"){
	         		//alert("没有权限！");
	         		right.src="noPower.jsp";
	         	    //window.location.href="noPower.jsp";
	         	}else{
		right.src="dataManage.jsp";
		return false;
	         	}
		
		}
		});
	}
	         
	}