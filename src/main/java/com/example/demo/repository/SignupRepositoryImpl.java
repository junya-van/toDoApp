package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
public class SignupRepositoryImpl implements SignupRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int insertUser(User user) {
		
		return jdbcTemplate.update("INSERT INTO user(user_id, user_name, password, role, created_at) VALUES(?, ?, ?, ?, ?)",
				user.getUser_id(), user.getUser_name(), user.getPassword(), user.getRole(),user.getCreated_at());
		
	}

}
