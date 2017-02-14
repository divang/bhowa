package societyhelp.dao;

import java.util.List;

import societyhelp.dao.mysql.impl.ExpenseType;
import societyhelp.dao.mysql.impl.FlatWisePayable;
import societyhelp.dao.mysql.impl.SocietyDetails;
import societyhelp.dao.mysql.impl.SocietyHelpTransaction;
import societyhelp.dao.mysql.impl.Flat;
import societyhelp.dao.mysql.impl.Login;
import societyhelp.dao.mysql.impl.StagingTransaction;
import societyhelp.dao.mysql.impl.TransactionOnBalanceSheet;
import societyhelp.dao.mysql.impl.UserPaid;
import societyhelp.dao.mysql.impl.UserDetails;
import societyhelp.dao.mysql.impl.WaterSuppyReading;
import societyhelp.parser.LoadBhowaInitialData;

/**
 * Created by divang.sharma on 8/6/2016.
 */
public interface ISocietyHelpDatabase {
	
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
    void  uploadMonthlyTransactions(Object transactionsObj) throws Exception;

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
    List<ExpenseType> getExpenseType() throws Exception;

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

    /**
     * Create a User login
     * @param loginId
     * @param password
     * @throws Exception
     */
    void createLogin(String loginId, String password, String adminLoginId) throws Exception;

    /**
     * Add society flat details
     * @param flat
     * @throws Exception
     */
    void addFlatDetails(Object flat) throws Exception;

    /**
     * It returns all the logins
     * @return
     * @throws Exception
     */
    List<Login> getAllLogins(String loginId) throws Exception;

    /**
     * It returns all the flats
     * @return
     * @throws Exception
     */
    List<Flat> getAllFlats() throws Exception;

    /**
     * Insert new user
     * @param userDetails
     * @throws Exception
     */
    void addUserDetails(Object userDetails)  throws Exception;

    /**
     * It returns all details of transactions like user_id, flat_id.
     * @return
     * @throws Exception
     */
    List<SocietyHelpTransaction> getAllDetailsTransactions() throws Exception;

    /**
     * It stores the verified transactions in DB
     * @param obj
     * @throws Exception
     */
    List<SocietyHelpTransaction> saveVerifiedTransactions(Object obj) throws  Exception;

    /**
     * Return users all transactions
     * @return
     * @throws Exception
     */
    List<SocietyHelpTransaction> getMyTransactions(String userId) throws Exception;

    /**
     * It returns the dues of particular flat.
     * @param flatId
     * @return
     * @throws Exception
     */
    float getMyDue(String flatId) throws Exception;

    /**
     * Get user details of a login id
     * @return
     * @throws Exception
     */
    UserDetails getMyDetails(String loginId) throws Exception;

    /**
     * It adds the flat wise monthly maintenance
     * @param obj
     * @throws Exception
     */
    void addMonthlyMaintenance(Object obj) throws Exception;

    /**
     * Get Flat id wise all transactions
     * @param flatId
     * @return
     * @throws Exception
     */
    List<SocietyHelpTransaction> getFlatWiseTransactions(String flatId) throws Exception;

    /**
     * Returns all assigned login Ids from a society
     * @return
     * @throws Exception
     */
    List<Login> getAllAssignedLogins() throws Exception;

    /**
     *
     * @param payment
     */
    void addUserCashPayment(Object payment) throws Exception;

    /**
     * Return all un verified user cash transactions
     * @return
     * @throws Exception
     */
    List<UserPaid> getUnVerifiedCashPayment() throws Exception;

    /**
     * It set verified 1 in Database for supplied payment ids
     * @param userId
     * @param paymentIds
     * @throws Exception
     */
    void saveVerifiedCashPayment(String userId, String paymentIds) throws Exception;

    /**
     * Returen splitted transactions flat wise.
     *
     * @return
     * @throws Exception
     */
    List<UserPaid> generateSplittedTransactionsFlatWise() throws Exception;

    /**
     * Return all flat wise payables
     * @return
     * @throws Exception
     */
    List<FlatWisePayable> getFlatWisePayables() throws Exception;

    /**
     * Returns the staging transaction data
     * @return
     * @throws Exception
     */
    List<StagingTransaction> getAllStaggingTransaction() throws Exception;

    /**
     * Returns all balance sheet transactions
     * @return
     * @throws Exception
     */
    List<TransactionOnBalanceSheet> getBalanceSheetData() throws Exception;

    /**
     * It loads the previous society transcations.
     * @throws Exception
     */
    void loadInitialData(LoadBhowaInitialData.LoadData data) throws Exception;

    /**
     * It adds the society details
     * @param societyDetails
     * @throws Exception
     */
    void createSociety(SocietyDetails societyDetails) throws Exception;

    /**
     * All auto split transaction which are not validate by user. Use for Rented users
     * @return
     * @throws Exception
     */
    List<TransactionOnBalanceSheet> userWiseAutoSplitTransactions(String loginId) throws Exception;

    /**
     * All auto split transaction which are not validate by user. Use for Owner
     * @return
     * @throws Exception
     */
    List<TransactionOnBalanceSheet> flatWiseAutoSplitTransactions(String flatId) throws Exception;

    /**
     * Get all user's unsplitted transactions
     * @param loginId
     * @return
     * @throws Exception
     */
    public List<SocietyHelpTransaction> getUserWiseUnSplittedTransaction(String loginId) throws Exception;

    /**
     * Get all flat's unsplitted transactions
     * @param flatId
     * @return
     * @throws Exception
     */
    public List<SocietyHelpTransaction> getFlatWiseUnSplittedTransaction(String flatId) throws Exception;

    /**
     * Get all society
     * @return
     * @throws Exception
     */
    public List<SocietyDetails> getAllSociety() throws Exception;

    /**
     * Get all water suppliers
     */
    public List<WaterSuppyReading> getAllWaterSupplier() throws Exception;

    /**
     * Insert water supply reading
     * @param reading
     * @throws Exception
     */
    public void insertWaterReading(WaterSuppyReading reading) throws Exception;

    /**
     * Return all the water reading
     * @return
     * @throws Exception
     */
    public List<WaterSuppyReading> getAllWaterReading() throws Exception;

}
