package cu.cs.cpsc215.project2;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * @author mpennel
 */
public class Main {

	@SuppressWarnings("unchecked")

	public static void main(String[] args) {
		AccountsTableModel accountsModel = new AccountsTableModel();
		BookTableModel bookModel = new BookTableModel();
		LoginScreen login = new LoginScreen(accountsModel, bookModel);
		login.setVisible(true);

		
		try(
				InputStream file = new FileInputStream("libraryusers.ser");
				InputStream buffer = new BufferedInputStream(file);
				ObjectInput input = new ObjectInputStream(buffer);
			){
				accountsModel.userList = (ArrayList<User>)input.readObject();
			} catch(IOException e){
				accountsModel.userList.set(0, new User(0, "admin", "", true, "admin", "", ""));
			} catch(ClassNotFoundException e2){
				System.out.println("User Class not found");
			} catch(IndexOutOfBoundsException e3){
				accountsModel.userList.set(0, new User(0, "admin", "", true, "admin", "", ""));
			}

		
		try(
				InputStream file = new FileInputStream("librarybooks.ser");
				InputStream buffer = new BufferedInputStream(file);
				ObjectInput input = new ObjectInputStream(buffer);
			){
				bookModel.bookList = (ArrayList<Book>)input.readObject();
			} catch(IOException e){
				//System.out.println("No user data detected.");
			} catch(ClassNotFoundException e2){
				//System.out.println("User Class not found");
			}
		
	}

}
