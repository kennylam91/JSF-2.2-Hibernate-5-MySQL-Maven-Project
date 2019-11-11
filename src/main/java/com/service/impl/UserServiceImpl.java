package com.service.impl;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.beans.User;
import com.repository.UserRepository;
import com.service.UserService;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@ManagedBean(name = "userService")
@SessionScoped
public class UserServiceImpl implements UserService {

	private static final long serialVersionUID = 1186987175499333079L;

	@ManagedProperty(value = "#{userRepository}")
	UserRepository userRepository;

	@Override
	public String validateUser(User user) {
		return userRepository.validateUser(user);
	}

	@Override
	public Long saveUser(User user) {
		return userRepository.save(user);
	}

}
