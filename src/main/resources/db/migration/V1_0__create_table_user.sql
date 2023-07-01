CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TYPE IF EXISTS UserType;
CREATE TYPE UserType AS ENUM ('CUSTOMER', 'PROVIDER');

DROP TYPE IF EXISTS AccountStatus;
CREATE TYPE AccountStatus AS ENUM ('ACTIVE', 'SUSPENDED', 'DELETED');

CREATE TABLE "user"
(
    id           UUID      DEFAULT uuid_generate_v4() PRIMARY KEY,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    name         VARCHAR(100)       NOT NULL,
    email        VARCHAR(50) UNIQUE NOT NULL,
    password     CHAR(64)           NOT NULL,
    type         UserType           NOT NULL,
    phone_number VARCHAR(15)        NOT NULL,
    address      VARCHAR(255)       NOT NULL,
    status       AccountStatus      NOT NULL,
    verified     BOOLEAN   DEFAULT FALSE
);

COMMENT ON COLUMN "user".id IS 'Unique ID for the user, generated automatically';
COMMENT ON COLUMN "user".created_at IS 'Date and time of user creation';
COMMENT ON COLUMN "user".updated_at IS 'Date and time of the last user update';
COMMENT ON COLUMN "user".name IS 'User`s name';
COMMENT ON COLUMN "user".email IS 'User`s email, must be unique';
COMMENT ON COLUMN "user".password IS 'User`s password';
COMMENT ON COLUMN "user".type IS 'User type, CUSTOMER or PROVIDER';
COMMENT ON COLUMN "user".phone_number IS 'User`s phone number';
COMMENT ON COLUMN "user".address IS 'User`s address';
COMMENT ON COLUMN "user".status IS 'User account status, ACTIVE, SUSPENDED, or DELETED';
COMMENT ON COLUMN "user".verified IS 'Indicates if the user`s account has been verified';
