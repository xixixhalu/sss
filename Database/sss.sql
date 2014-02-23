# Host: localhost  (Version: 5.6.14)
# Date: 2014-02-11 16:38:36
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
  `course_prerequisite_ids` varchar(50) DEFAULT '-',
  `course_corequisite_ids` varchar(50) DEFAULT '-',
  `course_oncampus` varchar(10) DEFAULT '-',
  `course_online` varchar(10) DEFAULT '-',
  PRIMARY KEY (`course_id_pk`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8;

#
# Data for table "course"
#

INSERT INTO `course` VALUES (74,'CS',115,'Introduction to Computer Science',3,'null','null','null','null'),(75,'CS',116,'Web Technology',3,'null','null','null','null'),(76,'CS',117,'Search Engine',3,'null','null','null','null');

#
# Structure for table "course_group"
#

DROP TABLE IF EXISTS `course_group`;
CREATE TABLE `course_group` (
  `cg_id_pk` int(11) NOT NULL AUTO_INCREMENT,
  `cg_prefix` varchar(20) DEFAULT NULL,
  `cg_title` varchar(50) DEFAULT NULL,
  `cg_course_ids` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`cg_id_pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "course_group"
#

INSERT INTO `course_group` VALUES (11,'Group B','History/Social Science','74,75');

#
# Structure for table "degree"
#

DROP TABLE IF EXISTS `degree`;
CREATE TABLE `degree` (
  `degree_id_pk` int(8) NOT NULL AUTO_INCREMENT,
  `degree_title` varchar(50) DEFAULT NULL,
  `degree_req_ids` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`degree_id_pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "degree"
#


#
# Structure for table "requirement"
#

DROP TABLE IF EXISTS `requirement`;
CREATE TABLE `requirement` (
  `req_id_pk` int(11) NOT NULL AUTO_INCREMENT,
  `req_title` varchar(50) NOT NULL,
  `req_sr_ids` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`req_id_pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "requirement"
#


#
# Structure for table "semester"
#

DROP TABLE IF EXISTS `semester`;
CREATE TABLE `semester` (
  `semester_id_pk` int(11) NOT NULL AUTO_INCREMENT,
  `semester_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`semester_id_pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "semester"
#


#
# Structure for table "simple_requirement"
#

DROP TABLE IF EXISTS `simple_requirement`;
CREATE TABLE `simple_requirement` (
  `sr_id_pk` int(11) NOT NULL AUTO_INCREMENT,
  `sr_title` varchar(50) DEFAULT NULL,
  `sr_required_num` int(11) DEFAULT NULL,
  `sr_cg_ids` int(11) DEFAULT NULL,
  `sr_not` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`sr_id_pk`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

#
# Data for table "simple_requirement"
#


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

