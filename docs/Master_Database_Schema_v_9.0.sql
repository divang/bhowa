# --------------------------------------------------------
# Host:                         sql6.freemysqlhosting.net
# Database:                     sql6134070
# Server version:               5.5.49-0ubuntu0.14.04.1
# Server OS:                    debian-linux-gnu
# HeidiSQL version:             5.0.0.3031
# Date/time:                    2016-12-25 17:28:10
# --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
# Dumping database structure for sql6134070
CREATE DATABASE IF NOT EXISTS `sql6134070` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `sql6134070`;


# Dumping structure for table sql6134070.Apartment_Expense
CREATE TABLE IF NOT EXISTS `Apartment_Expense` (
  `Apartment_Cash_Expense_ID` int(10) NOT NULL,
  `Expense_Type_Id` int(10) NOT NULL,
  `Amount` float NOT NULL,
  `Expend_Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `Expend_By_UserId` varchar(50) NOT NULL,
  `Verified` bit(1) NOT NULL,
  `Verified_By` varchar(50) NOT NULL,
  `Expendy_Comment` varchar(200) NOT NULL,
  `Admin_Comment` varchar(200) NOT NULL,
  PRIMARY KEY (`Apartment_Cash_Expense_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6134070.Apartment_Expense


# Dumping structure for table sql6134070.Authorizations
CREATE TABLE IF NOT EXISTS `Authorizations` (
  `Auth_Id` int(10) NOT NULL,
  `Type` varchar(50) NOT NULL,
  PRIMARY KEY (`Type`),
  UNIQUE KEY `Auth_Id` (`Auth_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table sql6134070.Authorizations: 17 rows
/*!40000 ALTER TABLE `Authorizations` DISABLE KEYS */;
INSERT IGNORE INTO `Authorizations` (`Auth_Id`, `Type`) VALUES (0, 'ADMIN'), (1, 'MY_DUES_VIEWS'), (2, 'NOTIFICATION_SEND'), (3, 'USER_DETAIL_VIEW'), (4, 'USER_DETAIL_CREATE'), (5, 'FLAT_DETAIL_VIEW'), (6, 'FLAT_DETAIL_CREATE'), (7, 'LOGIN_VIEW'), (8, 'LOGIN_CREATE'), (9, 'TRANSACTION_HOME_VIEW'), (10, 'RAW_DATA_VIEW'), (11, 'TRANSACTIONS_DETAIL_VIEW'), (12, 'PDF_TRANSACTION_VIEW'), (13, 'PDF_TRANSACTION_UPLOAD_TO_STAGING_TABLE'), (14, 'MAP_USER_WITH_MONTHLY_PDF_NAME'), (15, 'MONTHLY_MAINTENANCE_GENERATOR'), (16, 'VERIFIED_PDF_TRANSACTIONS_UPLOAD');
/*!40000 ALTER TABLE `Authorizations` ENABLE KEYS */;


# Dumping structure for table sql6134070.Bank_Statement
CREATE TABLE IF NOT EXISTS `Bank_Statement` (
  `Bank_Statement_FileName` varchar(50) NOT NULL,
  `Uploaded_Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Uploaded_LoginId` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table sql6134070.Bank_Statement: 7 rows
/*!40000 ALTER TABLE `Bank_Statement` DISABLE KEYS */;
INSERT IGNORE INTO `Bank_Statement` (`Bank_Statement_FileName`, `Uploaded_Date`, `Uploaded_LoginId`) VALUES ('/storage/emulated/0/Download/Report-20161107103403', '2016-12-10 00:00:00', ''), ('/storage/emulated/0/Download/Report-20161107103403', '2016-12-10 00:00:00', ''), ('/storage/emulated/0/Download/Report-20161107103403', '2016-12-10 16:19:17', ''), ('/storage/emulated/0/Download/Report-20161107103403', '2016-12-10 16:30:10', ''), ('/storage/emulated/0/Download/Report-20161107103403', '2016-12-10 16:38:12', 'w'), ('/storage/emulated/0/Download/Report-20161107103403', '2016-12-25 01:43:40', 'w'), ('/storage/emulated/0/Download/Report-20161107103403', '2016-12-25 13:15:27', 'w'), ('/storage/emulated/0/Download/Report-20161107103403', '2016-12-25 16:27:17', 'w'), ('/storage/emulated/0/Download/Report-20161107103403', '2016-12-25 16:27:22', 'w'), ('/storage/emulated/0/Download/Report-20161107103403', '2016-12-25 16:47:47', 'w'), ('/storage/emulated/0/Download/Report-20161107103403', '2016-12-25 17:24:06', 'w');
/*!40000 ALTER TABLE `Bank_Statement` ENABLE KEYS */;


# Dumping structure for table sql6134070.Expense_Type
CREATE TABLE IF NOT EXISTS `Expense_Type` (
  `Expense_Type_Id` int(10) NOT NULL,
  `Type` varchar(50) NOT NULL,
  `Payable_Priority` int(10) DEFAULT NULL,
  PRIMARY KEY (`Expense_Type_Id`),
  UNIQUE KEY `Type` (`Type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table sql6134070.Expense_Type: 32 rows
/*!40000 ALTER TABLE `Expense_Type` DISABLE KEYS */;
INSERT IGNORE INTO `Expense_Type` (`Expense_Type_Id`, `Type`, `Payable_Priority`) VALUES (0, 'Advance_Payment', -1), (1, 'Monthly_Maintenance', 1), (2, 'Initial_Payable', 4), (3, 'Khata_Payable', 3), (4, 'Generator_Repair_Servicing', NULL), (5, 'Lift_AMC', NULL), (6, 'Lift_Repair\r\n', NULL), (7, 'Plumbing', NULL), (8, 'Electrical_Repair', NULL), (9, 'Tank_Cleaning_Repairing', NULL), (10, 'Lawyer', NULL), (11, 'Miscellaneous', NULL), (12, 'Intercom_AMC\r\n', NULL), (13, 'Intercom_Purchase_Repair', NULL), (14, 'Fire_Extinguisher', NULL), (15, 'Security_Related', NULL), (16, 'Apartment_InfraStructure_Repair\r\n', NULL), (17, 'Children_Park', NULL), (18, 'Septick_Tank_ Pipe_Cleaning', NULL), (19, 'Club_House', NULL), (20, 'Alamari_Purchase', NULL), (21, 'Security_Guards\r\n', NULL), (22, 'House_Keeping_Labour', NULL), (23, 'Common_Electricity', NULL), (24, 'Water_Tankers ', NULL), (25, 'Gardening', NULL), (26, 'Deisel_For_Generator\r\n', NULL), (27, 'House_Keeping_Consumables', NULL), (28, 'Pest_Control', NULL), (29, 'Generator_AMC', NULL), (30, 'Flat_Old_Dues', 2), (31, 'Waste_Disposal', NULL);
/*!40000 ALTER TABLE `Expense_Type` ENABLE KEYS */;


# Dumping structure for table sql6134070.Flat
CREATE TABLE IF NOT EXISTS `Flat` (
  `Flat_Id` varchar(10) NOT NULL,
  `Flat_Number` varchar(10) NOT NULL,
  `Area` int(10) NOT NULL,
  `Maintenance_Amount` float NOT NULL,
  `Block_Number` varchar(1) NOT NULL,
  `Status` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`Flat_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table sql6134070.Flat: 37 rows
/*!40000 ALTER TABLE `Flat` DISABLE KEYS */;
INSERT IGNORE INTO `Flat` (`Flat_Id`, `Flat_Number`, `Area`, `Maintenance_Amount`, `Block_Number`, `Status`) VALUES ('Flat_001', '001', 1367, 3250, 'A', 1), ('Flat_002', '002', 1302, 3150, 'A', 1), ('Flat_003', '003', 1293, 3140, 'A', 1), ('Flat_004', '004', 1190, 2990, 'A', 1), ('Flat_005', '005', 1840, 3960, 'B', 1), ('Flat_006', '006', 1365, 3250, 'B', 1), ('Flat_007', '007', 1211, 3020, 'B', 1), ('Flat_008', '008', 1200, 3000, 'B', 1), ('Flat_009', '009', 1645, 3670, 'B', 1), ('Flat_101', '101', 1673, 3710, 'A', 1), ('Flat_102', '102', 1302, 3150, 'A', 1), ('Flat_103', '103', 1293, 3140, 'A', 1), ('Flat_104', '104', 1334, 3200, 'A', 1), ('Flat_105', '105', 1840, 3960, 'B', 1), ('Flat_106', '106', 1448, 3370, 'B', 1), ('Flat_107', '107', 1496, 3440, 'B', 1), ('Flat_108', '108', 1310, 3170, 'B', 1), ('Flat_109', '109', 1699, 3750, 'B', 1), ('Flat_201', '201', 1673, 3710, 'A', 1), ('Flat_202', '202', 1302, 3150, 'A', 1), ('Flat_203', '203', 1293, 3140, 'A', 1), ('Flat_204', '204', 1334, 3200, 'A', 1), ('Flat_205', '205', 1840, 3960, 'B', 1), ('Flat_206', '206', 1448, 3370, 'B', 1), ('Flat_207', '207', 1496, 3440, 'B', 1), ('Flat_208', '208', 1310, 3170, 'B', 1), ('Flat_209', '209', 1699, 3750, 'B', 1), ('Flat_301', '301', 1673, 3710, 'A', 1), ('Flat_302', '302', 1302, 3150, 'A', 1), ('Flat_303', '303', 1293, 3140, 'A', 1), ('Flat_304', '304', 1334, 3200, 'A', 1), ('Flat_305', '305', 1840, 3960, 'B', 1), ('Flat_306', '306', 1448, 3370, 'B', 1), ('Flat_307', '307', 1496, 3440, 'B', 1), ('Flat_308', '308', 1310, 3170, 'B', 1), ('Flat_309', '309', 1699, 3750, 'B', 1), ('Flat_310', '310', 1800, 4000, 'B', 1);
/*!40000 ALTER TABLE `Flat` ENABLE KEYS */;


# Dumping structure for table sql6134070.Flat_Wise_Payable
CREATE TABLE IF NOT EXISTS `Flat_Wise_Payable` (
  `Flat_Wise_Payable_ID` int(10) NOT NULL AUTO_INCREMENT,
  `Flat_Id` varchar(10) NOT NULL,
  `Status` int(1) NOT NULL DEFAULT '1',
  `Month` int(2) NOT NULL,
  `Year` int(4) NOT NULL,
  `Amount` float NOT NULL,
  `Expense_Type_Id` int(10) NOT NULL,
  `Comments` varchar(50) NOT NULL,
  `Payment_Status_ID` int(10) NOT NULL DEFAULT '1',
  PRIMARY KEY (`Flat_Wise_Payable_ID`),
  UNIQUE KEY `Flat_Id_Month_Year_Expense_Type` (`Flat_Id`,`Month`,`Year`,`Expense_Type_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table sql6134070.Flat_Wise_Payable: 56 rows
/*!40000 ALTER TABLE `Flat_Wise_Payable` DISABLE KEYS */;
INSERT IGNORE INTO `Flat_Wise_Payable` (`Flat_Wise_Payable_ID`, `Flat_Id`, `Status`, `Month`, `Year`, `Amount`, `Expense_Type_Id`, `Comments`, `Payment_Status_ID`) VALUES (732, 'Flat_001', 1, 1, 2016, 3000, 5, '', 2), (733, 'Flat_002', 1, 1, 2016, 3000, 5, '', 1), (734, 'Flat_003', 1, 1, 2016, 3000, 5, '', 1), (735, 'Flat_004', 1, 1, 2016, 3000, 5, '', 2), (736, 'Flat_005', 1, 1, 2016, 3000, 5, '', 2), (737, 'Flat_006', 1, 1, 2016, 3000, 5, '', 1), (738, 'Flat_007', 1, 1, 2016, 3000, 5, '', 1), (739, 'Flat_008', 1, 1, 2016, 3000, 5, '', 1), (740, 'Flat_009', 1, 1, 2016, 3000, 5, '', 2), (741, 'Flat_101', 1, 1, 2016, 3000, 5, '', 2), (742, 'Flat_102', 1, 1, 2016, 3000, 5, '', 2), (743, 'Flat_103', 1, 1, 2016, 3000, 5, '', 2), (744, 'Flat_104', 1, 1, 2016, 3000, 5, '', 2), (745, 'Flat_105', 1, 1, 2016, 3000, 5, '', 2), (746, 'Flat_106', 1, 1, 2016, 3000, 5, '', 2), (747, 'Flat_107', 1, 1, 2016, 3000, 5, '', 2), (748, 'Flat_108', 1, 1, 2016, 3000, 5, '', 1), (749, 'Flat_109', 1, 1, 2016, 3000, 5, '', 1), (750, 'Flat_201', 1, 1, 2016, 3000, 5, '', 2), (751, 'Flat_202', 1, 1, 2016, 3000, 5, '', 1), (752, 'Flat_203', 1, 1, 2016, 3000, 5, '', 1), (753, 'Flat_204', 1, 1, 2016, 3000, 5, '', 1), (754, 'Flat_205', 1, 1, 2016, 3000, 5, '', 1), (755, 'Flat_206', 1, 1, 2016, 3000, 5, '', 1), (756, 'Flat_207', 1, 1, 2016, 3000, 5, '', 1), (757, 'Flat_208', 1, 1, 2016, 3000, 5, '', 2), (758, 'Flat_209', 1, 1, 2016, 3000, 5, '', 1), (759, 'Flat_301', 1, 1, 2016, 3000, 5, '', 1), (760, 'Flat_302', 1, 1, 2016, 3000, 5, '', 1), (761, 'Flat_303', 1, 1, 2016, 3000, 5, '', 1), (762, 'Flat_304', 1, 1, 2016, 3000, 5, '', 1), (763, 'Flat_305', 1, 1, 2016, 3000, 5, '', 2), (764, 'Flat_306', 1, 1, 2016, 3000, 5, '', 2), (765, 'Flat_307', 1, 1, 2016, 3000, 5, '', 2), (766, 'Flat_308', 1, 1, 2016, 3000, 5, '', 2), (767, 'Flat_309', 1, 1, 2016, 3000, 5, '', 2), (768, 'Flat_310', 1, 1, 2016, 3000, 5, '', 1), (795, 'Flat_001', 1, 2, 2016, 1000, 4, '', 2), (796, 'Flat_002', 1, 2, 2016, 1000, 4, '', 1), (797, 'Flat_003', 1, 2, 2016, 1000, 4, '', 1), (798, 'Flat_004', 1, 2, 2016, 1000, 4, '', 1), (799, 'Flat_005', 1, 2, 2016, 1000, 4, '', 2), (800, 'Flat_006', 1, 2, 2016, 1000, 4, '', 1), (801, 'Flat_007', 1, 2, 2016, 1000, 4, '', 1), (802, 'Flat_008', 1, 2, 2016, 1000, 4, '', 1), (803, 'Flat_009', 1, 2, 2016, 1000, 4, '', 3), (804, 'Flat_101', 1, 2, 2016, 1000, 4, '', 2), (805, 'Flat_102', 1, 2, 2016, 1000, 4, '', 3), (806, 'Flat_103', 1, 2, 2016, 1000, 4, '', 2), (807, 'Flat_104', 1, 2, 2016, 1000, 4, '', 3), (808, 'Flat_105', 1, 2, 2016, 1000, 4, '', 2), (809, 'Flat_106', 1, 2, 2016, 1000, 4, '', 2), (810, 'Flat_107', 1, 2, 2016, 1000, 4, '', 2), (811, 'Flat_108', 1, 2, 2016, 1000, 4, '', 1), (812, 'Flat_109', 1, 2, 2016, 1000, 4, '', 1), (813, 'Flat_201', 1, 2, 2016, 1000, 4, '', 2), (814, 'Flat_202', 1, 2, 2016, 1000, 4, '', 1), (815, 'Flat_203', 1, 2, 2016, 1000, 4, '', 1), (816, 'Flat_204', 1, 2, 2016, 1000, 4, '', 1), (817, 'Flat_205', 1, 2, 2016, 1000, 4, '', 1), (818, 'Flat_206', 1, 2, 2016, 1000, 4, '', 1), (819, 'Flat_207', 1, 2, 2016, 1000, 4, '', 1), (820, 'Flat_208', 1, 2, 2016, 1000, 4, '', 3), (821, 'Flat_209', 1, 2, 2016, 1000, 4, '', 1), (822, 'Flat_301', 1, 2, 2016, 1000, 4, '', 1), (823, 'Flat_302', 1, 2, 2016, 1000, 4, '', 1), (824, 'Flat_303', 1, 2, 2016, 1000, 4, '', 1), (825, 'Flat_304', 1, 2, 2016, 1000, 4, '', 1), (826, 'Flat_305', 1, 2, 2016, 1000, 4, '', 2), (827, 'Flat_306', 1, 2, 2016, 1000, 4, '', 2), (828, 'Flat_307', 1, 2, 2016, 1000, 4, '', 2), (829, 'Flat_308', 1, 2, 2016, 1000, 4, '', 3), (830, 'Flat_309', 1, 2, 2016, 1000, 4, '', 2), (831, 'Flat_310', 1, 2, 2016, 1000, 4, '', 1);
/*!40000 ALTER TABLE `Flat_Wise_Payable` ENABLE KEYS */;


# Dumping structure for table sql6134070.Login
CREATE TABLE IF NOT EXISTS `Login` (
  `Login_Id` varchar(20) NOT NULL,
  `Password` varchar(20) NOT NULL,
  `Status` tinyint(1) NOT NULL DEFAULT '1',
  `Society_Id` int(100) DEFAULT NULL,
  PRIMARY KEY (`Login_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table sql6134070.Login: 41 rows
/*!40000 ALTER TABLE `Login` DISABLE KEYS */;
INSERT IGNORE INTO `Login` (`Login_Id`, `Password`, `Status`, `Society_Id`) VALUES ('abhinav1', '1', 1, 1), ('Amarjeet1', '1', 1, 1), ('Anshuman1', '1', 1, 1), ('Ashutosh1', '1', 1, 1), ('Balaji1', '1', 1, 1), ('Biju1', '123456', 1, 1), ('Dhiman1', '1', 1, 1), ('divang1', '1', 1, 1), ('Gautam1', '1', 1, 1), ('Gopa1', '1', 1, 1), ('Ivin1', '1', 1, 1), ('jay1', '1', 1, 1), ('Jaya1', '1', 1, 1), ('Karthik1', '1', 1, 1), ('Krishna1', '1', 1, 1), ('Krishnan1', '1', 1, 1), ('Mahesh1', '1', 1, 1), ('maheshwar1', '1', 1, 1), ('Manas1', '1', 1, 1), ('Manendra1', '1', 1, 1), ('manoj1', '1', 1, 1), ('Niteen1', '1', 1, 1), ('Phani1', '1', 1, 1), ('Philip1', '1', 1, 1), ('poonam', '1', 1, 1), ('Praveen1', '1', 1, 1), ('Raghunandan1', '1', 1, 1), ('Raj1', '1', 1, 1), ('ramesh1', '1', 1, 1), ('Sanjib1', '1', 1, 1), ('Satheesh1', '1', 1, 1), ('Shashi1', '1', 1, 1), ('Sidda1', '1', 1, 1), ('Subhash1', '1', 1, 1), ('sudha', '1', 1, 1), ('Sundarali1', '1', 1, 1), ('Vinod1', '1', 1, 1), ('vinoy1', '1', 1, 1), ('vishesh1', '1', 1, 1), ('vivek1', '1', 1, 1), ('w', '1', 1, 1);
/*!40000 ALTER TABLE `Login` ENABLE KEYS */;


# Dumping structure for table sql6134070.Notification
CREATE TABLE IF NOT EXISTS `Notification` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `Message` varchar(100) DEFAULT '0',
  `Time` datetime DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6134070.Notification


# Dumping structure for table sql6134070.Society
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

# Dumping data for table sql6134070.Society: 1 rows
/*!40000 ALTER TABLE `Society` DISABLE KEYS */;
INSERT IGNORE INTO `Society` (`Society_Id`, `Society_Name`, `Database_URL`, `Database_User`, `Database_Password`, `Email_Id`) VALUES (1, 'BHOWA', 'jdbc:mysql://sql6.freemysqlhosting.net:3306/sql6134070', 'sql6134070', 'UAgCjcJ2d4', 'bhowa.begurwoods@gmail.com');
/*!40000 ALTER TABLE `Society` ENABLE KEYS */;


# Dumping structure for table sql6134070.Transactions_BalanceSheet
CREATE TABLE IF NOT EXISTS `Transactions_BalanceSheet` (
  `Balance_Sheet_Transaction_ID` int(10) NOT NULL AUTO_INCREMENT,
  `Amount` float NOT NULL,
  `Verified_By_Admin` varchar(20) NOT NULL,
  `Verified_By_User` varchar(20) NOT NULL,
  `Expense_Type_Id` int(10) NOT NULL,
  `Transaction_From_Bank_Statement_ID` int(20) DEFAULT NULL,
  `User_Cash_Payment_ID` bigint(10) DEFAULT NULL,
  `Transaction_Expense_Id` int(10) DEFAULT NULL,
  `Transaction_Flow` varchar(20) NOT NULL,
  `Flat_Wise_Payable_ID` int(10) NOT NULL,
  PRIMARY KEY (`Balance_Sheet_Transaction_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table sql6134070.Transactions_BalanceSheet: 80 rows
/*!40000 ALTER TABLE `Transactions_BalanceSheet` DISABLE KEYS */;
INSERT IGNORE INTO `Transactions_BalanceSheet` (`Balance_Sheet_Transaction_ID`, `Amount`, `Verified_By_Admin`, `Verified_By_User`, `Expense_Type_Id`, `Transaction_From_Bank_Statement_ID`, `User_Cash_Payment_ID`, `Transaction_Expense_Id`, `Transaction_Flow`, `Flat_Wise_Payable_ID`) VALUES (402, 3000, '0', '0', 5, 508, 0, 0, 'Credit', 767), (403, 3000, '0', '0', 5, 518, 0, 0, 'Credit', 766), (404, 3000, '0', '0', 5, 510, 0, 0, 'Credit', 765), (405, 3000, '0', '0', 5, 506, 0, 0, 'Credit', 764), (406, 3000, '0', '0', 5, 522, 0, 0, 'Credit', 763), (407, 3000, '0', '0', 5, 527, 0, 0, 'Credit', 757), (408, 3000, '0', '0', 5, 512, 0, 0, 'Credit', 750), (409, 3000, '0', '0', 5, 505, 0, 0, 'Credit', 747), (410, 3000, '0', '0', 5, 507, 0, 0, 'Credit', 746), (411, 3000, '0', '0', 5, 511, 0, 0, 'Credit', 745), (412, 3000, '0', '0', 5, 519, 0, 0, 'Credit', 744), (413, 3000, '0', '0', 5, 517, 0, 0, 'Credit', 743), (414, 3000, '0', '0', 5, 504, 0, 0, 'Credit', 742), (415, 3000, '0', '0', 5, 503, 0, 0, 'Credit', 741), (416, 3000, '0', '0', 5, 513, 0, 0, 'Credit', 740), (417, 3000, '0', '0', 5, 502, 0, 0, 'Credit', 736), (418, 3000, '0', '0', 5, 515, 0, 0, 'Credit', 735), (419, 3000, '0', '0', 5, 516, 0, 0, 'Credit', 732), (420, 1000, '1', '0', 4, 502, 0, 0, 'Credit', 799), (421, 710, '1', '0', 4, 503, 0, 0, 'Credit', 804), (422, 200, '1', '0', 4, 504, 0, 0, 'Credit', 805), (423, 440, '1', '0', 4, 505, 0, 0, 'Credit', 810), (424, 9080, '1', '0', 0, 506, 0, 0, 'Credit', 0), (425, 6000, '1', '0', 0, 507, 0, 0, 'Credit', 0), (426, 750, '1', '0', 4, 508, 0, 0, 'Credit', 830), (427, 2200, '1', '0', 0, 509, 0, 0, 'Credit', 0), (428, 500, '1', '0', 4, 510, 0, 0, 'Credit', 828), (429, 1000, '1', '0', 4, 511, 0, 0, 'Credit', 808), (430, 710, '1', '0', 4, 512, 0, 0, 'Credit', 813), (431, 470, '1', '0', 4, 513, 0, 0, 'Credit', 803), (432, 3710, '1', '0', 0, 514, 0, 0, 'Credit', 0), (433, 1000, '1', '0', 0, 517, 0, 0, 'Credit', 0), (434, 100, '1', '0', 4, 518, 0, 0, 'Credit', 829), (435, 200, '1', '0', 4, 519, 0, 0, 'Credit', 807), (436, 3420, '1', '0', 0, 520, 0, 0, 'Credit', 0), (437, 25954, '1', '0', 0, 521, 0, 0, 'Credit', 0), (438, 1000, '1', '0', 4, 522, 0, 0, 'Credit', 826), (439, 3500, '1', '0', 0, 524, 0, 0, 'Credit', 0), (440, 4000, '1', '0', 0, 525, 0, 0, 'Credit', 0), (441, 2880, '1', '0', 0, 526, 0, 0, 'Credit', 0), (442, 200, '1', '0', 4, 527, 0, 0, 'Credit', 820), (443, 3000, '1', '0', 0, 528, 0, 0, 'Credit', 0), (444, 4000, '1', '0', 0, 529, 0, 0, 'Credit', 0), (445, 250, '0', '0', 4, 524, 0, 0, 'Credit', 830), (446, 500, '0', '0', 4, 528, 0, 0, 'Credit', 828), (447, 1000, '0', '0', 4, 506, 0, 0, 'Credit', 827), (448, 290, '0', '0', 4, 514, 0, 0, 'Credit', 813), (449, 560, '0', '0', 4, 526, 0, 0, 'Credit', 810), (450, 1000, '0', '0', 4, 507, 0, 0, 'Credit', 809), (451, 1000, '0', '0', 4, 517, 0, 0, 'Credit', 806), (452, 290, '0', '0', 4, 520, 0, 0, 'Credit', 804), (453, 1000, '0', '0', 4, 509, 0, 0, 'Credit', 795);
/*!40000 ALTER TABLE `Transactions_BalanceSheet` ENABLE KEYS */;


# Dumping structure for table sql6134070.Transactions_Staging_Data
CREATE TABLE IF NOT EXISTS `Transactions_Staging_Data` (
  `Transaction_ID` int(10) NOT NULL AUTO_INCREMENT,
  `StatementID` int(10) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Amount` float NOT NULL,
  `Transaction_Date` datetime NOT NULL,
  `Transaction_Flow` varchar(20) NOT NULL,
  `Transaction_Mode` varchar(20) DEFAULT NULL,
  `Transaction_Reference` varchar(70) DEFAULT NULL,
  `Upload_Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `Uploaded_LoginId` varchar(50) NOT NULL,
  PRIMARY KEY (`Transaction_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table sql6134070.Transactions_Staging_Data: 77 rows
/*!40000 ALTER TABLE `Transactions_Staging_Data` DISABLE KEYS */;
INSERT IGNORE INTO `Transactions_Staging_Data` (`Transaction_ID`, `StatementID`, `Name`, `Amount`, `Transaction_Date`, `Transaction_Flow`, `Transaction_Mode`, `Transaction_Reference`, `Upload_Date`, `Uploaded_LoginId`) VALUES (741, 5, 'DANIEL L', 15980, '2016-11-05 00:00:00', 'Debit', 'CLG', '27', '2016-12-25 17:22:45', 'w'), (742, 6, 'GOPAKUMAR', 4000, '2016-11-05 00:00:00', 'Credit', 'IMPS', '3101131809 IMPS-3101181738', '2016-12-25 17:22:45', 'w'), (743, 7, 'MANOJ K NAIR', 3710, '2016-11-05 00:00:00', 'Credit', 'NEFT', 'CITIN1606654521 NEFTINW-005096833', '2016-12-25 17:22:45', 'w'), (744, 8, 'PHANI KRISHNA NARAYANAM', 3200, '2016-11-04 00:00:00', 'Credit', 'NEFT', 'CITIN16706434669 NEFTINW-0050946254', '2016-12-25 17:22:45', 'w'), (745, 9, 'VINOD KUMAR A', 3440, '2016-11-04 00:00:00', 'Credit', 'NEFT', 'N30160203228013 NEFTINW-005026201', '2016-12-25 17:22:45', 'w'), (746, 10, 'GUARDIAN SOUHARDA SAHAKA', 23100, '2016-11-04 00:00:00', 'Debit', 'CLG', '258', '2016-12-25 17:22:45', 'w'), (747, 13, 'ABHINAV NIGAM', 13080, '2016-11-03 00:00:00', 'Credit', 'MB', 'MB-999963937489', '2016-12-25 17:22:45', 'w'), (748, 14, 'ADITYA BHAMIDI', 3750, '2016-11-03 00:00:00', 'Credit', 'NEFT', 'N3081602029346 NEFTINW-00508810', '2016-12-25 17:22:45', 'w'), (749, 15, 'IVIN SEBASTIAN', 10000, '2016-11-03 00:00:00', 'Credit', 'NEFT', 'AXIR163086594824 NEFTINW-0050867969', '2016-12-25 17:22:45', 'w'), (750, 18, 'DIVANG SHARMA', 3750, '2016-11-02 00:00:00', 'Credit', 'IB', '', '2016-12-25 17:22:45', 'w'), (751, 19, 'MANAS KUMAR DASH', 3200, '2016-11-02 00:00:00', 'Credit', 'NEFT', 'N307160202331660 NEFTINW-0050786163', '2016-12-25 17:22:45', 'w'), (752, 20, 'AMARJEET KUMAR', 3500, '2016-11-02 00:00:00', 'Credit', 'NEFT', '1071686558 NEFTINW-0050763147', '2016-12-25 17:22:45', 'w'), (753, 21, 'MANENDRA PRASAD SINGH', 4000, '2016-11-02 00:00:00', 'Credit', 'IB', '', '2016-12-25 17:22:45', 'w'), (754, 25, 'JAYAKRISHNAN A', 3710, '2016-10-28 00:00:00', 'Credit', 'NEFT', 'CITIN16703010102 NEFTINW-0050390455', '2016-12-25 17:22:45', 'w'), (755, 32, 'RAGHUNANDAN H K', 3470, '2016-10-24 00:00:00', 'Credit', 'NEFT', 'N298160199150337 NEFTINW-0050108227', '2016-12-25 17:22:45', 'w'), (756, 33, 'MR JAYAPRAKASH H P C O CG', 4000, '2016-10-24 00:00:00', 'Credit', 'NEFT', 'SBIN116297403772 NEFTINW-0050014695', '2016-12-25 17:22:45', 'w'), (757, 37, 'S KRISHNA MOORTHI', 3000, '2016-10-21 00:00:00', 'Credit', 'NEFT', '1064714649 NEFTINW-0049990359', '2016-12-25 17:22:45', 'w'), (758, 39, 'MANAS KUMAR DASH', 3000, '2016-10-20 00:00:00', 'Credit', 'NEFT', 'N294160198193664 NEFTINW-0049890832', '2016-12-25 17:22:45', 'w'), (759, 40, 'SHASHI PRAKASH KRISHNA', 5000, '2016-10-17 00:00:00', 'Credit', 'NEFT', '1061966011 NEFTINW-0049670690', '2016-12-25 17:22:45', 'w'), (760, 42, 'ASHUTOSH MOHANTY', 3100, '2016-10-15 00:00:00', 'Credit', 'NEFT', '1060964873 NEFTINW-0049606462', '2016-12-25 17:22:45', 'w'), (761, 43, 'BIJUKUMAR M', 3000, '2016-10-15 00:00:00', 'Credit', 'IMPS', '628907018531 IMPS-628907127559', '2016-12-25 17:22:45', 'w'), (762, 44, 'NITEEN UTTAM KOLE', 3200, '2016-10-10 00:00:00', 'Credit', 'NEFT', '1057996367 NEFTINW-0049311266', '2016-12-25 17:22:45', 'w'), (763, 49, 'ALIM UDDIN LASKAR', 18000, '2016-10-07 00:00:00', 'Debit', 'CASH', '256', '2016-12-25 17:22:45', 'w'), (764, 50, 'MANOJ K NAIR', 3710, '2016-10-07 00:00:00', 'Credit', 'NEFT', 'CITIN16696238932 NEFTINW-0049176567', '2016-12-25 17:22:45', 'w'), (765, 51, 'NRE REM JYOTHI M', 25954, '2016-10-07 00:00:00', 'Credit', 'NEFT', '1056327478 NEFTINW-0049182627', '2016-12-25 17:22:45', 'w'), (766, 54, 'GAUTAM KUMAR', 4000, '2016-10-06 00:00:00', 'Credit', 'IB', '', '2016-12-25 17:22:45', 'w'), (767, 55, 'GUARDIAN SOUHARDA SAHAKA', 19500, '2016-10-06 00:00:00', 'Debit', 'CLG', '2', '2016-12-25 17:22:45', 'w'), (768, 56, 'DANIEL L', 14960, '2016-10-06 00:00:00', 'Debit', 'CLG', '254', '2016-12-25 17:22:45', 'w'), (769, 60, 'DIVANG SHARMA', 3750, '2016-10-04 00:00:00', 'Credit', 'IB', '', '2016-12-25 17:22:45', 'w'), (770, 61, 'GOPAKUMAR T', 4000, '2016-10-04 00:00:00', 'Credit', 'NEFT', 'CITIN16694850574 NEFTINW-0048915866', '2016-12-25 17:22:45', 'w'), (771, 63, 'VINOD KUMAR A', 3440, '2016-10-03 00:00:00', 'Credit', 'NEFT', 'N277160192656710 NEFTINW-0048828741', '2016-12-25 17:22:45', 'w'), (772, 64, 'VISHESH NIGAM', 3200, '2016-10-03 00:00:00', 'Credit', 'NEFT', 'CITIN16694212776 NEFTINW-0048817411', '2016-12-25 17:22:45', 'w'), (773, 65, 'AMARJEET KUMAR', 3500, '2016-10-03 00:00:00', 'Credit', 'NEFT', '1052133974 NEFTINW-0048814695', '2016-12-25 17:22:45', 'w'), (774, 67, 'MANENDRA PRASAD SINGH', 4000, '2016-10-02 00:00:00', 'Credit', 'IB', '', '2016-12-25 17:22:45', 'w');
/*!40000 ALTER TABLE `Transactions_Staging_Data` ENABLE KEYS */;


# Dumping structure for table sql6134070.Transactions_Verified
CREATE TABLE IF NOT EXISTS `Transactions_Verified` (
  `Transaction_From_Bank_Statement_ID` int(10) NOT NULL AUTO_INCREMENT,
  `Amount` float NOT NULL,
  `Transaction_Date` datetime NOT NULL,
  `Transaction_Flow` varchar(20) NOT NULL,
  `Transaction_Mode` varchar(20) NOT NULL,
  `Transaction_Reference` varchar(70) DEFAULT NULL,
  `User_Id` varchar(20) NOT NULL,
  `Flat_Id` varchar(20) NOT NULL,
  `Verified_By` varchar(20) NOT NULL,
  `Splitted` tinyint(1) NOT NULL DEFAULT '0',
  `Way_Of_Verification` varchar(10) NOT NULL DEFAULT 'Auto',
  PRIMARY KEY (`Transaction_From_Bank_Statement_ID`),
  UNIQUE KEY `Amount_Transaction_Date_Transaction_Reference` (`Amount`,`Transaction_Date`,`Transaction_Reference`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table sql6134070.Transactions_Verified: 60 rows
/*!40000 ALTER TABLE `Transactions_Verified` DISABLE KEYS */;
INSERT IGNORE INTO `Transactions_Verified` (`Transaction_From_Bank_Statement_ID`, `Amount`, `Transaction_Date`, `Transaction_Flow`, `Transaction_Mode`, `Transaction_Reference`, `User_Id`, `Flat_Id`, `Verified_By`, `Splitted`, `Way_Of_Verification`) VALUES (501, 15980, '2016-11-05 00:00:00', 'Debit', 'CLG', '27', 'uTest6', 'Flat_005', '', 0, 'Auto'), (502, 4000, '2016-11-05 00:00:00', 'Credit', 'IMPS', '3101131809 IMPS-3101181738', 'Gopa', 'Flat_005', '', 1, 'Auto'), (503, 3710, '2016-11-05 00:00:00', 'Credit', 'NEFT', 'CITIN1606654521 NEFTINW-005096833', 'manoj', 'Flat_101', '', 1, 'Auto'), (504, 3200, '2016-11-04 00:00:00', 'Credit', 'NEFT', 'CITIN16706434669 NEFTINW-0050946254', 'Phani', 'Flat_102', '', 1, 'Auto'), (505, 3440, '2016-11-04 00:00:00', 'Credit', 'NEFT', 'N30160203228013 NEFTINW-005026201', 'Vinod', 'Flat_107', '', 1, 'Auto'), (506, 13080, '2016-11-03 00:00:00', 'Credit', 'MB', 'MB-999963937489', 'abhinav', 'Flat_306', '', 1, 'Auto'), (507, 10000, '2016-11-03 00:00:00', 'Credit', 'NEFT', 'AXIR163086594824 NEFTINW-0050867969', 'Ivin', 'Flat_106', '', 1, 'Auto'), (508, 3750, '2016-11-02 00:00:00', 'Credit', 'IB', '', 'divang', 'Flat_309', '', 1, 'Auto'), (509, 3200, '2016-11-02 00:00:00', 'Credit', 'NEFT', 'N307160202331660 NEFTINW-0050786163', 'manas', 'Flat_001', '', 1, 'Auto'), (510, 3500, '2016-11-02 00:00:00', 'Credit', 'NEFT', '1071686558 NEFTINW-0050763147', 'Amarjeet', 'Flat_307', '', 1, 'Auto'), (511, 4000, '2016-11-02 00:00:00', 'Credit', 'IB', '', 'Manendra', 'Flat_105', '', 1, 'Auto'), (512, 3710, '2016-10-28 00:00:00', 'Credit', 'NEFT', 'CITIN16703010102 NEFTINW-0050390455', 'jay', 'Flat_201', '', 1, 'Auto'), (513, 3470, '2016-10-24 00:00:00', 'Credit', 'NEFT', 'N298160199150337 NEFTINW-0050108227', 'Raghunandan', 'Flat_009', '', 1, 'Auto'), (514, 4000, '2016-10-24 00:00:00', 'Credit', 'NEFT', 'SBIN116297403772 NEFTINW-0050014695', 'jay', 'Flat_201', '', 1, 'Auto'), (515, 3000, '2016-10-21 00:00:00', 'Credit', 'NEFT', '1064714649 NEFTINW-0049990359', 'Krishna', 'Flat_004', '', 1, 'Auto'), (516, 3000, '2016-10-20 00:00:00', 'Credit', 'NEFT', 'N294160198193664 NEFTINW-0049890832', 'manas', 'Flat_001', '', 1, 'Auto'), (517, 5000, '2016-10-17 00:00:00', 'Credit', 'NEFT', '1061966011 NEFTINW-0049670690', 'Shashi', 'Flat_103', '', 1, 'Auto'), (518, 3100, '2016-10-15 00:00:00', 'Credit', 'NEFT', '1060964873 NEFTINW-0049606462', 'Ashutosh', 'Flat_308', '', 1, 'Auto'), (519, 3200, '2016-10-10 00:00:00', 'Credit', 'NEFT', '1057996367 NEFTINW-0049311266', 'Niteen', 'Flat_104', '', 1, 'Auto'), (520, 3710, '2016-10-07 00:00:00', 'Credit', 'NEFT', 'CITIN16696238932 NEFTINW-0049176567', 'manoj', 'Flat_101', '', 1, 'Auto'), (521, 25954, '2016-10-07 00:00:00', 'Credit', 'NEFT', '1056327478 NEFTINW-0049182627', 'jay', 'Flat_201', '', 1, 'Auto'), (522, 4000, '2016-10-06 00:00:00', 'Credit', 'IB', '', 'Gautam', 'Flat_305', '', 1, 'Auto'), (523, 14960, '2016-10-06 00:00:00', 'Debit', 'CLG', '254', 'uTest6', 'Flat_005', '', 0, 'Auto'), (524, 3750, '2016-10-04 00:00:00', 'Credit', 'IB', '', 'divang', 'Flat_309', '', 1, 'Auto'), (525, 4000, '2016-10-04 00:00:00', 'Credit', 'NEFT', 'CITIN16694850574 NEFTINW-0048915866', 'Gopa', 'Flat_005', '', 1, 'Auto'), (526, 3440, '2016-10-03 00:00:00', 'Credit', 'NEFT', 'N277160192656710 NEFTINW-0048828741', 'Vinod', 'Flat_107', '', 1, 'Auto'), (527, 3200, '2016-10-03 00:00:00', 'Credit', 'NEFT', 'CITIN16694212776 NEFTINW-0048817411', 'vishesh', 'Flat_208', '', 1, 'Auto'), (528, 3500, '2016-10-03 00:00:00', 'Credit', 'NEFT', '1052133974 NEFTINW-0048814695', 'Amarjeet', 'Flat_307', '', 1, 'Auto'), (529, 4000, '2016-10-02 00:00:00', 'Credit', 'IB', '', 'Manendra', 'Flat_105', '', 1, 'Auto');
/*!40000 ALTER TABLE `Transactions_Verified` ENABLE KEYS */;


# Dumping structure for table sql6134070.User_Details
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
  `Auth_Ids` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`User_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table sql6134070.User_Details: 44 rows
/*!40000 ALTER TABLE `User_Details` DISABLE KEYS */;
INSERT IGNORE INTO `User_Details` (`User_Id`, `Login_Id`, `User_Type`, `Status`, `Flat_Id`, `Name`, `Name_Alias`, `Mobile_No`, `Moble_No_Alternate`, `Email_Id`, `Address`, `Flat_Join_Date`, `Flat_Left_Date`, `Auth_Ids`) VALUES ('abhinav', 'geetika1', '', 1, 'Flat_306', 'Abhinav Nigam', 'Abhinav,ABHINAV  NIGAM', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Amarjeet', 'Amarjeet1', '', 1, 'Flat_307', 'Amarjeet Kumar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Anshuman', 'Anshuman1', '', 1, 'Flat_207', 'Anshuman', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Ashutosh', 'Ashutosh1', '', 1, 'Flat_308', 'Ashutosh', 'ASHUSH MOHANTY,ASHUTOSH MOHANTY', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Balaji', 'Balaji1', '', 1, 'Flat_205', 'Balaji Ganapathi', 'BIJUKUMAR', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Dhiman', 'Dhiman1', '', 1, 'Flat_304', 'Dhiman', 'SUMITH VARGHESE,DHIMAN CHAKRABORTY', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('divang', 'w', '', 1, 'Flat_309', 'Divang Sharma', 'divang,DIVANG  SHARMA  ', 9241797239, NULL, 'divang.s@gmail.com', 'Begur Heights', NULL, NULL, '0,1,2,3,4,5,6,9,10,11,12,13,16'), ('Gautam', 'Gautam1', '', 1, 'Flat_305', 'Gautam Kumar', 'GAUTAM  KUMAR  ', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Gopa', 'Gopa1', '', 1, 'Flat_005', 'Gopa Kumar', 'GOPAKUMAR T,GOVULA MADHURI', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Ivin', 'Ivin1', '', 1, 'Flat_106', 'Ivin Sebastian', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('jay', 'jay1', '', 1, 'Flat_201', ' Jay Krishan \r\n', 'JAYAKRISHNAN A,MR JAYAPRAKASH H P C O CG,NRE REM JYOTHI M', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Jaya', 'Jaya1', '', 1, 'Flat_109', 'Jaya Prakash', 'C SHIVA KUMAR', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Karthik', 'Karthik1', '', 1, 'Flat_002', 'Karthik', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Krishna', 'Krishna1', '', 1, 'Flat_004', 'Krishna Murthy', 'S KRISHNA M,S KRISHNA MOORTHI', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Krishnan', 'Krishnan1', '', 1, 'Flat_006', 'Krishnan', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Mahesh', 'Mahesh1', '', 1, 'Flat_209', 'Mahesh Suragimath', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('maheshwar', 'maheshwar1', '', 1, 'Flat_206', 'Maheshwar Mohanty', 'MAHESWAR MOHANTY,SL. NO. MAHESWAR MOHANTY', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('manas', 'Manas1', '', 1, 'Flat_001', ' Manas Dash ', 'MANAS KUMAR DASH', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Manendra', 'Manendra1', '', 1, 'Flat_105', 'Manendra Prasad  Singh', 'MANENA PRASAD SINGH,MANENDRA PRASAD SINGH', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('manoj', 'manoj1', '', 1, 'Flat_101', 'Manoj Nair', 'MANOJ K NAI ,MANOJ K NAIR', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Niteen', 'Niteen1', '', 1, 'Flat_104', 'Niteen Kole', 'NITEEN UTTA,NITEEN UTTAM KOLE', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Phani', 'Phani1', '', 1, 'Flat_102', 'Phani Krishna', 'PHANI KRISH ,PHANI KRISH ,PHANI KRISHNA NARAYANAM', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Philip', 'Philip1', '', 1, 'Flat_203', 'Philip George', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Praveen', 'Praveen1', '', 1, 'Flat_008', 'Praveen Pattanshetti', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('qwe', 'vibhanshu1', 'Tenant', 0, 'Flat_001', 'qqq', 'www,rrr', 2222222, 3333333, 's@g.c', 'weqw#11', '2016-09-20 00:00:00', '2016-09-20 00:00:00', NULL), ('Raghunandan', 'Raghunandan1', '', 1, 'Flat_009', 'Raghunandan', 'RAGHUNANDAN HK,RAGHUNANDAN H K', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Raj', 'Raj1', '', 1, 'Flat_108', 'Raj kumar Mandal', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('ramesh', 'ramesh1', '', 1, 'Flat_007', 'Ramesh Gangan', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Sanjib', 'Sanjib1', '', 1, 'Flat_303', 'Sanjib Singh', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Satheesh', 'Satheesh1', '', 1, 'Flat_302', 'Satheesh S', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Shashi', 'Shashi1', '', 1, 'Flat_103', 'Shashi Prakash Krishna', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Sidda', 'Sidda1', '', 1, 'Flat_003', 'Sidda Raju', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Subhash', 'Subhash1', '', 1, 'Flat_202', 'Subhash Chandra Gupta', 'SUNIL KUMAR', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Sundarali', 'Sundarali1', '', 1, 'Flat_204', 'M. Sundaralingam', 'SUNDARALINGAM MURUGATHITHAN', NULL, NULL, NULL, NULL, NULL, NULL, '1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16'), ('uTest1', 'divang1', 'Tenant', 0, 'Flat_309', 'u test', 'u,UMESHA', 1212121212, 0, 'a@f.c', '1111', '2016-11-01 00:00:00', '2016-11-19 00:00:00', NULL), ('uTest2', 'poonam', 'Tenant', 0, 'Flat_310', 'u test', ',SCHINDLER INDIA PVTLTD', 1313131313, 0, 'a@f.c', '', '2016-11-02 00:00:00', '2016-11-19 00:00:00', '0,1,2,4,3'), ('uTest3', 'poonam', 'Tenant', 0, 'Flat_310', 'u test ', ',AKBAR HUSSAIN LASKAR', 1313131313, 0, 'a@f.c', '', '2016-11-02 00:00:00', '2016-11-19 00:00:00', '0,1,2,4,3'), ('uTest5', 'abhinav1', 'Tenant', 0, 'Flat_208', 'u test', '', 141414141414, 0, 'd@g.c', '', '2016-11-17 00:00:00', '2016-11-19 00:00:00', '0,1,2'), ('uTest6', 'sudha', 'Tenant', 0, 'Flat_005', 'u test', ',DANIEL L', 1616161616, 0, 'a@g.h', '', '2016-11-02 00:00:00', '2016-11-19 00:00:00', '3'), ('Vinod', 'Vinod1', '', 1, 'Flat_107', 'A Vinod Kumar', 'VINOD KUMAR A', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('vinoy ', 'vinoy1', '', 1, 'Flat_301', ' Vinoy ', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('vishesh', 'vibhanshu1', '', 1, 'Flat_208', 'Vishesh Nigam', 'vishesh,SHRUTI NIGAM', NULL, NULL, NULL, NULL, NULL, NULL, '0'), ('viveksharma', 'vivek1', 'Tenant', 0, 'Flat_007', 'vivek sharma', 'vivek sharma', 980000, 1111, 's@s.com', NULL, '2016-09-04 00:00:00', '2016-09-04 00:00:00', NULL), ('xyz', 'Select Login Id', 'Select User Type', 0, 'Select Fla', 'vv', 'vvv', 111111, 222222, 'a@w.c', '111aaa', '2016-09-19 00:00:00', '2016-09-20 00:00:00', NULL);
/*!40000 ALTER TABLE `User_Details` ENABLE KEYS */;


# Dumping structure for table sql6134070.User_Paid
CREATE TABLE IF NOT EXISTS `User_Paid` (
  `User_Cash_Payment_ID` bigint(50) NOT NULL AUTO_INCREMENT,
  `User_ID` varchar(20) NOT NULL,
  `Flat_ID` varchar(10) NOT NULL,
  `Amount` double NOT NULL,
  `Paid_Date` datetime NOT NULL,
  `Expense_Type_Id` int(10) NOT NULL,
  `Verified` tinyint(1) NOT NULL,
  `Verified_By` varchar(20) NOT NULL DEFAULT '0',
  `User_Comment` varchar(200) NOT NULL,
  `Admin_Comment` varchar(200) NOT NULL,
  `Transaction_ID` int(10) DEFAULT NULL,
  `User_Verified` tinyint(1) NOT NULL DEFAULT '0',
  `Splitted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`User_Cash_Payment_ID`),
  UNIQUE KEY `User_ID_Flat_ID_Amount_Paid_Date_Expense_Type_Id_Transaction_ID` (`User_ID`,`Flat_ID`,`Amount`,`Paid_Date`,`Expense_Type_Id`,`Transaction_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6134070.User_Paid


# Dumping structure for table sql6134070.User_Payment_Status
CREATE TABLE IF NOT EXISTS `User_Payment_Status` (
  `Payment_Status_Id` int(10) NOT NULL AUTO_INCREMENT,
  `Status_Type` varchar(20) NOT NULL,
  PRIMARY KEY (`Payment_Status_Id`),
  UNIQUE KEY `Status_Type` (`Status_Type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table sql6134070.User_Payment_Status: 3 rows
/*!40000 ALTER TABLE `User_Payment_Status` DISABLE KEYS */;
INSERT IGNORE INTO `User_Payment_Status` (`Payment_Status_Id`, `Status_Type`) VALUES (2, 'Full_Paid'), (1, 'Not_Paid'), (3, 'Partial_Paid');
/*!40000 ALTER TABLE `User_Payment_Status` ENABLE KEYS */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
