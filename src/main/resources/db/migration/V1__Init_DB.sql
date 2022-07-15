create table role
(
    id   bigserial  constraint role_pk  primary key,
    name varchar(20)
);
create unique index role_name_uindex    on role (name);

create table users
(
    id         bigserial    constraint  users_pk  primary key,
    email      varchar(256) not null,
    password   varchar(100) not null,
    first_name varchar(50)  not null,
    last_name  varchar(50)  not null,
    surname    varchar(50),
    birthday   date         not null,
    enabled    boolean      not null,
    role_id    bigint       not null    constraint users_role_id_fk references role
);

create unique index user_email_uindex on users (email);
