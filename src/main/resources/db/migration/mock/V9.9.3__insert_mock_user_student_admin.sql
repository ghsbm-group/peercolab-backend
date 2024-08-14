INSERT INTO "user" (id, login, password_hash, first_name, last_name, email, image_url, activated,
                  lang_key, created_by, last_modified_by, created_date, last_modified_date)
VALUES (3, 'studentadmin', '$2a$10$QK/zIIa2reG0lZXfbFmLI.hG2I2uEx86PcYian2EUatA636x..Amu', 'Student Admin',
        'Student Admin', 'studentadmin@localhost.com', NULL, true, 'en', 'system', 'system', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP);

INSERT INTO user_authority (user_id, authority_name)
VALUES (3, 'ROLE_STUDENT_ADMIN');
INSERT INTO user_authority (user_id, authority_name)
VALUES (3, 'ROLE_USER');
