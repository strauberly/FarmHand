package main.Weather.instruments;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.Weather.weatherdb.WDBHourly;
import main.Weather.weatherdb.WeatherDB;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Objects;

public class InstrumentsController {
    @FXML
    private TextField pressureField;
    @FXML
    private TextField tempField;
    @FXML
    private TextField windField;
    @FXML
    private TextField humidityField;
    @FXML
    private Button logButton;
    @FXML
    private Button observationsButton;


    public void logButtonEnter(MouseEvent mouseEvent) {
        logButton.setEffect(new Glow(.25)); }
    public void logButtonExited(MouseEvent mouseEvent) {
        logButton.setEffect(new Glow(.0));  }
    public void logButtonPressed(MouseEvent mouseEvent) {
        logButton.setEffect(new Glow(.80));
        WDBHourly.setTimeStamp(System.currentTimeMillis());
    }

    public void logButtonReleased(MouseEvent mouseEvent) throws InterruptedException {
        logButton.setEffect(new Glow(.0));
        WDBHourly.setPressure(pressureField.getText());
        WDBHourly.setTemp(tempField.getText());
        WDBHourly.setWind(windField.getText());
        WDBHourly.setHumidity(humidityField.getText());
        WeatherDB.log();
    }


    public void observationsButtonEnter(MouseEvent mouseEvent) {
        observationsButton.setEffect(new Glow(.25)); }
    public void observationsButtonExit(MouseEvent mouseEvent) {
        observationsButton.setEffect(new Glow(.0));  }
    public void observationsButtonPressed(MouseEvent mouseEvent) {
        observationsButton.setEffect(new Glow(.80)); }
    public void observationsButtonReleased(MouseEvent mouseEvent) throws IOException {
        observationsButton.setEffect(new Glow(0.0));
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/main/Weather/instruments/Observations.fxml")));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

}
