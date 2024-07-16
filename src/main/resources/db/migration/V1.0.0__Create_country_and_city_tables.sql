-- Create the country table
CREATE TABLE country (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(100) NOT NULL UNIQUE
);

-- Create the city table
CREATE TABLE city (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(100) NOT NULL,
                      country_id INTEGER NOT NULL,
                      FOREIGN KEY (country_id) REFERENCES country(id) ON DELETE CASCADE
);

-- Create an index on the country_id column in the city table for better query performance
CREATE INDEX idx_city_country_id ON city(country_id);

-- Add a unique constraint to prevent duplicate city names within the same country
ALTER TABLE city ADD CONSTRAINT unique_city_per_country UNIQUE (name, country_id);