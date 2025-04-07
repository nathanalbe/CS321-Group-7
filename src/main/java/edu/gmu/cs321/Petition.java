package edu.gmu.cs321;

public class Petition {

    private String petitionID;
    private String petitionerID;
    private String submissionDate;
    private String status;

    public Petition(String petitionID, String petitionerID, String submissionDate, String status) {
        this.petitionID = petitionID;
        this.petitionerID = petitionerID;
        this.submissionDate = submissionDate;
        this.status = status;
    }

    public Petition createPetition(String petitionID, String petitionerID, String submissionDate, String status) { return new Petition(petitionID, petitionerID, submissionDate, status); }

    public void updatePetitionID(String newPetitionID) { this.petitionID = newPetitionID; }

    public String getPetitionID() { return petitionID; }
    
    public String getPetitionerID() { return petitionerID; }

    public String getSubmissionDate() { return submissionDate; }

    public String getStatus() { return status; }
}
