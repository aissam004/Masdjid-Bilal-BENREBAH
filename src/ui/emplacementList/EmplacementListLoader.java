
package ui.emplacementList;

import ui.categorieMaterielList.*;
import ui.typePersonneList.*;
import ui.personneList.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import util.MasdjidUtil;




public class EmplacementListLoader extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
      
        
         MasdjidUtil.loadWindow(getClass().getResource("emplacement_list.fxml"), "لائحة المواقع", null);
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
