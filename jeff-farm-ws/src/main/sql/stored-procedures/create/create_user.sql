-- DELIMITER $$

DROP PROCEDURE IF EXISTS create_user$$

CREATE PROCEDURE create_user (
	IN user_name VARCHAR(20),
	IN user_password VARCHAR(86),
	IN first_name VARCHAR(255),
	IN last_name VARCHAR(255),
	OUT id INT)

	BEGIN
		INSERT INTO users (user_name, user_password, first_name, last_name)
		VALUES (user_name, user_password, first_name, last_name);

		SET id = LAST_INSERT_ID();

		INSERT INTO user_roles (user_name, role_name)
		VALUES (user_name, 'user');
	END$$

-- DELIMITER ;
