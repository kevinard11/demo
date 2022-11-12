-- seed role data
INSERT INTO roles
("role", description)
VALUES
('USER', 'app user'),
('ADMIN', 'app admin'),
('SYSTEM_SERVICE', 'app system');

-- seed kyc data
INSERT INTO kycs
("name", email)
VALUES
('user', 'string'),
('admin', 'string'),
('user admin', 'string'),
('system', 'string');

-- seed user data
INSERT INTO users
(username, "password", kyc_id)
VALUES
('string', '$2a$10$AE8L/tAllzZz5ENORJiGquLtsAFd2/l6zFduR9tYF2VSxJM/UPihO', 1),
('string2', '$2a$10$AE8L/tAllzZz5ENORJiGquLtsAFd2/l6zFduR9tYF2VSxJM/UPihO', 2),
('string3', '$2a$10$AE8L/tAllzZz5ENORJiGquLtsAFd2/l6zFduR9tYF2VSxJM/UPihO', 3),
('string4', '$2a$10$AE8L/tAllzZz5ENORJiGquLtsAFd2/l6zFduR9tYF2VSxJM/UPihO',4);

-- seed user role data
INSERT INTO users_roles
(user_id, role_id)
VALUES
(1, 1),
(2, 2),
(3, 1),
(3, 2),
(4, 3);

-- seed demo data
INSERT INTO demo
(demo_id, demo_name, description, "level", active, "version", user_id)
VALUES
('string', 'string', 'string', 0, true, 1, 1),
('string2', 'string2', 'string2', 0, true, 1, 1);

