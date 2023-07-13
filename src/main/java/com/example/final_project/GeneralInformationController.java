package com.example.final_project;

import com.example.final_project.Model.GeneralInfo;
import com.example.final_project.Model.Officer;
import com.example.final_project.Model.Request;
import com.example.final_project.Model.Worker;
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
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class GeneralInformationController implements Initializable {
    private double x = 0.0;
    private double y = 0.0;
    JDBCSingleton jdbc = JDBCSingleton.getInstance();

    private GeneralInfo generalInfo;
    @FXML
    private Button closeBtn;

    @FXML
    private ComboBox<String> combo_box;

    @FXML
    private Button homeBtn;
    @FXML
    private AnchorPane anchor_box;

    @FXML
    private TableColumn<Officer, String> col_early_leave;
    @FXML
    private TableColumn<Officer, String> col_afternoon;
    @FXML
    private TableColumn<Officer, String> col_morning;
    @FXML
    private TableColumn<Officer, String> col_officer_name;
    @FXML
    private TableColumn<Officer, String> col_late_hours;

    @FXML
    private TableColumn<Officer, String> col_officer_id;

    @FXML
    private TableColumn<Worker, Float> col_overtime;
    @FXML
    private TableColumn<Worker, String> col_worker_name;

    @FXML
    private TableColumn<Officer, String> col_position_officer;

    @FXML
    private TableColumn<Worker, String> col_positon_worker;


    @FXML
    private TableColumn<Worker, String> col_worker_id;

    @FXML
    private TableColumn<Worker, Float> col_working_hours;


    @FXML
    private TableView<Officer> officer_table;

    @FXML
    private TextField total_employees;

    @FXML
    private TableView<Worker> worker_table;

    public GeneralInformationController() {
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
            this.combo_box.setPromptText("Chọn đơn vị :");
            this.combo_box.setValue(nameList.get(0));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void workingDepartmentSelect() throws SQLException {
        String selectedDepartment = this.combo_box.getValue();
        this.generalInfo = new GeneralInfo(selectedDepartment);
        System.out.println(this.combo_box.getValue());
        this.displayingDepartmentInfo(selectedDepartment);
    }
    public void displayingDepartmentInfo(String departmentName) throws SQLException {
        /* Displaying total employees */
        String stm = "select count(id) from hr_system where working_department = ?";
        ResultSet rs = jdbc.view(stm, new String[]{departmentName});
        rs.next();
        this.total_employees.setText(rs.getString(1));
        this.total_employees.setDisable(true);
        /*Displaying Officers Info*/
        if(!this.generalInfo.getOfficerList().isEmpty()){
            this.officer_table.setItems((ObservableList<Officer>) this.generalInfo.getOfficerList());
            this.col_officer_id.setCellValueFactory(new PropertyValueFactory("UID"));
            this.col_position_officer.setCellValueFactory(new PropertyValueFactory("position"));
            this.col_officer_name.setCellValueFactory(new PropertyValueFactory("name"));
            this.col_morning.setCellValueFactory(new PropertyValueFactory("totalMorningSession"));
            this.col_afternoon.setCellValueFactory(new PropertyValueFactory("totalAfternoonSession"));
            this.col_late_hours.setCellValueFactory(new PropertyValueFactory("hoursLate"));
            this.col_early_leave.setCellValueFactory(new PropertyValueFactory("hoursEarlyLeave"));
        }
        /*Displaying Workers Info*/
        if(!this.generalInfo.getWorkerList().isEmpty()){
            this.worker_table.setItems((ObservableList<Worker>) this.generalInfo.getWorkerList());
            this.col_worker_id.setCellValueFactory(new PropertyValueFactory("UID"));
            this.col_positon_worker.setCellValueFactory(new PropertyValueFactory("position"));
            this.col_worker_name.setCellValueFactory(new PropertyValueFactory("name"));
            this.col_working_hours.setCellValueFactory(new PropertyValueFactory("totalHours"));
            this.col_overtime.setCellValueFactory(new PropertyValueFactory("overTime"));
        }
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
    void close(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
         * Initializing a dropdown list of working departments.
         * */

        try {
            this.addDropDownList();
            this.workingDepartmentSelect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
