package util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import resources.util.UtilityHelper;

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
import com.ibm.rational.extensions.gui.utils.FindUtils;
import com.ibm.rational.extensions.gui.utils.PropertyFinder;
import com.ibm.rational.extensions.gui.widgets.dojo.grid.DojoxGrid;
import com.ibm.rational.extensions.gui.widgets.dojo.grid.DojoxGridCell;
import com.ibm.rational.extensions.gui.widgets.dojo.grid.DojoxGridView;
import com.ibm.rational.test.ft.object.interfaces.sapwebportal.*;
/**
 * Description   : Functional Test Script
 * @author sheaulk
 */
public class Utility extends UtilityHelper
{
	/**
	 * Script Name   : <b>Utility</b>
	 * Generated     : <b>Aug 28, 2012 6:38:52 PM</b>
	 * Description   : Functional Test Script
	 * Original Host : WinNT Version 5.1  Build 2600 (S)
	 * 
	 * @since  2012/08/28
	 * @author sheaulk
	 */
	public static TestObject findTheObject(TestObject parent, String type1, String val1, String type2, String val2)
	{
//		RootTestObject root = getRootTestObject();
		RegularExpression re1 = new RegularExpression(val1,false);
//		RegularExpression re2 = new RegularExpression(val2,false);
		TestObject[] to=(TestObject[]) parent.find(atDescendant(type1, re1, type2, val2));
		System.out.println(to.length);
		if(to.length>0){
			return to[0];
		}else
			return null;
	}
	public static TestObject findTheObject(TestObject parent, String type1, RegularExpression val1, String type2, RegularExpression val2)
	{
//		RootTestObject root = getRootTestObject();
//		RegularExpression re1 = new RegularExpression(val1,false);
//		RegularExpression re2 = new RegularExpression(val2,false);
		TestObject[] to=(TestObject[]) parent.find(atDescendant(type1, val1, type2, val2));
		System.out.println(to.length);
		if(to.length>0){
			return to[0];
		}else
			return null;
	}
	public static TestObject[] findTheObjects(TestObject parent, String type1, String val1, String type2, String val2)
	{
//		RootTestObject root = getRootTestObject();
		RegularExpression re = new RegularExpression(val1,false);
		TestObject[] to=(TestObject[]) parent.find(atDescendant(type1, re, type2, val2));
		System.out.println(to.length);
		if(to.length>0){
			return to;
		}else
			return null;
	}
	public static TestObject[] findTheObjects(TestObject parent, String type1, RegularExpression val1, String type2, RegularExpression val2)
	{
//		RootTestObject root = getRootTestObject();
//		RegularExpression re = new RegularExpression(val1,false);
		TestObject[] to=(TestObject[]) parent.find(atDescendant(type1, val1, type2, val2));
		System.out.println(to.length);
		if(to.length>0){
			return to;
		}else
			return null;
	}
	public static TestObject findTheObject(String type1, String val1, String type2, String val2)
	{
		RootTestObject root = getRootTestObject();
		RegularExpression re = new RegularExpression(val1,false);
		TestObject[] to=(TestObject[]) root.find(atDescendant(type1, re, type2, val2));
		System.out.println(to.length);
		if(to.length>0){
			return to[0];
		}else
			return null;
	}
	public static TestObject findTheObject(String type1, RegularExpression val1, String type2, RegularExpression val2)
	{
		RootTestObject root = getRootTestObject();
		TestObject[] to=(TestObject[]) root.find(atDescendant(type1, val1, type2, val2));
		System.out.println(to.length);
		if(to.length>0){
			return to[0];
		}else
			return null;
	}
	public static TestObject findTheObject(String type1, String[] val1, String type2, String val2)
	{	TestObject[] to=null;
		boolean found=false;
		RootTestObject root = getRootTestObject();
		for (int i = 0; i < val1.length; i++) {
			if(!found){
				RegularExpression re = new RegularExpression(val1[i],false);
				to=(TestObject[]) root.find(atDescendant(type1, re, type2, val2));
				if(to.length>0) found=true;
			}
		}
		if(to.length>0){
			return to[0];
		}else
			return null;
	}
	/**
	 * makeDir - create the specified directory if it doesn't exist
	 * @param path - the path to be created.
	 */
	public static void makeDir(String path){
		boolean suc=true;
		File dir=new File(path);
		if(!dir.exists())
			dir.mkdir();
	}
	/**
	 * logDetailRpt - log detail message in the text report.  Each message starts with a tab to lineup with other
	 *                detail message in the report.
	 * @param msgType - a string that indicates the type of the message such as error or warning...
	 * @param msg
	 */
	public static void logDetailRpt(String msgType, String msg){
//		rpt.logMsg("\t"+msgType+": "+msg, Global.outputFile);
	}
	public static Object[][] getDojoGridContent(TestObject parent)
	{
		TestObject grid = FindUtils.find(new PropertyFinder(
				parent, "Html.DIV", //.class
				"class", "dojoxGrid", //property, value 
				false, false));
		DojoxGrid gridChassis = new DojoxGrid((GuiTestObject)grid);

		gridChassis.refresh();

		java.util.List gridtitles = gridChassis.getGridHeaderTitles();
		String [] Rowdata = new String[gridtitles.size()];
		DojoxGridView dojoGridView2 = gridChassis.getGridView(0);	//only 1 grid view on this page.

		// get number of rows in grid.
		List gridRows2 = ((dojoGridView2.getDojoxGridRowList()!=null) 
			? dojoGridView2.getDojoxGridRowList()
			: dojoGridView2.getDojoxGridRowbarList());
		int numGridRows2 = gridRows2.size();
		// process each row.
		String printable2 = "";
		Object[][] tableData = new Object[numGridRows2+1][gridtitles.size()];
		String[] colHeadings = new String[gridtitles.size()];
		for(int z=0; z<numGridRows2; z++) {
			for(int i=0;i<gridtitles.size();i++) {
				String colName = (String) gridtitles.get(i);
				if(z==0)
					tableData[z][i]=colName;
				DojoxGridCell Gridcell = gridChassis.getGridCell((String) gridtitles.get(i),z); /*chagned by slk*/
				String colvalue = (String) Gridcell.wrappedTestObject.getProperty(".text");
//				if(colvalue.equals("Details")){
//					GuiTestObject url= (GuiTestObject) FindUtils.find(new PropertyFinder(
//							grid, "Html.A", //.class
//							"title", "Click here to work on case", //property, value 
//							false, false));
//					url.click();
//				}
				tableData[z+1][i]=colvalue;
				printable2 = printable2 + colvalue + "|";
				Rowdata[i]=colvalue;
			}
			System.out.println("*** row "+z+": "+printable2);
			printable2 = "";
		}
//		JTable resTable = new JTable(tableData, colHeadings);
//		System.out.println(resTable.getColumnCount());
//		System.out.println(resTable.getRowCount());
		return tableData;
	}
	public static String getCellValue(Object[][] theTable, String colName, int rowNo)
	{
		int colNo=-1;
		String cellVal=null;
		for (int i = 0; i < theTable[0].length; i++) {
			String txt=(String) theTable[0][i];
			if(null!=txt){			
				if(txt.equals(colName)){
					colNo=i;
					break;
				}
			}
		}
		if(colNo>-1)cellVal=(String) theTable[rowNo][colNo];
		return cellVal;	
	}
	public static int getTheRowNo(Object[][] theTable, String colName, String searchTxt)
	{
		int colNo=-1;
		int rowNo=-1;
		for (int i = 0; i < theTable[0].length; i++) {
			if(null!=theTable[0][i]){
				String txt=(String) theTable[0][i];
				if(txt.equals(colName)){
					colNo=i;
					break;
				}
			}
		}
		if(colNo>-1){
			for (int i=1; i < theTable.length; i++){
				String txt=(String) theTable[i][colNo];
				if(null!=txt){
					if(txt.equals(searchTxt)){
						rowNo=i;
						break;
					}
				}
				
			}
		}
		return rowNo;	
	}
	public static boolean stringExists(Object[][] theTable, String searchTxt)
	{
		String cell=null;
		boolean exists=false;
		
		for (int i=0; i < theTable.length; i++){
			for (int j=0; j < theTable[0].length; j++){
				cell=theTable[i][j].toString();
				if(cell.equalsIgnoreCase(searchTxt)){
					exists=true;
					break;
				}
			}
		}
		return exists;
	}
	public static void waitForExistence(TestObject parent, String clas, String property, String val){
		boolean exist=false;
		TestObject obj=null;
		int tryNo=1;
		while(!exist){
			try {
				obj=(TestObject) FindUtils.find(new PropertyFinder(parent, clas, //.class
						property, val, //property, value 
						false, false));
			} catch (Exception e) {
				tryNo++;
				if(tryNo>Global.maxTry){
					throw new RuntimeException(e.getMessage());
				}
			}
			if(null!=obj){
				if(!obj.getProperty(".className").toString().endsWith("dijitHidden"))exist=true;
			}
		}
	}
	public static void waitForExistence(TestObject parent, String clas, String property1, String val1, String property2, String val2){
		boolean exist=false;
		TestObject obj=null;
		int tryNo=1;
		while(!exist){
			try {
				obj=(TestObject) FindUtils.find(new PropertyFinder(parent, clas, //.class
						property1, val1, property2, val2,//property, value 
						false, false));
			} catch (Exception e) {
				tryNo++;
				if(tryNo>Global.maxTry){
					throw new RuntimeException(e.getMessage());
				}
			}
			if(null!=obj){
				if(!obj.getProperty(".className").toString().endsWith("dijitHidden"))exist=true;
			}
		}
	}
	public static void waitForExistence(TestObject parent, String clas, String property, RegularExpression re){
		boolean exist=false;
		TestObject obj=null;
		int tryNo=1;
		while(!exist){
			try {
				obj=(TestObject) FindUtils.find(new PropertyFinder(parent, clas, //.class
						property, re, //property, value 
						false, false));
			} catch (Exception e) {
				tryNo++;
				if(tryNo>Global.maxTry){
					throw new RuntimeException(e.getMessage());
				}
			}
			if(null!=obj){
				if(!obj.getProperty(".className").toString().endsWith("dijitHidden"))exist=true;
			}
		}
	}
	public static void waitForExistence(TestObject parent, String clas, String property1, RegularExpression re1, String property2, RegularExpression re2){
		boolean exist=false;
		TestObject obj=null;
		int tryNo=1;
		while(!exist){
			try {
//				obj=Utility.findTheObject(parent, property1, re1, property2, re2);
				obj=(TestObject) FindUtils.find(new PropertyFinder(parent, clas, //.class
						property1, re1, property2, re2,//property, value 
						false, false));
			} catch (Exception e) {
				tryNo++;
				if(tryNo>Global.maxTry){
					throw new RuntimeException(e.getMessage());
				}
			}
			if(null!=obj){
				if(!obj.getProperty(".className").toString().endsWith("dijitHidden"))exist=true;
			}
		}
	}
	public static void waitForExistence(String clas, String property1, RegularExpression re1, String property2, RegularExpression re2){
		boolean exist=false;
		TestObject obj=null;
		RootTestObject parent=null;
		int tryNo=1;
		while(!exist){
			try {
				parent= getRootTestObject();
//				obj=Utility.findTheObject(parent, property1, re1, property2, re2);
				obj=(TestObject) FindUtils.find(new PropertyFinder(parent, clas, //.class
						property1, re1, property2, re2,//property, value 
						false, false));
			} catch (Exception e) {
				tryNo++;
				if(tryNo>Global.maxTry){
					throw new RuntimeException(e.getMessage());
				}
			}
			if(null!=obj){
				if(!obj.getProperty(".className").toString().endsWith("dijitHidden"))exist=true;
			}
		}
	}
	public static void closeAllBrowsers()
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
	public static void copyFile(File sourceFile, File destFile) throws IOException {
	    if(!destFile.exists()) {
	        destFile.createNewFile();
	    }

	    FileChannel source = null;
	    FileChannel destination = null;

	    try {
	        source = new FileInputStream(sourceFile).getChannel();
	        destination = new FileOutputStream(destFile).getChannel();
	        destination.transferFrom(source, 0, source.size());
	    }
	    finally {
	        if(source != null) {
	            source.close();
	        }
	        if(destination != null) {
	            destination.close();
	        }
	    }
	}
}

