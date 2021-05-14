package ui.main.toolbar;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import ui.main.Masdjid;
import util.MasdjidUtil;


public class ToolbarController implements Initializable {

  

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void loadAddPersonne(ActionEvent event) {
          MasdjidUtil.loadWindow(getClass().getResource("/ui/addpersonne/personne_add.fxml"), "إضافة شخص", null);
      
    }

    @FXML
    private void loadAddMateriel(ActionEvent event) {
        MasdjidUtil.loadWindow(getClass().getResource("/ui/addmateriel/materiel_add.fxml"), "إضافة عتاد", null);
    }

    @FXML
    private void loadPersonneTable(ActionEvent event) {
       MasdjidUtil.loadWindow(getClass().getResource("/ui/personneList/personne_list.fxml"), "لائحة الأشخاص", null);
    }

    @FXML
    private void loadMaterielTable(ActionEvent event) {
  MasdjidUtil.loadWindow(getClass().getResource("/ui/materielList/materiel_list.fxml"), "قائمة العتاد", null);
    }

    @FXML
    private void loadParametre(ActionEvent event) {
       MasdjidUtil.loadWindow(getClass().getResource("/parametre/Parametre.fxml"), "الإعدادات", null);
    }

   

}
