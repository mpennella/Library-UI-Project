package cu.cs.cpsc215.project2;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;


@SuppressWarnings("serial")
/**
 * @author mpennel
 */
public class CheckOutTableModel extends AbstractTableModel {
	/**
	 * list of books that will be displayed in the table
	 */
	protected ArrayList<Book> bookList = new ArrayList<Book>();
	/**
	 * array of titles of columns that will be displayed
	 */
	String[] columnName = {"Title", "Action"};
	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		try{
			return bookList.size();
		} catch(NullPointerException e){
			return 0;
		}
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		switch (arg1) {
			case 0: 
				try{return bookList.get(arg0).getTitle();}
				catch(NullPointerException e){return "";}
			case 1: 
				try{if (bookList.get(arg0).isCheckedOut() == true)
						return("Return");
					else
						return("Check out");}
				catch(NullPointerException e){return "";}
			default: return 0;
		}
	}
	
	/**
	 * Removes a row from the table
	 * @param row specified row to be removed
	 */
	public void removeRow(int row){
		bookList.remove(row);
		fireTableRowsDeleted(row, row);
	}
	/**
	 * Adds a new book to the list and updates the table
	 * @param newBook Book object to be added
	 */
	protected void addNewBook(Book newBook)
	{
		bookList.add(newBook);	
		fireTableRowsUpdated(0, bookList.size());
	}
	/**
	 * Prevents user editing of cells for all rows and columns
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex){
		return false;
	}
	@Override
	public void setValueAt(Object str, int rowIndex, int columnIndex)
	{
		bookList.get(rowIndex).set(str, columnIndex); 
	}
	@Override
	public String getColumnName(int index){
		return columnName[index];
	}
}	