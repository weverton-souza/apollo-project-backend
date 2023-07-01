CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE "address"
(
    id           UUID      DEFAULT uuid_generate_v4() PRIMARY KEY,
    street       VARCHAR(100) NOT NULL,
    number       VARCHAR(10)  NOT NULL,
    complement   VARCHAR(50),
    neighborhood VARCHAR(50)  NOT NULL,
    city         VARCHAR(50)  NOT NULL,
    state        VARCHAR(50)  NOT NULL,
    postal_code  VARCHAR(10)  NOT NULL,
    verified     BOOLEAN   DEFAULT FALSE,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN "address".id IS 'Unique ID for the address, generated automatically';
COMMENT ON COLUMN "address".street IS 'Street name';
COMMENT ON COLUMN "address".number IS 'House number';
COMMENT ON COLUMN "address".complement IS 'Additional information for the address, if applicable';
COMMENT ON COLUMN "address".neighborhood IS 'Neighborhood name';
COMMENT ON COLUMN "address".city IS 'City name';
COMMENT ON COLUMN "address".state IS 'State name';
COMMENT ON COLUMN "address".postal_code IS 'Postal code (ZIP code)';
COMMENT ON COLUMN "address".verified IS 'Indicates if the address has been verified';
COMMENT ON COLUMN "address".created_at IS 'Date and time of address creation';
COMMENT ON COLUMN "address".updated_at IS 'Date and time of the last address update';

