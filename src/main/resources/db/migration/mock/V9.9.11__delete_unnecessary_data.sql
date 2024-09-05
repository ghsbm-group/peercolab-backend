--delete unnecessary faculties
DELETE FROM faculty
WHERE id BETWEEN 3 AND 13;

--delete unnecessary universities
DELETE FROM faculty
WHERE id BETWEEN 3 AND 7;


--delete unnecessary cities
DELETE FROM city
WHERE id BETWEEN 1 AND 6;

DELETE FROM city
WHERE id BETWEEN 9 AND 14;

--delete unnecessary countries

DELETE FROM country
WHERE id <> 4;
