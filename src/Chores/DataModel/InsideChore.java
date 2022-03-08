package Chores.DataModel;

import java.util.Date;

public class InsideChore extends Chore {
    private static final boolean insideChore = true;

    public InsideChore(String choreTitle, String description, String priority, Date dateCreated, Date dateDue) {
        super(choreTitle, description, priority, dateCreated, dateDue, insideChore);
    }
}
