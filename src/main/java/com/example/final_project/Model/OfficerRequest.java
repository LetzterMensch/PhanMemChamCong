package com.example.final_project.Model;

import java.sql.SQLException;

public class OfficerRequest extends Request{
    private float morningSession;
    private float afternoonSession;
    private float hoursLate;
    private float hoursEarlyLeave;

    public OfficerRequest() throws SQLException {
        super();
    }
}
