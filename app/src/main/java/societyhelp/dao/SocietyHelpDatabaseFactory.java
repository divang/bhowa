package societyhelp.dao;

import societyhelp.dao.mysql.impl.SocietyHelpDatabaseAndroidSDKImpl;

public class SocietyHelpDatabaseFactory {

	private static ISocietyHelpDatabase instance;
	private static Object lock = new Object();
	
	public static ISocietyHelpDatabase getDBInstance()
	{
		return new SocietyHelpDatabaseAndroidSDKImpl();
	}
}