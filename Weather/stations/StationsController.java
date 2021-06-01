package main.Weather.stations;

import javafx.scene.control.Button;
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

    public Button getReadOut;
    public void readOutButtonEnter(MouseEvent mouseEvent) {
        getReadOut.setEffect(new Glow(.25)); }

    public void readOutButtonExit(MouseEvent mouseEvent) {
        getReadOut.setEffect(new Glow(.0)); }

    public void readOutButtonPressed(MouseEvent mouseEvent) {
        getReadOut.setEffect(new Glow(.80)); }

    public void readOutButtonReleased(MouseEvent mouseEvent) throws IOException {
        getReadOut.setEffect(new Glow(.0));
        setLat(latitudeField.getText());
        setLongi(longitudeField.getText());
        Weather.getConditions();

//        System.out.println(getLongi());
//        System.out.println(getLat());
    }







    public void setLat(String lat) {
        StationsController.lat = lat;
    }

    public void setLongi(String longi) {
        StationsController.longi = longi;
    }

    public static String getLat(){
        return lat;
    }

    public static String getLongi(){
        return longi;
    }



}
