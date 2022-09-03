package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

/**
 * userテーブルクラス
 */
@Data
public class User implements UserDetails{
	
	private String user_id;
	private String user_name;
	private String password;
	private String role;
	private LocalDateTime created_at;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return AuthorityUtils.createAuthorityList(role);
	}
	@Override
	public String getUsername() {
		
		return this.user_id;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}
	
	
}
