package main.Weather.webServices;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import main.Weather.WeatherController;

import java.net.URL;
import java.util.ResourceBundle;


// Changes web-viewer displays through press of buttons to display various weather services found online.
// Useful for comparing with observations
//possible futureimplementations might include:
//allowing user to set their own sites.
// saving address user is on so if window is resized they dont have to start over.
    public class WebServicesController implements Initializable {
    public AnchorPane pane;
    @FXML
    private Rectangle instructionBox = new Rectangle();
    @FXML
    private Label instructions = new Label();
    @FXML
    private  WebView webViewer = new WebView();
    @FXML
    private Button windyButton;
    @FXML
    private Button noaaButton;
    @FXML
    private Button farmersButton;
    @FXML
    private Button wunderButton;
    @FXML
    private Button accuButton;



    @FXML
    private void windyEnter(MouseEvent mouseEvent) {
        windyButton.setEffect(new Glow(.25)); }

    @FXML
    private void windyExit(MouseEvent mouseEvent) {
        windyButton.setEffect(new Glow(.0)); }

    @FXML
    private void windyPressed(MouseEvent mouseEvent) {
        windyButton.setEffect(new Glow(.80)); }
    @FXML
    private void windyReleased(MouseEvent mouseEvent) {
        windyButton.setEffect(new Glow(.0));
        WebEngine engine = webViewer.getEngine();
        engine.load("https://www.windy.com");
        instructionBox.setOpacity(0);
        instructions.setText("");
        webViewer.setOpacity(100);
    }



    @FXML
    private void noaaEnter(MouseEvent mouseEvent) {
        noaaButton.setEffect(new Glow(.25)); }

    @FXML
    private void noaaExit(MouseEvent mouseEvent) {
        noaaButton.setEffect(new Glow(.0)); }

    @FXML
    private void noaaPressed(MouseEvent mouseEvent) {
        noaaButton.setEffect(new Glow(.80)); }
    @FXML
    private void noaaReleased(MouseEvent mouseEvent) {
        noaaButton.setEffect(new Glow(.25));
        WebEngine engine = webViewer.getEngine();
        engine.load("https://www.weather.gov/");
        instructionBox.setOpacity(0);
        instructions.setText("");
        webViewer.setOpacity(100);

    }



    @FXML
    private void farmersEnter(MouseEvent mouseEvent) {
        farmersButton.setEffect(new Glow(.25)); }

    @FXML
    private void farmersExit(MouseEvent mouseEvent) {
        farmersButton.setEffect(new Glow(.0)); }

    @FXML
    private void farmersPressed(MouseEvent mouseEvent) {
        farmersButton.setEffect(new Glow(.80)); }

    @FXML
    private void farmersReleased(MouseEvent mouseEvent) {
        farmersButton.setEffect(new Glow(.0));
        WebEngine engine = webViewer.getEngine();
        engine.load("https://www.farmersalmanac.com/");
        instructionBox.setOpacity(0);
        instructions.setText("");
        webViewer.setOpacity(100);
    }



    @FXML
    private void wunderButtonEnter(MouseEvent mouseEvent) {
        wunderButton.setEffect(new Glow(.25)); }

    @FXML
    private void wunderButtonExit(MouseEvent mouseEvent) {
        wunderButton.setEffect(new Glow(.0)); }

    @FXML
    private void wunderButtonPressed(MouseEvent mouseEvent) {
        wunderButton.setEffect(new Glow(.80)); }

    @FXML
    private void wunderButtonReleased(MouseEvent mouseEvent) {
        wunderButton.setEffect(new Glow(.0));
        WebEngine engine = webViewer.getEngine();
        engine.load("https://www.wunderground.com/");
        instructionBox.setOpacity(0);
        instructions.setText("");
        webViewer.setOpacity(100);
    }


    @FXML
    private void accuButtonEnter(MouseEvent mouseEvent) {
        accuButton.setEffect(new Glow(.0)); }

    @FXML
    private void accuButtonExit(MouseEvent mouseEvent) {
        accuButton.setEffect(new Glow(.25)); }

    @FXML
    private void accuButtonPressed(MouseEvent mouseEvent) {
        accuButton.setEffect(new Glow(.80)); }

    @FXML
    private void accuButtonReleased(MouseEvent mouseEvent) {
        accuButton.setEffect(new Glow(.0));
        WebEngine engine = webViewer.getEngine();
        engine.load("https://www.accuweather.com/");
        instructionBox.setOpacity(0);
        instructions.setText("");
        webViewer.setOpacity(100);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webViewer.setOpacity(0);
        instructions.setText("""
                 mAH d00d picks a weather website listed to left. Or hoses... 0_0.
                
                Resizing window will restart session. mAH d00d has been notified.
                """);

        if (WeatherController.isMaxed()) {
            windyButton.setScaleX(1.12);
            windyButton.setScaleY(1.12);
            windyButton.setTranslateX(12);
            windyButton.setTranslateY(80);

            noaaButton.setScaleX(1.12);
            noaaButton.setScaleY(1.12);
            noaaButton.setTranslateX(12);
            noaaButton.setTranslateY(120);

            farmersButton.setScaleX(1.12);
            farmersButton.setScaleY(1.12);
            farmersButton.setTranslateX(12);
            farmersButton.setTranslateY(160);

            wunderButton.setScaleX(1.12);
            wunderButton.setScaleY(1.12);
            wunderButton.setTranslateX(12);
            wunderButton.setTranslateY(200);

            accuButton.setScaleX(1.12);
            accuButton.setScaleY(1.12);
            accuButton.setTranslateX(12);
            accuButton.setTranslateY(240);

            webViewer.setTranslateY(50);
            webViewer.setPrefWidth(1500);
            webViewer.setPrefHeight(900);
            webViewer.setOpacity(0);

            instructionBox.setTranslateY(50);
            instructionBox.setWidth(1500);
            instructionBox.setHeight(900);
            instructionBox.setOpacity(100);

            instructions.setFont(Font.font(28));
            instructions.setPrefWidth(1500);
            instructions.setPrefHeight(900);


        }
    }
}
