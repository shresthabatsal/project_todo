create database TaskToDo;
use TaskToDo;
CREATE TABLE tasks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    due_date DATE,
    repeat_interval VARCHAR(50)
);
