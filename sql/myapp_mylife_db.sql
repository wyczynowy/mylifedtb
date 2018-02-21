# Dumping database structure for myapp
DROP DATABASE IF EXISTS `my_life_db`;
CREATE DATABASE IF NOT EXISTS `my_life_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `my_life_db`;

# Dumping structure for table myapp.microprocessorAdvice
DROP TABLE IF EXISTS `microprocessor_advice`;
CREATE TABLE IF NOT EXISTS `microprocessor_advice` (
  `ADVICE_ID` int(10) NOT NULL AUTO_INCREMENT,
  `ADVICE_NAME` varchar(500) NOT NULL,
  `ADVICE_DESCRIPTION` text NOT NULL,
  PRIMARY KEY (`ADVICE_ID`)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci COMMENT='Contains information about microprocessor advices';

# Dumping structure for table my_life_dtb.microprocessor_advices_attachments
DROP TABLE IF EXISTS `microprocessor_advices_attachments`;
CREATE TABLE IF NOT EXISTS `microprocessor_advices_attachments` (
	`id` int(10) NOT NULL AUTO_INCREMENT,
	`file_name` varchar(500) NOT NULL,
	`microprocessor_advice_id` int(10) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci COMMENT='Contains information about microprocessor_advices_attachments';

# Creating foreign key for tab my_life_dtb.microprocessor_advices_attachments.microprocessor_advice_id -> my_life_dtb.microprocessor_advice.ADVICE_ID
alter table microprocessor_advices_attachments
add constraint microprocessor_advice_id_ADVICE_ID_fk foreign key(microprocessor_advice_id) references microprocessor_advice(ADVICE_ID);

# Dumping structure for table myapp.electronicsGeneralAdvice
DROP TABLE IF EXISTS `electronics_general_advice`;
CREATE TABLE IF NOT EXISTS `electronics_general_advice` (
  `ADVICE_ID` int(10) NOT NULL AUTO_INCREMENT,
  `ADVICE_NAME` varchar(500) NOT NULL,
  `ADVICE_DESCRIPTION` text NOT NULL,
  PRIMARY KEY (`ADVICE_ID`)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci COMMENT='Contains information about electronics_general_advices';

# Dumping structure for table my_life_dtb.electronics_general_advices_attachments
DROP TABLE IF EXISTS `electronics_general_advices_attachments`;
CREATE TABLE IF NOT EXISTS `electronics_general_advices_attachments` (
	`id` int(10) NOT NULL AUTO_INCREMENT,
	`file_name` varchar(500) NOT NULL,
	`electronics_general_advice_id` int(10) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci COMMENT='Contains information about electronics_general_advices_attachments';

# Creating foreign key for tab my_life_dtb.electronics_general_advices_attachments.electronics_general_advice_id -> my_life_dtb.electronics_general_advice.ADVICE_ID
alter table electronics_general_advices_attachments
add constraint electronics_general_advice_id_ADVICE_ID_fk foreign key(electronics_general_advice_id) references electronics_general_advice(ADVICE_ID);

# Dumping structure for table myapp.mySqlAdvice
DROP TABLE IF EXISTS `my_sql_advice`;
CREATE TABLE IF NOT EXISTS `my_sql_advice` (
  `ADVICE_ID` int(10) NOT NULL AUTO_INCREMENT,
  `ADVICE_NAME` varchar(500) NOT NULL,
  `ADVICE_DESCRIPTION` text NOT NULL,
  PRIMARY KEY (`ADVICE_ID`)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci COMMENT='Contains information about my_sql_advices';

# Dumping structure for table my_life_dtb.my_sql_advices_attachments
DROP TABLE IF EXISTS `my_sql_advices_attachments`;
CREATE TABLE IF NOT EXISTS `my_sql_advices_attachments` (
	`id` int(10) NOT NULL AUTO_INCREMENT,
	`file_name` varchar(500) NOT NULL,
	`my_sql_advice_id` int(10) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci COMMENT='Contains information about my_sql_advices_attachments';

# Creating foreign key for tab my_life_dtb.my_sql_advices_attachments.my_sql_advice_id -> my_life_dtb.my_sql_advice.ADVICE_ID
alter table my_sql_advices_attachments
add constraint my_sql_advice_id_ADVICE_ID_fk foreign key(my_sql_advice_id) references my_sql_advice(ADVICE_ID);

# Dumping structure for table myapp.eclipseAdvice
DROP TABLE IF EXISTS `eclipse_advice`;
CREATE TABLE IF NOT EXISTS `eclipse_advice` (
  `ADVICE_ID` int(10) NOT NULL AUTO_INCREMENT,
  `ADVICE_NAME` varchar(500) NOT NULL,
  `ADVICE_DESCRIPTION` text NOT NULL,
  PRIMARY KEY (`ADVICE_ID`)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci COMMENT='Contains information about eclipse_advices';

# Dumping structure for table my_life_dtb.eclipse_advices_attachments
DROP TABLE IF EXISTS `eclipse_advices_attachments`;
CREATE TABLE IF NOT EXISTS `eclipse_advices_attachments` (
	`id` int(10) NOT NULL AUTO_INCREMENT,
	`file_name` varchar(500) NOT NULL,
	`eclipse_advice_id` int(10) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci COMMENT='Contains information about eclipse_advices_attachments';

# Creating foreign key for tab my_life_dtb.eclipse_advices_attachments.eclipse_advice_id -> my_life_dtb.eclipse_advice.ADVICE_ID
alter table eclipse_advices_attachments
add constraint eclipse_advice_id_ADVICE_ID_fk foreign key(eclipse_advice_id) references eclipse_advice(ADVICE_ID);

# Dumping structure for table myapp.windowsAdvice
DROP TABLE IF EXISTS `windows_advice`;
CREATE TABLE IF NOT EXISTS `windows_advice` (
  `ADVICE_ID` int(10) NOT NULL AUTO_INCREMENT,
  `ADVICE_NAME` varchar(500) NOT NULL,
  `ADVICE_DESCRIPTION` text NOT NULL,
  PRIMARY KEY (`ADVICE_ID`)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci COMMENT='Contains information about windows_advices';

# Dumping structure for table my_life_dtb.windows_advices_attachments
DROP TABLE IF EXISTS `windows_advices_attachments`;
CREATE TABLE IF NOT EXISTS `windows_advices_attachments` (
	`id` int(10) NOT NULL AUTO_INCREMENT,
	`file_name` varchar(500) NOT NULL,
	`windows_advice_id` int(10) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci COMMENT='Contains information about windows_advices_attachments';

# Creating foreign key for tab my_life_dtb.windows_advices_attachments.windows_advice_id -> my_life_dtb.windows.windows_advice.ADVICE_ID
alter table windows_advices_attachments
add constraint windows_advice_id_ADVICE_ID_fk foreign key(windows_advice_id) references windows_advice(ADVICE_ID);

# Dumping structure for table myapp.linuxAdvice
DROP TABLE IF EXISTS `linux_advice`;
CREATE TABLE IF NOT EXISTS `linux_advice` (
  `ADVICE_ID` int(10) NOT NULL AUTO_INCREMENT,
  `ADVICE_NAME` varchar(500) NOT NULL,
  `ADVICE_DESCRIPTION` text NOT NULL,
  PRIMARY KEY (`ADVICE_ID`)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci COMMENT='Contains information about linux_advices';

# Dumping structure for table my_life_dtb.linux_advices_attachments
DROP TABLE IF EXISTS `linux_advices_attachments`;
CREATE TABLE IF NOT EXISTS `linux_advices_attachments` (
	`id` int(10) NOT NULL AUTO_INCREMENT,
	`file_name` varchar(500) NOT NULL,
	`linux_advice_id` int(10) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci COMMENT='Contains information about windows_advices_attachments';

# Creating foreign key for tab my_life_dtb.linux_advices_attachments.linux_advice_id -> my_life_dtb.linux_advice.ADVICE_ID
alter table linux_advices_attachments
add constraint linux_advice_id_ADVICE_ID_fk foreign key(linux_advice_id) references linux_advice(ADVICE_ID);

# Dumping structure for table myapp.javaAdvice
DROP TABLE IF EXISTS `java_advice`;
CREATE TABLE IF NOT EXISTS `java_advice` (
  `ADVICE_ID` int(10) NOT NULL AUTO_INCREMENT,
  `ADVICE_NAME` varchar(500) NOT NULL,
  `ADVICE_DESCRIPTION` text NOT NULL,
  PRIMARY KEY (`ADVICE_ID`)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci COMMENT='Contains information about java_advices';

# Dumping structure for table my_life_dtb.java_advices_attachments
DROP TABLE IF EXISTS `java_advices_attachments`;
CREATE TABLE IF NOT EXISTS `java_advices_attachments` (
	`id` int(10) NOT NULL AUTO_INCREMENT,
	`file_name` varchar(500) NOT NULL,
	`java_advice_id` int(10) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci COMMENT='Contains information about java_advices_attachments';

# Creating foreign key for tab my_life_dtb.java_advices_attachments.java_advice_id -> my_life_dtb.java_advice.ADVICE_ID
alter table java_advices_attachments
add constraint java_advice_id_ADVICE_ID_fk foreign key(java_advice_id) references java_advice(ADVICE_ID);

# Dumping structure for table myapp.cAdvice
DROP TABLE IF EXISTS `c_advice`;
CREATE TABLE IF NOT EXISTS `c_advice` (
  `ADVICE_ID` int(10) NOT NULL AUTO_INCREMENT,
  `ADVICE_NAME` varchar(500) NOT NULL,
  `ADVICE_DESCRIPTION` text NOT NULL,
  PRIMARY KEY (`ADVICE_ID`)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci COMMENT='Contains information about c_advices';

# Dumping structure for table my_life_dtb.c_advices_attachments
DROP TABLE IF EXISTS `c_advices_attachments`;
CREATE TABLE IF NOT EXISTS `c_advices_attachments` (
	`id` int(10) NOT NULL AUTO_INCREMENT,
	`file_name` varchar(500) NOT NULL,
	`c_advice_id` int(10) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci COMMENT='Contains information about c_advices_attachments';

# Creating foreign key for tab my_life_dtb.c_advices_attachments.c_advice_id -> my_life_dtb.c_advice.ADVICE_ID
alter table c_advices_attachments
add constraint c_advice_id_ADVICE_ID_fk foreign key(c_advice_id) references c_advice(ADVICE_ID);

# Dumping structure for spring security tables

drop table if exists users;
create table if not exists users(
id int not null auto_increment,
username varchar(50) not null,
password varchar(100) not null,
enabled boolean not null,
primary key(id)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;

drop table if exists authorities;
create table if not exists authorities (
id int not null auto_increment primary key,
username varchar(50) not null,
authority varchar(50) not null,
constraint fk_authorities_users foreign key(id) references users(id)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;

create unique index ix_auth_username on authorities (username,authority);

# Example insert into spring security tables (encrypted password is: user)

insert into users(id,username,password,enabled)
	values(null,'user','$2a$10$b2SzVGgMUgtSQlrQ1Ht1puOB3A4MM98wrq9eboDnH.GUNAdxqbB7i',true);
insert into authorities(id, username,authority) 
	values(null,'user','ROLE_USER');
	
# Example insert into spring security tables (encrypted password is: admin)

insert into users(id,username,password,enabled)
	values(null,'admin','$2a$10$4VNlXv/TDHAzYGyu7caxWuOvpmn05UJ8aO5Sk4psbnhJ7wa8qeccy',true);
insert into authorities(id,username,authority) 
	values(null,'admin','ROLE_ADMIN');
	
# Example insert into spring security tables (encrypted password is: superadmin)

insert into users(id,username,password,enabled)
	values(null,'superadmin','$2a$10$uTxsM6mTsjdYfWuuOtrFkOjG4zGCfJQqkIjpFRQ3MfUVEHHZhVfP.',true);
insert into authorities(id,username,authority) 
	values(null,'superadmin','ROLE_SUPERADMIN');