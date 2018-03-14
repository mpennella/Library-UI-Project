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
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class CatalogWindow extends JFrame{
	public CatalogWindow(AccountsTableModel accountsModel, User currentUser, User editedUser, BookTableModel bookModel, CheckOutTableModel checkOutModel, boolean checkout){
		super("Catalog");
		setLayout(new BorderLayout());
		setSize(626, 300);
		
		JPanel scrollTable = new JPanel(new BorderLayout());
		JPanel left = new JPanel(new GridLayout(7, 1));
		JTable table = new JTable(bookModel);
		table.getTableHeader().setReorderingAllowed(false);
		table.addMouseListener(new MouseAdapter(){
		     public void mouseClicked(MouseEvent e){
		         if (e.getClickCount() == 2){
		        	 int row = table.getSelectedRow();
		        	 if(editedUser.isStaff() == true)
		        	 {
		        		BookScreen addBook = new BookScreen(accountsModel, currentUser, editedUser, bookModel.bookList.get(row), bookModel, true, checkout, checkOutModel);
		        		addBook.setVisible(true);
						setVisible(false);
						dispose();
		        	 }
		        	 else
		        	 {
		        		BookScreen addBook = new BookScreen(accountsModel, currentUser, editedUser, bookModel.bookList.get(row), bookModel, false, checkout, checkOutModel);
						addBook.setVisible(true);
						setVisible(false);
						dispose();
		        	 }
		         }
		     }
		});

		JButton changeSearch = new JButton("Search Type: Literal");
		changeSearch.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					if(changeSearch.getText() == "Search Type: Literal"){
						changeSearch.setText("Search Type: Fuzzy");
					}
					else{
						changeSearch.setText("Search Type: Literal");
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(new JFrame("Error"), "Failed to change search type.");
				}
			}
		});
		

		JTextField searchBar = new JTextField("Search", 12);
		JButton searchButton = new JButton("Search");
		JButton resetButton = new JButton("Reset");
		JButton addBook = new JButton("Add Book");
		JButton removeBook = new JButton("Remove Book");
		JButton returnButton = new JButton("Return to Home Page");
		JButton selectButton = new JButton("Add to Cart");
		JButton checkoutReturn = new JButton("Return to Checkout");
		BookTableModel bookSearch = new BookTableModel();
		JTable searchTable = new JTable(bookSearch);		
		
		
		searchTable.addMouseListener(new MouseAdapter(){
		     public void mouseClicked(MouseEvent e){
		         if (e.getClickCount() == 2){
		        	 int row = searchTable.getSelectedRow();
		        	 if(editedUser.isStaff() == true)
		        	 {
		        		BookScreen addBook = new BookScreen(accountsModel, currentUser, editedUser, bookSearch.bookList.get(row), bookModel, true, checkout, checkOutModel);
		        		addBook.setVisible(true);
						setVisible(false);
						dispose();
		        	 }
		        	 else
		        	 {
		        		BookScreen addBook = new BookScreen(accountsModel, currentUser, editedUser, bookSearch.bookList.get(row), bookModel, false, checkout, checkOutModel);
						addBook.setVisible(true);
						setVisible(false);
						dispose();
		        	 }
		         }
		     }
		});
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		
		searchBar.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(searchBar.getText().equals("Search"))
					searchBar.setText("");
			}
		});
		
		addBook.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				BookScreen addBook = new BookScreen(accountsModel, currentUser, editedUser, new Book(), bookModel, true, checkout, checkOutModel);
				addBook.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		
		removeBook.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
				int selected = table.getSelectedRow();
				bookModel.removeRow(selected);
				} catch (ArrayIndexOutOfBoundsException e2) {
					JOptionPane.showMessageDialog(new JFrame("Error"), "You must select a Book in order to remove them.");
				}
			}
		});
		
		returnButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try(
						OutputStream file = new FileOutputStream("librarybooks.ser");
						OutputStream buffer = new BufferedOutputStream(file);
						OutputStream output = new ObjectOutputStream(buffer);
						){
					((ObjectOutputStream) output).writeObject(bookModel.bookList);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(new JFrame("Error"), "Error, failed to return save Catalog.");
				}
				
				MainWindow mainWindow = new MainWindow(accountsModel, currentUser, bookModel);
				mainWindow.setVisible(true);
				setVisible(false);
				dispose();
				}
		});

		selectButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int row = table.getSelectedRow();
				try{
					if(checkOutModel.bookList.contains(bookModel.bookList.get(row)))
						JOptionPane.showMessageDialog(new JFrame("Error"), "You have already added this book to your checkout list");
					else if(bookModel.bookList.get(row).isCheckedOut() == true)
					{
						JOptionPane.showMessageDialog(new JFrame("Error"), "This book is currently checked out");
					}
					else
					{
						if(scrollPane.getViewport().getView() == table) 
							checkOutModel.addNewBook(bookModel.bookList.get(row));
						else
						{	
							row = searchTable.getSelectedRow();
							checkOutModel.addNewBook(bookSearch.bookList.get(row));
						}
					}
				}catch (NullPointerException e1){
					checkOutModel.addNewBook(bookModel.bookList.get(row));
					JOptionPane.showMessageDialog(new JFrame("Error"), "Error");
				} catch(ArrayIndexOutOfBoundsException e2){
					try{
						int row2 = searchTable.getSelectedRow();
						if(checkOutModel.bookList.contains(bookSearch.bookList.get(row2)))
							JOptionPane.showMessageDialog(new JFrame("Error"), "You have already added this book to your checkout list");
						else if(bookSearch.bookList.get(row2).isCheckedOut() == true)
						{
							JOptionPane.showMessageDialog(new JFrame("Error"), "This book is currently checked out");
						}
						else
						{	
							checkOutModel.addNewBook(bookSearch.bookList.get(row2));
						}
					}catch(ArrayIndexOutOfBoundsException e3){
						JOptionPane.showMessageDialog(new JFrame("Error"), "You must select a book to add");
					}
				}
			}
		});
		
		checkoutReturn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				CheckoutWindow checkout = new CheckoutWindow(accountsModel, currentUser, editedUser, bookModel, checkOutModel);
				checkout.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		
		if(currentUser.isStaff() == false)
		{
			addBook.setEnabled(false);
			removeBook.setEnabled(false);
		}

		
		searchButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				bookSearch.bookList.clear();
				if(searchBar.getText().equals("Search") || searchBar.getText().equals(""))
				{
					scrollPane.setViewportView(table);
				}
				else if(changeSearch.getText() == "Search Type: Literal"){
					for(int i = 0; i < bookModel.bookList.size(); i++)
					{
						if(searchBar.getText().equals(bookModel.bookList.get(i).getTitle()) || searchBar.getText().equals(bookModel.bookList.get(i).getAuthor()) || 
								searchBar.getText().equals(bookModel.bookList.get(i).getGenre()) || bookModel.bookList.get(i).getTags().contains(searchBar.getText()))
							bookSearch.addNewBook(bookModel.bookList.get(i));
					}
				}else{
					for(int i = 0; i < bookModel.bookList.size(); i++)
					{
						boolean added = false;
						if(bookModel.bookList.get(i).getTitle().toLowerCase().contains(searchBar.getText().toLowerCase()) || bookModel.bookList.get(i).getAuthor().toLowerCase().contains(searchBar.getText().toLowerCase()))
						{
						bookSearch.addNewBook(bookModel.bookList.get(i));
						added = true;
						}
						else if (bookModel.bookList.get(i).getGenre().toLowerCase().contains(searchBar.getText().toLowerCase()))
						{
							bookSearch.addNewBook(bookModel.bookList.get(i));
							added = true;
						}
						int j = 0;
						while(added == false && j < bookModel.bookList.get(i).getTags().size())
						{
							if(bookModel.bookList.get(i).getTags().get(j).equals(searchBar.getText()))
								bookSearch.addNewBook(bookModel.bookList.get(i));
							j++;
						}
					}
				}
				
				scrollPane.setViewportView(searchTable);
			}	
		});
		
		resetButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				scrollPane.setViewportView(table);
			}
		});
		scrollTable.add(scrollPane, BorderLayout.CENTER);
		add(scrollTable, BorderLayout.EAST);
		
		

		left.add(searchBar);
		left.add(searchButton);
		left.add(changeSearch);
		left.add(resetButton);
		if(checkout == false){
			left.add(addBook);
			left.add(removeBook);
			left.add(returnButton);
		}
		else{
			left.add(selectButton);
			left.add(checkoutReturn);
		}
		add(left, BorderLayout.WEST);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
				try(
						OutputStream file = new FileOutputStream("librarybooks.ser");
						OutputStream buffer = new BufferedOutputStream(file);
						OutputStream output = new ObjectOutputStream(buffer);
						){
					((ObjectOutputStream) output).writeObject(bookModel.bookList);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(new JFrame("Error"), "Error, failed to save Catalog.");
				}
				System.exit(0);
			}
		});
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
}
