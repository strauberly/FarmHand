package Weather.observations;

import Weather.weatherdb.WeatherDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

// analyzes data stored in DB and provides insight based on basic weather rules and definitions
// all subject to change
public class Observations {

    // weather reports
    // possibly organize this as a map with switch statements as it grows
    // create map of weather definitions
    // possibly add observations based on data from recorded days and weeks to look for trends
    public static String weatherReport() {
        String weatherReport;
        if (stormyDefinition()) {
            weatherReport = getWeatherReport() +
                    "Better batten down the hatches, and be prepared to respond because a storm is likely." +
                    "\n" + "\n" + "Check on your indoor list for things to do while waiting it out.";
            return weatherReport;
        } else if (hotAndDryDefinition()) {
            weatherReport = getWeatherReport() +
                    "Better not be sleeping in while its hot and dry out. " + "\n" + "\n" +
                    "Get outside chores in before 10-11am, then switch to indoor chores until it cools or evening " + "\n" +
                    "Make sure critters and crops are cool and well hydrated. That goes for you too.";
            return weatherReport;
        } else if (improvingDefinition()) {
            weatherReport = getWeatherReport() +
                    "Good news! Looks like weather is taking a turn for the better. Isn't that something?" + "\n" + "\n" +
                    "Take a look and see what you can get done outside.";
            return weatherReport;
        } else if (deterioratingDefinition()) {
            weatherReport = getWeatherReport() +
                    "Bummer, mAH d00d! Looks like weather is taking a turn for the worse. Shucks." + "\n" + "\n" +
                    "Keep an eye out for it to get worse and see what you need to put away or protect." + "\n" + "\n" +
                    "After all that, see what you can get done inside.";
            return weatherReport;

        } else if (Objects.equals(risingFallingUnSettledSteady("pressure", "hourly"), "unsettled") && Objects.equals(risingFallingUnSettledSteady("temperature", "hourly"), "rising")) {
            weatherReport = getWeatherReport() +
                    "Little weird out there for mAH d00ds but I might just keep an eye on the sky.";
            return weatherReport;
        } else if (Objects.equals(risingFallingUnSettledSteady("pressure", "hourly"), "unsettled") && Objects.equals(risingFallingUnSettledSteady("temperature", "hourly"), "falling")) {
            weatherReport = getWeatherReport() +
                    "Little weird out there for mAH d00ds. Keep an eye on the sky and be flexible but I'd probably take it inside.";
            return weatherReport;
        } else if (Objects.equals(risingFallingUnSettledSteady("pressure", "hourly"), "unsettled") && Objects.equals(risingFallingUnSettledSteady("temperature", "hourly"), "unsettled")) {
            weatherReport = getWeatherReport() +
                    "Little weird out there for mAH d00ds. Cup of tea while you wait a bit?";
            return weatherReport;
        } else if (Objects.equals(risingFallingUnSettledSteady("pressure", "hourly"), "fairly steady")) {
            weatherReport = getWeatherReport() +
                    "Keep on keeping on, mAH d00d. Looks like more of the same for a bit.";
            return weatherReport;
        } else
            return """
                    Whups. Looks like you've been entering nonsense.

                    Try the process again but enter solid consecutive hourly readings.

                    Figure it oot.""";
    }

    private static String getWeatherReport() {
        return returnCurrentConditions() + "\n" + "\n" +
                "But, pressure is " + risingFallingUnSettledSteady("pressure", "hourly") + ", temperature is " +
                risingFallingUnSettledSteady("temperature", "hourly") + ", wind is " +
                risingFallingUnSettledSteady("wind", "hourly") + ", and the humidity is " +
                risingFallingUnSettledSteady("humidity", "hourly") + "." + "\n" + "\n";
    }

    private static String returnCurrentConditions() {
        return "Well, it's " + (tempDefinition()) + (humidityDefinition()) + (windDefinition());
    }

    // definitions
    private static String risingFallingUnSettledSteady(String column, String table) {
        double readingOne;
        double readingTwo;
        double readingThree;
        ArrayList<Double> risingReadings = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            String sql = "SELECT" + "(" + column + ")" + "as readings FROM" + "(" + table + ")" + "ORDER BY hourly_id_ DESC limit 3";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                risingReadings.add(rs.getDouble("readings"));
            }
            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        readingOne = risingReadings.get(2);
        readingTwo = risingReadings.get(1);
        readingThree = risingReadings.get(0);
        if (readingThree > readingTwo && readingTwo > readingOne) {
            return "rising";
        } else if (readingThree < readingTwo && readingTwo < readingOne) {
            return "falling";
        } else if (Math.abs(readingOne - readingThree) <= 9) {
            return "fairly steady";
        } else if ((readingOne > readingTwo && readingTwo < readingThree) || (readingOne < readingTwo && readingTwo > readingThree))
            return "unsettled";
        else
            return null;
    }

    private static String tempDefinition() {
        if (WeatherDB.last3ReadingsAvg("temperature", "hourly") >= 80) {
            return "hot boss, ";
        } else if (WeatherDB.last3ReadingsAvg("temperature", "hourly") <= 79 && (WeatherDB.last3ReadingsAvg("temperature", "hourly") >= 60)) {
            return "nice and warm, ";
        } else if (WeatherDB.last3ReadingsAvg("temperature", "hourly") <= 59 && (WeatherDB.last3ReadingsAvg("temperature", "hourly") >= 40)) {
            return "a bit cool, ";
        } else
            return "cold outside, ";
    }

    private static String humidityDefinition() {
        if (WeatherDB.last3ReadingsAvg("humidity", "hourly") > 50 && (WeatherDB.last3ReadingsAvg("humidity", "hourly") < 75)) {
            return " a bit humid, ";
        } else if (WeatherDB.last3ReadingsAvg("humidity", "hourly") > 75) {
            return "wet, ";
        } else if (WeatherDB.last3ReadingsAvg("humidity", "hourly") < 45) {
            return "dry, ";
        } else
            return "a bit humid, ";
    }

    private static String windDefinition() {
        if (WeatherDB.last3ReadingsAvg("wind", "hourly") > 5 && (WeatherDB.last3ReadingsAvg("wind", "hourly") < 12)) {
            return "and the wind is breezy.";
        } else if (WeatherDB.last3ReadingsAvg("wind", "hourly") > 12 && (WeatherDB.last3ReadingsAvg("wind", "hourly") < 20)) {
            return "and the wind is blustery.";
        } else if (WeatherDB.last3ReadingsAvg("wind", "hourly") > 20 && (WeatherDB.last3ReadingsAvg("wind", "hourly") < 30)) {
            return "and the wind is blowing pretty good.";
        } else if (WeatherDB.last3ReadingsAvg("wind", "hourly") > 30) {
            return "and the wind is howling.";
        } else return "and the wind is calm.";
    }

    private static String pressureSpeedDefinition() {
        if (WeatherDB.last3ReadingsDiff("pressure", "hourly") >= 12) {
            return "quickly";
        } else if (WeatherDB.last3ReadingsDiff("pressure", "hourly") < 6 && (WeatherDB.last3ReadingsDiff("pressure", "hourly") > 0.101)) {
            return "slowly";
        } else
            return "holding steady";
    }

    private static boolean stormyDefinition() {
        return (Objects.equals(risingFallingUnSettledSteady("pressure", "hourly"), "falling")
                && pressureSpeedDefinition().equals("quickly"))
                && Objects.equals(risingFallingUnSettledSteady("wind", "hourly"), "rising") && risingFallingUnSettledSteady("humidity", "hourly").equals("rising")
                && Objects.equals(risingFallingUnSettledSteady("temperature", "hourly"), "falling");
    }

    private static boolean hotAndDryDefinition() {
        return (WeatherDB.last3ReadingsAvg("pressure", "hourly") > 1010) && (WeatherDB.last3ReadingsAvg("temperature", "hourly") > 75) &&
                (humidityDefinition().equals("dry"));
    }

    private static boolean improvingDefinition() {
        return Objects.equals(risingFallingUnSettledSteady("pressure", "hourly"), "rising");
    }

    private static boolean deterioratingDefinition() {
        return Objects.equals(risingFallingUnSettledSteady("pressure", "hourly"), "falling");
    }
}