package societyhelp.dao.mysql.impl;

import java.io.Serializable;
import java.util.Date;

public class SocietyHelpTransaction implements Serializable {

	public int transactionId;
	public int srNo;
	public String name;
	public String reference;
	public String type;
	public float amount;
	public Date transactionDate; 
	public String transactionFlow;
	public String userId;
	public String flatId;
	public boolean isAdminApproved;
	public String userComment;
	public String adminComment;

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("srNo=").append(srNo);
		str.append(" name=").append(name);
		str.append(" reference=").append(reference);
		str.append(" type=").append(type);
		str.append(" amount=").append(amount);
		str.append(" transactionDate=").append(transactionDate);
		str.append(" transactionFlow=").append(transactionFlow);
		return str.toString();
	}
	
	//Databse Schema
	/*
	CREATE TABLE `Transactions_Staging_Data` (
	`Transaction_ID` INT(10) NOT NULL AUTO_INCREMENT,
	`StatementID` INT(10) NOT NULL,
	`Name` VARCHAR(50) NOT NULL,
	`Amount` FLOAT NOT NULL,
	`Transaction_Date` DATETIME NOT NULL,
	`Transaction_Flow` VARCHAR(20) NOT NULL,
	`Transaction_Mode` VARCHAR(20) NULL DEFAULT NULL,
	`Transaction_Reference` VARCHAR(70) NULL DEFAULT NULL,
	`User_Id` VARCHAR(20) NULL DEFAULT NULL,
	`Flat_Id` VARCHAR(20) NULL DEFAULT NULL,
	`Admin_Approved` TINYINT(1) NOT NULL DEFAULT '0',
	`Admin_Comment` VARCHAR(100) NOT NULL DEFAULT '0',
	`User_Comment` VARCHAR(100) NOT NULL DEFAULT '0',
	PRIMARY KEY (`Transaction_ID`)
	)
	ENGINE=InnoDB
	ROW_FORMAT=DEFAULT
	AUTO_INCREMENT=94
	 */
}
