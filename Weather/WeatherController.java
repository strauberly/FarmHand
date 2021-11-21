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


public class WeatherController {
    public Label WeatherHeader;
    @FXML
    public Label instructions;

// scenes by calling from weather class and eliminate local development
// play with running set scene from here or elmininating additional class leaning towards eliminating additional class


    @FXML
    public Button weatherMinButton;

    @FXML
    public void weatherMinEnter(MouseEvent mouseEvent) {
        weatherMinButton.setEffect(new Glow(.25));
    }

    @FXML
    public void weatherMinExited(MouseEvent mouseEvent) {
        weatherMinButton.setEffect(new Glow(.0));
    }

    @FXML
    public void weatherMinPressed(MouseEvent mouseEvent) {
        weatherMinButton.setEffect(new Glow(.80));

    }

    public void weatherMinReleased(MouseEvent mouseEvent) {
        weatherMinButton.setEffect(new Glow(.0));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }


    public Button weatherCloseButton;

    public void weatherCloseEnter(MouseEvent mouseEvent) {
        weatherCloseButton.setEffect(new Glow(.25));
    }

    public void weatherCloseExit(MouseEvent mouseEvent) {
        weatherCloseButton.setEffect(new Glow(.0));
    }

    public void weatherClosePressed(MouseEvent mouseEvent) {
        weatherCloseButton.setEffect(new Glow(.80));
    }

    public void weatherCloseReleased(MouseEvent mouseEvent) {
        weatherCloseButton.setEffect(new Glow(.0));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }


    public Button stationsButton;

    public void stationsEntered(MouseEvent mouseEvent) {
        stationsButton.setEffect(new Glow(.25));
    }

    public void stationsExited(MouseEvent mouseEvent) {
        stationsButton.setEffect(new Glow(.0));
    }

    public void stationsPressed(MouseEvent mouseEvent) {
        stationsButton.setEffect(new Glow(.80));
    }

    public void stationsReleased(MouseEvent mouseEvent) throws IOException {
        stationsButton.setEffect(new Glow(.0));
        setWeatherScene("Stations", mouseEvent);
    }


    @FXML
    public Button instrumentsButton;

    @FXML
    public void instrumentsEnter(MouseEvent mouseEvent) {
        instrumentsButton.setEffect(new Glow(.25));
    }

    @FXML
    public void instrumentsExited(MouseEvent mouseEvent) {
        instrumentsButton.setEffect(new Glow(.0));
    }

    @FXML
    public void instrumentsPressed(MouseEvent mouseEvent) {
        instrumentsButton.setEffect(new Glow(0.80));
    }

    @FXML
    public void instrumentsReleased(MouseEvent mouseEvent) throws IOException {
        instrumentsButton.setEffect(new Glow(0.0));
        setWeatherScene("DBDisplayInstruments", mouseEvent);
    }


    public Button webButton;

    public void webEnter(MouseEvent mouseEvent) {
        webButton.setEffect(new Glow(.25));
    }

    public void webExited(MouseEvent mouseEvent) {
        webButton.setEffect(new Glow(.0));
    }

    public void webPressed(MouseEvent mouseEvent) {
        webButton.setEffect(new Glow(.80));
    }

    public void webReleased(MouseEvent mouseEvent) throws IOException {
        webButton.setEffect(new Glow(0.0));
        setWeatherScene("WebServices", mouseEvent);
    }


        public static void setWeatherScene (String fileName, MouseEvent mouseEvent) throws IOException {
            final double[] xOffset = {0};
            final double[] yOffset = {0};
            Parent root = FXMLLoader.load(Objects.requireNonNull(Weather.class.getResource("/main/Weather/FXML/" + fileName + ".fxml")));
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

    }

