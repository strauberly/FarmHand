package main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Weather.weatherdb.WeatherDB;


import java.io.IOException;
import java.util.Objects;

// create method for change table for FXML files
public class Main extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    public void start(Stage primaryStage) throws Exception {
        WeatherDB.createWeatherDB();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/main/Main.fxml")));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setX(50);
        primaryStage.setY(50);
        primaryStage.show();

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);

            }
        });

    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }
}