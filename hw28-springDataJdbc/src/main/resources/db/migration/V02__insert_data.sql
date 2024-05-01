DELETE FROM client WHERE id > 0;
INSERT INTO client(name) VALUES ('Jhon-1');
INSERT INTO client(name) VALUES ('Robert-2');
INSERT INTO client(name) VALUES ('Michael-3');

DELETE FROM phone WHERE id > 0;
INSERT INTO phone(number, client_id) VALUES ('925-111-11-11', 1);
INSERT INTO phone(number, client_id) VALUES ('925-222-22-22', 2);
INSERT INTO phone(number, client_id) VALUES ('925-333-33-33', 3);

DELETE FROM address WHERE id > 0;
INSERT INTO address(client_id, street) VALUES(1, 'adress-1');
INSERT INTO address(client_id, street) VALUES(2, 'adress-2');
INSERT INTO address(client_id, street) VALUES(3, 'adress-3');
