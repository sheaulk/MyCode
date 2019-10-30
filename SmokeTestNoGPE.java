package testCases;
import resources.testCases.SmokeTestNoGPEHelper;
import tasks.Common;
import tasks.GPE;
import tasks.SAP;
import tasks.SCR;
import util.Global;
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
public class SmokeTestNoGPE extends SmokeTestNoGPEHelper
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
//	GPE gpe = new GPE();
//	GPE_Menu menu = new GPE_Menu();
//	GPE_BidSummary bidsummary = new GPE_BidSummary();
//	GPE_SystemParameterFiles systemfile = new GPE_SystemParameterFiles();
//	GPE_QuoteInformation quoteinfo = new GPE_QuoteInformation();
//	GPE_QuoteItemRollup itemrollup = new GPE_QuoteItemRollup();
	SCR scr = new SCR();
	SAP sap = new SAP();
	
	Common common = new Common();
	SCR_Home home = new SCR_Home();
		
	public void testMain(Object[] args) throws Exception 
	{
		try {
			common.getUserInfo();
			common.logRptSetup("SDM Smoke Test");
			common.getInitTimestamp();
			common.connectDB2();
//			gpe.logIn(Global.userId, Global.pwd);
			sleep(0.5);
			while(!dpDone()){
				Global.OldGPECase=dpString("Old Case");
				Global.NewGPECase=dpString("New Case");
				Global.CaseName=Global.NewGPECase.substring(Global.NewGPECase.lastIndexOf("\\")+1).replace(".sdg", "");
				Global.QuoteId=dpString("Quote ID");
				Global.QuoteDesc=dpString("Quote Description");
				
//				gpe.dismissUnexpectedWindow();
//				gpe.openSDMFile(Global.NewGPECase);
//				sleep(1);
//				while(!bidsummary.get_bidSummary().exists()) gpe.dismissUnexpectedWindow();
	//		try {
	//			if(systemfile.get_upgradeBidLink().isEnabled()) gpe.upgradeBid();
	//		} catch (ObjectNotFoundException e){
	//			// Do nothing.
	//		} catch (Exception e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//		gpe.saveAsSDMFile("C:\\Documents and Settings\\Administrator\\My Documents\\SametimeFileTransfers\\smoke test 19.10.1012 3.sdg");
	//		sleep(1);
	//		while(!bidsummary.get_bidSummary().exists()) gpe.dismissUnexpectedWindow();
//				gpe.unlockPriceCase();
//				menu.clickGPEquote();
//				gpe.clearQuoteScreen();
//				gpe.associateQuote(Global.QuoteId);
//				gpe.initiateSQA();
//				gpe.verifyItemIn1014(itemrollup.get_quoteItemRollupGrid(), 1, "Total Adjusted Price", "YP00");
//				unregisterAll();
//				scr.closeAllBrowsers();
//				sap.changeQuoteStatus();
//				unregisterAll();
				scr.closeAllBrowsers();
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
				unregisterAll();
				dpNext();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			logError(e.getMessage());
		}finally {
			common.disconnectDB2();
			unregisterAll();
		}
		
//		quoteinfo.get_lookupSAPQuote().click();
	}
}

