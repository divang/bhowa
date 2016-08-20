package bhowa.parser;

import bhowa.parser.pdf.BhowaPDFiTextParserImpl;

public class BhowaParserFactory {

	private static IBhowaParser instance;
	private static Object lock = new Object();
	
	public static IBhowaParser getDBInstance()
	{
		if(instance != null) return instance;
		
		synchronized (lock) {
			if(instance != null) return instance;
			instance = new BhowaPDFiTextParserImpl();// impl for iText jar. Use BhowaPDFParserImpl for pdfbox
		}
		return instance;
	}

}
