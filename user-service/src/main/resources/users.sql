create user myUser with encrypted password 'password';
GRANT ALL PRIVILEGES ON DATABASE user to myUser;

CREATE TABLE users (
                      id SERIAL PRIMARY KEY,
                      username TEXT NOT NULL,
                      email TEXT NOT NULL,
                      password TEXT NOT NULL
);

GRANT ALL PRIVILEGES ON TABLE users TO myUser;
GRANT USAGE, SELECT ON SEQUENCE users_id_seq TO myUser;

INSERT INTO users (username, email, password)
VALUES ('mazurenko', 'mazurenko@gmail.com', '123456');

select * from users;

grant myuser to myuser;

GRANT SELECT ON users TO myUser;

ALTER TABLE users
ADD CONSTRAINT unique_email UNIQUE(email),
ADD CONSTRAINT unique_username UNIQUE(username);