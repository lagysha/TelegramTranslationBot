CREATE TABLE users (
                      id SERIAL PRIMARY KEY,
                      username TEXT NOT NULL,
                      email TEXT NOT NULL,
                      password TEXT NOT NULL
);

INSERT INTO users (username, email, password)
VALUES ('mazurenko', 'mazurenko@gmail.com', '123456');

ALTER TABLE users
ADD CONSTRAINT unique_email UNIQUE(email),
ADD CONSTRAINT unique_username UNIQUE(username);