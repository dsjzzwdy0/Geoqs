create table face_info(
	`id`  int(11) NOT NULL AUTO_INCREMENT,
	userid varchar(20),
	name varchar(30),
	format varchar(5),
	facebytes blob,
	PRIMARY KEY (`id`)
);