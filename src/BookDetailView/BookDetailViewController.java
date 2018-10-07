package BookDetailView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gateway.BookTableGateway;
import javafx.scene.control.TextField;
import main.driver;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;



public class BookDetailViewController implements Initializable {
		private static Logger logger = LogManager.getLogger(driver.class);
		
		static Connection conn;
		
	    BookTableGateway gateway = new BookTableGateway(conn);

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
	    	logger.info("clicked save button.. sending update");
	    	
	    	selectedBook.setTitle(title.getText());
	    	selectedBook.setSummary(summary.getText());
	    	selectedBook.setYearPublished(Integer.parseInt(year.getText()));
	    	selectedBook.setIsbn(isbn.getText());
	    	//selectedBook.setDateAdded(turnToDate(date.getText()));
	    	
	    	try {
				gateway.updateBook(selectedBook);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	logger.info("updated book!");
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

		public static void setTheConnection(Connection conn2) {
			// TODO Auto-generated method stub
			conn = conn2;
		}
		
		public LocalDateTime turnToDate(String date) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
			return dateTime;
		}

	}
