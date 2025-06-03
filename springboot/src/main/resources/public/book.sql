/* CREATE DATABASE library;
USE library;
CREATE TABLE book (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    pd DATE NOT NULL
); */
USE library;
SHOW TABLES;
DESCRIBE book;
SELECT * FROM book;
DELETE FROM book;
DROP TABLE book_sequence;
