CREATE TABLE product_category (
    product_category_id BIGINT NOT NULL AUTO_INCREMENT,
    category_id BIGINT,
    product_id BIGINT,
    PRIMARY KEY (product_category_id),
    FOREIGN KEY (category_id) REFERENCES category (category_id),
    FOREIGN KEY (product_id) REFERENCES product (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;