package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import gateway.BookTableGateway;

public class Author {
	
	int id;
	String firstName;
	String lastName;
	LocalDateTime dateOfBirth;
	String gender;
	String webSite;
	
	private BookTableGateway gateway;

	
	public Author(BookTableGateway gateway) {
		this.gateway = gateway;
	}
	
	public Author() {
		id = 0;
		firstName = null;
		lastName = null;
		dateOfBirth = null;
		gender = null;
		webSite = null;
	}
	public void setID(int id) {
		this.id = id;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setDOB(LocalDateTime dob) {
		this.dateOfBirth = dob;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setWebsite(String webSite) {
		this.webSite = webSite;
	}
	public int getID() {
		return id;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public LocalDateTime getDOB() {
		return dateOfBirth;
	}
	public String getGender() {
		return gender;
	}
	public String getWebsite() {
		return webSite;
	}

}
