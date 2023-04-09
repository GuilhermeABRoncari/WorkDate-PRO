ALTER TABLE schedules ADD COLUMN observation TEXT;

CREATE TABLE releases (
        id BIGINT NOT NULL AUTO_INCREMENT,
        schedule_id BIGINT,
        status VARCHAR(20) NOT NULL,

        PRIMARY KEY(id)
);

ALTER TABLE releases ADD CONSTRAINT fk_schedule FOREIGN KEY (schedule_id) REFERENCES schedules (id);
