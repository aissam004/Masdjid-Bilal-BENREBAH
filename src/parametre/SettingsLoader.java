package parametre;

import java.io.File;
import java.net.URL;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class SettingsLoader extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("Parametre.fxml"));
        
     

        Scene scene = new Scene(root);
        scene.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        
        stage.setScene(scene);
        stage.show();
        stage.setTitle("الإعدادات");

    }

    public static void main(String[] args) {
        launch(args);
    }

}
