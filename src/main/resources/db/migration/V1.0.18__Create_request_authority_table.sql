CREATE TABLE request_authority
(
    user_id        BIGINT      NOT NULL,
    authority_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, authority_name),
    FOREIGN KEY (user_id) REFERENCES "user" (id),
    FOREIGN KEY (authority_name) REFERENCES authority (name)
);
