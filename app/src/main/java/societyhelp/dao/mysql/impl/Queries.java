package societyhelp.dao.mysql.impl;

import java.sql.Timestamp;

public class Queries {

	  //Queries
    public static final String loginQuery = 
    		//"select Login_Id FROM Login WHERE Login_Id = ? and Password = ? and Status = 1";
			"select s.Database_URL, s.Database_User, s.Database_Password, s.Society_Name, s.Society_Id from " +
					" Login l" +
					" inner join " +
					" Society s " +
					" on l.Society_Id = s.Society_Id " +
					" where l.Login_Id= ? " +
					" and l.Password = ?" +
					" and l.Status = 1";

    public static final String activityLoggingQuery = 
    		"Insert into User_Activity_Logging (UserName, Mobile, Activity, Comment, Time) values (? ,? ,?, ?, ?)";
  
    public static final String statementProcessedQuery =
    		"Insert into Bank_Statement(Bank_Statement_FileName, Uploaded_Date, Uploaded_LoginId) values (?, ?, ?)";
    
    public static final String isStatementProcessedQuery =
    		"Select * FROM Bank_Statement WHERE Statement_FileName = ?";

	public static final String transactionStagingQuery =
			"Insert into Transactions_Staging_Data(" +
					"StatementID, Name, Amount, "
					+ "Transaction_Date, Transaction_Flow, Transaction_Mode, " +
					"Transaction_Reference, Upload_Date, Uploaded_LoginId) "
					+ "values(" +
					"?, ?, ?, " +
					"?, ?, ?, " +
					"?, '" + new Timestamp(System.currentTimeMillis())+"', ?)";

	public static final String cleanTransactionStagingDataQuery =
			"delete from Transactions_Staging_Data";

	public static final String allTransactionStagingDataQuery =
					"SELECT Transaction_ID,StatementID,Name,Amount," +
					"Transaction_Date,Transaction_Flow,Transaction_Mode,Transaction_Reference," +
					"Upload_Date,Uploaded_LoginId FROM Transactions_Staging_Data";

	public static final String insertRawDataQuery =
			"Insert into Row_Data(Transaction_Raw_Line_Parse_From_PDF) values (?)";

	public static final String selectAllRawQuery =
			"select * from Row_Data";

	public static final String deleteAllRawQuery =

			"delete from Row_Data";

	public static final String allUsersQuery =
			"SELECT * FROM User_Details";

	public static final String expenseTypeQuery =
			"SELECT * FROM Expense_Type";

	public static final String errorLoggingQuery =
			"Insert into Error_Log(User_Id, Log) values (?, ?)";

	public static final String allTransactionStagingUsersQuery =
			"select distinct(Name) from Transactions_Staging_Data";

	public static final String updateAliasUserId =
			"UPDATE User_Details SET Name_Alias = ? WHERE User_Id = ?";

	public static final String createLoginQuery =
			//"Insert into Login(Login_Id, Password) values (?, ?)"; //old query single database
			//New query with Master login database and Other Society database
			"Insert into Login(Login_Id, Password, Society_Id) " +
			" select  ?, ?, Society_Id from Login where login_id= ?";

	public static final String insertFlatDetailsQuery =
			"Insert into Flat(Flat_Id,Flat_Number,Area,Maintenance_Amount,Block_Number) " +
					"values (?, ?, ?, ?,?)";

	public static final String selectAllLoginQuery =
			//"SELECT * FROM Login"; //Old query
			//Login is move to Master database;
			"select l2.Login_Id, l2.Password, l2.Status, l2.Society_Id from Login l1 " +
			"inner join " +
			"Login l2 " +
			"on l1.Society_Id = l2.Society_Id " +
			"where " +
			"l1.Login_Id = ? ";


	public static final String selectAllAssignedLoginIdsQuery =
			"select Login_Id from User_Details";

	public static final String selectAllFlatQuery =
			"SELECT * FROM Flat";

	public static final String selectFinalTransactionQuery =
			"SELECT  Transaction_ID,StatementID,tsd.Name,Amount," +
					"Transaction_Date,Transaction_Flow,Transaction_Mode,Transaction_Reference," +
					"ud.User_Id,ud.Flat_Id " +
					"FROM Transactions_Staging_Data as tsd " +
					"left join " +
					"User_Details as ud " +
					"on (UPPER(ud.Name) LIKE CONCAT('%', UPPER(tsd.Name),'%')) " +
					"OR (UPPER(ud.Name_Alias) LIKE CONCAT('%', UPPER(tsd.Name), '%'))";

	public static final String insertFinalTransactionQuery =
			"Insert into Transactions_Verified (" +
					"Amount, Transaction_Date,Transaction_Flow," +
					"Transaction_Mode,Transaction_Reference,User_Id," +
					"Flat_Id,Verified_By,Splitted) " +
					"values (" +
					"? , ?, ?, " +
					"?, ?, ?, " +
					"?, ?, ?) ";

	public static final String insertUserDetailsQuery =
			"Insert into User_Details(" +
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
                    "SELECT Transaction_From_Bank_Statement_ID,Amount,Transaction_Date,Transaction_Flow," +
                            "Transaction_Mode,Transaction_Reference, ud.Flat_Id,Verified_By,Splitted " +
                            "FROM Transactions_Verified tv inner " +
                            "join User_Details ud " +
                            "on tv.User_Id = ud.User_Id " +
                            "inner join Login l " +
                            "on l.Login_Id = ud.Login_Id " +
                            "where l.Login_Id = ?";

	public static final String myDueQuery =
					"Select " +
					"IFNULL((select sum(fwp.Amount) " +
					"from Flat as f " +
					"inner join " +
					"Flat_Wise_Payable as fwp on f.Flat_ID = fwp.Flat_ID " +
					"where f.Flat_Id = ? " +
					"group by f.Flat_Id), 0) " +
					" - " +
					"IFNULL((select sum(up.Amount) " +
					"from Flat as f " +
					"inner join " +
					"User_Paid as up on up.Flat_ID = f.Flat_Id " +
					"where f.Flat_Id = ? " +
					"group by f.Flat_Id), 0) " +
					" - " +
					"IFNULL((select sum(tv.Amount) " +
					"from Flat as f " +
					"inner join " +
					"Transactions_Verified as tv on tv.Flat_Id = f.Flat_Id " +
					"where f.Flat_Id = ? AND tv.Transaction_Flow = 'Credit' "+
					"group by f.Flat_Id) , 0) " +
					" + " +
					"IFNULL((select sum(tv.Amount) " +
					"from Flat as f " +
					"inner join " +
					"Transactions_Verified as tv on tv.Flat_Id = f.Flat_Id " +
					"where f.Flat_Id = ? AND tv.Transaction_Flow = 'Debit'  " +
					"group by f.Flat_Id " +
					"),0) as MyDue ";

    public static final String myDetailsQuery =
                    "SELECT User_Id,Login_Id,User_Type,Status,Flat_Id,Name,Name_Alias,Mobile_No," +
					"Moble_No_Alternate,Email_Id,Address,Flat_Join_Date,Flat_Left_Date,Auth_Ids " +
					"FROM User_Details where Login_Id = ?";

    public static final String addlatMaintenancePayablesQuery =
            "insert into Flat_Wise_Payable (Flat_Id, Amount, Month, Year, Expense_Type_Id) " +
                    "select f.Flat_Id, f.Maintenance_Amount, ?, ?, ? " +
                    "from Flat f";

    public static final String addAllFlatPayablesQuery =
					"insert into Flat_Wise_Payable (Flat_Id, Amount, Month, Year, Expense_Type_Id) " +
					"select f.Flat_Id, ?, ?, ?, ? " +
					"from Flat f";

	public static final String addSingleFlatPayablesQuery =
			"insert into Flat_Wise_Payable (Flat_Id, Amount, Month, Year, Expense_Type_Id) " +
					"values (?, ?, ?, ?, ? )";

	public static final String flatWiseTransactionsQuery =
					"select * from " +
							"( " +
							"select Flat_Id as fid,'Payable', Amount, cast(rtrim(year *10000+ month) as datetime) as Date from Flat_Wise_Payable " +
							" union " +
							"select Flat_Id as fid,'Paid', Amount, Paid_Date from User_Paid " +
							" union " +
							"select Flat_Id as fid,'Paid', Amount, Transaction_Date from Transactions_Verified where Transaction_Flow = 'Credit' " +
							" union " +
							"select Flat_Id as fid,'Payable', Amount, Transaction_Date from Transactions_Verified where Transaction_Flow = 'Debit' " +
							") T " +
							"where fid = ? ";

	public static final String addUserPaymentQuery =
					"insert into User_Paid (User_ID,Flat_ID, Amount, Paid_Date, User_Comment, Expense_Type_Id) " +
					" select ? ,?, ?, ?, ?, et.Expense_Type_Id " +
					" from Expense_Type et where type = ? ";

	public static final String unVerifiedCashPaymentByUserQuery =
					"select " +
                    " User_Cash_Payment_ID,User_ID,Flat_ID,Amount,Paid_Date,u.Expense_Type_Id,User_Comment,Admin_Comment " +
                    " from User_Paid u " +
                    " left join Expense_Type e " +
                    " on u.Expense_Type_Id = e.Expense_Type_Id " +
                    " where Verified = 0";


	public static final String unSplittedCashPaymentByUserQuery =
			"select " +
					" User_Cash_Payment_ID,User_ID,Flat_ID,Amount,Paid_Date,u.Expense_Type_Id,User_Comment,Admin_Comment " +
					" from User_Paid u " +
					" left join Expense_Type e " +
					" on u.Expense_Type_Id = e.Expense_Type_Id " +
					" where Splitted = 0 ";

	public static final String unSplittedApartmentExpensePaymentQuery =
			"select Apartment_Cash_Expense_ID,Expense_Type_Id,Amount " +
					" ,Expend_Date,Expend_By_UserId,Verified " +
					" ,Verified_By,Expendy_Comment,Admin_Comment,Splitted " +
					" from Apartment_Expense " +
					" where Splitted = 0 ";

    public static final String saveVerifiedCashPaymentQuery =
                    "update User_Paid set Verified=1, Verified_by=? " +
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
                        "FROM Flat_Wise_Payable fwp " +
                        "left join " +
                        "Expense_Type et " +
                        "on et.Expense_Type_Id = fwp.Expense_Type_Id " +
                        "where Payment_Status_ID in (1,3)";

	public static final String payableAndPaidFlatWiseAmountQuery =
				"select fwp.Flat_Wise_Payable_ID, fwp.Amount Payable, sum(tbs.Amount) Paid, " +
						"et.Type, et.Expense_Type_Id, et.Payable_Priority, " +
						"fwp.Flat_Id, fwp.Status, " +
						"fwp.Month, fwp.Year,  " +
						"fwp.Comments, fwp.Payment_Status_ID " +
						"from Flat_Wise_Payable as fwp " +
						//"left join  " +
						//"Flat_Wise_Payable_Paid_Mapping as fwppm " +
						//"on fwp.Flat_Wise_Payable_ID = fwppm.Flat_Wise_Payable_ID " +
						"left join Transactions_BalanceSheet as tbs " +
						"on fwp.Flat_Wise_Payable_ID = tbs.Flat_Wise_Payable_ID " +
						"left join Expense_Type et on et.Expense_Type_Id = fwp.Expense_Type_Id " +
						"where Payment_Status_ID in (1,3) " +
						"group by fwp.Flat_Wise_Payable_ID";

	public static final String unSplittedCreditTransactionsQuery =
				"SELECT Transaction_From_Bank_Statement_ID,Amount,Transaction_Date,Transaction_Flow," +
						"Transaction_Mode,Transaction_Reference,User_Id,Flat_Id," +
						"Verified_By,Splitted " +
						"FROM Transactions_Verified " +
						"where splitted=0 and Transaction_Flow = 'Credit'";

	public static final String unSplittedDebitTransactionsQuery =
			"SELECT Transaction_From_Bank_Statement_ID,Amount,Transaction_Date,Transaction_Flow," +
					"Transaction_Mode,Transaction_Reference,tv.User_Id,ud.Service_Type_Id," +
					"Verified_By,Splitted " +
					"FROM Transactions_Verified tv " +
					"left join " +
					"User_Details ud on ud.User_Id = tv.User_Id " +
					"where splitted=0 and Transaction_Flow = 'Debit'";

	public static final String advanceTransactionsQuery =
			    "SELECT tbs.Balance_Sheet_Transaction_ID, tbs.Amount, tbs.Verified_By_Admin, tbs.Verified_By_User,tbs.Expense_Type_Id,"+
					"tbs.Transaction_From_Bank_Statement_ID, tbs.User_Cash_Payment_ID, tbs.Transaction_Expense_Id, tv.User_Id User_Id_tv, tv.Flat_Id User_Id_tv,"+
					"ud.User_Id User_Id_ud, ud.Flat_Id Flat_Id_ud, ae.Expend_By_UserId User_Id_ae "+
					"FROM Transactions_BalanceSheet tbs "+
					"left join Transactions_Verified tv on tbs.Transaction_From_Bank_Statement_ID = tv.Transaction_From_Bank_Statement_ID "+
					"left join User_Paid ud on tbs.User_Cash_Payment_ID = ud.User_Cash_Payment_ID   "+
					"left join Apartment_Expense ae on tbs.Transaction_Expense_Id = ae.Apartment_Cash_Expense_ID  "+
					"WHERE tbs.Expense_Type_Id=0";

	public static final String balanceSheetQuery =
			"SELECT tbs.Balance_Sheet_Transaction_ID, tbs.Amount, tbs.Verified_By_Admin, tbs.Verified_By_User,tbs.Expense_Type_Id,"+
					"tbs.Transaction_From_Bank_Statement_ID, tbs.User_Cash_Payment_ID, tbs.Transaction_Expense_Id, tv.User_Id User_Id_tv, tv.Flat_Id User_Id_tv,"+
					"ud.User_Id User_Id_ud, ud.Flat_Id Flat_Id_ud, ae.Expend_By_UserId User_Id_ae "+
					"FROM Transactions_BalanceSheet tbs "+
					"left join Transactions_Verified tv on tbs.Transaction_From_Bank_Statement_ID = tv.Transaction_From_Bank_Statement_ID "+
					"left join User_Paid ud on tbs.User_Cash_Payment_ID = ud.User_Cash_Payment_ID   "+
					"left join Apartment_Expense ae on tbs.Transaction_Expense_Id = ae.Apartment_Cash_Expense_ID  ";


	public static final String paidFlatnExpenseTypeWiseAmountQuery =
				"select  User_Cash_Payment_ID,User_ID,Flat_ID,sum(Amount) as Total_Paid," +
						"Paid_Date,Type,User_Comment,Admin_Comment " +
						"from User_Paid u " +
						"left join " +
						"Expense_Type e " +
						"on u.Expense_Type_Id = e.Expense_Type_Id " +
						"where Verified = 1 " +
						"group by u.Flat_ID, u.Expense_Type_Id";

	public static final String allFlatWiseAmountQuery =
				"SELECT Flat_Wise_Payable_ID,Flat_Id,Status,Month, " +
						"Year,Amount,Type,Comments, " +
						"Payment_IDs,Status_Type " +
						"FROM Flat_Wise_Payable fwp " +
						"left join Expense_Type et " +
						"on fwp.Expense_Type_Id = et.Expense_Type_Id " +
						"left join User_Payment_Status ups " +
						"on ups.Payment_Status_Id =fwp.Payment_Status_ID";

	public static final String allAuthorizationsQuery =
					"SELECT Auth_Id,Type FROM Authorizations";

	public static final String insertToBalanceQuery =
					"INSERT INTO Transactions_BalanceSheet (" +
					"Amount,Verified_By_Admin,Verified_By_User,Expense_Type_Id," +
					"Transaction_From_Bank_Statement_ID,User_Cash_Payment_ID,Transaction_Expense_Id,Transaction_Flow,Flat_Wise_Payable_ID,Apartment_Earning_ID)" +
					"VALUES (?, ?, ?, ?," +
							" ?, ?, ?, ?, ?, ?)";

	public static final String updateBalanceSheet_Transaction =
			"UPDATE Transactions_BalanceSheet SET Expense_Type_Id=? ,Amount=?," +
				"User_Cash_Payment_ID=?, Transaction_From_Bank_Statement_ID=?," +
				"Flat_Wise_Payable_ID=?  WHERE Balance_Sheet_Transaction_Id=?";

	public static final String updateBalanceSheet_ExpenseType =
			"UPDATE Transactions_BalanceSheet SET Expense_Type_Id=? WHERE Balance_Sheet_Transaction_Id=?";

	public static final String updateBalanceSheet_Amount =
			"UPDATE Transactions_BalanceSheet SET Amount=? WHERE Balance_Sheet_Transaction_Id=?";

	public static final String updateBalanceSheet_Transaction_From_Bank_Statement_ID =
			"UPDATE Transactions_BalanceSheet SET Transaction_From_Bank_Statement_ID=? WHERE Balance_Sheet_Transaction_Id=?";

	public static final String updateBalanceSheet_User_Cash_Payment_ID =
			"UPDATE Transactions_BalanceSheet SET User_Cash_Payment_ID=? WHERE Balance_Sheet_Transaction_Id=?";

	public static final String updateBalanceSheet_User_Transaction_Expense_Id =
			"UPDATE Transactions_BalanceSheet SET User_Cash_Payment_ID=? WHERE Balance_Sheet_Transaction_Id=?";

	public static final String updateUserCashSpitted =
			"UPDATE User_Paid SET Splitted=1 WHERE User_Cash_Payment_ID=?";

	public static final String updateBankTransactionSpillted =
			"UPDATE Transactions_Verified SET Splitted=1 WHERE Transaction_From_Bank_Statement_ID=?";

	public static final String insertFlatWisePayableToPaidMapping =
			"INSERT INTO Flat_Wise_Payable_Paid_Mapping (Flat_Wise_Payable_ID, Balance_Sheet_Transaction_ID) VALUES (?, ?)";

	public static final String updateFlatWisePayableStatus =
			"UPDATE Flat_Wise_Payable SET Payment_Status_ID=? WHERE Flat_Wise_Payable_ID=?";

	public static final String updateApartmentCashSpitted =
			"UPDATE Apartment_Expense SET Splitted=1 WHERE Apartment_Cash_Expense_ID=?";

	public static final String insertApartmentExpenseQuery =
			"INSERT INTO Apartment_Expense " +
			"(Expense_Type_Id,Amount,Expend_Date,Expend_By_UserId,Verified,Verified_By,Expendy_Comment,Admin_Comment,Splitted) " +
			"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	public static final String insertApartmentEarningQuery =
			"INSERT INTO Apartment_Earning " +
					"(Expense_Type_Id,Amount,Earned_Date,Verified,Verified_By,Admin_Comment,Splitted) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?)";

	public static final String cleanLoginDatabase =
			"delete from Login where Login_Id != 'superadmin'";

	public static final String cleanFlatDatabase =
			"delete from Flat";

	public static final String cleanUserDetailsDatabase =
			"delete from User_Details where Login_Id != 'superadmin'";

	public static final String cleanFlatPayablesDatabase =
			"delete from Flat_Wise_Payable";

	public static final String cleanTransactionVerficationDatabase =
			"delete from Transactions_Verified";

	public static final String cleanBalanceSheetDatabase =
			"delete from Transactions_BalanceSheet";

	public static final String cleanApartmentEarningDatabase =
			"delete from Apartment_Earning";

	public static final String cleanApartmentExpenseDatabase =
			"delete from Apartment_Expense";

}
