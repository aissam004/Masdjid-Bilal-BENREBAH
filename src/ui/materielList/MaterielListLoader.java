
package ui.materielList;

import ui.personneList.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import util.MasdjidUtil;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;


public class MaterielListLoader extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
      
        
         MasdjidUtil.loadWindow(getClass().getResource("materiel_list.fxml"), "قائمة العتاد", null);
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
