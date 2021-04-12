package waveoptics;

import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import transition.RadiusTransition;

public class Wavefront extends Circle {

    private RadiusTransition rt = new RadiusTransition();

    public Wavefront() {
        super();
    }

    public void animate(double toR, double duration) {
        rt.setCircle(this);
        rt.setDuration(Duration.seconds(duration));
        rt.setFromR(0);
        rt.setToR(toR);
        rt.setCycleCount(Timeline.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
    }

    public void stopAnimation() {
        rt.stop();
    }

}
