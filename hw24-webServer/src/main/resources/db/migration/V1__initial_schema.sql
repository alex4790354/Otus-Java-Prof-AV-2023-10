create sequence client_SEQ start with 1 increment by 1;

    create table client (
        id bigint not null,
        name varchar(255),
        primary key (id)
    );

    create table phone (
        id bigserial not null,
        number varchar(255),
        client_id bigint,
        primary key (id)
    );

    create table address (
        client_id bigint unique,
        id bigserial not null,
        street varchar(255),
        primary key (id)
    );

