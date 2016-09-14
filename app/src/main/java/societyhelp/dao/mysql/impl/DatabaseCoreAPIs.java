package societyhelp.dao.mysql.impl;

import android.util.Log;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import societyhelp.core.SocietyAuthorization;
import societyhelp.dao.SocietyHelpDatabaseFactory;

public class DatabaseCoreAPIs extends Queries {

	private String databaseDBURL;
	private String databaseUser;
	private String databasePassword;
	private boolean isInitialized;

	static
    {
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch(Exception e){
        	e.getStackTrace();
			Log.e("Error", "Driver Initialization failed", e);
        }
    }

	public DatabaseCoreAPIs(String strDatabaseDBURL, String strDatabaseUser, String strDatabasePassword)
	{
		init(strDatabaseDBURL, strDatabaseUser, strDatabasePassword);
		isInitialized = true;
	}

	private void init(String strDatabaseDBURL, String strDatabaseUser, String strDatabasePassword)
	{
		databaseDBURL = strDatabaseDBURL;
		databaseUser = strDatabaseUser;
		databasePassword = strDatabasePassword;
	}

    public Connection getDBInstance() throws Exception {

		if(!isInitialized) throw new ExceptionInInitializerError("DBURL, User and Password are not initialize.");

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
			/*
			select s.Database_URL, s.Database_User, s.Database_Password, s.Society_Name, l.Authorised_Activity from
			Login l
			inner join
			Society s
			on l.Society_Id = s.Society_Id
			where l.Login_Id='divangd'
			and l.Password = '1'
			and l.Status = 1
			 */
            connection = getDBInstance();
            pStat = connection.prepareStatement(loginQuery);
            pStat.setString(1, userName);
            pStat.setString(2, password);
            result = pStat.executeQuery();
            if(result != null && result.next())
			{
				//reset the DB URL, user and password
				String url = result.getString(1);
				String user = result.getString(2);
				String pass = result.getString(3);
				init(url, user, pass);
				//Reinitialize the factory class, so always new instance will use these configuration.
				SocietyHelpDatabaseFactory.init(url, user, pass);
				return true;
			}

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
				for(SocietyHelpTransaction t : bankStatement.allTransactions)
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
				u.userType = result.getString(3);

				u.isActive = result.getInt(4) == 1 ? true : false;
				u.flatId = result.getString(5);
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

	public void addUserDetails(Object oflat) throws Exception	{

		if(oflat instanceof UserDetails) {
			UserDetails ud = (UserDetails) oflat;
			Connection con = null;
			PreparedStatement pStat = null;
			ResultSet res = null;

			try {
				con = getDBInstance();
				pStat = con.prepareStatement(insertUserDetailsQuery);
				/*
		        User_Id, User_Type, Flat_Id,
                Name, Name_Alias, Mobile_No,
                Moble_No_Alternate,Email_Id,Address,
                Flat_Join_Date,Flat_Left_Date, Login_Id)
				 */
                pStat.setString(1, ud.userId);
				pStat.setString(2, ud.userType);
                pStat.setString(3, ud.flatId);

                pStat.setString(4, ud.userName);
                pStat.setString(5, ud.nameAlias);
                pStat.setLong(6, ud.mobileNo);

				pStat.setLong(7, ud.mobileNoAlternative);
				pStat.setString(8, ud.emailId);
                pStat.setString(9, ud.address);
				pStat.setDate(10, ud.flatJoinDate == null ? new Date(System.currentTimeMillis()) : ud.flatJoinDate);
                pStat.setDate(11, ud.flatLeftDate == null ? new Date(System.currentTimeMillis()) : ud.flatLeftDate);
                pStat.setString(12, ud.loginId);

/*				if(ud.flatLeftDate == null)
				{
					pStat.setNull(10, Types.DATE);
				}else {
					pStat.setDate(10, ud.flatLeftDate);
				}
*/
				pStat.executeUpdate();

			} catch (Exception e) {
				throw e;
			} finally {
				close(con, pStat, res);
			}
		}
	}

	public List<SocietyHelpTransaction> getAllDetailTransaction() throws Exception {
		List<SocietyHelpTransaction> list = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pStat = null;
		ResultSet result = null;
		try{
			/*
			Transaction_ID,StatementID,ud.Name,Amount," +
            "Transaction_Date,Transaction_Flow,Transaction_Mode,Transaction_Reference," +
            "ud.User_Id,ud.Flat_Id,Admin_Approved,Admin_Comment," +
                "User_Comment
		    */
			connection = getDBInstance();
			pStat = connection.prepareStatement(selectFinalTransactionQuery);
			result = pStat.executeQuery();
			while(result.next())
			{
				SocietyHelpTransaction t = new SocietyHelpTransaction();
				t.transactionId = result.getInt(1);
                t.srNo = result.getInt(2);
                t.name = result.getString(3);
                t.amount = result.getFloat(4);
                t.transactionDate = result.getDate(5);
                t.transactionFlow  = result.getString(6);
                t.type  = result.getString(7);
                t.reference  = result.getString(8);
                t.userId  = result.getString(9);
                t.flatId = result.getString(10);
                t.isAdminApproved  = result.getBoolean(11);
                t.adminComment  = result.getString(12);
                t.userComment = result.getString(13);
                list.add(t);
			}

		}catch(Exception e){
			throw e;
		} finally {
			close(connection,pStat,result);
		}
		return list;
	}

    public boolean saveVerifiedTransactionsDB(Object transactions) throws Exception{

        if(transactions instanceof BankStatement)
        {
            BankStatement bankStatement = (BankStatement) transactions;
            Connection con = null;
            PreparedStatement pStat = null;

            try
            {
                con = getDBInstance();

                pStat = con.prepareStatement(insertFinalTransactionQuery);
                for(SocietyHelpTransaction t : bankStatement.allTransactions)
                {
                    /*
                    "Insert into Transactions_Verified (" +
					"Amount, Transaction_Date,Transaction_Flow,Transaction_Mode,Transaction_Reference," +
					"User_Id, Flat_Id,Admin_Comment,User_Comment) " +
					"values (? , ?, ?, ?, ?, " +
					"? , ?, ?, ?) ";
                     */
                    if(t.userId != null) {
                        pStat.setFloat(1, t.amount);
                        if (t.transactionDate != null)
                            pStat.setDate(2, new Date(t.transactionDate.getTime()));
                        else pStat.setDate(2, new Date(System.currentTimeMillis()));
                        pStat.setString(3, t.transactionFlow);
                        pStat.setString(4, t.type);
                        pStat.setString(5, t.reference);
                        pStat.setString(6, t.userId);
                        pStat.setString(7, t.flatId);
                        pStat.setString(8, t.adminComment);
                        pStat.setString(9, t.userComment);

                        pStat.addBatch();
                        pStat.clearParameters();
                    }

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

    public List<SocietyHelpTransaction> getMyTransactions(String userId) throws Exception {
        List<SocietyHelpTransaction> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pStat = null;
        ResultSet result = null;
        try{

            connection = getDBInstance();
            pStat = connection.prepareStatement(myTransactionsQuery);
            pStat.setString(1, userId);
            result = pStat.executeQuery();
            while(result.next())
            {
                /*
               SELECT `Transaction_ID`, `Amount`, `Transaction_Date`,
               `Transaction_Flow`, `Transaction_Mode`, `Transaction_Reference`,
               `Flat_Id`, `Admin_Comment`,`User_Comment`
               FROM Transactions_Verified
                 */
                SocietyHelpTransaction t = new SocietyHelpTransaction();
                t.transactionId = result.getInt(1);
                t.amount = result.getFloat(2);
                t.transactionDate = result.getDate(3);
                t.transactionFlow  = result.getString(4);
                t.type  = result.getString(5);
                t.reference  = result.getString(6);
                t.flatId = result.getString(7);
                t.adminComment  = result.getString(8);
                t.userComment = result.getString(9);
                list.add(t);
            }

        }catch(Exception e){
            throw e;
        } finally {
            close(connection,pStat,result);
        }
        return list;
    }


	public float myDue(String flatId) throws Exception{

		Connection con = null;
		PreparedStatement pStat = null;
		ResultSet res = null;

		try
		{
			con = getDBInstance();
			pStat = con.prepareStatement(myDueQuery);
			pStat.setString(1, flatId);
			pStat.setString(2, flatId);
			pStat.setString(3, flatId);
			pStat.setString(4, flatId);
			res = pStat.executeQuery();
			if(res != null && res.next())
			{
				return res.getFloat("MyDue");
			}

		} catch(Exception e){
			throw e;
		} finally {
			close(con,pStat,res);
		}
		return 0f;
	}

	public UserDetails getMyDetails(String loginId) throws Exception	{

		UserDetails u = null;
		Connection connection = null;
		PreparedStatement pStat = null;
		ResultSet result = null;
		try{
			connection = getDBInstance();
			pStat = connection.prepareStatement(myDetailsQuery);
			pStat.setString(1, loginId);
			result = pStat.executeQuery();
			if(result.next())
			{
				u = new UserDetails();
				u.userId = result.getString(1);
				u.loginId = result.getString(2);
				u.userType = result.getString(3);

				u.isActive = result.getInt(4) == 1 ? true : false;
				u.flatId = result.getString(5);
				u.userName = result.getString(6);

				u.nameAlias = result.getString(7);
				u.mobileNo = result.getLong(8);
				u.mobileNoAlternative = result.getLong(9);

				u.emailId = result.getString(10);
				u.address = result.getString(11);
				u.flatJoinDate = result.getDate(12);

				u.flatLeftDate = result.getDate(13);
				String userAuthIds = result.getString(14);

				if(userAuthIds != null)
					for(String authId :  userAuthIds.split(","))
						u.sAuthorizations.add(SocietyAuthorization.Type.values()[Integer.valueOf(authId)]);

		 	}

		} catch (Exception e) {
			throw e;
		} finally {
			close(connection,pStat,result);
		}
		return u;
	}


	public void addMonthlyMaintenance(Object objFwp) throws Exception {

		if(objFwp instanceof  FlatWisePayable) {

			FlatWisePayable fwp = (FlatWisePayable) objFwp;
			Connection con = null;
			PreparedStatement pStat = null;
			ResultSet res = null;

			try {
				con = getDBInstance();
				pStat = con.prepareStatement(addFlatWiseMaintenanceQuery);

				pStat.setInt(1, fwp.month);
				pStat.setInt(2, fwp.year);
				pStat.setString(3, fwp.expenseType.name());

				pStat.executeUpdate();

			} catch (Exception e) {
				throw e;
			} finally {
				close(con, pStat, res);
			}
		}
	}

	public List<SocietyHelpTransaction> getFlatWiseTransactions(String flatId) throws Exception {
		List<SocietyHelpTransaction> list = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pStat = null;
		ResultSet result = null;
		try{
			connection = getDBInstance();
			pStat = connection.prepareStatement(flatWiseTransactionsQuery);
			pStat.setString(1, flatId);
			result = pStat.executeQuery();
			while(result.next())
			{
				SocietyHelpTransaction t = new SocietyHelpTransaction();
				t.flatId = result.getString(1);
				t.transactionFlow = result.getString(2);
				t.amount = result.getFloat(3);
				t.transactionDate = result.getDate(4);
				list.add(t);
			}

		}catch(Exception e){
			throw e;
		} finally {
			close(connection,pStat,result);
		}
		return list;
	}

}
