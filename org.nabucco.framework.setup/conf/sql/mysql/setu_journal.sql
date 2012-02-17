DROP TABLE IF EXISTS `setu_journal`;

CREATE TABLE `setu_journal` (
  `id` 			bigint(20) 		NOT NULL AUTO_INCREMENT,
  `version` 	bigint(20) 		NOT NULL,
  `owner` 		varchar(12) 	NOT NULL,
  `user` 		varchar(32) 	NOT NULL, 
  `timestamp` 	datetime 		NOT NULL,
  `class_name` 	varchar(1000) 	NOT NULL,
  `identifier` 	bigint(20) 		NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=latin1;


CREATE INDEX `setu_journal_ix01` ON `setu_journal` (`owner`, `user`, `timestamp`);