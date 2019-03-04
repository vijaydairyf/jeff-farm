-- DROP TABLE farms;
-- SELECT  * FROM farms LIMIT 0;

CREATE TABLE IF NOT EXISTS farms
(
	id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	location VARCHAR(255),
	created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
	modified_date DATETIME ON UPDATE CURRENT_TIMESTAMP
);