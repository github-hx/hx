package gameweb.management.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gameweb.management.user.userservice.UserLoginService;

@RestController
public class UserLoginControler {
	@Autowired
	private UserLoginService userLoginService;
	@RequestMapping("/login")
	public String login(){
		System.out.println("login methon");
		System.out.println(userLoginService);
		return "login";
	}
}
