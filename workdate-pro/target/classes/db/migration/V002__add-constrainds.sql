ALTER TABLE schedules ADD COLUMN user_id BIGINT NOT NULL;
ALTER TABLE schedules ADD CONSTRAINT fk_schedule_user FOREIGN KEY (user_id) REFERENCES workdate_users (id);

ALTER TABLE clients ADD COLUMN user_id BIGINT NOT NULL;
ALTER TABLE clients ADD CONSTRAINT fk_client_user FOREIGN KEY (user_id) REFERENCES workdate_users (id);

ALTER TABLE services ADD COLUMN user_id BIGINT NOT NULL;
ALTER TABLE services ADD CONSTRAINT fk_services_user FOREIGN KEY (user_id) REFERENCES workdate_users (id);