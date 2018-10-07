package BookDetailView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.scene.control.TextField;
import main.driver;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;



public class BookDetailViewController implements Initializable {
		private static Logger logger = LogManager.getLogger(driver.class);
		
		private static model.Book selectedBook;
		
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
			title.setText(selectedBook.getTitle());
			summary.setText(selectedBook.getSummary());
			year.setText(String.valueOf(selectedBook.getYearPublished()));
			isbn.setText(selectedBook.getIsbn());
			date.setText(String.valueOf(selectedBook.getDateAdded()));

			logger.info("loaded book's detail");			
		}

		public static void setBook(model.Book book) {
			// set selected book
			selectedBook = book;
		}

	}
