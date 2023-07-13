package com.example.final_project;

import com.example.final_project.Model.Request;
import com.example.final_project.databaseHandler.JDBCSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

import static com.example.final_project.databaseHandler.database.connectDb;

public class HomeController {

    JDBCSingleton jdbc= JDBCSingleton.getInstance();

    private String UID;
    private double x = 0.0;
    private double y = 0.0;
    @FXML
    private Button attendanceReportBtn;

    @FXML
    private Button closeBtn;
    @FXML
    private Button editBtn;
    @FXML
    private Button logOutBtn;
    @FXML
    private Button employeesAttBtn;

    @FXML
    private VBox homeController;

    @FXML
    private Button importBtn;

    @FXML
    private Button viewAllBtn;

    @FXML
    void editWindow() throws IOException, SQLException {
        String sql = "SELECT * FROM requests WHERE status = ? or status = ?";
        ResultSet result = jdbc.view(sql,new String[]{"pending","accepted"});
//        Connection connect = connectDb();
//        assert connect != null;
//        PreparedStatement prepare = connect.prepareStatement(sql);
//        prepare.setString(1, "pending");
//        prepare.setString(2, "accepted");
//        ResultSet result = prepare.executeQuery();
        this.editBtn.getScene().getWindow().hide();
        Parent root = null;
        if (result.next()) {
            root = FXMLLoader.load((Objects.requireNonNull(this.getClass().getResource("proceed_request.fxml"))));
        } else {
            root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("proceed_request.fxml")), new Request("##"));
        }
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

    public void close() {
        System.exit(0);
    }
}
