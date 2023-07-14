CREATE TABLE "role"
(
    id          UUID      DEFAULT uuid_generate_v4() PRIMARY KEY,
    type        VARCHAR(20)  NOT NULL UNIQUE,
    description VARCHAR(200) NOT NULL,
    deleted     BOOLEAN   DEFAULT FALSE,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by  UUID      DEFAULT NULL,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by  UUID      DEFAULT NULL
);

COMMENT ON COLUMN "role".id IS 'Unique ID for the role, generated automatically';
COMMENT ON COLUMN "role".type IS 'Role type: BACKOFFICE, CUSTOMER, or SERVICE_PROVIDER';
COMMENT ON COLUMN "role".description IS 'Description of the role';
COMMENT ON COLUMN "role".deleted IS 'Indicates if the role has been deleted';
COMMENT ON COLUMN "role".created_at IS 'Date and time of role creation';
COMMENT ON COLUMN "role".created_by IS 'UUID of the user who created the role';
COMMENT ON COLUMN "role".updated_at IS 'Date and time of the last role update';
COMMENT ON COLUMN "role".updated_by IS 'UUID of the user who last updated the role';

-- INSERT para BACKOFFICE
INSERT INTO "role" (type, description, created_at)
VALUES ('BACKOFFICE', 'Backoffice role: Provides administrative access and privileges.', CURRENT_TIMESTAMP);

-- INSERT para CUSTOMER
INSERT INTO "role" (type, description, created_at)
VALUES ('CUSTOMER', 'Customer role: Represents a user who interacts with the system as a customer.', CURRENT_TIMESTAMP);

-- INSERT para SERVICE_PROVIDER
INSERT INTO "role" (type, description, created_at)
VALUES ('SERVICE_PROVIDER', 'Service provider role: Represents a user who offers services within the system.',
        CURRENT_TIMESTAMP);
