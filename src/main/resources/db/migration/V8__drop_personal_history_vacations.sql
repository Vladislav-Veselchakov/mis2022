DROP TABLE IF EXISTS personal_history_vacations;
ALTER TABLE vacation
    ADD COLUMN personal_history_id BIGINT;
ALTER TABLE vacation
    ADD CONSTRAINT FK_VACATION_ON_PERSONAL_PERSONAL_HISTORY FOREIGN KEY (personal_history_id) REFERENCES personal_history (id);