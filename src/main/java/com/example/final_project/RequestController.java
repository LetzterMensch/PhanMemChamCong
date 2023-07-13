package com.example.final_project;

import com.example.final_project.Model.Request;
import com.example.final_project.databaseHandler.JDBCSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.example.final_project.databaseHandler.database.connectDb;

public class RequestController implements Initializable {
    private double x = 0.0;
    private double y = 0.0;

    JDBCSingleton jdbc= JDBCSingleton.getInstance();
    private ObservableList<Request> requestList;

    private Request request;
    @FXML
    private Button acceptBtn;

    @FXML
    private Button closeBtn;

    @FXML
    private TextField created_date;

    @FXML
    private TextField date;

    @FXML
    private TextField name;

    @FXML
    private ImageView proof;

    @FXML
    private TextArea reason;

    @FXML
    private Button rejectBtn;

    @FXML
    private AnchorPane request_form;
    @FXML
    private Button homeBtn;

    @FXML
    private TableView<Request> requests_list;

    @FXML
    private TableColumn<Request, String> rq_date;

    @FXML
    private TextField uid;

    @FXML
    private TextField working_department;

    @FXML
    private Font x1;

    @FXML
    private Font x3;

    @FXML
    private Color x4;

    @FXML
    private AnchorPane anchor_box;

    public RequestController() throws SQLException {
    }

    @FXML
    void accept(ActionEvent actionEvent) throws SQLException {
        String update_query = "update requests set status = ? where id = ?";
        this.jdbc.update(update_query, new String[]{"accepted", String.valueOf(request.getId())});
//        Connection connect = connectDb();
//        assert connect != null;
//        PreparedStatement prepare = connect.prepareStatement(update_query);
//        prepare.setString(1, "accepted");
//        prepare.setInt(2, request.getId());
//        System.out.println(prepare);
//        prepare.executeUpdate();
        this.acceptBtn.getScene().getWindow().hide();
        Parent root = null;
        try {
            String stm = "Select position from hr_system where id = ?";
            ResultSet resultSet = this.jdbc.view(stm, new String[]{request.getUID()});
//            PreparedStatement preparedStatement = connect.prepareStatement(stm);
//            preparedStatement.setString(1, request.getUID());
//             = preparedStatement.executeQuery();
            resultSet.next();
            System.out.println(resultSet.getString("position"));
            if (Objects.equals(resultSet.getString("position"), "Officer")) {
                System.out.println("Officer");
                root = (Parent) FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("edit_attendance_officer.fxml")), this.request);
            } else if (Objects.equals(resultSet.getString("position"), "Worker")) {
                System.out.println("Worker");
                root = (Parent) FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("edit_attendance_worker.fxml")), this.request);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    public void requestReset() throws SQLException {
        this.addRequestList();
        this.created_date.setText("");
        this.name.setText("");
        this.date.setText("");
        this.uid.setText("");
        this.working_department.setText("");
        this.reason.setText("");
        String uri = "file:" + "E:\\20222\\ISD\\Final_Project\\src\\main\\resources\\images\\No-Image-Placeholder.svg.png";
        this.proof.setImage(new Image(uri, 101.0, 127.0, false, true));
    }

    @FXML
    void reject(ActionEvent actionEvent) throws SQLException {
        String update_query = "UPDATE requests SET status = ? WHERE id = ?";
        this.jdbc.update(update_query, new String[]{"rejected", String.valueOf(request.getId())});
//        Connection connect = connectDb();
//        assert connect != null;
//        PreparedStatement prepare = connect.prepareStatement(update_query);
//        prepare.setString(1, "rejected");
//        prepare.setInt(2, request.getId());
//        System.out.println(prepare);
//        prepare.executeUpdate();
        this.requestReset();
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

    public ObservableList<Request> addRequestList(List requests) {
        ObservableList<Request> listData = FXCollections.observableArrayList();
        while (!requests.isEmpty()) {
            Request request1 = (Request) requests.get(0);
            listData.add(request1);
            requests.remove(request1);
        }
        return listData;
    }

    public void addRequestList() throws SQLException {
        this.requestList = this.addRequestList(this.request.getAllRequests());
        this.rq_date.setCellValueFactory(new PropertyValueFactory("createdAt"));
        System.out.println(this.rq_date);
        this.requests_list.setItems(this.requestList);
    }

    public void requestSelect() {
        Request request1 = (Request) this.requests_list.getSelectionModel().getSelectedItem();
        if (request1 == null) request1 = this.request;
        int num = this.requests_list.getSelectionModel().getSelectedIndex();
        System.out.println(num);
        System.out.println(request1.getName());
        request = request1;
        if (num - 1 >= -1) {
            this.created_date.setText(request1.getCreatedAt());
            this.name.setText(request1.getName());
            this.date.setText(request1.getRequestingDate());
            this.uid.setText(request1.getUID());
            this.working_department.setText(request1.getWorkingDepartment());
            this.reason.setText(request1.getReason());
            String uri = "file:" + request1.getProofURL();
            this.proof.setImage(new Image(uri, 101.0, 127.0, false, true));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            System.out.println(this.request);
            if (resourceBundle != null) {
                //Case : There is no request;
                this.request = new Request("##");
                this.acceptBtn.setDisable(true);
                this.rejectBtn.setDisable(true);
                System.out.println(this.request);
            } else {
                this.request = new Request();
//            ArrayList requestsList = (ArrayList) request.getAllRequests();
                this.addRequestList();
                this.requestSelect();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
