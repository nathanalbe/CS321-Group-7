package edu.gmu.cs321;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ScreenTest {
        @Test
    public void testDisplayPetition() {
        // Test the creation of a Petition
        // Test the Petition Constructor works correctly
        String petitionID = "123-456-789";
        String petitionerID = "987-654-321";
        String submissionDate = "01-01-2000";
        String status = "pending";
        Petition petition = new Petition(petitionID, petitionerID, submissionDate, status);

        // Simulate valid petition display
        boolean result = Screen.displayPetition(petition);
        assertTrue(result);  // TDD placeholder
    }    
}
