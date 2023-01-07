create schema if not exists delivery_project;

create table if not exists couriers
(
    id             bigserial,
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
    id          bigserial,
    name        varchar(45) not null unique,
    description varchar(255) null,
    primary key (id)
);

create table if not exists employees
(
    id            bigserial,
    name          varchar(45) not null,
    position      varchar(45) not null,
    primary key (id)
);

create table if not exists restaurants_employees
(
    restaurant_id bigint not null,
    employee_id   bigint not null,
    constraint fk_restaurants_employees_restaurants foreign key (restaurant_id) references restaurants (id) on delete cascade,
    constraint fk_restaurants_employees_employees foreign key (employee_id) references employees (id) on delete cascade
);

create table if not exists addresses
(
    id           bigserial,
    street_name  varchar(45) not null,
    house_number int         not null,
    floor_number int null ,
    flat_number  int null,
    primary key (id)
);

create table if not exists restaurants_addresses
(
    restaurant_id bigint not null,
    address_id    bigint not null,
    constraint fk_restaurants_addresses_restaurants foreign key (restaurant_id) references restaurants (id) on delete cascade,
    constraint fk_restaurants_addresses_addresses foreign key (address_id) references addresses (id) on delete cascade
);

create table if not exists items
(
    id            bigserial,
    name          varchar(45)      not null,
    description   varchar(255) null,
    price         numeric(8, 2) not null,
    type          varchar(45)      not null,
    available     boolean          not null default true,
    primary key (id)
);

create table if not exists restaurants_items
(
    restaurant_id bigint not null,
    item_id       bigint not null,
    constraint fk_restaurants_items_items foreign key (item_id) references items (id) on delete cascade,
    constraint fk_restaurants_items_restaurants foreign key (restaurant_id) references restaurants (id) on delete cascade
);

create table if not exists carts
(
    id      bigserial,
    primary key (id)
);

create table if not exists carts_items
(
    cart_id bigint not null,
    item_id bigint not null,
    quantity bigint not null,
    constraint fk_carts_items_carts foreign key (cart_id) references carts (id) on delete cascade,
    constraint fk_carts_items_items foreign key (item_id) references items (id) on delete cascade
);

create table if not exists users
(
    id           bigserial,
    cart_id      bigint      not null unique,
    name         varchar(45) not null,
    email        varchar(45) not null unique,
    password     varchar(65) not null,
    phone_number varchar(45) not null unique,
    created_at   timestamp   not null,
    primary key (id),
    constraint fk_users_carts foreign key (cart_id) references carts (id)
);

create table if not exists users_addresses
(
    user_id    bigint not null,
    address_id bigint not null,
    constraint fk_users_addresses_addresses foreign key (address_id) references addresses (id) on delete cascade,
    constraint fk_users_addresses_users foreign key (user_id) references users (id) on delete cascade
);

create table if not exists orders
(
    id            bigserial,
    address_id    bigint   not null,
    restaurant_id bigint   not null,
    cart_id       bigint   not null,
    courier_id    bigint null,
    status        varchar(45) not null,
    created_at    timestamp   not null,
    delivered_at  timestamp null,
    primary key (id),
    constraint fk_orders_addresses foreign key (address_id) references addresses (id) on delete cascade,
    constraint fk_orders_restaurants foreign key (restaurant_id) references restaurants (id) on delete cascade,
    constraint fk_orders_carts foreign key (cart_id) references carts (id) on delete cascade,
    constraint fk_orders_couriers foreign key (courier_id) references couriers (id)
);

create table if not exists users_orders
(
    user_id  bigint not null,
    order_id bigint not null,
    constraint fk_users_orders_users foreign key (user_id) references users (id) on delete cascade,
    constraint fk_users_orders_orders foreign key (order_id) references orders (id) on delete cascade
);