package com.zeronthirty.farmhandmaven.weather.stations;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.zeronthirty.farmhandmaven.weather.weatherdb.WDBDaily;
import com.zeronthirty.farmhandmaven.weather.weatherdb.WDBHourly;
import com.zeronthirty.farmhandmaven.weather.weatherdb.WDBWeekly;
import com.zeronthirty.farmhandmaven.weather.weatherdb.WeatherDB;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Stations {

        private static String conditions;
        private static String pressure;
        private static String temp;
        private static String windSpeed;
        private static String humidity;

        public static String getConditions() throws IOException {
            // takes the information entered as lat. and long. by user and applies values to api url
            String urlLat = StationsController.getLat();
            String urlLongi = StationsController.getLongi();
            String apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + urlLat.trim() + "&lon=" + urlLongi.trim() + "&units=imperial&appid=ba12fc74c50358f79d2f837033e212d7";

            // /creates variable "apiString" then opens connection to api url and makes get request
            // scanner then receives response and appends the string and closes scanner
            StringBuilder apiString = new StringBuilder();
            try {
                URL url = new URL(apiUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {

                    apiString.append(scanner.nextLine());
                }
                scanner.close();
            }catch (UnknownHostException e){
                System.out.println("no internet mah dood");
            }

            // parses the values of created string to build JSON Object and allows to be reparsed for values to be displayed
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

        private static void apiWriteToHourly() {
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

        public static void apiLog() throws SQLException {
            if (WDBDaily.notSameDates() && WeatherDB.getID("daily_id_","daily", WDBDaily.getDailyEntries) == 7){
                WDBWeekly.writeToWeekly();
                WeatherDB.resetTable("daily");
                WDBDaily.writeToDaily();
                WeatherDB.resetTable("hourly");
            }else if (WDBDaily.notSameDates() && WeatherDB.getID("hourly_id_", "hourly", WDBHourly.getHourlyEntries) > 0){
                WDBDaily.writeToDaily();
                WeatherDB.resetTable("hourly");
            }
            apiWriteToHourly();
        }
    }
