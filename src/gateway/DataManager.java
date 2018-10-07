package gateway;

import java.sql.Connection;

public class DataManager {
	private static DataManager instance = null;
	
	private Connection connection;
	
	private DataManager() {}
	
	public Connection getConnection() {
		return connection;
	}
	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public static DataManager getInstance() {
		if(instance == null) 
			instance = new DataManager();
		
		return instance;
	}
}
