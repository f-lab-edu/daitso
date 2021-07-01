CREATE TABLE `user` (
    user_id BIGINT NOT NULL AUTO_INCREMENT,
    user_email varchar(45) UNIQUE NOT NULL,
    user_password varchar(100) NOT NULL,
    name varchar(15) NOT NULL,
    phone_number varchar(15) NOT NULL,
    role varchar(15) NOT NULL,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;