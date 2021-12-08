package main.Messages;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


    public class MessagesController {
        @FXML
        public Button messagesMinButton;
        @FXML
        public Button messagesCloseButton;
       @FXML
        public Label messagesHeader;


        public void messagesMinEnter(MouseEvent mouseEvent) {
            messagesMinButton.setEffect(new Glow(.25));
        }

        public void messagesMinExit(MouseEvent mouseEvent) {
            messagesMinButton.setEffect(new Glow(.0));
        }

        public void messagesMinPressed(MouseEvent mouseEvent) {
            messagesMinButton.setEffect(new Glow(.80));

        }

        public void messagesMinReleased(MouseEvent mouseEvent) {
            messagesMinButton.setEffect(new Glow(.0));
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setIconified(true);
        }




        public void messagesCloseEnter(MouseEvent mouseEvent) {
            messagesCloseButton.setEffect(new Glow(.25));
        }

        public void messagesCloseExit(MouseEvent mouseEvent) {
            messagesCloseButton.setEffect(new Glow(.0));
        }

        public void messagesClosePressed(MouseEvent mouseEvent) {
            messagesCloseButton.setEffect(new Glow(.80));
        }

        public void messagesCloseReleased(MouseEvent mouseEvent) {
            messagesCloseButton.setEffect(new Glow(.0));
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.close();
        }
    }
