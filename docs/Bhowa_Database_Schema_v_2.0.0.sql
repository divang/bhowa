# --------------------------------------------------------
# Host:                         sql6.freemysqlhosting.net
# Database:                     sql6134070
# Server version:               5.5.49-0ubuntu0.14.04.1
# Server OS:                    debian-linux-gnu
# HeidiSQL version:             5.0.0.3031
# Date/time:                    2016-09-03 18:14:46
# --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
# Dumping database structure for sql6134070
DROP DATABASE IF EXISTS `sql6134070`;
CREATE DATABASE IF NOT EXISTS `sql6134070` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `sql6134070`;


# Dumping structure for table sql6134070.Bank_Statement
DROP TABLE IF EXISTS `Bank_Statement`;
CREATE TABLE IF NOT EXISTS `Bank_Statement` (
  `Statement_FileName` varchar(100) NOT NULL,
  `Uploaded_Date` datetime NOT NULL,
  `Status` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6134070.Bank_Statement


# Dumping structure for table sql6134070.Error_Log
DROP TABLE IF EXISTS `Error_Log`;
CREATE TABLE IF NOT EXISTS `Error_Log` (
  `User_Id` bigint(20) DEFAULT NULL,
  `Log` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6134070.Error_Log


# Dumping structure for table sql6134070.Expense_Type
DROP TABLE IF EXISTS `Expense_Type`;
CREATE TABLE IF NOT EXISTS `Expense_Type` (
  `Type` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6134070.Expense_Type


# Dumping structure for table sql6134070.Flat
DROP TABLE IF EXISTS `Flat`;
CREATE TABLE IF NOT EXISTS `Flat` (
  `Flat_Id` int(10) NOT NULL DEFAULT '0',
  `Flat_Number` varchar(10) DEFAULT NULL,
  `Area` int(10) DEFAULT NULL,
  `Maintenance_Amount` float DEFAULT NULL,
  `Block_Number` varchar(20) NOT NULL,
  `Status` tinyint(20) DEFAULT '1',
  PRIMARY KEY (`Flat_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6134070.Flat


# Dumping structure for table sql6134070.Flat_Charges
DROP TABLE IF EXISTS `Flat_Charges`;
CREATE TABLE IF NOT EXISTS `Flat_Charges` (
  `Charge_Id` mediumint(10) NOT NULL,
  `Flat_Id` int(10) NOT NULL,
  `Status` int(1) NOT NULL DEFAULT '1',
  `Month` int(2) NOT NULL,
  `Year` int(4) NOT NULL,
  `Amount` float NOT NULL,
  `Expense_Type_Id` int(10) NOT NULL,
  `User_Ids` varchar(50) NOT NULL,
  `Comments` varchar(50) NOT NULL,
  PRIMARY KEY (`Charge_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6134070.Flat_Charges


# Dumping structure for table sql6134070.Login
DROP TABLE IF EXISTS `Login`;
CREATE TABLE IF NOT EXISTS `Login` (
  `Login_Id` varchar(20) NOT NULL,
  `Password` varchar(20) NOT NULL,
  `Status` tinyint(1) NOT NULL DEFAULT '1',
  `Authorised_Activity` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Login_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table sql6134070.Login: 36 rows
/*!40000 ALTER TABLE `Login` DISABLE KEYS */;
REPLACE INTO `Login` (`Login_Id`, `Password`, `Status`, `Authorised_Activity`) VALUES (' Manas1', ' Manas1123', 1, NULL), (' vinoy1', ' vinoy1123', 1, NULL), ('abhinav1', 'abhinav1123', 1, NULL), ('Amarjeet1', 'Amarjeet1123', 1, NULL), ('Anshuman1', 'Anshuman1123', 1, NULL), ('Ashutosh1', 'Ashutosh1123', 1, NULL), ('Balaji1', 'Balaji1123', 1, NULL), ('Dhiman1', 'Dhiman1123', 1, NULL), ('divang1', 'divang1123', 1, NULL), ('Gautam1', 'Gautam1123', 1, NULL), ('Gopa1', 'Gopa1123', 1, NULL), ('Ivin1', 'Ivin1123', 1, NULL), ('jay1', 'jay1123', 1, NULL), ('Jaya1', 'Jaya1123', 1, NULL), ('Karthik1', 'Karthik1123', 1, NULL), ('Krishna1', 'Krishna1123', 1, NULL), ('Krishnan1', 'Krishnan1123', 1, NULL), ('Mahesh1', 'Mahesh1123', 1, NULL), ('maheshwar1', 'maheshwar1123', 1, NULL), ('Manendra1', 'Manendra1123', 1, NULL), ('manoj1', 'manoj1123', 1, NULL), ('Niteen1', 'Niteen1123', 1, NULL), ('Phani1', 'Phani1123', 1, NULL), ('Philip1', 'Philip1123', 1, NULL), ('Praveen1', 'Praveen1123', 1, NULL), ('Raghunandan1', 'Raghunandan1123', 1, NULL), ('Raj1', 'Raj1123', 1, NULL), ('ramesh1', 'ramesh1123', 1, NULL), ('Sanjib1', 'Sanjib1123', 1, NULL), ('Satheesh1', 'Satheesh1123', 1, NULL), ('Shashi1', 'Shashi1123', 1, NULL), ('Sidda1', 'Sidda1123', 1, NULL), ('Subhash1', 'Subhash1123', 1, NULL), ('Sundarali1', 'Sundarali1123', 1, NULL), ('Vinod1', 'Vinod1123', 1, NULL), ('vishesh1', 'vishesh1123', 1, NULL);
/*!40000 ALTER TABLE `Login` ENABLE KEYS */;


# Dumping structure for table sql6134070.Monthly_Maintenance
DROP TABLE IF EXISTS `Monthly_Maintenance`;
CREATE TABLE IF NOT EXISTS `Monthly_Maintenance` (
  `Month` int(10) NOT NULL,
  `Year` int(10) NOT NULL,
  `Status` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6134070.Monthly_Maintenance


# Dumping structure for table sql6134070.Notification
DROP TABLE IF EXISTS `Notification`;
CREATE TABLE IF NOT EXISTS `Notification` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `Message` varchar(100) DEFAULT '0',
  `Time` datetime DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6134070.Notification


# Dumping structure for table sql6134070.Notification_User_Received
DROP TABLE IF EXISTS `Notification_User_Received`;
CREATE TABLE IF NOT EXISTS `Notification_User_Received` (
  `User_Id` int(10) DEFAULT NULL,
  `Notification_Id` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6134070.Notification_User_Received


# Dumping structure for table sql6134070.Payment_Mode
DROP TABLE IF EXISTS `Payment_Mode`;
CREATE TABLE IF NOT EXISTS `Payment_Mode` (
  `Payment_Mode_Id` int(10) NOT NULL DEFAULT '0',
  `Payment_Mode` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`Payment_Mode_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6134070.Payment_Mode


# Dumping structure for table sql6134070.Row_Data
DROP TABLE IF EXISTS `Row_Data`;
CREATE TABLE IF NOT EXISTS `Row_Data` (
  `Transaction_Raw_Line_Parse_From_PDF` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6134070.Row_Data


# Dumping structure for table sql6134070.Transactions
DROP TABLE IF EXISTS `Transactions`;
CREATE TABLE IF NOT EXISTS `Transactions` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `StatementID` int(10) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Amount` float NOT NULL,
  `Transaction_Date` datetime NOT NULL,
  `Transaction_Flow` varchar(20) NOT NULL,
  `Transaction_Mode` varchar(20) DEFAULT NULL,
  `Transaction_Reference` varchar(70) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6134070.Transactions


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
  `Admin_Comment` varchar(100) NOT NULL DEFAULT '0',
  `User_Comment` varchar(100) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Transaction_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6134070.Transactions_Staging_Data


# Dumping structure for table sql6134070.User_Activity_Logging
DROP TABLE IF EXISTS `User_Activity_Logging`;
CREATE TABLE IF NOT EXISTS `User_Activity_Logging` (
  `UserName` varchar(50) NOT NULL,
  `Mobile` varchar(50) DEFAULT NULL,
  `Activity` varchar(20) NOT NULL,
  `Comment` varchar(100) DEFAULT NULL,
  `Time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6134070.User_Activity_Logging


# Dumping structure for table sql6134070.User_Details
DROP TABLE IF EXISTS `User_Details`;
CREATE TABLE IF NOT EXISTS `User_Details` (
  `User_Id` varchar(20) NOT NULL,
  `Login_Id` varchar(20) NOT NULL,
  `User_Type_Id` varchar(20) NOT NULL,
  `Status` tinyint(1) NOT NULL,
  `Flat_Id` int(10) DEFAULT NULL,
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

# Dumping data for table sql6134070.User_Details: 36 rows
/*!40000 ALTER TABLE `User_Details` DISABLE KEYS */;
REPLACE INTO `User_Details` (`User_Id`, `Login_Id`, `User_Type_Id`, `Status`, `Flat_Id`, `Name`, `Name_Alias`, `Mobile_No`, `Moble_No_Alternate`, `Email_Id`, `Address`, `Flat_Join_Date`, `Flat_Left_Date`) VALUES ('abhinav', 'abhinav1', '', 1, 306, 'Abhinav Nigam', 'Abhinav', NULL, NULL, NULL, NULL, NULL, NULL), ('Amarjeet', 'Amarjeet1', '', 1, 307, 'Amarjeet Kumar', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Anshuman', 'Anshuman1', '', 1, 207, 'Anshuman', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Ashutosh', 'Ashutosh1', '', 1, 308, 'Ashutosh', 'ASHUSH MOHANTY', NULL, NULL, NULL, NULL, NULL, NULL), ('Balaji', 'Balaji1', '', 1, 205, 'Balaji Ganapathi', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Dhiman', 'Dhiman1', '', 1, 304, 'Dhiman', 'SUMITH VARGHESE,DHIMAN CHAKRABORTY', NULL, NULL, NULL, NULL, NULL, NULL), ('divang', 'divang1', '', 1, 309, 'Divang Sharma', 'divang,DIVANG  SHARMA  ', 9241797239, NULL, 'divang.s@gmail.com', 'Begur Heights', NULL, NULL), ('Gautam', 'Gautam1', '', 1, 305, 'Gautam Kumar', 'GAUTAM  KUMAR  ', NULL, NULL, NULL, NULL, NULL, NULL), ('Gopa', 'Gopa1', '', 1, 5, 'Gopa Kumar', 'GOPAKUMAR T', NULL, NULL, NULL, NULL, NULL, NULL), ('Ivin', 'Ivin1', '', 1, 106, 'Ivin Sebastian', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('jay', 'jay1', '', 1, 201, ' Jay Krishan \r\n', 'JAYAKRISHNAN A', NULL, NULL, NULL, NULL, NULL, NULL), ('Jaya', 'Jaya1', '', 1, 109, 'Jaya Prakash', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Karthik', 'Karthik1', '', 1, 2, 'Karthik', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Krishna', 'Krishna1', '', 1, 4, 'Krishna Murthy', 'S KRISHNA M', NULL, NULL, NULL, NULL, NULL, NULL), ('Krishnan', 'Krishnan1', '', 1, 6, 'Krishnan', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Mahesh', 'Mahesh1', '', 1, 209, 'Mahesh Suragimath', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('maheshwar', 'maheshwar1', '', 1, 206, 'Maheshwar Mohanty', 'MAHESWAR MOHANTY', NULL, NULL, NULL, NULL, NULL, NULL), ('manas', ' Manas1', '', 1, 1, ' Manas Dash ', 'MANAS KUMAR DASH', NULL, NULL, NULL, NULL, NULL, NULL), ('Manendra', 'Manendra1', '', 1, 105, 'Manendra Prasad  Singh', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('manoj', 'manoj1', '', 1, 101, 'Manoj Nair', 'MANOJ K NAI ,MANOJ K NAIR', NULL, NULL, NULL, NULL, NULL, NULL), ('Niteen', 'Niteen1', '', 1, 104, 'Niteen Kole', 'NITEEN UTTA', NULL, NULL, NULL, NULL, NULL, NULL), ('Phani', 'Phani1', '', 1, 102, 'Phani Krishna', 'PHANI KRISH ,PHANI KRISH ,PHANI KRISHNA NARAYANAM', NULL, NULL, NULL, NULL, NULL, NULL), ('Philip', 'Philip1', '', 1, 203, 'Philip George', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Praveen', 'Praveen1', '', 1, 8, 'Praveen Pattanshetti', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Raghunandan', 'Raghunandan1', '', 1, 9, 'Raghunandan', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Raj', 'Raj1', '', 1, 108, 'Raj kumar Mandal', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('ramesh', 'ramesh1', '', 1, 7, 'Ramesh Gangan', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Sanjib', 'Sanjib1', '', 1, 303, 'Sanjib Singh', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Satheesh', 'Satheesh1', '', 1, 302, 'Satheesh S', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Shashi', 'Shashi1', '', 1, 103, 'Shashi Prakash Krishna', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Sidda', 'Sidda1', '', 1, 3, 'Sidda Raju', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Subhash', 'Subhash1', '', 1, 202, 'Subhash Chandra Gupta', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('Sundarali', 'Sundarali1', '', 1, 204, 'M. Sundaralingam', 'SUNDARALINGAM MURUGATHITHAN', NULL, NULL, NULL, NULL, NULL, NULL), ('Vinod', 'Vinod1', '', 1, 107, 'A Vinod Kumar', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('vinoy ', ' vinoy1', '', 1, 301, ' Vinoy ', NULL, NULL, NULL, NULL, NULL, NULL, NULL), ('vishesh', 'vishesh1', '', 1, 208, 'Vishesh Nigam', 'vishesh', NULL, NULL, NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `User_Details` ENABLE KEYS */;


# Dumping structure for table sql6134070.User_Expense
DROP TABLE IF EXISTS `User_Expense`;
CREATE TABLE IF NOT EXISTS `User_Expense` (
  `ID` bigint(50) NOT NULL AUTO_INCREMENT,
  `User_ID` bigint(50) DEFAULT '0',
  `Expense_Type` varchar(50) DEFAULT '0',
  `Amount` double DEFAULT '0',
  `Varified` bit(1) NOT NULL,
  `Varified_By` bigint(50) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6134070.User_Expense


# Dumping structure for table sql6134070.User_Payments
DROP TABLE IF EXISTS `User_Payments`;
CREATE TABLE IF NOT EXISTS `User_Payments` (
  `Payment_Id` mediumint(10) NOT NULL,
  `User_Id` varchar(20) NOT NULL,
  `Flat_Id` tinyint(10) NOT NULL,
  `Expense_Type_Id` tinyint(10) NOT NULL,
  `Amount` float NOT NULL,
  `Payment_Mode_Id` tinyint(10) NOT NULL,
  `Transaction_Id` mediumint(10) DEFAULT NULL,
  `User_Comment` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Payment_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6134070.User_Payments


# Dumping structure for table sql6134070.User_Type
DROP TABLE IF EXISTS `User_Type`;
CREATE TABLE IF NOT EXISTS `User_Type` (
  `User_Type_Id` int(10) NOT NULL DEFAULT '0',
  `Type` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`User_Type_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table sql6134070.User_Type
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
