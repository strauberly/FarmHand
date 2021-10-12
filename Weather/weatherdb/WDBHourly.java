package main.Weather.weatherdb;
import java.sql.*;

public class WDBHourly {

    private static String observableTime;
    private static String pressure;
    public static String temp;
    private static String wind;
    private static String humidity;
    private static long timeStamp;
    private static long loggedDate;
    protected static int getHourlyEntries;

    // create actual class!!! simple versions observable lists etc.


    // write to hourly table

        public static void writeToHourly() {
        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO hourly (timestamp, pressure, temperature, wind, humidity)" +
                    "VALUES ( " + timeStamp + ", " + pressure + ", "
                    + temp + ", " + wind + ", " + humidity + ")");
            observableTimeConversion();
            statement.executeUpdate("UPDATE hourly SET observable_time = ('" + observableTime + "')" + "WHERE hourly_id_ = (SELECT max(hourly_id_) FROM hourly)");
            statement.close();
            conn.close();
            WDBHourly.printHourlyLog();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void printHourlyLog() {
        System.out.println(
                "Wrote to hourly: " +
                        "\n" + WDBHourly.convertedCurrentTime() +
                        "\n" + "Pressure = " + pressure +
                        "\n" + "Temp = " + temp +
                        "\n" + "Wind = " + wind +
                        "\n" + "Humidity = " + humidity +
                        "\n" + "Hourly entries = "
                        + WeatherDB.getID("hourly_id_","hourly", getHourlyEntries) + "\n"
        );
    }

    // time methods

        public static void observableTimeConversion() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            String sql = "SELECT timestamp FROM hourly";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                long loggedTime = rs.getLong("timestamp");
                observableTime = convertedLoggedTime(loggedTime);
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

            String sql = "SELECT * FROM hourly ORDER by time DESC LIMIT 1";
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next())
            loggedDate = resultSet.getLong("time");

            resultSet.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loggedDate;
    }

    public static String convertedLoggedTime(Long time) {
        return String.valueOf(new Time(time));
    }

    private static Time convertedCurrentTime() {
        return new Time(timeStamp);
    }

    public static void setTimeStamp(long timeStamp){
        WDBHourly.timeStamp = timeStamp;
    }

    public static long getTimeStamp() {
        return timeStamp;
    }

    public static void setPressure(String pressure) {
        WDBHourly.pressure = pressure;
    }

    public static void setTemp(String temp) {
        WDBHourly.temp = temp;
    }

    public static void setWind(String wind) {
        WDBHourly.wind = wind;
    }

    public static void setHumidity(String humidity) {
        WDBHourly.humidity = humidity;
    }
}



