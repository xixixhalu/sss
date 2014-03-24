# Host: localhost  (Version: 5.6.14)
# Date: 2014-03-23 22:12:41
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

INSERT INTO `course` VALUES (74,'CS',115,'Introduction to Computer Science',3,'[]','[]','0,2','null'),(84,'CS',146,'Introduction to Web Programming and Project Development',3,'[]','[]','2','-'),(85,'CS',284,'Data Structures',3,'[{\"id\":\"74\",\"prefix\":\"CS\",\"num\":\"115\",\"relation\":\"\",\"group\":\"1\"}]','[{\"id\":\"180\",\"prefix\":\"CS\",\"num\":\"135\",\"relation\":\"\",\"group\":\"1\"}]','0,2','null'),(86,'CS',334,'Automata & Computation',3,'[{\"id\":\"74\",\"relation\":\"\",\"group\":\"1\",\"prefix\":\"CS\",\"num\":\"115\"},{\"id\":\"180\",\"relation\":\"and\",\"group\":\"1\",\"prefix\":\"CS\",\"num\":\"135\"}]','[]','2','-'),(87,'CS',383,'Computer Organization & Programming',3,'[{\"id\":\"74\",\"relation\":\"\",\"group\":\"1\",\"prefix\":\"CS\",\"num\":\"115\"}]','[{\"id\":\"135\",\"relation\":\"\",\"group\":\"1\",\"prefix\":\"CS\",\"num\":\"181\"},{\"id\":\"85\",\"relation\":\"or\",\"group\":\"1\",\"prefix\":\"CS\",\"num\":\"284\"}]','2','-'),(88,'CS',385,'Algorithms',3,'[{\"id\":\"135\",\"relation\":\"\",\"group\":\"1\",\"prefix\":\"CS\",\"num\":\"181\"},{\"id\":\"85\",\"relation\":\"or\",\"group\":\"1\",\"prefix\":\"CS\",\"num\":\"284\"}]','[]','0,1,2','-'),(89,'CS',494,'Complier Design',3,'[]','[]','0,2','-'),(90,'CS',496,'Principles of Programming Languages',3,'[{\"id\":\"86\",\"relation\":\"\",\"group\":\"1\",\"prefix\":\"CS\",\"num\":\"334\"}]','[{\"id\":\"136\",\"relation\":\"\",\"group\":\"1\",\"prefix\":\"CS\",\"num\":\"182\"},{\"id\":\"88\",\"relation\":\"or\",\"group\":\"1\",\"prefix\":\"CS\",\"num\":\"385\"}]\n','0','-'),(92,'CS',442,'Database Management System',3,'[{\"id\":\"136\",\"relation\":\"\",\"group\":\"1\",\"prefix\":\"CS\",\"num\":\"182\"},{\"id\":\"88\",\"relation\":\"or\",\"group\":\"1\",\"prefix\":\"CS\",\"num\":\"385\"}]','[]','2','-'),(93,'CS',511,'Computer Programming',3,'[]','[]','0,2','-'),(94,'CS',488,'Computer Architecture',3,'[{\"id\":\"87\",\"relation\":\"\",\"group\":\"1\",\"prefix\":\"CS\",\"num\":\"383\"}]','[{\"id\":\"102\",\"relation\":\"\",\"group\":\"1\",\"prefix\":\"MA\",\"num\":\"222\"}]','0','-'),(95,'CS',492,'Operating System',3,'[{\"id\":\"87\",\"relation\":\"\",\"group\":\"1\",\"prefix\":\"CS\",\"num\":\"383\"},{\"id\":\"176\",\"relation\":\"and\",\"group\":\"1\",\"prefix\":\"CS\",\"num\":\"392\"}]','[]','0','-'),(96,'CS',423,'Senior Design I',3,'[{\"id\":\"136\",\"relation\":\"\",\"group\":\"1\",\"prefix\":\"CS\",\"num\":\"182\"},{\"id\":\"88\",\"relation\":\"or\",\"group\":\"1\",\"prefix\":\"CS\",\"num\":\"385\"},{\"id\":\"175\",\"relation\":\"and\",\"group\":\"2\",\"prefix\":\"CS\",\"num\":\"347\"}]','[]','2','-'),(97,'CS',573,'Fundamentals of Cybersecurity',3,'[]','[]','0,2','-'),(98,'CS',424,'Senior Design II',3,'[{\"id\":\"96\",\"relation\":\"\",\"group\":\"1\",\"prefix\":\"CS\",\"num\":\"423\"}]','[]','0','-'),(99,'MA',115,'Calculus I',3,'[]','[]','0,2','-'),(100,'MA',116,'Calculus II',3,'[]','[]','0,2','-'),(101,'MA',134,'Discrete Mathematics',3,'[]','[]','0,2','-'),(102,'MA',222,'Probability and Statistics',3,'[]','[]','0,2','-'),(103,'MA',331,'Statistical Methods',3,'[]','[]','0,2','-'),(104,'MGT',111,'Organizational Behavior & Social Psych',3,'[]','[]','0,2','-'),(105,'PE',200,'Football',3,'[]','[]','0,2','-'),(106,'PEP',111,'Mechanics',3,'[]','[]','0,2','-'),(107,'PEP',112,'E&M',3,'[]','[]','0,2','-'),(108,'PEP',221,'Physics Lab',3,'[]','[]','0,2','-'),(109,'CH',115,'Gen Chem I',3,'[]','[]','0,2','-'),(110,'CH',116,'Gen Chem II',3,'[]','[]','0,2','-'),(111,'CH',117,'Chemistry Lab',3,'[]','[]','0,2','-'),(112,'CH',281,'Bio & Biotech',3,'[]','[]','0,2','-'),(113,'CH',282,'Biochemistry Lab',3,'[]','[]','0,2','-'),(114,'HUM',103,'Freshman Writing and Communications I',3,'[]','[]','0,2','-'),(115,'HUM',104,'Freshman Writing and Communications II',3,'[]','[]','0,2','-'),(116,'HLI',113,'Western Literature: Classical Literature',3,'[]','[]','0,2','-'),(117,'HLI',114,'Western Literature: Middle Ages to the Present',3,'[]','[]','0,2','-'),(118,'HLI',115,'The English Language: Language of ideas (3-0-3)',3,'[]','[]','0,2','-'),(120,'HLI',116,'The English Language: Introduction to Literary Forms (3-0-3)',3,'[]','[]','0,2','-'),(121,'HLI',117,'Colonial and Romantic American Literature (3-0-3)',3,'[]','[]','0,2','-'),(122,'HLI',118,'Realist and Modern American Literature (3-0-3)',3,'[]','[]','0,2','-'),(123,'HMU',192,'Music Appreciation I (3-0-3)',3,'[]','[]','0,2','-'),(124,'HMU',193,'Music Appreciation II (3-0-3)',3,'[]','[]','0,2','-'),(125,'HPL',111,'Philosophy I: Theories of Human Nature (3-0-3)',3,'[]','[]','0,2','-'),(126,'HPL',112,'-Philosophy II: Knowledge, Reality and Nature (3-0-3)',3,'[]','[]','0,2','-'),(127,'HUM',107,'Modern Civilization and its sources (3-0-3)',3,'[]','[]','0,2','-'),(128,'HUM',108,'Studies in History and Social Science II (3-0-3)',3,'[]','[]','0,2','-'),(129,'HUM',288,'Sophomore Honors in History/Social Science (3-0-3)',3,'[]','[]','0,2','-'),(130,'HAR',190,'\r\nHistory of Art: Prehistory to the Modern Era (3-0-3)',3,'[]','[]','0,2','-'),(131,'HAR',191,'Modern Art History and Theory (3-0-3)',3,'[]','[]','0,2','-'),(132,'HHS',124,'History of European Society and Culture Since 1500 (3-0-3)',3,'[]','[]','0,2','-'),(133,'HHS',125,'United States Social and Economic History to 1900 (3-0-3)',3,'[]','[]','0,2','-'),(134,'CS',105,'Introduction to Computer Science',3,'[]','[]','0,2','2'),(135,'CS',181,'Introduction to Computer Science Honors I',3,'[]','[]','2','-'),(136,'CS',182,'Introduction to Computer Science Honors II',3,'[{\"id\":\"135\",\"relation\":\"\",\"group\":\"1\",\"prefix\":\"CS\",\"num\":\"181\"}]','[{\"id\":\"180\",\"relation\":\"\",\"group\":\"1\",\"prefix\":\"CS\",\"num\":\"135\"}]','0','-'),(139,'CPE',358,'Switching Theory and Logical Design',3,'[]','[]','0,2','-'),(140,'CPE',390,'Microprocessor Systems',3,'[]','[]','0,2','-'),(141,'CPE',450,'Embedded Systems for Real-Time Applications',3,'[]','[]','0,2','-'),(142,'CPE',555,'Real-Time and Embedded Systems',3,'[]','[]','0,2','-'),(143,'CPE',487,'Digital System Design',3,'[]','[]','0,2','-'),(144,'CS',521,'TCP/IP Networks',3,'[]','[]','0,2','-'),(145,'NIS',583,'Wireless Communications',3,'[]','[]','0,2','-'),(146,'NIS',584,'Wireless Systems Security',3,'[]','[]','0,2','-'),(147,'NIS',586,'Wireless Networking: Architectures, Protocols, and Standards',3,'[]','[]','0,2','-'),(148,'MGT',244,'Microeconomics',3,'[]','[]','0,2','-'),(149,'BT',115,'Financial Accounting',3,'[]','[]','0,2','-'),(150,'BT',215,'Cost Accounting',3,'[]','[]','0,2','-'),(151,'BT',321,'Finance',3,'[]','[]','0,2','-'),(152,'MA',221,'Differential Equations',3,'[]','[]','0,2','-'),(153,'MA',232,'Linear Algebra',3,'[]','[]','0,2','-'),(154,'MA',335,'Number Theory',3,'[]','[]','0,2','-'),(155,'MA',336,'Modern Algebra',3,'[]','[]','0,2','-'),(156,'MA',346,'Numerical Methods',3,'[]','[]','0,2','-'),(157,'MA',460,'Chaotic Dynamics',3,'[]','[]','0,2','-'),(158,'CS',537,'Interactive Computer Graphics I',3,'[]','[]','0,2','-'),(159,'CS',541,'Artificial Intelligence',3,'[]','[]','0,2','-'),(160,'CS',522,'Mobile and Pervasive Computing or CS 549 Distributed Systems',3,'[]','[]','0,2','-'),(161,'CS',539,'Real-Time Rendering, Gaming, and Simulations Programming',3,'[]','[]','0,2','-'),(162,'CS',545,'Human Computer Interaction',3,'[]','[]','0,2','-'),(163,'CS',503,'Discrete Mathematics for Cryptography',3,'[]','[]','0,2','-'),(164,'MA',503,'Discrete Mathematics for Cryptography',3,'[]','[]','0,2','-'),(165,'CS',576,'Secure Systems',3,'[]','[]','0,2','-'),(166,'CS',577,'Cybersecurity Lab',3,'[]','[]','0,2','-'),(167,'CS',578,'Privacy in a Networked World',3,'[]','[]','0,2','-'),(168,'CS',579,'Foundations of Cryptography',3,'[]','[]','0,2','-'),(169,'CS',546,'Web Programming',3,'[]','[]','0,2','-'),(170,'CS',549,'Distributed Systems and Cloud Computing',3,'[]','[]','0,2','-'),(171,'CS',600,'Advanced Algorithm Design and Implementation',3,'[]','[]','0,2','-'),(172,'CS',601,'Algorithmic Complexity',3,'[]','[]','0,2','-'),(173,'CS',630,'Automata and Formal Languages',3,'[]','[]','0,2','-'),(174,'CS',634,'Decidability and Computability',3,'[]','[]','0,2','-'),(175,'CS',347,'Software Development Process',3,'[{\"id\":\"135\",\"relation\":\"\",\"group\":\"1\",\"prefix\":\"CS\",\"num\":\"181\"},{\"id\":\"85\",\"relation\":\"or\",\"group\":\"1\",\"prefix\":\"CS\",\"num\":\"284\"},{\"id\":\"180\",\"relation\":\"and\",\"group\":\"2\",\"prefix\":\"CS\",\"num\":\"135\"}]','[]','0','-'),(176,'CS',392,'Systems Programming',3,'[{\"id\":\"136\",\"relation\":\"\",\"group\":\"1\",\"prefix\":\"CS\",\"num\":\"182\"},{\"id\":\"88\",\"relation\":\"or\",\"group\":\"1\",\"prefix\":\"CS\",\"num\":\"385\"}]','[]','0,2','-'),(177,'CS',558,'Computer Vision',3,'[]','[]','0,2','-'),(178,'CS',549,'Distributed and Cloud Computing',3,'[]','[]','0,2','-'),(179,'CS',526,'Enterprise and Cloud Computing',3,'[]','[]','0,2','-'),(180,'CS',135,'Discrete Structures',3,'[]','[]','0,2','null'),(181,'CS',370,'Creative Problem Solving and Team Programming',3,'[{\"id\":\"136\",\"relation\":\"\",\"group\":\"1\",\"prefix\":\"CS\",\"num\":\"182\"},{\"id\":\"88\",\"relation\":\"or\",\"group\":\"1\",\"prefix\":\"CS\",\"num\":\"385\"}]','[]','0,2','-'),(184,'CS',443,'Database Practicum',3,'[]','[{\"id\":\"92\",\"relation\":\"\",\"group\":\"1\",\"prefix\":\"CS\",\"num\":\"442\"}]','2','-'),(185,'CS',465,'Selected Topics in Computer Science',3,'[]','[]','0,2','-'),(186,'CS',497,'Independent Study',3,'[]','[]','0,2','-'),(187,'CS',498,'Senior Research I',3,'[]','[]','0,2','-'),(188,'CS',499,'Senior Research II',3,'[]','[]','0,2','-'),(189,'HHS',123,'History of European Society and Culture to 1500 (3-0-3)',3,'[]','[]','0,2','-'),(190,'HSS',371,'Computers & Society',3,'[]','[]','0,2','-'),(191,'PE',200,'Tennis',3,'[]','[]','0,2','-'),(192,'PE',200,'Basketball',3,'[]','[]','0,2','-'),(193,'PE',200,'BaseBall',3,'[]','[]','0,2','-'),(194,'PE',200,'Soccer',3,'[]','[]','0,2','-'),(195,'PE',200,'Table Tennis',3,'[]','[]','0,2','-');

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

INSERT INTO `course_group` VALUES (13,'CS Course','Required Computer Science Courses','74,84,85,86,87,88,89,90,92,93,94,95,96,97,98'),(14,'Math Course','Required Math Courses','99,100,101,102,103'),(15,'MGT Course','Required Management Course','104'),(16,'PE Course','Required Physical Education Courses','105,191,192,193,194,195'),(17,'SCI Course I','Required Science Courses I','106,107,108'),(18,'SCI Course II','Required Science Courses II','109,110,111'),(19,'SCI Course III','Required Science Courses III','109,111,112'),(20,'SCI Course IV','Required Science Courses IV','109,112,113'),(21,'SCI Course V','Required Science Courses V','106,112,113'),(22,'L/P Group A','Group A: Literature / Philosophy','114,115,116,117,118,120,121,122,123,124,125,126'),(23,'H/SS Group B','Group B: History/ Social Science','114,115,127,128,129,130,131,132,133,189'),(24,'HSS 371','HSS 371','190'),(25,'SCI/MA Ele','Science/Math Electives','99,100,101,102,103,106,107,108,109,110,111,112,113,152,153,154,155,156,157,164'),(26,'Tech Ele','Technical Electives','74,84,85,86,87,88,89,90,92,93,94,95,96,97,98,134,135,136,144,158,159,160,161,162,163,165,166,167,168,169,170,171,172,173,174,175,176,177,178,179,180,181,184,185,186,187,188');

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

INSERT INTO `degree` VALUES (5,'BS in CS for students entering in September 20XX','17,18,19,20,21,22,23,25');

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

INSERT INTO `requirement` VALUES (17,'Required Computer Science Courses','[{\"id\":\"7\",\"name\":\"Required Computer Science Courses\",\"group\":\"1\"}]'),(18,'Required Math Courses','[{\"id\":\"8\",\"name\":\"Required Math Courses\",\"group\":\"1\"}]'),(19,'Required Management Course','[{\"id\":\"9\",\"name\":\"Required Management Course\",\"group\":\"1\"}]'),(20,'Required Science Courses','[{\"id\":\"10\",\"name\":\"Required Science Courses I\",\"group\":\"1\"},{\"id\":\"11\",\"name\":\"Required Science Courses II\",\"relation\":\"or\",\"group\":\"1\"},{\"id\":\"12\",\"name\":\"Required Science Courses III\",\"relation\":\"or\",\"group\":\"1\"},{\"id\":\"13\",\"name\":\"Required Science Courses IV\",\"relation\":\"or\",\"group\":\"1\"},{\"id\":\"14\",\"name\":\"Required Science Courses V\",\"relation\":\"or\",\"group\":\"1\"}]'),(21,'Required PE Course','[{\"id\":\"15\",\"name\":\"PE\",\"group\":\"1\"}]'),(22,'Required Humanities Course','[{\"id\":\"16\",\"name\":\"Group A: Literature / Philosophy\",\"group\":\"1\"},{\"id\":\"17\",\"name\":\" Group B: History/ Social Science\",\"relation\":\"and\",\"group\":\"1\"},{\"id\":\"18\",\"name\":\"HSS 371\",\"relation\":\"and\",\"group\":\"1\"}]'),(23,'Science/Math Electives ','[{\"id\":\"19\",\"name\":\"Science/Math Electives \",\"group\":\"1\"}]'),(25,'Technical Electives','[{\"id\":\"20\",\"name\":\"Technical Electives\",\"group\":\"1\"}]');

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

INSERT INTO `simple_requirement` VALUES (7,'Required Computer Science Courses',15,13),(8,'Required Math Courses',5,14),(9,'Required Management Course',1,15),(10,'Required Science Courses I',3,17),(11,'Required Science Courses II',3,18),(12,'Required Science Courses III',3,19),(13,'Required Science Courses IV',3,20),(14,'Required Science Courses V',3,21),(15,'PE',6,16),(16,'Group A: Literature / Philosophy',2,22),(17,' Group B: History/ Social Science',2,23),(18,'HSS 371',1,24),(19,'Science/Math Electives ',2,25),(20,'Technical Electives',2,26);

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

