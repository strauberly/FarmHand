package com.zeronthirty.farmhandmaven.weather.weatherdb;

import java.sql.*;
import java.util.ArrayList;


public class WeatherDB {

    private static String high;
    private static String low;
    private static String avg;
    private static final String DB_NAME = "src/main/resources/com/zeronthirty/farmhandmaven/DB/weather.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:" + DB_NAME;


    //creation of weather database
    public static void createWeatherDB() {
        try {
            Connection conn = DriverManager.getConnection(CONNECTION_STRING);
            Statement statement = conn.createStatement();

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS hourly " +
                    " (hourly_id_ INTEGER PRIMARY KEY AUTOINCREMENT, timestamp TIME, pressure STRING, temperature STRING, wind STRING," +
                    " humidity STRING, time STRING)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS daily " +
                    " (daily_id_ INTEGER PRIMARY KEY AUTOINCREMENT, timestamp LONG, highpressure STRING, lowpressure STRING, avgpressure STRING," +
                    "hightemp STRING, lowtemp STRING, avgtemp STRING, highwind STRING, avgwind STRING, avghumid STRING, date STRING)");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS weekly " +
                    " (weekly_id_ INTEGER PRIMARY KEY AUTOINCREMENT, weeklytimestamp LONG, weeklyhighpressure STRING, weeklylowpressure STRING, weeklyavgpressure STRING," +
                    "weeklyhightemp STRING, weeklylowtemp STRING, weeklyavgtemp STRING, weeklyhighwind STRING, weeklyavgwind STRING, weeklyavghumid STRING, weekending STRING)");

            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //methods for when to log data to which table and in what order
    public static void log() throws SQLException {
        if (WDBDaily.notSameDates() && WeatherDB.getID("daily_id_","daily", WDBDaily.getDailyEntries) == 7){
            WDBWeekly.writeToWeekly();
            WeatherDB.resetTable("daily");
            WDBDaily.writeToDaily();
            WeatherDB.resetTable("hourly");
        }else if (WDBDaily.notSameDates() && WeatherDB.getID("hourly_id_", "hourly", WDBHourly.getHourlyEntries) > 0){
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

    // few queries implemented in other classes
    public static String getHigh (String column, String table){
        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            String sql2 = "SELECT MAX" + "(" +column+ ")" + " as max FROM '" + table + "'";
            ResultSet rs2 = statement.executeQuery(sql2);
            high = rs2.getString("max");
            rs2.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return high;
    }

    public static String getLow (String column, String table){
        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            String sql3 = "SELECT MIN" +  "(" +column+ ")" + " as min FROM '" + table + "' ";
            ResultSet rs3 = statement.executeQuery(sql3);
            low = rs3.getString("min");
            rs3.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return low;
    }

    public static String getAvg (String column, String table){
        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            String sql4 = "SELECT AVG" + "(" + column + ")" + "as avg FROM '" + table + "' ";
            ResultSet rs4 = statement.executeQuery(sql4);
            avg = rs4.getString("avg");
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

    public static double last3ReadingsAvg(String column, String table) {
        double average = 0;
        try {
            Connection conn = DriverManager.getConnection(CONNECTION_STRING);
            Statement statement = conn.createStatement();
            String sql2 = "SELECT AVG" + "(" + column + ")" + "as avg FROM" +
                    "(SELECT" + "(" + column + ")" + "FROM" + "(" + table + ")" + "ORDER BY hourly_id_ DESC limit 3)";
            ResultSet rs2 = statement.executeQuery(sql2);
            average = rs2.getDouble("avg");
            rs2.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return average;
    }

    public static double last3ReadingsDiff(String column, String table) {
        double threeHourDiff;
        ArrayList<Double> readings = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(CONNECTION_STRING);
            Statement statement = conn.createStatement();
            String sql = "SELECT" + "(" + column + ")" + "as readings FROM" + "(" + table + ")" + "ORDER BY hourly_id_ DESC limit 3";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                readings.add(rs.getDouble("readings"));
            }
            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        threeHourDiff = Math.abs(readings.get(0) - readings.get(2));
        return threeHourDiff;
    }
}
