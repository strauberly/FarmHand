
import Chores.DataModel.Chore;
import Chores.DataModel.choredb.ChoreDB;
import Weather.weatherdb.WeatherDB;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends javafx.application.Application {
    public void start(Stage primaryStage) throws Exception {
        WeatherDB.createWeatherDB();
        ChoreDB.createChoreDB();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
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
        Chore chore = new Chore("clean","inside","high","clean up","04 mar 2022", "25 mar 2022", "Tom", "NA");
        System.out.println(chore.toString());
    }
}
