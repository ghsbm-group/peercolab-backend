DELETE
FROM enrolment
where class_configuration_id = 4;

DELETE
FROM folder
where class_configuration_id = 4;

DELETE
FROM class_configuration
where id = 4;

DELETE
FROM enrolment
where class_configuration_id = 3;

DELETE
FROM folder
where class_configuration_id = 3;

DELETE
FROM class_configuration
where id = 3;

DELETE
FROM department
WHERE id = 4;

--delete unnecessary faculties
DELETE FROM faculty
WHERE id BETWEEN 3 AND 13;

--delete unnecessary universities
DELETE
FROM university
WHERE id BETWEEN 3 AND 7;

--delete unnecessary cities
DELETE FROM city
WHERE id BETWEEN 1 AND 6;

DELETE FROM city
WHERE id BETWEEN 9 AND 14;

--delete unnecessary countries

DELETE FROM country
WHERE id <> 4;
