CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE "product"
(
    id          UUID      DEFAULT uuid_generate_v4() PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    deleted     BOOLEAN   DEFAULT FALSE,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by  UUID      DEFAULT NULL,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by  UUID      DEFAULT NULL
);

COMMENT ON TABLE "product" IS 'Table containing product information';
COMMENT ON COLUMN "product".id IS 'Unique ID for the product, generated automatically';
COMMENT ON COLUMN "product".name IS 'Product name';
COMMENT ON COLUMN "product".description IS 'Product description';
COMMENT ON COLUMN "product".deleted IS 'Indicates if the product has been deleted';
COMMENT ON COLUMN "product".created_at IS 'Date and time of product creation';
COMMENT ON COLUMN "product".created_by IS 'UUID of the user who created the product';
COMMENT ON COLUMN "product".updated_at IS 'Date and time of the last product update';
COMMENT ON COLUMN "product".updated_by IS 'UUID of the user who last updated the product';

CREATE TABLE "product_detail"
(
    id         UUID      DEFAULT uuid_generate_v4() PRIMARY KEY,
    product_id UUID        NOT NULL,
    price      FLOAT       NOT NULL,
    currency   VARCHAR(5)  NOT NULL,
    period     VARCHAR(20) NOT NULL,
    deleted    BOOLEAN   DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID      DEFAULT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by UUID      DEFAULT NULL,
    FOREIGN KEY (product_id) REFERENCES "product" (id) ON DELETE CASCADE
);

COMMENT ON TABLE "product_detail" IS 'Table containing product details';
COMMENT ON COLUMN "product_detail".id IS 'Unique ID for the product detail, generated automatically';
COMMENT ON COLUMN "product_detail".product_id IS 'ID of the associated product';
COMMENT ON COLUMN "product_detail".price IS 'Product price';
COMMENT ON COLUMN "product_detail".currency IS 'Currency of the price : BRL, USD';
COMMENT ON COLUMN "product_detail".period IS 'Array of periods : MONTHLY, BIMONTHLY, QUARTERLY, SEMIANNUAL, ANNUAL';
COMMENT ON COLUMN "product_detail".deleted IS 'Indicates if the product detail has been deleted';
COMMENT ON COLUMN "product_detail".created_at IS 'Date and time of product detail creation';
COMMENT ON COLUMN "product_detail".created_by IS 'UUID of the user who created the product detail';
COMMENT ON COLUMN "product_detail".updated_at IS 'Date and time of the last product detail update';
COMMENT ON COLUMN "product_detail".updated_by IS 'UUID of the user who last updated the product detail';

-- Inserts for the "product" table
INSERT INTO "product" (id, name, description, deleted, created_at, created_by, updated_at, updated_by)
VALUES ('2d043b35-b04f-4c1c-b271-1c5c74b42fd2', 'Startup',
        'A comprehensive package for growing startups, with advanced collaboration features and personalized support.',
        false, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, NULL);

INSERT INTO "product" (id, name, description, deleted, created_at, created_by, updated_at, updated_by)
VALUES ('e65e8da1-8b52-4f33-bc83-8237c3690406', 'Business',
        'The perfect plan for established businesses, offering advanced project management features and data analysis.',
        false, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, NULL);

INSERT INTO "product" (id, name, description, deleted, created_at, created_by, updated_at, updated_by)
VALUES ('59c0a201-65f5-4c94-a5f6-3a14c82c8a14', 'Agency',
        'A complete solution for creative agencies, with collaboration tools, task management, and custom reporting.',
        false, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, NULL);


-- Inserts for the "product_detail" table
INSERT INTO "product_detail" (id, product_id, price, currency, period, deleted, created_at, created_by, updated_at,
                              updated_by)
VALUES ('3acca7d2-8e2d-445e-bca6-3998ef5e10ff', '2d043b35-b04f-4c1c-b271-1c5c74b42fd2', 49.99, 'USD', 'MONTHLY', false,
        CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, NULL);

INSERT INTO "product_detail" (id, product_id, price, currency, period, deleted, created_at, created_by, updated_at,
                              updated_by)
VALUES ('0fe92e7a-efef-429a-a605-715e91a0e5b2', 'e65e8da1-8b52-4f33-bc83-8237c3690406', 99.99, 'USD', 'QUARTERLY',
        false,
        CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, NULL);

INSERT INTO "product_detail" (id, product_id, price, currency, period, deleted, created_at, created_by, updated_at,
                              updated_by)
VALUES ('147dd61b-27dd-4b2b-8e1c-9d1e888bdf1b', '59c0a201-65f5-4c94-a5f6-3a14c82c8a14', 199.99, 'USD', 'SEMIANNUAL',
        false, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, NULL);

INSERT INTO "product_detail" (id, product_id, price, currency, period, deleted, created_at, created_by, updated_at,
                              updated_by)
VALUES ('f72c5b7f-57a2-4155-bdff-54db38d9a8e4', '2d043b35-b04f-4c1c-b271-1c5c74b42fd2', 199.99, 'BRL', 'MONTHLY', false,
        CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, NULL);

INSERT INTO "product_detail" (id, product_id, price, currency, period, deleted, created_at, created_by, updated_at,
                              updated_by)
VALUES ('a4f3e786-c4d4-4dd1-a8f1-804cadd7efea', 'e65e8da1-8b52-4f33-bc83-8237c3690406', 399.99, 'BRL', 'BIMONTHLY',
        false,
        CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, NULL);

INSERT INTO "product_detail" (id, product_id, price, currency, period, deleted, created_at, created_by, updated_at,
                              updated_by)
VALUES ('4e98bea3-7c9c-4d45-9f6c-913cc6b8b888', '59c0a201-65f5-4c94-a5f6-3a14c82c8a14', 799.99, 'BRL', 'SEMIANNUAL',
        false, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, NULL);

