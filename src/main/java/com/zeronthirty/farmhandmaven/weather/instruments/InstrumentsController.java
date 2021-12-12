package com.zeronthirty.farmhandmaven.weather.instruments;

import com.zeronthirty.farmhandmaven.weather.observations.Observations;
import com.zeronthirty.farmhandmaven.weather.weatherdb.WDBHourly;
import com.zeronthirty.farmhandmaven.weather.weatherdb.WeatherDB;
import com.zeronthirty.farmhandmaven.weather.WeatherController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

//takes input from user, logs to database and pulls observations based on entries
//intended to be used with manual gauges in case of loss of internet signal
public class InstrumentsController implements Initializable {

    public Rectangle observationsSquare = new Rectangle();

    @FXML
    private Label errorText = new Label();
    @FXML
    private Rectangle errorSquare = new Rectangle();
    @FXML
    private Label observationsText = new Label();
    @FXML
    private TextField pressureField = new TextField();
    @FXML
    private TextField tempField = new TextField();
    @FXML
    private TextField windField = new TextField();
    @FXML
    private TextField humidityField = new TextField();
    @FXML
    private Button logButton = new Button();
    @FXML
    private Button observationsButton = new Button();


    public void logButtonEnter() {
        logButton.setEffect(new Glow(.25));
    }

    public void logButtonExited() {
        logButton.setEffect(new Glow(.0));
    }

    public void logButtonPressed() throws IOException {
        logButton.setEffect(new Glow(.80));
        WDBHourly.setTimeStamp(System.currentTimeMillis());
    }

    // attempts to validate input before logging to db
    public void logButtonReleased (MouseEvent mouseEvent) throws IOException, SQLException {
        logButton.setEffect(new Glow(.0));
        if ((!pressureField.getText().isEmpty() && instrumentsValidInput(pressureField.getText()) &&
                (!tempField.getText().isEmpty() && instrumentsValidInput(tempField.getText())  &&
                        (!windField.getText().isEmpty() && instrumentsValidInput(windField.getText()) &&
                                (!humidityField.getText().isEmpty() && instrumentsValidInput(humidityField.getText())))))) {
            WDBHourly.setPressureFieldValue(pressureField.getText());
            WDBHourly.setTemperatureFieldValue(tempField.getText());
            WDBHourly.setWindFieldValue(windField.getText());
            WDBHourly.setHumidityFieldValue(humidityField.getText());
            WeatherDB.log();
            WeatherController.setWeatherScene("instrumentsDBDisplay.fxml", mouseEvent);
        }else
            WeatherController.setWeatherScene("instrumentsInputError.fxml", mouseEvent);
    }


    public void observationsButtonEnter(){
        observationsButton.setEffect(new Glow(.25));
    }

    public void observationsButtonExit(){
        observationsButton.setEffect(new Glow(.0));
    }

    public void observationsButtonPressed(){
        observationsButton.setEffect(new Glow(.80));
    }

    public void observationsButtonReleased (MouseEvent mouseEvent) throws IOException {
        observationsButton.setEffect(new Glow(0.0));
        WeatherController.setWeatherScene("instrumentsObservations.fxml", mouseEvent);
    }


    @Override
    public void initialize(URL Location, ResourceBundle resources) {
        int hourlyEntries = 0;
        if (!(WeatherDB.getID("hourly_id_", "hourly", hourlyEntries) > 2)) {
            observationsText.setText("mAH d00d needs at least 3 hourly entries for observations. ");
        } else
            observationsText.setText(Observations.weatherReport());
        errorText.setText("""
                mAH d00d must enter ONLY digits, in ALL the fields.

                Try again dumb-dumb.""");
        if (WeatherController.isMaxed()) {

            errorSquare.setWidth(1500);
            errorSquare.setHeight(900);

            observationsSquare.setWidth(1500);
            observationsSquare.setHeight(900);

            errorText.setPrefWidth(1500);
            errorText.setPrefHeight(900);
            errorText.setFont(Font.font(30));
            errorText.setLayoutX(350);

            observationsText.setPrefWidth(1500);
            observationsText.setPrefHeight(900);
            observationsText.setFont(Font.font(30));
            observationsText.setLayoutX(350);


            pressureField.setPrefWidth(240);
            pressureField.setPrefHeight(80);
            pressureField.setFont(Font.font(22));

            tempField.setPrefWidth(240);
            tempField.setPrefHeight(80);
            tempField.setFont(Font.font(22));
            tempField.setTranslateY(25);

            windField.setPrefWidth(240);
            windField.setPrefHeight(60);
            windField.setFont(Font.font(22));
            windField.setTranslateY(50);

            humidityField.setPrefWidth(240);
            humidityField.setPrefHeight(80);
            humidityField.setFont(Font.font(22));
            humidityField.setTranslateY(75);

            logButton.setFont(Font.font(30));
            logButton.setTranslateY(75);

            observationsButton.setFont(Font.font(30));
            observationsButton.setTranslateY(100);
        }
    }

    public static boolean instrumentsValidInput(String s) {
        for (int i = 0; i < s.length(); i++)
            if (!Character.isDigit(s.charAt(i)))
                return false;
        return true;
    }
}




