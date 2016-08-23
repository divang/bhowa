package bhowa.dao.mysql.impl;

public class UserDetails {

	public String userId;
	public String userName;
	public String password;
	public boolean isActive;
	public int flatNo;
	public String nameAlias;
	public String emailId;
	public String address;
	public String block;
	public float maintenanceMonthly;
	public long  mobileNo;

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("userId=").append(userId);
		str.append(" userName").append(userName);
		str.append(" password=").append(password);
		str.append(" isActive=").append(isActive);
		str.append(" flatNo=").append(flatNo);
		str.append(" nameAlias=").append(nameAlias);
		str.append(" emailId=").append(emailId);
		str.append(" address=").append(address);
		str.append(" block=").append(block);
		str.append(" maintenanceMonthly=").append(maintenanceMonthly);
		str.append(" mobileNo=").append(mobileNo);
		return str.toString();
	}
	
	/* Database Schema

	CREATE TABLE `User` (
		`User_Id` VARCHAR(20) NOT NULL,
		`Password` VARCHAR(20) NOT NULL,
		`Status` TINYINT(1) NOT NULL,
		`Flat_No` INT(3) NULL DEFAULT NULL,
		`Name` VARCHAR(50) NOT NULL,
		`Name_Alias` VARCHAR(400) NULL DEFAULT NULL,
		`Mobile_No` BIGINT(13) NULL DEFAULT NULL,
		`Email_Id` VARCHAR(30) NULL DEFAULT NULL,
		`Maintenance_Monthly` FLOAT NULL DEFAULT NULL,
		`Address` VARCHAR(100) NULL DEFAULT NULL,
		`Block` VARCHAR(5) NULL DEFAULT NULL,
		PRIMARY KEY (`User_Id`)
	)
	ENGINE=InnoDB
	ROW_FORMAT=DEFAULT

	 */
}
