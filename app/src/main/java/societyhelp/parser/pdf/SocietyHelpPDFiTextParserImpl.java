package societyhelp.parser.pdf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import societyhelp.dao.mysql.impl.BankStatement;
import societyhelp.parser.ISocietyHelpParser;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class SocietyHelpPDFiTextParserImpl implements ISocietyHelpParser {

	public static void main(String args[]) throws Exception {
		//SocietyHelpPDFiTextParserImpl bp = new SocietyHelpPDFiTextParserImpl();
		//BankStatement BankStatement = bp.getAllTransaction("res/Report.pdf");
		Runtime.getRuntime().gc();
	}
 
	public BankStatement getAllTransaction(String pdfPath)
	{
		BankStatement bankStatement = new BankStatement();
	    bankStatement.bankStatementFileName = pdfPath;
	      
		StringBuilder pdfString = new StringBuilder();
		try
		{
			PdfReader reader = null;
			try {
		        reader = new PdfReader(pdfPath);
		        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
		        TextExtractionStrategy strategy;
		        for(int i=0;i<reader.getNumberOfPages();i++) {
		            try {
		                strategy = parser.processContent((i+1), new SimpleTextExtractionStrategy());
		                pdfString.append(strategy.getResultantText());
		            } catch(Exception e) {
		                e.printStackTrace();
		            }
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		    } finally {
		        if (reader != null) reader.close();
		    }
		}
		catch (Exception e)
		{
				e.printStackTrace();
		}
		
		MonthlyStatementDetailsFromiTextParser mt = new MonthlyStatementDetailsFromiTextParser();
		mt.isLoggingEnable = true;
		bankStatement.rowdata = allTransactions(pdfString.toString());
		bankStatement.allTransactions = mt.getMonthlyDetails(bankStatement.rowdata);
		return bankStatement;
	}
  
  	private static List<String> allTransactions(String pdfTextForm)
  	{
	  String[] lines = pdfTextForm.split("\n"); //New line splitter
	  int transactionNumber=1;
	  StringBuilder onetransactionStr = new StringBuilder();
	  boolean isTransactionFound = false;
	  List<String> allTransaction = new ArrayList<>();
	  for(String l :lines) {
		  // System.out.println("current line :" + l);
		  if (l.trim().equals(transactionNumber + "")) {
			  isTransactionFound = true;
			  onetransactionStr.append(l).append(" ");
		  } else if (l.trim().equals((transactionNumber + 1 + ""))) {
			  transactionNumber++;
			  allTransaction.add(onetransactionStr.toString());
			  onetransactionStr.delete(0, onetransactionStr.length());
			  onetransactionStr.append(l).append(" ");
		  } else if (l.contains("Opening balance") || l.contains("Closing balance")) // End of all transactions
		  {
			  allTransaction.add(onetransactionStr.toString());
			  break;
		  } else if (isTransactionFound) {
			  onetransactionStr.append(l).append(" ");
		  }
	  }

	  return allTransaction;
  	}
}