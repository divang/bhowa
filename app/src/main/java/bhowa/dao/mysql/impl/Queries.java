package bhowa.dao.mysql.impl;

public class Queries {

	  //Queries
    public static final String loginQuery = 
    		"SELECT user_id FROM User WHERE user_id = ? and password = ?";

    public static final String activityLoggingQuery = 
    		"Insert into User_Activity_Logging (UserName, Mobile, Activity, Comment, Time) values (? ,? ,?, ?, ?)";
  
    public static final String statementProcessedQuery =
    		"Insert into Bank_Statement(Bank_Statement_FileName, Uploaded_Date) values (?, ?)";
    
    public static final String isStatementProcessedQuery =
    		"Select Statement_FileName, Uploaded_Date FROM Bank_Statement WHERE Statement_FileName = ?";
    
    public static final String transactionQuery =
    	    "Insert into Transactions(StatementID, Name, Amount, "
    	    + "Transaction_Date, Transaction_Flow, Transaction_Mode, Transaction_Reference) "
    	    + "values(?, ?, ?, ?, ?, ?, ?)";
}
