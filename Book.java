package cu.cs.cpsc215.project2;
import java.io.Serializable;
import java.util.ArrayList;


@SuppressWarnings("serial")
public class Book implements Serializable{
	String title;
	String author;
	String genre;
	ArrayList<String> tags;
	boolean checkedOut;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public ArrayList<String> getTags() {
		return tags;
	}
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	public boolean isCheckedOut() {
		return checkedOut;
	}
	public void setCheckedOut(boolean checked){
		checkedOut = checked;
	}
	public Book(String title, String author, String genre,
			ArrayList<String> tags) {
		super();
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.tags = tags;
	}
	public void set(Object str, int col) {
		switch(col){
		case 0: title = (String) str;
				break;
		case 1: author = (String) str;
				break;
		case 2: genre = (String) str;
				break;
		case 3: checkedOut = (boolean) str;
				break;
		default: break;
		}
	}
	public Book(){
		title = "";
		author = "";
		genre = "";
		tags = new ArrayList<String>();
		checkedOut = false;
	}
}
