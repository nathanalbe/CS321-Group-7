package edu.gmu.cs321;

import java.sql.*; 

public class Reviewer {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;

    public Reviewer(String first_name, String last_name, String email, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
    }

    // Getters and setters...

    public int getId() { return id; }
    public String getfirstName() { return first_name; }
    public String getlastName() { return last_name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public void setId(int id) { this.id = id; }
    public void setfirstName(String name) { this.first_name = name; }
    public void setlastName(String name) { this.last_name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }

    // Method to create a new reviewer in the database
    public boolean createReviewer() {
        String insertQuery = "INSERT INTO reviewers (first_name, last_name, email, password) VALUES (?, ?, ?, ?)";
        try {
            Connection connection = DB_Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, last_name);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("\nReviewer added successfully.");
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                    System.out.println("Generated ID: " + id);
                }
                return true;
            } else {
                System.out.println("Error adding reviewer.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}