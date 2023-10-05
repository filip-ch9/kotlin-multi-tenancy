-- Create the tables
CREATE TABLE users
(
    user_id    SERIAL NOT NULL PRIMARY KEY,
    username   VARCHAR(50) UNIQUE  NOT NULL,
    password   VARCHAR(100)        NOT NULL,
    email      VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE tasks
(
    task_id      SERIAL NOT NULL PRIMARY KEY,
    user_id      INT,
    title        VARCHAR(255) NOT NULL,
    description  TEXT,
    due_date     DATE,
    is_completed BOOLEAN      NOT NULL DEFAULT false,
    category     TEXT,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

-- Insert dummy data
INSERT INTO users (username, password, email)
VALUES ('user1', 'password1', 'user1@example.com'),
       ('user2', 'password2', 'user2@example.com');

INSERT INTO tasks (user_id, title, description, due_date, is_completed, category)
VALUES (1, 'Task 1', 'Description for Task 1', DATE '2023-09-10', false, 'WORK'),
       (1, 'Task 2', 'Description for Task 2', DATE '2023-09-15', false, 'PERSONAL'),
       (2, 'Task 3', 'Description for Task 3', DATE '2023-09-12', false, 'SHOPPING');