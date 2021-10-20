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
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class InstrumentsController implements Initializable {
    // watch videos and figure out data binding and or creating a list for the

    @FXML
    private TableView<WDBHourly> hourly = new TableView<>();
    @FXML
    private final TableColumn<WDBHourly, String> col_time = new TableColumn<>();
    @FXML
    public TableColumn<WDBHourly, String> col_pressure = new TableColumn<>();
    @FXML
    public TableColumn<WDBHourly, String> col_temperature = new TableColumn<>();
    @FXML
    public TableColumn<WDBHourly, String> col_wind = new TableColumn<>();
    @FXML
    public TableColumn<WDBHourly, String> col_humidity = new TableColumn<>();


//    private final List<String> observableTimes = new ArrayList<>();
   private final ObservableList<WDBHourly> oblist = FXCollections.observableArrayList();
//    ObservableList<String> stringlist = FXCollections.observableArrayList();


//    private ObservableList<ObservableList> data;

    @Override
    public void initialize(URL Location, ResourceBundle resources) {

        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            String sql = "SELECT * FROM hourly";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) { // rename DB so table matches in sqlite and java fx including case
                oblist.add(new WDBHourly(rs.getString("pressure"), rs.getString("temperature"),
                        rs.getString("wind"), rs.getString("humidity"), rs.getString("time")));
            }
            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        col_pressure.setCellValueFactory(new PropertyValueFactory<>("Pressure"));
        col_temperature.setCellValueFactory(new PropertyValueFactory<>("Temperature"));
        col_wind.setCellValueFactory(new PropertyValueFactory<>("Wind"));
        col_humidity.setCellValueFactory(new PropertyValueFactory<>("Humidity"));
        col_time.setCellValueFactory(new PropertyValueFactory<>("Time"));
        hourly.setItems(oblist);
    }


    //    @FXML
//    public TableColumn pressure;
//    @FXML
//    public TableColumn temperature;
//    @FXML
//    public TableColumn wind;
//    @FXML
//    public TableColumn humidity;

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

    public void logButtonPressed(MouseEvent mouseEvent) {
        logButton.setEffect(new Glow(.80));
        WDBHourly.setTimeStamp(System.currentTimeMillis());
    }

    public void logButtonReleased(MouseEvent mouseEvent) throws IOException {
        logButton.setEffect(new Glow(.0));
        // rewrite all into
        // set fields
        // log
        // set tables
        // change stage
        WDBHourly.setPressureFieldValue(pressureField.getText());
        WDBHourly.setTemperatureFieldValue(tempField.getText());
        WDBHourly.setWindFieldValue(windField.getText());
        WDBHourly.setHumidityFieldValue(humidityField.getText());
        WeatherDB.log();

        col_pressure.setCellValueFactory(new PropertyValueFactory<>("Pressure"));
        col_temperature.setCellValueFactory(new PropertyValueFactory<>("Temperature"));
        col_wind.setCellValueFactory(new PropertyValueFactory<>("Wind"));
        col_humidity.setCellValueFactory(new PropertyValueFactory<>("Humidity"));
        col_time.setCellValueFactory(new PropertyValueFactory<>("Time"));
        hourly.setItems(getHourlyTable());


        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/main/Weather/instruments/InstrumentsDisplay.fxml")));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();

    }


    public void observationsButtonEnter(MouseEvent mouseEvent) {
        observationsButton.setEffect(new Glow(.25));
    }

    public void observationsButtonExit(MouseEvent mouseEvent) {
        observationsButton.setEffect(new Glow(.0));
    }

    public void observationsButtonPressed(MouseEvent mouseEvent) {
        observationsButton.setEffect(new Glow(.80));
    }

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
    //observable list is currently calling only current observable time instead of all times
    // column is created but not painting column with observable list
    // finish watching video


    public static ObservableList <WDBHourly> getHourlyTable () {
        ObservableList<WDBHourly> list = FXCollections.observableArrayList();
        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            String sql = "SELECT * FROM hourly";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                list.add(new WDBHourly(rs.getString("pressure"),rs.getString("temperature"),
                        rs.getString("wind"),rs.getString("humidity"),rs.getString("time")));
            }
            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }



//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        time.setCellValueFactory(new PropertyValueFactory<>("time"));
//    }


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



