package cartoon;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;


public class PaneOrganizer {
    private BorderPane _root;

    public PaneOrganizer() {
        _root = new BorderPane();
        Cartoon cartoon = new Cartoon();
        _root.setCenter(cartoon.getRootPane());
        this.setupQuitButton();
    }

    public Pane getRoot() {
        return _root;
    }

    private void setupQuitButton() {
        Button quitBtn = new Button("Quit");
        quitBtn.setOnAction(e -> System.exit(0));
        _root.setBottom(quitBtn);
    }
}
