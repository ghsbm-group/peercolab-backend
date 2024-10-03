create table contact_us_messages
(
    id            SERIAL PRIMARY KEY,
    user_email    VARCHAR(191)   NOT NULL,
    subject       VARCHAR(200)   NOT NULL,
    content       VARCHAR(10000) NOT NULL
);