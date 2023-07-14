package com.example.final_project.databaseHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCSingleton {
    //Step 1
    // create a JDBCSingleton class.
    //static member holds only one instance of the JDBCSingleton class.

    private static JDBCSingleton jdbc;

    //JDBCSingleton prevents the instantiation from any other class.
    private JDBCSingleton() {
    }

    //Now we are providing gloabal point of access.
    public static JDBCSingleton getInstance() {
        if (jdbc == null) {
            jdbc = new JDBCSingleton();
        }
        return jdbc;
    }

    // to get the connection from methods like insert, view etc.
    //Make sure to
    // listen to your database at port 3306
    //  Create "isd_2022" database with username = "root" and no password
    private static Connection getConnection() throws ClassNotFoundException, SQLException {

        Connection con = null;
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/isd_20222", "root", "");
        return con;

    }

    //to insert the record into the database
    public int insert(String stm, String[] args) throws SQLException {
        Connection c = null;

        PreparedStatement ps = null;

        int recordCounter = 0;

        try {

            c = getConnection();
            ps = c.prepareStatement(stm);
            for (int i = 0; i < args.length; i++) {
                ps.setString(i + 1, args[i]);
            }
            System.out.println(ps);
            recordCounter = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (c != null) {
                c.close();
            }
        }
        return recordCounter;
    }

    //to view the data from the database
    public ResultSet view(String stm, String[] args) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            con = getConnection();
            ps = con.prepareStatement(stm);
            for (int i = 0; i < args.length; i++) {
                ps.setString(i + 1, args[i]);
            }
            rs = ps.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
        }
        return rs;
    }

    // to update the password for the given username
    public int update(String stm, String[] args) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;

        int recordCounter = 0;
        try {
            c = getConnection();
            ps = c.prepareStatement(stm);
            for (int i = 0; i < args.length; i++) {
                ps.setString(i + 1, args[i]);
            }
            recordCounter = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (ps != null) {
                ps.close();
            }
            if (c != null) {
                c.close();
            }
        }
        return recordCounter;
    }

    // to delete the data from the database
    public int delete(int userid) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        int recordCounter = 0;
        try {
            c = getConnection();
            ps = c.prepareStatement(" delete from userdata where uid='" + userid + "' ");
            recordCounter = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (c != null) {
                c.close();
            }
        }
        return recordCounter;
    }
}// End of JDBCSingleton class
