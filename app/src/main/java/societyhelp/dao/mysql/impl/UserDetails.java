package societyhelp.dao.mysql.impl;

import java.sql.Date;

public class UserDetails {

	public String userId;
	public String password;
	public String userType;
	public String userName;
	public String loginId;
	public boolean isActive;
	public String flatId;
	public String nameAlias;
	public String emailId;
	public String address;
	public long  mobileNo;
	public long  mobileNoAlternative;
	public Date flatJoinDate;
	public Date flatLeftDate;

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("userId=").append(userId);
		str.append(" userType=").append(userType);
		str.append(" userName").append(userName);
		str.append(" loginId=").append(loginId);
		str.append(" isActive=").append(isActive);
		str.append(" flatId=").append(flatId);
		str.append(" nameAlias=").append(nameAlias);
		str.append(" emailId=").append(emailId);
		str.append(" address=").append(address);
		str.append(" mobileNo=").append(mobileNo);
		str.append(" mobileNoAlternative=").append(mobileNoAlternative);
		str.append(" flatJoinDate=").append(flatJoinDate);
		str.append(" flatLeftDate=").append(flatLeftDate);
		return str.toString();
	}
	
	/* Database Schema

	CREATE TABLE IF NOT EXISTS `User_Details` (
	  `User_Id` varchar(20) NOT NULL,
	  `Login_Id` varchar(20) NOT NULL,
	  `User_Type_Id` varchar(20) NOT NULL,
	  `Status` tinyint(1) NOT NULL,
	  `Flat_Id` int(10) DEFAULT NULL,
	  `Name` varchar(50) NOT NULL,
	  `Name_Alias` varchar(400) DEFAULT NULL,
	  `Mobile_No` bigint(13) DEFAULT NULL,
	  `Moble_No_Alternate` bigint(13) DEFAULT NULL,
	  `Email_Id` varchar(30) DEFAULT NULL,
	  `Address` varchar(100) DEFAULT NULL,
	  `Flat_Join_Date` datetime DEFAULT NULL,
	  `Flat_Left_Date` datetime DEFAULT NULL,
	  PRIMARY KEY (`User_Id`)
	) ENGINE=InnoDB DEFAULT CHARSET=latin1;

	 */
}
