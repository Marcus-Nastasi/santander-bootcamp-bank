CREATE TABLE users(
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    account_id VARCHAR(255) NOT NULL,
    card_id JSON,
    FOREIGN KEY (account_id) REFERENCES accounts(id)
);


