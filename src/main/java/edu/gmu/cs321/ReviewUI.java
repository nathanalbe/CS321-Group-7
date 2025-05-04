package edu.gmu.cs321;/*
 * Copyright (c) 2012, 2014 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicInteger;

public class ReviewUI extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {

        AtomicInteger modCount = new AtomicInteger();


        // IMMIGRANT VARIABLES
        String firstName = "Joel";
        String lastName = "Ballard";
        String birthdate = "03/05/1994";
        String address = "12345 Nowhere Lane";
        String email = "JoelBall.email.com";

        //PETITION VARIABLES
        String submissionDate = "04/04/2025";
        String status = "SUBMITTED";


//        // IMMIGRANT VARIABLES
//        String firstName = "";
//        String lastName = "";
//        String birthdate = "";
//        String address = "";
//        String email = "";
//
//        //PETITION VARIABLES
//        int petitionID = -1;
//        int petitionerID = -1;
//        String submissionDate = "";
//        String status = "";


        //OBJECT CREATION (MIGHT NOT USE)
        Immigrant immigrant = new Immigrant(firstName, lastName, birthdate, address, email);
        Petition petition = new Petition(immigrant.getUserID(), submissionDate, status);

        this.primaryStage = primaryStage;
        primaryStage.setTitle("Petition Review");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        // Page Title
        Text scenetitle = new Text("Petition for Alien Fiancé(e) and Children");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 1, 2, 1);


        //--------------------------------------------------------------------------------//
        //                          IMMIGRANT INFORMATION                                 //
        //--------------------------------------------------------------------------------//


        Label Spacer01 = new Label("");
        grid.add(Spacer01, 0, 2, 2, 2);

        Label immigrantTitle = new Label("---- Immigrant (Petitioner) Information ----");
        //immigrantTitle.setFont();
        grid.add(immigrantTitle, 0, 3, 2, 2);


        Label immigrantFirstNameTitle = new Label("First Name:");
        grid.add(immigrantFirstNameTitle, 0, 5);
        TextField immigrantFirstName = new TextField(immigrant.getFirst_name());
        grid.add(immigrantFirstName, 1, 5);

        Label immigrantLastNameTitle = new Label("Last Name:");
        grid.add(immigrantLastNameTitle, 0, 6);
        TextField immigrantLastName = new TextField(immigrant.getLast_name());
        grid.add(immigrantLastName, 1, 6);

        Label immigrantBDTitle = new Label("Birthdate:");
        grid.add(immigrantBDTitle, 0, 7);
        TextField immigrantBD = new TextField(immigrant.getBirthdate());
        grid.add(immigrantBD, 1, 7);

        Label immigrantAddressTitle = new Label("Address:");
        grid.add(immigrantAddressTitle, 0, 8);
        TextField immigrantAddress = new TextField(immigrant.getAddress());
        grid.add(immigrantAddress, 1, 8);

        Label immigrantEmailTitle = new Label("Email:");
        grid.add(immigrantEmailTitle, 0, 9);
        TextField immigrantEmail = new TextField(immigrant.getEmail());
        grid.add(immigrantEmail, 1, 9);


        //--------------------------------------------------------------------------------//
        //                          PETITION INFORMATION                                  //
        //--------------------------------------------------------------------------------//


        Label Spacer02 = new Label("");
        grid.add(Spacer02, 0, 10, 2, 2);

        Label petitionTitle = new Label("------------ Petition Information ------------");
        grid.add(petitionTitle, 0, 12, 2, 2);

        Label petitionIDTitle = new Label("PetitionID:");
        grid.add(petitionIDTitle, 0, 14);
        TextField petitionIDAns = new TextField(String.valueOf(petition.getPetitionID()));
        grid.add(petitionIDAns, 1, 14);

        Label petitionerIDTitle = new Label("PetitionerID:");
        grid.add(petitionerIDTitle, 0, 15);
        TextField petitionerIDAns = new TextField(String.valueOf(petition.getPetitionerID()));
        grid.add(petitionerIDAns, 1, 15);

        Label submissionDateTitle = new Label("Submission Date:");
        grid.add(submissionDateTitle, 0, 16);
        TextField submissionDateAns = new TextField(petition.getSubmissionDate());
        grid.add(submissionDateAns, 1, 16);

        Label statusTitle = new Label("Status:");
        grid.add(statusTitle, 0, 17);
        TextField statusAns = new TextField(petition.getStatus());
        grid.add(statusAns, 1, 17);


        //--------------------------------------------------------------------------------//
        //                                    Buttons                                     //
        //--------------------------------------------------------------------------------//


        Label Spacer03 = new Label("");
        grid.add(Spacer03, 0, 18, 2, 2);

        // SPACING
        Label modCountLab = new Label("");
        grid.add(modCountLab, 0, 21);

        // DENIAL BUTTON
        Button denialButton = new Button("<");
        denialButton.setOnAction(e -> {
            try {
                new PetitionUI().start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        grid.add(denialButton, 0, 19);

        // SAVE BUTTON
        Button saveButton = new Button("save");
        saveButton.setOnAction(e -> {
            immigrant.setName(immigrantFirstName.getText(), immigrantLastName.getText());
            immigrant.setEmail(immigrantEmail.getText());
            immigrant.setAddress(immigrantAddress.getText());
            immigrant.setBirthdate(immigrantBD.getText());

            petition.setPetitionID(Integer.parseInt(petitionIDAns.getText()));
            petition.setPetitionerID(Integer.parseInt(petitionerIDAns.getText()));
            petition.setStatus(statusAns.getText());
            petition.setSubmissionDate(submissionDateAns.getText());

            modCount.getAndIncrement();
            modCountLab.setText("save count: " + modCount);

        });
        grid.add(saveButton, 1, 19);

        // APPROVAL BUTTON
        Button approvalButton = new Button(">");
        approvalButton.setOnAction(e -> {
            try {
                new ApprovalUI().start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        grid.add(approvalButton, 2, 19);


        // REVERT BUTTON
        Button revertButton = new Button("revert");
        revertButton.setOnAction(e -> {
            immigrantFirstName.setText(immigrant.getFirst_name());
            immigrantLastName.setText(immigrant.getLast_name());
            immigrantEmail.setText(immigrant.getEmail());
            immigrantAddress.setText(immigrant.getAddress());
            immigrantBD.setText(immigrant.getBirthdate());

            petitionIDAns.setText(String.valueOf(petition.getPetitionID()));
            petitionerIDAns.setText(String.valueOf(petition.getPetitionerID()));
            statusAns.setText(String.valueOf(petition.getStatus()));
            submissionDateAns.setText(String.valueOf(petition.getSubmissionDate()));
        });
        grid.add(revertButton, 0, 20);



        // FETCH BUTTON
        Button fetchButton = new Button("another");
        fetchButton.setOnAction(e -> {

        });
        grid.add(fetchButton, 2, 20);

        Scene scene = new Scene(grid, 500, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
