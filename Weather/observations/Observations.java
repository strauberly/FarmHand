package main.Weather.observations;
import main.Weather.stations.Stations;
import main.Weather.weatherdb.WeatherDB;
import java.sql.*;
import java.util.ArrayList;

// yesterday's stats
// last week's stats
//A slow-moving system would have a change in barometric pressure of about 0.02 to 0.03 (.67-1.01) inches per hour,
// whereas a fast-moving system would have a change of about 0.05 to 0.06 ( 2mb)inches per hour.
//determine stormy, calm, unsettled, hot, cold, warm, cold, dry, really dry, damp, really damp, quickly, slowly
//determine predictions as well by comparing conditions to the current time
//falling quickly = storm moving in
// rising quickly = storm blowing out
//stormy = Baro < 980 + falling quickly + humidity rising + temp falling + wind increasing
// unsettled = Baro > 990 && Baro < 1010 + rising or falling + check wind = improving or getting worse
// calm = Baro > 1010 + low wind + warm
//6.1 per 3 hour, Baro change = quickly changing || 0.101 to 1.35 = slow change || < .101 = holding steady
// determine which chores are best suited to what conditions
// get yesterdays info and equate to hotter, colder, stormier, calmer, drier, wetter
// same for weekly
//if storming coming or dying be in position

//LOWER THAN Barometric Pressure Lower than 29.80 inHg (1009 mbar)
//
//If the pressure is steady or rising, expect the weather to clear with cooler temperatures. If it's dropping slowly, rain is on the way. If the pressure drops quickly, expect a storm.

//Barometric Pressure of 29.80 to 30.20 inHg (1009 to 1022 mbar)
//
//        If the pressure is steady or rising, expect the current conditions to stay the same. If the pressure drops slowly, there will be minimal change. If the pressure drops quickly, expect rain or snow.
//
//Barometric Pressure Higher Than 30.20 inHg (1022mBar mbar)
//
//If the pressure is steady or rising, you can expect fair weather to continue. If it's slow dropping, fair weather's on its way. If it drops quickly, expect cloudy conditions and warmer temperatures.

//The FALL of the barometer (decreasing pressure)
//
//        In very hot weather, the fall of the barometer denotes thunder. Otherwise, the sudden falling of the barometer denotes high wind.
//        In frosty weather, the fall of the barometer denotes thaw.
//        If wet weather happens soon after the fall of the barometer, expect but little of it.
//        In wet weather if the barometer falls expect much wet.
//        In fair weather, if the barometer falls much and remains low, expect much wet in a few days, and probably wind.
//        The barometer sinks lowest of all for wind and rain together; next to that wind, (except it be an east or north-east wind).
//        The RISE of the barometer (increasing pressure)
//
//        In winter, the rise of the barometer presages frost.
//        In frosty weather, the rise of the barometer presages snow.
//        If fair weather happens soon after the rise of the barometer, expect but little of it.
//        In wet weather, if the mercury rises high and remains so, expect continued fine weather in a day or two.
//        In wet weather, if the mercury rises suddenly very high, fine weather will not last long.
//        The barometer rises highest of all for north and east winds; for all other winds it sinks.
//        The barometer UNSETTLED (unsteady pressure)
//
//        If the motion of the mercury be unsettled, expect unsettled weather.
//        If it stands at "MUCH RAIN" and rises to "CHANGEABLE" expect fair weather of short continuance.
//        If it stands at "FAIR" and falls to "CHANGEABLE", expect foul weather.
//        Its motion upwards, indicates the approach of fine weather; its motion downwards, indicates the approach of foul weather.
//        If barometer unsettled probably partly cloudy
//        If ever dry check plants
//        Add yesterday was cooler warmer dryer wetter with more or less wind
//        Last week was warm cool hot cold dry wet humid


// possibly organize this as a map with switch statements
public class Observations {

    public static String weatherReport;

    // try and rewrite validation as if weather condition is and other conditions are not ie if stormy and not hot and dry or deteriorating or improving
    // rising falling and unsettled needs to be revisited.
    // automate reading from webservice every 30 minutes?
    public static String weatherReport() {
//       if (stormyDefinition() && !hotAndDryDefinition() || !deterioratingDefinition() || !improvingDefinition()){
        if (stormyDefinition()) {
            weatherReport = returnCurrentConditions() + "\n" + "\n" +
                    "But, pressure is " + risingFallingUnSettled("pressure", "hourly") + " " +
                    pressureSpeedDefinition() + ", temperature is " +
                    risingFallingUnSettled("temperature", "hourly") + ", wind is " +
                    risingFallingUnSettled("wind", "hourly") + ", and the humidity is " +
                    risingFallingUnSettled("humidity", "hourly") + "." + "\n" + "\n" +
                    "Better batten down the hatches, and be prepared to respond because a storm is likely." +
                    "\n" + "\n" + "Check on your indoor list for things to do while waiting it out.";
            return weatherReport;
//       }else if( hotAndDryDefinition() && !stormyDefinition() || !deterioratingDefinition() || !improvingDefinition()){
        } else if (hotAndDryDefinition()) {
            weatherReport = returnCurrentConditions() + "\n" + "\n" +
                    "But, pressure is " + risingFallingUnSettled("pressure", "hourly") + " " +
                    ", temperature is " +
                    risingFallingUnSettled("temperature", "hourly") + ", wind is " +
                    risingFallingUnSettled("wind", "hourly") + ", and the humidity is " +
                    risingFallingUnSettled("humidity", "hourly") + "." + "\n" + "\n" +
                    "Better not be sleeping in while its hot and dry out. " + "\n" + "\n" +
                    "Get outside chores in before 10-11am, then switch to indoor chores until it cools or evening " + "\n" +
                    "Make sure critters and crops are cool and well hydrated. That goes for you too.";
            return weatherReport;
//       }else if (improvingDefinition() && !stormyDefinition() || !hotAndDryDefinition() || !deterioratingDefinition()){
        } else if (improvingDefinition()) {
            weatherReport = returnCurrentConditions() + "\n" + "\n" +
                    "But, pressure is " +  risingFallingUnSettled("pressure", "hourly") + ", temperature is " +
                    risingFallingUnSettled("temperature", "hourly") + ", wind is " +
                    risingFallingUnSettled("wind", "hourly") + ", and the humidity is " +
                    risingFallingUnSettled("humidity", "hourly") + "." + "\n" + "\n" +
                    "Good news! Looks like weather is taking a turn for the better. Isn't that something?" + "\n" + "\n" +
                    "Take a look and see what you can get done outside.";
            return weatherReport;
//       }else if (deterioratingDefinition() && !stormyDefinition() || !hotAndDryDefinition() || !improvingDefinition()) {

        } else if (deterioratingDefinition()) {
            weatherReport = returnCurrentConditions() + "\n" + "\n" +
                    "But, pressure is " + risingFallingUnSettled("pressure", "hourly") + ", temperature is " +
                    risingFallingUnSettled("temperature", "hourly") + ", wind is " +
                    risingFallingUnSettled("wind", "hourly") + ", and the humidity is " +
                    risingFallingUnSettled("humidity", "hourly") + "." + "\n" + "\n" +
                    "Bummer, mAH d00d! Looks like weather is taking a turn for the worse. Shucks." + "\n" + "\n" +
                    "Keep an eye out for it to get worse and see what you need to put away or protect." + "\n" + "\n" +
                    "After all that, see what you can get done inside";
            return weatherReport;
        } else if (risingFallingUnSettled("pressure", "hourly").equals("unsettled")&& risingFallingUnSettled("temperature", "hourly").equals("falling")){
            weatherReport = returnCurrentConditions() + "\n" + "\n" +
                    "But, pressure is " + risingFallingUnSettled("pressure", "hourly") + ", temperature is " +
                    risingFallingUnSettled("temperature", "hourly") + ", wind is " +
                    risingFallingUnSettled("wind", "hourly") + ", and the humidity is " +
                    risingFallingUnSettled("humidity", "hourly") + "." + "\n" + "\n" +
                    "Little weird out there for mAH d00ds. Keep an eye on the sky and be flexible but I'd probably take it inside.";
        return weatherReport;
        } else if (risingFallingUnSettled("pressure", "hourly").equals("unsettled")&& risingFallingUnSettled("temperature", "hourly").equals("rising")){
            weatherReport = returnCurrentConditions() + "\n" + "\n" +
                    "But, pressure is " + risingFallingUnSettled("pressure", "hourly") + ", temperature is " +
                    risingFallingUnSettled("temperature", "hourly") + ", wind is " +
                    risingFallingUnSettled("wind", "hourly") + ", and the humidity is " +
                    risingFallingUnSettled("humidity", "hourly") + "." + "\n" + "\n" +
                    "Little weird out there for mAH d00ds. Keep an eye on the sky and be flexible but I might head outdoors and keep an eye on wind.";
            return weatherReport;
        } else if (risingFallingUnSettled("pressure", "hourly").equals("unsettled")&& risingFallingUnSettled("temperature", "hourly").equals("unsettled")){
            weatherReport = returnCurrentConditions() + "\n" + "\n" +
                    "But, pressure is " + risingFallingUnSettled("pressure", "hourly") + ", temperature is " +
                    risingFallingUnSettled("temperature", "hourly") + ", wind is " +
                    risingFallingUnSettled("wind", "hourly") + ", and the humidity is " +
                    risingFallingUnSettled("humidity", "hourly") + "." + "\n" + "\n" +
                    "Little weird out there for mAH d00ds. Cup of tea while you wait a bit?";
            return weatherReport;
    }else

            return """
                   Whups. Looks like you've been entering nonsense.

                   Try the process again but enter solid consecutive hourly readings. 

                   Figure it oot.""";
}




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

// rewrite to get last three readings and set values then run calculations seperately
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
            return "cold outside, ";
    }

    public static String humidityDefinition() {
        if (last3ReadingsAvg("humidity", "hourly") > 50 && (last3ReadingsAvg("humidity", "hourly") < 75)) {
            return "swampy, ";
        } else if (last3ReadingsAvg("humidity", "hourly") > 75) {
            return "damp, ";
        } else if (last3ReadingsAvg("humidity", "hourly") < 45) {
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
        if (last3ReadingsDiff("pressure", "hourly") >= 12) {
            return "quickly";
        } else if (last3ReadingsDiff("pressure", "hourly") < 6 && (last3ReadingsDiff("pressure", "hourly") > 0.101)) {
            return "slowly";
        } else
            return "holding steady";
    }
// make these boolean so they can be evaluated and then make a return in accordance

    public static boolean stormyDefinition() {
        return (risingFallingUnSettled("pressure", "hourly").equals("falling")
                && pressureSpeedDefinition().equals("quickly"))
                && risingFallingUnSettled("wind", "hourly").equals("rising") && risingFallingUnSettled("humidity", "hourly").equals("rising")
                && risingFallingUnSettled("temperature", "hourly").equals("falling");
    }

    public static boolean hotAndDryDefinition(){
        return  (last3ReadingsAvg("pressure", "hourly") > 1010) && (last3ReadingsAvg("temperature", "hourly") > 75) &&
                (humidityDefinition().equals("dry"));
    }


    public static boolean improvingDefinition() {
        return risingFallingUnSettled("pressure", "hourly").equals("rising");
    }

    public static boolean deterioratingDefinition(){
        return risingFallingUnSettled("pressure", "hourly").equals("falling")
                || risingFallingUnSettled("wind", "hourly").equals("rising") ||
                risingFallingUnSettled("temperature", "hourly").equals("falling");
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