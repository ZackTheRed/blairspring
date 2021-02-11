CREATE TABLE `jobs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dscr` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `dscr_UNIQUE` (`dscr`)
);

INSERT INTO `jobs` (`id`,`dscr`) VALUES (2,'Hairdresser');
INSERT INTO `jobs` (`id`,`dscr`) VALUES (1,'Java Developer');
INSERT INTO `jobs` (`id`,`dscr`) VALUES (4,'Lorry driver');
INSERT INTO `jobs` (`id`,`dscr`) VALUES (8,'Policeman');
INSERT INTO `jobs` (`id`,`dscr`) VALUES (6,'Sales Executive');
INSERT INTO `jobs` (`id`,`dscr`) VALUES (9,'Singer');
INSERT INTO `jobs` (`id`,`dscr`) VALUES (3,'Taxi driver');
INSERT INTO `jobs` (`id`,`dscr`) VALUES (5,'Trainer');
INSERT INTO `jobs` (`id`,`dscr`) VALUES (7,'Unemployed');

CREATE TABLE `employees` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(20) DEFAULT NULL,
  `last_name` varchar(20) DEFAULT NULL,
  `salary` int(11) DEFAULT NULL,
  `job_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_employees_jobs` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

INSERT INTO `employees` (`id`,`first_name`,`last_name`,`salary`,`job_id`) VALUES (3,'Ioannis','Lilimpakis',2000,3);
INSERT INTO `employees` (`id`,`first_name`,`last_name`,`salary`,`job_id`) VALUES (4,'Kostas','Katsougris',3000,5);
INSERT INTO `employees` (`id`,`first_name`,`last_name`,`salary`,`job_id`) VALUES (5,'Thodoris','Temourtzidis',1000,7);
INSERT INTO `employees` (`id`,`first_name`,`last_name`,`salary`,`job_id`) VALUES (6,'Filippos','Sofianos',4500,7);
INSERT INTO `employees` (`id`,`first_name`,`last_name`,`salary`,`job_id`) VALUES (7,'Kiucuk','Kainartzi',1000,7);
INSERT INTO `employees` (`id`,`first_name`,`last_name`,`salary`,`job_id`) VALUES (8,'Svetlana','Plakomounova',5500,1);
INSERT INTO `employees` (`id`,`first_name`,`last_name`,`salary`,`job_id`) VALUES (9,'Achileas','Kyriakidis',10000,4);
INSERT INTO `employees` (`id`,`first_name`,`last_name`,`salary`,`job_id`) VALUES (10,'Noobas','Randomas',500,6);