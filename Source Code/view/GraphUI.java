package view;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import waveoptics.Dot;
import waveoptics.Graph;

public class GraphUI extends Pane{

    private final double pixWidth = 500;
    private final double pixHeight = 250;

    private double Ppr;
    private double resolution;
    private ArrayList<Line> yiLines;
    private ArrayList<Line> ydLines;
    private ArrayList<Line> ytLines;

    private boolean yiIsVisible;
    private boolean ydIsVisible;
    private boolean ytIsVisible;

    public GraphUI() {
        yiLines = new ArrayList<>();
        ydLines = new ArrayList<>();
        ytLines = new ArrayList<>();
        yiIsVisible = true;
        ydIsVisible = true;
        ytIsVisible = true;
        instantiateGraph();
    }

    public void instantiateGraph() {

        this.setMinSize(pixWidth, pixHeight);

        this.setMaxSize(pixWidth, pixHeight);
        this.setStyle("-fx-background-color: grey;");
    }



    public void createGraph(Graph graph) {
        this.getChildren().clear();
        yiLines.clear();
        ydLines.clear();
        ytLines.clear();
        ArrayList<Dot> dotList = graph.getDotList();
        resolution = graph.getResolution();
        Ppr = pixWidth / resolution;

        yiGraph(dotList, resolution, Ppr);
        ydGraph(dotList, resolution, Ppr);
        ytGraph(dotList, resolution, Ppr);

        Line midLine = new Line(pixWidth / 2, 0, pixWidth / 2, pixHeight);
        this.getChildren().add(midLine);
    }

    public void yiGraph(ArrayList<Dot> dotList, double resolution, double Ppr) {
        for (int i = 1; i < resolution; i++) {
            Line gLine = new Line(i - 1 * Ppr, pixHeight - dotList.get(i - 1).getYi() * pixHeight, (i) * Ppr, pixHeight - dotList.get(i).getYi() * pixHeight);
            gLine.setStyle("-fx-stroke: red;");
            gLine.setStrokeWidth(2f);
            gLine.setVisible(yiIsVisible);
            yiLines.add(gLine);
        }
        this.getChildren().addAll(yiLines);
    }

    public void ydGraph(ArrayList<Dot> dotList, double resolution, double Ppr) {
        for (int i = 1; i < resolution; i++) {
            Line gLine = new Line(i - 1 * Ppr, pixHeight - dotList.get(i - 1).getYd() * pixHeight, (i) * Ppr, pixHeight - dotList.get(i).getYd() * pixHeight);
            gLine.setStyle("-fx-stroke: blue;");
            gLine.setStrokeWidth(2f);
            gLine.setVisible(ydIsVisible);
            ydLines.add(gLine);
        }
        this.getChildren().addAll(ydLines);
    }

    public void ytGraph(ArrayList<Dot> dotList, double resolution, double Ppr) {
        for (int i = 1; i < resolution; i++) {
            Line gLine = new Line(i - 1 * Ppr, pixHeight - dotList.get(i - 1).getYt() * pixHeight, (i) * Ppr, pixHeight - dotList.get(i).getYt() * pixHeight);
            gLine.setStyle("-fx-stroke: green;");
            gLine.setStrokeWidth(2f);
            gLine.setVisible(ytIsVisible);
            ytLines.add(gLine);
        }
        this.getChildren().addAll(ytLines);
    }

    public void addGraph(int num) {
        //num 1 = yiGraph, 2 = yd, 3 = yt
        if (num == 1) {
            for (int i = 0; i < resolution - 1; i++) {
                yiLines.get(i).setVisible(true);
            }
            yiIsVisible = true;
        }
        if (num == 2) {
            for (int i = 0; i < resolution - 1; i++) {
                ydLines.get(i).setVisible(true);
            }
            ydIsVisible = true;
        }
        if (num == 3) {
            for (int i = 0; i < resolution - 1; i++) {
                ytLines.get(i).setVisible(true);
            }
            ytIsVisible = true;
        }
    }

    public void removeGraph(int num){
        //num 1 = yiGraph, 2 = yd, 3 = yt
        if (num == 1) {
            for (int i = 0; i < resolution - 1; i++) {
                yiLines.get(i).setVisible(false);
            }
            yiIsVisible = false;
        }
        if (num == 2) {
            for (int i = 0; i < resolution - 1; i++) {
                ydLines.get(i).setVisible(false);
            }
            ydIsVisible = false;
        }
        if (num == 3) {
            for (int i = 0; i < resolution - 1; i++) {
                ytLines.get(i).setVisible(false);
            }
            ytIsVisible = false;
        }
    }

}
