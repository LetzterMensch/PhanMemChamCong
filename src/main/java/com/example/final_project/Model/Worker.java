package com.example.final_project.Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Worker extends Employee{
    private float totalShift1;
    private float totalShift2;
    private float totalShift3;

    private float overTime;

    private float totalHours;

    public Worker(String UID) throws SQLException {
        super(UID);
        String totalShift1Query = "select sum(shift1) from attendance where uid = ?";
        String totalShift2Query = "select sum(shift2) from attendance where uid = ?";
        String totalShift3Query = "select sum(shift3) from attendance where uid = ?";
        ResultSet rs = super.jdbc.view(totalShift1Query, new String[]{UID});
        rs.next();
        this.totalShift1 = rs.getFloat(1);
        rs = super.jdbc.view(totalShift2Query, new String[]{UID});
        rs.next();
        this.totalShift2 = rs.getFloat(1);
        rs = super.jdbc.view(totalShift3Query, new String[]{UID});
        rs.next();
        this.totalShift3 = rs.getFloat(1);
        this.totalHours = this.totalShift1 + this.totalShift2;
        this.overTime = this.totalShift3;
    }

    public float getTotalShift1() {
        return totalShift1;
    }

    public void setTotalShift1(float totalShift1) {
        this.totalShift1 = totalShift1;
    }

    public float getTotalShift2() {
        return totalShift2;
    }

    public void setTotalShift2(float totalShift2) {
        this.totalShift2 = totalShift2;
    }

    public float getTotalShift3() {
        return totalShift3;
    }

    public void setTotalShift3(float totalShift3) {
        this.totalShift3 = totalShift3;
    }

    public float getOverTime() {
        return overTime;
    }

    public void setOverTime(float overTime) {
        this.overTime = overTime;
    }

    public float getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(float totalHours) {
        this.totalHours = totalHours;
    }
}
