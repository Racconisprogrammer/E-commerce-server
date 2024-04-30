ALTER TABLE category ADD FOREIGN KEY (parent_category_id) REFERENCES category (id);
