package main.Weather.instruments;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class InstrumentsController {

    public Button logButton;
    public Button observationsButton;


    public void logButtonEnter(MouseEvent mouseEvent) {
        logButton.setEffect(new Glow(.25)); }
    public void logButtonExited(MouseEvent mouseEvent) {
        logButton.setEffect(new Glow(.0));  }
    public void logButtonPressed(MouseEvent mouseEvent) {
        logButton.setEffect(new Glow(.80)); }
    public void logButtonReleased(MouseEvent mouseEvent) {
        logButton.setEffect(new Glow(.0)); }



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
