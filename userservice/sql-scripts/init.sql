CREATE DATABASE  IF NOT EXISTS `fizzybudget_user_db`;

USE `fizzybudget_user_db`;

DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `roles`;
DROP TABLE IF EXISTS `user_roles`;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    activated tinyint NOT NULL DEFAULT 0,
    provider VARCHAR(50) DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO users (username, email, password_hash, activated, provider, created_at, updated_at)
VALUES 
('user', 'user@gmail.com', '{noop}user', 1, NULL, NOW(), NOW());

CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL
);

INSERT INTO roles (role_name)
VALUES 
('ROLE_REGULAR'),
('ROLE_MANAGER'),
('ROLE_ADMIN');

CREATE TABLE user_roles (
    user_id BIGINT,
    role_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id),
    PRIMARY KEY (user_id, role_id)
);

INSERT INTO user_roles 
VALUES 
(1, 1),
(1, 2),
(1, 3);