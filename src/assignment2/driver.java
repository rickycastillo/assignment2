// CS 4743 Assignment 2 by Ricardo Castillo and Jaynan San Jose

package assignment2;

import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class driver extends Application{
	
	private static Logger logger = LogManager.getLogger(driver.class);
	
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

