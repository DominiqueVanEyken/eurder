-- CREATE SEQUENCES
create sequence item_seq start with 1001 increment by 1;
create sequence order_seq start with 101 increment by 1;
create sequence item_group_seq start with 1 increment by 1;

-- CREATE TABLE
create table postal_code
(
    postal_code varchar(4) primary key,
    city_name   varchar(32)
);

create table customer
(
    customer_id   varchar(36) primary key,
    first_name    varchar(32),
    last_name     varchar(32) not null,
    email         varchar(64) not null,
    street_name   varchar(32) not null,
    street_number varchar(4)  not null,
    postal_code   varchar(4) references postal_code,
    phone_number  varchar(16) not null,
    password      varchar(32) not null,
    role          varchar(32) not null
);

create table "order"
(
    order_id    integer primary key,
    customer_id varchar(36) references customer,
    order_date  date             not null,
    total_price double precision not null
);

create table item
(
    item_id     integer primary key,
    name        varchar(32)      not null,
    description varchar(32)      not null,
    price       double precision not null,
    stock_count integer          not null
);

create table item_group
(
    item_group_id  integer primary key,
    order_id       integer references "order",
    item_id        integer references item,
    item_name      varchar(32)      not null,
    amount         integer          not null,
    shipping_date  date             not null,
    price_per_unit double precision not null,
    total_price    double precision not null
);

-- CREATE ZIPCODE
insert into postal_code
values ('0000', 'capitalistic city');

insert into postal_code
values ('1111', 'consumer valley');

-- CREATE ADMIN USER
insert into customer
values ('0', null, 'admin', 'admin@eurder.com', 'some street', '1', '0000', '012 34 56 78', 'admin@eurder',
        'ADMIN');