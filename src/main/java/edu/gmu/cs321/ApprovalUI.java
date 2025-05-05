package edu.gmu.cs321;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.*;
import java.time.format.DateTimeFormatter;

public class ApprovalUI extends Application {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Approval Queue");

        // Build the TableView
        TableView<PetitionRow> table = new TableView<>();
        TableColumn<PetitionRow, Integer> colID = new TableColumn<>("Petition ID");
        colID.setCellValueFactory(new PropertyValueFactory<>("petitionId"));
        TableColumn<PetitionRow, String> colName = new TableColumn<>("Petitioner Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("petitionerName"));
        TableColumn<PetitionRow, String> colDate = new TableColumn<>("Submission Date");
        colDate.setCellValueFactory(new PropertyValueFactory<>("submissionDate"));
        TableColumn<PetitionRow, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        table.getColumns().addAll(colID, colName, colDate, colStatus);

        // Highlight approved rows & row double-click
        table.setRowFactory(tv -> {
            TableRow<PetitionRow> row = new TableRow<>() {
                @Override
                protected void updateItem(PetitionRow item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setStyle("");
                    } else if ("APPROVED".equalsIgnoreCase(item.getStatus())) {
                        setStyle("-fx-background-color: lightgreen;");
                    } else {
                        setStyle("");
                    }
                }
            };
            row.setOnMouseClicked(evt -> {
                if (evt.getClickCount() == 2 && !row.isEmpty()) {
                    showDetailPopup(row.getItem(), primaryStage, table);
                }
            });
            return row;
        });

        // Load all petitions
        ObservableList<PetitionRow> data = FXCollections.observableArrayList();
        String sql = """
            SELECT p.petition_id
                 , i.first_name
                 , i.last_name
                 , p.submitted_at
                 , p.status
                 , p.signature
              FROM petition p
              JOIN immigrant i ON p.userID = i.userID;
            """;
        try (Connection conn = DB_Connection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("petition_id");
                String name = rs.getString("first_name") + " " + rs.getString("last_name");
                String date = rs.getTimestamp("submitted_at").toLocalDateTime().toLocalDate().format(DATE_FMT);
                String st = rs.getString("status");
                byte[] sig = rs.getBytes("signature");
                data.add(new PetitionRow(id, name, date, st, sig));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        table.setItems(data);
        table.setPlaceholder(new Label("No petitions found."));

        // Layout
        VBox root = new VBox(10, new Label("Approval Queue"), table);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));
        primaryStage.setScene(new Scene(root, 700, 450));
        primaryStage.show();
    }

    private void showDetailPopup(PetitionRow petition, Stage owner, TableView<PetitionRow> table) {
        Stage popup = new Stage();
        popup.initOwner(owner);
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Petition #" + petition.getPetitionId());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15));

        // Petition info
        grid.add(new Label("Petition ID:"), 0, 0);
        grid.add(new Label(String.valueOf(petition.getPetitionId())), 1, 0);
        grid.add(new Label("Petitioner:"), 0, 1);
        grid.add(new Label(petition.getPetitionerName()), 1, 1);
        grid.add(new Label("Submitted on:"), 0, 2);
        grid.add(new Label(petition.getSubmissionDate()), 1, 2);
        grid.add(new Label("Current status:"), 0, 3);
        grid.add(new Label(petition.getStatus()), 1, 3);

        // Dependents section
        grid.add(new Label("Dependents:"), 0, 4);
        TableView<DependentRow> depTable = new TableView<>();
        TableColumn<DependentRow, String> colF = new TableColumn<>("First Name");
        colF.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn<DependentRow, String> colL = new TableColumn<>("Last Name");
        colL.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn<DependentRow, String> colB = new TableColumn<>("Birthdate");
        colB.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        TableColumn<DependentRow, String> colR = new TableColumn<>("Relationship");
        colR.setCellValueFactory(new PropertyValueFactory<>("relationship"));
        depTable.getColumns().addAll(colF, colL, colB, colR);

        ObservableList<DependentRow> deps = FXCollections.observableArrayList();
        String depSql = "SELECT first_name,last_name,birthdate,relationship "
                      + "FROM dependent WHERE petition_id = ?";
        try (PreparedStatement ps = DB_Connection.getConnection().prepareStatement(depSql)) {
            ps.setInt(1, petition.getPetitionId());
            ResultSet drs = ps.executeQuery();
            while (drs.next()) {
                deps.add(new DependentRow(
                  drs.getString("first_name"),
                  drs.getString("last_name"),
                  drs.getDate("birthdate").toString(),
                  drs.getString("relationship")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        depTable.setItems(deps);
        depTable.setPrefHeight(120);
        grid.add(depTable, 1, 4);

        // Signature and actions begin at row 5
        grid.add(new Label("Signature:"), 0, 5);
        if (petition.getSignatureData() != null) {
            ImageView iv = new ImageView(
                new Image(new ByteArrayInputStream(petition.getSignatureData()))
            );
            iv.setFitWidth(300);
            iv.setFitHeight(150);
            iv.setPreserveRatio(true);
            StackPane ivPane = new StackPane(iv);
            ivPane.setStyle("-fx-border-color:black; -fx-border-width:1;");
            grid.add(ivPane, 1, 5);
        } else {
            // Drawing canvas
            Canvas sigCanvas = new Canvas(300, 150);
            GraphicsContext gc = sigCanvas.getGraphicsContext2D();
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            sigCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
                gc.beginPath();
                gc.moveTo(e.getX(), e.getY());
                gc.stroke();
            });
            sigCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
            });
            StackPane sigPane = new StackPane(sigCanvas);
            sigPane.setStyle("-fx-border-color:black; -fx-border-width:1;");
            grid.add(sigPane, 1, 5);

            // Clear button
            Button btnClear = new Button("Clear");
            btnClear.setOnAction(e -> 
                gc.clearRect(0, 0, sigCanvas.getWidth(), sigCanvas.getHeight())
            );
            grid.add(btnClear, 1, 6);

            // Approve / Deny
            HBox buttons = new HBox(10);
            Button btnApprove = new Button("Approve");
            Button btnDeny    = new Button("Deny");
            buttons.getChildren().addAll(btnApprove, btnDeny);
            grid.add(buttons, 0, 7, 2, 1);

            // Approve handler
            btnApprove.setOnAction(e -> {
                // Snapshot to image
                WritableImage snap = new WritableImage(
                  (int)sigCanvas.getWidth(), (int)sigCanvas.getHeight()
                );
                sigCanvas.snapshot(null, snap);
                byte[] sigBytes = null;
                try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    ImageIO.write(SwingFXUtils.fromFXImage(snap, null), "png", baos);
                    sigBytes = baos.toByteArray();
                } catch (IOException ioEx) {
                    ioEx.printStackTrace();
                }

                // Update DB
                String upd = "UPDATE petition SET status = ?, signature = ? " + "WHERE petition_id = ?";
                try (PreparedStatement ps = DB_Connection.getConnection().prepareStatement(upd)) {
                    ps.setString(1, "APPROVED");
                    ps.setBytes(2, sigBytes);
                    ps.setInt(3, petition.getPetitionId());
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                petition.setStatus("APPROVED");
                petition.setSignatureData(sigBytes);
                table.refresh();
                new Alert(Alert.AlertType.INFORMATION, "Petition approved and signed.").showAndWait();
                popup.close();
            });

            // Deny handler
            btnDeny.setOnAction(e -> {
                String upd = "UPDATE petition SET status = ? WHERE petition_id = ?";
                try (PreparedStatement ps = DB_Connection.getConnection().prepareStatement(upd)) {
                    ps.setString(1, "REVIEW");
                    ps.setInt(2, petition.getPetitionId());
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                petition.setStatus("REVIEW");
                table.refresh();
                popup.close();
                try {
                    new ReviewUI().start(owner);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }

        popup.setScene(new Scene(grid));
        popup.showAndWait();
    }

    /** Holds dependent info for the popup table */
    public static class DependentRow {
        private final String firstName, lastName, birthdate, relationship;
        public DependentRow(String fn, String ln, String bd, String rel) {
            this.firstName = fn;
            this.lastName = ln;
            this.birthdate = bd;
            this.relationship = rel;
        }

        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public String getBirthdate() { return birthdate; }
        public String getRelationship() { return relationship; }
    }

    /** Holds one row in the table, including optional signature data. */
    public static class PetitionRow {
        private final int petitionId;
        private final String petitionerName;
        private final String submissionDate;
        private String status;
        private byte[] signatureData;

        public PetitionRow(int petitionId, String petitionerName, String submissionDate, String status, byte[] signatureData) {
            this.petitionId = petitionId;
            this.petitionerName = petitionerName;
            this.submissionDate = submissionDate;
            this.status = status;
            this.signatureData = signatureData;
        }

        public int getPetitionId() { return petitionId; }
        public String getPetitionerName() { return petitionerName; }
        public String getSubmissionDate(){ return submissionDate; }
        public String getStatus() { return status; }
        public byte[] getSignatureData() { return signatureData; }
        public void setStatus(String s) { status = s; }
        public void setSignatureData(byte[] b) { signatureData = b; }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
