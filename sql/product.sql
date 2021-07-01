create table product(
   product_id BIGINT NOT NULL AUTO_INCREMENT,
   name VARCHAR(30) NOT NULL,
   price VARCHAR(40) NOT NULL,
   content text NOT NULL,
   created_at datetime,
   updated_at datetime,
   quantity int default 1,
   deleted char(1) default 'N',
   PRIMARY KEY (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
