-- liquibase formatted sql

-- changeset solvd:1674032559630-1
CREATE TABLE "items" ("id" BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, "name" VARCHAR(45) NOT NULL, "description" VARCHAR(255), "price" numeric(8, 2) NOT NULL, "type" VARCHAR(45) NOT NULL, "available" BOOLEAN DEFAULT TRUE NOT NULL, CONSTRAINT "items_pkey" PRIMARY KEY ("id"));

-- changeset solvd:1674032559630-2
CREATE TABLE "couriers" ("id" BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, "first_name" VARCHAR(45) NOT NULL, "last_name" VARCHAR(45) NOT NULL, "phone_number" VARCHAR(45) NOT NULL, "created_at" TIMESTAMP WITHOUT TIME ZONE NOT NULL, "last_active_at" TIMESTAMP WITHOUT TIME ZONE NOT NULL, "status" VARCHAR(45) NOT NULL, CONSTRAINT "couriers_pkey" PRIMARY KEY ("id"));

-- changeset solvd:1674032559630-3
CREATE TABLE "restaurants" ("id" BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, "name" VARCHAR(45) NOT NULL, "description" VARCHAR(255), CONSTRAINT "restaurants_pkey" PRIMARY KEY ("id"));

-- changeset solvd:1674032559630-4
CREATE TABLE "users" ("id" BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, "cart_id" BIGINT NOT NULL, "name" VARCHAR(45) NOT NULL, "email" VARCHAR(45) NOT NULL, "password" VARCHAR(65) NOT NULL, "phone_number" VARCHAR(45) NOT NULL, "created_at" TIMESTAMP WITHOUT TIME ZONE NOT NULL, CONSTRAINT "users_pkey" PRIMARY KEY ("id"));

-- changeset solvd:1674032559630-5
ALTER TABLE "couriers" ADD CONSTRAINT "couriers_phone_number_key" UNIQUE ("phone_number");

-- changeset solvd:1674032559630-6
ALTER TABLE "restaurants" ADD CONSTRAINT "restaurants_name_key" UNIQUE ("name");

-- changeset solvd:1674032559630-7
ALTER TABLE "users" ADD CONSTRAINT "users_cart_id_key" UNIQUE ("cart_id");

-- changeset solvd:1674032559630-8
ALTER TABLE "users" ADD CONSTRAINT "users_email_key" UNIQUE ("email");

-- changeset solvd:1674032559630-9
ALTER TABLE "users" ADD CONSTRAINT "users_phone_number_key" UNIQUE ("phone_number");

-- changeset solvd:1674032559630-10
CREATE TABLE "addresses" ("id" BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, "street_name" VARCHAR(45) NOT NULL, "house_number" INTEGER NOT NULL, "floor_number" INTEGER, "flat_number" INTEGER, CONSTRAINT "addresses_pkey" PRIMARY KEY ("id"));

-- changeset solvd:1674032559630-11
CREATE TABLE "carts" ("id" BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, CONSTRAINT "carts_pkey" PRIMARY KEY ("id"));

-- changeset solvd:1674032559630-12
CREATE TABLE "carts_items" ("cart_id" BIGINT NOT NULL, "item_id" BIGINT NOT NULL, "quantity" BIGINT NOT NULL, CONSTRAINT "carts_items_pkey" PRIMARY KEY ("cart_id", "item_id"));

-- changeset solvd:1674032559630-13
CREATE TABLE "employees" ("id" BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, "name" VARCHAR(45) NOT NULL, "position" VARCHAR(45) NOT NULL, CONSTRAINT "employees_pkey" PRIMARY KEY ("id"));

-- changeset solvd:1674032559630-14
CREATE TABLE "orders" ("id" BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, "address_id" BIGINT NOT NULL, "cart_id" BIGINT NOT NULL, "courier_id" BIGINT, "status" VARCHAR(45) NOT NULL, "created_at" TIMESTAMP WITHOUT TIME ZONE NOT NULL, "delivered_at" TIMESTAMP WITHOUT TIME ZONE, CONSTRAINT "orders_pkey" PRIMARY KEY ("id"));

-- changeset solvd:1674032559630-15
CREATE TABLE "restaurants_addresses" ("restaurant_id" BIGINT NOT NULL, "address_id" BIGINT NOT NULL);

-- changeset solvd:1674032559630-16
CREATE TABLE "restaurants_employees" ("restaurant_id" BIGINT NOT NULL, "employee_id" BIGINT NOT NULL);

-- changeset solvd:1674032559630-17
CREATE TABLE "restaurants_items" ("restaurant_id" BIGINT NOT NULL, "item_id" BIGINT NOT NULL);

-- changeset solvd:1674032559630-18
CREATE TABLE "users_addresses" ("user_id" BIGINT NOT NULL, "address_id" BIGINT NOT NULL);

-- changeset solvd:1674032559630-19
CREATE TABLE "users_orders" ("user_id" BIGINT NOT NULL, "order_id" BIGINT NOT NULL);

-- changeset solvd:1674032559630-20
ALTER TABLE "carts_items" ADD CONSTRAINT "fk_carts_items_carts" FOREIGN KEY ("cart_id") REFERENCES "carts" ("id") ON UPDATE NO ACTION ON DELETE CASCADE;

-- changeset solvd:1674032559630-21
ALTER TABLE "carts_items" ADD CONSTRAINT "fk_carts_items_items" FOREIGN KEY ("item_id") REFERENCES "items" ("id") ON UPDATE NO ACTION ON DELETE CASCADE;

-- changeset solvd:1674032559630-22
ALTER TABLE "orders" ADD CONSTRAINT "fk_orders_addresses" FOREIGN KEY ("address_id") REFERENCES "addresses" ("id") ON UPDATE NO ACTION ON DELETE CASCADE;

-- changeset solvd:1674032559630-23
ALTER TABLE "orders" ADD CONSTRAINT "fk_orders_carts" FOREIGN KEY ("cart_id") REFERENCES "carts" ("id") ON UPDATE NO ACTION ON DELETE CASCADE;

-- changeset solvd:1674032559630-24
ALTER TABLE "orders" ADD CONSTRAINT "fk_orders_couriers" FOREIGN KEY ("courier_id") REFERENCES "couriers" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset solvd:1674032559630-25
ALTER TABLE "restaurants_addresses" ADD CONSTRAINT "fk_restaurants_addresses_addresses" FOREIGN KEY ("address_id") REFERENCES "addresses" ("id") ON UPDATE NO ACTION ON DELETE CASCADE;

-- changeset solvd:1674032559630-26
ALTER TABLE "restaurants_addresses" ADD CONSTRAINT "fk_restaurants_addresses_restaurants" FOREIGN KEY ("restaurant_id") REFERENCES "restaurants" ("id") ON UPDATE NO ACTION ON DELETE CASCADE;

-- changeset solvd:1674032559630-27
ALTER TABLE "restaurants_employees" ADD CONSTRAINT "fk_restaurants_employees_employees" FOREIGN KEY ("employee_id") REFERENCES "employees" ("id") ON UPDATE NO ACTION ON DELETE CASCADE;

-- changeset solvd:1674032559630-28
ALTER TABLE "restaurants_employees" ADD CONSTRAINT "fk_restaurants_employees_restaurants" FOREIGN KEY ("restaurant_id") REFERENCES "restaurants" ("id") ON UPDATE NO ACTION ON DELETE CASCADE;

-- changeset solvd:1674032559630-29
ALTER TABLE "restaurants_items" ADD CONSTRAINT "fk_restaurants_items_items" FOREIGN KEY ("item_id") REFERENCES "items" ("id") ON UPDATE NO ACTION ON DELETE CASCADE;

-- changeset solvd:1674032559630-30
ALTER TABLE "restaurants_items" ADD CONSTRAINT "fk_restaurants_items_restaurants" FOREIGN KEY ("restaurant_id") REFERENCES "restaurants" ("id") ON UPDATE NO ACTION ON DELETE CASCADE;

-- changeset solvd:1674032559630-31
ALTER TABLE "users_addresses" ADD CONSTRAINT "fk_users_addresses_addresses" FOREIGN KEY ("address_id") REFERENCES "addresses" ("id") ON UPDATE NO ACTION ON DELETE CASCADE;

-- changeset solvd:1674032559630-32
ALTER TABLE "users_addresses" ADD CONSTRAINT "fk_users_addresses_users" FOREIGN KEY ("user_id") REFERENCES "users" ("id") ON UPDATE NO ACTION ON DELETE CASCADE;

-- changeset solvd:1674032559630-33
ALTER TABLE "users" ADD CONSTRAINT "fk_users_carts" FOREIGN KEY ("cart_id") REFERENCES "carts" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset solvd:1674032559630-34
ALTER TABLE "users_orders" ADD CONSTRAINT "fk_users_orders_orders" FOREIGN KEY ("order_id") REFERENCES "orders" ("id") ON UPDATE NO ACTION ON DELETE CASCADE;

-- changeset solvd:1674032559630-35
ALTER TABLE "users_orders" ADD CONSTRAINT "fk_users_orders_users" FOREIGN KEY ("user_id") REFERENCES "users" ("id") ON UPDATE NO ACTION ON DELETE CASCADE;
