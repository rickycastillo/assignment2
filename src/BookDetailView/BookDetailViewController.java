package BookDetailView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gateway.BookTableGateway;
import gateway.PublisherTableGateway;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.driver;
import model.Publisher;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;



public class BookDetailViewController implements Initializable {
		private static Logger logger = LogManager.getLogger(driver.class);
		private static int saved = -1;
		private static Stage scene;
		static int dialogButton = JOptionPane.YES_NO_CANCEL_OPTION;
		static Connection conn;
		boolean newBook;
		private List<model.Book> books;
		List<Publisher> publishers;
		String publisher;
	    BookTableGateway gateway = new BookTableGateway(conn);
	    PublisherTableGateway publishergateway = new PublisherTableGateway(conn);

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
	    private ComboBox<String> comboBox;
	    
	    @FXML
	    void setPublisher() {
	    	publisher = comboBox.getValue();
	    	for(Publisher item: publishers) {
	    		if(item.getTitle().equals(publisher)) {
	    			selectedBook.setPublisher(item.getId());
	    		}
	    	}
	    }
	    @FXML
	    void clickSaveButton() {
	    	saved = 1;
			setOnCloseRequest();
	    	logger.info("clicked save button.. ");
	    //	setPublisher();
	    	selectedBook.setTitle(title.getText());
	    	selectedBook.setSummary(summary.getText());
	    	System.out.print(year.getText());
	    	if(year.getText() == null) {
	    		selectedBook.setYearPublished(0000);
	    	}
	    	else if(year.getText().length() < 1) {
	    		selectedBook.setYearPublished(0000);
	    	}
	    	else {
	    		selectedBook.setYearPublished(Integer.parseInt(year.getText()));
	    	}
	    	selectedBook.setIsbn(isbn.getText());
	    	
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
		    			newBook = false;
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
	    // if u read this ure gay
	    // like super gay u kno...
		
	    @Override
		public void initialize(URL location, ResourceBundle resources) {
			saved = 0;
			setOnCloseRequest();
			try {
				publishers = publishergateway.getPublishers();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(Publisher item : publishers) {
				comboBox.getItems().add(item.getTitle());
			}
			if(selectedBook == null) {
				newBook = true;
				title.setText(null);
				summary.setText(null);
				year.setText(null);
				isbn.setText(null);
				date.setText(null);
		    	comboBox.setValue(publishers.get(0).getTitle());
			} else {
				newBook = false;
				title.setText(selectedBook.getTitle());
				summary.setText(selectedBook.getSummary());
				year.setText(String.valueOf(selectedBook.getYearPublished()));
				isbn.setText(selectedBook.getIsbn());
				date.setText(String.valueOf(selectedBook.getDateAdded()));
				comboBox.setValue(publishers.get(selectedBook.getPublisher()).getTitle());
			}
			
			logger.info("loaded book's detail");			
		}

	private void setOnCloseRequest() {
		if(saved == 1) {
			scene.setOnCloseRequest(event -> {System.exit(0);});
		} else {
			scene.setOnCloseRequest(event -> {
				int dialogResult = JOptionPane.showConfirmDialog (null, "Save Changes?","Warning",dialogButton);
				if(dialogResult == JOptionPane.YES_OPTION){
					clickSaveButton();
				}
				if(dialogResult == JOptionPane.CANCEL_OPTION) {
					event.consume();
				}
		});
			
		}
	}
	public static void setBook(model.Book book) {
			// set selected book
			selectedBook = book;
		}
		
		public static void setBookNull() {
			selectedBook = null;
		}

		public static void setTheConnection(Connection conn2) {
			conn = conn2;
		}
		public static void setStage(Stage stage) {
			scene = stage;
		}
		
		public LocalDateTime turnToDate(String date) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
			return dateTime;
		}

		public static int checkSavedChanges() {
			if(saved == 0) {
				int dialogResult = JOptionPane.showConfirmDialog (null, "Save Changes?","Warning",dialogButton);
				if(dialogResult == JOptionPane.YES_OPTION){
					BookDetailViewController controller = new BookDetailViewController();
					controller.clickSaveButton();
					saved = -1;
					return -1;
				} else if(dialogResult == JOptionPane.CANCEL_OPTION){
					saved = -1;
					return 0;
				} else {
					saved = -1;
				}
			}
			return -1;
		
	}
}
