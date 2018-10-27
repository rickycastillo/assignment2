package model;

import gateway.PublisherTableGateway;

public class Publisher {

	private int id;
	private String title;
	
	private PublisherTableGateway gateway;
	
	public Publisher() {
		id = 0;
		title = null;
	}
	
	public Publisher(int id, String title) {
		this.id = id;
		this.title = title;
	}
	
	public Publisher(PublisherTableGateway gateway) {
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
	
}
