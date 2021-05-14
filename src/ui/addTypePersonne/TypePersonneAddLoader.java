/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.addTypePersonne;

import ui.addpersonne.*;
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


public class TypePersonneAddLoader extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
      
        
        MasdjidUtil.loadWindow(getClass().getResource("type_personne_add.fxml"), "إضافة شخص", null);
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
