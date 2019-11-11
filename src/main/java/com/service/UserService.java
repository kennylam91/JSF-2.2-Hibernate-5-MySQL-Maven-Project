package com.service;

import java.io.Serializable;

import com.beans.User;

public interface UserService extends Serializable{
	
	boolean validateUser(User user);
	
	Long saveUser(User user);

}
