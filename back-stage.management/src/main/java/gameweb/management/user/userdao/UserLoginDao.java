package gameweb.management.user.userdao;

import org.apache.ibatis.annotations.Mapper;

import gameweb.management.user.domain.User;

@Mapper
public interface UserLoginDao {
	public User login(User user) throws Exception;
}
