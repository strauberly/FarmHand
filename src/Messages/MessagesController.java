package Messages;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MessagesController implements Initializable {
    public Rectangle messagesBanner;
    @FXML
    private Rectangle messagesSquare;
    @FXML
    private Button messagingMinButton;
    @FXML
    private Label messagesNotes;

    private static boolean maxed = false;
    private static final Rectangle2D displaySize = Screen.getPrimary().getBounds();


    public void messagingMinEnter(MouseEvent mouseEvent) {
        messagingMinButton.setEffect(new Glow(.25));
    }

    public void messagingMinExit(MouseEvent mouseEvent) {
        messagingMinButton.setEffect(new Glow(.0));
    }

    public void messagingMinPressed(MouseEvent mouseEvent) {
        messagingMinButton.setEffect(new Glow(.80));

    }

    public void messagingMinReleased(MouseEvent mouseEvent) {
        messagingMinButton.setEffect(new Glow(.0));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }


    public Button messagingCloseButton;

    public void messagingCloseEnter(MouseEvent mouseEvent) {
        messagingCloseButton.setEffect(new Glow(.25));
    }

    public void messagingCloseExit(MouseEvent mouseEvent) {
        messagingCloseButton.setEffect(new Glow(.0));
    }

    public void messagingClosePressed(MouseEvent mouseEvent) {
        messagingCloseButton.setEffect(new Glow(.80));
    }

    public void messagingCloseReleased(MouseEvent mouseEvent) {
        messagingCloseButton.setEffect(new Glow(.0));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messagesNotes.setText("""
                                                                            Behold!
                                                   This Section Is Under Development.
                                     
                Imagine this... a place... for mAH d00ds... to stop, collaborate, and listen!
                                
                Possibly forum style, possibly messenger style? 
                               
                How does that sound to mAH d00d?
                               
                Far out... Will get back to you on that.

                 """);

        if (maxed) {
            messagesBanner.setWidth(displaySize.getWidth());
            maxed = true;

            messagesNotes.setFont(Font.font(28));
            messagesNotes.setPrefHeight(900);
            messagesNotes.setPrefWidth(1500);
            messagesNotes.setTranslateX(170);

            messagesSquare.setHeight(900);
            messagesSquare.setWidth(1500);
            messagesSquare.setTranslateX(240);

        }
    }
}
