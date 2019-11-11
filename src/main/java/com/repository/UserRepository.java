package com.repository;

import java.io.Serializable;

import com.beans.User;

public interface UserRepository extends Serializable {

	Long save(User user);
}
