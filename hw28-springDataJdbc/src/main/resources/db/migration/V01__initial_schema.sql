CREATE TABLE IF NOT EXISTS client
(
    id bigserial,
    name varchar(255),
    CONSTRAINT client_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS  phone
(
   id bigserial,
   number varchar(255),
   client_id bigint,
   CONSTRAINT phone_pk PRIMARY KEY (id),
   CONSTRAINT phone_fk_01 FOREIGN KEY (client_id) REFERENCES client (id) ON DELETE CASCADE
);

create table address
(
    id bigserial,
    client_id bigint,
    street varchar(255),
    primary key (id),
    CONSTRAINT address_fk_01 FOREIGN KEY (client_id) REFERENCES client (id) ON DELETE CASCADE,
    CONSTRAINT unique_client_id UNIQUE (client_id)
);

