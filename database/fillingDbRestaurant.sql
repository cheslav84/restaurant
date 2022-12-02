-- -----------------------------------------------------
-- `CATEGORY`
-- -----------------------------------------------------
INSERT INTO category (name) VALUE ('Coffee');
INSERT INTO category (name) VALUE ('Lunch');
INSERT INTO category (name) VALUE ('Diner');
INSERT INTO category (name) VALUE ('Drinks');

-- -----------------------------------------------------
-- `DISH`
-- -----------------------------------------------------
-- COFFEE
INSERT INTO dish (name, description, price, amount, special, category_id) 
VALUES ('Single Cup Brew', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '6.50', '20', '1', '1');

INSERT INTO dish (name, description, price, amount, special, category_id) 
VALUES ('Cuban Shot', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '4.99', '20', '0', '1');

INSERT INTO dish (name, description, price, amount, special, category_id) 
VALUES ('Coffee of the Day', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '3.00', '20', '0', '1');

INSERT INTO dish (name, description, price, amount, special, category_id) 
VALUES ('Caffé Americano', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '5.99', '15', '0', '1');

INSERT INTO dish (name, description, price, amount, special, category_id) 
VALUES ('Café Latte', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '5.99', '18', '1', '1');

INSERT INTO dish (name, description, price, amount, special, category_id) 
VALUES ('Black Eyed Andy', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '4.50', '40', '1', '1');

INSERT INTO dish (name, description, price, amount, special, category_id) 
VALUES ('Cafe au Lait', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '1.55', '5', '1', '1');

INSERT INTO dish (name, description, price, amount, special, category_id) 
VALUES ('Cappuccino', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '4.00', '23', '1', '1');

INSERT INTO dish (name, description, price, amount, special, category_id) 
VALUES ('Caramel Macchiato', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '4.35', '32', '1', '1');

INSERT INTO dish (name, description, price, amount, special, category_id) 
VALUES ('Standard black coffee', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '5.99', '24', '0', '1');

-- LUHCH
INSERT INTO dish (name, description, price, amount, special, category_id) 
VALUES ('Taco Salad', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '14.00', '20', '0', '2');

INSERT INTO dish (name, description, price, amount, special, category_id) 
VALUES ('Fajita Lunch', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '15.00', '25', '0', '2');

INSERT INTO dish (name, description, price, amount, special, category_id) 
VALUES ('Chile Relleno', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '18.00', '12', '0', '2');

INSERT INTO dish (name, description, price, amount, special, category_id) 
VALUES ('Super Burrito', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '20.00', '15', '0', '2');

-- DINER

-- DRINKS
INSERT INTO dish (name, description, price, amount, special, category_id) 
VALUES ('Ice Tea', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '3.50', '12', '0', '4');

INSERT INTO dish (name, description, price, amount, special, category_id) 
VALUES ('Fruit Juice', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '8.00', '18', '0', '4');

INSERT INTO dish (name, description, price, amount, special, category_id) 
VALUES ('Milkshakes', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '12.00', '15', '0', '4');

INSERT INTO dish (name, description, price, amount, special, category_id) 
VALUES ('Coke', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '8.00', '15', '0', '4');

-- -----------------------------------------------------
-- `ROLE`
-- -----------------------------------------------------
INSERT INTO role (name) VALUE ('Manager');
INSERT INTO role (name) VALUE ('Client');

-- -----------------------------------------------------
-- `USER`
-- -----------------------------------------------------
INSERT INTO user (email, password, name, surname, gender, age, role_id) 
VALUES ('v_cheslav@ukr.net', 'pass!@#123', 'Вячеслав', 'Гаврилюк', 'male', '38', '1');


-- -----------------------------------------------------
-- `booking STATUS`
-- -----------------------------------------------------
INSERT INTO booking_status (name) VALUE ('new');
INSERT INTO booking_status (name) VALUE ('cooking');
INSERT INTO booking_status (name) VALUE ('in_delivery');
INSERT INTO booking_status (name) VALUE ('vaiting_payment');
INSERT INTO booking_status (name) VALUE ('completed');



-- SELECT * FROM category ORDER BY id
-- SELECT * FROM dish ORDER BY id
-- SELECT * FROM role ORDER BY id
-- SELECT * FROM user ORDER BY id
-- SELECT * FROM booking_status order by id
 -- SELECT * FROM dish d JOIN category c ORDER BY c.name
SELECT d.*, c.name as 'category_name' FROM dish d JOIN category c ON c.name='coffee'


 





