package com.example.demo.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Task;

@Repository
public class TaskRepositoryImpl implements TaskRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * ユーザIDを基にタスク一覧を取得
	 */
	@Override
	public List<Task> findAll(String userId) {
		
		String sql = "SELECT task.task_id, task.type_id, task.title, task.detail, task.deadline, task_type.type FROM task"
				+ " INNER JOIN task_type ON task.type_id = task_type.type_id"
				+ " WHERE user_id = ?";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, userId);
		
		List<Task> taskList = new ArrayList<>();
		for(Map<String, Object> map : list) {
			
			Task task = new Task();
			task.setTask_id((int) map.get("task_id"));
			task.setUser_id(userId);
			task.setType_id((int) map.get("type_id"));
			task.setTitle((String) map.get("title"));
			task.setDetail((String) map.get("detail"));
			task.setDeadline(((Timestamp) map.get("deadline")).toLocalDateTime());
			task.setType((String) map.get("type"));
			
			taskList.add(task);
			
		}
		
		return taskList;
	}
	
	/**
	 * タスクを1件新規登録
	 */
	@Override
	public int insert(Task task) {
		
		String sql = "INSERT INTO task(user_id, type_id, title, detail, deadline) VALUES(?, ?, ?, ?, ?)";
		return jdbcTemplate.update(sql, task.getUser_id(), task.getType_id(), task.getTitle(), task.getDetail(), task.getDeadline());
		
	}
	
	/**
	 * タスクIDを基に1件のタスクを取得
	 */
	@Override
	public Optional<Task> findTask(int taskId) {
		
		String sql = "SELECT task.task_id, task.user_id, task.type_id, task.title, task.detail, task.deadline, task_type.type FROM task"
				+ " INNER JOIN task_type ON task.type_id = task_type.type_id"
				+ " WHERE task_id = ?";
		Map<String, Object> map = jdbcTemplate.queryForMap(sql, taskId);
		
		Task task = new Task();
		task.setTask_id((int) map.get("task_id"));
		task.setUser_id((String) map.get("user_id"));
		task.setType_id((int) map.get("type_id"));
		task.setTitle((String) map.get("title"));
		task.setDetail((String) map.get("detail"));
		task.setDeadline(((Timestamp) map.get("deadline")).toLocalDateTime());
		task.setType((String) map.get("type"));
		
		// taskをOptionalでラップする
		Optional<Task> taskOpt = Optional.ofNullable(task);
		
		return taskOpt;
		
	}
	
	/**
	 * 1件のタスクを更新
	 */
	@Override
	public int update(Task task) {
		
		String sql = "UPDATE task SET type_id = ?, title = ?, detail = ?, deadline = ? WHERE task_id = ?";
		return jdbcTemplate.update(sql, task.getType_id(), task.getTitle(), task.getDetail(), task.getDeadline(), task.getTask_id());
		
	}
	
	/**
	 * タスクを1件削除
	 */
	@Override
	public int deleteTask(int taskId) {
		
		String sql = "DELETE FROM task WHERE task_id = ?";
		return jdbcTemplate.update(sql, taskId);
	
	}
	
	/**
	 * 特定のタイプのタスクのリストを取得
	 */
	@Override
	public List<Task> findByType(int typeId, String userId) {
		
		String sql = "SELECT task.task_id, task.user_id, task.type_id, task.title, task.detail, task.deadline, task_type.type FROM task"
				+ " INNER JOIN task_type ON task.type_id = task_type.type_id"
				+ " WHERE task_type.type_id = ? AND user_id = ?";
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, typeId, userId);
		
		List<Task> taskList = new ArrayList<>();
		for(Map<String, Object> map : list) {
			
			Task task = new Task();
			task.setTask_id((int) map.get("task_id"));
			task.setUser_id((String) map.get("user_id"));
			task.setType_id((int) map.get("type_id"));
			task.setTitle((String) map.get("title"));
			task.setDetail((String) map.get("detail"));
			task.setDeadline(((Timestamp) map.get("deadline")).toLocalDateTime());
			task.setType((String) map.get("type"));
			
			taskList.add(task);
			
		}
		
		return taskList;
		
	}

}
