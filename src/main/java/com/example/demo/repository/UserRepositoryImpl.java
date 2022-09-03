package com.example.demo.repository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
public class UserRepositoryImpl implements UserRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public User findLoginUser(String userId) {
		
		String sql = "SELECT * FROM user WHERE user_id = ?";
		Map<String, Object> map = jdbcTemplate.queryForMap(sql, userId);
		
		User user = new User();
		user.setUser_id((String) map.get("user_id"));
		user.setUser_name((String) map.get("user_name"));
		user.setPassword((String) map.get("password"));
		user.setRole((String) map.get("role"));
		
		return user;
		
	}
		
}
