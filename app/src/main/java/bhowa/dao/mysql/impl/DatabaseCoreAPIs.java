package bhowa.dao.mysql.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseCoreAPIs extends Queries {

    private static final String databaseDBURL = "jdbc:mysql://dbbhra.cr39zxmr47h3.us-east-1.rds.amazonaws.com:3306/BHOWA";
    private static final String databaseUser = "BHRA2010";
    private static final String databasePassword = "BHRA_2010";

    static
    {
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }catch(Exception e){
        	e.getStackTrace();
        }
    }

    private Connection getDBInstance()
    {
        try{
            Connection connection = DriverManager.getConnection(databaseDBURL, databaseUser, databasePassword);
            return connection;
        }catch(Exception e){
        	e.getStackTrace();
        }
        return null;
    }

    private void close(Connection connection, PreparedStatement pStat, ResultSet result) {

        try {
            if (connection != null) {
                connection.close();
            }

            if (pStat != null) {
                pStat.close();
            }

            if(result != null) {
                result.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean loginDB(String userName, String password) {
    	
    	Connection connection = null;
        PreparedStatement pStat = null;
        ResultSet result = null;
        try{
            connection = getDBInstance();
            pStat = connection.prepareStatement(loginQuery);
            pStat.setString(1, userName);
            pStat.setString(2, password);
            result = pStat.executeQuery();
            if(result != null && result.next()) return true;

        }catch(Exception e){
            System.err.println("Error");
        } finally {
            close(connection,pStat,result);
        }
        return false;
    }
    
    public void activityLoggingDB(Object activity) {
		
    	if(activity instanceof UserActivity)
		{
			UserActivity objActivity = (UserActivity) activity;
			Connection con = null;
			PreparedStatement pStat = null;
			ResultSet res = null;
			
			try
			{
				con = getDBInstance();
				pStat = con.prepareStatement(activityLoggingQuery);
				pStat.setString(1, objActivity.userName);
				pStat.setString(2, objActivity.mobileNo);
				pStat.setString(3, objActivity.activity.name());
				pStat.setString(4, objActivity.comment);
				pStat.setDate(5, new Date(System.currentTimeMillis()));
				res = pStat.executeQuery();
				
			} catch(Exception e){
				e.getStackTrace();
	        } finally {
	            close(con,pStat,res);
	        }
		}
	}

	public boolean isStatementAlreadyProcessedDB(String monthlyStatementFileName) {
		
		Connection con = null;
		PreparedStatement pStat = null;
		ResultSet res = null;
		
		try
		{
			con = getDBInstance();
			pStat = con.prepareStatement(isStatementProcessedQuery);
			pStat.setString(1, monthlyStatementFileName);
			res = pStat.executeQuery();
			if(res != null && res.next()) return true;
			
		} catch(Exception e){
			 e.getStackTrace();
        } finally {
            close(con,pStat,res);
        }
		return false;
	}

	public boolean uploadMonthlyTransactionsDB(Object transactions) {
		
		if(transactions instanceof BankStatement)
		{
			BankStatement bankStatement = (BankStatement) transactions;
			Connection con = null;
			PreparedStatement pStat = null;
			
			try
			{
				con = getDBInstance();
				pStat = con.prepareStatement(transactionQuery);
				
				for(BhowaTransaction t : bankStatement.allTransactions)
				{
					pStat.setInt(1, t.srNo);
					pStat.setString(2, t.name);
					pStat.setFloat(3, t.amount);
					pStat.setDate(4, new Date(t.transactionDate.getTime()));
					pStat.setString(5, t.transactionFlow);
					pStat.setString(6, t.type);
					pStat.setString(7, t.reference);
					pStat.addBatch();
					pStat.clearParameters();
				}
				
				pStat.executeBatch();
				
				saveStatementProcessedDB(bankStatement.bankStatementFileName);
				
				return true;
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		return false;
	}
	
	public void saveStatementProcessedDB(String monthlyStatementFileName) {
	
		Connection con = null;
		PreparedStatement pStat = null;
		ResultSet res = null;
		
		try
		{
			con = getDBInstance();
			pStat = con.prepareStatement(statementProcessedQuery);
			pStat.setString(1, monthlyStatementFileName);
			pStat.setDate(2, new Date(System.currentTimeMillis()));
			pStat.executeUpdate();
			
		} catch(Exception e){
			System.err.println(e.getMessage());
			 e.getStackTrace();
        } finally {
            close(con,pStat,res);
        }
	}

}
