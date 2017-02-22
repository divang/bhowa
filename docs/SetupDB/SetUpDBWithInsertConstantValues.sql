# --------------------------------------------------------
# Host:                         sql6.freemysqlhosting.net
# Database:                     sql6151810
# Server version:               5.5.49-0ubuntu0.14.04.1
# Server OS:                    debian-linux-gnu
# HeidiSQL version:             5.0.0.3031
# Date/time:                    2017-01-01 20:42:53
# --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
# No rows in table sql6151810.Apartment_Earning

# No rows in table sql6151810.Apartment_Expense

# Dumping data for table sql6151810.Authorizations: 17 rows
/*!40000 ALTER TABLE `Authorizations` DISABLE KEYS */;
INSERT INTO `Authorizations` (`Auth_Id`, `Type`) VALUES (0, 'ADMIN'), (1, 'MY_DUES_VIEWS'), (2, 'NOTIFICATION_SEND'), (3, 'USER_DETAIL_VIEW'), (4, 'USER_DETAIL_CREATE'), (5, 'FLAT_DETAIL_VIEW'), (6, 'FLAT_DETAIL_CREATE'), (7, 'LOGIN_VIEW'), (8, 'LOGIN_CREATE'), (9, 'TRANSACTION_HOME_VIEW'), (10, 'RAW_DATA_VIEW'), (11, 'TRANSACTIONS_DETAIL_VIEW'), (12, 'PDF_TRANSACTION_VIEW'), (13, 'PDF_TRANSACTION_UPLOAD_TO_STAGING_TABLE'), (14, 'MAP_USER_WITH_MONTHLY_PDF_NAME'), (15, 'MONTHLY_MAINTENANCE_GENERATOR'), (16, 'VERIFIED_PDF_TRANSACTIONS_UPLOAD');
/*!40000 ALTER TABLE `Authorizations` ENABLE KEYS */;

# No rows in table sql6151810.Bank_Statement

# Dumping data for table sql6151810.Expense_Type: 36 rows
/*!40000 ALTER TABLE `Expense_Type` DISABLE KEYS */;
INSERT INTO `Expense_Type` (`Expense_Type_Id`, `Type`, `Payable_Priority`) VALUES (0, 'Advance_Payment', -1), (1, 'Monthly_Maintenance', 1), (2, 'Initial_Payable', 4), (3, 'Khata_Payable', 3), (4, 'Generator_Repair_Servicing', NULL), (5, 'Lift_AMC', NULL), (6, 'Lift_Repair\r\n', NULL), (7, 'Plumbing', NULL), (8, 'Electrical_Repair', NULL), (9, 'Tank_Cleaning_Repairing', NULL), (10, 'Lawyer', NULL), (11, 'Miscellaneous', NULL), (12, 'Intercom_AMC\r\n', NULL), (13, 'Intercom_Purchase_Repair', NULL), (14, 'Fire_Extinguisher', NULL), (15, 'Security_Related', NULL), (16, 'Apartment_InfraStructure_Repair\r\n', NULL), (17, 'Children_Park', NULL), (18, 'Septick_Tank_ Pipe_Cleaning', NULL), (19, 'Club_House', NULL), (20, 'Alamari_Purchase', NULL), (21, 'Security_Guards\r\n', NULL), (22, 'House_Keeping_Labour', NULL), (23, 'Common_Electricity', NULL), (24, 'Water_Tankers ', NULL), (25, 'Gardening', NULL), (26, 'Deisel_For_Generator\r\n', NULL), (27, 'House_Keeping_Consumables', NULL), (28, 'Pest_Control', NULL), (29, 'Generator_AMC', NULL), (30, 'Flat_Old_Dues', 2), (31, 'Waste_Disposal', NULL), (32, 'Gym', NULL), (33, 'MotorRepair', NULL), (34, 'Penalty', NULL), (35, 'Club_Store_Earning', NULL);
/*!40000 ALTER TABLE `Expense_Type` ENABLE KEYS */;

# No rows in table sql6151810.Flat

# No rows in table sql6151810.Flat_Wise_Payable

# No rows in table sql6151810.Login

# No rows in table sql6151810.Notification

# Dumping data for table sql6151810.Society: 1 rows
/*!40000 ALTER TABLE `Society` DISABLE KEYS */;
INSERT INTO `Society` (`Society_Id`, `Society_Name`, `Database_URL`, `Database_User`, `Database_Password`, `Email_Id`) VALUES (1, 'BHOWA', 'jdbc:mysql://sql6.freemysqlhosting.net:3306/sql6151810', 'sql6151810', 'iSDBwCe8dF', 'bhowa.begurwoods@gmail.com');
/*!40000 ALTER TABLE `Society` ENABLE KEYS */;

# No rows in table sql6151810.Transactions_BalanceSheet

# No rows in table sql6151810.Transactions_Staging_Data

# No rows in table sql6151810.Transactions_Verified

# Dumping data for table sql6151810.User_Details: 1 rows
/*!40000 ALTER TABLE `User_Details` DISABLE KEYS */;
INSERT INTO `User_Details` (`User_Id`, `Login_Id`, `User_Type`, `Status`, `Flat_Id`, `Name`, `Name_Alias`, `Mobile_No`, `Moble_No_Alternate`, `Email_Id`, `Address`, `Flat_Join_Date`, `Flat_Left_Date`, `Auth_Ids`, `Service_Type_Id`) VALUES ('adminU', 'admin', '', 1, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL);
/*!40000 ALTER TABLE `User_Details` ENABLE KEYS */;

# No rows in table sql6151810.User_Paid

# Dumping data for table sql6151810.User_Payment_Status: 3 rows
/*!40000 ALTER TABLE `User_Payment_Status` DISABLE KEYS */;
INSERT INTO `User_Payment_Status` (`Payment_Status_Id`, `Status_Type`) VALUES (2, 'Full_Paid'), (1, 'Not_Paid'), (3, 'Partial_Paid');
/*!40000 ALTER TABLE `User_Payment_Status` ENABLE KEYS */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
