-- liquibase formatted sql

-- changeset solvd:1674032636372-1
INSERT INTO "items" ("id", "name", "description", "price", "type", "available") VALUES (1, 'Burger', 'Burger', 100.00, 'BURGER', TRUE);
INSERT INTO "items" ("id", "name", "description", "price", "type", "available") VALUES (2, 'Fries', 'Fries', 50.00, 'FRIES', TRUE);
INSERT INTO "items" ("id", "name", "description", "price", "type", "available") VALUES (3, 'Coca-Cola', 'Coca-Cola', 50.00, 'DRINK', TRUE);

-- changeset solvd:1674032636372-2
INSERT INTO "couriers" ("id", "first_name", "last_name", "phone_number", "created_at", "last_active_at", "status", "email", "password") VALUES (1, 'John', 'Doe', '1234567890', '2023-01-01 00:00:00', '2023-01-01 00:00:00', 'AVAILABLE', 'johndoe@gmail.com', '$2a$12$Iq9jB0.zAt/VA.XSeWrHb.jpeWUAP6AXqP4oH3pK8hhOOfPwMzbA2');
INSERT INTO "couriers" ("id", "first_name", "last_name", "phone_number", "created_at", "last_active_at", "status", "email", "password") VALUES (2, 'Michael', 'Depp', '12345', '2022-12-01 00:00:00', '2022-12-05 00:00:00', 'UNAVAILABLE', 'Michael@mail.ru', '$2a$12$Iq9jB0.zAt/VA.XSeWrHb.jpeWUAP6AXqP4oH3pK8hhOOfPwMzbA2');
INSERT INTO "couriers" ("id", "first_name", "last_name", "phone_number", "created_at", "last_active_at", "status", "email", "password") VALUES (3, 'Bob', 'Jonson', '123456', '2022-11-05 00:00:00', '2022-12-03 00:00:00', 'UNAVAILABLE', 'bob@yahoo.com', '$2a$12$Iq9jB0.zAt/VA.XSeWrHb.jpeWUAP6AXqP4oH3pK8hhOOfPwMzbA2');

-- changeset solvd:1674032636372-3
INSERT INTO "restaurants" ("id", "name", "description") VALUES (1, 'McDonalds', 'Вкусно и точка');

-- changeset solvd:1674032636372-4
INSERT INTO "carts" ("id") VALUES (1);
INSERT INTO "carts" ("id") VALUES (2);

-- changeset solvd:1674032636372-5
INSERT INTO "users" ("id", "cart_id", "name", "email", "password", "phone_number", "created_at") VALUES (1, 2, 'Peter', 'peter@gmail.com', '$2a$12$Iq9jB0.zAt/VA.XSeWrHb.jpeWUAP6AXqP4oH3pK8hhOOfPwMzbA2', '1234', '2022-12-01 00:00:00');

-- changeset solvd:1674032636372-6
INSERT INTO "addresses" ("id", "street_name", "house_number", "floor_number", "flat_number")
VALUES (1, 'Lenina', 1, 1, 1);
INSERT INTO "addresses" ("id", "street_name", "house_number", "floor_number", "flat_number")
VALUES (2, 'Lenina', 2, 2, 2);
INSERT INTO "addresses" ("id", "street_name", "house_number", "floor_number", "flat_number")
VALUES (3, 'Lenina', 3, 3, 3);

-- changeset solvd:1674032636372-7
INSERT INTO "carts_items" ("cart_id", "item_id", "quantity")
VALUES (1, 1, 3);
INSERT INTO "carts_items" ("cart_id", "item_id", "quantity")
VALUES (1, 2, 2);

-- changeset solvd:1674032636372-8
INSERT INTO "employees" ("id", "name", "position", "email", "password")
VALUES (1, 'John', 'COOK', 'john@gmail.com', '$2a$12$Iq9jB0.zAt/VA.XSeWrHb.jpeWUAP6AXqP4oH3pK8hhOOfPwMzbA2');
INSERT INTO "employees" ("id", "name", "position", "email", "password")
VALUES (2, 'Michael', 'COOK', 'michael@mail.ru', '$2a$12$Iq9jB0.zAt/VA.XSeWrHb.jpeWUAP6AXqP4oH3pK8hhOOfPwMzbA2');
INSERT INTO "employees" ("id", "name", "position", "email", "password")
VALUES (3, 'Bob', 'MANAGER', 'bob@rambler.ru', '$2a$12$Iq9jB0.zAt/VA.XSeWrHb.jpeWUAP6AXqP4oH3pK8hhOOfPwMzbA2');

-- changeset solvd:1674032636372-9
INSERT INTO "orders" ("id", "address_id", "cart_id", "courier_id", "status", "created_at", "delivered_at")
VALUES (1, 2, 1, 1, 'DELIVERED', '2022-12-01 00:00:00', '2022-12-01 00:00:00');

-- changeset solvd:1674032636372-10
INSERT INTO "restaurants_addresses" ("restaurant_id", "address_id")
VALUES (1, 1);

-- changeset solvd:1674032636372-11
INSERT INTO "restaurants_employees" ("restaurant_id", "employee_id")
VALUES (1, 1);
INSERT INTO "restaurants_employees" ("restaurant_id", "employee_id")
VALUES (1, 2);
INSERT INTO "restaurants_employees" ("restaurant_id", "employee_id") VALUES (1, 3);

-- changeset solvd:1674032636372-12
INSERT INTO "restaurants_items" ("restaurant_id", "item_id") VALUES (1, 1);
INSERT INTO "restaurants_items" ("restaurant_id", "item_id") VALUES (1, 2);
INSERT INTO "restaurants_items" ("restaurant_id", "item_id") VALUES (1, 3);

-- changeset solvd:1674032636372-13
INSERT INTO "users_addresses" ("user_id", "address_id") VALUES (1, 2);

-- changeset solvd:1674032636372-14
INSERT INTO "users_orders" ("user_id", "order_id")
VALUES (1, 1);

-- changeset solvd:1674032636372-15
INSERT INTO "users_roles" ("user_id", "role")
VALUES (1, 'ROLE_USER');

-- changeset solvd:1674032636372-16
INSERT INTO "couriers_roles" ("courier_id", "role")
VALUES (1, 'ROLE_COURIER');
INSERT INTO "couriers_roles" ("courier_id", "role")
VALUES (2, 'ROLE_COURIER');
INSERT INTO "couriers_roles" ("courier_id", "role")
VALUES (3, 'ROLE_COURIER');

-- changeset solvd:1674032636372-17
INSERT INTO "employees_roles" ("employee_id", "role")
VALUES (1, 'ROLE_EMPLOYEE');
INSERT INTO "employees_roles" ("employee_id", "role")
VALUES (2, 'ROLE_EMPLOYEE');
INSERT INTO "employees_roles" ("employee_id", "role")
VALUES (3, 'ROLE_MANAGER');