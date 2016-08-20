package bhowa.dao.mysql.impl;

import java.io.Serializable;
import java.util.Date;

public class BhowaTransaction implements Serializable {

	public int srNo;
	public String name;
	public String reference;
	public String type;
	public float amount;
	public Date transactionDate; 
	public String transactionFlow;
	
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
	
	 CREATE TABLE `Transactions` (
	`ID` INT(10) NOT NULL AUTO_INCREMENT,
	`StatementID` INT(10) NOT NULL,
	`Name` VARCHAR(50) NOT NULL,
	`Amount` FLOAT NOT NULL,
	`Transaction_Date` DATETIME NOT NULL,
	`Transaction_Flow` VARCHAR(20) NOT NULL,
	`Transaction_Mode` VARCHAR(20) NOT NULL,
	`Transaction_Reference` VARCHAR(70) NULL DEFAULT NULL,
	PRIMARY KEY (`ID`)
	)
	ENGINE=InnoDB
	ROW_FORMAT=DEFAULT

	 */
}
