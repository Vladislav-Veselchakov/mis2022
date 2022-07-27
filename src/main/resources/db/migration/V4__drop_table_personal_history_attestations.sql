DROP TABLE IF EXISTS personal_history_attestations;
ALTER TABLE attestation
    ADD COLUMN personal_history_id BIGINT;
ALTER TABLE attestation
    ADD CONSTRAINT FK_ATTESTATION_ON_PERSONAL_PERSONAL_HISTORY FOREIGN KEY (personal_history_id) REFERENCES personal_history (id);