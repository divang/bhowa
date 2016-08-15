package bhowa.test;

import bhowa.dao.mysql.impl.BankStatement;
import bhowa.dao.mysql.impl.DatabaseCoreAPIs;
import bhowa.parser.pdf.BhowaPDFParserImpl;

public class BhowaTestAPIs {

	DatabaseCoreAPIs dbCore = new DatabaseCoreAPIs();
	
	public static void main(String[] args) {
		BhowaTestAPIs testUtil = new BhowaTestAPIs();
		//testUtil.loginTest();
		//testUtil.uploadMonthlyTransactionsDBTest();
		testUtil.isStatementAlreadyProcessedDBTest();
	}
	
	public void loginTest()
	{
		try
		{
			boolean r = dbCore.loginDB("divang", "divang1");
			if(r) System.out.println("loging test OK");
			else System.err.println("loging test Failed");
		}
		catch(Exception e)
		{
			System.err.println("loging test Failed");
			e.printStackTrace();
		}
	}
	
	public void uploadMonthlyTransactionsDBTest()
	{
		try
		{
			BhowaPDFParserImpl bp = new BhowaPDFParserImpl();
			BankStatement bStat = bp.getAllTransaction("res/Report.pdf");
			boolean r = dbCore.uploadMonthlyTransactionsDB(bStat);
			if(r) System.out.println("All transactions uploaded test OK");
			else System.err.println("Transactions upload test Failed");
		}
		catch(Exception e)
		{
			System.err.println("transactions uploaded test Failed");
			e.printStackTrace();
		}
	}
	
	public void isStatementAlreadyProcessedDBTest()
	{
		try
		{
			boolean r = dbCore.isStatementAlreadyProcessedDB("res/Report.pdf");
			if(r) System.out.println("Bank statement processed test OK");
			else  System.out.println("Bank statement should be processed test Failed");
			
			r = dbCore.isStatementAlreadyProcessedDB("res/Report1.pdf");
			if(!r) System.out.println("Bank statement should not be processed test OK");
			else  System.out.println("Bank statement should not be processed test Failed");
			
		}
		catch(Exception e)
		{
			System.err.println("Bank statement processed test Failed");
			e.printStackTrace();
		}
	}
}
