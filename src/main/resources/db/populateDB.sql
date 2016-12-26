DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (userId, datetime, description, calories) VALUES
  (100000, '2016-01-08 07:05:00', 'user_breakfast', 1550),
  (100000, '2016-01-08 12:05:00', 'user_lunch', 550),
  (100001, '2016-01-08 08:05:00', 'admin_breakfast', 550),
  (100001, '2016-01-08 12:05:00', 'admin_lunch', 550);