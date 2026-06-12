-- Compatible with PostgreSQL and H2

CREATE TABLE IF NOT EXISTS roles (
    id UUID PRIMARY KEY,
    name VARCHAR(20) UNIQUE,
    description VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    role_id UUID,
    CONSTRAINT fk_users_role FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Required by doa_rules(category_id) foreign key
CREATE TABLE IF NOT EXISTS doa_categories (
    id UUID PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS PR (
    id UUID PRIMARY KEY,
    amount DECIMAL(18, 2) NOT NULL,
    currency VARCHAR(3) DEFAULT 'VND',
    requester_id UUID,
    category_id UUID,
    current_approval_level SMALLINT,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT fk_pr_category FOREIGN KEY (category_id) REFERENCES doa_categories(id)
);

CREATE TABLE IF NOT EXISTS doa_rules (
    id UUID PRIMARY KEY,
    category_id UUID,
    min_amount DECIMAL(18, 2) DEFAULT 0.00,
    max_amount DECIMAL(18, 2),
    currency VARCHAR(3) DEFAULT 'VND',
    approver_role_id UUID,
    priority SMALLINT DEFAULT 1,
    is_active BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_doa_rules_category FOREIGN KEY (category_id) REFERENCES doa_categories(id)
);

CREATE TABLE IF NOT EXISTS doa_approval_steps (
    id UUID PRIMARY KEY,
    approval_object_id UUID NOT NULL,
    rule_id UUID,
    approval_level SMALLINT NOT NULL,
    is_final_step BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_approval_steps_rule FOREIGN KEY (rule_id) REFERENCES doa_rules(id)
);
