package Chores.DataModel;

import java.util.Date;

public class Chore {
    private static String choreName;
    private String description;

    private String location;
    private String priority;
    private String  dateCreated;
    private String   dateDue;


    public Chore(String choreName, String location, String priority, String description,  String dateCreated, String dateDue) {
        this.choreName = choreName;
        this.location = location;
        this.priority = priority;
        this.description = description;
        this.dateCreated = dateCreated;
        this.dateDue = dateDue;
    }

    public static String getChoreName() {
        return choreName;
    }

    public void setChoreName(String choreName) {
        this.choreName = choreName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateDue() {
        return dateDue;
    }

    public void setDateDue(String dateDue) {
        this.dateDue = dateDue;
    }

    @Override
    public String toString() {
        return "Chore{" +
                "choreName= '" + choreName + '\'' +
                ", location = '" + location + '\'' +
                ", priority= '" + priority + '\'' +
                ", description= '" + description + '\'' +
                ", dateCreated= '" + dateCreated + '\'' +
                ", dateDue= '" + dateDue + '\'' +
                '}';
    }
}
