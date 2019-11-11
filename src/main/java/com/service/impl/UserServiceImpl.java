package com.service.impl;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.beans.User;
import com.repository.UserRepository;
import com.service.UserService;

@ManagedBean(name = "userService")
@SessionScoped
public class UserServiceImpl implements UserService {

	private static final long serialVersionUID = 1186987175499333079L;

	@ManagedProperty(value = "#{userRepository}")
	UserRepository userRepository;

	@Override
	public boolean validateUser(User user) {
		return true;
	}

	@Override
	public Long saveUser(User user) {
		return userRepository.save(user);
	}

}
