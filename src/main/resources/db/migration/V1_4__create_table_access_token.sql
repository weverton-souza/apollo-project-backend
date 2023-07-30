CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE "access_token"
(
    id         UUID      DEFAULT uuid_generate_v4() PRIMARY KEY,
    token      VARCHAR(255) UNIQUE NOT NULL,
    token_type VARCHAR(20)         NOT NULL,
    revoked    BOOLEAN   DEFAULT FALSE,
    expired    BOOLEAN   DEFAULT FALSE,
    expired_at TIMESTAMP           NOT NULL,
    user_id    UUID                NOT NULL,
    deleted    BOOLEAN   DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID      DEFAULT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by UUID      DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES "user" (id)
);

COMMENT ON COLUMN "access_token".id IS 'Unique ID for the token, generated automatically';
COMMENT ON COLUMN "access_token".token IS 'The access token';
COMMENT ON COLUMN "access_token".token_type IS 'The type of the token, e.g., BEARER';
COMMENT ON COLUMN "access_token".revoked IS 'Indicates if the token has been revoked';
COMMENT ON COLUMN "access_token".expired IS 'Indicates if the token has expired';
COMMENT ON COLUMN "access_token".expired_at IS 'The expiration date of the token';
COMMENT ON COLUMN "access_token".user_id IS 'The ID of the user to whom the token belongs';
COMMENT ON COLUMN "access_token".deleted IS 'Indicates if the token has been deleted';
COMMENT ON COLUMN "access_token".created_at IS 'Date and time of token creation';
COMMENT ON COLUMN "access_token".created_by IS 'UUID of the user who created the record';
COMMENT ON COLUMN "access_token".updated_at IS 'Date and time of the last token update';
COMMENT ON COLUMN "access_token".updated_by IS 'UUID of the user who last updated the record';
