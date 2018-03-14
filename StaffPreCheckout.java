package cu.cs.cpsc215.project2;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class StaffPreCheckout extends JFrame{
	public StaffPreCheckout(AccountsTableModel accountsModel, User currentUser, BookTableModel bookModel, CheckOutTableModel checkOutModel){
		super("Enter User ID to Proceed to Checkout");
		setSize(400,100);
		setLayout(new FlowLayout());
		
		JTextField idText= new JTextField("Enter User ID", 16);
		JButton cancelButton = new JButton("Cancel");
		JButton okButton = new JButton("Okay");
		
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		
		add(idText);
		add(buttonPanel);
		
		idText.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(idText.getText().equals("Enter User ID"))
					idText.setText("");
			}
		});
		
		
		
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				MainWindow mainWindow = new MainWindow(accountsModel, currentUser, bookModel);
				mainWindow.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int id = -1;
				try{
					id = Integer.parseInt(idText.getText());
				} catch (NumberFormatException e1){
					JOptionPane.showMessageDialog(new JFrame(), "Please enter a valid ID number");
				}
				
				try{
					int index = -1;
					for(int i = 0; i < accountsModel.userList.size(); i++){
						if(accountsModel.userList.get(i).getID() == id)
							index = i;
					}
					if(accountsModel.userList.get(index).isStaff() == false){
						User editedUser = new User();
						editedUser.setName(accountsModel.userList.get(index).getName());
						editedUser.setID(accountsModel.userList.get(index).getID());
						editedUser.setUsername(accountsModel.userList.get(index).getUsername());
						editedUser.setPassword(accountsModel.userList.get(index).getPassword());
						editedUser.setEmail(accountsModel.userList.get(index).getEmail());
						editedUser.setPhone(accountsModel.userList.get(index).getPhone());
						editedUser.setCheckoutList(accountsModel.userList.get(index).getCheckoutList());
						CheckoutWindow checkout= new CheckoutWindow(accountsModel, currentUser, editedUser, bookModel, checkOutModel);
						checkout.setVisible(true);
						setVisible(false);
						dispose();
					}
					else{
						JOptionPane.showMessageDialog(new JFrame(), "Please select a non-staff account");
					}
				} catch(IndexOutOfBoundsException e2){
					JOptionPane.showMessageDialog(new JFrame(), "Please enter a valid ID number");
				}
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
