create table product(
    product_id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    price BIGINT NOT NULL,
    content text NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    quantity BIGINT default 1,
    score BIGINT default 0,
    main_image VARCHAR(255),
    detail_image VARCHAR(255),
    delivery_charge_type VARCHAR(100),
    deleted char(1) default 'N',
    PRIMARY KEY (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
