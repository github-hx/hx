package starter.user.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import starter.user.bean.User;
import starter.user.bean.UserLoginInfo;
import starter.user.dao.UserDao;

@Service
public class UserServiceImp implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	/*
	 * (non-Javadoc)
	 * @see starter.user.service.UserService#login(starter.user.bean.User)
	 * 用户登录
	 */
	@Override
	public UserLoginInfo login(User user) throws Exception {
		UserLoginInfo userLoginInfo=new UserLoginInfo();
		User userFromDB=selectUserByUserName(user.getUserName());
		//根据用户名没有查询到对应的用户信息
		if(userFromDB == null){
			userLoginInfo.setLoginInfo("用户名不存在!");
			return userLoginInfo;
			//登录密码错误
		}else if(!userFromDB.getUserPassword().equals(user.getUserPassword())){
			userLoginInfo.setLoginInfo("密码错误!");
			return userLoginInfo;
		/*
		 * 正确登录
		 * 记录上一次登录的时间地点
		 * 将本次登录的时间地点记录到数据库中
		 */
		}else{
			Date lastLoginDate=userFromDB.getUserDate();
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String lastLoginTime=simpleDateFormat.format(lastLoginDate);
			userLoginInfo.setLastLoginTime(lastLoginTime);
			userLoginInfo.setLastLoginAddress(userFromDB.getUserAddress());
			userDao.login(user);
			return userLoginInfo;
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see starter.user.service.UserService#selectUserByUserName(java.lang.String)
	 * 根据用户名称查询用户
	 */
	@Override
	public User selectUserByUserName(String userName) throws Exception {
		User user=userDao.selectUserByUserName(userName);
		return user;
	}
	
	

}
