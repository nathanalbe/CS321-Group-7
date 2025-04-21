package edu.gmu.cs321;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ApprovalUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Approval Role - Approval Queue");

        // Create the TableView using Petition as the data model.
        TableView<Petition> table = new TableView<>();

        // Petition ID column (using getter getPetitionID())
        TableColumn<Petition, String> colID = new TableColumn<>("Petition ID");
        colID.setCellValueFactory(new PropertyValueFactory<>("petitionID"));

        // Petitioner Name column (using getter getPetitionerID(), assuming it holds the name)
        TableColumn<Petition, String> colName = new TableColumn<>("Petitioner Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("petitionerID"));

        // Submission Date column (using getter getSubmissionDate())
        TableColumn<Petition, String> colDate = new TableColumn<>("Submission Date");
        colDate.setCellValueFactory(new PropertyValueFactory<>("submissionDate"));

        // Reviewer Notes column. Since your Petition class doesn't have reviewer notes,
        // we provide a default value.
        TableColumn<Petition, String> colNotes = new TableColumn<>("Reviewer Notes");
        colNotes.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty("Pending review")
        );

        table.getColumns().addAll(colID, colName, colDate, colNotes);

        // Dummy data using your Petition class. Make sure your Petition class has appropriate getters.
        ObservableList<Petition> petitionList = FXCollections.observableArrayList(
                new Petition("P00112233", 928375014, "03/01/2025", "SUBMITTED"),
                new Petition("P00112234", 498175705, "03/05/2025", "SUBMITTED")
        );
        table.setItems(petitionList);

        // Sort by submission date (oldest first). This requires that the submission date format allows proper string sorting.
        table.getSortOrder().add(colDate);

        // Display placeholder text if no petitions are available.
        table.setPlaceholder(new Label("No petitions require approval at this time."));

        // Double-clicking a row simulates opening the petition's detailed view.
        table.setRowFactory(tv -> {
            TableRow<Petition> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Petition rowData = row.getItem();
                    javafx.scene.control.Alert detailAlert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                    detailAlert.setTitle("Petition Detail");
                    detailAlert.setHeaderText("Detail View for Petition: " + rowData.getPetitionID());
                    detailAlert.setContentText("This would open the petition's detailed view.");
                    detailAlert.showAndWait();
                }
            });
            return row;
        });

        // Layout the scene.
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);
        Text title = new Text("Approval Queue");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        vbox.getChildren().addAll(title, table);

        Scene scene = new Scene(vbox, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
