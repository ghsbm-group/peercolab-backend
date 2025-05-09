INSERT INTO "user" (id, login, password_hash, first_name, last_name, email, image_url, activated,
                  lang_key, created_by, last_modified_by, created_date, last_modified_date)
VALUES (1, 'admin', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', 'Administrator',
        'Administrator', 'admin@localhost', NULL, true, 'en', 'system', 'system', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP);

INSERT INTO "user" (id, login, password_hash, first_name, last_name, email, image_url, activated,
                  lang_key, created_by, last_modified_by, created_date, last_modified_date)
VALUES (2, 'user', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'User', 'User',
        'user@localhost', NULL, true, 'en', 'system', 'system', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP);

INSERT INTO user_authority (user_id, authority_name)
VALUES (1, 'ROLE_ADMIN');
INSERT INTO user_authority (user_id, authority_name)
VALUES (1, 'ROLE_STUDENT');
INSERT INTO user_authority (user_id, authority_name)
VALUES (2, 'ROLE_STUDENT');