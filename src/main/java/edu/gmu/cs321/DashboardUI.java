package edu.gmu.cs321;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.*;

public class DashboardUI extends Application {
    private Stage primaryStage;
    private TableView<PetitionRecord> table = new TableView<>();

    public static class PetitionRecord {
        private final int petitionId;
        private final String status;

        public PetitionRecord(int petitionId, String status) {
            this.petitionId = petitionId;
            this.status = status;
        }

        public int getPetitionId() {
            return petitionId;
        }

        public String getStatus() {
            return status;
        }
    }

    public static class ImmigrantDependentRow {
        private final String firstName, lastName, birthdate, relationship;
    
        public ImmigrantDependentRow(String firstName, String lastName, String birthdate, String relationship) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.birthdate = birthdate;
            this.relationship = relationship;
        }
    
        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public String getBirthdate() { return birthdate; }
        public String getRelationship() { return relationship; }
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Immigrant Dashboard");

        Immigrant currentImmigrant = Session.getCurrentImmigrant();
        if (currentImmigrant == null) {
            showAlert("Session Error", "No user logged in.");
            try {
                new NavigationUI().start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return;
        }

        Label header = new Label("Your Submitted Petitions");
        header.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Table setup
        TableColumn<PetitionRecord, Integer> idColumn = new TableColumn<>("Petition ID");
        idColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getPetitionId()).asObject());

        TableColumn<PetitionRecord, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getStatus()));

        table.getColumns().addAll(idColumn, statusColumn);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<PetitionRecord> data = FXCollections.observableArrayList();

        try (Connection conn = DB_Connection.getConnection()) {
            String sql = "SELECT petition_id, status FROM petition WHERE userID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, currentImmigrant.getUserID());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int petitionId = rs.getInt("petition_id");
                String currentStatus = rs.getString("status");

                // Get the most recent status from workflow_records
                String workflowStatus = null;
                String wfQuery = "SELECT next_step FROM workflow_db.workflow_records WHERE form_id = ? ORDER BY created_at DESC LIMIT 1";
                try (PreparedStatement wfStmt = conn.prepareStatement(wfQuery)) {
                    wfStmt.setInt(1, petitionId);
                    ResultSet wfRs = wfStmt.executeQuery();
                    if (wfRs.next()) {
                        workflowStatus = wfRs.getString("next_step");
                    }
                }

                // Update petition status if needed
                if (workflowStatus != null && !workflowStatus.equalsIgnoreCase(currentStatus)) {
                    String updateQuery = "UPDATE petition SET status = ? WHERE petition_id = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                        updateStmt.setString(1, workflowStatus);
                        updateStmt.setInt(2, petitionId);
                        updateStmt.executeUpdate();
                    }
                }

                data.add(new PetitionRecord(petitionId, workflowStatus != null ? workflowStatus : currentStatus));
            }

            table.setItems(data);
            table.setRowFactory(tv -> {
                TableRow<PetitionRecord> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && !row.isEmpty()) {
                        PetitionRecord selected = row.getItem();
                        showPetitionPopup(selected.getPetitionId());
                    }
                });
                return row;
            });

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Database Error", "Could not retrieve petition records.");
        }

        Button createPetitionButton = new Button("Create Petition");
        Button signOutButton = new Button("Sign Out");

        createPetitionButton.setOnAction(e -> {
            try {
                new PetitionUI().start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        signOutButton.setOnAction(e -> {
            Session.clearSession();
            try {
                new NavigationUI().start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        HBox buttonBar = new HBox(10, createPetitionButton, signOutButton);
        buttonBar.setPadding(new Insets(10));

        VBox layout = new VBox(15, header, table, buttonBar);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showPetitionPopup(int petitionId) {
        Stage popup = new Stage();
        popup.initOwner(primaryStage);
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Petition Details – ID " + petitionId);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15));

        grid.add(new Label("Petition ID:"), 0, 0);
        grid.add(new Label(String.valueOf(petitionId)), 1, 0);

        grid.add(new Label("Dependents:"), 0, 1);

        TableView<ImmigrantDependentRow> depTable = new TableView<>();
        TableColumn<ImmigrantDependentRow, String> colF = new TableColumn<>("First Name");
        colF.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn<ImmigrantDependentRow, String> colL = new TableColumn<>("Last Name");
        colL.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn<ImmigrantDependentRow, String> colB = new TableColumn<>("Birthdate");
        colB.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        TableColumn<ImmigrantDependentRow, String> colR = new TableColumn<>("Relationship");
        colR.setCellValueFactory(new PropertyValueFactory<>("relationship"));
        depTable.getColumns().addAll(colF, colL, colB, colR);

        ObservableList<ImmigrantDependentRow> deps = FXCollections.observableArrayList();
        try (PreparedStatement ps = DB_Connection.getConnection().prepareStatement(
                "SELECT first_name, last_name, birthdate, relationship FROM dependent WHERE petition_id = ?")) {
            ps.setInt(1, petitionId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                deps.add(new ImmigrantDependentRow(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getDate("birthdate").toString(),
                    rs.getString("relationship")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        depTable.setItems(deps);
        depTable.setPrefHeight(120);

        grid.add(depTable, 1, 1);

        Scene scene = new Scene(grid);
        popup.setScene(scene);
        popup.showAndWait();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}