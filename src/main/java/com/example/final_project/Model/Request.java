package com.example.final_project.Model;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import static com.example.final_project.databaseHandler.database.connectDb;

public class Request extends ResourceBundle {
    @FXML
    private Button Btn;
    private int id; // request id
    private String name;
    private String UID;
    private String workingDepartment;
    private String position;
    private String createdAt;
    private String requestingDate;
    private String reason;
    private String proofURL;
    private String status;

    public Request(String none){
        this.id = 0;
        this.name = none;
        this.UID = none;
        this.workingDepartment = none;
        this.position = none;
        this.createdAt = none;
        this.requestingDate =none;
        this.reason = none  ;
        this.proofURL = none;
        this.status = none;
    }

    public Request() throws SQLException {
        String sql = "SELECT * FROM requests WHERE status = ? ";
        String info_query = "SELECT * FROM hr_system where ID = ?";
        Connection connect = connectDb();
        assert connect != null;
        PreparedStatement prepare = connect.prepareStatement(sql);
        PreparedStatement prepareInfo = connect.prepareStatement(info_query);
        prepare.setString(1, "pending");
        ResultSet result = prepare.executeQuery();
        while (!result.next()) {
            prepare = connect.prepareStatement("SELECT * FROM requests WHERE status = ? ");
            prepare.setString(1, "accepted");
//            result.close();
            result = prepare.executeQuery();
        }
        System.out.println("A");
        prepareInfo.setString(1, result.getString("uid"));
        ResultSet rs_info = prepareInfo.executeQuery();
        rs_info.next();
        this.setId(result.getInt("id"));
        this.setUID(result.getString("uid"));
        this.setName(rs_info.getString("name"));
        this.setWorkingDepartment(rs_info.getString("working_department"));
        this.setPosition(rs_info.getString("position"));
        this.setCreatedAt(result.getString("created_at"));
        this.setRequestingDate(result.getString("date"));
        this.setReason(result.getString("reason"));
        this.setProofURL(result.getString("proof"));
        this.setStatus(result.getString("status"));
    }

    @Override
    protected Object handleGetObject(@NotNull String key) {
        return null;
    }

    @NotNull
    @Override
    public Enumeration<String> getKeys() {
        return null;
    }

    public List getAllRequests() throws SQLException {
        String sql = "SELECT * FROM requests WHERE status = ? or status = ?";
        String info_query = "SELECT * FROM hr_system where ID = ?";
        Connection connect = connectDb();
        assert connect != null;
        PreparedStatement prepare = connect.prepareStatement(sql);
        PreparedStatement prepareInfo = connect.prepareStatement(info_query);
        prepare.setString(1, "pending");
        prepare.setString(2, "accepted");
        ResultSet result = prepare.executeQuery();
        ArrayList<Object> requests = new ArrayList<>();
        while (result.next()) {
            prepareInfo.setString(1, result.getString("uid"));
            ResultSet rs_info = prepareInfo.executeQuery();
            rs_info.next();
            Request request = new Request();
            request.setId(result.getInt("id"));
            request.setUID(result.getString("uid"));
            request.setName(rs_info.getString("name"));
            request.setWorkingDepartment(rs_info.getString("working_department"));
            request.setPosition(rs_info.getString("position"));
            request.setCreatedAt(result.getString("created_at"));
            request.setRequestingDate(result.getString("date"));
            request.setReason(result.getString("reason"));
            request.setProofURL(result.getString("proof"));
            request.setStatus(result.getString("status"));
            requests.add(request);
        }
        return requests;
    }

    public Button getBtn() {
        this.Btn = new Button(this.createdAt);
        this.Btn.setPrefWidth(200);
        if (Objects.equals(this.status, "rejected")) {
            this.Btn.setDisable(true);
        }
        return this.Btn;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getWorkingDepartment() {
        return workingDepartment;
    }

    public void setWorkingDepartment(String workingDepartment) {
        this.workingDepartment = workingDepartment;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }


    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getRequestingDate() {
        return requestingDate;
    }

    public void setRequestingDate(String requestingDate) {
        this.requestingDate = requestingDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getProofURL() {
        return proofURL;
    }

    public void setProofURL(String proofURL) {
        this.proofURL = proofURL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
