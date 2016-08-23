package bhowa.dao.mysql.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public Connection getDBInstance()
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

				pStat = con.prepareStatement(transactionStagingQuery);
				for(BhowaTransaction t : bankStatement.allTransactions)
				{
					pStat.setInt(1, t.srNo);
					pStat.setString(2, t.name);
					pStat.setFloat(3, t.amount);
					if(t.transactionDate != null) pStat.setDate(4, new Date(t.transactionDate.getTime()));
					else pStat.setDate(4, new Date(System.currentTimeMillis()));
					pStat.setString(5, t.transactionFlow);
					pStat.setString(6, t.type);
					pStat.setString(7, t.reference);
					pStat.addBatch();
					pStat.clearParameters();
				}

				System.out.println("Executing Batch operations");
				pStat.executeBatch();
				System.out.println("Executed Batch operations");
				saveStatementProcessedDB(bankStatement.bankStatementFileName);
				
				return true;
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			finally {
				close(con,pStat, null);
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

	public void insertRawData(List<String> data)
	{
		Connection con = null;
		PreparedStatement pStat = null;
		ResultSet res = null;

		try
		{
			con = getDBInstance();
			pStat = con.prepareStatement(insertRawDataQuery);
			for(String d: data) {
				pStat.setString(1, d);
				pStat.addBatch();
				pStat.clearParameters();
			}
			pStat.executeBatch();

		} catch(Exception e){
			System.err.println(e.getMessage());
			e.getStackTrace();
		} finally {
			close(con,pStat,res);
		}

	}

	public List<String> showRawData()
	{
		List<String> allRawData = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pStat = null;
		ResultSet result = null;
		try{
			connection = getDBInstance();
			pStat = connection.prepareStatement(selectAllRawQuery);
			result = pStat.executeQuery();
			while(result.next())
			{
				allRawData.add(result.getString(1));
			}

		}catch(Exception e){
			System.err.println("Error");
		} finally {
			close(connection,pStat,result);
		}
		return allRawData;
	}

	public void deleteAllRawData()
	{
		Connection con = null;
		PreparedStatement pStat = null;
		ResultSet res = null;

		try
		{
			con = getDBInstance();
			pStat = con.prepareStatement(deleteAllRawQuery);
			pStat.executeUpdate();

		} catch(Exception e){
			System.err.println(e.getMessage());
			e.getStackTrace();
		} finally {
			close(con,pStat,res);
		}

	}

	public List<UserDetails> getActiveUser()
	{
		List<UserDetails> users = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pStat = null;
		ResultSet result = null;
		try{
			connection = getDBInstance();
			pStat = connection.prepareStatement(activeUserQuery);
			result = pStat.executeQuery();
			while(result.next())
			{
				//`User_Id`, `Password`, `Status`, `Flat_No`, `Name`,
				// //LEFT(`Name_Alias`, 256), `Mobile_No`, `Email_Id`, `Maintenance_Monthly`, `Address`, `Block`
				UserDetails u = new UserDetails();
				u.userId = result.getString(1);
				u.password = result.getString(2);
				u.isActive = result.getInt(3) == 1 ? true : false;
				u.flatNo = result.getInt(4);
				u.userName = result.getString(5);
				u.nameAlias = result.getString(6);
				u.mobileNo = result.getLong(7);
				u.emailId = result.getString(8);
				u.maintenanceMonthly = result.getFloat(9);
				u.address = result.getString(10);
				u.block = result.getString(11);
				users.add(u);
			}

		} catch (Exception e) {
			System.err.println("Error");
		} finally {
			close(connection,pStat,result);
		}
		return users;
	}


	public List<String> getExpenseType()
	{
		List<String> types = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pStat = null;
		ResultSet result = null;
		try{
			connection = getDBInstance();
			pStat = connection.prepareStatement(expenseTypeQuery);
			result = pStat.executeQuery();
			while(result.next())
			{
				types.add(result.getString(1));
			}

		}catch(Exception e){
			System.err.println("Error");
		} finally {
			close(connection,pStat,result);
		}
		return types;
	}

	public void errorLogging(String data)
	{
		Connection con = null;
		PreparedStatement pStat = null;
		ResultSet res = null;

		try
		{
			con = getDBInstance();
			pStat = con.prepareStatement(errorLoggingQuery);
			pStat.setString(1, data);
			pStat.executeUpdate();

		} catch(Exception e){
			System.err.println(e.getMessage());
			e.getStackTrace();
		} finally {
			close(con,pStat,res);
		}

	}


	public List<String> getAllTransactionStagingUsers()
	{
		List<String> uniqueNames = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pStat = null;
		ResultSet result = null;
		try{
			connection = getDBInstance();
			pStat = connection.prepareStatement(allTransactionStagingUsersQuery);
			result = pStat.executeQuery();
			while(result.next())
			{
				uniqueNames.add(result.getString(1));
			}

		}catch(Exception e){
			System.err.println("Error");
		} finally {
			close(connection,pStat,result);
		}
		return uniqueNames;
	}

}
