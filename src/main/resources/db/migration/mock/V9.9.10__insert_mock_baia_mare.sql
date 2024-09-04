--Baia Mare

insert into city(name, country_id)
values ('Baia Mare', 4); --id:33

insert into university(name, city_id)
   values ('UTCN - Centrul Universitar Nord din Baia Mare (CUNBM)', 32);--52

--CUNBM id: 52
insert into faculty(name, university_id)
values ('Facultatea de Inginerie',52),
       ('Facultatea de Litere',52),
       ('Facultatea de Științe',52);