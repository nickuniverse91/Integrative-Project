package waveoptics;

public class Dot {

    private double x; //x
    private double yi; //y interference
    private double yd; //y diffraction
    private double yt; //y total
    private double m; //bright fringe

    public void setX(double x) {
        this.x = x;
    }

    public void setYi(double yi) {
        this.yi = yi;
    }

    public void setYd(double yd) {
        this.yd = yd;
    }

    public void setYt(double yt) {
        this.yt = yt;
    }

    public void setM(double m) {
        this.m = m;
    }

    public double getX() {
        return x;
    }

    public double getYi() {
        return yi;
    }

    public double getYd() {
        return yd;
    }

    public double getYt() {
        return yt;
    }

    public double getM() {
        return m;
    }

}
