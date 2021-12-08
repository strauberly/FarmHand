package main.Chores;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ChoresController {


    public Button choresMinButton;
    @FXML
    public Label ChoresHeader;

    public void choresMinEnter(MouseEvent mouseEvent) {
        choresMinButton.setEffect(new Glow(.25));
    }

    public void choresMinExited(MouseEvent mouseEvent) {
        choresMinButton.setEffect(new Glow(.0));
    }

    public void choresMinPressed(MouseEvent mouseEvent) {
        choresMinButton.setEffect(new Glow(.80));

    }

    public void choresMinReleased(MouseEvent mouseEvent) {
        choresMinButton.setEffect(new Glow(.0));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }



    public Button choresCloseButton;
    public void choresCloseEnter(MouseEvent mouseEvent) {
        choresCloseButton.setEffect(new Glow(.25));
    }

    public void choresCloseExit(MouseEvent mouseEvent) {
        choresCloseButton.setEffect(new Glow(.0));
    }

    public void choresClosePressed(MouseEvent mouseEvent) {
        choresMinButton.setEffect(new Glow(.80));
    }

    public void choresCloseReleased(MouseEvent mouseEvent) {
        choresCloseButton.setEffect(new Glow(.0));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
