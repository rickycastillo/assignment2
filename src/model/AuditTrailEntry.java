package model;

import java.time.LocalDateTime;

import gateway.BookTableGateway;

public class AuditTrailEntry {
	
	private int id;
	private String entry_msg;
	
	private BookTableGateway gateway;
	private LocalDateTime dateAdded;
	
	public AuditTrailEntry() {
		id = 0;
		entry_msg = null;
	}
	
	public AuditTrailEntry(int id, String entry_msg) {
		this.id = id;
		this.entry_msg = entry_msg;
	}
	
	public AuditTrailEntry(BookTableGateway gateway) {
		this.gateway = gateway;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setEntryMsg(String entry_msg) {
		this.entry_msg = entry_msg;
	}
	
	public String getEntryMsg() {
		return entry_msg;
	}

	public void setDateAdded(LocalDateTime dateAdded) {
		this.dateAdded = dateAdded;
		
	}
	
	public LocalDateTime getDateAdded() {
		return dateAdded;
	}
	
}
