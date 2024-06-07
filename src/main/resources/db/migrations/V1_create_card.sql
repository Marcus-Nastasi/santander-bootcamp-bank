CREATE TABLE cards(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    number VARCHAR(255) NOT NULL,
    limits DECIMAL(13, 2),
    limit_spent DECIMAL(13, 2)
);


