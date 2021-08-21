package main.Weather.weatherdb;

import java.sql.*;

public class WDBDaily {

    public static final Long date = WDBHourly.getLoggedDate();
    public static int getDailyEntries;

    //pressure
    private static final Double dailyHighPressure = WeatherDB.getHigh("pressure", "hourly");
    private static final Double dailyLowPressure = WeatherDB.getLow("pressure", "hourly");
    private static final Double dailyAvgPressure = WeatherDB.getAvg("pressure", "hourly");


    //temp
    private static final Double dailyHighTemp = WeatherDB.getHigh("temperature", "hourly");
    private static final Double dailyLowTemp = WeatherDB.getLow("temperature", "hourly");
    private static final Double dailyAvgTemp = WeatherDB.getAvg("temperature", "hourly");

    //wind
    private static final Double dailyHighWind = WeatherDB.getHigh("wind", "hourly");
    private static final Double dailyAvgWind = WeatherDB.getAvg("wind", "hourly");

    //humidity
    private static final Double dailyAvgHumidity = WeatherDB.getAvg("humidity", "hourly");

    public static void writeToDaily(){
        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO daily(date, high_temp, low_temp, avg_temp," +
                    "high_wind, avg_wind, avg_humid, high_pressure, low_pressure, avg_pressure)" +
                    "VALUES ( "+ date + "," + dailyHighTemp + "," + dailyLowTemp + "," + dailyAvgTemp +
                    "," + dailyHighWind + "," + dailyAvgWind + "," + dailyAvgHumidity + "," + dailyHighPressure +
                            "," + dailyLowPressure + "," + dailyAvgPressure + ")");

            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    WDBDaily.printDailyLog();
    }
    
    
    public static void printDailyLog(){
        System.out.println(
                "Wrote to daily: " +
                 "\n" +  WDBHourly.convertedLoggedDate() +
                "\n" +"Daily high pressure = " + dailyHighPressure +
                "\n" + "Daily low pressure = " + dailyLowPressure +
                "\n" + "Daily avg pressure = " + dailyAvgPressure +
                "\n" + "Daily high temp = " + dailyHighTemp +
                "\n" + "Daily low temp = " + dailyLowTemp +
                "\n" + "Daily avg temp = " + dailyAvgTemp +
                "\n" + "Daily high wind = " + dailyHighWind +
                "\n" + "Daily avg wind = " + dailyAvgWind +
                "\n" + "Daily avg humidity = " + dailyAvgHumidity +
                        "\n" + "Daily entries = "
                        + WeatherDB.getID("daily_id_","daily", getDailyEntries) + "\n");
    }

    public static long getEndingDate() {
        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement stmt = conn.createStatement();

            String sql = "SELECT * FROM daily ORDER by date DESC LIMIT 1";
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next())
                WDBWeekly.endingDate = resultSet.getLong("date");

            resultSet.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return WDBWeekly.endingDate;
    }
}

