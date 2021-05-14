package ui.addTypePersonne;

import ui.addpersonne.*;

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
import Entity.TypePersonne;

import EntityController.TypePersonneJpaController;
import org.apache.commons.lang3.StringUtils;
import database.Database;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class TypePersonneAddController implements Initializable {
private final static Logger LOGGER = LogManager.getLogger(TypePersonneAddController.class.getName());
    private TypePersonneJpaController typePersonneJpaController;
    @FXML
    private Button btn_addPersonne;
    @FXML
    private JFXTextField id_type_personne;

    @FXML
    private JFXTextField type_personne;
    
    
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
        typePersonneJpaController = new TypePersonneJpaController(Database.getEntityManagerFactory());
      
    }
    
    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) type_personne.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void addTypePersonne(ActionEvent event) {
       
        String pType_personne= StringUtils.trimToEmpty(type_personne.getText());
        
        
        Boolean flag = pType_personne.isEmpty() ;
        if (flag) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "البيانات غير كافية", "الرجاء إدخال البيانات في جميع الحقول.");
            return;
        }

        if (isInEditMode) {
            handleUpdateTypePersonne();
            return;
        }
       
        TypePersonne typePersonne=new TypePersonne(pType_personne);
        typePersonneJpaController.create(typePersonne);
       
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "تمت إضافة وظيفة جديدة", pType_personne+" تمت إضافتها");
            clearEntries();
     
    }

    public void pasteToUI(TypePersonne typePersonne) {
        clearEntries();
        id_type_personne.setText(typePersonne.getIdTypePersonne().toString());
        type_personne.setText(typePersonne.getTypePersonne());
        
        isInEditMode = Boolean.TRUE;
    }

    private void clearEntries() {
      id_type_personne.clear();
      type_personne.clear();
    }

    private void handleUpdateTypePersonne() {
        Integer pId_type_personne=Integer.valueOf(id_type_personne.getText());
        String pType_personne= StringUtils.trimToEmpty(type_personne.getText());
        
        TypePersonne typePersonne=typePersonneJpaController.findTypePersonne(pId_type_personne);
        typePersonne.setTypePersonne(pType_personne);
        try {
            typePersonneJpaController.edit(typePersonne);
             AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "عملية تمت بنجاح", "معلومات الوظيفة عدلت.");
        } catch (Exception ex) {
            AlertMaker.showErrorMessage(ex);
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        
        
        
    }

    

}
