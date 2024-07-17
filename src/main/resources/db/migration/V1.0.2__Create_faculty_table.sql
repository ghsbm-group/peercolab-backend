CREATE TABLE faculty
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    university_id INTEGER      NOT NULL,
    FOREIGN KEY (university_id) REFERENCES university (id) ON DELETE CASCADE
);

-- Create an index on the country_id column in the city table for better query performance
CREATE INDEX idx_faculty_university_id ON faculty (university_id);

-- Add a unique constraint to prevent duplicate city names within the same country
ALTER TABLE faculty
    ADD CONSTRAINT unique_faculty_per_university UNIQUE (name, university_id);