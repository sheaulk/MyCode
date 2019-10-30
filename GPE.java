package tasks;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import resources.tasks.GPEHelper;
import util.Global;
import util.ReportGenerator;
import util.Utility;
import appObjects.GPE_Dialog;
import appObjects.GPE_Login;
import appObjects.GPE_Menu;
import appObjects.GPE_QuoteDataFromSAP;
import appObjects.GPE_QuoteInformation;
import appObjects.GPE_QuoteSearch;
import appObjects.GPE_SystemParameterFiles;

import com.rational.test.ft.object.interfaces.DomainTestObject;
import com.rational.test.ft.object.interfaces.GuiTestObject;
import com.rational.test.ft.object.interfaces.TestObject;
import com.rational.test.ft.object.interfaces.TopLevelTestObject;

import db2.DB2;

/**
 * Description   : Functional Test Script
 * @author sheaulk
 */
public class GPE extends GPEHelper
{
	/**
	 * Script Name   : <b>GPE_Tasks</b>
	 * Generated     : <b>Aug 30, 2012 5:49:25 PM</b>
	 * Description   : Functional Test Script
	 * Original Host : WinNT Version 5.1  Build 2600 (S)
	 * 
	 * @since  2012/08/30
	 * @author sheaulk
	 */
	GPE_Login login = new GPE_Login();
	GPE_Menu menu = new GPE_Menu();
	GPE_Dialog dialog = new GPE_Dialog();
	GPE_SystemParameterFiles systemfile = new GPE_SystemParameterFiles();
	GPE_QuoteInformation quoteinfo = new GPE_QuoteInformation();
	GPE_QuoteSearch quotesearch = new GPE_QuoteSearch();
	GPE_QuoteDataFromSAP quotedata = new GPE_QuoteDataFromSAP();
	DB2 db2 = new DB2();
	ReportGenerator rpt = new ReportGenerator();
	Common common = new Common();
	
	public void logIn (String id, String password)
	{
		menu.get_menu().waitForExistence();
		menu.get_menu().click(atPath("File"));
		menu.get_menu().click(atPath("File->Sign In"));
		login.get_id().waitForExistence();
		login.get_id().setText(id);
		login.get_password().setText(password);
		login.get_signIn().click();
	}
	public void logInAtPrompt(String id, String password)
	{
		login.get_id().setText(id);
		login.get_password().setText(password);
		login.get_signIn().click();
	}
	public void openSDMFile(String fileName)
	{
		menu.get_file().waitForExistence();
		menu.get_menu().click(atPath("File"));
		menu.get_menu().click(atPath("File->Open SDM File"));
		sleep(.5);
		dialog.get_fileDialog().setFile(atFile(fileName));
		sleep(0.5);
		dialog.get_fileDialog().inputKeys("{enter}");
		rpt.logMsg(fileName+" is opened.", Global.outputFile);
	}
	public void upgradeBid()
	{
		systemfile.get_upgradeBidLink().click();
		while(systemfile.get_upgradeBidLink().isEnabled())dismissUnexpectedWindow();
	}
	public void saveAsSDMFile()
	{
		File source = new File(Global.OldGPECase);
		File destination = new File(Global.NewGPECase);
		if(!destination.exists()){
			try {
				Utility.copyFile(source, destination);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void unlockPriceCase()
	{
		menu.get_file().waitForExistence();
		menu.get_menu().click(atPath("Lock Status"));
		menu.get_menu().click(atPath("Lock Status->Unlock"));
	}
	public void clearQuoteScreen()
	{
		quoteinfo.get_quoteSection().waitForExistence();
		if(quoteinfo.get_clearScreen().isEnabled()){
			quoteinfo.get_clearScreen().click();
			dismissUnexpectedWindow();
		}
	}
	public void associateQuote(String quote)
	{
//		quoteinfo.get_lookupSAPQuote().waitForExistence();
//		if(!quoteinfo.get_associateQuote().hasFocus())quoteinfo.get_associateQuote().click();
//		sleep(10);
		quoteinfo.get_lookupSAPQuote().doubleClick();
		sleep(1);
		if(login.get_id().exists())logInAtPrompt(Global.userId,Global.pwd);
		quotesearch.get_quoteID().waitForExistence();
		quotesearch.get_quoteID().setText(quote);
		quotesearch.get_searcH().click();
		while(!quotesearch.get_select().isEnabled())quotesearch.get_results().click(atPoint(129,49));
		sleep(0.5);
		quotesearch.get_select().click();
		sleep(2);
		quotedata.get_quoteDataFromSAP().inputKeys("%o");
		rpt.logMsg("Quote "+quote+" is associated with the price case.", Global.outputFile);
		Global.currentQuote=quote;
		dismissUnexpectedWindow();
		dismissUnexpectedWindow();
//		quotedata.get_OK().waitForExistence();
//		quotedata.get_OK().ensureObjectIsVisible();
//		quotedata.get_OK().click();
	}
	public void initiateSQA()
	{
		menu.get_file().waitForExistence();
		menu.get_menu().click(atPath("Data Export"));
		menu.get_menu().click(atPath("Data Export->Initiate SAP Quote Approval"));
		sleep(5);
		rpt.logMsg("Initiating SAP Quote Approval...", Global.outputFile);
		dismissUnexpectedWindows("Java", "Approval in progress");
	}

	public String getItemRollup(GuiTestObject grid, int row, String colHeader)
	{
		util.NebulaGridWidget neb = new util.NebulaGridWidget(grid);
		int noCol=neb.getColumnCount();
		int noRow=neb.getGridRowCount();
		int theCol=-1;
		String item=null;
		System.out.println(neb.getColumnCount()+" columns");
		System.out.println(neb.getGridRowCount()+" rows");
		for (int i = 0; i < noCol; i++) {
			String header = neb.getColumnHeader(i);
			if(header.contains(colHeader)){
				theCol=i;
				break;
			}
		}
		if(theCol>-1){
			item = neb.getItemAt(row, theCol);
			System.out.println(neb.getItemAt(1,theCol).toString());
		}

		return item;
	}
	public void verify1014(GuiTestObject grid, int row, String colHeader, String xmlField) throws SQLException, ParseException
	{
		String gpeTxt=null;
		String xmlTxt=null;
		Global.noXml=true;
		Global.sql1014=Global.sql1014.replace("nnnnnnnnnn", Global.QuoteId);
		String sqlts = common.getQuerySingleResult(Global.sql1014, 2);
		if(sqlts!=null){
			if(common.newResult(sqlts)){
				quoteinfo.get_itemRollup().click();
				gpeTxt = getItemRollup(grid, row, colHeader);
				gpeTxt = gpeTxt.replace(",", "");
				xmlTxt = common.getQuerySingleResult(Global.sql1014, 1);
				if(null!=xmlTxt) Global.noXml=false;
				String val=common.getXmlVal(xmlTxt, xmlField);
				System.out.println("val is "+val);
				System.out.println("gpeTxt is "+gpeTxt);
				if(gpeTxt.contains(val))
					rpt.logMsg("Value of "+xmlField+" "+val+" matches "+colHeader+" in ItemRollup.", Global.outputFile);
				else
					rpt.logMsg("Value of "+xmlField+" "+val+" matches "+colHeader+" in ItemRollup.", Global.outputFile);
			}
		}
		if(Global.noXml) rpt.logMsg("No 1014 xml created.", Global.outputFile);
	}
	public void dismissUnexpectedWindow()
	{
		   DomainTestObject domains[] = getDomains();
//		   System.out.println(domains.length);
		   for (int i = 0; i < domains.length; ++i)
		   {
//			   System.out.println(domains[i].getName().toString());
		       if (domains[i].getName().equals("Java"))
		       {
		    	   sleep(5);
		    	   TestObject[] topObjects = domains[i].getTopObjects();

		           if (topObjects != null)
		           {
//		               System.out.println(topObjects.length);
		        	   try
		               {
		                   for (int j = 0; j < topObjects.length; ++j)
		                   {
//		                	   System.out.println(".class is "+topObjects[j].getProperty(".class").toString());
		                	   GuiTestObject question=(GuiTestObject) Utility.findTheObject(topObjects[j],"text","^OK$",".class","org.eclipse.swt.widgets.Button");
		                	   if(null!=question){
		                		   String caption=topObjects[j].getProperty("text").toString();
		                		   if (null==caption)caption="";
//		                		   System.out.println("text is "+question.getProperty("text").toString());
		                		   ((TopLevelTestObject)topObjects[j]).inputKeys("{enter}");
		                		   GuiTestObject obj=(GuiTestObject) Utility.findTheObject(topObjects[j],".class","org.eclipse.swt.widgets.Label",".classIndex","1");
		                		   String desc="";
		                		   if(null!=obj) desc=obj.getProperty("text").toString();
		                		   rpt.logMsg("Dismissed window "+caption+" "+desc, Global.outputFile);
		                	   }
//		                       if (topObjects[j].getProperty(".class").equals("org.eclipse.swt.widgets.Shell"))
//		                    org.eclipse.swt.widgets.Label   {
//		                           // A top-level HtmlDialog is found.
//		                           logWarning("HtmlScript.onObjectNotFound - dismissing dialog.");
//		                           tryorg.eclipse.swt.widgets.Label
//		                           {
//		                               dismissedAWindow = true;
//		                               ((TopLevelTestObject)topObjects[j]).inputKeys("{enter}");
//		                           }
//		                           catch(RuntimeException e) {}
//		                       }
		                   }
		               }
		               finally
		               {
		                   //unregister all references to top objects
		                   unregister(topObjects);
		               }
		           }
		                       
		       }
		   }
	}
	/**
	 * dismissUnexpectedWindows - dismissed all unexpected windows including the last window passed.
	 * @param lastWindow - the last window to be dismissed.
	 */
	public void dismissUnexpectedWindows(String windowType, String lastWindow)
	{
		boolean done=false;
		boolean dismissedAWindow = false;
		String caption = "";
		String preCaption="";
		
		do {
			dismissedAWindow = false;
//	    	   int tryNo=1;
//	    	   DomainTestObject domains[]=null;
//	    	   while(tryNo<=20){
//	    	   		domains = getDomains();
//	    	   	if(domains.length==0){
//	    	   		tryNo++;
//	    	   		sleep(0.5);
//	    	   	}else tryNo=20;
//	    	   }			
			sleep(10);
		   DomainTestObject domains[] = getDomains();
		   System.out.println(domains.length+" domains");	   
		   for (int i = 0; i < domains.length; ++i)
		   {
		       if (domains[i].getName().equals("Java"))
		       {
				   System.out.println("domain is Java");
		    	   sleep(5);
		    	   TestObject[] topObjects=domains[i].getTopObjects();
//		    	   int tryNo=1;
//		    	   TestObject[] topObjects=null;;
//		    	   while(tryNo<=Global.maxTry){
//		    	   	topObjects = domains[i].getTopObjects();
//		    	   	if(topObjects.length==0){
//		    	   		tryNo++;
//		    	   		sleep(0.5);
//		    	   	}else tryNo=Global.maxTry;
//		    	   }
//System.out.println(topObjects.length+" topObjects");
		           if (topObjects != null)
		           {
		        	   try
		               {	        		   
		                   for (int j = 0; j < topObjects.length; ++j)
		                   {
		                	   if(!dismissedAWindow){
		                		   preCaption=caption;
		                		   caption = topObjects[j].getProperty("Text").toString();
		                		   if(null==caption) caption="";
			                	   String[] val = ".*OK$,.*YES$,.*Ok$,.*Yes$,.*Cancel$".split(",");
			                	   GuiTestObject question=(GuiTestObject) Utility.findTheObject("text",val,".class","org.eclipse.swt.widgets.Button");
			                	   if(null!=question){
		                			   System.out.println(j+"caption is "+caption+"question is "+question.getProperty("text".toString()));
		                			   try {
											if(caption.contains("Approval"))done=true; 
									//		if((caption=="")&&(preCaption.contains("Progress Information")))done=true;
											if(!(caption.contains("Progress Information"))&&!(question.getProperty("text").toString().contains("Cancel"))){
												System.out.println("1st caption"+caption+"question "+question);
												((TopLevelTestObject)topObjects[j]).inputKeys("{enter}");

												rpt.logMsg("Dismissed window "+caption, Global.outputFile);
											}
											dismissedAWindow = true;
										} catch (RuntimeException e) {}
			                	   }
			                       if (topObjects[j].getProperty(".class").equals("org.eclipse.swt.widgets.Shell"))
			                       {
			                           // A top-level HtmlDialog is found.
			                           logWarning("HtmlScript.onObjectNotFound - dismissing dialog.");
			                           try
			                           {
			                        	   System.out.println("2nd "+caption+ " "+j);
			                        	   if(caption.contains("Approval"))
			                        		   done=true;
			                        	   if((!dismissedAWindow)&&(!done)&&(!caption.contains("Progress Information"))){
		                        	    	 ((TopLevelTestObject)topObjects[j]).inputKeys("{enter}");
		                        	    	 dismissedAWindow = true;
		                        	    	 rpt.logMsg("Dismissed window "+caption, Global.outputFile);
			                        	   }
			                           }
			                           catch(RuntimeException e) {}
			                       }
		                	   }
		                	   if(done)break;
		                   }
		               }
		               finally
		               {
		                   //unregister all references to top objects
		                   unregister(topObjects);
		               }
		           }
		                       
		       }
		       if(done)break;
		   }
		} while (!done);
	}
}

