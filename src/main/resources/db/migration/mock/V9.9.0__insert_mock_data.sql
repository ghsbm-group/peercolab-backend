INSERT INTO country (name)
VALUES ('United States'),
       ('Canada'),
       ('United Kingdom'),
       ('Romania');

INSERT INTO city (name, country_id)
VALUES ('New York', 1),
       ('Los Angeles', 1),
       ('Toronto', 2),
       ('Vancouver', 2),
       ('London', 3),
       ('Manchester', 3),
       ('Cluj-Napoca', 4),
       ('Bucuresti', 4);
INSERT INTO university(name, city_id)
values ('UTCN', 7),
       ('UBB', 7);
INSERT INTO faculty(name, university_id)
values ('AC', 1),
       ('Telecomunicatii', 1),
       ('Drept', 2);

INSERT INTO department(name, faculty_id)
values ('Calculatoare en', 1),
       ('Calculatoare si tehnologia informatiei', 1),
       ('Automatica en', 1),
       ('Dreptul afacerilor', 3);