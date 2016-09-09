package bhowa.parser.pdf;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import bhowa.dao.mysql.impl.BhowaTransaction;

public class MonthlyStatementDetails {

	protected  DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");

	//Move these values to Database
	protected  String[] irrelevantWords = {"WITHDRAWAL", "BY", "BRANCH" ,"FROM" ,"Ref number Dr", "CrAmountDescription Balance Dr","CrDateSl. No.",
			"REF" ,"TRANSFER", "PROCEEDS", "FUND", "TO ", "Account Statement",":SI",
			"Chq", "Number", "AMOUNT","Description", "BALANCE", "@JKK", "@",
			"Date","/", "SL. NO." };

	//Various description
	protected  String subStrCASH = "CASH";
	protected  String subStrIB = "IB";
	protected  String subStrINT_PD = "PD:";
	protected  String subStrMATURITY = "MATURITY";
	protected  String subStrMB = "MB:";
	protected  String subStrNEFT = "NEFT";
	protected  String subStrSWEEP = "SWEEP";
	protected  String subStrCLG = "CLG";
	protected  String subStrTD = "TD PREMAT";
	protected  String subStrIMPS= "IMPS";

	public boolean isLoggingEnable = false;

	public  List<BhowaTransaction> getMonthlyDetails(List<String> strTransactionsList)
	{
		isLoggingEnable = false;
		List<BhowaTransaction> bhowaTransactions = new ArrayList<BhowaTransaction>();
		for(String curTransaction : strTransactionsList)
		{
			System.out.println(curTransaction);
			for(String irrelevantWord : irrelevantWords) curTransaction = curTransaction.toUpperCase().replaceAll(irrelevantWord.toUpperCase(), "");
			if(curTransaction.contains(subStrSWEEP)) continue; //Swip transaction ignore
			else if(curTransaction.contains(subStrMATURITY)) continue; //Swip transaction ignore 
			else if(curTransaction.contains(subStrNEFT)) bhowaTransactions.add(getNEFTTransaction(curTransaction));
			else if(curTransaction.contains(subStrCASH)) bhowaTransactions.add(getCashTransaction(curTransaction));
			else if(curTransaction.contains(subStrIB)) bhowaTransactions.add(getIBTransaction(curTransaction));
			else if(curTransaction.contains(subStrINT_PD)) continue; //bhowaTransactions.add(getIntPdTransaction(curTransaction));
			else if(curTransaction.contains(subStrMB)) bhowaTransactions.add(getMBTransaction(curTransaction));
			else if(curTransaction.contains(subStrCLG)) bhowaTransactions.add(getCLGTransaction(curTransaction));
			else if(curTransaction.contains(subStrTD)) continue; //bhowaTransactions.add(getTDTransaction(curTransaction));
			else if(curTransaction.contains(subStrIMPS)) bhowaTransactions.add(getIMPSTransaction(curTransaction));
			else bhowaTransactions.add(buildTransaction(curTransaction));
		}

		List<BhowaTransaction> trimmedList = trimBhowaTransaction(bhowaTransactions);
		//for(BhowaTransaction bt : trimmedList) System.out.println(bt);
		return trimmedList;
	}

	protected List<BhowaTransaction> trimBhowaTransaction(List<BhowaTransaction> bList)
	{
		List<BhowaTransaction> trimmedList = new ArrayList<BhowaTransaction>();
		for(BhowaTransaction bt : bList)
		{
			BhowaTransaction t = new BhowaTransaction();
			t.amount = bt.amount;
			if(bt.name != null) t.name = bt.name.trim().replaceAll("\\s+"," ").replaceAll(":","");
			System.out.println("bt.reference->"+bt.reference);
			if(bt.reference != null) t.reference = bt.reference.trim().replaceAll("\\s+"," ").replaceAll(":","");;
			System.out.println("t.reference->"+t.reference);
			t.srNo = bt.srNo;
			t.transactionDate = bt.transactionDate;
			if(bt.type != null) t.type = bt.type.trim().replaceAll("\\s+"," ").replaceAll(":","");;
			if(bt.transactionFlow != null) t.transactionFlow = bt.transactionFlow.trim().replaceAll("\\s+"," ").replaceAll(":","");;
			trimmedList.add(t);
		}

		return trimmedList;
	}

	protected  BhowaTransaction getIMPSTransaction(String strTransaction)
	{
		//4 IMPS FROM PHANI KRISH REF 613818443058 IMPS-613818595459 CR3,200.0017/05/2016 CR30,090.18 

		if(isLoggingEnable) System.out.println(strTransaction);
		strTransaction = strTransaction.replaceFirst(subStrIMPS, "");
		BhowaTransaction t = buildTransaction(strTransaction);
		t.type = subStrIMPS;
		if(isLoggingEnable) System.out.println(t);
		return t;
	}


	protected  BhowaTransaction getCLGTransaction(String strTransaction)
	{
		//24 TO CLG DANIEL L 236 DR13,260.0005/05/2016 CR6,611.18 
		//27 TO CLG UMESHA 285 DR25,480.0004/05/2016 CR12,791.18 

		if(isLoggingEnable) System.out.println(strTransaction);
		strTransaction = strTransaction.replaceFirst(subStrCLG, "");
		BhowaTransaction t = buildTransaction(strTransaction);
		t.type = subStrCLG;
		if(isLoggingEnable) System.out.println(t);
		return t;
	}

	protected  BhowaTransaction getTDTransaction(String strTransaction)
	{
		//53 TD PREMAT PROCEEDS: 5010906407 5010906407TO CR8.0022/04/2016 CR18,402.18 
		if(isLoggingEnable) System.out.println(strTransaction);
		strTransaction = strTransaction.replaceFirst(subStrTD, "");
		BhowaTransaction t = buildTransaction(strTransaction);
		t.type = subStrTD;
		if(isLoggingEnable) System.out.println(t);
		return t;
	}

	protected  BhowaTransaction getSWEEPTransaction(String strTransaction)
	{
		//SWEEP TRANSFER TO [5011214267] DR10,000.0018/05/2016 CR27,090.18
		BhowaTransaction t = new BhowaTransaction();
		return t;
	}

	protected  BhowaTransaction getNEFTTransaction(String strTransaction)
	{
		//2 NEFT 971020308 VIVEK SHARMA NEFTINW-0041525369 CR17,000.0018/05/2016 CR37,090.18 
		//14 NEFT AXMB161312110739 SUMITH VARGHESE NEFTINW-0041191977 CR10,000.0010/05/2016 CR32,634.18
		//26 NEFT CITIN16642810747MANOJ K NAIR NEFTINW-0040985527 CR3,710.0005/05/2016 CR16,501.18 
		//82 NEFT CITIN16632511367 NEFTINW-0039741498 CR3,370.0005042016 CR36,420.18      DR  CR  DR  CR  MAHESWAR MOHANTY 
		//19 NEFT CITIN16680570890 JAYAKRISHNAN A NEFTINW-0046498770 CR 3,710.00 29/08/2016 CR 27,443.18 Account StatementChq / Ref number Dr / Cr Amount Description Balance Dr / Cr Date Sl. No. 

		strTransaction = strTransaction.replaceFirst(subStrNEFT, "");
		BhowaTransaction t = buildTransaction(strTransaction);
		t.type = subStrNEFT;
		return t;
	}

	protected  BhowaTransaction getIBTransaction(String strTransaction)
	{
		//22 IB: FUND TRANSFER FROMGAUTAM  KUMAR IB CR4,000.0005/05/2016 CR14,211.18
		if(isLoggingEnable) System.out.println(strTransaction);
		strTransaction = strTransaction.replaceAll(subStrIB, "");
		BhowaTransaction t = buildTransaction(strTransaction);
		t.type = subStrIB;
		if(isLoggingEnable) System.out.println(t);
		return t;
	}

	protected  BhowaTransaction getIntPdTransaction(String strTransaction)
	{
		//102 Int.Pd:3011153048:01-10- 2015 to 31-03-2016 CR606.0031/03/2016 CR26,879.18
		if(isLoggingEnable) System.out.println(strTransaction);
		strTransaction = strTransaction.replaceFirst(subStrINT_PD, "");
		BhowaTransaction t = buildTransaction(strTransaction);
		t.type = subStrINT_PD;
		if(isLoggingEnable) System.out.println(t);
		return t;
	}

	protected  BhowaTransaction getMATURITYTransaction(String strTransaction)
	{
		//9 MATURITY PROCEEDS: 5019585711 TO CR10,308.0014/05/2016 CR39,790.18
		BhowaTransaction t = new BhowaTransaction();
		t.type = subStrMATURITY;
		return t;
	}

	protected  BhowaTransaction getMBTransaction(String strTransaction)
	{
		//13 MB:FUND TRANSFER FROM ABHINAV NIGAM MB-999976821552 CR6,540.0010/05/2016 CR39,174.18
		if(isLoggingEnable) System.out.println(strTransaction);
		strTransaction = strTransaction.replaceFirst(subStrMB, "");
		BhowaTransaction t = buildTransaction(strTransaction);
		t.type = subStrMB;
		if(isLoggingEnable) System.out.println(t);
		return t;
	}

	protected  BhowaTransaction getCashTransaction(String strTransaction)
	{
		//20 CASH WITHDRAWAL BY AKBAR HUSSAIN LASKAR@JKK BRANCH 237 DR16,000.0006/05/2016 CR8,517.18 
		//80 CASH WITHDRAWAL BY AKBAR HUSSAIN LASKAR 283 DR16,000.0005/04/2016 CR24,130.18 
		//153 CASH WITHDRAWAL BY SELF 277 DR16,000.0004/03/2016 DR-10,037.82 

		if(isLoggingEnable) System.out.println(strTransaction);
		strTransaction = strTransaction.replaceFirst(subStrCASH, "");
		BhowaTransaction t = buildTransaction(strTransaction);
		t.type = subStrCASH;
		if(isLoggingEnable) System.out.println(t);

		return t;
	}

	protected  BhowaTransaction buildTransaction(String strTransaction)
	{
		BhowaTransaction t = new BhowaTransaction();
		try
		{
			if(isLoggingEnable) System.out.println("-->"+strTransaction);
			setCreditDebit(strTransaction, t);
			if(isLoggingEnable) System.out.println(t);
			setAmountnDateStr(strTransaction, t);
			if(isLoggingEnable) System.out.println(t);
			setName(strTransaction, t);
			if(isLoggingEnable) System.out.println(t);
			setSrId(strTransaction, t);
			if(isLoggingEnable) System.out.println(t);
			setReference(strTransaction,t);
			if(isLoggingEnable) System.out.println(t);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return t;
	}

	protected  void setAmountnDateStr(String strTransaction, BhowaTransaction t)
	{
		/*
		 82 NEFT CITIN16632511367 NEFTINW-0039741498 CR3,370.0005042016 CR36,420.18      DR  CR  DR  CR  MAHESWAR MOHANTY 
		 88 NEFT N094160142122801MANAS KUMAR DASH NEFTINW-0039685499 CR4,000.0004042016 CR31,812.18  
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

		t.amount = Float.valueOf(strAmountnDate.substring(0, strAmountnDate.length()-8));
		try {
			String strDate = strAmountnDate.substring(strAmountnDate.length()-8,strAmountnDate.length());
			t.transactionDate = dateFormat.parse(strDate);
		} catch (ParseException e) {
			if(isLoggingEnable) System.out.println("i-"+strTransaction);
			if(isLoggingEnable) System.out.println();
			e.printStackTrace();
		}
	}

	protected  void setName(String strTransaction, BhowaTransaction t)
	{
		//==>50 NEFT CITIN16637946869 PHANI KRISHNA NARAYANAM NEFTINW-0040451164 CR3,200.0025042016 CR45,902.18      DR  CR  DR  CR  

		//==>2 NEFT 971020308 VIVEK SHARMA NEFTINW-0041525369 CR17,000.0018052016 CR37,090.18 

		//==>5  NEFT AXIR161383157617GOVULA MADHURI NEFTINW-0041444684 CR4,000.0017052016 CR26,890.18 
		//==>65 NEFT AXIR161026239612GOVULA MADHURI NEFTINW-0040012987 CR3,800.0012042016 CR34,376.18 
		//==>25 NEFT CITIN16642807794MAHESWAR MOHANTY NEFTINW-0040985780 CR3,370.0005052016 CR19,871.18 
		//==>30 NEFT CITIN16642410028GOPAKUMAR T NEFTINW-0040934411 CR3,960.0004052016 CR31,581.18 
		//==>75 NEFT CITIN16633214253MA K NAIR NEFTINW-0039806134 CR10,000.0006042016 CR47,570.18 
		//==>82 NEFT CITIN16632511367 NEFTINW-0039741498 CR3,370.0005042016 CR36,420.18      DR  CR  DR  CR  MAHESWAR MOHANTY 
		try
		{
			strTransaction = strTransaction.replace(" CR", "");
			strTransaction = strTransaction.replace(" DR", "");
			if(isLoggingEnable) System.out.println("name->"+strTransaction);

			boolean isNameMighitBe = false;
			int nameFirstCharIndex=-1, nameLastCharIndex=-1, nameFirstCharPreviousIndex = -1;
			int chIndex = 0;
			for(char ch : strTransaction.toUpperCase().toCharArray())
			{
				if(Character.isAlphabetic(ch)) {
					if(!isNameMighitBe)
					{
						nameFirstCharPreviousIndex = nameFirstCharIndex;
						nameFirstCharIndex = chIndex;
					}
					isNameMighitBe = true;
				}
				else if(Character.isDigit(ch))
				{
					isNameMighitBe = false;
				}
				else if(Character.isSpaceChar(ch))
				{
					if(isNameMighitBe)
					{
						nameLastCharIndex = chIndex;
					}
				}
				chIndex++;
			}
			if(isLoggingEnable) System.out.println("strTransaction-" + strTransaction + " nameFirstCharIndex-" + nameFirstCharIndex +" nameLastCharIndex-" + nameLastCharIndex);

			if(nameFirstCharIndex >= nameLastCharIndex) nameFirstCharIndex = nameFirstCharPreviousIndex;

			if(nameFirstCharIndex < nameLastCharIndex )
			{
				if(isLoggingEnable) System.out.println("Name=" + strTransaction.substring(nameFirstCharIndex,nameLastCharIndex));
				t.name = strTransaction.substring(nameFirstCharIndex,nameLastCharIndex);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	protected  void setSrId(String strTransaction, BhowaTransaction t)
	{
		//==>50 NEFT CITIN16637946869 PHANI KRISHNA NARAYANAM NEFTINW-0040451164 CR3,200.0025042016 CR45,902.18      DR  CR  DR  CR  
		//==>2 NEFT 971020308 VIVEK SHARMA NEFTINW-0041525369 CR17,000.0018052016 CR37,090.18 
		try
		{
			boolean isSrNoMighitBe = false;
			int srNoFirstCharIndex=-1, srNoLastCharIndex=-1;
			int chIndex = 0;
			for(char ch : strTransaction.toCharArray())
			{
				if(Character.isDigit(ch))
				{
					if(!isSrNoMighitBe) srNoFirstCharIndex = chIndex;
					isSrNoMighitBe = true;
				}
				else if(Character.isSpaceChar(ch))
				{
					srNoLastCharIndex = chIndex;
					break;
				}
				chIndex++;
			}
			if(srNoFirstCharIndex < srNoLastCharIndex )
			{
				if(isLoggingEnable) System.out.println("Name=" + strTransaction.substring(srNoFirstCharIndex,srNoLastCharIndex));
				t.srNo = Integer.parseInt(strTransaction.substring(srNoFirstCharIndex,srNoLastCharIndex));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	protected  void setReference(String strTransaction, BhowaTransaction t)
	{
		try
		{
			if(t.name !=null)
			{
				strTransaction = strTransaction.replaceAll(t.srNo+"", "");
				strTransaction = strTransaction.replaceAll(t.name, "");
				int endCRIndex = strTransaction.indexOf("CR");
				int endDRIndex = strTransaction.indexOf("DR");
				int endIndex = -1;
				if(endDRIndex == -1) endIndex = endCRIndex;
				else if(endCRIndex == -1) endIndex = endDRIndex;
				else if(endCRIndex > endDRIndex) endIndex = endDRIndex;
				else endIndex = endCRIndex;

				if(endIndex < 0) endIndex = strTransaction.indexOf("DR");
				if(isLoggingEnable) System.out.println("->"+strTransaction.substring(0, endIndex));
				t.reference = strTransaction.substring(0, endIndex);
			}
			else
			{
				System.err.println("Data corrupt->" + strTransaction);
				System.err.println("Data corrupt->" + t);
			}
		}catch(Exception e)
		{
			if(isLoggingEnable) System.out.println("-->" + strTransaction);
			if(isLoggingEnable) System.out.println("-i->" + t);
			e.printStackTrace();
		}
	}

	protected  void setCreditDebit(String strTransaction, BhowaTransaction t)
	{
		int indexCR = strTransaction.indexOf(" CR");
		int indexDR = strTransaction.indexOf(" DR");
		if(isLoggingEnable) System.out.println(strTransaction);

		if(indexDR == -1 &&  indexCR > 0 ) t.transactionFlow = "Credit";
		else if(indexCR == -1 &&  indexDR > 0 ) t.transactionFlow = "Debit";
		else if(indexCR > indexDR) t.transactionFlow = "Debit";
		else if(indexCR < indexDR) t.transactionFlow = "Credit";
	}

}
