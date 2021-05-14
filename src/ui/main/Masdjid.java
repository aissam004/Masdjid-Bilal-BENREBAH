/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.main;

import ui.main.*;
import exceptions.ExceptionUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.MasdjidUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 *
 * @author pc
 */
public class Masdjid extends Application {
     private final static Logger LOGGER = LogManager.getLogger(Masdjid.class.getName());
             
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/connexion/connexion.fxml"));
        
        Scene scene = new Scene(root);
        scene.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
         stage.setTitle("تسجيل الدخول");

         MasdjidUtil.setStageIcon(stage);

        new Thread(() -> {
            ExceptionUtil.init();
        }).start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        
        Long startTime = System.currentTimeMillis();
        LOGGER.log(Level.INFO, "Masdjid launched on {}", MasdjidUtil.formatDateTimeString(startTime));
        launch(args);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                Long exitTime = System.currentTimeMillis();
                LOGGER.log(Level.INFO, "Library Assistant is closing on {}. Used for {} ms", MasdjidUtil.formatDateTimeString(startTime), exitTime);
            }
        });
    }
    
}
