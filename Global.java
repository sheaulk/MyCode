package util;

import java.sql.Timestamp;

import com.rational.test.ft.object.interfaces.ProcessTestObject;

public class Global {

	public static int preQueryCount = 0;
	public static boolean dbConnected = false;
	public static Timestamp ts;
	public static String currentQuote="";
	public static String sql1014 = "SELECT QUEUE_XML, UPDATE_TS FROM SQA.QUOTE_QUEUE_1014_V WHERE UPDATE_TS =(SELECT MAX(QUOTE_QUEUE_1014_V.UPDATE_TS)FROM SQA.QUOTE_QUEUE_1014_V AS QUOTE_QUEUE_1014_V WHERE QUOTE_QUEUE_1014_V.QUOTE_ID LIKE 'nnnnnnnnnn' AND QUOTE_QUEUE_1014_V.ENTRY_TYPE_CD LIKE '%Request%')";
	public static String sql1015 = "SELECT QUEUE_XML, UPDATE_TS FROM SCR.QUOTE_QUEUE_1015_V WHERE UPDATE_TS =(SELECT MAX(QUOTE_QUEUE_1015_V.UPDATE_TS)FROM SCR.QUOTE_QUEUE_1015_V AS QUOTE_QUEUE_1015_V WHERE QUOTE_QUEUE_1015_V.QUOTE_ID LIKE 'nnnnnnnnnn' AND QUOTE_QUEUE_1015_V.ENTRY_TYPE_CD LIKE '%Request%')";
	public static String outputFile = "";
	public static boolean timeAndMaterials=false;
	public static boolean abortTest=false;
	public static String userId=null;
	public static String pwd=null;
	public static final int maxTry=60;
	public static ProcessTestObject sapProcess=null;
	public static ProcessTestObject scrProcess=null;
	public static String OldGPECase="";
	public static String NewGPECase="";
	public static String QuoteId="";
	public static String QuoteDesc="";
	public static String CaseName="";
	public static boolean pass=true;
	public static boolean noXml=true;
	public static String db2Id="";
	public static String db2Pwd="";
}