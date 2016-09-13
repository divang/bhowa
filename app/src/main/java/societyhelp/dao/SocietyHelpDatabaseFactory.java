package societyhelp.dao;

import societyhelp.dao.mysql.impl.SocietyHelpDatabaseAndroidSDKImpl;

public class SocietyHelpDatabaseFactory {

	private static ISocietyHelpDatabase instance;
	private static Object lock = new Object();

	private static String databaseDBURL;
	private static String databaseUser;
	private static String databasePassword;
	private static boolean isInitialized;

	public static void init(String strDatabaseDBURL, String strDatabaseUser, String strDatabasePassword)
	{
		databaseDBURL = strDatabaseDBURL;
		databaseUser = strDatabaseUser;
		databasePassword = strDatabasePassword;
		isInitialized = true;
	}

	public static ISocietyHelpDatabase getDBInstance()
	{
		if(!isInitialized) throw new ExceptionInInitializerError("Factory does not have DBURL, user and password");
		/*
		Async task should be executed once, beacuse of that always creating new Instance.
		if(instance != null) return instance;
		synchronized (lock)
		{
			if(instance != null) return instance;
			instance = new SocietyHelpDatabaseAndroidSDKImpl(databaseDBURL, databaseUser, databasePassword);
		}
		*/
		return new SocietyHelpDatabaseAndroidSDKImpl(databaseDBURL, databaseUser, databasePassword);
	}
}