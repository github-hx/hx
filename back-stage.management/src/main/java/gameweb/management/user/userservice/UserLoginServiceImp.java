package gameweb.management.user.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gameweb.management.user.domain.User;
import gameweb.management.user.userdao.UserLoginDao;

@Service
public class UserLoginServiceImp implements UserLoginService{
	
	@Autowired
	private UserLoginDao userLoginDao;
	@Override
	public User login(User user) throws Exception {
		
		return null;
	}

}
