-- Create the data_requests table
CREATE TABLE data_requests
(
    id           BIGSERIAL PRIMARY KEY,
    user_id      BIGINT    NOT NULL,
    request_date TIMESTAMP NOT NULL,
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES "user" (id)
);

-- Add an index on the user_id column for better query performance
CREATE INDEX idx_data_requests_user_id ON data_requests (user_id);