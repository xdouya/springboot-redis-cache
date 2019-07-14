package cn.cai.microservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cn.cai.microservice.entiy.User;
import cn.cai.microservice.service.UserService;

@RestController
public class UserController {	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	
	@PostMapping("/get/UserInfo/{userName}")
	public User getUserInfoByName(@PathVariable(value="userName") String userName) {
		long startTime = System.currentTimeMillis();
		User user =  userService.getUserInfoByName(userName);
		log.info("查询耗时：" + (System.currentTimeMillis() - startTime) + "ms");
		return user;
	}
	
	@PostMapping("/update/UserInfo")
	public void updateUserInfo(@RequestBody User user) {
		userService.updateUserInfo(user);
	}
	
	@PostMapping("/delete/userInfo/{userName}")
	public void deleteUserInfo(@PathVariable(value="userName") String userName) {
		userService.deleteUserInfo(userName);
	}
	
	@PostMapping("/insert/UserInfo")
	public void insertUserInfo(@RequestBody User user) {
		userService.insertUserInfo(user);
	}
	
}
