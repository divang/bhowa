package bhowa.dao.mysql.impl;

import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import bhowa.dao.IBhowaDatabase;

/**
 * Created by divang.sharma on 8/6/2016.
 */
public class BhowaDatabaseAndroidSDKImpl extends AsyncTask<Object, Void, Boolean> implements IBhowaDatabase {

	private DatabaseCoreAPIs dbCore = new DatabaseCoreAPIs();

	enum QUERY_NAME
	{
		login,
		activityLogging,
		isStatementAlreadyProcessed,
		uploadMonthlyTransactions
	}

	@Override
	protected Boolean doInBackground(Object... params) {

		boolean result = true;
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
			}
		}catch(Exception e){
			System.err.println("Error");
		}
		return result;
	}

	@Override
	public boolean login(Object userDetailsObj) {

		if(userDetailsObj instanceof UserLogin)
		{
			UserLogin user = (UserLogin) userDetailsObj;
			AsyncTask<Object, Void, Boolean> aTask = this.execute(QUERY_NAME.login.name(), user.userName, user.password);
			try {
				return aTask.get();
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

		AsyncTask<Object, Void, Boolean> aTask = this.execute(QUERY_NAME.activityLogging.name(), activity);
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
		AsyncTask<Object, Void, Boolean> aTask = this.execute(QUERY_NAME.isStatementAlreadyProcessed.name(), monthlyStatementFileName);
		try {
			return aTask.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean uploadMonthlyTransactions(Object transactions) {
		AsyncTask<Object, Void, Boolean> aTask = this.execute(QUERY_NAME.uploadMonthlyTransactions.name(), transactions);
		try {
			return aTask.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return false;
	}
}


