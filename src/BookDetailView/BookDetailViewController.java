package BookDetailView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gateway.BookTableGateway;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import main.driver;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;



public class BookDetailViewController implements Initializable {
		private static Logger logger = LogManager.getLogger(driver.class);
		
		static Connection conn;
		
		boolean newBook;
		
		private int id = 0;
		
		private List<model.Book> books;
		
	    BookTableGateway gateway = new BookTableGateway(conn);

		private static model.Book selectedBook;
		
		@FXML
	    private TextField title = new TextField();

	    @FXML
	    private TextField summary = new TextField();

	    @FXML
	    private TextField year = new TextField();

	    @FXML
	    private TextField isbn = new TextField();

	    @FXML
	    private TextField date = new TextField();

	    @FXML
	    void clickSaveButton() {
	    	logger.info("clicked save button.. sending update");
	    	
	    	selectedBook.setTitle(title.getText());
	    	selectedBook.setSummary(summary.getText());
	    	System.out.print(year.getText());
	    	if(year.getText().length() < 1) {
	    		selectedBook.setYearPublished(0000);
	    	} else {
	    		selectedBook.setYearPublished(Integer.parseInt(year.getText()));
	    	}
	    	selectedBook.setIsbn(isbn.getText());
	    	//selectedBook.setDateAdded(turnToDate(date.getText()));
	    	
	    	if (selectedBook.validate() == false) {
	    		Alert alert = new Alert(AlertType.INFORMATION);
	    		alert.setTitle("INPUT INCORRECT");
	    		alert.setHeaderText(null);
	    		alert.setContentText("Input incorrect. Please double check and try again!");
	    		alert.showAndWait();
	    	} else {
	    	
		    	try {
		    		if(newBook) {
		    			logger.info("This is a new book... determining new ID");
		    			selectedBook.setId(gateway.insertBook(selectedBook));
		    			logger.info("created book!");
		    		}
		    		else {
		    			gateway.updateBook(selectedBook);
		    			logger.info("updated book!");
		    		}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    }

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			if(selectedBook == null) {
				newBook = true;
				title.setText(null);
				summary.setText(null);
				year.setText(null);
				isbn.setText(null);
				date.setText(null);
			} else {
				newBook = false;
				title.setText(selectedBook.getTitle());
				summary.setText(selectedBook.getSummary());
				year.setText(String.valueOf(selectedBook.getYearPublished()));
				isbn.setText(selectedBook.getIsbn());
				date.setText(String.valueOf(selectedBook.getDateAdded()));
			}

			logger.info("loaded book's detail");			
		}

		public static void setBook(model.Book book) {
			// set selected book
			selectedBook = book;
		}
		
		public static void setBookNull() {
			selectedBook = null;
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
