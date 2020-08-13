DROP TABLE IF EXISTS dishes CASCADE;
DROP TABLE IF EXISTS restaurants CASCADE;
DROP TABLE IF EXISTS user_roles CASCADE;
DROP TABLE IF EXISTS votes CASCADE;
DROP TABLE IF EXISTS users CASCADE;

DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 1;

CREATE TABLE users
(
    id         INTEGER   DEFAULT global_seq.nextval PRIMARY KEY,
    name       VARCHAR                 NOT NULL,
    email      VARCHAR                 NOT NULL,
    password   VARCHAR                 NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL,
    enabled    BOOLEAN   DEFAULT TRUE  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_inx ON users (email);

CREATE TABLE user_roles
(
    role    VARCHAR,
    user_id INTEGER NOT NULL,
    CONSTRAINT user_roles_inx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
    id   INTEGER DEFAULT global_seq.nextval PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE UNIQUE INDEX restaurants_name_inx ON restaurants (name);

CREATE TABLE dishes
(
    id            INTEGER DEFAULT global_seq.nextval PRIMARY KEY,
    name          VARCHAR       NOT NULL,
    price         DECIMAL(9, 2) NOT NULL,
    restaurant_id INTEGER       NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);

CREATE TABLE votes
(
    id            INTEGER   DEFAULT global_seq.nextval PRIMARY KEY,
    user_id       INTEGER                 NOT NULL,
    date          TIMESTAMP DEFAULT now() NOT NULL,
    restaurant_id INTEGER                 NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX vote_unique_user_date_inx ON votes (user_id, date);