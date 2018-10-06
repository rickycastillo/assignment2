package BookListView;
import java.awt.print.Book;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	
	private ObservableList<Book> BooksDisplay;
	
    private List<Book> books;

	
   
//	@FXML
//    void onItemClick() {
//		selected = BookList.getSelectionModel().getSelectedItem(); // gets selected item
//		logger.info(selected + " selected");
//		ViewSwitcher.getInstance().switchView(2);
//		
//    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logger.info("loaded book list view");
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
    
}
