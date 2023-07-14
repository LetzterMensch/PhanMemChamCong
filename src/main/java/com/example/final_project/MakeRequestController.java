package com.example.final_project;

import com.example.final_project.Model.GeneralInfo;
import com.example.final_project.Model.Officer;
import com.example.final_project.Model.Request;
import com.example.final_project.Model.User;
import com.example.final_project.databaseHandler.JDBCSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MakeRequestController implements Initializable {
    JDBCSingleton jdbc= JDBCSingleton.getInstance();

    private double x = 0.0;
    private double y = 0.0;

    private String uid;
    @FXML
    private ComboBox<String> combo_box;

    @FXML
    private TextField afternoon_session;

    @FXML
    private Button closeBtn;

    @FXML
    private TextField created_at_rq;

    @FXML
    private TextField date_edit;

    @FXML
    private TextField date_rq;

    @FXML
    private Button homeBtn;

    @FXML
    private TextField hoursEarlyLeave;

    @FXML
    private TextField hoursLate;

    @FXML
    private TextField morning_session;

    @FXML
    private TextField name_form;

    @FXML
    private TextField name_rq;

    @FXML
    private TextField position_form;

    @FXML
    private ImageView proof_rq;

    @FXML
    private TextArea reason_rq;

    @FXML
    private TableView<String> request_list;

    @FXML
    private TableColumn<String, String> rq_date;

    @FXML
    private Button saveBtn;

    @FXML
    private TextField uid_form;

    @FXML
    private TextField uid_rq;

    @FXML
    private Button uploadBtn;

    @FXML
    private TextField working_department_form;

    @FXML
    private TextField working_department_rq;

    @FXML
    private Font x1;

    @FXML
    private Font x11;

    @FXML
    private Font x3;

    @FXML
    private Color x4;

    private String name;
    private String workingDepartment;
    private String position;
    private String reason;
    private String proof;
    private String createdAt;
    private String dateToFix;
    public MakeRequestController() {
    }

    @FXML
    public void close() {
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
    public void send() throws SQLException, IOException {
//        boolean isValidFormat = this.date_rq.getText().matches("(\\d{4})-(\\d{2})-(\\d{2})");
//        if(!isValidFormat){
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Information Message");
//            alert.setHeaderText((String) null);
//            alert.setContentText("Wrong date format, please enter again!");
//            alert.showAndWait();
//            this.date_rq.setText("");
//            return;
//        }
//        this.dateToFix = this.date_rq.getText();
        this.reason = this.reason_rq.getText();
        String insertStm = "insert into requests (uid,date,reason,proof,status,created_at)  values(?,?,?,?,?,?)";
        jdbc.insert(insertStm,new String[]{this.uid,this.dateToFix,this.reason,this.proof,"pending",this.createdAt});
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText((String) null);
        alert.setContentText("Send a request successfully !");
        alert.showAndWait();
        this.saveBtn.getScene().getWindow().hide();
        Parent root = (Parent) FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("employeeHome.fxml")), new User(this.uid));
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

    @FXML
    public void upload() throws MalformedURLException {
        /*Browse to upload proof*/
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.bmp", "*.png", "*.jpg", "*.gif"));
        File file = chooser.showOpenDialog(new Stage());
        if(file != null) {
            String imagepath = file.toURI().toURL().toString();
            System.out.println("file:"+imagepath);
            this.proof = imagepath;
            Image image = new Image(imagepath);
            this.proof_rq.setImage(image);
        }
    }
    @FXML
    public void displayInfo() throws SQLException {
        // Ngay tao yeu cau
        // name (hr_system)
        // uid (done)
        // working department (hr_system)
        // date to fix
        // reason -> edit
        // proof ->edit
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        String stm =  "Select * from hr_system where id = ?";
        ResultSet rs = this.jdbc.view(stm, new String[]{this.uid});
        rs.next();
        this.name = rs.getString("name");
//        this.position = rs.getString("position");
        this.workingDepartment = rs.getString("working_department");
        this.createdAt = now.toString();

        this.name_rq.setText(this.name);
        this.created_at_rq.setText(this.createdAt);
        this.uid_rq.setText(this.uid);
        this.working_department_rq.setText(this.workingDepartment);
        stm = "Select Date from attendance where uid = ?";
        rs = jdbc.view(stm,new String[]{this.uid});
        ObservableList<String> nameList = FXCollections.observableArrayList();
        while (rs.next()) {
            nameList.add(rs.getString("Date"));
        }
        this.combo_box.setItems(nameList);
        this.combo_box.setPromptText("Chọn ngày :");
    }
    @FXML
    public void dateSelect() throws SQLException {
        this.dateToFix = this.combo_box.getValue();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.uid = (String) resourceBundle.getObject("id");
        try {
            this.displayInfo();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
