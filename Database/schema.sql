DROP database IF EXISTS `sss`;
create database sss;
use sss;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "degree"
#

DROP TABLE IF EXISTS `degree`;
CREATE TABLE `degree` (
  `degree_id_pk` int(11) NOT NULL AUTO_INCREMENT,
  `degree_title` varchar(100) DEFAULT NULL,
  `degree_req_ids` text,
  PRIMARY KEY (`degree_id_pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "requirement"
#

DROP TABLE IF EXISTS `requirement`;
CREATE TABLE `requirement` (
  `req_id_pk` int(11) NOT NULL AUTO_INCREMENT,
  `req_title` varchar(50) NOT NULL,
  `req_sr_ids` text,
  PRIMARY KEY (`req_id_pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
# Structure for table "user"
#

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id_pk` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `user_password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id_pk`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

