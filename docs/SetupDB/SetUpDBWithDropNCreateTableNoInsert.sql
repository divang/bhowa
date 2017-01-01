# --------------------------------------------------------
# Host:                         sql6.freemysqlhosting.net
# Database:                     sql6151810
# Server version:               5.5.49-0ubuntu0.14.04.1
# Server OS:                    debian-linux-gnu
# HeidiSQL version:             5.0.0.3031
# Date/time:                    2017-01-01 20:44:57
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

# Data exporting was unselected.


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

# Data exporting was unselected.


# Dumping structure for table sql6151810.Authorizations
DROP TABLE IF EXISTS `Authorizations`;
CREATE TABLE IF NOT EXISTS `Authorizations` (
  `Auth_Id` int(10) NOT NULL,
  `Type` varchar(50) NOT NULL,
  PRIMARY KEY (`Type`),
  UNIQUE KEY `Auth_Id` (`Auth_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Data exporting was unselected.


# Dumping structure for table sql6151810.Bank_Statement
DROP TABLE IF EXISTS `Bank_Statement`;
CREATE TABLE IF NOT EXISTS `Bank_Statement` (
  `Bank_Statement_FileName` varchar(50) NOT NULL,
  `Uploaded_Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Uploaded_LoginId` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Data exporting was unselected.


# Dumping structure for table sql6151810.Expense_Type
DROP TABLE IF EXISTS `Expense_Type`;
CREATE TABLE IF NOT EXISTS `Expense_Type` (
  `Expense_Type_Id` int(10) NOT NULL,
  `Type` varchar(50) NOT NULL,
  `Payable_Priority` int(10) DEFAULT NULL,
  PRIMARY KEY (`Expense_Type_Id`),
  UNIQUE KEY `Type` (`Type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Data exporting was unselected.


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

# Data exporting was unselected.


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

# Data exporting was unselected.


# Dumping structure for table sql6151810.Login
DROP TABLE IF EXISTS `Login`;
CREATE TABLE IF NOT EXISTS `Login` (
  `Login_Id` varchar(20) NOT NULL,
  `Password` varchar(20) NOT NULL,
  `Status` tinyint(1) NOT NULL DEFAULT '1',
  `Society_Id` int(100) DEFAULT NULL,
  PRIMARY KEY (`Login_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Data exporting was unselected.


# Dumping structure for table sql6151810.Notification
DROP TABLE IF EXISTS `Notification`;
CREATE TABLE IF NOT EXISTS `Notification` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `Message` varchar(100) DEFAULT '0',
  `Time` datetime DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Data exporting was unselected.


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

# Data exporting was unselected.


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

# Data exporting was unselected.


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

# Data exporting was unselected.


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

# Data exporting was unselected.


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

# Data exporting was unselected.


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

# Data exporting was unselected.


# Dumping structure for table sql6151810.User_Payment_Status
DROP TABLE IF EXISTS `User_Payment_Status`;
CREATE TABLE IF NOT EXISTS `User_Payment_Status` (
  `Payment_Status_Id` int(10) NOT NULL AUTO_INCREMENT,
  `Status_Type` varchar(20) NOT NULL,
  PRIMARY KEY (`Payment_Status_Id`),
  UNIQUE KEY `Status_Type` (`Status_Type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Data exporting was unselected.
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
