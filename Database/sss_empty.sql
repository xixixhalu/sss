# Host: localhost  (Version: 5.6.14)
# Date: 2014-03-22 21:15:13
# Generator: MySQL-Front 5.3  (Build 4.115)

/*!40101 SET NAMES utf8 */;

#
# Structure for table "course"
#

DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `course_id_pk` int(11) NOT NULL AUTO_INCREMENT,
  `course_prefix` varchar(20) DEFAULT NULL,
  `course_number` int(11) DEFAULT NULL,
  `course_title` varchar(100) DEFAULT NULL,
  `course_credit` int(11) DEFAULT NULL,
  `course_prerequisite_ids` text,
  `course_corequisite_ids` text,
  `course_oncampus` varchar(20) DEFAULT '-',
  `course_online` varchar(20) DEFAULT '-',
  PRIMARY KEY (`course_id_pk`)
) ENGINE=InnoDB AUTO_INCREMENT=196 DEFAULT CHARSET=utf8;

#
# Data for table "course"
#


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
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

#
# Data for table "course_group"
#


#
# Structure for table "degree"
#

DROP TABLE IF EXISTS `degree`;
CREATE TABLE `degree` (
  `degree_id_pk` int(11) NOT NULL AUTO_INCREMENT,
  `degree_title` varchar(50) DEFAULT NULL,
  `degree_req_ids` text,
  PRIMARY KEY (`degree_id_pk`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

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
  `req_sr_ids` text,
  PRIMARY KEY (`req_id_pk`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

#
# Data for table "requirement"
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
  PRIMARY KEY (`sr_id_pk`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

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

