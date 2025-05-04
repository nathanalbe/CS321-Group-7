package edu.gmu.cs321;

import java.sql.*;

public class Dependent extends Immigrant{
    private String first_name;
    private String last_name;
    private String birthdate;
    private String relationship; 

    public Dependent(String first_name, String last_name, String birthdate, String relationship) {
        super(relationship, relationship, relationship, relationship); // Call the appropriate constructor of Immigrant class
        this.first_name = first_name;
        this.last_name = last_name;
        this.birthdate = birthdate;
        this.relationship = relationship;
    }

    public int createDependent(int petitionID) {
        String insertQuery = "insert into dependent (petition_id, first_name, last_name, birthdate, relationship) values (?,?,?,?,?)";
        int userID = 0;
        try {
            Connection connection = DB_Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, petitionID);
            preparedStatement.setString(2, first_name);
            preparedStatement.setString(3, last_name);
            preparedStatement.setString(4, birthdate);
            preparedStatement.setString(5, relationship);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Dependent added successfully.");
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    userID = generatedKeys.getInt(1);
                    System.out.println("Generated ID: " + userID);
                }
            } else {
                System.out.println("Error adding dependent.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userID;  // placeholder for TDD
    }
}
