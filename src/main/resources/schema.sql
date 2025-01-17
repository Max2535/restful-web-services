-- สร้างตาราง users
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL
);

-- สร้างตาราง posts
CREATE TABLE posts (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       content TEXT NOT NULL,
                       user_id BIGINT NOT NULL,
                       CONSTRAINT FK_user_post FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
