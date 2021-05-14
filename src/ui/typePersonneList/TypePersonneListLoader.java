
package ui.typePersonneList;

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


public class TypePersonneListLoader extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
      
        
         MasdjidUtil.loadWindow(getClass().getResource("type_personne_list.fxml"), "لائحة الأشخاص", null);
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
