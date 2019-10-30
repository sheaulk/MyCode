package tasks;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;

import resources.tasks.CommonHelper;
import util.Global;
import util.ReportGenerator;
import util.Utility;


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

import db2.DB2;
/**
 * Description   : Functional Test Script
 * @author sheaulk
 */
public class Common extends CommonHelper
{
	/**
	 * Script Name   : <b>Common</b>
	 * Generated     : <b>Sep 4, 2012 12:21:48 PM</b>
	 * Description   : Functional Test Script
	 * Original Host : WinNT Version 5.1  Build 2600 (S)
	 * 
	 * @since  2012/09/04
	 * @author sheaulk
	 */
	DB2 db2 = new DB2();
	ReportGenerator rpt = new ReportGenerator();
	
	public void connectDB2() throws Exception
	{
		if(!Global.dbConnected)
		{
			try {
				db2.connect();
				Global.dbConnected=true;
				rpt.logMsg("Connected to DB2", Global.outputFile);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void disconnectDB2() throws Exception
	{
		if(Global.dbConnected){
			try {
				db2.close();
				Global.dbConnected=false;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public String getQuerySingleResult(String sql, int col) throws SQLException
	{
		String result=null;
		ResultSet rs=db2.query(sql);
		sleep(1);
		rs.next();
		int rowNo = rs.getRow();
		if (rowNo>0)
			result = rs.getString(col);
		return result;
	}
	public ResultSet getQueryResultSet(String sql) throws SQLException
	{
		return db2.query(sql);
	}
	public int getQueryCount(String sql) throws SQLException
	{
		int count = Integer.parseInt(db2.query(sql).toString());
		return count;
	}
	public void getInitTimestamp()
	{
		java.util.Date date= new java.util.Date();
		Global.ts=new Timestamp(date.getTime());
	}
	public boolean newResult(String sqlTime) throws ParseException
	{
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
	    java.util.Date parsedDate = dateFormat.parse(sqlTime);
	    int hr=parsedDate.getHours();
	    hr=hr+2;
	    parsedDate.setHours(hr);
	    java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
	    if (timestamp.after(Global.ts))
	    	return true;
	    else
	    	return true;
	}
	public String getXmlVal(String xml, String field)
	{
		int start=xml.indexOf(">"+field+"<");
		if(start>-1){
			start=start+field.length()+2;
			xml=xml.substring(start);
			start=xml.indexOf("VALUE>")+6;
			xml=xml.substring(start);
			int end = xml.indexOf("<");
			if (end>-1) xml= xml.substring(0, end);
			return xml;
		}else
			return xml;
	}
	public float getSumOfXmlVal(String xml, String field)
	{
		int start= xml.indexOf(">"+field+"<");
		int end=0;
		float total=0;
		while (start>0){
			start=start+field.length()+2;
			xml=xml.substring(start);
			start=xml.indexOf("VALUE>")+6;
			xml=xml.substring(start);
			end = xml.indexOf("<");
			if (end>-1) total = total+Float.parseFloat(xml.substring(0, end));
			start= xml.indexOf(">"+field+"<");
		}
		return total;
	}
	public void logRptSetup(String title)
	{
		String logPath="C:\\SDMBHI\\log\\";
		Utility.makeDir(logPath);
		/*
		 * Initialize Global.outputFile to the name of the log file which has a timestamp at the end
		 * of the start time of the batch run.
		 */
		ReportGenerator rpt = new ReportGenerator(logPath+title+title+"TestSummary.log");
		Global.outputFile=rpt.returnFilename();
		rpt.Title(title);
		rpt.logMsg("", Global.outputFile);
	}
	public void getUserInfo()
	{
		try {
			Global.userId=JOptionPane.showInputDialog("Enter Your Intranet ID.");
			Global.pwd=JOptionPane.showInputDialog("Enter Your Password.");
			Global.db2Id=JOptionPane.showInputDialog("Enter Your DB2 ID.");
			Global.db2Pwd=JOptionPane.showInputDialog("Enter Your DB2 Password.");
		} catch (HeadlessException e) {
			Global.abortTest=true;
			throw new RuntimeException("Failed to get user information.");
		}
	}
	public void startGPE()
	{
		startApp("SDMGPE");
	}
}

