package assignment2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;



public class BookDetailViewController implements Initializable {
		private static Logger logger = LogManager.getLogger(driver.class);
		
		@FXML
	    private TextField title;

	    @FXML
	    private TextField summary;

	    @FXML
	    private TextField year;

	    @FXML
	    private TextField isbn;

	    @FXML
	    private TextField date;

	    @FXML
	    void clickSaveButton() {
	    	logger.info("clicked save button");
	    }

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			logger.info("loaded book's detail");
			title = summary = year = isbn = date = null;
			
		}

		public static void setBook(String selected) {
			// set selected book
			
		}

	}
