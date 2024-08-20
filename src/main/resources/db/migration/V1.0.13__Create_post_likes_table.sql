create table post_likes
(
    message_id  BIGINT    NOT NULL,
    user_id     BIGINT    NOT NULL,
    PRIMARY KEY (user_id, message_id),
    FOREIGN KEY (user_id) REFERENCES "user" (id),
    FOREIGN KEY (message_id) REFERENCES message (id)
);

-- Create an index on the message_id column in the message table for better query performance
CREATE INDEX idx_post_likes_message_id ON post_likes (message_id);

-- Add a unique constraint to prevent duplicate user likes the same message
ALTER TABLE post_likes
    ADD CONSTRAINT unique_post_like_per_user UNIQUE (user_id, message_id);

