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
import main.Weather.weatherdb.WDBDaily;
import main.Weather.weatherdb.WDBHourly;
import main.Weather.weatherdb.WDBWeekly;
import main.Weather.weatherdb.WeatherDB;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class InstrumentsController implements Initializable {

    //hourly tableview
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

    //daily tableview
    @FXML
    private TableView<WDBDaily> daily = new TableView<>();
    @FXML
    private final TableColumn<WDBDaily, String> col_date = new TableColumn<>();
    @FXML
    public TableColumn<WDBDaily, String> col_high_pressure = new TableColumn<>();
    @FXML
    public TableColumn<WDBDaily, String> col_low_pressure = new TableColumn<>();
    @FXML
    public TableColumn<WDBDaily, String> col_avg_pressure = new TableColumn<>();
    @FXML
    public TableColumn<WDBDaily, String> col_high_temperature = new TableColumn<>();
    @FXML
    public TableColumn<WDBDaily, String> col_low_temperature = new TableColumn<>();
    @FXML
    public TableColumn<WDBDaily, String> col_avg_temperature = new TableColumn<>();
    @FXML
    public TableColumn<WDBDaily, String> col_high_wind = new TableColumn<>();
    @FXML
    public TableColumn<WDBDaily, String> col_avg_wind = new TableColumn<>();
    @FXML
    public TableColumn<WDBDaily, String> col_avg_humidity = new TableColumn<>();

    //weekly tableview
    @FXML
    private TableView<WDBWeekly> weekly = new TableView<>();
    @FXML
    private final TableColumn<WDBWeekly, String> col_weekending = new TableColumn<>();
    @FXML
    public TableColumn<WDBWeekly, String> col_weeklyhighpressure = new TableColumn<>();
    @FXML
    public TableColumn<WDBWeekly, String> col_weeklylowpressure = new TableColumn<>();
    @FXML
    public TableColumn<WDBWeekly, String> col_weeklyavgpressure = new TableColumn<>();
    @FXML
    public TableColumn<WDBWeekly, String> col_weeklyhightemperature = new TableColumn<>();
    @FXML
    public TableColumn<WDBWeekly, String> col_weeklylowtemperature = new TableColumn<>();
    @FXML
    public TableColumn<WDBWeekly, String> col_weeklyavgtemperature = new TableColumn<>();
    @FXML
    public TableColumn<WDBWeekly, String> col_weeklyhighwind = new TableColumn<>();
    @FXML
    public TableColumn<WDBWeekly, String> col_weeklyavgwind = new TableColumn<>();
    @FXML
    public TableColumn<WDBWeekly, String> col_weeklyavghumidity = new TableColumn<>();


    @Override
    public void initialize(URL Location, ResourceBundle resources) {

        ObservableList<WDBHourly> hourlyList = FXCollections.observableArrayList();
        ObservableList<WDBDaily> dailyList = FXCollections.observableArrayList();
        ObservableList<WDBWeekly> weeklyList = FXCollections.observableArrayList();

        try {
            // connection method
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            String sql = "SELECT * FROM hourly";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                hourlyList.add(new WDBHourly(rs.getString("pressure"), rs.getString("temperature"),
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
        hourly.setItems(hourlyList);
    try {
        // connection method
        Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
        Statement statement = conn.createStatement();
        String sql = "SELECT * FROM daily";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            dailyList.add(new WDBDaily(rs.getString("date"), rs.getString("highpressure"), rs.getString("lowpressure"), rs.getString("avgpressure"), rs.getString("hightemp"),
                    rs.getString("lowtemp"), rs.getString("avgtemp"), rs.getString("highwind"), rs.getString("avgwind"),
                    rs.getString("avghumid")));
        }
        rs.close();
        statement.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_high_pressure.setCellValueFactory(new PropertyValueFactory<>("highpressure"));
        col_low_pressure.setCellValueFactory(new PropertyValueFactory<>("lowpressure"));
        col_avg_pressure.setCellValueFactory(new PropertyValueFactory<>("avgpressure"));
        col_high_temperature.setCellValueFactory(new PropertyValueFactory<>("hightemp"));
        col_low_temperature.setCellValueFactory(new PropertyValueFactory<>("lowtemp"));
        col_avg_temperature.setCellValueFactory(new PropertyValueFactory<>("avgtemp"));
        col_high_wind.setCellValueFactory(new PropertyValueFactory<>("highwind"));
        col_avg_wind.setCellValueFactory(new PropertyValueFactory<>("avgwind"));
        col_avg_humidity.setCellValueFactory(new PropertyValueFactory<>("avghumid"));
        daily.setItems(dailyList);
 try {
        // connection method
        Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
        Statement statement = conn.createStatement();
        String sql = "SELECT * FROM weekly";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            weeklyList.add(new WDBWeekly(rs.getString("weekending"), rs.getString("weeklyhighpressure"), rs.getString("weeklylowpressure"), rs.getString("weeklyavgpressure"),
                    rs.getString("weeklyhightemp"), rs.getString("weeklylowtemp"), rs.getString("weeklyavgtemp"), rs.getString("weeklyhighwind"),
                    rs.getString("weeklyavgwind"), rs.getString("weeklyavghumid")));
        }
        rs.close();
        statement.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
        col_weekending.setCellValueFactory(new PropertyValueFactory<>("weekending"));
        col_weeklyhighpressure.setCellValueFactory(new PropertyValueFactory<>("weeklyhighpressure"));
        col_weeklylowpressure.setCellValueFactory(new PropertyValueFactory<>("weeklylowpressure"));
        col_weeklyavgpressure.setCellValueFactory(new PropertyValueFactory<>("weeklyavgpressure"));
        col_weeklyhightemperature.setCellValueFactory(new PropertyValueFactory<>("weeklyhightemp"));
        col_weeklylowtemperature.setCellValueFactory(new PropertyValueFactory<>("weeklylowtemp"));
        col_weeklyavgtemperature.setCellValueFactory(new PropertyValueFactory<>("weeklyavgtemp"));
        col_weeklyhighwind.setCellValueFactory(new PropertyValueFactory<>("weeklyhighwind"));
        col_weeklyavgwind.setCellValueFactory(new PropertyValueFactory<>("weeklyavgwind"));
        col_weeklyavghumidity.setCellValueFactory(new PropertyValueFactory<>("weeklyavghumid"));
        weekly.setItems(weeklyList);
}

    //buttons and fields
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
        // clean this up so just calling the method
        WDBHourly.setTimeStamp(System.currentTimeMillis());
    }
    public void logButtonReleased(MouseEvent mouseEvent) throws IOException {
        logButton.setEffect(new Glow(.0));
        WDBHourly.setPressureFieldValue(pressureField.getText());
        WDBHourly.setTemperatureFieldValue(tempField.getText());
        WDBHourly.setWindFieldValue(windField.getText());
        WDBHourly.setHumidityFieldValue(humidityField.getText());
        WeatherDB.log();

        // scene change method
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/main/Weather/instruments/DBDisplay.fxml")));
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
        // scene change method
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/main/Weather/instruments/Observations.fxml")));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }
}



