--UTCN id:1

-- Arhitectura si Urbanism id:14

insert into department(name, faculty_id)
values ('Arhitectură',14),
       ('Urbanism și Științe Tehnice',14);

-- Automatica si Calculatoare id:1

UPDATE department
set name=' Calculatoare si Tehnologia Informației'
where name = 'Calculatoare si tehnologia informatiei';

UPDATE department
set name='Calculatoare (engleză)'
where name = 'Calculatoare en';

UPDATE department
set name='Automatică și Informatică Aplicată'
where name = 'Automatica en';

insert into department(name, faculty_id)
values ('Automatică și Informatică Aplicată (engleză)',1),
       ('Automatică și Informatică Aplicată - Satu-Mare',1);


--Facultatea de Electronica, Telecomunicații și Tehnologia Informației id:2
insert into department(name, faculty_id)
values ('Inginerie Electronică, Telecomunicații și Tehnologii Informaționale',2),
       ('Inginerie și Management',2);

--Facultatea de Autovehicule Rutiere, Mecatronică și Mecanică id:15

insert into department(name, faculty_id)
values ('Ingineria Autovehiculelor',15),
       ('Ingineria Transporturilor',15),
       ('Inginerie Mecanică', 15),
       ('Mecatronică și Robotică', 15);

--Facultatea de Construcții id:16

insert into department(name, faculty_id)
values ('Inginerie Civilă',16),
       ('Inginerie și Management',16),
       ('Inginerie Geodezică', 16);

--Facultatea de Ingineria Materialelor și a Mediului id:17

insert into department(name, faculty_id)
values ('Ingineria Materialelor',17),
       ('Ingineria Mediului',17);

--Facultatea de Inginerie a Instalațiilor id:18

insert into department(name, faculty_id)
values ('Ingineria Instalațiilor',18);

--Facultatea de Inginerie Electrică id:19

insert into department(name, faculty_id)
values ('Inginerie Electrică',19),
       ('Inginerie Energetică',19),
       ('Științe Inginerești Aplicate', 19),
       ('Inginerie și Management',19),
       ('Științe Inginerești Aplicate - Bistriţa',19),
       ('Inginerie Electrică - Bistrița', 19);

--Facultatea de Inginerie Industrială, Robotică și Managementul Producției id:20

insert into department(name, faculty_id)
values ('Inginerie Industrială',20),
       ('Inginerie Industrială - Bistrița',20),
       ('Inginerie Industrială - Satu Mare', 20),
       ('Inginerie Industrială - Alba Iulia',20),
       ('Inginerie și Management',20),
       ('Inginerie și Management - Bistrița', 20),
       ('Inginerie și Management - Alba Iulia',20),
       ('Mecatronică și Robotică', 20),
       ('Mecatronică și Robotică - Bistrița', 20);

--Universitatea Babeș-Bolyai (UBB) id:2

--Facultatea de Matematică și Informatică id:21

insert into department(name, faculty_id)
values ('Matematică',21),
       ('Matematică (maghiară)',21),
       ('Matematică informatică', 21),
       ('Matematică informatică (maghiară)',21),
       ('Matematică informatică (engleză)',21),
       ('Informatică', 21),
       ('Informatică (maghiară)',21),
       ('Informatică (engleză)',21),
       ('Informatică (germană)', 21),
       ('Inteligență artificială (engleză)', 21),
       ('Ingineria informației (maghiară)', 21),
       ('Ingineria informației (engleză)', 21);

--Facultatea de Fizică id:22

insert into department(name, faculty_id)
values ('Fizică',22),
       ('Fizică (maghiară)',22),
       ('Fizică informatică', 22),
       ('Fizică informatică (maghiară)',22),
       ('Fizică medicală', 22),
       ('Fizică tehnologică',22),
       ('Fizică tehnologică (maghiară)', 22);

--Facultatea de Chimie și Inginerie Chimică id:23

insert into department(name, faculty_id)
values ('Chimie',23),
       ('Chimie (maghiară)',23),
       ('Chimie farmaceutică', 23),
       ('Chimia şi ingineria substanţelor organice, petrochimie şi carbochimie',23),
       ('Chimia şi ingineria substanţelor organice, petrochimie şi carbochimie (maghiară)', 23),
       ('Chimie alimentară şi tehnologii biochimice',23),
       ('Ingineria substanţelor anorganice şi protecţia mediului', 23),
       ('Ingineria şi informatica proceselor chimice şi biochimice',23),
       ('Inginerie biochimică',23),
       ('Ştiinţa şi ingineria materialelor oxidice şi nanomateriale', 23);

--Facultatea de Biologie și Geologie id:24

insert into department(name, faculty_id)
values ('Biochimie',24),
       ('Biologie',24),
       ('Biologie (maghiară)', 24),
       ('Biologie ambientală',24),
       ('Geologie', 24),
       ('Geologie (maghiară)',24),
       ('Inginerie geologică', 24),
       ('Ecologie şi protecţia mediului (germană)',24),
       ('Ecologie şi protecţia mediului (maghiară)',24),
       ('Biotehnologii industriale', 24);

--Facultatea de Geografie id:25

insert into department(name, faculty_id)
values ('Cartografie',25),
       ('Geografia turismului',25),
       ('Geografia turismului (maghiară)', 25),
       ('Geografia turismului (maghiară) - Gheorgheni',25),
       ('Geografia turismului - Bistriţa', 25),
       ('Geografia turismului - Gheorgheni',25),
       ('Geografia turismului - Sighetu Marmaţiei', 25),
       ('Geografia turismului - Zalău',25),
       ('Geografia turismului (germană)',25),
       ('Geografie', 25),
       ('Geografie (maghiară)',25),
       ('Hidrologie şi meteorologie',25),
       ('Planificare teritorială', 25),
       ('Planificare teritorială (maghiară)',25);

--Facultatea de Știința și Ingineria Mediului id:26

insert into department(name, faculty_id)
values ('Ingineria mediului',26),
       ('Ştiinţa mediului',26),
       ('Ştiinţa mediului (maghiară)', 26),
       ('Ştiinţa mediului (maghiară) - Sfântu Gheorghe',26),
       ('Management și audit de mediu', 26);

--Facultatea de Drept id:27

insert into department(name, faculty_id)
values ('Drept',27);

--Facultatea de Litere id: 28

INSERT INTO department(name, faculty_id)
VALUES ('Filologie clasică', 28),
       ('Limba şi literatura română', 28),
       ('Literatură universală şi comparată', 28),
       ('Limba şi literatura maghiară (monospecializare)', 28),
       ('Limba şi literatura maghiară', 28),
       ('Limba şi literatura engleză', 28),
       ('Limba şi literatura franceză', 28),
       ('Limba şi literatura germană', 28),
       ('Limba şi literatura rusă', 28),
       ('Limba şi literatura italiană', 28),
       ('Limba şi literatura spaniolă', 28),
       ('Limba şi literatura ucraineană', 28),
       ('Limba şi literatura norvegiană', 28),
       ('Limba şi literatura japoneză', 28),
       ('Limba şi literatura chineză', 28),
       ('Limba şi literatura coreeană', 28),
       ('Limba şi literatura finlandeză', 28),
       ('Limba şi literatura portugheză', 28),
       ('Limbi moderne aplicate', 28),
       ('Etnologie (maghiară)', 28),
       ('Studii culturale (maghiară)', 28);

--Facultatea de Istorie și Filosofie id:29

insert into department(name, faculty_id)
values ('Filosofie',29),
       ('Filosofie (maghiară)',29),
       ('Arheologie', 29),
       ('Arheologie (maghiară)',29),
       ('Arhivistică', 29),
       ('Arhivistică (maghiară)',29),
       ('Istoria artei',29),
       ('Istoria artei (maghiară)', 29),
       ('Istorie',29),
       ('Istorie (maghiară)', 29),
       ('Relaţii internaţionale şi studii europene', 29),
       ('Relaţii internaţionale şi studii europene (maghiară)', 29),
       ('Etnologie', 29),
       ('Turism cultural', 29),
       ('Turism cultural (maghiară)', 29),
       ('Științe ale informării și documentării', 29),
       ('Științe ale informării și documentării (maghiară)', 29),
       ('Studii de securitate', 29),
       ('Studii de securitate (engleză)', 29);

--Facultatea de Sociologie și Asistență Socială id:30

insert into department(name, faculty_id)
values ('Asistenţă socială',30),
       ('Asistenţă socială (maghiară)',30),
       ('Asistenţă socială - Reșița', 30),
       ('Antropologie',30),
       ('Antropologie (maghiară)', 30),
       ('Resurse umane',30),
       ('Resurse umane (maghiară)',30),
       ('Sociologie', 30),
       ('Sociologie (maghiară)',30);

--Facultatea de Psihologie și Științe ale Educației id:31

insert into department(name, faculty_id)
values ('Psihologie',31),
       ('Psihologie (maghiară)',31),
       ('Psihologie-Științe cognitive (engleză)', 31),
       ('Pedagogia învăţământului primar şi preşcolar',31),
       ('Pedagogia învăţământului primar şi preşcolar (maghiară)', 31),
       ('Pedagogia învăţământului primar şi preşcolar (germană)', 31),
       ('Pedagogia învăţământului primar şi preşcolar - Odorheiu Secuiesc)',31),
       ('Pedagogia învăţământului primar şi preşcolar (maghiară) - Odorheiu Secuiesc',31),
       ('Pedagogia învăţământului primar şi preşcolar (maghiară) - Târgu Mureş',31),
       ('Pedagogia învăţământului primar şi preşcolar - Năsăud', 31),
       ('Pedagogia învăţământului primar şi preşcolar - Sighetu-Marmației',31),
       ('Pedagogia învăţământului primar şi preşcolar - Târgu Mureş', 31),
       ('Pedagogia învăţământului primar şi preşcolar (maghiară) - Satu Mare', 31),
       ('Pedagogia învăţământului primar şi preşcolar - Reșița', 31),
       ('Pedagogie', 31),
       ('Psihopedagogie specială', 31),
       ('Psihopedagogie specială (maghiară)', 31);

--Facultatea de Științe Economice și Gestiunea Afacerilor id:32

insert into department(name, faculty_id)
values ('Economia comerţului, turismului şi serviciilor',32),
       ('Economia comerţului, turismului şi serviciilor (maghiară) - Sfântu Gheorghe',32),
       ('Economia comerţului, turismului şi serviciilor - Sfântu Gheorghe', 32),
       ('Economia comerţului, turismului şi serviciilor - Reșița',32),
       ('Economia firmei (maghiară) - Sfântu Gheorghe', 32),
       ('Administrarea afacerilor (germană)', 32),
       ('Informatică economică',32),
       ('Informatică economică (maghiară)',32),
       ('Informatică economică - Arad',32),
       ('Statistică şi previziune economică', 32),
       ('Contabilitate şi informatică de gestiune',32),
       ('Contabilitate şi informatică de gestiune (engleză)', 32),
       ('Contabilitate şi informatică de gestiune (franceză)', 32),
       ('Contabilitate şi informatică de gestiune - Sighetu Marmaţiei', 32),
       ('Contabilitate şi informatică de gestiune - Reșița', 32),
       ('Economie agroalimentară şi a mediului', 32),
       ('Economie generală', 32),
       ('Economie şi afaceri internaţionale', 32),
       ('Economie şi afaceri internaţionale (engleză)', 32),
       ('Finanţe şi bănci', 32),
       ('Finanţe şi bănci (engleză)', 32),
       ('Finanţe şi bănci (maghiară)', 32),
       ('Management', 32),
       ('Management (engleză)', 32),
       ('Management (maghiară)', 32),
       ('Marketing', 32),
       ('Marketing (maghiară)', 32),
       ('Marketing - Reșița', 32);

--Facultatea de Studii Europene id:33

insert into department(name, faculty_id)
values ('Management',33),
       ('Relaţii internaţionale şi studii europene',33),
       ('Relaţii internaţionale şi studii europene (engleză)', 33),
       ('Relaţii internaţionale şi studii europene (germană)',33),
       ('Diplomație în afaceri', 33),
       ('Administraţie europeană', 33);

--Facultatea de Business id:34

insert into department(name, faculty_id)
values ('Administrarea afacerilor',34),
       ('Administrarea afacerilor (engleză)',34),
       ('Administrarea afacerilor - Bistrița', 34),
       ('Administrarea afacerilor în servicii de ospitalitate',34),
       ('Administrarea afacerilor în servicii de ospitalitate (engleză)', 34);

--Facultatea de Științe Politice, Administrative și ale Comunicării id:35

insert into department(name, faculty_id)
values ('Administraţie publică',35),
       ('Administraţie publică (maghiară)',35),
       ('Administraţie publică (maghiară) - Sfântu Gheorghe', 35),
       ('Administraţie publică - Bistriţa',35),
       ('Administraţie publică - Satu Mare', 35),
       ('Administraţie publică - Sfântu Gheorghe',35),
       ('Administraţie publică - Reșița',35),
       ('Servicii şi politici de sănătate publică (Public Health)(engleză)', 35),
       ('Servicii şi politici de sănătate publică (Public Health)',35),
       ('Leadership în sectorul public', 35),
       ('Studii de conflict',35),
       ('Știința datelor sociale',35),
       ('Comunicare şi relaţii publice', 35),
       ('Comunicare şi relaţii publice (germană)',35),
       ('Comunicare şi relaţii publice (maghiară)', 35),
       ('Jurnalism',35),
       ('Jurnalism (engleză)',35),
       ('Jurnalism (germană)',35),
       ('Jurnalism (maghiară)', 35),
       ('Publicitate',35),
       ('Media digitală', 35),
       ('Ştiinţe politice',35),
       ('Ştiinţe politice (maghiară)',35),
       ('Ştiinţe politice (engleză)', 35);

-- Facultatea de Educație Fizică și Sport id:36

insert into department(name, faculty_id)
values ('Educaţie fizică şi sportivă',36),
       ('Educaţie fizică şi sportivă (maghiară)',36),
       ('Educaţie fizică şi sportivă - Bistriţa', 36),
       ('Educaţie fizică şi sportivă - Reșița',36),
       ('Sport și performanță motrică', 36),
       ('Sport și performanță motrică (maghiară)',36),
       ('Kinetoterapie şi motricitate specială',36),
       ('Kinetoterapie şi motricitate specială (maghiară)', 36);

--Facultatea de Teologie Ortodoxă id:37

insert into department(name, faculty_id)
values ('Artă sacră',37),
       ('Teologie ortodoxă asistenţă socială',37),
       ('Teologie ortodoxă didactică', 37),
       ('Teologie ortodoxă pastorală',37);

-- Facultatea de Teologie Greco-Catolică id:38

insert into department(name, faculty_id)
values ('Teologie greco-catolică asistenţă socială',38),
       ('Teologie greco-catolică didactică',38),
       ('Teologie greco-catolică pastorală', 38),
       ('Teologie greco-catolică pastorală - Blaj',38),
       ('Teologie greco-catolică pastorală - Oradea', 38);

--Facultatea de Teologie Reformată și Muzică id:39

insert into department(name, faculty_id)
values ('Muzica (maghiară)',39),
       ('Teologie reformată didactică (maghiară)',39);

--Facultatea de Teologie Romano-Catolică id:40

insert into department(name, faculty_id)
values ('Teologie romano-catolică didactică (maghiară)',40),
       ('Teologie romano-catolică pastorală (maghiară)',40);

--Facultatea de Teatru și Film id:41

insert into department(name, faculty_id)
values ('Cinematografie, fotografie, media (Regie de film şi TV)',41),
       ('Cinematografie, fotografie, media (Imagine film şi TV)',41),
       ('Cinematografie, fotografie, media (Regie de film şi TV) (maghiară)',41),
       ('Cinematografie, fotografie, media (Imagine film şi TV) (maghiară)', 41),
       ('Cinematografie, fotografie, media (Multimedia: sunet-montaj) (maghiară)',41),
       ('Filmologie',41),
       ('Artele spectacolului (actorie)', 41),
       ('Artele spectacolului (actorie) (maghiară)',41),
       ('Artele spectacolului (regie)', 41),
       ('Teatrologie (Jurnalism teatral, Management cultural) (maghiară)',41),
       ('Teatrologie (Jurnalism teatral, Management cultural)',41);

--Facultatea de Inginerie id:42

insert into department(name, faculty_id)
values ('Inginerie mecanică - Reșița',42),
       ('Electromecanică - Reșița',42),
       ('Informatică aplicată în inginerie electrică - Reșița', 42),
       ('Inginerie medicală - Reșița',42),
       ('Informatică industrială - Reșița',42);

--Universitatea de Științe Agricole și Medicină Veterinară (USAMV) id:8

--Facultatea de Agricultură id:44

insert into department(name, faculty_id)
values ('Agricultură',44),
       ('Agricultura ID',44),
       ('Exploatarea mașinilor și instalațiilor pentru agricultură și industrie alimentară',44),
       ('Montanologie', 44),
       ('Biologie', 44),
       ('Ingineria mediului', 44),
       ('Ingineria mediului ID', 44);

--Facultatea de Horticultură și Afaceri în Dezvoltare Rurală id:45

insert into department(name, faculty_id)
values ('Horicultură',45),
       ('Horicultura ID',45),
       ('Peisagistică',45),
       ('Ingineria și managementul afacerilor agricole',45),
       ('Inginerie și managemengt în alimentație publică și agroturism', 45),
       ('Inginerie și management în industria turismului', 45);

--Facultatea de Zootehnie şi Biotehnologii id:46

insert into department(name, faculty_id)
values ('Zootehnie',46),
       ('Piscicultură și acvacultură',46),
       ('Zootehnie ID',46),
       ('Biotehnologii', 46);

--Facultatea de Medicină Veterinară id:47

insert into department(name, faculty_id)
values ('Medicină veterinară',47),
       ('Medicină veterinară (engleză)',47),
       ('Medicină veterinară (franceză)',47);

--Facultatea de Ştiinţa şi Tehnologia Alimentelor id:48

insert into department(name, faculty_id)
values ('Controlul și expertiza produselor alimentare',48),
       ('Ingineria produselor alimentare',48),
       ('Tehnologia prelucrării produselor agricole',48),
       ('Tehnologia prelucrării produselor agricole ID',48);

--Facultatea de Silvicultură și Cadastru id:49

insert into department(name, faculty_id)
values ('Silvicultură',49),
       ('Măsurători terestre și cadastru',49),
       ('Măsurători terestre și cadastru (engleză)',49);


--Universitatea de Medicină și Farmacie „Iuliu Hațieganu (UMF)” id:9

--Facultatea de Medicină id:50

insert into department(name, faculty_id)
values ('Medicină',50),
       ('Asistență medicală generală',50),
       ('Asistență medicală generală - Baia Mare',50),
       ('Balneofiziokinetoterapie și recuperare', 50),
       ('Radiologie și imagistică', 50);

--Facultatea de Medicină Dentară id:51

insert into department(name, faculty_id)
values ('Medicină dentară',51),
       ('Tehnică dentară',51);

--Facultatea de Farmacie id:52

insert into department(name, faculty_id)
values ('Farmacie',52),
       ('Nutriție și dietetică',52),
       ('Cosmetică medicală și tehnologia produsului cosmetic', 52);

--Academia Națională de Muzică „Gheorghe Dima” (ANMGD) id:10

--Facultatea de Interpretare Muzicală id:53

insert into department(name, faculty_id)
values ('Interpretare muzicală – Instrumente',53),
       ('Interpretare muzicală – Canto',53),
       ('Artele spectacolului muzical', 53);

-- Facultatea Teoretică id:54

insert into department(name, faculty_id)
values ('Compoziţie',54),
       ('Muzicologie',54),
       ('Dirijat', 54),
       ('Muzică', 54),
       ('Muzică (engleză)', 54);

-- Universitatea de Artă și Design (UAD) id:11

--Facultatea de Arte Plastice id:55

insert into department(name, faculty_id)
values ('Arte plastice – Pictură',55),
       ('Arte plastice – Sculptură',55),
       ('Arte plastice – Grafică', 55),
       ('Arte plastice – Fotografie-Videoprocesare computerizată a imaginii', 55),
       ('Conservare și Restaurare', 55),
       ('Pedagogia artelor plastice şi decorative', 55),
       ('Ceramică-Sticlă-Metal', 55);

--Facultatea de Arte Decorative şi Design id:56

insert into department(name, faculty_id)
values ('Arte textile – Design textil',56),
       ('Modă – Design vestimentar',56),
       ('Design', 56),
       ('Istoria și Teoria artei', 56);