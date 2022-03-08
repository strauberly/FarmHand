package Chores.DataModel;

import java.util.Date;

public class OutsideChore extends Chore{
    private static final boolean insideChore = false;

    public OutsideChore(String choreName, String description, String priority, Date dateCreated, Date dateDue) {
        super(choreName, description, priority, dateCreated, dateDue, insideChore);
    }
}
