drop table has_authority;

drop table authority;

drop table reviews;

drop table orders;

drop table customers;

drop table products;

drop table managers;


create table authority
(
    id       bigint       not null auto_increment,
    role_type varchar(255) not null unique,
    primary key (id)
);

create table customers
(
    id                bigint       not null auto_increment,
    name              varchar(50)  not null,
    email             varchar(255) not null unique,
    phone_number      varchar(255) not null,
    status            varchar(255) not null,
    registration_date datetime(6),
    updated_date      datetime(6),
    is_deleted        bit,
    primary key (id)
);

create table managers
(
    id                bigint       not null auto_increment,
    name              varchar(50)  not null,
    email             varchar(255) not null unique,
    phone_number      varchar(255) not null,
    status            varchar(255) not null,
    role              varchar(255) not null,
    registration_date datetime(6),
    updated_date      datetime(6),
    is_deleted        bit,
    password          varchar(255) not null,
    status_reason     varchar(255),
    primary key (id)
);

create table has_authority
(
    id           bigint not null auto_increment,
    manager_id   bigint not null,
    authority_id bigint not null,
    primary key (id),
    unique key uk_manager_authority (manager_id, authority_id),
    constraint fk_has_authority_manager foreign key (manager_id) references managers (id),
    constraint fk_has_authority_authority foreign key (authority_id) references authority (id)
);

create table products
(
    id         bigint       not null auto_increment,
    name       varchar(255) not null,
    category   varchar(255),
    price      integer      not null,
    quantity   integer      not null,
    status     varchar(255) not null,
    created_at datetime(6),
    updated_at datetime(6),
    manager_id bigint       not null,
    primary key (id),
    constraint fk_product_manager foreign key (manager_id) references managers (id)
);

create table orders
(
    id              bigint       not null auto_increment,
    quantity        integer      not null,
    total_price     integer      not null,
    delivery_status varchar(255) not null,
    order_date      datetime(6),
    customer_id     bigint       not null,
    manager_id      bigint,
    product_id      bigint       not null,
    primary key (id),
    constraint fk_order_customer foreign key (customer_id) references customers (id),
    constraint fk_order_manager foreign key (manager_id) references managers (id),
    constraint fk_order_product foreign key (product_id) references products (id)
);

create table reviews
(
    id          bigint       not null auto_increment,
    rating      integer      not null,
    content     varchar(100) not null,
    created_at  datetime(6),
    order_id    bigint       not null,
    customer_id bigint       not null,
    product_id  bigint       not null,
    primary key (id),
    constraint fk_review_order foreign key (order_id) references orders (id),
    constraint fk_review_customer foreign key (customer_id) references customers (id),
    constraint fk_review_product foreign key (product_id) references products (id)
);

INSERT INTO authority (role_type)
VALUES ('SUPER');
INSERT INTO authority (role_type)
VALUES ('OPS');
INSERT INTO authority (role_type)
VALUES ('CS');

-- 1. 어드민 계정 생성 (비밀번호는 반드시 암호화해서 넣으세요)
INSERT INTO managers (name, email, phone_number, status, role, registration_date, updated_date, is_deleted, password,
                      status_reason)
VALUES ('admin',
        'admin@example.com',
        '010-0000-0000',
        'ACTIVE',
        'SUPER',
        NOW(),
        NOW(),
        0,
        '$2a$12$tLmc76j3drS.YFeUyVmAQO8l1QtS7Tt2zl1JSBE.zCQvYJP4Z6B5i',
        NULL);

-- 2. 권한 ID 조회 (SUPER, OPS, CS)
SELECT id, role_type
FROM authority
WHERE role_type IN ('SUPER', 'OPS', 'CS');

-- 3. 어드민 계정의 id를 확인 (예: 1번이라고 가정)
SELECT id
FROM managers
WHERE email = 'admin@example.com';

-- 4. has_authority에 권한 부여 (아래에서 1은 어드민 id, 1/2/3은 authority id 예시)
INSERT INTO has_authority (manager_id, authority_id)
VALUES (1, 1);
INSERT INTO has_authority (manager_id, authority_id)
VALUES (1, 2);
INSERT INTO has_authority (manager_id, authority_id)
VALUES (1, 3);
