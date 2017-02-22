package societyhelp.dao.mysql.impl;

import java.io.Serializable;
import java.util.List;

public class BankStatement implements Serializable {

	public String bankStatementFileName;
	public List<SocietyHelpTransaction> allTransactions;
	public List<String> rowdata;
	public String uploadedLoginId;
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("bankStatementFileName").append(bankStatementFileName);
		return str.toString();
	}
}
