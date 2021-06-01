package main.Inventory;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class InventoryController {
    
    
    public Button inventoryMinButton;
    public void inventoryMinEnter(MouseEvent mouseEvent) {
        inventoryMinButton.setEffect(new Glow(.25));
    }

    public void inventoryMinExit(MouseEvent mouseEvent) {
        inventoryMinButton.setEffect(new Glow(.0));
    }

    public void inventoryMinPressed(MouseEvent mouseEvent) {
        inventoryMinButton.setEffect(new Glow(.80));

    }

    public void inventoryMinReleased(MouseEvent mouseEvent) {
        inventoryMinButton.setEffect(new Glow(.0));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }



    public Button inventoryCloseButton;
    public void inventoryCloseEnter(MouseEvent mouseEvent) {
        inventoryCloseButton.setEffect(new Glow(.25));
    }

    public void inventoryCloseExit(MouseEvent mouseEvent) {
        inventoryCloseButton.setEffect(new Glow(.0));
    }

    public void inventoryClosePressed(MouseEvent mouseEvent) {
        inventoryCloseButton.setEffect(new Glow(.80));
    }

    public void inventoryCloseReleased(MouseEvent mouseEvent) {
        inventoryCloseButton.setEffect(new Glow(.0));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
