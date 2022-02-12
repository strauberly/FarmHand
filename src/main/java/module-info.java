module com.zeronthirty.farmhandmaven {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires json.simple;
    requires javafx.web;


    opens com.zeronthirty.farmhandmaven to javafx.fxml;
    exports com.zeronthirty.farmhandmaven;
    exports com.zeronthirty.farmhandmaven.weather;
    opens com.zeronthirty.farmhandmaven.weather to javafx.fxml;
    exports com.zeronthirty.farmhandmaven.weather.instruments;
    opens com.zeronthirty.farmhandmaven.weather.instruments to javafx.fxml;
    exports com.zeronthirty.farmhandmaven.weather.stations;
    opens com.zeronthirty.farmhandmaven.weather.stations to javafx.fxml;
    exports com.zeronthirty.farmhandmaven.weather.webservices;
    opens com.zeronthirty.farmhandmaven.weather.webservices to javafx.fxml;
    exports com.zeronthirty.farmhandmaven.weather.weatherdb;
    opens com.zeronthirty.farmhandmaven.weather.weatherdb to javafx.fxml;
}