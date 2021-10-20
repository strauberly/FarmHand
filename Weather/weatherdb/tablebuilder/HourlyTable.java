package main.Weather.weatherdb.tablebuilder;

public class HourlyTable {
    private static String time;
    private static String pressure;
    private static String temperature;
    private static String wind;
    private static String humidity;

    public HourlyTable(String time, String pressure, String temperature, String wind, String humidity) {
    HourlyTable.time = time;
    HourlyTable.pressure = pressure;
    HourlyTable.temperature = temperature;
    HourlyTable.wind = wind;
    HourlyTable.humidity = humidity;
    }


}
