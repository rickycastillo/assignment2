package main;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import BookDetailView.BookDetailViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class MenuViewController implements Initializable{
	
	int saved;
	    
    @FXML
    void onCloseClick(ActionEvent event) {
    	saved = BookDetailViewController.checkSavedChanges();
    	if(saved == -1)
    		System.exit(0);
    }

    @FXML
    void onListViewClick(ActionEvent event) {
    	saved = BookDetailViewController.checkSavedChanges();
    	if(saved == -1)
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
