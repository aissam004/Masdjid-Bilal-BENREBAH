package ui.about;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import alert.AlertMaker;
import util.MasdjidUtil;

public class AboutController implements Initializable {

    private static final String GITHUB = "https://www.linkedin.com/in/muhammedafsalvillan/";
    private static final String GOOGLE_MAPS= "https://goo.gl/maps/kp3mXRCcsCYdarGdA";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AlertMaker.showTrayMessage(String.format("مرحبا %s!", System.getProperty("user.name")), "شكرا لاستعمالكم تطبيق المسجد");
    }

    private void loadWebpage(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e1) {
            e1.printStackTrace();
            handleWebpageLoadException(url);
        }
    }

    private void handleWebpageLoadException(String url) {
        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();
        webEngine.load(url);
        Stage stage = new Stage();
        Scene scene = new Scene(new StackPane(browser));
        stage.setScene(scene);
        stage.setTitle("مسجد بلال بن رباح");
        stage.show();
        MasdjidUtil.setStageIcon(stage);
    }

    

    @FXML
    private void loadGitHub(ActionEvent event) {
        loadWebpage(GITHUB);
    }
     @FXML
    private void loadMaps(ActionEvent event) {
        loadWebpage(GOOGLE_MAPS);
    }
}
