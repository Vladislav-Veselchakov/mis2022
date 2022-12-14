CREATE TABLE account
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name  VARCHAR(255),
    date  date,
    money BIGINT,
    CONSTRAINT pk_account PRIMARY KEY (id)
);

CREATE TABLE account_appeals
(
    account_id BIGINT NOT NULL,
    appeals_id BIGINT NOT NULL,
    CONSTRAINT pk_account_appeals PRIMARY KEY (account_id, appeals_id)
);

CREATE TABLE appeal
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    patient_id BIGINT,
    disease_id BIGINT,
    account_id BIGINT,
    is_closed  BOOLEAN,
    local_date date,
    CONSTRAINT pk_appeal PRIMARY KEY (id)
);

CREATE TABLE appeal_visits
(
    appeal_id BIGINT NOT NULL,
    visits_id BIGINT NOT NULL,
    CONSTRAINT pk_appeal_visits PRIMARY KEY (appeal_id, visits_id)
);

CREATE TABLE attestation
(
    id              BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    date_from       date,
    date_to         date,
    document_number VARCHAR(255),
    CONSTRAINT pk_attestation PRIMARY KEY (id)
);

CREATE TABLE department
(
    id                      BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name                    VARCHAR(255),
    medical_organization_id BIGINT,
    CONSTRAINT pk_department PRIMARY KEY (id)
);

CREATE TABLE department_diseases
(
    department_id BIGINT NOT NULL,
    diseases_id   BIGINT NOT NULL,
    CONSTRAINT pk_department_diseases PRIMARY KEY (department_id, diseases_id)
);

CREATE TABLE department_doctors
(
    department_id BIGINT NOT NULL,
    doctors_id    BIGINT NOT NULL,
    CONSTRAINT pk_department_doctors PRIMARY KEY (department_id, doctors_id)
);

CREATE TABLE department_medical_services
(
    department_id       BIGINT NOT NULL,
    medical_services_id BIGINT NOT NULL,
    CONSTRAINT pk_department_medicalservices PRIMARY KEY (department_id, medical_services_id)
);

CREATE TABLE diploma
(
    id              BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    university_name VARCHAR(255),
    serial_number   BIGINT,
    date_from       date,
    CONSTRAINT pk_diploma PRIMARY KEY (id)
);

CREATE TABLE disease
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    identifier    VARCHAR(255),
    name          VARCHAR(255),
    department_id BIGINT,
    CONSTRAINT pk_disease PRIMARY KEY (id)
);

CREATE TABLE medical_organization
(
    id      BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name    VARCHAR(255),
    address VARCHAR(255),
    CONSTRAINT pk_medicalorganization PRIMARY KEY (id)
);

CREATE TABLE medical_service
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    identifier VARCHAR(255),
    name       VARCHAR(255),
    CONSTRAINT pk_medicalservice PRIMARY KEY (id)
);

CREATE TABLE medical_service_prices
(
    medical_service_id BIGINT NOT NULL,
    prices_id          BIGINT NOT NULL,
    CONSTRAINT pk_medicalservice_prices PRIMARY KEY (medical_service_id, prices_id)
);

CREATE TABLE null_appeals
(
    patient_id BIGINT NOT NULL,
    appeals_id BIGINT NOT NULL,
    CONSTRAINT pk_null_appeals PRIMARY KEY (patient_id, appeals_id)
);

CREATE TABLE null_talons
(
    patient_id BIGINT NOT NULL,
    talons_id  BIGINT NOT NULL,
    CONSTRAINT pk_null_talons PRIMARY KEY (patient_id, talons_id)
);

CREATE TABLE personal_history
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    date_of_employment date,
    date_of_dismissal  date,
    CONSTRAINT pk_personalhistory PRIMARY KEY (id)
);

CREATE TABLE personal_history_attestations
(
    personal_history_id BIGINT NOT NULL,
    attestations_id     BIGINT NOT NULL,
    CONSTRAINT pk_personalhistory_attestations PRIMARY KEY (personal_history_id, attestations_id)
);

CREATE TABLE personal_history_diplomas
(
    personal_history_id BIGINT NOT NULL,
    diplomas_id         BIGINT NOT NULL,
    CONSTRAINT pk_personalhistory_diplomas PRIMARY KEY (personal_history_id, diplomas_id)
);

CREATE TABLE personal_history_vacations
(
    personal_history_id BIGINT NOT NULL,
    vacations_id        BIGINT NOT NULL,
    CONSTRAINT pk_personalhistory_vacations PRIMARY KEY (personal_history_id, vacations_id)
);

CREATE TABLE price_of_medical_service
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    yet                DOUBLE PRECISION,
    day_from           date,
    day_to             date,
    medical_service_id BIGINT,
    CONSTRAINT pk_priceofmedicalservice PRIMARY KEY (id)
);

CREATE TABLE role
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE talon
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    date       date,
    time       TIME WITHOUT TIME ZONE,
    patient_id BIGINT,
    doctor_id  BIGINT,
    CONSTRAINT pk_talon PRIMARY KEY (id)
);

CREATE TABLE users
(
    id                  BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    dtype               VARCHAR(31),
    email               VARCHAR(255)                            NOT NULL,
    password            VARCHAR(255),
    first_name          VARCHAR(255),
    last_name           VARCHAR(255),
    surname             VARCHAR(255),
    birthday            date,
    enabled             BOOLEAN,
    role_id             BIGINT,
    passport            VARCHAR(255),
    polis               VARCHAR(255),
    snils               VARCHAR(255),
    address             VARCHAR(255),
    department_id       BIGINT,
    personal_history_id BIGINT,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE vacation
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    date_from date,
    date_to   date,
    CONSTRAINT pk_vacation PRIMARY KEY (id)
);

CREATE TABLE visit
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    day_of_visit date,
    doctor_id    BIGINT,
    appeal_id    BIGINT,
    CONSTRAINT pk_visit PRIMARY KEY (id)
);

CREATE TABLE visit_medical_services
(
    visit_id            BIGINT NOT NULL,
    medical_services_id BIGINT NOT NULL,
    CONSTRAINT pk_visit_medicalservices PRIMARY KEY (visit_id, medical_services_id)
);

CREATE TABLE yet
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    price    DOUBLE PRECISION,
    day_from date,
    day_to   date,
    CONSTRAINT pk_yet PRIMARY KEY (id)
);

ALTER TABLE account_appeals
    ADD CONSTRAINT uc_account_appeals_appeals UNIQUE (appeals_id);

ALTER TABLE appeal_visits
    ADD CONSTRAINT uc_appeal_visits_visits UNIQUE (visits_id);

ALTER TABLE department_diseases
    ADD CONSTRAINT uc_department_diseases_diseases UNIQUE (diseases_id);

ALTER TABLE department_doctors
    ADD CONSTRAINT uc_department_doctors_doctors UNIQUE (doctors_id);

ALTER TABLE medical_service_prices
    ADD CONSTRAINT uc_medical_service_prices_prices UNIQUE (prices_id);

ALTER TABLE null_appeals
    ADD CONSTRAINT uc_null_appeals_appeals UNIQUE (appeals_id);

ALTER TABLE null_talons
    ADD CONSTRAINT uc_null_talons_talons UNIQUE (talons_id);

ALTER TABLE personal_history_attestations
    ADD CONSTRAINT uc_personal_history_attestations_attestations UNIQUE (attestations_id);

ALTER TABLE personal_history_diplomas
    ADD CONSTRAINT uc_personal_history_diplomas_diplomas UNIQUE (diplomas_id);

ALTER TABLE personal_history_vacations
    ADD CONSTRAINT uc_personal_history_vacations_vacations UNIQUE (vacations_id);

ALTER TABLE role
    ADD CONSTRAINT uc_role_name UNIQUE (name);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE visit_medical_services
    ADD CONSTRAINT uc_visit_medical_services_medicalservices UNIQUE (medical_services_id);

ALTER TABLE appeal
    ADD CONSTRAINT FK_APPEAL_ON_ACCOUNT FOREIGN KEY (account_id) REFERENCES account (id);

ALTER TABLE appeal
    ADD CONSTRAINT FK_APPEAL_ON_DISEASE FOREIGN KEY (disease_id) REFERENCES disease (id);

ALTER TABLE appeal
    ADD CONSTRAINT FK_APPEAL_ON_PATIENT FOREIGN KEY (patient_id) REFERENCES users (id);

ALTER TABLE department
    ADD CONSTRAINT FK_DEPARTMENT_ON_MEDICALORGANIZATION FOREIGN KEY (medical_organization_id) REFERENCES medical_organization (id);

ALTER TABLE disease
    ADD CONSTRAINT FK_DISEASE_ON_DEPARTMENT FOREIGN KEY (department_id) REFERENCES department (id);

ALTER TABLE price_of_medical_service
    ADD CONSTRAINT FK_PRICEOFMEDICALSERVICE_ON_MEDICALSERVICE FOREIGN KEY (medical_service_id) REFERENCES medical_service (id);

ALTER TABLE talon
    ADD CONSTRAINT FK_TALON_ON_DOCTOR FOREIGN KEY (doctor_id) REFERENCES users (id);

ALTER TABLE talon
    ADD CONSTRAINT FK_TALON_ON_PATIENT FOREIGN KEY (patient_id) REFERENCES users (id);

ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_DEPARTMENT FOREIGN KEY (department_id) REFERENCES department (id);

ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_PERSONALHISTORY FOREIGN KEY (personal_history_id) REFERENCES personal_history (id);

ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_ROLE FOREIGN KEY (role_id) REFERENCES role (id);

ALTER TABLE visit
    ADD CONSTRAINT FK_VISIT_ON_APPEAL FOREIGN KEY (appeal_id) REFERENCES appeal (id);

ALTER TABLE visit
    ADD CONSTRAINT FK_VISIT_ON_DOCTOR FOREIGN KEY (doctor_id) REFERENCES users (id);

ALTER TABLE account_appeals
    ADD CONSTRAINT fk_accapp_on_account FOREIGN KEY (account_id) REFERENCES account (id);

ALTER TABLE account_appeals
    ADD CONSTRAINT fk_accapp_on_appeal FOREIGN KEY (appeals_id) REFERENCES appeal (id);

ALTER TABLE appeal_visits
    ADD CONSTRAINT fk_appvis_on_appeal FOREIGN KEY (appeal_id) REFERENCES appeal (id);

ALTER TABLE appeal_visits
    ADD CONSTRAINT fk_appvis_on_visit FOREIGN KEY (visits_id) REFERENCES visit (id);

ALTER TABLE department_diseases
    ADD CONSTRAINT fk_depdis_on_department FOREIGN KEY (department_id) REFERENCES department (id);

ALTER TABLE department_diseases
    ADD CONSTRAINT fk_depdis_on_disease FOREIGN KEY (diseases_id) REFERENCES disease (id);

ALTER TABLE department_doctors
    ADD CONSTRAINT fk_depdoc_on_department FOREIGN KEY (department_id) REFERENCES department (id);

ALTER TABLE department_doctors
    ADD CONSTRAINT fk_depdoc_on_doctor FOREIGN KEY (doctors_id) REFERENCES users (id);

ALTER TABLE department_medical_services
    ADD CONSTRAINT fk_depmedser_on_department FOREIGN KEY (department_id) REFERENCES department (id);

ALTER TABLE department_medical_services
    ADD CONSTRAINT fk_depmedser_on_medical_service FOREIGN KEY (medical_services_id) REFERENCES medical_service (id);

ALTER TABLE medical_service_prices
    ADD CONSTRAINT fk_medserpri_on_medical_service FOREIGN KEY (medical_service_id) REFERENCES medical_service (id);

ALTER TABLE medical_service_prices
    ADD CONSTRAINT fk_medserpri_on_price_of_medical_service FOREIGN KEY (prices_id) REFERENCES price_of_medical_service (id);

ALTER TABLE null_appeals
    ADD CONSTRAINT fk_nulapp_on_appeal FOREIGN KEY (appeals_id) REFERENCES appeal (id);

ALTER TABLE null_appeals
    ADD CONSTRAINT fk_nulapp_on_patient FOREIGN KEY (patient_id) REFERENCES users (id);

ALTER TABLE null_talons
    ADD CONSTRAINT fk_nultal_on_patient FOREIGN KEY (patient_id) REFERENCES users (id);

ALTER TABLE null_talons
    ADD CONSTRAINT fk_nultal_on_talon FOREIGN KEY (talons_id) REFERENCES talon (id);

ALTER TABLE personal_history_attestations
    ADD CONSTRAINT fk_perhisatt_on_attestation FOREIGN KEY (attestations_id) REFERENCES attestation (id);

ALTER TABLE personal_history_attestations
    ADD CONSTRAINT fk_perhisatt_on_personal_history FOREIGN KEY (personal_history_id) REFERENCES personal_history (id);

ALTER TABLE personal_history_diplomas
    ADD CONSTRAINT fk_perhisdip_on_diploma FOREIGN KEY (diplomas_id) REFERENCES diploma (id);

ALTER TABLE personal_history_diplomas
    ADD CONSTRAINT fk_perhisdip_on_personal_history FOREIGN KEY (personal_history_id) REFERENCES personal_history (id);

ALTER TABLE personal_history_vacations
    ADD CONSTRAINT fk_perhisvac_on_personal_history FOREIGN KEY (personal_history_id) REFERENCES personal_history (id);

ALTER TABLE personal_history_vacations
    ADD CONSTRAINT fk_perhisvac_on_vacation FOREIGN KEY (vacations_id) REFERENCES vacation (id);

ALTER TABLE visit_medical_services
    ADD CONSTRAINT fk_vismedser_on_medical_service FOREIGN KEY (medical_services_id) REFERENCES medical_service (id);

ALTER TABLE visit_medical_services
    ADD CONSTRAINT fk_vismedser_on_visit FOREIGN KEY (visit_id) REFERENCES visit (id);

