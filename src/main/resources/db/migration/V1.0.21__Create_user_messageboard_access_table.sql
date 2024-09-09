create table user_messageboard_access
(
    messageboard_id   BIGINT       NOT NULL,
    user_id           BIGINT       NOT NULL,
    last_access_date  TIMESTAMP    NOT NULL,
    PRIMARY KEY (user_id, messageboard_id),
    FOREIGN KEY (user_id) REFERENCES "user" (id),
    FOREIGN KEY (messageboard_id) REFERENCES folder (id)
);