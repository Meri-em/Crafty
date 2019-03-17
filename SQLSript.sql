USE craft;
CREATE TABLE user (
	id CHAR(36) NOT NULL,
	email VARCHAR(255) NOT NULL,
	password VARCHAR(100) NOT NULL,
	role VARCHAR(100) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE (email)
);

CREATE TABLE author (
    id CHAR(36) NOT NULL,
	name VARCHAR(255) NOT NULL,
	PRIMARY KEY (id)
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

REPLACE INTO user VALUES
('2bee9ef1-7a94-41e3-81d9-4a38e2a6bec8','maria.karcheva@yahoo.com','$e0801$OGKNxhbBab6TpdqAs0l61GND5E2n3tk5plM1J77CYFY=$/XMXe6swplcNEj3reL4Rwg3boLGljXqXzOJ5hyfpPKg=','MEMBER');

REPLACE INTO author VALUES
('f180cc79-5856-4985-9794-26b7a787bec2', 'Кръстина Филипова');

REPLACE INTO item VALUES
('cd8c8983-dc7a-4570-97ca-ed2a218b18ba','Възглавница с бухали','Възглавница с бухали описание','PILLOW','2019-03-08 12:12:01', 12.5,'f180cc79-5856-4985-9794-26b7a787bec2'),
('64605868-ab4d-40a1-9b1e-28c5ac5a7ae0', 'Възглавница пролет', 'Възглавница пролет описание', 'PILLOW','2019-03-01 10:15:01', 16.5,'f180cc79-5856-4985-9794-26b7a787bec2'),
('52038cd8-91b7-4852-abec-fa8281edef68', 'Възглавница Star wars', 'Възглавница Star wars описание', 'PILLOW','2019-03-07 23:11:32', 14,'f180cc79-5856-4985-9794-26b7a787bec2');

REPLACE INTO item_image VALUES
('9ed87836-d42f-4fbf-a5f4-cc38b481d243','1.jpg','cd8c8983-dc7a-4570-97ca-ed2a218b18ba',1),
('97151fa9-fdb6-4b75-9556-4fa2a5a87df6','2.jpg','cd8c8983-dc7a-4570-97ca-ed2a218b18ba',2),
('5de23216-5f93-488b-a034-27872f130269','1.jpg','64605868-ab4d-40a1-9b1e-28c5ac5a7ae0',1),
('5c2af457-a07f-4c0f-95ae-ff9e297dc581','2.jpg','64605868-ab4d-40a1-9b1e-28c5ac5a7ae0',2),
('9d9af93b-b1c7-47b6-9f7a-606e2d2009a8','1.jpg','52038cd8-91b7-4852-abec-fa8281edef68',1),
('a9073774-8366-4d37-96fb-ce66910576cb','2.jpg','52038cd8-91b7-4852-abec-fa8281edef68',2);

