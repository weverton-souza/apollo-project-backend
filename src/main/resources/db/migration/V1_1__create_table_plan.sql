CREATE TABLE "plan"
(
    id         UUID      DEFAULT uuid_generate_v4() PRIMARY KEY,
    name       VARCHAR(10) NOT NULL,
    price      REAL        NOT NULL,
    deleted    BOOLEAN   DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID      DEFAULT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by UUID      DEFAULT NULL
);

COMMENT ON COLUMN "plan".id IS 'Unique ID for the plan, generated automatically';
COMMENT ON COLUMN "plan".name IS 'Plan name';
COMMENT ON COLUMN "plan".price IS 'Plan price';
COMMENT ON COLUMN "plan".deleted IS 'Indicates if the plan has been deleted';
COMMENT ON COLUMN "plan".created_at IS 'Date and time of plan creation';
COMMENT ON COLUMN "plan".created_by IS 'UUID of the user who created the plan';
COMMENT ON COLUMN "plan".updated_at IS 'Date and time of the last plan update';
COMMENT ON COLUMN "plan".updated_by IS 'UUID of the user who last updated the plan';
