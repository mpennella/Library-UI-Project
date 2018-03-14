package cu.cs.cpsc215.project2;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


@SuppressWarnings("serial")
public class CheckoutWindow extends JFrame {
	@SuppressWarnings({ "static-access", "deprecation" })
	public CheckoutWindow(AccountsTableModel accountsModel, User currentUser, User editedUser, BookTableModel bookModel, CheckOutTableModel checkOutModel){
		super("Checkout");
		setSize(925,400);
		setLayout(new BorderLayout());
		JButton addCheckout = new JButton("Add a Book");
		JButton okButton = new JButton("Okay");
		JButton cancelButton = new JButton("Cancel");
		
		JTable checkOutTable = new JTable(checkOutModel);
		checkOutTable.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane = checkOutTable.createScrollPaneForTable(checkOutTable);
		
		CheckedOutTableModel checkedOutModel = new CheckedOutTableModel();
		checkedOutModel.bookList = editedUser.getCheckoutList();
		JTable checkedOutTable = new JTable(checkedOutModel);
		checkedOutTable.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2 = checkedOutTable.createScrollPaneForTable(checkedOutTable);
		
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(addCheckout);
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		JPanel tablePanel = new JPanel(new BorderLayout());
		tablePanel.add(scrollPane, BorderLayout.EAST);
		tablePanel.add(scrollPane2, BorderLayout.WEST);
		add(buttonPanel, BorderLayout.SOUTH);
		add(tablePanel, BorderLayout.CENTER);
		
		
		checkedOutTable.addMouseListener(new MouseAdapter(){
		     public void mouseClicked(MouseEvent e){
		         if (e.getClickCount() == 2){
		        	 int row = checkedOutTable.getSelectedRow();
		        	 checkOutModel.addNewBook(checkedOutModel.bookList.get(row));
		        	 checkOutModel.fireTableDataChanged();
		        	 checkedOutModel.removeRow(row);
		         }
		     }
		});
		
		
		
 
		addCheckout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				CatalogWindow catalog = new CatalogWindow(accountsModel, currentUser, editedUser, bookModel, checkOutModel, true);
				catalog.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					for(int i = 0; i < checkOutTable.getRowCount(); i++)
					{
						if(checkOutModel.bookList.get(i).checkedOut == true)
						{
							for(int j = 0; j < bookModel.getRowCount(); j++)
							{
								if(bookModel.bookList.get(j).getTitle().equals(checkOutModel.bookList.get(i).getTitle()))
									bookModel.bookList.get(j).setCheckedOut(false);
							}
							editedUser.checkoutList.remove(checkOutModel.bookList.get(i));
						}	
						else
						{
							bookModel.bookList.get(bookModel.bookList.indexOf(checkOutModel.bookList.get(i))).setCheckedOut(true);
							editedUser.getCheckoutList().add(checkOutModel.bookList.get(i));
							editedUser.checkoutList.get(editedUser.checkoutList.size() - 1).setCheckedOut(true);
						}
					}
					for(int i = 0; i < accountsModel.userList.size(); i++)
					{
						int index = -1;
						if(accountsModel.userList.get(i).getName().equals(editedUser.getName()))
						{
							index = i;
							accountsModel.userList.get(index).setCheckoutList(editedUser.getCheckoutList());
						}
					}
					MainWindow main = new MainWindow(accountsModel, currentUser, bookModel);
					main.setVisible(true);
					setVisible(false);
					dispose();
				
				} catch(NullPointerException e1){
					JOptionPane.showMessageDialog(new JFrame("Error"), "Book could not be added to list");
				} catch(ArrayIndexOutOfBoundsException e2){
					JOptionPane.showMessageDialog(new JFrame("Error"), "Error");
				}
				
				try(
						OutputStream file = new FileOutputStream("libraryusers.ser");
						OutputStream buffer = new BufferedOutputStream(file);
						OutputStream output = new ObjectOutputStream(buffer);
						){
					((ObjectOutputStream) output).writeObject(accountsModel.userList);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(new JFrame("Error"), "Error, failed to save books to account.");
				}
				try(
						OutputStream file = new FileOutputStream("librarybooks.ser");
						OutputStream buffer = new BufferedOutputStream(file);
						OutputStream output = new ObjectOutputStream(buffer);
						){
					((ObjectOutputStream) output).writeObject(bookModel.bookList);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(new JFrame("Error"), "Error, failed to save checkout status.");
				}
			}
		});
	
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					for(int i = 0; i < checkOutModel.getRowCount(); i++){
						if(checkOutModel.bookList.get(i).isCheckedOut() == true)
							checkedOutModel.bookList.add(checkOutModel.bookList.get(i));
					}
				} catch(NullPointerException e1){}
				MainWindow main = new MainWindow(accountsModel, currentUser, bookModel);
				main.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
