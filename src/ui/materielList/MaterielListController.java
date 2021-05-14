package ui.materielList;

import Entity.CategorieMateriel;
import Entity.Emplacement;
import Entity.EtatMateriel;
import Entity.Materiel;
import EntityController.MaterielJpaController;
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
import java.time.LocalDate;
import javafx.scene.control.Menu;
import ui.addmateriel.MaterielAddController;
import util.MasdjidUtil;

public class MaterielListController implements Initializable {

    ObservableList<Materiel> list = FXCollections.observableArrayList();

      @FXML
    private TableView<Materiel> tableView;

    @FXML
    private TableColumn<Materiel, Integer> id_materiel_col;

    @FXML
    private TableColumn<Materiel, String> designation_col;

    @FXML
    private TableColumn<Materiel, String> reference_col;

    @FXML
    private TableColumn<Materiel, CategorieMateriel> id_categorie_materiel_col;

    @FXML
    private TableColumn<Materiel, LocalDate> date_ajout_col;

    @FXML
    private TableColumn<Materiel, Emplacement> id_emplacement_col;

    @FXML
    private TableColumn<Materiel, Integer> quantite_col;

    @FXML
    private TableColumn<Materiel, EtatMateriel> id_etat_materiel_col;

    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane contentPane;
   
    private MaterielJpaController materielJpaController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        initCol();

        loadData();
    }

    private void initCol() {

        id_materiel_col.setCellValueFactory(new PropertyValueFactory<>("idMateriel"));
        designation_col.setCellValueFactory(new PropertyValueFactory<>("designation"));
        reference_col.setCellValueFactory(new PropertyValueFactory<>("reference"));
        quantite_col.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        date_ajout_col.setCellValueFactory(new PropertyValueFactory<>("dateAjout"));
        id_categorie_materiel_col.setCellValueFactory(new PropertyValueFactory<>("idCategorieMateriel"));
        id_etat_materiel_col.setCellValueFactory(new PropertyValueFactory<>("idEtatMateriel"));
        id_emplacement_col.setCellValueFactory(new PropertyValueFactory<>("idEmplacement"));

    }

    private Stage getStage() {
        return (Stage) tableView.getScene().getWindow();
    }

    private void loadData() {
        list.clear();

        materielJpaController = new MaterielJpaController(Database.getEntityManagerFactory());
        list.addAll(materielJpaController.findMaterielEntities());

        tableView.setItems(list);
    }

    @FXML
    private void handleMaterielDelete(ActionEvent event) {
        //Fetch the selected row

        
        Materiel selectedForDeletion = tableView.getSelectionModel().getSelectedItem();
        if (selectedForDeletion == null) {
            AlertMaker.showErrorMessage("لم يتم اختيار أي شخص", "الرجاء تحديد العتاد للحذف.");
            return;
        }
        
       
       Optional<ButtonType> answer =AlertMaker.showConfirmationMessage("حذف الشخص"
               ,"هل أنت متأكد من أنك تريد حذف " + selectedForDeletion.getDesignation()+"رقمه الستلسلي  "+ selectedForDeletion.getReference()+ " ?");
        
        if (answer.get() == ButtonType.OK) {
             try {
            materielJpaController.destroy(selectedForDeletion);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(MaterielListController.class.getName()).log(Level.SEVERE, null, ex);
        }
           
                AlertMaker.showSimpleAlert("تم حذف العتاد", selectedForDeletion.getDesignation()+"رقمه الستلسلي  "+ selectedForDeletion.getReference()+  " تم حذفه بنجاح..");
                list.remove(selectedForDeletion);
           
        } else {
            AlertMaker.showSimpleAlert("تم إلغاء الحذف", "تم إلغاء عملية الحذف");
        }
         
    }

    @FXML
    private void handleRefresh(ActionEvent event) {
        loadData();
    }

    @FXML
    private void handleMaterielEdit(ActionEvent event) {
        //Fetch the selected row
        Materiel selectedMaterielForEdit = tableView.getSelectionModel().getSelectedItem();
        if (selectedMaterielForEdit == null) {
            AlertMaker.showErrorMessage("لم يتم اختيار أي عتاد", "الرجاء تحديد العتاد للتعديل.");
            return;
        }

        Stage stage = new Stage(StageStyle.DECORATED);

        MaterielAddController controller = (MaterielAddController) MasdjidUtil.loadWindow(getClass().getResource("/ui/addmateriel/materiel_add.fxml"), "تعديل معلومات العتاد", stage);
        controller.pasteToUI(selectedMaterielForEdit);
        stage.setOnHiding((e) -> {
            handleRefresh(new ActionEvent());
        });

    }
    @FXML
    private void handleButtonAddMateriel(ActionEvent event) {
        Stage stage = new Stage(StageStyle.DECORATED);
        
        MasdjidUtil.loadWindow(getClass().getResource("/ui/addmateriel/materiel_add.fxml"), "إضافة عتاد", stage);
      
        stage.setOnHiding((e) -> {
            handleRefresh(new ActionEvent());
        });
    }
    
    @FXML
    void handleShowCategorieMaterielList(ActionEvent event) {
Stage stage = new Stage(StageStyle.DECORATED);
        
        MasdjidUtil.loadWindow(getClass().getResource("/ui/categorieMaterielList/categorie_materiel_list.fxml"), "قائمة أنواع العتاد", stage);
      
        stage.setOnHiding((e) -> {
            handleRefresh(new ActionEvent());
        });
    }

    @FXML
    void handleShowEmplacementList(ActionEvent event) {
Stage stage = new Stage(StageStyle.DECORATED);
        
        MasdjidUtil.loadWindow(getClass().getResource("/ui/emplacementList/emplacement_list.fxml"), "قائمة المواقع", stage);
      
        stage.setOnHiding((e) -> {
            handleRefresh(new ActionEvent());
        });
    }

    @FXML
    void handleShowEtatMaterielList(ActionEvent event) {
Stage stage = new Stage(StageStyle.DECORATED);
        
        MasdjidUtil.loadWindow(getClass().getResource("/ui/etatMaterielList/etat_materiel_list.fxml"), "قائمة حالات العتاد", stage);
      
        stage.setOnHiding((e) -> {
            handleRefresh(new ActionEvent());
        });
    }
    
    @FXML
    private void exportAsPDF(ActionEvent event) {
      /*  
List<List> printData = new ArrayList<>();
        String[] headers = {"الحالة","الكمية","الموقع","تاريخ الإضافة","النوع","الرقم الستلسلي","التعيين","رقم"};
        printData.add(Arrays.asList(headers));
        for (Materiel materiel : list) {
            List<String> row = new ArrayList<>();
            row.add(materiel.getIdEtatMateriel().getEtatMateriel().replace(target, replacement));
            row.add(materiel.getQuantite().toString());
            row.add(materiel.getIdEmplacement().getEmplacement());
            row.add(materiel.getDateAjout().toString());
            row.add(materiel.getIdCategorieMateriel().getCategorieMateriel());
            row.add(materiel.getReference());
            row.add(materiel.getDesignation());
            row.add(materiel.getIdMateriel().toString());
            printData.add(row);
        }
        MasdjidUtil.initPDFExprot(rootPane, contentPane, getStage(), printData);
         */
    }

    @FXML
    private void closeStage(ActionEvent event) {
        getStage().close();
    }

}
