CREATE TABLE "subscription"
(
    id         UUID      DEFAULT uuid_generate_v4() PRIMARY KEY,
    user_id    UUID      NOT NULL,
    plan_id    UUID      NOT NULL,
    start_at   TIMESTAMP NOT NULL,
    end_at     TIMESTAMP,
    deleted   BOOLEAN   DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID      DEFAULT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by UUID      DEFAULT NULL
);

COMMENT ON COLUMN "subscription".id IS 'Unique ID for the subscription, generated automatically';
COMMENT ON COLUMN "subscription".user_id IS 'User ID associated with the subscription';
COMMENT ON COLUMN "subscription".plan_id IS 'Plan ID associated with the subscription';
COMMENT ON COLUMN "subscription".start_at IS 'Start date and time of the subscription';
COMMENT ON COLUMN "subscription".end_at IS 'End date and time of the subscription';
COMMENT ON COLUMN "subscription".deleted IS 'Indicates if the subscription has been deleted';
COMMENT ON COLUMN "subscription".created_at IS 'Date and time of subscription creation';
COMMENT ON COLUMN "subscription".created_by IS 'UUID of the user who created the subscription';
COMMENT ON COLUMN "subscription".updated_at IS 'Date and time of the last subscription update';
COMMENT ON COLUMN "subscription".updated_by IS 'UUID of the user who last updated the subscription';

ALTER TABLE "subscription"
    ADD CONSTRAINT fk_user_subscription FOREIGN KEY (user_id) REFERENCES "user" (id) ON DELETE CASCADE;

ALTER TABLE "subscription"
    ADD CONSTRAINT fk_plan_subscription FOREIGN KEY (plan_id) REFERENCES "plan" (id) ON DELETE CASCADE;
