create table medical_Organization
(
    id               bigserial  constraint medical_organization_pk  primary key,
    name             varchar(20) not null,
    address          varchar(50) not null
);
create unique index medical_organization_id_uindex    on medical_organization (id);


create table department
(
    id   bigserial    constraint  department_pk  primary key,
    name varchar(50) not null,
    medical_Organization_id    bigint constraint department_medical_organization_id_fk references medical_organization
);
create unique index department_id_uindex on department (id);
