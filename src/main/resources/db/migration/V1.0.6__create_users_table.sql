CREATE TABLE "user"
(
    id                 SERIAL PRIMARY KEY,
    login              VARCHAR(50) NOT NULL,
    password_hash      VARCHAR(60) NOT NULL,
    first_name         VARCHAR(50),
    last_name          VARCHAR(50),
    email              VARCHAR(191),
    image_url          VARCHAR(256),
    activated          BOOLEAN     NOT NULL DEFAULT FALSE,
    lang_key           VARCHAR(10),
    activation_key     VARCHAR(20),
    reset_key          VARCHAR(20),
    created_by         VARCHAR(50) NOT NULL,
    created_date       TIMESTAMP,
    reset_date         TIMESTAMP,
    last_modified_by   VARCHAR(50),
    last_modified_date TIMESTAMP,
    CONSTRAINT ux_user_login UNIQUE (login),
    CONSTRAINT ux_user_email UNIQUE (email)
);

CREATE TABLE authority
(
    name VARCHAR(50) PRIMARY KEY
);

CREATE TABLE user_authority
(
    user_id        BIGINT      NOT NULL,
    authority_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, authority_name)
);

ALTER TABLE user_authority
    ADD CONSTRAINT fk_authority_name
        FOREIGN KEY (authority_name) REFERENCES authority (name);

ALTER TABLE user_authority
    ADD CONSTRAINT fk_user_id
        FOREIGN KEY (user_id) REFERENCES "user" (id);

INSERT INTO authority (name)
VALUES ('ROLE_ADMIN');
INSERT INTO authority (name)
VALUES ('ROLE_USER');
INSERT INTO authority (name)
VALUES ('ROLE_STUDENT_ADMIN');