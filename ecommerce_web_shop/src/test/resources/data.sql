
CREATE TABLE IF NOT EXISTS roles (id int, roleName varchar(255));

INSERT INTO roles VALUES (1, 'ROLE_MANAGER');



CREATE TABLE IF NOT EXISTS users (id int,
                                 email varchar(255),
                                 first_name varchar(255),
                                 last_name varchar(255),
                                 password varchar(255),
                                 role_id int
);

CREATE TABLE IF NOT EXISTS product (id int,
                                    product_name varchar(255) null,
                                    price double null,
                                    stock_amount int null,
                                    date_added DATE null);

CREATE TABLE IF NOT EXISTS basket (id int,
                                   user_id int);

CREATE TABLE IF NOT EXISTS basket_contents (
                                            basket_id int,
                                            product_id int,
                                            quantity int,
                                            PRIMARY KEY (basket_id, product_id));

CREATE TABLE IF NOT EXISTS orders (id int,
                                   address varchar(255),
                                   city varchar(255),
                                   total_price double,
                                   user_id int);

CREATE TABLE IF NOT EXISTS order_contents (id int,
                                        order_id int,
                                        product_id int,
                                        name varchar(255),
                                        price double,
                                        quantity int);


INSERT INTO users VALUES (1, 'ivanivanovic231@hotmail.com', 'Ivan', 'Ivanovic', '123', 1);

-- za exception
INSERT INTO users VALUES (2, 'bobanrajovic@hotmail.com', 'Boban', 'Rajovic', '12345', 1);

INSERT INTO product (id, product_name, price, stock_amount, date_added) VALUES (1, 'Mobile Phone',  100.0, 5, CURDATE());
INSERT INTO product (id, product_name, price, stock_amount, date_added) VALUES (2, 'TV',  260.0, 7, CURDATE());
-- za exception
INSERT INTO product (id, product_name, price, stock_amount, date_added) VALUES (3, 'Headphones',  10.0, 4, CURDATE());
INSERT INTO product (id, product_name, price, stock_amount, date_added) VALUES (4, 'Mouse',  20.0, 9, CURDATE());

INSERT INTO basket VALUES (1, 1);

-- za exception
INSERT INTO basket VALUES (2, 2);

INSERT INTO basket_contents (basket_id, product_id, quantity) VALUES (1, 1, 3);
INSERT INTO basket_contents (basket_id, product_id, quantity) VALUES (1, 2, 2);

-- za exception
INSERT INTO basket_contents (basket_id, product_id, quantity) VALUES (2, 3, 3);
INSERT INTO basket_contents (basket_id, product_id, quantity) VALUES (2, 4, 10);
