package main.Weather.instruments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.Weather.weatherdb.WDBHourly;
import main.Weather.weatherdb.WeatherDB;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class InstrumentsController implements Initializable {
    @FXML
    private TableView<String> hourlyTable = new TableView<>();
    @FXML
   private final TableColumn<WDBHourly, String> time = new TableColumn<WDBHourly, String>("time");
    @FXML
    public TableColumn pressure;
    @FXML
    public TableColumn temperature;
    @FXML
    public TableColumn wind;
    @FXML
    public TableColumn humidity;

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
        logButton.setEffect(new Glow(.25)); }
    public void logButtonExited(MouseEvent mouseEvent) {
        logButton.setEffect(new Glow(.0));  }
    public void logButtonPressed(MouseEvent mouseEvent) {
        logButton.setEffect(new Glow(.80));
        WDBHourly.setTimeStamp(System.currentTimeMillis());
    }

    public void logButtonReleased(MouseEvent mouseEvent) throws InterruptedException, IOException {
        logButton.setEffect(new Glow(.0));
        // rewrite all into
        // set fields
        // log
        // set tables
        // change stage
        WDBHourly.setPressure(pressureField.getText());
        WDBHourly.setTemp(tempField.getText());
        WDBHourly.setWind(windField.getText());
        WDBHourly.setHumidity(humidityField.getText());
        WeatherDB.log();
        System.out.println(WeatherDB.getLow("pressure", "hourly"));
        System.out.println(WDBHourly.compareDates());
//        setHourlyTable();
//        ObservableList<String> loggedTimes = FXCollections.observableArrayList(WDBHourly.propagateTimes());
//        hourlyTable.setItems(loggedTimes);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/main/Weather/instruments/InstrumentsDisplay.fxml")));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();

    }


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
//// for each in loggedTimes paint a cell in time object
//    public void setHourlyTable() {
//        ObservableList<String> loggedTimes = FXCollections.observableArrayList(WDBHourly.getObservableTimes());
////       loggedTimes.forEach(timeObject.setCellValueFactory(new PropertyValueFactory<>("timeObject")));
//           time.setCellValueFactory(new PropertyValueFactory<>("time"));
//        hourlyTable.setItems(loggedTimes);
//        System.out.println(loggedTimes);
////        hourlyTable.getColumns().addAll();
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
    }


//    public void setHourlyTable(){
//        hourlyTable.setItems(data);
//        time.setCellValueFactory(new PropertyValueFactory<>("time"));
//        hourlyTable.getColumns().addAll(time);
//    }
//    public void setHourlyTable(){
//    time.setCellValueFactory(new PropertyValueFactory<>("time"));
//    hourlyTable.setItems(WDBHourly.propagateTimes());
//    }
}



