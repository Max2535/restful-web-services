-- เพิ่มข้อมูลผู้ใช้ (Users)
INSERT INTO users (name, email) VALUES ('John Doe', 'john.doe@example.com');
INSERT INTO users (name, email) VALUES ('Jane Smith', 'jane.smith@example.com');

-- เพิ่มข้อมูลโพสต์ (Posts)
INSERT INTO posts (title, content, user_id) VALUES ('First Post', 'This is the content of the first post.', 1);
INSERT INTO posts (title, content, user_id) VALUES ('Second Post', 'This is another post by John.', 1);
INSERT INTO posts (title, content, user_id) VALUES ('Jane''s Post', 'Content for Jane''s post.', 2);
