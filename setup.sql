DROP DATABASE IF EXISTS craft;
CREATE DATABASE craft CHARACTER SET UTF8 COLLATE utf8_bin;

USE craft;

CREATE TABLE member (
	id CHAR(36) NOT NULL,
	first_name VARCHAR(255),
	last_name VARCHAR(255),
	PRIMARY KEY(id)
);

CREATE TABLE author (
	id CHAR(36) NOT NULL,
	name VARCHAR(255) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE `user` (
	id CHAR(36) NOT NULL,
	email VARCHAR(255) NOT NULL,
	password VARCHAR(100) NOT NULL,
	role VARCHAR(100) NOT NULL,
	member_id CHAR(36),
	author_id CHAR(36),
	last_logout_date DATETIME,
	PRIMARY KEY (id),
	FOREIGN KEY (member_id) REFERENCES member(id),
	FOREIGN KEY (author_id) REFERENCES author(id),
	UNIQUE (email)
);

CREATE TABLE item (
	id CHAR(36) NOT NULL,
	name VARCHAR(255) NOT NULL,
	description TEXT,
	category VARCHAR(100),
	created_at DATETIME NOT NULL,
	price DOUBLE,
	author_id CHAR(36) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (author_id) REFERENCES author(id)
);

CREATE TABLE item_image (
	id CHAR(36) NOT NULL,
	name VARCHAR(100) NOT NULL,
	item_id CHAR(36) NOT NULL,
	`order` INT(11) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (item_id) REFERENCES item(id)
);

CREATE TABLE favourite (
	member_id CHAR(36) NOT NULL,
	author_id CHAR(36) NOT NULL,
	PRIMARY KEY (member_id, author_id),
	FOREIGN KEY (member_id) REFERENCES member(id),
	FOREIGN KEY (author_id) REFERENCES author(id)
);

REPLACE INTO member VALUES
	('ad8765aa-b33f-4e9f-a0b3-c11a26d8c2b0', 'Maria', 'Karcheva'),
	('834c8e56-6464-4143-acbc-b7e48e643a6c', 'Ангел', 'Димитров'),
	('9d700ade-ed19-4bb8-81f6-8733f4ec4ab0', NULL, NULL),
	('5ac2a7e9-9f7e-4ec2-83dc-bb28460f8e40', NULL, NULL),
	('cb97aac4-1b58-4563-b8f7-14060124d49e', NULL, NULL);

REPLACE INTO author VALUES
	('f180cc79-5856-4985-9794-26b7a787bec2', 'Арт точка'),
	('bdfbf791-95ba-401a-8461-8a8ecaf876dd', 'Дървен свят'),
	('91b8bcec-0c65-4aa6-8e9b-3af9a309c802', 'MoreThanSeaGlass'),
	('387927a1-f131-4a45-a7b7-b50969ff3cba', 'Цветно Китно');

REPLACE INTO user VALUES
	('2bee9ef1-7a94-41e3-81d9-4a38e2a6bec8','maria.karcheva@yahoo.com','$e0801$OGKNxhbBab6TpdqAs0l61GND5E2n3tk5plM1J77CYFY=$/XMXe6swplcNEj3reL4Rwg3boLGljXqXzOJ5hyfpPKg=',
	'MEMBER','ad8765aa-b33f-4e9f-a0b3-c11a26d8c2b0',NULL,NULL),
	('efd32e0e-9e96-4947-a6c4-614aafb77fc5','art@email.com','$e0801$OGKNxhbBab6TpdqAs0l61GND5E2n3tk5plM1J77CYFY=$/XMXe6swplcNEj3reL4Rwg3boLGljXqXzOJ5hyfpPKg=',
	'AUTHOR','834c8e56-6464-4143-acbc-b7e48e643a6c','f180cc79-5856-4985-9794-26b7a787bec2',NULL),
	('c2c45aea-14fd-4254-804b-606eaf465a69','dsviat@email.com','$e0801$OGKNxhbBab6TpdqAs0l61GND5E2n3tk5plM1J77CYFY=$/XMXe6swplcNEj3reL4Rwg3boLGljXqXzOJ5hyfpPKg=',
	'AUTHOR','9d700ade-ed19-4bb8-81f6-8733f4ec4ab0','bdfbf791-95ba-401a-8461-8a8ecaf876dd',NULL),
	('5b7d0db3-e3b2-41c0-b685-ac1410067313','sea@email.com','$e0801$OGKNxhbBab6TpdqAs0l61GND5E2n3tk5plM1J77CYFY=$/XMXe6swplcNEj3reL4Rwg3boLGljXqXzOJ5hyfpPKg=',
	'AUTHOR','5ac2a7e9-9f7e-4ec2-83dc-bb28460f8e40','91b8bcec-0c65-4aa6-8e9b-3af9a309c802',NULL),
	('2814620a-3497-42aa-9020-bba97dfdfb65','kitno@email.com','$e0801$OGKNxhbBab6TpdqAs0l61GND5E2n3tk5plM1J77CYFY=$/XMXe6swplcNEj3reL4Rwg3boLGljXqXzOJ5hyfpPKg=',
	'AUTHOR','cb97aac4-1b58-4563-b8f7-14060124d49e','387927a1-f131-4a45-a7b7-b50969ff3cba',NULL);

REPLACE INTO item VALUES
	('cd8c8983-dc7a-4570-97ca-ed2a218b18ba','Възглавница с бухали','Тази възглавница ще внесе красота и настроение във вашия дом.\n\nРазмери: 33х33см.\n\nМатериал: памучни платове, силиконов пълнеж\n\nhttp://www.owl-in-home.com','PILLOW','2019-03-08 12:12:01', 14.5,'f180cc79-5856-4985-9794-26b7a787bec2'),
	('64605868-ab4d-40a1-9b1e-28c5ac5a7ae0', 'Възглавница пролет', 'Тази възглавница ще внесе красота и настроение във вашия дом.\n\nРазмери: 33х33см.\n\nМатериал: памучни платове, силиконов пълнеж\n\nhttp://www.owl-in-home.com', 'PILLOW','2019-03-01 10:15:01', 14.5,'f180cc79-5856-4985-9794-26b7a787bec2'),
	('52038cd8-91b7-4852-abec-fa8281edef68', 'Възглавница Star Wars', 'За феновете на Star Wars това е отличен начин да декорирате своя дом.\n\nРазмери: 40х40см.\n\nМатериал: памук, силиконов пълнеж', 'PILLOW','2019-03-07 23:11:32', 20,'f180cc79-5856-4985-9794-26b7a787bec2'),
	('9f159664-9f73-4c17-aa76-297ddd4a5ca4', 'Обеци с авантюрин', 'Обеци със зелен авантюрин. Кукичите са направени от сребро 925. Големина 2-3 см\n\nwww.nakitiburleno.com', 'EARRINGS','2019-03-07 23:11:32', 20,'f180cc79-5856-4985-9794-26b7a787bec2'),
	('7b3ebcf3-73d5-4342-a653-eda72195d025', 'Обеци капки', 'Обеците пристигат в специално изработена кутия със сърце\n\nМатериали: Картинката върху обеците е отпечатена върху водоустойчива хартия и е защитена със стъклен кабошон\n\nРазмери:25х18мм', 'EARRINGS','2019-03-07 23:11:32', 10,'f180cc79-5856-4985-9794-26b7a787bec2'),
	('5dd0b033-c131-4544-93ee-815cb419d7dc', 'Обеци от полимерна глина', 'Нежни обеците изработени от полимерна глина и сребърни винтчета.\n\nРазмер : 2,5 см', 'EARRINGS','2019-03-07 23:11:32', 36,'f180cc79-5856-4985-9794-26b7a787bec2'),
	('749cfa82-bb71-407d-ab3c-6ac52c69a553', 'Обеци рози', 'Висококачествена изработка от розово злато. Обеците изключително нежни и са подходящи за всеки тоалет.\n\n.Материал:розово злато\n\nРазмери:10х10мм', 'EARRINGS','2019-03-07 23:11:32', 50,'f180cc79-5856-4985-9794-26b7a787bec2'),
	('708b4f7f-6f96-4df2-9049-31fcf5baa407', 'Обеци с издълбани морски звезди', 'Обеците са направени от изгладени от вълните стъкла, намерени на брега на морето.\n\nМатериали: морско стъкло, сребро 925. Размери: дължина 3см', 'EARRINGS','2019-03-07 23:11:32', 23,'91b8bcec-0c65-4aa6-8e9b-3af9a309c802'),
	('94c10ef1-74de-4864-9d8a-e70484899a41', 'Обеци с издълбани сърца', 'Обеците са направени от изгладени от вълните стъкла, намерени на брега на морето.\n\nМатериали: морско стъкло, сребро 925. Размери: дължина 3см', 'EARRINGS','2019-03-07 23:11:32', 26,'91b8bcec-0c65-4aa6-8e9b-3af9a309c802'),
	('45544ac1-a268-482f-a962-8cc173cacb8e', 'Обеци дървени капки', 'Уникални дървени обеци за тези, които искат да променят стила си.\n\nРазмери: 77х37мм', 'EARRINGS','2019-03-07 23:11:32', 13.5,'bdfbf791-95ba-401a-8461-8a8ecaf876dd'),
	('79b81e81-0da7-4763-80d5-fecab9359a99', 'Дървени кръгли обеци', 'Дървени обеци с интересен дизайн. Изработени са от дърво и неръждаема стомана', 'EARRINGS','2019-03-07 23:11:32', 13.4,'bdfbf791-95ba-401a-8461-8a8ecaf876dd'),
	('8eb162b7-45af-455b-b07b-693924994d08', 'Дървени обеци Монстера листа', 'Леки и елегантни за онези които ценят ръчно изработените продукти.\n\nРазмери:5х3.1см', 'EARRINGS','2019-03-07 23:11:32', 16,'bdfbf791-95ba-401a-8461-8a8ecaf876dd'),
	('a46019f9-7b56-457a-909a-da5bbb82c09e', 'Дървени обеци с тюркоазена смола', 'Леки и комфортни обеци за носене\n\nРазмери: 3х1.5см', 'EARRINGS','2019-03-07 23:11:32', 27,'bdfbf791-95ba-401a-8461-8a8ecaf876dd'),
	('244793c4-ac51-41ea-adb8-e7b7994676bd', 'Лале обеци', 'Дълги обеци от истинско лале в нежен тюркоазено син цвят, който ще ви донесе много настроение. Обеците са уникат, не могат да бъдат повторени.\n\nДължина на обеците с кукичките - около 5,5 см.\n\nКукички от медицинска стомана - не предизвикват алергия, не почерняват. Може да изберете и опция - сребърни кукички.\n\nОбеците ще получите в красива торбичка от органза, придружени със сертификат за качество.', 'EARRINGS','2019-03-07 23:11:32', 33,'387927a1-f131-4a45-a7b7-b50969ff3cba'),
	('9c3c5471-379d-44f6-825d-ac8e0ca9229c', 'Малки обеци от роза', 'Нежни обеци изработени от малки, пъстри листенца на роза.\n\nДължина на обеците с кукичките - около 3,5 см.\n\nСребърни кукички.\n\nОбеците ще получите в красива торбичка от органза, придружени със сертификат за качество.', 'EARRINGS','2019-03-07 23:11:32', 29,'387927a1-f131-4a45-a7b7-b50969ff3cba');

REPLACE INTO item_image VALUES
	('9ed87836-d42f-4fbf-a5f4-cc38b481d243','1.jpg','cd8c8983-dc7a-4570-97ca-ed2a218b18ba',1),
	('97151fa9-fdb6-4b75-9556-4fa2a5a87df6','2.jpg','cd8c8983-dc7a-4570-97ca-ed2a218b18ba',2),
	('5de23216-5f93-488b-a034-27872f130269','1.jpg','64605868-ab4d-40a1-9b1e-28c5ac5a7ae0',1),
	('5c2af457-a07f-4c0f-95ae-ff9e297dc581','2.jpg','64605868-ab4d-40a1-9b1e-28c5ac5a7ae0',2),
	('9d9af93b-b1c7-47b6-9f7a-606e2d2009a8','1.jpg','52038cd8-91b7-4852-abec-fa8281edef68',1),
	('e4ff7555-7e47-4beb-af77-1c2b81ec0adf','1.jpg','9f159664-9f73-4c17-aa76-297ddd4a5ca4',1),
	('7a49f4af-9b7c-4cf6-b9ff-c97fbd6efa22','1.jpg','7b3ebcf3-73d5-4342-a653-eda72195d025',1),
	('dd24d4fa-22d5-4bce-9b74-99e04700a07a','2.jpg','7b3ebcf3-73d5-4342-a653-eda72195d025',2),
	('960e0464-d673-448b-9308-021684f5ef5f','1.jpg','5dd0b033-c131-4544-93ee-815cb419d7dc',1),
	('a55621e2-f2b8-41c9-96bc-75dcf0816b9c','2.jpg','5dd0b033-c131-4544-93ee-815cb419d7dc',2),
	('384173cb-12a0-4fee-be68-745d51094c39','1.jpg','749cfa82-bb71-407d-ab3c-6ac52c69a553',1),
	('4be0db34-6a92-492a-bc84-c117ed1518a9','2.jpg','749cfa82-bb71-407d-ab3c-6ac52c69a553',2),
	('3c4c7326-e5bb-4405-83f5-f2b1fc9891c2','1.jpg','708b4f7f-6f96-4df2-9049-31fcf5baa407',1),
	('7daa2720-d199-460d-8b78-485d957a96ad','2.jpg','708b4f7f-6f96-4df2-9049-31fcf5baa407',2),
	('f41dd4ed-dd7f-4028-8283-2fb084078f24','1.jpg','94c10ef1-74de-4864-9d8a-e70484899a41',1),
	('c1785613-28cc-4d30-865e-e0adac14f29d','2.jpg','94c10ef1-74de-4864-9d8a-e70484899a41',2),
	('352dcd6d-de69-41a9-a3e4-0926335163ef','1.jpg','45544ac1-a268-482f-a962-8cc173cacb8e',1),
	('a3a05db0-d0a1-49bb-b77e-ae4eb3465998','2.jpg','45544ac1-a268-482f-a962-8cc173cacb8e',2),
	('18b59904-1e9f-4436-b3cd-113c6a6ea4c9','1.jpg','79b81e81-0da7-4763-80d5-fecab9359a99',1),
	('b99f17f6-7fb1-443a-a14c-a7295ce5fa6a','2.jpg','79b81e81-0da7-4763-80d5-fecab9359a99',2),
	('51ab6c29-ff64-456e-89ef-46f3fab45de3','1.jpg','8eb162b7-45af-455b-b07b-693924994d08',1),
	('f68c17a6-9553-4cc2-a5e0-158ba360033d','2.jpg','8eb162b7-45af-455b-b07b-693924994d08',2),
	('37728b66-60c0-4601-9ad2-324999013e3b','1.jpg','a46019f9-7b56-457a-909a-da5bbb82c09e',1),
	('c3c48f49-a598-49e9-99d0-351314b9e100','1.jpg','244793c4-ac51-41ea-adb8-e7b7994676bd',1),
	('fd6f4531-de8c-4e33-a80a-0c0da62ce229','2.jpg','244793c4-ac51-41ea-adb8-e7b7994676bd',2),
	('89640f19-50d2-4ecd-9871-e6079614e3c0','1.jpg','9c3c5471-379d-44f6-825d-ac8e0ca9229c',1),
	('b8839dbe-c679-40b9-90c0-a26b131a501c','2.jpg','9c3c5471-379d-44f6-825d-ac8e0ca9229c',2);

REPLACE INTO favourite VALUES
	('ad8765aa-b33f-4e9f-a0b3-c11a26d8c2b0', 'f180cc79-5856-4985-9794-26b7a787bec2'),
	('ad8765aa-b33f-4e9f-a0b3-c11a26d8c2b0', '91b8bcec-0c65-4aa6-8e9b-3af9a309c802');

CREATE TABLE category (
	id VARCHAR(50) NOT NULL,
	name VARCHAR(50) NOT NULL,
	parent VARCHAR(50),
	`order` INT(11),
	PRIMARY KEY (id),
	FOREIGN KEY (parent) REFERENCES category(id)
);

REPLACE INTO catgeory VALUES
	('HOLIDAYS', 'Празници', NULL, 0),
	('JEWELRY', 'Бижута', NULL, 2),
	('HOME', 'За дома', NULL, 1),
	('CLOTHING', 'Облекло', NULL, 3),
	('EASTER', 'Великден', 'HOLIDAYS', 0),
	('PILLOW', 'Възглавници', 'HOME', 0),
	('VASE', 'Вази', 'HOME', 1),
	('CLOCK', 'Чосовници', 'HOME', 2),
	('T-SHIRT', 'Тениски', 'CLOTHING', 0),
	('SCARF', 'Рисувани шалове', 'CLOTHING', 1),
	('EARRINGS', 'Обеци', 'JEWELRY', 0),
	('BRACELET', 'Гривни', 'JEWELRY', 1),
	('JEWELRY_SET', 'Колиета', 'JEWELRY', 3),
	('NECKLACE', 'Комплекти', 'JEWELRY', 2);