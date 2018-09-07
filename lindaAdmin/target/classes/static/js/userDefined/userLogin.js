//弹出登录窗口
$("#user-login-button").click(function(){
		$(".user-login").css("display","block");
	});
	
$("#closeLogin").click(function(){
		closeLogin();
	})
$("#cancleLogin").click(function(){
		closeLogin();
	})
	//关闭登录窗口
var closeLogin=function(){
		$(".user-login").css("display","none");
	}
//登录	
$("#login").click(function(){
	var userName=$("#userName").val();
	var userPassword=$("#userPassword").val();
		$.ajax({
				url:"/login",
				async:true,
				type:"POST",
				contentType:"application/x-www-form-urlencoded",
				data:"userName="+userName+"&userPassword="+userPassword,
				dataType:"json",
				success:function(result){
					alert(result.lastLoginTime);
					alert(result.lastLoginAddress);
					}
			})
		
	});
	

