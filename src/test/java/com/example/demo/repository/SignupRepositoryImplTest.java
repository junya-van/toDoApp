package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.example.demo.entity.User;

@SpringJUnitConfig
@SpringBootTest
@ActiveProfiles("unit")
//@Sql
class SignupRepositoryImplTest {
	
	@Autowired
	private SignupRepositoryImpl signupRepository;
	
	@Test
	@DisplayName("insertUserのテスト(正常系)")
	void test() {
		
		var user = new User();
		user.setUser_name("テスト1太郎");
		user.setPassword("test1pass");
		user.setCreated_at(LocalDateTime.now());
		
		var count = signupRepository.insertUser(user);
		
		assertEquals(1, count);
		
	}
	
	@Test
	@DisplayName("insertUserのテスト(ユーザー名の文字数オーバーで例外がスローされるか)")
	void test2() {
		
		var user = new User();
		user.setUser_name("123456789012345678901234567890123456789012345678901");
		user.setPassword("test1pass");
		user.setCreated_at(LocalDateTime.now());
		
		assertThrows(DataAccessException.class, () -> signupRepository.insertUser(user));
		
	}

}
