package com.zeronthirty.farmhandmaven.weather.weatherdb;

import com.zeronthirty.farmhandmaven.weather.WeatherController;
import com.zeronthirty.farmhandmaven.weather.observations.Observations;
import com.zeronthirty.farmhandmaven.weather.weatherdb.WDBDaily;
import com.zeronthirty.farmhandmaven.weather.weatherdb.WDBHourly;
import com.zeronthirty.farmhandmaven.weather.weatherdb.WDBWeekly;
import com.zeronthirty.farmhandmaven.weather.weatherdb.WeatherDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class TablesGui implements Initializable {
    @FXML
    public Label observationsText = new Label();

    //hourly tableview
    @FXML
    private javafx.scene.control.TableView<WDBHourly> hourly = new javafx.scene.control.TableView<>();
    @FXML
    private final TableColumn<WDBHourly, String> col_time = new TableColumn<>();
    @FXML
    private TableColumn<WDBHourly, String> col_pressure = new TableColumn<>();
    @FXML
    private TableColumn<WDBHourly, String> col_temperature = new TableColumn<>();
    @FXML
    private TableColumn<WDBHourly, String> col_wind = new TableColumn<>();
    @FXML
    private TableColumn<WDBHourly, String> col_humidity = new TableColumn<>();

    //daily tableview
    @FXML
    private javafx.scene.control.TableView<WDBDaily> daily = new javafx.scene.control.TableView<>();
    @FXML
    private final TableColumn<WDBDaily, String> col_date = new TableColumn<>();
    @FXML
    private TableColumn<WDBDaily, String> col_high_pressure = new TableColumn<>();
    @FXML
    private TableColumn<WDBDaily, String> col_low_pressure = new TableColumn<>();
    @FXML
    private TableColumn<WDBDaily, String> col_avg_pressure = new TableColumn<>();
    @FXML
    private TableColumn<WDBDaily, String> col_high_temperature = new TableColumn<>();
    @FXML
    private TableColumn<WDBDaily, String> col_low_temperature = new TableColumn<>();
    @FXML
    private TableColumn<WDBDaily, String> col_avg_temperature = new TableColumn<>();
    @FXML
    private TableColumn<WDBDaily, String> col_high_wind = new TableColumn<>();
    @FXML
    private TableColumn<WDBDaily, String> col_avg_wind = new TableColumn<>();
    @FXML
    private TableColumn<WDBDaily, String> col_avg_humidity = new TableColumn<>();

    //weekly tableview
    @FXML
    private javafx.scene.control.TableView<WDBWeekly> weekly = new javafx.scene.control.TableView<>();
    @FXML
    private TableColumn<WDBWeekly, String> col_weekending = new TableColumn<>();
    @FXML
    private TableColumn<WDBWeekly, String> col_weeklyhighpressure = new TableColumn<>();
    @FXML
    private TableColumn<WDBWeekly, String> col_weeklylowpressure = new TableColumn<>();
    @FXML
    private TableColumn<WDBWeekly, String> col_weeklyavgpressure = new TableColumn<>();
    @FXML
    private TableColumn<WDBWeekly, String> col_weeklyhightemperature = new TableColumn<>();
    @FXML
    private TableColumn<WDBWeekly, String> col_weeklylowtemperature = new TableColumn<>();
    @FXML
    private TableColumn<WDBWeekly, String> col_weeklyavgtemperature = new TableColumn<>();
    @FXML
    private TableColumn<WDBWeekly, String> col_weeklyhighwind = new TableColumn<>();
    @FXML
    private TableColumn<WDBWeekly, String> col_weeklyavgwind = new TableColumn<>();
    @FXML
    private TableColumn<WDBWeekly, String> col_weeklyavghumidity = new TableColumn<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int hourlyEntries = 0;
        if (!(WeatherDB.getID("hourly_id_", "hourly", hourlyEntries) > 2)) {
            observationsText.setText("mAH d00d needs at least 3 hourly entries for observations. ");
        } else
            observationsText.setText(Observations.weatherReport());
        ObservableList<WDBHourly> hourlyList = FXCollections.observableArrayList();
        ObservableList<WDBDaily> dailyList = FXCollections.observableArrayList();
        ObservableList<WDBWeekly> weeklyList = FXCollections.observableArrayList();

        try {
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

        if (WeatherController.isMaxed()){
            hourly.setScaleY(1.30);
            hourly.setScaleX(1.20);
            hourly.setPrefWidth(1300);
            hourly.setTranslateY(25);
            hourly.setTranslateX(125);

            daily.setScaleY(1.30);
            daily.setScaleX(1.20);
            daily.setTranslateY(150);
            daily.setTranslateX(125);
            daily.setPrefWidth(1300);

            weekly.setScaleY(1.30);
            weekly.setScaleX(1.20);
            weekly.setTranslateY(250);
            weekly.setTranslateX(125);
            weekly.setPrefWidth(1300);
        }
    }
}
