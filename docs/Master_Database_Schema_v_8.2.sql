# --------------------------------------------------------
# Host:                         sql6.freemysqlhosting.net
# Database:                     sql6134070
# Server version:               5.5.49-0ubuntu0.14.04.1
# Server OS:                    debian-linux-gnu
# HeidiSQL version:             5.0.0.3031
# Date/time:                    2016-12-23 08:45:14
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

# Dumping data for table sql6134070.Bank_Statement: 5 rows
/*!40000 ALTER TABLE `Bank_Statement` DISABLE KEYS */;
INSERT IGNORE INTO `Bank_Statement` (`Bank_Statement_FileName`, `Uploaded_Date`, `Uploaded_LoginId`) VALUES ('/storage/emulated/0/Download/Report-20161107103403', '2016-12-10 00:00:00', ''), ('/storage/emulated/0/Download/Report-20161107103403', '2016-12-10 00:00:00', ''), ('/storage/emulated/0/Download/Report-20161107103403', '2016-12-10 16:19:17', ''), ('/storage/emulated/0/Download/Report-20161107103403', '2016-12-10 16:30:10', ''), ('/storage/emulated/0/Download/Report-20161107103403', '2016-12-10 16:38:12', 'w');
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


# Dumping structure for table sql6134070.Expense_Verification
CREATE TABLE IF NOT EXISTS `Expense_Verification` (
  `Payment_ID` bigint(50) NOT NULL AUTO_INCREMENT,
  `User_ID` varchar(20) NOT NULL,
  `Amount` double NOT NULL,
  `Paid_Date` datetime NOT NULL,
  `Expense_Type_Id` int(10) NOT NULL,
  `Verified` tinyint(1) NOT NULL,
  `Verified_By` varchar(20) NOT NULL DEFAULT '0',
  `User_Comment` varchar(200) NOT NULL,
  `Admin_Comment` varchar(200) NOT NULL,
  `Transaction_ID` int(10) DEFAULT NULL,
  `User_Verified` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Payment_ID`),
  UNIQUE KEY `User_ID_Amount_Expense_Type_Id_Transaction_ID` (`User_ID`,`Amount`,`Expense_Type_Id`,`Transaction_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6134070.Expense_Verification


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

# Dumping data for table sql6134070.Flat_Wise_Payable: 4 rows
/*!40000 ALTER TABLE `Flat_Wise_Payable` DISABLE KEYS */;
INSERT IGNORE INTO `Flat_Wise_Payable` (`Flat_Wise_Payable_ID`, `Flat_Id`, `Status`, `Month`, `Year`, `Amount`, `Expense_Type_Id`, `Comments`, `Payment_Status_ID`) VALUES (1, 'Flat_309', 1, 7, 2106, 10000, 2, 'July 2016 BHOWA XLS Receivable data', 3), (2, 'Flat_001', 1, 7, 2106, 3000, 1, 'July 2016 BHOWA XLS Receivable data', 1), (3, 'Flat_001', 1, 8, 2016, 3200, 1, '', 2), (4, 'Flat_309', 1, 9, 2016, 4000, 1, '', 1);
/*!40000 ALTER TABLE `Flat_Wise_Payable` ENABLE KEYS */;


# Dumping structure for table sql6134070.Flat_Wise_Payable_Paid_Mapping
CREATE TABLE IF NOT EXISTS `Flat_Wise_Payable_Paid_Mapping` (
  `Flat_Wise_Payable_ID` int(10) NOT NULL,
  `Balance_Sheet_Transaction_ID` int(10) NOT NULL,
  UNIQUE KEY `Payable_Id_Transaction_ID` (`Flat_Wise_Payable_ID`,`Balance_Sheet_Transaction_ID`),
  UNIQUE KEY `Payable_Id_Balance_Sheet_Transaction_ID` (`Flat_Wise_Payable_ID`,`Balance_Sheet_Transaction_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table sql6134070.Flat_Wise_Payable_Paid_Mapping: 2 rows
/*!40000 ALTER TABLE `Flat_Wise_Payable_Paid_Mapping` DISABLE KEYS */;
INSERT IGNORE INTO `Flat_Wise_Payable_Paid_Mapping` (`Flat_Wise_Payable_ID`, `Balance_Sheet_Transaction_ID`) VALUES (1, 0), (1, 1), (1, 2), (2, 0), (4, 0);
/*!40000 ALTER TABLE `Flat_Wise_Payable_Paid_Mapping` ENABLE KEYS */;


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
  PRIMARY KEY (`Balance_Sheet_Transaction_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table sql6134070.Transactions_BalanceSheet: 9 rows
/*!40000 ALTER TABLE `Transactions_BalanceSheet` DISABLE KEYS */;
INSERT IGNORE INTO `Transactions_BalanceSheet` (`Balance_Sheet_Transaction_ID`, `Amount`, `Verified_By_Admin`, `Verified_By_User`, `Expense_Type_Id`, `Transaction_From_Bank_Statement_ID`, `User_Cash_Payment_ID`, `Transaction_Expense_Id`, `Transaction_Flow`) VALUES (1, 1000, '1', '1', 1, 1, NULL, NULL, ''), (2, 2000, '1', '1', 1, 1, NULL, NULL, ''), (3, 3000, '1', '1', 2, 1, 0, NULL, ''), (10, 4000, '0', '0', 2, 175, 0, 0, 'Credit'), (11, 3000, '0', '0', 1, 176, 0, 0, 'Credit'), (12, 4000, '0', '0', 1, 177, 0, 0, 'Credit'), (13, 1000, '1', '0', 0, 0, 0, 0, 'Credit'), (14, 6000, '1', '0', 0, 0, 0, 0, 'Credit'), (15, 1000, '1', '0', 0, 177, 0, 0, 'Credit'), (16, 4000, '0', '0', 2, 175, 0, 0, 'Credit'), (17, 3000, '0', '0', 1, 176, 0, 0, 'Credit'), (18, 4000, '0', '0', 1, 177, 0, 0, 'Credit');
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

# Dumping data for table sql6134070.Transactions_Staging_Data: 25 rows
/*!40000 ALTER TABLE `Transactions_Staging_Data` DISABLE KEYS */;
INSERT IGNORE INTO `Transactions_Staging_Data` (`Transaction_ID`, `StatementID`, `Name`, `Amount`, `Transaction_Date`, `Transaction_Flow`, `Transaction_Mode`, `Transaction_Reference`, `Upload_Date`, `Uploaded_LoginId`) VALUES (526, 18, 'DIVANG SHARMA', 4000, '2016-11-02 00:00:00', 'Credit', 'IB', '', '2016-12-12 03:00:32', 'w'), (534, 39, 'MANAS KUMAR DASH', 3000, '2016-10-20 00:00:00', 'Credit', 'NEFT', 'N294160198193664 NEFTINW-0049890832', '2016-12-10 16:37:29', 'w'), (545, 60, 'DIVANG SHARMA', 5000, '2016-10-04 00:00:00', 'Credit', 'IB', '', '2016-12-12 03:00:27', 'w');
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

# Dumping data for table sql6134070.Transactions_Verified: 58 rows
/*!40000 ALTER TABLE `Transactions_Verified` DISABLE KEYS */;
INSERT IGNORE INTO `Transactions_Verified` (`Transaction_From_Bank_Statement_ID`, `Amount`, `Transaction_Date`, `Transaction_Flow`, `Transaction_Mode`, `Transaction_Reference`, `User_Id`, `Flat_Id`, `Verified_By`, `Splitted`, `Way_Of_Verification`) VALUES (175, 4000, '2016-11-02 00:00:00', 'Credit', 'IB', '', 'divang', 'Flat_309', '', 0, 'Auto'), (176, 3000, '2016-10-20 00:00:00', 'Credit', 'NEFT', 'N294160198193664 NEFTINW-0049890832', 'manas', 'Flat_001', '', 0, 'Auto'), (177, 5000, '2016-10-04 00:00:00', 'Credit', 'IB', '', 'divang', 'Flat_309', '', 0, 'Auto');
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

# Dumping data for table sql6134070.User_Paid: 2 rows
/*!40000 ALTER TABLE `User_Paid` DISABLE KEYS */;
INSERT IGNORE INTO `User_Paid` (`User_Cash_Payment_ID`, `User_ID`, `Flat_ID`, `Amount`, `Paid_Date`, `Expense_Type_Id`, `Verified`, `Verified_By`, `User_Comment`, `Admin_Comment`, `Transaction_ID`, `User_Verified`, `Splitted`) VALUES (1, 'manas', 'Flat_001', 1000, '2016-07-15 00:00:00', 2, 1, 'w', '', 'July 2016 BHOWA XLS Receivable data', NULL, 0, 0), (2, 'divang', 'Flat_309', 6000, '2016-07-15 00:00:00', 2, 1, 'w', '', 'July 2016 BHOWA XLS Receivable data', NULL, 0, 0);
/*!40000 ALTER TABLE `User_Paid` ENABLE KEYS */;


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
