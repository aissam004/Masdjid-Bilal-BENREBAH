package ui.emplacementList;


import Entity.Emplacement;
import EntityController.EmplacementJpaController;


import EntityController.exceptions.NonexistentEntityException;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import alert.AlertMaker;
import javax.persistence.EntityManagerFactory;
import database.Database;
import javafx.scene.control.Menu;
import ui.addEmplacement.EmplacementAddController;

import util.MasdjidUtil;

public class EmplacementListController implements Initializable {

    ObservableList<Emplacement> list = FXCollections.observableArrayList();

    @FXML
    private TableView<Emplacement> tableView;

    @FXML
    private TableColumn<Emplacement, Integer> id_emplacement_col;

    @FXML
    private TableColumn<Emplacement, String> emplacement_col;


    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane contentPane;
   
    private EmplacementJpaController emplacementJpaController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        initCol();

        loadData();
    }

    private void initCol() {

        id_emplacement_col.setCellValueFactory(new PropertyValueFactory<>("idEmplacement"));
        emplacement_col.setCellValueFactory(new PropertyValueFactory<>("emplacement"));
        

    }

    private Stage getStage() {
        return (Stage) tableView.getScene().getWindow();
    }

    private void loadData() {
        list.clear();

        emplacementJpaController = new EmplacementJpaController(Database.getEntityManagerFactory());
        list.addAll(emplacementJpaController.findEmplacementEntities());

        tableView.setItems(list);
    }

    @FXML
    private void handleEmplacementDelete(ActionEvent event) {
        //Fetch the selected row
/*
        
        Personne selectedForDeletion = tableView.getSelectionModel().getSelectedItem();
        if (selectedForDeletion == null) {
            AlertMaker.showErrorMessage("لم يتم اختيار أي شخص", "الرجاء تحديد شخص للحذف.");
            return;
        }
        
       
       Optional<ButtonType> answer =AlertMaker.showConfirmationMessage("حذف الشخص"
               ,"هل أنت متأكد من أنك تريد حذف " + selectedForDeletion.getNom()+" "+ selectedForDeletion.getPrenom()+ " ?");
        
        if (answer.get() == ButtonType.OK) {
             try {
            typePersonneJpaController.destroy(selectedForDeletion);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(EmplacementListController.class.getName()).log(Level.SEVERE, null, ex);
        }
           
                AlertMaker.showSimpleAlert("تم حذف الشخص", selectedForDeletion.getNom()+" "+ selectedForDeletion.getPrenom()+  " تم حذفه بنجاح..");
                list.remove(selectedForDeletion);
           
        } else {
            AlertMaker.showSimpleAlert("تم إلغاء الحذف", "تم إلغاء عملية الحذف");
        }
         */
    }

    @FXML
    private void handleRefresh(ActionEvent event) {
        loadData();
    }

    @FXML
    private void handleEmplacementEdit(ActionEvent event) {
        //Fetch the selected row
        Emplacement selectedEmplacementForEdit = tableView.getSelectionModel().getSelectedItem();
        if (selectedEmplacementForEdit == null) {
            AlertMaker.showErrorMessage("لم يتم اختيار أي وظيفة", "الرجاء تحديد الموقع للتعديل.");
            return;
        }

        Stage stage = new Stage(StageStyle.DECORATED);

        EmplacementAddController controller = (EmplacementAddController) MasdjidUtil.loadWindow(getClass().getResource("/ui/addEmplacement/emplacement_add.fxml"), "تعديل معلومات الموقع", stage);
        controller.pasteToUI(selectedEmplacementForEdit);
        stage.setOnHiding((e) -> {
            handleRefresh(new ActionEvent());
        });

    }
    public final Emplacement getSelectedEmplacement(){
        return tableView.getSelectionModel().getSelectedItem();
    }
    @FXML
    private void handleButtonAddEmplacement(ActionEvent event) {
        Stage stage = new Stage(StageStyle.DECORATED);
        
        MasdjidUtil.loadWindow(getClass().getResource("/ui/addEmplacement/emplacement_add.fxml"), "إضافة موقع", stage);
      
        stage.setOnHiding((e) -> {
            handleRefresh(new ActionEvent());
        });
    }
    
  

    @FXML
    private void closeStage(ActionEvent event) {
        getStage().close();
    }

}
