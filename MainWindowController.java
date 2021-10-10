package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;

import java.io.IOException;
import java.util.Objects;

public class MainWindowController extends Main {
// create change scene method here
    // implement windows being draggable 

    @FXML
    private Button weatherButton;

    public MainWindowController() throws IOException {
    }

    @FXML
    private void onWeatherEntered(MouseEvent enter) {
        weatherButton.setEffect(new Glow(.25));
    }
    @FXML
    private void onWeatherExited(MouseEvent exit) {
        weatherButton.setEffect(new Glow(.00));
    }
    @FXML
    private void onWeatherPressed(MouseEvent pressed) throws IOException {
        weatherButton.setEffect(new Glow(.80));

    }
    @FXML
    private void onWeatherReleased(MouseEvent released) throws IOException {
        weatherButton.setEffect(new Glow(.00));
    }
    @FXML
    private void onWeatherClicked(MouseEvent mouseEvent) throws IOException {
            Stage weatherStage = new Stage();
            weatherStage.initStyle(StageStyle.TRANSPARENT);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/main/Weather/Weather.fxml")));
            FXMLLoader fxmlLoader = new FXMLLoader();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            weatherStage.setScene(scene);
            weatherStage.setX(355);
            weatherStage.setY(50);
            weatherStage.show();
            if (weatherStage.isShowing()) {
                weatherButton.setOnAction(event -> weatherStage.close());
            }
            choresButton.setOnAction(event -> weatherStage.close());
            inventoryButton.setOnAction(event -> weatherStage.close());
        }


    @FXML
    private Button choresButton;
    @FXML
    private void onChoresEntered(MouseEvent enter) {
        choresButton.setEffect(new Glow(.25));
    }
    @FXML
    private void onChoresExited(MouseEvent exit) {
        choresButton.setEffect(new Glow(.00));
    }
    @FXML
    private void onChoresPressed(MouseEvent pressed) {
        choresButton.setEffect(new Glow(.80));
    }
    @FXML
    private void onChoresReleased(MouseEvent released) {
        choresButton.setEffect(new Glow(.00));
    }
    @FXML
    private void onChoresClicked(MouseEvent mouseEvent) throws IOException {
        Stage choresStage = new Stage();
        choresStage.initStyle(StageStyle.TRANSPARENT);

        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/main/Chores/Chores.fxml")));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        choresStage.setScene(scene);
        choresStage.setX(355);
        choresStage.setY(50);
        choresStage.show();

        if (choresStage.isShowing()) {
        choresButton.setOnAction(event -> choresStage.close());
        }

        weatherButton.setOnAction(event -> choresStage.close());
        inventoryButton.setOnAction(event -> choresStage.close());
    }


    @FXML
    private Button inventoryButton;
    @FXML
    private void onInventoryEntered(MouseEvent enter) {
        inventoryButton.setEffect(new Glow(.25));
    }
    @FXML
    private void onInventoryExited(MouseEvent exit) {
        inventoryButton.setEffect(new Glow(.00));
    }
    @FXML
    private void onInventoryPressed(MouseEvent pressed) throws IOException {
        inventoryButton.setEffect(new Glow(.80));

    }
    @FXML
    private void onInventoryReleased(MouseEvent released) throws IOException {
        inventoryButton.setEffect(new Glow(.00));
    }
    @FXML
    private void onInventoryClicked(MouseEvent mouseEvent) throws IOException {
        Stage inventoryStage = new Stage();
        inventoryStage.initStyle(StageStyle.TRANSPARENT);

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/main/Inventory/Inventory.fxml")));
        FXMLLoader fxmlLoader = new FXMLLoader();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        inventoryStage.setScene(scene);
        inventoryStage.setX(355);
        inventoryStage.setY(50);
        inventoryStage.show();
        if (inventoryStage.isShowing()) {
            inventoryButton.setOnAction(event -> inventoryStage.close());
        }

        choresButton.setOnAction(event -> inventoryStage.close());
        weatherButton.setOnAction(event -> inventoryStage.close());
    }




    @FXML
    private Button minButton;
    @FXML
    private void onMinEntered(MouseEvent enter) {
        minButton.setEffect(new Glow(.25));
    }
    @FXML
    private void onMinExited(MouseEvent exit) {
        minButton.setEffect(new Glow(.00));
    }
    @FXML
    private void onMinPressed(MouseEvent pressed) throws IOException {
        minButton.setEffect(new Glow(.80));

    }
    @FXML
    private void onMinReleased(MouseEvent released) throws IOException {
        minButton.setEffect(new Glow(.00));
        Stage stage = (Stage) ((Node) released.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }




    @FXML
    private Button closeButton;
    @FXML
    private void onCloseEntered(MouseEvent enter) {
        closeButton.setEffect(new Glow(.25));
    }
    @FXML
    private void onCloseExited(MouseEvent exit) {
        closeButton.setEffect(new Glow(.00));
    }
    @FXML
    private void onClosePressed(MouseEvent pressed) throws IOException {
        closeButton.setEffect(new Glow(.80));
    }
    @FXML
    private void onCloseReleased(MouseEvent released) throws IOException {
        closeButton.setEffect(new Glow(.00));
        Stage stage = (Stage) ((Node) released.getSource()).getScene().getWindow();
        stage.close();
    }


}


