package main.Weather.weatherdb;
import java.sql.*;
import java.text.Format;
import java.text.SimpleDateFormat;

public class WDBDaily {

    private static long loggedDate;
    private static final Long date = getLoggedDate();
    public static int getDailyEntries;
    private static String observableDate;


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
            statement.execute("INSERT INTO daily(date, high_temp, low_temp, avg_temp," +
                    "high_wind, avg_wind, avg_humid, high_pressure, low_pressure, avg_pressure)" +
                    "VALUES ( "+ date + "," + dailyHighTemp + "," + dailyLowTemp + "," + dailyAvgTemp +
                    "," + dailyHighWind + "," + dailyAvgWind + "," + dailyAvgHumidity + "," + dailyHighPressure +
                    "," + dailyLowPressure + "," + dailyAvgPressure + ")");
            observableDateConversion();
            statement.executeUpdate("UPDATE daily SET observable_date = ('" + observableDate + "')" + "WHERE daily_id_ = (SELECT max(daily_id_) FROM daily)");

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
                        "\n" +  observableDate +
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


    public static void observableDateConversion() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            String sql = "SELECT date FROM daily";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                long loggedDate = rs.getLong("date");
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


    public static long getLoggedDate() {
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

    public static String convertedCurrentDateString(){
        Date currentDate = new Date(WDBHourly.getTimeStamp());
        Format format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(currentDate);
    }

    public static String convertedLoggedDateString(long loggedDate){
        Date currentDate = new Date(getLoggedDate());
        Format format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(currentDate);
    }

    public static boolean compareDates() {
        return !convertedCurrentDateString().equals(convertedLoggedDateString(getLoggedDate()));
    }
}
