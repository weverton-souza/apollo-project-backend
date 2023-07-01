ALTER TABLE "address"
ADD COLUMN file_id UUID;
COMMENT ON COLUMN "address".file_id IS 'UUID referencing the file table, used to associate a file with a address';

ALTER TABLE "user"
ADD FOREIGN KEY (file_id) REFERENCES "file" (id);
COMMENT ON COLUMN "user".file_id IS 'UUID referencing the file table, used to associate a file with a user';
