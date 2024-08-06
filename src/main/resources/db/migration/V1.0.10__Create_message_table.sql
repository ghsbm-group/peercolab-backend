CREATE TABLE message
(
    id              SERIAL PRIMARY KEY,
    content         VARCHAR(250) NOT NULL,
    user_id         INTEGER      NOT NULL,
    messageboard_id INTEGER      NOT NULL,
    post_date       DATE         NOT NULL,
    FOREIGN KEY (messageboard_id) REFERENCES folder (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES  "user" (id) ON DELETE CASCADE
);

-- Create an index on the user_id column in the message table for better query performance
CREATE INDEX message_user_id ON message (user_id);
