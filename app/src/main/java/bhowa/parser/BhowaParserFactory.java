package bhowa.parser;

import bhowa.parser.pdf.BhowaPDFParserImpl;

public class BhowaParserFactory {

	private static IBhowaParser instance;
	private static Object lock = new Object();
	
	public static IBhowaParser getDBInstance()
	{
		if(instance != null) return instance;
		
		synchronized (lock) {
			if(instance != null) return instance;
			instance = new BhowaPDFParserImpl();
		}
		return instance;
	}

}
