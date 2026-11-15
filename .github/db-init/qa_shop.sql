CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    age INTEGER
);

INSERT INTO users (id, name, email, age)
VALUES (1, 'Ivan', 'ivan@mail.com', 35);

SELECT setval(
           pg_get_serial_sequence('users', 'id'),
           (SELECT MAX(id) FROM users)
       );