DROP TABLE IF EXISTS department_medical_services;
ALTER TABLE medical_service
    ADD COLUMN department_id BIGINT;
ALTER TABLE medical_service
    ADD CONSTRAINT FK_MEDICAL_MEDICAL_SERVICE_ON_DEPARTMENT FOREIGN KEY (department_id) REFERENCES department (id);