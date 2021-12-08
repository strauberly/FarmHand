package main.Weather.WebServices;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import main.Weather.WeatherController;

import java.net.URL;
import java.util.ResourceBundle;


// Changes web-viewer displays through press of buttons to display various weather services found online.
// Useful for comparing with observations
//possible future implementations might include:
//allowing user to set their own sites.
// saving address user is on so if window is resized they don't have to start over.

    public class WebServicesController implements Initializable {

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
        private void windyEnter() {
            windyButton.setEffect(new Glow(.25)); }

        @FXML
        private void windyExit() {
            windyButton.setEffect(new Glow(.0)); }

        @FXML
        private void windyPressed() {
            windyButton.setEffect(new Glow(.80)); }
        @FXML
        private void windyReleased() {
            setWebViewer("https://www.windy.com");
        }



        @FXML
        private void noaaEnter() {
            noaaButton.setEffect(new Glow(.25)); }

        @FXML
        private void noaaExit() {
            noaaButton.setEffect(new Glow(.0)); }

        @FXML
        private void noaaPressed() {
            noaaButton.setEffect(new Glow(.80)); }
        @FXML
        private void noaaReleased() {
            setWebViewer("https://www.weather.gov/");
        }



        @FXML
        private void farmersEnter() {
            farmersButton.setEffect(new Glow(.25)); }

        @FXML
        private void farmersExit() {
            farmersButton.setEffect(new Glow(.0)); }

        @FXML
        private void farmersPressed() {
            farmersButton.setEffect(new Glow(.80)); }

        @FXML
        private void farmersReleased() {
            setWebViewer("https://www.farmersalmanac.com/");
        }



        @FXML
        private void wunderButtonEnter() {
            wunderButton.setEffect(new Glow(.25)); }

        @FXML
        private void wunderButtonExit() {
            wunderButton.setEffect(new Glow(.0)); }

        @FXML
        private void wunderButtonPressed() {
            wunderButton.setEffect(new Glow(.80)); }

        @FXML
        private void wunderButtonReleased() {
            setWebViewer("https://www.wunderground.com/");
        }



        @FXML
        private void accuButtonEnter() {
            accuButton.setEffect(new Glow(.0)); }

        @FXML
        private void accuButtonExit() {
            accuButton.setEffect(new Glow(.25)); }

        @FXML
        private void accuButtonPressed() {
            accuButton.setEffect(new Glow(.80)); }

        @FXML
        private void accuButtonReleased() {
            setWebViewer("https://www.accuweather.com/");
        }


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            webViewer.toBack();
            webViewer.setOpacity(0);
            instructions.setText("""
                mAH d00d picks a weather website listed to left.
                Or mAh d00d gets the hose again... 0_0.
                
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


        public void setWebViewer(String webAddress) {
            WebEngine engine = webViewer.getEngine();
            engine.load(webAddress);
            instructionBox.setOpacity(0);
            instructions.setText("");
            webViewer.setOpacity(100);
            webViewer.toFront();
        }
    }

