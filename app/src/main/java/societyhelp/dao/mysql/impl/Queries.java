package societyhelp.dao.mysql.impl;

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
    		"Insert into Bank_Statement(Bank_Statement_FileName, Uploaded_Date) values (?, ?)";
    
    public static final String isStatementProcessedQuery =
    		"Select * FROM Bank_Statement WHERE Statement_FileName = ?";
    
    public static final String transactionQuery =
    	    "Insert into Transactions(StatementID, Name, Amount, "
    	    + "Transaction_Date, Transaction_Flow, Transaction_Mode, Transaction_Reference) "
    	    + "values(?, ?, ?, ?, ?, ?, ?)";

	public static final String transactionStagingQuery =
			"Insert into Transactions_Staging_Data(" +
					"StatementID, Name, Amount, "
					+ "Transaction_Date, Transaction_Flow, Transaction_Mode, " +
					"Transaction_Reference) "
					+ "values(" +
					"?, ?, ?, " +
					"?, ?, ?, " +
					"?)";

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
					"Flat_Join_Date, Flat_Left_Date, Login_Id)" +
					" values (" +
					"?, ?, ?, " +
					"?, ?, ?," +
					"?, ?, ?," +
					"?, ?, ? )";

    public static final String myTransactionsQuery =
                    "SELECT Transaction_ID,Amount,Transaction_Date,Transaction_Flow," +
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

	public static final String addFlatWiseMaintenanceQuery =
					"insert into Flat_Wise_Payable (Flat_Id, Amount, Month, Year, Expense_Type) " +
					"select f.Flat_Id, f.Maintenance_Amount, ?, ?, ? " +
					"from Flat f";

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
                    " Payment_ID,User_ID,Flat_ID,Amount,Paid_Date,Type,User_Comment,Admin_Comment " +
                    " from User_Paid u " +
                    " left join Expense_Type e " +
                    " on u.Expense_Type_Id = e.Expense_Type_Id " +
                    " where Verified = 0";

    public static final String saveVerifiedCashPaymentQuery =
                    "update User_Paid set Verified=1, Verified_by=? " +
                            "where Payment_Id in (?)";

	/*
	"Payment_Status_Id";"Status_Type"
	"1";"Not Paid"
	"2";"Full Paid"
	"3";"Partial Paid"
   */
	public static final String unPaidFlatWiseAmountQuery =
				"SELECT Payable_Id,Flat_Id,Status,Month," +
                        "Year,Amount,et.Expense_Type_Id,Comments," +
                        "Payment_IDs,Payment_Status_ID,et.Payable_Priority " +
                        "FROM Flat_Wise_Payable fwp " +
                        "left join " +
                        "Expense_Type et " +
                        "on et.Expense_Type_Id = fwp.Expense_Type_Id " +
                        "where Payment_Status_ID in (1,3)";

	/*
	splitted=0 current transaction is not copied to User_Paid table.
	 */
	public static final String unSplittedTransactionsQuery =
				"SELECT Transaction_ID,Amount,Transaction_Date,Transaction_Flow," +
						"Transaction_Mode,Transaction_Reference,User_Id,Flat_Id," +
						"Verified_By,Splitted " +
						"FROM Transactions_Verified " +
						"where splitted=0";

	public static final String paidFlatnExpenseTypeWiseAmountQuery =
				"select  Payment_ID,User_ID,Flat_ID,sum(Amount) as Total_Paid," +
						"Paid_Date,Type,User_Comment,Admin_Comment " +
						"from User_Paid u " +
						"left join " +
						"Expense_Type e " +
						"on u.Expense_Type_Id = e.Expense_Type_Id " +
						"where Verified = 1 " +
						"group by u.Flat_ID, u.Expense_Type_Id";

}
