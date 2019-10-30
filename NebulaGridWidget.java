package util;
import java.awt.Point;

import com.rational.test.ft.object.interfaces.TestObject;
/**
 * Description   : Functional Test Script
 * @author sheaulk
 */

	public class NebulaGridWidget {
		private TestObject[] rowList;
		TestObject obj;
		int itemCount = 0;
		int columnCount = 0;
		
		public NebulaGridWidget(TestObject o){
					
			obj = o;
			itemCount = Integer.parseInt(obj.invoke("getItemCount").toString());
			columnCount = Integer.parseInt(obj.invoke("getColumnCount").toString());
			rowList = (TestObject[]) obj.invoke("getItems");
		}
		
		public int getGridRowCount(){
			return  itemCount;
		}
		public int getColumnCount(){
			return columnCount;
		}
		public String getGridRowsAsString(){
			
			TestObject[] children = (TestObject[]) obj.invoke("getItems");
			StringBuffer sb = new StringBuffer();
			for (int i=0; i<children.length; i++){
				TestObject row = children[i];
					for (int j=0; j<columnCount; j++){
						String txt = row.invoke("getText","(I)Ljava/lang/String;",new Object[]{new Integer(j)}).toString();
						if (txt!= null && txt.length()>0){
							sb.append(" ").append(txt);
						}
					}
			}
			return sb.toString();
		}
		
		
		public String getItemAt(int rowIndex, int colIndex){
			String item = new String("asd");
			//TODO test bounds
			TestObject objItem = rowList[rowIndex];

			item =((TestObject)objItem).invoke("getText", "(I)Ljava/lang/String;", new Object[]{new Integer(colIndex)}).toString();

			return item;
		}
		
		public String getColumnHeader(int colIndex){
			TestObject[] cols;

			cols = (TestObject[]) obj.invoke("getColumns");
			if (colIndex<cols.length){
				return cols[colIndex].invoke("getText").toString();
			}
			
			return "";
		}
		
		
		public Point getPoint(int rowIndex, int colIndex){
			
			TestObject objItem = rowList[rowIndex];
			java.awt.Rectangle re = (java.awt.Rectangle)objItem.invoke("getBounds", "(I)Lorg/eclipse/swt/graphics/Rectangle;", new Object[]{colIndex});
//			System.out.println(re);
//			System.out.println(getItemAt(rowIndex, colIndex));
			
			if ((re.height == 0 ) && (re.width == 0)){
//				System.out.println(objItem.getProperties());
				expandParent(rowIndex);
				return getPoint(rowIndex, colIndex);
			}
			
			return new Point(re.x + re.width/2, re.y + re.height/2); 
		}
		
		private void expandParent(int row){
			TestObject objItem;
			int level = Integer.parseInt(rowList[row].getProperty("level").toString());
			if (level==0) return;
			for (int i = row-1; i >= 0; i--){
				objItem = rowList[i];
				if (Integer.parseInt(objItem.getProperty("level").toString()) == level-1){
					if (objItem.getProperty("expanded").toString().equals("false") && 
							(objItem.getProperty("children").toString().equals("true"))){
						java.awt.Rectangle re = (java.awt.Rectangle)objItem.invoke("getBounds", "(I)Lorg/eclipse/swt/graphics/Rectangle;", new Object[]{0});
						if ((re.height ==0) && (re.width==0)){
							expandParent(i);
						}
						objItem.invoke("setExpanded","(Z)V;",new Object[]{true});
						return;
					}
				}
			}
		}

		
	}


