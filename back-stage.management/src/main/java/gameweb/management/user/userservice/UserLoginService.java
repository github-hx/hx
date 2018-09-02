package gameweb.management.user.userservice;

import gameweb.management.user.domain.User;

public interface UserLoginService {
	public User login(User user) throws Exception;
}
