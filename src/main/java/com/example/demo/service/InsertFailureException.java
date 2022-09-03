package com.example.demo.service;

/**
 * 独自エラークラス
 * ユーザー登録に失敗した時のエラー処理をする
 */
public class InsertFailureException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public InsertFailureException(String msg) {
		
		super(msg);
		
	}
	
}
