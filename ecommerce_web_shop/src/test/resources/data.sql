
CREATE TABLE IF NOT EXISTS roles (id int, roleName varchar(255));

INSERT INTO roles VALUES (1, 'ROLE_MANAGER');



CREATE TABLE IF NOT EXISTS users (id int,
                                 email varchar(255),
                                 first_name varchar(255),
                                 last_name varchar(255),
                                 password varchar(255),
                                 role_id int
   /* CONSTRAINT FK_role_user FOREIGN KEY (role_id) REFERENCES dbo.[roles](id)*/
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
-- INSERT INTO users VALUES (2, 'bobanrajovic@hotmail.com', 'Ivan', 'Ivanovic', '12345', 1);
-- INSERT INTO users VALUES (3, 'testproba@hotmail.com', 'Ivan', 'Ivanovic', '12', 1);

INSERT INTO product VALUES (1, 'blaaaa', 100.0, 5, current_date());

INSERT INTO basket VALUES (1, 1);

INSERT INTO basket_contents VALUES (1, 1, 3);
INSERT INTO basket_contents VALUES (1, 2, 2);
