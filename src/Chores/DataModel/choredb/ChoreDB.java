package Chores.DataModel.choredb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class ChoreDB {

    private static final String DB_NAME = "chores.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:farmhand/src/Chores/DataModel/choredb/" + DB_NAME;


    //creation of weather database
    public static void createChoreDB() {
        try {
            Connection conn = DriverManager.getConnection(CONNECTION_STRING);
            Statement statement = conn.createStatement();

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS chores " +
                    " (chore_id_ INTEGER PRIMARY KEY AUTOINCREMENT, chore_name STRING, location String," +
                    " description STRING, priority STRING, date_created STRING," +
                    " date_due STRING, responsible_person String, parent_chore String)");
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
