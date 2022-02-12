package com.zeronthirty.farmhandmaven.weather.stations;

import com.zeronthirty.farmhandmaven.weather.WeatherController;
import com.zeronthirty.farmhandmaven.weather.observations.Observations;
import com.zeronthirty.farmhandmaven.weather.weatherdb.WDBHourly;
import com.zeronthirty.farmhandmaven.weather.weatherdb.WeatherDB;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StationsController implements Initializable {

    private static String lat;
    private static String longi;

    @FXML
    public TextField longitudeField = new TextField();
    @FXML
    public TextField latitudeField = new TextField();
    @FXML
    public Rectangle readoutBox = new Rectangle();
    @FXML
    private Button getReadOut = new Button();
    @FXML
    private Button log = new Button();
    @FXML
    public Button viewLog = new Button();
    @FXML
    public Button observationsButton = new Button();
    @FXML
    public Label stationOutput = new Label();

    public String savedReadout = String.valueOf(stationOutput);


    public void readOutButtonEnter() {
        getReadOut.setEffect(new Glow(.25));
    }

    public void readOutButtonExit() {
        getReadOut.setEffect(new Glow(.0));
    }

    public void readOutButtonPressed() {
        getReadOut.setEffect(new Glow(.80));
        setLat(latitudeField.getText());
        setLongi(longitudeField.getText());
    }

    // validates input and if legit kicks off new thread in case connection or return is slow-going.
    public void readOutButtonReleased() {
        getReadOut.setEffect(new Glow(.0));
        if ((!latitudeField.getText().isEmpty() && stationsValidInput(latitudeField.getText()) && Double.parseDouble(latitudeField.getText()) >= -90 && Double.parseDouble(latitudeField.getText()) <= 90)
                && (!longitudeField.getText().isEmpty() && stationsValidInput(longitudeField.getText()) && Double.parseDouble(longitudeField.getText()) >= -180 && Double.parseDouble(latitudeField.getText())
                <= 180)) {
            Runnable task = () -> Platform.runLater(() -> {
                try {
                    stationOutput.setText(Stations.getConditions());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            new Thread(task).start();
            savedReadout = String.valueOf(stationOutput);
        } else
            stationOutput.setText("Either mAH d00d's lat and long are mixed up or..." + "\n" +
                    "mAH d00d must enter ONLY digits, in ALL the fields.");
        stationOutput.setAlignment(Pos.TOP_CENTER);
        if (WeatherController.isMaxed()) {
            stationOutput.setTranslateY(150);
            stationOutput.setTextAlignment(TextAlignment.CENTER);
        }
    }


    public void logButtonEnter() {
        log.setEffect(new Glow(.25));
    }

    public void logButtonExit() {
        log.setEffect(new Glow(.0));
    }

    public void logButtonPressed() {
        log.setEffect(new Glow(.80));
        WDBHourly.setTimeStamp(System.currentTimeMillis());
    }

    //validates before logging
    public void logButtonReleased() throws SQLException {
        getReadOut.setEffect(new Glow(.0));
        if ((!latitudeField.getText().isEmpty() && stationsValidInput(latitudeField.getText()) && Double.parseDouble(latitudeField.getText()) >= -90 && Double.parseDouble(latitudeField.getText()) <= 90)
                && (!longitudeField.getText().isEmpty() && stationsValidInput(longitudeField.getText()) && Double.parseDouble(longitudeField.getText()) >= -180 && Double.parseDouble(latitudeField.getText())
                <= 180)) {
            Stations.apiLog();
            stationOutput.setText("Conditions are logged, mAH d00d.");
        } else
            stationOutput.setText("""
                    mAH d00d has not entered the digits.
                     
                    Behold...!

                    There is nothing to log. Try again dumb-dumb.""");
    }


    public void viewLogButtonEnter() {
        log.setEffect(new Glow(.25));
    }

    public void viewLogButtonExit() {
        log.setEffect(new Glow(.0));
    }

    public void viewLogButtonPressed() {
        log.setEffect(new Glow(.80));

    }

    public void viewLogButtonReleased(MouseEvent mouseEvent) throws IOException {
        getReadOut.setEffect(new Glow(.0));
        WeatherController.setWeatherScene("stationsDBDisplay", mouseEvent);
    }


    public void observationsButtonEnter() {
        observationsButton.setEffect(new Glow(.25));
    }

    public void observationsButtonExit() {
        observationsButton.setEffect(new Glow(.0));
    }

    public void observationsButtonPressed() {
        observationsButton.setEffect(new Glow(.80));
    }

    public void observationsButtonReleased() {
        observationsButton.setEffect(new Glow(0.0));
        if (!(WeatherDB.getID("hourly_id_", "hourly") > 2)) {
            stationOutput.setText("mAH d00d needs at least 3 hourly entries for observations. ");
        } else
            stationOutput.setText(Observations.weatherReport());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stationOutput.setText("Enter your lat and long, mAH d00d.");
        stationOutput.setFont(Font.font(20));
        stationOutput.setTranslateY(60);
        if (WeatherController.isMaxed()) {
            readoutBox.setWidth(1500);
            readoutBox.setHeight(900);
            readoutBox.setTranslateX(50);

            stationOutput.setFont(Font.font(28));
            stationOutput.setPrefWidth(1500);
            stationOutput.setPrefHeight(900);
            stationOutput.setTranslateX(10);
            stationOutput.setTranslateY(200);

            latitudeField.setPrefWidth(240);
            latitudeField.setPrefHeight(80);
            latitudeField.setFont(Font.font(22));

            longitudeField.setPrefWidth(240);
            longitudeField.setPrefHeight(80);
            longitudeField.setFont(Font.font(22));
            longitudeField.setTranslateY(50);

            getReadOut.setFont(Font.font(25));
            getReadOut.setTranslateY(100);

            log.setFont(Font.font(25));
            log.setTranslateY(150);

            viewLog.setFont(Font.font(25));
            viewLog.setTranslateY(200);

            observationsButton.setFont(Font.font(25));
            observationsButton.setTranslateY(250);
        }
    }

    private boolean stationsValidInput(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (Exception e) {
            return false;
        }
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

}