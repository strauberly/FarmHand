package main.Weather.stations;
import main.Weather.weatherdb.WDBDaily;
import main.Weather.weatherdb.WDBHourly;
import main.Weather.weatherdb.WDBWeekly;
import main.Weather.weatherdb.WeatherDB;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

// holds methods utilized by subsections (ie. get conditions from a station, compare data from databases)
public class Stations {

    // add work for observations and viewing logs

    private static String conditions;
    private static String pressure;
    private static String temp;
    private static String windSpeed;
    private static String humidity;



    protected static String getConditions() throws IOException {
        // takes the information entered as lat. and long. by user and applies values to api url
        String urlLat = StationsController.getLat();
        String urlLongi = StationsController.getLongi();
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + urlLat + "&lon=" + urlLongi + "&units=imperial&appid=ba12fc74c50358f79d2f837033e212d7";

        // /creates variable "apiString" then opens connection to api url and makes get request
        // scanner then receives response and appends the string and closes scanner
        StringBuilder apiString = new StringBuilder();

        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        Scanner scanner = new Scanner(url.openStream());
        while (scanner.hasNext()) {

            apiString.append(scanner.nextLine());
        }
        scanner.close();

        // parses the values of created string to build JSON Object and allows to be reparsed for values to be displayed
        // would like to develop for scanner to return response directly as JSON object and eliminate a step
        JSONParser parser = new JSONParser();
        JSONObject apiReturn = new JSONObject();
        try {
            apiReturn = (JSONObject) parser.parse(String.valueOf(apiString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject sys = (JSONObject) apiReturn.get("sys");
        JSONObject coord = (JSONObject) apiReturn.get("coord");
        JSONObject main = (JSONObject) apiReturn.get("main");
        JSONObject wind = (JSONObject) apiReturn.get("wind");

        JSONArray weather = (JSONArray) apiReturn.get("weather");
        for (Object o : weather) {
            JSONObject obj = (JSONObject) o;
            String description = (String) obj.get("description");

          conditions = apiReturn.get("name") + ", " + sys.get("country") + " = "
                    + "lat: " + coord.get("lat") + ", " + "lon: " + coord.get("lon") + "\n" + "\n" + "\n"
                    + description + "\n" + "\n" + "\n"
                    + "Pressure = " + main.get("pressure") + "\n" + "\n" + "\n"
                    + "Temperature = " + main.get("temp") + "\n" + "\n" + "\n"
                    + "Wind = Speed: " + wind.get("speed") + ", Direction: " + wind.get("deg") + " degrees, Gusts: " + wind.get("gust") + "\n" + "\n" + "\n"
                    + "Humidity = " + main.get("humidity") + "%";
          pressure = String.valueOf(main.get("pressure"));
          temp = String.valueOf(main.get("temp"));
          windSpeed = String.valueOf(wind.get("speed"));
          humidity = String.valueOf(main.get("humidity"));
        }
        return conditions;
    }

    public static String getOutput() throws IOException {
        return conditions;
    }

    public static void apiWriteToHourly() {
        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO hourly (timestamp, pressure, temperature, wind, humidity)" +
                    "VALUES ( " + WDBHourly.getTimeStamp() + ", " + pressure + ", "
                    + temp + ", " + windSpeed + ", " + humidity + ")");
            WDBHourly.observableTimeConversion();
            statement.executeUpdate("UPDATE hourly SET time = ('" + WDBHourly.getTimeFieldValue() + "')" + "WHERE hourly_id_ = (SELECT max (hourly_id_) FROM hourly)");
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void apiLog() {
        if (WDBDaily.compareDates() && WeatherDB.getID("daily_id_","daily", WDBDaily.getDailyEntries) == 7){
            WDBWeekly.writeToWeekly();
            WeatherDB.resetTable("daily");
            WDBDaily.writeToDaily();
            WeatherDB.resetTable("hourly");
        }else if (WDBDaily.compareDates() && WeatherDB.getID("hourly_id_", "hourly", WDBHourly.getHourlyEntries) > 0){
            WDBDaily.writeToDaily();
            WeatherDB.resetTable("hourly");
        }
        apiWriteToHourly();
    }

    public static String getPressure() {
        return pressure;
    }

    public static String getTemp() {
        return temp;
    }

    public static String getWindSpeed() {
        return windSpeed;
    }

    public static String getHumidity() {
        return humidity;
    }
}






