package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import waveoptics.SimulationType;
import waveoptics.Wavefront;

import java.util.ArrayList;

public class SimulationView extends Pane {

    private final double WIDTH = 500;
    private final double HEIGHT = 300;

    // Constants that we use to multiply the inputted values so they fit in the simulation better
    private final double SLIT_WIDTH_FACTOR = 3, SCREEN_DISTANCE_FACTOR = WIDTH / 3, SLIT_DISTANCE_FACTOR = 3, WAVELENGTH_FACTOR = 0.1;
    private final int CIRCLE_COUNT = 20;


    private SimulationType type; //SINGLE_SLIT or DOUBLE_SLIT

    private Rectangle simulationArea = new Rectangle(WIDTH, HEIGHT); //background for simulation scene

    private double slitWidth;
    private double wavelength;
    private double slitDistance;
    private double screenDistance;

    // Gaps between the lines represent the slits
    private Line singleLine1 = new Line();
    private Line singleLine2 = new Line();
    private Line singleLine3 = new Line();

    private Line screen = new Line();
    private Rectangle screenBehind = new Rectangle(); // Rectangle behind the screen to cover scene

    private ArrayList<Wavefront> waves1 = new ArrayList<>();
    private ArrayList<Wavefront> waves2 = new ArrayList<>();

    private Timeline timeline1;
    private Timeline timeline2;

    public SimulationView() {
        simulationArea.setFill(Color.LIGHTGRAY);
        simulationArea.setStroke(Color.DARKGRAY);
        this.getChildren().add(simulationArea);

        singleLine1.setStrokeWidth(2);
        singleLine2.setStrokeWidth(2);
        singleLine3.setStrokeWidth(2);

        singleLine1.setStartX(0);
        singleLine1.setEndX(0);
        singleLine2.setStartX(0);
        singleLine2.setEndX(0);
        singleLine3.setStartX(0);
        singleLine3.setEndX(0);

        screen.setStartY(0);
        screen.setEndY(HEIGHT);
        screen.setStrokeWidth(2);

        screenBehind.setWidth(WIDTH);
        screenBehind.setHeight(HEIGHT);
        screenBehind.setLayoutY(0);

        // Adds waves (circles) to the arrays
        for (int i = 1; i <= CIRCLE_COUNT; i++) {
            Wavefront wavefront = new Wavefront();
            wavefront.setFill(Color.TRANSPARENT);
            wavefront.setStroke(Color.RED);
            waves1.add(wavefront);
        }

        for (int i = 1; i <= CIRCLE_COUNT; i++) {
            Wavefront wavefront = new Wavefront();
            wavefront.setFill(Color.TRANSPARENT);
            wavefront.setStroke(Color.RED);
            waves2.add(wavefront);
        }

        this.getChildren().addAll(waves1);
        this.getChildren().addAll(waves2);
        this.getChildren().addAll(singleLine1, singleLine2, singleLine3, screenBehind, screen);


    }

    public void setType(SimulationType type) {
        this.type = type;
        updateSlits();
        updateWavefront();
    }

    public void setSlitWidth(double slitWidth) {
        this.slitWidth = slitWidth;
        updateSlits();
    }

    public void setWavelength(double wavelength) {
        this.wavelength = wavelength;
        updateWavefront();
    }

    public void setSlitDistance(double slitDistance) {
        this.slitDistance = slitDistance;
        updateSlits();
        updateWavefront();
    }

    public void setScreenDistance(double screenDistance) {
        this.screenDistance = screenDistance;
        updateScreen();
    }

    private void updateScreen() {
        screen.setStartX(screenDistance * SCREEN_DISTANCE_FACTOR);
        screen.setEndX(screenDistance * SCREEN_DISTANCE_FACTOR);

        screenBehind.setLayoutX(screenDistance * SCREEN_DISTANCE_FACTOR);
        screenBehind.setFill(Color.DARKGRAY);
    }

    private void updateSlits() {

        if (type == SimulationType.SINGLE_SLIT) {
            singleLine1.setStartY(0);
            singleLine1.setEndY(HEIGHT/2 - slitWidth/2 * SLIT_WIDTH_FACTOR);

            singleLine2.setStartY(HEIGHT);
            singleLine2.setEndY(HEIGHT/2 + slitWidth/2 * SLIT_WIDTH_FACTOR);

            singleLine3.setVisible(false); // Line 3 isn't needed when there is only one opening
        }else if (type == SimulationType.DOUBLE_SLIT) {
            singleLine1.setStartY(0);
            singleLine1.setEndY(HEIGHT/2 - slitDistance/2 * SLIT_DISTANCE_FACTOR - slitWidth/2 * SLIT_WIDTH_FACTOR);

            singleLine2.setStartY(HEIGHT);
            singleLine2.setEndY(HEIGHT/2 + slitDistance/2 * SLIT_DISTANCE_FACTOR + slitWidth/2 * SLIT_WIDTH_FACTOR);

            singleLine3.setVisible(true);
            singleLine3.setStartY(HEIGHT/2 + slitDistance/2 * SLIT_DISTANCE_FACTOR - slitWidth/2 * SLIT_WIDTH_FACTOR);
            singleLine3.setEndY(HEIGHT/2 - slitDistance/2 * SLIT_DISTANCE_FACTOR + slitWidth/2 * SLIT_WIDTH_FACTOR);
        }

    }

    private void updateWavefront() {

        if (type == SimulationType.SINGLE_SLIT) {
            for (int i = 0; i < waves1.size(); i++) {
                Wavefront wave = waves1.get(i);
                wave.setCenterX(0);
                wave.setCenterY(HEIGHT/2);
                wave.setRadius(i * wavelength * WAVELENGTH_FACTOR);
            }
            for (int i = 0; i < waves2.size(); i++) {
                Wavefront wave = waves2.get(i);
                wave.setVisible(false);
            }
        }else if (type == SimulationType.DOUBLE_SLIT) {
            for (int i = 0; i < waves1.size(); i++) {
                Wavefront wave = waves1.get(i);
                wave.setCenterX(0);
                wave.setCenterY(HEIGHT/2 + slitDistance/2 * SLIT_DISTANCE_FACTOR);
                wave.setRadius(i * wavelength * WAVELENGTH_FACTOR);
            }
            for (int i = 0; i < waves2.size(); i++) {
                Wavefront wave = waves2.get(i);
                wave.setVisible(true);
                wave.setCenterX(0);
                wave.setCenterY(HEIGHT/2 - slitDistance/2 * SLIT_DISTANCE_FACTOR);
                wave.setRadius(i * wavelength * WAVELENGTH_FACTOR);
            }
        }

    }

    public void animateWave() {
        // This is made so that it takes one second for the wave to travel one wavelength
        // When the animation button is pressed, the radius of all circles are set to zero and then they increase one wavelength per second with an interval of 1 second between each wavefront
        // Timeline controls the intervals between waves and the transition in the waves class moves the waves

        timeline1 = new Timeline();
        timeline2 = new Timeline();


        for (int i = 0; i < waves1.size(); i++) {

            Wavefront wavefront = waves1.get(i);
            wavefront.setRadius(0);

            timeline1.getKeyFrames().add(new KeyFrame(Duration.seconds(i), event -> wavefront.animate((CIRCLE_COUNT - 1) * wavelength * WAVELENGTH_FACTOR, CIRCLE_COUNT - 1)));
            // CIRCLE_COUNT - 1 because first wave is normally at position 0

        }

        // For the second set of waves if it's a double slit. Otherwise they are invisible.
        if (type == SimulationType.DOUBLE_SLIT) {
            for (int i = 0; i < waves2.size(); i++) {

                Wavefront wavefront = waves2.get(i);
                wavefront.setRadius(0);

                timeline2.getKeyFrames().add(new KeyFrame(Duration.seconds(i), event -> wavefront.animate((CIRCLE_COUNT - 1) * wavelength * WAVELENGTH_FACTOR, CIRCLE_COUNT - 1)));

            }
        }

        timeline1.setCycleCount(Timeline.INDEFINITE);
        timeline2.setCycleCount(Timeline.INDEFINITE);
        timeline1.play();
        timeline2.play();

    }

    public void stopAnimation() {
        // Stops the timeline animation and then goes into each wavefront to stop the radius transition
        timeline1.stop();
        for (Wavefront wavefront : waves1) {
            wavefront.stopAnimation();
        }
        timeline2.stop();
        for (Wavefront wavefront : waves2) {
            wavefront.stopAnimation();
        }
    }

}
