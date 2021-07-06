CREATE TABLE `paymentoption` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `paymentoption` int NOT NULL,
  `userEmail` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userEmail` (`userEmail`),
  FOREIGN KEY (`userEmail`) REFERENCES `user` (`userEmail`)
  ON DELETE CASCADE
  ON UPDATE CASCADE
)