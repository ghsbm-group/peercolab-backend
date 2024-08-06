ALTER TABLE class_configuration
    ADD COLUMN enrolment_key VARCHAR(20) NOT NULL
        DEFAULT substr(md5(random()::text), 1, 20);