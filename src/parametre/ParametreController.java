/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parametre;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class ParametreController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initDefaultValues();
    }    
     @FXML
    private void handleSaveButtonAction(ActionEvent event) {
        String uname = username.getText();
        String pass = password.getText();

        Preferences preferences = Preferences.getPreferences();
      
        preferences.setUsername(uname);
        preferences.setPassword(pass);

        Preferences.writePreferenceToFile(preferences);
    }
    private Stage getStage() {
        return ((Stage) password.getScene().getWindow());
    }

    private void initDefaultValues() {
        Preferences preferences = Preferences.getPreferences();
        username.setText(String.valueOf(preferences.getUsername()));
        String passHash = String.valueOf(preferences.getPassword());
        password.setText(passHash.substring(0, Math.min(passHash.length(), 10)));
    
    }
}
