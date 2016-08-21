# --------------------------------------------------------
# Host:                         dbbhra-cluster.cluster-cr39zxmr47h3.us-east-1.rds.amazonaws.com
# Database:                     BHOWA
# Server version:               5.6.10
# Server OS:                    Linux
# HeidiSQL version:             5.0.0.3031
# Date/time:                    2016-08-21 19:45:58
# --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
# Dumping database structure for BHOWA
DROP DATABASE IF EXISTS `BHOWA`;
CREATE DATABASE IF NOT EXISTS `BHOWA` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `BHOWA`;


# Dumping structure for table BHOWA.Bank_Statement
DROP TABLE IF EXISTS `Bank_Statement`;
CREATE TABLE IF NOT EXISTS `Bank_Statement` (
  `Statement_FileName` varchar(100) NOT NULL,
  `Uploaded_Date` datetime NOT NULL,
  `Status` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table BHOWA.Bank_Statement: 19 rows
/*!40000 ALTER TABLE `Bank_Statement` DISABLE KEYS */;
INSERT IGNORE INTO `Bank_Statement` (`Statement_FileName`, `Uploaded_Date`, `Status`) VALUES ('/storage/1219-2C11/Download/re', '2016-08-20 00:00:00', ''), ('/storage/emulated/0/Download/R', '2016-08-20 00:00:00', ''), ('/storage/1219-2C11/Download/re', '2016-08-20 00:00:00', ''), ('/storage/1219-2C11/Download/re', '2016-08-21 00:00:00', ''), ('/storage/1219-2C11/Download/re', '2016-08-21 00:00:00', ''), ('/storage/1219-2C11/Download/re', '2016-08-21 00:00:00', ''), ('/storage/1219-2C11/Download/re', '2016-08-21 00:00:00', ''), ('/storage/1219-2C11/Download/re', '2016-08-21 00:00:00', ''), ('/storage/1219-2C11/Download/re', '2016-08-21 00:00:00', ''), ('/storage/1219-2C11/Download/re', '2016-08-21 00:00:00', ''), ('/storage/1219-2C11/Download/re', '2016-08-21 00:00:00', ''), ('/storage/1219-2C11/Download/re', '2016-08-21 00:00:00', ''), ('/storage/1219-2C11/Download/re', '2016-08-21 00:00:00', ''), ('/storage/1219-2C11/Download/re', '2016-08-21 00:00:00', ''), ('/storage/1219-2C11/Download/re', '2016-08-21 00:00:00', ''), ('/storage/1219-2C11/Download/re', '2016-08-21 00:00:00', ''), ('/storage/emulated/0/Download/R', '2016-08-21 00:00:00', ''), ('/storage/emulated/0/Download/R', '2016-08-21 00:00:00', ''), ('/storage/emulated/0/Download/R', '2016-08-21 00:00:00', '');
/*!40000 ALTER TABLE `Bank_Statement` ENABLE KEYS */;


# Dumping structure for table BHOWA.Error_Log
DROP TABLE IF EXISTS `Error_Log`;
CREATE TABLE IF NOT EXISTS `Error_Log` (
  `User_Id` bigint(20) DEFAULT NULL,
  `Log` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table BHOWA.Error_Log


# Dumping structure for table BHOWA.Expense_Type
DROP TABLE IF EXISTS `Expense_Type`;
CREATE TABLE IF NOT EXISTS `Expense_Type` (
  `Type` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table BHOWA.Expense_Type


# Dumping structure for table BHOWA.Row_Data
DROP TABLE IF EXISTS `Row_Data`;
CREATE TABLE IF NOT EXISTS `Row_Data` (
  `Transaction_Raw_Line_Parse_From_PDF` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table BHOWA.Row_Data


# Dumping structure for table BHOWA.Transactions
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

# No rows in table BHOWA.Transactions


# Dumping structure for table BHOWA.Transactions_Staging_Data
DROP TABLE IF EXISTS `Transactions_Staging_Data`;
CREATE TABLE IF NOT EXISTS `Transactions_Staging_Data` (
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

# No rows in table BHOWA.Transactions_Staging_Data


# Dumping structure for table BHOWA.User
DROP TABLE IF EXISTS `User`;
CREATE TABLE IF NOT EXISTS `User` (
  `User_Id` varchar(20) NOT NULL,
  `Password` varchar(20) NOT NULL,
  `Status` bit(1) NOT NULL,
  `Flat_No` int(3) DEFAULT NULL,
  `Name` varchar(50) NOT NULL,
  `Name_Alias` varchar(400) DEFAULT NULL,
  `Mobile_No` bigint(13) DEFAULT NULL,
  `Email_Id` varchar(30) DEFAULT NULL,
  `Maintenance_Monthly` float DEFAULT NULL,
  `Address` varchar(100) DEFAULT NULL,
  `Block` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`User_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table BHOWA.User: 2 rows
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT IGNORE INTO `User` (`User_Id`, `Password`, `Status`, `Flat_No`, `Name`, `Name_Alias`, `Mobile_No`, `Email_Id`, `Maintenance_Monthly`, `Address`, `Block`) VALUES ('divang', 'divang1', '', 309, 'Divang Sharma', 'divang', 9241797239, 'divang.s@gmail.com', 3750, 'Begur Heights', NULL), ('vishesh', 'vishesh1', '', 208, 'Vishesh Nigam', 'vishesh', NULL, NULL, 3170, NULL, NULL);
/*!40000 ALTER TABLE `User` ENABLE KEYS */;


# Dumping structure for table BHOWA.User_Activity_Logging
DROP TABLE IF EXISTS `User_Activity_Logging`;
CREATE TABLE IF NOT EXISTS `User_Activity_Logging` (
  `UserName` varchar(50) NOT NULL,
  `Mobile` varchar(50) DEFAULT NULL,
  `Activity` varchar(20) NOT NULL,
  `Comment` varchar(100) DEFAULT NULL,
  `Time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# No rows in table BHOWA.User_Activity_Logging


# Dumping structure for table BHOWA.User_Expense
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

# No rows in table BHOWA.User_Expense
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
