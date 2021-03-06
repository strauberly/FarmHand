import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

// should be able to reduce setting the stage and making it draggable to a method

public class MainController {
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private Button weatherButton;
    @FXML
    private Button choresButton;
    @FXML
    private Button messagesButton;
    @FXML
    private Button closeButton;
    @FXML
    private Button minButton;


    @FXML
    private void onWeatherEntered() {
        weatherButton.setEffect(new Glow(.25));
    }

    @FXML
    private void onWeatherExited() {
        weatherButton.setEffect(new Glow(.00));
    }

    @FXML
    private void onWeatherPressed() {
        weatherButton.setEffect(new Glow(.80));
    }

    @FXML
    private void onWeatherReleased() {
        weatherButton.setEffect(new Glow(.00));
    }


    @FXML
    private void onWeatherClicked() throws IOException {
        Stage weatherStage = new Stage();
        weatherStage.initStyle(StageStyle.TRANSPARENT);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Weather/FXML/weatherInstructions.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        weatherStage.setScene(scene);
        weatherStage.setX(355);
        weatherStage.setY(50);
        weatherStage.show();
        if (weatherStage.isShowing()) {
            weatherButton.setOnAction(event -> weatherStage.close());
        }
        choresButton.setOnAction(event -> weatherStage.close());
        messagesButton.setOnAction(event -> weatherStage.close());
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            weatherStage.setX(event.getScreenX() - xOffset);
            weatherStage.setY(event.getScreenY() - yOffset);

        });
    }


    @FXML
    private void onChoresEntered() {
        choresButton.setEffect(new Glow(.25));
    }

    @FXML
    private void onChoresExited() {
        choresButton.setEffect(new Glow(.00));
    }

    @FXML
    private void onChoresPressed() {
        choresButton.setEffect(new Glow(.80));
    }

    @FXML
    private void onChoresReleased() {
        choresButton.setEffect(new Glow(.00));
    }

    @FXML
    private void onChoresClicked() throws IOException {
        Stage choresStage = new Stage();
        choresStage.initStyle(StageStyle.TRANSPARENT);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Chores/FXML/choresInProgress.fxml")));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        choresStage.setScene(scene);
        choresStage.setX(355);
        choresStage.setY(50);
        choresStage.show();
        if (choresStage.isShowing()) {
            choresButton.setOnAction(event -> choresStage.close());
        }

        weatherButton.setOnAction(event -> choresStage.close());
        messagesButton.setOnAction(event -> choresStage.close());
    root.setOnMousePressed(event -> {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    });

    root.setOnMouseDragged(event -> {
        choresStage.setX(event.getScreenX() - xOffset);
        choresStage.setY(event.getScreenY() - yOffset);
    });
    }


    @FXML
    private void onMessagesEntered() {
        messagesButton.setEffect(new Glow(.25));
    }

    @FXML
    private void onMessagesExited() {
        messagesButton.setEffect(new Glow(.00));
    }

    @FXML
    private void onMessagesPressed() {
        messagesButton.setEffect(new Glow(.80));

    }

    @FXML
    private void onMessagesReleased() {
        messagesButton.setEffect(new Glow(.00));
    }

    @FXML
    private void onMessagesClicked() throws IOException {
        Stage messagesStage = new Stage();
        messagesStage.initStyle(StageStyle.TRANSPARENT);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Messages/messages.fxml")));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        messagesStage.setScene(scene);
        messagesStage.setX(355);
        messagesStage.setY(50);
        messagesStage.show();
        if (messagesStage.isShowing()) {
            messagesButton.setOnAction(event -> messagesStage.close());
        }
        choresButton.setOnAction(event -> messagesStage.close());
        weatherButton.setOnAction(event -> messagesStage.close());
    }


    @FXML
    private void onMinEntered() {
        minButton.setEffect(new Glow(.25));
    }

    @FXML
    private void onMinExited() {
        minButton.setEffect(new Glow(.00));
    }

    @FXML
    private void onMinPressed() {
        minButton.setEffect(new Glow(.80));

    }

    @FXML
    private void onMinReleased(MouseEvent released) {
        minButton.setEffect(new Glow(.00));
        Stage stage = (Stage) ((Node) released.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }


    @FXML
    private void onCloseEntered() {
        closeButton.setEffect(new Glow(.25));
    }

    @FXML
    private void onCloseExited() {
        closeButton.setEffect(new Glow(.00));
    }

    @FXML
    private void onClosePressed() {
        closeButton.setEffect(new Glow(.80));
    }

    @FXML
    private void onCloseReleased(MouseEvent released) {
        closeButton.setEffect(new Glow(.00));
        Stage stage = (Stage) ((Node) released.getSource()).getScene().getWindow();
        stage.close();
    }
}

