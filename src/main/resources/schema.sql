-- First drop the table if it exists
DROP TABLE IF EXISTS user;

-- Then create the table with correct column names
CREATE TABLE user (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    accountCreatedOn TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY (username),
    UNIQUE KEY (email)
);