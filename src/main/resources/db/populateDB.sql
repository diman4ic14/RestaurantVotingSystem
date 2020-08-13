DELETE FROM user_roles;
DELETE FROM votes;
DELETE FROM dishes;
DELETE FROM restaurants;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 1;

INSERT INTO users (name, email, password)
VALUES ('admin', 'admin@yandex.ru', 'admin'),
       ('user1', 'user1@yandex.ru', 'user1'),
       ('user2', 'user2@yandex.ru', 'user2'),
       ('user3', 'user3@yandex.ru', 'user3'),
       ('user4', 'user4@yandex.ru', 'user4'),
       ('user5', 'user5@yandex.ru', 'user5'),
       ('user6', 'user6@yandex.ru', 'user6'),
       ('user7', 'user7@yandex.ru', 'user7');

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

INSERT INTO dishes (name, price, restaurant_id)
VALUES ('Sub 6 inch', 4, 9),
       ('Sub 12 inch', 6, 9),
       ('BigMac', 3, 10),
       ('French Fries', 2, 10),
       ('Orange juice', 2, 10),
       ('Whopper', 4, 11),
       ('Ice cream', 2, 11),
       ('Basket of chicken', 6, 12),
       ('Longer', 2, 12);

INSERT INTO votes (user_id, date, restaurant_id)
VALUES (1, '2020-08-13 00:00:00', 9),
       (2, '2020-08-13 00:00:00', 12),
       (3, '2020-08-13 00:00:00', 10),
       (4, '2020-08-13 00:00:00', 11),
       (5, '2020-08-13 00:00:00', 10),
       (6, '2020-08-13 00:00:00', 9),
       (7, '2020-08-13 00:00:00', 10),
       (2, '2020-08-14 00:00:00', 9),
       (3, '2020-08-14 00:00:00', 10),
       (4, '2020-08-14 00:00:00', 10);


