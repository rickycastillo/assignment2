package model;

import java.time.LocalDateTime;
import java.util.Calendar;

import gateway.BookTableGateway;

public class Book {
	
	private int id;
	private String title;
	private String summary;
	private int yearPublished;
	private String isbn;
	private LocalDateTime dateAdded;
	
	private BookTableGateway gateway;
	
	public Book() {
		super();
	}
	
	public Book(BookTableGateway gateway) {
		this.gateway = gateway;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public int getYearPublished() {
		return yearPublished;
	}

	public void setYearPublished(int yearPublished) {
		this.yearPublished = yearPublished;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public LocalDateTime getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(LocalDateTime dateAdded) {
		this.dateAdded = dateAdded;
	}
	
	public boolean validate() {
			if((title.length() < 1 && title.length() > 255) ||
				summary.length() > 65536 ||
				yearPublished > Calendar.getInstance().get(Calendar.YEAR) ||
				isbn.length() > 13) {
			return false;
		}
		
		return true;
	}
	
	

}
