package model;

public class DataModel {

    private double wavelength;  // in nanometers
    private double slitDist;    // in micrometers
    private double screenDist;  // in meters
    private double slitWidth;   // in micrometers

    public DataModel(double wavelength, double slitDist, double screenDist, double slitWidth) {
        this.wavelength = wavelength;
        this.slitDist = slitDist;
        this.screenDist = screenDist;
        this.slitWidth = slitWidth;
    }

    public double getWavelength() {
        return wavelength;
    }

    public void setWavelength(double wavelength) {
        this.wavelength = wavelength;
    }

    public double getSlitDist() {
        return slitDist;
    }

    public void setSlitDist(double slitDist) {
        this.slitDist = slitDist;
    }

    public double getScreenDist() {
        return screenDist;
    }

    public void setScreenDist(double screenDist) {
        this.screenDist = screenDist;
    }

    public double getSlitWidth() {
        return slitWidth;
    }

    public void setSlitWidth(double slitWidth) {
        this.slitWidth = slitWidth;
    }

}
