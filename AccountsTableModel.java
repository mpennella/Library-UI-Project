package cu.cs.cpsc215.project2;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;


@SuppressWarnings("serial")
/**
 * @author mpennel
 */
public class AccountsTableModel extends AbstractTableModel {
	/**list of User objects that will be stored in the table*/
	protected ArrayList<User> userList = new ArrayList<User>();
	/**array of column names that will be displayed in the table*/
	String[] columnName = {"ID", "Username", "Name", "Account Type"};
	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public int getRowCount() {
		return userList.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		switch (arg1) {
			case 0: return userList.get(arg0).getID();
			case 1: return userList.get(arg0).getUsername();
			case 2: return userList.get(arg0).getName();
			case 3: if(userList.get(arg0).isStaff())
						return("Staff");
					else
						return("Normal");
			default: return 0;
		}
	}
	/**
	 * Removes a row from the table
	 * @param row specified row to be removed
	 */
	public void removeRow(int row){
		userList.remove(row);
		fireTableRowsDeleted(row, row);
	}
	/**
	 * Adds a new user to the list and updates the table
	 * @param newUser User object to be added
	 */
	protected void addNewUser(User newUser)
	{
		userList.add(newUser);	
		fireTableRowsUpdated(0, userList.size());
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
		userList.get(rowIndex).set(str, columnIndex); 
	}
	@Override
	public String getColumnName(int index){
		return columnName[index];
	}
}	
