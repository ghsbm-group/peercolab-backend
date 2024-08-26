CREATE TABLE "file"
(
    id               SERIAL        PRIMARY KEY,
    name             VARCHAR(255)  NOT NULL,
    path             VARCHAR(1000) NOT NULL,
    user_id          INTEGER       NOT NULL,
    folder_id        INTEGER       NOT NULL,
    file_date        TIMESTAMP     NOT NULL,
    FOREIGN KEY (folder_id) REFERENCES folder (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES  "user" (id) ON DELETE CASCADE
);
