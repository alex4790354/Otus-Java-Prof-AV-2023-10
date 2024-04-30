DELETE FROM client WHERE id > 0;
INSERT INTO client(id, name) VALUES (1, 'Jhon-1');
INSERT INTO client(id, name) VALUES (2, 'Robert-2');
INSERT INTO client(id, name) VALUES (3, 'Michael-3');

DELETE FROM phone WHERE id > 0;
INSERT INTO phone(id, number, client_id) VALUES (1, '925-111-11-11', 1);
INSERT INTO phone(id, number, client_id) VALUES (2, '925-222-22-22', 2);
INSERT INTO phone(id, number, client_id) VALUES (3, '925-333-33-33', 3);

DELETE FROM address WHERE id > 0;
INSERT INTO address(id, client_id, street) VALUES(1, 1, 'adress-1');
INSERT INTO address(id, client_id, street) VALUES(2, 2, 'adress-2');
INSERT INTO address(id, client_id, street) VALUES(3, 3, 'adress-3');
