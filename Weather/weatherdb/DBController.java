package main.Weather.weatherdb;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DBController implements Initializable {
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
    // check your naming convention
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

//    {
//        observationsText = new Label();
//    }

    @Override
    public void initialize(URL Location, ResourceBundle resources) {
        ObservableList<WDBHourly> hourlyList = FXCollections.observableArrayList();
        ObservableList<WDBDaily> dailyList = FXCollections.observableArrayList();
        ObservableList<WDBWeekly> weeklyList = FXCollections.observableArrayList();

        try {
            // connection method
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            String sql = "SELECT * FROM hourly ORDER BY hourly_id_ DESC";
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
            String sql = "SELECT * FROM daily ORDER BY daily_id_ DESC";
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
            String sql = "SELECT * FROM weekly ORDER BY weekly_id_ DESC";
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
}
