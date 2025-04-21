package edu.gmu.cs321;

public class Petition {

    private String petitionID; // Identification number/code assigned to the petition
    private int petitionerID; // Identification number/code assigned to the immigrant petitioner
    private String submissionDate; // Date petition was submitted
    private String status; // Current status of the petition

    public Petition(String petitionID, int petitionerID, String submissionDate, String status) {
        this.petitionID = petitionID;
        this.petitionerID = petitionerID;
        this.submissionDate = submissionDate;
        this.status = status;
    }

    public boolean createPetition(String petitionID, int petitionerID, String submissionDate, String status) {
        Petition newPetition = new Petition(petitionID, petitionerID, submissionDate, status);
        return true;
    }

    public boolean updatePetitionID(String newPetitionID) {
        this.petitionID = newPetitionID;
        return true;
    }

    //--------------------------------------------------------------------------------//
    //                                  SETTERS                                       //
    //--------------------------------------------------------------------------------//

    // Updates the petition ID to the new inputted one
    public void setPetitionID(String newPetitionID) { this.petitionID = newPetitionID; }

    // Updates the petitioner ID to the new inputted one
    public void setPetitionerID(int newPetitionerID) { this.petitionerID = newPetitionerID; }

    // Updates petition submission date to the new inputted one
    public void setSubmissionDate(String newSubmissionDate) { this.submissionDate = newSubmissionDate; }

    // Updates petition status to the new inputted one
    public void setStatus(String newStatus) { this.status = newStatus; }



    //--------------------------------------------------------------------------------//
    //                                  GETTERS                                       //
    //--------------------------------------------------------------------------------//

    // Returns the petition ID as a string
    public String getPetitionID() { return petitionID; }

    // Returns the petitioner ID as a string
    public int getPetitionerID() { return petitionerID; }

    // Returns the recorded petition submission date
    public String getSubmissionDate() { return submissionDate; }

    // Returns the status of petition
    public String getStatus() { return status; }
}