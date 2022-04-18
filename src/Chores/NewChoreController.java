package Chores;

import Chores.DataModel.Chore;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class NewChoreController {

    public Button newChoreMinButton;
    public Button newChoreCloseButton;
    public TextField choreName;
    public RadioButton insideLocationButton;
    public RadioButton outsideLocationButton;
    public RadioButton priorityButtonLow;
    public RadioButton priorityButtonMedium;
    public RadioButton priorityButtonHigh;
    public TextArea choreDescription;
    public DatePicker dueDate;
    public TextField subChore;
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

    public void saveChoreButtonReleased(MouseEvent mouseEvent) {
        saveChoreButton.setEffect(new Glow(.00));


    }
}
