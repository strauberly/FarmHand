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
    private final TableColumn<WDBHourly, String> col_date = new TableColumn<>();
    @FXML
    public TableColumn<WDBHourly, Double> col_high_pressure = new TableColumn<>();
    @FXML
    public TableColumn<WDBHourly, Double> col_low_pressure = new TableColumn<>();
    @FXML
    public TableColumn<WDBHourly, Double> col_avg_pressure = new TableColumn<>();
    @FXML
    public TableColumn<WDBHourly, Double> col_high_temperature = new TableColumn<>();
    @FXML
    public TableColumn<WDBHourly, Double> col_low_temperature = new TableColumn<>();
    @FXML
    public TableColumn<WDBHourly, Double> col_avg_temperature = new TableColumn<>();
    @FXML
    public TableColumn<WDBHourly, Double> col_high_wind = new TableColumn<>();
    @FXML
    public TableColumn<WDBHourly, Double> col_avg_wind = new TableColumn<>();
    @FXML
    public TableColumn<WDBHourly, Double> col_avg_humidity = new TableColumn<>();





    @Override
    public void initialize(URL Location, ResourceBundle resources) {

        ObservableList<WDBHourly> hourlyList = FXCollections.observableArrayList();
        ObservableList<WDBDaily> dailyList = FXCollections.observableArrayList();

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
            dailyList.add(new WDBDaily(rs.getString("observable_date")));


//            dailyList.add(new WDBDaily(rs.getDouble("high_temp"), rs.getDouble("low_temp"), rs.getDouble("avg_temp"), rs.getDouble("high_wind"),
//                    rs.getDouble("avg_wind"), rs.getDouble("avg_humid"), rs.getDouble("high_pressure"), rs.getDouble("low_pressure"),
//                    rs.getDouble("avg_pressure"), rs.getString("observable_date")));
        }
        rs.close();
        statement.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
        col_high_pressure.setCellValueFactory(new PropertyValueFactory<>("High Pressure"));
        col_low_pressure.setCellValueFactory(new PropertyValueFactory<>("Low Pressure"));
        col_avg_pressure.setCellValueFactory(new PropertyValueFactory<>("Average Pressure"));
        col_high_temperature.setCellValueFactory(new PropertyValueFactory<>("High Temperature"));
        col_low_temperature.setCellValueFactory(new PropertyValueFactory<>("Low Temperature"));
        col_avg_temperature.setCellValueFactory(new PropertyValueFactory<>("Average Temperature"));
        col_high_wind.setCellValueFactory(new PropertyValueFactory<>("High Wind"));
        col_avg_wind.setCellValueFactory(new PropertyValueFactory<>("Average Wind"));
        col_avg_humidity.setCellValueFactory(new PropertyValueFactory<>("Average Humidity"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        daily.setItems(dailyList);
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
        WDBDaily.writeToDaily();

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



