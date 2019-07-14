package cn.cai.microservice.entiy;

import java.io.Serializable;

//如果采用的是JdkSerializationRedisSerializer序列化器序列化value，则存储对象必须实现Serializable接口
//其他序列化器，可以不实现
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	private String userName;
	private String password;
	private String email;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "User [userName=" + userName + ", password=" + password + ", email=" + email + "]";
	}
}
