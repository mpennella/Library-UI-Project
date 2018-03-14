package cu.cs.cpsc215.project2;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class BookScreen extends JFrame{
	public BookScreen(AccountsTableModel accountsModel, User currentUser, User editedUser, Book editedBook, BookTableModel bookModel, boolean editable, boolean checkout, CheckOutTableModel checkOutModel){
		super("Edit Book");
		setLayout(new GridLayout(5,1));
		setSize(400, 400);
		
		JPanel titlePanel = new JPanel(new BorderLayout());
		JPanel authorPanel = new JPanel(new BorderLayout());
		JPanel genrePanel = new JPanel(new BorderLayout());
		JPanel tagPanel = new JPanel(new BorderLayout());
		JPanel buttonPanel = new JPanel(new FlowLayout());
		
		JLabel titleLabel = new JLabel("Title");
		JLabel authorLabel = new JLabel("Author");
		JLabel genreLabel = new JLabel("Genre");
		JLabel tagLabel = new JLabel("Tags");
		
		JTextField titleField = new JTextField(editedBook.getTitle(), 12);
		JTextField authorField = new JTextField(editedBook.getAuthor(), 12);
		JTextField genreField = new JTextField(editedBook.getGenre(), 12);
		JTextField tagField = new JTextField(editedBook.getTags().toString(), 12);
		
		JButton saveButton = new JButton("Save");
		JButton cancelButton = new JButton("Cancel");
		
		if(currentUser.isStaff() == false)
		{
			titleField.setEditable(false);
			authorField.setEditable(false);
			genreField.setEditable(false);
			tagField.setEditable(false);			
			saveButton.setEnabled(false);
		}
		
		saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				boolean newuser = false;
				if(editedBook.getTitle() == "" && editedBook.getAuthor() == "")
					newuser = true;
				int index = -1;
				if(newuser == false)
					index = bookModel.bookList.indexOf(editedBook);
				editedBook.setTitle(titleField.getText());
				editedBook.setAuthor(authorField.getText());
				editedBook.setGenre(genreField.getText());
				ArrayList<String> tagList = new ArrayList<String>(Arrays.asList(tagField.getText().split(" ")));
				editedBook.setTags(tagList);

				
				if(newuser == true)
					bookModel.addNewBook(editedBook);
				else{
					bookModel.bookList.remove(index);
					bookModel.bookList.add(index, editedBook);
				}
				CatalogWindow catalog = new CatalogWindow(accountsModel, currentUser, editedUser, bookModel,  checkOutModel, checkout);
				catalog.setVisible(true);
				setVisible(false);
				dispose();
				
			}
		});
		
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				CatalogWindow catalog = new CatalogWindow(accountsModel, currentUser, editedUser, bookModel,  checkOutModel, checkout);
				catalog.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		if(editable == false)
		{
			titleField.setEditable(false);
			authorField.setEditable(false);
			genreField.setEditable(false);
			tagField.setEditable(false);
			saveButton.setEnabled(false);
		}
		
		titlePanel.add(titleLabel, BorderLayout.WEST);
		titlePanel.add(titleField, BorderLayout.EAST);
		authorPanel.add(authorLabel, BorderLayout.WEST);
		authorPanel.add(authorField, BorderLayout.EAST);
		genrePanel.add(genreLabel, BorderLayout.WEST);
		genrePanel.add(genreField, BorderLayout.EAST);
		tagPanel.add(tagLabel, BorderLayout.WEST);
		tagPanel.add(tagField, BorderLayout.EAST);
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		
		add(titlePanel);
		add(authorPanel);
		add(genrePanel);
		add(tagPanel);
		add(buttonPanel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
