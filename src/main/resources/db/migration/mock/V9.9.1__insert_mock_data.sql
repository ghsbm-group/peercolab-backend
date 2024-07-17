insert into country (name)
values ('Germany'),--5
       ('France'),--6
       ('Italy'),--7
       ('China');--8

insert into city (name, country_id)
values ('Berlin', 5),--9
       ('Paris', 6),--10
       ('Lyon', 6),--11
       ('Milano', 7),--12
       ('Rome', 7),--13
       ('Beijing', 8);--14

insert into university(name, city_id)
values  ('Hertie School',9),--3
        ('Sapienza', 13),--4
        ('Sorbonne', 10),--5
        ('CY Cergy', 10),--6
        ('Claude Bernard', 11);--7

insert into faculty(name, university_id)
values ('Civil society', 3),--4
        ('Financial and fiscal policy', 3),--5
        ('Architecture',4),--6
        ('Pharmacy and Medicine',4),--7
        ('Arts and Humanities',5),--8
        ('Tech', 5),--9
        ('Law and Political Science', 5),--10
        ('Biology', 7),--11
        ('Earth science', 7),--12
        ('Mathematics', 7);--13
insert into department(name, faculty_id)
values ('CS en', 4),
       ('CS de', 4),
       ('Civil Architecture', 6),
       ('Human Biology', 8),
       ('Plant Biology', 8),
       ('Animal Biology', 8),
       ('Law en', 10),
       ('Law fr', 10);
insert into "class"(name, department_id, start_year ,no_of_study_years,no_of_semesters_per_year)
values ('30234', 2, 2017, 4, 2),
       ('30231', 2, 2018, 4, 2),
       ('30432', 2, 2019, 4, 2),
       ('30411', 1, 2020, 4, 2);




