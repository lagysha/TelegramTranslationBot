create user myUser with encrypted password 'password';
grant all privileges on database "user" to myUser;

CREATE TABLE users (
   id SERIAL PRIMARY KEY,
   username TEXT NOT NULL,
   email TEXT NOT NULL,
   password TEXT NOT NULL
);