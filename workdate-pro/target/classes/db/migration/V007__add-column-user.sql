ALTER TABLE releases ADD COLUMN user_id BIGINT;

ALTER TABLE releases ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES workdate_users (id);