--liquibase formatted sql
--changeset admin:samp_1
ALTER TABLE category ADD FOREIGN KEY (parent_category_id) REFERENCES category (id);

