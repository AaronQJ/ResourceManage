//日志管理
//定位刪除框
function setPosition(obj){
	
	//alert(11111);
    var width=parseFloat(obj.css("width"));
    var docWidth=parseFloat($("body").css("width"));
    //alert(docWidth);
    var bigWidth=docWidth/0.85;
    var rightWidth=(bigWidth-width)/2;
    var leftWidth=rightWidth-bigWidth*0.15;
  
    obj.css("left",leftWidth+"px");
    obj.css("top",95+"px");
 
}

//td的悬浮效果
$(function(){
    $("#logTable").on("mouseover","td",function(){
                $(this).prop("title",$(this).text());
      
    });
});

//日历
$(function(){
  $(".datepicker").bind("click",function(){
      WdatePicker();
  });
});
//关闭弹出框(右侧点击出现的弹出框)
$(function(){
	$(".close").bind("click",function(){
		$("#deleteOne").css("display","none");
		deleteGlobalShade();
		window.location.reload();
	});
});

//弹出删除框
$(function(){
	$("#del").bind("click",function(){
		if($("#updTime").val()==""){
			confirm("请选择日期！");
		}
		else{
			globalShade();
        	setPosition($("#deleteOne"));
            $("#deleteOne").css('display','block');
		}
	});
});
//确定删除
$(function(){
	$("#okBtn").bind("click",function(){
		$.ajax({
			type:"post",
		    url:"logdelete",//删除日志的action
		    data:{
		    	date:$("#updTime").val()
		    },
		    success:function(data){
		    	if(data){
		    		alert("删除成功！");
		    		
		    		deleteGlobalShade();
		    		window.location.reload();
		    	}
		    	else{
		    		alert("删除失败!");
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
