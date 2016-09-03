package bhowa.dao.mysql.impl;

import android.util.Log;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseCoreAPIs extends Queries {

	private static final String databaseDBURL = "jdbc:mysql://sql6.freemysqlhosting.net:3306/sql6134070";
	private static final String databaseUser = "sql6134070";
	private static final String databasePassword = "UAgCjcJ2d4";

	static
    {
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch(Exception e){
        	e.getStackTrace();
			Log.e("Error", "Driver Initialization failed", e);
        }
    }

    public Connection getDBInstance() throws Exception {
        try{
            Connection connection = DriverManager.getConnection(databaseDBURL, databaseUser, databasePassword);
            return connection;
        }catch(Exception e){
			throw e;
        }
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
			Log.e("Error", "Connection close has some problem.", e);
        }
    }

    public boolean loginDB(String userName, String password) throws Exception{
    	
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
			throw e;
        } finally {
            close(connection,pStat,result);
        }
        return false;
    }
    
    public void activityLoggingDB(Object activity) throws Exception{
		
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
				throw e;
	        } finally {
	            close(con,pStat,res);
	        }
		}
	}

	public boolean isStatementAlreadyProcessedDB(String monthlyStatementFileName) throws Exception{
		
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
			throw e;
        } finally {
            close(con,pStat,res);
        }
		return false;
	}

	public boolean uploadMonthlyTransactionsDB(Object transactions) throws Exception{
		
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
			}catch(Exception e) {
				throw e;
			}
			finally {
				close(con,pStat, null);
			}
			
		}
		return false;
	}
	
	public void saveStatementProcessedDB(String monthlyStatementFileName) throws Exception{
	
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
			throw e;
        } finally {
            close(con,pStat,res);
        }
	}

	public void insertRawData(List<String> data) throws Exception {
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
			throw e;
		} finally {
			close(con,pStat,res);
		}

	}

	public List<String> showRawData() throws Exception	{
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
			throw e;
		} finally {
			close(connection,pStat,result);
		}
		return allRawData;
	}

	public void deleteAllRawData() throws Exception	{
		Connection con = null;
		PreparedStatement pStat = null;
		ResultSet res = null;

		try
		{
			con = getDBInstance();
			pStat = con.prepareStatement(deleteAllRawQuery);
			pStat.executeUpdate();

		} catch(Exception e){
			throw e;
		} finally {
			close(con,pStat,res);
		}

	}

	public List<UserDetails> getAllUsers() throws Exception	{
		List<UserDetails> users = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pStat = null;
		ResultSet result = null;
		try{
			connection = getDBInstance();
			pStat = connection.prepareStatement(allUsersQuery);
			result = pStat.executeQuery();
			while(result.next())
			{
				/*
				SELECT `User_Id`, `Login_Id`, `User_Type_Id`,
				`Status`, `Flat_Id`, `Name`,
				LEFT(`Name_Alias`, 256), `Mobile_No`, `Moble_No_Alternate`,
				`Email_Id`, `Address`, `Flat_Join_Date`,
				`Flat_Left_Date`
				FROM `sql6134070`.`User_Details` ORDER BY `Login_Id` ASC LIMIT 0, 1000;
				 */
				UserDetails u = new UserDetails();
				u.userId = result.getString(1);
				u.loginId = result.getString(2);
				u.userTypeId = result.getString(3);

				u.isActive = result.getInt(4) == 1 ? true : false;
				u.flatId = result.getInt(5);
				u.userName = result.getString(6);

				u.nameAlias = result.getString(7);
				u.mobileNo = result.getLong(8);
				u.mobileNoAlternative = result.getLong(9);

				u.emailId = result.getString(10);
				u.address = result.getString(11);
				u.flatJoinDate = result.getDate(12);

				u.flatLeftDate = result.getDate(13);

				users.add(u);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			close(connection,pStat,result);
		}
		return users;
	}


	public List<String> getExpenseType() throws Exception {
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
			throw e;
		} finally {
			close(connection,pStat,result);
		}
		return types;
	}

	public void errorLogging(String data) throws Exception	{
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
			throw e;
		} finally {
			close(con,pStat,res);
		}
	}


	public List<String> getAllTransactionStagingUsers() throws Exception {
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
			throw e;
		} finally {
			close(connection,pStat,result);
		}
		return uniqueNames;
	}

	public void setAliasOfUserId(String userId, String alias) throws Exception
	{
		Connection con = null;
		PreparedStatement pStat = null;
		ResultSet res = null;

		try
		{
			con = getDBInstance();
			pStat = con.prepareStatement(updateAliasUserId);
			pStat.setString(1, alias);
			pStat.setString(2, userId);
			pStat.executeUpdate();

		} catch(Exception e){
			throw e;
		} finally {
			close(con,pStat,res);
		}
	}

	public void createUserLogin(String loginId, String password) throws Exception	{
		Connection con = null;
		PreparedStatement pStat = null;
		ResultSet res = null;

		try
		{
			con = getDBInstance();
			pStat = con.prepareStatement(createLoginQuery);
			pStat.setString(1, loginId);
			pStat.setString(2, password);
			pStat.executeUpdate();

		} catch(Exception e){
			throw e;
		} finally {
			close(con,pStat,res);
		}
	}

	public void addFlatDetails(Object oflat) throws Exception	{

		if(oflat instanceof Flat) {
			Flat flat = (Flat) oflat;
			Connection con = null;
			PreparedStatement pStat = null;
			ResultSet res = null;

			try {
				con = getDBInstance();
				pStat = con.prepareStatement(insertFlatDetailsQuery);
				pStat.setString(1, flat.flatId);
				pStat.setString(2, flat.flatNumber);
				pStat.setInt(3, flat.area);
				pStat.setFloat(4, flat.maintenanceAmount);
				pStat.setString(5, flat.block);

				pStat.executeUpdate();

			} catch (Exception e) {
				throw e;
			} finally {
				close(con, pStat, res);
			}
		}
	}

	public List<Login> getAllLogin() throws Exception {
		List<Login> logins = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pStat = null;
		ResultSet result = null;
		try{
			/*
			SELECT `Login_Id`, `Password`, `Status`, `Authorised_Activity` FROM `sql6134070`.`Login` LIMIT 0, 1000;
			 */
			connection = getDBInstance();
			pStat = connection.prepareStatement(selectAllLoginQuery);
			result = pStat.executeQuery();
			while(result.next())
			{
				Login l = new Login();
				l.loginId = result.getString(1);
				l.password = result.getString(2);
				l.status = result.getBoolean(3);
				l.authorisedActivity = result.getString(4);
				logins.add(l);
			}

		}catch(Exception e){
			throw e;
		} finally {
			close(connection,pStat,result);
		}
		return logins;
	}

	public List<Flat> getAllFlats() throws Exception {
		List<Flat> flats = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pStat = null;
		ResultSet result = null;
		try{
			/*
			SELECT `Flat_Id`, `Flat_Number`, `Area`, `Maintenance_Amount`, `Block_Number`, `Status`
			FROM `sql6134070`.`Flat` LIMIT 0, 1000;
			 */
			connection = getDBInstance();
			pStat = connection.prepareStatement(selectAllFlatQuery);
			result = pStat.executeQuery();
			while(result.next())
			{
				Flat l = new Flat();
				l.flatId = result.getString(1);
				l.flatNumber = result.getString(2);
				l.area = result.getInt(3);
				l.maintenanceAmount = result.getFloat(4);
				l.block = result.getString(5);
				l.status = result.getBoolean(6);

				flats.add(l);
			}

		}catch(Exception e){
			throw e;
		} finally {
			close(connection,pStat,result);
		}
		return flats;
	}
}
