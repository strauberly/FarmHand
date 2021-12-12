module com.zeronthirty.farmhandmaven {

    requires java.sql;
    requires json.simple;
    requires javafx.web;
    requires javafx.fxml;


    opens com.zeronthirty.farmhandmaven to javafx.fxml;
    exports com.zeronthirty.farmhandmaven;
    exports com.zeronthirty.farmhandmaven.weather;
    opens com.zeronthirty.farmhandmaven.weather to javafx.fxml;
    exports com.zeronthirty.farmhandmaven.messages;
    opens com.zeronthirty.farmhandmaven.messages to javafx.fxml;
    exports com.zeronthirty.farmhandmaven.chores;
    opens com.zeronthirty.farmhandmaven.chores to javafx.fxml;
    exports com.zeronthirty.farmhandmaven.weather.instruments;
    opens com.zeronthirty.farmhandmaven.weather.instruments to javafx.fxml;
    exports com.zeronthirty.farmhandmaven.weather.observations;
    opens com.zeronthirty.farmhandmaven.weather.observations to javafx.fxml;
    exports com.zeronthirty.farmhandmaven.weather.stations;
    opens com.zeronthirty.farmhandmaven.weather.stations to javafx.fxml;
    exports com.zeronthirty.farmhandmaven.weather.weatherdb;
    opens com.zeronthirty.farmhandmaven.weather.weatherdb to javafx.fxml;
    exports com.zeronthirty.farmhandmaven.weather.webservices;
    opens com.zeronthirty.farmhandmaven.weather.webservices to javafx.fxml;

}