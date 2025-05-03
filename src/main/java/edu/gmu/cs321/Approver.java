package edu.gmu.cs321;

import java.sql.*; 

public class Approver {
    private int id;
    private String name;
    private String email;
    private String password;

    public Approver(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters and setters...

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }

    // Method to create a new approver in the database
    public boolean createApprover() {
        String insertQuery = "INSERT INTO approvers (name, email, password) VALUES (?, ?, ?)";
        try {
            Connection connection = DB_Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("\nApprover added successfully.");
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                    System.out.println("Generated ID: " + id);
                }
                return true;
            } else {
                System.out.println("Error adding approver.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
