-- -----------------------------------------------------
-- `CATEGORY`
-- -----------------------------------------------------
INSERT INTO category (id, category_name) VALUES ('1', 'COFFEE');
INSERT INTO category (id, category_name) VALUES ('2', 'LUNCH');
INSERT INTO category (id, category_name) VALUES ('3', 'DINER');
INSERT INTO category (id, category_name) VALUES ('4', 'DRINKS');
INSERT INTO category (id, category_name) VALUES ('5', 'SPECIALS');

-- -----------------------------------------------------
-- `DISH`
-- -----------------------------------------------------
-- COFFEE
INSERT INTO dish (dish_name, description, weight, price, amount, spirits, image) 
VALUES ('Single Cup Brew', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '300', '6.50', '2', '0', 'brew-coffee.webp');
INSERT INTO dish_has_category (dish_id, category_id) VALUES (1, 1);
INSERT INTO dish_has_category (dish_id, category_id) VALUES (1, 5);

INSERT INTO dish (dish_name, description, weight, price, amount, spirits, image) 
VALUES ('Cuban Shot', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '25', '4.99', '20', '0', 'example-bg.png');
INSERT INTO dish_has_category (dish_id, category_id) VALUES (2, 1);

INSERT INTO dish (dish_name, description, weight, price, amount, spirits, image) 
VALUES ('Coffee of the Day', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '45', '3.00', '20', '0', 'example-bg.png');
INSERT INTO dish_has_category (dish_id, category_id) VALUES (3, 1);

INSERT INTO dish (dish_name, description, weight, price, amount, spirits, image) 
VALUES ('Caffé Americano', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '45', '5.99', '15', '0', 'example-bg.png');
INSERT INTO dish_has_category (dish_id, category_id) VALUES (4, 1);

INSERT INTO dish (dish_name, description, weight, price, amount, spirits, image) 
VALUES ('Café Latte', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '70', '5.99', '18', '0', 'example-bg.png');
INSERT INTO dish_has_category (dish_id, category_id) VALUES (5, 1);
INSERT INTO dish_has_category (dish_id, category_id) VALUES (5, 5);

INSERT INTO dish (dish_name, description, weight, price, amount, spirits, image) 
VALUES ('Black Eyed Andy', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '55', '4.50', '40', '0', 'example-bg.png');
INSERT INTO dish_has_category (dish_id, category_id) VALUES (6, 1);

INSERT INTO dish (dish_name, description, weight, price, amount, spirits, image) 
VALUES ('Cafe au Lait', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '70', '1.55', '5', '0', 'example-bg.png');
INSERT INTO dish_has_category (dish_id, category_id) VALUES (7, 1);
INSERT INTO dish_has_category (dish_id, category_id) VALUES (7, 5);

INSERT INTO dish (dish_name, description, weight, price, amount, spirits, image) 
VALUES ('Cappuccino', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '55', '4.00', '23', '0', 'example-bg.png');
INSERT INTO dish_has_category (dish_id, category_id) VALUES (8, 1);
INSERT INTO dish_has_category (dish_id, category_id) VALUES (8, 5);

INSERT INTO dish (dish_name, description, weight, price, amount, spirits, image) 
VALUES ('Caramel Macchiato', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '55', '4.35', '32', '0', 'example-bg.png');
INSERT INTO dish_has_category (dish_id, category_id) VALUES (9, 1);
INSERT INTO dish_has_category (dish_id, category_id) VALUES (9, 5);

INSERT INTO dish (dish_name, description, weight, price, amount, spirits, image) 
VALUES ('Standard black coffee', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '45', '5.99', '24', '0', 'example-bg.png');
INSERT INTO dish_has_category (dish_id, category_id) VALUES (10, 1);

-- LUHCH
INSERT INTO dish (dish_name, description, weight, price, amount, spirits, image) 
VALUES ('Taco Salad', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '150', '14.00', '20', '0', 'example-bg.png');
INSERT INTO dish_has_category (dish_id, category_id) VALUES (11, 2);

INSERT INTO dish (dish_name, description, weight, price, amount, spirits, image) 
VALUES ('Fajita Lunch', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '225', '15.00', '25', '0', 'example-bg.png');
INSERT INTO dish_has_category (dish_id, category_id) VALUES (12, 2);

INSERT INTO dish (dish_name, description, weight, price, amount, spirits, image) 
VALUES ('Chile Relleno', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '200', '18.00', '12', '0', 'example-bg.png');
INSERT INTO dish_has_category (dish_id, category_id) VALUES (13, 2);

INSERT INTO dish (dish_name, description, weight, price, amount, spirits, image) 
VALUES ('Super Burrito', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '240', '20.00', '15', '0', 'example-bg.png');
INSERT INTO dish_has_category (dish_id, category_id) VALUES (14, 2);

-- DINER

-- DRINKS
INSERT INTO dish (dish_name, description, weight, price, amount, spirits, image) 
VALUES ('Ice Tea', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '70', '3.50', '12', '0', 'example-bg.png');
INSERT INTO dish_has_category (dish_id, category_id) VALUES (15, 4);

INSERT INTO dish (dish_name, description, weight, price, amount, spirits, image)
VALUES ('Fruit Juice', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '80', '8.00', '18', '0', 'example-bg.png');
INSERT INTO dish_has_category (dish_id, category_id) VALUES (16, 4);

INSERT INTO dish (dish_name, description, weight, price, amount, spirits, image)
VALUES ('Milkshakes', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '80', '12.00', '15', '0', 'example-bg.png');
INSERT INTO dish_has_category (dish_id, category_id) VALUES (17, 4);

INSERT INTO dish (dish_name, description, weight, price, amount, spirits, image)
VALUES ('Coke', 'Lorem ipsum dolor sit amet consectetur adipiscing.', '80', '8.00', '15', '0', 'example-bg.png');
INSERT INTO dish_has_category (dish_id, category_id) VALUES (18, 4);




-- -----------------------------------------------------
-- `ROLE`
-- -----------------------------------------------------
INSERT INTO role (id, role_name) VALUES ('1', 'MANAGER');
INSERT INTO role (id, role_name) VALUES ('2', 'CLIENT');

-- -----------------------------------------------------
-- `USER`
-- -----------------------------------------------------
INSERT INTO user (email, password, user_name, surname, gender, age_over_eighteen, role_id)
VALUES ('v_cheslav@ukr.net', '$argon2i$v=19$m=15360,t=2,p=1$yc4+DEeggle2E1ucwo5JvA$xPIjuu7mUnlW1k5Q4JbMGUQ4KaeB2toxOKJdW0979B4', 'Вячеслав', 'Гаврилюк', 'Male', '1', '1');
INSERT INTO user_details (user_id, birthdate, passport, bank_account)
VALUES ('1', '2022-5-12', 'PSserial', 'secretAccount');

INSERT INTO user (email, password, user_name, surname, gender, age_over_eighteen, role_id)
VALUES ('some@gmail.com', '$argon2i$v=19$m=15360,t=2,p=1$yc4+DEeggle2E1ucwo5JvA$xPIjuu7mUnlW1k5Q4JbMGUQ4KaeB2toxOKJdW0979B4', 'Name', 'Surname', 'Male', '1', '2');

INSERT INTO user (email, password, user_name, surname, gender, age_over_eighteen, role_id)
VALUES ('another@gmail.com', '$argon2i$v=19$m=15360,t=2,p=1$yc4+DEeggle2E1ucwo5JvA$xPIjuu7mUnlW1k5Q4JbMGUQ4KaeB2toxOKJdW0979B4', 'Name', 'Surname', 'Female', '0', '2');

-- -----------------------------------------------------
-- `BOOKING STATUS`
-- -----------------------------------------------------
INSERT INTO booking_status (id, booking_status_name) VALUES ('1', 'BOOKING');
INSERT INTO booking_status (id, booking_status_name) VALUES ('2', 'NEW');
INSERT INTO booking_status (id, booking_status_name) VALUES ('3', 'COOKING');
INSERT INTO booking_status (id, booking_status_name) VALUES ('4', 'IN_DELIVERY');
INSERT INTO booking_status (id, booking_status_name) VALUES ('5', 'WAITING_PAYMENT');
INSERT INTO booking_status (id, booking_status_name) VALUES ('6', 'PAID');
INSERT INTO booking_status (id, booking_status_name) VALUES ('7', 'COMPLETED');


-- -----------------------------------------------------
-- `CUSTOM ORDERS`
-- -----------------------------------------------------
-- USER 2
INSERT INTO custom_order (address, phone_number, payment, creation_date, close_date, user_id, booking_status_id)
VALUES ('просп. Перемоги, 32', '0962453841', '1', '2022-12-20 15:22:45', '2022-12-25 15:22:45', '2', '6');

INSERT INTO custom_order (address, phone_number, payment, creation_date, close_date, user_id, booking_status_id)
VALUES ('вул, Приозерна 7', '0962453841', '1', '2022-12-23 15:22:45', '2022-12-26 15:22:45', '2', '5');

INSERT INTO custom_order (address, phone_number, payment, creation_date, close_date, user_id, booking_status_id)
VALUES ('просп. Перемоги, 32', '0934562873', '0', '2022-12-24 15:22:45', '2022-12-26 15:22:45', '2', '4');

INSERT INTO custom_order (address, phone_number, payment, creation_date, close_date, user_id, booking_status_id)
VALUES ('Хрещатик, 26', '0934562873', '0', '2022-12-25 15:22:45', '2022-12-26 15:22:45', '2', '3');

INSERT INTO custom_order (address, phone_number, payment, creation_date, close_date, user_id, booking_status_id)
VALUES ('просп. Перемоги, 32', '0934562873', '0', '2022-12-26 15:22:45', '2022-12-26 15:22:45', '2', '2');

-- USER 3
INSERT INTO custom_order (address, phone_number, payment, creation_date, close_date, user_id, booking_status_id)
VALUES ('вул. Саксаганського, 5', '0962549874', '0', '2022-12-27 15:22:45', '2022-12-27 15:22:45', '3', '6');

INSERT INTO custom_order (address, phone_number, payment, creation_date, close_date, user_id, booking_status_id)
VALUES ('вул. Саксаганського, 5', '0962549874', '1', '2022-12-01 15:22:45', '2022-12-01 15:22:45', '3', '4');


-- -----------------------------------------------------
-- `ORDER HAS DISHES`
-- -----------------------------------------------------
INSERT INTO order_has_dishes (custom_order_id, dish_id, amount_in_order, fixed_price) VALUES ('1', '1', '1', '5.50');
INSERT INTO order_has_dishes (custom_order_id, dish_id, amount_in_order, fixed_price) VALUES ('1', '11', '2', '14.00');
INSERT INTO order_has_dishes (custom_order_id, dish_id, amount_in_order, fixed_price) VALUES ('1', '13', '2', '18.00');
INSERT INTO order_has_dishes (custom_order_id, dish_id, amount_in_order, fixed_price) VALUES ('1', '14', '2', '20.00');

INSERT INTO order_has_dishes (custom_order_id, dish_id, amount_in_order, fixed_price) VALUES ('2', '4', '1', '4.50');
INSERT INTO order_has_dishes (custom_order_id, dish_id, amount_in_order, fixed_price) VALUES ('2', '16', '1', '7.50');

INSERT INTO order_has_dishes (custom_order_id, dish_id, amount_in_order, fixed_price) VALUES ('3', '8', '2', '17.50');
INSERT INTO order_has_dishes (custom_order_id, dish_id, amount_in_order, fixed_price) VALUES ('3', '18', '2', '7.00');
INSERT INTO order_has_dishes (custom_order_id, dish_id, amount_in_order, fixed_price) VALUES ('3', '11', '2', '18.00');
INSERT INTO order_has_dishes (custom_order_id, dish_id, amount_in_order, fixed_price) VALUES ('3', '12', '2', '15.00');

INSERT INTO order_has_dishes (custom_order_id, dish_id, amount_in_order, fixed_price) VALUES ('4', '5', '2', '4.99');

INSERT INTO order_has_dishes (custom_order_id, dish_id, amount_in_order, fixed_price) VALUES ('5', '9', '1', '4.70');
INSERT INTO order_has_dishes (custom_order_id, dish_id, amount_in_order, fixed_price) VALUES ('5', '14', '2', '19.60');
INSERT INTO order_has_dishes (custom_order_id, dish_id, amount_in_order, fixed_price) VALUES ('5', '17', '1', '11.50');

INSERT INTO order_has_dishes (custom_order_id, dish_id, amount_in_order, fixed_price) VALUES ('6', '1', '1', '5.50');
INSERT INTO order_has_dishes (custom_order_id, dish_id, amount_in_order, fixed_price) VALUES ('6', '11', '2', '18.00');
INSERT INTO order_has_dishes (custom_order_id, dish_id, amount_in_order, fixed_price) VALUES ('6', '12', '2', '15.00');

INSERT INTO order_has_dishes (custom_order_id, dish_id, amount_in_order, fixed_price) VALUES ('7', '13', '2', '18.00');
INSERT INTO order_has_dishes (custom_order_id, dish_id, amount_in_order, fixed_price) VALUES ('7', '9', '1', '4.70');
