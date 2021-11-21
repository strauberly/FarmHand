package main.Weather.stations;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import main.Weather.WeatherController;
import main.Weather.weatherdb.WDBHourly;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class StationsController implements Initializable{


    private static String lat;
    private static String longi;
    public TextField longitudeField;
    public TextField latitudeField;

    @FXML
    public Button getReadOut;
    @FXML
    public Button log;
    @FXML
    public Button viewLog;
    @FXML
    public Button observationsButton;
    @FXML
    public Label stationOutput = new Label();
    @FXML
    public Label stationOutputTest = new Label();



    public void readOutButtonEnter(MouseEvent mouseEvent) {
        getReadOut.setEffect(new Glow(.25));
    }

    public void readOutButtonExit(MouseEvent mouseEvent) {
        getReadOut.setEffect(new Glow(.0));
    }

    public void readOutButtonPressed(MouseEvent mouseEvent) throws IOException {
        getReadOut.setEffect(new Glow(.80));
        setLat(latitudeField.getText());
        setLongi(longitudeField.getText());
//        Weather.setWeatherScene("Stations",mouseEvent);
    }

    public void readOutButtonReleased(MouseEvent mouseEvent) throws IOException {
        getReadOut.setEffect(new Glow(.0));
        stationOutput.setText(Stations.getConditions());
//       stationOutput.setText(Stations.getOutput());
//        System.out.println(Stations.getOutput());
//        stationReading.setText(Stations.getOutput());

    }

    public void logButtonEnter(MouseEvent mouseEvent) {
        log.setEffect(new Glow(.25));
    }

    public void logButtonExit(MouseEvent mouseEvent) {
        log.setEffect(new Glow(.0));
    }

    public void logButtonPressed(MouseEvent mouseEvent) throws IOException {
        log.setEffect(new Glow(.80));
        WDBHourly.setTimeStamp(System.currentTimeMillis());
    }
    public void logButtonReleased(MouseEvent mouseEvent) throws IOException {
        getReadOut.setEffect(new Glow(.0));
        Stations.apiLog();
        stationOutput.setText("Conditions are logged, mAH d00d.");
    }

    public void viewLogButtonEnter(MouseEvent mouseEvent) {
        log.setEffect(new Glow(.25));
    }

    public void viewLogButtonExit(MouseEvent mouseEvent) {
        log.setEffect(new Glow(.0));
    }

    public void viewLogButtonPressed(MouseEvent mouseEvent) throws IOException {
        log.setEffect(new Glow(.80));

    }

    public void viewLogButtonReleased(MouseEvent mouseEvent) throws IOException {
        getReadOut.setEffect(new Glow(.0));
        WeatherController.setWeatherScene("DBDisplayStations", mouseEvent);
    }


    public void observationsButtonEnter(MouseEvent mouseEvent) {
        observationsButton.setEffect(new Glow(.25));
    }

    public void observationsButtonExit(MouseEvent mouseEvent) {
        observationsButton.setEffect(new Glow(.0));
    }

    public void observationsButtonPressed(MouseEvent mouseEvent) {
        observationsButton.setEffect(new Glow(.80));
    }

    public void observationsButtonReleased(MouseEvent mouseEvent) throws IOException {
        observationsButton.setEffect(new Glow(0.0));
        WeatherController.setWeatherScene("ObservationsStations", mouseEvent);
    }


    public void setLat(String lat) {
        StationsController.lat = lat;
    }

    public void setLongi(String longi) {
        StationsController.longi = longi;
    }

    public static String getLat() {
        return lat;
    }

    public static String getLongi() {
        return longi;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    stationOutput.setText("Enter your lat and long, mAH d00d.");
    }

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//    stationOutput = new Label();
//
//    }
}




