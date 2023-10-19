package cartoon;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * Here's cartoon! Your first JavaFX assignment!
 * Before you start coding your cartoon, take a look at
 * the lecture slides and JavaFX Guide for all the
 * information you'll need (and more!).
 *
 * Please put your overall comments for the project here.
 *
 */

public class App extends Application {

    @Override
    public void start(Stage stage) {
        // Create top-level object, set up the scene, and show the stage here.
        PaneOrganizer organizer = new PaneOrganizer();
        Pane root = organizer.getRoot();
        Scene scene = new Scene(root, 800, 600);

        stage.setScene(scene);
        stage.setTitle("My Cartoon");
        stage.show();

        root.requestFocus();
    }

    /*
    * Here is the mainline! No need to change this.
    */
    public static void main(String[] argv) {
        // launch is a method inherited from Application
        launch(argv);
    }
}
