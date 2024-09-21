CREATE DATABASE  IF NOT EXISTS `fizzybudget_user_db`;

USE `fizzybudget_user_db`;

DROP TABLE IF EXISTS `user_role`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `role`;
CREATE TABLE `user` (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    activated tinyint(1) NOT NULL DEFAULT 0,
    provider VARCHAR(50) DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO `user` (username, email, password_hash, activated, provider, created_at, updated_at)
VALUES 
('user', 'user@gmail.com', '{noop}user', 1, NULL, NOW(), NOW());

CREATE TABLE `role` (
    role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL
);

INSERT INTO `role` (role_name)
VALUES 
('ROLE_USER'),
('ROLE_MANAGER'),
('ROLE_ADMIN');

CREATE TABLE `user_role` (
    user_id BIGINT,
    role_id BIGINT,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES `user`(user_id),
    FOREIGN KEY (role_id) REFERENCES `role`(role_id),
    PRIMARY KEY (user_id, role_id)
);

INSERT INTO `user_role` (user_id, role_id, created_at, updated_at)
VALUES 
(1, 1, NOW(), NOW()),
(1, 2, NOW(), NOW()),
(1, 3, NOW(), NOW());