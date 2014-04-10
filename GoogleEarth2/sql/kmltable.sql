/* CREATE TABLE IF NOT EXISTS geopoints ( */
CREATE TABLE geopoints (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	lat DECIMAL(9,6) NOT NULL,
	lng DECIMAL(9,6) NOT NULL,
	name VARCHAR(40)
	);

DELETE FROM geopoints;

INSERT INTO geopoints (id, lat, lng, name) VALUES
	(NULL, 48.1333, 11.5667, 'Muenchen'),
	(NULL, 47.8000, 13.0333, 'Salzburg'),
	(NULL, 47.8, 13.0833, 'Gnigl'),
	(NULL, 47.6831, 13.0969, 'Hallein'),
	(NULL, 49.4122, 8.7100, 'Heidelberg'),
	(NULL, 48.5200, 9.0556, 'Tuebigen'),
	(NULL, 49.2333, 9.1667, 'Bad Wimpfen'),
	(NULL, 48.7786, 9.1794, 'Stuttgart'),
	(NULL, 51.2167, 3.2333, 'Brugge'),
	(NULL, 51.2333, 2.9167, 'Ostende'),
	(NULL, 51.2167, 4.4000, 'Antwerpen'),
	(NULL, 48.2083, 16.3731, 'Wien'),
	(NULL, 51.2333, 6.7833, 'Duesseldorf'),
	(NULL, 51.5072, 0.1275, 'London');
/* ON DUPLICATE KEY UPDATE id=id; */

SELECT * FROM geopoints;

/* For now time is just an ordering, later it can be TIME and we 
   can make a GE tour out of these points */
CREATE TABLE trackhistory (
	id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
	trackid INTEGER NOT NULL,
	/*time INTEGER NOT NULL AUTO_INCREMENT KEY,*/
	time INTEGER,
	lat DECIMAL(9,6) NOT NULL,
	lng DECIMAL(9,6) NOT NULL,
	alt DECIMAL(9) NOT NULL
	);

DROP PROCEDURE IF EXISTS 'sp_table_modified';
CREATE PROCEDURE 'sp_table_modified'(IN tablename VARCHAR(40))
    UPDATE modifications SET updated_at = NOW() WHERE name=tablename;

DROP TRIGGER trackhistory_trigger;
CREATE TRIGGER trackhistory_trigger AFTER INSERT ON trackhistory FOR EACH ROW CALL sp_table_modified('trackhistory');

CREATE TABLE trackposition (
	trackid INTEGER NOT NULL PRIMARY KEY,
	lat DECIMAL(9,6) NOT NULL,
	lng DECIMAL(9,6) NOT NULL,
	alt DECIMAL(9) NOT NULL,
	heading DECIMAL(3) NOT NULL
	);
DROP TRIGGER trackposition_trigger;
CREATE TRIGGER trackposition_trigger AFTER UPDATE ON trackposition FOR EACH ROW CALL sp_table_modified('trackposition');

DROP TABLE circles;
CREATE TABLE circles (
	id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
	type INTEGER NOT NULL,
	center_lat DECIMAL(9,6) NOT NULL,
	center_lng DECIMAL(9,6) NOT NULL,
	center_alt DECIMAL(9,3) NOT NULL,
	radius DECIMAL(13,3) NOT NULL
);
INSERT INTO circles (id, type, center_lat, center_lng, center_alt, radius) VALUES
	(NULL, 1, 48.1333, 11.5667, 100, 2),
	(NULL, 1, -50.8000, -13.0333, 0, 3),
	(NULL, 1, 90.8, 45.0833, 1000, 4),
	(NULL, 1, -12.6831, 50.0969, 10, 5),
	(NULL, 1, 79.4122, 45.7100, 50000, 6);
SELECT * FROM circles;

DROP TABLE modifications;
CREATE TABLE modifications (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(40) NOT NULL,
    updated_at timestamp
);

INSERT INTO modifications (id, name, updated_at) VALUES
	(NULL, 'geopoints', NULL),
	(NULL, 'trackhistory', NULL),
	(NULL, 'trackposition', NULL),
	(NULL, 'circles', NULL);
SELECT * FROM modifications;



