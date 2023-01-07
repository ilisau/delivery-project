insert into couriers (first_name, last_name, phone_number, created_at, last_active_at, status)
values ('John', 'Doe', '1234567890', '2023-01-01 00:00:00', '2023-01-01 00:00:00', 'AVAILABLE'),
       ('Michael', 'Depp', '12345', '2022-12-01 00:00:00', '2022-12-05 00:00:00', 'UNAVAILABLE'),
       ('Bob', 'Jonson', '123456', '2022-11-05 00:00:00', '2022-12-03 00:00:00', 'ON_DELIVERY');

insert into restaurants (name, description)
values ('McDonalds', 'Вкусно и точка');

insert into addresses (street_name, house_number, floor_number, flat_number)
values ('Lenina', '1', '1', '1'),
       ('Lenina', '2', '2', '2'),
       ('Lenina', '3', '3', '3');

insert into restaurants_addresses (restaurant_id, address_id)
values (1, 1);

insert into employees (name, position)
values ('John', 'COOK'),
       ('Michael', 'COOK'),
       ('Bob', 'MANAGER');

insert into restaurants_employees (restaurant_id, employee_id)
values (1, 1),
       (1, 2),
       (1, 3);

insert into carts
values (default);

insert into users (name, email, password, phone_number, cart_id, created_at)
values ('Peter', 'peter@gmail.com', '$2a$12$Iq9jB0.zAt/VA.XSeWrHb.jpeWUAP6AXqP4oH3pK8hhOOfPwMzbA2', '1234', 1, '2022-12-01 00:00:00');

insert into users_addresses (user_id, address_id)
values (1, 2);

insert into items (name, description, price, type)
values ('Burger', 'Burger', 100, 'BURGER'),
       ('Fries', 'Fries', 50, 'FRIES'),
       ('Coca-Cola', 'Coca-Cola', 50, 'DRINK');

insert into restaurants_items (restaurant_id, item_id)
values (1, 1),
       (1, 2),
       (1, 3);

insert into carts_items (cart_id, item_id, quantity)
values (1, 1, 3),
       (1, 2, 2);

insert into orders (address_id, restaurant_id, cart_id, status, courier_id, created_at, delivered_at)
values (2, 1, 1, 'DELIVERED', 1, '2022-12-01 00:00:00', '2022-12-01 00:00:00');

insert into users_orders (user_id, order_id)
values (1, 1);