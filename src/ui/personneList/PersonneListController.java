package ui.personneList;

import Entity.Personne;
import Entity.TypePersonne;
import EntityController.PersonneJpaController;
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
import ui.addpersonne.PersonneAddController;
import util.MasdjidUtil;

public class PersonneListController implements Initializable {

    ObservableList<Personne> list = FXCollections.observableArrayList();

    @FXML
    private TableView<Personne> tableView;
    @FXML
    private TableColumn<Personne, Integer> id_personne_col;

    @FXML
    private TableColumn<Personne, String> nom_col;

    @FXML
    private TableColumn<Personne, String> prenom_col;

    @FXML
    private TableColumn<Personne, String> date_naissance_col;

    @FXML
    private TableColumn<Personne, String> telephone_col;

    @FXML
    private TableColumn<Personne, String> date_engagement_col;

    @FXML
    private TableColumn<Personne, TypePersonne> type_personne_col;

    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane contentPane;
   
    private PersonneJpaController personneJpaController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        initCol();

        loadData();
    }

    private void initCol() {

        id_personne_col.setCellValueFactory(new PropertyValueFactory<>("idPersonne"));
        nom_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom_col.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        date_naissance_col.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
        telephone_col.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        date_engagement_col.setCellValueFactory(new PropertyValueFactory<>("dateEngagement"));
        type_personne_col.setCellValueFactory(new PropertyValueFactory<>("idTypePersonne"));

    }

    private Stage getStage() {
        return (Stage) tableView.getScene().getWindow();
    }

    private void loadData() {
        list.clear();

        personneJpaController = new PersonneJpaController(Database.getEntityManagerFactory());
        list.addAll(personneJpaController.findPersonneEntities());

        tableView.setItems(list);
    }

    @FXML
    private void handlePersonneDelete(ActionEvent event) {
        //Fetch the selected row

        
        Personne selectedForDeletion = tableView.getSelectionModel().getSelectedItem();
        if (selectedForDeletion == null) {
            AlertMaker.showErrorMessage("لم يتم اختيار أي شخص", "الرجاء تحديد شخص للحذف.");
            return;
        }
        
       
       Optional<ButtonType> answer =AlertMaker.showConfirmationMessage("حذف الشخص"
               ,"هل أنت متأكد من أنك تريد حذف " + selectedForDeletion.getNom()+" "+ selectedForDeletion.getPrenom()+ " ?");
        
        if (answer.get() == ButtonType.OK) {
             try {
            personneJpaController.destroy(selectedForDeletion);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PersonneListController.class.getName()).log(Level.SEVERE, null, ex);
        }
           
                AlertMaker.showSimpleAlert("تم حذف الشخص", selectedForDeletion.getNom()+" "+ selectedForDeletion.getPrenom()+  " تم حذفه بنجاح..");
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
    private void handlePersonneEdit(ActionEvent event) {
        //Fetch the selected row
        Personne selectedPersonneForEdit = tableView.getSelectionModel().getSelectedItem();
        if (selectedPersonneForEdit == null) {
            AlertMaker.showErrorMessage("لم يتم اختيار أي شخص", "الرجاء تحديد عضو للتعديل.");
            return;
        }

        Stage stage = new Stage(StageStyle.DECORATED);

        PersonneAddController controller = (PersonneAddController) MasdjidUtil.loadWindow(getClass().getResource("/ui/addpersonne/personne_add.fxml"), "تعديل معلومات الشخص", stage);
        controller.pasteToUI(selectedPersonneForEdit);
        stage.setOnHiding((e) -> {
            handleRefresh(new ActionEvent());
        });

    }
    @FXML
    private void handleButtonAddPersonne(ActionEvent event) {
        Stage stage = new Stage(StageStyle.DECORATED);
        
        MasdjidUtil.loadWindow(getClass().getResource("/ui/addpersonne/personne_add.fxml"), "إضافة شخص", stage);
      
        stage.setOnHiding((e) -> {
            handleRefresh(new ActionEvent());
        });
    }
    
    @FXML
    private void exportAsPDF(ActionEvent event) {
        /*
List<List> printData = new ArrayList<>();
        String[] headers = {"   Name    ", "ID", "Mobile", "    Email   "};
        printData.add(Arrays.asList(headers));
        for (Member member : list) {
            List<String> row = new ArrayList<>();
            row.add(member.getName());
            row.add(member.getId());
            row.add(member.getMobile());
            row.add(member.getEmail());
            printData.add(row);
        }
        LibraryAssistantUtil.initPDFExprot(rootPane, contentPane, getStage(), printData);
         */
    }

    @FXML
    private void closeStage(ActionEvent event) {
        getStage().close();
    }

}
