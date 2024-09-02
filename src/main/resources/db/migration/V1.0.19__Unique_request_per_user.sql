ALTER TABLE request_authority
    ADD CONSTRAINT unique_request_per_user UNIQUE (authority_name, user_id);