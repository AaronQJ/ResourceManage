
//复选框
function checkBox()
{
	  var checkbox=$("#checkbox");
	
    checkbox.bind("click",function(){
            checkbox.value = checkbox.value == 1 ? 2 : 1;
            var checkboxs = document.getElementsByName("same");
            for (var i = 0; i < checkboxs.length; i++) {
                if (checkbox.value == 2) {
                    checkboxs[i].checked = false;//全不选
                } else {
                    checkboxs[i].checked = true;//全选
					
                }
            }
        }
        );
}

function nullJudge()
{
	var notNullInput=document.getElementsByClassName("notNullInput");
	var i=0;
	for(i; i<notNullInput.length; i++)
	{
		var temp=notNullInput[i];
		if (temp.value=="")
			return false;
		
	}
	return true;
}
//批量添加
function addBox()
{
	if(document.getElementById("add") )
		var add=document.getElementById("add");
	else return false;
	if(document.getElementById("addBox"))
	var addBox=document.getElementById("addBox");
	else return false;
	if(document.getElementById("closeBox"))
	var closeBox=document.getElementById("closeBox");
	else return false;
	
	add.onclick=function()
	{
		$.ajax({
	         type:"post", 
	         url:"usersAction_check_ProAdd_power",
	         success:function(data){
	         	if(data == "noLimit"){
	         		alert("没有权限！请您联系超级管理员");
	         		}else{
	         	   globalShade();
                   addBox.style.display="block";
	         	}
	         	}
	         });    
	}

	closeBox.onclick=function()
	{
		if(window.confirm('您的数据尚未保存,您确定要退出吗？'))
		{
            addBox.style.display="none";
            deleteGlobalShade();
            window.location.reload();
        }
	}
}
//批量修改
/*
function changeBox()
{
	if(document.getElementById("change"))
		var change=document.getElementById("change");
	else return false;
	if(document.getElementById("changeBox"))
		var changeBox=document.getElementById("changeBox");
	else return false;
	if(document.getElementById("changeCloseBox"))
		var changeCloseBox=document.getElementById("changeCloseBox");
	else return false;
	change.onclick=function()
	{
		globalShade();
		changeBox.style.display="block";
	}
	changeCloseBox.onclick=function()
	{
		
		if(window.confirm('您的数据尚未保存,您确定要退出吗？'))
		{
			changeBox.style.display="none";
			deleteGlobalShade();
			window.location.reload();
        }
	}
}*/
//批量导入
function importBox()
{
	if(document.getElementById("import"))
		var theImport=document.getElementById("import");
	else return false;
	if(document.getElementById("importBox"))
		var importBox=document.getElementById("importBox");
	else return false;
	if(document.getElementById("importCloseBox"))
		var importCloseBox=document.getElementById("importCloseBox");
	else return false;
	theImport.onclick=function()
	{
		$.ajax({
	         type:"post", 
	         url:"usersAction_check_ProImport_power",
	         success:function(data){
	         	if(data == "noLimit"){
	         		/*$("#addBox").css("display","none");*/
	         		alert("没有权限！请您联系超级管理员");

	         		}else{
	         			globalShade();
	         			importBox.style.display="block";
	         	}
	         	}
	         });    
		
		
	}
	importCloseBox.onclick=function()
	{
		if(window.confirm('您确定要退出吗？'))
		{
			importBox.style.display="none";
			deleteGlobalShade();
        }
	}
}
/*
function sureToAdd()
{
	if(document.getElementById("sive"))
		var sureToSive=document.getElementById("sive");
	else return false;
	if(document.getElementById("sureToChange"))
		var sureToChange=document.getElementById("sureToChange");
	else return false;
	if(document.getElementById("isDownload"))
		var sureToImport=document.getElementById("sureToImport");
	else return false;
	sureToImport.onclick=sureToChange.onclick=sureToSive.onclick=function()
	{
		deleteGlobalShade();
	}
}
*/
window.onload=function()
{
	checkBox();//复选功能
	setPosition($("#addBox"));//批量添加弹出框定位
	setPosition($("#changeBox"));//批量修改弹出框定位
	setPosition($("#importBox"));//导入弹出框定位
	addBox();	//批量添加
	//changeBox();//批量修改
	importBox();//批量导入
	//sureToAdd();
};

