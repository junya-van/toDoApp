package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@SpringBootTest
@ActiveProfiles("unit")
@DisplayName("TaskServiceImplの結合テスト")
class TaskServiceImplTest {

	@Test
	void test() {
		fail("まだ実装されていません");
	}

}
