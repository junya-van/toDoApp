package com.example.demo.repository;

import com.example.demo.entity.User;

public interface UserRepository {
	
	User findLoginUser(String userId);
	
}
