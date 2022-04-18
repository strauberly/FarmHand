package Chores;

import Weather.WeatherController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ChoresController implements Initializable {
    @FXML
    private StackPane stack;
    @FXML
    public Label choresBannerText;

    @FXML
    private Button newChoreButton;
    @FXML
    private Rectangle choresBanner;
//    @FXML
//    private Label choresInstructions = new Label();
    @FXML
    private Rectangle choresSquare = new Rectangle();
    @FXML
    private Button choresMinButton;
    @FXML
    public Button choresMaxButton = new Button();
    @FXML
    private Button choresCloseButton = new Button();

    private static final String relativePath = "FXML/";

    public static String savedScene = "choresInProgress.fxml";

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


    public void choresMaxEnter(MouseEvent mouseEvent) {
        choresMaxButton.setEffect(new Glow(.25));

    }

    public void choresMaxExited(MouseEvent mouseEvent) {
        choresMaxButton.setEffect(new Glow(.0));

    }

    public void choresMaxPressed(MouseEvent mouseEvent) {
        choresMaxButton.setEffect(new Glow(.80));
    }
    public void choresMaxReleased(MouseEvent mouseEvent) throws IOException {
        System.out.println(maxed);
        System.out.println(savedScene);
        maxedChoresElements(mouseEvent);

    }
//        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
//        choresMaxButton.setEffect(new Glow(.0));
//        String savedScene = "FXML/choresInProgress.fxml";
//        if (!stage.isMaximized()) {
//            stage.setMaximized(true);
//            maxed = true;
//        } else {
//            stage.isMaximized();
//            if (stage.isMaximized()) {
//                stage.setMaximized(false);
//                maxed = false;
//            }
//        }
//    }




    public void newChoreEnter(MouseEvent mouseEvent) {
    }

    public void newChorePressed(MouseEvent mouseEvent) {
    }

    public void newChoreExited(MouseEvent mouseEvent) {
    }

    public void newChoreReleased(MouseEvent mouseEvent) throws IOException {
        Stage newChore = new Stage();
        newChore.initStyle(StageStyle.TRANSPARENT);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Chores/FXML/newChoreWindow.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        newChore.setScene(scene);
        newChore.show();
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        choresInstructions.setText("""
//                                                                           Hulloooooo, mAh d00d!
//
//                                                                             Select from above:
//
//                Stations:
//                Input your lat long and get a readout from a weather station,
//                log the readout to a database, view the database, and get observations based on the readouts stored.
//
//
//                Web Services:
//                View different weather pages on the net. Helpful in seeing whats coming long term.
//
//
//                Instruments:
//                Allows you to log data from manual gauges and get observations.
//                You know... in case zombies.
//
//                """);

        if (maxed) {
            choresBanner.setWidth(displaySize.getWidth());
            newChoreButton.setTranslateX(400);

        }
    }

    //behaviour if stage is or isn't maxed used by max/resize button
    // rewrite this logic for desired effect and reference weather controller
    public void maxedChoresElements(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        choresMaxButton.setEffect(new Glow(.0));
        if (!stage.isMaximized()) {
            stage.setMaximized(true);
            maxed = true;
        } else if ((stage.isMaximized() && savedScene.equals("choresInProgress.fxml"))) {
            stage.setMaximized(false);
            maxed = false;
        } else if (stage.isMaximized()) {
            stage.setMaximized(false);
            maxed = false;
        }
        setChoresScene(savedScene, mouseEvent);
    }

    public static void setChoresScene(String fileName, MouseEvent mouseEvent) throws IOException {
        final double[] xOffset = {0};
        final double[] yOffset = {0};
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(WeatherController.class.getResource(relativePath + fileName)));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.setX(355);
        stage.setY(50);
        stage.show();
        root.setOnMousePressed(event -> {
            xOffset[0] = event.getSceneX();
            yOffset[0] = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset[0]);
            stage.setY(event.getScreenY() - yOffset[0]);

        });
        savedScene = fileName;
    }

    public static boolean isMaxed() {
        return maxed;
    }

    public void newChoreCloseExit(MouseEvent mouseEvent) {
    }

    public void newChoreCloseEnter(MouseEvent mouseEvent) {
    }
}



