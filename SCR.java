package tasks;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.ibm.rational.extensions.gui.utils.FindUtils;
import com.ibm.rational.extensions.gui.utils.PropertyFinder;
import com.rational.test.ft.object.interfaces.GuiTestObject;
import com.rational.test.ft.object.interfaces.IWindow;
import com.rational.test.ft.object.interfaces.TestObject;
import com.rational.test.ft.value.RegularExpression;

import resources.tasks.SCRHelper;
import util.DojoTableMgr;
import util.Global;
import util.ReportGenerator;
import util.Utility;
import appObjects.SCR_BillCode;
import appObjects.SCR_ConfigureSplitCriteria;
import appObjects.SCR_Home;
import appObjects.SCR_LineItems;
import appObjects.SCR_Login;
import appObjects.SCR_Message;
import appObjects.SCR_QuoteSearch;
import appObjects.SCR_SAPQuoteItems;
/**
 * Description   : Functional Test Script
 * @author sheaulk
 */
public class SCR extends SCRHelper
{
	/**
	 * Script Name   : <b>SCR</b>
	 * Generated     : <b>Sep 5, 2012 2:22:49 PM</b>
	 * Description   : Functional Test Script
	 * Original Host : WinNT Version 5.1  Build 2600 (S)
	 * 
	 * @since  2012/09/05
	 * @author sheaulk
	 */
	SCR_Login login = new SCR_Login();
	SCR_Home home = new SCR_Home();
	SCR_QuoteSearch quotesearch = new SCR_QuoteSearch();
	DojoTableMgr dojoTable = new DojoTableMgr();
	ReportGenerator rpt = new ReportGenerator();
	Common common = new Common();
	SCR_SAPQuoteItems sqi = new SCR_SAPQuoteItems();
		
	public void closeAllBrowsers()
	{
		try
		{
			IWindow[] wins = getTopWindows();
			for (int n = 0; n < wins.length; ++n)
			{
				String name=wins[n].getWindowClassName();
				if(name.equals("IEFrame")||name.startsWith("Mozilla"))
				{
					System.out.println(name);
					wins[n].close();
				}
			}
		}
		catch (Exception e)
		{
			Global.abortTest=true;
			throw new RuntimeException("Unable to close confirmation window");
		}
	}
	public void startSCR()
	{
		try {
			startApp("SCR UAT");
			home.get_browser_htmlBrowser().maximize();
		} catch (Exception e) {
			Global.abortTest=true;
			throw new RuntimeException("Failed to start SCR.");
		}
	}
	public void login(String id, String password)
	{
		login.get_userName().waitForExistence();
		login.get_userName().setText(id);
		login.get_password().setText(password);
		login.get_submit().click();
	}
	public void searchQuote(String quote)
	{
		home.waitForPageExistence();
		home.get_html_ibmTop().click(atPoint(7,439));
		home.get_browser_htmlBrowser().inputKeys("{ExtPgUp}{ExtPgUp}");
		sleep(5);
		home.get_link_quoteSearch().click();
		sleep(5);
		quotesearch.get_text_searchParamsQuoteID().setText(quote);
		quotesearch.get_button_searchSubmit().click();	
	}
	public void selectQuoteToProcess(String quote)
	{
		quotesearch.waitForExistence("quote");
		TestObject resultPane = FindUtils.find(new PropertyFinder(
				home.get_html_SCR(), "Html.DIV", //.class
				"id", "quoteSearchResultsPane", //property, value 
				false, false));
		DojoTableMgr dojoTable=new DojoTableMgr();
		Object[][] theTable=dojoTable.getDojoGridContent(resultPane,false);
		int rowNo=dojoTable.getRowNo(theTable, "Quote Description", Global.QuoteDesc);
		if(rowNo>-1){
			if(dojoTable.getCellValue(theTable, "Available Actions", rowNo).contains("Details")){
				GuiTestObject url= (GuiTestObject) FindUtils.find(new PropertyFinder(
						resultPane, "Html.A", //.class
						"title", "Click here to work on case", //property, value 
						false, false));
				url.click();
			}
		}else{
			logError("Quote is not found.");
			rpt.logMsg("Quote is not found.", Global.outputFile);
		}
	}
	public void selectCaseToProcess(String caseName)
	{
		quotesearch.waitForExistence("case");
		TestObject searchResults = (TestObject) FindUtils.find(new PropertyFinder(home.get_html_SCR(), "Html.DIV", //.class
				".id", "quoteSearchCasesPane", //property, value 
				false, false));
		DojoTableMgr dojoTable=new DojoTableMgr();
		Object[][]theTable=dojoTable.getDojoGridContent(searchResults,true);
		if(null!=theTable){
//			System.out.println(theTable.length);
//			System.out.println(theTable[0].length);
//			System.out.println(theTable[0][0].toString());
//			System.out.println(theTable[1][0].toString());
			System.out.println(dojoTable.getCellValue(theTable, "Case Name", 1));
			int rowNo=dojoTable.getRowNo(theTable, "Case Name", caseName);
			if(rowNo>-1){
				if(dojoTable.getCellValue(theTable, "Available Actions", rowNo).contains("Configure")){
					GuiTestObject url= (GuiTestObject) FindUtils.find(new PropertyFinder(
							searchResults, "Html.A", //.class
							"title", "Click here to configure", //property, value 
							false, false));
					url.click();
				}
			}else {
				System.out.println("row not found.");
				rpt.logMsg("Row not found in Select Case to Process.", Global.outputFile);
			}
		}
	}
	public void setupStandardSplitCriteriaDefaults()
	{
		SCR_ConfigureSplitCriteria configureSC = new SCR_ConfigureSplitCriteria();
		configureSC.waitForExistence();
		configureSC.buttonAssignValues().click();
//		button_assignValuesbutton().click();
		configureSC.item_toAssignValue("Billing Method").click();
//		sleep(5);
		configureSC.item_SplitCriteriaDefaultValue("5:Milestone - RR BLANK").click();
		configureSC.buttonMakeDefault().click();
		configureSC.item_toAssignValue("Service Activity").click();
//		sleep(5);
		configureSC.item_SplitCriteriaDefaultValue("69SA-001:Services").click();
		configureSC.buttonMakeDefault().click();
//		sleep(1);
		configureSC.button_Continue().click();
	}
	public void transitLineItemsPage()
	{
		SCR_LineItems lineItems = new SCR_LineItems();
		SCR_Home home = new SCR_Home();
		SCR_Message msg = new SCR_Message();
		
		lineItems.waitForExistence();

		String disabled=lineItems.button_CertifyThisCase().getParent().getProperty("aria-disabled").toString();
		if(!disabled.equalsIgnoreCase("true"))
		{
			lineItems.button_CertifyThisCase().click();
			sleep(1);
			msg.get_button_dilalogOK().click();
			sleep(0.5);
			msg.get_button_messagesOK().click();
		}
//		home.get_browser_htmlBrowser().inputKeys("{TAB}");
//		home.get_browser_htmlBrowser().inputKeys("{ENTER}");
//		sleep(0.5);
//		home.get_browser_htmlBrowser().inputKeys("{ENTER}");
//		sleep(0.5);
		
		lineItems.button_Continue().click();
	}
	public void transitSAPQuoteItems()
	{
		SCR_SAPQuoteItems sapItems = new SCR_SAPQuoteItems();
		SCR_Home home = new SCR_Home();
		
		TestObject resultPane = FindUtils.find(new PropertyFinder(
				home.get_html_SCR(), "Html.DIV", //.class
				"id", "SAPLineItemsGridDIV", //property, value 
				false, false));
		Object[][] theTable=dojoTable.getDojoGridContent(resultPane,true);
		if(null!=theTable){
			int rowNo=dojoTable.getRowNo(theTable, "Billing Method", "Time & Materials");
			System.out.println("row # is "+rowNo);
			if(rowNo>-1){
				Global.timeAndMaterials=true;
				List rowList = FindUtils.findAllObjects(new PropertyFinder(
						resultPane, "Html.TR", false, false));
				System.out.println(rowList.size()+" rows");
				RegularExpression re = new RegularExpression("sapQuotLineItems.onDetailsClick.*",false);
				GuiTestObject url= (GuiTestObject) FindUtils.find(new PropertyFinder(
						(TestObject) rowList.get(rowNo), "Html.INPUT.submit", //.class
						"onclick", re, //property, value 
						false, false));
				url.click();
			}
		}
		if(Global.timeAndMaterials){
			sapItems.button_setUpBillCodes().click();
			SCR_BillCode billCode = new SCR_BillCode();
			billCode.waitForExistence();
			billCode.text_widget_billCode().setText("b01");
			billCode.button_billCodeOK().click();
		}
		sapItems.button_sendQuoteToSAPForContra().click();
//		sleep(1);
		for (int i = 0; i < 10; i++) {
			sleep(1);
			try {
				dismissPopUp();
				break;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		dismissPopUp();
	}
	public void dismissPopUp()
	{
		SCR_Message msg = new SCR_Message();
		if(msg.get_button_messagesOK().hasFocus()){
			String msgTxt=msg.get_html_messagesContent().getProperty(".text").toString();
			System.out.println(msgTxt);
			if(msgTxt.toUpperCase().contains("ERROR")){
				Global.pass=false;
				rpt.logMsg(msgTxt, Global.outputFile);
			}
			msg.get_button_messagesOK().click();
		}
	}
	public void verify1015(String scrField, String xmlField)
	{
		
		String xmlTxt=null;
		Global.noXml=true;
		Global.sql1015=Global.sql1015.replace("nnnnnnnnnn", Global.currentQuote);
		try {
			String sqlts = common.getQuerySingleResult(Global.sql1015, 2);
			if(null!=sqlts){			
				if(common.newResult(sqlts)){
					xmlTxt = common.getQuerySingleResult(Global.sql1015, 1);
					if(xmlTxt.contains("soap")){
						Global.noXml=false;
						System.out.println(xmlTxt);
						float xmlVal=common.getSumOfXmlVal(xmlTxt, "YP00");
						System.out.println("val is "+xmlVal);
						System.out.println(sqi.get_CaseDetail(scrField));
						String scrTxt = sqi.get_CaseDetail(scrField);
						scrTxt = scrTxt.substring(0,scrTxt.indexOf(" "));
						scrTxt = scrTxt.replace(",", "");
						System.out.println(scrTxt);
						float scrVal=Float.parseFloat(scrTxt);
						System.out.println("tcv = "+scrVal);
						if(scrVal==xmlVal){
							rpt.logMsg("Value of "+xmlField+" "+xmlVal+" matches value of "+scrField+" in SCR.", Global.outputFile);
						}else{
							rpt.logMsg("Value of "+xmlField+" "+xmlVal+" does not matche value of "+scrField+" in SCR.", Global.outputFile);
						}
					}
				}
			}

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			logError(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			rpt.logMsg("SQL error - "+xmlTxt, Global.outputFile);
			logError(e.getMessage());
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logError(e.getMessage());
		} finally {
			if(Global.noXml){
				rpt.logMsg("No 1015 xml created.", Global.outputFile);
			}
		}
	}
}

