package model;

public class AuthorBook {
	Author author;
	Book book;
	int royalty;
	boolean newRecord;
	
	public AuthorBook() {
		author = null;
		book = null;
		royalty = 0;
		newRecord = true;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public void setRoyalty(int royalty) {
		this.royalty = royalty;
	}
	public void setRecordFalse() {
		this.newRecord = false;
	}
	public void setRecordTrue() {
		this.newRecord = true;
	}
	public Author getAuthor() {
		return author;
	}
	public Book getBook() {
		return book;
	}
	public int getRoyalty() {
		return royalty*100000;
	}
	public boolean getRecordStatus() {
		return newRecord;
	}
}
