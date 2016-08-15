package bhowa.dao;

/**
 * Created by divang.sharma on 8/6/2016.
 */
public interface IBhowaDatabase {
	
    /** user login validation
     * @param userName
     * @param password
     * @return
     */
    boolean login(Object userDetailsObj);
    
    /** Capture all user activities
     * @param activities
     */
    void activityLogging(Object activityObj);
    
    /** Check whether uploaded statement is already processed or not 
     * @param monthlyStatementFileName
     * @return
     */
    boolean isStatementAlreadyProcessed(String monthlyStatementFileName);
   
    /** Upload monthly statement to DB and save all transaction.
     * @param monthlyStatementFileName
     * @return
     */
    boolean uploadMonthlyTransactions(Object transactionsObj);
    
}
