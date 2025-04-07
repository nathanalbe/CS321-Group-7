package edu.gmu.cs321;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isA;

public class StatusTest {
    @Test
    public void testUpdateStatus() {
        // Test the creation of a Petition
        // Test the Petition Constructor works correctly
        String petitionID = "123-456-789";
        String petitionerID = "987-654-321";
        String submissionDate = "01-01-2000";
        String status = "pending";
        Petition petition = new Petition(petitionID, petitionerID, submissionDate, status);

        String newStatus = "complete";
        // Simulate valid status update
        boolean result = Status.updateStatus(petition, newStatus);
        assertTrue(result);  // TDD placeholder

        // Simulate invalid status update (missing fields)
        result = Status.updateStatus(petition, "");
        assertTrue(result);
    }

    @Test
    public void testGetStatusHistory() {
        // Test the creation of a Petition
        // Test the Petition Constructor works correctly
        String petitionID = "123-456-789";
        String petitionerID = "987-654-321";
        String submissionDate = "01-01-2000";
        String status = "pending";
        Petition petition = new Petition(petitionID, petitionerID, submissionDate, status);

        // Simulate valid status history
        File result = Status.getStatusHistory(petition);
        assertNotNull(result);  // TDD placeholder
        assertThat(result, isA(File.class));

        // Simulate invalid status history (missing fields)
        result = Status.getStatusHistory(null);
        assertNotNull(result);  // TDD placeholder
    }

    @Test
    public void testSendStatusUpdate() {
        // Test the creation of a Petition
        // Test the Petition Constructor works correctly
        String petitionID = "123-456-789";
        String petitionerID = "987-654-321";
        String submissionDate = "01-01-2000";
        String status = "pending";
        Petition petition = new Petition(petitionID, petitionerID, submissionDate, status);

        // Simulate valid send
        boolean result = Status.sendStatusUpdate(petition);
        assertTrue(result);  // TDD placeholder
        
        // Simulate invalid send  
        result = Status.sendStatusUpdate(null);
        assertTrue(result);
    }
}