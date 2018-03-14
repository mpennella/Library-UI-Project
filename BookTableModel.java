package cu.cs.cpsc215.project2;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;


@SuppressWarnings("serial")
/**
 * @author mpennel
 */
public class BookTableModel extends AbstractTableModel {

	/**
	 * list of books that will be displayed in the table
	 */
	protected ArrayList<Book> bookList = new ArrayList<Book>();
	/**
	 * array of titles of columns that will be displayed
	 */
	String[] columnName = {"Title", "Author", "Genre", "Status"};
	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public int getRowCount() {
		return bookList.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		switch (arg1) {
			case 0: return bookList.get(arg0).getTitle();
			case 1: return bookList.get(arg0).getAuthor();
			case 2: return bookList.get(arg0).getGenre();
			case 3: if(bookList.get(arg0).isCheckedOut())
						return("Checked Out");
					else
						return("In Stock");
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
