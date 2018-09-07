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
	 * �û���¼
	 */
	@Override
	public UserLoginInfo login(User user) throws Exception {
		UserLoginInfo userLoginInfo=new UserLoginInfo();
		User userFromDB=selectUserByUserName(user.getUserName());
		//�����û���û�в�ѯ����Ӧ���û���Ϣ
		if(userFromDB == null){
			userLoginInfo.setLoginInfo("�û���������!");
			return userLoginInfo;
			//��¼�������
		}else if(!userFromDB.getUserPassword().equals(user.getUserPassword())){
			userLoginInfo.setLoginInfo("�������!");
			return userLoginInfo;
		/*
		 * ��ȷ��¼
		 * ��¼��һ�ε�¼��ʱ��ص�
		 * �����ε�¼��ʱ��ص��¼�����ݿ���
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
	 * �����û����Ʋ�ѯ�û�
	 */
	@Override
	public User selectUserByUserName(String userName) throws Exception {
		User user=userDao.selectUserByUserName(userName);
		return user;
	}
	
	

}
