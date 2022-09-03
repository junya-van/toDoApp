INSERT INTO task_type VALUES
(1, '緊急', '最優先で取りかかるべきタスク'),
(2, '重要', '期限で間に合わせるべき'),
(3, 'できれば', '今後やってみたいアイデア');

INSERT INTO user VALUES
('user1', 'ユーザー1', '$2a$10$ta5JNvz5cuXl5qU/wloF5eRCbOALp2MbaKBgZb9Us8nI//1U6cpdO', 'USER', '2022-3-27 00:00:00'),
('user2', 'ユーザー2', 'user2pass', 'USER', '2022-3-27 00:00:00'),
('user3', 'ユーザー3', 'user3pass', 'USER', '2022-3-27 00:00:00');

INSERT INTO task VALUES
(NULL, 'user1', 1, 'JUnitを学習', 'テストの仕方を学習する', '2022-03-27 00:00:00'),
(NULL, 'user1', 2, 'Springセキュリティを学習', 'Springセキュリティの認証と許可を学習する', '2022-03-27 00:11:00'),
(NULL, 'user2', 3, 'サービスの自作', 'マイクロサービスを作る', '2022-03-28 00:00:00');

