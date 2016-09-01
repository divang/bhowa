package bhowa.dao;

import java.util.List;

import bhowa.dao.mysql.impl.UserDetails;

/**
 * Created by divang.sharma on 8/6/2016.
 */
public interface IBhowaDatabase {
	
    /** user login validation
     * @return
     */
    boolean login(Object userDetailsObj) throws Exception;
    
    /** Capture all user activities
     * @param activityObj
     */
    void activityLogging(Object activityObj) throws Exception;
    
    /** Check whether uploaded statement is already processed or not 
     * @param monthlyStatementFileName
     * @return
     */
    boolean isStatementAlreadyProcessed(String monthlyStatementFileName) throws Exception;
   
    /** Upload monthly statement to DB and save all transaction.
     * @param transactionsObj
     * @return
     */
    boolean uploadMonthlyTransactions(Object transactionsObj) throws Exception;

    /**
     * After parsing the PDF file, Parser generate a single libe for each transaction.
     * It save that line in DB. Used for manually validation of data, if required.
     * @param data
     */
    void insertRawData(List<String> data) throws Exception;

    /**
     * It returns the Parser generated transaction lines. Store by insertRawData() API
     * @return
     */
    List<String> showRawData() throws Exception;

    /**
     * It deletes the Parser generated transaction lines. AFter validate the data it should be deleted.
     */
    void deleteAllRawData() throws Exception;

    /**
     * It returns all active society users which are paying their maintenance
     * @return
     */
    List<UserDetails> getAllUsers() throws Exception;

    /**
     * All type of expenses which user can do.
     * @return
     */
    List<String> getExpenseType() throws Exception;

    /**
     * Any kind of error occurred it APP, it should capture in DB
     * @param data
     */
    void errorLogging(String data) throws Exception;

    /**
     * It shows all the distinct users in transactions. Used for map the actual user with alias user names.
     * @return
     */
    List<String> getAllTransactionStagingUsers() throws Exception;

    /**
     * Used to update the alias names of user id
     * @param userId
     * @param allAliasNames
     */
    void setAliasOfUserId(String userId, String allAliasNames) throws Exception;

}
