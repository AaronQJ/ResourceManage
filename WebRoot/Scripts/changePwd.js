
$(function(){
	
	$("#changePwdOk").bind("click",function(){
		var oldPwd=$("#oldPwd").val();
		var newPwd=$("#newPwd").val();
		var newPwdOk=$("#newPwdOk").val();
		
		if(oldPwd==""){
			alert("请输入旧密码！");
			return false;
		}
		if(newPwd==""){
			alert("请输入新密码！");
			return false;
		}
		if(newPwdOk==""){
			alert("请确认新密码！");
			return false;
		}
		
		if(newPwd!=newPwdOk){
			alert("两次密码不一致！");
			var oldPwd=$("#oldPwd").val('');
			var newPwd=$("#newPwd").val('');
			var newPwdOk=$("#newPwdOk").val('');
			return false;
		}
		
		$.post('usersAction_updatePwdOldCheck.action', {userName : $('#userName').text(), oldPwd : oldPwd}, function(data){
			console.log(data);
			if (data == 'Yes') {
				$('#changePwd').get(0).submit();
				alert("修改成功");
				return true;
			} else {
				alert('旧密码不正确');
				var oldPwd=$("#oldPwd").val('');
				var newPwd=$("#newPwd").val('');
				var newPwdOk=$("#newPwdOk").val('');
				return false;
			}
		});
	});
	
});