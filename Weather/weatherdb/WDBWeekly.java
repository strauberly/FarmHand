package main.Weather.weatherdb;

import java.sql.*;
import java.text.Format;
import java.text.SimpleDateFormat;

public class WDBWeekly {

    private static Long endingDate = getEndingDate();
    private static String convertedEndingDate;
    public static int getWeeklyEntries;

    //pressure
    private static final Double weeklyHighPressure = WeatherDB.getHigh("high_pressure", "daily");
    private static final Double weeklyLowPressure = WeatherDB.getLow("low_pressure", "daily");
    private static final Double weeklyAvgPressure = WeatherDB.getAvg("avg_pressure", "daily");


    //temp
    private static final Double weeklyHighTemp = WeatherDB.getHigh("high_temp", "daily");
    private static final Double weeklyLowTemp = WeatherDB.getLow("low_temp", "daily");
    private static final Double weeklyAvgTemp = WeatherDB.getAvg("avg_temp", "daily");

    //wind
    private static final Double weeklyHighWind = WeatherDB.getHigh("high_wind", "daily");
    private static final Double weeklyAvgWind = WeatherDB.getAvg("avg_wind", "daily");

    //humidity
    private static final Double weeklyAvgHumidity = WeatherDB.getAvg("avg_humid", "daily");


    public static void writeToWeekly() {
        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO weekly(week_ending, high_temp, low_temp, avg_temp," +
                    "high_wind, avg_wind, avg_humid, high_pressure, low_pressure, avg_pressure)" +
                    "VALUES ( " + endingDate + "," + weeklyHighTemp + "," + weeklyLowTemp + "," + weeklyAvgTemp +
                    "," + weeklyHighWind + "," + weeklyAvgWind + "," + weeklyAvgHumidity + "," + weeklyHighPressure +
                    "," + weeklyLowPressure + "," + weeklyAvgPressure + ")");
            observableWeekEndingDateConversion();
            statement.executeUpdate("UPDATE weekly SET observable_week_ending = ('" + convertedEndingDate + "')" + "WHERE weekly_id_ = (SELECT max(weekly_id_) FROM weekly)");

            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        WDBWeekly.printWeeklyLog();
    }

    public static void printWeeklyLog(){
        System.out.println(
                "Wrote to Weekly totals for week ending: " +
                        "\n" +  convertedEndingDate +
                        "\n" +"weekly high pressure = " + weeklyHighPressure +
                        "\n" + "weekly low pressure = " + weeklyLowPressure +
                        "\n" + "weekly avg pressure = " + weeklyAvgPressure +
                        "\n" + "weekly high temp = " + weeklyHighTemp +
                        "\n" + "weekly low temp = " + weeklyLowTemp +
                        "\n" + "weekly avg temp = " + weeklyAvgTemp +
                        "\n" + "weekly high wind = " + weeklyHighWind +
                        "\n" + "weekly avg wind = " + weeklyAvgWind +
                        "\n" + "weekly avg humidity = " + weeklyAvgHumidity +
                        "\n" + "weekly entries = "
                        + WeatherDB.getID("weekly_id_", "weekly", getWeeklyEntries) + "\n");
    }

    public static String convertedEndingDate(long loggedWeekEnding) {
        Date currentDate = new Date(getEndingDate());
        Format format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(currentDate);
    }

    public static void observableWeekEndingDateConversion() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            String sql = "SELECT week_ending FROM weekly";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                long loggedWeekEnding = rs.getLong("week_ending");
                convertedEndingDate = convertedEndingDate(loggedWeekEnding);
            }
            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Long getEndingDate() {
        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement stmt = conn.createStatement();

            String sql = "SELECT * FROM daily ORDER by date DESC LIMIT 1";
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next())
                endingDate = resultSet.getLong("date");

            resultSet.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return endingDate;
    }
}