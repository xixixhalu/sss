# Host: localhost  (Version: 5.6.14)
# Date: 2014-03-05 15:36:06
# Generator: MySQL-Front 5.3  (Build 4.68)

/*!40101 SET NAMES utf8 */;

#
# Structure for table "course"
#

DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `course_id_pk` int(11) NOT NULL AUTO_INCREMENT,
  `course_prefix` varchar(20) DEFAULT NULL,
  `course_number` int(11) DEFAULT NULL,
  `course_title` varchar(50) DEFAULT NULL,
  `course_credit` int(11) DEFAULT NULL,
  `course_prerequisite_ids` text,
  `course_corequisite_ids` text,
  `course_oncampus` varchar(20) DEFAULT '-',
  `course_online` varchar(20) DEFAULT '-',
  PRIMARY KEY (`course_id_pk`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8;

#
# Data for table "course"
#

INSERT INTO `course` VALUES (74,'CS',115,'Introduction to Computer Science',3,'null','null','null','null'),(75,'CS',116,'Web Technology',3,'null','null','null','null'),(76,'CS',117,'Search Engine',3,'null','null','null','null'),(78,'adfsa',444,'asdf',4,'null','null','2','2');

#
# Structure for table "course_group"
#

DROP TABLE IF EXISTS `course_group`;
CREATE TABLE `course_group` (
  `cg_id_pk` int(11) NOT NULL AUTO_INCREMENT,
  `cg_prefix` varchar(20) DEFAULT NULL,
  `cg_title` varchar(50) DEFAULT NULL,
  `cg_course_ids` text,
  PRIMARY KEY (`cg_id_pk`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

#
# Data for table "course_group"
#

INSERT INTO `course_group` VALUES (11,'Group B','History/Social Science','74,75'),(12,'Group A','Science','74,76');

#
# Structure for table "degree"
#

DROP TABLE IF EXISTS `degree`;
CREATE TABLE `degree` (
  `degree_id_pk` int(11) NOT NULL AUTO_INCREMENT,
  `degree_title` varchar(50) DEFAULT NULL,
  `degree_req_ids` text,
  PRIMARY KEY (`degree_id_pk`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

#
# Data for table "degree"
#

INSERT INTO `degree` VALUES (4,'test Degree','7');

#
# Structure for table "requirement"
#

DROP TABLE IF EXISTS `requirement`;
CREATE TABLE `requirement` (
  `req_id_pk` int(11) NOT NULL AUTO_INCREMENT,
  `req_title` varchar(50) NOT NULL,
  `req_sr_ids` text,
  PRIMARY KEY (`req_id_pk`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

#
# Data for table "requirement"
#

INSERT INTO `requirement` VALUES (7,'SR1','5'),(8,'RE 2','5and6'),(9,'RE3','5and5'),(11,'retest','5and6'),(12,'test re','6AND5'),(16,'gasdf','[{\"id\":\"5\",\"name\":\"SR 1\",\"group\":\"1\"},{\"id\":\"5\",\"name\":\"SR 1\",\"relation\":\"and\",\"group\":\"1\"}]');

#
# Structure for table "simple_requirement"
#

DROP TABLE IF EXISTS `simple_requirement`;
CREATE TABLE `simple_requirement` (
  `sr_id_pk` int(11) NOT NULL AUTO_INCREMENT,
  `sr_title` varchar(50) DEFAULT NULL,
  `sr_required_num` int(11) DEFAULT NULL,
  `sr_cg_ids` int(11) DEFAULT NULL,
  PRIMARY KEY (`sr_id_pk`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

#
# Data for table "simple_requirement"
#

INSERT INTO `simple_requirement` VALUES (5,'SR 1',1,11),(6,'SR2',1,12);

#
# Structure for table "test"
#

DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "test"
#

