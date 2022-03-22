package Chores.DataModel;

import java.util.Date;

public class Chore {
    private String choreName;
    private String description;
    private String priority;
    private Date   dateCreated;
    private Date   dateDue;
    private boolean insideChore;

    public Chore(String choreName, String description, String priority, Date dateCreated, Date dateDue, boolean insideChore) {
        this.choreName = choreName;
        this.description = description;
        this.priority = priority;
        this.dateCreated = dateCreated;
        this.dateDue = dateDue;
        this.insideChore = insideChore;
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    public boolean isInsideChore() {
        return insideChore;
    }

    public void setInsideChore(boolean insideChore) {
        this.insideChore = insideChore;
    }
}
