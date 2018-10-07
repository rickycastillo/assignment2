package gateway;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import model.Book;

public class BookTableGateway {
	private Connection conn;
	
	public BookTableGateway(Connection conn) {
		this.conn = conn;
	}
	
	public List<Book> getBooks() throws SQLException {
		return null;
	}
	
}
