package main;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class MenuViewController implements Initializable{
    
    @FXML
    void onCloseClick(ActionEvent event) {
    	System.exit(0);
    }

    @FXML
    void onListViewClick(ActionEvent event) throws InvocationTargetException {
    	ViewSwitcher.getInstance().switchView(1);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
    
    
}
