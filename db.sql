/*
SQLyog Community v13.0.1 (64 bit)
MySQL - 5.1.32-community : Database - project
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`project` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `project`;

/*Table structure for table `a_add_tb` */

DROP TABLE IF EXISTS `a_add_tb`;

CREATE TABLE `a_add_tb` (
  `hid` int(11) NOT NULL,
  `type` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `gender` varchar(20) DEFAULT NULL,
  `mobile` bigint(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `qualification` varchar(50) DEFAULT NULL,
  `experience` varchar(50) DEFAULT NULL,
  `photo` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`hid`),
  KEY `eid` (`hid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `a_add_tb` */

insert  into `a_add_tb`(`hid`,`type`,`name`,`place`,`dob`,`gender`,`mobile`,`email`,`qualification`,`experience`,`photo`) values 
(2,'TL','anjali','baluserrii','1996-05-04','Female',9043435858,'anju@gmail.com','mca','2','IMG-20181026-WA0013.jpg'),
(3,'TL','minnu','calicut','0995-05-06','Female',8606853650,'minnu@gmail.com','mca','4','IMG-20181027-WA0031.jpg'),
(4,'MH','neethu','ramanatukara','1995-05-29','Female',9656496218,'neethu@gmail.com','phd','5','IMG-20181027-WA0034.jpg'),
(5,'MH','diljith','calicut','1995-05-01','Male',8129130581,'diljith@gmail.com','mtech','5','IMG-20190221-WA0161-1-1.jpg');

/*Table structure for table `a_assign_work_tb` */

DROP TABLE IF EXISTS `a_assign_work_tb`;

CREATE TABLE `a_assign_work_tb` (
  `wid` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) DEFAULT NULL,
  `hid` int(50) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `work` varchar(50) DEFAULT NULL,
  `time` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`wid`),
  KEY `wid` (`wid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `a_assign_work_tb` */

insert  into `a_assign_work_tb`(`wid`,`type`,`hid`,`title`,`work`,`time`,`date`) values 
(1,'TL',2,'designing','work1.docx','10:00','2019-05-07'),
(2,'TL',3,'testing','work2.docx','09:00','2019-05-08'),
(3,'MH',4,'feild work1','office_1.docx','10:00','2019-05-08'),
(4,'MH',5,'field work2','gooofyyyyyyyyyy.docx','10:10','2019-05-08');

/*Table structure for table `a_view_attendance_tb` */

DROP TABLE IF EXISTS `a_view_attendance_tb`;

CREATE TABLE `a_view_attendance_tb` (
  `at_id` int(11) NOT NULL AUTO_INCREMENT,
  `eid` int(11) DEFAULT NULL,
  `time` time DEFAULT NULL,
  `attendance` varchar(50) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`at_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `a_view_attendance_tb` */

/*Table structure for table `assign_phone` */

DROP TABLE IF EXISTS `assign_phone`;

CREATE TABLE `assign_phone` (
  `assign_id` int(11) NOT NULL AUTO_INCREMENT,
  `imei` varchar(50) DEFAULT NULL,
  `eid` int(11) DEFAULT NULL,
  PRIMARY KEY (`assign_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `assign_phone` */

insert  into `assign_phone`(`assign_id`,`imei`,`eid`) values 
(1,'355637082572116',14);

/*Table structure for table `back_up_file` */

DROP TABLE IF EXISTS `back_up_file`;

CREATE TABLE `back_up_file` (
  `b_id` int(11) NOT NULL AUTO_INCREMENT,
  `imei` varchar(50) DEFAULT NULL,
  `f_name` varchar(500) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`b_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `back_up_file` */

insert  into `back_up_file`(`b_id`,`imei`,`f_name`,`status`) values 
(1,'355637082572116','KR.doc','pending'),
(3,'355637082572116','CONTENTS.doc','backuped'),
(7,'355637082572116','VR1doc.doc','pending'),
(9,'355637082572116','copyimax.doc','backuped'),
(10,'355637082572116','','pending');

/*Table structure for table `call_log` */

DROP TABLE IF EXISTS `call_log`;

CREATE TABLE `call_log` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `imei` varchar(50) DEFAULT NULL,
  `ph_no` bigint(20) DEFAULT NULL,
  `dur` varchar(50) DEFAULT NULL,
  `ph_type` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  `time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;

/*Data for the table `call_log` */

insert  into `call_log`(`cid`,`imei`,`ph_no`,`dur`,`ph_type`,`date`,`time`) values 
(1,'351831101813361',919895965510,'24 sec','incoming','2019-05-08','14:28:08'),
(2,'355637082572116',919895965510,'1 sec','incoming','2019-05-08','14:41:09'),
(3,'355637082572116',919895965510,'1 sec','outgoing','2019-05-08','14:41:49'),
(4,'351831101813361',9048610026,'0 sec','outgoing','2019-05-08','16:04:39'),
(5,'355637082572116',917034512437,'1 sec','incoming','2019-05-08','16:35:06'),
(6,'355637082572116',919645128811,'318 sec','incoming','2019-05-08','16:39:07'),
(7,'355637082572116',919645128811,'0 sec','outgoing','2019-05-08','16:39:40'),
(8,'355637082572116',919645128811,'0 sec','incoming','2019-05-08','16:41:20'),
(9,'355637082572116',919645128811,'65 sec','outgoing','2019-05-08','16:41:37'),
(10,'355637082572116',919645128811,'0 sec','outgoing','2019-05-08','16:42:48'),
(11,'355637082572116',919645128811,'0 sec','outgoing','2019-05-08','16:44:29'),
(12,'355637082572116',919645128811,'0 sec','incoming','2019-05-08','16:47:23'),
(13,'355637082572116',911408899515,'149 sec','incoming','2019-05-08','17:02:24'),
(14,'351831101813361',918606853650,'48 sec','incoming','2019-05-08','17:47:30'),
(15,'351831101813361',918606853650,'23 sec','outgoing','2019-05-08','22:58:50'),
(16,'862857034268250',918248269376,'12 sec','incoming','2019-05-08','22:58:50'),
(17,'355637082572116',919895965510,'12 sec','outgoing','2019-05-09','10:41:33'),
(18,'351831101813361',917025841495,'12 sec','incoming','2019-05-09','10:55:12'),
(19,'355637082572116',911408899186,'0 sec','incoming','2019-05-09','11:31:28'),
(20,'355637082572116',919895965510,'0 sec','incoming','2019-05-09','11:35:16'),
(21,'355637082572116',919895965510,'1 sec','outgoing','2019-05-09','11:37:23'),
(22,'355637082572116',919895965510,'0 sec','outgoing','2019-05-09','11:38:02'),
(23,'355637082572116',919446945399,'0 sec','incoming','2019-05-09','11:38:32'),
(24,'355637082572116',919446945399,'1 sec','incoming','2019-05-09','11:39:08'),
(25,'355637082572116',919895965510,'0 sec','incoming','2019-05-09','11:41:36'),
(26,'351831101813361',919633067474,'15 sec','outgoing','2019-05-09','11:58:53'),
(27,'351831101813361',919048610026,'0 sec','outgoing','2019-05-09','12:03:41');

/*Table structure for table `erase_file` */

DROP TABLE IF EXISTS `erase_file`;

CREATE TABLE `erase_file` (
  `ers_id` int(11) NOT NULL AUTO_INCREMENT,
  `imei` varchar(50) DEFAULT NULL,
  `f_name` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ers_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `erase_file` */

insert  into `erase_file`(`ers_id`,`imei`,`f_name`,`status`) values 
(2,'355637082572116','copyimax.doc','deleted');

/*Table structure for table `file_log` */

DROP TABLE IF EXISTS `file_log`;

CREATE TABLE `file_log` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `imei` varchar(50) DEFAULT NULL,
  `file` varchar(100) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

/*Data for the table `file_log` */

insert  into `file_log`(`f_id`,`imei`,`file`,`date`) values 
(1,'355637082572116','VR1doc.doc','2019-05-08'),
(2,'355637082572116','KR.doc','2019-05-08'),
(5,'355637082572116','abstract.doc','2019-05-08'),
(6,'355637082572116','Circular.pdf','2019-05-08'),
(7,'355637082572116','CONTENTS.doc','2019-05-08'),
(8,'355637082572116','copyimax.doc','2019-05-08'),
(9,'351831101813361','','2019-05-08'),
(10,'355637082572116','','2019-05-08'),
(11,'862857034268250','','2019-05-08');

/*Table structure for table `h_add_tb` */

DROP TABLE IF EXISTS `h_add_tb`;

CREATE TABLE `h_add_tb` (
  `eid` int(11) NOT NULL,
  `hid` int(11) DEFAULT NULL,
  `em_type` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `dob` varchar(50) DEFAULT NULL,
  `gender` varchar(50) DEFAULT NULL,
  `mobile` bigint(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `qualification` varchar(50) DEFAULT NULL,
  `experience` varchar(50) DEFAULT NULL,
  `photo` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`eid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `h_add_tb` */

insert  into `h_add_tb`(`eid`,`hid`,`em_type`,`name`,`place`,`dob`,`gender`,`mobile`,`email`,`qualification`,`experience`,`photo`) values 
(11,2,'Office employee','manu','calicut','18/09/1999','male',97679766776,'m@hajaj','mca','2','IMG_20151030_151249-1_1.jpg'),
(12,2,'Office employee','shigha','calicut','5/5/1996','female',8129130581,'shigha@gmail.com','mca','2','PicsPlay_1428133780831-1.jpg'),
(13,2,'Office employee','shilpa','calicut','5/5/1993','female',9656496218,'shilpa@gmail.com','mca','5','PicsPlay_1428133780831-1.jpg'),
(14,4,'Marketing employee','amrutha','calicut','5/5/1995','female',9656496213,'amru@gmail.com','mca','5','IMG_20151030_151249-1_1.jpg'),
(15,4,'Marketing employee','anu','4/5/1995','3/21993','female',9947453521,'anu@gmail.com','btech','2','IMG_20151030_151249-1_1.jpg');

/*Table structure for table `h_assign_work_tb` */

DROP TABLE IF EXISTS `h_assign_work_tb`;

CREATE TABLE `h_assign_work_tb` (
  `wr_id` int(11) NOT NULL AUTO_INCREMENT,
  `hid` int(11) DEFAULT NULL,
  `eid` int(11) DEFAULT NULL,
  `work` varchar(50) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  `wid` int(11) DEFAULT NULL,
  PRIMARY KEY (`wr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `h_assign_work_tb` */

insert  into `h_assign_work_tb`(`wr_id`,`hid`,`eid`,`work`,`title`,`date`,`wid`) values 
(1,2,12,'work1.docx','designing','5/5/2019',1),
(2,2,11,'office.docx','designing','5/6/2019',1),
(3,2,13,'Sprint2_for_Third_Eye.docx','designing','5/8/2019',1);

/*Table structure for table `location` */

DROP TABLE IF EXISTS `location`;

CREATE TABLE `location` (
  `loc_id` int(11) NOT NULL AUTO_INCREMENT,
  `imei` varchar(50) DEFAULT NULL,
  `lattitude` varchar(50) DEFAULT NULL,
  `longitude` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`loc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `location` */

insert  into `location`(`loc_id`,`imei`,`lattitude`,`longitude`,`date`) values 
(1,'351831101813361','11.2596971','75.8746364','2019-05-09'),
(2,'352313073196793','11.2577633','75.7845908','2019-05-08'),
(3,'355637082572116','11.257779','75.784598','2019-05-08'),
(4,'862857034268250','11.19706848','75.86254488','2019-05-08');

/*Table structure for table `login_tb` */

DROP TABLE IF EXISTS `login_tb`;

CREATE TABLE `login_tb` (
  `tid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`tid`),
  KEY `tid` (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

/*Data for the table `login_tb` */

insert  into `login_tb`(`tid`,`username`,`password`,`type`) values 
(1,'admin','admin','admin'),
(2,'anju@gmail.com','123','TL'),
(3,'minnu@gmail.com','8606853650','TL'),
(4,'neethu','111','MH'),
(5,'diljith','222','MH'),
(6,'shigha@gmail.com','9656496218','employee'),
(7,'shilpa@gmail.com','8129130581','employee'),
(8,'hari@gmail.com','9947453524','employee'),
(9,'vinu@gmail.com','9947453512','employee'),
(10,'a@Gmail.com','964646464','employee'),
(11,'manu','555','employee'),
(12,'shigha','666','employee'),
(13,'shilpa','777','employee'),
(14,'amru','888','employee'),
(15,'anu','999','employee');

/*Table structure for table `m_emp_send_report` */

DROP TABLE IF EXISTS `m_emp_send_report`;

CREATE TABLE `m_emp_send_report` (
  `report_id` int(11) NOT NULL AUTO_INCREMENT,
  `eid` int(11) DEFAULT NULL,
  `work_id` int(11) DEFAULT NULL,
  `report` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`report_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `m_emp_send_report` */

insert  into `m_emp_send_report`(`report_id`,`eid`,`work_id`,`report`,`date`) values 
(1,14,1,'office.docx','2019-05-08');

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `msg_id` int(11) NOT NULL AUTO_INCREMENT,
  `eid` int(11) DEFAULT NULL,
  `msg_name` varchar(50) DEFAULT NULL,
  `msg_details` varchar(50) DEFAULT NULL,
  `hid` int(11) DEFAULT NULL,
  PRIMARY KEY (`msg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `message` */

/*Table structure for table `mh_assign_work` */

DROP TABLE IF EXISTS `mh_assign_work`;

CREATE TABLE `mh_assign_work` (
  `work_id` int(11) NOT NULL AUTO_INCREMENT,
  `eid` int(11) DEFAULT NULL,
  `hid` int(11) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `work` varchar(50) DEFAULT NULL,
  `location` varchar(50) DEFAULT NULL,
  `assign_date` varchar(50) DEFAULT NULL,
  `wid` int(11) DEFAULT NULL,
  PRIMARY KEY (`work_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `mh_assign_work` */

insert  into `mh_assign_work`(`work_id`,`eid`,`hid`,`title`,`work`,`location`,`assign_date`,`wid`) values 
(1,14,4,'feild work1','DemoContent.txt','calicut','8/5/2019',3),
(2,15,4,'feild work1','office.docx','farok','5/6/2019',3);

/*Table structure for table `mh_report_send_to_admin` */

DROP TABLE IF EXISTS `mh_report_send_to_admin`;

CREATE TABLE `mh_report_send_to_admin` (
  `rp_id` int(11) NOT NULL AUTO_INCREMENT,
  `hid` int(11) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  `report` varchar(50) DEFAULT NULL,
  `work_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`rp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `mh_report_send_to_admin` */

/*Table structure for table `msg_log` */

DROP TABLE IF EXISTS `msg_log`;

CREATE TABLE `msg_log` (
  `m_id` int(11) NOT NULL AUTO_INCREMENT,
  `imei` varchar(20) DEFAULT NULL,
  `ph_no` varchar(45) DEFAULT NULL,
  `msg` varchar(50) DEFAULT NULL,
  `msg_type` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  `time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`m_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `msg_log` */

insert  into `msg_log`(`m_id`,`imei`,`ph_no`,`msg`,`msg_type`,`date`,`time`) values 
(1,'355637082572116','+919895965510','haiiii','incoming','2019-05-08','14:42:38'),
(2,'355637082572116','+91 98959 65510','Hello darling','outgoing','2019-05-08','14:43:49'),
(3,'862857034268250','+918248269376','Lol','outgoing','2019-05-08','23:01:25'),
(4,'351831101813361','+918606853650','Lol','incoming','2019-05-08','23:01:27'),
(5,'351831101813361','+91 85939 96328','Hoiii','outgoing','2019-05-09','10:25:05'),
(6,'355637082572116','+918248269376','Hoiii','incoming','2019-05-09','10:25:15');

/*Table structure for table `notification` */

DROP TABLE IF EXISTS `notification`;

CREATE TABLE `notification` (
  `notif_id` int(11) NOT NULL AUTO_INCREMENT,
  `notif_name` varchar(50) DEFAULT NULL,
  `h_id` int(11) DEFAULT NULL,
  `notif_content` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`notif_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `notification` */

/*Table structure for table `phone_reg` */

DROP TABLE IF EXISTS `phone_reg`;

CREATE TABLE `phone_reg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hid` int(11) DEFAULT NULL,
  `imei` varchar(50) DEFAULT NULL,
  `model` varchar(50) DEFAULT NULL,
  `os` varchar(50) DEFAULT NULL,
  `ph_no` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `phone_reg` */

insert  into `phone_reg`(`id`,`hid`,`imei`,`model`,`os`,`ph_no`) values 
(1,4,'352313073196793','j7','android',9895965510),
(2,4,'352314073196791','j2','android',8129130581),
(3,4,'355637082572116','motoe4','android',8593996328),
(4,4,'355637082572124','motog4','android',8593996328),
(5,4,'862857034268250','vivo','android',8606853650);

/*Table structure for table `report_send_employee` */

DROP TABLE IF EXISTS `report_send_employee`;

CREATE TABLE `report_send_employee` (
  `rid` int(11) NOT NULL AUTO_INCREMENT,
  `eid` int(11) DEFAULT NULL,
  `wr_id` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `report` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `report_send_employee` */

insert  into `report_send_employee`(`rid`,`eid`,`wr_id`,`date`,`report`) values 
(1,12,1,'2019-05-08','20190508-125143.txt'),
(2,11,2,'2019-05-08','20190508-131145.txt'),
(3,13,3,'2019-05-08','20190508-131313.txt'),
(4,11,2,'2019-05-08','20190508-205428.txt');

/*Table structure for table `send_report_tb` */

DROP TABLE IF EXISTS `send_report_tb`;

CREATE TABLE `send_report_tb` (
  `aid` int(11) NOT NULL AUTO_INCREMENT,
  `hid` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `report` varchar(50) DEFAULT NULL,
  `wr_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `send_report_tb` */

insert  into `send_report_tb`(`aid`,`hid`,`date`,`report`,`wr_id`) values 
(1,2,'2019-05-08','20190508-125143.txt',1),
(2,4,'2019-05-08','office.docx',1),
(3,4,'2019-05-08','office.docx',1),
(4,4,'2019-05-08','office.docx',1),
(5,4,'2019-05-08','office.docx',1);

/*Table structure for table `system_reg` */

DROP TABLE IF EXISTS `system_reg`;

CREATE TABLE `system_reg` (
  `sid` int(11) NOT NULL AUTO_INCREMENT,
  `sys_num` varchar(50) DEFAULT NULL,
  `sys_adrs` varchar(50) DEFAULT NULL,
  `hid` int(11) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `system_reg` */

insert  into `system_reg`(`sid`,`sys_num`,`sys_adrs`,`hid`) values 
(1,'1','192.168.43.190',2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
