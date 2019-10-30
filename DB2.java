package db2;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.Global;

import com.rational.test.ft.script.RationalTestScript;
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

public class DB2
{
	/**
	 * Script Name   : <b>DB2</b>
	 * Generated     : <b>Sep 4, 2012 12:39:28 AM</b>
	 * Description   : Functional Test Script
	 * Original Host : WinNT Version 5.1  Build 2600 (S)
	 * 
	 * @since  2012/09/04
	 * @author sheaulk
	 */
	public static Connection connection = null;
	public static ResultSet resultset = null;
	public static Statement statement = null;
		
	public DB2() {
		
	}

	public void connect() throws Exception
		{
			Driver driver = new COM.ibm.db2.jdbc.app.DB2Driver();
			DriverManager.registerDriver(driver);
			System.out.println("Driver Loaded Successfully...");
			connection = DriverManager.getConnection("jdbc:db2:SDMRT",Global.db2Id,Global.db2Pwd);
			if (connection == null)
			{
				System.out.println("connection failed");
			}
			connection.setAutoCommit(true);
			System.out.println("Successfully connected to DB2...");
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		}

	public ResultSet query(String arg) throws SQLException
		{
			try
			{
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				resultset = statement.executeQuery(arg);
			}catch (Exception ioe)
				{
					System.out.println("Query failed: "+ arg);
					ioe.printStackTrace();
				}
			return resultset;
		}

	public void close () throws Exception
		{
			if (connection !=null)
			{
				try {
					connection.commit();
					connection.close();
				}catch (Exception e)
					{
						System.out.println("Problem in closing DB2 coonection: " + e.getMessage());
						connection = null;
					}
			}
		}
}

