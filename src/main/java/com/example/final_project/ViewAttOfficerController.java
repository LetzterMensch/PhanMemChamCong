package com.example.final_project;

import com.example.final_project.databaseHandler.JDBCSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ViewAttOfficerController implements Initializable {
    private String uid;
    private double x = 0.0;
    private double y = 0.0;
    JDBCSingleton jdbc = JDBCSingleton.getInstance();
    @FXML
    private Button closeBtn;

    @FXML
    private TableColumn<?, ?> col_afternoon;

    @FXML
    private TableColumn<?, ?> col_date;

    @FXML
    private TableColumn<?, ?> col_early_leave;

    @FXML
    private TableColumn<?, ?> col_late_hours;

    @FXML
    private TableColumn<?, ?> col_morning;

    @FXML
    private ComboBox<String> combo_box;

    @FXML
    private Button homeBtn;

    @FXML
    private TextField name;

    @FXML
    private TableView<?> officer_table;

    @FXML
    private TextField position;

    @FXML
    private TextField uid_field;

    @FXML
    private TableColumn<?, ?> uid2;

    @FXML
    void close(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    public void home() {
        try {
            this.homeBtn.getScene().getWindow().hide();
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
                stage.setOpacity(0.8);
            });
            root.setOnMouseReleased((event) -> {
                stage.setOpacity(1.0);
            });
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void workingDepartmentSelect(ActionEvent event) {

    }

    public void addDropDownList() {
        String getWorkingDepartmentQuery = "SELECT DISTINCT working_department FROM hr_system";
        ObservableList<String> nameList = FXCollections.observableArrayList();
        try {
            ResultSet workingDepartment = jdbc.view(getWorkingDepartmentQuery, new String[]{});

            while (workingDepartment.next()) {
                nameList.add(workingDepartment.getString("working_department"));
            }
            this.combo_box.setItems(nameList);
            this.combo_box.setPromptText("Chọn tháng :");
            this.combo_box.setValue(nameList.get(0));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
         * Initializing a dropdown list of working departments.
         * */
        this.uid = resourceBundle.toString();
//        try {
//            this.addDropDownList();
//            this.workingDepartmentSelect();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }
}
