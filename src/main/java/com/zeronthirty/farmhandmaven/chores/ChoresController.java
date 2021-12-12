package com.zeronthirty.farmhandmaven.chores;

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

public class ChoresController implements Initializable {
    @FXML
    Label ChoresHeader;
    @FXML
    private Rectangle choresBanner;
    @FXML
    private Label choresNotes = new Label();
    @FXML
    private Rectangle choresSquare = new Rectangle();
    @FXML
    private Button choresMinButton;
    @FXML
    public Button choresMaxButton = new Button();
    @FXML
    private Button choresCloseButton = new Button();

    private static boolean maxed = false;

    private static final Rectangle2D displaySize = Screen.getPrimary().getBounds();


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

    public void choresMaxReleased(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        choresMaxButton.setEffect(new Glow(.0));
//        String savedScene = "FXML/choresDevelopment.fxml";
        if (!stage.isMaximized()) {
            stage.setMaximized(true);
            maxed = true;
        }else {
            stage.isMaximized();
            if (stage.isMaximized()){
                stage.setMaximized(false);
                maxed = false;
            }
        }
    }

    public void choresMaxPressed(MouseEvent mouseEvent) {
    }

    public void choresMaxExited(MouseEvent mouseEvent) {
    }

    public void choresMaxEnter(MouseEvent mouseEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choresNotes.setText("""
                                                                           Behold!
                                                  This Section Is Under Development.
                                    
               Imagine this, A dashboard showing progress of chores and the ability to make a list for indoors and outdoors!
               Perhaps also notes for chores plus due dates etc? Maybe a heat map showing how busy a week is... might be kind of hip.
               
               How does that sound to mAH d00d?
               
               Far out... Will get back to you on that.

                """);

        if (maxed) {
            choresBanner.setWidth(displaySize.getWidth());
            maxed = true;

            choresNotes.setFont(Font.font(28));
            choresNotes.setPrefHeight(900);
            choresNotes.setPrefWidth(1500);
            choresNotes.setTranslateX(170);

            choresSquare.setHeight(900);
            choresSquare.setWidth(1500);
            choresSquare.setTranslateX(240);
        }
    }
}
