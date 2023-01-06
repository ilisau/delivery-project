create table if not exists couriers
(
    id             bigserial      not null,
    first_name     varchar(45) not null,
    last_name      varchar(45) not null,
    phone_number   varchar(45) not null unique,
    created_at     timestamp   not null,
    last_active_at timestamp   not null,
    status         varchar(45) not null,
    primary key (id)

);

create table if not exists restaurants
(
    id          bigserial      not null,
    name        varchar(45) not null unique,
    description varchar(255),
    primary key (id)
);

create table if not exists employees
(
    id            bigserial      not null,
    name          varchar(45) not null,
    position      varchar(45) not null,
    restaurant_id bigserial      not null,
    primary key (id),
    constraint FK_employees_restaurants foreign key (restaurant_id) references restaurants (id) on delete cascade
);

create table if not exists restaurants_employees
(
    restaurant_id bigserial not null,
    employee_id   bigserial not null,
    constraint FK_restaurants_employees_restaurants foreign key (restaurant_id) references restaurants (id) on delete cascade,
    constraint FK_restaurants_employees_employees foreign key (employee_id) references employees (id) on delete cascade
);

create table if not exists addresses
(
    id           bigserial      not null,
    street_name  varchar(45) not null,
    house_number int         not null,
    floor_number int,
    flat_number  int,
    primary key (id)
);

create table if not exists restaurants_addresses
(
    restaurant_id bigserial not null,
    address_id    bigserial not null,
    constraint FK_restaurants_addresses_restaurants foreign key (restaurant_id) references restaurants (id) on delete cascade,
    constraint FK_restaurants_addresses_addresses foreign key (address_id) references addresses (id) on delete cascade
);

create table if not exists items
(
    id            bigserial           not null,
    name          varchar(45)      not null,
    description   varchar(255),
    price         double precision not null,
    type          varchar(45)      not null,
    available     boolean          not null,
    restaurant_id bigserial           not null,
    primary key (id),
    constraint FK_items_restaurants foreign key (restaurant_id) references restaurants (id) on delete cascade
);

create table if not exists restaurants_items
(
    restaurant_id bigserial not null,
    item_id       bigserial not null,
    constraint FK_restaurants_items_items foreign key (item_id) references items (id) on delete cascade,
    constraint FK_restaurants_items_restaurants foreign key (restaurant_id) references restaurants (id) on delete cascade
);

create table if not exists carts
(
    id      bigserial not null,
    primary key (id)
);

create table if not exists carts_items
(
    cart_id bigserial not null,
    item_id bigserial not null,
    constraint FK_carts_items_carts foreign key (cart_id) references carts (id) on delete cascade,
    constraint FK_carts_items_items foreign key (item_id) references items (id) on delete cascade
);

create table if not exists users
(
    id           bigserial      not null,
    name         varchar(45) not null,
    email        varchar(45) not null unique,
    password     varchar(65) not null,
    phone_number varchar(45) not null unique,
    cart_id      bigserial      not null unique,
    created_at   timestamp   not null,
    primary key (id),
    constraint FK_users_carts foreign key (cart_id) references carts (id)
);

create table if not exists users_addresses
(
    user_id    bigserial not null,
    address_id bigserial not null,
    constraint FK_users_addresses_addresses foreign key (address_id) references addresses (id) on delete cascade,
    constraint FK_users_addresses_users foreign key (user_id) references users (id) on delete cascade
);

create table if not exists orders
(
    id            bigserial   not null,
    user_id       bigserial   not null,
    address_id    bigserial   not null,
    restaurant_id bigserial   not null,
    cart_id       bigserial   not null,
    status        varchar(45) not null,
    courier_id    bigserial,
    created_at    timestamp   not null,
    delivered_at  timestamp,
    primary key (id),
    constraint FK_orders_users foreign key (user_id) references users (id) on delete cascade,
    constraint FK_orders_addresses foreign key (address_id) references addresses (id) on delete cascade,
    constraint FK_orders_restaurants foreign key (restaurant_id) references restaurants (id) on delete cascade,
    constraint FK_orders_carts foreign key (cart_id) references carts (id) on delete cascade,
    constraint FK_orders_couriers foreign key (courier_id) references couriers (id)
);