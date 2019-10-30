package testCases;
import resources.testCases.SmokeTestNoSCRHelper;
import tasks.Common;
import tasks.GPE;
import tasks.SAP;
import tasks.SCR;
import util.Global;
import util.Utility;
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
public class SmokeTestNoSCR extends SmokeTestNoSCRHelper
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
	GPE gpe = new GPE();
	GPE_Menu menu = new GPE_Menu();
	GPE_BidSummary bidsummary = new GPE_BidSummary();
	GPE_SystemParameterFiles systemfile = new GPE_SystemParameterFiles();
	GPE_QuoteInformation quoteinfo = new GPE_QuoteInformation();
	GPE_QuoteItemRollup itemrollup = new GPE_QuoteItemRollup();
	Common common = new Common();
	SAP sap = new SAP();
		
	public void testMain(Object[] args) throws Exception 
	{
		try {
//			common.getUserInfo();
			Global.userId="sheaulk@ca.ibm.com";
			Global.pwd="like21oo";
			Global.db2Id="sheaulk";
			Global.db2Pwd="be6right";
			common.logRptSetup("SDM Smoke Test");
			common.getInitTimestamp();
			common.connectDB2();
			gpe.logIn(Global.userId, Global.pwd);
			sleep(0.5);
			while(!dpDone()){
				Global.OldGPECase=dpString("Old Case");
				Global.NewGPECase=dpString("New Case");
				Global.CaseName=Global.NewGPECase.substring(Global.NewGPECase.lastIndexOf("\\")+1).replace(".sdg", "");
				Global.QuoteId=dpString("Quote ID");
				Global.QuoteDesc=dpString("Quote Description");
				setDatapool("CaseUploaded","No");
				
				gpe.dismissUnexpectedWindow();
				gpe.saveAsSDMFile();
				gpe.openSDMFile(Global.NewGPECase);
				sleep(1);
				while(!bidsummary.get_bidSummary().exists()) gpe.dismissUnexpectedWindow();
	//		try {
	//			if(systemfile.get_upgradeBidLink().isEnabled()) gpe.upgradeBid();
	//		} catch (ObjectNotFoundException e){
	//			// Do nothing.
	//		} catch (Exception e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
				gpe.saveAsSDMFile();
				sleep(1);
				while(!bidsummary.get_bidSummary().exists()) gpe.dismissUnexpectedWindow();
				gpe.unlockPriceCase();
				menu.clickGPEquote();
				gpe.clearQuoteScreen();
				gpe.associateQuote(Global.QuoteId);
				gpe.initiateSQA();
				gpe.verify1014(itemrollup.get_quoteItemRollupGrid(), 1, dpString("GPE Field"), dpString("Xml 1014 Field"));
				if(!Global.noXml){
					setDatapool("CaseUploaded","Yes");
					unregisterAll();
					Utility.closeAllBrowsers();
					sap.changeQuoteStatus();
				}
				unregisterAll();
				dpNext();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			logError(e.getMessage());
		}finally {
			storeDatapool();
			common.disconnectDB2();
			unregisterAll();
		}
		
//		quoteinfo.get_lookupSAPQuote().click();
	}
}

