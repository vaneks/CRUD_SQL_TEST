package com.vaneks.crud.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    static final String DATABASE_URL = "jdbc:mysql://localhost/crud";
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String USER = "";
    static final String PASSWORD = "";

    public Connection getConnection() {
        Connection connection = null;
        try {
            System.out.println("Registering JDBC driver...");
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);

            System.out.println("Connection to " + DATABASE_URL + " successfully established.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
