package main.Weather.webServices;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


// Changes web-viewer displays through press of buttons to display various weather services found online.
// Useful for comparing with observations
    public class WebServicesController {
    @FXML
    private WebView webViewer;
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
    }
}
