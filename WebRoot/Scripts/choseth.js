/**
 * Created by Wangrong on 2016/8/5.
 */

//批量删除
$(function(){
	   $("#delete").bind("click",function(){
		   $.ajax({
		         type:"post", 
		         url:"usersAction_check_ProDel_power",
		         success:function(data){
		         	if(data == "noLimit"){
		         		/*$("#addBox").css("display","none");*/
		         		alert("您无此权限！请您联系超级管理员");

		         		}else{
		         		   
		         		     var checkboxs=$("input[name='same']");//所有复选框
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
		         		     else{
		         		    	 globalShade();
		         	         	 setPosition($("#deleteOne"));
		         	             $("#deleteOne").css('display','block');
		         		     }
		         	}
		         	}
		         });    
		
	   });
});



//点击操作（列表中最后一项),事件委托
$(function(){
  $("#massageTable").on('click','button',function(){
  	$(".choseDiv").css("display","none");
  	
      var prev=$(this).parent().parent();
      if(prev.prop("tagName")!="TR"){  //到tr
          prev=prev.parent();
      }
      var top=prev.children().last().offset().top;
      var left=prev.children().last().offset().left;
      var top1=parseFloat(top)+24;
      var width=prev.children().last().css("width");
      var checkbox=prev.children().first().next().find("input");//到tr的第二个子节点，找到本行checkbox
      if(checkbox.prop("checked")==false){//当前行未被选中
          var checkboxs = $("input[name='same']");//先将所有checkbox置为false
          for(var i=0;i<checkboxs.length;i++){
              if(checkboxs.eq(i)!=checkbox){
                  checkboxs.eq(i).prop("checked",false);
              }
          }
          
          var bodyHeight=parseFloat($("body").css("height"));//iframe的高度
          var operateHeight=parseFloat($("#showOperate").css("height"));//弹出框的高度
        
          if(bodyHeight-top1-30<operateHeight){
          	top1=bodyHeight-operateHeight-30;
          	left=parseFloat(left)-parseFloat(width)-2+"px";
          }
        // alert(top1);
          $("#showOperate").css({top:top1+"px",left:left,width:width});
          setTimeout( $("#showOperate").css("display","block"),1);
          checkbox.prop("checked",true);

      }else{
          $("#showOperate").css("display","none");
          checkbox.prop("checked",false);
      }

      $("#showOperate").find("li").bind("click",function(){
          var text=$(this).text();
          if(text=="删除"){
        	  $.ajax({
     	         type:"post", 
     	         url:"usersAction_check_ProDel_power",
     	         success:function(data){
     	         	if(data == "noLimit"){
     	         		alert("您无此权限！请您联系超级管理员");
     	         		location.reload(true);
     	         		//return false;
     	         	    //window.location.href="noPower.jsp";
     	         	}else{
     	         		globalShade();
     	             	setPosition($("#deleteOne"));
     	                 $("#deleteOne").css('display','block');
     	         	}
     		
     		}
     		});
          	
          }
          if(text=="修改"){
        	  $.ajax({
 		         type:"post", 
 		         url:"usersAction_check_ProDel_power",
 		         success:function(data){
 		         	if(data == "noLimit"){
 		         		/*$("#addBox").css("display","none");*/
 		         		alert("您无此权限！请您联系超级管理员");
 		         		location.reload(true);

 		         		}else{
 		         			globalShade();
 		                 	setPosition($("#changeOne"));
 		                     $("#changeOne").css('display','block');
 		                     test="";
 		         	}
 		         	}
 		         });  
          	
          }
          if(text=="详细信息"){
          	globalShade();
          	setPosition($("#showDetail"));
              $("#showDetail").css('display','block');
          }
          if(text=="生成条码"){
        	  $.ajax({
      	         type:"post", 
      	         url:"usersAction_check_BarCode_power",
      	         success:function(data){
      	         	if(data == "noLimit"){
      	         		alert("您无此权限！请您联系超级管理员");
      	         		location.reload(true);
      	         		//return false;
      	         	    //window.location.href="noPower.jsp";
      	         	}else{
      	         		globalShade();
      	            	setPosition($("#getCodeBar"));
      	                $("#getCodeBar").css('display','block');
      	         	}
      		
      		}
      		});
          	
              
          }
          if(text=="生命周期查询"){
          	globalShade();
          	setPosition($("#showDeadline"));
              $("#showDeadline").css('display','block');

          }
      });
  });
});

//点击th进行选择查询
$(function(){
    function fun(parent,ul){
        var top=parseFloat(parent.offset().top)+24+"px";
        var left=parseFloat(parent.offset().left)+1+"px";
        var width=parseFloat(parent.css("width"))+"px";
        ul.css({top:top,left:left,width:width});
        ul.css("display","block");
    }
  
    var choseimgs=$("table th img");
    var choseuls=$(".choseDiv");

    $.each(choseimgs, function(i,val){//点击显示

       choseimgs.eq(i).bind("click",function(){
    	   
    	   $("#showOperate").css("display","none");//更多操作弹出框关闭
    	   
           for(var j=0;j<choseuls.length;j++){
               if(j!=i){
                   choseuls.eq(j).css("display","none");
               }
           }
           if(choseuls.eq(i).css("display")=="block"){
               choseuls.eq(i).css("display","none");
           }else{
               fun(choseimgs.eq(i).parent(),choseuls.eq(i));
           }

       });
    });
});

//关闭弹出框(右侧点击出现的弹出框)
$(function(){
	$(".close").bind("click",function(){
		$(".dialog").css("display","none");
		deleteGlobalShade();
		$("#checkbox").attr("checked",false);
		window.location.reload();
	});
});

//点击旁边，关闭showOperate
$(function(){
	$(document).bind("click",function(e){
		var e=e||window.event;
		var item=e.target;
		//alert(item.tagName);
		if(item!=$("#showOperate")&& item.tagName!="BUTTON"){
			
			$("#showOperate").css('display','none');
			
		}
		
	});
});
//点击旁边关闭choseDiv
$(function(){
	$(document).bind("click",function(e){
		var e=e||window.event;
		var item=e.target;
		
		if(item!=$(".choseDiv")&& item.tagName!="IMG"){
			
			$(".choseDiv").css('display','none');
		}
	});
});

