CREATE TABLE department (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    faculty_id INTEGER NOT NULL,
    FOREIGN KEY (faculty_id) REFERENCES faculty(id) ON DELETE CASCADE
);

-- Create an index on the faculty_id column in the department table for better query performance
CREATE INDEX idx_department_faculty_id ON department(faculty_id);

-- Add a unique constraint to prevent duplicate department names within the same faculty
ALTER TABLE department ADD CONSTRAINT unique_department_per_faculty UNIQUE (name, faculty_id);