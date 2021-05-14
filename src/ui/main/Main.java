/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.main;

import ui.main.*;
import Entity.TypePersonne;
import EntityController.TypePersonneJpaController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import ui.main.toolbar.ToolbarController;
import util.MasdjidUtil;

/**
 *
 * @author pc
 */
public class Main implements Initializable {
    
   @FXML
    private StackPane rootPane;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private JFXTabPane mainTabPane;

    @FXML
    private Tab accueil;

    @FXML
    private JFXHamburger hamburger;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      initDrawer();
    }    
    @FXML
    private void handleMenuSettings(ActionEvent event) {
        MasdjidUtil.loadWindow(getClass().getResource("/parametre/Parametre.fxml"), "الإعدادات", null);
    }
     private Stage getStage() {
        return (Stage) rootPane.getScene().getWindow();
    }
 @FXML
    private void handleMenuAddPersonne(ActionEvent event) {
          MasdjidUtil.loadWindow(getClass().getResource("/ui/addpersonne/personne_add.fxml"), "إضافة شخص", null);
      
    }

    @FXML
    private void handleMenuAddMateriel(ActionEvent event) {
        MasdjidUtil.loadWindow(getClass().getResource("/ui/addmateriel/materiel_add.fxml"), "إضافة عتاد", null);
    }

    @FXML
    private void handleMenuPersonneTable(ActionEvent event) {
       MasdjidUtil.loadWindow(getClass().getResource("/ui/personneList/personne_list.fxml"), "لائحة الأشخاص", null);
    }

    @FXML
    private void handleMenuMaterielTable(ActionEvent event) {
  MasdjidUtil.loadWindow(getClass().getResource("/ui/materielList/materiel_list.fxml"), "قائمة العتاد", null);
    }
  
    @FXML
    private void handleMenuAboutUs(ActionEvent event) {
  MasdjidUtil.loadWindow(getClass().getResource("/ui/about/about.fxml"), "معلومات عنا", null);
    }
    @FXML
    private void handleMenuClose(ActionEvent event) {
        getStage().close();
    }
    private void initDrawer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/main/toolbar/toolbar.fxml"));
            VBox toolbar = loader.load();
            drawer.setSidePane(toolbar);
            ToolbarController controller = loader.getController();
         
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
        task.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
            drawer.toggle();
        });
        drawer.setOnDrawerOpening((event) -> {
            task.setRate(task.getRate() * -1);
            task.play();
            drawer.toFront();
        });
        drawer.setOnDrawerClosed((event) -> {
            drawer.toBack();
            task.setRate(task.getRate() * -1);
            task.play();
        });
    }
}
