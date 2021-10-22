package main.Weather.weatherdb;

import java.sql.*;
import java.text.Format;
import java.text.SimpleDateFormat;

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
    public static int getWeeklyEntries;
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

    public static void writeToWeekly() {
        try {
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
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        WDBWeekly.printWeeklyLog();
    }

    public static void printWeeklyLog(){
        System.out.println(
                "Wrote to Weekly totals for week ending: " +
                        "\n" +  endingDate +
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

    public static long getWeeklyTimestamp() {

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

    public static String getEndingDate() {
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

    public String getWeekending() {
        return weekending;
    }

    public void setWeekending(String weekending) {
        this.weekending = weekending;
    }

    public String getWeeklyhighpressure() {
        return weeklyhighpressure;
    }

    public void setWeeklyhighpressure(String weeklyhighpressure) {
        this.weeklyhighpressure = weeklyhighpressure;
    }

    public String getWeeklylowpressure() {
        return weeklylowpressure;
    }

    public void setWeeklylowpressure(String weeklylowpressure) {
        this.weeklylowpressure = weeklylowpressure;
    }

    public String getWeeklyavgpressure() {
        return weeklyavgpressure;
    }

    public void setWeeklyavgpressure(String weeklyavgpressure) {
        this.weeklyavgpressure = weeklyavgpressure;
    }

    public String getWeeklyhightemp() {
        return weeklyhightemp;
    }

    public void setWeeklyhightemp(String weeklyhightemp) {
        this.weeklyhightemp = weeklyhightemp;
    }

    public String getWeeklylowtemp() {
        return weeklylowtemp;
    }

    public void setWeeklylowtemp(String weeklylowtemp) {
        this.weeklylowtemp = weeklylowtemp;
    }

    public String getWeeklyavgtemp() {
        return weeklyavgtemp;
    }

    public void setWeeklyavgtemp(String weeklyavgtemp) {
        this.weeklyavgtemp = weeklyavgtemp;
    }

    public String getWeeklyhighwind() {
        return weeklyhighwind;
    }

    public void setWeeklyhighwind(String weeklyhighwind) {
        this.weeklyhighwind = weeklyhighwind;
    }

    public String getWeeklyavgwind() {
        return weeklyavgwind;
    }

    public void setWeeklyavgwind(String weeklyavgwind) {
        this.weeklyavgwind = weeklyavgwind;
    }

    public String getWeeklyavghumid() {
        return weeklyavghumid;
    }

    public void setWeeklyavghumid(String weeklyavghumid) {
        this.weeklyavghumid = weeklyavghumid;
    }
}