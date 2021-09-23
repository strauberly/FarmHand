package main.Weather.weatherdb;

import java.sql.*;
import java.text.Format;
import java.text.SimpleDateFormat;


public class WDBHourly {

    private static String pressure = null;
    public static String temp = null;
    private static String wind = null;
    private static String humidity = null;
    private static String convertedCurrentDate;
    public static String convertedLoggedDate;
    private static long timeStamp;
    private static long loggedDate = 0;
    public static int getHourlyEntries;

    // create db (implemented @ initial run time, streamline where connected)

    // log method calls getHourlyDate if result == null writes to hourly table

    // if query finds a logged timestamp it converts and  becomes logged date
    // if logged date == current date >> writes to hourly table

    // else if logged date != current date and number of daily entries == 7 >>
    // total daily values and write as weekly entry >> delete daily entries >>
    // total hourly entries and write as daily entry >> delete hourly entries >>
    // write to hourly table

    // else if logged date != current date and number of daily entries < 7 >>
    // total hourly entries and write as daily entry >> delete hourly entries >>
    // write to hourly table

// avg hourly entries >> write as daily entries >> delete hourly entries >>
// log current fields to hourly


//


//    public static Int getDailyEntries(){
//        //connect
//        //query result
//        //compare return
//        //
//    }


    //    public static void writeToDaily(){
//          connect
//          return hourly results
    //      total
    //      write
    //      repeat for each daily column
    //      delete hourly entries
    //      write to hourly
//    }

    //    public static void writeToWeekly(){
//          connect
//          return daily results
    //      total
    //      write
    //      repeat for each weekly column
    //      delete daily entries
    //      write to daily
    //      write to hourly
//}

    // write to

    public static void writeToHourly() {
        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO hourly(time, pressure, temperature, wind, humidity)" +
                    "VALUES ( " + timeStamp + ", " + pressure + ", " +
                    temp + ", " + wind + ", " + humidity + ")");
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

    // time

    public static Time convertedCurrentTime() {
        return new Time(timeStamp);
    }

    public static Date convertedCurrentDate() {
        return new Date(timeStamp);
    }

    public static Date convertedLoggedDate() {
        return new Date(getLoggedDate());
    }

    public static String convertedCurrentDateString(){
        Date currentDate = new Date(timeStamp);
        Format format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(currentDate);
    }

    public static String convertedLoggedDateString(){
        Date currentDate = new Date(WDBHourly.getLoggedDate());
        Format format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(currentDate);
    }

    public static boolean compareDates() {
        return WDBHourly.convertedCurrentDateString().equals(WDBHourly.convertedLoggedDateString());
    }


    //getters and setters
    public static Integer getID(String column, String table, int variable) {

        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            String sql = "SELECT MAX" + "(" +column+ ")" + " as lastid FROM '" + table + "' ";
            ResultSet rs = statement.executeQuery(sql);
            variable = rs.getInt("lastid");
            rs.close();
            statement.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return variable;
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




    public static long getTimestamp(){
        return timeStamp;
    }

    public static void setTimeStamp(long timeStamp){
        WDBHourly.timeStamp = timeStamp;
    }

    public static String getPressure() {
        return pressure;
    }

    public static void setPressure(String pressure) {
        WDBHourly.pressure = pressure;
    }

    public static String getTemp() {
        return temp;
    }

    public static void setTemp(String temp) {
        WDBHourly.temp = temp;
    }

    public static String getWind() {
        return wind;
    }

    public static void setWind(String wind) {
        WDBHourly.wind = wind;
    }

    public static String getHumidity() {
        return humidity;
    }

    public static void setHumidity(String humidity) {
        WDBHourly.humidity = humidity;
    }
}

