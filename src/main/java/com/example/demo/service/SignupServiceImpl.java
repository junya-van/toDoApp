package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.SignupRepository;

@Service
public class SignupServiceImpl implements SignupService {
	
	@Autowired
	private SignupRepository signupRepository;
	
	@Autowired
	private PasswordEncoder encoder;

	@Override
	public void save(User user) {
		
		// パスワード暗号化
		String rawPassword = user.getPassword();
		user.setPassword(encoder.encode(rawPassword));
		
		int i = signupRepository.insertUser(user);
		
		if(i == 0) {
			
			throw new InsertFailureException("ユーザー登録に失敗しました");
			
		}

	}

}
