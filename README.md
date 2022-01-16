FarmHandReadMe -

This application is built as a demonstration of ability in Java for consideration by future employers. It is my first solo application built from scratch, and is built while juggling a life filled with full time employment, family, and continued education in order to make a career change.

It is designed at this time as a standalone desktop app to be accessed in person from a central terminal in a common area.

The application's goal is to serve as a means to help a small team manage tasks on a small scale or hobby farm.

Functionality to include but not limited to:

Weather observation, planning tasks in accordance with weather conditions, monitoring progress and completion of tasks. As well, there will be functionality for basic messaging between team members.

# The following is the development journal of how this project came to be. Thank you for reading this and for checking out the project.

# Cheers!

---

-- 15 Dec 2021 --

- Code currently stable and functioning as intendeded to
  best of my knowledge. Huzzah.

- Future development notes made.

Now that there is a working prototype and proof of concept version. Development here will begin to slow as I will be begining a new web based project focused on java web services and api's as well as continuing courses.

If nothing else the read me will live on with updates.

Please feel free to download source and take for a spin.

Check out the technical read me for further notes on design
and construction of the program. Short demo videos to follow and be uploaded.

---

Dev1

- reformated read me file

---

---

Dev2

- validated font issue was fixed and adjusted layout

---

-- 14 Dec 2021 --

\*\* Continuing through the to do

- jpackage 90% successful but ultimately still ending up no manifest error.

- shaded plugin however worked

- next steps will include a fresh build of current project retyping code,
  possibly in eclipse

- rebuilt code - project now has a working executable jar (for linux at least) as a prototype. May not seem like a lot but exciting for me!

# Has definitely increased understanding of Maven, Jpackage, Jlink and building out programs beyond an IDE and getting them ready for the wild.

Dev1

---

---

Dev2

- continue adjusting gui for resize

- adjust gui to be the same across any platform regardless of size

- auto scale fonts

---

-- 13 Dec 2021 --

\*\* Continuing through the to do

- attempted cross-platform jar without much luck going to individual platform packaging as seems to be the norm with a gui app. Will continue to investigate down the road.

---

Dev1

- package program

---

---

Dev2

- continue adjusting gui for resize

- adjust gui to be the same across any platform regardless of size

- auto scale fonts

---

Maven plugin notes and tutorial

<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.2.4</version>
    <configuration>
        <transformers>
            <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                <mainClass>com.zeronthirty.farmhandmaven.Launcher</mainClass>
            </transformer>
        </transformers>
    </configuration>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>shade</goal>
            </goals>
        </execution>
    </executions>
</plugin>

JPackagePlugin

OpenJDK 16 and JPackage
OpenJDK 16 has been released on 16th March. However, this is not the right post to talk about all the new shining features and tools, but it is the right post to talk about a new particular tool: JPackage.
JPackage was introduced as an incubating tool from JDK 14 to JDK 15 and is now production-ready for packaging self-contained Java applications.
As above we want to rely on Maven and fortunately, we got a well-packed plugin (JPackage Plugin) on Maven Central that we can add:

<plugin>
  <groupId>org.panteleyev</groupId>
  <artifactId>jpackage-maven-plugin</artifactId>
  <version>1.4.0</version>
  <configuration>
    <name>TreeFX</name>
    <appVersion>1.0.0</appVersion>
    <vendor>com.acme</vendor>
    <destination>target/dist</destination>
    <module>treefx/com.acme.treefx.TreeFX</module>
    <runtimeImage>target/treefx</runtimeImage>
    <linuxShortcut>true</linuxShortcut>
    <linuxPackageName>treefx</linuxPackageName>
    <linuxAppCategory>Utilities</linuxAppCategory>
    <linuxMenuGroup>Utilities</linuxMenuGroup>
    <icon>${project.basedir}/duke.png</icon>
    <javaOptions>
      <option>-Dfile.encoding=UTF-8</option>
    </javaOptions>
  </configuration>
</plugin>

I have to spend some words about the configuration because it's a little bit tricky on some points.
Easy part
• name sets the application name (you don't say!)
• appVersion as any other application, our TreeFX has a release version too!
• vendor sets the vendor or our Acme company
• destination sets the destination folder for the generated package

Tricky part
◇ module we set the module and the main class of our application since JPackage will generate a new executable for our package
◇ runtimeImage is usually set to our development OpenJDK, because JPackge could work with JLink to create the custom JRE, but because of some issues I found with JavaFX and JLink (the process is not straightforward), we trick JPackage and we tell it to take the previous custom JRE created with the OpenJFX plugin

Linux part (I put it just for the sake of completeness)
◇ linuxShortcut creates a shortcut after the application installation process
◇ linuxPackageName sets the final deb package name
◇ linuxAppCategory sets the Linux application category
◇ linuxMenuGroup sets the Linux menu-group

Maybe I have to spend more words on the tricky part. As mentioned above, JPackage is a tool for packaging a self-contained Java application, any kind of Java application, modularized and non-modularized applications. But for this example, we want to distribute a simple animation and it would be quite weird to distribute a whole JRE image for the sake of one animation. Therefore, always think about what you really need for your application and let the right tool works for you.
About linux part. JPackage allows you to package not only for Linux, but also for Windows and MacOS. However, JPackage depends on the operating system in order to work, so you can't package a Linux app, if you're using Window or MacOS, in a nutshell, no, cross-compiling is not an option. We will try to package our TreeFX app for Windows in an addendum to this article (maybe next week).
Enough chatting. Let's see it work:

$ mvn clean compile javafx:jlink jpackage:jpackage

Wait a while and then:

$ ls target/dist/
treefx_1.0.0-1_amd64.deb

Nice fourice! We now have our package for AMD64 arch (yeah that's mine). If we still have some curiosity about how the deb package is organized, we can unpack it:

$ dpkg-deb -R target/dist/treefx_1.0.0-1_amd64.deb target/unpacked
$ tree target/unpacked

target/unpacked
├── DEBIAN
│   ├── control
│   ├── postinst
│   ├── postrm
│   ├── preinst
│   └── prerm
└── opt
└── treefx
├── bin
│   └── TreeFX
├── lib
│   ├── app
│   │   └── TreeFX.cfg
│   ├── runtime
│   │   ├── [...]
│   ├── TreeFX.png
│   └── treefx-TreeFX.desktop
└── share
└── doc
└── copyright

We could optimize things and surf into other JPackage configurations, but so far so good (as an old Bryan Adams' album and song said).

---

- 12 Dec 2021 -

\*\* Continuing through the to do

- Maven Version uploaded to repository

- rough logo icorporated

- fixed broken code in instruments pertaining to includinf .fxml vs just the file name

- corrected input error bug introduced during rebuild

---

Dev1

- package program

---

---

Dev2

- continue adjusting gui for resize

- adjust gui to be the same across any platform regardless of size

- auto scale fonts

---

- 9 Dec 2021 -

  \*\* Continuing through the to do

  - Project successfully rebuilt with Maven

  - Webview in web services required finding and incorporating libjfxwebkit

  - Backing up and wil upload to git as a branch

  - created from development notifications for chores and messaging

  - moved classes to corresponding packages so that Maven project design is near identical to original app design attempt
    to keep code well organised

  \*\* took a while to get into Maven but hate it much less now

---

Dev1

- package program > create third branch for maven

---

---

Dev2

-create logo

- continue adjusting gui for resize

- adjust gui to be the same across any platform regardless of size

- auto scale fonts

---

-- 8 Dec 2021 --

\*\* Continuing through the to do

** Project mostly rebuilt in Maven
**project completely rebuilt without Maven

---

Dev1

- package program > learn more about maven

- try packaging with earlier version

- need to figure out its formatting url for relative paths and resources

- try running earlier version of fxml and java in dependencies

- also try creating running java jar files now with new JDK

- try fast forwarding through a javafx maven tutorial to get the jist, look for a jdbc/mysql

---

---

Dev2

- continue adjusting gui for resize
- adjust gui to be the same across any platform regardless of size

- auto scale fonts

---

-- 7 Dec 2021 --

\*\* Continuing through the to do

\*\* Have begun rebuilding project in Maven

- project set up is really unintuitive in comparison to older set up and now

- apparently intellij wont let you set up a javafx program with out maven. Incredibly frustrating.

- trying to sort why in fxml file I trace controller paths with dots and then when listing out fxml files in a controller it appears to be with slashes

- or something else I cant seem to put my finger on. Really excited to get it figured out though.
- \*\* a tad stuck reorganizing with Maven will give it another go down the road. Have rebuilt Project without in the mean time

---

Dev1

- package program > learn more about maven

---

---

Dev2

- continue adjusting gui for resize

- adjust gui to be the same across any platform regardless of size

- auto scale fonts

---

-- 6 Dec 2021 --

\*\* Continuing through the to do

** adjusted stations label position
** trade positions of web view and label and box for webservices on click
**reformatted MainController
**reformatted WebServicesController > added method for setting webviewer
**reformatted StationsController
**reformatted InstrumentsController
\*\*reformatted WeatherController

\*\*Time spent researching packaging and deployment

- looks like options are creating a fat/uber jarr in Maven with shadow plugin or building out a file for each operating system.

- most documentation seems to be for a machine with a java version already installed

- Looking a way to package with an instance of java runtime

- feels like I may have just jumped back in over my head a little but will hope to resolve, package, deploy and share before year end.

---

Dev1

- package program > learn more about maven

---

---

Dev2

- continue adjusting gui for resize

- adjust gui to be the same across any platform regardless of size

- auto scale fonts

---

-- 5 Dec 2021 --

\*\* Continuing through the to do list

** tables resize on max
** observations resize on max
** eliminated need for station input error fxml by changing text on button press determined by conditions. So it is gone, so it goes.
** instruments resize as intended
** close button resets maxed to false so if weather restarted it does so at default and all operates as intended
** web services instructions included.

---

Dev1

- find out how to preload a method with a parameter ie mouse event

- package program

- include water mark or com.

- look for more code fine tuning

- implement controller schem from top to bottom.
- objects
- objectmethods
- init
- getters
- setters
- add future implementations notes

---

---

Dev2

- update read me and upload to git

- continue adjusting gui for resize

- adjust gui to be the same across any platform regardless of size

- auto scale fonts

---

-- 4 Dec 2021 --

\*\* Continuing through the to do list

- including parameters in initialization is beginning to produce desired effects. Next check action event handler and source.

- set switch based on position of switch elements populate with certain if valus and populate with default values if switch in other position >> un the struglle bus with it for a bit until breaking the scenario back into basic parts

# \*\* max button now operates as intended

- observations fxml is no longer needed now just resets stations output to get both bodies of text, could do with some renaming

---

Dev1

---

---

Dev2

- continue adjusting gui for resize

- adjust gui to be the same across any platform regardless of size

- auto scale fonts

- possibly adjust so that all three windows can be opened visible and then user can select wich window to enlarge >> could be creating way more headache could be the stand apart > possibly set it up so if already showing that window just goes to the front in that position

- package program

- web services instructions

---

Weather Controller

package main.Weather;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_SRC_OUTPeer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.stage.Stage;
import main.Weather.stations.StationsController;

import java.awt.\*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

//Besides managing main weather button actions, also contains method for quickly setting scenes.
public class WeatherController implements Initializable{
@FXML
protected Label instructions = new Label();
@FXML
protected Label weatherBannerText;
@FXML
protected static StackPane stack = new StackPane();
@FXML
private Rectangle weatherBanner = new Rectangle();
@FXML
private Button weatherMinButton = new Button();
@FXML
private Button weatherCloseButton = new Button();
@FXML
public Button weatherMaxButton = new Button();
@FXML
private Button stationsButton = new Button();
@FXML
private Button instrumentsButton = new Button();
@FXML
private Button webButton = new Button();

    private static String savedScene;

    Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();

    public static boolean maxed;



    @FXML
    private void weatherMinEnter(MouseEvent mouseEvent) {
        weatherMinButton.setEffect(new Glow(.25));
    }

    @FXML
    private void weatherMinExited(MouseEvent mouseEvent) {
        weatherMinButton.setEffect(new Glow(.0));
    }

    @FXML
    private void weatherMinPressed(MouseEvent mouseEvent) {
        weatherMinButton.setEffect(new Glow(.80));

    }

    @FXML
    private void weatherMinReleased(MouseEvent mouseEvent) {
        weatherMinButton.setEffect(new Glow(.0));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void weatherMaxEnter(MouseEvent mouseEvent) {
        weatherMaxButton.setEffect(new Glow(.25));
    }

    @FXML
    private void weatherMaxExited(MouseEvent mouseEvent) {
        weatherMaxButton.setEffect(new Glow(.0));
    }

    @FXML
    // create method for weather big
    private void weatherMaxPressed(MouseEvent mouseEvent) throws IOException {
        System.out.println(maxed);
    }

    @FXML
    private void weatherMaxReleased(MouseEvent mouseEvent) throws IOException {
     maxedWeatherElements(mouseEvent);
    }


    @FXML
    private void weatherCloseEnter(MouseEvent mouseEvent) {
        weatherCloseButton.setEffect(new Glow(.25));
    }

    @FXML
    private void weatherCloseExit(MouseEvent mouseEvent) {
        weatherCloseButton.setEffect(new Glow(.0));
    }

    @FXML
    private void weatherClosePressed(MouseEvent mouseEvent) {
        weatherCloseButton.setEffect(new Glow(.80));
    }

    @FXML
    private void weatherCloseReleased(MouseEvent mouseEvent) {
        weatherCloseButton.setEffect(new Glow(.0));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }


    @FXML
    private void stationsEntered(MouseEvent mouseEvent) {
        stationsButton.setEffect(new Glow(.25));
    }

    @FXML
    private void stationsExited(MouseEvent mouseEvent) {
        stationsButton.setEffect(new Glow(.0));
    }

    @FXML
    private void stationsPressed(MouseEvent mouseEvent) throws IOException {
        stationsButton.setEffect(new Glow(.80));
    }

    @FXML
    private void stationsReleased(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stationsButton.setEffect(new Glow(.0));
        if (stage.isMaximized())
            maxed = true;
        setWeatherScene("Stations", mouseEvent);
        System.out.println(maxed);
        System.out.println(savedScene);
    }



    @FXML
    private void instrumentsEnter(MouseEvent mouseEvent) {
        instrumentsButton.setEffect(new Glow(.25));
    }

    @FXML
    private void instrumentsExited(MouseEvent mouseEvent) {
        instrumentsButton.setEffect(new Glow(.0));
    }

    @FXML
    private void instrumentsPressed(MouseEvent mouseEvent) {
        instrumentsButton.setEffect(new Glow(0.80));
    }

    @FXML
    private void instrumentsReleased(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        instrumentsButton.setEffect(new Glow(0.0));
        setWeatherScene("InstrumentsDBDisplay", mouseEvent);
        if (stage.isMaximized())
            maxed = true;
        System.out.println(maxed);
        System.out.println(savedScene);
    }



    @FXML
    private void webEnter(MouseEvent mouseEvent) {
        webButton.setEffect(new Glow(.25));
    }

    @FXML
    private void webExited(MouseEvent mouseEvent) {
        webButton.setEffect(new Glow(.0));
    }

    @FXML
    private void webPressed(MouseEvent mouseEvent) {
        webButton.setEffect(new Glow(.80));
    }

    @FXML
    private void webReleased(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        setWeatherScene("WebServices", mouseEvent);
        if (stage.isMaximized())
            maxed = true;
        System.out.println(maxed);
        System.out.println(savedScene);
    }


    public static void setWeatherScene (String fileName, MouseEvent mouseEvent) throws IOException {
        final double[] xOffset = {0};
        final double[] yOffset = {0};
        Parent root = FXMLLoader.load(Objects.requireNonNull(WeatherController.class.getResource("/main/Weather/FXML/" + fileName + ".fxml")));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset[0] = event.getSceneX();
                yOffset[0] = event.getSceneY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset[0]);
                stage.setY(event.getScreenY() - yOffset[0]);

            }
        });
        savedScene = fileName;
        }

    public static boolean instrumentsValidInput(String s) {
        for (int i = 0; i < s.length(); i++)
            if (!Character.isDigit(s.charAt(i)))
                return false;
        return true;
    }

    public static boolean stationsValidInput(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instructions.setText("""
                                                                        Hulloooooo, mAh d00d!

                                                                           Select from above:

                Stations to input your lat long and get a readout from a weather station,
                log the readout to a database, view the database, and get observations based on the readouts stored.


                Web Services to view different weather pages on the net. Helpful in seeing whats coming long term.


                Instruments allows you to log data from manual gauges and get observations. You know...
                in case zombies.

                """);
        if (maxed) {
            weatherBanner.setWidth(displaySize.getWidth());
            stationsButton.setTranslateX(200);
            webButton.setTranslateX(400);
            instrumentsButton.setTranslateX(600);
            maxed = true;

// } else
// weatherBanner.setWidth(1200);
// stationsButton.setTranslateX(0);
// webButton.setTranslateX(0);
// instrumentsButton.setTranslateX(0);
//// weatherMaxButton.setTranslateX(0);
//// weatherMinButton.setTranslateX(0);
//// weatherCloseButton.setTranslateX(0);
// maxed = false;
}
}

    public static void setSavedScene() {
        WeatherController.savedScene = "WeatherInstructions";
    }

    //create one of these for each and a return to
    public void maxedWeatherElements(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        weatherMaxButton.setEffect(new Glow(.0));
        if (!stage.isMaximized()) {
            stage.setMaximized(true);
            maxed = true;
            System.out.println(true);
            System.out.println(savedScene);
        }else if ((stage.isMaximized()&& savedScene.equals("WeatherInstructions"))){
            stage.setMaximized(false);
            System.out.println(savedScene);
            maxed = false;
            System.out.println(false);
        }else if (stage.isMaximized()){
            stage.setMaximized(false);
            maxed = false;
            System.out.println(savedScene);
            System.out.println(maxed);
        }
        setWeatherScene(savedScene,mouseEvent);
    }

    public static boolean isMaxed() {
        return maxed;
    }

    public static void setMaxed(){
        maxed = false;
    }


    // stage a universal variable from main controller?
    public void maxedStationsElements(MouseEvent mouseEvent) throws IOException{
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        if (stage.isMaximized()){
        }
    }

    public static String getSavedScene() {
        return savedScene;
    }

}

---

-- 3 Dec 2021 --

\*\* Continuing through the to do list

- Including parameters in initialization is beginning to produce desired effects.

- Next check action event handler and source.

## \*\* banged head against wall sorting out logic for max button and how it would affect other elements

---

Dev1

---

---

Dev2

> >

Start the following changes small for proof of concept

- rest element method

- current model is extended model has its elements

- and calls in base elements but so far does not control base elements -> need to figure out how to rectify this

- at moment loads with base elements then changes only base elements vs

- change scene > check for maxed = true >
  if true (change properties of all elements) to fit maxed
  scheme

- > in initialize check maxed > if maxed > load scene with maxed values

- attampt loading fxml with additional controllers > research nested controllers

!! all elements listed out into controller and on max button pressed all elements change appropriately this will take a fair bit of time so be patient and work at it one step at a time.

- adjust gui to be the same across any platform regardless of size

- auto scale fonts

- find means to get resolution and set dimensions accordingly

- possibly adjust so that all three windows can be opened visible and then user can select wich window to enlarge >> could be creating way more headache could be the stand apart > possibly set it up so if already showing that window just goes to the front in that position

- package program

- web services instructions

---

-- 2 Dec 2021 --

\*\* Continuing through the to do list

- max button initial function as desired

- banner and banner buttons resizing as desired from Weather Instructions

- all Weather submenus resize, next will be resizing their elements

- created variable to hold name of fxml file name and entered when scene changes then logic is worked out based on from those values and if stage is maximized or not.

- Saved file name is reset to null if weather button clicked.

\*\* new elements below

---

---

---

Dev2

- if saved scene == && max pushed || stage maxed and button pushed

  - resize elements

- set big weather etc

- record filename set as variable > when min button is pressed setscene with file name and it should display as originally set up > set up set scene so that it does its job and returns file name variable then == set scene

!! all elements listed out into controller and on max button pressed all elements change appropriately this will take a fair bit of time so be patient and work at it one step at a time.

- adjust gui to be the same across any platform regardless of size

- auto scale fonts

- find means to get resolution and set dimensions accordingly

- possibly adjust so that all three windows can be opened visible and then user can select wich window to enlarge >> could be creating way more headache could be the stand apart > possibly set it up so if already showing that window just goes to the front in that position

- package program

---

# updated

static String savedScene;
public static void setWeatherScene (String fileName, MouseEvent mouseEvent) throws IOException {
final double[] xOffset = {0};
final double[] yOffset = {0};
Parent root = FXMLLoader.load(Objects.requireNonNull(WeatherController.class.getResource("/main/Weather/FXML/" + fileName + ".fxml")));
Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
Scene scene = new Scene(root);
scene.setFill(Color.TRANSPARENT);
stage.setScene(scene);
stage.show();

    root.setOnMousePressed(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            xOffset[0] = event.getSceneX();
            yOffset[0] = event.getSceneY();
        }
    });

    root.setOnMouseDragged(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            stage.setX(event.getScreenX() - xOffset[0]);
            stage.setY(event.getScreenY() - yOffset[0]);

        }
    });
    System.out.println(fileName);
    savedScene = fileName;
    System.out.println(savedScene);

}

- action event handler if event is max button / resize button

- and saved scene == current scene/controller then resize the elements

- find out how to preload a method with a parameter

- web services instructions

---

--1 Dec 2021 --

** Continuing through the to do list
** added instructions to weather scene
** swapped positions of stations, web sites and instruments
** changed inventory values to messaging
\*\* user test with parents established that gui needs ability to size to full scale

---

Dev1

- tasks complete at this point

---

---

Dev2

> >

!! all elements listed out into controller and on max button pressed all elements change appropriately this will take a fair bit of time so be patient and work at it one step at a time.

- @web http://java-buddy.blogspot.com/
  \*/
  public class JavaFX_Screen extends Application {

      @Override
      public void start(Stage primaryStage) {

          Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();

          Label label = new Label();
          label.setText(
                  visualBounds.getWidth() + " x " + visualBounds.getHeight());

          StackPane root = new StackPane();
          root.getChildren().add(label);

          Scene scene = new Scene(root,
                  visualBounds.getWidth(), visualBounds.getHeight());

          primaryStage.setTitle("java-buddy.blogspot.com");
          primaryStage.setScene(scene);
          primaryStage.show();
      }

      public static void main(String[] args) {
          launch(args);
      }

# Get Screen Size of Primary Monitor

| 123456789101112131415 | import javafx.application.Application;import javafx.geometry.Rectangle2D;import javafx.stage.Screen;import javafx.stage.Stage; public class Main extends Application {   @Override  public void start(Stage primaryStage) {    //Get primary screen bounds    Rectangle2D screenBounds = Screen.getPrimary().getBounds();    System.out.println(screenBounds);    System.exit(0);  }} |

Example Output

| 1 | Rectangle2D [minX = 0.0, minY=0.0, maxX=1920.0, maxY=1080.0, width=1920.0, height=1080.0] |

Get Number Of Monitors / Visual Devices
Let’s see how we can find number of monitors currently attached to the system using the JavaFX Screen API.

| 123456789101112 | import javafx.application.Application;import javafx.stage.Screen;import javafx.stage.Stage; public class Main extends Application {   @Override  public void start(Stage primaryStage) {    System.out.println(Screen.getScreens().size());    System.exit(0);  }} |

Example Output

| 1 | 2 |

Get Screen Size Of All Monitors

| 12345678910111213141516 | import javafx.application.Application;import javafx.collections.ObservableList;import javafx.stage.Screen;import javafx.stage.Stage; public class Main extends Application {   @Override  public void start(Stage primaryStage) {    ObservableList<Screen> screenSizes = Screen.getScreens();    screenSizes.forEach(screen -> {      System.out.println(screen.getBounds());    });    System.exit(0);  }} |

\*\*Start with if max button is pressed then either change to maxed css

- or change to maxed FXML that changes to maxed css

- adjust gui to be the same across any platform regardless of size

- auto scale fonts

- find means to get resolution and set dimensions accordingly

- package program

---

-- 30 Nov 2021 --

\*\* Continuing through the to do list

- Restructered observations class and removed significant amount of lines

- Moved two query methods to Weather Data Base
- Next steps to include altering gui and packaging

## uploading current build to repos

---

Dev1

## tasks completed

---

Dev2

- create new scenes for instructions

- adjust gui to be the same across any platform regardless of size

- auto scale fonts

- swap positions of stations, web sites and instruments

- change inventory values to messaging

- package program

---

-- 29 Nov 2021 --

\*\*Continuing through the to do list

- Prepared statements and eliminate possibilities for sql injection
  should be ok with out diving in depth here as user does not have function of submitting a query.

- This could change and needs to be kept in mind.

- New thread kicks off while calling api. Seems to help eliminate lag
  while simultaneously streaming pandora.

- Included trim method in api call so if user fat fingers the space bar it doesn't impede the function.

---

Dev1

- consider switch statements and address observations notes

---

---

Dev2

- create new scenes for instructions

- adjust gui to be the same across any platform regardless of size

- auto scale fonts

- swap positions of stations, web sites and instruments

- package program

---

-- 28 Nov 2021 --

## Notation of Dev 1 and Dev 2 is just me breaking down tasks and practicing project management. Dont think I'm quite losing my mind yet. ;)

## Dev 1 is Backend Focused & Dev 2 Frontend focused.

\*\*Continuing through the to do list

- User feedback for input errors provided for instruments

- Began work on stations but a regular expression may be needed in order to validate results. Will be a good reminder and excercise anyways.

---

- if lat and long are not empty and are  
  a double (doesn't matter if positive or negative)
  submit values

- if illigitimate values create alert

\*\* found means to verify user input is a double through simple means, also to validate values
entered are within range of lat and long

---

Dev1

- prepared statements and eliminate possibilities for sql injection

- go through each class and address notes

- look into rewriting controllers for more efficient use instead of being replicated

- look for places to inact multi thread and processing animations

- look into rewriting controllers for more efficient use instead of being replicated

---

---

Dev2

- create new scenes for instructions

- adjust gui to be the same across any platform regardless of size

- auto scale fonts

- swap positions of stations, web sites and instruments

- package program

---

-- 27 Nov 2021 --

Continuing through the to do list
shifted properties from public to private for the following classes

- instruments controller

---

## \*\*some unneccesary FXML files removed

- create new scenes for instructions

- look into rewriting controllers for more efficient use instead of being replicated

- error pages and quantifiers

- handle exceptions

- package program

- adjust gui to be the same across any platform regardless of size
  such as while calling api

- look for places to inact multi thread and processing animations

- prepared statements and eliminate possibilities for sql injection

- go through each class and address notes

- auto scale fonts

---

-- 24 Nov 2021 --

Continuing through the to do list
shifted properties from public to private for the following classes

- wdbdaily
- dbcontroller
- stations
- stationscontroller
- observations

---

- inact proper convention

- create new scenes for instructions

- look into rewriting controllers for more efficient use instead of being replicated

- FXML to be arranged so user interaction is seperate and thereby allows inclusion in all additional scenes
  including instructions

- error pages and quantifiers

- handle exceptions

- package program

- Desired FXML for stations non functional can not load new stage, either a 400 error as api does not have a location to call >> work out other functionality in scene change leave stations as you had it just include new stations page without label

- adjust gui to be the same across any platform regardless of size

- look for places to inact multi thread and processing animations

- prepared statements and eliminate possibilities for sql injection

- go through each class and address notes

- auto scale fonts

---

-- 22 Nov 2021 --

Shifted properties from public to private for the following classes

- weather controller
- webservices controller
- weatherdb
- wdbweekly
- wdbhourly
- wdbdaily

---

- try to inact proper convention

- create new scenes for instructions

- look into rewriting controllers for more efficient use insteaad of being replicated

- FXML to be arranged so user interaction is seperate and thereby allows inclusion in all additional scenes
- including instructions

- error pages and quantifiers

- handle exceptions

- package program

- Desired FXML for stations non functional can not load new stage, either a 400 error as api does not have a location to call >> work out other functionality in scene change leave stations as you had it just include new stations page without label

- Adjust gui to be the same across any platform regardless of size

- Look for places to inact multi thread

- Prepared statements and eliminate possibilities for sql injection

- Go through each class and address notes

---

-- 20 Nov 2021 --

## Uploading version to git

\*\* DB is now written directly into project instead of on an absolute path limiting what system program operates from without changing values.

\*\* If written for specific client and none other it would be written to a specific file path and makes program unusable to others?

create new scenes for instructions

- Look into rewriting controllers for more efficient use insteaad of being replicated

- FXML to be arranged so user interaction is seperate and thereby allows inclusion in all additional scenes
  including instructions

- error pages and quantifiers

- handle exceptions

- package program

- Desired FXML for stations non functional can not load new stage, either a 400 error as api does not have a location to call >> work out other functionality in scene change leave stations as you had it just include new stations page without label

- Adjust gui to be the same across any platform regardless of size

---

--15 Nov 2021 --

- DB columns now appear By order of last entered perhaps, still sortable ?

- FXML to be arranged so user interaction is seperate and thereby allows inclusion in all additional scenes
  including instructions

- Error pages and quantifiers

- Handle exceptions

- Identify system and write db in safe area

- Package program

- Desired FXML for stations non functional can not load new stage, either a 400 error as api does not have a location to call >> work out other functionality in scene change leave stations as you had it just include new stations page without label

---

-- 14 Nov 2021 --

_!!Stations failure >> no longer populating label as desired back to drawing board!!_

- Stations functioning as intended and now logs displayed conditions, displays logs and gets observations.

- Tweaked observations a bit more time for real world trial

- Next might be making sure latest reading is always at top

- Followed by clean up

- Followed by possible validation for bad data entered

- Work backed up among drives git upload to follow shortly

“Say now... You better straighten up and fly right!”
-- Leonard Lee Dill - 14 Nov 2021 (RIP)
========

---

- 13 Nov 2021 -

Updated a bit of Stations FXML and current conditions from stations class. Now logs to the database as well

- Future implementations will included viewing the logs
- And getting observations

- Observations class also needs a little bit of work determining rising falling and unsettled

---

-- 11 Nov 2021 --

- Implementation of new fxml method
- Adjusment of fxml items

---

-- 10 Nov 2021 --

- Created weather class to house scene change method and any other repetitive methods that need to be called into a controller
- Reformatted scene change method to accept string and mouse event

- Reformatted scene change method to house local variables in the method for position x and y.

# Operating as desired so far.

---

-- 9 Nov 2021 --

-created method for setting stage

Need to gather all fxml into singular package for a non muddled integration,
be prepared that you are most likely about to break a fair bit of your code, but this should eliminate 21 lines of code where used

- unable to acheive desired results as method containing scene change must have a file name put in and still be able to be called a mouse event, where as method that opens and closes stage hard codes the fxml. Will take a bit more work.
- main stage is no longer moveable but weather stage and all scenes contained are. Intention is for more of an experience of main stage being anchored while extra stage can be moved out of the way to view windows in background.

Would like to possibly play with the whole window being resizeable.
Will play around with visual formatting of gui and then see about logging data provided from the called weather api. Feels like its starting to get ready to present.

---

-- 8 Nov 2021--

primary stage is now dragable around screen not sure if this is the implementation I want yet or not

---

- 2 Nov 2021 -

Just need some initialization work to get observations to display as intended.

Updated criteria for stormy, hot and dry, improving, detiorating.

Formatted text

included error messages for bad input, insufficient number of entries in instruments.

include errors for leaving fields blank or garbage

- try writing as regular expressions

Next to include attempts to test, optimize, display on different screen sizes, write db to a location neutral to type of system, clean code and check for best practices

Current state of code to be pushed to GIT

---

-- 1 Nov 2021 --

Adjusted hotAndDryDefinitiion() and humidityDefinition()
in Observations.

Adjusted improvingDefinition() and detioratingDefinition()
in Observations.

---

-- 27 Oct 2021 --

Definitions for observations logic(ie. rising, falling, hot, cold, windy etc.)

---

-- 22 Oct 2021 --

## All tables now display as they should, next clean, style and observations logic. Feels pretty good. Cheers!

---

-- 20 Oct 2021 --

HourlyDB is displaying now in tableview as it should. Next Rinse and repeat for other 2 tables, clean up, and begin observations logic.

---

-- 10 Oct 2021 --

All tables have observable columns and writing to database methods cleared of anomalies.

Next steps to include building out classes properly and attempting to display once more.

---

-- 30 Sept 2021 --

Much research into displaying data some work put into restructuring methods into more suitable classes. Efforts are to streamline before displaying.

## Dayjob finally starting to slow for the year and so hoping for more available time to renew efforts in development.

---

-- 22 Aug 2021 --

Created method that selects all timestamps from hourly table and converts them into an observable array list of human readable times. Goal is to then bind list to the appropriate column.

## Wish me luck.

---

-- 21 Aug 2021 --

Repository updated, pushed to version, merged to master.

Began creating fxml file to display database after logging an entry.

Sorted through one more possible means to save useable formatted date. Results unsatifactory.

Next step will include calling time from tables and converting each entry to user readable, storing in a list and then
populating appropriate column with results stored in list. Hoping to populate remaining columns with a table view or
or queries. Possibly a multi threaded process in order to aide in spead and efficiency.

---

-- 19 Aug 2021 --

# Functioning Database! Does what it is supposed to!

Created weekly class and worked out logic for when to write to each table.

Next is a version upload, followed by displaying results in in the gui followed by observations predictions and that will conclude the Weather section of this application for the time being!

# But first a celebratory beer!

---

-- 17 Aug 2021 --

Date conversion struggle bus was attributed to breaking time (rolling system clock backwards) as well as sequence of calling methods.

possible fix might be

- compare if logged date is greater than current date,
  if so write contents of db up until conflict to new db >>
  write conflict properly to new db >> clear old db >> write new db to old db >> clear new db.

Cleaned code and sequence and now to next stage.

---

-- 16 Aug 2021 --

Seperated out methods and variables between Weather Database
Hourly and Daily classes.

Created methods for getting highs, lows and avg of any column in any table in the database.

Created Conditionl statements for writing to hourly or daily table.

Program is still struggling with its ability to convert stored longs (timestamps) back into dates accurately for comparison making comparisons as currently implemented unreliable.

Will be subject of accute focus moving forward.

---

-- 15 Aug 2021 --

Cleaned up code and completed means for comparing current and logged time as dates.

Next is conditional statement and methods for averaging hourly and writing as daily values to daily table.

Program does not apprecitate system being set in reverse and hangs writing the same entries over and over. >\_<

# Do not mess with the flow of time!

---

-- 14 Aug 2021 --

Progress on SQL Database. Variables and methods in place to log to the hourly table and get required information to order to self increment id values and compare time and dates.

Next will be comparing stored vs current time to determine if it is a new day and if so gather hourly entries and write to daily values for a daily entry before recording more hourly entries.

---

-- 12 Aug 2021 --

Development has been greatly hindered due to job hours growing to 13-14 hour days and 6 day weeks. Feels like forever!

Weather DB will be created by application at first start up.

Have found means for getting current time and converting it to easily read format and storing into DB.

Possibilty to streamline database by consolidating date and time columns into single column labeled "timestamp" and then letting application convert into understandable formats for creating following methods for comparison of timestamps.

Next phase to include fleshing out methods for writing to and reading from DB for data manipultaion to finally begin.

Possibilty to streamline past described process by omitting step of writing database to map before comparing data.

Proper SQL queries and storage of variables should eliminate extra step.

---

-- 16 June 2021 --

Created getters and setters in WeatherDB. Gave Parameters to Instruments controller on logButtonReleased and now intruments fields data is captured as variables.

Next will be casting fields as numerlogical data types for comparison (doubles).

Then assigning into a map or array list, followed by calculations and storing to the database.

Also will need to look into validation process. Possibly regular expressions?

Will push changes at next stage.

---

-- 15 June 2021 --

Created weather.db in Weather package with corresponding tables (hourly, daily, weekly) in Java. Application will ship with database ready to be written to.

rough sketch of instrument data to database as follows:

enter data into fields >> click log >>
assign field data to variables
all table entries to a map or array list
assign field data to variables
create variable last date and last time
weather addtodatabase method ()
(if)
time > last time and date == last date >>
add current values as hourly entry
(if not)
all hourly values on a seperate thread calculated and stored to daily variables && all current values stored as hourly entry
(if)
amount of days stored == 7
values of everyday calculated through weekly methods and store as a weekly entry && daily values stored as new daily entry
(if)
days stored < 7
add daily entry

repository updated

---

-- 14 June 2021 --

At this time have settled on a SQLite database as a means to gather weather data in a central file. This data will be used to offer suggestions in planning out the days chores and compare past conditions. Should be a relatively simple and lightweight solution since program access is isolated.

Have also begun making notes on how database should be initiated and methods that will call data and populate
fields.

WeatherDB class has been added to Weather package.

## "While the unknown often creates the wall of dread that keeps us from moving forward, it should also be the dangling lure that calls us into the depths."

---

-- 10 June 2021 --

Have begun fleshing out weather database structure and proposed functionality. Looking forward to increased development once life stabilizes a bit.

Also optimized font size for station readout and altered getConditions method for better implementation.

Changes pushed to version branch and merged into Master.

---

-- 3 June 2021 --

Api is now called, parsed, formatted and displayed on stations sub window. Whew! Functionality for adding new coordinates and getting a read out from nearest station. Most precise coordinates provides most precise results.

Next steps will be logging conditions to a database and make chore reccomendations based on recorded weather trends.

! Future improvements may include calling a more robust weather api. Perhaps NOAA. Might also include calling coordinates by current ip address to create a sort of auto feature instead of manually entering coordinates.

Respository updated.

---

-- 31 May 2021 --

Was not receiving desired results and so began re-evaluting means to parse data from JSON response. New discoveries ARE returning desired results and so formatting of desired data has begun. Hooray!

Next steps will include continued formatting followed by setting the data to a label to be displayed on "Stations" sub window.

"Never forget those who gave their all in order for us to enjoy what we have today."

---

-- 27 May 2021 --

Found means to parse returned JSON keys and accompanying data. Next steps will include parsing nested data and formatting. Pushed to repository.

---

--19 May 2021--

Began work on methods for Weather sub window. Started with api inclusion for reading data out of a station. Verified obtaining the field input through print out. Next will be a string variable formatted to accept the field values and insert into the url address used for the API call.

Following will be parsing the returned JSON keys and applying them to a java map and variables in order to log data to the upcoming weather data base and display conditions.

Have decided to hold off on transferring to GIT hub for source control when I have already started with BitBucket.

Have not seen a great difference in functionality and really appreciate that BitBucket isn't trying to log my every sign in or machine.

Will begin next day of development work with pushing current build into repository.

---

-- 18 May 2021 --

Relocation mostly complete. Began adding UI for Stations section and fleshed out more of the functionality.
Will be migrating the project to GitHub from BitBucket in near future as per current industry standard. Would love to see that change.

---

-- 5 May 2021 --

Implemented web viewer with buttons for switching between online weather services. Created WebServicesController and updated WebServices.fxml

Beginning reasearch on JSON parsing. Plan is to implent
Open Weather Map API for stations section. Then it will be on to creating database for logging weather and comparing data. Followed by methods for observations.

Development will be on hold while relocating.

# Happy Cinco de Mayo!

---

-- 4 May 2021 --

Began creating user interface for Weather sub window.

// Created InstrumentsFXML, InstrumentsController, ObservationsFXML, WebServicesFXML and WebServicesController.

// Implemented WebServicesFXML and InstrumentsFXML in WeatherController in order to switch displays on Weather SubWindow.

//Added instruction to WeatherCSS

! Continue with implementing webview in WebServices and corresponding buttons !

! Look at creating a method in order to quickly create scene changes !

---

-- 3 May 2021 --

Base template of GUI completed and uploaded to Repository.

// A Main Window for launching 3 sub-windows for Weather, Chores and Inventory with buttons for selecting sub window and minimize and close.

// Considering swapping Inventory functionality for basic messaging functionality as database integration will be demonstrated in the application through the other sub sets.

Would still like to develop inventory functionality, but at a later time.
