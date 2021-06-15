package main.Weather.webServices;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;



public class WebServicesController {
    @FXML
    public WebView webViewer;
    public Button windyButton;
    public Button noaaButton;
    public Button farmersButton;
    public Button wunderButton;
    public Button accuButton;




    public void windyEnter(MouseEvent mouseEvent) {
        windyButton.setEffect(new Glow(.25)); }

    public void windyExit(MouseEvent mouseEvent) {
        windyButton.setEffect(new Glow(.0)); }

    public void windyPressed(MouseEvent mouseEvent) {
        windyButton.setEffect(new Glow(.80)); }
@FXML
    public void windyReleased(MouseEvent mouseEvent) {
        windyButton.setEffect(new Glow(.0));
       WebEngine engine = webViewer.getEngine();
       engine.load("https://www.windy.com");
    }



    public void noaaEnter(MouseEvent mouseEvent) {
        noaaButton.setEffect(new Glow(.25)); }

    public void noaaExit(MouseEvent mouseEvent) {
        noaaButton.setEffect(new Glow(.0)); }

    public void noaaPressed(MouseEvent mouseEvent) {
        noaaButton.setEffect(new Glow(.80)); }
@FXML
    public void noaaReleased(MouseEvent mouseEvent) {
        noaaButton.setEffect(new Glow(.25));
        WebEngine engine = webViewer.getEngine();
        engine.load("https://www.weather.gov/");
    }



    public void farmersEnter(MouseEvent mouseEvent) {
        farmersButton.setEffect(new Glow(.25)); }

    public void farmersExit(MouseEvent mouseEvent) {
        farmersButton.setEffect(new Glow(.0)); }

    public void farmersPressed(MouseEvent mouseEvent) {
        farmersButton.setEffect(new Glow(.80)); }

    public void farmersReleased(MouseEvent mouseEvent) {
        farmersButton.setEffect(new Glow(.0));
        WebEngine engine = webViewer.getEngine();
        engine.load("https://www.farmersalmanac.com/");
    }



    public void wunderButtonEnter(MouseEvent mouseEvent) {
        wunderButton.setEffect(new Glow(.25)); }

    public void wunderButtonExit(MouseEvent mouseEvent) {
        wunderButton.setEffect(new Glow(.0)); }

    public void wunderButtonPressed(MouseEvent mouseEvent) {
        wunderButton.setEffect(new Glow(.80)); }

    @FXML
    public void wunderButtonReleased(MouseEvent mouseEvent) {
        wunderButton.setEffect(new Glow(.0));
        WebEngine engine = webViewer.getEngine();
        engine.load("https://www.wunderground.com/");
    }


    @FXML
    public void accuButtonEnter(MouseEvent mouseEvent) {
        accuButton.setEffect(new Glow(.0)); }

    public void accuButtonExit(MouseEvent mouseEvent) {
        accuButton.setEffect(new Glow(.25)); }

    public void accuButtonPressed(MouseEvent mouseEvent) {
        accuButton.setEffect(new Glow(.80)); }

    public void accuButtonReleased(MouseEvent mouseEvent) {
        accuButton.setEffect(new Glow(.0));
        WebEngine engine = webViewer.getEngine();
        engine.load("https://www.accuweather.com/");
    }




}
