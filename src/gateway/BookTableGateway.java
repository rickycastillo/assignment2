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

import model.Book;

public class BookTableGateway {
	private Connection conn;
	
	public BookTableGateway(Connection conn) {
		this.conn = conn;
	}
	
	
	public BookTableGateway() {
	
	}


	//Create portion of CRUD
	public int insertBook(Book book) throws SQLException {
		PreparedStatement st = conn.prepareStatement("insert into book "
				+ "(id, title, summary, year_published, 'isbn') values ( ? ) ", PreparedStatement.RETURN_GENERATED_KEYS);
		st.setInt(1, book.getId());
		st.setString(2,  book.getTitle());
		st.setString(3, book.getSummary());
		st.setInt(4, book.getYearPublished());
		st.setString(5, book.getIsbn());
		st.executeUpdate();
		ResultSet newKeys = st.getGeneratedKeys();
		newKeys.next();
		return newKeys.getInt(1);
	}
	
	//Update portion of CRUD
	public void updateBook(Book book) throws SQLException {
		PreparedStatement st = conn.prepareStatement("update book "
				+ " set title = ? set summary = ? set year_published = ? set isbn = ? where id = ?");
		st.setString(1, book.getTitle());
		st.setString(2, book.getSummary());
		st.setInt(3, book.getYearPublished());
		st.setString(4, book.getIsbn());
		st.setInt(5, book.getId());
		
		st.executeUpdate();
	}
	
	
	
	//Read portion of CRUD.
	public List<model.Book> getBooks() throws SQLException {
		
		
		List<model.Book> books = new ArrayList<model.Book>();
		//Our parametized query.
		PreparedStatement st = conn.prepareStatement("select * from book order by id");
		st.executeQuery();
		
		ResultSet rs = st.executeQuery(null);
		while(rs.next()) {
			Book book = new Book(this);
			book.setId(rs.getInt("id"));
			book.setTitle(rs.getString("title"));
			book.setSummary(rs.getString("summary"));
			book.setYearPublished(rs.getInt("year_published"));
			book.setIsbn(rs.getString("isbn"));
			book.setDateAdded(turnToDate(rs.getString("date_added")));
			
			books.add(book);
		}
		
		rs.close();
		st.close();
		return books;
	}
	
	//Delete portion of CRUD.
	public void deleteBook(Book book) throws SQLException {
		PreparedStatement st = conn.prepareStatement("delete from book where id = ?");
		st.setInt(1, book.getId());
		st.executeQuery();
	}
	
	
	public LocalDateTime turnToDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
		return dateTime;
	}
	
}
