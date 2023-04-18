create user myUser with encrypted password 'password';
GRANT ALL PRIVILEGES ON DATABASE user to myUser;

CREATE TABLE users (
                      id SERIAL PRIMARY KEY,
                      username TEXT NOT NULL,
                      email TEXT NOT NULL,
                      password TEXT NOT NULL
);

GRANT ALL PRIVILEGES ON TABLE users TO myUser;

INSERT INTO users (username, email, password)
VALUES ('mazurenko', 'mazurenko@gmail.com', '123456');

select * from users;