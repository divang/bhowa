package bhowa.dao.mysql.impl;

import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import bhowa.dao.IBhowaDatabase;

/**
 * Created by divang.sharma on 8/6/2016.
 */
public class BhowaDatabaseAndroidSDKImpl extends AsyncTask<String, Void, Boolean> implements IBhowaDatabase {

	private DatabaseCoreAPIs dbCore = new DatabaseCoreAPIs();
    
    @Override
    public boolean login(Object userDetailsObj) {
    	
    	if(userDetailsObj instanceof UserLogin)
    	{
    		UserLogin user = (UserLogin) userDetailsObj;
	    	AsyncTask<String, Void, Boolean> aTask = this.execute(user.userName, user.password);
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
    protected Boolean doInBackground(String... params) {

        try{
        	return dbCore.loginDB(params[0], params[1]);
        }catch(Exception e){
            System.err.println("Error");
        } 
        return false;
    }

	@Override
	public void activityLogging(Object activity) {
		dbCore.activityLoggingDB(activity);
	}

	@Override
	public boolean isStatementAlreadyProcessed(String monthlyStatementFileName) {
		return dbCore.isStatementAlreadyProcessedDB(monthlyStatementFileName);
	}

	@Override
	public boolean uploadMonthlyTransactions(Object transactions) {
		return dbCore.uploadMonthlyTransactionsDB(transactions);
	}
}
