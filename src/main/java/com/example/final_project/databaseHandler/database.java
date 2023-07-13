package com.example.final_project.databaseHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class database {
    public database() {
    }

    public static Connection connectDb() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/isd_20222", "root", "");
            return connect;
        } catch (Exception var1) {
            var1.printStackTrace();
            return null;
        }
    }
}
