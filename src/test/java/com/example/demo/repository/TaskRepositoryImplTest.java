package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.example.demo.entity.Task;

@SpringJUnitConfig
@SpringBootTest
@ActiveProfiles("unit")
//@Sql
class TaskRepositoryImplTest {
	
	@Autowired
	private TaskRepositoryImpl taskRepository;
	
	@Test
	@DisplayName("findAllのテスト")
	void findAll() {
		
		var list = taskRepository.findAll("user1");
		
		// 件数のチェック
		assertEquals(2, list.size());
		
		// 2件目のレコードの取得
		var task2 = list.get(1);
		assertNotNull(task2);
		
		// 各カラムの値が正しくセットされているか
		assertEquals("user1", task2.getUser_id());
		assertEquals("Springセキュリティを学習", task2.getTitle());
		assertEquals("Springセキュリティの認証と許可を学習する", task2.getDetail());
		assertEquals("重要", task2.getType());
		
		// まだ1件もタスクが作成されていない
		var list2 = taskRepository.findAll("user3");
		assertEquals(0, list2.size());
		
	}
	
	@Test
	@DisplayName("insertのテスト(正常系)")
	void insert() {
		
		var task = new Task();
		task.setUser_id("user2");
		task.setType_id(1);
		task.setTitle("テストをする");
		task.setDetail("JUnitを使ってテストする");
		task.setDeadline(LocalDateTime.now());
		
		var insertCount = taskRepository.insert(task);
		
		assertEquals(1, insertCount);
		
		var taskList = taskRepository.findAll("user2");

		// 件数のチェック
		assertEquals(2, taskList.size());
		
		// 登録されたレコードの取得
		var taskx = taskList.get(1);
				
		// 各カラムの値が正しくセットされているか
		assertEquals(task.getUser_id() ,taskx.getUser_id());
		assertEquals(task.getType_id(), taskx.getType_id());
		assertEquals(task.getTitle(), taskx.getTitle());
		assertEquals(task.getDetail(), taskx.getDetail());
		assertEquals(task.getDeadline(), taskx.getDeadline());
		
	}
	
	@Test
	@DisplayName("updateのテスト(正常系)")
	void update1() {
		
		var task = new Task();
		task.setTask_id(2);
		task.setUser_id("user1");
		task.setType_id(3);
		task.setTitle("Springセキュリティを学習テスト");
		task.setDetail("Springセキュリティを学習するべし");
		task.setDeadline(LocalDateTime.now());
		
		var updateCount = taskRepository.update(task);
		
		assertEquals(1, updateCount);
		
		// レコードの存在確認
		Optional<Task> taskOpt = taskRepository.findTask(2);
		assertTrue(taskOpt.isPresent());
		
		// 各カラムの値が正しくセットされているか
		Task task2 = taskOpt.get();
		assertEquals(task.getTask_id(), task2.getTask_id());
		assertEquals(task.getUser_id(), task2.getUser_id());
		assertEquals(task.getType_id(), task2.getType_id());
		assertEquals(task.getTitle(), task2.getTitle());
		assertEquals(task.getDetail(), task2.getDetail());
		assertEquals(task.getDeadline(), task2.getDeadline());
		
	}
	
	@Test
	@DisplayName("updateのテスト(更新対象がない場合)")
	void update2() {
		
		var task = new Task();
		task.setTask_id(0);
		var updateCount = taskRepository.update(task);
		assertEquals(0, updateCount);
		
	}
	
	@Test
	@DisplayName("deleteTaskのテスト(正常系)")
	void delete1() {
		
		var deleteCount = taskRepository.deleteTask(1);
		assertEquals(1, deleteCount);
		
	}
	
	@Test
	@DisplayName("deleteTaskのテスト(削除対象がない場合)")
	void delete2() {
		
		var deleteCount = taskRepository.deleteTask(0);
		assertEquals(0, deleteCount);
		
	}
	
	@Test
	@DisplayName("findByTypeのテスト")
	void findByType() {
		
		var list = taskRepository.findByType(1, "user1");
		assertEquals(1, list.size());
		
		Task task = list.get(0);
		assertNotNull(task);
		
		assertEquals("user1", task.getUser_id());
		assertEquals(1, task.getType_id());
		assertEquals("JUnitを学習", task.getTitle());
		assertEquals("テストの仕方を学習する", task.getDetail());
		
	}

}
