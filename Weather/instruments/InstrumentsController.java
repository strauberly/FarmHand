package main.Weather.instruments;

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
import java.sql.*;
import java.util.ResourceBundle;

public class InstrumentsController implements Initializable {
    @FXML
    public Label errorText = new Label();
    @FXML
    private Label observationsText = new Label();

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
    }

    //buttons and fields
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
        logButton.setEffect(new Glow(.25));
    }

    public void logButtonExited(MouseEvent mouseEvent) {
        logButton.setEffect(new Glow(.0));
    }

    public void logButtonPressed(MouseEvent mouseEvent) throws IOException {
        logButton.setEffect(new Glow(.80));
        WDBHourly.setTimeStamp(System.currentTimeMillis());
    }

    // attempts to validate input before logging to db
        public void logButtonReleased (MouseEvent mouseEvent) throws IOException, SQLException {
            logButton.setEffect(new Glow(.0));
            if ((!pressureField.getText().isEmpty() && WeatherController.instrumentsValidInput(pressureField.getText()) &&
                    (!tempField.getText().isEmpty() && WeatherController.instrumentsValidInput(tempField.getText())  &&
                            (!windField.getText().isEmpty() && WeatherController.instrumentsValidInput(windField.getText()) &&
                                    (!humidityField.getText().isEmpty() && WeatherController.instrumentsValidInput(humidityField.getText())))))) {
                WDBHourly.setPressureFieldValue(pressureField.getText());
                WDBHourly.setTemperatureFieldValue(tempField.getText());
                WDBHourly.setWindFieldValue(windField.getText());
                WDBHourly.setHumidityFieldValue(humidityField.getText());
                WeatherDB.log();
                WeatherController.setWeatherScene("InstrumentsDBDisplay", mouseEvent);
            }else
                WeatherController.setWeatherScene("InstrumentsInputError", mouseEvent);
            }



        public void observationsButtonEnter (MouseEvent mouseEvent){
            observationsButton.setEffect(new Glow(.25));
        }

        public void observationsButtonExit (MouseEvent mouseEvent){
            observationsButton.setEffect(new Glow(.0));
        }

        public void observationsButtonPressed (MouseEvent mouseEvent){
            observationsButton.setEffect(new Glow(.80));
        }

        public void observationsButtonReleased (MouseEvent mouseEvent) throws IOException {
            observationsButton.setEffect(new Glow(0.0));
            WeatherController.setWeatherScene("InstrumentsObservations", mouseEvent);
        }
    }




