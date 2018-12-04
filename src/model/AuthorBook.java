package model;

import gateway.BookTableGateway;

public class AuthorBook {
	Author author;
	Book book;
	float royalty;
	boolean newRecord;
	
	private BookTableGateway gateway;
	
	public AuthorBook(BookTableGateway gateway) {
		this.gateway = gateway;
	}
	
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
	public void setRoyalty(float royalty) {
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
	public float getRoyalty() {
		return royalty*100000;
	}
	public boolean getRecordStatus() {
		return newRecord;
	}

	public String getPrintStatement() {
		return author.getFirstName() + " " + author.getLastName() + "     " + royalty;
	}
}
