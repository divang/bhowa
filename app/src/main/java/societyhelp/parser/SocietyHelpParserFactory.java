package societyhelp.parser;

import societyhelp.parser.pdf.SocietyHelpPDFiTextParserImpl;

public class SocietyHelpParserFactory {

	private static ISocietyHelpParser instance;
	private static Object lock = new Object();
	
	public static ISocietyHelpParser getInstance()
	{
		if(instance != null) return instance;
		
		synchronized (lock) {
			if(instance != null) return instance;
			instance = new SocietyHelpPDFiTextParserImpl();// impl for iText jar. Use BhowaPDFParserImpl for pdfbox
		}
		return instance;
	}

}
