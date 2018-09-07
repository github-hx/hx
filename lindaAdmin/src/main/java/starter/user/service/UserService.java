package starter.user.service;

import org.apache.ibatis.annotations.Param;

import starter.user.bean.User;
import starter.user.bean.UserLoginInfo;

public interface UserService {
	public UserLoginInfo login(User user) throws Exception;
	public User selectUserByUserName(@Param("userName")String userName) throws Exception;
}
