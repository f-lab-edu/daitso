create table product(
   pid INT NOT NULL AUTO_INCREMENT,
   categoryId INT NOT NULL,
   name VARCHAR(30) NOT NULL,
   price VARCHAR(40) NOT NULL,
   content text NOT NULL,
   createdAt datetime,
   updatedAt datetime,
   quantity int default 1,
   deleted char(1) default 'N',
   PRIMARY KEY (pid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
