package ui.categorieMaterielList;

import Entity.CategorieMateriel;
import EntityController.CategorieMaterielJpaController;


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
import ui.addCategorieMateriel.CategorieMaterielAddController;

import util.MasdjidUtil;

public class CategorieMaterielListController implements Initializable {

    ObservableList<CategorieMateriel> list = FXCollections.observableArrayList();

    @FXML
    private TableView<CategorieMateriel> tableView;

    @FXML
    private TableColumn<CategorieMateriel, Integer> id_categorie_materiel_col;

    @FXML
    private TableColumn<CategorieMateriel, String> categorie_materiel_col;


    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane contentPane;
   
    private CategorieMaterielJpaController categorieMaterielJpaController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        initCol();

        loadData();
    }

    private void initCol() {

        id_categorie_materiel_col.setCellValueFactory(new PropertyValueFactory<>("idCategorieMateriel"));
        categorie_materiel_col.setCellValueFactory(new PropertyValueFactory<>("categorieMateriel"));
        

    }

    private Stage getStage() {
        return (Stage) tableView.getScene().getWindow();
    }

    private void loadData() {
        list.clear();

        categorieMaterielJpaController = new CategorieMaterielJpaController(Database.getEntityManagerFactory());
        list.addAll(categorieMaterielJpaController.findCategorieMaterielEntities());

        tableView.setItems(list);
    }

    @FXML
    private void handleCategorieMaterielDelete(ActionEvent event) {
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
            Logger.getLogger(CategorieMaterielListController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void handleCategorieMaterielEdit(ActionEvent event) {
        //Fetch the selected row
        CategorieMateriel selectedCategorieMaterielForEdit = tableView.getSelectionModel().getSelectedItem();
        if (selectedCategorieMaterielForEdit == null) {
            AlertMaker.showErrorMessage("لم يتم اختيار أي حالة العتاد", "الرجاء تحديد نوع العتاد للتعديل.");
            return;
        }

        Stage stage = new Stage(StageStyle.DECORATED);

        CategorieMaterielAddController controller = (CategorieMaterielAddController) MasdjidUtil.loadWindow(getClass().getResource("/ui/addCategorieMateriel/categorie_materiel_add.fxml"), "تعديل معلومات نوع العتاد", stage);
        controller.pasteToUI(selectedCategorieMaterielForEdit);
        stage.setOnHiding((e) -> {
            handleRefresh(new ActionEvent());
        });

    }
    public final CategorieMateriel getSelectedCategorieMateriel(){
        return tableView.getSelectionModel().getSelectedItem();
    }
    @FXML
    private void handleButtonAddCategorieMateriel(ActionEvent event) {
        Stage stage = new Stage(StageStyle.DECORATED);
        
        MasdjidUtil.loadWindow(getClass().getResource("/ui/addCategorieMateriel/categorie_materiel_add.fxml"), "إضافة نوع العتاد", stage);
      
        stage.setOnHiding((e) -> {
            handleRefresh(new ActionEvent());
        });
    }
    
  

    @FXML
    private void closeStage(ActionEvent event) {
        getStage().close();
    }

}
