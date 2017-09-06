/**
 * Created by Wangrong on 2016/9/3.
 */

function forgotPwd() {
    alert("请联系超级管理员！");
}
$(function(){
    $("#checkNumber").keyup(function(){
//    	console.log($(this).val());
        if ($(this).val().length == 4) {
            console.log($(this).val());
            $.post('usersAction_login_checkCode.action', {checkNumber : $(this).val()}, function(data){
                console.log(data);
                if (data == 'Yes') {
                    $('#checkCodeAlert').hide();
                    $('#checkCodeAlert').data('checkCodeStatus', 'yes');
                } else {
                    $('#checkCodeAlert').show();
                    $('#checkCodeAlert').data('checkCodeStatus', 'no');
                }
            });
        } else {
            $('#checkCodeAlert').data('checkCodeStatus', 'no');
        }
    });

    $("#button1").bind("click",function(){
        var userName=$("#userName").val();
        var pwd=$("#pwd").val();
	var test;
	//alert("Test");

	/*$.ajax({
		type:"post",
		url:"usersAction_userNameCheck.action"

	});
	*/
  	$.post('usersAction_userNameCheck.action',{userName : userName , pwd : pwd}, function(data){
		
            if(data == "No"){
		alert("用户名或密码错误！");
                
            }
        });
	

	//alert("TestEnd");
        if(userName=="" || pwd==""){
            alert("用户名或密码不能为空！");
            return false;
        }
        if(!$('#checkCodeAlert').data('checkCodeStatus')){
            alert("请输入验证码！");
            return false;
        }

        if($('#checkCodeAlert').data('checkCodeStatus') == 'no'){
            $('#checkCodeAlert').show();
            return false;
        }

      

        $('#form-logID').get(0).submit();
    });
});
