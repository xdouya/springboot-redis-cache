package cn.cai.microservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cn.cai.microservice.dao.UserDao;
import cn.cai.microservice.entiy.User;

@Service
public class UserService {
	private static final Logger log = LoggerFactory.getLogger(UserService.class);	
	@Autowired
	private UserDao userDao;	
	
//	注解@Cacheable：如果缓存之中能够查找到，就不执行该注解下面的方法；
//	如果找不到，就执行方法，将方法的返回结果作为value防止到缓存中；
	@Cacheable(value="userInfoCache", key="#userName")
	public User getUserInfoByName(String userName) {
		log.info("查询" + userName + "信息");
		User user = userDao.getUserInfoByName(userName);
		return user;
	}

//	注解@CachePut：不管缓存之中能否查找到，该注解下的方法都会被执行，且用返回结果作为value更新缓存
	@CachePut(value="userInfoCache", key="#user.userName")
	public User updateUserInfo(User user) {
		log.info("更新" + user.getUserName() + "信息");
		userDao.updateUserInfo(user);	
		return user;
	}

//	注解@CachePut：删除指定key的缓存 
	@CacheEvict(value="userInfoCache", key="#userName")
	public void deleteUserInfo(String userName) {
		log.info("删除" + userName + "信息");
		userDao.deleteUserInfo(userName);	
	}
	
	@CachePut(value="userInfoCache", key="#user.userName")
	public User insertUserInfo(User user) {
		userDao.insertUserInfo(user);
		return user;
	}
	
}
