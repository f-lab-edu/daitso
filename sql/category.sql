CREATE TABLE category (
    category_id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) UNIQUE,
    parent_id BIGINT,
    PRIMARY KEY (category_id),
    FOREIGN KEY (parent_id) REFERENCES category (category_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;