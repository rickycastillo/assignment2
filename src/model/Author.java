package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import gateway.AuthorGateway;
import gateway.BookTableGateway;

public class Author {
	
	int id;
	String firstName;
	String lastName;
	LocalDate dateOfBirth;
	String gender;
	String webSite;
	
	private BookTableGateway gateway;
	private AuthorGateway a_gateway;
	
	public Author(BookTableGateway gateway) {
		this.gateway = gateway;
	}
	
	public Author(AuthorGateway gateway) {
		this.a_gateway = gateway;
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
	public void setDOB(LocalDate dob) {
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
	public LocalDate getDOB() {
		return dateOfBirth;
	}
	public String getGender() {
		return gender;
	}
	public String getWebsite() {
		return webSite;
	}

}
