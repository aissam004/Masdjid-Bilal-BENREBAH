package ui.addpersonne;

import Entity.Personne;
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
import EntityController.PersonneJpaController;
import EntityController.TypePersonneJpaController;
import org.apache.commons.lang3.StringUtils;
import database.Database;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.stage.StageStyle;
import ui.addTypePersonne.TypePersonneAddController;
import ui.typePersonneList.TypePersonneListController;
import util.MasdjidUtil;

public class PersonneAddController implements Initializable {

    private PersonneJpaController personneJpaController;
    @FXML
    private Button btn_addPersonne;
    @FXML
    private JFXTextField id_personne;
    @FXML
    private JFXTextField nom;

    @FXML
    private JFXTextField prenom;

    @FXML
    private JFXDatePicker date_naissance;

    @FXML
    private JFXTextField telephone;

    @FXML
    private JFXDatePicker date_engagement;

    @FXML
    private JFXComboBox<TypePersonne> idTypePersonne;

    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;

    private Boolean isInEditMode = false;
    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane mainContainer;

    ObservableList<TypePersonne> listTypePersonne = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        personneJpaController = new PersonneJpaController(Database.getEntityManagerFactory());
        loadData();
    }

    private void loadData() {
        listTypePersonne.clear();
        TypePersonneJpaController typePersonneJpaController = new TypePersonneJpaController(Database.getEntityManagerFactory());

        listTypePersonne.addAll(typePersonneJpaController.findTypePersonneEntities());
        idTypePersonne.setItems(listTypePersonne);
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) nom.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void addPersonne(ActionEvent event) {

        String pNom = StringUtils.trimToEmpty(nom.getText());
        String pPrenom = StringUtils.trimToEmpty(prenom.getText());

        String pDateNaissance = date_naissance.getValue() != null ? date_naissance.getValue().toString() : null;
        String pTelephone = StringUtils.trimToEmpty(telephone.getText());
        String pDateEngagement = date_engagement.getValue() != null ? date_engagement.getValue().toString() : null;
        TypePersonne pIdTypePersonne = this.idTypePersonne.getSelectionModel().getSelectedItem();

        Boolean flag = pNom.isEmpty() || pPrenom.isEmpty() || pIdTypePersonne == null || pDateEngagement == null;
        if (flag) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "البيانات غير كافية", "الرجاء إدخال البيانات في جميع الحقول.");
            return;
        }

        if (isInEditMode) {
            handlePersonneEdit();
            return;
        }

        Personne personne = new Personne(pNom, pPrenom, pDateNaissance, pTelephone, pDateEngagement, pIdTypePersonne);
        personneJpaController.create(personne);

        AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "تمت إضافة شخص جديد", pNom + " " + pPrenom + " تمت إضافته");

    }
    @FXML
     private void handleShowTypePersonneList(ActionEvent event){
          Stage stage = new Stage(StageStyle.DECORATED);
        TypePersonneListController typePersonneListController= (TypePersonneListController) MasdjidUtil.loadWindow(getClass().getResource("/ui/typePersonneList/type_personne_list.fxml"), "لائحة الوظائف", stage);
        stage.setOnHiding((e) -> {
           loadData();
           idTypePersonne.getSelectionModel().select(typePersonneListController.getSelectedTypePersonne());
        });
    }
     
    public void pasteToUI(Personne personne) {
        clearEntries();
        id_personne.setText(personne.getIdPersonne().toString());
        nom.setText(personne.getNom());
        prenom.setText(personne.getPrenom());
        System.out.println(date_naissance);
        if (personne.getDateNaissance() != null) {
            date_naissance.setValue(LocalDate.parse(personne.getDateNaissance()));
        }
        telephone.setText(personne.getTelephone());
        if (personne.getDateEngagement() != null) {
            date_engagement.setValue(LocalDate.parse(personne.getDateEngagement()));
        }
        idTypePersonne.getSelectionModel().select(personne.getIdTypePersonne());
        isInEditMode = Boolean.TRUE;
    }

    private void clearEntries() {
        id_personne.clear();
        nom.clear();
        prenom.clear();
        date_naissance.setValue(null);
        telephone.clear();
        date_engagement.setValue(null);
        idTypePersonne.getSelectionModel().clearSelection();
    } 
    private void handlePersonneEdit() {
        Integer pIdPersonne = Integer.valueOf(id_personne.getText());
        String pNom = StringUtils.trimToEmpty(nom.getText());
        String pPrenom = StringUtils.trimToEmpty(prenom.getText());
        String pDateNaissance = StringUtils.trimToEmpty(date_naissance.getValue().toString());
        String pTelephone = StringUtils.trimToEmpty(telephone.getText());
        String pDateEngagement = StringUtils.trimToEmpty(date_engagement.getValue().toString());
        TypePersonne idTypePersonne = this.idTypePersonne.getSelectionModel().getSelectedItem();

        Personne personne = personneJpaController.findPersonne(pIdPersonne);
        personne.setNom(pNom);
        personne.setPrenom(pPrenom);
        personne.setDateNaissance(pDateNaissance);
        personne.setTelephone(pTelephone);
        personne.setDateEngagement(pDateEngagement);
        personne.setIdTypePersonne(idTypePersonne);

        try {
            personneJpaController.edit(personne);
        } catch (Exception e) {

        }

        AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "عميلية تمت بنجاح", "معلومات الشخص حدثت.");

    }

    

}
