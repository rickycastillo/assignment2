package BookDetailView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mysql.cj.protocol.Protocol.GetProfilerEventHandlerInstanceFunction;

import AuditTrail.AuditTrailController;
import gateway.AuthorGateway;
import gateway.BookTableGateway;
import gateway.PublisherTableGateway;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.ViewSwitcher;
import main.driver;
import model.Author;
import model.AuthorBook;
import model.Publisher;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

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
		List<AuthorBook> authors;
		private ObservableList<String> authorsDisplay = FXCollections.observableArrayList();
		String publisher;
	    BookTableGateway gateway = new BookTableGateway(conn);
	    PublisherTableGateway publishergateway = new PublisherTableGateway(conn);
	    AuthorGateway authorgateway = new AuthorGateway(conn);
	    LocalDateTime lastModified;
	    int alreadyModified;

		private static model.Book selectedBook;
		private static model.Book oldBook;
		
		private String selected;
		
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
	    private ListView<String> authorList;

	    @FXML
	    private Button deleteAuthor;

	    @FXML
	    private Button addAuthor;
	    
	    @FXML
	    private Button changeRoyalty;

	    
	    @FXML
	    void loadAuditTrail() {
	    	if(!newBook) {
	    	AuditTrailController.setBook(selectedBook);
	    	ViewSwitcher.getInstance().switchView(3);
	    	}
	    }
	    
	    @FXML
	    void changeRoyalty() {
	    	JTextField royalty = new JTextField();
	    	Object[] input = {
	    			"New Royalty: ", royalty
	    	};
	    	selected = authorList.getSelectionModel().getSelectedItem();
	    	System.out.print(selected);
	    	String[] tokens = selected.split("\t");
	    	List<AuthorBook> authors_book = null;
	    	int option = JOptionPane.showConfirmDialog(null, input, "Change Royalty", JOptionPane.OK_CANCEL_OPTION);
	    	if(option == JOptionPane.OK_OPTION) {
				try {
					logger.info("Getting Authors for Book...");
					authors_book = gateway.getAuthorsForBook(selectedBook);
					System.out.print(authors_book);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	for(AuthorBook item: authors_book) {
		    		System.out.printf("Printing Comparison: %s %s %s %s", tokens[0], tokens[1], item.getAuthor().getFirstName(), item.getAuthor().getLastName());
		    		if(item.getAuthor().getFirstName().equals(tokens[0]) && item.getAuthor().getLastName().equals(tokens[1])){
		    			logger.info("Changed royalty");
		    			item.setRoyalty(Float.parseFloat(royalty.getText()));
		    			System.out.print(item.getRoyalty());
		    		}
		    	}
	    	}
	    }
	    
	    @FXML
	    void clickedAddAuthor() {
    		Author author = new Author();
	    	JTextField firstName = new JTextField();
	    	JTextField lastName = new JTextField();
	    	JTextField dob = new JTextField();
	    	JTextField gender = new JTextField();
	    	JTextField weBsite = new JTextField();
	    	JTextField royalty = new JTextField();
	    	Float royaltyValue = null;

	    	Object[] input = {
	    			"First Name: ", firstName,
	    			"Last Name: ", lastName,
	    			"Date of Birth: ", dob,
	    			"Gender: ", gender,
	    			"Website: ", weBsite,
	    			"Royalty: ", royalty,
	    	};
	    	
	    	int option = JOptionPane.showConfirmDialog(null, input, "Add Author", JOptionPane.OK_CANCEL_OPTION);
	    	if(option == JOptionPane.OK_OPTION) {
	    		author.setFirstName(firstName.getText());
	    		author.setLastName(lastName.getText());
	    		author.setDOB(turnToDOB(dob.getText()));
	    		author.setGender(gender.getText());
	    		author.setWebsite(weBsite.getText());
	    		royaltyValue = Float.parseFloat(royalty.getText());
	    		try {
					authorgateway.insertAuthor(author);
					gateway.addAuthorToBook(author, selectedBook, royaltyValue);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	try {
				authors = gateway.getAuthorsForBook(selectedBook);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	authorsDisplay.clear();
			if(authors != null) {
				System.out.print("enter printing authors...");
				System.out.print(authors);
				for(AuthorBook item : authors) {
					authorsDisplay.add(item.getPrintStatement());
				}
				this.authorList.setItems(authorsDisplay);
			}
	    	
	    }

	    @FXML
	    void clickedAuthorList() {
	    	
	    }

	    @FXML
	    void clickedDeleteAuthor() {
	    	selected = authorList.getSelectionModel().getSelectedItem();
	    	System.out.print(selected);
	    	String[] tokens = selected.split("\t");
	    	List<Author> authors_list = null;
			try {
				authors_list = gateway.getAllAuthors();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	for(Author item: authors_list) {
	    		if(item.getFirstName().equals(tokens[0]) && item.getLastName().equals(tokens[1])){
	    			try {
						authorgateway.deleteAuthor(item);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		}
	    	}
	    	try {
				authors = gateway.getAuthorsForBook(selectedBook);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	authorsDisplay.clear();
			if(authors != null) {
				System.out.print("enter printing authors...");
				System.out.print(authors);
				for(AuthorBook item : authors) {
					authorsDisplay.add(item.getPrintStatement());
				}
				this.authorList.setItems(authorsDisplay);
			}
	    }
	    
	    @FXML
	    void setPublisher() {
	    	publisher = comboBox.getValue();
	    	for(Publisher item: publishers) {
	    		if(item.getTitle().equals(publisher) && saved == 1) {
	    			selectedBook.setPublisherID(item.getId());
	    			selectedBook.setPublisher(item.getTitle());
	    		}
	    	}
	    }
	    @FXML
	    void clickSaveButton() {
	    	if(!newBook) checkLastModified();
	    	if(alreadyModified == 1) return;
	    	saved = 1;
			setOnCloseRequest();
	    	logger.info("clicked save button.. ");
	    	setPublisher();
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
		    			gateway.updateBook(selectedBook, oldBook);
		    			logger.info("updated book!");
		    		}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    }
	    private void checkLastModified() {
	    	if(selectedBook.getLastModified() == null) return;
	    	
	    	try {
				lastModified = gateway.getLastModified(selectedBook.getId());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	System.out.println(lastModified);
	    	System.out.println(selectedBook.getLastModified());

			if(lastModified.compareTo(selectedBook.getLastModified()) != 0) {
				alreadyModified = 1;
				logger.info("dates don't match");
				Alert alert = new Alert(AlertType.INFORMATION);
	    		alert.setTitle("ERROR");
	    		alert.setHeaderText(null);
	    		alert.setContentText("This book has been modified while you were trying to do so too. SLOW POKE!");
	    		alert.showAndWait();
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
		    	lastModified = null;
			} else {
				try {
					authors = gateway.getAuthorsForBook(selectedBook);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(authors != null) {
					System.out.print("enter printing authors...");
					System.out.print(authors);
					for(AuthorBook item : authors) {
						authorsDisplay.add(item.getPrintStatement());
					}
					this.authorList.setItems(authorsDisplay);
				}
				newBook = false;
				title.setText(selectedBook.getTitle());
				summary.setText(selectedBook.getSummary());
				year.setText(String.valueOf(selectedBook.getYearPublished()));
				isbn.setText(selectedBook.getIsbn());
				date.setText(String.valueOf(selectedBook.getDateAdded()));
				comboBox.setValue(publishers.get(selectedBook.getPublisherID()-1).getTitle());
				
				oldBook = new model.Book();
				oldBook.setId(selectedBook.getId());
				oldBook.setTitle(selectedBook.getTitle());
				oldBook.setSummary(selectedBook.getSummary());
				oldBook.setYearPublished(selectedBook.getYearPublished());
				oldBook.setIsbn(selectedBook.getIsbn());
				oldBook.setDateAdded(selectedBook.getDateAdded());
				oldBook.setPublisherID(selectedBook.getPublisherID());
				oldBook.setPublisher(publishers.get(selectedBook.getPublisherID()-1).getTitle());
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
		public LocalDate turnToDOB(String date) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate dateTime = LocalDate.parse(date, formatter);
			return dateTime;
		}

		public static int checkSavedChanges() {
			if(saved == 0) {
				int dialogResult = JOptionPane.showConfirmDialog (null, "Save Changes?","Warning",dialogButton);
				if(dialogResult == JOptionPane.YES_OPTION){
					//c.clickSaveButton();
					saved = -1;
					return -1;
				} else if(dialogResult == JOptionPane.CANCEL_OPTION){
					saved = 0;
					return 0;
				} else {
					saved = -1;
				}
			}
			return -1;
		
	}
	
}
