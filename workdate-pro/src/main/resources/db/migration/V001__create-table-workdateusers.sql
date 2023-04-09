CREATE TABLE workdate_users (
        id BIGINT NOT NULL AUTO_INCREMENT,
        login VARCHAR(100) NOT NULL UNIQUE,
        password VARCHAR(255) NOT NULL,
        active BIT NOT NULL,
        client_id BIGINT NOT NULL,
        service_id BIGINT NOT NULL,
        schedule_id BIGINT NOT NULL,

        PRIMARY KEY(id)
);
CREATE TABLE clients (
        id BIGINT NOT NULL AUTO_INCREMENT,
        name VARCHAR(100) NOT NULL,
        fone VARCHAR(20) NOT NULL,
        address VARCHAR(255),

        PRIMARY KEY(id)
);
CREATE TABLE services (
        id BIGINT NOT NULL AUTO_INCREMENT,
        title VARCHAR(55) NOT NULL,
        description TEXT,
        price DECIMAL(10,2) NOT NULL,
        has_permanence BIT NOT NULL,
        permanence DATETIME,

        PRIMARY KEY(id)
);
CREATE TABLE schedules (
        id BIGINT NOT NULL AUTO_INCREMENT,
        client_id BIGINT NOT NULL,
        service_id BIGINT NOT NULL,
        schedule_time DATETIME NOT NULL,
        status VARCHAR(20) NOT NULL,

        PRIMARY KEY(id)
);
ALTER TABLE workdate_users ADD CONSTRAINT fk_user_client FOREIGN KEY (client_id) REFERENCES clients (id);
ALTER TABLE workdate_users ADD CONSTRAINT fk_user_service FOREIGN KEY (service_id) REFERENCES services (id);
ALTER TABLE workdate_users ADD CONSTRAINT fk_user_schedule FOREIGN KEY (schedule_id) REFERENCES schedules (id);

ALTER TABLE schedules ADD CONSTRAINT fk_schedule_client FOREIGN KEY (client_id) REFERENCES clients (id);
ALTER TABLE schedules ADD CONSTRAINT fk_schedule_service FOREIGN KEY (service_id) REFERENCES services (id);