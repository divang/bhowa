# --------------------------------------------------------
# Host:                         sql6.freemysqlhosting.net
# Database:                     sql6151810
# Server version:               5.5.49-0ubuntu0.14.04.1
# Server OS:                    debian-linux-gnu
# HeidiSQL version:             5.0.0.3031
# Date/time:                    2017-01-01 23:09:01
# --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

# Dumping structure for table sql6151810.Apartment_Earning
DROP TABLE IF EXISTS `Apartment_Earning`;
CREATE TABLE IF NOT EXISTS `Apartment_Earning` (
  `Apartment_Earning_ID` int(10) NOT NULL AUTO_INCREMENT,
  `Expense_Type_Id` int(10) NOT NULL,
  `Amount` float NOT NULL,
  `Earned_Date` datetime NOT NULL,
  `Verified` bit(1) NOT NULL,
  `Verified_By` varchar(50) NOT NULL,
  `Admin_Comment` varchar(200) NOT NULL,
  `Splitted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Apartment_Earning_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6151810.Apartment_Earning


# Dumping structure for table sql6151810.Apartment_Expense
DROP TABLE IF EXISTS `Apartment_Expense`;
CREATE TABLE IF NOT EXISTS `Apartment_Expense` (
  `Apartment_Cash_Expense_ID` int(10) NOT NULL AUTO_INCREMENT,
  `Expense_Type_Id` int(10) NOT NULL,
  `Amount` float NOT NULL,
  `Expend_Date` datetime NOT NULL,
  `Expend_By_UserId` varchar(50) NOT NULL,
  `Verified` bit(1) NOT NULL,
  `Verified_By` varchar(50) NOT NULL,
  `Expendy_Comment` varchar(200) NOT NULL,
  `Admin_Comment` varchar(200) NOT NULL,
  `Splitted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Apartment_Cash_Expense_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6151810.Apartment_Expense


# Dumping structure for table sql6151810.Authorizations
DROP TABLE IF EXISTS `Authorizations`;
CREATE TABLE IF NOT EXISTS `Authorizations` (
  `Auth_Id` int(10) NOT NULL,
  `Type` varchar(50) NOT NULL,
  PRIMARY KEY (`Type`),
  UNIQUE KEY `Auth_Id` (`Auth_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table sql6151810.Authorizations: 17 rows
/*!40000 ALTER TABLE `Authorizations` DISABLE KEYS */;
INSERT INTO `Authorizations` (`Auth_Id`, `Type`) VALUES (0, 'ADMIN'), (1, 'MY_DUES_VIEWS'), (2, 'NOTIFICATION_SEND'), (3, 'USER_DETAIL_VIEW'), (4, 'USER_DETAIL_CREATE'), (5, 'FLAT_DETAIL_VIEW'), (6, 'FLAT_DETAIL_CREATE'), (7, 'LOGIN_VIEW'), (8, 'LOGIN_CREATE'), (9, 'TRANSACTION_HOME_VIEW'), (10, 'RAW_DATA_VIEW'), (11, 'TRANSACTIONS_DETAIL_VIEW'), (12, 'PDF_TRANSACTION_VIEW'), (13, 'PDF_TRANSACTION_UPLOAD_TO_STAGING_TABLE'), (14, 'MAP_USER_WITH_MONTHLY_PDF_NAME'), (15, 'MONTHLY_MAINTENANCE_GENERATOR'), (16, 'VERIFIED_PDF_TRANSACTIONS_UPLOAD');
/*!40000 ALTER TABLE `Authorizations` ENABLE KEYS */;


# Dumping structure for table sql6151810.Bank_Statement
DROP TABLE IF EXISTS `Bank_Statement`;
CREATE TABLE IF NOT EXISTS `Bank_Statement` (
  `Bank_Statement_FileName` varchar(50) NOT NULL,
  `Uploaded_Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Uploaded_LoginId` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6151810.Bank_Statement


# Dumping structure for table sql6151810.Expense_Type
DROP TABLE IF EXISTS `Expense_Type`;
CREATE TABLE IF NOT EXISTS `Expense_Type` (
  `Expense_Type_Id` int(10) NOT NULL,
  `Type` varchar(50) NOT NULL,
  `Payable_Priority` int(10) DEFAULT NULL,
  PRIMARY KEY (`Expense_Type_Id`),
  UNIQUE KEY `Type` (`Type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table sql6151810.Expense_Type: 36 rows
/*!40000 ALTER TABLE `Expense_Type` DISABLE KEYS */;
INSERT INTO `Expense_Type` (`Expense_Type_Id`, `Type`, `Payable_Priority`) VALUES (0, 'Advance_Payment', -1), (1, 'Monthly_Maintenance', 1), (2, 'Initial_Payable', 4), (3, 'Khata_Payable', 3), (4, 'Generator_Repair_Servicing', NULL), (5, 'Lift_AMC', NULL), (6, 'Lift_Repair\r\n', NULL), (7, 'Plumbing', NULL), (8, 'Electrical_Repair', NULL), (9, 'Tank_Cleaning_Repairing', NULL), (10, 'Lawyer', NULL), (11, 'Miscellaneous', NULL), (12, 'Intercom_AMC\r\n', NULL), (13, 'Intercom_Purchase_Repair', NULL), (14, 'Fire_Extinguisher', NULL), (15, 'Security_Related', NULL), (16, 'Apartment_InfraStructure_Repair\r\n', NULL), (17, 'Children_Park', NULL), (18, 'Septick_Tank_ Pipe_Cleaning', NULL), (19, 'Club_House', NULL), (20, 'Alamari_Purchase', NULL), (21, 'Security_Guards\r\n', NULL), (22, 'House_Keeping_Labour', NULL), (23, 'Common_Electricity', NULL), (24, 'Water_Tankers ', NULL), (25, 'Gardening', NULL), (26, 'Deisel_For_Generator\r\n', NULL), (27, 'House_Keeping_Consumables', NULL), (28, 'Pest_Control', NULL), (29, 'Generator_AMC', NULL), (30, 'Flat_Old_Dues', 2), (31, 'Waste_Disposal', NULL), (32, 'Gym', NULL), (33, 'MotorRepair', NULL), (34, 'Penalty', NULL), (35, 'Club_Store_Earning', NULL);
/*!40000 ALTER TABLE `Expense_Type` ENABLE KEYS */;


# Dumping structure for table sql6151810.Flat
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

# No rows in table sql6151810.Flat


# Dumping structure for table sql6151810.Flat_Wise_Payable
DROP TABLE IF EXISTS `Flat_Wise_Payable`;
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

# No rows in table sql6151810.Flat_Wise_Payable


# Dumping structure for table sql6151810.Login
DROP TABLE IF EXISTS `Login`;
CREATE TABLE IF NOT EXISTS `Login` (
  `Login_Id` varchar(20) NOT NULL,
  `Password` varchar(20) NOT NULL,
  `Status` tinyint(1) NOT NULL DEFAULT '1',
  `Society_Id` int(100) DEFAULT NULL,
  PRIMARY KEY (`Login_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table sql6151810.Login: 1 rows
/*!40000 ALTER TABLE `Login` DISABLE KEYS */;
INSERT INTO `Login` (`Login_Id`, `Password`, `Status`, `Society_Id`) VALUES ('superadmin', 'p0o9i8!)@(', 1, 1);
/*!40000 ALTER TABLE `Login` ENABLE KEYS */;


# Dumping structure for table sql6151810.Notification
DROP TABLE IF EXISTS `Notification`;
CREATE TABLE IF NOT EXISTS `Notification` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `Message` varchar(100) DEFAULT '0',
  `Time` datetime DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6151810.Notification


# Dumping structure for table sql6151810.Society
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

# Dumping data for table sql6151810.Society: 1 rows
/*!40000 ALTER TABLE `Society` DISABLE KEYS */;
INSERT INTO `Society` (`Society_Id`, `Society_Name`, `Database_URL`, `Database_User`, `Database_Password`, `Email_Id`) VALUES (1, 'BHOWA', 'jdbc:mysql://sql6.freemysqlhosting.net:3306/sql6151810', 'sql6151810', 'iSDBwCe8dF', 'bhowa.begurwoods@gmail.com');
/*!40000 ALTER TABLE `Society` ENABLE KEYS */;


# Dumping structure for table sql6151810.Transactions_BalanceSheet
DROP TABLE IF EXISTS `Transactions_BalanceSheet`;
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

# No rows in table sql6151810.Transactions_BalanceSheet


# Dumping structure for table sql6151810.Transactions_Staging_Data
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
  `Upload_Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `Uploaded_LoginId` varchar(50) NOT NULL,
  PRIMARY KEY (`Transaction_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6151810.Transactions_Staging_Data


# Dumping structure for table sql6151810.Transactions_Verified
DROP TABLE IF EXISTS `Transactions_Verified`;
CREATE TABLE IF NOT EXISTS `Transactions_Verified` (
  `Transaction_From_Bank_Statement_ID` int(10) NOT NULL AUTO_INCREMENT,
  `Amount` float NOT NULL,
  `Transaction_Date` datetime NOT NULL,
  `Transaction_Flow` varchar(20) NOT NULL,
  `Transaction_Mode` varchar(20) NOT NULL,
  `Transaction_Reference` varchar(70) DEFAULT NULL,
  `User_Id` varchar(20) NOT NULL,
  `Flat_Id` varchar(20) DEFAULT NULL,
  `Verified_By` varchar(20) NOT NULL,
  `Splitted` tinyint(1) NOT NULL DEFAULT '0',
  `Way_Of_Verification` varchar(10) NOT NULL DEFAULT 'Auto',
  PRIMARY KEY (`Transaction_From_Bank_Statement_ID`),
  UNIQUE KEY `Amount_Transaction_Date_Transaction_Reference` (`Amount`,`Transaction_Date`,`Transaction_Reference`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6151810.Transactions_Verified


# Dumping structure for table sql6151810.User_Details
DROP TABLE IF EXISTS `User_Details`;
CREATE TABLE IF NOT EXISTS `User_Details` (
  `User_Id` varchar(20) NOT NULL,
  `Login_Id` varchar(20) DEFAULT NULL,
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
  `Service_Type_Id` int(10) DEFAULT NULL,
  PRIMARY KEY (`User_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table sql6151810.User_Details: 1 rows
/*!40000 ALTER TABLE `User_Details` DISABLE KEYS */;
INSERT INTO `User_Details` (`User_Id`, `Login_Id`, `User_Type`, `Status`, `Flat_Id`, `Name`, `Name_Alias`, `Mobile_No`, `Moble_No_Alternate`, `Email_Id`, `Address`, `Flat_Join_Date`, `Flat_Left_Date`, `Auth_Ids`, `Service_Type_Id`) VALUES ('super_admin', 'admin', '', 1, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL);
/*!40000 ALTER TABLE `User_Details` ENABLE KEYS */;


# Dumping structure for table sql6151810.User_Paid
DROP TABLE IF EXISTS `User_Paid`;
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
  `User_Verified` tinyint(1) NOT NULL DEFAULT '0',
  `Splitted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`User_Cash_Payment_ID`),
  UNIQUE KEY `User_ID_Flat_ID_Amount_Paid_Date_Expense_Type_Id` (`User_ID`,`Flat_ID`,`Amount`,`Paid_Date`,`Expense_Type_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6151810.User_Paid


# Dumping structure for table sql6151810.User_Payment_Status
DROP TABLE IF EXISTS `User_Payment_Status`;
CREATE TABLE IF NOT EXISTS `User_Payment_Status` (
  `Payment_Status_Id` int(10) NOT NULL AUTO_INCREMENT,
  `Status_Type` varchar(20) NOT NULL,
  PRIMARY KEY (`Payment_Status_Id`),
  UNIQUE KEY `Status_Type` (`Status_Type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table sql6151810.User_Payment_Status: 3 rows
/*!40000 ALTER TABLE `User_Payment_Status` DISABLE KEYS */;
INSERT INTO `User_Payment_Status` (`Payment_Status_Id`, `Status_Type`) VALUES (2, 'Full_Paid'), (1, 'Not_Paid'), (3, 'Partial_Paid');
/*!40000 ALTER TABLE `User_Payment_Status` ENABLE KEYS */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
