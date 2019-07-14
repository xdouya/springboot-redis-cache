package cn.cai.microservice.dao;

import cn.cai.microservice.entiy.User;

public interface UserDao {
	User getUserInfoByName(String userName);
	
	void updateUserInfo(User user);
	
	void deleteUserInfo(String userName);
	
	void insertUserInfo(User user);
}
