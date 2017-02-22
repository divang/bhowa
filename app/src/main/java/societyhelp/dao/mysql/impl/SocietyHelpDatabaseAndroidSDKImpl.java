package societyhelp.dao.mysql.impl;

import java.util.List;
import java.util.concurrent.ExecutionException;
import android.os.AsyncTask;
import societyhelp.dao.ISocietyHelpDatabase;
import societyhelp.parser.LoadBhowaInitialData;

/**
 * Created by divang.sharma on 8/6/2016.
 */
public class SocietyHelpDatabaseAndroidSDKImpl extends AsyncTask<Object, Integer, Object> implements ISocietyHelpDatabase {

	private DatabaseCoreAPIs dbCore;

	public SocietyHelpDatabaseAndroidSDKImpl(String dburl, String userName, String password)
	{
		dbCore = new DatabaseCoreAPIs(dburl, userName, password);
	}

	enum QUERY_NAME
	{
		login,
		activityLogging,
		isStatementAlreadyProcessed,
		uploadMonthlyTransactions,
		insertRawData,
		showRawData,
		deleteAllRawData,
		getActiveUser,
		getExpenseType,
		errorLogging,
		getAllTransactionStagingUsers,
		setAliasOfUserId,
		createLogin,
		addFlatDetails,
        getAllLogins,
        getAllFlats,
        addUserDetails,
		getAllDetailsTransactions,
		saveVerifiedTransactions,
		getMyTransactions,
		getMyDue,
        getMyDetails,
		addMonthlyMaintenance,
		getFlatWiseTransactions,
		getAllAssignedLogins,
		addUserCashPayment,
		getUnVerifiedCashPayment,
		saveVerifiedCashPayment,
		generateSplittedTransactionsFlatWise,
		getFlatWisePayables,
		getAllStagingTransactions,
		balanceSheetData,
		loadInitialData,
		createSociety,
		userWiseAutoSplitTransactions,
		flatWiseAutoSplitTransactions,
		userWiseUnSplittedTransactions,
		flatWiseUnSplittedTransactions,
		getSociety,
		allWaterSupplier,
		insertWaterReading,
		getAllWaterReadings,
		getUnsettledCreditTransaction,
		getUnsettledDebitTransaction,
		settledCreditTransaction
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected Object doInBackground(Object... params) {

		Object result = null;
		try{

			switch(QUERY_NAME.valueOf(String.valueOf(params[0])))
			{
				case login:
					result = dbCore.loginDB(String.valueOf(params[1]), String.valueOf(params[2]));
					break;

				case activityLogging:
					dbCore.activityLoggingDB(params[1]);
					break;

				case isStatementAlreadyProcessed:
					result = dbCore.isStatementAlreadyProcessedDB(String.valueOf(params[1]));
					break;

				case uploadMonthlyTransactions:
					result = dbCore.uploadMonthlyTransactionsDB(params[1]);
					break;

				case deleteAllRawData:
					dbCore.deleteAllRawData();
					break;

				case errorLogging:
					dbCore.errorLogging(String.valueOf(params[1]));
					break;

				case getActiveUser:
					result = dbCore.getAllUsers();
					break;

				case getAllTransactionStagingUsers:
					result = dbCore.getAllTransactionStagingUsers();
					break;

				case getExpenseType:
					result = dbCore.getExpenseType();
					break;

				case insertRawData:
					dbCore.insertRawData((List<String>)params[1]);
					break;

				case showRawData:
					result = dbCore.showRawData();
					break;

				case setAliasOfUserId:
					dbCore.setAliasOfUserId(String.valueOf(params[1]), String.valueOf(params[2]));
					break;

				case createLogin:
					dbCore.createUserLogin(String.valueOf(params[1]), String.valueOf(params[2]), String.valueOf(params[3]));
					break;

				case addFlatDetails:
					dbCore.addFlatDetails(params[1]);
					break;

                case getAllLogins:
                    result = dbCore.getAllLogin(String.valueOf(params[1]));
                    break;

                case getAllFlats:
                    result = dbCore.getAllFlats();
                    break;

                case addUserDetails:
                    dbCore.addUserDetails(params[1]);
                    break;

				case getAllDetailsTransactions:
					result = dbCore.getAllDetailTransaction();
					break;

				case saveVerifiedTransactions:
					result = dbCore.saveVerifiedTransactionsDB(params[1]);
					break;

				case getMyTransactions:
					result = dbCore.getMyTransactions(String.valueOf(params[1]));
					break;

				case getMyDue:
					result = dbCore.myDue(String.valueOf(params[1]));
					break;

                case getMyDetails:
                    result = dbCore.getMyDetails(String.valueOf(params[1]));
                    break;

				case addMonthlyMaintenance:
					dbCore.addMonthlyMaintenance(params[1]);
					break;

				case getFlatWiseTransactions:
					result = dbCore.getFlatWiseTransactions(String.valueOf(params[1]));
					break;

				case getAllAssignedLogins:
					result = dbCore.getAllAssignedLogin();
					break;

				case addUserCashPayment:
					dbCore.addUserCashPaymentDB(params[1]);
					break;

				case getUnVerifiedCashPayment:
					result = dbCore.getUnVerifiedUserCashPayment();
					break;

				case saveVerifiedCashPayment:
					dbCore.saveVerifiedCashPayment(String.valueOf(params[1]), String.valueOf(params[2]));
					break;

				case generateSplittedTransactionsFlatWise:
					result = dbCore.generateSplittedTransactionsFlatWise();
					break;
				case getFlatWisePayables:
					result = dbCore.getFlatWisePayables();
					break;
				case getAllStagingTransactions:
					result = dbCore.getAllStaggingTransaction();
					break;
				case balanceSheetData:
					result = dbCore.getBalanceSheetData();
					break;
				case loadInitialData:
					dbCore.loadInitialData(params[1]);
					break;
				case createSociety:
					dbCore.createSociety(params[1]);
					break;
				case userWiseAutoSplitTransactions:
					result = dbCore.userWiseAutoSplitTransactions(String.valueOf(params[1]));
					break;
				case flatWiseAutoSplitTransactions:
					result = dbCore.flatWiseAutoSplitTransactions(String.valueOf(params[1]));
					break;
				case userWiseUnSplittedTransactions:
					result = dbCore.getUserWiseUnSplittedTransaction(String.valueOf(params[1]));
					break;
				case flatWiseUnSplittedTransactions:
					result = dbCore.getFlatWiseUnSplittedTransaction(String.valueOf(params[1]));
					break;
				case getSociety:
					result = dbCore.getAllSociety();
					break;
				case allWaterSupplier:
					result = dbCore.getAllWaterSupplier();
					break;
				case insertWaterReading:
					dbCore.insertWaterReading(params[1]);
					break;
				case getAllWaterReadings:
					result = dbCore.getAllWaterReading();
					break;
				case getUnsettledCreditTransaction:
					result = dbCore.getUnsettledCreditTransaction();
					break;
				case getUnsettledDebitTransaction:
					result = dbCore.getUnsettledDebitTransaction();
					break;
				case settledCreditTransaction:
					dbCore.settledCreditTransaction(params[1]);
					break;
			}

		}catch(Exception e){
			System.err.println("Error");
		}
		return result;
	}

	@Override
	protected void onPostExecute(Object result) {
	}

	@Override
	public boolean login(Object userDetailsObj)  throws Exception{

		if(userDetailsObj instanceof UserDetails)
		{
			UserDetails user = (UserDetails) userDetailsObj;
			AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.login.name(), user.userName, user.password);
			try {
				return (Boolean)aTask.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public void activityLogging(Object activity)  throws Exception{

		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.activityLogging.name(), activity);
		try {
			aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public boolean isStatementAlreadyProcessed(String monthlyStatementFileName)  throws Exception{
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.isStatementAlreadyProcessed.name(), monthlyStatementFileName);
		try {
			return (Boolean)aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void uploadMonthlyTransactions(Object transactions)  throws Exception{
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.uploadMonthlyTransactions.name(), transactions);
		try {
			aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void insertRawData(List<String> data)  throws Exception{
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.insertRawData.name(), data);
		try {
			aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<String> showRawData()  throws Exception{
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.showRawData.name());
		try {
			return (List<String>)aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void deleteAllRawData()  throws Exception{
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.deleteAllRawData.name());
		try {
			aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<UserDetails> getAllUsers()  throws Exception{
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.getActiveUser.name());
		try {
			return (List<UserDetails>)aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<ExpenseType> getExpenseType()  throws Exception{
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.getExpenseType.name());
		try {
			return (List<ExpenseType>)aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void errorLogging(String data)  throws Exception{
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.errorLogging.name(), data);
		try {
			aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<String> getAllTransactionStagingUsers()  throws Exception{
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.getAllTransactionStagingUsers.name());
		try {
			return (List<String>)aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void setAliasOfUserId(String userId, String allAliasNames)  throws Exception{
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.setAliasOfUserId.name(), userId, allAliasNames);
		try {
			aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void createLogin(String loginId, String password, String adminLoginId) throws Exception {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.createLogin.name(), loginId, password, adminLoginId);
		try {
			aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void addFlatDetails(Object flat) throws Exception {

		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.addFlatDetails.name(), flat);
		try {
			aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

    @Override
    public List<Login> getAllLogins(String loginId) throws Exception {
        AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.getAllLogins.name(), loginId);
        try {
            return (List<Login>)aTask.get();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Flat> getAllFlats() throws Exception {
        AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.getAllFlats.name());
        try {
            return (List<Flat>)aTask.get();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void addUserDetails(Object userDetails) throws Exception {
        AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.addUserDetails.name(),userDetails);
        try {
            aTask.get();
        } catch (Exception e) {
            throw e;
        }
    }

	@Override
	public List<SocietyHelpTransaction> getAllDetailsTransactions() throws Exception {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.getAllDetailsTransactions.name());
		try {
			return (List<SocietyHelpTransaction>)aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<SocietyHelpTransaction> saveVerifiedTransactions(Object obj) throws Exception {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.saveVerifiedTransactions.name(),obj);
		try {
			return (List<SocietyHelpTransaction>)aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<SocietyHelpTransaction> getMyTransactions(String userId) throws Exception {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.getMyTransactions.name(), userId);
		try {
			return (List<SocietyHelpTransaction>)aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public float getMyDue(String flatId) throws Exception {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.getMyDue.name(), flatId);
		try {
			return (float)aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

    @Override
    public UserDetails getMyDetails(String loginId) throws Exception {
        AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.getMyDetails.name(), loginId);
        try {
            return (UserDetails)aTask.get();
        } catch (Exception e) {
            throw e;
        }
    }

	@Override
	public void addMonthlyMaintenance(Object obj) throws Exception {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.addMonthlyMaintenance.name(), obj);
		try {
			aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<SocietyHelpTransaction> getFlatWiseTransactions(String flatId) throws Exception {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.getFlatWiseTransactions.name(), flatId);
		try {
			return (List<SocietyHelpTransaction>)aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<Login> getAllAssignedLogins() throws Exception {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.getAllAssignedLogins.name());
		try {
			return (List<Login>)aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void addUserCashPayment(Object payment) throws Exception {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.addUserCashPayment.name(), payment);
		try {
			aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<UserPaid> getUnVerifiedCashPayment() throws Exception {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.getUnVerifiedCashPayment.name());
		try {
			return (List<UserPaid>)aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void saveVerifiedCashPayment(String userId, String paymentIds) throws Exception {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.saveVerifiedCashPayment.name(), userId, paymentIds);
		try {
			aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<UserPaid> generateSplittedTransactionsFlatWise() throws Exception {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.generateSplittedTransactionsFlatWise.name());
		try {
			return (List<UserPaid>)aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<FlatWisePayable> getFlatWisePayables() throws Exception {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.getFlatWisePayables.name());
		try {
			return (List<FlatWisePayable>)aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<StagingTransaction> getAllStaggingTransaction() throws Exception {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.getAllStagingTransactions.name());
		try {
			return (List<StagingTransaction>)aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<TransactionOnBalanceSheet> getBalanceSheetData() throws Exception {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.balanceSheetData.name());
		try {
			return (List<TransactionOnBalanceSheet>)aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void loadInitialData(LoadBhowaInitialData.LoadData data) throws Exception {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.loadInitialData.name(), data);
		try {
			aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void createSociety(SocietyDetails societyDetails) throws Exception {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.createSociety.name(), societyDetails);
		try {
			aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<TransactionOnBalanceSheet> userWiseAutoSplitTransactions(String loginId) throws Exception {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.userWiseAutoSplitTransactions.name(), loginId);
		try {
			return (List<TransactionOnBalanceSheet>)aTask.get();
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public List<TransactionOnBalanceSheet> flatWiseAutoSplitTransactions(String flatId) throws Exception {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.flatWiseAutoSplitTransactions.name(), flatId);
		try {
			return (List<TransactionOnBalanceSheet>)aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<SocietyHelpTransaction> getUserWiseUnSplittedTransaction(String loginId) throws Exception {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.userWiseUnSplittedTransactions.name(), loginId);
		try {
			return (List<SocietyHelpTransaction>)aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<SocietyHelpTransaction> getFlatWiseUnSplittedTransaction(String flatId) throws Exception {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.flatWiseUnSplittedTransactions.name(), flatId);
		try {
			return (List<SocietyHelpTransaction>)aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<SocietyDetails> getAllSociety() throws Exception {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.getSociety.name());
		try {
			return (List<SocietyDetails>)aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<WaterSuppyReading> getAllWaterSupplier() throws Exception {

		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.allWaterSupplier.name());
		try {
			return (List<WaterSuppyReading>)aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void insertWaterReading(WaterSuppyReading reading) throws Exception {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.insertWaterReading.name(), reading);
		try {
			aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<WaterSuppyReading> getAllWaterReading() throws Exception {

		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.getAllWaterReadings.name());
		try {
			return (List<WaterSuppyReading>)aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<StagingTransaction> getUnsettledCreditTransaction() throws Exception {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.getUnsettledCreditTransaction.name());
		try {
			return (List<StagingTransaction>)aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<StagingTransaction> getUnsettledDebitTransaction() throws Exception {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.getUnsettledDebitTransaction.name());
		try {
			return (List<StagingTransaction>)aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void settledCreditTransaction(Object transactions) throws Exception {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.settledCreditTransaction.name(), transactions);
		try {
			aTask.get();
		} catch (Exception e) {
			throw e;
		}
	}

}