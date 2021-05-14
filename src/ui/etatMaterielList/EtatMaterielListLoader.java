
package ui.etatMaterielList;

import ui.etatMaterielList.*;
import ui.typePersonneList.*;
import ui.personneList.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.MasdjidUtil;




public class EtatMaterielListLoader extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
      
        
         MasdjidUtil.loadWindow(getClass().getResource("etat_materiel_list.fxml"), "لائحة حالات العتاد", null);
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
