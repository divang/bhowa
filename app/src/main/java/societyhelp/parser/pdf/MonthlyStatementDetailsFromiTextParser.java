package societyhelp.parser.pdf;

import java.util.Date;
import societyhelp.dao.mysql.impl.SocietyHelpTransaction;

public class MonthlyStatementDetailsFromiTextParser extends MonthlyStatementDetails {
	
	protected  void setAmountnDateStr(String strTransaction, SocietyHelpTransaction t)
	{
		/*
		 129 SWEEP TRANSFER TO [5010587865] DR 20,000.00 11/03/2016 CR 27,520.18 
		 130 IMPS FROM MANOJ K NAI REF 607111471520 IMPS-607111640033 CR 8,000.00 11/03/2016 CR 47,520.18 
		 145   DANIEL L 278 DR 11,900.00 05032016 CR 18,097.18    CR    DR  CR  SL. NO.  strAmountnDate-  18097.18   
		 */
		int firstCRIndex = strTransaction.indexOf(" CR");
		int secondCRIndex = strTransaction.indexOf(" CR", firstCRIndex+1);
		if(isLoggingEnable) System.out.println("firstCRIndex-" + firstCRIndex + "secondCRIndex-"+secondCRIndex);
		//152 NEFT AXIR160648954149 IVIN SEBASTIAN NEFTINW-0038465061 CR6,110.0004032016 DR-3,927.82 
		
		if(firstCRIndex < 0)
		{
			firstCRIndex = strTransaction.indexOf(" DR");
			secondCRIndex = strTransaction.indexOf(" CR", firstCRIndex+1);
			if(isLoggingEnable) System.out.println("0 firstCRIndex-" + firstCRIndex + "secondCRIndex-"+secondCRIndex);
		}
		
		if(secondCRIndex < 0)
		{
			secondCRIndex = strTransaction.indexOf(" DR", firstCRIndex+1);
			if(isLoggingEnable) System.out.println("1 firstCRIndex-" + firstCRIndex + "secondCRIndex-"+secondCRIndex);
		}
		
		if(secondCRIndex < 0) 
		{
			firstCRIndex = strTransaction.indexOf(" DR");
			secondCRIndex = strTransaction.indexOf(" DR", firstCRIndex+1);
			if(secondCRIndex < 0) secondCRIndex = strTransaction.indexOf(" CR", firstCRIndex+1);
			if(isLoggingEnable) System.out.println("2 firstCRIndex-" + firstCRIndex + "secondCRIndex-"+secondCRIndex);
		}
		
		String strAmountnDate = strTransaction.substring(firstCRIndex, secondCRIndex).replaceAll("CR", "").replaceAll(",", "");
		strAmountnDate = strAmountnDate.replace("DR", "");
		String[] arrAmtDate = strAmountnDate.trim().split(" ");
		if(arrAmtDate.length > 0 ) t.amount = Float.valueOf(arrAmtDate[0]);
		try {
			if(arrAmtDate.length > 1 )
			{
				String strDate = arrAmtDate[1];
				t.transactionDate = new java.sql.Date(dateFormat.parse(strDate).getTime());
			}
			else
			{
				if(isLoggingEnable) System.out.println("i-"+strTransaction + " strAmountnDate-" + strAmountnDate);
				t.transactionDate = new java.sql.Date(getDateFromLine(strTransaction).getTime());
			}
		} catch (Exception e) {
			if(isLoggingEnable) System.out.println("i-"+strTransaction);
			if(isLoggingEnable) System.out.println();
			e.printStackTrace();
		}
	}
	
	protected  Date getDateFromLine(String strTransaction)
	{
		//i-145   DANIEL L 278 DR 11,900.00 05032016 CR 18,097.18    CR    DR  CR  SL. NO.  strAmountnDate-  18097.18   
		int dateIntCount=0, startDateIndex = 0, currentIndex=0;
		
		for(char ch : strTransaction.toCharArray())
		{
			if(Character.isDigit(ch))
			{
				if(dateIntCount==0) startDateIndex = currentIndex;
				dateIntCount++;
				if(dateIntCount == 6 && Character.isSpaceChar(strTransaction.charAt(currentIndex+1)))
				{
					try{
						if(isLoggingEnable) System.out.println("date-" + strTransaction.substring(startDateIndex, currentIndex+1));
						return dateFormat.parse(strTransaction.substring(startDateIndex, currentIndex+1));
					}
					catch(Exception e)
					{
						dateIntCount=0;
					}
				}
				else if(dateIntCount > 6) dateIntCount = 0;
			}
			else
			{
				dateIntCount = 0;
			}
			
			currentIndex++;
		}
		
		return null;
	}
}
