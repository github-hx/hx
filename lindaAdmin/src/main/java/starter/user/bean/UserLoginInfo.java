package starter.user.bean;

public class UserLoginInfo {
	private User user;
	//��¼��ʾ��Ϣ
	private String loginInfo;
	//���һ�ε�¼ʱ��
	private String lastLoginTime;
	//���һ�ε�¼��ַ
	private String lastLoginAddress;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getLoginInfo() {
		return loginInfo;
	}
	public void setLoginInfo(String loginInfo) {
		this.loginInfo = loginInfo;
	}
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getLastLoginAddress() {
		return lastLoginAddress;
	}
	public void setLastLoginAddress(String lastLoginAddress) {
		this.lastLoginAddress = lastLoginAddress;
	}
	
}
