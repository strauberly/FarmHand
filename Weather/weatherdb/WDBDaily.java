package main.Weather.weatherdb;
import java.sql.*;
import java.text.Format;
import java.text.SimpleDateFormat;

public class WDBDaily {

    private String date;

    private String highpressure;
    private String lowpressure;
    private String  avgpressure;

    private String hightemp;
    private String lowtemp;
    private String avgtemp;

    private String highwind;
    private String avgwind;

    private String avghumid;

    private static long loggedDate;
    private static final Long dateValue = getLoggedDate();
    public static int getDailyEntries;
    private static String observableDate;

    //
//    private double highPressureValueValue;
//    private double lowPressureValueValue;
//    private double avgPressureValueValue;
//
//    private double highTempValue;
//    private double lowTempValue;
//    private double avgTempValue;
//
//    private double highWindValue;
//    private double avgWindValue;
//
//    private double avgHumidityValue;
//
//    private String observableDateValue;


//rewrite same format as hourly try one column at a time first
    //pressure
    private static  String highPressureValue = WeatherDB.getHigh("pressure", "hourly");
    private  static String lowPressureValue = WeatherDB.getLow("pressure", "hourly");
    private static String avgPressureValue = WeatherDB.getAvg("pressure", "hourly");


    //temp
    private static String highTempValue = WeatherDB.getHigh("temperature", "hourly");
    private static String  lowTempValue = WeatherDB.getLow("temperature", "hourly");
    private static String  avgTempValue = WeatherDB.getAvg("temperature", "hourly");

    //wind
    private static String highWindValue  = WeatherDB.getHigh("wind", "hourly");
    private static String  avgWindValue = WeatherDB.getAvg("wind", "hourly");

    //humidity
    private static String avgHumidityValue = WeatherDB.getAvg("humidity", "hourly");

    public WDBDaily(String date, String highpressure, String lowpressure, String avgpressure, String hightemp,
                    String lowtemp, String avgtemp, String highwind, String avgwind, String avghumid) {
        this.date = date;
        this.highpressure = highpressure;
        this.lowpressure = lowpressure;
        this.avgpressure = avgpressure;
        this.hightemp = hightemp;
        this.lowtemp = lowtemp;
        this.avgtemp = avgtemp;
        this.highwind = highwind;
        this.avgwind = avgwind;
        this.avghumid = avghumid;
    }

    //    private String observabledate;
//
//    public WDBDaily(String observabledate) {
//        this.observabledate = observabledate;
        //        this.highPressureValueValue = WeatherDB.getHigh("pressure", "hourly");
//        this.lowPressureValueValue = lowPressureValueValue;
//        this.avgPressureValueValue = avgPressureValueValue;
//        this.highTempValue = highTempValue;
//        this.lowTempValue = lowTempValue;
//        this.avgTempValue = avgtempValue;
//        this.highWindValue = highWindValue;
//        this.avgWindValue = avgWindValue;
//        this.avgHumidityValue = avgHumidityValue;




    //    public WDBDaily(double highPressureValueValue, double lowPressureValueValue, double avgPressureValueValue, double highTempValue,
//                    double lowTempValue, double avgtempValue, double highWindValue, double avgWindValue,
//                    double avgHumidityValue, String observableDateValue) {
//        this.highPressureValueValue = WeatherDB.getHigh("pressure", "hourly");
//        this.lowPressureValueValue = lowPressureValueValue;
//        this.avgPressureValueValue = avgPressureValueValue;
//        this.highTempValue = highTempValue;
//        this.lowTempValue = lowTempValue;
//        this.avgTempValue = avgtempValue;
//        this.highWindValue = highWindValue;
//        this.avgWindValue = avgWindValue;
//        this.avgHumidityValue = avgHumidityValue;
//        this.observableDateValue = observableDateValue;
//    }

//
//    public WDBDaily(String observabledate) {
//        this.observabledate = observableDate;
//    }

//
//    public WDBDaily(String date, double highpressure, double low_pressure, double avg_pressure, double high_temp, double low_temp, double avg_temp,
//                    double high_wind, double avg_wind, double avg_humid){
//        this.date = date;
//        this.highpressure = highpressure;
//        this.low_pressure = low_pressure;
//        this. avg_pressure = avg_pressure;
//
//    }



//    , String low_pressure, String avg_pressure, String high_temp,
//    String low_temp, String avg_temp, String high_wind,
//    String avg_wind, String avg_humid

    public static void writeToDaily(){
        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            statement.execute("INSERT INTO daily(timestamp, hightemp, lowtemp, avgtemp," +
                    "highwind, avgwind, avghumid, highpressure, lowpressure, avgpressure)" +
                    "VALUES ( "+ dateValue + "," + highTempValue + "," + lowTempValue + "," + avgTempValue +
                    "," + highWindValue + "," + avgWindValue + "," + avgHumidityValue + "," + highPressureValue +
                    "," + lowPressureValue + "," + avgPressureValue + ")");
            observableDateConversion();
            statement.executeUpdate("UPDATE daily SET date = ('" + observableDate + "')" + "WHERE daily_id_ = (SELECT max(daily_id_) FROM daily)");

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
                        "\n" +"Daily high pressure = " + highPressureValue +
                        "\n" + "Daily low pressure = " + lowPressureValue +
                        "\n" + "Daily avg pressure = " + avgPressureValue +
                        "\n" + "Daily high temp = " + highTempValue +
                        "\n" + "Daily low temp = " + lowTempValue +
                        "\n" + "Daily avg temp = " + avgTempValue +
                        "\n" + "Daily high wind = " + highWindValue +
                        "\n" + "Daily avg wind = " + avgWindValue +
                        "\n" + "Daily avg humidity = " + avgHumidityValue +
                        "\n" + "Daily entries = "
                        + WeatherDB.getID("daily_id_","daily", getDailyEntries) + "\n");
    }


    public static void observableDateConversion() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            String sql = "SELECT timestamp FROM daily";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                long loggedDate = rs.getLong("timestamp");
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

    public void setHighPressureValue() {
        highPressureValue = WeatherDB.getHigh("pressure", "hourly");
    }

    public void setLowPressureValue() {
        lowPressureValue = WeatherDB.getLow("pressure", "hourly");
    }

    public void setAvgPressureValueValue() {
        avgPressureValue = WeatherDB.getAvg("pressure", "hourly");
    }

    public void setHighTempValue() {
        highTempValue = WeatherDB.getHigh("temperature", "hourly");
    }

    public void setLowTempValue() {
        lowTempValue = WeatherDB.getLow("temperature", "hourly");

    }
    public void setAvgTempValue() {
        avgTempValue = WeatherDB.getAvg("temperature", "hourly");

    }

    public void setHighWindValue() {
        highWindValue = WeatherDB.getHigh("wind", "hourly");
    }

    public void setAvgWindValue() {
        avgWindValue = WeatherDB.getAvg("wind", "hourly");
    }

    public void setAvgHumidityValue() {
        avgHumidityValue = WeatherDB.getAvg("humidity", "hourly");
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHighpressure() {
        return highpressure;
    }

    public void setHighpressure(String highpressure) {
        this.highpressure = highpressure;
    }

    public String getLowpressure() {
        return lowpressure;
    }

    public void setLowpressure(String lowpressure) {
        this.lowpressure = lowpressure;
    }

    public String getAvgpressure() {
        return avgpressure;
    }

    public void setAvgpressure(String avgpressure) {
        this.avgpressure = avgpressure;
    }

    public String getHightemp() {
        return hightemp;
    }

    public void setHightemp(String hightemp) {
        this.hightemp = hightemp;
    }

    public String getLowtemp() {
        return lowtemp;
    }

    public void setLowtemp(String lowtemp) {
        this.lowtemp = lowtemp;
    }

    public String getAvgtemp() {
        return avgtemp;
    }

    public void setAvgtemp(String avgtemp) {
        this.avgtemp = avgtemp;
    }

    public String getHighwind() {
        return highwind;
    }

    public void setHighwind(String highwind) {
        this.highwind = highwind;
    }

    public String getAvgwind() {
        return avgwind;
    }

    public void setAvgwind(String avgwind) {
        this.avgwind = avgwind;
    }

    public String getAvghumid() {
        return avghumid;
    }

    public void setAvghumid(String avghumid) {
        this.avghumid = avghumid;
    }
}
