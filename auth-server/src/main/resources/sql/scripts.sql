CREATE DATABASE security;

CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE CHECK ( name LIKE 'ROLE_%' )
);

INSERT INTO roles (name)
VALUES ('ROLE_USER'), ('ROLE_ADMIN');

INSERT INTO roles (name, is_default)
VALUES ('ROLE_VERIFIED', false);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username TEXT UNIQUE NOT NULL,
    email TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL
);

CREATE TABLE users_roles (
    role_id INT REFERENCES roles ON UPDATE CASCADE ON DELETE CASCADE,
    user_id INT REFERENCES users ON UPDATE CASCADE ON DELETE CASCADE
);

ALTER TABLE users
DROP COLUMN email_verified;

ALTER TABLE roles
ADD COLUMN is_default BOOLEAN NOT NULL DEFAULT FALSE;

select * from roles;
select * from users;
update roles set is_default=true where id=1;