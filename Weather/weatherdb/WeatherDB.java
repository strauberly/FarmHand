package main.Weather.weatherdb;

import java.sql.*;
public class WeatherDB {

    public static Double high;
    public static Double low;
    public static Double avg;

//reset connection string to something more generic or run a method to check system and then pick based on system
    public static final String DB_NAME = "weather.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:/media/brian/Backup 2/Documents/Tablet Backup/Documents/Courses/Java/personal projects/Farmhand6/src/main/Weather/weatherdb/" + DB_NAME;

    // create db (implemented @ initial run time, streamline where DB created)

    public static void createWeatherDB() {
        try {
            Connection conn = DriverManager.getConnection(CONNECTION_STRING);
            Statement statement = conn.createStatement();

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS hourly " +
                    " (hourly_id_ INTEGER PRIMARY KEY AUTOINCREMENT, timestamp TIME, pressure DOUBLE, temperature DOUBLE, wind DOUBLE," +
                    " humidity DOUBLE, observable_time STRING)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS daily " +
                    " (daily_id_ INTEGER PRIMARY KEY AUTOINCREMENT, date LONG, high_temp DOUBLE, low_temp DOUBLE, avg_temp DOUBLE," +
                    "high_wind DOUBLE, avg_wind DOUBLE, avg_humid DOUBLE, high_pressure DOUBLE, low_pressure DOUBLE, avg_pressure DOUBLE," +
                    "observable_date STRING)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS weekly " +
                    " (weekly_id_ INTEGER PRIMARY KEY AUTOINCREMENT, week_ending LONG, high_temp DOUBLE, low_temp DOUBLE, avg_temp DOUBLE," +
                    "high_wind DOUBLE, avg_wind DOUBLE, avg_humid DOUBLE, high_pressure DOUBLE, low_pressure DOUBLE, avg_pressure DOUBLE, observable_week_ending STRING)");

            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void log() {
        if (WDBDaily.compareDates() && WeatherDB.getID("daily_id_","daily", WDBDaily.getDailyEntries) == 7){
            WDBWeekly.writeToWeekly();
            WeatherDB.resetTable("daily");
            WDBDaily.writeToDaily();
            WeatherDB.resetTable("hourly");
        }else if (WDBDaily.compareDates() && WeatherDB.getID("hourly_id_", "hourly", WDBHourly.getHourlyEntries) > 0){
            WDBDaily.writeToDaily();
            WeatherDB.resetTable("hourly");
        }
        WDBHourly.writeToHourly();
        }

    public static void resetTable(String table) {
        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            statement.executeUpdate("DELETE FROM '" + table + "'");
            statement.executeUpdate("UPDATE `sqlite_sequence` SET `seq` = 0 WHERE `name` = '" + table + "'");

            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Getters
    public static Double getHigh (String column, String table){
        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            String sql2 = "SELECT MAX" + "(" +column+ ")" + " as maxtemp FROM '" + table + "' ";
            ResultSet rs2 = statement.executeQuery(sql2);
            high = rs2.getDouble("maxtemp");
            rs2.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return high;
    }

    public static Double getLow (String column, String table){
        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            String sql3 = "SELECT MIN" +  "(" +column+ ")" + " as mintemp FROM '" + table + "' ";
            ResultSet rs3 = statement.executeQuery(sql3);
            low = rs3.getDouble("mintemp");
            rs3.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return low;
    }

    public static Double getAvg (String column, String table){
        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            String sql4 = "SELECT AVG" + "(" + column + ")" + "as avgtemp FROM '" + table + "' ";
            ResultSet rs4 = statement.executeQuery(sql4);
            avg = rs4.getDouble("avgtemp");
            rs4.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return avg;
    }

    public static Integer getID(String column, String table, int variable) {

        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            String sql2 = "SELECT MAX" + "(" +column+ ")" + " as lastid FROM '" + table + "' ";
            ResultSet rs2 = statement.executeQuery(sql2);
            variable = rs2.getInt("lastid");
            rs2.close();
            statement.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return variable;
    }
}
