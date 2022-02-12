package com.zeronthirty.farmhandmaven;

import com.zeronthirty.farmhandmaven.weather.weatherdb.WeatherDB;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends javafx.application.Application {
    public void start(Stage primaryStage) throws Exception {
        WeatherDB.createWeatherDB();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/base.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setX(50);
        primaryStage.setY(50);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}