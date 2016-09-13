# --------------------------------------------------------
# Host:                         sql6.freemysqlhosting.net
# Database:                     sql6134070
# Server version:               5.5.49-0ubuntu0.14.04.1
# Server OS:                    debian-linux-gnu
# HeidiSQL version:             5.0.0.3031
# Date/time:                    2016-09-13 06:14:40
# --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
# Dumping database structure for sql6134070
DROP DATABASE IF EXISTS `sql6134070`;
CREATE DATABASE IF NOT EXISTS `sql6134070` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `sql6134070`;


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

# Dumping data for table sql6134070.Flat: 36 rows
/*!40000 ALTER TABLE `Flat` DISABLE KEYS */;
INSERT IGNORE INTO `Flat` (`Flat_Id`, `Flat_Number`, `Area`, `Maintenance_Amount`, `Block_Number`, `Status`) VALUES ('Flat_001', '001', 1367, 3250, 'A', 1), ('Flat_002', '002', 1302, 3150, 'A', 1), ('Flat_003', '003', 1293, 3140, 'A', 1), ('Flat_004', '004', 1190, 2990, 'A', 1), ('Flat_005', '005', 1840, 3960, 'B', 1), ('Flat_006', '006', 1365, 3250, 'B', 1), ('Flat_007', '007', 1211, 3020, 'B', 1), ('Flat_008', '008', 1200, 3000, 'B', 1), ('Flat_009', '009', 1645, 3670, 'B', 1), ('Flat_101', '101', 1673, 3710, 'A', 1), ('Flat_102', '102', 1302, 3150, 'A', 1), ('Flat_103', '103', 1293, 3140, 'A', 1), ('Flat_104', '104', 1334, 3200, 'A', 1), ('Flat_105', '105', 1840, 3960, 'B', 1), ('Flat_106', '106', 1448, 3370, 'B', 1), ('Flat_107', '107', 1496, 3440, 'B', 1), ('Flat_108', '108', 1310, 3170, 'B', 1), ('Flat_109', '109', 1699, 3750, 'B', 1), ('Flat_201', '201', 1673, 3710, 'A', 1), ('Flat_202', '202', 1302, 3150, 'A', 1), ('Flat_203', '203', 1293, 3140, 'A', 1), ('Flat_204', '204', 1334, 3200, 'A', 1), ('Flat_205', '205', 1840, 3960, 'B', 1), ('Flat_206', '206', 1448, 3370, 'B', 1), ('Flat_207', '207', 1496, 3440, 'B', 1), ('Flat_208', '208', 1310, 3170, 'B', 1), ('Flat_209', '209', 1699, 3750, 'B', 1), ('Flat_301', '301', 1673, 3710, 'A', 1), ('Flat_302', '302', 1302, 3150, 'A', 1), ('Flat_303', '303', 1293, 3140, 'A', 1), ('Flat_304', '304', 1334, 3200, 'A', 1), ('Flat_305', '305', 1840, 3960, 'B', 1), ('Flat_306', '306', 1448, 3370, 'B', 1), ('Flat_307', '307', 1496, 3440, 'B', 1), ('Flat_308', '308', 1310, 3170, 'B', 1), ('Flat_309', '309', 1699, 3750, 'B', 1);
/*!40000 ALTER TABLE `Flat` ENABLE KEYS */;


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
  PRIMARY KEY (`Payable_Id`),
  UNIQUE KEY `Flat_Id_Month_Year_Expense_Type` (`Flat_Id`,`Month`,`Year`,`Expense_Type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table sql6134070.Flat_Wise_Payable: 108 rows
/*!40000 ALTER TABLE `Flat_Wise_Payable` DISABLE KEYS */;
INSERT IGNORE INTO `Flat_Wise_Payable` (`Payable_Id`, `Flat_Id`, `Status`, `Month`, `Year`, `Amount`, `Expense_Type`, `Comments`) VALUES (1, 'Flat_309', 1, 7, 2106, 274755, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (2, 'Flat_001', 1, 7, 2106, 244764, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (3, 'Flat_101', 1, 7, 2106, 272091, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (4, 'Flat_201', 1, 7, 2106, 272691, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (5, 'Flat_301', 1, 7, 2106, 272191, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (6, 'Flat_002', 1, 7, 2106, 232527, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (7, 'Flat_102', 1, 7, 2106, 238825, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (8, 'Flat_202', 0, 7, 2106, 20319, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (9, 'Flat_302', 1, 7, 2106, 238825, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (10, 'Flat_003', 1, 7, 2106, 184780, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (11, 'Flat_103', 1, 7, 2106, 238256, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (12, 'Flat_203', 1, 7, 2106, 240956, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (13, 'Flat_303', 0, 7, 2106, 56458, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (14, 'Flat_004', 1, 7, 2106, 229889, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (15, 'Flat_104', 1, 7, 2106, 241743, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (16, 'Flat_204', 1, 7, 2106, 241743, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (17, 'Flat_304', 1, 7, 2106, 242043, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (18, 'Flat_005', 1, 7, 2106, 260120, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (19, 'Flat_105', 1, 7, 2106, 287070, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (20, 'Flat_205', 1, 7, 2106, 287070, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (21, 'Flat_305', 1, 7, 2106, 287070, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (22, 'Flat_006', 1, 7, 2106, 244914, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (23, 'Flat_106', 1, 7, 2106, 251930, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (24, 'Flat_206', 1, 7, 2106, 252230, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (25, 'Flat_306', 1, 7, 2106, 251930, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (26, 'Flat_007', 1, 7, 2106, 232595, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (27, 'Flat_107', 1, 7, 2106, 256175, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (28, 'Flat_207', 1, 7, 2106, 175220, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (29, 'Flat_307', 1, 7, 2106, 256175, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (30, 'Flat_008', 1, 7, 2106, 230384, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (31, 'Flat_108', 1, 7, 2106, 239871, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (32, 'Flat_208', 1, 7, 2106, 239871, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (33, 'Flat_308', 1, 7, 2106, 239871, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (34, 'Flat_009', 1, 7, 2106, 270717, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (35, 'Flat_109', 1, 7, 2106, 274455, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (36, 'Flat_209', 1, 7, 2106, 275255, 'Intial_Payable', 'July 2016 BHOWA XLS Receivable data'), (292, 'Flat_001', 1, 8, 2016, 3250, 'Monthly_Maintenance', ''), (293, 'Flat_002', 1, 8, 2016, 3150, 'Monthly_Maintenance', ''), (294, 'Flat_003', 1, 8, 2016, 3140, 'Monthly_Maintenance', ''), (295, 'Flat_004', 1, 8, 2016, 2990, 'Monthly_Maintenance', ''), (296, 'Flat_005', 1, 8, 2016, 3960, 'Monthly_Maintenance', ''), (297, 'Flat_006', 1, 8, 2016, 3250, 'Monthly_Maintenance', ''), (298, 'Flat_007', 1, 8, 2016, 3020, 'Monthly_Maintenance', ''), (299, 'Flat_008', 1, 8, 2016, 3000, 'Monthly_Maintenance', ''), (300, 'Flat_009', 1, 8, 2016, 3670, 'Monthly_Maintenance', ''), (301, 'Flat_101', 1, 8, 2016, 3710, 'Monthly_Maintenance', ''), (302, 'Flat_102', 1, 8, 2016, 3150, 'Monthly_Maintenance', ''), (303, 'Flat_103', 1, 8, 2016, 3140, 'Monthly_Maintenance', ''), (304, 'Flat_104', 1, 8, 2016, 3200, 'Monthly_Maintenance', ''), (305, 'Flat_105', 1, 8, 2016, 3960, 'Monthly_Maintenance', ''), (306, 'Flat_106', 1, 8, 2016, 3370, 'Monthly_Maintenance', ''), (307, 'Flat_107', 1, 8, 2016, 3440, 'Monthly_Maintenance', ''), (308, 'Flat_108', 1, 8, 2016, 3170, 'Monthly_Maintenance', ''), (309, 'Flat_109', 1, 8, 2016, 3750, 'Monthly_Maintenance', ''), (310, 'Flat_201', 1, 8, 2016, 3710, 'Monthly_Maintenance', ''), (311, 'Flat_202', 1, 8, 2016, 3150, 'Monthly_Maintenance', ''), (312, 'Flat_203', 1, 8, 2016, 3140, 'Monthly_Maintenance', ''), (313, 'Flat_204', 1, 8, 2016, 3200, 'Monthly_Maintenance', ''), (314, 'Flat_205', 1, 8, 2016, 3960, 'Monthly_Maintenance', ''), (315, 'Flat_206', 1, 8, 2016, 3370, 'Monthly_Maintenance', ''), (316, 'Flat_207', 1, 8, 2016, 3440, 'Monthly_Maintenance', ''), (317, 'Flat_208', 1, 8, 2016, 3170, 'Monthly_Maintenance', ''), (318, 'Flat_209', 1, 8, 2016, 3750, 'Monthly_Maintenance', ''), (319, 'Flat_301', 1, 8, 2016, 3710, 'Monthly_Maintenance', ''), (320, 'Flat_302', 1, 8, 2016, 3150, 'Monthly_Maintenance', ''), (321, 'Flat_303', 1, 8, 2016, 3140, 'Monthly_Maintenance', ''), (322, 'Flat_304', 1, 8, 2016, 3200, 'Monthly_Maintenance', ''), (323, 'Flat_305', 1, 8, 2016, 3960, 'Monthly_Maintenance', ''), (324, 'Flat_306', 1, 8, 2016, 3370, 'Monthly_Maintenance', ''), (325, 'Flat_307', 1, 8, 2016, 3440, 'Monthly_Maintenance', ''), (326, 'Flat_308', 1, 8, 2016, 3170, 'Monthly_Maintenance', ''), (327, 'Flat_309', 1, 8, 2016, 3750, 'Monthly_Maintenance', ''), (355, 'Flat_001', 1, 9, 2016, 3250, 'Monthly_Maintenance', ''), (356, 'Flat_002', 1, 9, 2016, 3150, 'Monthly_Maintenance', ''), (357, 'Flat_003', 1, 9, 2016, 3140, 'Monthly_Maintenance', ''), (358, 'Flat_004', 1, 9, 2016, 2990, 'Monthly_Maintenance', ''), (359, 'Flat_005', 1, 9, 2016, 3960, 'Monthly_Maintenance', ''), (360, 'Flat_006', 1, 9, 2016, 3250, 'Monthly_Maintenance', ''), (361, 'Flat_007', 1, 9, 2016, 3020, 'Monthly_Maintenance', ''), (362, 'Flat_008', 1, 9, 2016, 3000, 'Monthly_Maintenance', ''), (363, 'Flat_009', 1, 9, 2016, 3670, 'Monthly_Maintenance', ''), (364, 'Flat_101', 1, 9, 2016, 3710, 'Monthly_Maintenance', ''), (365, 'Flat_102', 1, 9, 2016, 3150, 'Monthly_Maintenance', ''), (366, 'Flat_103', 1, 9, 2016, 3140, 'Monthly_Maintenance', ''), (367, 'Flat_104', 1, 9, 2016, 3200, 'Monthly_Maintenance', ''), (368, 'Flat_105', 1, 9, 2016, 3960, 'Monthly_Maintenance', ''), (369, 'Flat_106', 1, 9, 2016, 3370, 'Monthly_Maintenance', ''), (370, 'Flat_107', 1, 9, 2016, 3440, 'Monthly_Maintenance', ''), (371, 'Flat_108', 1, 9, 2016, 3170, 'Monthly_Maintenance', ''), (372, 'Flat_109', 1, 9, 2016, 3750, 'Monthly_Maintenance', ''), (373, 'Flat_201', 1, 9, 2016, 3710, 'Monthly_Maintenance', ''), (374, 'Flat_202', 1, 9, 2016, 3150, 'Monthly_Maintenance', ''), (375, 'Flat_203', 1, 9, 2016, 3140, 'Monthly_Maintenance', ''), (376, 'Flat_204', 1, 9, 2016, 3200, 'Monthly_Maintenance', ''), (377, 'Flat_205', 1, 9, 2016, 3960, 'Monthly_Maintenance', ''), (378, 'Flat_206', 1, 9, 2016, 3370, 'Monthly_Maintenance', ''), (379, 'Flat_207', 1, 9, 2016, 3440, 'Monthly_Maintenance', ''), (380, 'Flat_208', 1, 9, 2016, 3170, 'Monthly_Maintenance', ''), (381, 'Flat_209', 1, 9, 2016, 3750, 'Monthly_Maintenance', ''), (382, 'Flat_301', 1, 9, 2016, 3710, 'Monthly_Maintenance', ''), (383, 'Flat_302', 1, 9, 2016, 3150, 'Monthly_Maintenance', ''), (384, 'Flat_303', 1, 9, 2016, 3140, 'Monthly_Maintenance', ''), (385, 'Flat_304', 1, 9, 2016, 3200, 'Monthly_Maintenance', ''), (386, 'Flat_305', 1, 9, 2016, 3960, 'Monthly_Maintenance', ''), (387, 'Flat_306', 1, 9, 2016, 3370, 'Monthly_Maintenance', ''), (388, 'Flat_307', 1, 9, 2016, 3440, 'Monthly_Maintenance', ''), (389, 'Flat_308', 1, 9, 2016, 3170, 'Monthly_Maintenance', ''), (390, 'Flat_309', 1, 9, 2016, 3750, 'Monthly_Maintenance', '');
/*!40000 ALTER TABLE `Flat_Wise_Payable` ENABLE KEYS */;


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


# Dumping structure for table sql6134070.Notification
DROP TABLE IF EXISTS `Notification`;
CREATE TABLE IF NOT EXISTS `Notification` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `Message` varchar(100) DEFAULT '0',
  `Time` datetime DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6134070.Notification


# Dumping structure for table sql6134070.Society
DROP TABLE IF EXISTS `Society`;
CREATE TABLE IF NOT EXISTS `Society` (
  `Society_Id` int(100) NOT NULL,
  `Society_Name` varchar(50) NOT NULL,
  `Database_URL` varchar(200) NOT NULL,
  `Database_User` varchar(50) NOT NULL,
  `Database_Password` varchar(50) NOT NULL,
  `Database_Port` int(4) NOT NULL DEFAULT '3306',
  `Email_Id` varchar(100) NOT NULL,
  PRIMARY KEY (`Society_Id`),
  UNIQUE KEY `Society_Name` (`Society_Name`),
  UNIQUE KEY `Database_URL_Database_User` (`Database_URL`,`Database_User`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table sql6134070.Society: 2 rows
/*!40000 ALTER TABLE `Society` DISABLE KEYS */;
INSERT IGNORE INTO `Society` (`Society_Id`, `Society_Name`, `Database_URL`, `Database_User`, `Database_Password`, `Database_Port`, `Email_Id`) VALUES (1, 'BHOWA', 'jdbc:mysql://sql6.freemysqlhosting.net:3306/sql6135564', 'sql6135564', 'uTUgxGNjLx', 3306, 'bhowa.begurwoods@gmail.com'), (2, 'DEMO', 'jdbc:mysql://sql6.freemysqlhosting.net:3306/sql6135566', 'sql6135566', 'liIWBN52LB', 3306, 'divang.sharma@gmail.com');
/*!40000 ALTER TABLE `Society` ENABLE KEYS */;


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
INSERT IGNORE INTO `Transactions_Staging_Data` (`Transaction_ID`, `StatementID`, `Name`, `Amount`, `Transaction_Date`, `Transaction_Flow`, `Transaction_Mode`, `Transaction_Reference`, `User_Id`, `Flat_Id`, `Admin_Approved`, `Admin_Comment`, `User_Comment`) VALUES (1, 5, 'MANENDRA PRASAD SINGH', 4000, '2016-09-02 00:00:00', 'Credit', 'IB', '', NULL, NULL, 0, '0', '0'), (2, 6, 'AMARJEET KUMAR', 3500, '2016-09-02 00:00:00', 'Credit', 'NEFT', '1033808554 NEFTINW-004971119', NULL, NULL, 0, '0', '0'), (3, 8, 'DIVANG SHARMA', 11678, '2016-09-01 00:00:00', 'Credit', 'IB', '', NULL, NULL, 0, '0', '0'), (4, 9, 'DIVANG SHARMA', 3750, '2016-09-01 00:00:00', 'Credit', 'IB', '', NULL, NULL, 0, '0', '0'), (5, 13, 'DHIMAN CHAKRABORTY', 3200, '2016-08-31 00:00:00', 'Credit', 'NEFT', '1032230414 NEFTINW-0046792155', NULL, NULL, 0, '0', '0'), (6, 14, 'SUNDARALINGAM MURUGATHITH', 50000, '2016-08-31 00:00:00', 'Debit', 'CLG', '250', NULL, NULL, 0, '0', '0'), (7, 15, 'S KRISHNA MOORTHI', 3000, '2016-08-31 00:00:00', 'Credit', 'NEFT', '1031786468 NEFTINW-0046691849', NULL, NULL, 0, '0', '0'), (8, 16, 'VISHESH NIGAM', 3200, '2016-08-31 00:00:00', 'Credit', 'NEFT', 'CITIN681863291 NEFTINW-0046676804', NULL, NULL, 0, '0', '0'), (9, 18, 'RAGHUNANDAN H K', 3470, '2016-08-29 00:00:00', 'Credit', 'NEFT', 'N2421601571377 NEFTINW-0046528812', NULL, NULL, 0, '0', '0'), (10, 19, 'JAYAKRISHNAN A', 3710, '2016-08-29 00:00:00', 'Credit', 'NEFT', 'CITIN16680570890 NEFTINW-0046498770', NULL, NULL, 0, '0', '0'), (11, 33, 'MR JAYAPRAKASH H P C O CG', 5000, '2016-08-19 00:00:00', 'Credit', 'NEFT', 'SBIN616232761130 NEFTINW-0046087397', NULL, NULL, 0, '0', '0'), (12, 38, 'ASHUTOSH MOHANTY', 3100, '2016-08-16 00:00:00', 'Credit', 'NEFT', '1023581870 NEFTINW-0045853591', NULL, NULL, 0, '0', '0'), (13, 41, 'BIJUKUMAR', 3000, '2016-08-14 00:00:00', 'Credit', 'IMPS', '622715620355 IMPS-622715637734', NULL, NULL, 0, '0', '0'), (14, 44, 'S KRISHNA M', 3000, '2016-08-10 00:00:00', 'Credit', 'IMPS', '622319580518 IMPS-622319168490', NULL, NULL, 0, '0', '0'), (15, 45, 'VIVEK SHARMA', 3600, '2016-08-10 00:00:00', 'Credit', 'NEFT', '1021414712 NEFTINW-00699447', NULL, NULL, 0, '0', '0'), (16, 46, 'HARISH KUMAR', 7000, '2016-08-10 00:00:00', 'Debit', 'CLG', '249', NULL, NULL, 0, '0', '0'), (17, 47, 'ABHINAV NIGAM', 6560, '2016-08-10 00:00:00', 'Credit', 'MB', 'MB-999970704298', NULL, NULL, 0, '0', '0'), (18, 48, 'NITEEN UTTAM KOLE', 3200, '2016-08-10 00:00:00', 'Credit', 'NEFT', '1020897595 NEFTINW-0045632024', NULL, NULL, 0, '0', '0'), (19, 49, 'SHASHI PRAKASH KRISHNA', 4000, '2016-08-09 00:00:00', 'Credit', 'NEFT', '1020386156 NEFTINW-0045584316', NULL, NULL, 0, '0', '0'), (20, 50, 'SUMITH VARGHESE', 10000, '2016-08-09 00:00:00', 'Credit', 'NEFT', 'AXMB162228830545 NEFTINW-0045572445', NULL, NULL, 0, '0', '0'), (21, 52, 'DANIEL L', 16660, '2016-08-06 00:00:00', 'Debit', 'CLG', '247', NULL, NULL, 0, '0', '0'), (22, 54, 'GUARDIAN SOUHARDA SAHAKA', 24300, '2016-08-05 00:00:00', 'Debit', 'CLG', '246', NULL, NULL, 0, '0', '0'), (23, 55, 'GOPAKUMAR', 4000, '2016-08-05 00:00:00', 'Credit', 'IMPS', '621813079159 IMPS-621813540260', NULL, NULL, 0, '0', '0'), (24, 56, 'AKBAR HUSSAIN LASKAR', 18000, '2016-08-05 00:00:00', 'Debit', 'CASH', '248', NULL, NULL, 0, '0', '0'), (25, 57, 'MANOJ K NAIR', 3710, '2016-08-05 00:00:00', 'Credit', 'NEFT', 'CITIN16674423130 NEFTINW-0045374063', NULL, NULL, 0, '0', '0'), (26, 58, 'MAHESWAR MOHANTY', 3370, '2016-08-05 00:00:00', 'Credit', 'NEFT', 'CITIN16674414482 NEFTINW-0045374223', NULL, NULL, 0, '0', '0'), (27, 65, 'S KRISHNA M', 3000, '2016-08-02 00:00:00', 'Credit', 'IMPS', '621518927613 IMPS-621518211322', NULL, NULL, 0, '0', '0'), (28, 66, 'MANENDRA PRASAD SINGH', 4000, '2016-08-02 00:00:00', 'Credit', 'IB', '', NULL, NULL, 0, '0', '0'), (29, 67, 'JAYAKRISHNAN A', 5000, '2016-08-02 00:00:00', 'Credit', 'NEFT', 'CITIN163193850 NEFTINW-0045187845', NULL, NULL, 0, '0', '0'), (30, 68, 'AMARJEET KUMAR', 3500, '2016-08-02 00:00:00', 'Credit', 'NEFT', '1015182808 NEFTINW-0045193099', NULL, NULL, 0, '0', '0'), (31, 69, 'VISHESH NIGAM', 3200, '2016-08-02 00:00:00', 'Credit', 'NEFT', 'CITIN16673180822 NEFTINW-0045187664', NULL, NULL, 0, '0', '0'), (32, 72, 'MANAS KUMAR DASH', 3000, '2016-08-01 00:00:00', 'Credit', 'NEFT', 'N214160174065349 NEFTINW-0045125170', NULL, NULL, 0, '0', '0'), (33, 73, 'DIVANG SHARMA', 11678, '2016-08-01 00:00:00', 'Credit', 'IB', '', NULL, NULL, 0, '0', '0'), (34, 74, 'DIVANG SHARMA', 3750, '2016-08-01 00:00:00', 'Credit', 'IB', '', NULL, NULL, 0, '0', '0'), (35, 75, 'VINOD KUMAR A', 3440, '2016-08-01 00:00:00', 'Credit', 'NEFT', '000002174193 NEFTINW-0045101066', NULL, NULL, 0, '0', '0'), (36, 76, 'RAGHUNANDAN HK', 3470, '2016-08-01 00:00:00', 'Credit', 'NEFT', '000002172083 NEFTINW-0045095372', NULL, NULL, 0, '0', '0'), (37, 77, 'DHIMAN CHAKRABORTY', 3200, '2016-08-01 00:00:00', 'Credit', 'NEFT', '1014108518 NEFTINW-0045093266', NULL, NULL, 0, '0', '0');
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
INSERT IGNORE INTO `Transactions_Verified` (`Transaction_ID`, `Amount`, `Transaction_Date`, `Transaction_Flow`, `Transaction_Mode`, `Transaction_Reference`, `User_Id`, `Flat_Id`, `Admin_Comment`, `User_Comment`) VALUES (1, 4000, '2016-09-02 00:00:00', 'Credit', 'IB', '', 'Manendra', 'Flat_105', '0', '0'), (2, 3500, '2016-09-02 00:00:00', 'Credit', 'NEFT', '1033808554 NEFTINW-004971119', 'Amarjeet', 'Flat_307', '0', '0'), (3, 11678, '2016-09-01 00:00:00', 'Credit', 'IB', '', 'divang', 'Flat_309', '0', '0'), (4, 3750, '2016-09-01 00:00:00', 'Credit', 'IB', '', 'divang', 'Flat_309', '0', '0'), (5, 3200, '2016-08-31 00:00:00', 'Credit', 'NEFT', '1032230414 NEFTINW-0046792155', 'Dhiman', 'Flat_304', '0', '0'), (6, 50000, '2016-08-31 00:00:00', 'Debit', 'CLG', '250', 'Sundarali', 'Flat_204', '0', '0'), (7, 3000, '2016-08-31 00:00:00', 'Credit', 'NEFT', '1031786468 NEFTINW-0046691849', 'Krishna', 'Flat_004', '0', '0'), (8, 3200, '2016-08-31 00:00:00', 'Credit', 'NEFT', 'CITIN681863291 NEFTINW-0046676804', 'vishesh', 'Flat_208', '0', '0'), (9, 3470, '2016-08-29 00:00:00', 'Credit', 'NEFT', 'N2421601571377 NEFTINW-0046528812', 'Raghunandan', 'Flat_009', '0', '0'), (10, 3710, '2016-08-29 00:00:00', 'Credit', 'NEFT', 'CITIN16680570890 NEFTINW-0046498770', 'jay', 'Flat_201', '0', '0'), (11, 5000, '2016-08-19 00:00:00', 'Credit', 'NEFT', 'SBIN616232761130 NEFTINW-0046087397', 'jay', 'Flat_201', '0', '0'), (12, 3100, '2016-08-16 00:00:00', 'Credit', 'NEFT', '1023581870 NEFTINW-0045853591', 'Ashutosh', 'Flat_308', '0', '0'), (13, 3000, '2016-08-10 00:00:00', 'Credit', 'IMPS', '622319580518 IMPS-622319168490', 'Krishna', 'Flat_004', '0', '0'), (14, 3600, '2016-08-10 00:00:00', 'Credit', 'NEFT', '1021414712 NEFTINW-00699447', 'viveksharma', 'Flat_007', '0', '0'), (15, 6560, '2016-08-10 00:00:00', 'Credit', 'MB', 'MB-999970704298', 'abhinav', 'Flat_306', '0', '0'), (16, 3200, '2016-08-10 00:00:00', 'Credit', 'NEFT', '1020897595 NEFTINW-0045632024', 'Niteen', 'Flat_104', '0', '0'), (17, 4000, '2016-08-09 00:00:00', 'Credit', 'NEFT', '1020386156 NEFTINW-0045584316', 'Shashi', 'Flat_103', '0', '0'), (18, 10000, '2016-08-09 00:00:00', 'Credit', 'NEFT', 'AXMB162228830545 NEFTINW-0045572445', 'Dhiman', 'Flat_304', '0', '0'), (19, 4000, '2016-08-05 00:00:00', 'Credit', 'IMPS', '621813079159 IMPS-621813540260', 'Gopa', 'Flat_005', '0', '0'), (20, 3710, '2016-08-05 00:00:00', 'Credit', 'NEFT', 'CITIN16674423130 NEFTINW-0045374063', 'manoj', 'Flat_101', '0', '0'), (21, 3370, '2016-08-05 00:00:00', 'Credit', 'NEFT', 'CITIN16674414482 NEFTINW-0045374223', 'maheshwar', 'Flat_206', '0', '0'), (22, 3000, '2016-08-02 00:00:00', 'Credit', 'IMPS', '621518927613 IMPS-621518211322', 'Krishna', 'Flat_004', '0', '0'), (23, 4000, '2016-08-02 00:00:00', 'Credit', 'IB', '', 'Manendra', 'Flat_105', '0', '0'), (24, 5000, '2016-08-02 00:00:00', 'Credit', 'NEFT', 'CITIN163193850 NEFTINW-0045187845', 'jay', 'Flat_201', '0', '0'), (25, 3500, '2016-08-02 00:00:00', 'Credit', 'NEFT', '1015182808 NEFTINW-0045193099', 'Amarjeet', 'Flat_307', '0', '0'), (26, 3200, '2016-08-02 00:00:00', 'Credit', 'NEFT', 'CITIN16673180822 NEFTINW-0045187664', 'vishesh', 'Flat_208', '0', '0'), (27, 3000, '2016-08-01 00:00:00', 'Credit', 'NEFT', 'N214160174065349 NEFTINW-0045125170', 'manas', 'Flat_001', '0', '0'), (28, 11678, '2016-08-01 00:00:00', 'Credit', 'IB', '', 'divang', 'Flat_309', '0', '0'), (29, 3750, '2016-08-01 00:00:00', 'Credit', 'IB', '', 'divang', 'Flat_309', '0', '0'), (30, 3440, '2016-08-01 00:00:00', 'Credit', 'NEFT', '000002174193 NEFTINW-0045101066', 'Vinod', 'Flat_107', '0', '0'), (31, 3470, '2016-08-01 00:00:00', 'Credit', 'NEFT', '000002172083 NEFTINW-0045095372', 'Raghunandan', 'Flat_009', '0', '0'), (32, 3200, '2016-08-01 00:00:00', 'Credit', 'NEFT', '1014108518 NEFTINW-0045093266', 'Dhiman', 'Flat_304', '0', '0');
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
INSERT IGNORE INTO `User_Details` (`User_Id`, `Login_Id`, `User_Type`, `Status`, `Flat_Id`, `Name`, `Name_Alias`, `Mobile_No`, `Moble_No_Alternate`, `Email_Id`, `Address`, `Flat_Join_Date`, `Flat_Left_Date`) VALUES ('abhinav', 'abhinav1', '', 1, 'Flat_306', 'Abhinav Nigam', 'Abhinav,ABHINAV  NIGAM', NULL, NULL, NULL, NULL, NULL, NULL), ('Amarjeet', 'Amarjeet1', '', 1, 'Flat_307', 'Amarjeet Kumar', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Anshuman', 'Anshuman1', '', 1, 'Flat_207', 'Anshuman', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Ashutosh', 'Ashutosh1', '', 1, 'Flat_308', 'Ashutosh', 'ASHUSH MOHANTY,ASHUTOSH MOHANTY', NULL, NULL, NULL, NULL, NULL, NULL), ('Balaji', 'Balaji1', '', 1, 'Flat_205', 'Balaji Ganapathi', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Dhiman', 'Dhiman1', '', 1, 'Flat_304', 'Dhiman', 'SUMITH VARGHESE,DHIMAN CHAKRABORTY', NULL, NULL, NULL, NULL, NULL, NULL), ('divang', 'divang1', '', 1, 'Flat_309', 'Divang Sharma', 'divang,DIVANG  SHARMA  ', 9241797239, NULL, 'divang.s@gmail.com', 'Begur Heights', NULL, NULL), ('Gautam', 'Gautam1', '', 1, 'Flat_305', 'Gautam Kumar', 'GAUTAM  KUMAR  ', NULL, NULL, NULL, NULL, NULL, NULL), ('Gopa', 'Gopa1', '', 1, 'Flat_005', 'Gopa Kumar', 'GOPAKUMAR T,GOVULA MADHURI', NULL, NULL, NULL, NULL, NULL, NULL), ('Ivin', 'Ivin1', '', 1, 'Flat_106', 'Ivin Sebastian', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('jay', 'jay1', '', 1, 'Flat_201', ' Jay Krishan \r\n', 'JAYAKRISHNAN A,MR JAYAPRAKASH H P C O CG', NULL, NULL, NULL, NULL, NULL, NULL), ('Jaya', 'Jaya1', '', 1, 'Flat_109', 'Jaya Prakash', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Karthik', 'Karthik1', '', 1, 'Flat_002', 'Karthik', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Krishna', 'Krishna1', '', 1, 'Flat_004', 'Krishna Murthy', 'S KRISHNA M,S KRISHNA MOORTHI', NULL, NULL, NULL, NULL, NULL, NULL), ('Krishnan', 'Krishnan1', '', 1, 'Flat_006', 'Krishnan', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Mahesh', 'Mahesh1', '', 1, 'Flat_209', 'Mahesh Suragimath', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('maheshwar', 'maheshwar1', '', 1, 'Flat_206', 'Maheshwar Mohanty', 'MAHESWAR MOHANTY,SL. NO. MAHESWAR MOHANTY', NULL, NULL, NULL, NULL, NULL, NULL), ('manas', 'Manas1', '', 1, 'Flat_001', ' Manas Dash ', 'MANAS KUMAR DASH', NULL, NULL, NULL, NULL, NULL, NULL), ('Manendra', 'Manendra1', '', 1, 'Flat_105', 'Manendra Prasad  Singh', 'MANENA PRASAD SINGH,MANENDRA PRASAD SINGH', NULL, NULL, NULL, NULL, NULL, NULL), ('manoj', 'manoj1', '', 1, 'Flat_101', 'Manoj Nair', 'MANOJ K NAI ,MANOJ K NAIR', NULL, NULL, NULL, NULL, NULL, NULL), ('Niteen', 'Niteen1', '', 1, 'Flat_104', 'Niteen Kole', 'NITEEN UTTA,NITEEN UTTAM KOLE', NULL, NULL, NULL, NULL, NULL, NULL), ('Phani', 'Phani1', '', 1, 'Flat_102', 'Phani Krishna', 'PHANI KRISH ,PHANI KRISH ,PHANI KRISHNA NARAYANAM', NULL, NULL, NULL, NULL, NULL, NULL), ('Philip', 'Philip1', '', 1, 'Flat_203', 'Philip George', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Praveen', 'Praveen1', '', 1, 'Flat_008', 'Praveen Pattanshetti', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Raghunandan', 'Raghunandan1', '', 1, 'Flat_009', 'Raghunandan', 'RAGHUNANDAN HK,RAGHUNANDAN H K', NULL, NULL, NULL, NULL, NULL, NULL), ('Raj', 'Raj1', '', 1, 'Flat_108', 'Raj kumar Mandal', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('ramesh', 'ramesh1', '', 1, 'Flat_007', 'Ramesh Gangan', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Sanjib', 'Sanjib1', '', 1, 'Flat_303', 'Sanjib Singh', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Satheesh', 'Satheesh1', '', 1, 'Flat_302', 'Satheesh S', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Shashi', 'Shashi1', '', 1, 'Flat_103', 'Shashi Prakash Krishna', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Sidda', 'Sidda1', '', 1, 'Flat_003', 'Sidda Raju', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Subhash', 'Subhash1', '', 1, 'Flat_202', 'Subhash Chandra Gupta', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Sundarali', 'Sundarali1', '', 1, 'Flat_204', 'M. Sundaralingam', 'SUNDARALINGAM MURUGATHITHAN', NULL, NULL, NULL, NULL, NULL, NULL), ('Vinod', 'Vinod1', '', 1, 'Flat_107', 'A Vinod Kumar', 'VINOD KUMAR A', NULL, NULL, NULL, NULL, NULL, NULL), ('vinoy ', 'vinoy1', '', 1, 'Flat_301', ' Vinoy ', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('vishesh', 'vishesh1', '', 1, 'Flat_208', 'Vishesh Nigam', 'vishesh,SHRUTI NIGAM', NULL, NULL, NULL, NULL, NULL, NULL), ('viveksharma', 'vivek1', 'Tenant', 0, 'Flat_007', 'vivek sharma', 'vivek sharma', 980000, 1111, 's@s.com', NULL, '2016-09-04 00:00:00', '2016-09-04 00:00:00');
/*!40000 ALTER TABLE `User_Details` ENABLE KEYS */;


# Dumping structure for table sql6134070.User_Paid
DROP TABLE IF EXISTS `User_Paid`;
CREATE TABLE IF NOT EXISTS `User_Paid` (
  `Payment_ID` bigint(50) NOT NULL AUTO_INCREMENT,
  `User_ID` varchar(20) NOT NULL,
  `Flat_ID` varchar(10) NOT NULL,
  `Amount` double NOT NULL,
  `Paid_Date` datetime NOT NULL,
  `Expense_Type` varchar(50) NOT NULL,
  `Varified` tinyint(1) NOT NULL,
  `Varified_By` varchar(20) NOT NULL,
  `User_Comment` varchar(200) NOT NULL,
  `Admin_Comment` varchar(200) NOT NULL,
  PRIMARY KEY (`Payment_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table sql6134070.User_Paid: 36 rows
/*!40000 ALTER TABLE `User_Paid` DISABLE KEYS */;
INSERT IGNORE INTO `User_Paid` (`Payment_ID`, `User_ID`, `Flat_ID`, `Amount`, `Paid_Date`, `Expense_Type`, `Varified`, `Varified_By`, `User_Comment`, `Admin_Comment`) VALUES (1, 'manas', 'Flat_001', 239885, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (2, 'manoj', 'Flat_101', 273467, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (3, 'jay', 'Flat_201', 267692, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (4, 'vinoy ', 'Flat_301', 273181, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (5, 'Karthik', 'Flat_002', 186227, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (6, 'Phani', 'Flat_102', 236677, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (7, 'Subhash', 'Flat_202', 20319, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (8, 'Satheesh', 'Flat_302', 239760, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (9, 'Sidda', 'Flat_003', 152000, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (10, 'Shashi', 'Flat_103', 238138, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (11, 'Philip', 'Flat_203', 220556, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (12, 'Sanjib', 'Flat_303', 56458, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (13, 'Krishna', 'Flat_004', 216853, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (14, 'Niteen', 'Flat_104', 241743, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (15, 'Sundarali', 'Flat_204', 241715, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (16, 'Dhiman', 'Flat_304', 242043, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (17, 'Gopa', 'Flat_005', 260200, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (18, 'Manendra', 'Flat_105', 281180, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (19, 'Balaji', 'Flat_205', 280590, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (20, 'Gautam', 'Flat_305', 291960, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (21, 'Krishnan', 'Flat_006', 232624, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (22, 'Ivin', 'Flat_106', 245190, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (23, 'maheshwar', 'Flat_206', 252230, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (24, 'abhinav', 'Flat_306', 248540, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (25, 'Vinod', 'Flat_107', 256175, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (26, 'Anshuman', 'Flat_207', 137725, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (27, 'Amarjeet', 'Flat_307', 255975, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (28, 'Praveen', 'Flat_008', 232004, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (29, 'Raj', 'Flat_108', 236701, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (30, 'vishesh', 'Flat_208', 234399, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (31, 'Ashutosh', 'Flat_308', 234981, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (32, 'Raghunandan', 'Flat_009', 270317, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (33, 'Jaya', 'Flat_109', 272855, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (34, 'Mahesh', 'Flat_209', 256801, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (35, 'divang', 'Flat_309', 222565, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data'), (162, 'ramesh', 'Flat_007', 221400, '2016-07-15 00:00:00', 'Initial_Payable', 1, 'vishesh', '', 'July 2016 BHOWA XLS Receivable data');
/*!40000 ALTER TABLE `User_Paid` ENABLE KEYS */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;