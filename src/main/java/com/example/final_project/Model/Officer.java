package com.example.final_project.Model;

import com.almasb.fxgl.ui.UI;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Officer extends Employee {
    private float hoursLate;
    private float hoursEarlyLeave;
    private int totalMorningSession;
    private int totalAfternoonSession;

    public float getHoursLate() {
        return hoursLate;
    }

    public void setHoursLate(float hoursLate) {
        this.hoursLate = hoursLate;
    }

    public float getHoursEarlyLeave() {
        return hoursEarlyLeave;
    }

    public void setHoursEarlyLeave(float hoursEarlyLeave) {
        this.hoursEarlyLeave = hoursEarlyLeave;
    }

    public int getTotalMorningSession() {
        return totalMorningSession;
    }

    public void setTotalMorningSession(int totalMorningSession) {
        this.totalMorningSession = totalMorningSession;
    }

    public int getTotalAfternoonSession() {
        return totalAfternoonSession;
    }

    public void setTotalAfternoonSession(int totalAfternoonSession) {
        this.totalAfternoonSession = totalAfternoonSession;
    }

    public Officer(String UID) throws SQLException {
        super(UID);
        String totalMorningSessionQuery = "select count(morningSession) from attendance where uid = ?";
        String totalAfternoonSessionQuery = "select count(afternoonSession) from attendance where uid = ?";
        String hoursLateQuery = "select sum(hoursLate) from attendance where uid = ?";
        String hoursEarlyLeaveQuery = "select sum(hoursEarlyLeave) from attendance where uid = ?";
        ResultSet rs = super.jdbc.view(totalMorningSessionQuery, new String[]{UID});
        rs.next();
        this.totalMorningSession = rs.getInt(1);
        rs = super.jdbc.view(totalAfternoonSessionQuery, new String[]{UID});
        rs.next();
        this.totalAfternoonSession = rs.getInt(1);
        rs = super.jdbc.view(hoursLateQuery, new String[]{UID});
        rs.next();
        this.hoursLate = rs.getFloat(1);
        rs = super.jdbc.view(hoursEarlyLeaveQuery, new String[]{UID});
        rs.next();
        this.hoursEarlyLeave = rs.getFloat(1);
    }
}
