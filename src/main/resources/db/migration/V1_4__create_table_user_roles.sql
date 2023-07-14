CREATE TABLE user_roles
(
    user_id UUID NOT NULL,
    role_id UUID NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES "user" (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES "role" (id) ON DELETE CASCADE
);

COMMENT ON TABLE user_roles IS 'Table to store the one-to-many relationship between an user and your roles';
COMMENT ON COLUMN user_roles.user_id IS 'User ID associated with the role';
COMMENT ON COLUMN user_roles.role_id IS 'Role ID associated with the user';
