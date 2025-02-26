USE pagina_logare;
CREATE TABLE IF NOT EXISTS user (
     id INT NOT NULL AUTO_INCREMENT,
     username varchar(255) NOT NULL,
     email varchar(255) NOT NULL,
     password varchar(255) NOT NULL,
     accountCreatedOn timestamp NOT NULL,
     PRIMARY KEY (id)
);