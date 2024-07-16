CREATE TABLE university (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(100) NOT NULL,
                      city_id INTEGER NOT NULL,
                      FOREIGN KEY (city_id) REFERENCES city(id) ON DELETE CASCADE
);

-- Create an index on the country_id column in the city table for better query performance
CREATE INDEX idx_university_city_id ON university(city_id);

-- Add a unique constraint to prevent duplicate city names within the same country
ALTER TABLE university ADD CONSTRAINT unique_university_per_city UNIQUE (name, city_id);