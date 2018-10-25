package main;

import java.net.URL;
import java.util.ResourceBundle;

import BookDetailView.BookDetailViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class MenuViewController implements Initializable{
	    
    @FXML
    void onCloseClick(ActionEvent event) {
    	System.exit(0);
    }

    @FXML
    void onListViewClick(ActionEvent event) {
    	ViewSwitcher.getInstance().switchView(1);
    }
    @FXML
    void onAddBookClick(ActionEvent event) {
    	model.Book book = new model.Book();
    	BookDetailViewController.setBookNull();
    	ViewSwitcher.getInstance().switchView(2);
    	BookDetailViewController.setBook(book);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
    
    
}
