CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE "refresh_token"
(
    access_token_id UUID      NOT NULL,
    user_id         UUID      NOT NULL,
    token           VARCHAR(255) UNIQUE NOT NULL,
    revoked         BOOLEAN   DEFAULT FALSE,
    expires_at      TIMESTAMP NOT NULL,
    deleted         BOOLEAN   DEFAULT FALSE,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by      UUID      DEFAULT NULL,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by      UUID      DEFAULT NULL,
    PRIMARY KEY (access_token_id, user_id),
    FOREIGN KEY (user_id) REFERENCES "user" (id),
    FOREIGN KEY (access_token_id) REFERENCES "access_token" (id)
);

COMMENT ON COLUMN "refresh_token".access_token_id IS 'The ID of the associated access token';
COMMENT ON COLUMN "refresh_token".user_id IS 'The ID of the user to whom the refresh token belongs';
COMMENT ON COLUMN "refresh_token".token IS 'The refresh token';
COMMENT ON COLUMN "refresh_token".revoked IS 'Indicates if the refresh token has been revoked';
COMMENT ON COLUMN "refresh_token".expires_at IS 'The expiration date of the refresh token';
COMMENT ON COLUMN "refresh_token".deleted IS 'Indicates if the refresh token has been deleted';
COMMENT ON COLUMN "refresh_token".created_at IS 'Date and time of refresh token creation';
COMMENT ON COLUMN "refresh_token".created_by IS 'UUID of the user who created the record';
COMMENT ON COLUMN "refresh_token".updated_at IS 'Date and time of the last refresh token update';
COMMENT ON COLUMN "refresh_token".updated_by IS 'UUID of the user who last updated the record';
