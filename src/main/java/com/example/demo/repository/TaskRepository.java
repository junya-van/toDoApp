package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Task;

public interface TaskRepository {
	
	List<Task> findAll(String userId); 
	
	int insert(Task task);
	
	Optional<Task> findTask(int taskId);
	
	int update(Task task);
	
	int deleteTask(int taskId);
	
	List<Task> findByType(int typeId, String userId);
	
}
