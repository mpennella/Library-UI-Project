package cu.cs.cpsc215.project2;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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


public class AccountsScreen extends JFrame{

	private static final long serialVersionUID = 1L;

	@SuppressWarnings({ "deprecation", "static-access" })
	public AccountsScreen(AccountsTableModel accountsModel, User currentUser, BookTableModel bookModel){
		super("Accounts");
		setLayout(new BorderLayout());
		setSize(626, 300);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(3, 1));
		JPanel scrollTable = new JPanel(new BorderLayout());
		
		
		JTable table = new JTable(accountsModel);
		table.getTableHeader().setReorderingAllowed(false);
		table.addMouseListener(new MouseAdapter(){
		     public void mouseClicked(MouseEvent e){
		         if (e.getClickCount() == 2){
		        	 int row = table.getSelectedRow();
		        	 AccountScreen addAccount = new AccountScreen(accountsModel, currentUser, accountsModel.userList.get(row), bookModel);
						addAccount.setVisible(true);
						setVisible(false);
						dispose();
		         }
		     }
		});
		
		JButton addAccount = new JButton("Add Account");
		JButton removeAccount = new JButton("Remove Account");
		JButton returnButton = new JButton("Return to Home Page");
		
		addAccount.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				AccountScreen addAccount = new AccountScreen(accountsModel, currentUser, new User(), bookModel);
				addAccount.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		
		removeAccount.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
				int selected = table.getSelectedRow();
				accountsModel.removeRow(selected);
				} catch (ArrayIndexOutOfBoundsException e2) {
					JOptionPane.showMessageDialog(new JFrame("Error"), "You must select a User in order to remove them.");
				}
			}
		});
		
		returnButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try(
						OutputStream file = new FileOutputStream("libraryusers.ser");
						OutputStream buffer = new BufferedOutputStream(file);
						OutputStream output = new ObjectOutputStream(buffer);
						){
					((ObjectOutputStream) output).writeObject(accountsModel.userList);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(new JFrame("Error"), "Error, failed to save new accounts.");
				}
				
				MainWindow mainWindow = new MainWindow(accountsModel, currentUser, bookModel);
				mainWindow.setVisible(true);
				setVisible(false);
				dispose();
				}
		});
		
		
		
		buttons.add(addAccount);
		buttons.add(removeAccount);
		buttons.add(returnButton);
		add(buttons, BorderLayout.WEST);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane = table.createScrollPaneForTable(table);
		scrollTable.add(scrollPane);
		add(scrollTable, BorderLayout.EAST);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
				try(
						OutputStream file = new FileOutputStream("libraryusers.ser");
						OutputStream buffer = new BufferedOutputStream(file);
						OutputStream output = new ObjectOutputStream(buffer);
						){
					((ObjectOutputStream) output).writeObject(accountsModel.userList);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(new JFrame("Error"), "Error, failed to save new accounts.");
				}
				System.exit(0);
			}
		});
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}