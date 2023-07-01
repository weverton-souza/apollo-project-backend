CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TYPE FileType AS ENUM ('PHOTO','ADDRESS_PROOF','THUMB','RECEIPT');

CREATE TABLE "file"
(
    id         UUID      DEFAULT uuid_generate_v4() PRIMARY KEY,
    file_name  VARCHAR(255) NOT NULL,
    s3_url     VARCHAR(255) NOT NULL,
    file_type  FileType     NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN "file".id IS 'Unique ID for the file, generated automatically';
COMMENT ON COLUMN "file".file_name IS 'File name';
COMMENT ON COLUMN "file".s3_url IS 'URL of the file stored in S3';
COMMENT ON COLUMN "file".file_type IS 'PHOTO, ADDRESS_PROOF, THUMB (Thumbnail image file), or RECEIPT (proof of payment file)';
COMMENT ON COLUMN "file".created_at IS 'Date and time of file creation';
COMMENT ON COLUMN "file".updated_at IS 'Date and time of the last file update';
