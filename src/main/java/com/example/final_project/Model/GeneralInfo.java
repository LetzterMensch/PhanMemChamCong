package com.example.final_project.Model;

import com.example.final_project.databaseHandler.JDBCSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GeneralInfo {
    JDBCSingleton jdbc= JDBCSingleton.getInstance();


    private String name; // Working Department
    private ObservableList<Officer> officerList = FXCollections.observableArrayList();
    private ObservableList<Worker> workerList = FXCollections.observableArrayList();
    public GeneralInfo(){

    }

    public GeneralInfo(String name) throws SQLException {
        this.name = name;
        String getUIDQuery = "Select id from hr_system where working_department = ? and position = ?";
        /*Get all officers in the working department*/
        ResultSet rs = jdbc.view(getUIDQuery,new String[]{name,"Officer"});
        if(rs != null){
            while (rs.next()){
                this.officerList.add(new Officer(rs.getString("id")));
            }
        }
        /*Get all woker in the working department*/
        rs = jdbc.view(getUIDQuery,new String[]{name,"Worker"});
        while (rs != null && rs.next()){
            this.workerList.add(new Worker(rs.getString("id")));
        }
    }

    public JDBCSingleton getJdbc() {
        return jdbc;
    }

    public void setJdbc(JDBCSingleton jdbc) {
        this.jdbc = jdbc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Officer> getOfficerList() {
        return officerList;
    }

    public void setOfficerList(ObservableList<Officer> officerList) {
        this.officerList = officerList;
    }

    public List<Worker> getWorkerList() {
        return workerList;
    }

    public void setWorkerList(ObservableList<Worker> workerList) {
        this.workerList = workerList;
    }
}
