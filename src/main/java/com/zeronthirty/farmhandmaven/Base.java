package com.zeronthirty.farmhandmaven;

import com.zeronthirty.farmhandmaven.weather.weatherdb.WeatherDB;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Base extends javafx.application.Application {
    public void start(Stage primaryStage) throws Exception {
        WeatherDB.createWeatherDB();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/application.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setX(50);
        primaryStage.setY(50);
        primaryStage.show();
    }

    // note the class name that extends to the fxml and remember the pattern of three and includes
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();
//    }

    public static void main(String[] args) {
        launch();
    }
}