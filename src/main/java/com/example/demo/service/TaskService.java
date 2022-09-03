package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Task;

public interface TaskService {
	
	List<Task> getAll(String userId);
	
	void insert(Task task);
	
	Optional<Task> getTask(int taskId);
	
	void update(Task task);
	
	void deleteTask(int taskId);
	
	List<Task> getByTask(int taskId, String userId);
	
}
