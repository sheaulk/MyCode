package tasks;
import resources.tasks.SAPHelper;
import util.Global;
import util.Utility;
import appObjects.SAP_Home;
import appObjects.SAP_Logon;
import appObjects.SAP_Search;
import appObjects.SAP_SearchQuotations;
import appObjects.SAP_SelectBusinessRole;
import appObjects.SAP_StandardQuotation;

import com.rational.test.ft.*;
import com.rational.test.ft.object.interfaces.*;
import com.rational.test.ft.object.interfaces.SAP.*;
import com.rational.test.ft.object.interfaces.WPF.*;
import com.rational.test.ft.object.interfaces.dojo.*;
import com.rational.test.ft.object.interfaces.siebel.*;
import com.rational.test.ft.object.interfaces.flex.*;
import com.rational.test.ft.object.interfaces.generichtmlsubdomain.*;
import com.rational.test.ft.script.*;
import com.rational.test.ft.value.*;
import com.rational.test.ft.vp.*;
import com.ibm.rational.test.ft.object.interfaces.sapwebportal.*;
/**
 * Description   : Functional Test Script
 * @author sheaulk
 */
public class SAP extends SAPHelper
{
	/**
	 * Script Name   : <b>SAP</b>
	 * Generated     : <b>Oct 22, 2012 2:08:42 PM</b>
	 * Description   : Functional Test Script
	 * Original Host : WinNT Version 5.1  Build 2600 (S)
	 * 
	 * @since  2012/10/22
	 * @author sheaulk
	 */
	public void changeQuoteStatus()
	{
//		SAP_StandardQuotation sq = new SAP_StandardQuotation();
//		StatelessGuiSubitemTestObject parent = sq.get_table_items();
//		// Get the mappable childrent of the parent table in an array.
////		TestObject[] objList =parent.getMappableChildren();
//		
//		// Get the data table of the parent table.
//		ITestDataTable contents=(ITestDataTable) parent.getTestData("grid");
//		
//		boolean foundItem = false;
//		int noOfRow = contents.getRowCount();
//		int noOfCol = contents.getColumnCount();
//		System.out.println("has "+noOfRow+" rows");
//		int row=0;
//		int col=0;
//		
//		// Search the data table cell by cell to look for the name of
//		// the child object.
//			String txt;
//			Object cellObj;
//			for (col = 0; col < noOfCol; col++) {
//				cellObj=contents.getCell(row,col);
//				if(null==cellObj)continue;
//				txt = cellObj.toString();
//				if (txt.equalsIgnoreCase("Status")){
//					foundItem=true;
//					break;
//				}
//			}
//		GuiTestObject theCell;
//		TestObject[] selection;
//		GuiTestObject obj;
//		GuiTestObject statusList;
//		for (row=1; row < noOfRow; row++) {
//			cellObj=contents.getCell(row, col);
//			if(null==cellObj)break;
//			if(cellObj.toString().length()==0)break;
//			if(cellObj.toString().equalsIgnoreCase("Quote Price OK"))continue;
//			System.out.println(cellObj.toString());
//			theCell=(GuiTestObject) parent.getSubitem(atCell(atRow(row),atColumn(col)));
//			theCell.click();
//			statusList=sq.get_html_statusList(row);
//			selection=(TestObject[]) statusList.find(atDescendant(".class","Html.A",".text","Quote Price OK"));
//			if(selection.length==0){
//				selection=(TestObject[]) statusList.find(atDescendant(".class","Html.A",".text","Special Review Required"));
//				obj=(GuiTestObject) selection[0];
//				obj.click();
//				sleep(1);
//				theCell.click();
//				statusList=sq.get_html_statusList(row);
//				selection=(TestObject[]) statusList.find(atDescendant(".class","Html.A",".text","Quote Price OK"));
//			}
//			obj=(GuiTestObject) selection[0];
//			obj.click();
//			sleep(0.5);
//		}
		SAP_SelectBusinessRole sbr = new SAP_SelectBusinessRole();
		SAP_Logon logon = new SAP_Logon();
		SAP_SearchQuotations searchQuotations = new SAP_SearchQuotations();
		SAP_Home home = new SAP_Home();
		SAP_Search search = new SAP_Search();
		SAP_StandardQuotation standardQuote = new SAP_StandardQuotation();
		RegularExpression re1;
		RegularExpression re2;
		try {
			startApp("SAP QA3");
			sleep(5);
			logon.get_table_logonInfo().waitForExistence();
			logon.get_text_sapUserId().setText("084949649");
			logon.get_text_sapPassword().setText("be6right");
			logon.get_button_logOn().click();
			Utility.waitForExistence(home.get_document_sapHome(), "Html.A", ".id", "ZOTOIC-S1");
			sbr.get_link_businessRole().click();
			re1=new RegularExpression("C[0-9]+_W[0-9]+_V[0-9]+_ZSLS_ALL",false);
			Utility.waitForExistence(home.get_document_sapHome(), "Html.A",".id", re1);
			home.get_link_salesFunctions().click();
			sleep(10);
			re1=new RegularExpression("C[0-9]+_W[0-9]+_V[0-9]+_ZSRV-QT-SR",false);
			re2=new RegularExpression("Quotations",false);
			Utility.waitForExistence(home.get_document_sapHome(), "Html.A",".id", re1, ".text", re2 );
			search.get_link_searchQuotations().click();
			searchQuotations.get_link_search().waitForExistence();
			home.get_browser_htmlBrowser().inputChars(Global.QuoteId);
			searchQuotations.get_table_quoteResultTable().waitForExistence();
			searchQuotations.get_link_search().click();
			StatelessGuiSubitemTestObject parent=searchQuotations.get_table_quoteResultTable();
			// Get the mappable childrent of the parent table in an array.
			TestObject[] objList =parent.getMappableChildren();
			
			// Get the data table of the parent table.
			ITestDataTable contents=(ITestDataTable) parent.getTestData("contents");
			
			boolean foundItem = false;
			int noOfRow = contents.getRowCount();
			int noOfCol = contents.getColumnCount();
			int row=0;
			int col=0;
			
			// Search the data table cell by cell to look for the name of
			// the child object.
			search:
			for (row = 0; row < noOfRow; row++) {
				for (col = 0; col < noOfCol; col++) {
					Object cellObj=contents.getCell(row,col);
					if(null==cellObj)continue;
					String txt = cellObj.toString();
					if (txt.contains(Global.QuoteId)){
						foundItem=true;
						break search;
					}
				}
			}
			System.out.println("row is "+row+"col is "+col);
			TestObject obj=(TestObject) parent.getSubitem(atCell(atRow(row+1),atColumn(col)));
			TestObject[]chd=obj.getChildren();
			System.out.println(chd.length);
			System.out.println(chd[0].getProperties().toString());
			GuiTestObject c=(GuiTestObject)chd[0];
			c.click();
			sleep(10);
			re1=new RegularExpression("C[0-9]+_W[0-9]+_V[0-9]+_V[0-9]+_but1",false);
			re2=new RegularExpression("^th-bt th-bt-icontext$",false);
			Utility.waitForExistence(standardQuote.get_table_quotationDetails(), "Html.A", ".id", re1, "class", re2);
			standardQuote.get_link_quoteEdit().click();
			re1=new RegularExpression("C[0-9]+_W[0-9]+_V[0-9]+_V[0-9]+_btadminh_lcstatus-btn",false);
			Utility.waitForExistence(standardQuote.get_document_sapStandardQuotation(), "Html.A", ".id", re1);
			standardQuote.get_link_quoteStatusButton().click();
			re1=new RegularExpression("C[0-9]+_W[0-9]+_V[0-9]+_V[0-9]+_btadminh_lcstatus__items",false);
			Utility.waitForExistence(standardQuote.get_document_sapStandardQuotation(), "Html.DIV", ".id", re1);
			standardQuote.get_link_quoteStatusItem("CF In Process").click();
			sleep(2);
			standardQuote.get_link_save().click();
//			RootTestObject root = getRootTestObject();
//			re1=new RegularExpression("C[0-9]+_W[0-9]+_V[0-9]+_V[0-9]+_but1",false);
//			re2=new RegularExpression("^th-bt th-bt-icontext$",false);
//			Utility.waitForExistence("Html.A", ".id", re1, "class", re2);	
			sleep(30);
		} catch (Exception e) {
			System.out.println("Error is "+e.getMessage());
			logError(e.getMessage());
		}
	}
	public void changeStatusForAllItems()
	{
		SAP_StandardQuotation sq = new SAP_StandardQuotation();
		StatelessGuiSubitemTestObject parent = sq.get_table_items();
		// Get the mappable childrent of the parent table in an array.
//		TestObject[] objList =parent.getMappableChildren();
		
		// Get the data table of the parent table.
		ITestDataTable contents=(ITestDataTable) parent.getTestData("grid");
		
		boolean foundItem = false;
		int noOfRow = contents.getRowCount();
		int noOfCol = contents.getColumnCount();
		System.out.println("has "+noOfRow+" rows");
		int row=0;
		int col=0;
		
		// Search the data table cell by cell to look for the name of
		// the child object.
			String txt;
			Object cellObj;
			for (col = 0; col < noOfCol; col++) {
				cellObj=contents.getCell(row,col);
				if(null==cellObj)continue;
				txt = cellObj.toString();
				if (txt.equalsIgnoreCase("Status")){
					foundItem=true;
					break;
				}
			}
		GuiTestObject theCell;
		TestObject[] selection;
		GuiTestObject obj;
		GuiTestObject statusList;
		for (row=1; row < noOfRow; row++) {
			cellObj=contents.getCell(row, col);
			if(null==cellObj)break;
			if(cellObj.toString().length()==0)break;
			if(cellObj.toString().equalsIgnoreCase("Quote Price OK"))continue;
			System.out.println(cellObj.toString());
			theCell=(GuiTestObject) parent.getSubitem(atCell(atRow(row),atColumn(col)));
			theCell.click();
			statusList=sq.get_html_statusList(row);
			selection=(TestObject[]) statusList.find(atDescendant(".class","Html.A",".text","Quote Price OK"));
			if(selection.length==0){
				selection=(TestObject[]) statusList.find(atDescendant(".class","Html.A",".text","Special Review Required"));
				obj=(GuiTestObject) selection[0];
				obj.click();
				sleep(1);
				theCell.click();
				statusList=sq.get_html_statusList(row);
				selection=(TestObject[]) statusList.find(atDescendant(".class","Html.A",".text","Quote Price OK"));
			}
			obj=(GuiTestObject) selection[0];
			obj.click();
			sleep(0.5);
		}
	}
}

