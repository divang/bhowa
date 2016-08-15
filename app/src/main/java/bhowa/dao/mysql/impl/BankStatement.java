package bhowa.dao.mysql.impl;

import java.util.List;

public class BankStatement {

	public String bankStatementFileName;
	public List<BhowaTransaction> allTransactions;
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("bankStatementFileName").append(bankStatementFileName);
		return str.toString();
	}
	
	/* Database Schema
 	
 	CREATE TABLE `Processed_Bank_Statement` (
		`Bank_Statement_FileName` VARCHAR(30) NOT NULL,
		`Uploaded_Date` DATETIME NOT NULL
	)
	ENGINE=InnoDB
	ROW_FORMAT=DEFAULT
	 
	 */
}
