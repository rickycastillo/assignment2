package gateway;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Book;
import model.Publisher;

public class PublisherTableGateway {
	
	private Connection conn;
	
	public PublisherTableGateway(Connection conn) {
		this.conn = conn;
	}
	
	public List<Publisher> getPublishers() throws SQLException {
		
		List<Publisher> publishers = new ArrayList<Publisher>();
		//Our parametized query.
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from publisher order by publisher_id");
		while(rs.next()) {
			Publisher publisher = new Publisher(this);
			publisher.setId(rs.getInt("publisher_id"));
			publisher.setTitle(rs.getString("title"));
			
			publishers.add(publisher);
			System.out.println(publisher.getId());
		}
		
		rs.close();
		st.close();
		return publishers;
	}

}
