package main.Weather.weatherdb;
import java.sql.*;

public class WDBHourly {

    private static String observableTime;
    private static String pressure;
    public static String temp;
    private static String wind;
    private static String humidity;
    private static long timeStamp;
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

<<<<<<< HEAD
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
=======
    public static String convertedLoggedTime(Long time) {
        return String.valueOf(new Time(time));
>>>>>>> 87042fa5e32693dbb090c23f1edea9585cd236bb
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
<<<<<<< HEAD




//---------------------------------------------------------------------------------------------------------------------------
// package main.Weather.weatherdb;
//
//
//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.property.StringProperty;
//
//import java.sql.*;
//import java.sql.Date;
//import java.text.Format;
//import java.text.SimpleDateFormat;
//
//
//public class WDBHourly {
//
////    private static final ArrayList<String> getObservableTimes = getObservableTimes();
//    //    private final ArrayList<String> loggedTimes = new ArrayList<>();
//    private static SimpleStringProperty timeObject;
//    //    private final double pressureObject;
////    private final double temperatureObject;
////    private final double windObject;
////    private final double humidityObject;
//    private static String observableTime = null;
//    private static String pressure = null;
//    public static String temp = null;
//    private static String wind = null;
//    private static String humidity = null;
//    private static String convertedCurrentDate;
//    public static String convertedLoggedDate;
//    private static long loggedTime;
//    private static long timeStamp;
////    private static long loggedDate = 0;
//    public static int getHourlyEntries;
//
//    public WDBHourly(String timeObject) {
//        WDBHourly.timeObject = new SimpleStringProperty(timeObject);
////        this.pressureObject = pressureObject;
////        this.temperatureObject = temperatureObject;
////        this.windObject = windObject;
////        this.humidityObject = humidityObject;
//    }
//
//    public String getTimeObject() {
//        return timeObject.get();
//    }
//
//    public void setTimeObject(String timeObject) {
//        WDBHourly.timeObject.set(timeObject);
//    }
//
//    public StringProperty timeObjectProperty() {
//        return timeObject;
//    }
//
//
//    ////    public double getPressureObject() {
//////        return pressureObject;
//////    }
//////
//////    public double getTemperatureObject() {
//////        return temperatureObject;
//////    }
//////
//////    public double getWindObject() {
//////        return windObject;
//////    }
//////
//////    public double getHumidityObject() {
////        return humidityObject;
////    }
//    //streamline where connected)
//    // write to
//
//    public static void writeToHourly() {
//        try {
//            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
//            Statement statement = conn.createStatement();
//            statement.executeUpdate("INSERT INTO hourly (timestamp, pressure, temperature, wind, humidity)" +
//                    "VALUES ( " + timeStamp + ", " + pressure + ", "
//                    + temp + ", " + wind + ", " + humidity + ")");
//            observableTimeConversion();
//            //update observable time in last position
//            statement.executeUpdate("UPDATE hourly SET observable_time = ('" + observableTime + "')" + "WHERE hourly_id_ = (SELECT max(hourly_id_) FROM hourly)");
//
//            //delete last row
//            statement.close();
//            conn.close();
//            WDBHourly.printHourlyLog();
//        } catch (SQLException e) {
//            System.out.println("Error: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    public static void printHourlyLog() {
//        System.out.println(
//                "Wrote to hourly: " +
//                        "\n" + convertedTime(timeStamp) +
//                        "\n" + "Pressure = " + pressure +
//                        "\n" + "Temp = " + temp +
//                        "\n" + "Wind = " + wind +
//                        "\n" + "Humidity = " + humidity +
//                        "\n" + "Hourly entries = "
//                        + WeatherDB.getID("hourly_id_", "hourly", getHourlyEntries) + "\n"
//        );
//    }
//
//    // time
//
//    public static String convertedTime(Long time) {
//        return String.valueOf(new Time(time));
//    }
//
//    public static Date convertedCurrentDate() {
//        return new Date(timeStamp);
//    }
//
////    public static Date convertedLoggedDate() {
////        return new Date(getLoggedDate());
////    }
////
//    public static String convertedCurrentDateString() {
//        Date currentDate = new Date(timeStamp);
//        Format format = new SimpleDateFormat("yyyy-MM-dd");
//        return format.format(currentDate);
//    }
//
////    public static String convertedLoggedDateString() {
////        Date currentDate = new Date(WDBHourly.getLoggedDate());
////        Format format = new SimpleDateFormat("yyyy-MM-dd");
////        return format.format(currentDate);
////    }
//
////    public static boolean compareDates() {
////        return WDBHourly.convertedCurrentDateString().equals(WDBDaily.convertedLoggedDate(loggedDate));
////    }
//
//
//    //getters and setters
////    public static Integer getID(String column, String table, int variable) {
////
////        try {
////            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
////            Statement statement = conn.createStatement();
////            String sql = "SELECT MAX" + "(" + column + ")" + " as lastid FROM '" + table + "' ";
////            ResultSet rs = statement.executeQuery(sql);
////            variable = rs.getInt("lastid");
////            rs.close();
////            statement.close();
////            conn.close();
////
////        } catch (SQLException e) {
////            System.out.println("Error: " + e.getMessage());
////            e.printStackTrace();
////        }
////        return variable;
////    }
//
////    public static long getLoggedDate() {
////        try {
////            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
////            Statement stmt = conn.createStatement();
////
////            String sql = "SELECT * FROM hourly ORDER by timestamp DESC LIMIT 1";
////            ResultSet resultSet = stmt.executeQuery(sql);
////            while (resultSet.next())
////                loggedDate = resultSet.getLong("timestamp");
////
////            resultSet.close();
////            stmt.close();
////            conn.close();
////
////
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
////        return loggedDate;
////    }
//
//
//    public static long getTimestamp() {
//        return timeStamp;
//    }
//
//    public static void setTimeStamp(long timeStamp) {
//        WDBHourly.timeStamp = timeStamp;
//    }
//
//    public static String getPressure() {
//        return pressure;
//    }
//
//    public static void setPressure(String pressure) {
//        WDBHourly.pressure = pressure;
//    }
//
//    public static String getTemp() {
//        return temp;
//    }
//
//    public static void setTemp(String temp) {
//        WDBHourly.temp = temp;
//    }
//
//    public static String getWind() {
//        return wind;
//    }
//
//    public static void setWind(String wind) {
//        WDBHourly.wind = wind;
//    }
//
//    public static String getHumidity() {
//        return humidity;
//    }
//
//    public static void setHumidity(String humidity) {
//        WDBHourly.humidity = humidity;
//    }
//
//
//    public static void setLoggedTime(long loggedTime) {
//        WDBHourly.loggedTime = loggedTime;
//    }
//
//    public static long getLoggedTime() {
//        return loggedTime;
//    }
//
//
//    // display
//
//    //try the following for table build, see if you can get a string sql column with formatted time from list
//    // then build table following
//    // https://www.youtube.com/watch?v=LoiQVoNil9Q
//
//    public static void observableTimeConversion() throws SQLException {
//        try {
//            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
//            Statement statement = conn.createStatement();
//            String sql = "SELECT timestamp FROM hourly";
//            ResultSet rs = statement.executeQuery(sql);
//            while (rs.next()) {
//                loggedTime = rs.getLong("timestamp");
//                observableTime = convertedTime(loggedTime);
//            }
//            rs.close();
//            statement.close();
//            conn.close();
//        } catch (SQLException e) {
//            System.out.println("Error: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
////            public static ArrayList<String> getObservableTimes () {
////                //get rid of observable strategy here (next line) and have it just return list. Load that list into
//////        ObservableList<String> loggedTimes = FXCollections.observableArrayList(); // << here
////                ArrayList<String> loggedTimes = new ArrayList<>();
////                try {
////                    Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
////                    Statement statement = conn.createStatement();
////                    String sql = "SELECT timestamp FROM hourly";
////                    ResultSet rs = statement.executeQuery(sql);
////                    while (rs.next()) {
////                        loggedTime = rs.getLong("timestamp");
////                        loggedTimes.add(convertedTime(loggedTime));
////                    }
//////            loggedTimes.forEach(statement.executeUpdate("INSERT INTO hourly(observable_time)"));
////                    rs.close();
////                    statement.close();
////                    conn.close();
////                } catch (SQLException e) {
////                    System.out.println("Error: " + e.getMessage());
////                    e.printStackTrace();
////                }
////                return loggedTimes;
////            }
//}
=======
>>>>>>> 87042fa5e32693dbb090c23f1edea9585cd236bb
