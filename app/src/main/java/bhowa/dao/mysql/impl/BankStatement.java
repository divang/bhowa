package bhowa.dao.mysql.impl;

import java.io.Serializable;
import java.util.List;

public class BankStatement implements Serializable {

	public String bankStatementFileName;
	public List<BhowaTransaction> allTransactions;
	public List<String> rowdata;
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("bankStatementFileName").append(bankStatementFileName);
		return str.toString();
	}
	
	/* Database Schema
 	
 	CREATE TABLE `Bank_Statement` (
	`Statement_FileName` VARCHAR(100) NOT NULL,
	`Uploaded_Date` DATETIME NOT NULL,
	`Status` VARCHAR(10) NOT NULL
	)
	ENGINE=InnoDB
	ROW_FORMAT=DEFAULT
	 
	 */
}
