
package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuideController {

    @FXML
    private Parent root;

    @FXML
    private void HandleExitBtn(ActionEvent e) {
        System.exit(0);
    }

    @FXML
    private void HandleBackBtn(ActionEvent e) throws IOException{
        Parent par = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(par, 1000, 650));
    }

    @FXML
    private void HandleMenuBtn(ActionEvent e) throws IOException{
        Parent par = FXMLLoader.load(getClass().getResource("/view/StartScreen.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(par, 1000, 650));
    }

    @FXML
    private void HandleExpBtn(ActionEvent e) throws IOException{
        Parent par = FXMLLoader.load(getClass().getResource("/view/ExpScreen.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(par, 1000, 650));
    }

}
