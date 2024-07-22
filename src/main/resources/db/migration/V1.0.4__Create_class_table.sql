create TABLE class_configuration
(
    id                       SERIAL PRIMARY KEY,
    name                     VARCHAR(100) NOT NULL,
    start_year               INTEGER      NOT NULL,
    no_of_study_years        INTEGER      NOT NULL,
    no_of_semesters_per_year INTEGER      NOT NULL,
    department_id            INTEGER      NOT NULL,
    FOREIGN KEY (department_id) REFERENCES department (id) ON delete CASCADE
);

-- Create an index on the department_id column in the table for better query performance
create index idx_class_configuration_department_id on class_configuration (department_id);

-- Add a unique constraint to prevent duplicate classes names within the same department
alter table class_configuration
    add CONSTRAINT unique_class_configuration_per_department UNIQUE (name, start_year, no_of_study_years, department_id);