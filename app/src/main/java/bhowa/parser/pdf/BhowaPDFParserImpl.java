package bhowa.parser.pdf;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

import bhowa.dao.mysql.impl.BankStatement;
import bhowa.parser.IBhowaParser;

public class BhowaPDFParserImpl implements IBhowaParser{

	public static void main(String args[]) throws Exception {
		BhowaPDFParserImpl bp = new BhowaPDFParserImpl();
		//printUsedMemroy();
		BankStatement BankStatement = bp.getAllTransaction("res/Report.pdf");
		Runtime.getRuntime().gc();
		//for(BhowaTransaction t : BankStatement.allTransactions) System.out.println(t);
		//printUsedMemroy();
	}
 
	public BankStatement getAllTransaction(String pdfPath)
	{
	  InputStream is = null;
	    try 
	    {
	      is = new FileInputStream(pdfPath);
	      ContentHandler contenthandler = new BodyContentHandler();
	      Metadata metadata = new Metadata();
	      PDFParser pdfparser = new PDFParser();
	      pdfparser.parse(is, contenthandler, metadata, new ParseContext());
	      BankStatement bankStatement = new BankStatement();
	      bankStatement.bankStatementFileName = pdfPath;
	      bankStatement.allTransactions = MonthlyStatementDetails.getMonthlyDetails(allTransactions(contenthandler.toString())); 
	      return bankStatement;
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	    finally {
	        if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    }
	    return null;
	}
  
	public static void printUsedMemroy()
	{
	  long used = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
      System.out.println("Total memory(kb) : " + Runtime.getRuntime().totalMemory()/1024 + "kb");
      System.out.println("Free memory(kb) : " + Runtime.getRuntime().freeMemory()/1024 + "kb");
      System.out.println("Used memory(kb) : " + used/1024 + "kb");
  	}
  
  
  	private static List<String> allTransactions(String pdfTextForm)
  	{
	  String[] lines = pdfTextForm.split("\n"); //New line splitter
	  int transactionNumber=1;
	  StringBuilder onetransactionStr = new StringBuilder();
	  boolean isTransactionFound = false;
	  List<String> allTransaction = new ArrayList<String>();
	  for(String l :lines) {
		  
		  if(l.startsWith(transactionNumber + " "))
		  {
			  isTransactionFound = true;
			  onetransactionStr.append(l).append(" ");
		  }
		  else if(l.startsWith((transactionNumber+1) + " "))
		  {
			  transactionNumber++;
			  allTransaction.add(onetransactionStr.toString());
			  onetransactionStr.delete(0, onetransactionStr.length());
			  onetransactionStr.append(l).append(" ");
		  }
		  else if(l.contains("Opening balance") || l.contains("Closing balance")) // End of all transactions
		  {
			  allTransaction.add(onetransactionStr.toString());
			  break;
		  }
		  else if(isTransactionFound)
		  {
			  onetransactionStr.append(l).append(" ");
		  }
	  }
	  lines = null;
	  return allTransaction;
  	}
}