insert into address (id, country, city, postcode, street, other, phone) values (100, 'Magyarország', 'Budapest', '2024', 'Kanóc utca 13','első ajtó','+3619876542');
insert into address (id, country, city, postcode, street, other, phone) values (101, 'Sohaország', 'Budapest', '1122', 'Zsörbéki utca 14','első ajtó','+3619876543');
insert into address (id, country, city, postcode, street, phone) values (102, 'Magyarország', 'Celdömök', '3010', 'Nonum utca 5','+36202020');
insert into address (id, country, city, postcode, street, other, phone) values (103, 'Magyarország', 'Budapest', '1122', 'Zsámbéki utca 14','alsó kapu','+3619876544');
insert into address (id, country, city, postcode, street, phone) values (104, 'Magyarország', 'Budapest', '1241', 'Kisház utca 10','+361765487');
insert into address (id, country, city, postcode, street) values (105, 'Ukrajna', 'Budapest', '1122', 'Guzsajas u 6');
insert into address (id, country, city, postcode, street, phone) values (106, 'Sohaország', 'Gőzangyal', '1000', 'Tribün köz','+36111111');

insert into person (id, lastname, firstname, email, phone, other, gender) values (200, 'Pipogya', 'Márton','kuncm@gmail.com','+36201231234','semmi',1);
insert into person (id, lastname, firstname, email, phone, other, gender) values (201, 'Boroskót', 'Katus','borospiskota@gmail.com','+36201231234','tényleg',2);

insert into person (id, lastname, firstname, nickname, birthdate, birthplace, email, phone, address_id, father_id, mother_id, nameday, religion, personcardid, tajid, omid, studentcardid, gender, foodsensitivity, disease, other) values (202, 'Tökély','Tóbiás', 'Toncsi','1906-01-21 01:01:01','Budapest','daunera@gmail.com', '+36205290243', 100, 200, 201, '05-25', 1, '123456ME', '110220330','74444560230','1234567890',1,'liszt, tej','rák','nincs' ) ;	
insert into person (id, lastname, firstname, nickname, gender, religion, birthdate) values (203, 'Kis','Joana', 'Toby', 2, 1, '2000-05-27 01:01:01');
insert into person (id, lastname, firstname, nickname, gender, religion, birthdate) values (208, 'Nyúl','Béla', 'Arnold', 1, 0,'1999-05-30 01:01:01');

insert into scout (id, ecsetcode, person_id, status, joindate, leavedate, scoutcard) values(300,'DG3041-6', 202, 1, '2015-01-01 01:01:01', '2015-01-01 01:01:01', 2);
insert into scout (id, ecsetcode, person_id, status) values(301,'CB3081-8', 203, 2);
insert into scout (id, ecsetcode, person_id, status) values(306,'ABCDEF-0', 208, 1);

insert into leader (id, username, password, salt, scout_id, leadername, food, becomeleader, leaderpromise, keynum, god, uniformer) values (400,'admin','$2a$10$zpsVY2/dc/W4Qndx4uG6/.zsnSgY3zbsgKsvpGvQvWMqwAndR8rqS', '$2a$10$zpsVY2/dc/W4Qndx4uG6/.', 300,'Mukaka','itóka', '2015-01-01 01:01:01', '2015-01-01 01:01:01','36900', 1, 0);
insert into leader (id, username, password, salt, scout_id, god, uniformer) values (401,'kis','$2a$10$zpsVY2/dc/W4Qndx4uG6/.zsnSgY3zbsgKsvpGvQvWMqwAndR8rqS', '$2a$10$zpsVY2/dc/W4Qndx4uG6/.', 301, 0, 1);
insert into leader (id, username, password, salt, scout_id, god, uniformer) values (402,'ad','$2a$10$zpsVY2/dc/W4Qndx4uG6/.zsnSgY3zbsgKsvpGvQvWMqwAndR8rqS', '$2a$10$zpsVY2/dc/W4Qndx4uG6/.', 306, 0, 0);

insert into userroles (id, username, role, leader_id) values (1000, 'admin', 'ADMIN', 400);
insert into userroles (id, username, role, leader_id) values (1001, 'kis', 'ADMIN', 401);
insert into userroles (id, username, role, leader_id) values (1002, 'ad', 'ADMIN', 402);

insert into promise (id, scout_id, type, date, place) values (500, 300, 1, '2016-01-01 01:01:01', 'Budapest');
insert into promise (id, scout_id, type, date, place) values (501, 300, 2, '2015-01-01 01:01:01', 'Budapest');
insert into promise (id, scout_id, type, date, place) values (502, 300, 3, '2018-01-01 01:01:01', 'Budapest');

insert into challenge (id, scout_id, type, date, place) values (600, 300, 3, '2016-01-01 01:01:01', 'Budapest');
insert into challenge (id, scout_id, type) values (601, 300, 4);
insert into challenge (id, scout_id, type, date) values (602, 300, 5, '2018-01-01 01:01:01');
insert into challenge (id, scout_id, type, place) values (603, 300, 3, 'Budapest');

insert into qualification (id, scout_id, type, date, place, course) values (700, 300, 0, '2014-01-01 01:01:01', 'Budapest', 'KCSŐVK');
insert into qualification (id, scout_id, type, place, course) values (701, 300, 1, 'Budapest', 'STVK 23/A');
insert into qualification (id, scout_id, type, date) values (702, 300, 3, '2016-01-01 01:01:01');

insert into fee (id, scout_id, year, amount, support, completedate, status) values (801, 300, 2016, 3000, 0, '2016-05-10 01:01:01', 1);
insert into fee (id, scout_id, year, amount, support, completedate, contributor, status) values (800, 300, 2017, 3000, 3000, '2017-10-12 01:01:01', 'apja', 4);
insert into fee (id, scout_id, year, amount, completedate, status) values (802, 300, 2018, 3000, '2018-03-03 01:01:01', 3);

insert into patrol (id, name, gender, birthdate) values (900, 'Mókuscica', 1, '2018-01-01 01:01:01');
insert into patrol (id, name, gender) values (901, 'Kankalin', 2);

insert into person (id, lastname, firstname, gender, religion, birthdate) values (204, 'Kis','János', 1, 1, '1997-06-06 01:01:01');
insert into person (id, lastname, firstname, gender, religion, birthdate) values (205, 'Nagy','Julia', 2, 2, '1993-05-01 01:01:01');
insert into person (id, lastname, firstname, gender, religion, birthdate) values (206, 'Pék','János', 1, 3, '1990-05-26 01:01:01');
insert into person (id, lastname, firstname, gender, religion, birthdate) values (207, 'Lakat','Klára', 2, 1, '2002-05-19 01:01:01');

insert into scout (id, ecsetcode, person_id, status) values(302,'FB3081-1', 204,1);
insert into scout (id, ecsetcode, person_id, status) values(303,'HB3081-2', 205,1);
insert into scout (id, ecsetcode, person_id, status) values(304,'ZB3081-3', 206,1);
insert into scout (id, ecsetcode, person_id, status) values(305,'JB3081-4', 207,1);

insert into patrol_scout(patrol_id, scout_id) values (900, 300);
insert into patrol_scout(patrol_id, scout_id) values (900, 302);
insert into patrol_scout(patrol_id, scout_id) values (900, 306);
insert into patrol_scout(patrol_id, scout_id) values (900, 304);

insert into patrol_scout(patrol_id, scout_id) values (901, 303);
insert into patrol_scout(patrol_id, scout_id) values (901, 305);

insert into patrol_leader(patrol_id, leader_id) values (900, 400);
insert into patrol_leader(patrol_id, leader_id) values (901, 401);
insert into patrol_leader(patrol_id, leader_id) values (901, 400);

insert into troop(id, name) values (3000, 'Szent Tóbiás');
insert into troop_patrol(troop_id, patrol_id) values (3000, 900);
insert into troop_patrol(troop_id, patrol_id) values (3000, 901);
insert into troop_leader(troop_id, leader_id) values (3000, 400);
insert into troop_leader(troop_id, leader_id) values (3000, 401);

insert into news(id, created, expire, submitter_id, text) values (2000, '2018-05-20 01:01:01', '2018-09-28 01:01:01', 400, 'Több tízezer macska rohanta meg Tokió utcáit, Godzilla se tudott semmit tenni :(');
insert into news(id, created, expire, submitter_id, text) values (2001, '2018-05-23 01:01:01', '2018-07-04 01:01:01', 400, 'Kérem mindenki ellenőrizze június 2-ig a vezetett egységeiben az aktivitást!');
insert into news(id, created, expire, submitter_id, text) values (2002, '2018-05-20 01:01:01', '2018-05-21 01:01:01', 400, 'Egy lejárt határidős hír');

insert into uniform(id, begindate, price, renttype, returndate, returned, uniformsize, scout_id) values (2500, '2018-05-20 01:01:01', 3500, 2, '2018-05-20 01:01:01', 1, 4, 303);
insert into uniform(id, begindate, price, renttype, returned, uniformsize, scout_id) values (2501, '2018-05-21 01:01:01', 6600, 1, 0, 2, 304);
insert into uniform(id, begindate, price, renttype, returned, uniformsize, scout_id) values (2502, '2018-05-22 01:01:01', 4000, 0, 0, 5, 305);
