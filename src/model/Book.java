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
		id = 0;
		title = null;
		summary = null;
		yearPublished = 0;
		isbn = null;
		dateAdded = null;
	}
	
	public Book(int id, String title, String summary, int yearPublished, String isbn, LocalDateTime dateAdded) {
		this.id = id;
		this.title = title;
		this.summary = summary;
		this.yearPublished = yearPublished;
		this.isbn = isbn;
		this.dateAdded = dateAdded;
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
			if(((title == null || title.length() < 1 || title.length() > 255)) ||
				(summary == null || summary.length() < 1 || summary.length() > 65536) ||
				(yearPublished < 1 || yearPublished > Calendar.getInstance().get(Calendar.YEAR)) ||
				(isbn == null || isbn.length() < 1 || isbn.length() > 13)) {
			return false;
		}
		
		return true;
	}
	
	

}
