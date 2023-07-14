package com.example.final_project;


import com.example.final_project.Model.User;
import com.example.final_project.databaseHandler.JDBCSingleton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeHomeController implements Initializable {

    @FXML
    private Button attEditRequestBtn;

    @FXML
    private Button attRecordBtn;

    @FXML
    private Button generalInfoBtn;

    @FXML
    private Button logOutBtn;

    JDBCSingleton jdbc= JDBCSingleton.getInstance();

    private double x = 0.0;
    private double y = 0.0;

    private String uid;
    @FXML
    public void viewAttendanceRecord() throws IOException {
        this.attEditRequestBtn.getScene().getWindow().hide();
        Parent root = null;
        root = (Parent) FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("viewRecordOfficer.fxml")), new User(this.uid));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        root.setOnMousePressed((event) -> {
            this.x = event.getSceneX();
            this.y = event.getSceneY();
        });
        root.setOnMouseDragged((event) -> {
            stage.setX(event.getScreenX() - this.x);
            stage.setY(event.getScreenY() - this.y);
        });
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void makeRequest() throws IOException {
        this.attEditRequestBtn.getScene().getWindow().hide();
        Parent root = null;
        root = (Parent) FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("makeRequest.fxml")), new User(this.uid));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        root.setOnMousePressed((event) -> {
            this.x = event.getSceneX();
            this.y = event.getSceneY();
        });
        root.setOnMouseDragged((event) -> {
            stage.setX(event.getScreenX() - this.x);
            stage.setY(event.getScreenY() - this.y);
        });
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    public void logout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText((String) null);
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> option = alert.showAndWait();

        try {
            if (((ButtonType) option.get()).equals(ButtonType.OK)) {
                this.logOutBtn.getScene().getWindow().hide();
                Parent root = (Parent) FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("Login.fxml")));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                root.setOnMousePressed((event) -> {
                    this.x = event.getSceneX();
                    this.y = event.getSceneY();
                });
                root.setOnMouseDragged((event) -> {
                    stage.setX(event.getScreenX() - this.x);
                    stage.setY(event.getScreenY() - this.y);
                    stage.setOpacity(0.8);
                });
                root.setOnMouseReleased((event) -> {
                    stage.setOpacity(1.0);
                });
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }
    @FXML
    public void close() {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.uid = (String) resourceBundle.getObject("id");
        System.out.println(this.uid);
    }
}