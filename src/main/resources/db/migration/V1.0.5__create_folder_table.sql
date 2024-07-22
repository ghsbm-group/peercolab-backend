--create folder(id, name, type-root, leaf, subfolder, parentId-class configuration(type==root), root folders(ani), )

CREATE TABLE folder
(
    id                     SERIAL PRIMARY KEY,
    name                   VARCHAR(100) NOT NULL,
    parent_id              INTEGER,
    class_configuration_id INTEGER      NOT NULL,
    FOREIGN KEY (parent_id) REFERENCES folder (id) ON DELETE CASCADE,
    FOREIGN KEY (class_configuration_id) REFERENCES class_configuration (id)
);











