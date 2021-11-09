package main.Weather.instruments;
import main.Weather.weatherdb.WeatherDB;
import java.sql.*;
import java.util.ArrayList;

// yesterday's stats
// last week's stats
//determine stormy, calm, unsettled, hot, cold, warm, cold, dry, really dry, damp, really damp, quickly, slowly
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

     public static String weatherReport;
     // try and rewrite validation as if weather condition is and other conditions are not ie if stormy and not hot and dry or dteriorating or improving
    // automate reading from webservice every 30 minutes?
   public static String weatherReport() {
       if (stormyDefinition() && !hotAndDryDefinition() || !deterioratingDefinition() || !improvingDefinition()){
           weatherReport = returnCurrentConditions() + "\n" + "\n"+
                   "But, pressure is " + risingFallingUnSettled("pressure", "hourly") + " " +
                   pressureSpeedDefinition() + ", temperature is " +
                   risingFallingUnSettled("temperature", "hourly") + ", wind is " +
                   risingFallingUnSettled("wind", "hourly") + ", and the humidity is " +
                   risingFallingUnSettled("humidity", "hourly") + "." + "\n" + "\n"+
                   "Better batten down the hatches, and be prepared to respond because a storm is likely." +
                   "\n"+ "\n"+ "Check on your indoor list for things to do while waiting it out.";
           return weatherReport;
       }else if( hotAndDryDefinition() && !stormyDefinition() || !deterioratingDefinition() || !improvingDefinition()){
           weatherReport =  returnCurrentConditions() + "\n" + "\n"+
                   "But, pressure is " + risingFallingUnSettled("pressure", "hourly") + " " +
                   pressureSpeedDefinition() + ", temperature is " +
                   risingFallingUnSettled("temperature", "hourly") + ", wind is " +
                   risingFallingUnSettled("wind", "hourly") + ", and the humidity is " +
                   risingFallingUnSettled("humidity", "hourly") + "." + "\n" + "\n"+
                   "Better not be sleeping in while its hot and dry out. " + "\n" + "\n"+
                   "Get outside chores in before 10-11am, then switch to indoor chores until it cools or evening " +"\n"+
                   "Make sure critters and crops are a cool and well hydrated. That goes for you too.";
            return weatherReport;
       }else if (improvingDefinition() && !stormyDefinition() || !hotAndDryDefinition() || !deterioratingDefinition()){
           weatherReport =  returnCurrentConditions() + "\n" + "\n"+
                   "But, pressure is " + risingFallingUnSettled("pressure", "hourly") +  ", temperature is " +
                   risingFallingUnSettled("temperature", "hourly") + ", wind is " +
                   risingFallingUnSettled("wind", "hourly") + ", and the humidity is " +
                   risingFallingUnSettled("humidity", "hourly") + "." + "\n" + "\n"+
                   "Good news! Looks like weather is taking a turn for the better. Isn't that something?" + "\n" + "\n"+
                   "Take a look and see what you can get done outside.";
            return weatherReport;
       }else if (deterioratingDefinition() && !stormyDefinition() || !hotAndDryDefinition() || !improvingDefinition()) {
           weatherReport = returnCurrentConditions() + "\n" + "\n"+
                   "But, pressure is " + risingFallingUnSettled("pressure", "hourly") + ", temperature is " +
                   risingFallingUnSettled("temperature", "hourly") + ", wind is " +
                   risingFallingUnSettled("wind", "hourly") + ", and the humidity is " +
                   risingFallingUnSettled("humidity", "hourly") + "." + "\n" + "\n"+
                   "Bummer, mAH d00d! Looks like weather is taking a turn for the worse. Shucks." + "\n" + "\n"+
                   "Keep an eye out for it to get worse and see what you need to put away or protect." + "\n" + "\n"+
                   "After all that see what you can get done inside";
           return weatherReport;
       }else
           return """
                   Whups. Looks like you've been entering nonsense.

                   Try the process again but enter solid consecutive hourly readings. 

                   Figure it oot.""";



       //       if (stormyDefinition()) {
//           weatherReport = returnCurrentConditions() + "\n" + "\n"+
//                   "But, pressure is " + risingFallingUnSettled("pressure", "hourly") + " " +
//                   pressureSpeedDefinition() + ", temperature is " +
//                   risingFallingUnSettled("temperature", "hourly") + ", wind is " +
//                   risingFallingUnSettled("wind", "hourly") + ", and the humidity is " +
//                   risingFallingUnSettled("humidity", "hourly") + "." + "\n" + "\n"+
//                   "Better batten down the hatches, and be prepared to respond because a storm is on the way." +
//                   "\n"+ "\n"+ "Check on your indoor list for things to do while waiting it out.";
//           return weatherReport;
//       }else if(hotAndDryDefinition()){
//           weatherReport =  returnCurrentConditions() + "\n" + "\n"+
//                   "But, pressure is " + risingFallingUnSettled("pressure", "hourly") + " " +
//                   pressureSpeedDefinition() + ", temperature is " +
//                   risingFallingUnSettled("temperature", "hourly") + ", wind is " +
//                   risingFallingUnSettled("wind", "hourly") + ", and the humidity is " +
//                   risingFallingUnSettled("humidity", "hourly") + "." + "\n" + "\n"+
//                   "Better not be sleeping in while its hot and dry out. " + "\n" + "\n"+
//                   "Get outside chores in before 10-11am, then switch to indoor chores until it cools or evening " +"\n"+
//                   "Make sure critters and crops are a cool and well hydrated. That goes for you too.";
//            return weatherReport;
//       }else if (improvingDefinition()){
//           weatherReport =  returnCurrentConditions() + "\n" + "\n"+
//                   "But, pressure is " + risingFallingUnSettled("pressure", "hourly") + " " +
//                   pressureSpeedDefinition() + ", temperature is " +
//                   risingFallingUnSettled("temperature", "hourly") + ", wind is " +
//                   risingFallingUnSettled("wind", "hourly") + ", and the humidity is " +
//                   risingFallingUnSettled("humidity", "hourly") + "." + "\n" + "\n"+
//                   "Good news! Looks like weather is taking a turn for the better. Isn't that something?" + "\n" + "\n"+
//                   "Take a look and see what you can get done outside.";
//            return weatherReport;
//       }else if (deterioratingDefinition()) {
//           weatherReport = returnCurrentConditions() + "\n" + "\n"+
//                   "But, pressure is " + risingFallingUnSettled("pressure", "hourly") + " " +
//                   pressureSpeedDefinition() + ", temperature is " +
//                   risingFallingUnSettled("temperature", "hourly") + ", wind is " +
//                   risingFallingUnSettled("wind", "hourly") + ", and the humidity is " +
//                   risingFallingUnSettled("humidity", "hourly") + "." + "\n" + "\n"+
//                   "Bummer, mAH d00d! Looks like weather is taking a turn for the worse. Shucks." + "\n" + "\n"+
//                   "Keep an eye out for it to get worse and see what you need to put away or protect." + "\n" + "\n"+
//                   "After all that see what you can get done inside";
//           return weatherReport;
//       }else
//           return """
//                   Whups. Looks like you've been entering nonsense.
//
//                   Try the process again but enter solid consecutive hourly readings.
//
//                   Figure it oot.""";
   }

    public static String getWeatherReport() {
        return weatherReport();
    }

    //        }else if (getBetterDefinition() != null){
//            return "Well it's been " + tempDefinition() + " " + humidityDefinition() + " " + windDefinition() +
//                    "\n" + "But it's looking " + getBetterDefinition();
//
////        }else if ((hotAndDryDefinition() == null) && (stormyDefinition() == null) && (getWorseDefinition()==null)) {
////            return "Well it's been " + tempDefinition() + " " + humidityDefinition() + " " + windDefinition() +
////                    "\n" + "And its looking " + getBetterDefinition();
//
//        }else
////            (getWorseDefinition() != null){
//            return "Well it's been " + tempDefinition() + " " + humidityDefinition() + " " + windDefinition() +
//                    "\n" + "But it's looking " + getWorseDefinition();
////        }else
////            return "Not enough data, MaH d00d";
//    }

//    this is nine shades of fucked research getting values from column to array list
    public static String risingFallingUnSettled(String column, String table) {
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
        if (readingTwo > readingOne && readingThree > readingTwo){
            return "rising";
        }else if (readingThree < readingTwo && readingTwo < readingOne){
            return "falling";
        }else
            return "unsettled";
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
            String sql = "SELECT" + "(" + column + ")" + "as readings FROM" + "(" + table + ")" + "ORDER BY hourly_id_ DESC limit 3";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                readings.add(rs.getDouble("readings"));
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
        if (last3ReadingsAvg("temperature", "hourly") >= 80) {
            return "hot boss, ";
        } else if (last3ReadingsAvg("temperature", "hourly") <= 79 && (last3ReadingsAvg("temperature", "hourly") >= 60)) {
            return "nice and warm, ";
        } else if (last3ReadingsAvg("temperature", "hourly") <= 59 && (last3ReadingsAvg("temperature", "hourly") >= 40)) {
            return "a bit cool, ";
        } else
            return "I said baby it's cold outside, ";
    }

    public static String humidityDefinition() {
        if (last3ReadingsAvg("humidity", "hourly") > 50 && (last3ReadingsAvg("humidity", "hourly") < 75)) {
            return "damp, ";
        } else if (last3ReadingsAvg("humidity", "hourly") > 75) {
            return "swampy, ";
        } else if (last3ReadingsAvg("humidity", "hourly") < 35) {
            return "dry, ";
        } else
            return "a bit humid, ";
    }

    public static String windDefinition() {
        if (last3ReadingsAvg("wind", "hourly") > 5 && (last3ReadingsAvg("wind", "hourly") < 12)) {
            return "and the wind is breezy.";
        } else if (last3ReadingsAvg("wind", "hourly") > 12 && (last3ReadingsAvg("wind", "hourly") < 20)) {
            return "and the wind is blustery.";
        } else if (last3ReadingsAvg("wind", "hourly") > 20 && (last3ReadingsAvg("wind", "hourly") < 30)) {
            return "and the wind is blowing pretty good.";
        } else if (last3ReadingsAvg("wind", "hourly") > 30) {
            return "and the wind is howling.";
        } else return "and the wind is calm.";
   }

    private static String pressureSpeedDefinition() {
        if (last3ReadingsDiff("pressure", "hourly") > 5) {
            return "quickly";
        } else if (last3ReadingsDiff("pressure", "hourly") < 5 && (last3ReadingsDiff("pressure", "hourly") > 0.101)) {
            return "slowly";
        } else
            return "holding steady";
    }
// make these boolean so they can be evaluated and then make a return in accordance

    public static boolean stormyDefinition() {
        return risingFallingUnSettled("pressure", "hourly").equals("falling")
                && pressureSpeedDefinition().equals("quickly")
                && risingFallingUnSettled("wind", "hourly").equals("rising") && risingFallingUnSettled("humidity", "hourly").equals("rising")
                && risingFallingUnSettled("temperature", "hourly").equals("falling");
    }

    public static boolean hotAndDryDefinition(){
        return  ((last3ReadingsAvg("pressure", "hourly") > 1010) && ((last3ReadingsAvg("temperature", "hourly") > 75)
                || (risingFallingUnSettled("temperature", "hourly").equals("rising")) &&
                (humidityDefinition().equals("dry") || risingFallingUnSettled("humidity", "hourly").equals("falling"))));
    }


    public static boolean improvingDefinition() {
        return risingFallingUnSettled("pressure", "hourly").equals("rising") && pressureSpeedDefinition().equals("slowly")
                && risingFallingUnSettled("wind", "hourly").equals("falling") && risingFallingUnSettled("humidity", "hourly").equals("falling")
                && risingFallingUnSettled("temperature", "hourly").equals("rising");
    }

    public static boolean deterioratingDefinition(){
        return risingFallingUnSettled("pressure", "hourly").equals("falling") && pressureSpeedDefinition().equals("slowly")
                && risingFallingUnSettled("wind", "hourly").equals("rising") && risingFallingUnSettled("humidity", "hourly").equals("rising")
                &&risingFallingUnSettled("temperature", "hourly").equals("rising");
    }

    //        if ((last3ReadingsAvg("pressure", "hourly") > 1010) && (last3ReadingsAvg("temperature", "hourly") > 80)
//                && risingFallingUnSettled("temperature", "hourly").equals("rising") && humidityDefinition().equals("dry") ){
//            return "looks a bit warm and dry out there. Keep the critters and crops cool and watered. Drink lots of water and consider indoor chores.";
//        }else return null;
//    }

    public static String returnCurrentConditions(){
        return "Well, it's " + tempDefinition() + humidityDefinition() + windDefinition();
    }
}