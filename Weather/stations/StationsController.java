package main.Weather.stations;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import main.Weather.Weather;
import java.io.IOException;


public class StationsController {
    private static String lat;
    private static String longi;
    public TextField longitudeField;
    public TextField latitudeField;

    @FXML
    Button getReadOut;
    @FXML
    Label stationOutput;

    {
        stationOutput = new Label();
    }


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
        stationOutput.setText(Weather.getOutput());
    }

    public void readOutButtonReleased(MouseEvent mouseEvent) throws IOException {
        getReadOut.setEffect(new Glow(.0));
        System.out.println("sup?");
        setLat(null);
        setLongi(null);
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

}




