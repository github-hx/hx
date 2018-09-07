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