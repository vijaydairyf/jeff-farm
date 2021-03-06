CREATE OR REPLACE FUNCTION read_farms()
RETURNS SETOF farms
AS
$body$
	SELECT
		  f.id
		, f.name
		, f.location
		, f.created_date
		, f.modified_date
	FROM farms AS f;
$body$
LANGUAGE SQL;
