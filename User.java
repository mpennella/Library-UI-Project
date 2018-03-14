package cu.cs.cpsc215.project2;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author mpennel
 */
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	/** ID number of account*/	 
	int ID;
	String username;
	String password;
	/** Account type (staff or non-staff) */
	boolean staff;
	String name;
	String email;
	String phone;
	/** List of books that the account has checked out */
	ArrayList<Book> checkoutList;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isStaff() {
		return staff;
	}
	public void setStaff(boolean staff) {
		this.staff = staff;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public ArrayList<Book> getCheckoutList(){
		return checkoutList;
	}
	public void setCheckoutList(ArrayList<Book> newList){
		checkoutList = newList;
	}
	public User(int iD, String username, String password, boolean staff,
			String name, String email, String phone) {
		super();
		ID = iD;
		this.username = username;
		this.password = password;
		this.staff = staff;
		this.name = name;
		this.email = email;
		this.phone = phone;
	}
	/**Constructs a class with blank fields and non-staff status to
	 * be modified
	 */
	public User() {
		ID = -1;
		username = "";
		password = "";
		staff = false;
		name = "";
		email = "";
		phone = "";
		checkoutList = new ArrayList<Book>();
	}
	/**
	 * Sets fields in a User object
	 * @param str object that will be placed into the user object in specified col
	 * @param col dictates field that str object will replace in the user
	 */
	public void set(Object str, int col) {
		switch(col){
		case 0: ID = (int) str;
				break;
		case 1: username = (String) str;
				break;
		case 2: name = (String) str;
				break;
		case 3: staff = (boolean) str;
				break;
		default: break;
		}
		
	}
	
}
