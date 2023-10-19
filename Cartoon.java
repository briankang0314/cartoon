package cartoon;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class Cartoon {
    private Pane _cartoonPane;
    private Circle _sun;
    private Group _flower;
    private Text _label;

    public Cartoon() {
        _cartoonPane = new Pane();
        _cartoonPane.setFocusTraversable(true);
        this.setupSun();
        this.setupGrassField();
        this.setupFlower();
        this.setupLabel();
        this.setupTimeline();
        this.setupKeyHandler();
    }

    private void setupSun() {
        _sun = new Circle(40, Color.GOLD);  // Adjusted size and color
        _sun.setCenterX(100);
        _sun.setCenterY(100);
        _cartoonPane.getChildren().add(_sun);

        for (int i = 0; i < 8; i++) {
            Line ray = new Line();
            ray.setStartX(_sun.getCenterX());
            ray.setStartY(_sun.getCenterY());
            ray.setEndX(_sun.getCenterX() + Math.cos(Math.toRadians(i * 45)) * 60);  // Adjusted length
            ray.setEndY(_sun.getCenterY() + Math.sin(Math.toRadians(i * 45)) * 60);  // Adjusted length
            ray.setStroke(Color.ORANGE);
            ray.setStrokeWidth(2);  // Adjusted stroke width
            _cartoonPane.getChildren().add(ray);
        }
    }

    private void setupFlower() {
        _flower = new Group();  // Initialize _flower as a new Group

        for (int i = 0; i < 5; i++) {
            Ellipse petal = new Ellipse(350, 350, 15, 35);
            petal.setFill(Color.LIGHTPINK);
            petal.setStroke(Color.PURPLE);
            petal.setStrokeWidth(2);
            petal.setRotate(i * 72);
            _flower.getChildren().add(petal);  // Add petal to _flower group
        }

        Line stem = new Line(350, 350, 350, 450);
        stem.setStroke(Color.DARKGREEN);
        stem.setStrokeWidth(4);
        _flower.getChildren().add(stem);  // Add stem to _flower group

        _cartoonPane.getChildren().add(_flower);  // Add _flower group to the pane
    }

    private void setupGrassField() {
        Rectangle grass = new Rectangle(800, 300);
        grass.setY(300);
        LinearGradient gradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.LIGHTGREEN), new Stop(1, Color.DARKGREEN));  // Adjusted colors
        grass.setFill(gradient);
        _cartoonPane.getChildren().add(grass);
    }

    private void setupLabel() {
        _label = new Text("Flower is photosynthesizing");
        _label.setX(200);  // Adjusted position
        _label.setY(550);
        _label.setFont(Font.font("Arial", FontWeight.BOLD, 25));  // Adjusted font and size
        _label.setFill(Color.DARKGREEN);  // Adjusted color
        _cartoonPane.getChildren().add(_label);
    }

    private void setupTimeline() {
        // Create a timeline for animating the flower petals
        Timeline timeline = new Timeline();

        // Iterate through all nodes (petals) in the _flower group
        for (Node petal : _flower.getChildren()) {
            if (petal instanceof Ellipse) {  // Check if the node is an instance of Ellipse
                // Create key frames to animate the petal's scale properties
                KeyFrame start = new KeyFrame(Duration.seconds(0),
                        new KeyValue(petal.scaleXProperty(), 1),
                        new KeyValue(petal.scaleYProperty(), 1));
                KeyFrame middle = new KeyFrame(Duration.seconds(2),
                        new KeyValue(petal.scaleXProperty(), 1.5),
                        new KeyValue(petal.scaleYProperty(), 1.5));
                KeyFrame end = new KeyFrame(Duration.seconds(4),
                        new KeyValue(petal.scaleXProperty(), 1),
                        new KeyValue(petal.scaleYProperty(), 1));

                // Add key frames to the timeline
                timeline.getKeyFrames().addAll(start, middle, end);
            }
        }

        timeline.setCycleCount(Timeline.INDEFINITE);  // Set the timeline to loop indefinitely
        timeline.play();  // Start the animation
    }


    private void setupKeyHandler() {
        _cartoonPane.setOnKeyPressed((KeyEvent e) -> this.onKeyPressed(e));
        _cartoonPane.setFocusTraversable(true);
    }

    private void onKeyPressed(KeyEvent event) {
        System.out.println("Key Pressed: " + event.getCode());  // Print the key pressed

        switch(event.getCode()) {
            case A:
                _sun.setCenterX(_sun.getCenterX() - 10);
                System.out.println("Sun Position: " + _sun.getCenterX());  // Print the sun's position
                break;
            case D:
                _sun.setCenterX(_sun.getCenterX() + 10);
                System.out.println("Sun Position: " + _sun.getCenterX());  // Print the sun's position
                break;
            default:
                break;
        }

        boolean isSunAboveFlower = _sun.getCenterX() > _flower.getBoundsInParent().getMinX() &&
                _sun.getCenterX() < _flower.getBoundsInParent().getMaxX();

        if (isSunAboveFlower) {
            _label.setText("Flower has blossomed");
            for (Node petal : _flower.getChildren()) {
                if (petal instanceof Ellipse) {
                    ((Ellipse) petal).setFill(Color.YELLOW);
                }
            }
        } else {
            _label.setText("Flower is photosynthesizing");
            for (Node petal : _flower.getChildren()) {
                if (petal instanceof Ellipse) {
                    ((Ellipse) petal).setFill(Color.TRANSPARENT);
                    System.out.println("Petals should be transparent now.");  // Print when petals should be transparent
                }
            }
        }

        event.consume();
    }

    public Pane getRootPane() {
        return _cartoonPane;
    }
}
