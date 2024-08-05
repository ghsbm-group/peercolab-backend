--Add a unique constraint to prevent duplicate folder names within the parent and class
alter table folder
    add CONSTRAINT unique_folder UNIQUE (name,parent_id,class_configuration_id);