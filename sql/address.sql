CREATE TABLE `address` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `address` varchar(100) NOT NULL,
  `userEmail` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userEmail` (`userEmail`),
  CONSTRAINT `address_ibfk_1` FOREIGN KEY (`userEmail`) REFERENCES `user` (`userEmail`)
  ON DELETE CASCADE
  ON UPDATE CASCADE
)