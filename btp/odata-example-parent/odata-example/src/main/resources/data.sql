insert into car_maker(id,name) values (1,'Special Motors');
insert into car_maker(id,name) values (2,'BWM');
insert into car_maker(id,name) values (3,'Dolores');

insert into car_model(id,maker_fk,name,year) values(1,1,'Muze',2018);
insert into car_model(id,maker_fk,name,year) values(2,1,'Empada',2008);

insert into car_model(id,maker_fk,name,year) values(4,2,'BWM-100',2008);
insert into car_model(id,maker_fk,name,year) values(5,2,'BWM-200',2009);
insert into car_model(id,maker_fk,name,year) values(6,2,'BWM-300',2008);

alter sequence hibernate_sequence restart with 100;