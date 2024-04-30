DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS cart_item;
DROP TABLE IF EXISTS images;
DROP TABLE IF EXISTS order_item;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS product_highlights;
DROP TABLE IF EXISTS rating;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS user_payment_information;


CREATE TABLE IF NOT EXISTS category
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    level              INT         NOT NULL,
    name               VARCHAR(50) NOT NULL,
    parent_category_id BIGINT      NULL
);



CREATE TABLE IF NOT EXISTS product
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand            VARCHAR(255)  NULL,
    color            VARCHAR(255)  NULL,
    created_at       DATETIME(6)   NULL,
    description      VARCHAR(1500) NULL,
    details          VARCHAR(1500) NULL,
    discount_percent INT           NULL,
    discount_price   INT           NULL,
    price            INT           NOT NULL,
    quantity         INT           NULL,
    title            VARCHAR(255)  NULL,
    category_id      BIGINT        NULL,
    preview_image_id BIGINT        NULL,
    highlights       VARCHAR(1500) NULL,
    CONSTRAINT fk_product_category_id FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE CASCADE ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS images
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    bytes              LONGBLOB     NULL,
    content_type       VARCHAR(255) NULL,
    is_preview_image   BIT          NOT NULL,
    name               VARCHAR(255) NULL,
    original_file_name VARCHAR(255) NULL,
    size               BIGINT       NULL,
    product_id         BIGINT       NULL,
    CONSTRAINT fk_images_product_id FOREIGN KEY (product_id) REFERENCES product (id)
);

CREATE TABLE IF NOT EXISTS product_highlights
(
    product_id BIGINT       NOT NULL,
    name       VARCHAR(255) NULL,
    CONSTRAINT fk_product_highlights_product_id FOREIGN KEY (product_id) REFERENCES product (id)
);


CREATE TABLE IF NOT EXISTS user
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at DATETIME(6)  NULL,
    email      VARCHAR(255) NULL,
    first_name VARCHAR(255) NULL,
    last_name  VARCHAR(255) NULL,
    mobile     VARCHAR(255) NULL,
    password   VARCHAR(255) NULL,
    role       VARCHAR(255) NULL
);

CREATE TABLE IF NOT EXISTS address
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    city           VARCHAR(255) NULL,
    first_name     VARCHAR(255) NULL,
    last_name      VARCHAR(255) NULL,
    mobile         VARCHAR(255) NULL,
    state          VARCHAR(255) NULL,
    street_address VARCHAR(255) NULL,
    zip_code       VARCHAR(255) NULL,
    user_id        BIGINT       NULL,
    CONSTRAINT fk_address_user_id FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE IF NOT EXISTS cart
(
    id                     BIGINT AUTO_INCREMENT PRIMARY KEY,
    discounted             INT    NOT NULL,
    total_discounted_price INT    NOT NULL,
    total_item             INT    NULL,
    total_price            DOUBLE NULL,
    user_id                BIGINT NOT NULL,
    CONSTRAINT unique_user_id UNIQUE (user_id),
    CONSTRAINT fk_cart_user_id FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE IF NOT EXISTS cart_item
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    discounted_price INT          NULL,
    price            INT          NULL,
    quantity         INT          NOT NULL,
    size             VARCHAR(255) NULL,
    user_id          BIGINT       NULL,
    cart_id          BIGINT       NULL,
    product_id       BIGINT       NULL,
    CONSTRAINT fk_cart_item_cart_id FOREIGN KEY (cart_id) REFERENCES cart (id),
    CONSTRAINT fk_cart_item_product_id FOREIGN KEY (product_id) REFERENCES product (id)
);

CREATE TABLE IF NOT EXISTS orders
(
    id                                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    create_at                          DATETIME(6)  NULL,
    delivery_date                      DATETIME(6)  NULL,
    discounted                         INT          NULL,
    order_date                         DATETIME(6)  NULL,
    order_id                           VARCHAR(255) NULL,
    order_status                       VARCHAR(255) NULL,
    payment_id                         VARCHAR(255) NULL,
    payment_method                     VARCHAR(255) NULL,
    razorpay_payment_id                VARCHAR(255) NULL,
    razorpay_payment_link_id           VARCHAR(255) NULL,
    razorpay_payment_link_reference_id VARCHAR(255) NULL,
    razorpay_payment_link_status       VARCHAR(255) NULL,
    status                             VARCHAR(255) NULL,
    total_discounted_price             INT          NULL,
    total_item                         INT          NOT NULL,
    total_price                        DOUBLE       NOT NULL,
    shipping_address_id                BIGINT       NULL,
    user_id                            BIGINT       NULL,
    CONSTRAINT unique_shipping_address_id UNIQUE (shipping_address_id),
    CONSTRAINT fk_orders_user_id FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT fk_orders_shipping_address_id FOREIGN KEY (shipping_address_id) REFERENCES address (id)
);

CREATE TABLE IF NOT EXISTS order_item
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    delivery_date    DATETIME(6)  NULL,
    discounted_price INT          NULL,
    price            INT          NULL,
    quantity         INT          NOT NULL,
    size             VARCHAR(255) NULL,
    user_id          BIGINT       NULL,
    order_id         BIGINT       NULL,
    product_id       BIGINT       NULL,
    CONSTRAINT fk_order_item_product_id FOREIGN KEY (product_id) REFERENCES product (id),
    CONSTRAINT fk_order_item_order_id FOREIGN KEY (order_id) REFERENCES orders (id)
);

CREATE TABLE IF NOT EXISTS rating
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at DATETIME(6) NULL,
    rating     BIGINT      NULL,
    product_id BIGINT      NOT NULL,
    user_id    BIGINT      NOT NULL,
    CONSTRAINT fk_rating_product_id FOREIGN KEY (product_id) REFERENCES product (id),
    CONSTRAINT fk_rating_user_id FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE IF NOT EXISTS review
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    create_at  DATETIME(6)  NULL,
    review     VARCHAR(255) NULL,
    product_id BIGINT       NULL,
    user_id    BIGINT       NULL,
    CONSTRAINT fk_review_user_id FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT fk_review_product_id FOREIGN KEY (product_id) REFERENCES product (id)
);

CREATE TABLE IF NOT EXISTS user_payment_information
(
    user_id         BIGINT       NOT NULL,
    card_number     VARCHAR(255) NULL,
    cardholder_name VARCHAR(255) NULL,
    cvv             VARCHAR(255) NULL,
    expiration_date DATETIME     NULL,
    CONSTRAINT fk_user_payment_information_user_id FOREIGN KEY (user_id) REFERENCES user (id)
);




# create table if not exists category
# (
#     id                 bigint auto_increment
#         primary key,
#     level              int         not null,
#     name               varchar(50) not null,
#     parent_category_id bigint      null,
#     constraint foreign key (parent_category_id) references category (id)
# );
#
# create table if not exists product
# (
#     id               bigint auto_increment
#         primary key,
#     brand            varchar(255)  null,
#     color            varchar(255)  null,
#     created_at       datetime(6)   null,
#     description      varchar(1500) null,
#     details          varchar(1500) null,
#     discount_percent int           null,
#     discount_price   int           null,
#     num_ratings      int           null,
#     price            int           not null,
#     quantity         int           null,
#     title            varchar(255)  null,
#     category_id      bigint        null,
#     preview_image_id bigint        null,
#     highlights       varchar(1500) null,
#     constraint foreign key (category_id) references category (id)
# );
#
# create table if not exists images
# (
#     id                 bigint auto_increment
#         primary key,
#     bytes              longblob     null,
#     content_type       varchar(255) null,
#     is_preview_image   bit          not null,
#     name               varchar(255) null,
#     original_file_name varchar(255) null,
#     size               bigint       null,
#     product_id         bigint       null,
#     constraint foreign key (product_id) references product (id)
# );
#
# create table if not exists product_highlights
# (
#     product_id bigint       not null,
#     name       varchar(255) null,
#     constraint foreign key (product_id) references product (id)
# );
#
# create table if not exists user
# (
#     id         bigint auto_increment
#         primary key,
#     created_at datetime(6)  null,
#     email      varchar(255) null,
#     first_name varchar(255) null,
#     last_name  varchar(255) null,
#     mobile     varchar(255) null,
#     password   varchar(255) null,
#     role       varchar(255) null
# );
#
# create table if not exists address
# (
#     id             bigint auto_increment
#         primary key,
#     city           varchar(255) null,
#     first_name     varchar(255) null,
#     last_name      varchar(255) null,
#     mobile         varchar(255) null,
#     state          varchar(255) null,
#     street_address varchar(255) null,
#     zip_code       varchar(255) null,
#     user_id        bigint       null,
#     constraint foreign key (user_id) references user (id)
# );
#
# create table if not exists cart
# (
#     id                     bigint auto_increment
#         primary key,
#     discounted             int    not null,
#     total_discounted_price int    not null,
#     total_item             int    null,
#     total_price            double null,
#     user_id                bigint not null,
#     constraint unique (user_id),
#     constraint foreign key (user_id) references user (id)
# );
#
# create table if not exists cart_item
# (
#     id               bigint auto_increment
#         primary key,
#     discounted_price int          null,
#     price            int          null,
#     quantity         int          not null,
#     size             varchar(255) null,
#     user_id          bigint       null,
#     cart_id          bigint       null,
#     product_id       bigint       null,
#     constraint foreign key (cart_id) references cart (id),
#     constraint foreign key (product_id) references product (id)
# );
#
# create table if not exists orders
# (
#     id                                 bigint auto_increment
#         primary key,
#     create_at                          datetime(6)  null,
#     delivery_date                      datetime(6)  null,
#     discounted                         int          null,
#     order_date                         datetime(6)  null,
#     order_id                           varchar(255) null,
#     order_status                       varchar(255) null,
#     payment_id                         varchar(255) null,
#     payment_method                     varchar(255) null,
#     razorpay_payment_id                varchar(255) null,
#     razorpay_payment_link_id           varchar(255) null,
#     razorpay_payment_link_reference_id varchar(255) null,
#     razorpay_payment_link_status       varchar(255) null,
#     status                             varchar(255) null,
#     total_discounted_price             int          null,
#     total_item                         int          not null,
#     total_price                        double       not null,
#     shipping_address_id                bigint       null,
#     user_id                            bigint       null,
#     constraint unique (shipping_address_id),
#     constraint foreign key (user_id) references user (id),
#     constraint foreign key (shipping_address_id) references address (id)
# );
#
# create table if not exists order_item
# (
#     id               bigint auto_increment
#         primary key,
#     delivery_date    datetime(6)  null,
#     discounted_price int          null,
#     price            int          null,
#     quantity         int          not null,
#     size             varchar(255) null,
#     user_id          bigint       null,
#     order_id         bigint       null,
#     product_id       bigint       null,
#     constraint foreign key (product_id) references product (id),
#     constraint foreign key (order_id) references orders (id)
# );
#
# create table if not exists rating
# (
#     id         bigint auto_increment
#         primary key,
#     created_at datetime(6) null,
#     rating     bigint      null,
#     product_id bigint      not null,
#     user_id    bigint      not null,
#     constraint foreign key (product_id) references product (id),
#     constraint foreign key (user_id) references user (id)
# );
#
# create table if not exists review
# (
#     id         bigint auto_increment
#         primary key,
#     create_at  datetime(6)  null,
#     review     varchar(255) null,
#     product_id bigint       null,
#     user_id    bigint       null,
#     constraint foreign key (user_id) references user (id),
#     constraint foreign key (product_id) references product (id)
# );
#
# create table if not exists user_payment_information
# (
#     user_id         bigint       not null,
#     card_number     varchar(255) null,
#     cardholder_name varchar(255) null,
#     cvv             varchar(255) null,
#     expiration_date date         null,
#     constraint foreign key (user_id) references user (id)
# );