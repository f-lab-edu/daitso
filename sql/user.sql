CREATE TABLE `user` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `userId` varchar(45) UNIQUE NOT NULL,
    `userPassword` varchar(100) NOT NULL,
    `name` varchar(15) NOT NULL,
    `phoneNumber` varchar(15) NOT NULL,
    `role` varchar(15) NOT NULL,
    `registrationDate`  varchar (15) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;