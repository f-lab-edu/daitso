create table product(
   product_id BIGINT NOT NULL AUTO_INCREMENT,
   name VARCHAR(100) NOT NULL UNIQUE,
   price BIGINT NOT NULL,
   content text NOT NULL,
   created_at datetime,
   updated_at datetime,
   quantity int default 1,
   deleted char(1) default 'N',
   PRIMARY KEY (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
