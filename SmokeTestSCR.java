package testCases;
import resources.testCases.SmokeTestSCRHelper;
import tasks.Common;
import tasks.GPE;
import tasks.SAP;
import tasks.SCR;
import util.Global;
import util.ReportGenerator;
import appObjects.GPE_BidSummary;
import appObjects.GPE_Menu;
import appObjects.GPE_QuoteInformation;
import appObjects.GPE_QuoteItemRollup;
import appObjects.GPE_SystemParameterFiles;
import appObjects.SCR_Home;

/**
 * Description   : Functional Test Script
 * @author sheaulk
 */
public class SmokeTestSCR extends SmokeTestSCRHelper
{
	/**
	 * Script Name   : <b>SmokeTest</b>
	 * Generated     : <b>Aug 30, 2012 6:24:11 PM</b>
	 * Description   : Functional Test Script
	 * Original Host : WinNT Version 5.1  Build 2600 (S)
	 * 
	 * @since  2012/08/30
	 * @author sheaulk
	 */

	ReportGenerator rpt = new ReportGenerator();
	SCR scr = new SCR();
	Common common = new Common();
	SCR_Home home = new SCR_Home();
		
	public void testMain(Object[] args) throws Exception 
	{
		try {
//			common.getUserInfo();
			common.logRptSetup("SDM Smoke Test - SCR");
			Global.userId="sheaulk@ca.ibm.com";
			Global.pwd="like21oo";
			Global.db2Id="sheaulk";
			Global.db2Pwd="be6right";
			common.getInitTimestamp();
			common.connectDB2();
			sleep(0.5);
			while(!dpDone()){
				if(dpString("CaseUploaded").equalsIgnoreCase("Yes")){
					Global.OldGPECase=dpString("Old Case");
					Global.NewGPECase=dpString("New Case");
					Global.CaseName=Global.NewGPECase.substring(Global.NewGPECase.lastIndexOf("\\")+1).replace(".sdg", "");
					Global.QuoteId=dpString("Quote ID");
					Global.QuoteDesc=dpString("Quote Description");
	
	//				scr.closeAllBrowsers();
					scr.startSCR();
					scr.login(Global.userId, Global.pwd);
		//			home.waitForPageExistence();
		//			home.get_html_ibmTop().click(atPoint(7,439));
		//			home.get_browser_htmlBrowser().inputKeys("{ExtPgUp}{ExtPgUp}");
		//			sleep(1);
					scr.searchQuote(Global.QuoteId);
					scr.selectQuoteToProcess(Global.QuoteId);
					scr.selectCaseToProcess(Global.CaseName);
					scr.setupStandardSplitCriteriaDefaults();
					scr.transitLineItemsPage();
					scr.transitSAPQuoteItems();
					scr.verify1015(dpString("SCR Field"), dpString("Xml 1015 Field"));
					unregisterAll();
				}else{
					rpt.logMsg("Case is not loaded for SCR to process", Global.outputFile);
				}
				dpNext();
			}
		} catch (Exception e) {
			e.printStackTrace();
			String errorMsg = e.getMessage();
			logError(errorMsg);
			rpt.logMsg(errorMsg, Global.outputFile);
		}finally {
			common.disconnectDB2();
			unregisterAll();
		}
		
//		quoteinfo.get_lookupSAPQuote().click();
	}
}

