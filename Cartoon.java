package cartoon;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.input.KeyEvent;

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

    private Group _sunGroup;  // Add this field to hold the sun and its rays

    private void setupSun() {
        _sun = new Circle(40, Color.GOLD);
        _sun.setCenterX(0);  // Changed to 0
        _sun.setCenterY(0);  // Changed to 0

        RadialGradient gradient = new RadialGradient(
                0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.YELLOW),
                new Stop(1, Color.ORANGE)
        );
        _sun.setFill(gradient);

        _sunGroup = new Group();  // Initialize the sun group
        _sunGroup.getChildren().add(_sun);  // Add the sun to the group

        for (int i = 0; i < 16; i++) {
            Line ray = new Line();
            ray.setStartX(0);  // Changed to 0
            ray.setStartY(0);  // Changed to 0
            double length = 60 + i % 2 * 20;
            ray.setEndX(Math.cos(Math.toRadians(i * 22.5)) * length);  // Adjusted
            ray.setEndY(Math.sin(Math.toRadians(i * 22.5)) * length);  // Adjusted
            ray.setStroke(Color.ORANGE);
            ray.setStrokeWidth(2);
            ray.setOpacity(0.7 + i % 2 * 0.3);
            _sunGroup.getChildren().add(ray);  // Add the ray to the group
        }

        _sunGroup.setLayoutX(100 + _sun.getRadius());  // Set the group's position
        _sunGroup.setLayoutY(100 + _sun.getRadius());  // Set the group's position

        _cartoonPane.getChildren().add(_sunGroup);  // Add the group to the pane
    }

    private void setupFlower() {
        _flower = new Group();

        for (int i = 0; i < 5; i++) {
            Ellipse petal = new Ellipse(350, 350, 15, 35);
            petal.setFill(Color.TRANSPARENT);  // Set initial fill to transparent
            petal.setStroke(Color.PURPLE);
            petal.setStrokeWidth(2);
            petal.setRotate(i * 72);
            _flower.getChildren().add(petal);
        }

        Line stem = new Line(350, 350, 350, 450);
        stem.setStroke(Color.DARKGREEN);
        stem.setStrokeWidth(4);
        _flower.getChildren().add(stem);

        _cartoonPane.getChildren().add(_flower);
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
                _sunGroup.setLayoutX(_sunGroup.getLayoutX() - 10);
                if (_sunGroup.getLayoutX() < 0) {
                    _sunGroup.setLayoutX(0);
                }
                break;
            case D:
                _sunGroup.setLayoutX(_sunGroup.getLayoutX() + 10);
                if (_sunGroup.getLayoutX() > _cartoonPane.getWidth() - _sun.getRadius() * 2) {
                    _sunGroup.setLayoutX(_cartoonPane.getWidth() - _sun.getRadius() * 2);
                }
                break;
            default:
                break;
        }

        // Print the current positions of the sun and the flower
        System.out.println("Sun X Position: " + _sunGroup.getLayoutX());
        System.out.println("Flower Min X Position: " + _flower.getBoundsInParent().getMinX());
        System.out.println("Flower Max X Position: " + _flower.getBoundsInParent().getMaxX());

        boolean isSunAboveFlower = _sunGroup.getLayoutX() > _flower.getBoundsInParent().getMinX() &&
                _sunGroup.getLayoutX() < _flower.getBoundsInParent().getMaxX();

        System.out.println("Is Sun Above Flower: " + isSunAboveFlower);  // Print the condition result

        if (isSunAboveFlower) {
            _label.setText("Flower has blossomed");

            for (Node petal : _flower.getChildren()) {
                if (petal instanceof Ellipse) {
                    ((Ellipse) petal).setFill(Color.YELLOW);
                    ((Ellipse) petal).setStroke(Color.PURPLE);
                    System.out.println("Petal Color Changed to Yellow");  // Print when the petal color changes
                }
            }
        } else {
            _label.setText("Flower is photosynthesizing");

            for (Node petal : _flower.getChildren()) {
                if (petal instanceof Ellipse) {
                    ((Ellipse) petal).setFill(Color.TRANSPARENT);
                    ((Ellipse) petal).setStroke(Color.PURPLE);
                    System.out.println("Petal Color Changed to Transparent");  // Print when the petal color changes
                }
            }
        }

        event.consume();
    }


    public Pane getRootPane() {
        return _cartoonPane;
    }
}
