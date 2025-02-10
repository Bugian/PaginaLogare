# CREATE TABLE IF NOT EXISTS Run (
#     id INT NOT NULL,
#     title varchar(255) NOT NULL,
#     started_on timestamp NOT NULL,
#     completed_on timestamp NOT NULL,
#     miles INT NOT NULL,
#     location varchar(10) NOT NULL,
#     version INT,
#     PRIMARY KEY (id)
# );

CREATE TABLE IF NOT EXISTS users (
   id INT NOT NULL AUTO_INCREMENT,
   username varchar(255) NOT NULL,
   email varchar(255) NOT NULL,
   password varchar(255) NOT NULL,
   accountCreatedOn timestamp NOT NULL,
   PRIMARY KEY (id)
);