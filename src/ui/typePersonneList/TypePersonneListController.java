package ui.typePersonneList;

import ui.personneList.*;

import Entity.TypePersonne;
import EntityController.TypePersonneJpaController;
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
import ui.addTypePersonne.TypePersonneAddController;

import util.MasdjidUtil;

public class TypePersonneListController implements Initializable {

    ObservableList<TypePersonne> list = FXCollections.observableArrayList();

    @FXML
    private TableView<TypePersonne> tableView;

    @FXML
    private TableColumn<TypePersonne, Integer> id_type_personne_col;

    @FXML
    private TableColumn<TypePersonne, String> type_personne_col;


    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane contentPane;
   
    private TypePersonneJpaController typePersonneJpaController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        initCol();

        loadData();
    }

    private void initCol() {

        id_type_personne_col.setCellValueFactory(new PropertyValueFactory<>("idTypePersonne"));
        type_personne_col.setCellValueFactory(new PropertyValueFactory<>("typePersonne"));
        

    }

    private Stage getStage() {
        return (Stage) tableView.getScene().getWindow();
    }

    private void loadData() {
        list.clear();

        typePersonneJpaController = new TypePersonneJpaController(Database.getEntityManagerFactory());
        list.addAll(typePersonneJpaController.findTypePersonneEntities());

        tableView.setItems(list);
    }

    @FXML
    private void handleTypePersonneDelete(ActionEvent event) {
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
            Logger.getLogger(TypePersonneListController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void handleTypePersonneEdit(ActionEvent event) {
        //Fetch the selected row
        TypePersonne selectedTypePersonneForEdit = tableView.getSelectionModel().getSelectedItem();
        if (selectedTypePersonneForEdit == null) {
            AlertMaker.showErrorMessage("لم يتم اختيار أي وظيفة", "الرجاء تحديد وظيفة للتعديل.");
            return;
        }

        Stage stage = new Stage(StageStyle.DECORATED);

        TypePersonneAddController controller = (TypePersonneAddController) MasdjidUtil.loadWindow(getClass().getResource("/ui/addTypePersonne/type_personne_add.fxml"), "تعديل معلومات الوظيفة", stage);
        controller.pasteToUI(selectedTypePersonneForEdit);
        stage.setOnHiding((e) -> {
            handleRefresh(new ActionEvent());
        });

    }
    public final TypePersonne getSelectedTypePersonne(){
        return tableView.getSelectionModel().getSelectedItem();
    }
    @FXML
    private void handleButtonAddTypePersonne(ActionEvent event) {
        Stage stage = new Stage(StageStyle.DECORATED);
        
        MasdjidUtil.loadWindow(getClass().getResource("/ui/addTypePersonne/type_personne_add.fxml"), "إضافة وظيفة", stage);
      
        stage.setOnHiding((e) -> {
            handleRefresh(new ActionEvent());
        });
    }
    
  

    @FXML
    private void closeStage(ActionEvent event) {
        getStage().close();
    }

}
