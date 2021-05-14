package ui.addEmplacement;


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
import Entity.Emplacement;

import EntityController.EmplacementJpaController;
import org.apache.commons.lang3.StringUtils;
import database.Database;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class EmplacementAddController implements Initializable {
private final static Logger LOGGER = LogManager.getLogger(EmplacementAddController.class.getName());
    private EmplacementJpaController emplacementJpaController;
   
    @FXML
    private JFXTextField id_emplacement_field;

    @FXML
    private JFXTextField emplacement_field;
    
    
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
        emplacementJpaController = new EmplacementJpaController(Database.getEntityManagerFactory());
      
    }
    
    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) emplacement_field.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void addEmplacement(ActionEvent event) {
       
        String pEmplacement= StringUtils.trimToEmpty(emplacement_field.getText());
        
        
        Boolean flag = pEmplacement.isEmpty() ;
        if (flag) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "البيانات غير كافية", "الرجاء إدخال البيانات في جميع الحقول.");
            return;
        }

        if (isInEditMode) {
            handleUpdateEmplacement();
            return;
        }
       
        Emplacement emplacement=new Emplacement(pEmplacement);
        emplacementJpaController.create(emplacement);
       
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "تمت إضافة الموقع جديد", pEmplacement+" تمت إضافتها");
            clearEntries();
     
    }

    public void pasteToUI(Emplacement emplacement) {
        clearEntries();
        id_emplacement_field.setText(emplacement.getIdEmplacement().toString());
        emplacement_field.setText(emplacement.getEmplacement());
        
        isInEditMode = Boolean.TRUE;
    }

    private void clearEntries() {
      id_emplacement_field.clear();
      emplacement_field.clear();
    }

    private void handleUpdateEmplacement() {
        Integer pId_emplacement=Integer.valueOf(id_emplacement_field.getText());
        String pEmplacement= StringUtils.trimToEmpty(emplacement_field.getText());
        
        Emplacement emplacement=emplacementJpaController.findEmplacement(pId_emplacement);
        emplacement.setEmplacement(pEmplacement);
        try {
            emplacementJpaController.edit(emplacement);
             AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "العملية تمت بنجاح", "معلومات الموقع عدلت.");
        } catch (Exception ex) {
            AlertMaker.showErrorMessage(ex);
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        
        
        
    }

    

}
