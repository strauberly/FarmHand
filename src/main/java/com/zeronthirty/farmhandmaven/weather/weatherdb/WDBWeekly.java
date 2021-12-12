package com.zeronthirty.farmhandmaven.weather.weatherdb;

import java.sql.*;

//evaluate if variables can become more concise after you have weekly data
public class WDBWeekly {

    private String weekending;

    private String weeklyhighpressure;
    private String weeklylowpressure;
    private String  weeklyavgpressure;

    private String weeklyhightemp;
    private String weeklylowtemp;
    private String weeklyavgtemp;

    private String weeklyhighwind;
    private String weeklyavgwind;

    private String weeklyavghumid;

    private static String endingDate = getEndingDate();
    private static int getWeeklyEntries;
    private static long weeklyTimestamp = getWeeklyTimestamp();

    //pressure
    private static final String weeklyHighPressure = WeatherDB.getHigh("highpressure", "daily");
    private static final String weeklyLowPressure = WeatherDB.getLow("lowpressure", "daily");
    private static final String weeklyAvgPressure = WeatherDB.getAvg("avgpressure", "daily");


    //temp
    private static final String weeklyHighTemp = WeatherDB.getHigh("hightemp", "daily");
    private static final String weeklyLowTemp = WeatherDB.getLow("lowtemp", "daily");
    private static final String weeklyAvgTemp = WeatherDB.getAvg("avgtemp", "daily");

    //wind
    private static final String weeklyHighWind = WeatherDB.getHigh("highwind", "daily");
    private static final String weeklyAvgWind = WeatherDB.getAvg("avgwind", "daily");

    //humidity
    private static final String weeklyAvgHumidity = WeatherDB.getAvg("avghumid", "daily");

    public WDBWeekly(String weekending, String weeklyhighpressure, String weeklylowpressure, String weeklyavgpressure,
                     String weeklyhightemp, String weeklylowtemp, String weeklyavgtemp, String weeklyhighwind,
                     String weeklyavgwind, String weeklyavghumid) {

        this.weekending = weekending;
        this.weeklyhighpressure = weeklyhighpressure;
        this.weeklylowpressure = weeklylowpressure;
        this.weeklyavgpressure = weeklyavgpressure;
        this.weeklyhightemp = weeklyhightemp;
        this.weeklylowtemp = weeklylowtemp;
        this.weeklyavgtemp = weeklyavgtemp;
        this.weeklyhighwind = weeklyhighwind;
        this.weeklyavgwind = weeklyavgwind;
        this.weeklyavghumid = weeklyavghumid;
    }
    // write to weekly table in weather database
    public static void writeToWeekly() throws SQLException {

        Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
        Statement statement = conn.createStatement();
        statement.executeUpdate("INSERT INTO weekly(weeklytimestamp, weeklyhightemp, weeklylowtemp, weeklyavgtemp," +
                "weeklyhighwind, weeklyavgwind, weeklyavghumid, weeklyhighpressure, weeklylowpressure, weeklyavgpressure)" +
                "VALUES ( " + weeklyTimestamp + "," + weeklyHighTemp + "," + weeklyLowTemp + "," + weeklyAvgTemp +
                "," + weeklyHighWind + "," + weeklyAvgWind + "," + weeklyAvgHumidity + "," + weeklyHighPressure +
                "," + weeklyLowPressure + "," + weeklyAvgPressure + ")");
        statement.executeUpdate("UPDATE weekly SET weekending = ('" + endingDate + "')" + "WHERE weekly_id_ = (SELECT max(weekly_id_) FROM weekly)");
        statement.close();
        conn.close();
    }
// ** feedback method
//    private static void printWeeklyLog(){
//        System.out.println(
//                "Wrote to Weekly totals for week ending: " +
//                        "\n" +  endingDate +
//                        "\n" +"weekly high pressure = " + weeklyHighPressure +
//                        "\n" + "weekly low pressure = " + weeklyLowPressure +
//                        "\n" + "weekly avg pressure = " + weeklyAvgPressure +
//                        "\n" + "weekly high temp = " + weeklyHighTemp +
//                        "\n" + "weekly low temp = " + weeklyLowTemp +
//                        "\n" + "weekly avg temp = " + weeklyAvgTemp +
//                        "\n" + "weekly high wind = " + weeklyHighWind +
//                        "\n" + "weekly avg wind = " + weeklyAvgWind +
//                        "\n" + "weekly avg humidity = " + weeklyAvgHumidity +
//                        "\n" + "weekly entries = "
//                        + WeatherDB.getID("weekly_id_", "weekly", getWeeklyEntries) + "\n");
//    }
//    *******************************

    // queries from weekly table
    private static long getWeeklyTimestamp() {

        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement stmt = conn.createStatement();

            String sql = "SELECT * FROM daily ORDER by timestamp DESC LIMIT 1";
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next())
                weeklyTimestamp = resultSet.getLong("timestamp");

            resultSet.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return weeklyTimestamp;
    }

    private static String getEndingDate() {
        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement stmt = conn.createStatement();

            String sql = "SELECT * FROM daily ORDER by date DESC LIMIT 1";
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next())
                endingDate = resultSet.getString("date");

            resultSet.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return endingDate;
    }

    private String getWeekending() {
        return weekending;
    }

    private void setWeekending(String weekending) {
        this.weekending = weekending;
    }

    private String getWeeklyhighpressure() {
        return weeklyhighpressure;
    }

    private void setWeeklyhighpressure(String weeklyhighpressure) {
        this.weeklyhighpressure = weeklyhighpressure;
    }

    private String getWeeklylowpressure() {
        return weeklylowpressure;
    }

    private void setWeeklylowpressure(String weeklylowpressure) {
        this.weeklylowpressure = weeklylowpressure;
    }

    private String getWeeklyavgpressure() {
        return weeklyavgpressure;
    }

    private void setWeeklyavgpressure(String weeklyavgpressure) {
        this.weeklyavgpressure = weeklyavgpressure;
    }

    private String getWeeklyhightemp() {
        return weeklyhightemp;
    }

    private void setWeeklyhightemp(String weeklyhightemp) {
        this.weeklyhightemp = weeklyhightemp;
    }

    private String getWeeklylowtemp() {
        return weeklylowtemp;
    }

    private void setWeeklylowtemp(String weeklylowtemp) {
        this.weeklylowtemp = weeklylowtemp;
    }

    private String getWeeklyavgtemp() {
        return weeklyavgtemp;
    }

    private void setWeeklyavgtemp(String weeklyavgtemp) {
        this.weeklyavgtemp = weeklyavgtemp;
    }

    private String getWeeklyhighwind() {
        return weeklyhighwind;
    }

    private void setWeeklyhighwind(String weeklyhighwind) {
        this.weeklyhighwind = weeklyhighwind;
    }

    private String getWeeklyavgwind() {
        return weeklyavgwind;
    }

    private void setWeeklyavgwind(String weeklyavgwind) {
        this.weeklyavgwind = weeklyavgwind;
    }

    private String getWeeklyavghumid() {
        return weeklyavghumid;
    }

    private void setWeeklyavghumid(String weeklyavghumid) {
        this.weeklyavghumid = weeklyavghumid;
    }
}