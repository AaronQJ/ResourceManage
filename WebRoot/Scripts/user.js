/**
 * Created by Wangrong on 2016/8/9.
 */


$(function(){
  $("#addUser").bind("click",function(){
	  globalShade();
	  setPosition($("#addUser1"));
      $("#addUser1").css('display','block');
  });
});
//弹出框中点击右上角图标会关闭
$(function(){
    $(".closeBox").bind("click",function(){
        $(".addUser1").css('display','none');
        deleteGlobalShade();
    });
});
$(function(){
	
	$("#userName").keyup(function(){
    	console.log($(this).val());
    	if ($(this).val()!= null) {
    		console.log($(this).val());
    		$.post('usersAction_userNameExistCheck.action',{userName : $(this).val()}, function(data){
    			console.log(data);
    			if (data == 'No') {
    				//alert("用户名已经存在！");
    				//var userName=$("#userName").val('');
    				/*var pwd=$("#pwd").val('');
    				var pwdCheck=$("#pwdCheck").val('');*/
    				$('#checkUserNameAlert').show();    				
    				$('#checkUserNameAlert').data('checkUserNameStatus', 'no');
    			} else {
    				$('#checkUserNameAlert').hide();
    				$('#checkUserNameAlert').data('checkUserNameStatus', 'yes');
    			}
    		});
    	} else {
    		$('#checkUserNameAlert').data('checkUserNameStatus', 'yes');
    	}
	});
	
	
	
	$("#addOk").bind("click",function(){		
		var pwd=$("#pwd").val();
		var pwdCheck=$("#pwdCheck").val();
		var userName=$('#userName').val();
		var telNumber=$("#telNumber").val();
		
		if (userName!= null){
			console.log(userName);
			$.post('usersAction_userNameExistCheck.action',{userName : userName}, function(data){
				if (data == 'No'){
					//alert("用户名已经存在！");
					return false;
				}
			});
		}
		
		/*if(!$('#checkUserNameAlert').data('checkUserNameStatus')){
	    	   alert("请输入用户名！");
	           return false;
	       }*/
		if(userName==""){
			alert("请输入用户名！");
			return false;
		}
		
		if(pwd==""){
			alert("请输入密码！");
			return false;
		}
		if(pwdCheck==""){
			alert("请确认密码！");
			return false;
		}
	
		if(pwd!=pwdCheck){
			alert("两次密码不一致！");
			var pwdCheck=$("#pwdCheck").val('');
			return false;
		}	       
		

		/*if(telNumber!=""){
			var reg=/^1[3|4|5|7|8]\d{9}$/;
			if(!reg.test(telNumber)){
				alert("电话号码输入有误！");
				return false;
			}
			
			
		}*/
		
		
	    if($('#checkUserNameAlert').data('checkUserNameStatus') == 'no'){
	    	   //alert("用户名已经存在！");
	    	   /*$('#checkUserNameAlert').show();*/
	           return false;
	       }
	    $('#userAdd').get(0).submit();
	    alert("添加成功！");
	    deleteGlobalShade();
	    window.location.reload();
		
	});
	
	
});

function changeInfo(userName,tel,depart,roleId) {
	globalShade();
	setPosition($("#changeInfo"));
	$("#toUserName").val(userName);
	$("#toTel").val(tel);
	$("#toDepart").val(depart);
	$("#toRoleId").val(roleId);
	$("#changeInfo").css('display','block');
}

function userDel(userName){
	//$("#delUser").val(userName);
	globalShade();
	setPosition($("#deleteOne"));
	$("#deleteOne").css('display','block');
//	alert(1)
	  $("#okBtn").bind("click",function(){
		$.post('usersAction_del_check.action',{'userName':userName},  function(data, status){
			if('YES' == data) {
				$("#deleteOne").css('display','none');
				deleteGlobalShade();
				$.post('usersAction_del.action' ,{'userName':userName},function(){
				alert(userName + '删除成功');
				 location.reload(true);
				}
			);
				
			} else {					
				alert('admin不能被删除！');
				$("#deleteOne").css('display','none');
				deleteGlobalShade();
				
			}
		} );
		
	});
	 $(".close").bind("click",function(){
		
		$("#deleteOne").css('display','none');
		deleteGlobalShade();
	});
}

/*
function userDel(userName){
	if (confirm("确定删除?")){
	//console.log('start delete ');
	$.post('usersAction_del_check.action' ,{'userName':userName}, function(data, status){
		if('YES' == data) {
			$.post('usersAction_del.action' ,{'userName':userName},function(){
				alert(userName + '删除成功');
				 location.reload(true);
				}
			);
			
			
		} else {					
			alert('admin不能被删除！');
		}
	} );
	
}
}*/

function returnsumbit_sure() {
	var gnl=confirm("确定修改?");
	if (gnl == true){
		deleteGlobalShade();
		alert("修改成功！");
		
		return true;
		
	}else{
		return false;
	}
}
