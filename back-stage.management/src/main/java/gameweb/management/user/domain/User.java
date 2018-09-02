package gameweb.management.user.domain;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
	private Integer user_id;
	private String username;
	private String user_password;
	//用户登录的IP
	private String user_ip;
	//用户登录的地址
	private String user_address;
	//用户登录的时间
	private Date user_login_date;
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public String getUser_ip() {
		return user_ip;
	}
	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
	}
	public String getUser_address() {
		return user_address;
	}
	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}
	public Date getUser_login_date() {
		return user_login_date;
	}
	public void setUser_login_date(Date user_login_date) {
		this.user_login_date = user_login_date;
	}
	
	
}
