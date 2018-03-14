package cu.cs.cpsc215.project2;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.EmptyStackException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class AccountScreen extends JFrame{
	public AccountScreen(AccountsTableModel accountsModel, User currentUser, User editedUser, BookTableModel bookModel){
		super("Account");
		setLayout(new GridLayout(7,1));
		setSize(300, 400);
		
		
		JPanel usernamePanel = new JPanel(new BorderLayout());
		JPanel passwordPanel = new JPanel(new BorderLayout());
		JPanel staffPanel = new JPanel(new BorderLayout());
		JPanel namePanel = new JPanel(new BorderLayout());
		JPanel emailPanel = new JPanel(new BorderLayout());
		JPanel phonePanel = new JPanel(new BorderLayout());
		JPanel buttonPanel = new JPanel(new FlowLayout());
		
		JLabel usernameLabel = new JLabel("Username");
		JLabel passwordLabel = new JLabel("Password");
		JLabel staffLabel = new JLabel("Account Type");
		JLabel nameLabel = new JLabel("Name");
		JLabel emailLabel = new JLabel("Email");
		JLabel phoneLabel = new JLabel("Phone Number");		
		
		
		JTextField usernameText = new JTextField(editedUser.getUsername(), 12);
		JTextField passwordText = new JTextField(editedUser.getPassword(), 12);
		JTextField staffText = new JTextField("Staff Member", 12);
		if(editedUser.isStaff() == false)
			staffText.setText("Standard Account");
		staffText.setEditable(false);
		JTextField nameText = new JTextField(editedUser.getName(), 12);
		JTextField emailText = new JTextField(editedUser.getEmail(), 12);
		JTextField phoneText = new JTextField(editedUser.getPhone(), 12);
		
		JButton staffChange= new JButton("+/-"); 
		staffChange.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(editedUser.isStaff() == true){
					editedUser.setStaff(false);
					staffText.setText("Standard Account");
				}
				else{
					editedUser.setStaff(true);
					staffText.setText("Staff Member");
				}	
			}
		});
		
		usernamePanel.add(usernameLabel, BorderLayout.WEST);
		usernamePanel.add(usernameText, BorderLayout.EAST);
		passwordPanel.add(passwordLabel, BorderLayout.WEST);
		passwordPanel.add(passwordText, BorderLayout.EAST);
		staffPanel.add(staffLabel, BorderLayout.WEST);
		staffPanel.add(staffChange, BorderLayout.CENTER);
		staffPanel.add(staffText, BorderLayout.EAST);
		namePanel.add(nameLabel, BorderLayout.WEST);
		namePanel.add(nameText, BorderLayout.EAST);
		emailPanel.add(emailLabel, BorderLayout.WEST);
		emailPanel.add(emailText, BorderLayout.EAST);
		phonePanel.add(phoneLabel, BorderLayout.WEST);
		phonePanel.add(phoneText, BorderLayout.EAST);
		
		JButton saveButton = new JButton("Save");
		JButton cancelButton = new JButton("Cancel");
		

		saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					boolean newuser = false;
					if(editedUser.getUsername() == "" && editedUser.getName() == "")
						newuser = true;
					int index = -1;
					if(newuser == false)
						index = accountsModel.userList.indexOf(editedUser);
					editedUser.setUsername(usernameText.getText());
					editedUser.setPassword(passwordText.getText());
					editedUser.setName(nameText.getText());
					if(emailText.getText().contains("@") && emailText.getText().contains("."))	
						editedUser.setEmail(emailText.getText());
					else
						throw new EmptyStackException();
					try{
						Double.parseDouble(phoneText.getText());
						if(phoneText.getText().length() != 10)
							throw new IOException();
						editedUser.setPhone(phoneText.getText());
					} catch(NumberFormatException e2){
						throw new IOException();
					} catch(IOException e3){
						throw new NumberFormatException();
					}
					if(newuser == true)
					{
						editedUser.setID(accountsModel.userList.get(accountsModel.userList.size() - 1).getID() + 1);
						accountsModel.addNewUser(editedUser);
					}
					else{
						try{
							accountsModel.userList.remove(index);
							accountsModel.userList.add(index, editedUser);
						} catch(ArrayIndexOutOfBoundsException e4){
							accountsModel.userList.add(index, editedUser);
						}
					}
					AccountsScreen accounts = new AccountsScreen(accountsModel, currentUser, bookModel);
					accounts.setVisible(true);
					setVisible(false);
					dispose();
				} catch(IOException e4){
					JOptionPane.showMessageDialog(new JFrame(), "You must enter a number in the phone field");
				} catch(EmptyStackException e5) {
					JOptionPane.showMessageDialog(new JFrame(), "You must enter a valid email in the email field");
				} catch(NumberFormatException e6){
					JOptionPane.showMessageDialog(new JFrame(), "Please enter a phone number of a valid length (10 digits)");
				}
				
			}
		});
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				AccountsScreen accounts = new AccountsScreen(accountsModel, currentUser, bookModel);
				accounts.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		
		
	
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		
		add(usernamePanel);
		add(passwordPanel);
		add(namePanel);
		add(staffPanel);
		add(emailPanel);
		add(phonePanel);
		add(buttonPanel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
