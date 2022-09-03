package com.example.demo.config;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 全てのControllerで共通処理を定義
**/
@ControllerAdvice
public class WebMvcControllerAdvice {
	
	/**
	 * 送信された空文字をnullに変換する
	 * @param dataBinder
	 */
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		
	}
	
}
