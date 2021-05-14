package ui.addmateriel;

import Entity.CategorieMateriel;
import Entity.Emplacement;
import Entity.EtatMateriel;
import Entity.Materiel;
import EntityController.CategorieMaterielJpaController;
import EntityController.EmplacementJpaController;
import EntityController.EtatMaterielJpaController;
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
import EntityController.MaterielJpaController;
import org.apache.commons.lang3.StringUtils;
import database.Database;
import java.time.LocalDate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ui.addmateriel.MaterielAddController;
import ui.categorieMaterielList.CategorieMaterielListController;
import ui.emplacementList.EmplacementListController;
import ui.etatMaterielList.EtatMaterielListController;

import util.MasdjidUtil;

public class MaterielAddController implements Initializable {

    private final static Logger LOGGER = LogManager.getLogger(MaterielAddController.class.getName());
    private MaterielJpaController materielJpaController;
    @FXML
    private JFXTextField id_materiel_field;

    @FXML
    private JFXTextField designation_field;

    @FXML
    private JFXTextField reference_field;

    @FXML
    private JFXComboBox<CategorieMateriel> categorie_materiel_field;

    @FXML
    private JFXDatePicker date_ajout_field;
    @FXML
    private JFXTextField quantite_field;
    @FXML
    private JFXComboBox<Emplacement> emplacement_field;

    @FXML
    private JFXComboBox<EtatMateriel> etat_materiel_field;

    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;

    private Boolean isInEditMode = false;
    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane mainContainer;

    ObservableList<CategorieMateriel> listCategorieMateriel = FXCollections.observableArrayList();
    ObservableList<Emplacement> listEmplacement = FXCollections.observableArrayList();
    ObservableList<EtatMateriel> listEtatMateriel = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        materielJpaController = new MaterielJpaController(Database.getEntityManagerFactory());
        setNumeriqueTextField(quantite_field);
        loadData();
    }

    private void loadData() {
        loadDataListCategorieMateriel();
        loadDataListEtatMateriel();
        loadDataListEmplacement();
    }

    private void loadDataListCategorieMateriel() {
        listCategorieMateriel.clear();
        CategorieMaterielJpaController categorieMaterielJpaController = new CategorieMaterielJpaController(Database.getEntityManagerFactory());
        listCategorieMateriel.addAll(categorieMaterielJpaController.findCategorieMaterielEntities());
        categorie_materiel_field.setItems(listCategorieMateriel);
    }

    private void loadDataListEtatMateriel() {
        listEtatMateriel.clear();
        EtatMaterielJpaController etatMaterielJpaController = new EtatMaterielJpaController(Database.getEntityManagerFactory());
        listEtatMateriel.addAll(etatMaterielJpaController.findEtatMaterielEntities());
        etat_materiel_field.setItems(listEtatMateriel);
    }

    private void loadDataListEmplacement() {
        listEmplacement.clear();
        EmplacementJpaController emplacementJpaController = new EmplacementJpaController(Database.getEntityManagerFactory());
        listEmplacement.addAll(emplacementJpaController.findEmplacementEntities());
        emplacement_field.setItems(listEmplacement);
    }

    private void setNumeriqueTextField(JFXTextField textField) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) designation_field.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void addMateriel(ActionEvent event) {

        String mDesignation = StringUtils.trimToEmpty(designation_field.getText());
        String mReference = StringUtils.trimToEmpty(reference_field.getText());
        CategorieMateriel mCategorieMateriel = this.categorie_materiel_field.getSelectionModel().getSelectedItem();
        Integer mQuantite = Integer.valueOf(quantite_field.getText() != "" ? quantite_field.getText() : "1");
        String mDateAjout = date_ajout_field.getValue() != null ? date_ajout_field.getValue().toString() : null;

        Emplacement mEmplacement = this.emplacement_field.getSelectionModel().getSelectedItem();
        EtatMateriel mEtatMateriel = this.etat_materiel_field.getSelectionModel().getSelectedItem();

        Boolean flag = mDesignation.isEmpty() || mCategorieMateriel == null || mEtatMateriel == null;
        if (flag) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "البيانات غير كافية", "الرجاء إدخال البيانات في جميع الحقول.");
            return;
        }

        if (isInEditMode) {
            handleMaterielEdit();
            return;
        }

        Materiel materiel = new Materiel(mDesignation, mReference, mQuantite, mDateAjout, mCategorieMateriel, mEtatMateriel, mEmplacement);
        materielJpaController.create(materiel);

        AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "تمت إضافة عتاد جديد", mDesignation + " تمت إضافته");

    }

    @FXML
    private void handleShowCategorieMaterielList(ActionEvent event) {

        Stage stage = new Stage(StageStyle.DECORATED);
        CategorieMaterielListController categorieMaterielListController = (CategorieMaterielListController) MasdjidUtil.loadWindow(getClass().getResource("/ui/categorieMaterielList/categorie_materiel_list.fxml"), "لائحة أنواع العتاد", stage);
        stage.setOnHiding((e) -> {
            loadDataListCategorieMateriel();
            categorie_materiel_field.getSelectionModel().select(categorieMaterielListController.getSelectedCategorieMateriel());
        });
    }

    @FXML
    private void handleShowEtatMaterielList(ActionEvent event) {

        Stage stage = new Stage(StageStyle.DECORATED);
        EtatMaterielListController categorieMaterielListController = (EtatMaterielListController) MasdjidUtil.loadWindow(getClass().getResource("/ui/etatMaterielList/etat_materiel_list.fxml"), "لائحة حالات العتاد", stage);
        stage.setOnHiding((e) -> {
         loadDataListEtatMateriel();
            etat_materiel_field.getSelectionModel().select(categorieMaterielListController.getSelectedEtatMateriel());
        });
    }

    @FXML
    private void handleShowEmplacementList(ActionEvent event) {

        Stage stage = new Stage(StageStyle.DECORATED);
        EmplacementListController categorieMaterielListController = (EmplacementListController) MasdjidUtil.loadWindow(getClass().getResource("/ui/emplacementList/emplacement_list.fxml"), "لائحة المواقع", stage);
        stage.setOnHiding((e) -> {
          loadDataListEmplacement();
            emplacement_field.getSelectionModel().select(categorieMaterielListController.getSelectedEmplacement());
        });
    }

    public void pasteToUI(Materiel materiel) {
        clearEntries();
        id_materiel_field.setText(materiel.getIdMateriel().toString());
        designation_field.setText(materiel.getDesignation());
        reference_field.setText(materiel.getReference());
        categorie_materiel_field.getSelectionModel().select(materiel.getIdCategorieMateriel());
        quantite_field.setText(materiel.getQuantite().toString());

        if (materiel.getDateAjout() != null) {
            date_ajout_field.setValue(materiel.getDateAjout());
        }
        emplacement_field.getSelectionModel().select(materiel.getIdEmplacement());
        etat_materiel_field.getSelectionModel().select(materiel.getIdEtatMateriel());

        isInEditMode = Boolean.TRUE;
    }

    private void clearEntries() {
        designation_field.clear();
        reference_field.clear();
        categorie_materiel_field.getSelectionModel().clearSelection();
        quantite_field.clear();
        date_ajout_field.setValue(null);
        emplacement_field.getSelectionModel().clearSelection();
        etat_materiel_field.getSelectionModel().clearSelection();
    }

    private void handleMaterielEdit() {
        Integer pIdMateriel = Integer.valueOf(id_materiel_field.getText());
        String mDesignation = StringUtils.trimToEmpty(designation_field.getText());
        String mReference = StringUtils.trimToEmpty(reference_field.getText());
        CategorieMateriel mCategorieMateriel = this.categorie_materiel_field.getSelectionModel().getSelectedItem();
        Integer mQuantite = Integer.valueOf(quantite_field.getText() != "" ? quantite_field.getText() : "1");
        String mDateAjout = date_ajout_field.getValue() != null ? date_ajout_field.getValue().toString() : null;

        Emplacement mEmplacement = this.emplacement_field.getSelectionModel().getSelectedItem();
        EtatMateriel mEtatMateriel = this.etat_materiel_field.getSelectionModel().getSelectedItem();

        Materiel materiel = materielJpaController.findMateriel(pIdMateriel);
        materiel.setProperties(mDesignation, mReference, mQuantite, mDateAjout, mCategorieMateriel, mEtatMateriel, mEmplacement);

        try {
            materielJpaController.edit(materiel);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "{}", e);
        }

        AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "العملية تمت بنجاح", "معلومات العتاد عدلت.");

    }

}
