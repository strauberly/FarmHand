package main.Weather;

import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.MainWindowController;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

//Besides managing main weather button actions, also contains method for quickly setting scenes.
public class WeatherController {

    @FXML
    private Button weatherMinButton;
    @FXML
    private Button weatherCloseButton;
    @FXML
    private Button stationsButton;
    @FXML
    private Button instrumentsButton;
    @FXML
    private Button webButton;


        @FXML
        private void weatherMinEnter(MouseEvent mouseEvent) {
            weatherMinButton.setEffect(new Glow(.25));
        }

        @FXML
        private void weatherMinExited(MouseEvent mouseEvent) {
            weatherMinButton.setEffect(new Glow(.0));
        }

        @FXML
        private void weatherMinPressed(MouseEvent mouseEvent) {
            weatherMinButton.setEffect(new Glow(.80));

        }

        @FXML
        private void weatherMinReleased(MouseEvent mouseEvent) {
            weatherMinButton.setEffect(new Glow(.0));
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setIconified(true);
        }



        @FXML
        private void weatherCloseEnter(MouseEvent mouseEvent) {
            weatherCloseButton.setEffect(new Glow(.25));
        }

        @FXML
        private void weatherCloseExit(MouseEvent mouseEvent) {
            weatherCloseButton.setEffect(new Glow(.0));
        }

        @FXML
        private void weatherClosePressed(MouseEvent mouseEvent) {
            weatherCloseButton.setEffect(new Glow(.80));
        }

        @FXML
        private void weatherCloseReleased(MouseEvent mouseEvent) {
            weatherCloseButton.setEffect(new Glow(.0));
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.close();
        }


        @FXML
        private void stationsEntered(MouseEvent mouseEvent) {
            stationsButton.setEffect(new Glow(.25));
        }

        @FXML
        private void stationsExited(MouseEvent mouseEvent) {
            stationsButton.setEffect(new Glow(.0));
        }

        @FXML
        private void stationsPressed(MouseEvent mouseEvent) {
            stationsButton.setEffect(new Glow(.80));
        }

        @FXML
        private void stationsReleased(MouseEvent mouseEvent) throws IOException {
            stationsButton.setEffect(new Glow(.0));
            setWeatherScene("Stations", mouseEvent);
        }



        @FXML
        private void instrumentsEnter(MouseEvent mouseEvent) {
            instrumentsButton.setEffect(new Glow(.25));
        }

        @FXML
        private void instrumentsExited(MouseEvent mouseEvent) {
            instrumentsButton.setEffect(new Glow(.0));
        }

        @FXML
        private void instrumentsPressed(MouseEvent mouseEvent) {
            instrumentsButton.setEffect(new Glow(0.80));
        }

        @FXML
        private void instrumentsReleased(MouseEvent mouseEvent) throws IOException {
            instrumentsButton.setEffect(new Glow(0.0));
            setWeatherScene("InstrumentsDBDisplay", mouseEvent);
        }



        @FXML
        private void webEnter(MouseEvent mouseEvent) {
            webButton.setEffect(new Glow(.25));
        }

        @FXML
        private void webExited(MouseEvent mouseEvent) {
            webButton.setEffect(new Glow(.0));
        }

        @FXML
        private void webPressed(MouseEvent mouseEvent) {
            webButton.setEffect(new Glow(.80));
        }

        @FXML
        private void webReleased(MouseEvent mouseEvent) throws IOException {
            webButton.setEffect(new Glow(0.0));
            setWeatherScene("WebServices", mouseEvent);
        }


        public static void setWeatherScene (String fileName, MouseEvent mouseEvent) throws IOException {
            final double[] xOffset = {0};
            final double[] yOffset = {0};
            Parent root = FXMLLoader.load(Objects.requireNonNull(WeatherController.class.getResource("/main/Weather/FXML/" + fileName + ".fxml")));
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.show();

            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset[0] = event.getSceneX();
                    yOffset[0] = event.getSceneY();
                }
            });

            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX() - xOffset[0]);
                    stage.setY(event.getScreenY() - yOffset[0]);

                }
            });
        }

        public static boolean instrumentsValidInput(String s) {
        for (int i = 0; i < s.length(); i++)
            if (!Character.isDigit(s.charAt(i)))
                return false;
        return true;
    }

    public static boolean stationsValidInput(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (Exception e){
            return false;
        }
    }

}

