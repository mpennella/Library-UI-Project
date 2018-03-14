package cu.cs.cpsc215.project2;
import java.awt. *;
import java.awt.event. *;

import javax.swing. *;

@SuppressWarnings("serial")
public class LoginScreen extends JFrame{
	public LoginScreen(AccountsTableModel accountsModel, BookTableModel bookModel){
		super("Login");
		setLayout(new BorderLayout());		
		setSize(370,100);
		try{
			accountsModel.userList.set(0, new User(0, "admin", "", true, "admin", "", ""));
		} catch(IndexOutOfBoundsException e1){
			accountsModel.userList.add(0, new User(0, "admin", "", true, "admin", "", ""));
		}
		
		
		JTextField usernameEntry = new JTextField("Username", 11);
		JTextField passwordEntry = new JTextField("Password", 12);

		JButton loginButton = new JButton("Login");
		JButton cancelButton = new JButton("Cancel");
		
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		
		JPanel textPanel = new JPanel(new FlowLayout());
		JPanel buttonPanel = new JPanel(new FlowLayout());
		
		buttonPanel.add(loginButton);
		buttonPanel.add(cancelButton);
		textPanel.add(usernameEntry);
		textPanel.add(passwordEntry);
		
		
		add(textPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		
		usernameEntry.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(usernameEntry.getText().equals("Username"))
					usernameEntry.setText("");
			}
		});
		
		passwordEntry.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(passwordEntry.getText().equals("Password"))
					passwordEntry.setText("");
			}
		});
		
		loginButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					int index = -1;
					for(int i = 0; i < accountsModel.userList.size(); i++)
					{
						if(accountsModel.userList.get(i).getUsername().equals(usernameEntry.getText()))
						{
							index = i;
							break;
						}
					}
					if(accountsModel.userList.get(index).getPassword().equals(passwordEntry.getText()))
					{
						MainWindow mainWindow = new MainWindow(accountsModel, accountsModel.userList.get(index), bookModel);
						mainWindow.setVisible(true);
						setVisible(false);
						dispose();
					}
					else
					{
						JFrame frame = new JFrame("Error");
						JOptionPane.showMessageDialog(frame, "Invalid username or password");
					}
				}catch(Exception e2){
					JFrame frame = new JFrame("Error");
					JOptionPane.showMessageDialog(frame, "Invalid username or password");
				}
			}
		});
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
