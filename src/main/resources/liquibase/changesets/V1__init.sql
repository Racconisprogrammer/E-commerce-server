--liquibase formatted sql
--changeset admin:sample1_0
DROP TABLE if exists category;
DROP TABLE if exists DATABASECHANGELOG;
DROP TABLE if exists DATABASECHANGELOGLOCK;
DROP TABLE if exists address;
DROP TABLE if exists cart;
DROP TABLE if exists cart_item;
DROP TABLE if exists images;
DROP TABLE if exists order_item;
DROP TABLE if exists orders;
DROP TABLE if exists product;
DROP TABLE if exists rating;
DROP TABLE if exists review;
DROP TABLE if exists user_payment_information;
DROP TABLE if exists user;

--changeset admin:sample1_1
CREATE TABLE IF NOT EXISTS category
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    level              INT         NOT NULL,
    name               VARCHAR(50) NOT NULL,
    parent_category_id BIGINT      NULL
);


--changeset admin:sample1_2
CREATE TABLE IF NOT EXISTS product
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand            VARCHAR(255)  NULL,
    color            VARCHAR(255)  NULL,
    created_at       DATETIME  NULL,
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

--changeset admin:sample1_3
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

--changeset admin:sample1_4
CREATE TABLE IF NOT EXISTS user
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at DATETIME  NULL,
    email      VARCHAR(255) NULL,
    first_name VARCHAR(255) NULL,
    last_name  VARCHAR(255) NULL,
    mobile     VARCHAR(255) NULL,
    password   VARCHAR(255) NULL,
    role       VARCHAR(255) NULL
);

--changeset admin:sample1_5
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

--changeset admin:sample1_6
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

--changeset admin:sample1_7
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


--changeset admin:sample1_8
CREATE TABLE IF NOT EXISTS orders
(
    id                                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    create_at                          DATETIME  NULL,
    delivery_date                      DATETIME  NULL,
    discounted                         INT          NULL,
    order_date                         DATETIME  NULL,
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

--changeset admin:sample1_9
CREATE TABLE IF NOT EXISTS order_item
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    delivery_date    DATETIME NULL,
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

--changeset admin:sample1_10
CREATE TABLE IF NOT EXISTS rating
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at DATETIME NULL,
    rating     BIGINT      NULL,
    product_id BIGINT      NOT NULL,
    user_id    BIGINT      NOT NULL,
    CONSTRAINT fk_rating_product_id FOREIGN KEY (product_id) REFERENCES product (id),
    CONSTRAINT fk_rating_user_id FOREIGN KEY (user_id) REFERENCES user (id)
);

--changeset admin:sample1_11
CREATE TABLE IF NOT EXISTS review
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    create_at  DATETIME  NULL,
    review     VARCHAR(255) NULL,
    product_id BIGINT       NULL,
    user_id    BIGINT       NULL,
    CONSTRAINT fk_review_user_id FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT fk_review_product_id FOREIGN KEY (product_id) REFERENCES product (id)
);

--changeset admin:sample1_12
CREATE TABLE IF NOT EXISTS user_payment_information
(
    user_id         BIGINT       NOT NULL,
    card_number     VARCHAR(255) NULL,
    cardholder_name VARCHAR(255) NULL,
    cvv             VARCHAR(255) NULL,
    expiration_date DATETIME     NULL,
    CONSTRAINT fk_user_payment_information_user_id FOREIGN KEY (user_id) REFERENCES user (id)
);