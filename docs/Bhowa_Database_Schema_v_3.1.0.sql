# --------------------------------------------------------
# Host:                         sql6.freemysqlhosting.net
# Database:                     sql6134070
# Server version:               5.5.49-0ubuntu0.14.04.1
# Server OS:                    debian-linux-gnu
# HeidiSQL version:             5.0.0.3031
# Date/time:                    2016-09-11 03:17:22
# --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
# Dumping database structure for sql6134070
DROP DATABASE IF EXISTS `sql6134070`;
CREATE DATABASE IF NOT EXISTS `sql6134070` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `sql6134070`;


# Dumping structure for table sql6134070.Expense_Type
DROP TABLE IF EXISTS `Expense_Type`;
CREATE TABLE IF NOT EXISTS `Expense_Type` (
  `Type` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6134070.Expense_Type


# Dumping structure for table sql6134070.Flat
DROP TABLE IF EXISTS `Flat`;
CREATE TABLE IF NOT EXISTS `Flat` (
  `Flat_Id` varchar(10) NOT NULL,
  `Flat_Number` varchar(10) NOT NULL,
  `Area` int(10) NOT NULL,
  `Maintenance_Amount` float NOT NULL,
  `Block_Number` varchar(1) NOT NULL,
  `Status` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`Flat_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6134070.Flat


# Dumping structure for table sql6134070.Flat_Wise_Payable
DROP TABLE IF EXISTS `Flat_Wise_Payable`;
CREATE TABLE IF NOT EXISTS `Flat_Wise_Payable` (
  `Payable_Id` mediumint(10) NOT NULL AUTO_INCREMENT,
  `Flat_Id` varchar(10) NOT NULL,
  `Status` int(1) NOT NULL DEFAULT '1',
  `Month` int(2) NOT NULL,
  `Year` int(4) NOT NULL,
  `Amount` float NOT NULL,
  `Expense_Type` varchar(20) NOT NULL,
  `Comments` varchar(50) NOT NULL,
  PRIMARY KEY (`Payable_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table sql6134070.Flat_Wise_Payable: 36 rows
/*!40000 ALTER TABLE `Flat_Wise_Payable` DISABLE KEYS */;
REPLACE INTO `Flat_Wise_Payable` (`Payable_Id`, `Flat_Id`, `Status`, `Month`, `Year`, `Amount`, `Expense_Type`, `Comments`) VALUES (1, 'Flat_309', 1, 0, 0, 274755, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (2, 'Flat_001', 1, 0, 0, 244764, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (3, 'Flat_101', 1, 0, 0, 272091, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (4, 'Flat_201', 1, 0, 0, 272691, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (5, 'Flat_301', 1, 0, 0, 272191, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (6, 'Flat_002', 1, 0, 0, 232527, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (7, 'Flat_102', 1, 0, 0, 238825, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (8, 'Flat_202', 0, 0, 0, 20319, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (9, 'Flat_302', 1, 0, 0, 238825, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (10, 'Flat_003', 1, 0, 0, 184780, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (11, 'Flat_103', 1, 0, 0, 238256, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (12, 'Flat_203', 1, 0, 0, 240956, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (13, 'Flat_303', 0, 0, 0, 56458, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (14, 'Flat_004', 1, 0, 0, 229889, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (15, 'Flat_104', 1, 0, 0, 241743, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (16, 'Flat_204', 1, 0, 0, 241743, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (17, 'Flat_304', 1, 0, 0, 242043, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (18, 'Flat_005', 1, 0, 0, 260120, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (19, 'Flat_105', 1, 0, 0, 287070, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (20, 'Flat_205', 1, 0, 0, 287070, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (21, 'Flat_305', 1, 0, 0, 287070, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (22, 'Flat_006', 1, 0, 0, 244914, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (23, 'Flat_106', 1, 0, 0, 251930, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (24, 'Flat_206', 1, 0, 0, 252230, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (25, 'Flat_306', 1, 0, 0, 251930, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (26, 'Flat_007', 1, 0, 0, 232595, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (27, 'Flat_107', 1, 0, 0, 256175, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (28, 'Flat_207', 1, 0, 0, 175220, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (29, 'Flat_307', 1, 0, 0, 256175, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (30, 'Flat_008', 1, 0, 0, 230384, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (31, 'Flat_108', 1, 0, 0, 239871, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (32, 'Flat_208', 1, 0, 0, 239871, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (33, 'Flat_308', 1, 0, 0, 239871, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (34, 'Flat_009', 1, 0, 0, 270717, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (35, 'Flat_109', 1, 0, 0, 274455, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (36, 'Flat_209', 1, 0, 0, 275255, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data');
/*!40000 ALTER TABLE `Flat_Wise_Payable` ENABLE KEYS */;


# Dumping structure for table sql6134070.Login
DROP TABLE IF EXISTS `Login`;
CREATE TABLE IF NOT EXISTS `Login` (
  `Login_Id` varchar(20) NOT NULL,
  `Password` varchar(20) NOT NULL,
  `Status` tinyint(1) NOT NULL DEFAULT '1',
  `Authorised_Activity` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Login_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table sql6134070.Login: 41 rows
/*!40000 ALTER TABLE `Login` DISABLE KEYS */;
REPLACE INTO `Login` (`Login_Id`, `Password`, `Status`, `Authorised_Activity`) VALUES ('abhinav1', '1', 1, NULL), ('Amarjeet1', 'Amarjeet1123', 1, NULL), ('Anshuman1', 'Anshuman1123', 1, NULL), ('Ashutosh1', 'Ashutosh1123', 1, NULL), ('Balaji1', 'Balaji1123', 1, NULL), ('Dhiman1', 'Dhiman1123', 1, NULL), ('divang1', '1', 1, NULL), ('Gautam1', '1', 1, NULL), ('Gopa1', 'Gopa1123', 1, NULL), ('Ivin1', 'Ivin1123', 1, NULL), ('jay1', 'jay1123', 1, NULL), ('Jaya1', 'Jaya1123', 1, NULL), ('Karthik1', 'Karthik1123', 1, NULL), ('Krishna1', 'Krishna1123', 1, NULL), ('Krishnan1', 'Krishnan1123', 1, NULL), ('Mahesh1', 'Mahesh1123', 1, NULL), ('maheshwar1', 'maheshwar1123', 1, NULL), ('Manas1', '1', 1, NULL), ('Manendra1', 'Manendra1123', 1, NULL), ('manoj1', 'manoj1123', 1, NULL), ('Niteen1', 'Niteen1123', 1, NULL), ('Phani1', 'Phani1123', 1, NULL), ('Philip1', 'Philip1123', 1, NULL), ('poonam', 'poonam', 1, NULL), ('Praveen1', 'Praveen1123', 1, NULL), ('Raghunandan1', 'Raghunandan1123', 1, NULL), ('Raj1', 'Raj1123', 1, NULL), ('ramesh1', 'ramesh1123', 1, NULL), ('Sanjib1', 'Sanjib1123', 1, NULL), ('Satheesh1', 'Satheesh1123', 1, NULL), ('Shashi1', 'Shashi1123', 1, NULL), ('Sidda1', 'Sidda1123', 1, NULL), ('Subhash1', 'Subhash1123', 1, NULL), ('sudha', 'sudha1', 1, NULL), ('Sundarali1', 'Sundarali1123', 1, NULL), ('test', 'test1', 1, NULL), ('Vinod1', 'Vinod1123', 1, NULL), ('vinoy1', ' vinoy1123', 1, NULL), ('vishesh1', '1', 1, NULL), ('vivek1', 'vivek123', 1, NULL), ('w', 'w', 1, NULL);
/*!40000 ALTER TABLE `Login` ENABLE KEYS */;


# Dumping structure for table sql6134070.Notification
DROP TABLE IF EXISTS `Notification`;
CREATE TABLE IF NOT EXISTS `Notification` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `Message` varchar(100) DEFAULT '0',
  `Time` datetime DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6134070.Notification


# Dumping structure for table sql6134070.Periodic_Payable_Amount
DROP TABLE IF EXISTS `Periodic_Payable_Amount`;
CREATE TABLE IF NOT EXISTS `Periodic_Payable_Amount` (
  `Type` varchar(20) NOT NULL,
  `Month` int(10) NOT NULL,
  `Year` int(10) NOT NULL,
  `Status` int(10) NOT NULL,
  PRIMARY KEY (`Type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6134070.Periodic_Payable_Amount


# Dumping structure for table sql6134070.Transactions_Staging_Data
DROP TABLE IF EXISTS `Transactions_Staging_Data`;
CREATE TABLE IF NOT EXISTS `Transactions_Staging_Data` (
  `Transaction_ID` int(10) NOT NULL AUTO_INCREMENT,
  `StatementID` int(10) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Amount` float NOT NULL,
  `Transaction_Date` datetime NOT NULL,
  `Transaction_Flow` varchar(20) NOT NULL,
  `Transaction_Mode` varchar(20) DEFAULT NULL,
  `Transaction_Reference` varchar(70) DEFAULT NULL,
  `User_Id` varchar(20) DEFAULT NULL,
  `Flat_Id` varchar(20) DEFAULT NULL,
  `Admin_Approved` tinyint(1) NOT NULL DEFAULT '0',
  `Admin_Comment` varchar(100) NOT NULL,
  `User_Comment` varchar(100) NOT NULL,
  PRIMARY KEY (`Transaction_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table sql6134070.Transactions_Staging_Data: 37 rows
/*!40000 ALTER TABLE `Transactions_Staging_Data` DISABLE KEYS */;
REPLACE INTO `Transactions_Staging_Data` (`Transaction_ID`, `StatementID`, `Name`, `Amount`, `Transaction_Date`, `Transaction_Flow`, `Transaction_Mode`, `Transaction_Reference`, `User_Id`, `Flat_Id`, `Admin_Approved`, `Admin_Comment`, `User_Comment`) VALUES (1, 5, 'MANENDRA PRASAD SINGH', 4000, '2016-09-02 00:00:00', 'Credit', 'IB', '', NULL, NULL, 0, '0', '0'), (2, 6, 'AMARJEET KUMAR', 3500, '2016-09-02 00:00:00', 'Credit', 'NEFT', '1033808554 NEFTINW-004971119', NULL, NULL, 0, '0', '0'), (3, 8, 'DIVANG SHARMA', 11678, '2016-09-01 00:00:00', 'Credit', 'IB', '', NULL, NULL, 0, '0', '0'), (4, 9, 'DIVANG SHARMA', 3750, '2016-09-01 00:00:00', 'Credit', 'IB', '', NULL, NULL, 0, '0', '0'), (5, 13, 'DHIMAN CHAKRABORTY', 3200, '2016-08-31 00:00:00', 'Credit', 'NEFT', '1032230414 NEFTINW-0046792155', NULL, NULL, 0, '0', '0'), (6, 14, 'SUNDARALINGAM MURUGATHITH', 50000, '2016-08-31 00:00:00', 'Debit', 'CLG', '250', NULL, NULL, 0, '0', '0'), (7, 15, 'S KRISHNA MOORTHI', 3000, '2016-08-31 00:00:00', 'Credit', 'NEFT', '1031786468 NEFTINW-0046691849', NULL, NULL, 0, '0', '0'), (8, 16, 'VISHESH NIGAM', 3200, '2016-08-31 00:00:00', 'Credit', 'NEFT', 'CITIN681863291 NEFTINW-0046676804', NULL, NULL, 0, '0', '0'), (9, 18, 'RAGHUNANDAN H K', 3470, '2016-08-29 00:00:00', 'Credit', 'NEFT', 'N2421601571377 NEFTINW-0046528812', NULL, NULL, 0, '0', '0'), (10, 19, 'JAYAKRISHNAN A', 3710, '2016-08-29 00:00:00', 'Credit', 'NEFT', 'CITIN16680570890 NEFTINW-0046498770', NULL, NULL, 0, '0', '0'), (11, 33, 'MR JAYAPRAKASH H P C O CG', 5000, '2016-08-19 00:00:00', 'Credit', 'NEFT', 'SBIN616232761130 NEFTINW-0046087397', NULL, NULL, 0, '0', '0'), (12, 38, 'ASHUTOSH MOHANTY', 3100, '2016-08-16 00:00:00', 'Credit', 'NEFT', '1023581870 NEFTINW-0045853591', NULL, NULL, 0, '0', '0'), (13, 41, 'BIJUKUMAR', 3000, '2016-08-14 00:00:00', 'Credit', 'IMPS', '622715620355 IMPS-622715637734', NULL, NULL, 0, '0', '0'), (14, 44, 'S KRISHNA M', 3000, '2016-08-10 00:00:00', 'Credit', 'IMPS', '622319580518 IMPS-622319168490', NULL, NULL, 0, '0', '0'), (15, 45, 'VIVEK SHARMA', 3600, '2016-08-10 00:00:00', 'Credit', 'NEFT', '1021414712 NEFTINW-00699447', NULL, NULL, 0, '0', '0'), (16, 46, 'HARISH KUMAR', 7000, '2016-08-10 00:00:00', 'Debit', 'CLG', '249', NULL, NULL, 0, '0', '0'), (17, 47, 'ABHINAV NIGAM', 6560, '2016-08-10 00:00:00', 'Credit', 'MB', 'MB-999970704298', NULL, NULL, 0, '0', '0'), (18, 48, 'NITEEN UTTAM KOLE', 3200, '2016-08-10 00:00:00', 'Credit', 'NEFT', '1020897595 NEFTINW-0045632024', NULL, NULL, 0, '0', '0'), (19, 49, 'SHASHI PRAKASH KRISHNA', 4000, '2016-08-09 00:00:00', 'Credit', 'NEFT', '1020386156 NEFTINW-0045584316', NULL, NULL, 0, '0', '0'), (20, 50, 'SUMITH VARGHESE', 10000, '2016-08-09 00:00:00', 'Credit', 'NEFT', 'AXMB162228830545 NEFTINW-0045572445', NULL, NULL, 0, '0', '0'), (21, 52, 'DANIEL L', 16660, '2016-08-06 00:00:00', 'Debit', 'CLG', '247', NULL, NULL, 0, '0', '0'), (22, 54, 'GUARDIAN SOUHARDA SAHAKA', 24300, '2016-08-05 00:00:00', 'Debit', 'CLG', '246', NULL, NULL, 0, '0', '0'), (23, 55, 'GOPAKUMAR', 4000, '2016-08-05 00:00:00', 'Credit', 'IMPS', '621813079159 IMPS-621813540260', NULL, NULL, 0, '0', '0'), (24, 56, 'AKBAR HUSSAIN LASKAR', 18000, '2016-08-05 00:00:00', 'Debit', 'CASH', '248', NULL, NULL, 0, '0', '0'), (25, 57, 'MANOJ K NAIR', 3710, '2016-08-05 00:00:00', 'Credit', 'NEFT', 'CITIN16674423130 NEFTINW-0045374063', NULL, NULL, 0, '0', '0'), (26, 58, 'MAHESWAR MOHANTY', 3370, '2016-08-05 00:00:00', 'Credit', 'NEFT', 'CITIN16674414482 NEFTINW-0045374223', NULL, NULL, 0, '0', '0'), (27, 65, 'S KRISHNA M', 3000, '2016-08-02 00:00:00', 'Credit', 'IMPS', '621518927613 IMPS-621518211322', NULL, NULL, 0, '0', '0'), (28, 66, 'MANENDRA PRASAD SINGH', 4000, '2016-08-02 00:00:00', 'Credit', 'IB', '', NULL, NULL, 0, '0', '0'), (29, 67, 'JAYAKRISHNAN A', 5000, '2016-08-02 00:00:00', 'Credit', 'NEFT', 'CITIN163193850 NEFTINW-0045187845', NULL, NULL, 0, '0', '0'), (30, 68, 'AMARJEET KUMAR', 3500, '2016-08-02 00:00:00', 'Credit', 'NEFT', '1015182808 NEFTINW-0045193099', NULL, NULL, 0, '0', '0'), (31, 69, 'VISHESH NIGAM', 3200, '2016-08-02 00:00:00', 'Credit', 'NEFT', 'CITIN16673180822 NEFTINW-0045187664', NULL, NULL, 0, '0', '0'), (32, 72, 'MANAS KUMAR DASH', 3000, '2016-08-01 00:00:00', 'Credit', 'NEFT', 'N214160174065349 NEFTINW-0045125170', NULL, NULL, 0, '0', '0'), (33, 73, 'DIVANG SHARMA', 11678, '2016-08-01 00:00:00', 'Credit', 'IB', '', NULL, NULL, 0, '0', '0'), (34, 74, 'DIVANG SHARMA', 3750, '2016-08-01 00:00:00', 'Credit', 'IB', '', NULL, NULL, 0, '0', '0'), (35, 75, 'VINOD KUMAR A', 3440, '2016-08-01 00:00:00', 'Credit', 'NEFT', '000002174193 NEFTINW-0045101066', NULL, NULL, 0, '0', '0'), (36, 76, 'RAGHUNANDAN HK', 3470, '2016-08-01 00:00:00', 'Credit', 'NEFT', '000002172083 NEFTINW-0045095372', NULL, NULL, 0, '0', '0'), (37, 77, 'DHIMAN CHAKRABORTY', 3200, '2016-08-01 00:00:00', 'Credit', 'NEFT', '1014108518 NEFTINW-0045093266', NULL, NULL, 0, '0', '0');
/*!40000 ALTER TABLE `Transactions_Staging_Data` ENABLE KEYS */;


# Dumping structure for table sql6134070.Transactions_Verified
DROP TABLE IF EXISTS `Transactions_Verified`;
CREATE TABLE IF NOT EXISTS `Transactions_Verified` (
  `Transaction_ID` int(10) NOT NULL AUTO_INCREMENT,
  `Amount` float NOT NULL,
  `Transaction_Date` datetime NOT NULL,
  `Transaction_Flow` varchar(20) NOT NULL,
  `Transaction_Mode` varchar(20) DEFAULT NULL,
  `Transaction_Reference` varchar(70) DEFAULT NULL,
  `User_Id` varchar(20) DEFAULT NULL,
  `Flat_Id` varchar(20) DEFAULT NULL,
  `Admin_Comment` varchar(100) NOT NULL,
  `User_Comment` varchar(100) NOT NULL,
  PRIMARY KEY (`Transaction_ID`),
  UNIQUE KEY `Amount_Transaction_Date_Transaction_Reference` (`Amount`,`Transaction_Date`,`Transaction_Reference`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table sql6134070.Transactions_Verified: 32 rows
/*!40000 ALTER TABLE `Transactions_Verified` DISABLE KEYS */;
REPLACE INTO `Transactions_Verified` (`Transaction_ID`, `Amount`, `Transaction_Date`, `Transaction_Flow`, `Transaction_Mode`, `Transaction_Reference`, `User_Id`, `Flat_Id`, `Admin_Comment`, `User_Comment`) VALUES (1, 4000, '2016-09-02 00:00:00', 'Credit', 'IB', '', 'Manendra', 'Flat_105', '0', '0'), (2, 3500, '2016-09-02 00:00:00', 'Credit', 'NEFT', '1033808554 NEFTINW-004971119', 'Amarjeet', 'Flat_307', '0', '0'), (3, 11678, '2016-09-01 00:00:00', 'Credit', 'IB', '', 'divang', 'Flat_309', '0', '0'), (4, 3750, '2016-09-01 00:00:00', 'Credit', 'IB', '', 'divang', 'Flat_309', '0', '0'), (5, 3200, '2016-08-31 00:00:00', 'Credit', 'NEFT', '1032230414 NEFTINW-0046792155', 'Dhiman', 'Flat_304', '0', '0'), (6, 50000, '2016-08-31 00:00:00', 'Debit', 'CLG', '250', 'Sundarali', 'Flat_204', '0', '0'), (7, 3000, '2016-08-31 00:00:00', 'Credit', 'NEFT', '1031786468 NEFTINW-0046691849', 'Krishna', 'Flat_004', '0', '0'), (8, 3200, '2016-08-31 00:00:00', 'Credit', 'NEFT', 'CITIN681863291 NEFTINW-0046676804', 'vishesh', 'Flat_208', '0', '0'), (9, 3470, '2016-08-29 00:00:00', 'Credit', 'NEFT', 'N2421601571377 NEFTINW-0046528812', 'Raghunandan', 'Flat_009', '0', '0'), (10, 3710, '2016-08-29 00:00:00', 'Credit', 'NEFT', 'CITIN16680570890 NEFTINW-0046498770', 'jay', 'Flat_201', '0', '0'), (11, 5000, '2016-08-19 00:00:00', 'Credit', 'NEFT', 'SBIN616232761130 NEFTINW-0046087397', 'jay', 'Flat_201', '0', '0'), (12, 3100, '2016-08-16 00:00:00', 'Credit', 'NEFT', '1023581870 NEFTINW-0045853591', 'Ashutosh', 'Flat_308', '0', '0'), (13, 3000, '2016-08-10 00:00:00', 'Credit', 'IMPS', '622319580518 IMPS-622319168490', 'Krishna', 'Flat_004', '0', '0'), (14, 3600, '2016-08-10 00:00:00', 'Credit', 'NEFT', '1021414712 NEFTINW-00699447', 'viveksharma', 'Flat_007', '0', '0'), (15, 6560, '2016-08-10 00:00:00', 'Credit', 'MB', 'MB-999970704298', 'abhinav', 'Flat_306', '0', '0'), (16, 3200, '2016-08-10 00:00:00', 'Credit', 'NEFT', '1020897595 NEFTINW-0045632024', 'Niteen', 'Flat_104', '0', '0'), (17, 4000, '2016-08-09 00:00:00', 'Credit', 'NEFT', '1020386156 NEFTINW-0045584316', 'Shashi', 'Flat_103', '0', '0'), (18, 10000, '2016-08-09 00:00:00', 'Credit', 'NEFT', 'AXMB162228830545 NEFTINW-0045572445', 'Dhiman', 'Flat_304', '0', '0'), (19, 4000, '2016-08-05 00:00:00', 'Credit', 'IMPS', '621813079159 IMPS-621813540260', 'Gopa', 'Flat_005', '0', '0'), (20, 3710, '2016-08-05 00:00:00', 'Credit', 'NEFT', 'CITIN16674423130 NEFTINW-0045374063', 'manoj', 'Flat_101', '0', '0'), (21, 3370, '2016-08-05 00:00:00', 'Credit', 'NEFT', 'CITIN16674414482 NEFTINW-0045374223', 'maheshwar', 'Flat_206', '0', '0'), (22, 3000, '2016-08-02 00:00:00', 'Credit', 'IMPS', '621518927613 IMPS-621518211322', 'Krishna', 'Flat_004', '0', '0'), (23, 4000, '2016-08-02 00:00:00', 'Credit', 'IB', '', 'Manendra', 'Flat_105', '0', '0'), (24, 5000, '2016-08-02 00:00:00', 'Credit', 'NEFT', 'CITIN163193850 NEFTINW-0045187845', 'jay', 'Flat_201', '0', '0'), (25, 3500, '2016-08-02 00:00:00', 'Credit', 'NEFT', '1015182808 NEFTINW-0045193099', 'Amarjeet', 'Flat_307', '0', '0'), (26, 3200, '2016-08-02 00:00:00', 'Credit', 'NEFT', 'CITIN16673180822 NEFTINW-0045187664', 'vishesh', 'Flat_208', '0', '0'), (27, 3000, '2016-08-01 00:00:00', 'Credit', 'NEFT', 'N214160174065349 NEFTINW-0045125170', 'manas', 'Flat_001', '0', '0'), (28, 11678, '2016-08-01 00:00:00', 'Credit', 'IB', '', 'divang', 'Flat_309', '0', '0'), (29, 3750, '2016-08-01 00:00:00', 'Credit', 'IB', '', 'divang', 'Flat_309', '0', '0'), (30, 3440, '2016-08-01 00:00:00', 'Credit', 'NEFT', '000002174193 NEFTINW-0045101066', 'Vinod', 'Flat_107', '0', '0'), (31, 3470, '2016-08-01 00:00:00', 'Credit', 'NEFT', '000002172083 NEFTINW-0045095372', 'Raghunandan', 'Flat_009', '0', '0'), (32, 3200, '2016-08-01 00:00:00', 'Credit', 'NEFT', '1014108518 NEFTINW-0045093266', 'Dhiman', 'Flat_304', '0', '0');
/*!40000 ALTER TABLE `Transactions_Verified` ENABLE KEYS */;


# Dumping structure for table sql6134070.User_Details
DROP TABLE IF EXISTS `User_Details`;
CREATE TABLE IF NOT EXISTS `User_Details` (
  `User_Id` varchar(20) NOT NULL,
  `Login_Id` varchar(20) NOT NULL,
  `User_Type` varchar(20) NOT NULL,
  `Status` tinyint(1) NOT NULL,
  `Flat_Id` varchar(10) DEFAULT NULL,
  `Name` varchar(50) NOT NULL,
  `Name_Alias` varchar(400) DEFAULT NULL,
  `Mobile_No` bigint(13) DEFAULT NULL,
  `Moble_No_Alternate` bigint(13) DEFAULT NULL,
  `Email_Id` varchar(30) DEFAULT NULL,
  `Address` varchar(100) DEFAULT NULL,
  `Flat_Join_Date` datetime DEFAULT NULL,
  `Flat_Left_Date` datetime DEFAULT NULL,
  PRIMARY KEY (`User_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table sql6134070.User_Details: 37 rows
/*!40000 ALTER TABLE `User_Details` DISABLE KEYS */;
REPLACE INTO `User_Details` (`User_Id`, `Login_Id`, `User_Type`, `Status`, `Flat_Id`, `Name`, `Name_Alias`, `Mobile_No`, `Moble_No_Alternate`, `Email_Id`, `Address`, `Flat_Join_Date`, `Flat_Left_Date`) VALUES ('abhinav', 'abhinav1', '', 1, 'Flat_306', 'Abhinav Nigam', 'Abhinav,ABHINAV  NIGAM', NULL, NULL, NULL, NULL, NULL, NULL), ('Amarjeet', 'Amarjeet1', '', 1, 'Flat_307', 'Amarjeet Kumar', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Anshuman', 'Anshuman1', '', 1, 'Flat_207', 'Anshuman', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Ashutosh', 'Ashutosh1', '', 1, 'Flat_308', 'Ashutosh', 'ASHUSH MOHANTY,ASHUTOSH MOHANTY', NULL, NULL, NULL, NULL, NULL, NULL), ('Balaji', 'Balaji1', '', 1, 'Flat_205', 'Balaji Ganapathi', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Dhiman', 'Dhiman1', '', 1, 'Flat_304', 'Dhiman', 'SUMITH VARGHESE,DHIMAN CHAKRABORTY', NULL, NULL, NULL, NULL, NULL, NULL), ('divang', 'divang1', '', 1, 'Flat_309', 'Divang Sharma', 'divang,DIVANG  SHARMA  ', 9241797239, NULL, 'divang.s@gmail.com', 'Begur Heights', NULL, NULL), ('Gautam', 'Gautam1', '', 1, 'Flat_305', 'Gautam Kumar', 'GAUTAM  KUMAR  ', NULL, NULL, NULL, NULL, NULL, NULL), ('Gopa', 'Gopa1', '', 1, 'Flat_005', 'Gopa Kumar', 'GOPAKUMAR T,GOVULA MADHURI', NULL, NULL, NULL, NULL, NULL, NULL), ('Ivin', 'Ivin1', '', 1, 'Flat_106', 'Ivin Sebastian', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('jay', 'jay1', '', 1, 'Flat_201', ' Jay Krishan \r\n', 'JAYAKRISHNAN A,MR JAYAPRAKASH H P C O CG', NULL, NULL, NULL, NULL, NULL, NULL), ('Jaya', 'Jaya1', '', 1, 'Flat_109', 'Jaya Prakash', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Karthik', 'Karthik1', '', 1, 'Flat_002', 'Karthik', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Krishna', 'Krishna1', '', 1, 'Flat_004', 'Krishna Murthy', 'S KRISHNA M,S KRISHNA MOORTHI', NULL, NULL, NULL, NULL, NULL, NULL), ('Krishnan', 'Krishnan1', '', 1, 'Flat_006', 'Krishnan', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Mahesh', 'Mahesh1', '', 1, 'Flat_209', 'Mahesh Suragimath', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('maheshwar', 'maheshwar1', '', 1, 'Flat_206', 'Maheshwar Mohanty', 'MAHESWAR MOHANTY,SL. NO. MAHESWAR MOHANTY', NULL, NULL, NULL, NULL, NULL, NULL), ('manas', 'Manas1', '', 1, 'Flat_001', ' Manas Dash ', 'MANAS KUMAR DASH', NULL, NULL, NULL, NULL, NULL, NULL), ('Manendra', 'Manendra1', '', 1, 'Flat_105', 'Manendra Prasad  Singh', 'MANENA PRASAD SINGH,MANENDRA PRASAD SINGH', NULL, NULL, NULL, NULL, NULL, NULL), ('manoj', 'manoj1', '', 1, 'Flat_101', 'Manoj Nair', 'MANOJ K NAI ,MANOJ K NAIR', NULL, NULL, NULL, NULL, NULL, NULL), ('Niteen', 'Niteen1', '', 1, 'Flat_104', 'Niteen Kole', 'NITEEN UTTA,NITEEN UTTAM KOLE', NULL, NULL, NULL, NULL, NULL, NULL), ('Phani', 'Phani1', '', 1, 'Flat_102', 'Phani Krishna', 'PHANI KRISH ,PHANI KRISH ,PHANI KRISHNA NARAYANAM', NULL, NULL, NULL, NULL, NULL, NULL), ('Philip', 'Philip1', '', 1, 'Flat_203', 'Philip George', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Praveen', 'Praveen1', '', 1, 'Flat_008', 'Praveen Pattanshetti', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Raghunandan', 'Raghunandan1', '', 1, 'Flat_009', 'Raghunandan', 'RAGHUNANDAN HK,RAGHUNANDAN H K', NULL, NULL, NULL, NULL, NULL, NULL), ('Raj', 'Raj1', '', 1, 'Flat_108', 'Raj kumar Mandal', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('ramesh', 'ramesh1', '', 1, 'Flat_007', 'Ramesh Gangan', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Sanjib', 'Sanjib1', '', 1, 'Flat_303', 'Sanjib Singh', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Satheesh', 'Satheesh1', '', 1, 'Flat_302', 'Satheesh S', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Shashi', 'Shashi1', '', 1, 'Flat_103', 'Shashi Prakash Krishna', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Sidda', 'Sidda1', '', 1, 'Flat_003', 'Sidda Raju', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Subhash', 'Subhash1', '', 1, 'Flat_202', 'Subhash Chandra Gupta', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Sundarali', 'Sundarali1', '', 1, 'Flat_204', 'M. Sundaralingam', 'SUNDARALINGAM MURUGATHITHAN', NULL, NULL, NULL, NULL, NULL, NULL), ('Vinod', 'Vinod1', '', 1, 'Flat_107', 'A Vinod Kumar', 'VINOD KUMAR A', NULL, NULL, NULL, NULL, NULL, NULL), ('vinoy ', 'vinoy1', '', 1, 'Flat_301', ' Vinoy ', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('vishesh', 'vishesh1', '', 1, 'Flat_208', 'Vishesh Nigam', 'vishesh,SHRUTI NIGAM', NULL, NULL, NULL, NULL, NULL, NULL), ('viveksharma', 'vivek1', 'Tenant', 0, 'Flat_007', 'vivek sharma', 'vivek sharma', 980000, 1111, 's@s.com', NULL, '2016-09-04 00:00:00', '2016-09-04 00:00:00');
/*!40000 ALTER TABLE `User_Details` ENABLE KEYS */;


# Dumping structure for table sql6134070.User_Paid
DROP TABLE IF EXISTS `User_Paid`;
CREATE TABLE IF NOT EXISTS `User_Paid` (
  `Payment_ID` bigint(50) NOT NULL AUTO_INCREMENT,
  `User_ID` varchar(20) NOT NULL,
  `Flat_ID` varchar(10) NOT NULL,
  `Amount` double NOT NULL,
  `Expense_Type` varchar(50) NOT NULL,
  `Varified` tinyint(1) NOT NULL,
  `Varified_By` varchar(20) NOT NULL,
  `User_Comment` varchar(200) NOT NULL,
  `Admin_Comment` varchar(200) NOT NULL,
  PRIMARY KEY (`Payment_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table sql6134070.User_Paid: 98 rows
/*!40000 ALTER TABLE `User_Paid` DISABLE KEYS */;
REPLACE INTO `User_Paid` (`Payment_ID`, `User_ID`, `Flat_ID`, `Amount`, `Expense_Type`, `Varified`, `Varified_By`, `User_Comment`, `Admin_Comment`) VALUES (1, 'manas', 'Flat_001', 239885, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (2, 'manoj', 'Flat_101', 273467, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (3, 'jay', 'Flat_201', 267692, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (4, 'vinoy ', 'Flat_301', 273181, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (5, 'Karthik', 'Flat_002', 186227, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (6, 'Phani', 'Flat_102', 236677, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (7, 'Subhash', 'Flat_202', 20319, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (8, 'Satheesh', 'Flat_302', 239760, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (9, 'Sidda', 'Flat_003', 152000, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (10, 'Shashi', 'Flat_103', 238138, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (11, 'Philip', 'Flat_203', 220556, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (12, 'Sanjib', 'Flat_303', 56458, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (13, 'Krishna', 'Flat_004', 216853, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (14, 'Niteen', 'Flat_104', 241743, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (15, 'Sundarali', 'Flat_204', 241715, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (16, 'Dhiman', 'Flat_304', 242043, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (17, 'Gopa', 'Flat_005', 260200, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (18, 'Manendra', 'Flat_105', 281180, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (19, 'Balaji', 'Flat_205', 280590, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (20, 'Gautam', 'Flat_305', 291960, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (21, 'Krishnan', 'Flat_006', 232624, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (22, 'Ivin', 'Flat_106', 245190, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (23, 'maheshwar', 'Flat_206', 252230, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (24, 'abhinav', 'Flat_306', 248540, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (25, 'Vinod', 'Flat_107', 256175, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (26, 'Anshuman', 'Flat_207', 137725, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (27, 'Amarjeet', 'Flat_307', 255975, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (28, 'Praveen', 'Flat_008', 232004, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (29, 'Raj', 'Flat_108', 236701, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (30, 'vishesh', 'Flat_208', 234399, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (31, 'Ashutosh', 'Flat_308', 234981, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (32, 'Raghunandan', 'Flat_009', 270317, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (33, 'Jaya', 'Flat_109', 272855, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (34, 'Mahesh', 'Flat_209', 256801, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (35, 'divang', 'Flat_309', 222565, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (162, 'ramesh', 'Flat_007', 221400, 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data');
/*!40000 ALTER TABLE `User_Paid` ENABLE KEYS */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
