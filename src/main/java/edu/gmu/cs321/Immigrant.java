package edu.gmu.cs321;

public class Immigrant {
    private int userID = 0;
    private String name;
    private String birthdate;
    private String address;
    private String email;

    
    public Immigrant(String name, String birthdate, String address, String email) {
        this.userID = userID++;
        this.name = name;
        this.birthdate = birthdate;
        this.address = address;
        this.email = email;
        System.out.println("Immigrant created with id: " + userID);
    }

     // Create new immigrant instance (will return false for now)
     public boolean createImmigrant() {
        return false;  // placeholder for TDD
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
    public void setName(String newName) { this.name = newName; }

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
    public String getName() { return name; }

    // Return the immigrant's birthdate
    public String getBirthdate() { return birthdate; }

    // Return the immigrant's address
    public String getAddress() { return address; }

    // Returns the immigrant's email
    public String getEmail() { return email; }
}
