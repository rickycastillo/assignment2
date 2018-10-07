package BookListView;
import java.awt.print.Book;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gateway.BookTableGateway;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import main.ViewSwitcher;
import main.driver;


public class BookListViewController implements Initializable {
	private static Logger logger = LogManager.getLogger(driver.class);
//	private String selected;
		
	@FXML private ListView<String> BookList;
	
	private ObservableList<model.Book> BooksDisplay;
	
    private List<model.Book> books;
    
    static Connection conn;
       
    BookTableGateway gateway = new BookTableGateway(conn);
   
//	@FXML
//    void onItemClick() {
//		selected = BookList.getSelectionModel().getSelectedItem(); // gets selected item
//		logger.info(selected + " selected");
//		ViewSwitcher.getInstance().switchView(2);
//		
//    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logger.info("initializing book list");
		try {
			books = gateway.getBooks();
		} catch (SQLException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("loaded book list view");
		if(books != null)
			populate();

	
		//Once the list gets double clicked, update the list.
		BookList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent click) {
				if(click.getClickCount() == 2) {
					if(BookList.getSelectionModel().getSelectedItem() != null) {
						logger.info("You clicked this: " + BookList.getSelectionModel().getSelectedItem());
						ViewSwitcher.getInstance().switchView(2);
					}else {
						logger.info("You clicked on nothing!");
					}
				}
			}
		});
	}
	
    public void populate() {
		this.BooksDisplay = FXCollections.observableArrayList(books);
		//this.BookList.setItems(BooksDisplay);
		logger.info("successfully populated list");
    	
    }

	public static void setTheConnection(Connection conn2) {
		conn = conn2;
		
	}
    
}
