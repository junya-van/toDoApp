package com.example.demo.entity;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * taskテーブルクラス(task_typeテーブルと結合)
 */
@Data
public class Task {
	
	private int task_id;
	private String user_id;
	private int type_id;
	private String title;
	private String detail;
	private LocalDateTime deadline;
	
	private String type;
	
}
