drop table has_authority;

drop table authority;

drop table reviews;

drop table orders;

drop table customers;

drop table products;

drop table managers;


create table authority (
                           id bigint not null auto_increment,
                           role varchar(255) not null unique,
                           primary key (id)
);

create table customers (
                           id bigint not null auto_increment,
                           name varchar(50) not null,
                           email varchar(255) not null unique,
                           phone_number varchar(255) not null,
                           status varchar(255) not null,
                           registration_date datetime(6),
                           updated_date datetime(6),
                           is_deleted bit,
                           primary key (id)
);

create table managers (
                          id bigint not null auto_increment,
                          name varchar(50) not null,
                          email varchar(255) not null unique,
                          phone_number varchar(255) not null,
                          status varchar(255) not null,
                          registration_date datetime(6),
                          updated_date datetime(6),
                          is_deleted bit,
                          password varchar(255) not null,
                          status_reason varchar(255),
                          primary key (id)
);

create table has_authority (
                               id bigint not null auto_increment,
                               manager_id bigint not null,
                               authority_id bigint not null,
                               primary key (id),
                               unique key uk_manager_authority (manager_id, authority_id),
                               constraint fk_has_authority_manager foreign key (manager_id) references managers(id),
                               constraint fk_has_authority_authority foreign key (authority_id) references authority(id)
);

create table products (
                          id bigint not null auto_increment,
                          name varchar(255) not null,
                          category varchar(255),
                          price integer not null,
                          quantity integer not null,
                          status varchar(255) not null,
                          created_at datetime(6),
                          updated_at datetime(6),
                          manager_id bigint not null,
                          primary key (id),
                          constraint fk_product_manager foreign key (manager_id) references managers(id)
);

create table orders (
                        id bigint not null auto_increment,
                        quantity integer not null,
                        total_price integer not null,
                        delivery_status varchar(255) not null,
                        order_date datetime(6),
                        customer_id bigint not null,
                        manager_id bigint,
                        product_id bigint not null,
                        primary key (id),
                        constraint fk_order_customer foreign key (customer_id) references customers(id),
                        constraint fk_order_manager foreign key (manager_id) references managers(id),
                        constraint fk_order_product foreign key (product_id) references products(id)
);

create table reviews (
                         id bigint not null auto_increment,
                         rating integer not null,
                         content varchar(100) not null,
                         created_at datetime(6),
                         order_id bigint not null,
                         customer_id bigint not null,
                         product_id bigint not null,
                         primary key (id),
                         constraint fk_review_order foreign key (order_id) references orders(id),
                         constraint fk_review_customer foreign key (customer_id) references customers(id),
                         constraint fk_review_product foreign key (product_id) references products(id)
);

INSERT INTO authority (role) VALUES ('SUPER');
INSERT INTO authority (role) VALUES ('OPS');
INSERT INTO authority (role) VALUES ('CS');

