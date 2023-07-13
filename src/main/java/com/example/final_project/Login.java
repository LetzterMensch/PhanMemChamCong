package com.example.final_project;

import com.almasb.fxgl.app.scene.LoadingScene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static com.example.final_project.databaseHandler.database.connectDb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class Login {
    private String role;
    @FXML
    private TextField id;

    @FXML
    private Button loginBtn;

    @FXML
    private AnchorPane login_form;

    @FXML
    private TextField password;

    private double x = 0.0;
    private double y = 0.0;

    @SuppressWarnings("WARNING: Loading FXML document with JavaFX API of version 19 by JavaFX runtime of version 16")
    public void login() {
        try {
            String sql = "SELECT role FROM user WHERE id = ? and password = ?";
            Connection connect = connectDb();
            assert connect != null;
            PreparedStatement prepare = connect.prepareStatement(sql);
            prepare.setString(1, this.id.getText());
            prepare.setString(2, this.password.getText());
            System.out.println(prepare);
            ResultSet result = prepare.executeQuery();
            Alert alert;
            if (!this.id.getText().isEmpty() && !this.password.getText().isEmpty()) {
                if (result.next()) {
                    System.out.println(result.getString(1));
                    if(Objects.equals(result.getString(1), "HR")){
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText((String) null);
                        alert.setContentText("Successfully Login as an HR");
                        alert.showAndWait();
                        this.loginBtn.getScene().getWindow().hide();
                        Parent root = (Parent) FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("home.fxml")));
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
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText((String) null);
                    alert.setContentText("Wrong Username/Password");
                    alert.showAndWait();
                }
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText((String) null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }
    }

    public void close() {
        System.exit(0);
    }
}
