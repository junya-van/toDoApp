package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("TaskServiceImplの単体テスト")
class TaskServiceImplUnitTest {
	
	/**
	 * モック(stub)
	 */
	@Mock
	private TaskRepository taskRepository;
	
	/**
	 * テスト対象
	 */
	@InjectMocks	
	private TaskServiceImpl taskServiceImpl;
	
	@Test
	@DisplayName("テーブルtaskの全件取得で0件の場合のテスト")
	void testGetAllReturnEmptyList() {
		
		
		List<Task> list = new ArrayList<>();
		
		// モックのI/Oをセット
		when(taskRepository.findAll("testuser1")).thenReturn(list);
		
		// サービスを実行
		List<Task> actualList = taskServiceImpl.getAll("testuser1");
		
		// モックの指定メソッドの実行回数を検査
		verify(taskRepository, times(1)).findAll("testuser1");
		
		assertEquals(0, actualList.size());
		
	}
	
	@Test
	@DisplayName("テーブルTaskの全件取得で2件の場合のテスト")
	void testGetAllReturnList() {
		
		// モックから返すListに2つのTaskオブジェクトをセット
		List<Task> list = new ArrayList<>();
		Task task1 = new Task();
		Task task2 = new Task();
		list.add(task1);
		list.add(task2);
		
		when(taskRepository.findAll("testuser1")).thenReturn(list);
		
		List<Task> actualList = taskServiceImpl.getAll("testuser1");
		
		verify(taskRepository, times(1)).findAll("testuser1");
		
		assertEquals(2, actualList.size());
		
	}
	
	@Test
	@DisplayName("タスクが取得できない場合のテスト")
	void testGetTaskThrowException() {
		
		when(taskRepository.findTask(0)).thenThrow(new EmptyResultDataAccessException(1));
		
		try {
			taskServiceImpl.getTask(0);
		} catch(TaskNotFoundException e) {
			assertEquals(e.getMessage(), "指定されたタスクが存在しません");
		}
		
	}
	
	@Test
	@DisplayName("タスクを1件取得した場合のテスト")
	void testGetTaskReturnOne() {
		
		Task task = new Task();
		Optional<Task> taskOpt = Optional.ofNullable(task);
		when(taskRepository.findTask(1)).thenReturn(taskOpt);
		
		Optional<Task> taskActual = taskServiceImpl.getTask(1);
		
		verify(taskRepository, times(1)).findTask(1);
		
		assertTrue(taskActual.isPresent());
		
	}
	
	@Test
	@DisplayName("存在しないidの場合メソッドが実行されない事を確認するテスト")
	void throwNotFoundException() {
		
		when(taskRepository.deleteTask(0)).thenReturn(0);
		
		try {
			taskServiceImpl.deleteTask(0);
		} catch(TaskNotFoundException e) {
			assertEquals(e.getMessage(), "削除するタスクが存在しません");
		}
		
	}
	
	@Test
	@DisplayName("特定のタイプのタスク取得で0件の場合のテスト")
	void testGetByTypeReturnEmpty() {
		List<Task> list = new ArrayList<>();
		when(taskRepository.findByType(0, "testUser")).thenReturn(list);
		
		List<Task> actualList = taskServiceImpl.getByTask(0, "testUser");
		
		verify(taskRepository, times(1)).findByType(0, "testUser");
		assertEquals(0, actualList.size());
	}
	
	@Test
	@DisplayName("特定のタイプのタスク取得で2件の場合のテスト")
	void testGetByTypeReturnList() {
		
		List<Task> list = new ArrayList<>();
		Task task1 = new Task();
		Task task2 = new Task();
		list.add(task1);
		list.add(task2);
		
		when(taskRepository.findByType(1, "testUser")).thenReturn(list);
		
		List<Task> actualList = taskServiceImpl.getByTask(1, "testUser");
		
		verify(taskRepository, times(1)).findByType(1, "testUser");
		assertEquals(2, actualList.size());
		
	}

}
