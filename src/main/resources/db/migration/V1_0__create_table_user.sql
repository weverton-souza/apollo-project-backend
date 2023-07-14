CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE "user"
(
    id         UUID      DEFAULT uuid_generate_v4() PRIMARY KEY,
    name       VARCHAR(100)       NOT NULL,
    email      VARCHAR(50) UNIQUE NOT NULL,
    password   CHAR(64)           NOT NULL,
    status     VARCHAR(20)        NOT NULL,
    verified   BOOLEAN   DEFAULT FALSE,
    deleted    BOOLEAN   DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID      DEFAULT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by UUID      DEFAULT NULL
);

COMMENT ON COLUMN "user".id IS 'Unique ID for the user, generated automatically';
COMMENT ON COLUMN "user".name IS 'User`s name';
COMMENT ON COLUMN "user".email IS 'User`s email, must be unique';
COMMENT ON COLUMN "user".password IS 'User`s password (hashed)';
COMMENT ON COLUMN "user".status IS 'User account status: ACTIVE, SUSPENDED, or INACTIVE';
COMMENT ON COLUMN "user".verified IS 'Indicates if the user`s account has been verified';
COMMENT ON COLUMN "user".deleted IS 'Indicates if the user has been deleted';
COMMENT ON COLUMN "user".created_at IS 'Date and time of user creation';
COMMENT ON COLUMN "user".created_by IS 'UUID of the user who created the record';
COMMENT ON COLUMN "user".updated_at IS 'Date and time of the last user update';
COMMENT ON COLUMN "user".updated_by IS 'UUID of the user who last updated the record';
