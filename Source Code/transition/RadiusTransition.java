package transition;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

// Custom Transition that will increase or decrease the radius of a circle
public class RadiusTransition extends Transition {

    private double toR;
    private double fromR;
    private Circle circle;

    public RadiusTransition(Duration duration, Circle circle) {
        this.circle = circle;
        setCycleDuration(duration);
        this.setInterpolator(Interpolator.LINEAR);
        // Set some initial values
        fromR = 1;
        toR = 1;
    }

    public RadiusTransition(Duration duration) {
        this(duration, null);
    }

    public RadiusTransition() {
        //Default Duration
        this(Duration.seconds(5));
    }

    @Override
    protected void interpolate(double frac) {
        double x = fromR + frac * (toR - fromR);
        circle.setRadius(x);
    }

    public double getToR() {
        return toR;
    }

    public void setToR(double toR) {
        this.toR = toR;
    }

    public double getFromR() {
        return fromR;
    }

    public void setFromR(double fromR) {
        this.fromR = fromR;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public void setDuration(Duration duration) {
        setCycleDuration(duration);
    }

}
