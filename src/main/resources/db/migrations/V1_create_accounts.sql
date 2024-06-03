CREATE TABLE accounts(
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    number VARCHAR(255) NOT NULL,
    agency VARCHAR(255) NOT NULL,
    balance DECIMAL(13, 2),
    limits DECIMAL(13, 2)
);



