package com.zeronthirty.farmhandmaven.weather.weatherdb;

import java.sql.*;
import java.text.Format;
import java.text.SimpleDateFormat;

// daily table values, conversions, and comparisons
public class WDBDaily {

    private String date;
    // init variables
    private String highpressure;
    private String lowpressure;
    private String  avgpressure;

    private String hightemp;
    private String lowtemp;
    private String avgtemp;

    private String highwind;
    private String avgwind;

    private String avghumid;

    // calculation variables
    private static long loggedDate;
    private static final Long dateValue = getLoggedDate();
    public static int getDailyEntries;
    private static String observableDate;


    private static  String highPressureValue = WeatherDB.getHigh("pressure", "hourly");
    private  static String lowPressureValue = WeatherDB.getLow("pressure", "hourly");
    private static String avgPressureValue = WeatherDB.getAvg("pressure", "hourly");

    private static String highTempValue = WeatherDB.getHigh("temperature", "hourly");
    private static String  lowTempValue = WeatherDB.getLow("temperature", "hourly");
    private static String  avgTempValue = WeatherDB.getAvg("temperature", "hourly");

    private static String highWindValue  = WeatherDB.getHigh("wind", "hourly");
    private static String  avgWindValue = WeatherDB.getAvg("wind", "hourly");

    private static String avgHumidityValue = WeatherDB.getAvg("humidity", "hourly");

    // constructor
    public WDBDaily(String date, String highpressure, String lowpressure, String avgpressure, String hightemp,
                    String lowtemp, String avgtemp, String highwind, String avgwind, String avghumid) {
        this.date = date;
        this.highpressure = highpressure;
        this.lowpressure = lowpressure;
        this.avgpressure = avgpressure;
        this.hightemp = hightemp;
        this.lowtemp = lowtemp;
        this.avgtemp = avgtemp;
        this.highwind = highwind;
        this.avgwind = avgwind;
        this.avghumid = avghumid;
    }
    // methods to write to daily table
    public static void writeToDaily() throws SQLException {

        Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
        Statement statement = conn.createStatement();
        statement.execute("INSERT INTO daily(timestamp, hightemp, lowtemp, avgtemp," +
                "highwind, avgwind, avghumid, highpressure, lowpressure, avgpressure)" +
                "VALUES ( "+ dateValue + "," + highTempValue + "," + lowTempValue + "," + avgTempValue +
                "," + highWindValue + "," + avgWindValue + "," + avgHumidityValue + "," + highPressureValue +
                "," + lowPressureValue + "," + avgPressureValue + ")");
        observableDateConversion();
        statement.executeUpdate("UPDATE daily SET date = ('" + observableDate + "')" + "WHERE daily_id_ = (SELECT max(daily_id_) FROM daily)");
        statement.close();
        conn.close();
    }
//        WDBDaily.printDailyLog();

// ** value test output **
//     static void printDailyLog(){
//        System.out.println(
//                "Wrote to daily: " +
//                        "\n" +  observableDate +
//                        "\n" +"Daily high pressure = " + highPressureValue +
//                        "\n" + "Daily low pressure = " + lowPressureValue +
//                        "\n" + "Daily avg pressure = " + avgPressureValue +
//                        "\n" + "Daily high temp = " + highTempValue +
//                        "\n" + "Daily low temp = " + lowTempValue +
//                        "\n" + "Daily avg temp = " + avgTempValue +
//                        "\n" + "Daily high wind = " + highWindValue +
//                        "\n" + "Daily avg wind = " + avgWindValue +
//                        "\n" + "Daily avg humidity = " + avgHumidityValue +
//                        "\n" + "Daily entries = "
//                        + WeatherDB.getID("daily_id_","daily", getDailyEntries) + "\n");
//********************************************
//    }

    // methods to format time as dates and compare dates
    private static void observableDateConversion() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            String sql = "SELECT timestamp FROM daily";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                long loggedDate = rs.getLong("timestamp");
                observableDate = convertedLoggedDateString(loggedDate);
            }
            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static long getLoggedDate() {
        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement stmt = conn.createStatement();

            String sql = "SELECT * FROM hourly ORDER by timestamp DESC LIMIT 1";
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next())
                loggedDate = resultSet.getLong("timestamp");

            resultSet.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loggedDate;
    }

    private static String convertedCurrentDateString(){
        Date currentDate = new Date(WDBHourly.getTimeStamp());
        Format format = new SimpleDateFormat("MM-dd-yyyy");
        return format.format(currentDate);
    }

    private static String convertedLoggedDateString(long loggedDate){
        Date currentDate = new Date(getLoggedDate());
        Format format = new SimpleDateFormat("MM-dd-yyyy");
        return format.format(currentDate);
    }

    public static boolean notSameDates() {
        return !convertedCurrentDateString().equals(convertedLoggedDateString(getLoggedDate()));
    }

    // getters and setters
    public void setHighPressureValue() {
        highPressureValue = WeatherDB.getHigh("pressure", "hourly");
    }

    public void setLowPressureValue() {
        lowPressureValue = WeatherDB.getLow("pressure", "hourly");
    }

    public void setAvgPressureValueValue() {
        avgPressureValue = WeatherDB.getAvg("pressure", "hourly");
    }

    public void setHighTempValue() {
        highTempValue = WeatherDB.getHigh("temperature", "hourly");
    }

    public void setLowTempValue() {
        lowTempValue = WeatherDB.getLow("temperature", "hourly");

    }
    public void setAvgTempValue() {
        avgTempValue = WeatherDB.getAvg("temperature", "hourly");

    }

    public void setHighWindValue() {
        highWindValue = WeatherDB.getHigh("wind", "hourly");
    }

    public void setAvgWindValue() {
        avgWindValue = WeatherDB.getAvg("wind", "hourly");
    }

    public void setAvgHumidityValue() {
        avgHumidityValue = WeatherDB.getAvg("humidity", "hourly");
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHighpressure() {
        return highpressure;
    }

    public void setHighpressure(String highpressure) {
        this.highpressure = highpressure;
    }

    public String getLowpressure() {
        return lowpressure;
    }

    public void setLowpressure(String lowpressure) {
        this.lowpressure = lowpressure;
    }

    public String getAvgpressure() {
        return avgpressure;
    }

    public void setAvgpressure(String avgpressure) {
        this.avgpressure = avgpressure;
    }

    public String getHightemp() {
        return hightemp;
    }

    public void setHightemp(String hightemp) {
        this.hightemp = hightemp;
    }

    public String getLowtemp() {
        return lowtemp;
    }

    public void setLowtemp(String lowtemp) {
        this.lowtemp = lowtemp;
    }

    public String getAvgtemp() {
        return avgtemp;
    }

    public void setAvgtemp(String avgtemp) {
        this.avgtemp = avgtemp;
    }

    public String getHighwind() {
        return highwind;
    }

    public void setHighwind(String highwind) {
        this.highwind = highwind;
    }

    public String getAvgwind() {
        return avgwind;
    }

    public void setAvgwind(String avgwind) {
        this.avgwind = avgwind;
    }

    public String getAvghumid() {
        return avghumid;
    }

    public void setAvghumid(String avghumid) {
        this.avghumid = avghumid;
    }
}