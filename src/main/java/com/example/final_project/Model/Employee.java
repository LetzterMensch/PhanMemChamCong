package com.example.final_project.Model;

import com.example.final_project.databaseHandler.JDBCSingleton;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Employee {
    JDBCSingleton jdbc= JDBCSingleton.getInstance();

    private String name;
    private String UID;
    private String workingDepartment;
    private String position;
    public Employee(String UID) throws SQLException {
        String stm = "select * from hr_system where id = ?";
        ResultSet rs =  jdbc.view(stm,new String[]{UID});
        rs.next();
        this.name = rs.getString("name");
        this.UID = UID;
        this.workingDepartment = rs.getString("working_department");
        this.position = rs.getString("position");
    }

    public Employee(String name, String UID, String workingDepartment, String position) {
        this.name = name;
        this.UID = UID;
        this.workingDepartment = workingDepartment;
        this.position = position;
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
}
