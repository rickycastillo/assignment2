package assignment1;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;

public class BookListViewController implements Initializable {
	private static Logger logger = LogManager.getLogger(driver.class);
	private String selected;
		
	@FXML
	ListView<String> BookList;
	
   
	@FXML
    void onItemClick() {
		selected = BookList.getSelectionModel().getSelectedItem(); // gets selected item
		logger.info(selected + " selected");
		ViewSwitcher.getInstance().switchView(2);
		
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logger.info("loaded book list view");
		populate();
	}
	
    public void populate() {
    	BookList.getItems().addAll("book1", "book2", "book3");
    	logger.info("successfully populated list");
    }
    
}
