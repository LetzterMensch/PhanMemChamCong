package com.example.final_project;

import com.example.final_project.Model.OfficerRequest;
import com.example.final_project.Model.Request;
import com.example.final_project.Model.WorkerRequest;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.example.final_project.databaseHandler.database.connectDb;

public class SaveEditWorkerController implements Initializable {

    private double x = 0.0;
    private double y = 0.0;
    private WorkerRequest workerRequest;
    private ObservableList<Request> requestList;

    private Request request = new Request();

    JDBCSingleton jdbc = JDBCSingleton.getInstance();
    @FXML
    private Button closeBtn;

    @FXML
    private TextField shift1;

    @FXML
    private TextField shift2;

    @FXML
    private TextField shift3;
    @FXML
    private TextField created_at_rq;

    @FXML
    private TextField date_edit;

    @FXML
    private TextField date_form;

    @FXML
    private TextField date_rq;

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
    private TableView<Request> request_list;

    @FXML
    private TableColumn<Request, String> rq_date;

    @FXML
    private Button saveBtn;

    @FXML
    private TextField uid_form;

    @FXML
    private TextField uid_rq;

    @FXML
    private TextField working_department_form;

    @FXML
    private TextField working_department_rq;

    @FXML
    private Font x1;

    @FXML
    private Font x3;

    @FXML
    private Color x4;


    public SaveEditWorkerController() throws SQLException {
    }


    @FXML
    void save(ActionEvent e) throws SQLException, IOException {

        Connection connect = connectDb();
        assert connect != null;
        String sql = "update attendance set shift1 = ? , shift2 = ? , shift3 = ? where uid = ? AND date = ?";
        jdbc.update(sql,new String[]{this.shift1.getText(),
                this.shift2.getText(),
                this.shift3.getText(),
                this.request.getUID(),
                this.request.getRequestingDate()
        });
//        PreparedStatement prepare = connect.prepareStatement(sql);
//        prepare.setFloat(1, Float.parseFloat(this.shift1.getText()));
//        System.out.println(String.valueOf(this.shift1));
//        prepare.setFloat(2, Float.parseFloat(this.shift2.getText()));
//        prepare.setFloat(3, Float.parseFloat(this.shift3.getText()));
//        prepare.setInt(4, Integer.parseInt(this.request.getUID()));
//        prepare.setString(5, this.request.getRequestingDate());
//        prepare.executeUpdate();
        sql = "update requests set status = 'done' where id = ?";
        jdbc.update(sql,new String[]{String.valueOf(this.request.getId())});
//        prepare = connect.prepareStatement(sql);
//        prepare.setInt(1,this.request.getId());
//        prepare.executeUpdate();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText((String) null);
        alert.setContentText("Edit a record successfully !");
        alert.showAndWait();
        sql = "SELECT * FROM requests WHERE status = ? or status = ?";
        ResultSet result = jdbc.view(sql,new String[]{"pending","accepted"});
//        connect = connectDb();
//        assert connect != null;
//        prepare = connect.prepareStatement(sql);
//        prepare.setString(1, "pending");
//        prepare.setString(2, "accepted");
//        ResultSet result = prepare.executeQuery();
        this.saveBtn.getScene().getWindow().hide();
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
        this.request_list.setItems(this.requestList);
    }

    public void requestSelect(Request request1) throws SQLException {
//        this.workerRequest = (WorkerRequest) request1;
        Connection connect = connectDb();
        assert connect != null;
        String stm = "select shift1 , shift2 , shift3 from attendance where uid = ? AND date = ?";
        PreparedStatement preparedStatement = connect.prepareStatement(stm);
        preparedStatement.setInt(1,Integer.parseInt(request1.getUID()));
        preparedStatement.setString(2,request1.getRequestingDate());
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        this.shift1.setText(rs.getString(1));
        this.shift2.setText(rs.getString(2));
        this.shift3.setText(rs.getString(3));

        this.date_form.setText(request1.getRequestingDate());
        this.name_form.setText(request1.getName());
        this.position_form.setText(request1.getPosition());
        this.uid_form.setText(request1.getUID());
        this.working_department_form.setText(request1.getWorkingDepartment());
        this.created_at_rq.setText(request1.getCreatedAt());
        this.name_rq.setText(request1.getName());
        this.date_rq.setText(request1.getRequestingDate());
        this.uid_rq.setText(request1.getUID());
        this.working_department_rq.setText(request1.getWorkingDepartment());
        this.reason_rq.setText(request1.getReason());
        String uri;
        if(!request1.getProofURL().contains("file")){
            uri = "file:" + request1.getProofURL();
        }
        else {
            uri = request1.getProofURL();
        }
        this.proof_rq.setImage(new Image(uri, 101.0, 127.0, false, true));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ArrayList requestsList = (ArrayList) request.getAllRequests();
            this.request = new Request();
            this.addRequestList();
            this.request = (Request) resourceBundle;
            this.requestSelect(this.request);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        System.exit(0);
    }
}
