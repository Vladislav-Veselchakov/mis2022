DROP TABLE IF EXISTS personal_history_diplomas;
ALTER TABLE diploma
    ADD COLUMN personal_history_id BIGINT;
ALTER TABLE diploma
    ADD CONSTRAINT FK_DIPLOMA_ON_PERSONAL_PERSONAL_HISTORY FOREIGN KEY (personal_history_id) REFERENCES personal_history (id);