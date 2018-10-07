package main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import BookDetailView.BookDetailViewController;
import BookListView.BookListViewController;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class ViewSwitcher {
	private static Logger logger = LogManager.getLogger(driver.class);
	private static ViewSwitcher switcher = null;
	private BorderPane pane;
	private ViewSwitcher() {
		
	}
	
	public static ViewSwitcher getInstance() {
		if(switcher == null) {
			switcher = new ViewSwitcher();
		}
		return switcher;
	}
	
	public void setPane(BorderPane pane) {
		this.pane=pane;
	}
	
	public void switchView(int viewType) {
		if(viewType == 1) {
			try {
				URL view = this.getClass().getResource("/BookListView/BookListView.fxml");
				FXMLLoader loader = new FXMLLoader(view);
				loader.setController(new BookListViewController());
				Parent contentPane = loader.load();	
				this.pane.setCenter(contentPane);
			}catch(IOException ex) {
				ex.printStackTrace();
			}
		}

		if(viewType == 2) {
			try {
				URL view = this.getClass().getResource("/BookDetailView/BookDetailView.fxml");
				FXMLLoader loader = new FXMLLoader(view);
				loader.setController(new BookDetailViewController());
				Parent contentPane = loader.load();
				this.pane.setCenter(contentPane);
			}catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
}

// if you see this ure gay
