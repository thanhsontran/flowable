-- Roles
INSERT INTO roles (id, name, description) VALUES
    (RANDOM_UUID(), 'STAFF', 'Regular employee - creates PRs'),
    (RANDOM_UUID(),'SUPERVISOR', 'Team supervisor'),
    (RANDOM_UUID(),'MANAGER', 'Department manager'),
    (RANDOM_UUID(),'DIRECTOR','Division director'),
    (RANDOM_UUID(),'CFO', 'Chief Financial Officer');

-- Users
INSERT INTO users (id, username, role_id) VALUES
    (RANDOM_UUID(), 'son.tran', (SELECT id FROM roles WHERE name = 'STAFF')),
    (RANDOM_UUID(), 'huong.nguyen', (SELECT id FROM roles WHERE name = 'SUPERVISOR')),
    (RANDOM_UUID(), 'khang.nguyen', (SELECT id FROM roles WHERE name = 'MANAGER')),
    (RANDOM_UUID(), 'phuc.nguyen', (SELECT id FROM roles WHERE name = 'DIRECTOR')),
    (RANDOM_UUID(), 'king', (SELECT id FROM roles WHERE name = 'CFO')); 

-- DOA Categories
INSERT INTO doa_categories (id, name, description) VALUES
    (RANDOM_UUID(), 'Office Supplies', 'Stationery, printer ink, etc.'),
    (RANDOM_UUID(), 'Travel Expenses', 'Flights, hotels, meals during business trips'),
    (RANDOM_UUID(), 'Software Licenses', 'Tools and software subscriptions'),
    (RANDOM_UUID(), 'Hardware Purchases', 'Laptops, monitors, peripherals'),
    (RANDOM_UUID(), 'Consulting Services', 'External consultants and contractors');

-- DOA Rules
INSERT INTO doa_rules (id, category_id, min_amount, max_amount, currency, approver_role_id, priority, is_active) VALUES
    (RANDOM_UUID(), (SELECT id FROM doa_categories WHERE name = 'Office Supplies'), 0.00, 50000000.00, 'VND', (SELECT id FROM roles WHERE name = 'SUPERVISOR'), 1, TRUE),
    (RANDOM_UUID(), (SELECT id FROM doa_categories WHERE name = 'Office Supplies'), 50000000.01, 100000000.00, 'VND', (SELECT id FROM roles WHERE name = 'MANAGER'), 1, TRUE),
    (RANDOM_UUID(), (SELECT id FROM doa_categories WHERE name = 'Office Supplies'), 100000000.01, 200000000.0, 'VND', (SELECT id FROM roles WHERE name = 'DIRECTOR'), 1, TRUE),
    (RANDOM_UUID(), (SELECT id FROM doa_categories WHERE name = 'Office Supplies'), 200000000.0, NULL, 'VND', (SELECT id FROM roles WHERE name = 'CFO'), 1, TRUE);