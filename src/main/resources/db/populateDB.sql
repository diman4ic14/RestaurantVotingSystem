DELETE FROM user_roles;
DELETE FROM votes;
DELETE FROM dishes;
DELETE FROM restaurants;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 1;

INSERT INTO users (name, email, password)
VALUES ('admin', 'admin@yandex.ru', '{noop}admin'),
       ('user1', 'user1@yandex.ru', '{noop}user1'),
       ('user2', 'user2@yandex.ru', '{noop}user2'),
       ('user3', 'user3@yandex.ru', '{noop}user3'),
       ('user4', 'user4@yandex.ru', '{noop}user4'),
       ('user5', 'user5@yandex.ru', '{noop}user5'),
       ('user6', 'user6@yandex.ru', '{noop}user6'),
       ('user7', 'user7@yandex.ru', '{noop}user7');

INSERT INTO user_roles (role, user_id)
VALUES ('ADMIN', 1),
       ('USER', 2),
       ('USER', 3),
       ('USER', 4),
       ('USER', 5),
       ('USER', 6),
       ('USER', 7),
       ('USER', 8);

INSERT INTO restaurants (name)
VALUES ('Subway'),
       ('McDonalds'),
       ('Burger King'),
       ('KFC');

INSERT INTO dishes (name, date, price, restaurant_id)
VALUES ('Sub 6 inch', cast(current_date AS timestamp(0)), 4, 9),
       ('Sub 12 inch', cast(current_date AS timestamp(0)), 6, 9),
       ('BigMac', cast(current_date AS timestamp(0)), 3, 10),
       ('French Fries', cast(current_date AS timestamp(0)), 2, 10),
       ('Orange juice', cast(current_date AS timestamp(0)), 2, 10),
       ('Whopper', cast(current_date AS timestamp(0)), 4, 11),
       ('Ice cream', cast(current_date AS timestamp(0)), 2, 11),
       ('Basket of chicken', cast(current_date AS timestamp(0)), 6, 12),
       ('Longer', cast(current_date AS timestamp(0)), 2, 12);

INSERT INTO votes (user_id, date, restaurant_id)
VALUES (2, cast(current_date AS timestamp(0)), 10),
       (3, cast(current_date AS timestamp(0)), 10),
       (4, cast(current_date AS timestamp(0)), 11),
       (5, cast(current_date AS timestamp(0)), 12),
       (6, cast(current_date AS timestamp(0)), 10),
       (7, cast(current_date AS timestamp(0)), 9),
       (8, cast(current_date AS timestamp(0)), 9);


