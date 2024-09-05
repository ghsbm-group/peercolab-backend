update university
set name = 'Universitatea Tehnică din Cluj-Napoca (UTCN)'
where id = 1;

update university
set name = 'Universitatea Babeș-Bolyai (UBB)'
where id = 2;

--public universities in Cluj-Napoca
insert into university(name, city_id)
   values ('Universitatea de Științe Agricole și Medicină Veterinară (USAMV)', 7),
       ('Universitatea de Medicină și Farmacie „Iuliu Hațieganu (UMF)”', 7),
       ('Academia Națională de Muzică „Gheorghe Dima” (ANMGD)', 7),
       ('Universitatea de Artă și Design (UAD)', 7);

-- UTCN faculties

insert into faculty(name, university_id)
   values ('Facultatea de Arhitectură și Urbanism',1),
   ('Facultatea de Autovehicule Rutiere, Mecatronică și Mecanică',1),
   ('Facultatea de Construcții',1),
   ('Facultatea de Ingineria Materialelor și a Mediului',1),
   ('Facultatea de Inginerie a Instalațiilor',1),
   ('Facultatea de Inginerie Electrică',1),
   ('Facultatea de Inginerie Industrială, Robotică și Managementul Producției',1);

update faculty
set name = 'Facultatea de Automatică și Calculatoare'
where id = 1;

update faculty
set name = 'Facultatea de Electronica, Telecomunicații și Tehnologia Informației'
where id = 2;

-- UBB faculties

insert into faculty(name, university_id)
values ('Facultatea de Matematică și Informatică',2),
       ('Facultatea de Fizică',2),
       ('Facultatea de Chimie și Inginerie Chimică',2),
       ('Facultatea de Biologie și Geologie',2),
       ('Facultatea de Geografie',2),
       ('Facultatea de Știința și Ingineria Mediului',2),
       ('Facultatea de Drept',2),
       ('Facultatea de Litere',2),
       ('Facultatea de Istorie și Filosofie',2),
       ('Facultatea de Sociologie și Asistență Socială',2),
       ('Facultatea de Psihologie și Științe ale Educației',2),
       ('Facultatea de Științe Economice și Gestiunea Afacerilor',2),
       ('Facultatea de Studii Europene',2),
       ('Facultatea de Business',2),
       ('Facultatea de Științe Politice, Administrative și ale Comunicării',2),
       ('Facultatea de Educație Fizică și Sport',2),
       ('Facultatea de Teologie Ortodoxă',2),
       ('Facultatea de Teologie Greco-Catolică',2),
       ('Facultatea de Teologie Reformată și Muzică',2),
       ('Facultatea de Teologie Romano-Catolică',2),
       ('Facultatea de Teatru și Film',2),
       ('Facultatea de Inginerie',2),
       ('School of Health',2);

--USAMV faculties

insert into faculty(name, university_id) --8
  values ('Facultatea de Agricultură',8),
         ('Facultatea de Horticultură și Afaceri în Dezvoltare Rurală',8),
         ('Facultatea de Zootehnie şi Biotehnologii',8),
         ('Facultatea de Medicină Veterinară',8),
         ('Facultatea de Ştiinţa şi Tehnologia Alimentelor',8),
         ('Facultatea de Silvicultură și Cadastru ', 8);

--UMF faculties

insert into faculty(name, university_id) --9
  values ('Facultatea de Medicină',9),
         ('Facultatea de Medicină Dentară',9),
         ('Facultatea de Farmacie',9);

--ANMGD faculties

insert into faculty(name, university_id) --10
  values ('Facultatea de Interpretare Muzicală',10),
         ('Facultatea Teoretică',10);

--UAD faculties

insert into faculty(name, university_id) --11
  values ('Facultatea de Arte Plastice',11),
         ('Facultatea de Arte Decorative şi Design',11);

