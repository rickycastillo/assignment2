package AuditTrail;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import main.driver;
import model.AuditTrailEntry;

public class AuditTrailController implements Initializable {
	
	private static Logger logger = LogManager.getLogger(driver.class);

	
	private static model.Book book;
	
	private List<model.AuditTrailEntry> auditTrail;
	
	private ObservableList<String> auditDisplay = FXCollections.observableArrayList();
	
    @FXML
    private Label labelTitle;

    @FXML
    private ListView<String> auditList;

    @FXML
    private Button backButton;

    @FXML
    void backButtonPress(ActionEvent event) {
    	logger.info("pressed back button");
    }
    
    public static void setBook(model.Book b) {
    	book = b;
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			auditTrail = book.getAuditTrails();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		populate();
	}
	void populate() {
		for(model.AuditTrailEntry item : auditTrail) {
			System.out.println(item.getEntryMsg());
				auditDisplay.add(item.getEntryMsg());
		}
		this.auditList.setItems(auditDisplay);
		
	}
}
