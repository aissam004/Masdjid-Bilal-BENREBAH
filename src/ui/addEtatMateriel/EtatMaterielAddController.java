package ui.addEtatMateriel;


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
import Entity.EtatMateriel;

import EntityController.EtatMaterielJpaController;
import org.apache.commons.lang3.StringUtils;
import database.Database;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class EtatMaterielAddController implements Initializable {
private final static Logger LOGGER = LogManager.getLogger(EtatMaterielAddController.class.getName());
    private EtatMaterielJpaController etatMaterielJpaController;
    @FXML
    private Button btn_addPersonne;
    @FXML
    private JFXTextField id_etat_materiel;

    @FXML
    private JFXTextField etat_materiel;
    
    
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
        etatMaterielJpaController = new EtatMaterielJpaController(Database.getEntityManagerFactory());
      
    }
    
    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) etat_materiel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void addEtatMateriel(ActionEvent event) {
       
        String pEtat_materiel= StringUtils.trimToEmpty(etat_materiel.getText());
        
        
        Boolean flag = pEtat_materiel.isEmpty() ;
        if (flag) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "البيانات غير كافية", "الرجاء إدخال البيانات في جميع الحقول.");
            return;
        }

        if (isInEditMode) {
            handleUpdateEtatMateriel();
            return;
        }
       
        EtatMateriel etatMateriel=new EtatMateriel(pEtat_materiel);
        etatMaterielJpaController.create(etatMateriel);
       
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "تمت إضافة حالة العتاد جديد", pEtat_materiel+" تمت إضافتها");
            clearEntries();
     
    }

    public void pasteToUI(EtatMateriel etatMateriel) {
        clearEntries();
        id_etat_materiel.setText(etatMateriel.getIdEtatMateriel().toString());
        etat_materiel.setText(etatMateriel.getEtatMateriel());
        
        isInEditMode = Boolean.TRUE;
    }

    private void clearEntries() {
      id_etat_materiel.clear();
      etat_materiel.clear();
    }

    private void handleUpdateEtatMateriel() {
        Integer pId_etat_materiel=Integer.valueOf(id_etat_materiel.getText());
        String pEtat_materiel= StringUtils.trimToEmpty(etat_materiel.getText());
        
        EtatMateriel etatMateriel=etatMaterielJpaController.findEtatMateriel(pId_etat_materiel);
        etatMateriel.setEtatMateriel(pEtat_materiel);
        try {
            etatMaterielJpaController.edit(etatMateriel);
             AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "العملية تمت بنجاح", "معلومات حالة العتاد عدلت.");
        } catch (Exception ex) {
            AlertMaker.showErrorMessage(ex);
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        
        
        
    }

    

}
