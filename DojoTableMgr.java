package util;
import java.util.List;

import resources.util.DojoTableMgrHelper;
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
public class DojoTableMgr extends DojoTableMgrHelper
{
	/**
	 * Script Name   : <b>DojoTableMgr</b>
	 * Generated     : <b>Sep 24, 2012 1:08:23 PM</b>
	 * Description   : Functional Test Script
	 * Original Host : WinNT Version 5.1  Build 2600 (S)
	 * 
	 * @since  2012/09/24
	 * @author sheaulk
	 */
	
	public DojoTableMgr(){
		
	}
	public Object[][] getDojoGridContent(TestObject parent, boolean addWildcard)
	{
		RegularExpression dojoxGridRE;
		if(addWildcard)
			dojoxGridRE = new RegularExpression(
					"^dojoxGrid.*", false);
		else dojoxGridRE = new RegularExpression(
				"^dojoxGrid", false);
		TestObject grid = FindUtils.find(new PropertyFinder(
				parent, "Html.DIV", //.class
				"class", dojoxGridRE, //property, value 
				false, true));
		DojoxGrid gridChassis = new DojoxGrid((GuiTestObject)grid);

		try {
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
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	public Object[][] getDojoGridContent(TestObject parent, String key, String value)
	{
		TestObject grid = FindUtils.find(new PropertyFinder(
				parent, "Html.DIV", //.class
				key, value, //property, value 
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
	public String getCellValue(Object[][] theTable, String colName, int rowNo)
	{
		int colNo=-1;
		String cellVal=null;
		for (int i = 0; i < theTable[0].length; i++) {
			String txt= (String)theTable[0][i];
			if(null!=txt){			
				if(txt.equals(colName)){
					colNo=i;
					break;
				}
			}
		}
		if(colNo>-1)cellVal=(String)theTable[rowNo][colNo];
		return cellVal;	
	}
	public Object getCellObject(Object[][] theTable, String colName, int rowNo)
	{
		int colNo=-1;
		Object cell=null;
		for (int i = 0; i < theTable[0].length; i++) {
			String txt=(String)theTable[0][i];
			if(null!=txt){			
				if(txt.equalsIgnoreCase(colName)){
					colNo=i;
					break;
				}
			}
		}
		if(colNo>-1)cell= theTable[rowNo][colNo];
		return cell;	
	}
	public int getRowNo(Object[][] theTable, String colName, String searchTxt)
	{
		int colNo=-1;
		int rowNo=-1;
		for (int i = 0; i < theTable[0].length; i++) {
			if(null!=theTable[0][i]){
				String txt=(String)theTable[0][i];
				if(txt.equals(colName)){
					colNo=i;
					break;
				}
			}
		}
		if(colNo>-1){
			for (int i=1; i < theTable.length; i++){
				String txt=(String)theTable[i][colNo];
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
}

