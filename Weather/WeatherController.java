package main.Weather;

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
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.awt.Color.*;
import static java.awt.Font.*;
import static javafx.scene.paint.Color.TRANSPARENT;
import static javafx.scene.text.Font.font;

public class WeatherController implements Initializable {
    public Rectangle instructionsSquare = new Rectangle();
    @FXML
    protected Label instructions = new Label();
    @FXML
    protected Label weatherBannerText;
    @FXML
    protected static StackPane stack = new StackPane();
    @FXML
    private Rectangle weatherBanner = new Rectangle();
    @FXML
    private Button weatherMinButton = new Button();
    @FXML
    private Button weatherCloseButton = new Button();
    @FXML
    public Button weatherMaxButton = new Button();
    @FXML
    private Button stationsButton = new Button();
    @FXML
    private Button instrumentsButton = new Button();
    @FXML
    private Button webButton = new Button();


    private static String savedScene = "WeatherInstructions";

    Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();

    private static boolean maxed = false;



    @FXML
    private void weatherMinEnter() {
        weatherMinButton.setEffect(new Glow(.25));
    }

    @FXML
    private void weatherMinExited() {
        weatherMinButton.setEffect(new Glow(.0));
    }

    @FXML
    private void weatherMinPressed() {
        weatherMinButton.setEffect(new Glow(.80));

    }

    @FXML
    private void weatherMinReleased(MouseEvent mouseEvent) {
        weatherMinButton.setEffect(new Glow(.0));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void weatherMaxEnter() {
        weatherMaxButton.setEffect(new Glow(.25));
    }

    @FXML
    private void weatherMaxExited() {
        weatherMaxButton.setEffect(new Glow(.0));
    }

    @FXML
    private void weatherMaxPressed() {
    }

    @FXML
    private void weatherMaxReleased(MouseEvent mouseEvent) throws IOException {
        maxedWeatherElements(mouseEvent);
    }


    @FXML
    private void weatherCloseEnter() {
        weatherCloseButton.setEffect(new Glow(.25));
    }

    @FXML
    private void weatherCloseExit() {
        weatherCloseButton.setEffect(new Glow(.0));
    }

    @FXML
    private void weatherClosePressed() {
        weatherCloseButton.setEffect(new Glow(.80));
    }

    @FXML
    private void weatherCloseReleased(MouseEvent mouseEvent) {
        maxed = false;
        weatherCloseButton.setEffect(new Glow(.0));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }


    @FXML
    private void stationsEntered() {
        stationsButton.setEffect(new Glow(.25));
    }

    @FXML
    private void stationsExited() {
        stationsButton.setEffect(new Glow(.0));
    }

    @FXML
    private void stationsPressed() {
        stationsButton.setEffect(new Glow(.80));
    }

    @FXML
    private void stationsReleased(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stationsButton.setEffect(new Glow(.0));
        if (stage.isMaximized())
            maxed = true;
        setWeatherScene("Stations", mouseEvent);
    }



    @FXML
    private void instrumentsEnter() {
        instrumentsButton.setEffect(new Glow(.25));
    }

    @FXML
    private void instrumentsExited() {
        instrumentsButton.setEffect(new Glow(.0));
    }

    @FXML
    private void instrumentsPressed() {
        instrumentsButton.setEffect(new Glow(0.80));
    }

    @FXML
    private void instrumentsReleased(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        instrumentsButton.setEffect(new Glow(0.0));
        setWeatherScene("InstrumentsDBDisplay", mouseEvent);
        if (stage.isMaximized())
            maxed = true;
    }



    @FXML
    private void webEnter() {
        webButton.setEffect(new Glow(.25));
    }

    @FXML
    private void webExited() {
        webButton.setEffect(new Glow(.0));
    }

    @FXML
    private void webPressed() {
        webButton.setEffect(new Glow(.80));
    }

    @FXML
    private void webReleased(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        setWeatherScene("WebServices", mouseEvent);
        if (stage.isMaximized())
            maxed = true;
    }


    public static void setWeatherScene (String fileName, MouseEvent mouseEvent) throws IOException {
        final double[] xOffset = {0};
        final double[] yOffset = {0};
        Parent root = FXMLLoader.load(Objects.requireNonNull(WeatherController.class.getResource("/main/Weather/FXML/" + fileName + ".fxml")));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.setFill(TRANSPARENT);
        stage.setScene(scene);
        stage.show();

        root.setOnMousePressed(event -> {
            xOffset[0] = event.getSceneX();
            yOffset[0] = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset[0]);
            stage.setY(event.getScreenY() - yOffset[0]);

        });
        savedScene = fileName;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instructions.setText("""
                                                                           Hulloooooo, mAh d00d!
                                    
                                                                             Select from above:
                                    
                Stations:
                Input your lat long and get a readout from a weather station,
                log the readout to a database, view the database, and get observations based on the readouts stored.
                                    
                                    
                Web Services:
                View different weather pages on the net. Helpful in seeing whats coming long term.
                                    
                                    
                Instruments:
                Allows you to log data from manual gauges and get observations.
                You know... in case zombies.

                """);

        if (maxed) {
            weatherBanner.setWidth(displaySize.getWidth());
            stationsButton.setTranslateX(200);
            webButton.setTranslateX(400);
            instrumentsButton.setTranslateX(600);
            maxed = true;

            instructions.setFont(font(28));
            instructions.setPrefHeight(900);
            instructions.setPrefWidth(1500);
            instructions.setTranslateX(170);

            instructionsSquare.setHeight(900);
            instructionsSquare.setWidth(1500);
            instructionsSquare.setTranslateX(240);
        }
    }

    //behaviour if stage is or isn't maxed used by max/resize button
    public void maxedWeatherElements(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        weatherMaxButton.setEffect(new Glow(.0));
        if (!stage.isMaximized()) {
            stage.setMaximized(true);
            maxed = true;
        }else if ((stage.isMaximized()&& savedScene.equals("WeatherInstructions"))){
            stage.setMaximized(false);
            maxed = false;
        }else if (stage.isMaximized()){
            stage.setMaximized(false);
            maxed = false;
        }
        setWeatherScene(savedScene,mouseEvent);
    }

    public static boolean isMaxed() {
        return maxed;
    }

}
