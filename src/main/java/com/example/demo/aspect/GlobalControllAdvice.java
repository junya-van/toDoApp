package com.example.demo.aspect;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.service.InsertFailureException;
import com.example.demo.service.TaskNotFoundException;
import com.example.demo.service.UpdateFailureException;

@ControllerAdvice
public class GlobalControllAdvice {
	
	/** INSERTに失敗した時の例外処理 */
	@ExceptionHandler(InsertFailureException.class)
	public String insertUserFailureHandler(InsertFailureException e, Model model) {
		
		model.addAttribute("error", "");
		model.addAttribute("message", e.getMessage());
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
		
		return "error";
		
	}
	
	/** UPDATEに失敗した時の例外処理 */
	@ExceptionHandler(UpdateFailureException.class)
	public String updateFailureHandler(UpdateFailureException e, Model model) {
		
		model.addAttribute("error", "");
		model.addAttribute("message", e.getMessage());
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
		
		return "error";
		
	}
	
	/** タスクが見つからなかった時の例外処理 */
	@ExceptionHandler(TaskNotFoundException.class)
	public String getTaskFailureHandler(TaskNotFoundException e, Model model) {
		
		model.addAttribute("error", "");
		model.addAttribute("message", e.getMessage());
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
		
		return "error";
		
	}
	
	/** データベース関連の例外処理 */
	@ExceptionHandler(DataAccessException.class)
	public String dataAccessExceptionHandler(DataAccessException e, Model model) {
		
		// 空文字をセット
		model.addAttribute("error", "");
		
		// メッセージをModelに登録
		model.addAttribute("message", "DataAccessExceptionが発生しました");
		
		// HTTPのエラーコード(500)をModelに登録
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
		
		return "error";
		
	}
	
	/** その他の例外処理 */
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e, Model model) {
		
		// 空文字をセット
		model.addAttribute("error", "");
		
		// メッセージをModelに登録
		model.addAttribute("message", "Exceptionが発生しました");
		
		// HTTPのエラーコード(500)をModelに登録
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
		
		return "error";
		
	}
	
}
