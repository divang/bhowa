package bhowa.dao;

import bhowa.dao.mysql.impl.BhowaDatabaseAndroidSDKImpl;

public class BhowaDatabaseFactory {

	private static IBhowaDatabase instance;
	private static Object lock = new Object();
	
	public static IBhowaDatabase getDBInstance()
	{
		return new BhowaDatabaseAndroidSDKImpl();
	}
}