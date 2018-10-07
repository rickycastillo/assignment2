// CS 4743 Assignment 2 by Ricardo Castillo and Jaynan San Jose

package main;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mysql.cj.jdbc.MysqlDataSource;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class driver extends Application{
	
	private static Logger logger = LogManager.getLogger(driver.class);
	
	@Override
	public void init() throws Exception {
		super.init();
		try {
			MysqlDataSource ds = new MysqlDataSource();
			ds.setURL("jdbc:mysql://easel2.fulgentcorp.com:3306/jxt528?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
			ds.setUser("jxt528");
			ds.setPassword("KF9U8nczdL7pcxuqJWC5");
			logger.info("Sucess!");
			Connection conn = ds.getConnection();
			 //DataManager.getInstance().setConnection(conn);
			
			logger.info("connection created");
			
		}catch(SQLException e) {
			e.printStackTrace();
			logger.fatal("connectioon cant be made");
			Platform.exit();
		}
	}
	
	@Override
	public void stop() throws Exception {
		super.stop();
		
		//close the connection
//		try {
//			logger.info("connection closed.");
//		}catch (SQLException e) {
//			e.printStackTrace();
//		}
	}
	
	public static void main(String args[]) {
		logger.info("program initialized");
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		URL viewURL = this.getClass().getResource("MenuView.fxml");
		FXMLLoader loader = new FXMLLoader(viewURL);
		loader.setController(new MenuViewController());
		BorderPane rootPane = loader.load();
		ViewSwitcher.getInstance().setPane(rootPane);
		Scene scene = new Scene(rootPane);
		stage.setTitle("Assignment 1");
		stage.setScene(scene);
		stage.show();
	}
	
	
}
