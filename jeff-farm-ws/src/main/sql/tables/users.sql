-- DROP TABLE users;

CREATE TABLE IF NOT EXISTS users
(
	id INT PRIMARY KEY AUTO_INCREMENT,
	user_name VARCHAR(20) UNIQUE NOT NULL,
	user_password VARCHAR(20) NOT NULL, -- TODO: this is stored as plaintext (NOT DESIRED)
	first_name VARCHAR(255),
	last_name VARCHAR(255),
	created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
	modified_date DATETIME ON UPDATE CURRENT_TIMESTAMP
);