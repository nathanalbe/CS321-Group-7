package edu.gmu.cs321;

import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;

public class DB_Connection {
    // loads from .env by default
    private static final Dotenv dotenv = Dotenv.load();

    private static final String DB_URL = "jdbc:mysql://localhost:3306/CS321";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = dotenv.get("DB_PASSWORD"); // Replace with your actual password

    // JDBC variables for opening and managing connection
    private static Connection connection = null;
    private static Statement statement = null;

    // Method to establish a connection to the database
    public static Connection getConnection() {
        try {
            //load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish the connection if it is not already established
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            }
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}