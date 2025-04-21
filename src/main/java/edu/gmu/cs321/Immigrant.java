package edu.gmu.cs321;


import java.sql.*; 

public class Immigrant {
    private int userID;
    private String first_name;
    private String last_name;
    private String birthdate;
    private String address;
    private String email;

    
    public Immigrant(String first_name, String last_name, String birthdate, String address, String email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.birthdate = birthdate;
        this.address = address;
        this.email = email;
    }

    public Immigrant(String firstName, String lastName, String dob, String address) {
        this(firstName, lastName, dob, address, "");
    }

     // Create new immigrant instance (will return false for now)
     public int createImmigrant() {
        String insertQuery = "insert into immigrant (first_name, last_name, birthdate, address, email) values (?,?,?,?,?)";
        int userID = 0;
        try {
            Connection connection = DB_Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, last_name);
            preparedStatement.setString(3, birthdate);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, email);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Immigrant added successfully.");
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    userID = generatedKeys.getInt(1);
                    System.out.println("Generated ID: " + userID);
                }
            } else {
                System.out.println("Error adding immigrant.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userID;  // placeholder for TDD
    }

    // Update an immigrant's information
    public boolean updateImmigrant(String address, String email) {
        return false;  // placeholder
    }

    // Get immigrant object (simulate retrieval by userID)
    public Immigrant getImmigrantByID(int userID) {
        return null;  // placeholder
    }

    //--------------------------------------------------------------------------------//
    //                                  SETTERS                                       //
    //--------------------------------------------------------------------------------//

    // Changes the immigrants userID
    public void setUserID(int newUserID) { this.userID = newUserID; }

    // Updates the immigrant's name
    public void setName(String first_name, String last_name) { this.first_name = first_name;
        this.last_name = last_name; }

    // Updates the immigrant's recorded birthdate
    public void setBirthdate(String newBirthdate) { this.birthdate = newBirthdate; }

    // Updates the immigrant's address of residence
    public void setAddress(String newAddress) { this.address = newAddress; }

    // Updates the immigrant's email
    public void setEmail(String newEmail) { this.email = newEmail; }


    //--------------------------------------------------------------------------------//
    //                                  GETTERS                                       //
    //--------------------------------------------------------------------------------//

    // Return the ID number assigned to the immigrant
    public int getUserID() { return userID; }

    // Return the immigrant's recorded name
    public String getName() { return first_name + last_name; }

    // Return the immigrant's birthdate
    public String getBirthdate() { return birthdate; }

    // Return the immigrant's address
    public String getAddress() { return address; }

    // Returns the immigrant's email
    public String getEmail() { return email; }
}
