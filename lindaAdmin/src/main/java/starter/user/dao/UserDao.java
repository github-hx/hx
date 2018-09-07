package starter.user.dao;

import org.apache.ibatis.annotations.Param;

import starter.user.bean.User;

public interface UserDao {
	public void login(User user) throws Exception;
	public User selectUserByUserName(@Param("userName") String userName) throws Exception;
}
