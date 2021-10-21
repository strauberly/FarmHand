package main.Weather.weatherdb;
import java.sql.*;
import java.text.Format;
import java.text.SimpleDateFormat;

public class WDBDaily {

    private static long loggedDate;
    private static final Long date = getLoggedDate();
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
    private static  double highPressureValue = WeatherDB.getHigh("pressure", "hourly");
    private  static double lowPressureValue = WeatherDB.getLow("pressure", "hourly");
    private static double avgPressureValue = WeatherDB.getAvg("pressure", "hourly");


    //temp
    private static double highTempValue = WeatherDB.getHigh("temperature", "hourly");
    private static double  lowTempValue = WeatherDB.getLow("temperature", "hourly");
    private static double  avgTempValue = WeatherDB.getAvg("temperature", "hourly");

    //wind
    private static double highWindValue  = WeatherDB.getHigh("wind", "hourly");
    private static double  avgWindValue = WeatherDB.getAvg("wind", "hourly");

    //humidity
    private static double avgHumidityValue = WeatherDB.getAvg("humidity", "hourly");

    private String observable_date;

    public WDBDaily(String observable_date) {
        //        this.highPressureValueValue = WeatherDB.getHigh("pressure", "hourly");
//        this.lowPressureValueValue = lowPressureValueValue;
//        this.avgPressureValueValue = avgPressureValueValue;
//        this.highTempValue = highTempValue;
//        this.lowTempValue = lowTempValue;
//        this.avgTempValue = avgtempValue;
//        this.highWindValue = highWindValue;
//        this.avgWindValue = avgWindValue;
//        this.avgHumidityValue = avgHumidityValue;
    }



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

    public static void writeToDaily(){
        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            statement.execute("INSERT INTO daily(date, high_temp, low_temp, avg_temp," +
                    "high_wind, avg_wind, avg_humid, high_pressure, low_pressure, avg_pressure)" +
                    "VALUES ( "+ date + "," + highTempValue + "," + lowTempValue + "," + avgTempValue +
                    "," + highWindValue + "," + avgWindValue + "," + avgHumidityValue + "," + highPressureValue +
                    "," + lowPressureValue + "," + avgPressureValue + ")");
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

    public void setObservableDate(String observable_date) {
        this.observable_date = observable_date;
    }
}
