package Chores;

import Chores.DataModel.Chore;
import Weather.weatherdb.WDBDaily;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

import static javax.print.attribute.Size2DSyntax.MM;


public class NewChoreController {

    public Button newChoreMinButton;
    public Button newChoreCloseButton;
    public Label responsiblePerson;
    public Label parentChore;
    @FXML
    private TextField choreName, subChore, personResponsible;

    @FXML
    private RadioButton insideLocationButton, outsideLocationButton;
    @FXML
    private RadioButton priorityButtonLow, priorityButtonMedium, priorityButtonHigh;


    public TextArea choreDescription;
    @FXML
    private DatePicker dueDate;

    public Button saveChoreButton;




    public void choresMinEnter(MouseEvent mouseEvent) {
        newChoreMinButton.setEffect(new Glow(.25));
    }

    public void choresMinExited(MouseEvent mouseEvent) {
        newChoreMinButton.setEffect(new Glow(.00));
    }

    public void choresMinPressed(MouseEvent mouseEvent) {
        newChoreMinButton.setEffect(new Glow(.80));
    }

    public void choresMinReleased(MouseEvent mouseEvent) {
        newChoreMinButton.setEffect(new Glow(.00));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void newChoreCloseEnter(MouseEvent mouseEvent) {
        newChoreCloseButton.setEffect(new Glow(.25));
    }

    public void newChoreCloseExit(MouseEvent mouseEvent) {
        newChoreCloseButton.setEffect(new Glow(.00));
    }

    public void newChoreClosePressed(MouseEvent mouseEvent) {
        newChoreCloseButton.setEffect(new Glow(.80));
    }

    public void newChoreCloseReleased(MouseEvent mouseEvent) {
        newChoreCloseButton.setEffect(new Glow(.00));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void saveChoreButtonEnter(MouseEvent mouseEvent) {
        saveChoreButton.setEffect(new Glow(.25));
    }

    public void saveChoreButtonExited(MouseEvent mouseEvent) {
        saveChoreButton.setEffect(new Glow(.00));
    }

    public void saveChoreButtonPressed(MouseEvent mouseEvent) {
        saveChoreButton.setEffect(new Glow(.80));
    }

    Chore newChore = new Chore(null, null, null, null, null, null, null, null);

    public void saveChoreButtonReleased(MouseEvent mouseEvent) {
        newChore.setChoreName(choreName.getText());
        if (outsideLocationButton.isSelected()) {
            newChore.setLocation("outside");
        } else if (insideLocationButton.isSelected()) {
            newChore.setLocation("inside");
        }
        if (priorityButtonLow.isSelected()){
            newChore.setPriority("low");
        }else if (priorityButtonMedium.isSelected()) {
            newChore.setPriority("medium");
        }else if (priorityButtonHigh.isSelected()) {
            newChore.setPriority("high");
        }
        newChore.setDescription(choreDescription.getText());
        newChore.setDateCreated(LocalDate.now().toString());
        newChore.setDateDue(String.valueOf(dueDate.getValue()));
        newChore.setResponsiblePerson(personResponsible.getText());
        newChore.setParentChore(subChore.getText());
        System.out.println(newChore.toString());

    }
}
