package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartController {

    @FXML
    private Parent root ;

    @FXML
    private void HandleStartBtn(ActionEvent e) throws IOException{
        Parent par = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
        Stage mainStage = (Stage)root.getScene().getWindow();
        mainStage.setScene(new Scene(par, 1000, 650));
    }
    
    @FXML
    private void HandleGuideBtn(ActionEvent e) throws IOException{
        Parent par = FXMLLoader.load(getClass().getResource("/view/UserGuide.fxml"));
        Stage mainStage = (Stage)root.getScene().getWindow();
        mainStage.setScene(new Scene(par, 1000, 650));
    }
    
    @FXML
    private void HandleExpBtn(ActionEvent e) throws IOException{
        Parent par = FXMLLoader.load(getClass().getResource("/view/ExpScreen.fxml"));
        Stage mainStage = (Stage)root.getScene().getWindow();
        mainStage.setScene(new Scene(par, 1000, 650));
    }
    
    @FXML
    private void HandleExitBtn(ActionEvent e) {
        System.exit(0);
    }
    
}
