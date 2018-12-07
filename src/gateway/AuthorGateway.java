package gateway;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Author;
import model.Book;

public class AuthorGateway {
	
private Connection conn;
	
	public AuthorGateway(Connection conn) {
		this.conn = conn;
	}
	
	public void insertAuthor(Author author) throws SQLException {
		PreparedStatement st = conn.prepareStatement("insert into author "
				+ "(first_name, last_name, dob, gender, web_site) values (?, ?, ?, ?, ?) ", PreparedStatement.RETURN_GENERATED_KEYS);
		st.setString(1,  author.getFirstName());
		st.setString(2, author.getLastName());
		st.setDate(3, convertToDate(author.getDOB()));
		st.setString(4, author.getGender());
		st.setString(5,  author.getWebsite());
		st.executeUpdate();
		ResultSet newKeys = st.getGeneratedKeys();
		newKeys.next();

		author.setID(newKeys.getInt(1));
		
	}
	
	public void deleteAuthor(Author author) throws SQLException {
		
		//deletes the audit trail
		PreparedStatement st1 = conn.prepareStatement("delete from author_book where author_id = ?");
		st1.setInt(1, author.getID());
		st1.execute();
		
		PreparedStatement st2 = conn.prepareStatement("delete from author where id = ?");
		st2.setInt(1, author.getID());
		st2.execute();
	}
	
	public List<Author> getAllAuthors() throws SQLException{
		List<Author> authors = new ArrayList<Author>();
		PreparedStatement st = conn.prepareStatement("select * from author order by id");
		ResultSet rs = st.executeQuery();
		
		while(rs.next()) {
			Author author = new Author(this);
			author.setFirstName(rs.getString("first_name"));
			author.setLastName(rs.getString("last_name"));
			author.setID(rs.getInt("id"));
			author.setGender(rs.getString("gender"));
			author.setDOB(LocalDate.parse(rs.getString("dob")));
			author.setWebsite(rs.getString("web_site"));
			authors.add(author);
		}
		
		return authors;

	}
	
	public void updateAuthor(Author author) throws SQLException {
		PreparedStatement st = conn.prepareStatement("update author "
				+ " SET first_name = ?, last_name = ?, dob = ?, gender = ?, website = ? where id = ?");
		st.setString(1, author.getFirstName());
		st.setString(2, author.getLastName());
		st.setDate(3, convertToDate(author.getDOB()));
		st.setString(4, author.getGender());
		st.setString(5, author.getWebsite());
		st.setInt(6, author.getID());
		st.executeUpdate();
	}
	
	public Date convertToDate(LocalDate dateToConvert) {
	    return java.sql.Date.valueOf(dateToConvert);
	}

}
