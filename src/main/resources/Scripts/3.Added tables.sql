use test;
alter table developers add column salary double;
UPDATE developers SET salary = 5000 WHERE (id = 1);
UPDATE developers SET salary = 2600 WHERE (id = 2);
UPDATE developers SET salary = 1200 WHERE (id = 3);
UPDATE developers SET salary = 3200 WHERE (id = 4);
UPDATE developers SET salary = 2400 WHERE (id = 5);
UPDATE developers SET salary = 2500 WHERE (id = 6);
UPDATE developers SET salary = 3300 WHERE (id = 7);
UPDATE developers SET salary = 4400 WHERE (id = 8);
UPDATE developers SET salary = 2600 WHERE (id = 9);
UPDATE developers SET salary = 1000 WHERE (id = 10);

alter table projects add column creation_date varchar (45);
UPDATE projects SET creation_date = "1.02.2020" WHERE (id = 1);
UPDATE projects SET creation_date = "2.03.2020" WHERE (id = 2);
UPDATE projects SET creation_date = "3.04.2020" WHERE (id = 3);
UPDATE projects SET creation_date = "4.05.2020" WHERE (id = 4);
UPDATE projects SET creation_date = "5.06.2020" WHERE (id = 5);

alter table projects add column cost double;
UPDATE projects SET cost = 7600 WHERE (id = 1);
UPDATE projects SET cost = 4400 WHERE (id = 2);
UPDATE projects SET cost = 4900 WHERE (id = 3);
UPDATE projects SET cost = 7700 WHERE (id = 4);
UPDATE projects SET cost = 3600 WHERE (id = 5);

