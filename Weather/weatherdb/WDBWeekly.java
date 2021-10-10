package main.Weather.weatherdb;

import java.sql.*;

public class WDBWeekly {


    public static Long endingDate;
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
                        "\n" +  WDBWeekly.convertedEndingDate() +
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

    public static Date convertedEndingDate() {
        return new Date(WDBDaily.getEndingDate());
    }

}

//---------------------------------------------------------------------------------------------------------------------
// package main.Weather.weatherdb;
//
//import java.sql.*;
//
//public class WDBWeekly {
//
//    public static String endingDate;
//    public static int getWeeklyEntries;
//    public static long weekEndingTimestamp;
//
//    //pressure
//    private static final Double weeklyHighPressure = WeatherDB.getHigh("high_pressure", "daily");
//    private static final Double weeklyLowPressure = WeatherDB.getLow("low_pressure", "daily");
//    private static final Double weeklyAvgPressure = WeatherDB.getAvg("avg_pressure", "daily");
//
//
//    //temp
//    private static final Double weeklyHighTemp = WeatherDB.getHigh("high_temp", "daily");
//    private static final Double weeklyLowTemp = WeatherDB.getLow("low_temp", "daily");
//    private static final Double weeklyAvgTemp = WeatherDB.getAvg("avg_temp", "daily");
//
//    //wind
//    private static final Double weeklyHighWind = WeatherDB.getHigh("high_wind", "daily");
//    private static final Double weeklyAvgWind = WeatherDB.getAvg("avg_wind", "daily");
//
//    //humidity
//    private static final Double weeklyAvgHumidity = WeatherDB.getAvg("avg_humid", "daily");
//
//    public static void writeToWeekly() {
//        try {
//            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
//            Statement statement = conn.createStatement();
//            statement.executeUpdate("INSERT INTO weekly(weekly_timestamp, high_temp, low_temp, avg_temp," +
//                    "high_wind, avg_wind, avg_humid, high_pressure, low_pressure, avg_pressure, observable_week_ending)" +
//                    "VALUES ( " + weekEndingTimestamp + "," + weeklyHighTemp + "," + weeklyLowTemp + "," + weeklyAvgTemp +
//                    "," + weeklyHighWind + "," + weeklyAvgWind + "," + weeklyAvgHumidity + "," + weeklyHighPressure +
//                    "," + weeklyLowPressure + "," + weeklyAvgPressure + "," +endingDate+")");
//
//            statement.close();
//            conn.close();
//        } catch (SQLException e) {
//            System.out.println("Error: " + e.getMessage());
//            e.printStackTrace();
//        }
//        WDBWeekly.printWeeklyLog();
//    }
//
//    public static void printWeeklyLog() {
//        System.out.println(
//                "Wrote to Weekly totals for week ending: " +
//                        "\n" + getEndingDate() +
//                        "\n" + "weekly high pressure = " + weeklyHighPressure +
//                        "\n" + "weekly low pressure = " + weeklyLowPressure +
//                        "\n" + "weekly avg pressure = " + weeklyAvgPressure +
//                        "\n" + "weekly high temp = " + weeklyHighTemp +
//                        "\n" + "weekly low temp = " + weeklyLowTemp +
//                        "\n" + "weekly avg temp = " + weeklyAvgTemp +
//                        "\n" + "weekly high wind = " + weeklyHighWind +
//                        "\n" + "weekly avg wind = " + weeklyAvgWind +
//                        "\n" + "weekly avg humidity = " + weeklyAvgHumidity +
//                        "\n" + "weekly entries = "
//                        + WeatherDB.getID("weekly_id_", "weekly", getWeeklyEntries) + "\n"
//                        + "time stamp = " + weekEndingTimestamp + "\n"
//                        + "week ending = " + endingDate + "\n");
//    }
//
////    public static Date convertedEndingDate() {
////        return new Date(WDBWeekly.getEndingDate());
////    }
//    public static String getEndingDate() {
//        try {
//            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
//            Statement stmt = conn.createStatement();
//
//            String sql = "SELECT * FROM daily ORDER by observable_date";
//            ResultSet resultSet = stmt.executeQuery(sql);
//            while (resultSet.next())
//             endingDate = resultSet.getString("observable_date");
//
//            resultSet.close();
//            stmt.close();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return endingDate;
//    }
//    public static Long getWeekEndingTimeStamp() {
//        try {
//            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
//            Statement stmt = conn.createStatement();
//
//            String sql = "SELECT * FROM daily ORDER by daily_timestamp DESC LIMIT 1";
//            ResultSet resultSet = stmt.executeQuery(sql);
//            while (resultSet.next())
//                weekEndingTimestamp = resultSet.getLong("daily_timestamp");
//
//            resultSet.close();
//            stmt.close();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return weekEndingTimestamp;
//    }
//}
//
//
//
