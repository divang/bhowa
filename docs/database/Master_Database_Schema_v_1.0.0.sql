# --------------------------------------------------------
# Host:                         sql6.freemysqlhosting.net
# Database:                     sql6134070
# Server version:               5.5.49-0ubuntu0.14.04.1
# Server OS:                    debian-linux-gnu
# HeidiSQL version:             5.0.0.3031
# Date/time:                    2016-09-13 18:03:21
# --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
# Dumping database structure for sql6134070
DROP DATABASE IF EXISTS `sql6134070`;
CREATE DATABASE IF NOT EXISTS `sql6134070` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `sql6134070`;


# Dumping structure for table sql6134070.Login
DROP TABLE IF EXISTS `Login`;
CREATE TABLE IF NOT EXISTS `Login` (
  `Login_Id` varchar(20) NOT NULL,
  `Password` varchar(20) NOT NULL,
  `Status` tinyint(1) NOT NULL DEFAULT '1',
  `Authorised_Activity` varchar(100) DEFAULT NULL,
  `Society_Id` int(100) DEFAULT NULL,
  PRIMARY KEY (`Login_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table sql6134070.Login: 45 rows
/*!40000 ALTER TABLE `Login` DISABLE KEYS */;
INSERT IGNORE INTO `Login` (`Login_Id`, `Password`, `Status`, `Authorised_Activity`, `Society_Id`) VALUES ('abhinav1', '1', 1, NULL, 1), ('Amarjeet1', '1', 1, NULL, 1), ('Anshuman1', '1', 1, NULL, 1), ('Ashutosh1', '1', 1, NULL, 1), ('Balaji1', '1', 1, NULL, 1), ('Dhiman1', '1', 1, NULL, 1), ('divang1', '1', 1, NULL, 1), ('divangd', '1', 1, NULL, 2), ('Gautam1', '1', 1, NULL, 1), ('geetika', '1', 1, NULL, 1), ('geetika1', '1', 1, NULL, 2), ('Gopa1', '1', 1, NULL, 1), ('Ivin1', '1', 1, NULL, 1), ('jay1', '1', 1, NULL, 1), ('Jaya1', '1', 1, NULL, 1), ('Karthik1', '1', 1, NULL, 1), ('Krishna1', '1', 1, NULL, 1), ('Krishnan1', '1', 1, NULL, 1), ('Mahesh1', '1', 1, NULL, 1), ('maheshwar1', '1', 1, NULL, 1), ('Manas1', '1', 1, NULL, 1), ('Manendra1', '1', 1, NULL, 1), ('manoj1', '1', 1, NULL, 1), ('Niteen1', '1', 1, NULL, 1), ('Phani1', '1', 1, NULL, 1), ('Philip1', '1', 1, NULL, 1), ('poonam', '1', 1, NULL, 1), ('Praveen1', '1', 1, NULL, 1), ('Raghunandan1', '1', 1, NULL, 1), ('Raj1', '1', 1, NULL, 1), ('ramesh1', '1', 1, NULL, 1), ('Sanjib1', '1', 1, NULL, 1), ('Satheesh1', '1', 1, NULL, 1), ('Shashi1', '1', 1, NULL, 1), ('Sidda1', '1', 1, NULL, 1), ('Subhash1', '1', 1, NULL, 1), ('sudha', '1', 1, NULL, 1), ('Sundarali1', '1', 1, NULL, 1), ('test', '1', 1, NULL, 1), ('vibhanshu1', '1', 1, NULL, 2), ('Vinod1', '1', 1, NULL, 1), ('vinoy1', '1', 1, NULL, 1), ('vishesh1', '1', 1, NULL, 1), ('vivek1', '1', 1, NULL, 1), ('w', '1', 1, NULL, 1);
/*!40000 ALTER TABLE `Login` ENABLE KEYS */;


# Dumping structure for table sql6134070.Society
DROP TABLE IF EXISTS `Society`;
CREATE TABLE IF NOT EXISTS `Society` (
  `Society_Id` int(100) NOT NULL,
  `Society_Name` varchar(50) NOT NULL,
  `Database_URL` varchar(200) NOT NULL,
  `Database_User` varchar(50) NOT NULL,
  `Database_Password` varchar(50) NOT NULL,
  `Email_Id` varchar(100) NOT NULL,
  PRIMARY KEY (`Society_Id`),
  UNIQUE KEY `Society_Name` (`Society_Name`),
  UNIQUE KEY `Database_URL_Database_User` (`Database_URL`,`Database_User`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table sql6134070.Society: 2 rows
/*!40000 ALTER TABLE `Society` DISABLE KEYS */;
INSERT IGNORE INTO `Society` (`Society_Id`, `Society_Name`, `Database_URL`, `Database_User`, `Database_Password`, `Email_Id`) VALUES (1, 'BHOWA', 'jdbc:mysql://sql6.freemysqlhosting.net:3306/sql6135564', 'sql6135564', 'uTUgxGNjLx', 'bhowa.begurwoods@gmail.com'), (2, 'DEMO', 'jdbc:mysql://sql6.freemysqlhosting.net:3306/sql6135566', 'sql6135566', 'liIWBN52LB', 'divang.sharma@gmail.com');
/*!40000 ALTER TABLE `Society` ENABLE KEYS */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
