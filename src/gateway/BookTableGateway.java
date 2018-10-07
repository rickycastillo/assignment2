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
	//Updates our book.
	public void updateBook(Book book) throws SQLException {
		PreparedStatement st = conn.prepareStatement("update book "
				+ " set title = ?, set summary = ?, set year_published = ? set isbn = ? where id = ?");
		st.setString(1, book.getTitle());
		st.setString(2, book.getSummary());
		st.setInt(3, book.getYearPublished());
		st.setString(4, book.getIsbn());
		st.setInt(5, book.getId());
		
		st.executeUpdate();
	}
	
	
	
	public List<Book> getBooks() throws SQLException {
		
		
		List<Book> books = new ArrayList<Book>();
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
	
	public LocalDateTime turnToDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
		return dateTime;
	}
	
}
