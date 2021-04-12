package controller;

import database.Database;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.DataModel;
import view.GraphUI;
import view.SimulationView;
import waveoptics.Graph;
import waveoptics.SimulationType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private DataModel dataModel;

    private Graph myGraph;

    @FXML
    private Parent root;

    @FXML
    private SimulationView simulationArea;

    @FXML
    private GraphUI graphArea;

    @FXML
    private Slider wavelengthSlider;

    @FXML
    private Label wavelengthLabel;

    @FXML
    private Slider slitDistanceSlider;

    @FXML
    private Label slitDistanceLabel;

    @FXML
    private Slider screenDistanceSlider;

    @FXML
    private Label screenDistanceLabel;

    @FXML
    private Slider slitWidthSlider;

    @FXML
    private Label slitWidthLabel;

    @FXML
    private RadioButton singleSlitButton;

    @FXML
    private RadioButton doubleSlitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Database.createNewTable();
        dataModel = Database.getData();

        // Create the graph based on the data retrieved from the database. (Multiplication is used to convert to nanometers)
        myGraph = new Graph(dataModel.getWavelength(), dataModel.getSlitDist() * Math.pow(10, 3), dataModel.getScreenDist() * Math.pow(10, 9), dataModel.getSlitWidth() * Math.pow(10, 3));
        myGraph.createGraph();
        graphArea.createGraph(myGraph);

        // Set sliders and labels based on the data retrieved
        wavelengthLabel.setText("Wavelength: " + String.format("%.2f", dataModel.getWavelength()) + " nm");
        wavelengthSlider.setValue(dataModel.getWavelength());
        simulationArea.setWavelength(dataModel.getWavelength());

        slitDistanceLabel.setText("Distance between slits: " + String.format("%.2f", dataModel.getSlitDist()) + " µm");
        slitDistanceSlider.setValue(dataModel.getSlitDist());
        simulationArea.setSlitDistance(dataModel.getSlitDist());

        screenDistanceLabel.setText("Distance between slits and screen: " + String.format("%.2f", dataModel.getScreenDist()) + " m");
        screenDistanceSlider.setValue(dataModel.getScreenDist());
        simulationArea.setScreenDistance(dataModel.getScreenDist());

        slitWidthLabel.setText("Slit width: " + String.format("%.2f", dataModel.getSlitWidth()) + " µm");
        slitWidthSlider.setValue(dataModel.getSlitWidth());
        simulationArea.setSlitWidth(dataModel.getSlitWidth());

        simulationArea.setType(SimulationType.DOUBLE_SLIT);

        initializeListeners();

    }

    private void initializeListeners() {
        // Every time a slider is moved, the label, simulation, graph and model are updated

        wavelengthSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                double wavelength = wavelengthSlider.getValue();
                wavelengthLabel.setText("Wavelength: " + String.format("%.2f", wavelength) + " nm");
                simulationArea.setWavelength(wavelength);
                myGraph.setWl(wavelength);
                myGraph.createGraph();
                graphArea.createGraph(myGraph);
                dataModel.setWavelength(wavelength);
            }
        });

        slitDistanceSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                double distance = slitDistanceSlider.getValue();
                slitDistanceLabel.setText("Distance between slits: " + String.format("%.2f", distance) + " µm");
                simulationArea.setSlitDistance(distance);
                myGraph.setdSlits(distance * Math.pow(10, 3));
                myGraph.createGraph();
                graphArea.createGraph(myGraph);
                dataModel.setSlitDist(distance);
            }
        });

        screenDistanceSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                double distance = screenDistanceSlider.getValue();
                screenDistanceLabel.setText("Distance between slits and screen: " + String.format("%.2f", distance) + " m");
                simulationArea.setScreenDistance(distance);
                myGraph.setdSS(distance * Math.pow(10, 9));
                myGraph.createGraph();
                graphArea.createGraph(myGraph);
                dataModel.setScreenDist(distance);
            }
        });

        slitWidthSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                double width = slitWidthSlider.getValue();
                slitWidthLabel.setText("Slit width: " + String.format("%.2f", width) + " µm");
                simulationArea.setSlitWidth(width);
                myGraph.setsW(width * Math.pow(10, 3));
                myGraph.createGraph();
                graphArea.createGraph(myGraph);
                dataModel.setSlitWidth(width);
            }
        });

        ToggleGroup group = new ToggleGroup();
        singleSlitButton.setToggleGroup(group);
        doubleSlitButton.setToggleGroup(group);
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                RadioButton button = (RadioButton) group.getSelectedToggle();
                if (button.equals(singleSlitButton)) {
                    simulationArea.setType(SimulationType.SINGLE_SLIT);
                }else if (button.equals(doubleSlitButton)) {
                    simulationArea.setType(SimulationType.DOUBLE_SLIT);
                }
            }
        });

    }

    @FXML
    private void YInterferenceHandler(ActionEvent actionEvent) {
        CheckBox cb1 = (CheckBox) actionEvent.getSource();
        if (cb1.isSelected()) {
            graphArea.addGraph(1);  // Different graphs are identified by number
        } else {
            graphArea.removeGraph(1);
        }
    }

    @FXML
    private void YDiffractionHandler(ActionEvent actionEvent) {
        CheckBox cb2 = (CheckBox) actionEvent.getSource();
        if (cb2.isSelected()) {
            graphArea.addGraph(2);
        } else {
            graphArea.removeGraph(2);
        }
    }

    @FXML
    private void YTogetherHandler(ActionEvent actionEvent) {
        CheckBox cb3 = (CheckBox) actionEvent.getSource();
        if (cb3.isSelected()) {
            graphArea.addGraph(3);
        } else {
            graphArea.removeGraph(3);
        }
    }

    @FXML
    private void animationButtonHandler(ActionEvent actionEvent) {
        Button b = (Button) actionEvent.getSource();
        if (b.getText().equals("Animate")) {
            b.setText("Stop Animation");
            b.setStyle("-fx-text-fill: #a60000");
            disableButtons(true);
            simulationArea.animateWave();
        } else {
            b.setText("Animate");
            b.setStyle("-fx-text-fill: #11a600");
            disableButtons(false);
            simulationArea.stopAnimation();
        }
    }

    private void disableButtons(boolean bool) {
        // These can't be enabled during the animation because using them creates undesirable affects on the animation
        wavelengthSlider.setDisable(bool);
        slitDistanceSlider.setDisable(bool);

        singleSlitButton.setDisable(bool);
        doubleSlitButton.setDisable(bool);
    }

    @FXML
    private void HandleExitBtn(ActionEvent e) {
        System.exit(0);
    }

    @FXML
    private void HandleMenuBtn(ActionEvent e) throws IOException {
        Parent par = FXMLLoader.load(getClass().getResource("/view/StartScreen.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(par, 1000, 650));
    }

    @FXML
    private void HandleGuideBtn(ActionEvent e) throws IOException {
        Parent par = FXMLLoader.load(getClass().getResource("/view/UserGuide.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(par, 1000, 650));
    }

    @FXML
    private void HandleExpBtn(ActionEvent e) throws IOException {
        Parent par = FXMLLoader.load(getClass().getResource("/view/ExpScreen.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(par, 1000, 650));
    }

    @FXML
    private void SaveButtonHandler(ActionEvent e) {
        Database.insertData(dataModel.getWavelength(), dataModel.getSlitDist(), dataModel.getScreenDist(), dataModel.getSlitWidth());
    }

}
