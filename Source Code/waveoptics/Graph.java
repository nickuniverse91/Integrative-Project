package waveoptics;

import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author matth
 */
public class Graph {

    private final int SCREENWIDTH = 1;
    private double wl; //wavelength (nanometers)
    private double dSlits; //distance between slits (nanometer)
    private double dSS; //distance between slits and screen (nanometer)
    private double sW; //slit width
    private double fringeDist; //distance to first fringe (nanometers)
    private final double screenWidth = 20; //width from left to right of the graph (nanometer// )

    private ArrayList<Dot> dotList;

    private int resolution = 500;

    public Graph(double wl, double dSlits, double dSS, double sW) {
        this.wl = wl;
        this.dSlits = dSlits;
        this.dSS = dSS;
        this.sW = sW;
    }

    public double fringeDistance(double slitDist, double wavelength, double slitScreenDist) {
        double fD = ((slitScreenDist * wavelength) / slitDist); //calculated in nanometers

        return fD;
    }

    public void createGraph() {
        fringeDist = fringeDistance(dSlits, wl, dSS);
        fringeDist = fringeDist * Math.pow(10, -7);
        double dotDist = screenWidth / resolution;

        dotList = new ArrayList<Dot>();

        //System.out.println(fringeDist);

        //Yi (The y value of interference pattern)
        for (int i = 0; i < resolution; i++) {
            dotList.add(new Dot());
            dotList.get(i).setX((-screenWidth / 2) + i * dotDist);

            dotList.get(i).setYi((Math.cos((2 / fringeDist * Math.PI) * dotList.get(i).getX()) + 1) / 2);
            //System.out.println(dotList.get(i).getX() + " --- " + dotList.get(i).getYi());
        }


        //Yd (The y value of the diffraction pattern)
        for (int i = 0; i < resolution; i++) {
            dotList.get(i).setM(dotList.get(i).getX() / fringeDist);
            double B = ((dotList.get(i).getM() * Math.PI * sW)/ dSlits);

            dotList.get(i).setYd(Math.pow((Math.sin(B)/B), 2));
            //System.out.println(dotList.get(i).getX() + " --- " + dotList.get(i).getYd());

        }

        //Yd (y value for both together
        for(int i = 0; i < resolution; i++){
            dotList.get(i).setYt(dotList.get(i).getYi() * dotList.get(i).getYd());
        }
    }

    public ArrayList<Dot> getDotList() {
        return dotList;
    }

    public double getWl() {
        return wl;
    }

    public void setWl(double wl) {
        this.wl = wl;
    }

    public double getdSlits() {
        return dSlits;
    }

    public void setdSlits(double dSlits) {
        this.dSlits = dSlits;
    }

    public double getdSS() {
        return dSS;
    }

    public void setdSS(double dSS) {
        this.dSS = dSS;
    }

    public double getsW() {
        return sW;
    }

    public void setsW(double sW) {
        this.sW = sW;
    }

    public int getResolution() {
        return resolution;
    }
}

