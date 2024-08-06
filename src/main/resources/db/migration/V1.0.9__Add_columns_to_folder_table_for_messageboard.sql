--V1.0.9__Add_columns_to_folder_table_for_messageboard.sql
ALTER TABLE folder
ADD COLUMN description VARCHAR(300),
ADD COLUMN is_messageboard BOOLEAN NOT NULL;