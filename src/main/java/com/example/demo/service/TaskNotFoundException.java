package com.example.demo.service;

/**
 * 独自エラークラス
 * タスクが見つからない場合の例外クラス
 */
public class TaskNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	TaskNotFoundException(String msg) {
		
		super(msg);
		
	}
	
}
