package Chores.DataModel;

import java.util.Date;

public class Chore {
    private String choreName;
    private String description;
    private String priority;
    private String  dateCreated;
    private String   dateDue;


    public Chore(String choreName, String description, String priority, String dateCreated, String dateDue) {
        this.choreName = choreName;
        this.description = description;
        this.priority = priority;
        this.dateCreated = dateCreated;
        this.dateDue = dateDue;
    }

    public String getChoreName() {
        return choreName;
    }

    public void setChoreName(String choreName) {
        this.choreName = choreName;
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
                ", description= '" + description + '\'' +
                ", priority= '" + priority + '\'' +
                ", dateCreated= '" + dateCreated + '\'' +
                ", dateDue= '" + dateDue + '\'' +
                '}';
    }
}
