package societyhelp.dao.mysql.impl;

public class Queries {

	  //Queries
    public static final String loginQuery = 
    		//"select Login_Id FROM Login WHERE Login_Id = ? and Password = ? and Status = 1";
			"select s.Database_URL, s.Database_User, s.Database_Password, s.Society_Name from " +
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
			"Insert into Login(Login_Id, Password) values (?, ?)";

	public static final String insertFlatDetailsQuery =
			"Insert into Flat(Flat_Id,Flat_Number,Area,Maintenance_Amount,Block_Number) " +
					"values (?, ?, ?, ?,?)";

	public static final String selectAllLoginQuery =
			"SELECT * FROM Login";

	public static final String selectAllFlatQuery =
			"SELECT * FROM Flat";

	public static final String selectFinalTransactionQuery =
			"SELECT  Transaction_ID,StatementID,tsd.Name,Amount," +
					"Transaction_Date,Transaction_Flow,Transaction_Mode,Transaction_Reference," +
					"ud.User_Id,ud.Flat_Id,Admin_Approved,Admin_Comment," +
					"User_Comment " +
					"FROM Transactions_Staging_Data as tsd " +
					"left join " +
					"User_Details as ud " +
					"on (UPPER(ud.Name) LIKE CONCAT('%', UPPER(tsd.Name),'%')) " +
					"OR (UPPER(ud.Name_Alias) LIKE CONCAT('%', UPPER(tsd.Name), '%'))";

	public static final String insertFinalTransactionQuery =
			"Insert into Transactions_Verified (" +
					"Amount, Transaction_Date,Transaction_Flow," +
					"Transaction_Mode,Transaction_Reference,User_Id," +
					"Flat_Id,Admin_Comment,User_Comment) " +
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
                            "Transaction_Mode,Transaction_Reference, ud.Flat_Id,Admin_Comment,User_Comment " +
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

}
