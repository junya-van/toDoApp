package com.example.demo.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignupForm {
	
	@NotNull (message = "ユーザーIDを入力してください")
	@Size(min = 5, max = 20, message = "5文字以上20文字以下で入力してください")
	private String user_id;
	
	@NotNull (message = "ユーザー名を入力してください。")
	@Size(max = 50, message = "50文字以内で入力して下さい。")
	private String user_name;
	
	@NotNull
	@Size(max = 50, message = "50文字以内で入力して下さい。")
	private String password;
	
}
