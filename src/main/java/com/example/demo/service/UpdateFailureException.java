package com.example.demo.service;

/**
 * 独自のエラークラス
 * 更新に失敗時のエラークラス
 */
public class UpdateFailureException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UpdateFailureException(String msg) {
		super(msg);
	}
	
}
