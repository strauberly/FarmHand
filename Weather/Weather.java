package main.Weather;
import main.Weather.stations.StationsController;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Weather {

    private static String conditions;

    public static String getConditions() throws IOException {
        // takes the information entered as lat. and long. and applies values to api url
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

        // parses the values of created string to build JSON Object and allows to be reparsed for values.
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
                    + "Wind = Speed: " + wind.get("speed") + ", Direction: " + wind.get("deg") + ", Gusts: " + wind.get("gust") + "\n" + "\n" + "\n"
                    + "Humidity = " + main.get("humidity") + "%";
        }
        return conditions;
    }

    public static String getOutput() throws IOException {
        return Weather.getConditions();
    }
}






