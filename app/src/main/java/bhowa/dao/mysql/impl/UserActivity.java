package bhowa.dao.mysql.impl;

public class UserActivity {

	public String userName;
	public String mobileNo;
	public ACTIVITY activity;
	public String comment;
	
	public enum ACTIVITY
	{
		LOGIN,
		UPDATE,
		VIEW, //ADD all pages views
		TRANSACTIONS_UPLOAD,
		UPLOAD_MONTHLY_STATEMENT
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("userName=").append(userName);
		str.append(" mobileNo=").append(mobileNo);
		str.append(" activity=").append(activity);
		str.append(" comment=").append(comment);
		return str.toString();
	}
	
	/* Database Schema
	 
	 CREATE TABLE `User_Activity_Logging` (
	`UserName` VARCHAR(50) NOT NULL,
	`Mobile` VARCHAR(50) NULL DEFAULT NULL,
	`Activity` VARCHAR(20) NOT NULL,
	`Comment` VARCHAR(100) NULL DEFAULT NULL,
	`Time` DATETIME NOT NULL
	)
	ENGINE=InnoDB
	ROW_FORMAT=DEFAULT
	 
	 */
}