package bhowa.dao.mysql.impl;


public class UserLogin {

	public String userName;
	public String password;
	public boolean isActive;
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("userName=").append(userName);
		str.append(" password").append(password);
		str.append(" isActive=").append(isActive);
		return super.toString();
	}
	
	/* Database Schema

	 CREATE TABLE `User_Login` (
	`UserID` VARCHAR(20) NOT NULL,
	`Password` VARCHAR(20) NOT NULL,
	`Status` BIT(1) NOT NULL,
	PRIMARY KEY (`UserID`)
	)
	ENGINE=InnoDB
	ROW_FORMAT=DEFAULT
	  
	 */
}
