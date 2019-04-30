package com.soft.ware.service.impl;

import com.soft.ware.mapper.UserMapper;
import com.soft.ware.model.User;
import com.soft.ware.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
	@Resource
	private UserMapper userMapper;
	public void insert(User user){
		userMapper.insert(user);
	}

}
