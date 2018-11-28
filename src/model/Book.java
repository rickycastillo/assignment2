package model;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

import gateway.BookTableGateway;

public class Book {
	
	private int id;
	private String publisher;
	private String title;
	private String summary;
	private int yearPublished;
	private String isbn;
	private LocalDateTime dateAdded;
	private LocalDateTime lastModified;
	
	private BookTableGateway gateway;
	
	private int publisher_id;
	
	public Book() {
		id = 0;
		title = null;
		summary = null;
		yearPublished = 0;
		isbn = null;
		dateAdded = null;
		lastModified = null;
		publisher_id = 0;
		publisher = null;
	}
	
	public Book(int id, String title, String summary, int yearPublished, String isbn, LocalDateTime dateAdded, LocalDateTime lastModified, int publisher_id, String publisher) {
		this.id = id;
		this.title = title;
		this.summary = summary;
		this.yearPublished = yearPublished;
		this.isbn = isbn;
		this.dateAdded = dateAdded;
		this.lastModified = lastModified;
		this.publisher_id = publisher_id;
		this.publisher = publisher;
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
	
	public LocalDateTime getLastModified() {
		return lastModified;
	}
	
	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}
	
	public int getPublisherID() {
		return publisher_id;
	}
	
	public void setPublisherID(int publisher_id) {
		this.publisher_id = publisher_id;
	}
	
	public String getPublisher() {
		return publisher;
	}
	
	public void setPublisher(String publisher) {
		this.publisher = publisher;
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
	
	public List<AuditTrailEntry> getAuditTrails() throws SQLException{
		return gateway.getAuditTrail(this);
	}
	
	public List<AuthorBook> getAuthors(){
		return null;
	}
	
	

}
