package bhowa.dao.mysql.impl;

public class Queries {

	  //Queries
    public static final String loginQuery = 
    		"SELECT userid FROM User_Login WHERE userid = ? and password = ?";
    
    public static final String activityLoggingQuery = 
    		"Insert into User_Activity_Logging (UserName, Mobile, Activity, Comment, Time) values (? ,? ,?, ?, ?)";
  
    public static final String statementProcessedQuery =
    		"Insert into Processed_Bank_Statement(Bank_Statement_FileName, Uploaded_Date) values (?, ?)";
    
    public static final String isStatementProcessedQuery =
    		"Select Bank_Statement_FileName, Uploaded_Date FROM Processed_Bank_Statement WHERE Bank_Statement_FileName = ?";
    
    public static final String transactionQuery =
    	    "Insert into Transactions(StatementID, Name, Amount, "
    	    + "Transaction_Date, Transaction_Flow, Transaction_Mode, Transaction_Reference) "
    	    + "values(?, ?, ?, ?, ?, ?, ?)";
}
