/*
CREATE TABLE `User` (
                         id int,
                         roleName varchar(255)
);
*/

CREATE TABLE IF NOT EXISTS roles (id int, roleName varchar(255));

INSERT INTO roles VALUES (1, 'ROLE_MANAGER');



CREATE TABLE IF NOT EXISTS users (id int,
                                 email varchar(255),
                                 firstName varchar(255),
                                 lastName varchar(255),
                                 password varchar(255),
                                 roleId int
);

INSERT INTO users VALUES (1, 'ivanivanovic231@hotmail.com', 'Ivan', 'Ivanovic', '123', 1);


/*create table products (id int primary key not null,
                        name varchar,
                        price double,
                        stockAmount int);*/