ALTER TABLE "user"
    ADD COLUMN provider    VARCHAR(50) NOT NULL DEFAULT 'local',
    ADD COLUMN provider_id VARCHAR(255);