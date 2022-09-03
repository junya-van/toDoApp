package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {
	
	@Autowired
	private TaskRepository taskRepository;
	
	/**
	 * ユーザーIDを基にタスク一覧を取得
	 */
	@Override
	public List<Task> getAll(String userId) {
		
		List<Task> taskList = taskRepository.findAll(userId);
		return taskList;
		
	}
	
	/**
	 * タスクを1件新規登録
	 */
	@Override
	public void insert(Task task) {
		
		taskRepository.insert(task);
		
	}
	
	/**
	 * タスクIDを基にタスクを1件取得
	 */
	@Override
	public Optional<Task> getTask(int taskId) {
		
		try {
			
			Optional<Task> taskOpt = taskRepository.findTask(taskId);
			return taskOpt;
			
		} catch(EmptyResultDataAccessException e) {
			
			//タスクIDが存在しなければ例外発生
			throw new TaskNotFoundException("指定されたタスクが存在しません");
			
		}
		
	}
	
	/**
	 * タスクを1件更新する
	 */
	@Override
	public void update(Task task) {
		
		int i = taskRepository.update(task);
		if(i == 0) {
			throw new UpdateFailureException("更新に失敗しました");
		}
		
	}
	
	/**
	 * タスクを1件削除する
	 */
	@Override
	public void deleteTask(int taskId) {
		
		int i = taskRepository.deleteTask(taskId);
		if(i == 0) {
			throw new TaskNotFoundException("削除するタスクが存在しません");
		}
		
	}

	@Override
	public List<Task> getByTask(int taskId, String userId) {
		
		return taskRepository.findByType(taskId, userId);
	}

}
