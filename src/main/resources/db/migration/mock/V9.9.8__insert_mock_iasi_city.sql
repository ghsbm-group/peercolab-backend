--Iași

insert into city(name, country_id)
values ('Iași', 4); --id:31

insert into university(name, city_id)
   values ('Universitatea Tehnică „Gheorghe Asachi” (TUIASI)', 31),--43
          ('Universitatea de Ştiinţele Vieţii “Ion Ionescu de la Brad” (USV)', 31), --44
          ('Universitatea „Alexandru Ioan Cuza" (UAIC)', 31),--45
          ('Universitatea de Medicina si Farmacie "Grigore T. Popa" (UMFIASI)', 31),--46
          ('Universitatea Națională de Arte „George Enescu” (UNAGE)', 31); --47
--TUIASI id: 43
insert into faculty(name, university_id)
values ('Facultatea de Arhitectură „G.M. Cantacuzino“',43),
       ('Facultatea de Automatică şi Calculatoare',43),
       ('Facultatea de Inginerie Chimică şi Protecţia Mediului „Cristofor Simionescu”',43),
       ('Facultatea de Construcţii şi Instalaţii',43),
       ('Facultatea de Construcţii de Maşini şi Management Industrial',43),
       ('Facultatea de Electronică, Telecomunicaţii şi Tehnologia Informaţiei',43),
       ('Facultatea de Inginerie Electrică, Energetică şi Informatică Aplicată',43),
       ('Facultatea de Hidrotehnică, Geodezie şi Ingineria Mediului',43),
       ('Facultatea de Mecanică',43),
       ('Facultatea de Ştiinţa şi Ingineria Materialelor',43),
       ('Facultatea de Textile Pielărie şi Management Industrial',43);

-- USV id: 44
insert into faculty(name, university_id)
values ('Facultatea de Agricultură',44),
       ('Facultatea de Horticultură',44),
       ('Facultatea de Ingineria Resurselor Animale și Alimentare',44),
       ('Facultatea de Medicină Veterinară',44);

--UAIC id: 45

insert into faculty(name, university_id)
values ('Facultatea de Biologie',45),
       ('Facultatea de Chimie',45),
       ('Facultatea de Drept',45),
       ('Facultatea de Economie și Administrarea Afacerilor',45),
       ('Facultatea de Educație Fizică și Sport',45),
       ('Facultatea de Filosofie și Științe Social-Politice',45),
       ('Facultatea de Fizică',45),
       ('Facultatea de Geografie și Geologie',45),
       ('Facultatea de Informatică',45),
       ('Facultatea de Istorie',45),
       ('Facultatea de Litere',45),
       ('Facultatea de Matematică',45),
       ('Facultatea de Psihologie și Științe ale Educației',45),
       ('Facultatea de Teologie Ortodoxă',45),
       ('Facultatea de Teologie Romano-Catolică',45);

-- UMFIASI id: 46
insert into faculty(name, university_id)
values ('Facultatea de Medicină',46),
       ('Facultatea de Medicină Dentară',46),
       ('Facultatea de Farmacie',46),
       ('Facultatea de Bioinginerie Medicală',46);

-- UNAGE id:47
insert into faculty(name, university_id)
values ('Facultatea de Interpretare, Compoziție și Studii Muzicale Teoretice',47),
       ('Facultatea de Teatru',47),
       ('Facultatea de Arte Vizuale și Design',47);


