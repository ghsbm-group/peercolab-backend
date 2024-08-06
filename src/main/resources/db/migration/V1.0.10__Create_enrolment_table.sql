-- Create Enrollments table
CREATE TABLE enrolment
(
    user_id                BIGINT    NOT NULL,
    class_configuration_id BIGINT    NOT NULL,
    enrolment_date         TIMESTAMP NOT NULL,
    PRIMARY KEY (user_id, class_configuration_id),
    FOREIGN KEY (user_id) REFERENCES "user" (id),
    FOREIGN KEY (class_configuration_id) REFERENCES class_configuration (id)
);

-- Add index on enrolment_key column in class_configuration table
CREATE INDEX idx_class_configuration_enrolment_key ON class_configuration (enrolment_key);
