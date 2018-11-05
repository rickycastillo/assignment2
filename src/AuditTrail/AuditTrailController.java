package AuditTrail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import main.driver;

public class AuditTrailController {
	
	private static Logger logger = LogManager.getLogger(driver.class);

	
	private static model.Book book;
	
    @FXML
    private Label labelTitle;

    @FXML
    private ListView<?> BookList;

    @FXML
    private Button backButton;

    @FXML
    void backButtonPress(ActionEvent event) {
    	logger.info("pressed back button");
    }
    
    public static void setBook(model.Book b) {
    	book = b;
    }
}
