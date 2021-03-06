DROP FUNCTION IF EXISTS read_farm;
CREATE FUNCTION read_farm(IN id INT)
RETURNS SETOF farms
AS
$body$
	SELECT
		  f.id
		, f.name
		, f.location
		, f.created_date
		, f.modified_date
	FROM farms AS f
	WHERE f.id = read_farm.id;
$body$
LANGUAGE SQL;
