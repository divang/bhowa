package bhowa.dao.mysql.impl;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import bhowa.app.R;
import bhowa.dao.BhowaDatabaseFactory;
import bhowa.dao.IBhowaDatabase;

/**
 * Created by divang.sharma on 8/6/2016.
 */
public class BhowaDatabaseAndroidSDKImpl extends AsyncTask<Object, Integer, Object> implements IBhowaDatabase {

	private DatabaseCoreAPIs dbCore = new DatabaseCoreAPIs();

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
		getAllTransactionStagingUsers
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
	public boolean login(Object userDetailsObj) {

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
	public void activityLogging(Object activity) {

		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.activityLogging.name(), activity);
		try {
			aTask.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isStatementAlreadyProcessed(String monthlyStatementFileName) {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.isStatementAlreadyProcessed.name(), monthlyStatementFileName);
		try {
			return (Boolean)aTask.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean uploadMonthlyTransactions(Object transactions) {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.uploadMonthlyTransactions.name(), transactions);
		try {
			return (Boolean)aTask.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void insertRawData(List<String> data) {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.insertRawData.name(), data);
		try {
			aTask.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> showRawData() {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.showRawData.name());
		try {
			return (List<String>)aTask.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deleteAllRawData() {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.deleteAllRawData.name());
		try {
			aTask.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<UserDetails> getAllUsers() {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.getActiveUser.name());
		try {
			return (List<UserDetails>)aTask.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<String> getExpenseType() {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.getExpenseType.name());
		try {
			return (List<String>)aTask.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void errorLogging(String data) {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.errorLogging.name());
		try {
			aTask.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> getAllTransactionStagingUsers() {
		AsyncTask<Object, Integer, Object> aTask = this.execute(QUERY_NAME.getAllTransactionStagingUsers.name());
		try {
			return (List<String>)aTask.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}
}


