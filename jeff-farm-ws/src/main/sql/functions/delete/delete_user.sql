CREATE OR REPLACE FUNCTION delete_user
	( IN id INT
	)
RETURNS VOID
AS
$body$
	DELETE
	FROM user_roles AS ur
	USING users AS u
	WHERE u.user_name = ur.user_name AND u.id = id;

	DELETE
	FROM users AS u
	WHERE u.id = delete_user.id;
$body$
LANGUAGE SQL;
