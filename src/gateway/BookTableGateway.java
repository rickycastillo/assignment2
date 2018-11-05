package gateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.AuditTrailEntry;
import model.Book;

public class BookTableGateway {
	private Connection conn;
	
	public BookTableGateway(Connection conn) {
		this.conn = conn;
	}
	
	public LocalDateTime getLastModified(int id) throws SQLException{
		PreparedStatement st = conn.prepareStatement("select * from book where id = ?");
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		return turnToDate(rs.getString("last_modified"));
	}


	//Create portion of CRUD
	public int insertBook(Book book) throws SQLException {
		PreparedStatement st = conn.prepareStatement("insert into book "
				+ "(title, summary, year_published, isbn, publisher_id) values (?, ?, ?, ?, ?) ", PreparedStatement.RETURN_GENERATED_KEYS);
		st.setString(1,  book.getTitle());
		st.setString(2, book.getSummary());
		st.setInt(3, book.getYearPublished());
		st.setString(4, book.getIsbn());
		st.setInt(5,  book.getPublisherID());
		st.executeUpdate();
		ResultSet newKeys = st.getGeneratedKeys();
		newKeys.next();
		
		PreparedStatement st2 = conn.prepareStatement("insert into audit_trail "
				+ "(book_id, entry_msg) values (?, ?) ", PreparedStatement.RETURN_GENERATED_KEYS);
		
		int key = newKeys.getInt(1);
		 
		st2.setInt(1, key);
		
		st2.setString(2, "Book created");
		
		st2.executeUpdate();
		
		return key;
	}
	
	//Update portion of CRUD
	public void updateBook(Book newBook, Book oldBook) throws SQLException {
		PreparedStatement st = conn.prepareStatement("update book "
				+ " SET title = ?, summary = ?, year_published = ?, isbn = ?, publisher_id = ? where id = ?");
		st.setString(1, newBook.getTitle());
		st.setString(2, newBook.getSummary());
		st.setInt(3, newBook.getYearPublished());
		st.setString(4, newBook.getIsbn());
		st.setInt(5, newBook.getPublisherID());
		st.setInt(6, newBook.getId());
		st.executeUpdate();
		
		PreparedStatement st2;
		
		if (oldBook.getIsbn().equals(newBook.getIsbn()) == false) {
			st2 = conn.prepareStatement("insert into audit_trail "
					+ "(book_id, entry_msg) values (?, ?) ", PreparedStatement.RETURN_GENERATED_KEYS);
			st2.setInt(1, newBook.getId());
			st2.setString(2, "isbn changed from " + oldBook.getIsbn() + " to " + newBook.getIsbn());
			st2.executeUpdate();
		}
		
		if (oldBook.getTitle().equals(newBook.getTitle()) == false) {
			st2 = conn.prepareStatement("insert into audit_trail "
					+ "(book_id, entry_msg) values (?, ?) ", PreparedStatement.RETURN_GENERATED_KEYS);
			st2.setInt(1, newBook.getId());
			st2.setString(2, "title changed from " + oldBook.getTitle() + " to " + newBook.getTitle());
			st2.executeUpdate();
		}
		
		if (oldBook.getPublisher() != newBook.getPublisher()) {
			st2 = conn.prepareStatement("insert into audit_trail "
					+ "(book_id, entry_msg) values (?, ?) ", PreparedStatement.RETURN_GENERATED_KEYS);
			st2.setInt(1, newBook.getId());
			st2.setString(2, "Publisher changed from " + oldBook.getPublisher() + " to " + newBook.getPublisher());
			st2.executeUpdate();
		}
		
		if (oldBook.getYearPublished() != newBook.getYearPublished()) {
			st2 = conn.prepareStatement("insert into audit_trail "
					+ "(book_id, entry_msg) values (?, ?) ", PreparedStatement.RETURN_GENERATED_KEYS);
			st2.setInt(1, newBook.getId());
			st2.setString(2, "Year Published changed from " + oldBook.getYearPublished() + " to " + newBook.getYearPublished());
			st2.executeUpdate();
		}
		
		if (oldBook.getSummary().equals(newBook.getSummary()) ==  false) {
			st2 = conn.prepareStatement("insert into audit_trail "
					+ "(book_id, entry_msg) values (?, ?) ", PreparedStatement.RETURN_GENERATED_KEYS);
			st2.setInt(1, newBook.getId());
			st2.setString(2, "Summary changed from " + oldBook.getSummary() + " to " + newBook.getSummary());
			st2.executeUpdate();
		}
		
		
		
		
	}
	
	
	
	//Read portion of CRUD.
	public List<Book> getBooks() throws SQLException {
		
		
		List<Book> books = new ArrayList<Book>();
		//Our parametized query.
		//PreparedStatement st = conn.prepareStatement("select * from book order by id");
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from book order by id");
		while(rs.next()) {
			Book book = new Book(this);
			book.setId(rs.getInt("id"));
			book.setTitle(rs.getString("title"));
			book.setSummary(rs.getString("summary"));
			book.setYearPublished(rs.getInt("year_published"));
			book.setIsbn(rs.getString("isbn"));
			book.setDateAdded(turnToDate(rs.getString("date_added")));
			if(rs.getString("last_modified") == null) {
				book.setLastModified(null);
			}else {
				book.setLastModified(turnToDate(rs.getString("last_modified")));
			}
			book.setPublisherID(rs.getInt("publisher_id"));
			books.add(book);
		}
		
		rs.close();
		st.close();
		return books;
	}
	
	//Delete portion of CRUD.
	public void deleteBook(Book book) throws SQLException {
		PreparedStatement st1 = conn.prepareStatement("delete from audit_trail where book_id = ?");
		st1.setInt(1, book.getId());
		st1.execute();
		
		PreparedStatement st2 = conn.prepareStatement("delete from book where id = ?");
		st2.setInt(1, book.getId());
		st2.execute();
	}
	
	
	//Read portion of audit trail table
	public List<AuditTrailEntry> getAuditTrail(Book book) throws SQLException{
		List<AuditTrailEntry> audit_trails = new ArrayList<AuditTrailEntry>();
		PreparedStatement st = conn.prepareStatement("select * from audit_trail where book_id = ?");
		st.setInt(1, book.getId());
		ResultSet rs = st.executeQuery();
		
		while(rs.next()) {
			AuditTrailEntry entry = new AuditTrailEntry(this);
			entry.setId(rs.getInt("id"));
			entry.setEntryMsg(rs.getString("entry_msg"));
			audit_trails.add(entry);
		}
		return audit_trails;
	}
	public LocalDateTime turnToDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
		return dateTime;
	}
	
}
