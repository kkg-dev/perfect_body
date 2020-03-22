package pl.edu.dsw.perfectbody.controllers;

import javafx.scene.input.*;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PerfectBodyController implements Initializable {

    @FXML
    private Pane mainPane;

    @FXML
    private ImageView langPol;

    @FXML
    private ImageView langEng;

    @FXML
    private Text close;

    @FXML
    private void openEnglishInterface(MouseEvent event) throws IOException { // Load US/UK-FXML
        try {
            //Set scene (pane) to US/UK imperial
            Pane engPane = FXMLLoader.load(getClass().getResource("/EnglishImperial.fxml"));
            mainPane.getChildren().setAll(engPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openPolishInterface(MouseEvent event) throws IOException { // Load PL-FXML
        try {
            //Set scene (pane) to polish metric
            Pane polPane = FXMLLoader.load(getClass().getResource("/PolishMetric.fxml"));
            mainPane.getChildren().setAll(polPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void close(MouseEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
