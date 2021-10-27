package main.Weather.observations;

import main.Weather.weatherdb.WeatherDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

// yesterday's stats
// last week's stats
//determine stormy, calm, unsettled, hot, cold, warm, cold, dry, really dry, damp, really damp rising, falling, quickly, slowly
//determine predictions as well by comparing conditions to the current time
//falling quickly = storm moving in
// rising quickly = storm blowing out
//stormy = Baro < 970 + falling quickly + humidity rising + temp falling + wind increasing
// unsettled = Baro > 990 && Baro < 1010 + rising or falling + check wind = improving or getting worse
// calm = Baro > 1010 + low wind + warm
//6.1 per 3 hour, Baro change = quickly changing || 0.101 to 1.35 = slow change || < .101 = holding steady
// determine which chores are best suited to what conditions
// get yesterdays info and equate to hotter, colder, stormier, calmer, drier, wetter
// same for weekly
//if storming coming or dying be in position
public class Observations {
    // automate reading from webservice every 30 minutes?
    public static String weatherReport(){
        if ((stormyDefinition() == null) && (getBetterDefinition() == null) && (getWorseDefinition()==null)) {
            return "Well it's been " + tempDefinition() + " " + humidityDefinition() + " " + windDefinition() +
                    "\n" + "And its looking" + hotAndDryDefinition();

        }else if ((hotAndDryDefinition() == null) && (getBetterDefinition() == null) && (getWorseDefinition()==null)) {
            return "Well it's been " + tempDefinition() + " " + humidityDefinition() + " " + windDefinition() +
                    "\n" + "And its looking" + stormyDefinition();

        }else if ((hotAndDryDefinition() == null) && (stormyDefinition() == null) && (getWorseDefinition()==null)) {
            return "Well it's been " + tempDefinition() + " " + humidityDefinition() + " " + windDefinition() +
                    "\n" + "And its looking" + getBetterDefinition();

        }else if ((hotAndDryDefinition() == null) && (stormyDefinition() == null) && (getBetterDefinition()==null)) {
            return "Well it's been " + tempDefinition() + " " + humidityDefinition() + " " + windDefinition() +
                    "\n" + "And its looking" + getWorseDefinition();
        }else
            return "Not enough data, MaH d00d";
    }
    // update this for possible reuse if appropriate
    public static String risingFallingOrSteady(String column, String table){

        double readingOne;
        double readingTwo;
        double readingThree;
        ArrayList<Double> readings = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            String sql = "SELECT '" + column + "'FROM '" + table + "' ORDER BY hourly_id_ DESC limit 3";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                readings.add(rs.getDouble(column));
            }
            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        readingOne = readings.get(0);
        readingTwo = readings.get(1);
        readingThree = readings.get(2);
        if (readingTwo > readingOne && readingThree > readingTwo){
            return ("rising");
        }else if (readingThree < readingTwo && readingTwo < readingOne){
            return "falling";
        }else
            return "fairly steady";
    }
    public static double last3ReadingsAvg(String column, String table) {
        double average = 0;
        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            String sql2 = "SELECT AVG" + "(" + column + ")" + "as avg FROM" +
                    "(SELECT" + "(" + column + ")" + "FROM" + "(" + table + ")" + "ORDER BY hourly_id_ DESC limit 3)";

            ResultSet rs2 = statement.executeQuery(sql2);
            average = rs2.getDouble("avg");
            rs2.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return average;
    }


    public static double last3ReadingsDiff(String column, String table) {
        double threeHourDiff;
        ArrayList<Double> readings = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(WeatherDB.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            String sql = "SELECT" + "(" + column + ")" + "FROM" + "(" + table + ")" + "ORDER BY hourly_id_ DESC limit 3";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                readings.add(rs.getDouble("pressure"));
            }
            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        threeHourDiff = Math.abs(readings.get(0) - readings.get(2));
        return threeHourDiff;
    }

    public static String tempDefinition() {
        if (last3ReadingsAvg("temperature", "hourly") > 80) {
            return "It's hot, boss.";
        } else if (last3ReadingsAvg("temperature", "hourly") < 80 && (last3ReadingsAvg("temperature", "hourly") > 60)) {
            return "Nice and warm.";
        } else if (last3ReadingsAvg("temperature", "hourly") < 60 && (last3ReadingsAvg("temperature", "hourly") > 40)) {
            return "Bit cool.";
        } else
            return "But baby I said it's cold outside.";
    }

    public static String humidityDefinition() {
        if (last3ReadingsAvg("humidity", "hourly") > 50 && (last3ReadingsAvg("humidity", "hourly") < 75)) {
            return "damp";
        } else if (last3ReadingsAvg("humidity", "hourly") > 75) {
            return "rain";
        } else if (last3ReadingsAvg("humidity", "hourly") < 30) {
            return "dry";
        } else
            return "humid";
    }


    public static String windDefinition() {
        if (last3ReadingsAvg("wind", "hourly") > 5 && (last3ReadingsAvg("wind", "hourly") < 12)) {
            return "breezy";
        } else if (last3ReadingsAvg("wind", "hourly") > 12 && (last3ReadingsAvg("wind", "hourly") < 20)) {
            return "blustery";
        } else if (last3ReadingsAvg("wind", "hourly") > 20 && (last3ReadingsAvg("wind", "hourly") < 30)) {
            return "really wind";
        } else if (last3ReadingsAvg("wind", "hourly") > 30) {
            return "howling";
        } else
            return "calm";
    }


    private static String pressureSpeedDefinition() {
        if (last3ReadingsDiff("pressure", "hourly") > 5) {
            return "quickly";
        } else if (last3ReadingsDiff("pressure", "hourly") < 5 && (last3ReadingsDiff("pressure", "hourly") > 0.101)) {
            return "slowly";
        } else
            return "holding steady";
    }

    public static String stormyDefinition() {
        if (risingFallingOrSteady("pressure", "hourly").equals("falling") && pressureSpeedDefinition().equals("quickly")
                && risingFallingOrSteady("wind", "hourly").equals("rising") && risingFallingOrSteady("humidity", "hourly").equals("rising")
                && risingFallingOrSteady("temperature", "hourly").equals("falling")) {
            return "Looks like a storm is brewing. Batten down the hatches, put the things you care about away, check water storage, hunker down and be ready to respond.";
        } else
            return null;
    }

    public static String getBetterDefinition(){
            if (risingFallingOrSteady("pressure", "hourly").equals("rising") && pressureSpeedDefinition().equals("slowly")
                    && risingFallingOrSteady("wind", "hourly").equals("falling") && risingFallingOrSteady("humidity", "hourly").equals("falling")
                    &&risingFallingOrSteady("temperature", "hourly").equals("rising")){
                return "Looks like conditions are improving. See what you can get done outside";
            }else
                return null;
    }

    public static String getWorseDefinition(){
        if (risingFallingOrSteady("pressure", "hourly").equals("falling") && pressureSpeedDefinition().equals("slowly")
                && risingFallingOrSteady("wind", "hourly").equals("rising") && risingFallingOrSteady("humidity", "hourly").equals("rising")
                &&risingFallingOrSteady("temperature", "hourly").equals("rising")){
            return "Looks like conditions are deteriorating. See what you can get done inside.";
        }else
            return null;
    }

    public static String hotAndDryDefinition(){
        if ((last3ReadingsAvg("pressure", "hourly") > 1010) && (last3ReadingsAvg("temperature", "hourly") > 80)
                && risingFallingOrSteady("temperature", "hourly").equals("rising") && humidityDefinition().equals("dry") ){
            return "Getting a bit warm and dry out there. Keep the critters and crops, cool and watered.  ";
        }else
            return null;
    }
}