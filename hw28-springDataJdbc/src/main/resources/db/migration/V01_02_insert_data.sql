create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);
create table manager
(
    no   bigserial not null primary key,
    label varchar(50),
    param1 varchar(50)
);

DELETE FROM client WHERE id > 0;
INSERT INTO client(id, name) VALUES (1, 'Jhon');
INSERT INTO client(id, name) VALUES (2, 'Robert');
INSERT INTO client(id, name) VALUES (3, 'Michael');

DELETE FROM manager WHERE no > 0;
INSERT INTO manager(no, label, param1) VALUES (1, 'Jhon-label', 'Jhon-params');
INSERT INTO manager(no, label, param1) VALUES (2, 'Robert-label', 'Robert-params');
INSERT INTO manager(no, label, param1) VALUES (3, 'Michael-label', 'Michael-params');
