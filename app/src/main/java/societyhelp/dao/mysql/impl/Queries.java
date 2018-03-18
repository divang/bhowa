package societyhelp.dao.mysql.impl;

import java.sql.Timestamp;

public class Queries {

	//Queries
	public static final String loginQuery =
			"select s.Database_URL, s.Database_User, s.Database_Password, s.Society_Name, s.Society_Id, l.isAdmin from " +
					" login l" +
					" inner join " +
					" society s " +
					" on l.Society_Id = s.Society_Id " +
					" where l.Login_Id= ? " +
					" and l.Password = ?" +
					" and l.Status = 1";

   public static final String activityLoggingQuery =
			"Insert into user_activity_logging (UserName, Mobile, Activity, Comment, Time) values (? ,? ,?, ?, ?)";

	public static final String statementProcessedQuery =
			"Insert into bank_statement(Bank_Statement_FileName, Uploaded_Date, Uploaded_LoginId) values (?, ?, ?)";

	public static final String isStatementProcessedQuery =
			"Select * FROM bank_statement WHERE Statement_FileName = ?";

	public static final String transactionStagingQuery =
			"Insert into transactions_staging_data(" +
					"StatementID, Name, Amount, "
					+ "Transaction_Date, Transaction_Flow, Transaction_Mode, " +
					"Transaction_Reference, Upload_Date, Uploaded_LoginId) "
					+ "values(" +
					"?, ?, ?, " +
					"?, ?, ?, " +
					"?, '" + new Timestamp(System.currentTimeMillis())+"', ?)";

	public static final String cleanTransactionStagingDataQuery =
			"delete from transactions_staging_data";

	public static final String allTransactionStagingDataQuery =
			"SELECT Transaction_ID,StatementID,Name,Amount," +
					"Transaction_Date,Transaction_Flow,Transaction_Mode,Transaction_Reference," +
					"Upload_Date,Uploaded_LoginId FROM transactions_staging_data";

	public static final String insertRawDataQuery =
			"Insert into row_data(Transaction_Raw_Line_Parse_From_PDF) values (?)";

	public static final String selectAllRawQuery =
			"select * from row_data";

	public static final String deleteAllRawQuery =
			"delete from row_data";

	public static final String deleteAllBackupDataQuery =
			"delete from transactions_balancesheet_rollback_data";

	public static final String allUsersQuery =
			"SELECT * FROM user_details";
	
	public static final String allSocietyUsersQuery =
		"SELECT * FROM user_details ud INNER JOIN login l on ud.Login_Id = l.Login_Id where l.Society_Id=?";
	
	public static final String expenseTypeQuery =
			"SELECT * FROM expense_type";

	public static final String errorLoggingQuery =
			"Insert into error_log(User_Id, Log) values (?, ?)";

	public static final String allTransactionStagingUsersQuery =
			"select distinct(Name) from transactions_staging_data";

	public static final String updateAliasUserId =
			"UPDATE user_details SET Name_Alias = ? WHERE User_Id = ?";

	public static final String createLoginQuery =
			"Insert into login(Login_Id, Password, isAdmin, Society_Id) " +
					" select  ?, ?, ?, ?";

	public static final String insertFlatDetailsQuery =
			"Insert into flat(Flat_Id,Flat_Number,Area,Maintenance_Amount,Block_Number, Society_Id) " +
					"values (?, ?, ?, ?, ?, ?)";

	public static final String selectAllLoginQuery =
			//"SELECT * FROM login"; //Old query
			//login is move to Master database;
			"select l2.Login_Id, l2.Password, l2.Status, l2.Society_Id from login l1 " +
					"inner join " +
					"login l2 " +
					"on l1.Society_Id = l2.Society_Id " +
					"where " +
					"l1.Login_Id = ? ";


	public static final String selectAllAssignedLoginIdsQuery =
			"select Login_Id from user_details";

	public static final String selectAllFlatQuery =
			"SELECT f.Flat_Id,f.Flat_Number,f.Area,f.Maintenance_Amount," +
					"f.Block_Number,f.Status,group_concat(ud.Name, '')" +
					"FROM flat f left join user_details ud on ud.Flat_ID = f.Flat_Id " +
					"where ud.User_Type = 'Owner' " +
					"group by f.Flat_Id " +
					"order by f.Flat_Id ";

	public static final String selectAllFlatInSocietyQuery =
			"SELECT f.Flat_Id,f.Flat_Number,f.Area,f.Maintenance_Amount," +
					"f.Block_Number,f.Status " +
					"FROM flat f  " +
					"where f.Society_Id = ? " +
					"order by f.Flat_Id ";

	public static final String selectFinalTransactionQuery =
			"SELECT  Transaction_ID,StatementID,tsd.Name,Amount," +
					"Transaction_Date,Transaction_Flow,Transaction_Mode,Transaction_Reference," +
					"ud.User_Id,ud.Flat_Id " +
					"FROM transactions_staging_data as tsd " +
					"left join " +
					"user_details as ud " +
					"on (UPPER(ud.Name) LIKE CONCAT('%', UPPER(tsd.Name),'%')) " +
					"OR (UPPER(ud.Name_Alias) LIKE CONCAT('%', UPPER(tsd.Name), '%'))";

	public static final String getUnSettledCreditTransactionQuery =
			"SELECT  Transaction_ID,StatementID,tsd.Name,Amount," +
					"Transaction_Date,Transaction_Flow,Transaction_Mode,Transaction_Reference," +
					"ud.User_Id,ud.Flat_Id " +
					"FROM transactions_staging_data as tsd " +
					"left join " +
					"user_details as ud " +
					"on (UPPER(ud.Name) LIKE CONCAT('%', UPPER(tsd.Name),'%')) " +
					"OR (UPPER(ud.Name_Alias) LIKE CONCAT('%', UPPER(tsd.Name), '%'))" +
					"where tsd.Transaction_Flow = 'Credit' " +
					"and User_Id is NULL";

	public static final String getUnSettledDebitTransactionQuery =
			"SELECT  Transaction_ID,StatementID,tsd.Name,Amount," +
					"Transaction_Date,Transaction_Flow,Transaction_Mode,Transaction_Reference," +
					"ud.User_Id,ud.Flat_Id " +
					"FROM transactions_staging_data as tsd " +
					"left join " +
					"user_details as ud " +
					"on (UPPER(ud.Name) LIKE CONCAT('%', UPPER(tsd.Name),'%')) " +
					"OR (UPPER(ud.Name_Alias) LIKE CONCAT('%', UPPER(tsd.Name), '%'))" +
					"where tsd.Transaction_Flow = 'Debit'";

	public static final String deleteUnSettledTransactionQuery =
			"delete FROM transactions_staging_data where Transaction_ID = ?";

	public static final String insertFinalTransactionQuery =
			"Insert into transactions_verified (" +
					"Amount, Transaction_Date,Transaction_Flow," +
					"Transaction_Mode,Transaction_Reference,User_Id," +
					"Flat_Id,Verified_By,Splitted, Auto_Split_Id) " +
					"values (" +
					"? , ?, ?, " +
					"?, ?, ?, " +
					"?, ?, ?, ?) ";

	public static final String insertSettledCreditTransactionQuery =
			"Insert into transactions_verified (" +
					"Amount, Transaction_Date,Transaction_Flow," +
					"Transaction_Mode,Transaction_Reference,User_Id," +
					"Flat_Id,Verified_By,Splitted, Auto_Split_Id) " +
					"values (" +
					"? , ?, ?, " +
					"?, ?, ?, " +
					"?, ?, ?, ?) ";

	public static final String insertUserDetailsQuery =
			"Insert into user_details(" +
					"User_Id, User_Type, Flat_Id," +
					"Name, Name_Alias,Mobile_No," +
					"Moble_No_Alternate, Email_Id, Address," +
					"Flat_Join_Date, Flat_Left_Date, Login_Id, Auth_Ids)" +
					" values (" +
					"?, ?, ?, " +
					"?, ?, ?," +
					"?, ?, ?," +
					"?, ?, ? , ?)";

	public static final String myTransactionsQuery =
			"SELECT Transaction_From_bank_statement_ID,Amount,Transaction_Date,Transaction_Flow," +
					"Transaction_Mode,Transaction_Reference, ud.Flat_Id,Verified_By,Splitted " +
					"FROM transactions_verified tv inner " +
					"join user_details ud " +
					"on tv.User_Id = ud.User_Id " +
					"inner join login l " +
					"on l.Login_Id = ud.Login_Id " +
					"where l.Login_Id = ?";

	public static final String myDueQuery =
			"Select " +
					"IFNULL((select sum(fwp.Amount) " +
					"from flat as f " +
					"inner join " +
					"flat_wise_payable as fwp on f.Flat_ID = fwp.Flat_ID " +
					"where f.Flat_Id = ? " +
					"group by f.Flat_Id), 0) " +
					" - " +
					"IFNULL((select sum(up.Amount) " +
					"from flat as f " +
					"inner join " +
					"user_paid as up on up.Flat_ID = f.Flat_Id " +
					"where f.Flat_Id = ? " +
					"group by f.Flat_Id), 0) " +
					" - " +
					"IFNULL((select sum(tv.Amount) " +
					"from flat as f " +
					"inner join " +
					"transactions_verified as tv on tv.Flat_Id = f.Flat_Id " +
					"where f.Flat_Id = ? AND tv.Transaction_Flow = 'Credit' "+
					"group by f.Flat_Id) , 0) " +
					" + " +
					"IFNULL((select sum(tv.Amount) " +
					"from flat as f " +
					"inner join " +
					"transactions_verified as tv on tv.Flat_Id = f.Flat_Id " +
					"where f.Flat_Id = ? AND tv.Transaction_Flow = 'Debit'  " +
					"group by f.Flat_Id " +
					"),0) as MyDue ";

	public static final String myDetailsQuery =
			"SELECT User_Id,Login_Id,User_Type,Status,Flat_Id,Name,Name_Alias,Mobile_No," +
					"Moble_No_Alternate,Email_Id,Address,Flat_Join_Date,Flat_Left_Date,Auth_Ids " +
					"FROM user_details where Login_Id = ?";

	public static final String addlatMaintenancePayablesQuery =
			"insert into flat_wise_payable (Flat_Id, Amount, Month, Year, Expense_Type_Id) " +
					"select f.Flat_Id, f.Maintenance_Amount, ?, ?, ? " +
					"from flat f";

	public static final String addAllFlatPayablesQuery =
			"insert into flat_wise_payable (Flat_Id, Amount, Month, Year, Expense_Type_Id) " +
					"select f.Flat_Id, ?, ?, ?, ? " +
					"from flat f";

	public static final String addSingleFlatPayablesQuery =
			"insert into flat_wise_payable (Flat_Id, Amount, Month, Year, Expense_Type_Id, Comments, Payment_Status_ID) " +
					"values (?, ?, ?, ?, ?, ?, ? )";

	public static final String flatWiseTransactionsQuery =
			"select * from " +
					"( " +
					"select Flat_Id as fid,'Payable', Amount, cast(rtrim(year *10000+ month) as datetime) as Date from flat_wise_payable " +
					" union " +
					"select Flat_Id as fid,'Paid', Amount, Paid_Date from user_paid " +
					" union " +
					"select Flat_Id as fid,'Paid', Amount, Transaction_Date from transactions_verified where Transaction_Flow = 'Credit' " +
					" union " +
					"select Flat_Id as fid,'Payable', Amount, Transaction_Date from transactions_verified where Transaction_Flow = 'Debit' " +
					") T " +
					"where fid = ? ";

	public static final String addUserPaymentQuery =
			"insert into user_paid (User_ID,Flat_ID, Amount, Paid_Date, User_Comment, Expense_Type_Id) " +
					" select ? ,?, ?, ?, ?, et.Expense_Type_Id " +
					" from expense_type et where type = ? ";

	public static final String unVerifiedCashPaymentByUserQuery =
			"select " +
					" User_Cash_Payment_ID,User_ID,Flat_ID,Amount,Paid_Date,u.Expense_Type_Id,User_Comment,Admin_Comment " +
					" from user_paid u " +
					" left join expense_type e " +
					" on u.Expense_Type_Id = e.Expense_Type_Id " +
					" where Verified = 0";


	public static final String unSplittedCashPaymentByUserQuery =
			"select " +
					" User_Cash_Payment_ID,User_ID,Flat_ID,Amount,Paid_Date,u.Expense_Type_Id,User_Comment,Admin_Comment " +
					" from user_paid u " +
					" left join expense_type e " +
					" on u.Expense_Type_Id = e.Expense_Type_Id " +
					" where Splitted = 0 ";

	public static final String unSplittedApartmentExpensePaymentQuery =
			"select Apartment_Cash_Expense_ID,Expense_Type_Id,Amount " +
					" ,Expend_Date,Expend_By_UserId,Verified " +
					" ,Verified_By,Expendy_Comment,Admin_Comment,Splitted " +
					" from apartment_expense " +
					" where Splitted = 0 ";

	public static final String saveVerifiedCashPaymentQuery =
			"update user_paid set Verified=1, Verified_by=? " +
					"where User_Cash_Payment_ID in (?)";

	/*
	"Payment_Status_Id";"Status_Type"
	"1";"Not Paid"
	"2";"Full Paid"
	"3";"Partial Paid"
   */
	public static final String unPaidFlatWiseAmountQuery =
			"SELECT Flat_Wise_Payable_ID,Flat_Id,Status,Month," +
					"Year,Amount,et.Expense_Type_Id,Comments," +
					"Payment_IDs,Payment_Status_ID,et.Payable_Priority " +
					"FROM flat_wise_payable fwp " +
					"left join " +
					"expense_type et " +
					"on et.Expense_Type_Id = fwp.Expense_Type_Id " +
					"where Payment_Status_ID in (1,3)";

	public static final String payableAndPaidFlatWiseAmountQuery =
			"select fwp.Flat_Wise_Payable_ID, fwp.Amount Payable, sum(tbs.Amount) Paid, " +
			"et.Type, et.Expense_Type_Id, et.Payable_Priority, " +
			"fwp.Flat_Id, fwp.Status, " +
			"fwp.Month, fwp.Year,  " +
			"fwp.Comments, fwp.Payment_Status_ID " +
			"from flat_wise_payable as fwp " +
			//"left join  " +
			//"flat_wise_payable_paid_mapping as fwppm " +
			//"on fwp.Flat_Wise_Payable_ID = fwppm.Flat_Wise_Payable_ID " +
			"left join transactions_balancesheet as tbs " +
			"on fwp.Flat_Wise_Payable_ID = tbs.Flat_Wise_Payable_ID " +
			"left join expense_type et on et.Expense_Type_Id = fwp.Expense_Type_Id " +
			"where Payment_Status_ID in (1,3) " +
			"group by fwp.Flat_Wise_Payable_ID";

	public static final String initialPayableFlatWiseAmountQuery = 
			"select sum(fwp.Amount) InitialPayable, "+ 
			"fwp.Flat_Id, fwp.Status "+ 																																								
			"from flat_wise_payable as fwp  "+ 
			"where Payment_Status_ID in (4,5)  "+
			"group by fwp.Flat_Id";
	
	public static final String initialPaidFlatWiseAmountQuery =
			"SELECT tv.Flat_Id, sum(tbs.Amount), tbs.Flat_Wise_Payable_ID "+
			"FROM transactions_balancesheet tbs  "+
			"left join transactions_verified tv  "+
			"on tbs.Transaction_From_Bank_Statement_ID = tv.Transaction_From_Bank_Statement_ID "+ 
			"WHERE tbs.Flat_Wise_Payable_ID in (-1, -2) "+
			"and tbs.Transaction_Flow = 'Credit'  "+
			"and tv.Flat_Id is not null "+
			"group by tv.Flat_Id";
	
	public static final String unSplittedCreditTransactionsQuery =
			"SELECT Transaction_From_Bank_Statement_ID,Amount,Transaction_Date,Transaction_Flow," +
					"Transaction_Mode,Transaction_Reference,User_Id,Flat_Id," +
					"Verified_By,Splitted " +
					"FROM transactions_verified " +
					"where splitted=0 and Transaction_Flow = 'Credit'";

	public static final String unSplittedDebitTransactionsQuery =
			"SELECT Transaction_From_Bank_Statement_ID,Amount,Transaction_Date,Transaction_Flow," +
					"Transaction_Mode,Transaction_Reference,tv.User_Id,ud.Service_Type_Id," +
					"Verified_By,Splitted " +
					"FROM transactions_verified tv " +
					"left join " +
					"user_details ud on ud.User_Id = tv.User_Id " +
					"where splitted=0 and Transaction_Flow = 'Debit'";

	public static final String advanceTransactionsQuery =
			"SELECT tbs.Balance_Sheet_Transaction_ID, tbs.Amount, tbs.Verified_By_Admin, tbs.Verified_By_User,tbs.Expense_Type_Id,"+
					"tbs.Transaction_From_Bank_Statement_ID, tbs.User_Cash_Payment_ID, tbs.Transaction_Expense_Id, tv.User_Id User_Id_tv, tv.Flat_Id User_Id_tv,"+
					"ud.User_Id User_Id_ud, ud.Flat_Id Flat_Id_ud, ae.Expend_By_UserId User_Id_ae "+
					"FROM transactions_balancesheet tbs "+
					"left join transactions_verified tv on tbs.Transaction_From_Bank_Statement_ID = tv.Transaction_From_Bank_Statement_ID "+
					"left join user_paid ud on tbs.User_Cash_Payment_ID = ud.User_Cash_Payment_ID   "+
					"left join apartment_expense ae on tbs.Transaction_Expense_Id = ae.Apartment_Cash_Expense_ID  "+
					"WHERE tbs.Expense_Type_Id=0";

	public static final String balanceSheetQuery =
			"SELECT tbs.Balance_Sheet_Transaction_ID, tbs.Amount, tbs.Verified_By_Admin, tbs.Verified_By_User,tbs.Expense_Type_Id,"+
					"tbs.Transaction_From_Bank_Statement_ID, tbs.User_Cash_Payment_ID, tbs.Transaction_Expense_Id, tv.User_Id User_Id_tv, tv.Flat_Id User_Id_tv,"+
					"ud.User_Id User_Id_ud, ud.Flat_Id Flat_Id_ud, ae.Expend_By_UserId User_Id_ae, "+
					"tbs.Transaction_Flow, et.Type, tv.Transaction_Mode,"+
					"tv.Transaction_Date UserPaidDate, aearn.Earned_Date ApartmentEarnDate, ae.Expend_Date ApartmentExpenseDate,"+
					"f.Flat_Number, f.Block_Number, f.Area, userD.Name, ud.Paid_Date  "+
					" FROM transactions_balancesheet tbs " +
					" left join transactions_verified tv on tbs.Transaction_From_Bank_Statement_ID = tv.Transaction_From_Bank_Statement_ID " +
					" left join user_paid ud on tbs.User_Cash_Payment_ID = ud.User_Cash_Payment_ID  " +
					" left join apartment_expense ae on tbs.Transaction_Expense_Id = ae.Apartment_Cash_Expense_ID " +
					" left join expense_type et on et.Expense_Type_Id = tbs.Expense_Type_Id" +
					" left join flat f on f.Flat_Id = tv.Flat_Id" +
					" left join user_details userD on userD.User_Id = tv.User_Id" +
					" left join apartment_earning aearn on aearn.Apartment_Earning_ID = tbs.Apartment_Earning_ID";

	public static final String paidFlatnExpenseTypeWiseAmountQuery =
			"select  User_Cash_Payment_ID,User_ID,Flat_ID,sum(Amount) as Total_Paid," +
					"Paid_Date,Type,User_Comment,Admin_Comment " +
					"from user_paid u " +
					"left join " +
					"expense_type e " +
					"on u.Expense_Type_Id = e.Expense_Type_Id " +
					"where Verified = 1 " +
					"group by u.Flat_ID, u.Expense_Type_Id";

	public static final String allFlatWiseAmountQuery =
			"SELECT Flat_Wise_Payable_ID,Flat_Id,Status,Month, " +
					"Year,Amount,Type,Comments, " +
					"Status_Type " +
					"FROM flat_wise_payable fwp " +
					"left join expense_type et " +
					"on fwp.Expense_Type_Id = et.Expense_Type_Id " +
					"left join user_payment_status ups " +
					"on ups.Payment_Status_Id =fwp.Payment_Status_ID";

	public static final String allAuthorizationsQuery =
			"SELECT Auth_Id,Type FROM Authorizations";

	public static final String insertToBalanceQuery =
			"INSERT INTO transactions_balancesheet (" +
					"Amount,Verified_By_Admin,Verified_By_User,Expense_Type_Id," +
					"Transaction_From_Bank_Statement_ID,User_Cash_Payment_ID,Transaction_Expense_Id,Transaction_Flow,Flat_Wise_Payable_ID,Apartment_Earning_ID,Auto_Split_Id)" +
					"VALUES (?, ?, ?, ?," +
					" ?, ?, ?, ?, ?, ?, ?)";

	public static final String insertToBalanceRollbackDataQuery =
			"INSERT INTO transactions_balancesheet_rollback_data " +
					" (Amount,Verified_By_Admin,Verified_By_User,Expense_Type_Id,Transaction_From_Bank_Statement_ID,User_Cash_Payment_ID,Transaction_Expense_Id,Transaction_Flow,Flat_Wise_Payable_ID,Apartment_Earning_ID, Balance_Sheet_Transaction_ID) " +
					" SELECT Amount,Verified_By_Admin,Verified_By_User,Expense_Type_Id,Transaction_From_Bank_Statement_ID,User_Cash_Payment_ID,Transaction_Expense_Id,Transaction_Flow,Flat_Wise_Payable_ID,Apartment_Earning_ID, Balance_Sheet_Transaction_ID " +
					" FROM transactions_balancesheet" +
					" WHERE Balance_Sheet_Transaction_ID=?";

	public static final String insertFromBalanceRollbackDataQuery =
			"INSERT INTO transactions_balancesheet  (Amount,Verified_By_Admin,Verified_By_User,Expense_Type_Id,"+
					" Transaction_From_Bank_Statement_ID,User_Cash_Payment_ID,Transaction_Expense_Id," +
					" Transaction_Flow,Flat_Wise_Payable_ID,Apartment_Earning_ID) "+
					" SELECT Amount,Verified_By_Admin,Verified_By_User,Expense_Type_Id,Transaction_From_Bank_Statement_ID,User_Cash_Payment_ID,"+
					" Transaction_Expense_Id,Transaction_Flow,Flat_Wise_Payable_ID,Apartment_Earning_ID "+
					" FROM transactions_balancesheet_rollback_data";

	public static final String deleteLastAutoSplitQuery = "delete from last_auto_split";

	public static final String insertLastAutoSplitQuery = "Insert into last_auto_split values (?)";

	public static final String getLastAutoSplitQuery = "SELECT Auto_Split_Id FROM last_auto_split";

	public static final String updateBalanceSheet_Transaction =
			"UPDATE transactions_balancesheet SET Expense_Type_Id=? ,Amount=?," +
					"User_Cash_Payment_ID=?, Transaction_From_Bank_Statement_ID=?," +
					"Flat_Wise_Payable_ID=?, Auto_Split_Id=?  WHERE Balance_Sheet_Transaction_Id=?";

	public static final String updateBalanceSheet_ExpenseType =
			"UPDATE transactions_balancesheet SET Expense_Type_Id=? WHERE Balance_Sheet_Transaction_Id=?";

	public static final String updateBalanceSheet_Amount =
			"UPDATE transactions_balancesheet SET Amount=? WHERE Balance_Sheet_Transaction_Id=?";

	public static final String updateBalanceSheet_Transaction_From_Bank_Statement_ID =
			"UPDATE transactions_balancesheet SET Transaction_From_Bank_Statement_ID=? WHERE Balance_Sheet_Transaction_Id=?";

	public static final String updateBalanceSheet_User_Cash_Payment_ID =
			"UPDATE transactions_balancesheet SET User_Cash_Payment_ID=? WHERE Balance_Sheet_Transaction_Id=?";

	public static final String updateBalanceSheet_User_Transaction_Expense_Id =
			"UPDATE transactions_balancesheet SET User_Cash_Payment_ID=? WHERE Balance_Sheet_Transaction_Id=?";

	public static final String updateUserCashSpitted =
			"UPDATE user_paid SET Splitted=1, Auto_Split_Id=? WHERE User_Cash_Payment_ID=?";

	public static final String updateBankTransactionSpillted =
			"UPDATE transactions_verified SET Splitted=1, Auto_Split_Id=? WHERE Transaction_From_Bank_Statement_ID=?";

	public static final String insertFlatWisePayableToPaidMapping =
			"INSERT INTO flat_wise_payable_paid_mapping (Flat_Wise_Payable_ID, Balance_Sheet_Transaction_ID) VALUES (?, ?)";

	public static final String updateFlatWisePayableStatus =
			"UPDATE flat_wise_payable SET Payment_Status_ID=?, Auto_Split_Id = ? WHERE Flat_Wise_Payable_ID=?";

	public static final String updateApartmentCashSpitted =
			"UPDATE apartment_expense SET Splitted=1, Auto_Split_Id = ? WHERE Apartment_Cash_Expense_ID=?";

	public static final String insertApartmentExpenseQuery =
			"INSERT INTO apartment_expense " +
					"(Expense_Type_Id,Amount,Expend_Date,Expend_By_UserId,Verified,Verified_By,Expendy_Comment,Admin_Comment,Splitted) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	public static final String insertApartmentEarningQuery =
			"INSERT INTO apartment_earning " +
					"(Expense_Type_Id,Amount,Earned_Date,Verified,Verified_By,Admin_Comment,Splitted) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?)";

	public static final String cleanLoginDatabase =
			"delete from login where Login_Id != 'superadmin'";

	public static final String cleanFlatDatabase =
			"delete from flat";

	public static final String cleanUserDetailsDatabase =
			"delete from user_details where Login_Id != 'superadmin'";

	public static final String cleanFlatPayablesDatabase =
			"delete from flat_wise_payable";

	public static final String cleanTransactionVerficationDatabase =
			"delete from transactions_verified";

	public static final String cleanBalanceSheetDatabase =
			"delete from transactions_balancesheet";

	public static final String cleanApartmentEarningDatabase =
			"delete from apartment_earning";

	public static final String cleanApartmentExpenseDatabase =
			"delete from apartment_expense";

	public static final String rollbackUserPaidDataQuery =
			"update user_paid T"+
					" inner join"+
					" last_auto_split las"+
					" on las.Auto_Split_Id = T.Auto_Split_Id"+
					" set T.Splitted=0"+
					" where las.Auto_Split_Id != 0";

	public static final String rollbackTransactionVerifiedQuery =
			"DELETE T"+
					" FROM transactions_verified T"+
					" INNER JOIN last_auto_split las on las.Auto_Split_Id = T.Auto_Split_Id"+
					" WHERE las.Auto_Split_Id != 0";

	public static final String rollbackTransactionsBalanceSheet =
			"DELETE T"+
					" FROM transactions_balancesheet T"+
					" INNER JOIN last_auto_split las on las.Auto_Split_Id = T.Auto_Split_Id"+
					" WHERE las.Auto_Split_Id != 0";

	public static final String rollbackFlatWisePayable =
			"update flat_wise_payable T"+
					" inner join"+
					" last_auto_split las "+
					" on las.Auto_Split_Id = T.Auto_Split_Id"+
					" set T.Payment_Status_ID=3"+
					" where las.Auto_Split_Id != 0";

	public static final String rollbackApartmentExpense =
			"DELETE T"+
					" FROM apartment_expense T"+
					" INNER JOIN last_auto_split las on las.Auto_Split_Id = T.Auto_Split_Id"+
					" WHERE las.Auto_Split_Id != 0";

	public static final String rollbackApartmentEarning =
			"DELETE T"+
					" FROM apartment_earning T"+
					" INNER JOIN last_auto_split las on las.Auto_Split_Id = T.Auto_Split_Id"+
					" WHERE las.Auto_Split_Id != 0";

	public static final String createSocietyQuery =
			"INSERT INTO society (Society_Name, Email_Id, Admin_Mobile_No, City, Country) VALUES (?, ?, ?, ?, ?)";

	public static final String userWiseAutoSplitTransactionsQuery =
			"SELECT tv.flat_id, ud.Name, Balance_Sheet_Transaction_ID,tb.Transaction_From_Bank_Statement_ID, " +
					"tv.Amount total_amount, tb.Amount splitted_amount, " +
					"tv.Transaction_Date," +
					"Verified_By_Admin, Verified_By_User, Expense_Type_Id, tb.Transaction_Flow," +
					"tb.Flat_Wise_Payable_ID" +
					"FROM transactions_balancesheet tb " +
					"inner join" +
					"transactions_verified tv " +
					"on tv.Transaction_From_Bank_Statement_ID = tb.Transaction_From_Bank_Statement_ID" +
					"inner join " +
					"user_details ud " +
					"on ud.User_Id = tv.User_Id" +
					"where " +
					"ud.Login_Id = ?" +
					"and tb.Transaction_Flow='Credit' " +
					"and tb.Verified_By_User = 0 " +
					"and tv.Amount !=0 " +
					"and tb.Amount !=0 ";

	public static final String flatWiseAutoSplitTransactionsQuery =
			"SELECT tv.flat_id, ud.Name, Balance_Sheet_Transaction_ID,tb.Transaction_From_Bank_Statement_ID, " +
					"tv.Amount total_amount, tb.Amount splitted_amount, " +
					"tv.Transaction_Date," +
					"Verified_By_Admin, Verified_By_User, Expense_Type_Id, tb.Transaction_Flow," +
					"tb.Flat_Wise_Payable_ID" +
					"FROM transactions_balancesheet tb " +
					"inner join" +
					"transactions_verified tv " +
					"on tv.Transaction_From_Bank_Statement_ID = tb.Transaction_From_Bank_Statement_ID" +
					"inner join " +
					"user_details ud " +
					"on ud.User_Id = tv.User_Id" +
					"where " +
					"tv.flat_id = ? " +
					"and tb.Transaction_Flow='Credit' " +
					"and tb.Verified_By_User = 0 " +
					"and tv.Amount !=0 " +
					"and tb.Amount !=0 ";

	public static final String flatWiseUnSplittedTransactionQuery=
			"SELECT Transaction_From_Bank_Statement_ID,Amount,Transaction_Date," +
					"Transaction_Mode,Transaction_Reference, ud.User_Id, ud.Name ," +
					"tv.Flat_Id,Verified_By " +
					"FROM " +
					"transactions_verified tv " +
					"inner join " +
					"user_details ud " +
					"on ud.User_Id = tv.User_Id " +
					"where " +
					"tv.flat_id = ? " +
					"and tv.Transaction_Flow='Credit' " +
					"and tv.Splitted = 0 " +
					"and tv.Amount !=0 ";


	public static final String userWiseUnSplittedTransactionQuery=
			"SELECT Transaction_From_Bank_Statement_ID,Amount,Transaction_Date," +
					"Transaction_Mode,Transaction_Reference, ud.User_Id, ud.Name ," +
					"tv.Flat_Id,Verified_By " +
					"FROM " +
					"transactions_verified tv " +
					"inner join " +
					"user_details ud " +
					"on ud.User_Id = tv.User_Id " +
					"where " +
					"ud.Login_Id = ? " +
					"and tv.Transaction_Flow='Credit' " +
					"and tv.Splitted = 0 " +
					"and tv.Amount !=0 ";

	public static final String allSocietyQuery =
			"SELECT Society_Id,Society_Name,Database_URL,Database_User,Database_Password,Email_Id,Admin_Mobile_No,Created_Date," +
					"Service_Start_Date,Service_Renewal_Date,Charge_Per_Flat,City,Country,Address,Status FROM societyhelp";

	public static final String allWaterSupplierQuery =
			"SELECT Supplier_Id,Supplier_Name,Capacity_In_Liter FROM water_suppiler";

	public static final String insertWaterReadingQuery =
			"INSERT INTO water_supply_reading " +
			"(Supplier_Id,Capacity_In_Liter,Reading_Before_Supply,Reading_After_Supply, Supply_Time, Login_Id) " +
			" VALUES (?, ?, ?, ?, ?, ?)";

	public static final String allWaterReadingQuery =
			"SELECT ws.Supplier_Id, ws.Supplier_Name, wsr.Capacity_In_Liter, Supply_Time, Reading_Before_Supply, Reading_After_Supply, Login_Id " +
			"FROM water_supply_reading wsr " +
			"left join water_suppiler ws " +
			"on ws.Supplier_Id = wsr.Supplier_Id " +
			"order by wsr.Supply_Time desc";

}
