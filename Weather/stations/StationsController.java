package main.Weather.stations;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import main.Weather.WeatherController;
import main.Weather.observations.Observations;
import main.Weather.weatherdb.WDBHourly;
import main.Weather.weatherdb.WeatherDB;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

// possibly play with opacity setting on button clicks for elements and gaining desired effects
public class StationsController implements Initializable{
    private static String lat;
    private static String longi;
    public TextField longitudeField;
    public TextField latitudeField;

    @FXML
    public Label observationsText = new Label();
    @FXML
    public Label errorText = new Label();
    @FXML
    private Button getReadOut;
    @FXML
    private Button log;
    @FXML
    public Button viewLog;
    @FXML
    public Button observationsButton;
    @FXML
    public Label stationOutput = new Label();

    //button handling
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
    }
// validates input and if legit kicks off new thread in case connection or return is slow-going.
    public void readOutButtonReleased(MouseEvent mouseEvent) throws IOException {
        getReadOut.setEffect(new Glow(.0));
        if ((!latitudeField.getText().isEmpty() && WeatherController.stationsValidInput(latitudeField.getText()) && Double.parseDouble(latitudeField.getText()) >= -90 && Double.parseDouble(latitudeField.getText()) <= 90)
                && (!longitudeField.getText().isEmpty() && WeatherController.stationsValidInput(longitudeField.getText()) && Double.parseDouble(longitudeField.getText()) >= -180 && Double.parseDouble(latitudeField.getText())
                <= 180)){
            Runnable task = () -> Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        stationOutput.setText(Stations.getConditions());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            new Thread(task).start();
        }else
        WeatherController.setWeatherScene("StationsInputError", mouseEvent);
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
    //validates before logging
    public void logButtonReleased(MouseEvent mouseEvent) throws IOException, SQLException {
        getReadOut.setEffect(new Glow(.0));
        if ((!latitudeField.getText().isEmpty() && WeatherController.stationsValidInput(latitudeField.getText()) && Double.parseDouble(latitudeField.getText()) >= -90 && Double.parseDouble(latitudeField.getText()) <= 90)
                && (!longitudeField.getText().isEmpty() && WeatherController.stationsValidInput(longitudeField.getText()) && Double.parseDouble(longitudeField.getText()) >= -180 && Double.parseDouble(latitudeField.getText())
                <= 180)){
            Stations.apiLog();
            stationOutput.setText("Conditions are logged, mAH d00d.");
        }else
            WeatherController.setWeatherScene("StationsInputError", mouseEvent);
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
        WeatherController.setWeatherScene("StationsDBDisplay", mouseEvent);
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
        WeatherController.setWeatherScene("StationsObservations", mouseEvent);
    }

// getters setters
    private void setLat(String lat) {
        StationsController.lat = lat;
    }

    private void setLongi(String longi) {
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
        int hourlyEntries = 0;
        if (!(WeatherDB.getID("hourly_id_", "hourly", hourlyEntries) > 2)) {
            observationsText.setText("mAH d00d needs at least 3 hourly entries for observations. ");
        } else
            observationsText.setText(Observations.weatherReport());
    stationOutput.setText("Enter your lat and long, mAH d00d.");
    }
}




