package com.zeronthirty.farmhandmaven.weather.weatherdb;

import java.sql.*;

//contains constructor for writing to viewable table
public class WDBHourly {
    // init variables
    private final String time;
    private   String pressure;
    private    String temperature;
    private   String wind;
    private   String humidity;

    // calculation variables
    private static  String timeFieldValue;
    private static String pressureFieldValue;
    private static String temperatureFieldValue;
    private static String windFieldValue;
    private static String humidityFieldValue;
    private static long timeStamp;
    public static int getHourlyEntries;

    //constructor
    public WDBHourly(String pressure, String temperature, String wind, String humidity, String time) {
        this.pressure = pressure;
        this.temperature = temperature;
        this.wind = wind;
        this.humidity = humidity;
        this.time = time;
    }

    // write to hourly table in weather database
    public static void writeToHourly() throws SQLException {
        Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
        Statement statement = conn.createStatement();
        statement.executeUpdate("INSERT INTO hourly (timestamp, pressure, temperature, wind, humidity)" +
                "VALUES ( " + timeStamp + ", " + pressureFieldValue + ", "
                + temperatureFieldValue + ", " + windFieldValue + ", " + humidityFieldValue + ")");
        observableTimeConversion();
        statement.executeUpdate("UPDATE hourly SET time = ('" + timeFieldValue + "')" + "WHERE hourly_id_ = (SELECT max (hourly_id_) FROM hourly)");
        statement.close();
        conn.close();
    }
// ** feed back method
//     protected static void printHourlyLog() {
//        System.out.println(
//                "Wrote to hourly: " +
//                        "\n" + WDBHourly.getCurrentTime() +
//                        "\n" + "Pressure = " + pressureFieldValue +
//                        "\n" + "Temperature = " + temperatureFieldValue +
//                        "\n" + "Wind = " + windFieldValue +
//                        "\n" + "Humidity = " + humidityFieldValue +
//                        "\n" + "Hourly entries = "
//                        + WeatherDB.getID("hourly_id_","hourly", getHourlyEntries) + "\n"
//        );
//    }
//    *****************************************************

    //methods convert time returned in milliseconds into a human-readable format then written to hourly table
    public static void observableTimeConversion() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            String sql = "SELECT timestamp FROM hourly";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                long loggedTime = rs.getLong("timestamp");
                timeFieldValue = convertedLoggedTime(loggedTime);
            }
            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String convertedLoggedTime(Long time) {
        return String.valueOf(new Time(time));
    }

    // getters and setters

    public static void setTimeStamp(long timeStamp){
        WDBHourly.timeStamp = timeStamp;
    }

    public static long getTimeStamp() {
        return timeStamp;
    }

    public static void setPressureFieldValue(String pressureFieldValue) {
        WDBHourly.pressureFieldValue = pressureFieldValue;
    }

    public static void setTemperatureFieldValue(String temperatureFieldValue) {
        WDBHourly.temperatureFieldValue = temperatureFieldValue;
    }

    public static void setWindFieldValue(String windFieldValue) {
        WDBHourly.windFieldValue = windFieldValue;
    }

    public static void setHumidityFieldValue(String humidityFieldValue) {
        WDBHourly.humidityFieldValue = humidityFieldValue;
    }
    public static String getTimeFieldValue() {
        return timeFieldValue;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public  void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public  void setWind(String wind) {
        this.wind = wind;
    }

    public  void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTime() {
        return time;
    }

    public  String getPressure() {
        return pressure;
    }

    public  String getTemperature() {
        return temperature;
    }

    public  String getWind() {
        return wind;
    }

    public  String getHumidity() {
        return humidity;
    }

}
