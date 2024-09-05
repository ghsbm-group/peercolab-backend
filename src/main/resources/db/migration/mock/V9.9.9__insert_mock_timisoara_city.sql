--Timișoara

insert into city(name, country_id)
values ('Timișoara', 4); --id:32

insert into university(name, city_id)
   values ('Universitatea Politehnică Timișoara (UPT)', 32),--48
          ('Universitatea de Ştiințele Vieții „Regele Mihai I Al României” (USAMBVT)', 32), --49
          ('Universitatea de Vest din Timișoara (UVT)', 32),--50
          ('Universitatea de Medicină și Farmacie „Victor Babeș" (UMFT)', 32); --51
--UPT id: 48
insert into faculty(name, university_id)
values ('Facultatea de Arhitectură şi Urbanism',48),
       ('Facultatea de Automatică şi Calculatoare',48),
       ('Facultatea de Inginerie Chimică, Biotehnologii și Protecția Mediului',48),
       ('Facultatea de Construcţii',48),
       ('Facultatea de Electronică, Telecomunicaţii și Tehnologii Informaționale',48),
       ('Facultatea de Inginerie Electrică și Energetică',48),
       ('Facultatea de Inginerie din Hunedoara',48),
       ('Facultatea de Management în Producţie şi Transporturi',48),
       ('Facultatea de Mecanică',48),
       ('Facultatea de Stiinţe ale Comunicării',48);

--USAMBVT id: 49
insert into faculty(name, university_id)
values ('Facultatea de Agricultură',49),
       ('Facultatea de Inginerie și Tehnologii Aplicate',49),
       ('Facultatea de Management și Turism Rural',49),
       ('Facultatea de Medicină Veterinară',49),
       ('Facultatea de Inginerie Alimentară',49),
       ('Facultatea de Bioingineria Resurselor Animaliere',49);

--UVT id: 50
insert into faculty(name, university_id)
values ('Facultatea de Arte și Design',50),
       ('Facultatea de Chimie, Biologie, Geografie',50),
       ('Facultatea de Drept',50),
       ('Facultatea de Economie și de Administrare a Afacerilor',50),
       ('Facultatea de Educație Fizică și Sport',50),
       ('Facultatea de Fizică',50),
       ('Facultatea de Litere, Istorie și Teologie',50),
       ('Facultatea de Matematică și Informatică',50),
       ('Facultatea de Muzică și Teatru',50),
       ('Facultatea de Sociologie și Psihologie',50),
       ('Facultatea de Științe Politice, Filosofie și Științe ale Comunicării',50);

--UMFT id: 51
insert into faculty(name, university_id)
values ('Facultatea de Medicină',51),
       ('Facultatea de Asisitență Medicală',51),
       ('Facultatea de Medicină Dentară',51),
       ('Facultatea de Farmacie',51);