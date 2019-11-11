package com.service;

import java.io.Serializable;

import com.beans.User;

public interface UserService extends Serializable{
	
	User validateUser(User user);
	
	Long saveUser(User user);

}
