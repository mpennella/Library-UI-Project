package cu.cs.cpsc215.project2;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainWindow extends JFrame{
	public MainWindow(AccountsTableModel accountsModel, User currentUser, BookTableModel bookModel){
		super("Main");
		setLayout(new BorderLayout());
		setSize(400,400);
		
		JLabel displayUser = new JLabel("Current User: " + currentUser.getName());
		JButton viewCatalog = new JButton("View Catalog");
		JButton viewAccounts = new JButton("View Accounts");
		JButton beginCheckout = new JButton("Checkout");
		JButton logout = new JButton("Logout");
		CheckOutTableModel checkOutModel = new CheckOutTableModel();
		
		
		viewCatalog.setSize(200, 40);
		viewAccounts.setSize(200, 40);
		beginCheckout.setSize(200, 40);
		logout.setSize(200, 40);
		
		beginCheckout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(currentUser.isStaff() == false)
				{
					User editedUser = new User();
					editedUser.setName(currentUser.getName());
					editedUser.setID(currentUser.getID());
					editedUser.setUsername(currentUser.getUsername());
					editedUser.setPassword(currentUser.getPassword());
					editedUser.setEmail(currentUser.getEmail());
					editedUser.setPhone(currentUser.getPhone());
					editedUser.setCheckoutList(currentUser.getCheckoutList());
					CheckoutWindow checkout= new CheckoutWindow(accountsModel, currentUser, editedUser, bookModel, checkOutModel);
					checkout.setVisible(true);
				}
				else
				{
					StaffPreCheckout precheckout = new StaffPreCheckout(accountsModel, currentUser, bookModel, checkOutModel);
					precheckout.setVisible(true);
				}
				setVisible(false);
				dispose();
			}
		});
		
		viewAccounts.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				AccountsScreen accounts = new AccountsScreen(accountsModel, currentUser, bookModel);
				accounts.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		
		viewCatalog.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				CatalogWindow catalog = new CatalogWindow(accountsModel, currentUser, currentUser, bookModel, checkOutModel, false);
				catalog.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		
		
		logout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				LoginScreen login = new LoginScreen(accountsModel, bookModel);
				login.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		
		
		JPanel buttons = new JPanel(new GridLayout(2,2));
		buttons.add(viewCatalog);
		buttons.add(beginCheckout);
		buttons.add(viewAccounts);
		if(currentUser.staff == false)
			viewAccounts.setEnabled(false);
		buttons.add(logout);
		
		add(displayUser, BorderLayout.NORTH);
		add(buttons, BorderLayout.CENTER);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}