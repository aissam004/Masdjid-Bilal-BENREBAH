package ui.addCategorieMateriel;

import ui.addCategorieMateriel.*;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import alert.AlertMaker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import Entity.CategorieMateriel;

import EntityController.CategorieMaterielJpaController;
import org.apache.commons.lang3.StringUtils;
import database.Database;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class CategorieMaterielAddController implements Initializable {
private final static Logger LOGGER = LogManager.getLogger(CategorieMaterielAddController.class.getName());
    private CategorieMaterielJpaController categorieMaterielJpaController;
    @FXML
    private Button btn_addPersonne;
    @FXML
    private JFXTextField id_categorie_materiel;

    @FXML
    private JFXTextField categorie_materiel;
    
    
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;

    private Boolean isInEditMode = false;
    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane mainContainer;
    
  

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categorieMaterielJpaController = new CategorieMaterielJpaController(Database.getEntityManagerFactory());
      
    }
    
    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) categorie_materiel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void addCategorieMateriel(ActionEvent event) {
       
        String pCategorie_materiel= StringUtils.trimToEmpty(categorie_materiel.getText());
        
        
        Boolean flag = pCategorie_materiel.isEmpty() ;
        if (flag) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "البيانات غير كافية", "الرجاء إدخال البيانات في جميع الحقول.");
            return;
        }

        if (isInEditMode) {
            handleUpdateCategorieMateriel();
            return;
        }
       
        CategorieMateriel categorieMateriel=new CategorieMateriel(pCategorie_materiel);
        categorieMaterielJpaController.create(categorieMateriel);
       
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "تمت إضافة نوع العتاد جديد", pCategorie_materiel+" تمت إضافتها");
            clearEntries();
     
    }

    public void pasteToUI(CategorieMateriel categorieMateriel) {
        clearEntries();
        id_categorie_materiel.setText(categorieMateriel.getIdCategorieMateriel().toString());
        categorie_materiel.setText(categorieMateriel.getCategorieMateriel());
        
        isInEditMode = Boolean.TRUE;
    }

    private void clearEntries() {
      id_categorie_materiel.clear();
      categorie_materiel.clear();
    }

    private void handleUpdateCategorieMateriel() {
        Integer pId_categorie_materiel=Integer.valueOf(id_categorie_materiel.getText());
        String pCategorie_materiel= StringUtils.trimToEmpty(categorie_materiel.getText());
        
        CategorieMateriel categorieMateriel=categorieMaterielJpaController.findCategorieMateriel(pId_categorie_materiel);
        categorieMateriel.setCategorieMateriel(pCategorie_materiel);
        try {
            categorieMaterielJpaController.edit(categorieMateriel);
             AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "العملية تمت بنجاح", "معلومات نوع العتاد عدلت.");
        } catch (Exception ex) {
            AlertMaker.showErrorMessage(ex);
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        
        
        
    }

    

}
