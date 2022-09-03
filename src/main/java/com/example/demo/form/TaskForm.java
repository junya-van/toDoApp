package com.example.demo.form;

import java.time.LocalDateTime;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class TaskForm {
	
	@Digits(integer = 1, fraction = 0)
	private int typeId;
	
	@NotNull (message = "内容を入力してください")
	@Size(min = 1, max = 20, message = "20文字以内で入力してください")
	private String title;
	
	@NotNull (message = "内容を入力してください")
	private String detail;
	
	@NotNull (message = "期限を設定してください。")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Future(message = "期限が過去に設定されています")
	private LocalDateTime deadline;
	
	private boolean newTask; // trueがセットされていれば新規タスク作成、falseならば編集
	
	public TaskForm() {}
	
	public TaskForm(int typeId, String title, String detail, LocalDateTime deadline, boolean newTask) {
		
		this.typeId = typeId;
		this.title = title;
		this.detail = detail;
		this.deadline = deadline;
		this.newTask = newTask;
		
	}
	
}
