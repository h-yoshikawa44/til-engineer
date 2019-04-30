CREATE DATABASE test;

USE test;

CREATE TABLE user (
    id int AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255)
);

INSERT INTO user (name, email) VALUES
    ('taro', 'taro@example.com'),
    ('jiro', 'jiro@example.com');